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

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "mijey.kau_javaproject2017.MESSAGE";

    private ListView todoListView;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private String sql;
    private Cursor cursor;

    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext(), "TODOLIST.db", null, 1);
        selectDB();
        /*
        // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성
        listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

        // Xml에서 추가한 ListView 연결
        todoListView = (ListView) findViewById(R.id.todolist);
        todoListView.setAdapter(listAdapter);
        todoListView.setOnItemClickListener(onClickListItem);
        */

    }

    private void selectDB() {
        db = dbHelper.getWritableDatabase();
        sql = "SELECT * FROM TODOLIST;";

        cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            startManagingCursor(cursor);
            DBAdapter dbAdapter = new DBAdapter(this, cursor);
            todoListView.setAdapter(dbAdapter);
        }
    }

        // 아이템 터치 이벤트
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
        }
    };

    public void addTask(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        listAdapter.add(message);
        editText.setText("");
    }
}
