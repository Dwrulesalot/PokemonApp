package com.example.pokemonapp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.LogRecord;

public class NetworkingService {
    private String allPokemonURL = "https://pokeapi.co/api/v2/pokemon/?limit=1126";// todo make these string resource in future to support different languages
    private String onePokemonURL = "https://pokeapi.co/api/v2/pokemon/";
    private String defaultPageURL = "https://pokeapi.co/api/v2/pokemon/?limit=26";

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(20);
    public static android.os.Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListener{
        void dataListener(String jsonData);
    }

    public NetworkingListener networkingListener;

    public void getAllPokemon(){
        connect(allPokemonURL);
    }

    public void getPokemonByURL(String url){
        connect(url);
    }

    public void getDefaultPokemon(){
        connect(defaultPageURL);
    }

    public void getPokemonByID (int id){connect(onePokemonURL+Integer.toString(id));}

    //todo update to write to local storage file / cache instead - make file name the url or something?
    // the json is string too big (10-11 thousand characters for a single pokemon) and slows down everything when InputStreamReader reading into memory
    public void connect(String url){
        Log.d("NetworkingService: url = ", url);
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    URL urlObject = new URL(url);
                    httpURLConnection = (HttpURLConnection)urlObject.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");

                    InputStream inputStream = null;
                    inputStream = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(inputStream);

                    String jsonData = "";
                    int inputStreamData = 0;
                    //while not at the end of file
                    while ((inputStreamData = reader.read())!=-1){
                        char current = (char) inputStreamData;
                        jsonData+=current;
                    }
                    Log.d("json", jsonData);

                    //Back to main thread through interface
                    final String finalJsonString = jsonData;
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            networkingListener.dataListener(finalJsonString);
                            }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }
}
