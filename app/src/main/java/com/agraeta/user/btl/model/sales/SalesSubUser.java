package com.agraeta.user.btl.model.sales;

import com.agraeta.user.btl.model.RegisteredUserTourResponse;
import com.agraeta.user.btl.model.User;

import java.io.Serializable;

/**
 * Created by Nivida new on 28-Jun-17.
 */

public class SalesSubUser implements Serializable {
    User User = new User();
    RegisteredUserTourResponse.Distributor Distributor = new RegisteredUserTourResponse.Distributor();

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
}
