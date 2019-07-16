package com.example.moxpoc.bewatch.ModelAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @JsonIgnore
    private String id;
    private String imei;
    private String type;
    private String lat;
    private String lon;

    public Location(){

    }

    public Location(String id,String imei, String type, String lat, String lon){
        this.id = id;
        this.imei = imei;
        this.type = type;
        this.lat = lat;
        this.lon = lon;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}