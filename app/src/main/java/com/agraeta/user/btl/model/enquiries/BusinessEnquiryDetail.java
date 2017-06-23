package com.agraeta.user.btl.model.enquiries;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class BusinessEnquiryDetail extends EnquiryDetail {


    @SerializedName("company_name")
    String companyName="";

    @SerializedName("enquiry")
    String enquiry="";

    public String getCompanyName() {
        return companyName;
    }

    public String getComment() {
        return enquiry;
    }
}
