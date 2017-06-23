package com.agraeta.user.btl.Distributor;

/**
 * Created by chaitalee on 9/19/2016.
 */
public class Bean_Balance {

    int balance_id;
    int user_id;
    String outstandingRs;
    String outstandingName;
    String dueRs;
    String dueName;

    public Bean_Balance() {
    }

    public Bean_Balance(int balance_id, int user_id, String outstandingRs, String outstandingName, String dueRs, String dueName) {
        this.balance_id = balance_id;
        this.user_id = user_id;
        this.outstandingRs = outstandingRs;
        this.outstandingName = outstandingName;
        this.dueRs = dueRs;
        this.dueName = dueName;
    }

    public int getBalance_id() {
        return balance_id;
    }

    public void setBalance_id(int balance_id) {
        this.balance_id = balance_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOutstandingRs() {
        return outstandingRs;
    }

    public void setOutstandingRs(String outstandingRs) {
        this.outstandingRs = outstandingRs;
    }

    public String getOutstandingName() {
        return outstandingName;
    }

    public void setOutstandingName(String outstandingName) {
        this.outstandingName = outstandingName;
    }

    public String getDueRs() {
        return dueRs;
    }

    public void setDueRs(String dueRs) {
        this.dueRs = dueRs;
    }

    public String getDueName() {
        return dueName;
    }

    public void setDueName(String dueName) {
        this.dueName = dueName;
    }
}
