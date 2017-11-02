package io.sabri.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sabri on 11/1/17.
 */

public class TodoAdapter extends ArrayAdapter<TodoItem> {

    private Context context;
    private int resource;
    private ArrayList<TodoItem> objects;

    public TodoAdapter(Context context, int resource, ArrayList<TodoItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, null);
        }

        TextView id = convertView.findViewById(R.id.id);
        TextView title = convertView.findViewById(R.id.title);
        CheckBox checked = convertView.findViewById(R.id.checked);
        ImageButton deleteButton = convertView.findViewById(R.id.delete);

        final TodoItem todo = objects.get(position);

        id.setText(String.valueOf(todo.getId()));
        title.setText(todo.getTitle());

        if (todo.getChecked() == 1)
            checked.setChecked(true);
        else
            checked.setChecked(false);

        // set checked listener to update the database when the user change the checked status
        checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // initialize the TodoHelper class
                TodoHelper helper = new TodoHelper(context);

                // get the writable database because we'll be doing a writing operation
                SQLiteDatabase writableDB = helper.getWritableDatabase();

                // create a ContentValues object, which as a key value pairs
                ContentValues values = new ContentValues();

                // the key is the field name, the value is the value you want to add to that field in the newly created record
                values.put(TodoContract.TodoEntry.CHECKED, isChecked);

                // create the args String array to fill in the where state
                String[] args = {String.valueOf(todo.getId())};

                // update table with these new values and where id = the id of the current todo item that's being changed
                writableDB.update("todo", values, TodoContract.TodoEntry.ID + " = ?", args);

                // this is equivalent to this in RAW SQL
                // UPDATE todo SET checked = true WHERE id = todo.getId();


                // update the Adapter dataset to hold the new data
                if (isChecked)
                    objects.get(position).setChecked(1);
                else
                    objects.get(position).setChecked(0);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // initialize the TodoHelper class
                TodoHelper helper = new TodoHelper(context);

                // get the writable database because we'll be doing a writing operation
                SQLiteDatabase writableDB = helper.getWritableDatabase();

                // create the args String array to fill in the where state
                String[] args = {String.valueOf(todo.getId())};

                // delete item from table where id = the id of the current todo item that's being changed
                writableDB.delete("todo", TodoContract.TodoEntry.ID + " = ?", args);

                // this is equivalent to this in RAW SQL
                // DELETE FROM todo WHERE ID = todo.getId();

                // update the Adapter dataset to remove the deleted todo item
                objects.remove(position);
                notifyDataSetChanged();

            }
        });

        return convertView;

    }
}
