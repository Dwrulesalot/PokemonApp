# PokemonApp

## Short Demo
<p align="center">
Link to a 2 minute demo video: https://youtu.be/RtZ1WXWK3OI
</p>

## Description
This Project uses the following API https://pokeapi.co/ which doesn't have a built in search by Pokemon Name functionality so I created a method for that in my [MainActivity](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/MainActivity.java).

This application has persistent storage using Room DB, which is managed in the following files: [DatabaseManager.java](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/DatabaseManager.java), [PokemonDao.java](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/PokemonDao.java), [PokemonData.java](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/PokemonData.java), and [PokemonDatabase.java](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/PokemonDatabase.java). 

When a pokemon is displayed in a [RecyclerView with an Adapter](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/SavedPokemonRecyclerAdapter.java)(either in the [MainActivity](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/MainActivity.java) or the [SavedPokemonActivity](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/SavedPokemonActivity.java)), it can be clicked to navigate to the [DetailsActivity](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/DetailsActivity.java) page which recieves all the Pokemon's data through intents and displays detailed pokemon information.

## Classes

[PokemonData.java](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/PokemonData.java) holds all data used in this application when it comes to an individual pokemon, ie icons, height, weight, and various stats that can be seen on the [DetailsActivity](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/DetailsActivity.java).

[PokemonSearchData.java](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/PokemonSearchData.java) is a simple object meant to hold the Pokemon's name and URL, which when matching a user's search input is used to call the [NetworkingService](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/NetworkingService.java) to populate a [PokemonData object.](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/PokemonData.java)

## Services

[NetworkService.java](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/NetworkingService.java) uses multi-threading in the app's background to GET json data from the https://pokeapi.co/ API, saving that data temporarily to a file in the cache directory. 

[JsonService.java](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/JsonService.java) parses through the json data in the created file and defines the data either into an array of [PokemonSearchData](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/PokemonSearchData.java) (run once on inital OnCreate of [MainActivity](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/MainActivity.java), which is used for search functionality) or parses the data into a [PokemonData](https://github.com/Dwrulesalot/PokemonApp/blob/main/app/src/main/java/com/example/pokemonapp/PokemonData.java) object. 
