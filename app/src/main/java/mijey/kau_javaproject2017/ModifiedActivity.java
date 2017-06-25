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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModifiedActivity extends AppCompatActivity  {
    private String id, type, memo;
    private Calendar date;
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private EditText modiMemo;
    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            setComponents();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setComponents() throws ParseException{
        id = getIntent().getExtras().getString("id");
        type = getIntent().getExtras().getString("type");
        memo = getIntent().getExtras().getString("memo");

        date = Calendar.getInstance();
        date.setTime(SDF.parse(getIntent().getExtras().getString("date")));

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        //datePicker.setMinDate(System.currentTimeMillis() - 1000);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        modiMemo = (EditText)findViewById(R.id.etModiMemo);

        datePicker.updateDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
        timePicker.setCurrentHour(date.get(Calendar.HOUR));
        timePicker.setCurrentMinute(date.get(Calendar.MINUTE));
        modiMemo.setText(memo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);  //앱바에 저장버튼
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home){   //수정 취소
            finish();
            return true;
        }

        if (itemId == R.id.btnSave) {   //저장
            if(modiMemo.getText().toString().equals("")){
                Toast.makeText(this, "메모를 입력해주세요", Toast.LENGTH_SHORT).show();
              //  return false;
            }

            date.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            Intent intent = getIntent();
            intent.putExtra("id", id);
            intent.putExtra("type", "0");
            intent.putExtra("date", SDF.format(date.getTime()));
            intent.putExtra("memo", modiMemo.getText().toString());
            setResult(RESULT_OK,intent);

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
