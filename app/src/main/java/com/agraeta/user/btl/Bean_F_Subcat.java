package com.agraeta.user.btl;

/**
 * Created by chaitalee on 4/26/2016.
 */
public class Bean_F_Subcat {

    private int id ;
    private String sub_cat_id = new String();
    private String sub_cat_name = new String();

    public Bean_F_Subcat(){

    }

    public Bean_F_Subcat(int id, String sub_cat_id, String sub_cat_name) {
        this.id = id;
        this.sub_cat_id = sub_cat_id;
        this.sub_cat_name = sub_cat_name;
    }

    public Bean_F_Subcat(String sub_cat_id, String sub_cat_name) {
        this.sub_cat_id = sub_cat_id;
        this.sub_cat_name = sub_cat_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }
}

