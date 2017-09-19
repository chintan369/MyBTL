package com.agraeta.user.btl;

import com.agraeta.user.btl.model.combooffer.ProductItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaitalee on 4/14/2016.
 */
public class Bean_Product {

    String optionID = "";
    String optionName = "";
    String optionValueID = "";
    String optionValueName = "";
    String reOrderedoptionName = "";
    String reorderOptionValueName = "";
    String reorderOptionId = "";
    String reOrderOptionValueID = "";
    List<ProductItem.ProductOption> ProductOption = new ArrayList<>();
    private int _id;
    @SerializedName("id")
    private String pro_id = "";
    @SerializedName("product_code")
    private String pro_code = "";
    @SerializedName("product_name")
    private String pro_name = "";
    private String pro_label = "";
    @SerializedName("mrp")
    private String pro_mrp = "";
    @SerializedName("selling_price")
    private String pro_sellingprice = "";
    @SerializedName("description")
    private String pro_shortdesc = "";
    private String pro_moreinfo = "";
    private String pro_cat_id = "";
    private String pro_qty = "";
    private String pro_image = "";
    private ArrayList<String>  Schemea= new ArrayList<>();
    private String Scheme = "";

    // Label Label = new Label();
    private int packQty = 1;
    private ArrayList<Bean_schemeData> schemeList=new ArrayList<>();

    /* public class Label{
         String name = "";
     }
 */
    public Bean_Product() {

    }

    public Bean_Product(int id, String pro_id, String pro_code, String pro_name, String pro_label, String pro_mrp, String pro_sellingprice, String pro_shortdesc, String pro_moreinfo, String pro_cat_id, String pro_qty, String pro_image) {
        this._id = id;
        this.pro_id = pro_id;
        this.pro_code = pro_code;
        this.pro_name = pro_name;
        this.pro_label = pro_label;
        this.pro_mrp = pro_mrp;
        this.pro_sellingprice = pro_sellingprice;
        this.pro_shortdesc = pro_shortdesc;
        this.pro_moreinfo = pro_moreinfo;
        this.pro_cat_id = pro_cat_id;
        this.pro_qty = pro_qty;
        this.pro_image = pro_image;
    }

    public Bean_Product(String pro_id, String pro_code, String pro_name, String pro_label, String pro_mrp, String pro_sellingprice, String pro_shortdesc, String pro_moreinfo, String pro_cat_id, String pro_qty, String pro_image) {
        this.pro_id = pro_id;
        this.pro_code = pro_code;
        this.pro_name = pro_name;
        this.pro_label = pro_label;
        this.pro_mrp = pro_mrp;
        this.pro_sellingprice = pro_sellingprice;
        this.pro_shortdesc = pro_shortdesc;
        this.pro_moreinfo = pro_moreinfo;
        this.pro_cat_id = pro_cat_id;
        this.pro_qty = pro_qty;
        this.pro_image = pro_image;
    }

    public Bean_Product(int id, String pro_id, String pro_code, String pro_name, String pro_label, String pro_mrp, String pro_sellingprice, String pro_shortdesc, String pro_moreinfo, String pro_cat_id, String pro_qty, String pro_image, String scheme) {
        this._id = id;
        this.pro_id = pro_id;
        this.pro_code = pro_code;
        this.pro_name = pro_name;
        this.pro_label = pro_label;
        this.pro_mrp = pro_mrp;
        this.pro_sellingprice = pro_sellingprice;
        this.pro_shortdesc = pro_shortdesc;
        this.pro_moreinfo = pro_moreinfo;
        this.pro_cat_id = pro_cat_id;
        this.pro_qty = pro_qty;
        this.pro_image = pro_image;
        Scheme = scheme;
    }

