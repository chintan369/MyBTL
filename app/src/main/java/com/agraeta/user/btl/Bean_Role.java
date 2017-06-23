package com.agraeta.user.btl;

/**
 * Created by chaitalee on 6/21/2016.
 */
public class Bean_Role {

    String role_id;
    String role_name;

    public Bean_Role(String role_id, String role_name) {
        this.role_id = role_id;
        this.role_name = role_name;
    }

    public Bean_Role() {

    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
