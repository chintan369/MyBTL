package com.agraeta.user.btl.Distributor;

/**
 * Created by chaitalee on 9/19/2016.
 */
public class Bean_Outstanding {
    int id;
    String outstanding_id;
    String outstandingName;
    String date;
    String startingDate;
    String endingDate;
    String refNo;
    String openingAmount;
    String pendingAmount;
    String openingType;
    String pendingType;
    String dueOn;
    String overDues;
    String totalOpeningAmt;
    String totalPendingAmt;
    String totalDays;
    String totalDue;

    public Bean_Outstanding() {
    }

    public Bean_Outstanding(int id, String outstanding_id, String outstandingName, String date, String startingDate, String endingDate, String refNo, String openingAmount, String pendingAmount,String openingType,String pendingType, String dueOn, String overDues, String totalOpeningAmt, String totalPendingAmt,String totalDays,String totalDue) {
        this.id = id;
        this.outstanding_id = outstanding_id;
        this.outstandingName = outstandingName;
        this.date = date;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.refNo = refNo;
        this.openingAmount = openingAmount;
        this.pendingAmount = pendingAmount;
        this.openingType=openingType;
        this.pendingType=pendingType;
        this.dueOn = dueOn;
        this.overDues = overDues;
        this.totalOpeningAmt = totalOpeningAmt;
        this.totalPendingAmt = totalPendingAmt;
    }

    public Bean_Outstanding(String outstanding_id, String outstandingName, String date, String startingDate, String endingDate, String refNo, String openingAmount, String pendingAmount,String openingType,String pendingType, String dueOn, String overDues, String totalOpeningAmt, String totalPendingAmt,String totalDays,String totalDue) {
        this.outstanding_id = outstanding_id;
        this.outstandingName = outstandingName;
        this.date = date;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.refNo = refNo;
        this.openingAmount = openingAmount;
        this.pendingAmount = pendingAmount;
        this.openingType=openingType;
        this.pendingType=pendingType;
        this.dueOn = dueOn;
        this.overDues = overDues;
        this.totalOpeningAmt = totalOpeningAmt;
        this.totalPendingAmt = totalPendingAmt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutstanding_id() {
        return outstanding_id;
    }

    public void setOutstanding_id(String outstanding_id) {
        this.outstanding_id = outstanding_id;
    }

    public String getOutstandingName() {
        return outstandingName;
    }

    public void setOutstandingName(String outstandingName) {
        this.outstandingName = outstandingName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getOpeningAmount() {
        return openingAmount;
    }

    public void setOpeningAmount(String openingAmount) {
        this.openingAmount = openingAmount;
    }

    public String getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(String pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getDueOn() {
        return dueOn;
    }

    public void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    public String getOverDues() {
        return overDues;
    }

    public void setOverDues(String overDues) {
        this.overDues = overDues;
    }

    public String getTotalOpeningAmt() {
        return totalOpeningAmt;
    }

    public void setTotalOpeningAmt(String totalOpeningAmt) {
        this.totalOpeningAmt = totalOpeningAmt;
    }

    public String getTotalPendingAmt() {
        return totalPendingAmt;
    }

    public void setTotalPendingAmt(String totalPendingAmt) {
        this.totalPendingAmt = totalPendingAmt;
    }

    public String getPendingType() {
        return pendingType;
    }

    public void setPendingType(String pendingType) {
        this.pendingType = pendingType;
    }

    public String getOpeningType() {
        return openingType;
    }

    public void setOpeningType(String openingType) {
        this.openingType = openingType;
    }

    public String getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(String totalDue) {
        this.totalDue = totalDue;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }
}
