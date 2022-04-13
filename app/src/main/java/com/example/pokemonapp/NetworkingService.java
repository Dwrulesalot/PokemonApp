package com.example.pokemonapp;

import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

public class NetworkingService {
    private String allPokemonURL = "https://pokeapi.co/api/v2/pokemon/?limit=1126";
    private String onePokemonURL = "https://pokeapi.co/api/v2/pokemon/";//add pokemon id - might not need this as if we search by name it gives the pokemon's url

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);//watch week 10
    //public static Handler networkingHandler = new Handler(Looper.getMainLooper()); //week 10 first

    public void connect(String url){

    }
}
