package com.agraeta.user.btl;

/**
 * Created by chaitalee on 4/5/2016.
 */
public class Bean_category {

    private String id = new String();
    private String img = new String();
    private String name = new String();
    private String is_child = new String();

    public Bean_category(String id, String img, String name, String is_child) {
        super();
        this.id = id;
        this.img = img;
        this.name = name;
        this.is_child = is_child;
    }

    public Bean_category() {
        super();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_child() {
        return is_child;
    }

    public void setIs_child(String is_child) {
        this.is_child = is_child;
    }
}
