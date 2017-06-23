package com.agraeta.user.btl;

/**
 * Created by chaitalee on 5/3/2016.
 */
public class Bean_RecentView {

    private int id ;
    private String pro_id = new String();
    private String pro_name = new String();
    private String pro_Images = new String();
    private String pro_date = new String();

    public Bean_RecentView(int id, String pro_id, String pro_name, String pro_Images, String pro_date) {
        this.id = id;
        this.pro_id = pro_id;
        this.pro_name = pro_name;
        this.pro_Images = pro_Images;
        this.pro_date = pro_date;
    }

    public Bean_RecentView(String pro_id, String pro_name, String pro_Images, String pro_date) {
        this.pro_id = pro_id;
        this.pro_name = pro_name;
        this.pro_Images = pro_Images;
        this.pro_date = pro_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_Images() {
        return pro_Images;
    }

    public void setPro_Images(String pro_Images) {
        this.pro_Images = pro_Images;
    }

    public String getPro_date() {
        return pro_date;
    }

    public void setPro_date(String pro_date) {
        this.pro_date = pro_date;
    }
}

