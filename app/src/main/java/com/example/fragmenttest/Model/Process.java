package com.example.fragmenttest.Model;

public class Process {
    private int app_number;
    private String app_status;
    private String app_pro_title;
    private String app_tas_title;
    private String app_current_user;
    private String app_create_date;
    private String del_priority;

    public Process() {
        super();
    }

    public int getApp_number() {
        return app_number;
    }

    public String getApp_create_date() {
        return app_create_date;
    }

    public String getApp_current_user() {
        return app_current_user;
    }

    public String getApp_pro_title() {
        return app_pro_title;
    }

    public String getApp_status() {
        return app_status;
    }

    public String getApp_tas_title() {
        return app_tas_title;
    }

    public String getDel_priority() {
        return del_priority;
    }

    public void setApp_create_date(String app_create_date) {
        this.app_create_date = app_create_date;
    }

    public void setApp_current_user(String app_current_user) {
        this.app_current_user = app_current_user;
    }

    public void setApp_number(int app_number) {
        this.app_number = app_number;
    }

    public void setApp_pro_title(String app_pro_title) {
        this.app_pro_title = app_pro_title;
    }

    public void setApp_status(String app_status) {
        this.app_status = app_status;
    }

    public void setApp_tas_title(String app_tas_title) {
        this.app_tas_title = app_tas_title;
    }

    public void setDel_priority(String del_priority) {
        this.del_priority = del_priority;
    }
}
