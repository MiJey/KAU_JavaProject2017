package mijey.kau_javaproject2017;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yeji Moon on 2017-05-29.
 */

//Natural Language Processing
public class NLP {
    /**
     * type 0: 연/월/일 정보가 다 있음 ex)오늘, 내일, 모레, 이번주무슨요일, 다음주무슨요일, 몇년몇월몇일
     * type 1: 요일정보가 없음(일요일이 기본값) ex)이번주, 다음주
     * type 2: 일 정보가 없음(해당 월 마지막일이 기본값) ex)이번달, 다음달, 내년몇월
     * type 3: 월/일 정보가 없음(해당 년도 마지막달 마지막일이 기본값) ex)올해
     * type 4: 연/월/일 정보가 없음(그냥 메모로 분류)
     */
    private int type;
    private Date date;
    private String memo;

    SimpleDateFormat sdt = new SimpleDateFormat("yyyy-mm-dd hh:mm");
    DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);

    public NLP(String msg){
        //자연어를 Date로 바꿔줘야 함 --> 분석하기
        Date now = new Date();

        type = 0;
        date = now;
        memo = msg;
    }

    public NLP(int _type, String _date, String _memo) throws ParseException {
        type = _type;
        date = sdt.parse(_date);
        memo = _memo;
    }

    public String getNaturalDate(){
        String nl = df.format(date) + "까지 ";
        return nl;
    }

    public int getType(){return type;}
    public String getDate(){return sdt.format(date);}
    public String getMemo(){return memo;}
}
