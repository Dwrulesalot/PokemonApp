package com.example.pokemonapp;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PokemonData.class}, version = 1)
public abstract class PokemonDatabase extends RoomDatabase {
    public abstract PokemonDao pokemonDao();
}


