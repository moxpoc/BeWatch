package com.starlayt.moxpoc.slwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.starlayt.moxpoc.slwatch.ModelAPI.RegistrationRequest;
import com.starlayt.moxpoc.slwatch.ModelAPI.RegistrationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private BeWatchAPI beWatchAPI;
    PreferencesLoad load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText regName = findViewById(R.id.editTextRegName);
        final EditText regPass = findViewById(R.id.editTextRegPass);
        final EditText regEmail = findViewById(R.id.editTextRegEmail);

        Button signupBtn = findViewById(R.id.sign_upRegBtn);
        Button signinBtn = findViewById(R.id.sign_inRegBtn);

        load = new PreferencesLoad(getApplicationContext());

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        beWatchAPI = retrofit.create(BeWatchAPI.class);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = regName.getText().toString();
                String password = regPass.getText().toString();
                String email = regEmail.getText().toString();
                if(password.length()<8){
                    Toast.makeText(RegistrationActivity.this, getString(R.string.invalidPwlength), Toast.LENGTH_SHORT).show();
                    if(!email.contains("@")){
                        Toast.makeText(RegistrationActivity.this, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
                    }
                }
                if( !login.equals("")&& !password.equals("")&& !email.equals("")&& password.length()>=8 && email.contains("@")){
                    final RegistrationRequest registrationRequest = new RegistrationRequest(login, password, email);
                    Call<RegistrationResponse> registration = beWatchAPI.registration(registrationRequest);
                    registration.enqueue(new Callback<RegistrationResponse>() {
                        @Override
                        public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                            if(response.isSuccessful()) {
                                System.out.println(response.body());
                                RegistrationResponse registrationResponse = response.body();
                                load.setPassword(registrationRequest.getPassword());
                                try {
                                    load.setLogin(registrationResponse.getLogin());

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(RegistrationActivity.this, SignInActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(RegistrationActivity.this, response.code() + " User is exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                            Log.i("___SOME_PROBLEMS___", t.getMessage());
                        }
                    });


                }
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onNavigateUp(){
        onBackPressed();
        return true;
    }
}
