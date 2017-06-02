package mijey.kau_javaproject2017;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yeji Moon on 2017-05-29.
 */

//Natural Language Processing
public class NLP {
    private int type;
    private String date;
    private String memo;

    public NLP(String msg){
        //자연어를 Date로 바꿔줘야 함 --> 분석하기
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        Date d = new Date(System.currentTimeMillis());
        date = dateFormat.format(d);
        memo = msg;
    }

    public NLP(int _type, String _date, String _memo){
        type = _type;
        date = _date;
        memo = _memo;
    }

    public String getNaturalDate(){
        String nl = date + "쀼쀼뺘뺘";
        return nl;
    }

    public int getType(){return type;}
    public String getDate(){return date;}
    public String getMemo(){return memo;}
}
