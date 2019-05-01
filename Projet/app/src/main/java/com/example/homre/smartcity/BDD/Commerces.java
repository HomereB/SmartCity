package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lupusanghren on 25-Apr-19.
 */

public class Commerces {

    private int id;
    private String nom;
    private int idCategorie;
    private String nomCategorie;
    private String description;
    private String img;
    private double longitude;
    private double latitude;
    private String adresse;
    private String ville;

    public Commerces(JSONObject data){
        try{
            id=data.getInt("id");
            nom=data.getString("nom");
            idCategorie = data.getInt("idCategorie");
            nomCategorie=data.getString("nomCategorie");
            description=data.getString("description");
            img=data.getString("img");
            longitude=data.getDouble("longitude");
            latitude=data.getDouble("latitude");
            adresse=data.getString("adresse");
            ville = data.getString("ville");
        }catch (JSONException e){
            Log.e("json","error parsing data : "+e.toString());
        }
    }

    public int getId(){
        return id;
    }

    public String getNom(){
        return nom;
    }

    public String getNomCategorie(){
        return nomCategorie;
    }

    public int getIdCategorie(){
        return idCategorie;
    }

    public String getDescription(){
        return description;
    }

    public String getImg(){
        return img;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getAdresse() {return adresse;}

    public String getVille(){return ville;}

}
