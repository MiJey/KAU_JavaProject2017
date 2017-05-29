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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        Date d = new Date(System.currentTimeMillis());
        date = dateFormat.format(d);
        memo = msg;
    }

    public int getType(){return type;}
    public String getDate(){return date;}
    public String getMemo(){return memo;}
}
