package com.example.homre.smartcity.RecyclerViewRessources;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homre.smartcity.R;

public class View_Holder_Network extends RecyclerView.ViewHolder implements  View.OnClickListener {

    CardView cv;
    TextView nom;
    TextView proprio;
    TextView confidentialite;
    RecyclerViewClickListener rvcl;

    View_Holder_Network(View view, RecyclerViewClickListener listener) {
        super(view);
        cv = (CardView) itemView.findViewById(R.id.cardViewNetwork);
        nom = (TextView) itemView.findViewById(R.id.textViewNetworkName);
        proprio = (TextView) itemView.findViewById(R.id.textViewNetworkOwner);
        confidentialite = (TextView) itemView.findViewById(R.id.textViewNetworkStatus);
        rvcl = listener;
            view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        rvcl.onClick(view,getAdapterPosition());
    }


}