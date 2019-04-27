package com.example.homre.smartcity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homre.smartcity.BDD.BaseDeDonne;
import com.example.homre.smartcity.BDD.Publicite;
import com.example.homre.smartcity.BDD.PubliciteSQL;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PubActivity extends FragmentActivity {


    private Button buttonAnnuaire;
    private Button buttonProxi;

    TextView tv;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adds);

        imageView = (ImageView)findViewById(R.id.imageViewAdds);
        tv = (TextView) findViewById(R.id.textAdds);

        FrameLayout frame = new FrameLayout(this);
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        Fragment mainFragment= new NavigationFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.add(android.R.id.content, mainFragment).commit();


        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute("dsds");
        } else {
            tv.setText("No network connection available.");
        }

    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<Publicite>> {
        ArrayList<Bitmap> bitmaps;
        @Override
        protected ArrayList<Publicite> doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            Log.i("smart","DoInBackground");
            ArrayList<Publicite> p = PubliciteSQL.selectAll();
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
        protected void onPostExecute(ArrayList<Publicite> pubs) {

            //TODO Faire l'affichage
            Log.i("smart",""+bitmaps.get(0).getByteCount());
            Log.i("smart",""+bitmaps.size());
            imageView.setImageBitmap(bitmaps.get(0));
            imageView.setVisibility(View.VISIBLE);

            tv.setText(pubs.get(0).getNomCommerce());
        }
    }
}
