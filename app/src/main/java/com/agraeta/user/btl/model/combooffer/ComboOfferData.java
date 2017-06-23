package com.agraeta.user.btl.model.combooffer;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Apr-17.
 */

public class ComboOfferData extends AppModel {

    @SerializedName("data")
    List<ComboOfferItem> offerItemList=new ArrayList<>();

    public List<ComboOfferItem> getOfferItemList() {
        return offerItemList;
    }

}
