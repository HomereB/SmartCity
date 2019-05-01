package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Lupusanghren on 26-Apr-19.
 */

public class Categorie {

    private int id;
    private String nom;

    public Categorie (JSONObject data){
        try{
            id=data.getInt("id");
            nom=data.getString("nom");
        }catch (JSONException e){
            Log.e("json","error parsing data : "+e.toString());
        }
    }

    public Categorie(int identifiant, String name){
        id=identifiant;
        nom=name;
    }

    public int getId(){
        return id;
    }

    public String getNom(){
        return nom;
    }
}
