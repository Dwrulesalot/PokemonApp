package com.example.pokemonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedPokemonActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener{

    RecyclerView savedRecyclerView;
    PokemonRecyclerAdapter pokemonRecyclerAdapter;

    DatabaseManager databaseManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_pokemon);

        savedRecyclerView = (RecyclerView) findViewById(R.id.savedRecyclerView);

        databaseManager = ((MyApp)getApplication()).dbManager;
        databaseManager.getDb(this);
        databaseManager.listener = this;
        databaseManager.getAllPokemon();

    }

    //Menu Creation
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.saved_menu, menu);
        return true;
    }

    //Menu Functionality
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem){
        super.onOptionsItemSelected(menuItem);

        switch (menuItem.getItemId()){
            case R.id.menuSearch:
                searchActivityNavigation();
                break;
        }
        return true;
    }

    void searchActivityNavigation() {
        Intent savedActivity = new Intent(SavedPokemonActivity.this, MainActivity.class);//
        startActivity(savedActivity);
    }

    @Override
    public void onDataListReady(ArrayList<PokemonData> list) {
        pokemonRecyclerAdapter = new PokemonRecyclerAdapter(list, this);
        savedRecyclerView.setAdapter(pokemonRecyclerAdapter);
        savedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onAddComplete() {
        databaseManager.getAllPokemon();
    }

    @Override
    public void onDeleteComplete() {
        databaseManager.getAllPokemon();
    }
}
