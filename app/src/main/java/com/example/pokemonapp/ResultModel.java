package com.example.pokemonapp;

public class ResultModel {
    String name;
    String url;

    public ResultModel(){
        name = "";
        url = "";
    }
    public ResultModel(String name, String url ){
        this.name = name;
        this.url = url;
    }
}
