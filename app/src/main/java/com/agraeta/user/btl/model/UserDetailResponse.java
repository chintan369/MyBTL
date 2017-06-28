package com.agraeta.user.btl.model;

import com.agraeta.user.btl.model.sales.SalesSubUserData;

/**
 * Created by Nivida new on 28-Jun-17.
 */

public class UserDetailResponse extends AppModel {
    SalesSubUserData data = new SalesSubUserData();

    public SalesSubUserData getData() {
        return data;
    }
}
