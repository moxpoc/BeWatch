package com.example.moxpoc.bewatch;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MyGoalsActivity extends AppCompatActivity {

    EditText goalSteps, goalDream, goalActivity;
    PreferencesLoad load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goals);
        load = new PreferencesLoad(getApplicationContext());

        //Объявляем тулбар
        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        goalSteps = findViewById(R.id.textTotalGoalSteps);
        goalDream = findViewById(R.id.textTimeGoalSleep);
        goalActivity = findViewById(R.id.textTimeGoalActivity);

        goalSteps.setText(load.getGoalSteps());
        goalDream.setText(load.getGoalDream());
        goalActivity.setText(load.getGoalActivity());

        //Событие кнопки назад(настройки)
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyGoalsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

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
                        Intent intentS = new Intent(MyGoalsActivity.this, VoiceChatActivity.class);
                        startActivity(intentS);
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
