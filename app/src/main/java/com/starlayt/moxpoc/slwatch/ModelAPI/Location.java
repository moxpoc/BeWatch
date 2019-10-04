package com.starlayt.moxpoc.slwatch.ModelAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @JsonIgnore
    private Long id;
    private String imei;
    private String type;
    private String lat = "0";
    private String lon = "0";

    public Location(){

    }

    public Location(Long id, String imei, String type, String lat, String lon){
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
