package com.agraeta.user.btl.model;

/**
 * Created by Nivida new on 10-Jun-17.
 */

public class PageResponse extends AppModel {


    Page data=new Page();

    public Page getData() {
        return data;
    }

    public class Page{
        String title="";
        String description="";

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
