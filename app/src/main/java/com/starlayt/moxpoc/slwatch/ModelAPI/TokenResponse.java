package com.starlayt.moxpoc.slwatch.ModelAPI;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class TokenResponse {

    private String login;
    private String token;

    public TokenResponse(){
    }

    public TokenResponse(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthHeader(){
        return "Bearer_" + token;
    }

    public long getExpiration(){
        byte [] decodedBytes = Base64.decode(token, Base64.DEFAULT);
        String decodedToken = new String(decodedBytes, StandardCharsets.UTF_8);
        int indexExp = decodedToken.indexOf("exp");
        return Long.parseLong(decodedToken.substring(76, 86));
    }

}
