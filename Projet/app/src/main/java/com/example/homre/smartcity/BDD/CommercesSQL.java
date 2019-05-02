package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Lupusanghren on 25-Apr-19.
 */

public class CommercesSQL {

    public static ArrayList<Commerces> selectByVille(String ville){
        ArrayList<Commerces> commerces = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("commerces.php?ville="+ville);
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    commerces.add(new Commerces(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return commerces;
        }
        return commerces;
    }

    public static ArrayList<Commerces> selectByVilleAndCat(String ville, ArrayList<Categorie> Cats){
        if (Cats.size()==0){
            return selectByVille(ville);
        }

        String cats = "";
        if (Cats.size()==1){
            cats="["+Cats.get(0).getId()+"]";
        }else {
            cats="[";
            for(Categorie c : Cats){
                cats+=c.getId()+",";
            }
            cats=cats.substring(0,cats.length()-1);
            cats+="]";
        }
        ArrayList<Commerces> commerces = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("commerces.php?ville="+ville+"&idCategorie="+cats);
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    commerces.add(new Commerces(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return commerces;
        }
        return commerces;
    }

    public static Commerces selectById(int id){
        ArrayList<Commerces> commerces = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("commerce_selectione?id="+id);
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    commerces.add(new Commerces(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return commerces.get(0);
        }
        return commerces.get(0);
    }
}
