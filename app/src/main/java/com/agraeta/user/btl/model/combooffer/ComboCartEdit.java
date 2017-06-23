package com.agraeta.user.btl.model.combooffer;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nivida new on 26-Apr-17.
 */

public class ComboCartEdit extends AppModel {

    @SerializedName("data")
    CartEditData editData;

    public class  CartEditData{

        @SerializedName("ComboCart")
        ComboCart comboCart;

        @SerializedName("ComboPack")
        ComboOfferItem offerItem;

        public ComboCart getComboCart() {
            return comboCart;
        }

        public ComboOfferItem getOfferItem() {
            return offerItem;
        }
    }

    public CartEditData getEditData() {
        return editData;
    }
}
