package com.starlayt.moxpoc.slwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.starlayt.moxpoc.slwatch.ModelAPI.LoginRequest;
import com.starlayt.moxpoc.slwatch.ModelAPI.TokenResponse;
import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SignInActivity extends AppCompatActivity {


    private Retrofit retrofit;
    private BeWatchAPI beWatchAPI;
    boolean flag = false;

    public static final String APP_PREFERENCES = "watchsettings";
    public static final String APP_PREFERENCES_IMEI = "imei";
    private SharedPreferences watchSettings;
    SharedPreferences.Editor editor;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar profileToolbar = findViewById(R.id.regToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);


        final EditText loginText = findViewById(R.id.editTextLogin);
        final EditText passwordText = findViewById(R.id.editTextLogPass);

        final Button loginBtn = findViewById(R.id.sign_inLogBtn);

        final ApiImpl api = new ApiImpl(getApplicationContext());
        final PreferencesLoad load = new PreferencesLoad(getApplicationContext());

        dialog = new ProgressDialog(this);
        watchSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        editor = watchSettings.edit();

        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        beWatchAPI = retrofit.create(BeWatchAPI.class);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final String login = loginText.getText().toString();
                    final String password = passwordText.getText().toString();
                    dialog.setIndeterminate(true);
                    dialog.setTitle("Connecting to server");
                    dialog.setMessage("Trying to get your data...");
                    dialog.show();
                    LoginRequest loginRequest = new LoginRequest(login, password);

                    Call<TokenResponse> auth = beWatchAPI.auth(loginRequest);
                    auth.enqueue(new Callback<TokenResponse>() {

                        @Override
                        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                            if (response.isSuccessful()) {
                                TokenResponse tokenResponse = response.body();
                                load.setToken(tokenResponse.getToken());
                                load.setLogin(login);
                                load.setPassword(password);
                                Call<Watch> getWatchByClient = beWatchAPI.getWatchByClient(load.getBearer());
                                getWatchByClient.enqueue(new Callback<Watch>() {
                                    @Override
                                    public void onResponse(Call<Watch> call, Response<Watch> response) {
                                        if(response.isSuccessful()) {
                                            Watch watch = response.body();
                                            load.setWatch(watch);
                                            dialog.dismiss();
                                            editor.putString(APP_PREFERENCES_IMEI, watch.getImei());
                                            editor.apply();
                                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(SignInActivity.this, "Cant get info of watches", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            editor.putString(APP_PREFERENCES_IMEI, "");
                                            editor.apply();
                                            Intent intent = new Intent(SignInActivity.this, KostilActivity.class);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Watch> call, Throwable t) {
                                        Log.i("__GET_WATCH_FAIL__", t.getMessage());
                                        dialog.dismiss();
                                        editor.putString(APP_PREFERENCES_IMEI, "");
                                        editor.apply();
                                        Intent intent = new Intent(SignInActivity.this, KostilActivity.class);

                                        startActivity(intent);
                                    }
                                });
                            } else {
                                dialog.dismiss();
                                Toast.makeText(SignInActivity.this, response.code() + " Wrong login or password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TokenResponse> call, Throwable t) {
                            Log.i("___SOME_PROBLEMS___", t.getMessage());
                            dialog.dismiss();
                            Toast.makeText(SignInActivity.this, " Wrong login or password", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }
}
