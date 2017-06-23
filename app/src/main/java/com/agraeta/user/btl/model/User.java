package com.agraeta.user.btl.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class User implements Serializable {

    @SerializedName("id")
    String id;
    @SerializedName("role_id")
    String role_id;
    @SerializedName(value = "email_id",alternate = "email")
    String email_id;
    @SerializedName(value = "phone_no",alternate = "contact_no")
    String phone_no;
    @SerializedName("first_name")
    String first_name;
    @SerializedName("last_name")
    String last_name;
    @SerializedName("created")
    String registeredOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getFirst_name() {
        return first_name==null ? "" : first_name;
    }

    public String getName(){
        return first_name+" "+last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name==null ? "" : last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }
}
