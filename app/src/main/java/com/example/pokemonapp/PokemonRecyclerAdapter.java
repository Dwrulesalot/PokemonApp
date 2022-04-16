package com.example.pokemonapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

    ArrayList<Pokemon> pokemonArrayList;
    Context context;

    public PokemonRecyclerAdapter(ArrayList<Pokemon> pokemonArrayList, Context context){
        this.pokemonArrayList = pokemonArrayList;
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
        Picasso.get().load(pokemonArrayList.get(position).smallIcon).into(holder.pokemonSmallIcon);
        holder.pokemonName.setText(pokemonArrayList.get(position).name);

        holder.pokemonHeight.setText(heightToString(String.valueOf(pokemonArrayList.get(position).height)));
        holder.pokemonWeight.setText(weightToString(String.valueOf(pokemonArrayList.get(position).weight)));
    }
    public String heightToString(String height){
        StringBuilder heightText = new StringBuilder(height);
        //if shorter than a meter tall adding a 0 in front
        if(heightText.length() ==1)
            heightText.insert(0,"0");
        heightText.insert(heightText.length()-1, '.');
        //context so that I can access my String resource
        Context tempContext = context.getApplicationContext();
        heightText.append(" "+tempContext.getString(R.string.heightMeasurement));
        return String.valueOf(heightText);
    }
    public String weightToString(String weight){
        StringBuilder weightText = new StringBuilder(weight);
        //if weighing less than a kg adding a 0 in front
        if(weightText.length() == 1)
            weightText.insert(0,"0");
        weightText.insert(weightText.length()-1, '.');
        //context so that I can access my String resource
        Context tempContext = context.getApplicationContext();
        weightText.append(" "+tempContext.getString(R.string.weightMeasurement));
        return String.valueOf(weightText);
    }

    @Override
    public int getItemCount() {
        return pokemonArrayList.size();
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
                    //text including the decimal and measurement
                    detailsIntent.putExtra("heightText", pokemonHeight.getText());
                    detailsIntent.putExtra("weightText", pokemonWeight.getText());
                    detailsIntent.putExtra("height", pokemonArrayList.get(position).height);
                    detailsIntent.putExtra("weight", pokemonArrayList.get(position).weight);
                    detailsIntent.putExtra("smallImage", pokemonArrayList.get(position).smallIcon);
                    detailsIntent.putExtra("bigImage", pokemonArrayList.get(position).bigIcon);
                    detailsIntent.putExtra("id", pokemonArrayList.get(position).id);

                    c.startActivity(detailsIntent);
                }
            });
        }
    }
}
