package com.example.pokemonapp;

public class PokemonSearchData {
    String name;
    String url;


    //do I eventually wanna split the id# out of the url? or will this be easier?
    public PokemonSearchData(String url, String name){
        this.name = name;
        this.url = url;
    }

    //todo method that takes a string confirms if that string is contained in this pokemon's name - here or elsewhere?
}
