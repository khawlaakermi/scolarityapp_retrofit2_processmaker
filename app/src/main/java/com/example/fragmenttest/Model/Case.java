package com.example.fragmenttest.Model;

import com.google.gson.annotations.SerializedName;

public class Case {
    @SerializedName("tas_uid")
    private String tas_uid;
    @SerializedName("pro_title")
    private String pro_title;
    @SerializedName("pro_uid")
    private String pro_uid;

    public Case(String tas_uid, String pro_title, String pro_uid) {
        this.pro_uid = pro_uid;
        this.tas_uid = tas_uid;
        this.pro_title = pro_title;
    }

    public Case() {
        super();
    }

    public String getPro_title() {
        return pro_title;
    }

    public String getTas_uid() {
        return tas_uid;
    }

    public String getPro_uid() {
        return pro_uid;
    }

    public void setPro_title(String pro_title) {
        this.pro_title = pro_title;
    }

    public void setPro_uid(String pro_uid) {
        this.pro_uid = pro_uid;
    }

    public void setTas_uid(String tas_uid) {
        this.tas_uid = tas_uid;
    }
}
