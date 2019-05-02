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
    public static final String HOMME="Homme";
    public static final String Femme="Femme";
    private ArrayList<Categorie> categories;

    public User(String identifiant, Date d, String s){
        id=identifiant;
        date=d;
        sexe=s;
        categories=new ArrayList<>();
    }

    public String getId(){
        return id;
    }

    public Date getDate(){
        return date;
    }

    public  String getSexe(){
        return sexe;
    }
}
