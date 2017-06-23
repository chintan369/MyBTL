package com.agraeta.user.btl.CompanySalesPerson;

/**
 * Created by chaitalee on 9/21/2016.
 */
public class Bean_invoice {
    String ino = "";
    String idate="";
    String ipdf="";
    String itotal="";

    public Bean_invoice() {
    }

    public Bean_invoice(String ino, String idate, String ipdf, String itotal) {
        this.ino = ino;
        this.idate = idate;
        this.ipdf = ipdf;
        this.itotal = itotal;
    }

    public String getIno() {
        return ino;
    }

    public void setIno(String ino) {
        this.ino = ino;
    }

    public String getIdate() {
        return idate;
    }

    public void setIdate(String idate) {
        this.idate = idate;
    }

    public String getIpdf() {
        return ipdf;
    }

    public void setIpdf(String ipdf) {
        this.ipdf = ipdf;
    }

    public String getItotal() {
        return itotal;
    }

    public void setItotal(String itotal) {
        this.itotal = itotal;
    }
}
