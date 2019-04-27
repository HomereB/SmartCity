package com.example.homre.smartcity.RecyclerViewRessources;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homre.smartcity.R;

import org.w3c.dom.Text;

import java.util.Date;

public class View_Holder_News extends RecyclerView.ViewHolder {

    CardView cv;
    TextView titre;
    TextView date;
    TextView categorie;
    TextView ville;
    TextView texte;
    ImageView img;

    View_Holder_News(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardViewNews);
        titre = (TextView) itemView.findViewById(R.id.textViewNewsTitle);
        date = (TextView) itemView.findViewById(R.id.textViewNewsDate);
        categorie = (TextView) itemView.findViewById(R.id.textViewNewsCategory);
        ville = (TextView) itemView.findViewById(R.id.textViewNewsCity);
        texte = (TextView) itemView.findViewById(R.id.textViewNewsText);
        img = (ImageView) itemView.findViewById(R.id.imageViewNews);
    }
}