package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lupusanghren on 26-Apr-19.
 */

public class Post {

    private int id;
    private String idAuteur;
    private String titre;
    private String text;
    private int idReseau;

    public Post(JSONObject data){
        try{
            id=data.getInt("id");
            idAuteur=data.getString("idAuteur");
            titre=data.getString("titre");
            text=data.getString("text");
            id=data.getInt("idReseauSocial");
        }catch (JSONException e){
            Log.e("json","error parsing data : "+e.toString());
        }
    }

    public int getId(){
        return id;
    }

    public String getIdAuteur(){
        return idAuteur;
    }

    public String getTitre(){
        return titre;
    }

    public String getText(){
        return text;
    }

    public int getIdReseau(){
        return idReseau;
    }
}
