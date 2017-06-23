package com.agraeta.user.btl.model.enquiries;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class ProductEnquiryData extends AppModel {

    @SerializedName("data")
    List<ProductEnquiryDetail> productEnquiryDetailList=new ArrayList<>();

    public List<ProductEnquiryDetail> getProductEnquiryDetailList() {
        return productEnquiryDetailList;
    }
}
