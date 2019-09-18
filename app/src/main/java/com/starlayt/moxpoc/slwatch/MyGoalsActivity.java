package com.starlayt.moxpoc.slwatch;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyGoalsActivity extends AppCompatActivity {

    EditText goalSteps, goalDream, goalActivity;
    TextView chargeText,  progressSteps, progressDream, progressActivity;
    ProgressBar progressPedTotal;
    PreferencesLoad load;
    Watch watch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goals);
        load = new PreferencesLoad(getApplicationContext());
        watch = load.getWatch();

        //Объявляем тулбар
        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        progressSteps = findViewById(R.id.textTotalSteps);

        goalSteps = findViewById(R.id.textTotalGoalSteps);
        goalDream = findViewById(R.id.textTimeGoalSleep);
        goalActivity = findViewById(R.id.textTimeGoalActivity);

        progressPedTotal = findViewById(R.id.statProgressBarPedTotal);


        goalSteps.setText(load.getGoalSteps());
        goalDream.setText(load.getGoalDream());
        goalActivity.setText(load.getGoalActivity());

        try{
            progressSteps.setText(String.valueOf(load.getWatch().getBeatHeart().getPedometer()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        //Событие кнопки назад(настройки)
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyGoalsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        try {
            Float percent = (float)0;
            try {
                percent = (float) ((watch.getBeatHeart().getPedometer() * 100) / Integer.parseInt(load.getGoalSteps()));
            }catch (ArithmeticException e) {
                e.printStackTrace();
            }

            progressPedTotal.setProgress(Math.round(percent));
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(MyGoalsActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        String phoneNo = watch.getDeviceMobileNo();
                        if(!TextUtils.isEmpty(phoneNo)) {
                            String dial = "tel:" + phoneNo;
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        }else {
                            Toast.makeText(MyGoalsActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(MyGoalsActivity.this, MainActivity.class);
                        startActivity(intentT);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem item = menu.findItem(R.id.watchChargeItem);
        FrameLayout rootView = (FrameLayout)item.getActionView();
        chargeText = (TextView)rootView.findViewById(R.id.watchChargeText);
        try {
            chargeText.setText((load.getWatch().getBeatHeart().getBattery()) + "%");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onStop(){
        super.onStop();
        String steps = goalSteps.getText().toString();
        String dream = goalDream.getText().toString();
        String activity = goalActivity.getText().toString();
        load.setGoalSteps(steps);
        load.setGoalDream(dream);
        load.setGoalActivity(activity);
        /*
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_IMEI, imei);
        editor.apply();*/
    }
}
