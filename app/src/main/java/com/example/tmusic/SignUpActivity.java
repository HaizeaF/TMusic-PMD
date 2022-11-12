package com.example.tmusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    public static final int ACTIVITY_SIGNUP = 1;

    private EditText textUsername = null;
    private EditText textEmail = null;
    private EditText password = null;
    private EditText confirmPassword = null;
    private Button buttonSignIn = null;
    private Button buttonSignUp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textUsername = (EditText) findViewById(R.id.textUser);
        textEmail = (EditText) findViewById(R.id.textEmail);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dbHelper = new DataBaseHelper(SignUpActivity.this);
                if (dbHelper.doSignUp(textUsername.getText().toString(),textEmail.getText().toString(),password.getText().toString())) {
                    Intent intent = new Intent (SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}
