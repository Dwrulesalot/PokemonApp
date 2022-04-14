package com.example.pokemonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ImageView sprite;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        sprite = (ImageView) findViewById(R.id.pokemonBigIcon);

        Picasso.get().load(getIntent().getStringExtra("image")).into(sprite);//right now the image is cut off when the phone is flipped sideways todo fix
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
}
