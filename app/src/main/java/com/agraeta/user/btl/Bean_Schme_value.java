package com.agraeta.user.btl;

/**
 * Created by chaitalee on 9/29/2016.
 */
public class Bean_Schme_value {
    private String get_qty = new String();
    private String buy_qty = new String();
    private String max_qty = "";
    private String scheme_name = new String();
    private String disc_per = new String();
    private String type_id = new String();
    private String scheme_id = new String();

    public Bean_Schme_value(String get_qty, String buy_qty, String max_qty) {
        this.get_qty = get_qty;
        this.buy_qty = buy_qty;
        this.max_qty = max_qty;
    }

    public Bean_Schme_value(String get_qty, String buy_qty, String max_qty, String scheme_name) {
        this.get_qty = get_qty;
        this.buy_qty = buy_qty;
        this.max_qty = max_qty;
        this.scheme_name = scheme_name;
    }

    public Bean_Schme_value() {
    }

    public Bean_Schme_value(String get_qty, String buy_qty, String max_qty, String scheme_name, String disc_per, String type_id) {
        this.get_qty = get_qty;
        this.buy_qty = buy_qty;
        this.max_qty = max_qty;
        this.scheme_name = scheme_name;
        this.disc_per = disc_per;
        this.type_id = type_id;
    }

    public Bean_Schme_value(String get_qty, String buy_qty, String max_qty, String scheme_name, String disc_per, String type_id, String scheme_id) {
        this.get_qty = get_qty;
        this.buy_qty = buy_qty;
        this.max_qty = max_qty;
        this.scheme_name = scheme_name;
        this.disc_per = disc_per;
        this.type_id = type_id;
        this.scheme_id = scheme_id;
    }

    public String getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(String scheme_id) {
        this.scheme_id = scheme_id;
    }

    public String getDisc_per() {
        return disc_per;
    }

    public void setDisc_per(String disc_per) {
        this.disc_per = disc_per;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getGet_qty() {
        return get_qty;
    }

    public void setGet_qty(String get_qty) {
        this.get_qty = get_qty;
    }

    public String getBuy_qty() {
        return buy_qty;
    }

    public void setBuy_qty(String buy_qty) {
        this.buy_qty = buy_qty;
    }

    public String getMax_qty() {
        return max_qty==null ? "" : max_qty;
    }

    public void setMax_qty(String max_qty) {
        this.max_qty = max_qty;
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public void setScheme_name(String scheme_name) {
        this.scheme_name = scheme_name;
    }
}
