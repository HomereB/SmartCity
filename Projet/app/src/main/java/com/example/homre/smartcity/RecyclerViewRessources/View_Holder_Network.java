package com.example.homre.smartcity.RecyclerViewRessources;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homre.smartcity.R;

public class View_Holder_Network extends RecyclerView.ViewHolder {

    CardView cv;
    TextView nom;
    TextView proprio;
    TextView confidentialite;

    View_Holder_Network(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardViewNetwork);
        nom = (TextView) itemView.findViewById(R.id.textViewNetworkName);
        proprio = (TextView) itemView.findViewById(R.id.textViewNetworkOwner);
        confidentialite = (TextView) itemView.findViewById(R.id.textViewNetworkStatus);
    }
}