package com.example.pokemonapp;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SavedPokemonActivity extends AppCompatActivity {

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