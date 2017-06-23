package com.agraeta.user.btl;

/**
 * Created by chaitalee on 5/17/2016.
 */
public class Bean_Desc {

    private int id ;
    private String Pro_id = new String();
    private String description = new String();
    private String specification = new String();
    private String technical = new String();
    private String catalogs = new String();
    private String guarantee = new String();
    private String videos = new String();
    private String general = new String();

    public Bean_Desc(int id, String pro_id, String description, String specification, String technical, String catalogs, String guarantee, String videos, String general) {
        this.id = id;
        this.Pro_id = pro_id;
        this.description = description;
        this.specification = specification;
        this.technical = technical;
        this.catalogs = catalogs;
        this.guarantee = guarantee;
        this.videos = videos;
        this.general = general;
    }

    public Bean_Desc(String pro_id, String description, String specification, String technical, String catalogs, String guarantee, String videos, String general) {
        this.Pro_id = pro_id;
        this.description = description;
        this.specification = specification;
        this.technical = technical;
        this.catalogs = catalogs;
        this.guarantee = guarantee;
        this.videos = videos;
        this.general = general;
    }

    public Bean_Desc() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPro_id() {
        return Pro_id;
    }

    public void setPro_id(String pro_id) {
        Pro_id = pro_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public String getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(String catalogs) {
        this.catalogs = catalogs;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }
}
