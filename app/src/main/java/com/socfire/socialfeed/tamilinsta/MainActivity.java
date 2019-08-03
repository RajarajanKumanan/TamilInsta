package com.socfire.socialfeed.tamilinsta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CardView cdClick;
    Toast toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cdClick=(CardView)findViewById(R.id.memesIds);
        cdClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("clicked CardView "+v.toString());
                clickImageView(v);
            }
        });
    }

    private void clickImageView(View v){
        Intent init =new Intent(this, MemesActivity.class);
        startActivity(init);
    }

    private void showToast(String str){
        toastMessage.makeText(this,str,Toast.LENGTH_LONG).show();
    }

}
