package com.example.tmusic;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity{
    private Button buttonSignIn;
    private Button goSignUp;
    private EditText textUser;
    private EditText textPassword;
    private ImageView imgUser;
    private ImageView imgPassword;
    private ImageButton instagram;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

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