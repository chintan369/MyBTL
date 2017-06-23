package com.agraeta.user.btl;

import java.util.ArrayList;

/**
 * Created by chaitalee on 4/20/2016.
 */
public class Bean_Spinner {

    ArrayList<Bean_Attribute> arr_spinner = new ArrayList<Bean_Attribute>();

    public Bean_Spinner()
    {

    }

    public Bean_Spinner(ArrayList<Bean_Attribute> arr_spinner) {
        this.arr_spinner = arr_spinner;
    }

    public ArrayList<Bean_Attribute> getArr_spinner() {
        return arr_spinner;
    }

    public void setArr_spinner(ArrayList<Bean_Attribute> arr_spinner) {
        this.arr_spinner = arr_spinner;
    }
}
