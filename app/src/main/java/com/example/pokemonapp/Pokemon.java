package com.example.pokemonapp;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

public class Pokemon {

        int id;//pokemon id
        String smallIcon;
        String bigIcon;
        String name;
        int height;
        int weight;


        ArrayList<String> types;
        ArrayList<StatModel> stats;

        public Pokemon(){
            id = 0;
            smallIcon = "";
            bigIcon = "";
            name = "";
            height = 0;
            weight = 0;
            types = new ArrayList<String>();
            stats = new ArrayList<StatModel>();
        }

        public Pokemon(int id, String smallIcon, String bigIcon, String name, int height, int weight, ArrayList<String> types, ArrayList<StatModel> stats){
            this.id = id;
            this.smallIcon = smallIcon;
            this.bigIcon = bigIcon;
            this.name = name;
            this.height = height;
            this.weight = weight;
            this.types = types;
            this.stats = stats;
        }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", smallIcon='" + smallIcon + '\'' +
                ", bigIcon='" + bigIcon + '\'' +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", types=" + types +
                ", stats=" + stats +
                '}';
    }
}


