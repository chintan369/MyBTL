package com.agraeta.user.btl;

/**
 * Created by chaitalee on 9/22/2016.
 */
public class Bean_Quotation {

    private int id ;
    private String qu_user_id = new String();
    private String qu_role_id = new String();
    private String qu_owner_id = new String();
    private String QU_PRO_ID = new String();
    private String qu_category_id = new String();
    private String QU_PRO_CODE = new String();
    private String QU_PRO_NAME = new String();
    private String QU_PRO_QTY = new String();
    private String qu_mrp = new String();
    private String QU_PRO_SELLING_PRICE = new String();
    private String QU_PRO_TOTAL = new String();
    private String QU_pro_Option_id = new String();
    private String QU_pro_Option_name = new String();
    private String QU_pro_Option_value_id = new String();
    private String QU_pro_Option_value_name = new String();
    private String qu_pro_scheme = new String();
    private String qu_pack_of = new String();
    private String qu_scheme_id = new String();
    private String qu_scheme_title = new String();
    private String qu_scheme_pack_id = new String();
    private String qu_prod_img = new String();



    public Bean_Quotation() {
    }

    public Bean_Quotation(int id, String QU_PRO_ID, String QU_PRO_CODE, String QU_PRO_NAME, String QU_PRO_QTY, String QU_PRO_SELLING_PRICE, String QU_PRO_TOTAL) {
        this.id = id;
        this.QU_PRO_ID = QU_PRO_ID;
        this.QU_PRO_CODE = QU_PRO_CODE;
        this.QU_PRO_NAME = QU_PRO_NAME;
        this.QU_PRO_QTY = QU_PRO_QTY;
        this.QU_PRO_SELLING_PRICE = QU_PRO_SELLING_PRICE;
        this.QU_PRO_TOTAL = QU_PRO_TOTAL;
    }

    public Bean_Quotation(int id, String QU_PRO_ID, String QU_PRO_CODE, String QU_PRO_NAME, String QU_PRO_QTY, String QU_PRO_SELLING_PRICE, String QU_PRO_TOTAL, String QU_pro_Option_id, String QU_pro_Option_name, String QU_pro_Option_value_id, String QU_pro_Option_value_name) {
        this.id = id;
        this.QU_PRO_ID = QU_PRO_ID;
        this.QU_PRO_CODE = QU_PRO_CODE;
        this.QU_PRO_NAME = QU_PRO_NAME;
        this.QU_PRO_QTY = QU_PRO_QTY;
        this.QU_PRO_SELLING_PRICE = QU_PRO_SELLING_PRICE;
        this.QU_PRO_TOTAL = QU_PRO_TOTAL;
        this.QU_pro_Option_id = QU_pro_Option_id;
        this.QU_pro_Option_name = QU_pro_Option_name;
        this.QU_pro_Option_value_id = QU_pro_Option_value_id;
        this.QU_pro_Option_value_name = QU_pro_Option_value_name;
    }

    public Bean_Quotation(String QU_PRO_ID, String QU_PRO_CODE, String QU_PRO_NAME, String QU_PRO_QTY, String QU_PRO_SELLING_PRICE, String QU_PRO_TOTAL, String QU_pro_Option_id, String QU_pro_Option_name, String QU_pro_Option_value_id, String QU_pro_Option_value_name) {
        this.QU_PRO_ID = QU_PRO_ID;
        this.QU_PRO_CODE = QU_PRO_CODE;
        this.QU_PRO_NAME = QU_PRO_NAME;
        this.QU_PRO_QTY = QU_PRO_QTY;
        this.QU_PRO_SELLING_PRICE = QU_PRO_SELLING_PRICE;
        this.QU_PRO_TOTAL = QU_PRO_TOTAL;
        this.QU_pro_Option_id = QU_pro_Option_id;
        this.QU_pro_Option_name = QU_pro_Option_name;
        this.QU_pro_Option_value_id = QU_pro_Option_value_id;
        this.QU_pro_Option_value_name = QU_pro_Option_value_name;
    }

    public Bean_Quotation(String QU_PRO_ID, String QU_PRO_CODE, String QU_PRO_NAME, String QU_PRO_QTY, String QU_PRO_SELLING_PRICE, String QU_PRO_TOTAL) {
        this.QU_PRO_ID = QU_PRO_ID;
        this.QU_PRO_CODE = QU_PRO_CODE;
        this.QU_PRO_NAME = QU_PRO_NAME;
        this.QU_PRO_QTY = QU_PRO_QTY;
        this.QU_PRO_SELLING_PRICE = QU_PRO_SELLING_PRICE;
        this.QU_PRO_TOTAL = QU_PRO_TOTAL;
    }

