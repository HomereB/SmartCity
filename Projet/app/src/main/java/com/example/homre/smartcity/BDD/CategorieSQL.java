package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Lupusanghren on 26-Apr-19.
 */

public class CategorieSQL {

    public static ArrayList<Categorie> selectAll(){
        ArrayList<Categorie> categories = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("categorie.php");
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    categories.add(new Categorie(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return categories;
        }
        return categories;
    }
}
