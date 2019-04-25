package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lupusanghren on 25-Apr-19.
 */

public class ActualiteSQL {

    public static ArrayList<Actualite> selectByVille(String ville){
        ArrayList<Actualite> actualites = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("actus.php?ville="+ville);
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    actualites.add(new Actualite(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return actualites;
        }
        return actualites;
    }

    public static Actualite selectById(int id){
        ArrayList<Actualite> actualites = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("actu_selectionne?id="+id);
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    actualites.add(new Actualite(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return actualites.get(0);
        }
        return actualites.get(0);
    }

}
