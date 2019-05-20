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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Network_Management;

import java.util.ArrayList;

public class NetworkManagementActivity extends AppCompatActivity {

    private TextView name;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = findViewById(R.id.textViewMNetworkName);
        Intent i = getIntent();
        name.setText(i.getStringExtra("nameNetwork"));
        id = i.getIntExtra("idNetwork", -1);
        setContentView(R.layout.management_network);
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("dsds");
            new DownloadWebpageTask2().execute("dsds");

        }

        //event listener annuaire
        Button supprNetwork = (Button)findViewById(R.id.buttonNetworkAll);
        supprNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReseauSocialSQL.deleteReseau(id);
            }
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
            members = ReseauSocialSQL.getUsersFromReseaux(id);
            return members;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<String> members) {
            int i = 1;

            RecyclerView rv = findViewById(R.id.RVMNetworkMembers);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(llm);
            Adapter_Network_Management adapter = new Adapter_Network_Management(members,i,id,getApplicationContext());
            rv.setAdapter(adapter);
        }
    }

}
