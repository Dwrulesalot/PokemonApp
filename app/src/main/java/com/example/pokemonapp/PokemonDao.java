package com.example.pokemonapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PokemonDao {
    @Insert
    void addNewPokemonToDB(PokemonData newPokemonData);

    @Delete
    void deletePokemonFromDB(PokemonData toDeletePokemonData);

    @Query("SELECT * FROM PokemonData")
    List<PokemonData> getAll();//todo check if this can be an arrayList so it works with my recycler view

    //do I want any other queries?
}
