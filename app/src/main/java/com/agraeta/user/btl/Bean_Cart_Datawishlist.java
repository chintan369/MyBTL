package com.agraeta.user.btl;

import com.agraeta.user.btl.model.combooffer.ComboCart;
import com.agraeta.user.btl.model.combooffer.ComboOfferItem;

/**
 * Created by chaitalee on 11/24/2016.
 */

public class Bean_Cart_Datawishlist {

    String quotationID = "";
    String isBTLProduct = "1";
    String cartID = "";
    boolean comboPack = false;
    ComboOfferItem comboData;
    ComboCart comboCart;
    private String id = new String();
    private String user_id = new String();
    private String role_id = new String();
    private String owner_id = new String();
    private String product_id = new String();
    private String category_id = new String();
    private String name = new String();
    private String pro_code = new String();
    private String quantity = new String();
    private String mrp = new String();
    private String selling_price = new String();
    private String option_id = new String();
    private String option_name = new String();
    private String option_value_id = new String();
    private String option_value_name = new String();
    private String item_total = new String();
    private String pro_scheme = new String();
    private String pack_of = new String();
    private String scheme_id = new String();
    private String scheme_title = new String();
    private String scheme_pack_id = new String();
    private String prod_img = new String();
    private String final_packOf = "";
    private int minPackOfQty=1;


    public Bean_Cart_Datawishlist(String id, String user_id, String role_id, String owner_id, String product_id, String category_id, String name, String pro_code, String quantity, String mrp, String selling_price, String option_id, String option_name, String option_value_id, String option_value_name, String item_total, String pro_scheme, String pack_of, String scheme_id, String scheme_title, String prod_img) {
        this.id = id;
        this.user_id = user_id;
        this.role_id = role_id;
        this.owner_id = owner_id;
        this.product_id = product_id;
        this.category_id = category_id;
        this.name = name;
        this.pro_code = pro_code;
        this.quantity = quantity;
        this.mrp = mrp;
        this.selling_price = selling_price;
        this.option_id = option_id;
        this.option_name = option_name;
        this.option_value_id = option_value_id;
        this.option_value_name = option_value_name;
        this.item_total = item_total;
        this.pro_scheme = pro_scheme;
        this.pack_of = pack_of;
        this.scheme_id = scheme_id;
        this.scheme_title = scheme_title;
        this.prod_img = prod_img;

    }

    public Bean_Cart_Datawishlist(String id, String user_id, String role_id, String owner_id, String product_id, String category_id, String name, String pro_code, String quantity, String mrp, String selling_price, String option_id, String option_name, String option_value_id, String option_value_name, String item_total, String pro_scheme, String pack_of, String scheme_id, String scheme_title, String scheme_pack_id, String prod_img) {
        this.id = id;
        this.user_id = user_id;
        this.role_id = role_id;
        this.owner_id = owner_id;
        this.product_id = product_id;
        this.category_id = category_id;
        this.name = name;
        this.pro_code = pro_code;
        this.quantity = quantity;
        this.mrp = mrp;
        this.selling_price = selling_price;
        this.option_id = option_id;
        this.option_name = option_name;
        this.option_value_id = option_value_id;
        this.option_value_name = option_value_name;
        this.item_total = item_total;
        this.pro_scheme = pro_scheme;
        this.pack_of = pack_of;
        this.scheme_id = scheme_id;
        this.scheme_title = scheme_title;
        this.scheme_pack_id = scheme_pack_id;
        this.prod_img = prod_img;
    }

    public Bean_Cart_Datawishlist() {
    }

    public int getMinPackOfQty() {
        return minPackOfQty;
    }

    public void setMinPackOfQty(int minPackOfQty) {
        this.minPackOfQty = minPackOfQty;
    }

    public String getScheme_pack_id() {
        return scheme_pack_id;
    }

    public void setScheme_pack_id(String scheme_pack_id) {
        this.scheme_pack_id = scheme_pack_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getOption_value_id() {
        return option_value_id;
    }

    public void setOption_value_id(String option_value_id) {
        this.option_value_id = option_value_id;
    }

    public String getOption_value_name() {
        return option_value_name;
    }

    public void setOption_value_name(String option_value_name) {
        this.option_value_name = option_value_name;
    }

    public String getItem_total() {
        return item_total;
    }

    public void setItem_total(String item_total) {
        this.item_total = item_total;
    }

    public String getPro_scheme() {
        return pro_scheme;
    }

    public void setPro_scheme(String pro_scheme) {
        this.pro_scheme = pro_scheme;
    }

    public String getPack_of() {
        return pack_of;
    }

    public void setPack_of(String pack_of) {
        this.pack_of = pack_of;
    }

    public String getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(String scheme_id) {
        this.scheme_id = scheme_id;
    }

    public String getScheme_title() {
        return scheme_title;
    }

    public void setScheme_title(String scheme_title) {
        this.scheme_title = scheme_title;
    }

    public String getProd_img() {
        return prod_img;
    }

    public void setProd_img(String prod_img) {
        this.prod_img = prod_img;
    }

    public boolean isComboPack() {
        return comboPack;
    }

    public void setComboPack(boolean comboPack) {
        this.comboPack = comboPack;
    }

    public ComboOfferItem getComboData() {
        return comboData;
    }

    public void setComboData(ComboOfferItem comboData) {
        this.comboData = comboData;
    }

    public ComboCart getComboCart() {
        return comboCart;
    }

    public void setComboCart(ComboCart comboCart) {
        this.comboCart = comboCart;
    }

    public String getQuotationID() {
        return quotationID;
    }

    public void setQuotationID(String quotationID) {
        this.quotationID = quotationID;
    }

    public String getIsBTLProduct() {
        return isBTLProduct;
    }

    public void setIsBTLProduct(String isBTLProduct) {
        this.isBTLProduct = isBTLProduct;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getFinal_packOf() {
        return final_packOf;
    }

    public void setFinal_packOf(String final_packOf) {
        this.final_packOf = final_packOf;
    }
}
