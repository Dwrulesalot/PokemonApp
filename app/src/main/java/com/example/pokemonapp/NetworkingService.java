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
    private String allPokemonURL = "https://pokeapi.co/api/v2/pokemon/?limit=1126";// todo make this string resource for different languages?
    private String onePokemonURL = "https://pokeapi.co/api/v2/pokemon/";// string resource as above?

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

    public void getPokemonByID (int id){connect(onePokemonURL+Integer.toString(id));}

    //todo update to write to local storage file instead - name of url or something - json string too big and slows down everything when reading into memory
    public void connect(String url){
        Log.d("NetworkingService: connect(String url) ","enters connect() ");
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("NetworkingService: networkExecutorService.execute ","enters networkExecutorService.execute() ");
                HttpURLConnection httpURLConnection = null;
                try {
                    Log.d("NetworkingService: networkExecutorService.execute ","in first try ");
                    URL urlObject = new URL(url);
                    httpURLConnection = (HttpURLConnection)urlObject.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    Log.d("NetworkingService: networkExecutorService.execute ","Post httpConnection is complete ");

                    InputStream inputStream = null;
                    inputStream = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    Log.d("NetworkingService: networkExecutorService.execute ","InputStreamReader gets connection input stream");

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
                            networkingListener.dataListener(finalJsonString);//null object reference error?
                            }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    Log.d("NetworkingService: networkExecutorService.execute:finally ","disconnects from http connection");
                    httpURLConnection.disconnect();
                }
            }
        });
        Log.d("NetworkingService: connect(String url) ","exits connect() ");
    }
}
