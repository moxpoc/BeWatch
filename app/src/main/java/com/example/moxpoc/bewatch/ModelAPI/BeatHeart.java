package com.example.moxpoc.bewatch.ModelAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BeatHeart {

    private String imei;

    private int battery;

    private int pedometer;

    private Watch watch;
}
