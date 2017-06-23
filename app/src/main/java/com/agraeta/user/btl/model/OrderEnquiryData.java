package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nivida new on 23-Mar-17.
 */

public class OrderEnquiryData extends AppModel {

    @SerializedName("order_inquiry")
    List<OrderEnquiry> orderEnquiryList=new ArrayList<>();

    public class OrderEnquiry{
        @SerializedName("enquiry")
        String enquiry="";

        @SerializedName("created")
        String onDate="";

        public String getEnquiry() {
            return enquiry;
        }

        public String getOnDate(String givenFormat, String dateFormat){
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

    public List<OrderEnquiry> getOrderEnquiryList() {
        return orderEnquiryList;
    }
}
