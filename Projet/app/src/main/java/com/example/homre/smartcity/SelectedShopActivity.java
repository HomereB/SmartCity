package com.example.homre.smartcity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

class SelectedShopActivity extends AppCompatActivity {

    int id;
    TextView nom;
    TextView adresse;
    TextView description;
    TextView categorie;
    TextView coordGPS;
    double latitude;
    double longitude;
    ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_shop);
        nom = findViewById(R.id.textViewSShopName);
        categorie = findViewById(R.id.textViewSShopCategory);
        adresse = findViewById(R.id.textViewSShopAdress);
        description = findViewById(R.id.textViewSShopDescription);
        coordGPS = findViewById(R.id.textViewSShopCoordinates);
        image = findViewById(R.id.imageViewSShop);

        Intent i = getIntent();
        byte[] byteArray = getIntent().getByteArrayExtra("imageShop");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        nom.setText(i.getStringExtra("nameShop"));
        categorie.setText(i.getStringExtra("categoryShop"));
        adresse.setText(i.getStringExtra("adressShop"+", "+i.getStringExtra("cityShop")));
        description.setText(i.getStringExtra("descriptionShop"));
        image.setImageBitmap(bmp);
        i.getDoubleExtra("latitudeShop",latitude);
        i.getDoubleExtra("longitudeShop",longitude);
        coordGPS.setText(latitude+", "+longitude);
    }
}
