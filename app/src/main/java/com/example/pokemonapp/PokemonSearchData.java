package com.example.pokemonapp;

public class PokemonSearchData {
    String name;
    String url;

    public PokemonSearchData(){
        name = "";
        url = "";
    }

    public PokemonSearchData(String name, String url){
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return "PokemonSearchData{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
