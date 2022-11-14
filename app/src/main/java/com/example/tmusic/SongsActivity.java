package com.example.tmusic;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SongsActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener{

    private RecyclerView listSongs;
    private DataBaseHelper conn = null;
    private LinearLayoutManager listSongsLayoutManager;
    private CustomAdapterSong listSongsAdapter;
    private ArrayList<Integer> songsId;
    private ArrayList<String> authorName;
    private ArrayList<String> songName;
    private Integer author;
    private BottomNavigationView bottomNavigationView;
    private String email;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        listSongs = findViewById(R.id.listSongs);
        Bundle extras = getIntent().getExtras();
        author = extras.getInt("author");
        email = extras.getString("email");

        songsId = new ArrayList<>();
        authorName = new ArrayList<>();
        songName = new ArrayList<>();

        //Crear conexion con la base de datos
        conn = new DataBaseHelper(getApplicationContext());

        listSongs.setHasFixedSize(true);
        listSongsLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        listSongs.setLayoutManager(listSongsLayoutManager);

        //Insertar los datos de la base de datos en los arrayList Id y Authors
        storeDataInArrays();

        listSongsAdapter = new CustomAdapterSong(this, songsId, authorName, songName, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSong(authorName.get(listSongs.indexOfChild(view)), songName.get(listSongs.indexOfChild(view)));
            }
        });
        listSongs.setAdapter(listSongsAdapter);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        buttonBack = findViewById(R.id.buttonBack);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.toString().equalsIgnoreCase("Profile")){
                onNavigationItemSelected(bottomNavigationView.getMenu().getItem(0));
            }else{
                onNavigationItemSelected(bottomNavigationView.getMenu().getItem(1));
            }
            return true;
        });

        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AuthorsActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });
    }

    private void openSong(String author, String song) {
        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
        intent.putExtra("author", author);
        intent.putExtra("song", song);
        startActivity(intent);
        finish();
    }

    private void storeDataInArrays() {
        Cursor cursor = conn.listaCanciones(author);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                songsId.add(cursor.getInt(0));
                authorName.add(cursor.getString(1));
                songName.add(cursor.getString(2));
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
}
