package com.starlayt.moxpoc.slwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.starlayt.moxpoc.slwatch.ModelAPI.LoginRequest;
import com.starlayt.moxpoc.slwatch.ModelAPI.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SignInActivity extends AppCompatActivity {


    private Retrofit retrofit;
    private BeWatchAPI beWatchAPI;

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

                    LoginRequest loginRequest = new LoginRequest(login, password);

                    Call<TokenResponse> auth = beWatchAPI.auth(loginRequest);
                    auth.enqueue(new Callback<TokenResponse>() {
                        @Override
                        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                            if (response.isSuccessful()) {
                                TokenResponse tokenResponse = response.body();
                                load.setToken(tokenResponse.getToken());
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);

                                load.setLogin(login);
                                load.setPassword(password);

                            } else {
                                Toast.makeText(SignInActivity.this, response.code() + " Wrong login or password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TokenResponse> call, Throwable t) {
                            Log.i("___SOME_PROBLEMS___", t.getMessage());
                            Toast.makeText(SignInActivity.this, "Wrong login or password", Toast.LENGTH_SHORT).show();
                        }
                    });





                    /*api.auth(loginRequest);
                    boolean status = api.isStatus();*/
                  /*  if(status){
                        load.setLogin(login);
                        load.setPassword(password);
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(SignInActivity.this, "Wrong login or password", Toast.LENGTH_SHORT).show();
                    }*/


                /*} else {
                    LoginRequest loginRequest = new LoginRequest(load.getLogin(), load.getPassword());
                    api.auth(loginRequest);
                }*/
            }
        });
    }
}
