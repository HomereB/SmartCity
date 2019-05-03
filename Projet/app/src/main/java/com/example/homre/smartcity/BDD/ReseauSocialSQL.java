package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lupusanghren on 25-Apr-19.
 */

public class ReseauSocialSQL {

    public static ArrayList<ReseauSocial> selectAll(){
        ArrayList<ReseauSocial> reseauSocials = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("reseaux.php");
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    reseauSocials.add(new ReseauSocial(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return reseauSocials;
        }
        return reseauSocials;
    }

    public static ArrayList<ReseauSocial> selectByUser(String idUser){
        ArrayList<ReseauSocial> reseauSocials = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("reseaux.php?userid="+idUser);
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    reseauSocials.add(new ReseauSocial(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return reseauSocials;
        }
        return reseauSocials;
    }

    public static ReseauSocial selectById(int id){
        ArrayList<ReseauSocial> reseauSocials = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("reseaux_selectione.php?idReseau="+id);
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    reseauSocials.add(new ReseauSocial(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return reseauSocials.get(0);
        }
        return reseauSocials.get(0);
    }

    public static boolean insertReseau(String username,String ville,boolean isPublic,String nom){
        String bool = "0";
        if (isPublic){bool="1";}
        JSONArray jsonArray = BaseDeDonne.SQLQuery("insertReseauSocial.php?pseudo="+username+"&ville="+ville+"&nom="+nom+"&isPublic="+bool);
        return true;
    }

    public static boolean insertReseauUser(String username, int idReseau){
        JSONArray jsonArray = BaseDeDonne.SQLQuery("insertReseauSocialUser.php?pseudo="+username+"&idReseau="+idReseau);
        return true;
    }

}
