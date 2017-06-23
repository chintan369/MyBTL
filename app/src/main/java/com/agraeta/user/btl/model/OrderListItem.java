package com.agraeta.user.btl.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderListItem{

    @SerializedName("Order")
    public Order order;

    @SerializedName("OrderStatus")
    public OrderStatus orderStatus;

    @SerializedName("Owner")
    public Owner owner;

    public class Order{
        @SerializedName("id")
        String id="";

        @SerializedName("email")
        String emailID="";

        @SerializedName("mobile")
        String mobile="";

        @SerializedName("attachment_1")
        String attchmentPath="";

        @SerializedName("comment")
        String comment="";

        @SerializedName("payment_firstname")
        String paymentFirstName="";

        @SerializedName("payment_lastname")
        String paymentLastName="";

        @SerializedName("payment_address_1")
        String paymentAddress1="";

        @SerializedName("payment_address_2")
        String paymentAddress2="";

        @SerializedName("payment_address_3")
        String paymentAddress3="";

        @SerializedName("payment_city")
        String paymentCity="";

        @SerializedName("payment_postcode")
        String paymentPostal="";

        @SerializedName("payment_state")
        String paymentState="";

        @SerializedName("payment_country")
        String paymentCountry="";

        @SerializedName("shipping_firstname")
        String shippingFirstName="";

        @SerializedName("shipping_lastname")
        String shippingLastName="";

        @SerializedName("shipping_address_1")
        String shippingAddress1="";

        @SerializedName("shipping_address_2")
        String shippingAddress2="";

        @SerializedName("shipping_address_3")
        String shippingAddress3="";

        @SerializedName("shipping_city")
        String shippingCity="";

        @SerializedName("shipping_postcode")
        String shippingPostal="";

        @SerializedName("shipping_state")
        String shippingState="";

        @SerializedName("shipping_country")
        String shippingCountry="";

        @SerializedName("order_pdf")
        String orderPDF="";

        @SerializedName("payment_code")
        String paymentMethod="";

        @SerializedName("order_status_id")
        String orderStatus="";

        @SerializedName("discount_amount")
        String discountAmount="";

        @SerializedName("total")
        String total="";

        @SerializedName("firstname")
        String firstname="";

        @SerializedName("lastname")
        String lastname="";

        @SerializedName("created")
        String orderDate="";

        public String getName(){
            return firstname+" "+lastname;
        }

        public String getId() {
            return id;
        }

        public String getTotal() {
            return total;
        }

        public String getDiscountAmount() {
            return discountAmount;
        }

        public String getOrderDate(String dateFormat){
            SimpleDateFormat currentFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat requiredFormat=new SimpleDateFormat(dateFormat,Locale.getDefault());

            try {
                Date currentDate=currentFormat.parse(orderDate.split(" ")[0]);
                return requiredFormat.format(currentDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return orderDate;
            }
        }

        public String getOrderDate(String givenFormat,String dateFormat){
            SimpleDateFormat currentFormat=new SimpleDateFormat(givenFormat, Locale.getDefault());
            SimpleDateFormat requiredFormat=new SimpleDateFormat(dateFormat,Locale.getDefault());

            try {
                Date currentDate=currentFormat.parse(orderDate.split(" ")[0]);
                return requiredFormat.format(currentDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return orderDate;
            }
        }

        public String getEmailID() {
            return emailID;
        }

        public String getMobile() {
            return mobile;
        }

        public String getPaymentName() {
            return paymentFirstName+" "+paymentLastName;
        }

        public String getShippingName() {
            return shippingFirstName+" "+shippingLastName;
        }

        public String getPaymentAddress() {
            return paymentAddress1+", "+paymentAddress2+", "+paymentAddress3+",\n"+paymentCity+"-"+paymentPostal+
                    "\n"+paymentState+" - "+paymentCountry;
        }

        public String getShippingAddress() {
            return shippingAddress1+", "+shippingAddress2+", "+shippingAddress3+",\n"+shippingCity+"-"+shippingPostal+
                    "\n"+shippingState+" - "+shippingCountry;
        }

        public String getOrderPDF() {
            return orderPDF;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public String getAttchmentPath() {
            return attchmentPath==null ? "" : attchmentPath.replace("null","");
        }

        public String getComment() {
            return comment;
        }
    }

    public class Owner{
        @SerializedName("first_name")
        String firstName="";

        @SerializedName("last_name")
        String lastName="";

        public String getOwnedBy(){

            //firstName=firstName.replace("null","");
            //lastName=lastName.replace("null","");

            if((firstName==null && lastName==null) || (firstName!=null && firstName.isEmpty() && lastName.isEmpty())) return "Self";

            return firstName+" "+lastName;
        }
    }

    public class OrderStatus{
        @SerializedName("order_status_name")
        String status="";

        public String getStatus() {
            return status;
        }
    }

    public class OrderProduct{
        @SerializedName("id")
        String id="";
        @SerializedName("name")
        String name="";
        @SerializedName("pro_code")
        String code="";
        @SerializedName("quantity")
        String quantity="";
        @SerializedName("mrp")
        String mrp="";
        @SerializedName("selling_price")
        String selling_price="";
        @SerializedName("option_name")
        String option_name="";
        @SerializedName("option_value_name")
        String option_value_name="";
        @SerializedName("item_total")
        String item_total="";
        @SerializedName("pro_scheme")
        String scheme="";
        @SerializedName("pack_of")
        String pack_of="";
        @SerializedName("scheme_title")
        String schemeTitle="";

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getMrp() {
            return mrp;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public String getOptionValue() {
            String options=option_name+" : "+option_value_name;
            return options.replace("Select","").trim();
        }

        public String getItem_total() {
            return item_total;
        }

        public String getScheme() {
            return scheme;
        }

        public String getPack_of() {
            return pack_of;
        }

        public String getSchemeTitle() {
            return schemeTitle;
        }
    }


}
