package com.example.student.quitmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseOperations extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 2;

    // UserDemo Query String
    public String CREATE_DIARY_ENTRY_TABLE = "CREATE TABLE " + TableData.TableInfo.DIARY_ENTRY_TABLE_NAME + "(" +
            TableData.TableInfo.DIARY_ID + " TEXT," + TableData.TableInfo.LATLONG +
            " TEXT," + TableData.TableInfo.DIARY_CONTENT +
            " TEXT," + TableData.TableInfo.TIME + " TEXT," + TableData.TableInfo.CONTENT_TYPE + " TEXT);";

    // Create Database
    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        // Create user_demo Table
        Log.d("Database Operations", "creating DIARY_ENTRY table");
        sdb.execSQL(CREATE_DIARY_ENTRY_TABLE);
        Log.d("Database Operations", "DIARY_ENTRY table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
        //sdb.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.DIARY_ENTRY_TABLE_NAME );
        //onCreate(sdb);
    }


    // pulling all diary entries
    public static Cursor pullDiaryEntries(DatabaseOperations dbop) {

        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.DIARY_ID, TableData.TableInfo.LATLONG,
                TableData.TableInfo.DIARY_CONTENT, TableData.TableInfo.TIME, TableData.TableInfo.CONTENT_TYPE};
        Cursor cr = sq.query(TableData.TableInfo.DIARY_ENTRY_TABLE_NAME, columns, null, null, null, null, null); //pull all rows
        return cr;
    }

    //add to user

    public static void saveDiaryEntry(DatabaseOperations dbop, diaryEntry entry){
        SQLiteDatabase sq = dbop.getWritableDatabase();
        String[] columns = {TableData.TableInfo.DIARY_ID, TableData.TableInfo.LATLONG,
                TableData.TableInfo.DIARY_CONTENT, TableData.TableInfo.TIME, TableData.TableInfo.CONTENT_TYPE};

        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo.DIARY_ID, entry.getId());
        cv.put(TableData.TableInfo.LATLONG, entry.getLatLongString() );
        cv.put(TableData.TableInfo.DIARY_CONTENT, entry.getDiaryEntry() );
        cv.put(TableData.TableInfo.TIME, entry.getmDate()+"" );
        cv.put(TableData.TableInfo.CONTENT_TYPE, entry.getContentType());


        sq.insert(TableData.TableInfo.DIARY_ENTRY_TABLE_NAME, null, cv);
        Log.d("Database Operations", "One row inserted into user_auth table");
    }
}
