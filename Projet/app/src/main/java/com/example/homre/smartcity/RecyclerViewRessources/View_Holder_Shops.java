package com.example.homre.smartcity.RecyclerViewRessources;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homre.smartcity.R;

public class View_Holder_Shops extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cv;
        TextView nom;
        TextView adresse;
        TextView categorie;
        TextView coordonnees;
        ImageView image;
        RecyclerViewClickListener rvcl;

        View_Holder_Shops(View itemView,RecyclerViewClickListener listener) {
                super(itemView);
                cv = (CardView) itemView.findViewById(R.id.cardViewShop);
                nom = (TextView) itemView.findViewById(R.id.textViewShopName);
                categorie = (TextView) itemView.findViewById(R.id.textViewShopCategorie);
                adresse = (TextView) itemView.findViewById(R.id.textViewShopAdresse);
                coordonnees = (TextView) itemView.findViewById(R.id.textViewShopCoordonees);
                image = (ImageView) itemView.findViewById(R.id.imageViewShop);
                rvcl = listener;
                itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
                rvcl.onClick(view,getAdapterPosition());
        }
}