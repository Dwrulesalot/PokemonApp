package com.example.pokemonapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.ArrayList;

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

    //ignoring saving below - couldn't figure out Type Converters / making it it's own @ Entity was causing problems too
    //todo will I need to api call for types/stats to display on details? or only if offline?
    @Ignore
    ArrayList<String> types;
    @Ignore
    ArrayList<StatModel> stats;

    public PokemonData(){
        //should set this up eventually
    }

    public PokemonData(int id, String smallIcon, String bigIcon, String name, int height, int weight, ArrayList<String> types, ArrayList<StatModel> stats){
        this.id = id;
        this.smallIcon = smallIcon;
        this.bigIcon = bigIcon;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "PokemonData{" +
                "databaseID=" + databaseID +
                ", id=" + id +
                ", smallIcon='" + smallIcon + '\'' +
                ", bigIcon='" + bigIcon + '\'' +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", types=" + types +
                ", stats=" + stats +
                '}';
    }
}
