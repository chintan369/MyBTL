package com.agraeta.user.btl;

/**
 * Created by chaitalee on 9/23/2016.
 */
public class Bean_Quotation_Info {

    private String title = "";
    private String fullname = "";
    private String emailto = "";
    private String emailcc = "";
    private String date = "";
    private String download = "";
    String firmName="";
    String ownerName="";



    public Bean_Quotation_Info() {
    }

    public Bean_Quotation_Info(String title, String fullname, String emailto, String emailcc, String date, String download) {
        this.title = title;
        this.fullname = fullname;
        this.emailto = emailto;
        this.emailcc = emailcc;
        this.date = date;
        this.download = download;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmailto() {
        return emailto;
    }

    public void setEmailto(String emailto) {
        this.emailto = emailto;
    }

    public String getEmailcc() {
        return emailcc;
    }

    public void setEmailcc(String emailcc) {
        this.emailcc = emailcc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getOwnerName() {
        return ownerName.replace("null","").trim();
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
