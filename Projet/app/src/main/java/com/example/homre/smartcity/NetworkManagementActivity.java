package com.example.homre.smartcity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Network_Management;

import java.util.ArrayList;

public class NetworkManagementActivity extends AppCompatActivity {
    static final String PREFS_NAME = "User";

    private TextView name;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_network);
        name = findViewById(R.id.textViewMNetworkName);
        Intent i = getIntent();
        id = i.getIntExtra("idNetwork", -1);
        name.setText(i.getStringExtra("nameNetwork"));
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("dsds");
            new DownloadWebpageTask2().execute("dsds");

        }

        //event listener annuaire
        Button supprNetwork = findViewById(R.id.buttonMNetworkDelete);
        supprNetwork.setOnClickListener(v ->{

        new DownloadWebpageTask3().execute("ntm");
        Intent j = new Intent(this,NetworkActivity.class);
        startActivity(j);
    });



    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            ArrayList<String> members;

            Log.e("smart","all");
            members = ReseauSocialSQL.getUsersFromReseaux(id);
            return members;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<String> members) {
            int i = 0;
            RecyclerView rv = findViewById(R.id.RVMNetworkMembers);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(llm);
            Adapter_Network_Management adapter = new Adapter_Network_Management(members,i,id,getApplicationContext());
            rv.setAdapter(adapter);
        }
    }

    private class DownloadWebpageTask2 extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            ArrayList<String> members;
            Log.e("smart","all");
            members = ReseauSocialSQL.getRequestFromReseaux(id);
            SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
            String username = user.getString("username","Name" );
            members.remove(username);
            return members;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<String> members) {
            int i = 1;
            RecyclerView rv = findViewById(R.id.RVMNetworkPendingMembers);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(llm);
            Adapter_Network_Management adapter = new Adapter_Network_Management(members,i,id,getApplicationContext());
            rv.setAdapter(adapter);
        }
    }

    private class DownloadWebpageTask3 extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            ReseauSocialSQL.deleteReseau(id);

            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<String> members) {
        }
    }
}
