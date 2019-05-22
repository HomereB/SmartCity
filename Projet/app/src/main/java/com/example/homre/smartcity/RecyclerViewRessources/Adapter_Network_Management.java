package com.example.homre.smartcity.RecyclerViewRessources;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homre.smartcity.BDD.Post;
import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.R;
import com.example.homre.smartcity.SelectedNetworkActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Adapter_Network_Management  extends RecyclerView.Adapter<View_Holder_Network_Management>{

    ArrayList<String> list ;
    Context context;
    int type;
    int idReso;

    public Adapter_Network_Management(ArrayList<String> list,int type,int idReso, Context context) {
        this.list = list;
        this.context = context;
        this.type = type;
        this.idReso = idReso;
    }

    @Override
    public View_Holder_Network_Management onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item_list, parent, false);
        View_Holder_Network_Management holder = new View_Holder_Network_Management(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder_Network_Management holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.nom.setText(list.get(position));

        ArrayList<String> data = new ArrayList<String>();
        data.add(holder.nom.getText().toString());
        data.add(Integer.toString(idReso));
        data.add(Integer.toString(type));
        Object[] azy= data.toArray();
        String[] tab = Arrays.copyOf(azy,
                azy.length,
                String[].class);
        holder.boutonReject.setOnClickListener(v -> {
            new DownloadWebpageTask2().execute(tab);
        });
        holder.boutonPromote.setOnClickListener(v -> {
            new DownloadWebpageTask().execute(tab);
        });
/*            Drawable icon = context.getDrawable(R.mipmap.accept);
            icon.setBounds(0,0,64,64);
            holder.boutonPromote.setBackground(icon);*/

    }


    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            if(Integer.parseInt(urls[2])==0){
                ReseauSocialSQL.updateOwner(Integer.parseInt(urls[1]),urls[0]);
                Log.i("smart","promotion");

            }
            else {
                ReseauSocialSQL.checkRequest(Integer.parseInt(urls[1]),urls[0],true);
                Log.i("smart","requete  acceptee");

            }
            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<String> ah) {
        }
    }

    private class DownloadWebpageTask2 extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            if(Integer.parseInt(urls[2])==0){
                ReseauSocialSQL.deleteMember(urls[0],Integer.parseInt(urls[1]));
                Log.i("smart","membre suppr");
            }
            else {
                ReseauSocialSQL.checkRequest(Integer.parseInt(urls[1]),urls[0],false);
                Log.i("smart","requete non acceptee");

            }
            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<String> a) {
        }
    }
}
