package rakshan.sps.tamilnames;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SaveAndShareActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    TextView lblInfo, textViewResults, savedNamesCount;
    ImageView imgProfilePic, btnLogout, shareNames;
    SignInButton btnLogin;
    GoogleSignInOptions gso;
    GoogleSignInAccount account;
    private InterstitialAd mInterstitialAd;
    CardView user_card_visible;
    FirebaseDatabase addNames;
    String user_saved_all_names = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_and_share_login);

        lblInfo = (TextView) findViewById(R.id.lblInfo);

        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        btnLogin = (SignInButton) findViewById(R.id.btnLogin);
        btnLogout = (ImageView) findViewById(R.id.btnLogout);
        user_card_visible = (CardView) findViewById(R.id.user_data_cardView);
        textViewResults = (TextView) findViewById(R.id.textViewResults);
        savedNamesCount = (TextView) findViewById(R.id.savedNamesCount);
        shareNames = (ImageView) findViewById(R.id.shareNames);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(SaveAndShareActivity.this, gso);

        account = GoogleSignIn.getLastSignedInAccount(SaveAndShareActivity.this);

        if (account != null)
            updateUI(account);
        else {
            btnLogout.setVisibility(View.GONE);
            user_card_visible.setVisibility(View.GONE);
            textViewResults.setVisibility(View.GONE);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        shareNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareWithSocialMedia();
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

//    private void setContentView(int save_and_share_login) {
//    }

    private void updateUI(GoogleSignInAccount account) {
        try {

//            String strData = "Name : " + account.getDisplayName()
//                    + "\r\nEmail : " + account.getEmail() + "\r\nGiven name : " + account.getGivenName()
//                    + "\r\nDisplay Name : " + account.getDisplayName() + "\r\nId : "
//                    + account.getId();
//+ "\r\nImage URL : " + account.getPhotoUrl().toString();
//+ "\r\nAccount : " + account.getAccount().toString()

            String strData = account.getDisplayName().toUpperCase();

            lblInfo.setText(strData);

            Log.d("Image URL : ", account.getPhotoUrl().toString());
            Log.d("Login Data : ", strData);
            new DownloadImageTask((ImageView) findViewById(R.id.imgProfilePic))
                    .execute(account.getPhotoUrl().toString());


            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            user_card_visible.setVisibility(View.VISIBLE);
            textViewResults.setVisibility(View.VISIBLE);

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User_and_saved_names").child(account.getId().toString()).child("user_Saved_names");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        Long countVal = dataSnapshot.getChildrenCount();
                        savedNamesCount.setText(countVal.toString());

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //String key = ds.getKey();
                            user_saved_all_names += ds.getValue(String.class) + ", \n";
                        }
                        textViewResults.setText(user_saved_all_names);
                    } else {
                        textViewResults.setText("Hi there, save your fav names and share with your friends");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("Firebase Error ", databaseError.getDetails());
                }
            });


            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

        } catch (NullPointerException ex) {
            textViewResults.setText(lblInfo.getText().toString() + "\r\n" + "NullPointerException : " + ex.getMessage().toString());
        } catch (RuntimeException ex) {
            textViewResults.setText(lblInfo.getText().toString() + "\r\n" + "RuntimeException : " + ex.getMessage().toString());
        } catch (Exception ex) {
            textViewResults.setText(ex.getMessage().toString());
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            final List<String> userSavednames = new ArrayList<String>();

            try {
                if (addNames == null) {
                    addNames = FirebaseDatabase.getInstance();

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User_and_saved_names");

                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (!snapshot.hasChild(account.getId())) {
                                // run some code
                                Toast.makeText(SaveAndShareActivity.this, "user does not exists", Toast.LENGTH_SHORT).show();
                                UserActivity userdetails = new UserActivity(account.getGivenName().toUpperCase(), account.getEmail().toString(), userSavednames);
                                addNames.getReference("User_and_saved_names").child(account.getId().toString()).setValue(userdetails);
                            } else {
                                Toast.makeText(SaveAndShareActivity.this, "user exists", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i("Firebase Error ", databaseError.getDetails());
                        }
                    });


                }
            } catch (Exception e) {
                Log.d("database Error", e.toString());
            } finally {
                Log.d("database Finally", "Done");
                addNames = FirebaseDatabase.getInstance();
            }

            updateUI(account);
        } catch (ApiException e) {
            Log.w("Google Error ", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
// ...
                        revokeAccess();
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
// ...
                        lblInfo.setText("Please Login.");
                        btnLogin.setVisibility(View.VISIBLE);
                        btnLogout.setVisibility(View.GONE);
                        user_card_visible.setVisibility(View.GONE);
                        textViewResults.setVisibility(View.GONE);
                        imgProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black_24dp));
                    }
                });
    }


    private void shareWithSocialMedia() {
        if (user_saved_all_names.trim() != "") {
            try {
                String shareBody = "\n This names are downloaded from https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n" + user_saved_all_names;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Tamil Baby Names");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Names"));
            } catch (Exception e) {
                Toast.makeText(SaveAndShareActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SaveAndShareActivity.this, "Please add names", Toast.LENGTH_SHORT).show();
        }
    }


}
