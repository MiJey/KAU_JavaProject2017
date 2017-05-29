package mijey.kau_javaproject2017;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by yeaji on 2017-05-28.
 */

public class DBAdapter extends CursorAdapter {
    public DBAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView date = (TextView)view.findViewById(R.id.listDate);
        TextView memo = (TextView)view.findViewById(R.id.listMemo);

        //DB에 있는 값을 이용해서 자연어로 바꾸기
        date.setText(cursor.getString(cursor.getColumnIndex("_id")));
        memo.setText(cursor.getString(cursor.getColumnIndex("memo")));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.todolist_layout, parent, false);
        return v;
    }
}