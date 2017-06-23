package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 22-Mar-17.
 */

public class AppModel {
    @SerializedName("status")
    boolean status=false;

    @SerializedName("message")
    String message="";

    int total_pages=1;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getTotalPage() {
        return total_pages;
    }
}
