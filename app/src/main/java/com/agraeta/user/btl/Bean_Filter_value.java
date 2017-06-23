package com.agraeta.user.btl;

/**
 * Created by chaitalee on 4/26/2016.
 */
public class Bean_Filter_value {

    private String value_id = new String();
    private String value_name = new String();
    private String value_opt_id = new String();

    public Bean_Filter_value(){

    }

    public Bean_Filter_value(String value_id, String value_name, String value_opt_id) {
        this.value_id = value_id;
        this.value_name = value_name;
        this.value_opt_id = value_opt_id;
    }

    public String getValue_id() {
        return value_id;
    }

    public void setValue_id(String value_id) {
        this.value_id = value_id;
    }

    public String getValue_name() {
        return value_name;
    }

    public void setValue_name(String value_name) {
        this.value_name = value_name;
    }

    public String getValue_opt_id() {
        return value_opt_id;
    }

    public void setValue_opt_id(String value_opt_id) {
        this.value_opt_id = value_opt_id;
    }
}
