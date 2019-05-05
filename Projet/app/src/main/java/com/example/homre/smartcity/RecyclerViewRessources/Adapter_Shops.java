package com.example.homre.smartcity.RecyclerViewRessources;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homre.smartcity.BDD.Commerces;
import com.example.homre.smartcity.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Adapter_Shops extends RecyclerView.Adapter<View_Holder_Shops>{

        ArrayList<Commerces> list ;
        ArrayList<Bitmap> imgList;
        Context context;
        RecyclerViewClickListener listener;


    public Adapter_Shops(ArrayList<Commerces> list, ArrayList<Bitmap> imgList, Context context,RecyclerViewClickListener rvcl) {
            this.list = list;
            this.imgList = imgList;
            this.context = context;
            this.listener = rvcl;
        }

        @Override
        public View_Holder_Shops onCreateViewHolder(ViewGroup parent, int viewType) {
            //Inflate the layout, initialize the View Holder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_list, parent, false);
            View_Holder_Shops holder = new View_Holder_Shops(v,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(View_Holder_Shops holder, int position) {

            //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
            holder.nom.setText(list.get(position).getNom());
            holder.categorie.setText(list.get(position).getNomCategorie());
            holder.adresse.setText(list.get(position).getAdresse()+", "+list.get(position).getVille());
            holder.coordonnees.setText(list.get(position).getLatitude()+" , "+list.get(position).getLongitude());
            holder.image.setImageBitmap(imgList.get(position));
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
        public void insert(int position, Commerces commerce,Bitmap bitmap) {
            list.add(position, commerce);
            imgList.add(position,bitmap);
            notifyItemInserted(position);
        }

        // Remove a RecyclerView item containing a specified Data object
        public void remove(Commerces commerces, Bitmap bitmap) {
            int position = list.indexOf(commerces);
            list.remove(position);
            imgList.remove(bitmap);
            notifyItemRemoved(position);
        }
}
