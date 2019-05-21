package com.example.homre.smartcity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homre.smartcity.BDD.Actualite;
import com.example.homre.smartcity.BDD.ActualiteReaderDbHelper;
import com.example.homre.smartcity.BDD.ActualiteSQL;
import com.example.homre.smartcity.BDD.BaseDeDonne;
import com.example.homre.smartcity.BDD.Publicite;
import com.example.homre.smartcity.BDD.PubliciteSQL;
import com.example.homre.smartcity.RecyclerViewRessources.Adapter_News;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends FragmentActivity {


    private Button buttonAnnuaire;
    private Button buttonProxi;

    TextView tv;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        imageView = (ImageView)findViewById(R.id.imageViewNews);
        tv = (TextView) findViewById(R.id.text);

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("dsds");
        } else {
            tv.setText("No network connection available.");
        }

        //bind button ville
        Button ville = findViewById(R.id.buttonNewsVille);
        ville.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpageTask().execute("dsds");
                } else {
                    tv.setText("No network connection available.");
                }
            }
        });

        //bind button perso
        Button perso = findViewById(R.id.buttonNewsPerso);
        perso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Actualite> news = ActualiteReaderDbHelper.getAll(getApplicationContext());
                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                for (Actualite a : news){
                    byte[] tab =a.getImgData();
                    Bitmap b =BitmapFactory.decodeByteArray(tab,0,tab.length);
                    bitmaps.add(b);
                }

                //display des news
                RecyclerView rv = findViewById(R.id.RVNews);
                Adapter_News adapter = new Adapter_News(news,bitmaps,getApplication());
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });

        //bind button +
        Button create = findViewById(R.id.buttonNewsCreate);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),NewsCreationActivity.class);
                startActivity(i);
            }
        });

    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<Actualite>> {
        ArrayList<Bitmap> bitmaps;
        @Override
        protected ArrayList<Actualite> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            SharedPreferences user = getSharedPreferences(ProfileActivity.PREFS_NAME,0);
            String ville = user.getString("ville","Montpellier");
            ArrayList<Actualite> p = ActualiteSQL.selectByVille(ville);
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
            return p;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<Actualite> actus) {

            Log.i("smart",""+bitmaps.size());
            RecyclerView rv = findViewById(R.id.RVNews);
            Adapter_News adapter = new Adapter_News(actus,bitmaps,getApplication());
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //imageView.setImageBitmap(bitmaps.get(0));
            //imageView.setVisibility(View.VISIBLE);

            //tv.setText(actus.get(0).getTitre());
        }
    }
}