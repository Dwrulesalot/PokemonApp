package com.example.pokemonapp;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import java.util.ArrayList;

public class MyApp extends Application {

    DatabaseManager dbManager = new DatabaseManager();
    NetworkingService networkingService = new NetworkingService();

    JsonService jsonService = new JsonService();

    //holds ArrayList of all pokemon name/api urls
    ArrayList<PokemonSearchData> allPokemon = new ArrayList<>(0);
    //holds ArrayList of default pokemon names & urls
    ArrayList<PokemonSearchData> defaultPokemon = new ArrayList<>(0);

    ArrayList<PokemonSearchData> currentSearchData = new ArrayList<>(0);

    ArrayList<PokemonData> currentListPokemonData = new ArrayList<>(0);

    public MyApp() {
    }


    public NetworkingService getNetworkingService() {
        return networkingService;
    }
}
