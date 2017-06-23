package com.agraeta.user.btl.model.enquiries;

import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.area.AreaItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 29-May-17.
 */

public class RegisteredDealerData extends AppModel {

    List<RegisteredDealer> data=new ArrayList<>();

    public List<RegisteredDealer> getData() {
        return data;
    }

    public class RegisteredDealer implements Serializable{
        @SerializedName("Seller")
        Seller Seller;

        AreaItem Role;
        AreaItem State;
        AreaItem City;

        public Seller getSeller() {
            return Seller;
        }

        public AreaItem getRole() {
            return Role;
        }

        public AreaItem getState() {
            return State;
        }

        public AreaItem getCity() {
            return City;
        }
    }
}
