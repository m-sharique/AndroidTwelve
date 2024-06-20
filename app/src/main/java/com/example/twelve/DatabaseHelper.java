package com.example.twelve;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENTS = "students";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public boolean insertStudent(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return result != -1;
    }

    public Cursor searchStudent(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_NAME + "=? AND " + COLUMN_PASSWORD + "=?";
        return db.rawQuery(query, new String[]{name, password});
    }

    public boolean updateStudent(String name, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);
        int result = db.update(TABLE_STUDENTS, values, COLUMN_NAME + "=?", new String[]{name});
        db.close();
        return result > 0;
    }
}
