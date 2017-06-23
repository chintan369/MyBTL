package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nivida new on 22-Mar-17.
 */

public class OrderTrackingData extends AppModel {

    @SerializedName("order_history")
    ArrayList<OrderTracking> orderTrackingData=new ArrayList<>();

    public class OrderTracking{
        @SerializedName("OrderHistory")
        OrderHistory orderHistory;

        @SerializedName("OrderStatus")
        OrderListItem.OrderStatus orderStatus;

        public OrderHistory getOrderHistory() {
            return orderHistory;
        }

        public OrderListItem.OrderStatus getOrderStatus() {
            return orderStatus;
        }
    }

    public class OrderHistory{
        @SerializedName("comment")
        String statusComment="";

        @SerializedName("created")
        String onDate="";

        public String getStatusComment() {
            return statusComment;
        }

        public String getOnDate(String givenFormat,String dateFormat){
            SimpleDateFormat currentFormat=new SimpleDateFormat(givenFormat, Locale.getDefault());
            SimpleDateFormat requiredFormat=new SimpleDateFormat(dateFormat,Locale.getDefault());

            try {
                Date currentDate=currentFormat.parse(onDate.split(" ")[0]);
                return requiredFormat.format(currentDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return onDate;
            }
        }
    }

    public ArrayList<OrderTracking> getOrderTrackingData() {
        return orderTrackingData;
    }
}
