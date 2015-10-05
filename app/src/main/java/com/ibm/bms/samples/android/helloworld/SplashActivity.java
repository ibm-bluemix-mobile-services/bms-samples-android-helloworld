package com.ibm.bms.samples.android.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Typeface IBMFont = Typeface.createFromAsset(getAssets(), "fonts/helvetica-neue-light.ttf");

        TextView copyrightText = (TextView) findViewById(R.id.copyrightText);
        copyrightText.setTypeface(IBMFont);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 1500);
    }
}

