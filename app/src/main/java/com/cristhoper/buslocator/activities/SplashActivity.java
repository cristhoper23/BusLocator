package com.cristhoper.buslocator.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cristhoper.buslocator.R;

public class SplashActivity extends AppCompatActivity {

    ImageView img_bus, logo;
    Animation bus_anim, logo_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        img_bus = findViewById(R.id.bus_splash);
        logo = findViewById(R.id.logo_app);

        bus_anim = AnimationUtils.loadAnimation(this, R.anim.bus_anim);
        logo_anim = AnimationUtils.loadAnimation(this, R.anim.transition_logo);

        img_bus.setAnimation(bus_anim);
        logo.setAnimation(logo_anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 4000);
    }
}
