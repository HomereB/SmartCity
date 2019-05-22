package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Lupusanghren on 25-Apr-19.
 */

public class ReseauSocial {

    private int id;
    private String nom;
    private boolean isPublic;
    private String idOwner;

    public ReseauSocial(JSONObject data){
        try{
            id=data.getInt("id");
            nom=data.getString("nom");
            isPublic=data.getString("isPublic").equals("1");
            idOwner=data.getString("idOwner");
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

    public boolean isPublic(){
        return isPublic;
    }

    public String getIdOwner(){
        return idOwner;
    }

    public String getPrivacy() {if (isPublic)
                                    return "Public";
                                return "Prive";}
}
