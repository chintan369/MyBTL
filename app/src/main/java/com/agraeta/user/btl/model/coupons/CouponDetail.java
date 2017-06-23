package com.agraeta.user.btl.model.coupons;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nivida new on 28-Mar-17.
 */

public class CouponDetail {

    @SerializedName("id")
    String id="";

    @SerializedName("name")
    String couponName="";

    @SerializedName("code")
    String couponCode="";

    @SerializedName("type")
    String couponType="";

    @SerializedName("discount")
    String discount="";

    @SerializedName("total")
    String minOrderValue="";

    @SerializedName("date_start")
    String startDate="";

    @SerializedName("date_end")
    String expiryDate="";

    @SerializedName("total_used_coupon")
    String totalUsed="";

    @SerializedName("max_discount")
    String maxDiscount="";

    @SerializedName("use_coupon_multiple")
    String multipleTimeUses="";

    @SerializedName("status")
    String status="";

    @SerializedName("created")
    String onDate="";

    public String getId() {
        return id;
    }

    public String getCouponName() {
        return couponName;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getCouponType() {
        return couponType.equalsIgnoreCase("F") ? "Fixed Amount" : " Percentage";
    }

    public String getDiscount() {
        return couponType.equalsIgnoreCase("F") ? "\u20B9 "+discount : discount+" %";
    }

    public String getMinOrderValue() {
        return "\u20B9 "+minOrderValue;
    }

    public String getStartDate() {
        return getOnDate("yyyy-MM-dd","dd MMM yyyy",startDate);
    }

    public String getExpiryDate() {
        return getOnDate("yyyy-MM-dd","dd MMM yyyy",expiryDate);
    }

    public String getTotalUsed() {
        return totalUsed;
    }

    public String getMaxDiscount() {
        return "\u20B9 "+maxDiscount;
    }

    public String getMultipleTimeUses() {
        return multipleTimeUses+" Time";
    }

    public String getStatus() {
        return status.equals("1") ? "Active" : "InActive";
    }

    public String getOnDate() {
        return getOnDate("dd/MM/yyyy","dd-MM-yyyy",onDate);
    }

    public String getOnDate(String givenFormat, String dateFormat,String dateGiven){
        SimpleDateFormat currentFormat=new SimpleDateFormat(givenFormat, Locale.getDefault());
        SimpleDateFormat requiredFormat=new SimpleDateFormat(dateFormat,Locale.getDefault());

        try {
            Date currentDate=currentFormat.parse(dateGiven.split(" ")[0]);
            return requiredFormat.format(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateGiven;
        }
    }
}
