package com.agraeta.user.btl.model.enquiries;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 27-Mar-17.
 */

public class CareerData extends AppModel {

    @SerializedName("data")
    List<CareerDetail> careerDetailList=new ArrayList<>();

    public List<CareerDetail> getCareerDetailList() {
        return careerDetailList;
    }
}
