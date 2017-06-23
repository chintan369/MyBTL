package com.agraeta.user.btl.model.enquiries;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class CorporateOfficeDetail extends EnquiryDetail {

    @SerializedName("message")
    String message="";

    public String getMessage() {
        return message;
    }
}
