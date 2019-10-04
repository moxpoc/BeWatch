package com.starlayt.moxpoc.slwatch.ModelAPI;

public class TokenResponse {

    private String login;
    private String token;

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

}
