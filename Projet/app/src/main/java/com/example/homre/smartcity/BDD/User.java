package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User {

    private String id;
    private Date date;
    private String sexe;
    private String ville;
    public static final String HOMME="Homme";
    public static final String Femme="Femme";
    private ArrayList<Categorie> categories;

    //TODO user
    public User(){
    }

    public String getVille(){return ville;}

    public String getId(){
        return id;
    }

    public Date getDate(){
        return date;
    }

    public  String getSexe(){
        return sexe;
    }

    public ArrayList<Categorie> getCategories(){return categories;}
}
