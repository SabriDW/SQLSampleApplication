package io.sabri.sqliteexample;

import android.provider.BaseColumns;

/**
 * Created by Sabri on 11/1/17.
 */

public final class TodoContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TodoContract() {
    }

    /* Inner class that defines the table contents */
    // it implements BaseColumns to have access to the
    public static class TodoEntry implements BaseColumns {
        // use the built in _ID constant to make it consistent across your tables
        public static final String ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String CHECKED = "checked";
    }

    // we created inner classes because we'll be creating
    // classes for every table inside the same TodoEntry class

}
