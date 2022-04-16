package com.example.pokemonapp;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.ArrayList;

@Entity
public class PokemonData {
    @PrimaryKey(autoGenerate = true)
    int databaseID;

    int id;//pokemon id
    String smallIcon;
    String bigIcon;
    String name;
    int height;
    int weight;

    //there's a max of 2 types per pokemon
    String type1;
    String type2;
    //there's always 6 stats: hp, attack, defense, special-attack, special-defense, speed in that order
    int hpStat;
    int attackStat;
    int defenseStat;
    int specialAttackStat;
    int specialDefenseStat;
    int speedStat;

    public PokemonData(){
        id = 0;
        smallIcon = "";
        bigIcon = "";
        name = "";
        height = 0;
        weight = 0;

        type1 = "";
        type2 = "";

        hpStat = 0;
        attackStat = 0;
        defenseStat = 0;
        specialAttackStat = 0;
        specialDefenseStat = 0;
        speedStat = 0;
    }

    public PokemonData(int id, String smallIcon, String bigIcon, String name, int height, int weight, String type1, String type2, int hpStat, int attackStat, int defenseStat,
                       int specialAttackStat, int specialDefenseStat, int speedStat){
        this.id = id;
        this.smallIcon = smallIcon;
        this.bigIcon = bigIcon;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.type1 = type1;
        this.type2 = type2;
        this.hpStat = hpStat;
        this.attackStat = attackStat;
        this.defenseStat = defenseStat;
        this.specialAttackStat = specialAttackStat;
        this.specialDefenseStat = specialDefenseStat;
        this.speedStat = speedStat;
    }

    @Override
    public String toString() {
        return "PokemonData{" +
                "databaseID=" + databaseID +
                ", id=" + id +
                ", smallIcon='" + smallIcon + '\'' +
                ", bigIcon='" + bigIcon + '\'' +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", hpStat=" + hpStat +
                ", attackStat=" + attackStat +
                ", defenseStat=" + defenseStat +
                ", specialAttackStat=" + specialAttackStat +
                ", specialDefenseStat=" + specialDefenseStat +
                ", speedStat=" + speedStat +
                '}';
    }
}
