package io.sabri.sqliteexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // declare views
    EditText todoTitle;
    Button addButton;
    Button readButton;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
        todoTitle = findViewById(R.id.todo_title);
        addButton = findViewById(R.id.add_button);
        listView = findViewById(R.id.list_view);

        // set onClickListeners
        addButton.setOnClickListener(addListener);
        readButton.setOnClickListener(addListener);

    }

    View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // initialize the TodoHelper class
            TodoHelper helper = new TodoHelper(MainActivity.this);

            // get the writable database because we'll be doing a writing operation
            SQLiteDatabase writeableDB = helper.getWritableDatabase();

            // create a ContentValues object, which as a key value pairs
            ContentValues values = new ContentValues();

            // the key is the field name, the value is the value you want to add to that field in the newly created record
            values.put(TodoContract.TodoEntry.TITLE, todoTitle.getText().toString());
            values.put(TodoContract.TodoEntry.CHECKED, 0);

            // execute the insert method, table name, null, the values
            writeableDB.insert("todo", null, values);

            // this is equivalent to this in RAW SQL
            // INSERT INTO todo(title, checked) VALUES("do something", 0);

            // empty the EditText after the user finishes adding
            todoTitle.setText("");

        }
    };

    View.OnClickListener readListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            // initialize the TodoHelper class
            TodoHelper helper = new TodoHelper(MainActivity.this);

            // get the readable database because we'll be doing a reading operation
            SQLiteDatabase readableDB = helper.getReadableDatabase();

            // create a String array of the fields we want to retrieve
            String[] field = {TodoContract.TodoEntry.ID, TodoContract.TodoEntry.TITLE, TodoContract.TodoEntry.CHECKED};

            // execute the query, the first parameter is the field name
            Cursor cursor = readableDB.query("todo", field, null, null, null, null, null);
            // the cursor is a two dimensional data storage object

            // this is equivalent to this in RAW SQL
            // SELECT id, title, checked FROM todo;

            // create an empty array list to hold all the todo items that we'll get from the cursor
            ArrayList<TodoItem> todoArrayList = new ArrayList<>();

            // loop the cursor to move through the table's rows
            while (cursor.moveToNext()) {
                // get the fields
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int checked = cursor.getInt(2);

                //create a new object using the constructor
                TodoItem todo = new TodoItem(id, title, checked);
                todoArrayList.add(todo);
            }

            // close the cursor after we're finished with it
            cursor.close();

            // create the adapter
            TodoAdapter adapter = new TodoAdapter(MainActivity.this, R.layout.item_todo, todoArrayList);

            // setAdapter to the listView
            listView.setAdapter(adapter);

        }
    };

}
