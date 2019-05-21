package com.example.homre.smartcity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.homre.smartcity.BDD.Actualite;
import com.example.homre.smartcity.BDD.ActualiteReaderDbHelper;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_create);

        //annuler
        Button cancel = findViewById(R.id.buttonCNewsCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //create
        Button add = findViewById(R.id.buttonCNewsAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    View parent = v.getRootView();
                    String titre = ((EditText)findViewById(R.id.editTextCNewsTitle)).getText().toString();
                    String ville = ((EditText)findViewById(R.id.editTextCNewsCity)).getText().toString();
                    String description = ((EditText)findViewById(R.id.editTextCNewsDescription)).getText().toString();
                    String d = ((EditText)findViewById(R.id.editTextCNewsDate)).getText().toString();
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(d);
                    ImageView img = findViewById(R.id.imageViewCNewsImage);
                    Bitmap image = ((BitmapDrawable)img.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] tab = stream.toByteArray();

                    ActualiteReaderDbHelper.insert(getApplicationContext(),titre,description,ville,d,tab);
                    Toast.makeText(getApplicationContext(),"News Correctement cree",Toast.LENGTH_LONG).show();
                    finish();
                }catch (ParseException e){
                    Log.e("smart",e.toString());
                }

            }
        });
    }

}
