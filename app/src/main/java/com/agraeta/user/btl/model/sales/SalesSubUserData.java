package com.agraeta.user.btl.model.sales;

import com.agraeta.user.btl.model.RegisteredUserTourResponse;
import com.agraeta.user.btl.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 28-Jun-17.
 */

public class SalesSubUserData implements Serializable {
    User User = new User();
    RegisteredUserTourResponse.Distributor Distributor = new RegisteredUserTourResponse.Distributor();
    List<RegisteredUserTourResponse.Address> Address = new ArrayList<>();

    public com.agraeta.user.btl.model.User getUser() {
        return User;
    }

    public void setUser(com.agraeta.user.btl.model.User user) {
        User = user;
    }

    public RegisteredUserTourResponse.Distributor getDistributor() {
        return Distributor;
    }

    public void setDistributor(RegisteredUserTourResponse.Distributor distributor) {
        Distributor = distributor;
    }

    public List<RegisteredUserTourResponse.Address> getAddress() {
        return Address;
    }

    public void setAddress(List<RegisteredUserTourResponse.Address> address) {
        Address = address;
    }
}
