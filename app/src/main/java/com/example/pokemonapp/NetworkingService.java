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
    private String onePokemonURL = "https://pokeapi.co/api/v2/pokemon/";//add pokemon id - might not need this as if we search by name it gives the pokemon's url

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static android.os.Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListener{
        void dataListener(String jsonData);
    }

    public NetworkingListener networkingListener;

    public void getAllPokemon(){
        connect(allPokemonURL);
    }
    //Assuming I'm getting a url String from ResultModel - do I return/ store pokemon data somewhere?
    public void getPokemonByURL(String url){
        connect(url);
    }

    public void connect(String url){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlObject = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)urlObject.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);

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
            }
        });
    }
}
