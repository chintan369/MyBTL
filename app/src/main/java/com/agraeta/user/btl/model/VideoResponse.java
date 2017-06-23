package com.agraeta.user.btl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 10-Jun-17.
 */

public class VideoResponse extends AppModel {

    List<Video> data=new ArrayList<>();

    public List<Video> getData() {
        return data;
    }

    public class Video implements Serializable{
        String id="";
        String product_name="";
        String product_id="";
        String product_code="";
        String short_description="";
        String url="";
        String sort_order="";
        String status="";
        String created="";

        public String getId() {
            return id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getProduct_code() {
            return product_code;
        }

        public String getShort_description() {
            return short_description;
        }

        public String getUrl() {
            return url;
        }

        public String getSort_order() {
            return sort_order;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated() {
            return created;
        }
    }
}
