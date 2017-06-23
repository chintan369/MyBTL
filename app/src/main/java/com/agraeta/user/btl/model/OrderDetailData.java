package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 20-Mar-17.
 */

public class OrderDetailData {
    @SerializedName("status")
    boolean status=false;

    @SerializedName("message")
    String message="";

    @SerializedName("data")
    OrderDetail orderDetail;

    public class OrderDetail{

        @SerializedName("Order")
        OrderListItem.Order order;

        @SerializedName("OrderProduct")
        List<OrderListItem.OrderProduct> orderProductList=new ArrayList<>();

        public OrderListItem.Order getOrder() {
            return order;
        }

        public List<OrderListItem.OrderProduct> getOrderProductList() {
            return orderProductList;
        }

        public float getSubTotalAmount(){
            float subTotalAmount=0;

            for(int i=0; i<orderProductList.size(); i++){
                String subTotalString=orderProductList.get(i).getItem_total();
                subTotalAmount += Float.parseFloat(subTotalString.isEmpty() ? "0" : subTotalString);
            }

            return subTotalAmount;
        }
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }
}
