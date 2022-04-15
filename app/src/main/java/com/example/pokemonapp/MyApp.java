package com.example.pokemonapp;

import android.app.Application;

import java.util.ArrayList;

public class MyApp extends Application {

    DatabaseManager dbManager = new DatabaseManager();
    NetworkingService networkingService = new NetworkingService();

    //holds ArrayList of all pokemon name/api urls
    ArrayList<PokemonSearchData> allPokemon;

    public MyApp() {
        networkingService.getAllPokemon();
    }


}
