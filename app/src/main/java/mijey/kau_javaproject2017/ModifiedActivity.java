package mijey.kau_javaproject2017;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class ModifiedActivity extends AppCompatActivity {
    private String id;
    private EditText modiMemo;
    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getExtras().getString("id");
        modiMemo = (EditText)findViewById(R.id.etModiMemo);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);  //앱바에 저장버튼
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home){
            Toast.makeText(this, "수정 취소", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        if (itemId == R.id.btnSave) {
            //저장저장...
            //EditText editText = (EditText) findViewById(R.id.edit_message);
            //String message = editText.getText().toString();
            //intent.putExtra(EXTRA_MESSAGE, message);

            //db.execSQL("UPDATE TODOLIST SET type 0, date TEXT, memo TEXT WHERE _id = "+id+";");



            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
