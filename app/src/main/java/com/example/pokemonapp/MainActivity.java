package com.example.pokemonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {

    ArrayList<PokemonData> currentListPokemonData;
    ArrayList<PokemonSearchData> allPokemon;
    ArrayList<PokemonSearchData> currentSearchData;
    ArrayList<PokemonSearchData> defaultSearchData;

    int numOfDefaultPokemon = 25;
    boolean querySubmitted = false;

    JsonService jsonService = new JsonService();
    NetworkingService networkingService;
    SavedPokemonRecyclerAdapter savedPokemonRecyclerAdapter;
    RecyclerView pokemonListRecyclerView;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);
        querySubmitted = false;

        networkingService = ((MyApp)getApplication()).getNetworkingService();
        networkingService.networkingListener = this;
        allPokemon = ((MyApp)getApplication()).allPokemon;
        if (allPokemon.isEmpty()){
            networkingService.getAllPokemon(getApplicationContext());
        }

        currentSearchData = ((MyApp)getApplication()).currentSearchData;
        currentListPokemonData = ((MyApp)getApplication()).currentListPokemonData;
        jsonService = ((MyApp)getApplication()).jsonService;
        defaultSearchData = ((MyApp)getApplication()).defaultPokemon;

        pokemonListRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        savedPokemonRecyclerAdapter = new SavedPokemonRecyclerAdapter(currentListPokemonData, this);
        pokemonListRecyclerView.setAdapter(savedPokemonRecyclerAdapter);
        pokemonListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MyApp)getApplication()).currentSearchData = currentSearchData;
        ((MyApp)getApplication()).currentListPokemonData = currentListPokemonData;
    }

    //Menu Creation
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        String input = searchView.getQuery().toString();

        if (!input.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(input, false);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //if something has been entered
                if(s.length()>0 && querySubmitted == false){

                    allPokemon = ((MyApp)getApplication()).allPokemon;
                    //clears previous search data /recyclerview data
                    currentSearchData = new ArrayList<>(0);
                    currentListPokemonData = new ArrayList<>(0);

                    currentSearchData = searchPokemonByName(s);
                    Log.d("onQueryTextSubmit(String s): currentSearchData: ", currentSearchData.toString());

                    if(!currentSearchData.isEmpty()) {
                        getPokemonDataFromSearchData(currentSearchData);

                    }
                    else{
                        builder.setTitle(R.string.searchErrorTitle);
                        builder.setMessage(R.string.searchErrorMsg);
                        builder.setCancelable(true);
                        builder.setNegativeButton(R.string.ok,null);
                        builder.show();
                    }
                }
                else {
                    querySubmitted = false;
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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
            case R.id.menuDisplayDefaultSearch:
                displayDefaultSearch();
                break;
        }
        return true;
    }

    private void displayDefaultSearch() {
        defaultSearchData = ((MyApp)getApplication()).defaultPokemon;
        currentSearchData = defaultSearchData;
        currentListPokemonData = new ArrayList<>(0);
        getPokemonDataFromSearchData(defaultSearchData);
    }
    void savedActivityNavigation() {
        Intent savedActivity = new Intent(MainActivity.this, SavedPokemonActivity.class);
        startActivity(savedActivity);
    }

    @Override
    public void dataListener(String fileName) {
        PokemonData newPokemon = jsonService.getPokemonData(fileName, getApplicationContext());
        currentListPokemonData.add(newPokemon);
        Log.d("newPokemon", String.valueOf(newPokemon));

        savedPokemonRecyclerAdapter = new SavedPokemonRecyclerAdapter(currentListPokemonData, this);
        pokemonListRecyclerView.setAdapter(savedPokemonRecyclerAdapter);
        pokemonListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void allPokemonDataListener(String fileName) {
        allPokemon = jsonService.getFullPokemonList(fileName, getApplicationContext());
        ((MyApp)getApplication()).allPokemon = allPokemon;
        ((MyApp)getApplication()).defaultPokemon = new ArrayList<PokemonSearchData>(allPokemon.subList(0,numOfDefaultPokemon));
    }

    //Loops through all pokemon, returns ArrayList of PokemonSearchData aka name/url of each pokemon that contains the search string
    public ArrayList<PokemonSearchData> searchPokemonByName(String searchString){
        ArrayList<PokemonSearchData> allMatchingPokemon = new ArrayList<>(0);
        PokemonSearchData matchingPokemon;

        for (int i = 0; i<allPokemon.size();i++){
            String name = allPokemon.get(i).name;
            if(name.contains(searchString)){
                matchingPokemon = new PokemonSearchData(name, allPokemon.get(i).url);
                allMatchingPokemon.add(matchingPokemon);
            }
        }
        querySubmitted = true;
        Log.d("MainActivity searchPokemonByName: - allMatchingPokemon ", allMatchingPokemon.toString());
        return allMatchingPokemon;
    }

    //loops through each PokemonSearchData and gets its url, uses the url to get Pokemon from NetworkService and adds it to currentListPokemon
    // then displays it through recycler view -  I get this data in dataListener(String jsonData)
    public void getPokemonDataFromSearchData(ArrayList<PokemonSearchData> searchDataArrayList){
        for(int i =0; i<searchDataArrayList.size(); i++){
            networkingService.getPokemonByURL(searchDataArrayList.get(i).url, searchDataArrayList.get(i).name, getApplicationContext());
        }
    }
}