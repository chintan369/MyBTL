package com.agraeta.user.btl.model.enquiries;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class CustomerComplaintDetail extends EnquiryDetail {
    @SerializedName("product_name")
    String productName="";

    @SerializedName("reason")
    String reason="";

    @SerializedName("image")
    String imagePath="";

    @SerializedName("invoice")
    String invoice="";

    @SerializedName("warranty")
    String warranty;

    public String getProductName() {
        return productName;
    }

    public String getReason() {
        return reason;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getWarranty() {
        return warranty;
    }
}
