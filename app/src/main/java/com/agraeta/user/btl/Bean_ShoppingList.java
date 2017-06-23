
package com.agraeta.user.btl;
/**
 * Created by ravina on 4/29/2016.
 */
public class Bean_ShoppingList {
    private String listid;
    private String listname;

    public Bean_ShoppingList() {
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public Bean_ShoppingList(String listid, String listname) {
        this.listid = listid;
        this.listname = listname;
    }
}
