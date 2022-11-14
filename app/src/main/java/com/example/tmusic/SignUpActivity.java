package com.example.tmusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        buttonSignIn = (Button) findViewById(R.id.buttonHome);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        buttonSignUp = (Button) findViewById(R.id.buttonUpdate);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean correct = true;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (textUsername.getText().toString().contains(" ") || textUsername.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.text_errorUser), Toast.LENGTH_SHORT);
                    toast.show();
                    correct = false;
                }
                if (!textEmail.getText().toString().matches(emailPattern) || textEmail.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.text_errorEmail), Toast.LENGTH_SHORT);
                    toast.show();
                    correct = false;
                }
                if (password.getText().toString().length() < 8) {
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.text_errorPasswd), Toast.LENGTH_SHORT);
                    toast.show();
                    correct = false;
                }
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.text_errorConfirmPasswd), Toast.LENGTH_SHORT);
                    toast.show();
                    correct = false;
                }
                if (correct) {
                    DataBaseHelper dbHelper = new DataBaseHelper(SignUpActivity.this);
                    if (dbHelper.doSignUp(textUsername.getText().toString(),textEmail.getText().toString(),password.getText().toString()).equals('O')) {
                        Intent intent = new Intent (SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                    } else if (dbHelper.doSignUp(textUsername.getText().toString(),textEmail.getText().toString(),password.getText().toString()).equals('A')){
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.text_alreadyExist), Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.text_errorDB), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

    }

}
