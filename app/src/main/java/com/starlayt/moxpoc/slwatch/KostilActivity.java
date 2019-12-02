package com.starlayt.moxpoc.slwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class KostilActivity extends AppCompatActivity {

    ApiImpl api;
    BeWatchAPI beWatchAPI;
    private Retrofit retrofit;
    PreferencesLoad load;
    ProgressDialog pDialog;
    EditText et_imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kostil);

        Toolbar profileToolbar = findViewById(R.id.regToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KostilActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        pDialog = new ProgressDialog(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        beWatchAPI = retrofit.create(BeWatchAPI.class);
        load = new PreferencesLoad(getApplicationContext());

        Button continueBtn = findViewById(R.id.continueBtn);
        et_imei = findViewById(R.id.et_imei);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.setIndeterminate(true);
                pDialog.setTitle("Connecting to server");
                pDialog.setMessage("Trying to set your data...");
                pDialog.show();

                Watch newWatch = new Watch("0","",0,0,0,0,"","",0,0,0,0,"","","","");
                newWatch.setImei(et_imei.getText().toString());

                Call<Watch> updateWatch = beWatchAPI.updateWatch(newWatch, load.getBearer());
                updateWatch.enqueue(new Callback<Watch>() {
                    @Override
                    public void onResponse(Call<Watch> call, Response<Watch> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(KostilActivity.this, "All is ok " + response.code(), Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                            load.setImei(et_imei.getText().toString());

                            Call<Watch> getWatchByClient = beWatchAPI.getWatchByClient(load.getBearer());
                            getWatchByClient.enqueue(new Callback<Watch>() {
                                @Override
                                public void onResponse(Call<Watch> call, Response<Watch> response) {
                                    if(response.isSuccessful()) {
                                        Watch watch = response.body();
                                        load.setWatch(watch);
                                        Intent intent = new Intent(KostilActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(KostilActivity.this, "Cant get info of watches", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Watch> call, Throwable t) {
                                    Log.i("__GET_WATCH_FAIL__", t.getMessage());
                                }
                            });
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(KostilActivity.this, "Cant get info of watches " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Watch> call, Throwable t) {
                        pDialog.dismiss();
                        //load.setImei(et_imei.getText().toString());
                        Toast.makeText(KostilActivity.this, "Cant set imei " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("FAIL PUT>>>", t.getMessage());
                    }
                });

            }
        });

    }
}
