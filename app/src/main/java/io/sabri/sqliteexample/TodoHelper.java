package io.sabri.sqliteexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sabri on 11/1/17.
 */

/**
 * a helper class which extends SQLiteOpenHelper to provide an easier way to interact with the database
 */
public class TodoHelper extends SQLiteOpenHelper {

    // public constructor that takes in context
    public TodoHelper(Context context) {
        // pass context, along with the database name, null and 1 to the super class
        super(context, "todo.db", null, 1);
    }

    /**
     * this method is activated the first time the database is created, to create the necessary tables
     * @param db is the SQLiteDatabase that you'll create the database in.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create a table called todo with id as the the primary key, title as text, checked is an integer
        db.execSQL("CREATE TABLE todo(" + TodoContract.TodoEntry.ID + " INTEGER PRIMARY KEY, "+ TodoContract.TodoEntry.TITLE + " TEXT, "+ TodoContract.TodoEntry.CHECKED + "  INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
