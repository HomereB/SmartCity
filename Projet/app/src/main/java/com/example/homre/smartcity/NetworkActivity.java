package com.example.homre.smartcity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.Actualite;
import com.example.homre.smartcity.BDD.ActualiteSQL;
import com.example.homre.smartcity.BDD.BaseDeDonne;
import com.example.homre.smartcity.BDD.ReseauSocial;
import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Network;
import com.example.homre.smartcity.RecyclerViewRessources.RecyclerViewClickListener;

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
            ArrayList<ReseauSocial> reseauxSociaux = ReseauSocialSQL.selectAll();
            return reseauxSociaux;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<ReseauSocial> reseauxSociaux) {

            Log.i("smart",""+reseauxSociaux.get(0).getIdOwner());
            RecyclerView rv = findViewById(R.id.RVNetwork);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(llm);
            RecyclerViewClickListener listener = (v, position) -> {
                //TODO Lancer l'activité avec param
                Intent i = new Intent(getApplicationContext(),SelectedNetworkActivity.class);
                i.putExtra("idNetwork",reseauxSociaux.get(position).getId());
                i.putExtra("ownerNetwork",reseauxSociaux.get(position).getIdOwner());
                i.putExtra("cityNetwork",reseauxSociaux.get(position).getPrivacy());
                i.putExtra("nameNetwork",reseauxSociaux.get(position).getNom());
                startActivity(i);
            };
            Adapter_Network adapter = new Adapter_Network(reseauxSociaux,getApplication(),listener);
            rv.setAdapter(adapter);
        }
    }
}
