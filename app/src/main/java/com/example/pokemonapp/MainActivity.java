package com.example.pokemonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseManager dbManager;//do I need this here? or only in details page & Saved page when I'm saving/deleting to the database / getting from the database

    EditText searchBar;
    RecyclerView pokemonListRecyclerView;

    //temp
    PokemonData exampleData1;
    PokemonData exampleData2;
    PokemonData exampleData3;
    ArrayList<PokemonData> exampleListPokemonData;//in future set a default list and have a list for the current search

    PokemonRecyclerAdapter pokemonRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = (EditText) findViewById(R.id.mainSearchBar);
        pokemonListRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);

        //temp int id, String smallIcon, String bigIcon, String name, int height, int weight, String[] types, String[] stats
        exampleData1 = new PokemonData(6,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
                "charizard", 17, 905, new String[]{"fire", "flying"}, new String[]{"hp", "attack", "defense"}
                );//WARNING this may be incorrect/ not how I get my data from the api - the string arrays are 100% wrong
        exampleData2 = new PokemonData(7,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png",
                "squirtle", 5, 90, new String[]{"temp", "temp"}, new String[]{"hp", "attack", "defense"}
        );
        exampleData3 = new PokemonData(8,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/8.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/8.png",
                "wartortle", 10, 225, new String[]{"temp", "temp"}, new String[]{"hp", "attack", "defense"}
        );
        exampleListPokemonData = new ArrayList<>();//probably set this in MyApp in future
        exampleListPokemonData.add(exampleData1);
        exampleListPokemonData.add(exampleData2);
        exampleListPokemonData.add(exampleData3);

        pokemonRecyclerAdapter = new PokemonRecyclerAdapter(exampleListPokemonData, this);
        pokemonListRecyclerView.setAdapter(pokemonRecyclerAdapter);
        pokemonListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    //Menu Creation
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    //Menu Functionality
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem){
        super.onOptionsItemSelected(menuItem);

        switch (menuItem.getItemId()){
            case R.id.menuViewSaved:
                savedActivityNavigation();
                break;
        }
        return true;
    }

    void savedActivityNavigation() {
        Intent savedActivity = new Intent(MainActivity.this, SavedPokemonActivity.class);
        startActivity(savedActivity);
    }
}