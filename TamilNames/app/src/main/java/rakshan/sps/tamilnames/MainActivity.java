package rakshan.sps.tamilnames;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    RecyclerView mRecycleView;
    FirebaseDatabase mFirebase;
    DatabaseReference mDatabaseRef;
    CardView christianCardView, hinduCardView, muslimCardView, Religion;
    String[] splitedVlaue;
    Query masterQuery;

    private static int total_No_Of_Data_To_Load = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleView = findViewById(R.id.mainRecycler);
        mRecycleView.setHasFixedSize(true);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1185498701006717~4117111354");

        mAdView = findViewById(R.id.adView_namePage);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Bundle bundle = getIntent().getExtras();
        String selectedCatagory = bundle.getString("CatagoryAndAlphabetLetter");
        splitedVlaue = selectedCatagory.split(":::");

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        christianCardView = (CardView) findViewById(R.id.christian_id);
        hinduCardView = (CardView) findViewById(R.id.hindu_id);
        muslimCardView = (CardView) findViewById(R.id.muslim_id);
        Religion = (CardView) findViewById(R.id.allReligion_id);

        mFirebase = FirebaseDatabase.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        if (splitedVlaue[0].equals("Girl") || splitedVlaue[0].equals("Boy")) {
            masterQuery = mFirebase.getReference("name_list").orderByChild("sex_name").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1]);
        } else if (splitedVlaue[0].equals("Sangam")) {
            christianCardView.setVisibility(View.GONE);
            hinduCardView.setVisibility(View.GONE);
            muslimCardView.setVisibility(View.GONE);

            masterQuery = mFirebase.getReference("name_list").orderByChild("sex_name").equalTo("Sangam_" + splitedVlaue[1]);

        } else {
            hinduCardView.setVisibility(View.GONE);
            muslimCardView.setVisibility(View.GONE);
            masterQuery = mFirebase.getReference("name_list").orderByChild("sex_name").equalTo("King_" + splitedVlaue[1]);
        }
        masterQuery.keepSynced(true);


        christianCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterQuery = mFirebase.getReference("name_list").orderByChild("sex_name").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1] + "_" + "Christian");
                retriveData(masterQuery);
            }
        });
        hinduCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterQuery = mFirebase.getReference("name_list").orderByChild("sex_name_religion").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1] + "_" + "Hindu");
                retriveData(masterQuery);
            }
        });
        muslimCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterQuery = mFirebase.getReference("name_list").orderByChild("sex_name_religion").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1] + "_" + "Muslim");
                retriveData(masterQuery);
            }
        });
        Religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterQuery = mFirebase.getReference("name_list").orderByChild("sex_name").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1]);
                retriveData(masterQuery);
            }
        });

    }


    private void retriveData(Query selectedQuery) {


        selectedQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Toast.makeText(MainActivity.this,"data exists",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast toast = Toast.makeText(MainActivity.this,"No data exists",Toast.LENGTH_LONG);
                    toast.getView().setBackgroundColor(0xFF00ddff);
                    toast.getView().setMinimumWidth(200);
                    toast.getView().setMinimumHeight(70);
                    toast.show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerAdapter<DataModel, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<DataModel, ViewHolder>(
                        DataModel.class,
                        R.layout.name_list,
                        ViewHolder.class,
                        selectedQuery
                ) {

                    @Override
                    protected void populateViewHolder(ViewHolder vhVal, DataModel dataModel,
                                                      int position) {

                        vhVal.setTitleAndDescription(getApplicationContext(), dataModel.getName(), dataModel.getMeaning(), dataModel.getSex());
                    }

                };
        mRecycleView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Query startUpQuery = mFirebase.getReference("name_list").orderByChild("name").startAt(splitedVlaue[1]).endAt().orderByChild("sex").startAt(splitedVlaue[0]);

        FirebaseRecyclerAdapter<DataModel, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<DataModel, ViewHolder>(
                        DataModel.class,
                        R.layout.name_list,
                        ViewHolder.class,
                        masterQuery
                ) {

                    @Override
                    protected void populateViewHolder(ViewHolder vhVal, DataModel dataModel,
                                                      int position) {
                        vhVal.setTitleAndDescription(getApplicationContext(), dataModel.getName(), dataModel.getMeaning(), dataModel.getSex());
                    }

                };

        mRecycleView.setAdapter(firebaseRecyclerAdapter);
    }
}