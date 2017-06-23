package com.agraeta.user.btl.model.combooffer;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nivida new on 25-Apr-17.
 */

public class ComboCart {
    @SerializedName("id")
    String id="";

    @SerializedName("combo_id")
    String combo_id="";

    @SerializedName("total_qty")
    String total_qty="";

    @SerializedName("total_mrp")
    String total_mrp="";

    @SerializedName("total_selling_price")
    String total_selling_price="";

    @SerializedName("products")
    String products="";

    public ComboCart() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCombo_id() {
        return combo_id;
    }

    public void setCombo_id(String combo_id) {
        this.combo_id = combo_id;
    }

    public String getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }

    public String getTotal_mrp() {
        return total_mrp;
    }

    public void setTotal_mrp(String total_mrp) {
        this.total_mrp = total_mrp;
    }

    public String getTotal_selling_price() {
        return total_selling_price;
    }

    public void setTotal_selling_price(String total_selling_price) {
        this.total_selling_price = total_selling_price;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getProductQty(){
        int totalQty=0;

        try{
            JSONArray productArray=new JSONArray(products.replace("\\",""));
            for(int i=0; i<productArray.length(); i++){
                JSONObject object=productArray.getJSONObject(i);
                int qty=Integer.parseInt(object.getString("pro_qty"));
                totalQty += qty;
            }
        }catch (JSONException j){
            Log.e("JSONException",j.getMessage());
        }

        return String.valueOf(totalQty);
    }
}
