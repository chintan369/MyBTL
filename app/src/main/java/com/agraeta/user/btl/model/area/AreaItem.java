package com.agraeta.user.btl.model.area;

import java.io.Serializable;

/**
 * Created by Nivida new on 10-May-17.
 */

public class AreaItem implements Serializable {
    String id="0";
    String name="";

    public AreaItem(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {

        if(name==null || name.equalsIgnoreCase("null")) return "";

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
