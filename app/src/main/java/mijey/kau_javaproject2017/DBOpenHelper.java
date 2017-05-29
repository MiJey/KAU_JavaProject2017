package mijey.kau_javaproject2017;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper{
    public static SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DBHelper mDBHelper;
    private Context mCtx;

    public class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //id    type    year    date    time    memo
            //123   0       2017    0528    1925    작성중
            db.execSQL("CREATE TABLE TODOLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER, year INTEGER, date INTEGER, time INTEGER, memo TEXT);");
            db.execSQL("INSERT INTO TODOLIST VALUES(null, 0, 2017, 0529, 1023, '자료구조 노잼');");
            db.execSQL("INSERT INTO TODOLIST VALUES(null, 0, 2017, 1234, 1023, '자바 꿀잼');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS TODOLIST");
            onCreate(db);
        }
    }

    /*
    public void insert(int type, int year, int date, int time, String memo){
        db = getWritableDatabase();
        db.execSQL("INSERT INTO TODOLIST VALUES(null, " + type + ", " + year + ", " + date + ", " + time + ", '" + memo +"');");
        db.close();
        //key값을 리턴해서 객체가 가지고 있는 것도 좋을듯
    }
    */

    public DBOpenHelper(Context context){
        this.mCtx = context;
    }

    public DBOpenHelper open() throws SQLException {
        mDBHelper = new DBHelper(mCtx, "TODOLIST", null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDB.close();
    }
}
