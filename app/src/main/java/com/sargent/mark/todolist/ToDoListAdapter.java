package com.sargent.mark.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.sargent.mark.todolist.data.Contract;
import com.sargent.mark.todolist.data.ToDoItem;

import java.util.ArrayList;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todolistadapter";
    private SQLiteDatabase db; // I added a field for the database to update

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        void onItemClick(int pos, String description, String duedate, long id);
    }

    // I added an extra parameter to instantiate the database so I have access to it in the adapter
    public ToDoListAdapter(Cursor cursor, SQLiteDatabase db, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
        this.db = db;
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    /* I added 4 extra fields for checkbox and spinner so I can get the reference to the
        checkbox and the value and the spinner and its value.
     */
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descr;
        TextView due;
        CheckBox checkBox;
        Spinner spinner;
        String duedate;
        String description;
        boolean checked;
        String category;
        long id;

        // I instantiate the checkBox and spinner using their ids.
        ItemHolder(View view) {
            super(view);
            descr = (TextView) view.findViewById(R.id.description);
            due = (TextView) view.findViewById(R.id.dueDate);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            spinner = (Spinner) view.findViewById(R.id.spinner);
            view.setOnClickListener(this);
        }

        /* Bind is where I did a lot of my work. I instantiated the 4 extra fields I created.
            First, I instantiated the values of checked and category by retrieving the value from
            the database. Then I set the OnClickListeners and Overrided the method so it will update
            the database when clicked. Next, I set the OnItemSelectedListeners for the spinner and
            updated the database just like before.
         */
        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);

            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));
            int dbChecked = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CHECKED));
            checked = (dbChecked == 1) ? true : false;
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY));
            //Log.d(TAG, "Spinner Selection: " + getSpinnerIndex(spinner, category) + " category: " + category + " id: " + id);
            spinner.setSelection(getSpinnerIndex(spinner, category));

            checkBox.setChecked(checked);
            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Boolean checked = ((CheckBox) v).isChecked();
                    MainActivity.updateChecked(db, checked, id);
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idRow) {
                    String item = parent.getItemAtPosition(position).toString();
                    //Log.d(TAG, "Item Selected: " + idpos + " " + item);
                    MainActivity.updateCategory(db, item, id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            descr.setText(description);
            due.setText(duedate);
            holder.itemView.setTag(id);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, description, duedate, id);
        }
    }

    // This method is to retrieve the item position of the string input so I can set the position
    private int getSpinnerIndex(Spinner s, String input) {
        int index = 0;
        for (int i = 0; i < s.getCount(); i++) {
            if (s.getItemAtPosition(i).equals(input)) {
                index = i;
            }
        }
        return index;
    }

}
