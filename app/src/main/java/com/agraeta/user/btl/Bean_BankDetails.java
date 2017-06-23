package com.agraeta.user.btl;

/**
 * Created by chaitalee on 9/24/2016.
 */
public class Bean_BankDetails {

    private String user_id = new String();
    String bankDetailId;
    String titleLine;
    String bankName;
    String accountNo;
    String ifsCode;
    String branchName;
    String branchCode;

    public Bean_BankDetails() {
    }

    public Bean_BankDetails(String user_id, String bankDetailId, String titleLine, String bankName, String accountNo, String ifsCode, String branchName, String branchCode) {
        this.user_id = user_id;
        this.bankDetailId = bankDetailId;
        this.titleLine = titleLine;
        this.bankName = bankName;
        this.accountNo = accountNo;
        this.ifsCode = ifsCode;
        this.branchName = branchName;
        this.branchCode = branchCode;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBankDetailId() {
        return bankDetailId;
    }

    public void setBankDetailId(String bankDetailId) {
        this.bankDetailId = bankDetailId;
    }

    public String getTitleLine() {
        return titleLine;
    }

    public void setTitleLine(String titleLine) {
        this.titleLine = titleLine;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfsCode() {
        return ifsCode;
    }

    public void setIfsCode(String ifsCode) {
        this.ifsCode = ifsCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}
