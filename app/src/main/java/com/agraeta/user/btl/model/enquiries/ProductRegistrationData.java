package com.agraeta.user.btl.model.enquiries;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class ProductRegistrationData extends AppModel {

    @SerializedName("data")
    List<ProductRegistrationDetail> registrationDetailList=new ArrayList<>();

    public List<ProductRegistrationDetail> getRegistrationDetailList() {
        return registrationDetailList;
    }
}
