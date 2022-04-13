package com.example.pokemonapp;

import android.content.Context;

import androidx.room.Room;

public class DatabaseManager {

    static PokemonDatabase db;

    static void buildDBInstance(Context context){
        db = Room.databaseBuilder(context,
                PokemonDatabase.class, "test_pokemon_db").build();//If I change PokemonData at all I need to build a new database/change name here
    }                       //todo change above to pokemon_db when certain it works

    public PokemonDatabase getDb(Context context){
        if(db ==null)
            buildDBInstance(context);
        return db;
    }
}
