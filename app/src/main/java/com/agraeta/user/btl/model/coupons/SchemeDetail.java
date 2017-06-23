package com.agraeta.user.btl.model.coupons;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nivida new on 28-Mar-17.
 */

public class SchemeDetail {

    @SerializedName("Scheme")
    Scheme scheme;

    @SerializedName("BuyProduct")
    Product buyProduct;

    @SerializedName("GetProduct")
    Product getFreeProduct;

    public Scheme getScheme() {
        return scheme;
    }

    public Product getBuyProduct() {
        return buyProduct;
    }

    public Product getGetFreeProduct() {
        return getFreeProduct;
    }

    public class Scheme{
        @SerializedName("id")
        String schemeID="";

        @SerializedName("scheme_name")
        String schemeName="";

        @SerializedName("buy_prod_qty")
        String buyQuantity="";

        @SerializedName("get_prod_qty")
        String getFreeQuantity="";

        @SerializedName("max_qty")
        String maxGetQuantity="";

        @SerializedName("date_start")
        String startDate="";

        @SerializedName("date_end")
        String expiryDate="";

        @SerializedName("type_id")
        String schemeTypeID="";

        @SerializedName("discount_percentage")
        String discount_percentage="";

        @SerializedName("scheme_title")
        String schemeTitle="";

        @SerializedName("status")
        String status="0";

        @SerializedName("created")
        String onDate="";

        public String getSchemeID() {
            return schemeID;
        }

        public String getSchemeName() {
            return schemeName;
        }

        public String getBuyQuantity() {
            return buyQuantity;
        }

        public String getGetFreeQuantity() {
            return getFreeQuantity;
        }

        public String getMaxGetQuantity() {
            return maxGetQuantity;
        }

        public String getStartDate() {
            return getOnDate("yyyy-MM-dd","dd-MM-yyyy",startDate);
        }

        public String getExpiryDate() {
            return getOnDate("yyyy-MM-dd","dd-MM-yyyy",expiryDate);
        }

        public String getSchemeTypeID() {
            switch(schemeTypeID){
                case "1":
                    return "Buy X Get X Free";
                case "2":
                    return "Buy X Get Y Free";
                case "3":
                    return "Buy X Get on X (n% less)";
                default:
                    return "Buy X Get X Free";
            }
        }

        public String getDiscount_percentage() {
            return discount_percentage;
        }

        public String getSchemeTitle() {
            return schemeTitle;
        }

        public String getOnDate() {
            return getOnDate("dd/MM/yyyy","dd-MM-yyyy",onDate);
        }

        public String getOnDate(String givenFormat, String dateFormat,String dateGiven){
            SimpleDateFormat currentFormat=new SimpleDateFormat(givenFormat, Locale.getDefault());
            SimpleDateFormat requiredFormat=new SimpleDateFormat(dateFormat,Locale.getDefault());

            try {
                Date currentDate=currentFormat.parse(dateGiven.split(" ")[0]);
                return requiredFormat.format(currentDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return dateGiven;
            }
        }

        public String getStatus() {
            return status.equals("1") ? "Active" : "Inactive";
        }
    }

    public class Product{
        @SerializedName("product_name")
        String productName="";

        public String getProductName() {
            return productName;
        }
    }

}
