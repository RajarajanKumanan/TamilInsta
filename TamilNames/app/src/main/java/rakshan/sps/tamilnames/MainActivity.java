package rakshan.sps.tamilnames;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    RecyclerView mRecycleView;
    FirebaseDatabase mFirebase, addNames;
    CardView christianCardView, hinduCardView, muslimCardView, Religion;
    String[] splitedVlaue;
    Query masterQuery;
    ProgressBarHandler mProgressBarHandler;
    private static int total_No_Of_Data_To_Load = 0;
    static boolean calledAlready = false;
    TextView sangamTextCaption, kingTextCaption;
    GoogleSignInAccount account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        mRecycleView = findViewById(R.id.mainRecycler);
        mRecycleView.setHasFixedSize(true);
        mProgressBarHandler = new ProgressBarHandler(this); // In onCreate
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1185498701006717~4117111354");

        mAdView = findViewById(R.id.adView_namePage);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Bundle bundle = getIntent().getExtras();
        String selectedCatagory = bundle.getString("CatagoryAndAlphabetLetter");
        splitedVlaue = selectedCatagory.split(":::");

        mRecycleView.setLayoutManager(new LinearLayoutManager(this));


        sangamTextCaption = (TextView) findViewById(R.id.sangamCaption);
        kingTextCaption = (TextView) findViewById(R.id.kingCaption);


        sangamTextCaption.setVisibility(View.GONE);
        kingTextCaption.setVisibility(View.GONE);

        christianCardView = (CardView) findViewById(R.id.christian_id);
        hinduCardView = (CardView) findViewById(R.id.hindu_id);
        muslimCardView = (CardView) findViewById(R.id.muslim_id);


        try {
            if (mFirebase == null) {
                mFirebase = FirebaseDatabase.getInstance();
                addNames = FirebaseDatabase.getInstance();
                mFirebase.setPersistenceEnabled(true);
            }
        } catch (Exception e) {
            Log.d("database Error", e.toString());
        } finally {
            Log.d("database Finally", "Done");
            mFirebase = FirebaseDatabase.getInstance();
            addNames = FirebaseDatabase.getInstance();
        }

        if (splitedVlaue[0].equals("Boy")) {
            masterQuery = mFirebase.getReference("Hindu").child(splitedVlaue[0]).orderByChild("sex_name").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1]);
        } else if (splitedVlaue[0].equals("Girl")) {
            masterQuery = mFirebase.getReference("Hindu").child(splitedVlaue[0]).orderByChild("sex_name").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1]);
        } else if (splitedVlaue[0].equals("Sangam")) {
            christianCardView.setVisibility(View.GONE);
            hinduCardView.setVisibility(View.GONE);
            muslimCardView.setVisibility(View.GONE);
            kingTextCaption.setVisibility(View.GONE);
            sangamTextCaption.setVisibility(View.VISIBLE);
            masterQuery = mFirebase.getReference("Sangam").child("Nutral").orderByChild("sex_name").equalTo("Sangam_" + splitedVlaue[1]);

        } else {
            hinduCardView.setVisibility(View.GONE);
            muslimCardView.setVisibility(View.GONE);
            christianCardView.setVisibility(View.GONE);
            sangamTextCaption.setVisibility(View.GONE);
            kingTextCaption.setVisibility(View.VISIBLE);
            masterQuery = mFirebase.getReference("King").child("Nutral").orderByChild("sex_name").equalTo("King_" + splitedVlaue[1]);
        }
        masterQuery.keepSynced(true);
        mProgressBarHandler.show();


        christianCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Print C ",splitedVlaue[0] + "_" + splitedVlaue[1] + "_" + "Christian");
                masterQuery = mFirebase.getReference("Christian").child(splitedVlaue[0]).orderByChild("sex_name_religion").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1] + "_" + "Christian");
                retriveData(masterQuery);
                muslimCardView.setBackgroundColor(Color.parseColor("#BC110F0F"));
                hinduCardView.setBackgroundColor(Color.parseColor("#BC110F0F"));
                v.setBackgroundColor(Color.GRAY);
            }

        });

        hinduCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterQuery = mFirebase.getReference("Hindu").child(splitedVlaue[0]).orderByChild("sex_name_religion").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1] + "_" + "Hindu");
                christianCardView.setBackgroundColor(Color.parseColor("#BC110F0F"));
                muslimCardView.setBackgroundColor(Color.parseColor("#BC110F0F"));
                retriveData(masterQuery);
                v.setBackgroundColor(Color.GRAY);

            }
        });

        muslimCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterQuery = mFirebase.getReference("Muslim").child(splitedVlaue[0]).orderByChild("sex_name_religion").equalTo(splitedVlaue[0] + "_" + splitedVlaue[1] + "_" + "Muslim");
                retriveData(masterQuery);
                christianCardView.setBackgroundColor(Color.parseColor("#BC110F0F"));
                hinduCardView.setBackgroundColor(Color.parseColor("#BC110F0F"));
                v.setBackgroundColor(Color.GRAY);
            }
        });


    }


    private void retriveData(Query selectedQuery) {

        mProgressBarHandler.show();
        selectedQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Toast.makeText(MainActivity.this,"data exists",Toast.LENGTH_SHORT).show();
                    mProgressBarHandler.hide();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "No data exists", Toast.LENGTH_LONG);
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
        masterQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Toast.makeText(MainActivity.this,"data exists",Toast.LENGTH_SHORT).show();
                    mProgressBarHandler.hide();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "No data exists", Toast.LENGTH_LONG);
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
                        R.layout.name_list,
                        ViewHolder.class,
                        masterQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder vhVal, DataModel dataModel,
                                                      int position) {
                        vhVal.setTitleAndDescription(getApplicationContext(), dataModel.getName(), dataModel.getMeaning(), dataModel.getSex());
                        final TextView textView = (TextView) vhVal.itemView.findViewById(R.id.xml_title_post);
                        final List<String> userSavednames = new ArrayList<String>();

                        vhVal.itemView.findViewById(R.id.saveNames).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final String txt = textView.getText().toString();  // add here

                                account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

                                if (account != null) {
                                    try {
                                        Query dataRoof = FirebaseDatabase.getInstance().getReference("User_and_saved_names").child(account.getId().toString()).child("user_Saved_names");
                                        dataRoof.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    boolean isAvailable = false;
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        if (ds.getValue(String.class).equals(txt)) {
                                                            isAvailable = true;
                                                        }
                                                    }
                                                    if (!isAvailable) {
                                                        Toast.makeText(MainActivity.this, "New name added", Toast.LENGTH_SHORT).show();
                                                        addNames.getReference("User_and_saved_names").child(account.getId().toString()).child("user_Saved_names").push().setValue(txt.toString());
                                                    } else {
                                                        Toast.makeText(MainActivity.this, "Name already saved", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.i("Firebase Error ", databaseError.getDetails());
                                            }
                                        });
                                    } catch (Exception e) {
                                        Log.i("Firebase data ", e.toString());
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, " Please Login to save this NAME", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }

                };

        mRecycleView.setAdapter(firebaseRecyclerAdapter);
    }
}