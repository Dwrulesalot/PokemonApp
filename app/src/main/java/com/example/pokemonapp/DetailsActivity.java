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

public class DetailsActivity extends AppCompatActivity /*implements DatabaseManager.DatabaseListener*/ {
    ImageView sprite;
    Button addPokemonBtn;
    Button deletePokemonBtn;

    DatabaseManager databaseManager;

    PokemonData currentPokemon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Defining currentPokemon from intents - so that it can be stored/removed from the DB
        currentPokemon = new PokemonData(getIntent().getIntExtra("id",-1),
                getIntent().getStringExtra("smallImage"),getIntent().getStringExtra("bigImage"),
                getIntent().getStringExtra("name"),getIntent().getIntExtra("height", -1),
                getIntent().getIntExtra("weight", -1),getIntent().getStringArrayExtra("types"),
                getIntent().getStringArrayExtra("stats"));//todo change when editing types/stats

        sprite = (ImageView) findViewById(R.id.pokemonBigIcon);
        //right now the image is cut off when the phone is flipped sideways todo fix
        Picasso.get().load(getIntent().getStringExtra("bigImage")).into(sprite);

        databaseManager = ((MyApp)getApplication()).dbManager;
        databaseManager.getDb(this);//todo check if the currently displayed pokemon is in the database and make the appropriate button visible
        //databaseManager.getAllPokemon();

        addPokemonBtn = (Button) findViewById(R.id.savePokemonButton);
        addPokemonBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //answerClicked(true);//todo add functionality
                databaseManager.saveNewPokemon(currentPokemon);
                //toast?
                addPokemonBtn.setVisibility(View.INVISIBLE);//prevents double clicking/other errors
                //deletePokemonBtn.setVisibility(View.VISIBLE);
            }
        });
        deletePokemonBtn = (Button) findViewById(R.id.deletePokemonButton);
        deletePokemonBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //answerClicked(true);//todo add functionality
                //toast?

                //addPokemonBtn.setVisibility(View.VISIBLE);//Will be made visible when the background thread completes deleting it from the database?
                deletePokemonBtn.setVisibility(View.INVISIBLE);
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

    /*
    @Override
    public void onDataListReady(ArrayList<PokemonData> list) {
        if(list.contains(currentPokemon)){
            addPokemonBtn.setVisibility(View.INVISIBLE);
            deletePokemonBtn.setVisibility(View.VISIBLE);
        }else{
            addPokemonBtn.setVisibility(View.VISIBLE);
            deletePokemonBtn.setVisibility(View.INVISIBLE);
        }
    }
    */
}
