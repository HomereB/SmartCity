package com.example.homre.smartcity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.homre.smartcity.BDD.Post;
import com.example.homre.smartcity.BDD.PostSQL;
import com.example.homre.smartcity.BDD.ReseauSocial;
import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Network;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_SelectedNetwork;
import com.example.homre.smartcity.RecyclerViewRessources.RecyclerViewClickListener;

import java.util.ArrayList;

public class SelectedNetworkActivity extends AppCompatActivity{
    TextView tvName;
    TextView tvOwner;
    TextView tvPrivacy;
    int id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_network);
        tvName = findViewById(R.id.textViewSNetworkName);
        tvOwner = findViewById(R.id.textViewSNetworkOwner);
        tvPrivacy = findViewById(R.id.textViewSNetworkPrivacy);
        Intent i = getIntent();
        tvName.setText(i.getStringExtra("nameNetwork"));
        tvOwner.setText(i.getStringExtra("ownerNetwork"));
        tvPrivacy.setText(i.getStringExtra("privacyNetwork"));
        id = i.getIntExtra("idNetwork",-1);
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new SelectedNetworkActivity.DownloadWebpageTask().execute("dsds");
        }
        else {
                tv.setText("No network connection available.");
            }



    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<Post>> {
        @Override
        protected ArrayList<Post> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            //TODO choix des reseaux de l utilisateur
            //TODO all
            ArrayList<Post> posts = PostSQL.selectByIdReseau(id);
            return posts;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<Post> posts) {

            RecyclerView rv = findViewById(R.id.RVSNetwork);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(llm);
            Adapter_SelectedNetwork adapter = new Adapter_SelectedNetwork(posts,getApplication());
            rv.setAdapter(adapter);
        }
    }
}
