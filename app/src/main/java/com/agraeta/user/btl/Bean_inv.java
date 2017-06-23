package com.agraeta.user.btl;

/**
 * Created by chaitalee on 12/21/2016.
 */

public class Bean_inv {

    private String order_id = new String();
    private String order_invoice = new String();

    public Bean_inv(String order_id, String order_invoice) {
        this.order_id = order_id;
        this.order_invoice = order_invoice;
    }

    public Bean_inv() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_invoice() {
        return order_invoice;
    }

    public void setOrder_invoice(String order_invoice) {
        this.order_invoice = order_invoice;
    }
}
