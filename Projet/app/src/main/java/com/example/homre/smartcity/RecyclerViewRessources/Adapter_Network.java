package com.example.homre.smartcity.RecyclerViewRessources;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homre.smartcity.BDD.ReseauSocial;
import com.example.homre.smartcity.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Adapter_Network extends RecyclerView.Adapter<View_Holder_Network>{

    ArrayList<ReseauSocial> list ;
    Context context;

    public Adapter_Network(ArrayList<ReseauSocial> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder_Network onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_item_list, parent, false);
        View_Holder_Network holder = new View_Holder_Network(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder_Network holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.nom.setText(list.get(position).getNom());
        holder.proprio.setText(list.get(position).getIdOwner());
        holder.confidentialite.setText(list.get(position).getPrivacy());
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, ReseauSocial reseauSocial) {
        list.add(position, reseauSocial);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(ReseauSocial data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
