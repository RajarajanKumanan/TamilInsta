package com.socfire.socialfeed.tamilinsta;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class NameListActivity extends AppCompatActivity {

    private AdView mAdView;
    static String[] boyArray = {}, girlArray = {}, sangamArray = {}, kingArray = {}, names_array = {};

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myOnClickListener = new NameListActivity.MyOnClickListener(this);
        String value = getIntent().getExtras().getString("Catagory");

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();

        boyArray = MyData.baby_boy_names_array;
        girlArray = MyData.baby_girl_names_array;
        sangamArray = MyData.sangam_names_array;
        kingArray = MyData.king_names_array;



        for (int i = 0; i < sangamArray.length; i++) {
            data.add(new DataModel(sangamArray[i]));
        }

        adapter = new CardViewAdapter(data);
        recyclerView.setAdapter(adapter);


        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {


        }


        private void addRemovedItemToList() {
            int addItemAtListPosition = 3;
            data.add(addItemAtListPosition, new DataModel(names_array[removedItems.get(0)]));
            adapter.notifyItemInserted(addItemAtListPosition);
            removedItems.remove(0);
        }

    }
}