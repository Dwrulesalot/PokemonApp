package com.example.pokemonapp;

import android.app.Application;

public class MyApp extends Application {

    DatabaseManager dbManager = new DatabaseManager();
    NetworkingService networkingService = new NetworkingService();

    public MyApp() {

    }


}
