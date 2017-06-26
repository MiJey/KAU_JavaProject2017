package mijey.kau_javaproject2017;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//Natural Language Processing
public class NLP {
    private final int NORMAL = 0, MEMO = 1;
    private int type;
    private Calendar date;
    private String memo;
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    final String[] week = {"", "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};

    //EditText --> DB
    public NLP(String msg){
        int idx = msg.indexOf("까지");
        date = Calendar.getInstance();

        if (idx > 0) {
            String d = msg.substring(0, idx).replaceAll("\\s", "");
            String m = msg.substring(idx + 2).trim();

            type = NORMAL;
            memo = m;

            if (d.contains("오늘")) return;
            if (d.contains("내일")) {
                date.add(Calendar.DATE, 1);
                return;
            }
            if(d.contains("모레")){
                date.add(Calendar.DATE, 2);
                return;
            }
            if(d.contains("이번주")){
                for(int i = 1; i <= 7; i++){
                    if(d.contains(week[i])){
                        int dow = date.get(Calendar.DAY_OF_WEEK);
                        if(i - dow < 0){
                            type = MEMO;
                            memo = msg;
                            return;
                        }
                        date.add(Calendar.DATE, i - dow);
                        return;
                    }
                }
            }
            if(d.contains("다음주")){
                for(int i = 1; i <= 7; i++){
                    if(d.contains(week[i])){
                        int dow = date.get(Calendar.DAY_OF_WEEK);
                        date.add(Calendar.DATE, i + 7 - dow);
                        return;
                    }
                }
            }
        }

        //메모
        type = MEMO;
        memo = msg;
    }

    //DB --> ListView
    public NLP(int _type, String _date, String _memo) throws ParseException {
        type = _type;
        date = Calendar.getInstance();
        date.setTime(SDF.parse(_date));
        memo = _memo;
    }

    public String getNaturalDate() {
        if (type == MEMO) return "[메모] ";

        Calendar now = Calendar.getInstance();
        long dif = differenceOfDays(now, date);

        if (dif < -1) return "[삭제] ";
        if (dif == -1) return "어제까지 ";
        if (dif == 0) return "오늘까지 ";
        if (dif == 1) return "내일까지 ";
        if (dif == 2) return "모레까지 ";

        int nowdow = now.get(Calendar.DAY_OF_WEEK);
        int dow = date.get(Calendar.DAY_OF_WEEK);
        if (dif < 7 && nowdow < dow) return "이번 주 " + week[dow] + "까지 ";
        if ((dif < 7 && dow < nowdow) || (dif < 14 && nowdow < dow)) return "다음 주 " + week[dow] + "까지 ";

        String nl = (date.get(Calendar.MONTH) + 1) + "월 " + date.get(Calendar.DATE) + "일까지 ";
        if (now.get(Calendar.YEAR) != date.get(Calendar.YEAR)) nl = date.get(Calendar.YEAR) + "년 ";
        return nl;
    }

    public int getType(){return type;}
    public String getDate(){return SDF.format(date.getTime());}
    public String getMemo(){return memo;}

    private long differenceOfDays(Calendar d1, Calendar d2){
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(d1.get(Calendar.YEAR), d1.get(Calendar.MONTH), d1.get(Calendar.DATE));
        end.set(d2.get(Calendar.YEAR), d2.get(Calendar.MONTH), d2.get(Calendar.DATE));
        long diff = (end.getTimeInMillis() - start.getTimeInMillis())/1000/60/60/24;
        return diff;
    }
}
