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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {

    NetworkingService networkingService;

    EditText searchBar;
    RecyclerView pokemonListRecyclerView;

    ArrayList<PokemonData> currentListPokemonData;//back this up in MyApp in onDestroy // don't know if I need this anymore
    ArrayList<Pokemon> currentListPokemon;//back this up in MyApp in onDestroy

    ArrayList<PokemonSearchData> allPokemon;
    ArrayList<PokemonSearchData> currentSearchData;//back this up in MyApp in onDestroy

    JsonService jsonService;

    PokemonRecyclerAdapter pokemonRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkingService = ((MyApp)getApplication()).networkingService;
        allPokemon = ((MyApp)getApplication()).allPokemon;
        currentSearchData = ((MyApp)getApplication()).currentSearchData;
        currentListPokemonData = ((MyApp)getApplication()).currentListPokemonData;// don't know if I need this anymore
        currentListPokemon = ((MyApp)getApplication()).currentListPokemon;
        jsonService = ((MyApp)getApplication()).jsonService;


        searchBar = (EditText) findViewById(R.id.mainSearchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                //todo finish

                //only going to search after 3 letters are entered to save load time
                if(s.length()>2){
                    //currentSearchData = searchPokemonByName(String.valueOf(s));
                    //temp to test if json parse works
                    currentSearchData = searchPokemonByName("bulbasaur");
                    //todo check if empty
                    getPokemonDataFromSearchData(currentSearchData);//does this work?
                    //currentListPokemonData = getPokemonDataFromSearchData(currentSearchData);





                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        pokemonListRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
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

    @Override
    public void dataListener(String jsonData) {

        jsonService = ((MyApp)getApplication()).jsonService;

        currentListPokemon.add(jsonService.getPokemon(jsonData));
        //currentListPokemonData.add(jsonService.getPokemonData(jsonData));//old
        Log.d("currentListPokemon", String.valueOf(currentListPokemon));

        // Do I display / add to recycler here?
        pokemonRecyclerAdapter = new PokemonRecyclerAdapter(currentListPokemon, this);
        pokemonListRecyclerView.setAdapter(pokemonRecyclerAdapter);
        pokemonListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //Loops through all pokemon, returns ArrayList of PokemonSearchData aka name/url of each pokemon that contains the search string
    public ArrayList<PokemonSearchData> searchPokemonByName(String searchString){
        allPokemon = ((MyApp)getApplication()).allPokemon;
        ArrayList<PokemonSearchData> allMatchingPokemon = new ArrayList<>(0);
        PokemonSearchData matchingPokemon;

        for (int i = 0; i<allPokemon.size();i++){
            String name = allPokemon.get(i).name;
            if(name.contains(searchString)){
                matchingPokemon = new PokemonSearchData(name, allPokemon.get(i).url);
                allMatchingPokemon.add(matchingPokemon);
            }
        }

        return allMatchingPokemon;
    }

    //loops through each PokemonSearchData and gets its url, uses the url to get Pokemon from NetworkService and adds it to currentListPokemon
    // then displays it through recycler view -  I get this data in dataListener(String jsonData)
    public void getPokemonDataFromSearchData(ArrayList<PokemonSearchData> searchDataArrayList){
        networkingService.networkingListener = this;

        for(int i =0; i<searchDataArrayList.size(); i++){
            networkingService.getPokemonByURL(searchDataArrayList.get(i).url);
        }
    }
}