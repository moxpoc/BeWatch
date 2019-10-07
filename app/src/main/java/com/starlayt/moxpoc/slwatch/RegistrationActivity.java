package com.starlayt.moxpoc.slwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.starlayt.moxpoc.slwatch.ModelAPI.RegistrationRequest;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText regName = findViewById(R.id.editTextRegName);
        final EditText regPass = findViewById(R.id.editTextRegPass);
        final EditText regEmail = findViewById(R.id.editTextRegEmail);

        Button signupBtn = findViewById(R.id.sign_upRegBtn);
        Button signinBtn = findViewById(R.id.sign_inRegBtn);

        final ApiImpl api =  new ApiImpl(getApplicationContext());
        PreferencesLoad load = new PreferencesLoad(getApplicationContext());


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = regName.getText().toString();
                String password = regPass.getText().toString();
                String email = regEmail.getText().toString();
                if( !login.equals("")&&!password.equals("")&&!email.equals("")){
                    RegistrationRequest registrationRequest = new RegistrationRequest(login, password, email);
                    api.registration(registrationRequest);
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
