package com.agraeta.user.btl.model.enquiries;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class ProductRegistrationDetail extends EnquiryDetail {

    @SerializedName("product_name")
    String productName="";

    @SerializedName("retailer_name")
    String retailerName="";

    @SerializedName("upload_invoice")
    String invoice="";

    @SerializedName("purchase_date")
    String purchaseDate="";

    public String getProductName() {
        return productName;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
}
