package com.example.moxpoc.bewatch;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PedometerActivity extends AppCompatActivity {

    ProgressBar pedometerProgress;
    TextView totatlSteps, chargeText;
    PreferencesLoad load;
    String goalSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        //Событие кнопки назад(настройки)
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PedometerActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        load = new PreferencesLoad(getApplicationContext());
        pedometerProgress = findViewById(R.id.progressBarPedometrPedometer);
        totatlSteps = findViewById(R.id.textTotalGoalSteps);
        goalSteps = load.getGoalSteps();
        try {
            Float percent = (float)0;
            try{
                percent = (float) ((load.getWatch().getBeatHeart().getPedometer() * 100) / Integer.parseInt(goalSteps));
            }catch (ArithmeticException e){
                e.printStackTrace();
            }

            pedometerProgress.setProgress(Math.round(percent));
            totatlSteps.setText(String.valueOf(load.getWatch().getBeatHeart().getPedometer()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        //Меняем цвет progressBar со счетчиком шагов
        ProgressBar progressBarPedTotal = findViewById(R.id.pedoProgressBarHistory);
        progressBarPedTotal.setProgressDrawable(getDrawable(R.drawable.total_ped_circle));

        //Нижнее меню, навигация
        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(PedometerActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        String phoneNo = load.getWatch().getDeviceMobileNo();
                        if(!TextUtils.isEmpty(phoneNo)) {
                            String dial = "tel:" + phoneNo;
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        }else {
                            Toast.makeText(PedometerActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(PedometerActivity.this, MainActivity.class);
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
}
