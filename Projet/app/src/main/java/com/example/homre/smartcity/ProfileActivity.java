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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.Categorie;
import com.example.homre.smartcity.BDD.CategorieSQL;
import com.example.homre.smartcity.BDD.Post;
import com.example.homre.smartcity.BDD.PostSQL;
import com.example.homre.smartcity.BDD.UserSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_SelectedNetwork;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends FragmentActivity {
    final int MY_PERMISSIONS_REQUEST_LOCATION=0;
    static final String PREFS_NAME = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //pseudo
        //TODO autowrite data
        SharedPreferences user = getSharedPreferences(PREFS_NAME,0);
        String username = user.getString("username","Name");
        EditText editText = findViewById(R.id.editTextProfileName);
        editText.setText(username);

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
                    TextView nom = findViewById(R.id.editTextProfileName);

                    Spinner ville = findViewById(R.id.spinnerProfileCity);

                    RadioGroup rg = findViewById(R.id.radioGroupProfileSex);
                    int selectedId = rg.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(selectedId);

                    TextView date = findViewById(R.id.editTextProfileDate);

                    LinearLayout ll = findViewById(R.id.listProfileCategories);
                    ArrayList<String> data = new ArrayList<>();
                    data.add(nom.getText().toString());
                    data.add(ville.getSelectedItem().toString());
                    data.add(rb.getText().toString());
                    data.add(date.getText().toString());
                    int firstCB = ll.getChildAt(0).getId();
                    for(int i =0;i<ll.getChildCount();i++)
                    {
                        CheckBox cb = (CheckBox)ll.getChildAt(i);
                        if(cb.isChecked())
                        {
                            data.add(Integer.toString(Integer.parseInt(cb.getText().toString().substring(0,1))));
                        }
                    }
                    Object[] azy= data.toArray();
                    String[] tab = Arrays.copyOf(azy,
                            azy.length,
                            String[].class);
                    new InsertUser().execute(tab);
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
            Spinner spinner = findViewById(R.id.spinnerProfileCity);
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
            //todo Autowrite data
            LinearLayout ln = findViewById(R.id.listProfileCategories);
            for (Categorie c : categories){
                CheckBox checkBox = new CheckBox(ln.getContext());
                checkBox.setText(c.getId()+". "+c.getNom());
                ln.addView(checkBox);
            }
        }
    }

    private class InsertUser extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");

            SharedPreferences user = getSharedPreferences(PREFS_NAME,0);
            String username = user.getString("username","");
            String id = urls[0];//view get
            String ville = urls[1];
            String sexe=urls[2];//view get
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(urls[3]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (username.equals("")){
                //insert
                UserSQL.insertUser(id,ville,date,sexe);
                for (int i = 4; i < urls.length ; i++){
                    UserSQL.insertCategoriesUser(id,Integer.parseInt(urls[i]));
                }
                SharedPreferences.Editor editor = user.edit();
                editor.putString("username",id);
                editor.putString("ville", ville);
                editor.putString("date",urls[3]);
                editor.commit();
            }else{
                //update
                ArrayList<Integer> categories = new ArrayList<>();
                for (int i = 4; i < urls.length ; i++){
                    //UserSQL.insertCategoriesUser(id,Integer.parseInt(urls[i]));
                    categories.add(Integer.parseInt(urls[i]));
                }
                UserSQL.updateCategoriesUser(id,categories);//TODO delete user categorie
                SharedPreferences.Editor editor = user.edit();
                editor.putString("username",id);
                editor.putString("ville", ville);
                editor.putString("date",urls[3]);
                editor.commit();
            }

            return true;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean success) {
            if (success){
                Toast.makeText(getApplicationContext(),"ggwp!",(Toast.LENGTH_SHORT));
            }
        }
    }
}
