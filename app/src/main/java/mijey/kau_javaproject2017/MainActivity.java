package mijey.kau_javaproject2017;

import android.content.ContentValues;
import android.content.Intent;
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
    protected ListView todoList;
    protected DBHelper dbHelper;
    protected DBAdapter dbAdapter;
    protected SQLiteDatabase db;
    protected Cursor cursor;

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
                popup.getMenuInflater().inflate(R.menu.menu_option, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String id = cursor.getString(cursor.getColumnIndex("_id"));

                        switch (item.toString()){
                            case "수정":
                                ContentValues cv = new ContentValues();
                                cv.put("type",0); //These Fields should be your String values of actual column names
                                cv.put("date","2017-07-11 10:23");
                                cv.put("memo","업데이트 테스트");
                                db.update("TODOLIST", cv, "_id="+id, null);
                                cursor = db.rawQuery("SELECT * FROM TODOLIST ORDER BY date ASC", null);
                                dbAdapter.changeCursor(cursor);
                                //Intent intent = new Intent(getApplicationContext(), ModifiedActivity.class);
                                //intent.putExtra("id", id);
                                //startActivity(intent);
                                break;
                            case "삭제":
                                //삭제확인 다이얼로그 띄우는 것도 좋을 듯
                                cursor.moveToPosition(position);
                                String m = cursor.getString(cursor.getColumnIndex("memo"));
                                db.execSQL("DELETE FROM TODOLIST WHERE _id = " + id);
                                cursor = db.rawQuery("SELECT * FROM TODOLIST ORDER BY date ASC", null);
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
        cursor = db.rawQuery("SELECT * FROM TODOLIST ORDER BY date ASC", null);
        dbAdapter.changeCursor(cursor);

        tb.setText("");
    }


}