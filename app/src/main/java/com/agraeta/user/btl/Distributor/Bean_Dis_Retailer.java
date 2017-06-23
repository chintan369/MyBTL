package com.agraeta.user.btl.Distributor;

import java.io.Serializable;

/**
 * Created by SEO on 8/24/2016.
 */
public class Bean_Dis_Retailer implements Serializable {
    private String dis_ret_user_id = new String();
    int retailer_id;
    int role_id;
    String firstName="";
    String lastName="";
    String firmName="";
    String creditLimit="";
    String creditPeriod="";
    String address1="";
    String address2="";
    String address3="";
    String pincode="";
    String country="";
    String state="";
    String city="";
    String area="";
    String contactPersonName="";
    String telephoneNo="";
    String emailid="";
    String phone_no="";
    String altPhone_no="";
    //String password;
    String status="0";
    String pancardno="";
    String vatno="";
    String serviceTaxNo="";

    public Bean_Dis_Retailer() {
    }

    public Bean_Dis_Retailer(int retailer_id, int role_id, String firstName, String lastName, String firmName, String creditLimit, String creditPeriod, String address1, String address2, String address3, String pincode, String country, String state, String city, String area, String contactPersonName, String telephoneNo, String emailid, String phone_no, String altPhone_no, String status, String pancardno, String vatno, String serviceTaxNo) {
        this.retailer_id = retailer_id;
        this.role_id = role_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.firmName = firmName;
        this.creditLimit=creditLimit;
        this.creditPeriod=creditPeriod;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.pincode = pincode;
        this.country = country;
        this.state = state;
        this.city = city;
        this.area = area;
        this.contactPersonName = contactPersonName;
        this.telephoneNo = telephoneNo;
        this.emailid = emailid;
        this.phone_no = phone_no;
        this.altPhone_no = altPhone_no;
        this.status = status;
        this.pancardno = pancardno;
        this.vatno = vatno;
        this.serviceTaxNo=serviceTaxNo;
    }

    public Bean_Dis_Retailer(String dis_ret_user_id, int retailer_id, int role_id, String firstName, String lastName, String firmName, String creditLimit, String creditPeriod, String address1, String address2, String address3, String pincode, String country, String state, String city, String area, String contactPersonName, String telephoneNo, String emailid, String phone_no, String altPhone_no, String status, String pancardno,  String vatno, String serviceTaxNo) {
        this.dis_ret_user_id = dis_ret_user_id;
        this.retailer_id = retailer_id;
        this.role_id = role_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.firmName = firmName;
        this.creditLimit=creditLimit;
        this.creditPeriod=creditPeriod;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.pincode = pincode;
        this.country = country;
        this.state = state;
        this.city = city;
        this.area = area;
        this.contactPersonName = contactPersonName;
        this.telephoneNo = telephoneNo;
        this.emailid = emailid;
        this.phone_no = phone_no;
        this.altPhone_no = altPhone_no;
        this.status = status;
        this.pancardno = pancardno;
        this.vatno = vatno;
        this.serviceTaxNo=serviceTaxNo;
    }



    public String getDis_ret_user_id() {
        return dis_ret_user_id;
    }

    public void setDis_ret_user_id(String dis_ret_user_id) {
        this.dis_ret_user_id = dis_ret_user_id;
    }

    public int getRetailer_id() {
        return retailer_id;
    }

    public void setRetailer_id(int retailer_id) {
        this.retailer_id = retailer_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getAltPhone_no() {
        return altPhone_no;
    }

    public void setAltPhone_no(String altPhone_no) {
        this.altPhone_no = altPhone_no;
    }

    public String getPancardno() {
        return pancardno;
    }

    public void setPancardno(String pancardno) {
        this.pancardno = pancardno;
    }

    public String getVatno() {
        return vatno;
    }

    public void setVatno(String vatno) {
        this.vatno = vatno;
    }

    public String getServiceTaxNo() {
        return serviceTaxNo;
    }

    public void setServiceTaxNo(String serviceTaxNo) {
        this.serviceTaxNo = serviceTaxNo;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(String creditPeriod) {
        this.creditPeriod = creditPeriod;
    }
}
