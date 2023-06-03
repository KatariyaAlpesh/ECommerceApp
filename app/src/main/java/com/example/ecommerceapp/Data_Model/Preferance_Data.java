package com.example.ecommerceapp.Data_Model;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferance_Data {
    public static final String NoticeeDialog = "NoticeeDialog";
    SharedPreferences sharedpref;

    public Preferance_Data ( Context context) {
        this.sharedpref = context.getSharedPreferences(context.getPackageName(), 0);
    }

    public String getSubscription() {
        return this.sharedpref.getString("Subscription", "");
    }

    public void setSubscription(String i) {
        this.sharedpref.edit().putString("Subscription", i).apply();
    }

    public String getprivacy() {
        return this.sharedpref.getString("log", "");
    }

    public void setprivacy(String i) {
        this.sharedpref.edit().putString("log", i).apply();
    }

}