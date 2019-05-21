package com.example.homre.smartcity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homre.smartcity.BDD.ReseauSocial;
import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Network;
import com.example.homre.smartcity.RecyclerViewRessources.RecyclerViewClickListener;

import java.util.ArrayList;

public class NetworkActivity extends FragmentActivity{
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_networks);
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("dsds");
        } else {
            tv.setText("No network connection available.");
        }

        //event listener annuaire
        Button tous = (Button)findViewById(R.id.buttonNetworkAll);
        tous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new NetworkActivity.DownloadWebpageTask().execute("all");
                } else {
                    tv.setText("No network connection available.");
                }
            }
        });

        //event listener proches
        Button rejoints = (Button)findViewById(R.id.buttonNetworkJoined);

            rejoints.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        new NetworkActivity.DownloadWebpageTask().execute("id");
                    } else {
                        tv.setText("No network connection available.");
                    }
                }
            });

        Button crees = (Button)findViewById(R.id.buttonNetworkCreated);

        crees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    new NetworkActivity.DownloadWebpageTask().execute("id");
                } else {
                    tv.setText("No network connection available.");
                }
            }
        });


        //event listener creation
        Button creer = findViewById(R.id.buttonNetworkCreate);

        creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CreateNetworkActivity.class);
                Log.i("smart", "la c ok");
                startActivity(i);
            }
        });
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<ReseauSocial>> {
        @Override
        protected ArrayList<ReseauSocial> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            ArrayList<ReseauSocial> reseauxSociaux;
            if(urls[0].equals("all")){
                Log.e("smart","all");
                reseauxSociaux = ReseauSocialSQL.selectAll();
            }else if(urls[0].equals("id")){
                Log.e("smart","xoxo");
                SharedPreferences user = getSharedPreferences(ProfileActivity.PREFS_NAME,0);
                String id = user.getString("username","xoxo");
                reseauxSociaux = ReseauSocialSQL.selectByUser(id);
            }else
            {
                Log.e("smart","xoxo");
                SharedPreferences user = getSharedPreferences(ProfileActivity.PREFS_NAME,0);
                String id = user.getString("username","xoxo");
                reseauxSociaux = ReseauSocialSQL.selectByUser(id);
                for(ReseauSocial rs : reseauxSociaux)
                {
                    if(!rs.getIdOwner().equals(id))
                    {
                        reseauxSociaux.remove(rs);
                    }
                }
            }

            return reseauxSociaux;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<ReseauSocial> reseauxSociaux) {

            Log.i("smart",""+reseauxSociaux.size());
            RecyclerView rv = findViewById(R.id.RVNetwork);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(llm);
            RecyclerViewClickListener listener = (v, position) -> {
                Intent i = new Intent(getApplicationContext(),SelectedNetworkActivity.class);
                i.putExtra("idNetwork",reseauxSociaux.get(position).getId());
                i.putExtra("ownerNetwork",reseauxSociaux.get(position).getIdOwner());
                i.putExtra("privacyNetwork",reseauxSociaux.get(position).getPrivacy());
                i.putExtra("nameNetwork",reseauxSociaux.get(position).getNom());
                startActivity(i);
            };
            Adapter_Network adapter = new Adapter_Network(reseauxSociaux,getApplication(),listener);
            rv.setAdapter(adapter);
        }
    }
}
