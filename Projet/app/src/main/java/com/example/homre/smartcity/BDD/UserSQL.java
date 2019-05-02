package com.example.homre.smartcity.BDD;

import android.util.Log;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserSQL {

    public static boolean insertUser(User u){
        return insertUser(u.getId(),u.getDate(),u.getSexe());
    }

    public static boolean insertUser(String id, Date d, String sexe){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date=format.format(d);
        JSONArray jsonArray = BaseDeDonne.SQLQuery("insertUser.php?pseudo="+id+"&sexe="+sexe+"&date="+date);
        Log.i("smart",jsonArray.length()+"");
        return true;
    }

    public static boolean insertCategoriesUser(String id, int idCat){
        JSONArray jsonArray = BaseDeDonne.SQLQuery("insertUserCategorie.php?idUser="+id+"&idCategorie"+idCat);
        Log.i("smart",jsonArray.length()+"");
        return true;
    }
}
