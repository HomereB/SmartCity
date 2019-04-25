package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lupusanghren on 22-Apr-19.
 */

public class BaseDeDonne {

    public static final String SERVEUR ="http://www.raphaelcourbier.fr/Android/";

    public static JSONArray SQLQuery(String page){
        String result ="";
        BufferedInputStream in=null;
        // Envoi de la requête avec HTTPPost
        try{
            URL url = new URL(SERVEUR+page);
            Log.i("smart",url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
        }catch(Exception e){
            Log.e("smart", "Error in http connection "+e.toString());
        }

        //Conversion de la réponse en chaine
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            in.close();

            result=sb.toString();
            Log.i("smart",result);
        }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }

        // Parsing des données JSON
        try{
            JSONArray jArray = new JSONArray(result);
            Log.i("smart",""+jArray.length());
            return jArray;
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
        }

        return null;
    }
}
