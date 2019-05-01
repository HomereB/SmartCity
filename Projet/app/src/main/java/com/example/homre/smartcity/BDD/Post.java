package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lupusanghren on 26-Apr-19.
 */

public class Post {

    private int id;
    private String idAuteur;
    private Date date;
    private String text;
    private int idReseau;

    public Post(JSONObject data){
        try{
            id=data.getInt("id");
            idAuteur=data.getString("idAuteur");
            date = new SimpleDateFormat("yyyy-MM-dd").parse(data.getString("date"));
            text=data.getString("text");
            id=data.getInt("idReseauSocial");
        }catch (JSONException e){
            Log.e("json","error parsing data : "+e.toString());
        }catch (ParseException e){
            Log.e("json","error parsing data : "+e.toString());
        }
    }

    public int getId(){
        return id;
    }

    public String getIdAuteur(){
        return idAuteur;
    }

    public Date getDate(){
        return date;
    }

    public String getText(){
        return text;
    }

    public int getIdReseau(){
        return idReseau;
    }
}
