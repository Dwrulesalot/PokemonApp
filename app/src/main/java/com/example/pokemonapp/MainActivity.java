package com.example.pokemonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText searchBar;
    RecyclerView pokemonListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = (EditText) findViewById(R.id.mainSearchBar);
        pokemonListRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);

    }

    //Menu Creation
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }
}