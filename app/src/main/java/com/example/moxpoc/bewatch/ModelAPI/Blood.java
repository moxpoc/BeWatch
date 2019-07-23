package com.example.moxpoc.bewatch.ModelAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Blood {

    private String imei;

    private int dbp;

    private int sbp;

    public Blood(){

    }

    public Blood(String imei, int dbp, int sbp){
        this.imei = imei;
        this.dbp = dbp;
        this.sbp =sbp;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public int getSbp() {
        return sbp;
    }

    public void setSbp(int sbp) {
        this.sbp = sbp;
    }
}
