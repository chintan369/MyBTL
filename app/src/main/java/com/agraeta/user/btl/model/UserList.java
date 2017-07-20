package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class UserList {
    @SerializedName("status")
    boolean status = true;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    List<User> userList=new ArrayList<>();

    @SerializedName("total_page_count")
    int totalPage=1;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getUserList() {
        return userList;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
