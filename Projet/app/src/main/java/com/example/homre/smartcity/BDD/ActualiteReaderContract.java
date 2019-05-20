package com.example.homre.smartcity.BDD;

import android.provider.BaseColumns;

public final class ActualiteReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ActualiteReaderContract() {}

    /* Inner class that defines the table contents */
    public static class ActualiteEntry implements BaseColumns {
        public static final String TABLE_NAME = "actualite";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_VILLE = "ville";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_IMG = "image";
    }
}
