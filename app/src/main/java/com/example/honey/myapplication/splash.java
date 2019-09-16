package com.example.honey.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by HONEY on 4/4/2018.
 */

public class splash extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.splash);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        progressBar.setProgress(1);
       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splash.this,login.class);
                startActivity(intent);
                finish();
            }
        },1500);
    }
}
