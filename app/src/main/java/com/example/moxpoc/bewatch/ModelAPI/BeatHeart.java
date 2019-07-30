package com.example.moxpoc.bewatch.ModelAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeatHeart {

    private String imei;

    private int battery = 0;

    private int pedometer = 0;


    public BeatHeart(){

    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getPedometer() {
        return pedometer;
    }

    public void setPedometer(int pedometer) {
        this.pedometer = pedometer;
    }

}
