package com.example.pokemonapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//do I need two of these? one for api and one for my database?
//backup just in case:
/*
public class PokemonData {
    int id;
    String smallIcon;
    String bigIcon;
    String name;
    int height;
    int weight;

    String[] types;
    String[] stats;

    public PokemonData(int id, String smallIcon, String bigIcon, String name, int height, int weight, String[] types, String[] stats){
        this.id = id;
        this.smallIcon = smallIcon;
        this.bigIcon = bigIcon;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.stats = stats;
    }
 */
@Entity
public class PokemonData {
    @PrimaryKey(autoGenerate = true)
    int databaseID;

    int id;//pokemon id
    String smallIcon;
    String bigIcon;
    String name;
    int height;
    int weight;

    @Ignore     //todo check back if I still want to ignore this - need to change these? - want variables inside of these maybe need classes to deal with this data?
    String[] types;
    @Ignore
    String[] stats;

    //temp
    public PokemonData(){

    }

    public PokemonData(int id, String smallIcon, String bigIcon, String name, int height, int weight, String[] types, String[] stats){
        this.id = id;
        this.smallIcon = smallIcon;
        this.bigIcon = bigIcon;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.stats = stats;
    }
}
