package com.example.homre.smartcity.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActualiteReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ActualiteReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ActualiteReaderContract.ActualiteEntry.TABLE_NAME + " (" +
                    ActualiteReaderContract.ActualiteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_VILLE + " TEXT," +
                    ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_IMG + " BLOB," +
                    ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_DATE + " TEXT," +
                    ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ActualiteReaderContract.ActualiteEntry.TABLE_NAME;

    public ActualiteReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static long insert(Context context, String title, String description, String ville, String date, byte[] img){
        // Gets the data repository in write mode
        ActualiteReaderDbHelper dbHelper = new ActualiteReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_TITLE, title);
        values.put(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_VILLE, ville);
        values.put(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_DATE, date);
        values.put(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_IMG, img);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ActualiteReaderContract.ActualiteEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public static ArrayList<Actualite> getAll(Context context){
        ActualiteReaderDbHelper dbHelper = new ActualiteReaderDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ActualiteReaderContract.ActualiteEntry._ID,
                ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_TITLE,
                ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_DESCRIPTION,
                ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_VILLE,
                ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_DATE,
                ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_IMG
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                ActualiteReaderContract.ActualiteEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList<Actualite> items = new ArrayList<>();
        while(cursor.moveToNext()) {
            try{
                long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ActualiteReaderContract.ActualiteEntry._ID));
                String ville=cursor.getString(cursor.getColumnIndex(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_VILLE));
                String d=cursor.getString(cursor.getColumnIndex(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_DATE));
                String titre=cursor.getString(cursor.getColumnIndex(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_TITLE));
                String texte=cursor.getString(cursor.getColumnIndex(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_DESCRIPTION));
                byte[] image=cursor.getBlob(cursor.getColumnIndex(ActualiteReaderContract.ActualiteEntry.COLUMN_NAME_IMG));

                Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(d);
                items.add(new Actualite((int)itemId,ville,date,titre,texte,image,null));
            }catch (ParseException e){
                Log.e("json","error parsing data : "+e.toString());
            }
        }
        cursor.close();

        return items;
    }
}
