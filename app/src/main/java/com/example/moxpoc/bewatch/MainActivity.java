package com.example.moxpoc.bewatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        setSupportActionBar(profileToolbar);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        final MenuItem chargeMenuItem = menu.findItem(R.id.watchChargeItem);
        FrameLayout rootView = (FrameLayout) chargeMenuItem.getActionView();
        return super.onPrepareOptionsMenu(menu);
    }
}