    public Bean_Quotation(int id, String qu_user_id, String qu_role_id, String qu_owner_id, String QU_PRO_ID, String qu_category_id, String QU_PRO_CODE, String QU_PRO_NAME, String QU_PRO_QTY, String qu_mrp, String QU_PRO_SELLING_PRICE, String QU_PRO_TOTAL, String QU_pro_Option_id, String QU_pro_Option_name, String QU_pro_Option_value_id, String QU_pro_Option_value_name, String qu_pro_scheme, String qu_pack_of, String qu_scheme_id, String qu_scheme_title, String qu_scheme_pack_id, String qu_prod_img) {
        this.id = id;
        this.qu_user_id = qu_user_id;
        this.qu_role_id = qu_role_id;
        this.qu_owner_id = qu_owner_id;
        this.QU_PRO_ID = QU_PRO_ID;
        this.qu_category_id = qu_category_id;
        this.QU_PRO_CODE = QU_PRO_CODE;
        this.QU_PRO_NAME = QU_PRO_NAME;
        this.QU_PRO_QTY = QU_PRO_QTY;
        this.qu_mrp = qu_mrp;
        this.QU_PRO_SELLING_PRICE = QU_PRO_SELLING_PRICE;
        this.QU_PRO_TOTAL = QU_PRO_TOTAL;
        this.QU_pro_Option_id = QU_pro_Option_id;
        this.QU_pro_Option_name = QU_pro_Option_name;
        this.QU_pro_Option_value_id = QU_pro_Option_value_id;
        this.QU_pro_Option_value_name = QU_pro_Option_value_name;
        this.qu_pro_scheme = qu_pro_scheme;
        this.qu_pack_of = qu_pack_of;
        this.qu_scheme_id = qu_scheme_id;
        this.qu_scheme_title = qu_scheme_title;
        this.qu_scheme_pack_id = qu_scheme_pack_id;
        this.qu_prod_img = qu_prod_img;
    }

    public Bean_Quotation(String qu_user_id, String qu_role_id, String qu_owner_id, String QU_PRO_ID, String qu_category_id, String QU_PRO_CODE, String QU_PRO_NAME, String QU_PRO_QTY, String qu_mrp, String QU_PRO_SELLING_PRICE, String QU_PRO_TOTAL, String QU_pro_Option_id, String QU_pro_Option_name, String QU_pro_Option_value_id, String QU_pro_Option_value_name, String qu_pro_scheme, String qu_pack_of, String qu_scheme_id, String qu_scheme_title, String qu_scheme_pack_id, String qu_prod_img) {
        this.qu_user_id = qu_user_id;
        this.qu_role_id = qu_role_id;
        this.qu_owner_id = qu_owner_id;
        this.QU_PRO_ID = QU_PRO_ID;
        this.qu_category_id = qu_category_id;
        this.QU_PRO_CODE = QU_PRO_CODE;
        this.QU_PRO_NAME = QU_PRO_NAME;
        this.QU_PRO_QTY = QU_PRO_QTY;
        this.qu_mrp = qu_mrp;
        this.QU_PRO_SELLING_PRICE = QU_PRO_SELLING_PRICE;
        this.QU_PRO_TOTAL = QU_PRO_TOTAL;
        this.QU_pro_Option_id = QU_pro_Option_id;
        this.QU_pro_Option_name = QU_pro_Option_name;
        this.QU_pro_Option_value_id = QU_pro_Option_value_id;
        this.QU_pro_Option_value_name = QU_pro_Option_value_name;
        this.qu_pro_scheme = qu_pro_scheme;
        this.qu_pack_of = qu_pack_of;
        this.qu_scheme_id = qu_scheme_id;
        this.qu_scheme_title = qu_scheme_title;
        this.qu_scheme_pack_id = qu_scheme_pack_id;
        this.qu_prod_img = qu_prod_img;
    }

    public String getQU_pro_Option_id() {
        return QU_pro_Option_id;
    }

    public void setQU_pro_Option_id(String QU_pro_Option_id) {
        this.QU_pro_Option_id = QU_pro_Option_id;
    }

