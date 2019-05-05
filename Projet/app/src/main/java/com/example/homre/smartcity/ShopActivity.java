package com.example.homre.smartcity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.Actualite;
import com.example.homre.smartcity.BDD.ActualiteSQL;
import com.example.homre.smartcity.BDD.BaseDeDonne;
import com.example.homre.smartcity.BDD.Categorie;
import com.example.homre.smartcity.BDD.CategorieSQL;
import com.example.homre.smartcity.BDD.Commerces;
import com.example.homre.smartcity.BDD.CommercesSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_Shops;
import com.example.homre.smartcity.RecyclerViewRessources.RecyclerViewClickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShopActivity extends FragmentActivity {
    TextView tv;
    final int MY_PERMISSIONS_REQUEST_LOCATION=0;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shops);

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("all");
        } else {
            tv.setText("No network connection available.");
        }

        //event listener annuaire
        Button annuaire = (Button)findViewById(R.id.buttonAnnuaire);
        annuaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpageTask().execute("all");
                } else {
                    tv.setText("No network connection available.");
                }
            }
        });

        //event listener proches
        Button proches = (Button)findViewById(R.id.buttonProches);
        if ( checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            // Permission is not
            Log.e("smart","pas de permission");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
        }else {
            proches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        new DownloadWebpageTask().execute("orderByLocation");
                    } else {
                        tv.setText("No network connection available.");
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    Button proches = (Button)findViewById(R.id.buttonProches);
                    proches.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {

                                new DownloadWebpageTask().execute("orderByLocation");
                            } else {
                                tv.setText("No network connection available.");
                            }
                        }
                    });

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

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<Commerces>> {
        ArrayList<Bitmap> bitmaps;
        @Override
        protected ArrayList<Commerces> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            SharedPreferences user = getSharedPreferences(ProfileActivity.PREFS_NAME,0);
            String ville = user.getString("ville","Montpellier");
            String idUser = user.getString("username","Lupusanghren");
            ArrayList<Categorie> categories = CategorieSQL.selectByUser(idUser);
            ArrayList<Commerces> p = CommercesSQL.selectByVilleAndCat(ville,categories);
            bitmaps = new ArrayList<>();
            try{
                for(int j=0;j<p.size();j++){
                    URL url = new URL(BaseDeDonne.SERVEUR+p.get(j).getImg());
                    Log.e("smart",url.toString());
                    bitmaps.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
                }
            }catch (MalformedURLException e){
                Log.e("smart",e.toString());
            } catch (IOException e){
                Log.e("smart",e.toString());
            }

            if (urls[0].equals("all")){
                Log.e("smart","all");
                return p;
            }else{
                Log.e("smart","order by location");
                //recuperation des coordonnes gps du telephone
                LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

                if ( checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                    // Permission is not
                    Log.e("smart","pas de permission");
                }
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
                Log.i("smart",location.getLatitude()+"");

                //tri du p par localisation
                ArrayList<Commerces> tri = new ArrayList<>();
                ArrayList<Float> distances = new ArrayList<>();
                ArrayList<Bitmap> bits = new ArrayList<>();

                for (Commerces c : p){
                    float[] result=new float[1];
                    Location.distanceBetween(location.getLatitude(),location.getLongitude(),c.getLatitude(),c.getLongitude(),result);
                    float distance = result[0];
                    //tri des distances
                    int index=0;
                    while (index<distances.size() && distance<distances.get(index)){
                        index++;
                    }
                    distances.add(index,distance);
                    tri.add(index,c);
                    bits.add(index,bitmaps.get(p.indexOf(c)));
                }
                bitmaps=bits;
                Log.i("smart",distances.toString());
                return tri;
            }

        }



        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<Commerces> commerces) {

            Log.i("smart",""+bitmaps.size());
            RecyclerView rv = findViewById(R.id.RVshops);
            RecyclerViewClickListener listener = (v, position) -> {
                Intent i = new Intent(getApplicationContext(),SelectedShopActivity.class);
                i.putExtra("idShop",commerces.get(position).getId());
                i.putExtra("nameShop",commerces.get(position).getNom());
                i.putExtra("categoryShop",commerces.get(position).getNomCategorie());
                i.putExtra("descritpionShop",commerces.get(position).getDescription());
                i.putExtra("adressShop",commerces.get(position).getAdresse());
                i.putExtra("latitudeShop",commerces.get(position).getLatitude());
                i.putExtra("longitudeShop",commerces.get(position).getLongitude());
                i.putExtra("cityShop",commerces.get(position).getVille());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmaps.get(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                i.putExtra("imageShop",byteArray);

                startActivity(i);
            };
            Adapter_Shops adapter = new Adapter_Shops(commerces,bitmaps,getApplication(),listener);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            Log.i("smart",""+bitmaps.size());
        }
    }
}
