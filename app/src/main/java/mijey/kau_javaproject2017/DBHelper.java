package mijey.kau_javaproject2017;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

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
        db.execSQL("DROP TABLE IF EXISTS TODOLIST");
        onCreate(db);
    }
}