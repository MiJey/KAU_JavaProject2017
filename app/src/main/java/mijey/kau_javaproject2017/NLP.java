package mijey.kau_javaproject2017;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//Natural Language Processing
public class NLP {
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
    private int type;
    private Calendar date;
    private String memo;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmm");
    //DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);

    public NLP(String msg){
        //자연어를 Calendar로 바꿔줘야 함 --> 분석하기
        Calendar now = Calendar.getInstance();

        type = 0;
        date = now;
        memo = msg;
    }

    public NLP(int _type, String _date, String _memo) throws ParseException {
        type = _type;
        date.setTime(sdf.parse("yyyymmddhhmm"));
        //date.setTime(sdf.parse("yyyy-mm-dd hh:mm"));
        memo = _memo;
    }

    public String getNaturalDate(){
        String nl = sdf.format(date.getTime());
        switch(type) {
            case 0: //(yyyy년) mm월 dd일 hh시(mm분) O
            case 4: //(yyyy년) mm월 AA일 hh시(mm분) O
                //nl = df.format(date);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            default: //그냥 메모
                return "";
        }
        return nl + "까지 ";
    }

    public int getType(){return type;}
    public String getDate(){return sdf.format(date.getTime());}
    public String getMemo(){return memo;}
}
