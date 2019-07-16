package com.example.moxpoc.bewatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


    }

    @Override
    public boolean onNavigateUp(){
        onBackPressed();
        return true;
    }
}
