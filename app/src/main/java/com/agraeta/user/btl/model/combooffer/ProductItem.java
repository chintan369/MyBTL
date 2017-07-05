package com.agraeta.user.btl.model.combooffer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nivida new on 24-Apr-17.
 */

public class ProductItem {
    @SerializedName("id")
    String productID="";

    @SerializedName("category_id")
    String categoryID="";

    @SerializedName("product_code")
    String productCode="";

    @SerializedName("product_name")
    String productName="";

    @SerializedName("mrp")
    String mrpPrice="";

    @SerializedName("selling_price")
    String sellingPrice="";

    @SerializedName("pack_of_qty")
    String minPackOfQty="";

    @SerializedName("Label")
    Label label;

    @SerializedName("ProductOption")
    List<ProductOption> productOptionList;

    String extraDiscount = "0.00";

    int quantity=0;

    public String getProductID() {
        return productID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public String getMrpPrice() {
        return mrpPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getMinPackOfQty() {
        return minPackOfQty;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExtraDiscount() {
        return extraDiscount;
    }

    public void setExtraDiscount(String extraDiscount) {
        this.extraDiscount = extraDiscount;
    }

    public Label getLabel() {
        return label;
    }

    public List<ProductOption> getProductOption() {
        return productOptionList;
    }

    public class Label{
        String name="";

        public String getName() {
            return name;
        }
    }

    public class ProductOption{

        String id="";
        String option_id="";
        String option_value_id="";

        @SerializedName("Option")
        Option option;

        @SerializedName("OptionValue")
        Option optionValue;

        public String getId() {
            return id;
        }

        public String getOption_id() {
            return option_id;
        }

        public String getOption_value_id() {
            return option_value_id;
        }

        public Option getOption() {
            return option;
        }

        public Option getOptionValue() {
            return optionValue;
        }
    }

    public class Option{
        String id="";
        String name="";

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
