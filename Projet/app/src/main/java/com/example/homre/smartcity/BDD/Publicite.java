package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lupusanghren on 22-Apr-19.
 */

public class Publicite {

    private int id;
    private String url;
    private String img;
    private int idCommerce;
    private String nomCommerce;

    public Publicite(JSONObject data){
        try{
            id=data.getInt("id");
            url=data.getString("url");
            img=data.getString("img");
            idCommerce=data.getInt("idCommerce");
            nomCommerce=data.getString("nom");
        }catch (JSONException e){
            Log.e("json","error parsing data : "+e.toString());
        }

    }

    public int getId(){
        return id;
    }

    public String getUrl(){
        return url;
    }

    public String getImg(){
        return img;
    }

    public int getIdCommerce(){
        return idCommerce;
    }

    public String getNomCommerce(){
        return nomCommerce;
    }
}
