package com.example.pokemonapp;

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
}
