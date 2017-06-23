package com.agraeta.user.btl;

import java.util.ArrayList;

/**
 * Created by chaitalee on 4/26/2016.
 */
public class Bean_Filter_option {

    private String opt_id = new String();
    private String opt_name = new String();
    private ArrayList<Bean_Filter_value> value_list = new ArrayList<Bean_Filter_value>();

    public Bean_Filter_option(){

    }

    public Bean_Filter_option(String opt_id, String opt_name, ArrayList<Bean_Filter_value> value_list) {
        this.opt_id = opt_id;
        this.opt_name = opt_name;
        this.value_list = value_list;
    }

    public String getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(String opt_id) {
        this.opt_id = opt_id;
    }

    public String getOpt_name() {
        return opt_name;
    }

    public void setOpt_name(String opt_name) {
        this.opt_name = opt_name;
    }

    public ArrayList<Bean_Filter_value> getValue_list() {
        return value_list;
    }

    public void setValue_list(ArrayList<Bean_Filter_value> value_list) {
        this.value_list = value_list;
    }
}
