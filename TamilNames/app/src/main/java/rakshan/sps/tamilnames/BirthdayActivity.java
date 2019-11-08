package rakshan.sps.tamilnames;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.view.View;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BirthdayActivity extends AppCompatActivity {
    private AdView mAdView;
    DatePicker picker;
    Button btnGet;
    RecyclerView mRecycleView;
    FirebaseDatabase mFirebaseDatabase;
    Query masterQuery;
    ProgressBarHandler mProgressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday_layout);

        mRecycleView = findViewById(R.id.birth_recycler);
        mRecycleView.setHasFixedSize(true);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1185498701006717~4117111354");
        mProgressBarHandler = new ProgressBarHandler(this); // In onCreate

        mAdView = findViewById(R.id.birthAds);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        picker = (DatePicker) findViewById(R.id.datePicker1);
        btnGet = (Button) findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedBirthday(picker.getDayOfMonth() + "_" + (picker.getMonth() + 1));
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecycleView.setLayoutManager(mLayoutManager);
        Log.i("Date", picker.getDayOfMonth() + "_" + (picker.getMonth() + 1));
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        masterQuery = mFirebaseDatabase.getReference("Birthday").child("Nutral").orderByChild("sex_name").equalTo(picker.getDayOfMonth() + "_" + (picker.getMonth() + 1));
        mProgressBarHandler.show();
        masterQuery.keepSynced(true);

    }

    public void getSelectedBirthday(String conString) {
        mProgressBarHandler.show();
        masterQuery = mFirebaseDatabase.getReference("Birthday").child("Nutral").orderByChild("sex_name").equalTo(conString);

        masterQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mProgressBarHandler.hide();
                }
                else{
                    Toast toast = Toast.makeText(BirthdayActivity.this,"No data exists",Toast.LENGTH_LONG);
                    toast.getView().setBackgroundColor(Color.RED);
                    toast.getView().setMinimumWidth(200);
                    toast.getView().setMinimumHeight(70);
                    toast.show();
                    mProgressBarHandler.hide();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerAdapter<DataModel, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<DataModel, ViewHolder>(
                        DataModel.class,
                        R.layout.cardview_birthday,
                        ViewHolder.class,
                        masterQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder vhVal, DataModel dataModel,
                                                      int position) {
                        vhVal.setBirthDayDetails(getApplicationContext(), dataModel.getName(), dataModel.getMeaning(), dataModel.getSex());
                    }
                };
        mRecycleView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        masterQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mProgressBarHandler.hide();
                }
                else{
                    Toast toast = Toast.makeText(BirthdayActivity.this,"No data exists",Toast.LENGTH_LONG);
                    toast.getView().setBackgroundColor(Color.RED);
                    toast.getView().setMinimumWidth(200);
                    toast.getView().setMinimumHeight(70);
                    toast.show();
                    mProgressBarHandler.hide();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerAdapter<DataModel, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<DataModel, ViewHolder>(
                        DataModel.class,
                        R.layout.cardview_birthday,
                        ViewHolder.class,
                        masterQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder vhVal, DataModel dataModel,
                                                      int position) {
                        vhVal.setBirthDayDetails(getApplicationContext(), dataModel.getName(), dataModel.getMeaning(), dataModel.getSex());
                    }
                };

        mRecycleView.setAdapter(firebaseRecyclerAdapter);
    }

}
