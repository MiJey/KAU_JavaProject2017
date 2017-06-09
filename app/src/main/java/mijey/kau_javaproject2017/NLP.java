package mijey.kau_javaproject2017;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//Natural Language Processing
public class NLP {
    private int type;
    private Calendar date;
    private String memo;

    SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    SimpleDateFormat yMdSDF = new SimpleDateFormat("yyyy-MM-dd");

    public NLP(String msg){ //EditText --> DB
        /*
        A유추가능 T오늘 L디폴트(막날),표시x
        없을때가중치    월(8)일(4)  시간(2)  요일(1)
        0       (yyyy년) mm월 dd일 hh시(mm분) O
        1       (yyyy년) mm월 dd일 hh시(mm분) X
        2    	(yyyy년) mm월 dd일 LL시(LL분) O
        3  	    (yyyy년) mm월 dd일 LL시(LL분) X
        4→0	(yyyy년) mm월 AA일 hh시(mm분) O
        5→1	(yyyy년) mm월 TT일 hh시(mm분) X
        6→2	(yyyy년) mm월 AA일 LL시(LL분) O
        7   	(yyyy년) mm월 LL일 LL시(LL분) X
        8   	(yyyy년) TT월 dd일 hh시(mm분) O
        9   	(yyyy년) TT월 dd일 hh시(mm분) X
        10  	(yyyy년) TT월 dd일 LL시(LL분) O
        11  	(yyyy년) TT월 dd일 LL시(LL분) X
        12→8	(yyyy년) TT월 AA일 hh시(mm분) O
        13 	    (yyyy년) TT월 TT일 hh시(mm분) X
        14→10	(yyyy년) TT월 AA일 LL시(LL분) O
        15  	(yyyy년) LL월 LL일 LL시(LL분) X
        16      그냥 메모
        */
        //자연어를 Calendar로 바꿔줘야 함 --> 분석하기
        Calendar now = Calendar.getInstance();

        type = 0;
        date = now;
        memo = msg;
    }

    public NLP(int _type, String _date, String _memo) throws ParseException {   //DB --> ListView
        type = _type;
        date = Calendar.getInstance();
        date.setTime(SDF.parse(_date));
        memo = _memo;
    }

    public String getNaturalDate(){


        /*
        A유추가능 T오늘 L디폴트(막날),표시x
        없을때가중치    월(8)일(4)  시간(2)  요일(1)
        0       (yyyy년) mm월 dd일 hh시(mm분) O
        1       (yyyy년) mm월 dd일 hh시(mm분) X
        2    	(yyyy년) mm월 dd일 LL시(LL분) O
        3  	    (yyyy년) mm월 dd일 LL시(LL분) X
        4→0	(yyyy년) mm월 AA일 hh시(mm분) O
        5→1	(yyyy년) mm월 TT일 hh시(mm분) X
        6→2	(yyyy년) mm월 AA일 LL시(LL분) O
        7   	(yyyy년) mm월 LL일 LL시(LL분) X
        8   	(yyyy년) TT월 dd일 hh시(mm분) O
        9   	(yyyy년) TT월 dd일 hh시(mm분) X
        10  	(yyyy년) TT월 dd일 LL시(LL분) O
        11  	(yyyy년) TT월 dd일 LL시(LL분) X
        12→8	(yyyy년) TT월 AA일 hh시(mm분) O
        13 	    (yyyy년) TT월 TT일 hh시(mm분) X
        14→10	(yyyy년) TT월 AA일 LL시(LL분) O
        15  	(yyyy년) LL월 LL일 LL시(LL분) X
        16      그냥 메모
        */
        if(type == 16) return "";

        Calendar now = Calendar.getInstance();
        long dif = differenceOfDays(date, now);

        if(dif < 0) return dif+"지난날";
        else if(dif == 0) return dif+"오늘까지";
        else if(dif == 1) return dif+"내일까지";
        else if(dif == 2) return dif+"모레까지";

        String nl = ""; //sdf.format(date.getTime());

        if(type/2 == 0)
            nl = DayOfWeek(date.get(Calendar.DAY_OF_WEEK));

        //이번주
        //다음주
        if(date.get(Calendar.MONTH) == now.get(Calendar.MONTH))
            nl = "이번 달" + (nl==""?"":" ") + nl;
        //다음달
        else
            nl = nl + date.get(Calendar.MONTH) + "월 ";

        if(date.get(Calendar.YEAR) != now.get(Calendar.YEAR))
            nl = date.get(Calendar.YEAR) + "년" + (nl==""?"":" ") + nl;

        return nl + "까지 ";
    }

    public int getType(){return type;}
    public String getDate(){return SDF.format(date.getTime());}
    public String getMemo(){return memo;}

    private String DayOfWeek(int d){
        final String[] week = {"", "일", "월", "화", "수", "목", "금", "토"};
        return week[d] + "요일";
    }

    private long differenceOfDays(Calendar d1, Calendar d2){
        long diff = (d1.getTimeInMillis() - d2.getTimeInMillis())/1000/60/60/24;   //return d1-d2
        return diff;
    }
}
