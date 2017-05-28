package mijey.kau_javaproject2017;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //id    type    year    date    time    memo
        //123   0       2017    0528    1925    작성중
        db.execSQL("CREATE TABLE TODOLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, year INTEGER, date INTEGER, time INTEGER, memo TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(int type, int year, int date, int time, String memo){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MONEYBOOK VALUES(null, '" + type + "', " + year + ", '" + date + ", '" + time + ", '" + memo +"');");
        db.close();
        //key값을 리턴해서 객체가 가지고 있는 것도 좋을듯
    }
}