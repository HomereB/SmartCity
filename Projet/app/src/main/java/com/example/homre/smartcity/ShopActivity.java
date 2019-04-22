package com.example.homre.smartcity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class ShopActivity extends FragmentActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shops);

            /*        FrameLayout frame = new FrameLayout(this);
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        Fragment mainFragment= new NavigationFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.add(android.R.id.content, mainFragment).commit();*/
    }
}
