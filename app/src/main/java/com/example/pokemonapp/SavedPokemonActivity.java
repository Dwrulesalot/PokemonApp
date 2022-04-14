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

public class SavedPokemonActivity extends AppCompatActivity {

    RecyclerView savedRecyclerView;
    PokemonRecyclerAdapter pokemonRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_pokemon);

        savedRecyclerView = (RecyclerView) findViewById(R.id.savedRecyclerView);

        //todo change below to display based on what pokemon are in the database
        /*
        pokemonRecyclerAdapter = new PokemonRecyclerAdapter(exampleListPokemonData, this);
        pokemonListRecyclerView.setAdapter(pokemonRecyclerAdapter);
        pokemonListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        */
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
}
