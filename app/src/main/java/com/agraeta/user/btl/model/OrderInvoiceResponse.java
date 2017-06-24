package com.agraeta.user.btl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 24-Jun-17.
 */

public class OrderInvoiceResponse extends AppModel {
    List<OrderInvoice> order_invoice = new ArrayList<>();

    public List<OrderInvoice> getOrder_invoice() {
        return order_invoice;
    }
}
