package com.agraeta.user.btl.CompanySalesPerson;

/**
 * Created by chaitalee on 9/21/2016.
 */
public class Bean_Otracking {

    String oid = "";
    String ostatus="";
    String odate="";
    String ocomment="";

    public Bean_Otracking() {
    }

    public Bean_Otracking(String oid, String ostatus, String odate, String ocomment) {
        this.oid = oid;
        this.ostatus = ostatus;
        this.odate = odate;
        this.ocomment = ocomment;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOstatus() {
        return ostatus;
    }

    public void setOstatus(String ostatus) {
        this.ostatus = ostatus;
    }

    public String getOdate() {
        return odate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public String getOcomment() {
        return ocomment;
    }

    public void setOcomment(String ocomment) {
        this.ocomment = ocomment;
    }
}
