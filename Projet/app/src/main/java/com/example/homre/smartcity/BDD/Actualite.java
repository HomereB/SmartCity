package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lupusanghren on 25-Apr-19.
 */

public class Actualite {

    private int id;
    private String ville;
    private Date date;
    private String titre;
    private String texte;
    private String img;
    private String categorie;

    public Actualite(JSONObject data){
        try{
            id=data.getInt("id");
            ville=data.getString("ville");
            img=data.getString("img");
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(data.getString("date"));
            titre=data.getString("titre");
            texte=data.getString("description");
            categorie = data.getString("nomCategorie");
        }catch (JSONException e){
            Log.e("json","error parsing data : "+e.toString());
        }catch (ParseException e){
            Log.e("json","error parsing data : "+e.toString());
        }
    }

    public int getId(){
        return id;
    }

    public String getVille(){
        return ville;
    }

    public Date getDate(){
        return date;
    }

    public String getTitre(){
        return titre;
    }

    public String getTexte(){
        return texte;
    }

    public String getImg(){
        return img;
    }

    public String getCategorie() {return categorie;}
}
