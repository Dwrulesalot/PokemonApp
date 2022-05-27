package com.example.pokemonapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
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
        void dataListener(String fileName);
        void allPokemonDataListener(String fileName);
    }

    public NetworkingListener networkingListener;

    public void getAllPokemon(Context context){
        connect(allPokemonURL, "allPokemon", context);
    }

    public void getPokemonByURL(String url, String filename, Context context){
        connect(url, filename, context);
    }

    //public void getDefaultPokemon(){        connect(defaultPageURL);    }

    //public void getPokemonByID (int id){connect(onePokemonURL+Integer.toString(id));}

    //Connects to the given url, then gets and stores the received json into a file "fileName" in the cacheDir
    public void connect(String url, String fileName, Context context){
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


                    File cacheFile = new File(context.getCacheDir(), fileName);

                    int inputStreamData = 0;
                    //while not at the end of file
                    FileOutputStream fos = new FileOutputStream(cacheFile, true);
                    while ((inputStreamData = reader.read())!=-1){
                        fos.write(inputStreamData);
                    }
                    //todo find what resource isn't closing - networkExecutorService or networkingHandler?
                    fos.flush();
                    fos.close();
                    inputStream.close();
                    reader.close();
                    Log.d("file complete ", "yep");

                    //Back to main thread through interface
                    if(fileName.equalsIgnoreCase("allPokemon")){
                        networkingHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                networkingListener.allPokemonDataListener(fileName);
                            }
                        });
                    }
                    else {
                        networkingHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                networkingListener.dataListener(fileName);
                            }
                        });
                    }

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
