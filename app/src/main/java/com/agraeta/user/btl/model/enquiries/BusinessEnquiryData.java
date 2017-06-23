package com.agraeta.user.btl.model.enquiries;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class BusinessEnquiryData extends AppModel {

    @SerializedName("data")
    List<BusinessEnquiryDetail> businessEnquiryDetailList=new ArrayList<>();

    public List<BusinessEnquiryDetail> getBusinessEnquiryDetailList() {
        return businessEnquiryDetailList;
    }
}
