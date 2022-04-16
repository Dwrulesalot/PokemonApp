
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

//todo make Recycler similar to this for search page that api calls for rest of pokemon data not needed to display on recyclerView
public class SavedPokemonRecyclerAdapter extends RecyclerView.Adapter<SavedPokemonRecyclerAdapter.PokemonViewHolder>{

    ArrayList<PokemonData> pokemonDataArrayList;
    Context context;

    public SavedPokemonRecyclerAdapter(ArrayList<PokemonData> pokemonDataArrayList, Context context){
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

        holder.pokemonHeight.setText(heightToString(String.valueOf(pokemonDataArrayList.get(position).height)));
        holder.pokemonWeight.setText(weightToString(String.valueOf(pokemonDataArrayList.get(position).weight)));
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

                    Intent detailsIntent = new Intent(c, DetailsActivity.class);
                    detailsIntent.putExtra("name", pokemonName.getText());
                    //text including the decimal and measurement
                    detailsIntent.putExtra("heightText", pokemonHeight.getText());
                    detailsIntent.putExtra("weightText", pokemonWeight.getText());
                    //Integer of the pokemon - for the database
                    detailsIntent.putExtra("height", pokemonDataArrayList.get(position).height);
                    detailsIntent.putExtra("weight", pokemonDataArrayList.get(position).weight);
                    detailsIntent.putExtra("smallImage", pokemonDataArrayList.get(position).smallIcon);
                    detailsIntent.putExtra("bigImage", pokemonDataArrayList.get(position).bigIcon);
                    detailsIntent.putExtra("id", pokemonDataArrayList.get(position).id);//how will this be effected when using database/api calls

                    //todo check if the below works
                    detailsIntent.putExtra("hpStat", pokemonDataArrayList.get(position).hpStat);
                    detailsIntent.putExtra("attackStat", pokemonDataArrayList.get(position).attackStat);
                    detailsIntent.putExtra("defenseStat", pokemonDataArrayList.get(position).defenseStat);
                    detailsIntent.putExtra("specialAttackStat", pokemonDataArrayList.get(position).specialAttackStat);
                    detailsIntent.putExtra("specialDefenseStat", pokemonDataArrayList.get(position).specialDefenseStat);
                    detailsIntent.putExtra("speedStat", pokemonDataArrayList.get(position).speedStat);
                    detailsIntent.putExtra("type1", pokemonDataArrayList.get(position).type1);
                    detailsIntent.putExtra("type2", pokemonDataArrayList.get(position).type2);

                    c.startActivity(detailsIntent);
                }
            });
        }
    }
}
