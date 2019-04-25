package com.example.homre.smartcity.BDD;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Lupusanghren on 22-Apr-19.
 */

public class PubliciteSQL{


    public static ArrayList<Publicite> selectAll(){
        ArrayList<Publicite> pub = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("publicite.php");
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    pub.add(new Publicite(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return pub;
        }
        return pub;
    }
}
