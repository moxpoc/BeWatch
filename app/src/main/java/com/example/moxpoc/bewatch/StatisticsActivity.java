package com.example.moxpoc.bewatch;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.moxpoc.bewatch.ModelAPI.Watch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    TextView chargeText, sdpDbpText, statPulseText, statOxygenText, textTotalSteps;
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
        watch = load.getWatch();

        //Событие кнопки назад(настройки)
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        //Меняем цвет progressBar со счетчиком шагов
        ProgressBar progressBarPedTotal = findViewById(R.id.statProgressBarPedTotal);
        progressBarPedTotal.setProgressDrawable(getDrawable(R.drawable.total_ped_circle));
        //Меняем цвет progressBar со счетчиком килокалорий
        ProgressBar progressBarPedEnergy = findViewById(R.id.statProgressBarPedEnergy);
        progressBarPedEnergy.setProgressDrawable(getDrawable(R.drawable.test_circle));

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
                        Intent intentS = new Intent(StatisticsActivity.this, VoiceChatActivity.class);
                        startActivity(intentS);
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(StatisticsActivity.this, MainActivity.class);
                        startActivity(intentT);
                        break;
                }
                return true;
            }
        });

        sdpDbpText.setText(watch.getBlood().getSbp() + "/" + watch.getBlood().getDbp());
        statPulseText.setText(String.valueOf(watch.getBlood().getHeartrate()));
        statOxygenText.setText(watch.getBlood().getOxygen());
        try{
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


}
