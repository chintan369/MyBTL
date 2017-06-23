package com.agraeta.user.btl;

/**
 * Created by chaitalee on 12/31/2016.
 */

public class Bean_Order_E_History {

    private String enquiry = new String();
    private String enquiry_date = new String();

    public Bean_Order_E_History() {
    }

    public Bean_Order_E_History(String enquiry, String enquiry_date) {
        this.enquiry = enquiry;
        this.enquiry_date = enquiry_date;
    }

    public String getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(String enquiry) {
        this.enquiry = enquiry;
    }

    public String getEnquiry_date() {
        return enquiry_date;
    }

    public void setEnquiry_date(String enquiry_date) {
        this.enquiry_date = enquiry_date;
    }
}
