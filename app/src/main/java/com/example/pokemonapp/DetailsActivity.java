package com.example.pokemonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener, NetworkingService.NetworkingListener {
    ImageView sprite;
    Button addPokemonBtn;
    Button deletePokemonBtn;

    DatabaseManager databaseManager;

    PokemonData currentPokemonData;
    Pokemon currentPokemon;
    int currentPokemonID;

    NetworkingService networkingService;
    JsonService jsonService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        networkingService = ((MyApp)getApplication()).networkingService;
        jsonService = ((MyApp)getApplication()).jsonService;

        currentPokemonID = getIntent().getIntExtra("id", -1);
        networkingService.networkingListener = this;//do I need this or is once in MyApp fine?
        networkingService.getPokemonByID(currentPokemonID);

        //need to still build the details page to display all the below
        //only types and stats will be built in datalistener? - might need to also put anything using currentPokemon there


        /*
        //Defining currentPokemon from intents - so that it can be stored/removed from the DB
        //Can this be made parcelable? - should I just implement
        //have to api call for the individual pokemon as my database doesn't save types/stats todo fix in future
        currentPokemon = new PokemonData(getIntent().getIntExtra("id",-1),
                getIntent().getStringExtra("smallImage"),getIntent().getStringExtra("bigImage"),
                getIntent().getStringExtra("name"),getIntent().getIntExtra("height", -1),
                getIntent().getIntExtra("weight", -1),getIntent().getStringArrayListExtra("types"),
                getIntent().getStringArrayExtra("stats"));//todo change when editing types/stats

         */

        sprite = (ImageView) findViewById(R.id.pokemonBigIcon);
        //right now the image is cut off when the phone is flipped sideways todo fix
        Picasso.get().load(getIntent().getStringExtra("bigImage")).into(sprite);

        databaseManager = ((MyApp)getApplication()).dbManager;
        databaseManager.getDb(this);
        databaseManager.listener = this;
        databaseManager.getAllPokemon();

        addPokemonBtn = (Button) findViewById(R.id.savePokemonButton);
        //moved addPokemonBtn onclick listener to dataListener as it needs need currentPokemon to be define
        deletePokemonBtn = (Button) findViewById(R.id.deletePokemonButton);
        deletePokemonBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //addPokemonBtn.setVisibility(View.VISIBLE);//Will be made visible when the background thread completes deleting it from the database
                deletePokemonBtn.setVisibility(View.INVISIBLE);
                databaseManager.deletePokemonByID(currentPokemonID);
                //toast?
            }
        });

    }

    //Menu Creation
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
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
            case R.id.menuViewSaved:
                savedActivityNavigation();
                break;
        }
        return true;
    }

    void searchActivityNavigation() {
        Intent savedActivity = new Intent(DetailsActivity.this, MainActivity.class);//
        startActivity(savedActivity);
    }

    void savedActivityNavigation() {
        Intent mainActivity = new Intent(DetailsActivity.this, SavedPokemonActivity.class);//
        startActivity(mainActivity);
    }


    @Override
    public void onDataListReady(ArrayList<PokemonData> list) {
        //Ensures the pokemon doesn't already exist in the database
        boolean isSaved = false;
        for (int i=0; i<list.size();i++){
            if(list.get(i).name.equalsIgnoreCase(currentPokemonData.name)){
                isSaved = true;
            }
        }
        if(isSaved){
            addPokemonBtn.setVisibility(View.INVISIBLE);
            deletePokemonBtn.setVisibility(View.VISIBLE);
        }else{
            addPokemonBtn.setVisibility(View.VISIBLE);
            deletePokemonBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAddComplete() {
        databaseManager.getAllPokemon();
    }

    @Override
    public void onDeleteComplete() {
        databaseManager.getAllPokemon();
    }

    @Override
    public void dataListener(String jsonData) {
        currentPokemonData = jsonService.getPokemonData(jsonData);

        addPokemonBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //prevents double clicking/error if background thread is still saving
                addPokemonBtn.setVisibility(View.INVISIBLE);
                databaseManager.saveNewPokemon(currentPokemonData);
            }
        });

    }
}
