package com.agraeta.user.btl.model.sales;

import com.agraeta.user.btl.model.RegisteredUserTourResponse;

import java.io.Serializable;

/**
 * Created by Nivida new on 28-Jun-17.
 */

public class SalesSubUserDataSingleAddress extends SalesSubUser implements Serializable {

    RegisteredUserTourResponse.Address Address = new RegisteredUserTourResponse.Address();

    public RegisteredUserTourResponse.Address getAddress() {
        return Address;
    }

    public void setAddress(RegisteredUserTourResponse.Address address) {
        Address = address;
    }
}
