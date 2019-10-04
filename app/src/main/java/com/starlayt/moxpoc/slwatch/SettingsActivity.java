package com.starlayt.moxpoc.slwatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "watchsettings";
    public static final String APP_PREFERENCES_IMEI = "imei"; // imei
    public static final String APP_PREFERENCES_WATCH = "watch";
    private static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 100;
    //Создаем экземпляр настроек
    //SharedPreferences watchSettings;

    EditText editSettImei;
    EditText editSettSex;
    EditText editSettWeight;
    EditText editSettHeight;
    EditText editSettAge;
    EditText editSettName;
    EditText editSettPhone;
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
        editSettPhone = findViewById(R.id.editSettPhone);
        profileImage = findViewById(R.id.profile_image);
        textSettAge = findViewById(R.id.textSettAge);
        textSettHeight = findViewById(R.id.textSettHeight);

        load = new PreferencesLoad(getApplicationContext());
        api = new ApiImpl(getApplicationContext());
        watch = load.getWatch();

            editSettAge.setText(watch.getOwnerBirthday());//ошибка
            editSettSex.setText(watch.getOwnerGender());
            editSettName.setText(watch.getName());
            editSettHeight.setText(String.valueOf( watch.getHeight()));
            editSettWeight.setText(String.valueOf(watch.getWeight()));
            editSettImei.setText(watch.getImei());
            editSettPhone.setText(watch.getDeviceMobileNo());
        try{
            textSettAge.setText(watch.getOwnerBirthday() + " years");
            textSettHeight.setText(watch.getHeight() + " sm, " + watch.getWeight() + " kg");
            if(!load.getImagePath().contains("©"))
                profileImage.setImageBitmap(BitmapFactory.decodeFile(load.getImagePath()));
        } catch (NullPointerException e){
            e.printStackTrace();
        }

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
                        String phoneNo = load.getWatch().getDeviceMobileNo();
                        if(!TextUtils.isEmpty(phoneNo)) {
                            String dial = "tel:" + phoneNo;
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        }else {
                            Toast.makeText(SettingsActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                        }
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
                try {
                    watch.setOwnerGender(sex);
                    if(!height.equals(""))
                        watch.setHeight(Integer.valueOf(height));
                    if(!weight.equals(""))
                        watch.setWeight(Integer.valueOf(weight));
                    watch.setOwnerBirthday(age);
                    watch.setName(editSettName.getText().toString());
                    watch.setDeviceMobileNo(editSettPhone.getText().toString());
                    watch.setImei(imei);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                load.setWatch(watch);
                Toast.makeText(getApplicationContext(), getString(R.string.saveSucces), Toast.LENGTH_SHORT).show();
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
/*
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
        }*/
        if (requestCode == pick_image && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //ImageView imageView = (ImageView) findViewById(R.id.imgView);
            profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            load.setImagePath(picturePath);

        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        String imei = editSettImei.getText().toString();
        try {
            watch.setImei(imei);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
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

    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Note: If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }
}
