package com.example.moxpoc.bewatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moxpoc.bewatch.ModelAPI.Location;
import com.example.moxpoc.bewatch.ModelAPI.Watch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "watchsettings";
    public static final String APP_PREFERENCES_IMEI = "imei"; // imei
    public static final String APP_PREFERENCES_SEX = "sex";
    public static final String APP_PREFERENCES_WEIGHT = "weight";
    public static final String APP_PREFERENCES_HEIGHT = "height";
    public static final String APP_PREFERENCES_AGE = "age";
    public static final String APP_PREFERENCES_WATCH = "watch";
    //Создаем экземпляр настроек
    SharedPreferences watchSettings;

    EditText editSettImei;
    EditText editSettSex;
    EditText editSettWeight;
    EditText editSettHeight;
    EditText editSettAge;
    EditText editSettName;
    String json;
    Watch watch;

    Retrofit retrofit;
    BeWatchAPI beWatchAPI;

    ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        watchSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        beWatchAPI = retrofit.create(BeWatchAPI.class);


        Button savePrefBtn = findViewById(R.id.savePreferencesBtn);
        editSettImei = findViewById(R.id.editSettImei);
        editSettSex = findViewById(R.id.editSettSex);
        editSettWeight = findViewById(R.id.editSettWeight);
        editSettHeight = findViewById(R.id.editSettHeight);
        editSettAge = findViewById(R.id.editSettAge);
        editSettName = findViewById(R.id.editSettName);

        mapper = new ObjectMapper();
       /* try{
            json = fromFile("/data/user/0/com.example.moxpoc.bewatch/files/watch.json");
            watch = mapper.readValue(json, Watch.class);
            Toast.makeText(getApplicationContext(), watch.getImei(), Toast.LENGTH_LONG).show();
        } catch(IOException e){
            e.printStackTrace();
        }*/

        if(watchSettings.contains(APP_PREFERENCES_WATCH)){
            String jsin = watchSettings.getString(APP_PREFERENCES_WATCH,"");
            try{
                watch = mapper.readValue(jsin, Watch.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            editSettAge.setText(watch.getOwnerBirthday());
            editSettSex.setText(watch.getOwnerGender());
            editSettHeight.setText(String.valueOf( watch.getHeight()));
            editSettWeight.setText(String.valueOf(watch.getWeight()));
            editSettImei.setText(watch.getImei());
        }
        /*if(watchSettings.contains(APP_PREFERENCES_IMEI)){
            editSettImei.setText(watchSettings.getString(APP_PREFERENCES_IMEI,""));
        }
        if(watchSettings.contains(APP_PREFERENCES_SEX)){
            editSettSex.setText(watchSettings.getString(APP_PREFERENCES_SEX,""));
        }
        if(watchSettings.contains(APP_PREFERENCES_WEIGHT)){
            editSettWeight.setText(watchSettings.getString(APP_PREFERENCES_WEIGHT,""));
        }
        if(watchSettings.contains(APP_PREFERENCES_HEIGHT)){
            editSettHeight.setText(watchSettings.getString(APP_PREFERENCES_HEIGHT,""));
        }
        if(watchSettings.contains(APP_PREFERENCES_AGE)){
            editSettAge.setText(watchSettings.getString(APP_PREFERENCES_AGE,""));
        }*/

        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(SettingsActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        Intent intentS = new Intent(SettingsActivity.this, VoiceChatActivity.class);
                        startActivity(intentS);
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intentT);
                        break;
                }
                return true;
            }
        });

        savePrefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imei = editSettImei.getText().toString();
                String sex = editSettSex.getText().toString();
                String weight = editSettWeight.getText().toString();
                String height = editSettHeight.getText().toString();
                String age = editSettAge.getText().toString();
                watch.setOwnerGender(sex);
                watch.setHeight(Integer.valueOf(height));
                watch.setWeight(Integer.valueOf(weight));
                watch.setOwnerBirthday(age);
                watch.setName(editSettName.getText().toString());
                String watchJson = "";
                try {
                    watchJson = mapper.writeValueAsString(watch);
                }catch (Exception e){
                    e.printStackTrace();
                }

                SharedPreferences.Editor editor = watchSettings.edit();
                editor.putString(APP_PREFERENCES_IMEI, imei);
                editor.putString(APP_PREFERENCES_SEX, sex);
                editor.putString(APP_PREFERENCES_WEIGHT, weight);
                editor.putString(APP_PREFERENCES_HEIGHT, height);
                editor.putString(APP_PREFERENCES_AGE, age);
                editor.putString(APP_PREFERENCES_WATCH, watchJson);
                editor.apply();


                final Call<Watch> updateWatch = beWatchAPI.updateWatch(watch);
                updateWatch.clone().enqueue(new Callback<Watch>() {
                    @Override
                    public void onResponse(Call<Watch> call, Response<Watch> response) {

                    }

                    @Override
                    public void onFailure(Call<Watch> call, Throwable t) {

                    }
                });
            }
        });
    }

    //Метод читает файл квеста
    public String fromFile(String path) throws IOException {
        InputStream is = getAssets().open(path);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }

    @Override
    protected void onStop(){
        super.onStop();
        String imei = editSettImei.getText().toString();
        String sex = editSettSex.getText().toString();
        String weight = editSettWeight.getText().toString();
        String height = editSettHeight.getText().toString();
        String age = editSettAge.getText().toString();
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_IMEI, imei);
        editor.putString(APP_PREFERENCES_SEX, sex);
        editor.putString(APP_PREFERENCES_WEIGHT, weight);
        editor.putString(APP_PREFERENCES_HEIGHT, height);
        editor.putString(APP_PREFERENCES_AGE, age);
        editor.apply();
    }
}
