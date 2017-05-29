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
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.todolist_layout, parent, false);
        return v;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView date = (TextView)view.findViewById(R.id.listDate);
        final TextView memo = (TextView)view.findViewById(R.id.listMemo);

        date.setText(cursor.getString(cursor.getColumnIndex("date")));
        memo.setText(cursor.getString(cursor.getColumnIndex("memo")));
    }
}
