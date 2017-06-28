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
    User user = new User();
    RegisteredUserTourResponse.Distributor distributor = new RegisteredUserTourResponse.Distributor();
    List<RegisteredUserTourResponse.Address> addressList = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RegisteredUserTourResponse.Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(RegisteredUserTourResponse.Distributor distributor) {
        this.distributor = distributor;
    }

    public List<RegisteredUserTourResponse.Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<RegisteredUserTourResponse.Address> addressList) {
        this.addressList = addressList;
    }
}
