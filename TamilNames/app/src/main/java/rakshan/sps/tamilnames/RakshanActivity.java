package rakshan.sps.tamilnames;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class RakshanActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RakshanActivity";

    private AdView mAdView;
    private CardView boyClick, girlClick, sangamClick, kingClick,onthisday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

//        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1185498701006717~4117111354");
//
//
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        boyClick = (CardView) findViewById(R.id.baby_boy);
        girlClick = (CardView) findViewById(R.id.baby_girl);
        sangamClick = (CardView) findViewById(R.id.sangam_names);
        kingClick = (CardView) findViewById(R.id.king_names);
        onthisday = (CardView) findViewById(R.id.birthdatechekcer);

        boyClick.setOnClickListener(this);
        girlClick.setOnClickListener(this);
        sangamClick.setOnClickListener(this);
        kingClick.setOnClickListener(this);
        onthisday.setOnClickListener(this);

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
            case R.id.birthdatechekcer:
                i = new Intent(this, BirthdayActivity.class);
                i.putExtra("Catagory", "King");
                startActivity(i);
                break;

            default:
                break;
        }
    }

}
