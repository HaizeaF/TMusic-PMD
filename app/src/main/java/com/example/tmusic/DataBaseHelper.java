package com.example.tmusic;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tmusicdb.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MUSIC = "music";
    private static final String TABLE_AUTHOR = "author";
    private static final String TABLE_USERS = "users";

    private Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS author (author_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS music(" +
                "song_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "author_id INTEGER," +
                "name VARCHAR," +
                "FOREIGN KEY (author_id) REFERENCES AUTHOR(author_id) ON DELETE CASCADE" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, email VARCHAR, password VARCHAR)");
        db.execSQL("INSERT INTO author (author_id, name) VALUES (null, 'Yeat'), (null, 'Eros'), (null, 'CharlieUSG'), (null, 'Dio'), (null, 'TwentyOnePilots'), (null, 'KanyeWest')");
        db.execSQL("INSERT INTO music (song_id, author_id, name) VALUES (null, 1, 'Dub')," +
                "(null, 1, 'Krank')," +
                "(null, 2, 'Sakanigadik')," +
                "(null, 3, 'LoQueNuncaTeDije')," +
                "(null, 3, 'MeDaIgual')," +
                "(null, 4, 'EndOfTheWorld')," +
                "(null, 4, 'RainbowInTheDark')," +
                "(null, 4, 'MasterOfTheMoon')," +
                "(null, 5, 'Ride')," +
                "(null, 5, 'ShyAway')," +
                "(null, 6, 'DarkFantasy')");
        db.execSQL("INSERT INTO users (user_id, username, email, password) VALUES (null, 'userTest', 'userTest@gmail.com', 'abcd*1234')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    Cursor listaAutores(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery("SELECT * FROM author", null);
        }
        return cursor;
    }

    Cursor listaCanciones(Integer author){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery("SELECT m.song_id, a.name, m.name FROM MUSIC m, AUTHOR a WHERE m.author_id = a.author_id AND m.author_id = "+author, null);
        }
        return cursor;
    }

    public Cursor checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = null;
        if(MyDB != null){
            cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        }
        return cursor;
    }

    public String getEmail(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = null;
        String email = null;
        if (MyDB != null){
            cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
            if(cursor.moveToNext())
                email = cursor.getString(2);
        }
        return email;
    }
    public Character doSignUp(String username, String email, String passwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            String selectionArgs[] = new String[]{email,username};
            cursor = db.rawQuery("SELECT * FROM USERS WHERE email LIKE ? OR username LIKE ?", selectionArgs);
            if (cursor.moveToNext()) {
                return 'A';
            } else {
                ContentValues cv = new ContentValues();
                Integer user_id = null;

                cv.put("user_id",user_id);
                cv.put("username", username);
                cv.put("email", email);
                cv.put("password", passwd);
                try {
                    long result = db.insert(TABLE_USERS,null,cv);
                    if (result == -1) {
                        return 'E';
                    } else {
                        return 'O';
                    }
                }catch (SQLException ex) {
                    System.out.println(ex.getStackTrace());
                }
            }
        }
        return 'D';
    }
    public Character doUpdateData(String username, String email, String passwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            String selectionArgs[] = new String[]{username};
            cursor = db.rawQuery("SELECT * FROM USERS WHERE username LIKE ?", selectionArgs);
            if (cursor.moveToNext()) {
                return 'A';
            } else {
                ContentValues cv = new ContentValues();
                cv.put("username", username);
                cv.put("email", email);
                cv.put("password", passwd);
                long result = db.update(TABLE_USERS, cv,"email=?",new String[] {email});
                if (result == -1) {
                    return 'E';
                } else {
                    return 'O';
                }
            }
        }
        return 'D';
    }
}
