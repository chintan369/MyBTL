package com.agraeta.user.btl;

/**
 * Created by chaitalee on 12/15/2016.
 */

public class Bean_schemeData {

    private String schme_id = "";
    private String schme_name = "";
    private String schme_qty = "";
    private String schme_buy_prod_id = "";
    private String schme_prod_id = "";
    private String category_id="";

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public Bean_schemeData() {
    }

    public String getSchme_id() {
        return schme_id;
    }

    public void setSchme_id(String schme_id) {
        this.schme_id = schme_id;
    }

    public String getSchme_name() {
        return schme_name;
    }

    public void setSchme_name(String schme_name) {
        this.schme_name = schme_name;
    }

    public String getSchme_qty() {
        return schme_qty;
    }

    public void setSchme_qty(String schme_qty) {
        this.schme_qty = schme_qty;
    }

    public String getSchme_buy_prod_id() {
        return schme_buy_prod_id;
    }

    public void setSchme_buy_prod_id(String schme_buy_prod_id) {
        this.schme_buy_prod_id = schme_buy_prod_id;
    }

    public String getSchme_prod_id() {
        return schme_prod_id;
    }

    public void setSchme_prod_id(String schme_prod_id) {
        this.schme_prod_id = schme_prod_id;
    }
}
