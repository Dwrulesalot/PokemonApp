package com.example.pokemonapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokemonRecyclerAdapter extends RecyclerView.Adapter<PokemonRecyclerAdapter.PokemonViewHolder>{

    ArrayList<PokemonData> pokemonDataArrayList;
    Context context;

    public PokemonRecyclerAdapter(ArrayList<PokemonData> pokemonDataArrayList, Context context){
        this.pokemonDataArrayList = pokemonDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.pokemon_item_layout, parent, false);
        return new PokemonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        //using Picasso to load/show image
        Picasso.get().load(pokemonDataArrayList.get(position).smallIcon).into(holder.pokemonSmallIcon);
        holder.pokemonName.setText(pokemonDataArrayList.get(position).name);
        holder.pokemonHeight.setText(String.valueOf(pokemonDataArrayList.get(position).height));
        holder.pokemonWeight.setText(String.valueOf(pokemonDataArrayList.get(position).weight));

    }

    @Override
    public int getItemCount() {
        return pokemonDataArrayList.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{
        ImageView pokemonSmallIcon;
        TextView pokemonName;
        TextView pokemonHeight;
        TextView pokemonWeight;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonSmallIcon = itemView.findViewById(R.id.pokemonSmallIcon);
            pokemonName = itemView.findViewById(R.id.pokemonName);
            pokemonHeight = itemView.findViewById(R.id.pokemonHeight);
            pokemonWeight = itemView.findViewById(R.id.pokemonWeight);

            //opening Details page and passing info
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();//need this for info not currently displayed
                    Context c = view.getContext();

                    //todo check if this will work when list comes from api - should all this info be passed or should I do another api call?
                    Intent detailsIntent = new Intent(c, DetailsActivity.class);
                    detailsIntent.putExtra("name", pokemonName.getText());
                    detailsIntent.putExtra("height", pokemonHeight.getText());
                    detailsIntent.putExtra("weight", pokemonWeight.getText());

                    detailsIntent.putExtra("image", pokemonDataArrayList.get(position).bigIcon);
                    detailsIntent.putExtra("id", pokemonDataArrayList.get(position).id);//how will this be effected when using database/api calls

                    //todo functionality for stats and types - need to break them down somehow

                    c.startActivity(detailsIntent);
                }
            });
        }
    }
}
