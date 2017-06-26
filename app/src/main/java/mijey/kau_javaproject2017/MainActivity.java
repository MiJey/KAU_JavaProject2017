package mijey.kau_javaproject2017;

import android.app.Activity;
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

        todoList = (ListView) findViewById(R.id.todolist);
        EditText etMemo = (EditText) findViewById(R.id.etMemo);
        final Button btnAdd = (Button) findViewById(R.id.btnAdd);

        readDB();

        //메모를 선택하면 수정/삭제 팝업메뉴 띄우기
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_option, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String id = cursor.getString(cursor.getColumnIndex("_id"));
                        String type = cursor.getString(cursor.getColumnIndex("type"));
                        String date = cursor.getString(cursor.getColumnIndex("date"));
                        String memo = cursor.getString(cursor.getColumnIndex("memo"));

                        switch (item.toString()) {
                            case "수정":
                                Intent intent = new Intent(getApplicationContext(), ModifiedActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("type", type);
                                intent.putExtra("date", date);
                                intent.putExtra("memo", memo);
                                startActivityForResult(intent, 0);
                                break;
                            case "삭제":
                                cursor.moveToPosition(position);
                                String m = cursor.getString(cursor.getColumnIndex("memo"));
                                db.execSQL("DELETE FROM TODOLIST WHERE _id = " + id);
                                listRefresh();
                                Toast.makeText(getApplicationContext(), m + "이(가) 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        //글자 입력했을 때만 추가버튼 활성화
        etMemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) btnAdd.setEnabled(false);
                else btnAdd.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable arg0) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
    }

    void readDB() {
        dbHelper = new DBHelper(getApplicationContext(), "TODOLIST.db", null, 1);
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM TODOLIST ORDER BY type, date ASC", null);
        dbAdapter = new DBAdapter(this, cursor);
        todoList.setAdapter(dbAdapter);
    }

    public void addTask(View view) {
        TextView tb = (TextView) findViewById(R.id.etMemo);
        NLP msg = new NLP(tb.getText().toString());

        db.execSQL("INSERT INTO TODOLIST VALUES(null, " + msg.getType() + ", '" + msg.getDate() + "', '" + msg.getMemo() + "');");
        listRefresh();

        tb.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            ContentValues cv = new ContentValues();
            cv.put("type", data.getStringExtra("type"));
            cv.put("date", data.getStringExtra("date"));
            cv.put("memo", data.getStringExtra("memo"));

            String id = data.getStringExtra("id");

            db.update("TODOLIST", cv, "_id=" + id, null);
            listRefresh();

            Toast.makeText(this, "수정되었습니다", Toast.LENGTH_SHORT).show();
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "취소되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private void listRefresh(){
        cursor = db.rawQuery("SELECT * FROM TODOLIST ORDER BY type, date ASC", null);
        dbAdapter.changeCursor(cursor);
    }
}