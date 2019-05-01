package com.example.homre.smartcity.RecyclerViewRessources;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homre.smartcity.R;

public class View_Holder_SelectedNetwork extends RecyclerView.ViewHolder {

    CardView cv;
    TextView nom;
    TextView date;
    TextView message;

    View_Holder_SelectedNetwork(View view) {
        super(view);
        cv = (CardView) itemView.findViewById(R.id.cardViewPost);
        nom = (TextView) itemView.findViewById(R.id.textViewPostName);
        date = (TextView) itemView.findViewById(R.id.textViewPostDate);
        message = (TextView) itemView.findViewById(R.id.textViewPostMessage);
    }


}