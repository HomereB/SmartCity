package com.example.homre.smartcity.RecyclerViewRessources;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homre.smartcity.BDD.Post;
import com.example.homre.smartcity.BDD.ReseauSocialSQL;
import com.example.homre.smartcity.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_list, parent, false);
        View_Holder_Network_Management holder = new View_Holder_Network_Management(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder_Network_Management holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.nom.setText(list.get(position));
        if(type == 0){
            View.OnClickListener blub = v -> {
                ReseauSocialSQL.deleteMember(holder.nom.getText().toString(),idReso);
            };
            holder.bouton.setOnClickListener(blub);
        }
        if(type == 1){
            View.OnClickListener blub = v -> {
                ReseauSocialSQL.insertReseauUser(holder.nom.getText().toString(),idReso);
            };
            holder.bouton.setOnClickListener(blub);
        }
    }


    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }
}
