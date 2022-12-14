package com.example.tmusic;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AuthorsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private RecyclerView listAuthors;
    private CustomAdapterAuthor listAuthorsAdapter;
    private LinearLayoutManager listAuthorsLayoutManager;
    private DataBaseHelper conn = null;
    private ArrayList<Integer> arrayListId;
    private ArrayList<String> arrayListAuthorName;
    private BottomNavigationView bottomNavigationView;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        listAuthors = findViewById(R.id.listAuthors);

        Bundle extra = getIntent().getExtras();
        email = extra.getString("email");

        arrayListId = new ArrayList<>();
        arrayListAuthorName = new ArrayList<>();

        //Crear conexion con la base de datos
        conn = new DataBaseHelper(getApplicationContext());

        listAuthors.setHasFixedSize(true);
        listAuthorsLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        listAuthors.setLayoutManager(listAuthorsLayoutManager);

        //Insertar los datos de la base de datos en los arrayList Id y Authors
        storeDataInArrays();

        //Mete los datos de las arrayList en el RecyclerView
        listAuthorsAdapter = new CustomAdapterAuthor(arrayListId, arrayListAuthorName, this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSongs(arrayListId.get(listAuthors.indexOfChild(view)));
            }
        });
        listAuthors.setAdapter(listAuthorsAdapter);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.toString().equalsIgnoreCase("Profile")){
                onNavigationItemSelected(bottomNavigationView.getMenu().getItem(0));
            }else{
                onNavigationItemSelected(bottomNavigationView.getMenu().getItem(1));
            }
            return true;
        });
    }

    public void storeDataInArrays(){
        Cursor cursor = conn.listaAutores();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                arrayListId.add(cursor.getInt(0));
                arrayListAuthorName.add(cursor.getString(1));
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemProfile:
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                return true;
            case R.id.menuItemLogOut:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Log out");
                builder.setMessage("Are you sure that you want to Log out?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
        }
        return false;
    }

    public void openSongs(Integer author) {
        Intent intent = new Intent(getApplicationContext(), SongsActivity.class);
        intent.putExtra("author", author);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}
