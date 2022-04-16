package com.example.pokemonapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class DetailsActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener {
    ImageView sprite;
    Button addPokemonBtn;
    Button deletePokemonBtn;

    DatabaseManager databaseManager;

    PokemonData currentPokemonData;
    int currentPokemonID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        databaseManager = ((MyApp)getApplication()).dbManager;
        databaseManager.getDb(this);
        databaseManager.listener = this;
        databaseManager.getAllPokemon();

        currentPokemonID = getIntent().getIntExtra("id", -1);

        //Defining currentPokemon from intents - so that it can be stored/removed from the DB
        currentPokemonData = new PokemonData(getIntent().getIntExtra("id",-1), getIntent().getStringExtra("smallImage"),getIntent().getStringExtra("bigImage"),
                getIntent().getStringExtra("name"),getIntent().getIntExtra("height", -1), getIntent().getIntExtra("weight", -1),
                getIntent().getStringExtra("type1"), getIntent().getStringExtra("type2"),getIntent().getIntExtra("hpStat", -1),
                getIntent().getIntExtra("attackStat", -1), getIntent().getIntExtra("defenseStat", -1),
                getIntent().getIntExtra("specialAttackStat", -1), getIntent().getIntExtra("specialDefenseStat", -1),
                getIntent().getIntExtra("speedStat", -1));


        Log.d("Details Activity: currentPokemonData.toString()",currentPokemonData.toString());
        //todo need to still build the details page to display all of the above
        sprite = (ImageView) findViewById(R.id.pokemonBigIcon);
        //right now the image is cut off when the phone is flipped sideways todo fix
        Picasso.get().load(getIntent().getStringExtra("bigImage")).into(sprite);

        addPokemonBtn = (Button) findViewById(R.id.savePokemonButton);
        addPokemonBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //prevents double clicking/error if background thread is still saving
                addPokemonBtn.setVisibility(View.INVISIBLE);
                databaseManager.saveNewPokemon(currentPokemonData);
            }
        });
        deletePokemonBtn = (Button) findViewById(R.id.deletePokemonButton);
        deletePokemonBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Will be made visible when the background thread completes deleting it from the database
                deletePokemonBtn.setVisibility(View.INVISIBLE);
                databaseManager.deletePokemonByID(currentPokemonID);
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

}