    public String getQU_pro_Option_name() {
        return QU_pro_Option_name;
    }

    public void setQU_pro_Option_name(String QU_pro_Option_name) {
        this.QU_pro_Option_name = QU_pro_Option_name;
    }

    public String getQU_pro_Option_value_id() {
        return QU_pro_Option_value_id;
    }

    public void setQU_pro_Option_value_id(String QU_pro_Option_value_id) {
        this.QU_pro_Option_value_id = QU_pro_Option_value_id;
    }

    public String getQU_pro_Option_value_name() {
        return QU_pro_Option_value_name;
    }

    public void setQU_pro_Option_value_name(String QU_pro_Option_value_name) {
        this.QU_pro_Option_value_name = QU_pro_Option_value_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQU_PRO_ID() {
        return QU_PRO_ID;
    }

    public void setQU_PRO_ID(String QU_PRO_ID) {
        this.QU_PRO_ID = QU_PRO_ID;
    }

    public String getQU_PRO_CODE() {
        return QU_PRO_CODE;
    }

    public void setQU_PRO_CODE(String QU_PRO_CODE) {
        this.QU_PRO_CODE = QU_PRO_CODE;
    }

    public String getQU_PRO_NAME() {
        return QU_PRO_NAME;
    }

    public void setQU_PRO_NAME(String QU_PRO_NAME) {
        this.QU_PRO_NAME = QU_PRO_NAME;
    }

    public String getQU_PRO_QTY() {
        return QU_PRO_QTY;
    }

    public void setQU_PRO_QTY(String QU_PRO_QTY) {
        this.QU_PRO_QTY = QU_PRO_QTY;
    }

    public String getQU_PRO_SELLING_PRICE() {
        return QU_PRO_SELLING_PRICE;
    }

    public void setQU_PRO_SELLING_PRICE(String QU_PRO_SELLING_PRICE) {
        this.QU_PRO_SELLING_PRICE = QU_PRO_SELLING_PRICE;
    }

    public String getQU_PRO_TOTAL() {
        return QU_PRO_TOTAL;
    }

    public void setQU_PRO_TOTAL(String QU_PRO_TOTAL) {
        this.QU_PRO_TOTAL = QU_PRO_TOTAL;
    }

    public String getQu_user_id() {
        return qu_user_id;
    }

    public void setQu_user_id(String qu_user_id) {
        this.qu_user_id = qu_user_id;
    }

    public String getQu_role_id() {
        return qu_role_id;
    }

    public void setQu_role_id(String qu_role_id) {
        this.qu_role_id = qu_role_id;
    }

    public String getQu_owner_id() {
        return qu_owner_id;
    }

    public void setQu_owner_id(String qu_owner_id) {
        this.qu_owner_id = qu_owner_id;
    }

    public String getQu_category_id() {
        return qu_category_id;
    }

    public void setQu_category_id(String qu_category_id) {
        this.qu_category_id = qu_category_id;
    }

    public String getQu_mrp() {
        return qu_mrp;
    }

    public void setQu_mrp(String qu_mrp) {
        this.qu_mrp = qu_mrp;
    }

    public String getQu_pro_scheme() {
        return qu_pro_scheme;
    }

    public void setQu_pro_scheme(String qu_pro_scheme) {
        this.qu_pro_scheme = qu_pro_scheme;
    }

    public String getQu_pack_of() {
        return qu_pack_of;
    }

    public void setQu_pack_of(String qu_pack_of) {
        this.qu_pack_of = qu_pack_of;
    }

    public String getQu_scheme_id() {
        return qu_scheme_id;
    }

    public void setQu_scheme_id(String qu_scheme_id) {
        this.qu_scheme_id = qu_scheme_id;
    }

    public String getQu_scheme_title() {
        return qu_scheme_title;
    }

    public void setQu_scheme_title(String qu_scheme_title) {
        this.qu_scheme_title = qu_scheme_title;
    }

    public String getQu_scheme_pack_id() {
        return qu_scheme_pack_id;
    }

    public void setQu_scheme_pack_id(String qu_scheme_pack_id) {
        this.qu_scheme_pack_id = qu_scheme_pack_id;
    }

    public String getQu_prod_img() {
        return qu_prod_img;
    }

    public void setQu_prod_img(String qu_prod_img) {
        this.qu_prod_img = qu_prod_img;
    }
}
