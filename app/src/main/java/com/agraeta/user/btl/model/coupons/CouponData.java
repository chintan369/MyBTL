package com.agraeta.user.btl.model.coupons;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 28-Mar-17.
 */

public class CouponData extends AppModel {

    @SerializedName("data")
    List<CouponDetail> couponDetailList=new ArrayList<>();

    public List<CouponDetail> getCouponDetailList() {
        return couponDetailList;
    }
}
