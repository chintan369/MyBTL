package com.agraeta.user.btl;

/**
 * Created by chaitalee on 4/26/2016.
 */
public class Bean_F_Cat {

    private int id ;
    private String cat_id = new String();
    private String cat_name = new String();

    public Bean_F_Cat(){

    }

    public Bean_F_Cat(int id, String cat_id, String cat_name) {
        this.id = id;
        this.cat_id = cat_id;
        this.cat_name = cat_name;
    }

    public Bean_F_Cat(String cat_id, String cat_name) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
}
