package com.example.homre.smartcity.RecyclerViewRessources;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.Post;
import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.NetworkManagementActivity;
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
            //rejette les invit ou supprinme l user
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setNegativeButton(R.string.noMain, null)
                    .setPositiveButton(R.string.okMain, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new DownloadWebpageTask2().execute(tab);
                        }
                    });

            //Integer.parseInt(urls[2])==0 => delete
            if (Integer.parseInt(tab[2])==0){
                //delete
                builder.setTitle("delete User ?");
                builder.setMessage(R.string.dialog_MNNetworkDeleteUser);
            }else{
                builder.setTitle("Refuser l'utilisateur ?");
                builder.setMessage(R.string.dialog_MNNetworkRejectRequest);
            }

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        holder.boutonPromote.setOnClickListener(v -> {

            // update owner ou accepte la demande
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setNegativeButton(R.string.noMain, null)
                    .setPositiveButton(R.string.okMain, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new DownloadWebpageTask().execute(tab);
                        }
                    });

            //Integer.parseInt(urls[2])==0 => delete
            if (Integer.parseInt(tab[2])==0){
                //delete
                builder.setTitle("Update Owner ?");
                builder.setMessage(R.string.dialog_MNNetworkUpdateOwner);
            }else{
                builder.setTitle("Accepter la demande ?");
                builder.setMessage(R.string.dialog_MNNetworkAcceptRequest);
            }

            AlertDialog dialog = builder.create();
            dialog.show();
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
            Toast.makeText(context,"ggwp!",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context,"ggwp!",Toast.LENGTH_SHORT).show();
        }
    }
}
