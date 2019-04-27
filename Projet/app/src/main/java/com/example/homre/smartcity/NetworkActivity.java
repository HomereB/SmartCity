package com.example.homre.smartcity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.homre.smartcity.BDD.Actualite;
import com.example.homre.smartcity.BDD.ActualiteSQL;
import com.example.homre.smartcity.BDD.BaseDeDonne;
import com.example.homre.smartcity.BDD.ReseauSocial;
import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Network;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NetworkActivity extends FragmentActivity{
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_networks);
        //TODO
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("dsds");
        } else {
            tv.setText("No network connection available.");
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<ReseauSocial>> {
        @Override
        protected ArrayList<ReseauSocial> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            //TODO choix des reseaux de l utilisateur
            //TODO all
            ArrayList<ReseauSocial> reseauSocials = ReseauSocialSQL.selectAll();
            return reseauSocials;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<ReseauSocial> reseauSocials) {

            //TODO Faire l'affichage
            Log.i("smart",""+reseauSocials.get(0).getIdOwner());
            RecyclerView rv = findViewById(R.id.RVNetwork);
            Adapter_Network adapter = new Adapter_Network(reseauSocials,getApplication());
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //imageView.setImageBitmap(bitmaps.get(0));
            //imageView.setVisibility(View.VISIBLE);

            //tv.setText(actus.get(0).getTitre());
        }
    }
}
