package com.agraeta.user.btl.model.sales;

import com.agraeta.user.btl.model.RegisteredUserTourResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 28-Jun-17.
 */

public class SalesSubUserData extends SalesSubUser implements Serializable {

    List<RegisteredUserTourResponse.Address> Address = new ArrayList<>();

    public List<RegisteredUserTourResponse.Address> getAddress() {
        return Address;
    }

    public void setAddress(List<RegisteredUserTourResponse.Address> address) {
        Address = address;
    }
}
