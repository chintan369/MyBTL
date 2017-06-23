package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 22-Mar-17.
 */

public class Dashboard {
    @SerializedName("status")
    boolean status=false;

    @SerializedName("message")
    String message="";

    @SerializedName("data")
    Data data;

    public class Data{
        @SerializedName("Company_Order")
        DataOrder companyOrder;

        @SerializedName("Distributor")
        DataOrder distributorOrder;

        @SerializedName("Customer")
        DataCustomer customer;

        public DataOrder getCompanyOrder() {
            return companyOrder;
        }

        public DataCustomer getCustomer() {
            return customer;
        }

        public DataOrder getDistributorOrder() {
            return distributorOrder;
        }
    }

    public class DataOrder{
        @SerializedName("Total_order_count")
        String orderCount="";

        @SerializedName("Total_order_value")
        int orderValue=0;

        public String getOrderCount() {
            return orderCount;
        }

        public int getOrderValue() {
            return orderValue;
        }
    }

    public class DataCustomer{
        @SerializedName("Total_customer_count")
        int totalCustomers=0;

        public int getTotalCustomers() {
            return totalCustomers;
        }
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }
}
