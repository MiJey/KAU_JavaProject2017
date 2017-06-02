package mijey.kau_javaproject2017;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        EditText etMemo = (EditText)findViewById(R.id.etMemo);
        final Button btnAdd = (Button)findViewById(R.id.btnAdd);

        readDB();

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getApplicationContext(), "갸아악", Toast.LENGTH_SHORT).show();

                /*
                LinearLayout layOpt = (LinearLayout)view.findViewById(R.id.layOption);
                if(layOpt.getVisibility() == View.GONE) layOpt.setVisibility(View.VISIBLE);
                else layOpt.setVisibility(View.GONE);

                Button btnDel = (Button)layOpt.findViewById(R.id.btnDelete);
                btnDel.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cursor.moveToPosition(position);
                        String str = cursor.getString(cursor.getColumnIndex("_id"));
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

                        db.execSQL("DELETE FROM TODOLIST WHERE _id = " + str);
                        cursor = db.rawQuery("SELECT * FROM TODOLIST", null);
                        dbAdapter.changeCursor(cursor);
                    }
                });
                */
            }
        });

        etMemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    btnAdd.setEnabled(false);
                } else {
                    btnAdd.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
    }

    void readDB(){
        dbHelper = new DBHelper(getApplicationContext(), "TODOLIST.db", null, 1);
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM TODOLIST ORDER BY date ASC", null);
        dbAdapter = new DBAdapter(this, cursor);
        todoList.setAdapter(dbAdapter);
    }

    public void addTask(View view) {
        TextView tb = (TextView)findViewById(R.id.etMemo);
        NLP msg = new NLP(tb.getText().toString());

        db.execSQL("INSERT INTO TODOLIST VALUES(null, " + msg.getType() + ", '" + msg.getDate() + "', '" + msg.getMemo() +"');");
        cursor = db.rawQuery("SELECT * FROM TODOLIST", null);
        dbAdapter.changeCursor(cursor);

        tb.setText("");
    }


}