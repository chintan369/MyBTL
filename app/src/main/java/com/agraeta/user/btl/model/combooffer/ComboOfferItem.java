package com.agraeta.user.btl.model.combooffer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 25-Apr-17.
 */

public class ComboOfferItem{

    @SerializedName("banner_image")
    String imagePath="";

    @SerializedName("combo_name")
    String offerTitle="";

    @SerializedName("title_in_invoice")
    String titleInInvoice="";

    @SerializedName("id")
    String offerID="";

    @SerializedName("min_qty")
    String minQty="";

    String discount_percentage = "0.00";

    @SerializedName("ProductComboPack")
    List<ComboOfferDetail.ComboProduct> comboProductList=new ArrayList<>();

    public String getImagePath() {
        return imagePath;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public String getTitleInInvoice() {
        return titleInInvoice;
    }

    public String getOfferID() {
        return offerID;
    }

    public String getMinQty() {
        return minQty;
    }

    public List<ComboOfferDetail.ComboProduct> getComboProductList() {
        return comboProductList;
    }

    public String getDiscount_percentage() {
        return discount_percentage;
    }
}
