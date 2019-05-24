package com.example.homre.smartcity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Network_Management;

import java.util.ArrayList;
import java.util.Arrays;

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
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.dialog_MNetworkDeleteMessage)
                    .setTitle(R.string.dialog_MNetworkDeleteTitle)
                    .setPositiveButton(R.string.okMain, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        new DownloadWebpageTask3().execute("nsm");
                    }
                }
            })
            .setNegativeButton(R.string.noMain, null);
        AlertDialog dialog = builder.create();
        dialog.show();
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
            String idUser = getSharedPreferences(PREFS_NAME,0).getString("username","");
            Log.e("smart","user"+idUser);
            members.remove(idUser);
            Log.i("smart",members.toString());
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
            Intent i = new Intent(getApplicationContext(),NetworkActivity.class);
            finish();
            startActivity(i);

            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<String> members) {
            Toast.makeText(NetworkManagementActivity.this, "Vous avez supprime le reseau", Toast.LENGTH_SHORT).show();
        }
    }
}
