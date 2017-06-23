package com.agraeta.user.btl;

/**
 * Created by chaitalee on 4/5/2016.
 */
public class Bean_Slider {

    private String id = new String();
    private String img = new String();
    String link_type="0";
    String link_id="0";
    String has_child="0";


    public Bean_Slider(String id, String img) {
        super();
        this.id = id;
        this.img = img;
    }

    public Bean_Slider() {
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

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public String getHas_child() {
        return has_child;
    }

    public void setHas_child(String has_child) {
        this.has_child = has_child;
    }
}
