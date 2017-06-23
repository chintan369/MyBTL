package com.agraeta.user.btl.model;

/**
 * Created by Nivida new on 07-Jun-17.
 */

public class AddLess {

    String title="";
    boolean isToAdd=false;
    String amount="";

    public AddLess() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isToAdd() {
        return isToAdd;
    }

    public void setToAdd(boolean toAdd) {
        isToAdd = toAdd;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
