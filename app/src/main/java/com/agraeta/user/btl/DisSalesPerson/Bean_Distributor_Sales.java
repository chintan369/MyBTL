package com.agraeta.user.btl.DisSalesPerson;

import com.agraeta.user.btl.model.sales.SalesSubUserData;

/**
 * Created by chaitalee on 9/14/2016.
 */
public class Bean_Distributor_Sales {
    int dSalesID = 0;
    String dsRetId = "";
    String dsProId = "";
    String userDisSalesId = "";
    String firstname = "";
    String lastname = "";
    String areaId = "";
    String areaName = "";

    SalesSubUserData userData = new SalesSubUserData();

    public Bean_Distributor_Sales() {
    }

    public Bean_Distributor_Sales(int dSalesID, String dsRetId, String dsProId, String userDisSalesId, String firstname, String lastname, String areaId, String areaName) {
        this.dSalesID = dSalesID;
        this.dsRetId = dsRetId;
        this.dsProId = dsProId;
        this.userDisSalesId = userDisSalesId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.areaId = areaId;
        this.areaName = areaName;
    }

    public Bean_Distributor_Sales(String userDisSalesId, String firstname, String lastname, String areaId, String areaName) {
        this.userDisSalesId = userDisSalesId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.areaId = areaId;
        this.areaName = areaName;
    }

    public int getdSalesID() {
        return dSalesID;
    }

    public void setdSalesID(int dSalesID) {
        this.dSalesID = dSalesID;
    }

    public String getDsRetId() {
        return dsRetId;
    }

    public void setDsRetId(String dsRetId) {
        this.dsRetId = dsRetId;
    }

    public String getDsProId() {
        return dsProId;
    }

    public void setDsProId(String dsProId) {
        this.dsProId = dsProId;
    }

    public String getUserDisSalesId() {
        return userDisSalesId;
    }

    public void setUserDisSalesId(String userDisSalesId) {
        this.userDisSalesId = userDisSalesId;
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

    public SalesSubUserData getUserData() {
        return userData;
    }

    public void setUserData(SalesSubUserData userData) {
        this.userData = userData;
    }
}
