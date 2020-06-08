package com.example.fragmenttest.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class PostObject {

    private String pro_uid;
    private String tas_uid;
    private Map<String , String> variables;


    public void setTas_uid(String tas_uid) {
        this.tas_uid = tas_uid;
    }

    public void setPro_uid(String pro_uid) {
        this.pro_uid = pro_uid;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public String getPro_uid() {
        return pro_uid;
    }

    public String getTas_uid() {
        return tas_uid;
    }
    public PostObject(String pro_uid,String tas_uid,Map<String,String> variables){
        this.pro_uid=pro_uid;
        this.tas_uid=tas_uid;
        this.variables=variables;
    }


    public Map<String, String> getVariables() {
        return variables;
    }
}