    public Bean_Product(int id, String pro_id, String pro_code, String pro_name, String pro_label, String pro_mrp, String pro_sellingprice, String pro_shortdesc, String pro_moreinfo, String pro_cat_id, String pro_qty, String pro_image, ArrayList<String> schemea, String scheme) {
        this._id = id;
        this.pro_id = pro_id;
        this.pro_code = pro_code;
        this.pro_name = pro_name;
        this.pro_label = pro_label;
        this.pro_mrp = pro_mrp;
        this.pro_sellingprice = pro_sellingprice;
        this.pro_shortdesc = pro_shortdesc;
        this.pro_moreinfo = pro_moreinfo;
        this.pro_cat_id = pro_cat_id;
        this.pro_qty = pro_qty;
        this.pro_image = pro_image;
        Schemea = schemea;
        Scheme = scheme;
    }

    public List<ProductItem.ProductOption> getProductOption() {
        return ProductOption;
    }

    public ArrayList<String> getSchemea() {
        return Schemea;
    }

    public void setSchemea(ArrayList<String> schemea) {
        Schemea = schemea;
    }

    public String getScheme() {
        return Scheme;
    }

    public void setScheme(String scheme) {
        Scheme = scheme;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_label() {
        return pro_label;
    }

    public void setPro_label(String pro_label) {
        this.pro_label = pro_label;
    }

    public String getPro_mrp() {
        return pro_mrp;
    }

    public void setPro_mrp(String pro_mrp) {
        this.pro_mrp = pro_mrp;
    }

    public String getPro_sellingprice() {
        return pro_sellingprice;
    }

    public void setPro_sellingprice(String pro_sellingprice) {
        this.pro_sellingprice = pro_sellingprice;
    }

    public String getPro_shortdesc() {
        return pro_shortdesc;
    }

    public void setPro_shortdesc(String pro_shortdesc) {
        this.pro_shortdesc = pro_shortdesc;
    }

    public String getPro_moreinfo() {
        return pro_moreinfo;
    }

    public void setPro_moreinfo(String pro_moreinfo) {
        this.pro_moreinfo = pro_moreinfo;
    }

    public String getPro_cat_id() {
        return pro_cat_id;
    }

    public void setPro_cat_id(String pro_cat_id) {
        this.pro_cat_id = pro_cat_id;
    }

    public String getPro_qty() {
        return pro_qty;
    }

    public void setPro_qty(String pro_qty) {
        this.pro_qty = pro_qty;
    }

    public String getPro_image() {
        return pro_image;
    }

    public void setPro_image(String pro_image) {
        this.pro_image = pro_image;
    }

    public int getPackQty() {
        return packQty;
    }

    public void setPackQty(int packQty) {
        this.packQty = packQty;
    }

    public ArrayList<Bean_schemeData> getSchemeList() {
        return schemeList;
    }

    public void setSchemeList(ArrayList<Bean_schemeData> schemeList) {
        this.schemeList = schemeList;
    }

    public String getOptionID() {
        return ProductOption.get(0).getOption_id();
    }

    public void setOptionID(String optionID) {
        this.optionID = optionID;
    }

    public String getOptionName() {
        return ProductOption.get(0).getOption().getName();

    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionValueID() {
        return ProductOption.get(0).getOption_value_id();
    }

    public void setOptionValueID(String optionValueID) {
        this.optionValueID = optionValueID;
    }

    public String getOptionValueName() {
        return ProductOption.get(0).getOptionValue().getName();
    }

    public void setOptionValueName(String optionValueName) {
        this.optionValueName = optionValueName;
    }

    public String getReOrderedoptionName() {
        return reOrderedoptionName;
    }

    public void setReOrderedoptionName(String reOrderedoptionName) {
        this.reOrderedoptionName = reOrderedoptionName;
    }

    public String getReorderOptionValueName() {
        return reorderOptionValueName;
    }

    public void setReorderOptionValueName(String reorderOptionValueName) {
        this.reorderOptionValueName = reorderOptionValueName;
    }

    public String getReorderOptionId() {
        return reorderOptionId;
    }

    public void setReorderOptionId(String reorderOptionId) {
        this.reorderOptionId = reorderOptionId;
    }

    public String getReOrderOptionValueID() {
        return reOrderOptionValueID;
    }

    public void setReOrderOptionValueID(String reOrderOptionValueID) {
        this.reOrderOptionValueID = reOrderOptionValueID;
    }
}
