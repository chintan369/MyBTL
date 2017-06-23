package com.agraeta.user.btl.model.enquiries;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class ProductEnquiryDetail extends EnquiryDetail {

    @SerializedName("company_name")
    String companyName="";

    @SerializedName("product_name")
    String productName="";

    @SerializedName("comment")
    String comment="";

    public String getCompanyName() {
        return companyName;
    }

    public String getProductName() {
        return productName;
    }

    public String getComment() {
        return comment;
    }
}
