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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.Post;
import com.example.homre.smartcity.BDD.PostSQL;
import com.example.homre.smartcity.BDD.ReseauSocial;
import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Network;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Network_Management;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_SelectedNetwork;
import com.example.homre.smartcity.RecyclerViewRessources.RecyclerViewClickListener;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class SelectedNetworkActivity extends AppCompatActivity {
    TextView tvName;
    TextView tvOwner;
    TextView tvPrivacy;

    int id;

    static final String PREFS_NAME = "User";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_network);
        tvName = findViewById(R.id.textViewSNetworkName);
        tvOwner = findViewById(R.id.textViewSNetworkOwner);
        tvPrivacy = findViewById(R.id.textViewSNetworkPrivacy);
        Intent i = getIntent();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("dsds");
        } else {
            tvName.setText("No network connection available.");
        }

        tvName.setText(i.getStringExtra("nameNetwork"));
        tvOwner.setText(i.getStringExtra("ownerNetwork"));
        tvPrivacy.setText(i.getStringExtra("privacyNetwork"));
        id = i.getIntExtra("idNetwork", -1);

        SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
        String username = user.getString("username", "Name");
        new DownloadWebpageTask2().execute(tvName.getText().toString());
        Button rejoindre = findViewById(R.id.ButtonSNetworkManage);

        rejoindre.setOnClickListener(v -> {
            ArrayList<String> data = new ArrayList<String>();
            data.add(tvOwner.getText().toString());
            data.add(tvPrivacy.getText().toString());
            Object[] azy= data.toArray();
            String[] tab = Arrays.copyOf(azy,
                    azy.length,
                    String[].class);
            new DownloadWebpageTask3().execute(tab);

        });

        //Button envoyer
        Button envoyer = findViewById(R.id.buttonSNetworkSend);
        envoyer.setOnClickListener(v -> {
            TextView textView = findViewById(R.id.editTextSNetwork);
            String msg = textView.getText().toString();
            String[] tab = { msg, Integer.toString(id)};
            new InsertPost().execute(tab);
        });

    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<Post>> {
        @Override
        protected ArrayList<Post> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart", "DoInBackground");
            ArrayList<Post> posts = PostSQL.selectByIdReseau(id);
            return posts;
        }

        protected void onPostExecute(ArrayList<Post> posts) {

            RecyclerView rv = findViewById(R.id.RVSNetwork);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(llm);
            Adapter_SelectedNetwork adapter = new Adapter_SelectedNetwork(posts, getApplication());
            rv.setAdapter(adapter);
        }
    };

    private class InsertPost extends AsyncTask<String, Void, Boolean> {
        @Override
            protected Boolean doInBackground(String... urls){
                Log.i("smart", "DoInBackground");

            SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
            String idUser = user.getString("username","");
            String msg = urls[0];
            int idNetwork=Integer.parseInt(urls[1]);
            DateFormat df =new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
            Date date = new Date();

            try {
                PostSQL.insertPost(idUser, df.format(date), URLEncoder.encode(msg,"UTF-8"),idNetwork);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return true;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute (Boolean success){
            if (success) {

                Toast.makeText(getApplicationContext(), "Message envoyé!", (Toast.LENGTH_SHORT)).show();

            }
        }
    }

    private class DownloadWebpageTask2 extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            int blub;
            SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
            String username = user.getString("username", "Name");
            Log.i("smart",username);
            Log.i("smart",urls[0]);
            Log.i("smart",Boolean.toString(urls[0].equals(username)));
            if(username.equals(urls[0]))
            {
                blub=0;
            }
            else{
                if(ReseauSocialSQL.getUsersFromReseaux(id).contains(username))
                {
                    blub=1;
                }
                else
                {
                    blub=2;
                }
            }            Log.e("smart","all");

            return blub;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Integer res) {
            //Button manage
            Button rejoindre = findViewById(R.id.ButtonSNetworkManage);

            switch(res)
            {
                case 0:                    rejoindre.setText("Gerer");break;

                case 1:                    rejoindre.setText("Quitter");break;

                case 2:                    rejoindre.setText("Rejoindre");break;

            }
        }
    }


    private class DownloadWebpageTask3 extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            int blub=-1;
            SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
            String username = user.getString("username", "Name");
            if(username.equals(urls[0]))
             {
                    Intent j = new Intent(getApplicationContext(),NetworkManagementActivity.class);
                    j.putExtra("idNetwork", id);
                    j.putExtra("nameNetwork",urls[0]);
                    startActivity(j);
                }
                else{
                    if(ReseauSocialSQL.getUsersFromReseaux(id).contains(username))
                    {
                        ReseauSocialSQL.deleteMember(username,id);
                        blub = 0;
                        finish();
                    }
                    else
                    {
                        if(urls[1].equals( getResources().getString(R.string.txtNetworkPublic)))
                        {
                            ReseauSocialSQL.insertReseauUser(username,id);
                            blub = 1;

                        }
                        else
                        {
                            ReseauSocialSQL.requestReseauSocial(id,username);
                            blub = 2;
                        }
                    }
                }
            return blub;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Integer res) {
            //Button manage
            Button rejoindre = findViewById(R.id.ButtonSNetworkManage);

            switch(res)
            {
                case 0:
                    Toast.makeText(getApplicationContext(), "Vous avez quite le reseau", Toast.LENGTH_SHORT).show();;break;

                case 1:
                    Toast.makeText(getApplicationContext(), "Vous avez rejoint le reseau", Toast.LENGTH_SHORT).show();;break;

                case 2:
                    Toast.makeText(getApplicationContext(), "requete envoyee", Toast.LENGTH_SHORT).show();;break;

            }
        }
    }

}
