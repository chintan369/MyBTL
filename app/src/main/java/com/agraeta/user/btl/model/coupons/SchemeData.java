package com.agraeta.user.btl.model.coupons;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 28-Mar-17.
 */

public class SchemeData extends AppModel {

    @SerializedName("data")
    List<SchemeDetail> schemeDetailList=new ArrayList<>();

    public List<SchemeDetail> getSchemeDetailList() {
        return schemeDetailList;
    }
}
