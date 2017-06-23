package com.agraeta.user.btl.model.combooffer;

import com.agraeta.user.btl.model.AppModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 24-Apr-17.
 */

public class ComboOfferDetail extends AppModel {

    @SerializedName("data")
    ComboData comboData;

    public class ComboData{
        @SerializedName("ComboPack")
        ComboOfferItem offerItem;

        @SerializedName("ProductComboPack")
        List<ComboProduct> comboProductList=new ArrayList<>();

        public ComboOfferItem getOfferItem() {
            return offerItem;
        }

        public void setOfferItem(ComboOfferItem offerItem){
            this.offerItem=offerItem;
        }

        public List<ComboProduct> getComboProductList() {
            return comboProductList;
        }
    }

    public class ComboProduct{
        @SerializedName("id")
        String id="";

        @SerializedName("product_id")
        String productID="";

        @SerializedName("Product")
        ProductItem product;

        boolean checked=false;

        public String getId() {
            return id;
        }

        public String getProductID() {
            return productID;
        }

        public ProductItem getProduct() {
            return product;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

    public ComboData getComboData() {
        return comboData;
    }

    public void initComboData(){
        this.comboData=new ComboData();
    }
}
