package com.starlayt.moxpoc.slwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.starlayt.moxpoc.slwatch.ModelAPI.LoginRequest;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiImpl api = new ApiImpl(getApplicationContext());
        PreferencesLoad load = new PreferencesLoad(getApplicationContext());

        if(load.getToken() != null){//если в настрйоках найден токен
            if(load.getExpiration() != 0 && load.getExpiration() <= System.currentTimeMillis()){//то сравнивается его дата протухания с текущей датой,
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);//если токен валиден, то выполняется переход на основной экран
                api.auth(new LoginRequest(load.getLogin(), load.getPassword()));
                startActivity(intent);
            }
        }else {
            if(load.getLogin() != null) { //проверяем на наличие сохраненного логина, если такой имеется, то отправляем на экран авторизации
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
            } else {//если логина не существует, отправляем на экран регистрациии
                Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        }


        finish();
    }
}
