package com.agraeta.user.btl;

/**
 * Created by prince on 4/30/2016.
 */
public class Bean_Address {

    String address_id;
    String firstName;
    String mobile;
    String address_1;
    String address_2;
    String address_3;
    String landmark;
    String pincode;
    String country;
    String state;
    String city;
    String state_id;
    String city_id;
    String defult_add;


    public Bean_Address() {
    }

    public Bean_Address(String address_id,String firstName, String mobile, String address_1, String address_2, String address_3, String landmark, String pincode, String country, String state, String city, String state_id, String city_id, String defult_add) {
        this.address_id = address_id;
        this.firstName=firstName;
        this.mobile=mobile;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.address_3 = address_3;
        this.landmark = landmark;
        this.pincode = pincode;
        this.country = country;
        this.state = state;
        this.city = city;
        this.state_id = state_id;
        this.city_id = city_id;
        this.defult_add = defult_add;
    }

    public Bean_Address(String address_id,String firstName, String mobile, String address_1, String address_2, String address_3, String landmark, String pincode, String country, String state, String city) {
        this.address_id = address_id;
        this.firstName=firstName;
        this.mobile=mobile;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.address_3 = address_3;
        this.landmark = landmark;
        this.pincode = pincode;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public Bean_Address(String address_id,String firstName,  String mobile, String address_1, String address_2, String address_3, String landmark, String pincode, String country, String state, String city, String state_id, String city_id) {
        this.address_id = address_id;
        this.firstName=firstName;
        this.mobile=mobile;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.address_3 = address_3;
        this.landmark = landmark;
        this.pincode = pincode;
        this.country = country;
        this.state = state;
        this.city = city;
        this.state_id = state_id;
        this.city_id = city_id;
    }

    public String getDefult_add() {
        return defult_add;
    }

    public void setDefult_add(String defult_add) {
        this.defult_add = defult_add;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getAddress_3() {
        return address_3;
    }

    public void setAddress_3(String address_3) {
        this.address_3 = address_3;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
