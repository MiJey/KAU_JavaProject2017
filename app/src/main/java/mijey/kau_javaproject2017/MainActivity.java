package mijey.kau_javaproject2017;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //public final static String EXTRA_MESSAGE = "mijey.kau_javaproject2017.MESSAGE";

    private ListView todoList;
    private DBHelper dbHelper;
    private DBAdapter dbAdapter;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = (ListView)findViewById(R.id.todolist);
        readDB();

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                String str = cursor.getString(cursor.getColumnIndex("_id"));
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                //dbHelper.delete(cursor.getInt(cursor.getColumnIndex("_id")));
            }
        });
    }

    void readDB(){
        dbHelper = new DBHelper(getApplicationContext(), "TODOLIST.db", null, 1);
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM TODOLIST", null);
        dbAdapter = new DBAdapter(this, cursor);
        todoList.setAdapter(dbAdapter);
    }

    public void addTask(View view) {
        TextView tb = (TextView)findViewById(R.id.etMemo);
        NLP msg = new NLP(tb.getText().toString());

        db.execSQL("INSERT INTO TODOLIST VALUES(null," + msg.getType() + ", " + msg.getYear() + ", " + msg.getDate() + ", " + msg.getTime() + ", '" + msg.getMemo() + "');");
        cursor = db.rawQuery("SELECT * FROM TODOLIST", null);
        dbAdapter.changeCursor(cursor);

        tb.setText("");
    }
}