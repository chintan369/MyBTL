package com.agraeta.user.btl.CompanySalesPerson;

import com.agraeta.user.btl.model.sales.SalesSubUserData;

import java.io.Serializable;

/**
 * Created by SEO on 9/1/2016.
 */
public class Bean_Company_Sales_User implements Serializable {

    int cSalesID = 0;
    String csDisId = "";
    String csRetId = "";
    String csProId = "";
    String userSalesId = "";
    String firstname = "";
    String lastname = "";
    String areaId = "";
    String areaNameId = "";
    String areaName = "";
    String countryId = "";
    String countryNameId = "";
    String countryName = "";
    SalesSubUserData UserData = new SalesSubUserData();

    public Bean_Company_Sales_User() {
    }

    public Bean_Company_Sales_User(int cSalesID, String csDisId, String csRetId, String csProId, String userSalesId, String firstname, String lastname,String areaId,String areaNameId,String areaName) {
        this.cSalesID = cSalesID;
        this.csDisId = csDisId;
        this.csRetId = csRetId;
        this.csProId = csProId;
        this.userSalesId = userSalesId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.areaId=areaId;
        this.areaNameId=areaNameId;
        this.areaName=areaName;
    }

    public Bean_Company_Sales_User(String csDisId, String csRetId, String csProId, String userSalesId, String firstname, String lastname,String areaId,String areaNameId, String areaName) {
        this.csDisId = csDisId;
        this.csRetId = csRetId;
        this.csProId = csProId;
        this.userSalesId = userSalesId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.areaId=areaId;
        this.areaNameId=areaNameId;
        this.areaName=areaName;
    }

    public Bean_Company_Sales_User(int cSalesID, String userSalesId, String firstname, String lastname,String areaId,String areaNameId, String areaName) {
        this.cSalesID = cSalesID;
        this.userSalesId = userSalesId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.areaId=areaId;
        this.areaNameId=areaNameId;
        this.areaName=areaName;
    }

    public int getcSalesID() {
        return cSalesID;
    }

    public void setcSalesID(int cSalesID) {
        this.cSalesID = cSalesID;
    }

    public String getCsDisId() {
        return csDisId;
    }

    public void setCsDisId(String csDisId) {
        this.csDisId = csDisId;
    }

    public String getCsRetId() {
        return csRetId;
    }

    public void setCsRetId(String csRetId) {
        this.csRetId = csRetId;
    }

    public String getCsProId() {
        return csProId;
    }

    public void setCsProId(String csProId) {
        this.csProId = csProId;
    }

    public String getUserSalesId() {
        return userSalesId;
    }

    public void setUserSalesId(String userSalesId) {
        this.userSalesId = userSalesId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaNameId() {
        return areaNameId;
    }

    public void setAreaNameId(String areaNameId) {
        this.areaNameId = areaNameId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryNameId() {
        return countryNameId;
    }

    public void setCountryNameId(String countryNameId) {
        this.countryNameId = countryNameId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public SalesSubUserData getUserData() {
        return UserData;
    }

    public void setUserData(SalesSubUserData userData) {
        UserData = userData;
    }
}
