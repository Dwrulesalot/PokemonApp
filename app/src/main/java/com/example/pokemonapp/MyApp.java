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

    //might not need to back this up here
    ArrayList<PokemonSearchData> currentSearchData = new ArrayList<>(0);

    ArrayList<PokemonData> currentListPokemonData = new ArrayList<>(0);

    public MyApp() {
        networkingService.networkingListener = this;
        networkingService.getAllPokemon();
    }

    @Override
    public void dataListener(String jsonData) {
        allPokemon = jsonService.getFullPokemonList(jsonData);
        Log.d("MyApp startDataListener: String.valueOf(allPokemon)", String.valueOf(allPokemon));
    }

    public NetworkingService getNetworkingService() {
        return networkingService;
    }
}
