package com.cristhoper.buslocator.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.fragments.SignInFragment;

public class RegisterActivity extends AppCompatActivity {


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        getSignInFragment();
    }

    public void getSignInFragment() {
        Fragment signInFragment = new SignInFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.register_activity, signInFragment).commit();
    }

}
