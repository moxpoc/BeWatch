package com.example.moxpoc.bewatch.ModelAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Watch {


    @JsonIgnore
    private Long id;
    private String imei;

    private String name;
    private int bpCorrectionHigh;
    private int bpCorrectionLow;
    private int bpThresholdHigh;
    private int bpThresholdLow;
    private String deviceMobileNo;
    private String fallingAlarm;
    private int height = 0;
    private int weight = 0;
    private int hrmTresholdHigh;
    private int hrmTresholdLow;
    private String ownerBirthday;
    private String ownerBloodType;
    private String ownerGender;
    private String restricted;

    private Location location;

    private Blood blood;

    private BeatHeart beatHeart;

    public Watch(){

    }

    public Watch(String imei, String name,
                 int bpCorrectionHigh, int bpCorrectionLow,
                 int bpThresholdHigh, int bpThresholdLow,
                 String deviceMobileNo, String fallingAlarm,
                 int height, int weight,
                 int hrmTresholdHigh, int hrmTresholdLow,
                 String ownerBirthday, String ownerBloodType,
                 String ownerGender, String restricted){
        this.imei = imei;
        this.name = name;
        this.bpCorrectionHigh = bpCorrectionHigh;
        this.bpCorrectionLow = bpCorrectionLow;
        this.bpThresholdHigh = bpThresholdHigh;
        this.bpThresholdLow = bpThresholdLow;
        this.deviceMobileNo = deviceMobileNo;
        this.fallingAlarm = fallingAlarm;
        this.height = height;
        this.weight = weight;
        this.hrmTresholdHigh = hrmTresholdHigh;
        this.hrmTresholdLow = hrmTresholdLow;
        this.ownerBirthday = ownerBirthday;
        this.ownerBloodType = ownerBloodType;
        this.ownerGender = ownerGender;
        this.restricted = restricted;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBpCorrectionHigh() {
        return bpCorrectionHigh;
    }

    public void setBpCorrectionHigh(int bpCorrectionHigh) {
        this.bpCorrectionHigh = bpCorrectionHigh;
    }

    public int getBpCorrectionLow() {
        return bpCorrectionLow;
    }

    public void setBpCorrectionLow(int bpCorrectionLow) {
        this.bpCorrectionLow = bpCorrectionLow;
    }

    public int getBpThresholdHigh() {
        return bpThresholdHigh;
    }

    public void setBpThresholdHigh(int bpThresholdHigh) {
        this.bpThresholdHigh = bpThresholdHigh;
    }

    public int getBpThresholdLow() {
        return bpThresholdLow;
    }

    public void setBpThresholdLow(int bpThresholdLow) {
        this.bpThresholdLow = bpThresholdLow;
    }

    public String getDeviceMobileNo() {
        return deviceMobileNo;
    }

    public void setDeviceMobileNo(String deviceMobileNo) {
        this.deviceMobileNo = deviceMobileNo;
    }

    public String getFallingAlarm() {
        return fallingAlarm;
    }

    public void setFallingAlarm(String fallingAlarm) {
        this.fallingAlarm = fallingAlarm;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHrmTresholdHigh() {
        return hrmTresholdHigh;
    }

    public void setHrmTresholdHigh(int hrmTresholdHigh) {
        this.hrmTresholdHigh = hrmTresholdHigh;
    }

    public int getHrmTresholdLow() {
        return hrmTresholdLow;
    }

    public void setHrmTresholdLow(int hrmTresholdLow) {
        this.hrmTresholdLow = hrmTresholdLow;
    }

    public String getOwnerBirthday() {
        return ownerBirthday;
    }

    public void setOwnerBirthday(String ownerBirthday) {
        this.ownerBirthday = ownerBirthday;
    }

    public String getOwnerBloodType() {
        return ownerBloodType;
    }

    public void setOwnerBloodType(String ownerBloodType) {
        this.ownerBloodType = ownerBloodType;
    }

    public String getOwnerGender() {
        return ownerGender;
    }

    public void setOwnerGender(String ownerGender) {
        this.ownerGender = ownerGender;
    }

    public String getRestricted() {
        return restricted;
    }

    public void setRestricted(String restricted) {
        this.restricted = restricted;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Blood getBlood() {
        return blood;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
    }

    public BeatHeart getBeatHeart() {
        return beatHeart;
    }

    public void setBeatHeart(BeatHeart beatHeart) {
        this.beatHeart = beatHeart;
    }
}
