package com.agraeta.user.btl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 19-Aug-17.
 */

public class GetCartItemQuantityResponse extends AppModel {
    List<OrderListItem.OrderProduct> data = new ArrayList<>();

    public List<OrderListItem.OrderProduct> getData() {
        return data;
    }
}
