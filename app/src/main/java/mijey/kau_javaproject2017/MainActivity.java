package mijey.kau_javaproject2017;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.toString()){
                            case "수정":
                                //수정할 수 있게 해야함
                                Toast.makeText(getApplicationContext(), "수정하기 기능 만들어야 함ㅠㅠ", Toast.LENGTH_SHORT).show();
                                break;
                            case "삭제":
                                cursor.moveToPosition(position);
                                String id = cursor.getString(cursor.getColumnIndex("_id"));
                                String m = cursor.getString(cursor.getColumnIndex("memo"));
                                db.execSQL("DELETE FROM TODOLIST WHERE _id = " + id);
                                cursor = db.rawQuery("SELECT * FROM TODOLIST", null);
                                dbAdapter.changeCursor(cursor);
                                Toast.makeText(getApplicationContext(), m + "이(가) 삭제되었습니다" + id, Toast.LENGTH_SHORT).show();
                                break;
                            default: break;
                        }
                        return true;
                    }
                });

                popup.show();
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