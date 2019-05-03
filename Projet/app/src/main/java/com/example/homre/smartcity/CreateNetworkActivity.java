package com.example.homre.smartcity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.ReseauSocialSQL;

public class CreateNetworkActivity extends AppCompatActivity {
    static final String PREFS_NAME = "User";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_network);
        Log.i("smart","ntm");
        SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
        String username = user.getString("username","Name" );
        String city = user.getString("ville","Name" );
        Log.i("smart","ntm");

        //Button valider
        Button valider = findViewById(R.id.buttonCNetworkConfirm);
        valider.setOnClickListener(v -> {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                TextView nom = findViewById(R.id.editTextCNetworkInputName);

                RadioGroup rg = findViewById(R.id.radioGroupCNetworkPrivacy);
                int selectedId = rg.getCheckedRadioButtonId();
                RadioButton rb = findViewById(selectedId);

                String[] tab= {username,city,rb.getText().toString(), nom.getText().toString()};
                Log.i("laaaaaaaaaaa",tab[0]+" " +tab[1]+" "+tab[2]+" "+tab[3]);
                new InsertNetwork().execute(tab);
            } else {
                //tv.setText("No network connection available.");
            }
        });

        }

    private class InsertNetwork extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart", "DoInBackground");

            SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
            String username = user.getString("username", "");
            String id = urls[0];//view get
            String ville = urls[1];
            Boolean confi = true;
            if(urls[2]==getResources().getString(R.string.txtNetworkPrivate))
            {
                confi = false;
            }

            String nom = urls[3];//view get
            ReseauSocialSQL.insertReseau(id, ville, confi,nom);
            return true;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute (Boolean success){
            if (success) {

                Toast.makeText(getApplicationContext(), "ggwp!", (Toast.LENGTH_SHORT)).show();
                Intent i = new Intent(getApplicationContext(), NetworkActivity.class);
                startActivity(i);
            }
        }
    }
}
