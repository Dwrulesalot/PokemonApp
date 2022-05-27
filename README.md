# PokemonApp

## Short Demo
<p align="center">
Link to a 2 minute demo video: https://youtu.be/RtZ1WXWK3OI
</p>

## Description
This Project uses the following API https://pokeapi.co/ which doesn't have a built in search functionality so I created a method for that in my MainActivity.

This application has persistent storage using Room DB, which is managed in the following files: DatabaseManager.java, PokemonDao.java, PokemonData.java, and PokemonDatabase.java. 

When a pokemon is displayed in a RecyclerView(either in the MainActivity or the SavedPokemonActivity), it can be clicked to navigate to the DetailsActivity page which recieves all the Pokemon's data through intents and displays detailed pokemon information.

## Classes

PokemonData.java holds all data used in this application when it comes to an individual pokemon, ie icons, height, weight, and various stats that can be seen on the DetailsActivity.

PokemonSearchData.java is a simple object meant to hold the Pokemon's name and URL, which when matching a user's search input is used to call the NetworkingService to populate a PokemonData object. 

## Services

NetworkService.java GETS json data from the https://pokeapi.co/ API and saves it to a file in the cache directory. 

JsonService.java parses through the json data in the created file and defines the data either into an array of PokemonSearchData (run once on inital OnCreate of MainActivity, which is used for search functionality) or parses the data into a PokemonData object. 
