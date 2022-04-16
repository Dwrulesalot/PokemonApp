package com.example.pokemonapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener {
    ImageView sprite;
    Button addPokemonBtn;
    Button deletePokemonBtn;
    TextView name;
    TextView type1;
    TextView type2;
    TextView height;
    TextView weight;
    TextView hp;
    TextView attack;
    TextView defense;
    TextView specialAttack;
    TextView specialDefense;
    TextView speed;


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

        //used for deleting from database
        currentPokemonID = getIntent().getIntExtra("id", -1);

        //Defining currentPokemon from intents - so that it can be stored/removed from the DB
        currentPokemonData = new PokemonData(getIntent().getIntExtra("id",-1), getIntent().getStringExtra("smallImage"),getIntent().getStringExtra("bigImage"),
                getIntent().getStringExtra("name"),getIntent().getIntExtra("height", -1), getIntent().getIntExtra("weight", -1),
                getIntent().getStringExtra("type1"), getIntent().getStringExtra("type2"),getIntent().getIntExtra("hpStat", -1),
                getIntent().getIntExtra("attackStat", -1), getIntent().getIntExtra("defenseStat", -1),
                getIntent().getIntExtra("specialAttackStat", -1), getIntent().getIntExtra("specialDefenseStat", -1),
                getIntent().getIntExtra("speedStat", -1));

        name = (TextView) findViewById(R.id.detailsName);
        name.setText(currentPokemonData.name);
        type1 = (TextView) findViewById(R.id.detailsType1);
        type1.setText(currentPokemonData.type1);
        type2 = (TextView) findViewById(R.id.detailsType2);
        type2.setText(currentPokemonData.type2);
        height = (TextView) findViewById(R.id.detailsHeight);
        height.setText(heightToString(Integer.toString(currentPokemonData.height)));
        weight = (TextView) findViewById(R.id.detailsWeight);
        weight.setText(weightToString(Integer.toString(currentPokemonData.weight)));
        hp = (TextView) findViewById(R.id.detailsHP);
        hp.setText(Integer.toString(currentPokemonData.hpStat));
        attack = (TextView) findViewById(R.id.detailsAttack);
        attack.setText(Integer.toString(currentPokemonData.attackStat));
        defense = (TextView) findViewById(R.id.detailsDefense);
        defense.setText(Integer.toString(currentPokemonData.defenseStat));
        specialAttack = (TextView) findViewById(R.id.detailsSpecialAttack);
        specialAttack.setText(Integer.toString(currentPokemonData.specialAttackStat));
        specialDefense = (TextView) findViewById(R.id.detailsSpecialDefense);
        specialDefense.setText(Integer.toString(currentPokemonData.specialDefenseStat));
        speed = (TextView) findViewById(R.id.detailsSpeed);
        speed.setText(Integer.toString(currentPokemonData.speedStat));

        //functionality to change background depending on type value
        if(currentPokemonData.type1.equalsIgnoreCase("bug")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.bug));}
        else if(currentPokemonData.type1.equalsIgnoreCase("dark")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.dark));}
        else if(currentPokemonData.type1.equalsIgnoreCase("dragon")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.dragon));}
        else if(currentPokemonData.type1.equalsIgnoreCase("electric")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.electric));}
        else if(currentPokemonData.type1.equalsIgnoreCase("fairy")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.fairy));}
        else if(currentPokemonData.type1.equalsIgnoreCase("fighting")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.fighting));}
        else if(currentPokemonData.type1.equalsIgnoreCase("fire")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.fire));}
        else if(currentPokemonData.type1.equalsIgnoreCase("flying")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.flying));}
        else if(currentPokemonData.type1.equalsIgnoreCase("ghost")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.ghost));}
        else if(currentPokemonData.type1.equalsIgnoreCase("grass")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.grass));}
        else if(currentPokemonData.type1.equalsIgnoreCase("ground")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.ground));}
        else if(currentPokemonData.type1.equalsIgnoreCase("ice")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.ice));}
        else if(currentPokemonData.type1.equalsIgnoreCase("normal")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.normal));}
        else if(currentPokemonData.type1.equalsIgnoreCase("poison")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.poison));}
        else if(currentPokemonData.type1.equalsIgnoreCase("psychic")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.psychic));}
        else if(currentPokemonData.type1.equalsIgnoreCase("rock")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.rock));}
        else if(currentPokemonData.type1.equalsIgnoreCase("steel")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.steel));}
        else if(currentPokemonData.type1.equalsIgnoreCase("water")){type1.setBackgroundColor(ContextCompat.getColor(this, R.color.water));}
        else{type1.setBackgroundColor(ContextCompat.getColor(this, R.color.empty));}

        if(currentPokemonData.type2.equalsIgnoreCase("bug")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.bug));}
        else if(currentPokemonData.type2.equalsIgnoreCase("dark")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.dark));}
        else if(currentPokemonData.type2.equalsIgnoreCase("dragon")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.dragon));}
        else if(currentPokemonData.type2.equalsIgnoreCase("electric")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.electric));}
        else if(currentPokemonData.type2.equalsIgnoreCase("fairy")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.fairy));}
        else if(currentPokemonData.type2.equalsIgnoreCase("fighting")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.fighting));}
        else if(currentPokemonData.type2.equalsIgnoreCase("fire")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.fire));}
        else if(currentPokemonData.type2.equalsIgnoreCase("flying")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.flying));}
        else if(currentPokemonData.type2.equalsIgnoreCase("ghost")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.ghost));}
        else if(currentPokemonData.type2.equalsIgnoreCase("grass")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.grass));}
        else if(currentPokemonData.type2.equalsIgnoreCase("ground")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.ground));}
        else if(currentPokemonData.type2.equalsIgnoreCase("ice")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.ice));}
        else if(currentPokemonData.type2.equalsIgnoreCase("normal")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.normal));}
        else if(currentPokemonData.type2.equalsIgnoreCase("poison")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.poison));}
        else if(currentPokemonData.type2.equalsIgnoreCase("psychic")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.psychic));}
        else if(currentPokemonData.type2.equalsIgnoreCase("rock")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.rock));}
        else if(currentPokemonData.type2.equalsIgnoreCase("steel")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.steel));}
        else if(currentPokemonData.type2.equalsIgnoreCase("water")){type2.setBackgroundColor(ContextCompat.getColor(this, R.color.water));}
        else{type2.setBackgroundColor(ContextCompat.getColor(this, R.color.empty));}

        sprite = (ImageView) findViewById(R.id.pokemonBigIcon);
        Picasso.get().load(currentPokemonData.bigIcon).into(sprite);

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

    public String heightToString(String height){
        StringBuilder heightText = new StringBuilder(height);
        //if shorter than a meter tall adding a 0 in front
        if(heightText.length() ==1)
            heightText.insert(0,"0");
        heightText.insert(heightText.length()-1, '.');
        //context so that I can access my String resource
        Context tempContext = this;
        heightText.append(" "+tempContext.getString(R.string.heightMeasurement));
        return String.valueOf(heightText);
    }
    public String weightToString(String weight){
        StringBuilder weightText = new StringBuilder(weight);
        //if weighing less than a kg adding a 0 in front
        if(weightText.length() == 1)
            weightText.insert(0,"0");
        weightText.insert(weightText.length()-1, '.');
        //context so that I can access my String resource
        Context tempContext = this;
        weightText.append(" "+tempContext.getString(R.string.weightMeasurement));
        return String.valueOf(weightText);
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
