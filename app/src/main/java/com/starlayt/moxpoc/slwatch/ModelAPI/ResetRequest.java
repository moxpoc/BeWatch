package com.starlayt.moxpoc.slwatch.ModelAPI;

public class ResetRequest {
    private String email;
    private String newPass;

    public ResetRequest(String email, String newPass) {
        this.email = email;
        this.newPass = newPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

}
