package com.agraeta.user.btl;

/**
 * Created by chaitalee on 4/26/2016.
 */
public class Bean_Filter_SubCat {

    private String sub_cat_id = new String();
    private String sub_cat_name = new String();

    public Bean_Filter_SubCat(){

    }


    public Bean_Filter_SubCat(String sub_cat_id, String sub_cat_name) {
        this.sub_cat_id = sub_cat_id;
        this.sub_cat_name = sub_cat_name;
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
