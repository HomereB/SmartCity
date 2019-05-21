package com.example.homre.smartcity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NavigationFragment extends Fragment {

    private Button buttonShop;
    private Button buttonNetwork;
    private Button buttonAdds;
    private Button buttonProfile;
    private Button buttonNews;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);


        buttonNews = (Button)view.findViewById(R.id.button1);
        View.OnClickListener bleb = new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),NewsActivity.class);
                startActivity(i);
            }
        };
        buttonNews.setOnClickListener(bleb);

        buttonNetwork = (Button)view.findViewById(R.id.button2);
        View.OnClickListener blub = new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),NetworkActivity.class);
                startActivity(i);
            }
        };
        buttonNetwork.setOnClickListener(blub);

        buttonShop = (Button)view.findViewById(R.id.button3);
        View.OnClickListener blab = new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),ShopActivity.class);
                startActivity(i);
            }
        };
        buttonShop.setOnClickListener(blab);

        buttonAdds = (Button)view.findViewById(R.id.button4);
        View.OnClickListener blib = new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),PubActivity.class);
                startActivity(i);
            }
        };
        buttonAdds.setOnClickListener(blib);

        buttonProfile = (Button)view.findViewById(R.id.buttonNewsVille);
        View.OnClickListener blob = new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),ProfileActivity.class);
                startActivity(i);
            }
        };
        buttonProfile.setOnClickListener(blob);

        return view; }

}
