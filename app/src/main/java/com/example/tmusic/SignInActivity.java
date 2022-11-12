package com.example.tmusic;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

        buttonSignIn = (Button) findViewById(R.id.buttonHome);
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
        if(buttonClicked.equals(findViewById(R.id.buttonHome))){

        } else if(buttonClicked.equals(findViewById(R.id.goSignUp))){

        }
    }
}