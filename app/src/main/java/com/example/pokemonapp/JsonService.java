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

        try {

            JSONObject mainJsonObject = new JSONObject(jsonData);

            pokemonData.height = mainJsonObject.getInt("height");
            pokemonData.id = mainJsonObject.getInt("id");
            pokemonData.name =  mainJsonObject.getString("name");
            pokemonData.weight = mainJsonObject.getInt("weight");


            JSONObject sprites1JsonObject = new JSONObject(String.valueOf(mainJsonObject.getJSONObject("sprites")));
            pokemonData.smallIcon = sprites1JsonObject.getString("front_default");
            JSONObject sprites2JsonObject = new JSONObject(String.valueOf(sprites1JsonObject.getJSONObject("other")));
            JSONObject sprites3JsonObject = new JSONObject(String.valueOf(sprites2JsonObject.getJSONObject("official-artwork")));
            pokemonData.bigIcon = sprites3JsonObject.getString("front_default");


            JSONArray statsArray = new JSONArray(String.valueOf(mainJsonObject.getJSONArray("stats")));
            int[] allStats = new int[6];

            //Loops through stats json array, putting each index into a jsonObject then creating a StatModel and adding it to pokemonData.stats
            for(int i = 0; i< statsArray.length(); i++){
                JSONObject jsonObject = new JSONObject(statsArray.get(i).toString());
                int base_stat = jsonObject.getInt("base_stat");
                allStats[i] = base_stat;
            }
            //todo check that below works
            pokemonData.hpStat = allStats[0];
            pokemonData.attackStat = allStats[1];
            pokemonData.defenseStat = allStats[2];
            pokemonData.specialAttackStat = allStats[3];
            pokemonData.specialDefenseStat = allStats[4];
            pokemonData.speedStat = allStats[5];

            JSONArray typesArray = new JSONArray(String.valueOf(mainJsonObject.getJSONArray("types")));

            String[] allTypes = new String[]{"",""};

            //All pokemon have either 1 or 2 types, no exceptions.
            if (typesArray.length()==1){//if there's only one type
                JSONObject jsonObject = new JSONObject(typesArray.get(0).toString());
                JSONObject typeObject = new JSONObject(String.valueOf(jsonObject.getJSONObject("type")));
                String name = typeObject.getString("name");

                allTypes[0] = name;
            }else{//there's two types
                JSONObject jsonObject1 = new JSONObject(typesArray.get(0).toString());
                JSONObject jsonObject2 = new JSONObject(typesArray.get(1).toString());
                JSONObject type1Object = new JSONObject(String.valueOf(jsonObject1.getJSONObject("type")));
                JSONObject type2Object = new JSONObject(String.valueOf(jsonObject2.getJSONObject("type")));
                String type1 = type1Object.getString("name");
                String type2 = type2Object.getString("name");

                allTypes = new String[]{type1, type2};
            }

            pokemonData.type1 = allTypes[0];
            pokemonData.type2 = allTypes[1];

            Log.d("JsonService-getPokemonData(String jsonData): pokemonData", String.valueOf(pokemonData));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pokemonData;
    }

}
