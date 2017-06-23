package com.agraeta.user.btl;

/**
 * Created by chaitalee on 4/27/2016.
 */
public class Bean_F_Sorting {

    private String sort_id = new String();
    private String sort_name = new String();

    public Bean_F_Sorting(){

    }

    public Bean_F_Sorting(String sort_id, String sort_name) {
        this.sort_id = sort_id;
        this.sort_name = sort_name;
    }

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }

    public String getSort_name() {
        return sort_name;
    }

    public void setSort_name(String sort_name) {
        this.sort_name = sort_name;
    }
}
