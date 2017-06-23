package com.agraeta.user.btl;

/**
 * Created by chaitalee on 4/5/2016.
 */
public class Bean_Stickers {

    private String id = new String();
    private String img = new String();

    public Bean_Stickers() {
        super();

    }

    public Bean_Stickers(String id, String img) {
        super();

        this.id = id;
        this.img = img;
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
}
