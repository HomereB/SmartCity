package com.example.homre.smartcity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.Categorie;
import com.example.homre.smartcity.BDD.CategorieSQL;
import com.example.homre.smartcity.BDD.Post;
import com.example.homre.smartcity.BDD.PostSQL;
import com.example.homre.smartcity.BDD.UserSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_SelectedNetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends FragmentActivity {
    final int MY_PERMISSIONS_REQUEST_LOCATION=0;
    public static final String PREFS_NAME = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //centre d interet
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("");
        } else {
            //tv.setText("No network connection available.");
        }

        //ville
        if ( checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            // Permission is not
            Log.e("smart","pas de permission");

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
        }else {
            //recuperation des villes
            displayVille();
        }

        //Button valider
        Button valider = findViewById(R.id.buttonProfileApply);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new InsertUser().execute(v);
                } else {
                    //tv.setText("No network connection available.");
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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

    private void displayVille(){
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location;

        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            location= locationGPS;
        }
        else {
            location= locationNet;
        }
        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        ArrayList<String> towns=new ArrayList<>();
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            //on affiches les villes
            for (Address a : addresses){
                towns.add(a.getLocality());
            }
            //addresses.get(0).getLocality();
            Spinner spinner = findViewById(R.id.spinner);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, towns);
            //SpinnerAdapter adapter = ArrayAdapter.createFromResource(this,towns, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(dataAdapter);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<Categorie>> {
        @Override
        protected ArrayList<Categorie> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            ArrayList<Categorie> categories = CategorieSQL.selectAll();
            return categories;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<Categorie> categories) {
            LinearLayout ln = findViewById(R.id.list_check);
            for (Categorie c : categories){
                CheckBox checkBox = new CheckBox(ln.getContext());
                checkBox.setText(c.getNom());
                checkBox.setId(c.getId());
                ln.addView(checkBox);
            }
        }
    }

    private class InsertUser extends AsyncTask<View, Void, Boolean> {
        @Override
        protected Boolean doInBackground(View... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            //TODO insert user
            String id ="Lupusanghren";//view get
            String sexe="Homme";//view get
            Date date = new Date();
            UserSQL.insertUser(id,date,sexe);
            //TODO insert userCategories
            ArrayList<Categorie> categories = new ArrayList<>();
            //view get
            for (Categorie c :categories){
                UserSQL.insertCategoriesUser(id,c.getId());
            }
            //TODO stcoker var user
            SharedPreferences user = getSharedPreferences(PREFS_NAME,0);
            SharedPreferences.Editor editor = user.edit();
            editor.putString("username",id);
            //editor.putString(JsonWriter js)
            //ArrayList<Categorie> categories = CategorieSQL.selectAll();
            return true;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean success) {
            if (success){
                //Toast.makeText()
            }
        }
    }
}
