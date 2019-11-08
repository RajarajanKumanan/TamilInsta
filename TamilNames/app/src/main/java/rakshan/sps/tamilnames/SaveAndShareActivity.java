package rakshan.sps.tamilnames;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.InputStream;

public class SaveAndShareActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    TextView lblInfo, lblHeader;
    ImageView imgProfilePic;
    SignInButton btnLogin;
    Button btnLogout;
    GoogleSignInOptions gso;
    GoogleSignInAccount account;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_and_share_login);

        lblInfo = (TextView) findViewById(R.id.lblInfo);
        lblHeader = (TextView) findViewById(R.id.lblHeader);

        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        btnLogin = (SignInButton) findViewById(R.id.btnLogin);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(SaveAndShareActivity.this, gso);

        account = GoogleSignIn.getLastSignedInAccount(SaveAndShareActivity.this);
        if (account != null)
            updateUI(account);
        else
            btnLogout.setVisibility(View.GONE);

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

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

//    private void setContentView(int save_and_share_login) {
//    }

    private void updateUI(GoogleSignInAccount account) {
        try {

            String strData = "Name : " + account.getDisplayName()
                    + "\r\nEmail : " + account.getEmail() + "\r\nGiven name : " + account.getGivenName()
                    + "\r\nDisplay Name : " + account.getDisplayName() + "\r\nId : "
                    + account.getId();
//+ "\r\nImage URL : " + account.getPhotoUrl().toString();
//+ "\r\nAccount : " + account.getAccount().toString()
            lblInfo.setText(strData);

            Log.d("Image URL : ", account.getPhotoUrl().toString());
            Log.d("Login Data : ", strData);
            new DownloadImageTask((ImageView) findViewById(R.id.imgProfilePic))
                    .execute(account.getPhotoUrl().toString());

            lblHeader.setText("Sign In with Google Successful");
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

        } catch (NullPointerException ex) {
            lblInfo.setText(lblInfo.getText().toString() + "\r\n" + "NullPointerException : " + ex.getMessage().toString());
        } catch (RuntimeException ex) {
            lblInfo.setText(lblInfo.getText().toString() + "\r\n" + "RuntimeException : " + ex.getMessage().toString());
        } catch (Exception ex) {
// lblInfo.setText(ex.getMessage().toString());
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
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

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
                        lblHeader.setText("Android Login with Google");
                        btnLogin.setVisibility(View.VISIBLE);
                        btnLogout.setVisibility(View.GONE);
imgProfilePic.setBackgroundResource(R.drawable.boy_black);
                        //imgProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.baby_girl_oval, null));
                    }
                });
    }


}
