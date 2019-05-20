package com.example.homre.smartcity.RecyclerViewRessources;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homre.smartcity.R;

public class View_Holder_Network_Management extends RecyclerView.ViewHolder {

    CardView cv;
    TextView nom;
    Button boutonReject;
    Button boutonPromote;


    View_Holder_Network_Management(View view) {
        super(view);
        cv = itemView.findViewById(R.id.cardViewMember);
        nom = itemView.findViewById(R.id.textViewMemberName);
        boutonReject =  itemView.findViewById(R.id.buttonMemberBan);
        boutonPromote =  itemView.findViewById(R.id.buttonMemberBan);

    }
}
