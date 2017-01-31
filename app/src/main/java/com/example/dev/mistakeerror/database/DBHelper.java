package com.example.dev.mistakeerror.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by dev on 20.01.17..
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    private static final int version = 10;
    private static final String DB_NAME = "notesDB"; //database name
    private static final String TABLE_NAME = "errors"; //table name
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE + " TEXT NOT NULL, " + KEY_CONTENT + " TEXT NOT NULL, " + KEY_DATE + " TEXT);";

    public DBHelper(Context context){
        super(context, DB_NAME, null, version);
        this.context = context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNote(String title, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("date", new Date().toString());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public Cursor getNotes(SQLiteDatabase db){
        Cursor c = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_TITLE, KEY_DATE},null,null,null,null, "id DESC");
        c.moveToFirst();
        return c;
    }

    public Cursor getNote(SQLiteDatabase db, int id) {
        Cursor c = db.query(TABLE_NAME, new String[]{KEY_TITLE, KEY_CONTENT, KEY_DATE}, KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        c.moveToFirst();
        return c;
    }

    public void removeNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateNote(String title, String content, Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        cv.put("date", new Date().toString());

        db.update(TABLE_NAME, cv, KEY_ID + "="  + id, null);
        db.close();
    }
}
