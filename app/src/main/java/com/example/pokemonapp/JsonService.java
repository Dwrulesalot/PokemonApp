package com.example.pokemonapp;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class JsonService {
    //json parse for initial search/to give search functionality
    public ArrayList<PokemonSearchData> getFullPokemonList(String jsonData){
        ArrayList<PokemonSearchData> allPokemon = new ArrayList<PokemonSearchData>(0);
        try {
            JSONArray jsonArray = new JSONArray(jsonData);

            for(int i = 0; i< jsonArray.length(); i++){
                //String name
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return allPokemon;//do I want to return or do I want to just change the MyApp allPokemon variable?
    }
    //how would I handle the default 151 I plan on doing for the search page?
    //todo individualPokemon *PokemonData* return for all data needed to display in recycler view/details page


}
