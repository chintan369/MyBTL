package com.agraeta.user.btl.CompanySalesPerson;

/**
 * Created by SEO on 9/29/2016.
 */
public class BeanTargetAchieve {

    int id;
    String sales_id;
    String salesName;
    String categoryName;
    String targetQuality;
    String achieveQuality;

    public BeanTargetAchieve() {
    }

    public BeanTargetAchieve(int id, String sales_id, String salesName, String categoryName, String targetQuality, String achieveQuality) {
        this.id = id;
        this.sales_id = sales_id;
        this.salesName = salesName;
        this.categoryName = categoryName;
        this.targetQuality = targetQuality;
        this.achieveQuality = achieveQuality;
    }

    public BeanTargetAchieve(String salesName, String sales_id, String categoryName, String targetQuality, String achieveQuality) {
        this.salesName = salesName;
        this.sales_id = sales_id;
        this.categoryName = categoryName;
        this.targetQuality = targetQuality;
        this.achieveQuality = achieveQuality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSales_id() {
        return sales_id;
    }

    public void setSales_id(String sales_id) {
        this.sales_id = sales_id;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTargetQuality() {
        return targetQuality;
    }

    public void setTargetQuality(String targetQuality) {
        this.targetQuality = targetQuality;
    }

    public String getAchieveQuality() {
        return achieveQuality;
    }

    public void setAchieveQuality(String achieveQuality) {
        this.achieveQuality = achieveQuality;
    }
}
