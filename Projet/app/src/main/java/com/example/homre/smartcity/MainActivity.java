package com.example.homre.smartcity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.Categorie;
import com.example.homre.smartcity.BDD.CategorieSQL;
import com.example.homre.smartcity.BDD.UserSQL;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final int MY_PERMISSIONS_REQUEST_LOCATION=0;
    static final String PREFS_NAME = "User";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
        String username = user.getString("username","Name" );
        if (username != "Name") {
            Intent i = new Intent(getApplicationContext(), NewsActivity.class);
            startActivity(i);
        } else {
            //centre d interet
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new DownloadWebpageTask().execute("");
            } else {
                //tv.setText("No network connection available.");
            }

            //ville
            if (checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not
                Log.e("smart", "pas de permission");

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                //recuperation des villes
                displayVille();
            }

            //Button valider
            Button valider = findViewById(R.id.buttonMainApply);
            valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("smart","builder");
                    // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.dialog_messageMain)
                            .setTitle(R.string.dialog_titleMain)
                            .setPositiveButton(R.string.okMain, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                                    if (networkInfo != null && networkInfo.isConnected()) {
                                        TextView nom = findViewById(R.id.editTextMainName);

                                        Spinner ville = findViewById(R.id.spinnerMainCity);

                                        RadioGroup rg = findViewById(R.id.radioGroupMainSex);
                                        int selectedId = rg.getCheckedRadioButtonId();
                                        RadioButton rb = findViewById(selectedId);

                                        TextView date = findViewById(R.id.editTextMainDate);

                                        LinearLayout ll = findViewById(R.id.listMainCategories);
                                        ArrayList<String> data = new ArrayList<>();
                                        data.add(nom.getText().toString());
                                        data.add(ville.getSelectedItem().toString());
                                        data.add(rb.getText().toString());
                                        data.add(date.getText().toString());
                                        int firstCB = ll.getChildAt(0).getId();
                                        for (int i = 0; i < ll.getChildCount(); i++) {
                                            CheckBox cb = (CheckBox) ll.getChildAt(i);
                                            if (cb.isChecked()) {
                                                data.add(Integer.toString(Integer.parseInt(cb.getText().toString().substring(0, 1))));
                                            }
                                        }
                                        Object[] azy = data.toArray();
                                        String[] tab = Arrays.copyOf(azy,
                                                azy.length,
                                                String[].class);
                                        new InsertUser().execute(tab);
                                    } else {
                                        //tv.setText("No network connection available.");
                                    }
                                }
                            })
                            .setNegativeButton(R.string.noMain, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }
    }

        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,
        int[] grantResults){
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_LOCATION: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        displayVille();

                    } else {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.

                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request.
            }
        }

        private void displayVille () {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location;

            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            long GPSLocationTime = 0;
            if (null != locationGPS) {
                GPSLocationTime = locationGPS.getTime();
            }

            long NetLocationTime = 0;

            if (null != locationNet) {
                NetLocationTime = locationNet.getTime();
            }

            if (0 < GPSLocationTime - NetLocationTime) {
                location = locationGPS;
            } else {
                location = locationNet;
            }
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            ArrayList<String> towns = new ArrayList<>();
            try {
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                //on affiches les villes
                for (Address a : addresses) {
                    towns.add(a.getLocality());
                }
                Spinner spinner = findViewById(R.id.spinnerMainCity);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, towns);
                spinner.setAdapter(dataAdapter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<Categorie>> {
            @Override
            protected ArrayList<Categorie> doInBackground(String... urls) {
                // params comes from the execute() call: params[0] is the url.
                Log.i("smart", "DoInBackground");
                ArrayList<Categorie> categories = CategorieSQL.selectAll();
                return categories;
            }

            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(ArrayList<Categorie> categories) {
                //todo Autowrite data
                LinearLayout ln = findViewById(R.id.listMainCategories);
                for (Categorie c : categories) {
                    CheckBox checkBox = new CheckBox(ln.getContext());
                    checkBox.setText(c.getId() + ". " + c.getNom());
                    ln.addView(checkBox);
                }
            }
        }

        private class InsertUser extends AsyncTask<String, Void, Boolean> {
            @Override
            protected Boolean doInBackground(String... urls) {
                // params comes from the execute() call: params[0] is the url.
                Log.i("smart", "DoInBackground");

                SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
                String username = user.getString("username", "");
                String id = urls[0];//view get
                String ville = urls[1];
                String sexe = urls[2];//view get
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sdf.parse(urls[3]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (username.equals("")) {
                    //insert
                    UserSQL.insertUser(id, ville, date, sexe);
                    for (int i = 4; i < urls.length; i++) {
                        UserSQL.insertCategoriesUser(id, Integer.parseInt(urls[i]));
                    }
                    SharedPreferences.Editor editor = user.edit();
                    editor.putString("username", id);
                    editor.putString("sexe", sexe);
                    editor.putString("ville", ville);
                    editor.putString("date", urls[3]);

                    editor.commit();

                }
                return true;
            }
            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute (Boolean success){
                if (success) {

                    Toast.makeText(getApplicationContext(), "ggwp!", (Toast.LENGTH_SHORT)).show();
                    Intent i = new Intent(getApplicationContext(), NewsActivity.class);
                    startActivity(i);
                }
            }
        }
    }



