package com.example.tmusic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TMusicDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MUSIC = "music";
    private static final String TABLE_AUTHOR = "author";
    private static final String TABLE_USERS = "users";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    Cursor listaAutores(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery("SELECT * FROM author", null);
        }
        return cursor;
    }

    public boolean doSignUp(String username, String email, String passwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("username", username);
        cv.put("email", email);
        cv.put("passwd", passwd);
        long result = db.insert("user",null,cv);
        if (result == -1) {

        }
        return false;
    }
}