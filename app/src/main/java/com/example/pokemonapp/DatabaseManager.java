package com.example.pokemonapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager {

    interface DatabaseListener {
        void onDataListReady (ArrayList<PokemonData> list);//can this be arraylist?
    }

    DatabaseListener listener;
    static PokemonDatabase db;
    //does running this, alongside NetworkingService doing the same thing, work?
    ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);
    Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    static void buildDBInstance(Context context){
        db = Room.databaseBuilder(context,
                PokemonDatabase.class, "test_pokemon_db").build();//If I change PokemonData at all I need to build a new database/change name here
    }                       //todo change above to pokemon_db when certain it works

    public PokemonDatabase getDb(Context context){
        if(db ==null)
            buildDBInstance(context);
        return db;
    }
    //todo - details page will use this to check if the pokemon being displayed is already in the database
    public boolean isPokemonInDataBase(PokemonData pokemonData){
        return false;
    }

    public void saveNewPokemon(PokemonData pokemonData){
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.pokemonDao().addNewPokemonToDB(pokemonData);
            }
        });
    }

    public void getAllPokemon(){

        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<PokemonData> list = new ArrayList<PokemonData>(db.pokemonDao().getAll());//todo check if works
                mainThreadHandler.post(new Runnable() {//goes to main thread
                    @Override
                    public void run() {
                        listener.onDataListReady(list);
                    }
                });
            }
        });
    }
}
