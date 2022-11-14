package com.example.tmusic;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSignIn;
    private Button goSignUp;
    private EditText textUser;
    private EditText textPassword;
    private ImageView imgUser;
    private ImageView imgPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(this::onClick);
        goSignUp = (Button) findViewById(R.id.goSignUp);
        goSignUp.setOnClickListener(this::onClick);
        textUser = (EditText) findViewById(R.id.editTextTextPersonName);
        textPassword = (EditText) findViewById(R.id.editTextTextPassword);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        imgPassword = (ImageView) findViewById(R.id.imgPassword);

    }

    @Override
    public void onClick(View v) {
        Button buttonClicked = (Button) findViewById(v.getId());
        if(buttonClicked.equals(findViewById(R.id.buttonSignIn))){
            DataBaseHelper dbh = new DataBaseHelper(getApplicationContext());
            Cursor cursor = dbh.checkusernamepassword(textUser.getText().toString(), textPassword.getText().toString());
            if(cursor.getCount() == 0) {
                Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(SignInActivity.this, AuthorsActivity.class);
                //Cursor cursor2 = dbh.getEmail(textUser.getText().toString(), textPassword.getText().toString());
                String email = dbh.getEmail(textUser.getText().toString(), textPassword.getText().toString());
                intent.putExtra("email", email);
                startActivity(intent);
            }
        } else if(buttonClicked.equals(findViewById(R.id.goSignUp))){
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }
}