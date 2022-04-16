package com.example.pokemonapp;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

public class MyApp extends Application implements NetworkingService.NetworkingListener {

    DatabaseManager dbManager = new DatabaseManager();
    NetworkingService networkingService = new NetworkingService();

    JsonService jsonService = new JsonService();

    //holds ArrayList of all pokemon name/api urls
    ArrayList<PokemonSearchData> allPokemon = new ArrayList<>(0);
    //holds ArrayList of default/starting 13 pokemon name/api urls
    ArrayList<PokemonSearchData> defaultPokemon = new ArrayList<>(0);
    int numOfDefaultPokemon = 12;

    ArrayList<PokemonSearchData> currentSearchData = new ArrayList<>(0);

    ArrayList<PokemonData> currentListPokemonData = new ArrayList<>(0);

    public MyApp() {
        networkingService.networkingListener = this;
        networkingService.getAllPokemon();
    }

    @Override
    public void dataListener(String jsonData) {
        allPokemon = jsonService.getFullPokemonList(jsonData);
        defaultPokemon = new ArrayList<PokemonSearchData>(allPokemon.subList(0,numOfDefaultPokemon));
        Log.d("MyApp: String.valueOf(defaultPokemon)", String.valueOf(defaultPokemon));
    }

    public NetworkingService getNetworkingService() {
        return networkingService;
    }
}
