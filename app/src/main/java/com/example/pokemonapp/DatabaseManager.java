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
        void onDataListReady (ArrayList<PokemonData> list);
        void onAddComplete();
        void onDeleteComplete();
    }

    DatabaseListener listener;
    static PokemonDatabase db;
    //does running this, alongside NetworkingService doing the same thing, work?
    ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);
    Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    static void buildDBInstance(Context context){
        db = Room.databaseBuilder(context,
                PokemonDatabase.class, "test1_pokemon_db").build();//If I change PokemonData at all I need to build a new database/change name here
    }                       //todo change above to pokemon_db when certain it works

    public PokemonDatabase getDb(Context context){
        if(db ==null)
            buildDBInstance(context);
        return db;
    }
    //todo - details page will use this to check if the pokemon being displayed is already in the database
    //public boolean isPokemonInDataBase(PokemonData pokemonData){        return false;    }//done in details - check if that's ok - might need this for delete no?

    public void saveNewPokemon(PokemonData pokemonData){
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.pokemonDao().addNewPokemonToDB(pokemonData);
                //need this for proper display in SavedPokemon activity
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onAddComplete();
                    }
                });
            }
        });
    }

    public void getAllPokemon(){
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<PokemonData> list = new ArrayList<PokemonData>(db.pokemonDao().getAll());
                mainThreadHandler.post(new Runnable() {//goes to main thread
                    @Override
                    public void run() {
                        listener.onDataListReady(list);
                    }
                });
            }
        });
    }

    public void deletePokemonByID(int id){
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PokemonData pokemonToDelete = db.pokemonDao().getPokemonDataById(id);
                db.pokemonDao().deletePokemonFromDB(pokemonToDelete);
                //need this for proper display in SavedPokemon activity
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDeleteComplete();
                    }
                });
            }
        });
    }
}
