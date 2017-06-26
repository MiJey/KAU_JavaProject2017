package mijey.kau_javaproject2017;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModifiedActivity extends AppCompatActivity  {
    private String id, type, memo;
    private Calendar date;
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private TextView tvPickDate, tvPickTime;
    private EditText modiMemo;
    private Switch switchMemo;
    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switchMemo = (Switch)findViewById(R.id.switchMemo);
        tvPickDate = (TextView)findViewById(R.id.tvPickDate);
        tvPickTime = (TextView)findViewById(R.id.tvPickTime);
        modiMemo = (EditText)findViewById(R.id.etModiMemo);

        try {
            id = getIntent().getExtras().getString("id");
            type = getIntent().getExtras().getString("type");
            memo = getIntent().getExtras().getString("memo");
            date = Calendar.getInstance();
            date.setTime(SDF.parse(getIntent().getExtras().getString("date")));

            if(type.equals("1")){
                switchMemo.setChecked(true);
                tvPickDate.setVisibility(View.GONE);
                tvPickTime.setVisibility(View.GONE);
            }
            setTvPickDate();
            setTvPickTime();
            modiMemo.setText(memo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switchMemo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    tvPickDate.setVisibility(View.GONE);
                    tvPickTime.setVisibility(View.GONE);
                } else {
                    tvPickDate.setVisibility(View.VISIBLE);
                    tvPickTime.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setTvPickDate(){
        tvPickDate.setText(date.get(Calendar.YEAR) + "년 " + (date.get(Calendar.MONTH) + 1) + "월 " + date.get(Calendar.DATE) + "일");
    }

    private void setTvPickTime(){
        String ap = "";
        if((int)date.get(Calendar.AM_PM) == Calendar.AM) ap = "오전 ";
        else ap = "오후 ";
        tvPickTime.setText(ap + date.get(Calendar.HOUR) + "시 " + date.get(Calendar.MINUTE) + "분");
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
                return true;
            }

            Intent intent = getIntent();
            intent.putExtra("id", id);

            if(switchMemo.isChecked()){
                intent.putExtra("type", "1");
                date = Calendar.getInstance();
                intent.putExtra("date", SDF.format(date.getTime()));
            }else{
                intent.putExtra("type", "0");
                intent.putExtra("date", SDF.format(date.getTime()));
            }

            intent.putExtra("memo", modiMemo.getText().toString());
            setResult(RESULT_OK,intent);

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDatePicker(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
        datePickerDialog.getDatePicker().setMinDate(now.getTimeInMillis() - 1000);
        datePickerDialog.show();
    }

    public void showTimePicker(View view) {
        TimePickerDialog dialog = new TimePickerDialog(this, timeSetListener, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), false);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int y, int m, int d) {
            date.set(y, m, d);
            setTvPickDate();
        }
    };
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int h, int m) {
            date.set(Calendar.HOUR_OF_DAY, h);
            date.set(Calendar.MINUTE, m);
            setTvPickTime();;
        }
    };
}
