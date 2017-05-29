package mijey.kau_javaproject2017;

/**
 * Created by Yeji Moon on 2017-05-29.
 */

//Natural Language Processing
public class NLP {
    private int type, year, date, time;
    private String memo;

    public NLP(String msg){
        type = 0;
        year = 0;
        date = 0;
        time = 0;
        memo = msg;
    }

    public int getType(){return type;}
    public int getYear(){return year;}
    public int getDate(){return date;}
    public int getTime(){return time;}
    public String getMemo(){return memo;}
}
