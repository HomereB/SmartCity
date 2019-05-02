package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Lupusanghren on 26-Apr-19.
 */

public class PostSQL {

    public static ArrayList<Post> selectByIdReseau(int idReseau){
        ArrayList<Post> posts = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("post.php?idReseau="+idReseau);
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    posts.add(new Post(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return posts;
        }
        return posts;
    }

    public static Post selectById(int id){
        ArrayList<Post> posts = new ArrayList<>();
        JSONArray jsonArray = BaseDeDonne.SQLQuery("post.php?id="+id);
        if (jsonArray!=null){
            try{
                for (int i=0;i<jsonArray.length();i++){
                    posts.add(new Post(jsonArray.getJSONObject(i)));
                }
            }catch (JSONException e){
                Log.e("json",e.toString());
            }

            return posts.get(0);
        }
        return posts.get(0);
    }
}
