package com.agraeta.user.btl.model.area;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Nivida new on 10-May-17.
 */

public class AreaData extends AppModel {
    @SerializedName("data")
    ArrayList<AreaItem> areaItemList = new ArrayList<>();

    public ArrayList<AreaItem> getAreaItemList() {
        return areaItemList;
    }

    public ArrayList<String> getAreaNames() {
        ArrayList<String> names = new ArrayList<>();

        for (AreaItem item : areaItemList) {
            names.add(item.getName());
        }

        return names;
    }
}
