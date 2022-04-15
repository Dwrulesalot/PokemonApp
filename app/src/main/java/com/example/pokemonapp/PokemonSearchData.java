package com.example.pokemonapp;

public class PokemonSearchData {
    String name;
    String url;

    public PokemonSearchData(){
        name = "";
        url = "";
    }

    public PokemonSearchData(String url, String name){
        this.name = name;
        this.url = url;
    }

    //todo method that takes a string confirms if that string is contained in this pokemon's name - here or elsewhere?
}
