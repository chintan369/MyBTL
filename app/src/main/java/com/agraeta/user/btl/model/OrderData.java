package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 20-Mar-17.
 */

public class OrderData {

    @SerializedName("status")
    boolean status=false;

    @SerializedName("message")
    String message="";

    @SerializedName("data")
    List<OrderListItem> orderList=new ArrayList<>();

    @SerializedName("total_pages")
    int totalPage=1;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<OrderListItem> getOrderList() {
        return orderList;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
