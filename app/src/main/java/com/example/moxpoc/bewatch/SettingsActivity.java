package com.example.moxpoc.bewatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moxpoc.bewatch.ModelAPI.Location;
import com.example.moxpoc.bewatch.ModelAPI.Watch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "watchsettings";
    public static final String APP_PREFERENCES_IMEI = "imei"; // imei
    public static final String APP_PREFERENCES_WATCH = "watch";
    //Создаем экземпляр настроек
    //SharedPreferences watchSettings;

    EditText editSettImei;
    EditText editSettSex;
    EditText editSettWeight;
    EditText editSettHeight;
    EditText editSettAge;
    EditText editSettName;
    TextView chargeText, textSettAge, textSettHeight;
    CircleImageView profileImage;
    Watch watch;
    PreferencesLoad load;
    ApiImpl api;

    private final int pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);


        Button savePrefBtn = findViewById(R.id.savePreferencesBtn);
        editSettImei = findViewById(R.id.editSettImei);
        editSettSex = findViewById(R.id.editSettSex);
        editSettWeight = findViewById(R.id.editSettWeight);
        editSettHeight = findViewById(R.id.editSettHeight);
        editSettAge = findViewById(R.id.editSettAge);
        editSettName = findViewById(R.id.editSettName);
        profileImage = findViewById(R.id.profile_image);
        textSettAge = findViewById(R.id.textSettAge);
        textSettHeight = findViewById(R.id.textSettHeight);

        load = new PreferencesLoad(getApplicationContext());
        api = new ApiImpl(getApplicationContext());
        watch = load.getWatch();
            editSettAge.setText(watch.getOwnerBirthday());
            editSettSex.setText(watch.getOwnerGender());
            editSettHeight.setText(String.valueOf( watch.getHeight()));
            editSettWeight.setText(String.valueOf(watch.getWeight()));
            editSettImei.setText(watch.getImei());
            textSettAge.setText(watch.getOwnerBirthday() + " years");
            textSettHeight.setText(watch.getHeight() + " sm, " + watch.getWeight() + " kg");
            if(!load.getImagePath().contains("©"))
                profileImage.setImageURI(Uri.fromFile(new File(load.getImagePath())));
        //}

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
                watch.setImei(imei);
                load.setWatch(watch);
                api.updateWatch();

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, pick_image);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case pick_image:
                if(resultCode == RESULT_OK){
                    try{
                        final Uri imageUri = data.getData();
                        load.setImagePath(imageUri.getPath());
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        profileImage.setImageBitmap(selectedImage);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        String imei = editSettImei.getText().toString();
        watch.setImei(imei);
        load.setWatch(watch);
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
