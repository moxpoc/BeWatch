package com.starlayt.moxpoc.slwatch;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;
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

public class StatisticsActivity extends AppCompatActivity {

    TextView chargeText, sdpDbpText, statPulseText, statOxygenText, textTotalSteps, textEnergy;
    PreferencesLoad load;
    Watch watch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        //Объявляем тулбар
        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        load = new PreferencesLoad(getApplicationContext());
        sdpDbpText = findViewById(R.id.sbp_dbp_text);
        statPulseText = findViewById(R.id.textStatBp);
        statOxygenText = findViewById(R.id.textStatOxygen);
        textTotalSteps = findViewById(R.id.textTotalSteps);
        textEnergy = findViewById(R.id.textEnergy);
        watch = load.getWatch();

        String goalSteps = load.getGoalSteps();

        //Событие кнопки назад(настройки)
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        //Меняем цвет progressBar со счетчиком шагов
        ProgressBar progressBarPedStatic = findViewById(R.id.statProgressBarPedStatic);
        progressBarPedStatic.setProgressDrawable(getDrawable(R.drawable.total_ped_circle));
        //Меняем цвет progressBar со счетчиком килокалорий
        ProgressBar progressBarPedEnergy = findViewById(R.id.statProgressBarPedEnergy);
        /*progressBarPedEnergy.setProgressDrawable(getDrawable(R.drawable.test_circle));*/

        ProgressBar progressBarPedTotal = findViewById(R.id.statProgressBarPedTotal);

        try {
            Float percent = (float)0;
            try {
                percent = (float) ((watch.getBeatHeart().getPedometer() * 100) / Integer.parseInt(goalSteps));
            }catch (ArithmeticException e) {
                e.printStackTrace();
            }

            progressBarPedTotal.setProgress(Math.round(percent));
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            Float percent = (float)0;
            try {
                percent = (float) ((Kkal(watch.getWeight(), watch.getHeight(), watch.getBeatHeart().getPedometer()) * 100) / KkalGoal(watch.getWeight(), watch.getHeight(), Integer.parseInt(watch.getOwnerBirthday())));
            }catch (ArithmeticException e) {
                e.printStackTrace();
            }
            progressBarPedEnergy.setProgress(Math.round(percent));
            textEnergy.setText(String.valueOf(Kkal(watch.getWeight(), watch.getHeight(), watch.getBeatHeart().getPedometer())));
        }catch (Exception e){
            e.printStackTrace();
        }

        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(StatisticsActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        String phoneNo = load.getWatch().getDeviceMobileNo();
                        if(!TextUtils.isEmpty(phoneNo)) {
                            String dial = "tel:" + phoneNo;
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        }else {
                            Toast.makeText(StatisticsActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(StatisticsActivity.this, MainActivity.class);
                        startActivity(intentT);
                        break;
                }
                return true;
            }
        });
        try{
            sdpDbpText.setText(watch.getBlood().getSbp() + "/" + watch.getBlood().getDbp());
            statPulseText.setText(String.valueOf(watch.getBlood().getHeartrate()));
            statOxygenText.setText(watch.getBlood().getOxygen());
            textTotalSteps.setText(String.valueOf(watch.getBeatHeart().getPedometer()));
        } catch (NullPointerException e){
            e.printStackTrace();
        }

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

    private int Kkal(int weight, int height, int steps){
        int S = (height / 4) + 37;
        int Q = weight * steps * S / 100000;
        return Q;
    }

    private int KkalGoal(int weight, int height, int age){
        double kkal =  (10 * weight + 6.25 * height - 5 * age -161) * 1.2;
        return (int)kkal;
    }
}
