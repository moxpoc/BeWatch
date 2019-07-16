package com.example.moxpoc.bewatch;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class VoiceChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_chat);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        //Событие кнопки назад(настройки)
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoiceChatActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(VoiceChatActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        Intent intentS = new Intent(VoiceChatActivity.this, VoiceChatActivity.class);
                        startActivity(intentS);
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(VoiceChatActivity.this, MainActivity.class);
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
}
