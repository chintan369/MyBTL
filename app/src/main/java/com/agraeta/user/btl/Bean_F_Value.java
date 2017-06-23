package com.agraeta.user.btl;

/**
 * Created by chaitalee on 4/26/2016.
 */
public class Bean_F_Value {

    private int id ;
    private String value_id = new String();
    private String value_name = new String();
    private String value_Opt_name = new String();

    public Bean_F_Value(){

    }

    public Bean_F_Value(int id, String value_id, String value_name, String value_Opt_name) {
        this.id = id;
        this.value_id = value_id;
        this.value_name = value_name;
        this.value_Opt_name = value_Opt_name;
    }

    public Bean_F_Value(String value_id, String value_name, String value_Opt_name) {
        this.value_id = value_id;
        this.value_name = value_name;
        this.value_Opt_name = value_Opt_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getValue_Opt_name() {
        return value_Opt_name;
    }

    public void setValue_Opt_name(String value_Opt_name) {
        this.value_Opt_name = value_Opt_name;
    }
}
