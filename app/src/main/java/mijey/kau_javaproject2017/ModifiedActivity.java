package mijey.kau_javaproject2017;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ModifiedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified);

        // ActionBar에 타이틀 변경
        getSupportActionBar().setTitle("");
        // ActionBar의 배경색 변경
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));

        // 홈 아이콘 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            Toast.makeText(this, "수정 취소", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class); //애니메이션이 마음에 안듬;;
            startActivity(intent);
            return true;
        }

        if (id == R.id.btnSave) {
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            //저장저장...
            //EditText editText = (EditText) findViewById(R.id.edit_message);
            //String message = editText.getText().toString();
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
