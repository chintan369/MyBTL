package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 24-Mar-17.
 */

public class EnquiryCounts extends AppModel {

    @SerializedName("data")
    CountData countData;

    public class CountData{

        @SerializedName("ProductEnquiry")
        Enquiry productEnquiry;

        @SerializedName("ProductRegistration")
        Enquiry productRegistration;

        @SerializedName("BusinessEnquiry")
        Enquiry businessEnquiry;

        @SerializedName("CustomerComplaint")
        Enquiry customerComplain;

        @SerializedName("Career")
        Enquiry career;

        @SerializedName("CorporateOffice")
        Enquiry corporateOffice;

        @SerializedName("Quotation")
        Enquiry quotation;

        @SerializedName("Seller")
        Enquiry seller;

        public Enquiry getProductEnquiry() {
            return productEnquiry;
        }

        public Enquiry getProductRegistration() {
            return productRegistration;
        }

        public Enquiry getBusinessEnquiry() {
            return businessEnquiry;
        }

        public Enquiry getCustomerComplain() {
            return customerComplain;
        }

        public Enquiry getCareer() {
            return career;
        }

        public Enquiry getCorporateOffice() {
            return corporateOffice;
        }

        public Enquiry getQuotation() {
            return quotation;
        }

        public Enquiry getSeller() {
            return seller;
        }
    }

    public class Enquiry{
        @SerializedName("total_count")
        int count=0;

        public int getCount() {
            return count;
        }
    }

    public CountData getCountData() {
        return countData;
    }
}
