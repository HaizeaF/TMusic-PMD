package com.example.tmusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyActivity extends AppCompatActivity {
    public static final int ACTIVITY_MODIFY = 1;

    private EditText textUsername = null;
    private EditText password = null;
    private EditText confirmPassword = null;
    private Button buttonHome = null;
    private Button buttonUpdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Bundle extra = getIntent().getExtras();
        String email = extra.getString("EMAIL");

        textUsername = (EditText) findViewById(R.id.textUser);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyActivity.this, AuthorsActivity.class);
                startActivity(intent);
            }
        });
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean correct = true;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (textUsername.getText().toString().contains(" ") || textUsername.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.text_errorUser), Toast.LENGTH_SHORT);
                    toast.show();
                    correct = false;
                }
                if (password.getText().toString().length() < 8) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.text_errorEmail), Toast.LENGTH_SHORT);
                    toast.show();
                    correct = false;
                }
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.text_errorConfirmPasswd), Toast.LENGTH_SHORT);
                    toast.show();
                    correct = false;
                }
                if (correct) {
                    DataBaseHelper dbHelper = new DataBaseHelper(ModifyActivity.this);
                    if (dbHelper.doUpdate(textUsername.getText().toString(), email, password.getText().toString())) {
                        Intent intent = new Intent(ModifyActivity.this, AuthorsActivity.class);
                        startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.text_errorDB), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
    }
}
