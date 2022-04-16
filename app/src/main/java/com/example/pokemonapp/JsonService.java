package com.example.pokemonapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {

    //json parse for initial search/to give search functionality
    public ArrayList<PokemonSearchData> getFullPokemonList(String jsonData){

        ArrayList<PokemonSearchData> allPokemon = new ArrayList<>(0);
        try {
            JSONObject mainJsonObject = new JSONObject(jsonData);

            JSONArray resultsArray = new JSONArray(String.valueOf(String.valueOf(mainJsonObject.getJSONArray("results"))));

            //Loops through array, putting each index into a jsonObject then creating a pokemonSearchData and adding it to the allPokemon ArrayList
            for(int i = 0; i< resultsArray.length(); i++){

                String index = resultsArray.get(i).toString();
                JSONObject jsonObject = new JSONObject(index);
                String name = jsonObject.getString("name");
                String url = jsonObject.getString("url");

                PokemonSearchData pokemonSearchData = new PokemonSearchData(name, url);
                allPokemon.add(pokemonSearchData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return allPokemon;//do I want to return or do I want to just change the MyApp allPokemon variable?
    }


    //returns all data for an individual Pokemon needed to display in recycler views/details page
    public PokemonData getPokemonData(String jsonData){

        PokemonData pokemonData = new PokemonData();
        Log.d("JONNY", jsonData);
        try {

            JSONObject mainJsonObject = new JSONObject(jsonData);
            Log.d("WTF", String.valueOf(mainJsonObject));
            pokemonData.height = mainJsonObject.getInt("height");
            pokemonData.id = mainJsonObject.getInt("id");
            pokemonData.name =  mainJsonObject.getString("name");
            pokemonData.weight = mainJsonObject.getInt("weight");


            JSONObject sprites1JsonObject = new JSONObject(String.valueOf(mainJsonObject.getJSONObject("sprites")));//does this work?
            pokemonData.smallIcon = sprites1JsonObject.getString("front_default");
            JSONObject sprites2JsonObject = new JSONObject(String.valueOf(sprites1JsonObject.getJSONObject("other")));//^^
            JSONObject sprites3JsonObject = new JSONObject(String.valueOf(sprites2JsonObject.getJSONObject("official-artwork")));
            pokemonData.bigIcon = sprites3JsonObject.getString("front_default");

            /*
            JSONArray statsArray = new JSONArray(mainJsonObject.getJSONArray("stats"));

            //Loops through stats json array, putting each index into a jsonObject then creating a StatModel and adding it to pokemonData.stats
            for(int i = 0; i< statsArray.length(); i++){
                String index = statsArray.get(0).toString();
                JSONObject jsonObject = new JSONObject(index);
                int base_stat = jsonObject.getInt("base_stat");
                String name = jsonObject.getString("name");

                StatModel statModel = new StatModel(base_stat, name);
                pokemonData.stats.add(statModel);
            }

            JSONArray typesArray = new JSONArray(mainJsonObject.getJSONArray("types"));

            //Loops through types json array, putting each index into a jsonObject then adding it to pokemonData.types
            for(int i = 0; i< typesArray.length(); i++){
                String index = typesArray.get(0).toString();
                JSONObject jsonObject = new JSONObject(index);
                JSONObject typeObject = new JSONObject(String.valueOf(jsonObject.getJSONObject("type")));
                String name = typeObject.getString("name");

                pokemonData.types.add(name);
            }

             */
            int base_stat = 45;
            String name = "hp";

            StatModel statModel = new StatModel(base_stat, name);

            pokemonData.stats.add(statModel);
            pokemonData.types.add("Fire");

            Log.d("JsonService-getPokemonData(String jsonData)", String.valueOf(pokemonData));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pokemonData;
    }

    //returns all data for an individual Pokemon needed to display in recycler views/details page
    public Pokemon getPokemon(String jsonData){

        Pokemon pokemon = new Pokemon();
        Log.d("JONNY", jsonData);
        try {

            JSONObject mainJsonObject = new JSONObject(jsonData);
            Log.d("WTF", String.valueOf(mainJsonObject));
            pokemon.height = mainJsonObject.getInt("height");
            pokemon.id = mainJsonObject.getInt("id");
            pokemon.name =  mainJsonObject.getString("name");
            pokemon.weight = mainJsonObject.getInt("weight");


            JSONObject sprites1JsonObject = new JSONObject(String.valueOf(mainJsonObject.getJSONObject("sprites")));//does this work?
            pokemon.smallIcon = sprites1JsonObject.getString("front_default");
            JSONObject sprites2JsonObject = new JSONObject(String.valueOf(sprites1JsonObject.getJSONObject("other")));//^^
            JSONObject sprites3JsonObject = new JSONObject(String.valueOf(sprites2JsonObject.getJSONObject("official-artwork")));
            pokemon.bigIcon = sprites3JsonObject.getString("front_default");

            /*
            JSONArray statsArray = new JSONArray(mainJsonObject.getJSONArray("stats"));

            //Loops through stats json array, putting each index into a jsonObject then creating a StatModel and adding it to pokemonData.stats
            for(int i = 0; i< statsArray.length(); i++){
                String index = statsArray.get(0).toString();
                JSONObject jsonObject = new JSONObject(index);
                int base_stat = jsonObject.getInt("base_stat");
                String name = jsonObject.getString("name");

                StatModel statModel = new StatModel(base_stat, name);
                pokemonData.stats.add(statModel);
            }

            JSONArray typesArray = new JSONArray(mainJsonObject.getJSONArray("types"));

            //Loops through types json array, putting each index into a jsonObject then adding it to pokemonData.types
            for(int i = 0; i< typesArray.length(); i++){
                String index = typesArray.get(0).toString();
                JSONObject jsonObject = new JSONObject(index);
                JSONObject typeObject = new JSONObject(String.valueOf(jsonObject.getJSONObject("type")));
                String name = typeObject.getString("name");

                pokemonData.types.add(name);
            }

             */

            StatModel statModel = new StatModel(45, "hp");

            pokemon.stats.add(statModel);
            pokemon.types.add("Fire");

            Log.d("JsonService-getPokemonData(String jsonData)", String.valueOf(pokemon));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pokemon;
    }

}
