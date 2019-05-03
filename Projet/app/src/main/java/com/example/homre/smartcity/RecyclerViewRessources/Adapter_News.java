package com.example.homre.smartcity.RecyclerViewRessources;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homre.smartcity.BDD.Actualite;
import com.example.homre.smartcity.R;

import java.net.URL;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Adapter_News extends RecyclerView.Adapter<View_Holder_News>{

    ArrayList<Actualite> list ;
    ArrayList<Bitmap> imgList;
    Context context;

    public Adapter_News(ArrayList<Actualite> list, ArrayList<Bitmap> bitmaps,Context context) {
        this.list = list;
        this.imgList = bitmaps;
        this.context = context;
    }

    @Override
    public View_Holder_News onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_list, parent, false);
        View_Holder_News holder = new View_Holder_News(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder_News holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.titre.setText(list.get(position).getTitre());
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy, hh");
        DateFormat dfmin = new SimpleDateFormat("mm");

        holder.date.setText(df.format(list.get(position).getDate())+"h"+dfmin.format(list.get(position).getDate())+"min");
        holder.ville.setText(list.get(position).getVille()+", ");
        holder.categorie.setText(list.get(position).getCategorie());
        holder.texte.setText(list.get(position).getTexte());
        holder.img.setImageBitmap(imgList.get(position));
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
    public void insert(int position, Actualite actu) {
        list.add(position, actu);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Actualite actu) {
        int position = list.indexOf(actu);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
