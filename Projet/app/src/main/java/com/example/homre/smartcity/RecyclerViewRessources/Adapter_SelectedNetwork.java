package com.example.homre.smartcity.RecyclerViewRessources;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homre.smartcity.BDD.Commerces;
import com.example.homre.smartcity.BDD.Post;
import com.example.homre.smartcity.R;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import java.sql.Date;

public class Adapter_SelectedNetwork extends RecyclerView.Adapter<View_Holder_SelectedNetwork>{

    ArrayList<Post> list ;
    Context context;

    public Adapter_SelectedNetwork(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder_SelectedNetwork onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_list, parent, false);
        View_Holder_SelectedNetwork holder = new View_Holder_SelectedNetwork(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder_SelectedNetwork holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh");
        DateFormat dfmin = new SimpleDateFormat("mm");
        holder.nom.setText(list.get(position).getIdAuteur());
        holder.date.setText(df.format(list.get(position).getDate())+"h"+dfmin.format(list.get(position).getDate())+"min");
        holder.message.setText(list.get(position).getText());
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
    public void insert(int position, Post post){
        list.add(position,post);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Post post) {
        int position = list.indexOf(post);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
