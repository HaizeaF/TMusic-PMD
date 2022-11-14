package com.example.tmusic;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

public class SignInActivity extends AppCompatActivity{
    private Button buttonSignIn;
    private Button goSignUp;
    private EditText textUser;
    private EditText textPassword;
    private ImageView imgUser;
    private ImageView imgPassword;
    private ImageButton instagram;
    private Intent intent;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        /*db = openOrCreateDatabase("tmusic", Context.MODE_PRIVATE, null);
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
        db.execSQL("INSERT INTO users (user_id, username, email, password) VALUES (null, 'userTest', 'userTest@gmail.com', 'abcd*1234')");*/

        db = new DataBaseHelper(getApplicationContext());

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    DataBaseHelper dbh = new DataBaseHelper(getApplicationContext());
                    Cursor cursor = dbh.checkusernamepassword(textUser.getText().toString(), textPassword.getText().toString());
                    if(cursor.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), R.string.userNotExist, Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(SignInActivity.this, AuthorsActivity.class);
                        String email = dbh.getEmail(textUser.getText().toString(), textPassword.getText().toString());
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
            }
        });
        goSignUp = (Button) findViewById(R.id.goSignUp);
        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        textUser = (EditText) findViewById(R.id.editTextTextPersonName);
        textPassword = (EditText) findViewById(R.id.editTextTextPassword);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        imgPassword = (ImageView) findViewById(R.id.imgPassword);
        instagram = findViewById(R.id.instagram);

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse((String) getText(R.string.linkInstagram)));
                startActivity(intent);
            }
        });

    }
}