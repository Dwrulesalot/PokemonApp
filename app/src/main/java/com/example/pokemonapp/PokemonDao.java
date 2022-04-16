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
    List<PokemonData> getAll();

    //helps with deletion
    @Query("SELECT * FROM PokemonData WHERE id= :id")
    PokemonData getPokemonDataById(int id);
}
