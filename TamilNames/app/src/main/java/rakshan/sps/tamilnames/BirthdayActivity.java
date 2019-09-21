package rakshan.sps.tamilnames;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.DatePicker;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class BirthdayActivity extends AppCompatActivity {
    private AdView mAdView;
    DatePicker picker;
    Button btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday_layout);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1185498701006717~4117111354");

        mAdView = findViewById(R.id.birthAds);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        picker = (DatePicker) findViewById(R.id.datePicker1);
        btnGet = (Button) findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvw.setText("Selected Date: "+ picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());
                Toast.makeText(BirthdayActivity.this, " " + picker.getDayOfMonth() + "", Toast.LENGTH_LONG).show();
            }
        });


    }
}
