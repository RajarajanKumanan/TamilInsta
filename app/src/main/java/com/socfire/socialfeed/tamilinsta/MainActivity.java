package com.socfire.socialfeed.tamilinsta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Toast toastMessage;
    private AdView mAdView;
    private CardView boyClick, girlClick, sangamClick, kingClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);


        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        boyClick = (CardView) findViewById(R.id.baby_boy);
        girlClick = (CardView) findViewById(R.id.baby_girl);
        sangamClick = (CardView) findViewById(R.id.sangam_names);
        kingClick = (CardView) findViewById(R.id.king_names);

        boyClick.setOnClickListener(this);
        girlClick.setOnClickListener(this);
        sangamClick.setOnClickListener(this);
        kingClick.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.baby_boy:

                i = new Intent(this, NameListActivity.class);
                i.putExtra("Catagory", "Boy");
                startActivity(i);
                break;
            case R.id.baby_girl:
                i = new Intent(this, NameListActivity.class);
                i.putExtra("Catagory", "Girl");
                startActivity(i);
                break;
            case R.id.sangam_names:

                i = new Intent(this, NameListActivity.class);
                i.putExtra("Catagory", "Sangam");
                startActivity(i);
                break;
            case R.id.king_names:

                i = new Intent(this, NameListActivity.class);
                i.putExtra("Catagory", "King");
                startActivity(i);
                break;
            default:
                break;
        }
    }


    private void showToast(String str) {
        toastMessage.makeText(this, str, Toast.LENGTH_LONG).show();
    }

}
