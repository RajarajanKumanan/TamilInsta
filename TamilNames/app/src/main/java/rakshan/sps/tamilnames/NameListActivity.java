package rakshan.sps.tamilnames;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class NameListActivity extends AppCompatActivity {

    private AdView mAdView;
    Context context;

    private List<DataModel> alpha_letter;
    Intent selectedIntent;
    private String selectedCatagory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abcd_list);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1185498701006717~4117111354");

        mAdView = findViewById(R.id.adView_recycle_abcd);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        context = getApplicationContext();

        alpha_letter = new ArrayList<>();
        alpha_letter.add(new DataModel("A"));
        alpha_letter.add(new DataModel("B"));
        alpha_letter.add(new DataModel("C"));
        alpha_letter.add(new DataModel("D"));
        alpha_letter.add(new DataModel("E"));
        alpha_letter.add(new DataModel("F"));
        alpha_letter.add(new DataModel("G"));
        alpha_letter.add(new DataModel("H"));
        alpha_letter.add(new DataModel("I"));
        alpha_letter.add(new DataModel("J"));
        alpha_letter.add(new DataModel("K"));
        alpha_letter.add(new DataModel("L"));
        alpha_letter.add(new DataModel("M"));
        alpha_letter.add(new DataModel("N"));
        alpha_letter.add(new DataModel("O"));
        alpha_letter.add(new DataModel("P"));
        alpha_letter.add(new DataModel("Q"));
        alpha_letter.add(new DataModel("R"));
        alpha_letter.add(new DataModel("S"));
        alpha_letter.add(new DataModel("T"));
        alpha_letter.add(new DataModel("U"));
        alpha_letter.add(new DataModel("V"));
        alpha_letter.add(new DataModel("W"));
        alpha_letter.add(new DataModel("X"));
        alpha_letter.add(new DataModel("Y"));
        alpha_letter.add(new DataModel("Z"));


        Bundle bundle = getIntent().getExtras();
        selectedCatagory = bundle.getString("Catagory");

        RecyclerView mRV_Obj = (RecyclerView) findViewById(R.id.abcd_recycleView);
        RecyclerViewAdapter mRVA_obj = new RecyclerViewAdapter(this, alpha_letter, selectedCatagory);
        mRV_Obj.setLayoutManager(new GridLayoutManager(this, 4));
        mRV_Obj.setAdapter(mRVA_obj);
    }

}