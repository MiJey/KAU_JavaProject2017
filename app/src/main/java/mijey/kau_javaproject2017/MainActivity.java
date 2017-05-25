package mijey.kau_javaproject2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "mijey.kau_javaproject2017.MESSAGE";

    private ListView todoListview;
    private ArrayAdapter<String> todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Android에서 제공하는 string 문자열 하나를 출력 가능한 layout으로 어댑터 생성
        todoAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

        // Xml에서 추가한 ListView 연결
        todoListview = (ListView) findViewById(R.id.todolist);
        todoListview.setAdapter(todoAdapter);
        todoListview.setOnItemClickListener(onClickListItem);

    }

    // 아이템 터치 이벤트
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
        }
    };

    public void addTask(View view) {
        todoAdapter.add("asdf");
    }
}
