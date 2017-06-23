package com.agraeta.user.btl;

import java.io.Serializable;

/**
 * Created by chaitalee on 9/6/2016.
 */
public class Bean_texhnicalImages implements Serializable {
    private String id = new String();
    private String img = new String();
    private String product_id = new String();

    public Bean_texhnicalImages() {
    }

    public Bean_texhnicalImages(String id, String img, String product_id) {
        this.id = id;
        this.img = img;
        this.product_id = product_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
