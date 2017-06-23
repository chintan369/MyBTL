package com.agraeta.user.btl;

/**
 * Created by chaitalee on 1/12/2017.
 */

public class Bean_Status {
    private String pro_id = new String();
    private String pro_status = new String();

    public Bean_Status() {
    }

    public Bean_Status(String pro_id, String pro_status) {
        this.pro_id = pro_id;
        this.pro_status = pro_status;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_status() {
        return pro_status;
    }

    public void setPro_status(String pro_status) {
        this.pro_status = pro_status;
    }
}
