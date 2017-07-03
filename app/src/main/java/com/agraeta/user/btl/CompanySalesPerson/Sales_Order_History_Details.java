package com.agraeta.user.btl.CompanySalesPerson;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.BTL_Cart;
import com.agraeta.user.btl.Bean_Address;
import com.agraeta.user.btl.Bean_Desc;
import com.agraeta.user.btl.Bean_Order_E_History;
import com.agraeta.user.btl.Bean_Order_history;
import com.agraeta.user.btl.Bean_Product;
import com.agraeta.user.btl.Bean_ProductImage;
import com.agraeta.user.btl.Bean_ProductOprtion;
import com.agraeta.user.btl.Bean_Schme_value;
import com.agraeta.user.btl.Bean_User_data;
import com.agraeta.user.btl.Bean_Value_Selected_Detail;
import com.agraeta.user.btl.Bean_texhnicalImages;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.ImageLoader;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.Notification;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ReOrderActivity;
import com.agraeta.user.btl.ServiceHandler;
import com.agraeta.user.btl.Thankyou_Page;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaitalee on 9/20/2016.
 */
public class Sales_Order_History_Details extends AppCompatActivity {

    public static ArrayList<String> WishList = new ArrayList<String>();
    //    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
//    String user_id_main = new String();
//    String role_id = new String();
    ImageView reorder_all;
    Button btn_pay1;
    TextView tv_total_product, tv_order_total, tv_subtotal, sub_t;
    ListView lv_checkout_product_list;
    ArrayList<Bean_Order_history> bean_cart = new ArrayList<Bean_Order_history>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ArrayList<Bean_Otracking> order_track = new ArrayList<Bean_Otracking>();
    CustomResultAdapter adapter;
    Custom_ProgressDialog loadingView;
    JSONArray jarray_OrderProductData = new JSONArray();
    JSONArray jarray_OrderUserData = new JSONArray();
    JSONArray jarray_OrderTotalData = new JSONArray();
    String json;
    ImageLoader imageloader;
    AppPrefs appPrefs;
    CustomAdapterOrderHistory_tracking adapter1;
    // ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    TextView tv_user_name_billing, tv_number_billing, tv_address_billing, tv_user_name_delivery, tv_number_delivery, tv_address_delivery;
    Dialog dialog;
    ImageView im_billing, im_delivery;
    ArrayList<Bean_Address> array_address = new ArrayList<Bean_Address>();
    ArrayList<String> array_final_address = new ArrayList<String>();
    String selected_billing_address = "";
    String selected_shipping_address = "";
    String selected_billing_address_id = "";
    String selected_shipping_address_id = "";
    String comment = "";
    String option_name = "";
    String option_id = "";
    String Schemee = "";
    ArrayList<Bean_Product> bean_product_schme = new ArrayList<Bean_Product>();
    ArrayList<Bean_Schme_value> bean_schme = new ArrayList<Bean_Schme_value>();
    ArrayList<Bean_Value_Selected_Detail> array_value = new ArrayList<Bean_Value_Selected_Detail>();
    String user_id = "";
    String user_id_main, role_id, role;
    DatabaseHandler db;
    TextView txt_orderid, txt_orderdate, order_status;
    ImageView more_info, order_inq, option_icon;
    ListView l_tracking;
    ScrollView sc_amt;
    TextView txt_couponname, txt_couponamount;
    LinearLayout lnr_amountdetails, lnr_moreinfo, lnr_coupon;
    Bean_Order_history history;
    EditText txt_comm;
    int kkk = 0;
    LinearLayout lcomment;
    String owner_id = new String();
    String u_id = new String();
    CustomAdapterOrderHistory_enquiry adapter2;
    String qun = new String();
    ArrayList<Bean_Product> bean_product1 = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_Desc> bean_desc1 = new ArrayList<Bean_Desc>();
    ArrayList<Bean_texhnicalImages> bean_technical = new ArrayList<Bean_texhnicalImages>();
    ArrayList<Bean_ProductImage> bean_productImages = new ArrayList<Bean_ProductImage>();
    ArrayList<Bean_ProductOprtion> bean_Oprtions = new ArrayList<Bean_ProductOprtion>();
    ArrayList<String> list_of_images = new ArrayList<String>();
    JSONArray jarray_cart = new JSONArray();
    ListView lst_en;
    ArrayList<Bean_Order_E_History> order_e_history = new ArrayList<Bean_Order_E_History>();
    String cartJSON = "";
    boolean hasCartCallFinish = true;

    TextView txt;

    boolean isNotDone = true;
    String jsonData = "";

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");
        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6")) {

                    appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                    role_id = appPrefs.getSubSalesId().toString();
                    u_id = appPrefs.getSalesPersonId().toString();
                } else if (role_id.equalsIgnoreCase("7")) {
                    appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                    role_id = appPrefs.getSubSalesId().toString();
                    u_id = appPrefs.getSalesPersonId().toString();
                    //Log.e("IDIDD",""+app.getSalesPersonId().toString());
                } else {
                    u_id = owner_id;
                }


            }

            List<NameValuePair> para = new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id", u_id));

            new GetCartByQty(para).execute();


        } else {
            appPrefs = new AppPrefs(Sales_Order_History_Details.this);
            appPrefs.setCart_QTy("");
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__history__details);
        appPrefs = new AppPrefs(Sales_Order_History_Details.this);

        setRefershData();
        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id().toString();
                role_id = user_data.get(i).getUser_type().toString();
                //Log.e("123232344",""+role_id);
                if (role_id.equalsIgnoreCase("6") || role_id.equalsIgnoreCase("7")) {
                    appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                    role_id = appPrefs.getSubSalesId().toString();
                    user_id_main = appPrefs.getSalesPersonId().toString();
                    //Log.e("211221",""+appPrefs.getSalesPersonId().toString());
                    //Log.e("232323",""+role_id);
                }


            }

        } else {
            user_id_main = "";
        }

        sub_t = (TextView) findViewById(R.id.sub_t);

        if (role_id.equalsIgnoreCase("0")) {

            sub_t.setText("Sub Total (VAT will be added extra):");
        } else if (role_id.equalsIgnoreCase("2")) {

            sub_t.setText("Sub Total (Tax Amount Inclusive):");
        } else if (role_id.equalsIgnoreCase("10")) {

            sub_t.setText("Sub Total (Tax Amount Inclusive):");
        } else {
            sub_t.setText("Sub Total (VAT will be added extra):");
        }

        Intent intent = getIntent();
        history = (Bean_Order_history) intent.getSerializableExtra("order");
        lnr_coupon = (LinearLayout) findViewById(R.id.lnr_coupon);
        txt_couponname = (TextView) findViewById(R.id.txt_coupon_name);
        txt_couponamount = (TextView) findViewById(R.id.tv_coupondiscount);


        reorder_all = (ImageView) findViewById(R.id.reorder_txt);
        reorder_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e("Cart Size:",""+bean_cart.size());

                appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                String ss = appPrefs.getOrdercheck();
                int ij = Integer.parseInt(ss);

                ArrayList<String> productIDs = new ArrayList<String>();
                ArrayList<String> productQtys = new ArrayList<String>();
                ArrayList<String> productOptionID = new ArrayList<String>();
                ArrayList<String> productOptionName = new ArrayList<String>();
                ArrayList<String> productOptionValueID = new ArrayList<String>();
                ArrayList<String> productOptionValueName = new ArrayList<String>();

                for (int ii = 0; ii < bean_cart.size(); ii++) {
                    if(!bean_cart.get(ii).getScheme_type().equalsIgnoreCase("2")){
                        productIDs.add(bean_cart.get(ii).getProduct_id());
                        productQtys.add(bean_cart.get(ii).getProduct_qty());
                        productOptionID.add(bean_cart.get(ii).getPro_Option_id());
                        productOptionValueID.add(bean_cart.get(ii).getPro_Option_value_id());
                        productOptionName.add(bean_cart.get(ii).getProduct_option_name());
                        productOptionValueName.add(bean_cart.get(ii).getProduct_value_name());
                    }
                }

                Intent intent = new Intent(getApplicationContext(), ReOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putStringArrayListExtra("productIDs", productIDs);
                intent.putStringArrayListExtra("productQtys", productQtys);
                intent.putStringArrayListExtra("productOptionIDs", productOptionID);
                intent.putStringArrayListExtra("productOptionNames", productOptionName);
                intent.putStringArrayListExtra("productOptionValueIDs", productOptionValueID);
                intent.putStringArrayListExtra("productOptionValueNames", productOptionValueName);
                startActivity(intent);

                /*for (int ii = 0; ii < ij; ii++) {

                    //Log.e("Cart Size:",""+ij);


                    if (bean_cart.get(ii).getScheme_type().toString().equalsIgnoreCase("2")) {

                    } else {

                        Log.e("11111111111", "111111111111111");

                        setRefershData();

                        if (user_data.size() != 0) {
                            for (int i = 0; i < user_data.size(); i++) {

                                owner_id = user_data.get(i).getUser_id().toString();

                                role_id = user_data.get(i).getUser_type().toString();

                                if (role_id.equalsIgnoreCase("6")) {

                                    appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                                    role_id = appPrefs.getSubSalesId().toString();
                                    u_id = appPrefs.getSalesPersonId().toString();
                                } else if (role_id.equalsIgnoreCase("7")) {
                                    appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                                    role_id = appPrefs.getSubSalesId().toString();
                                    u_id = appPrefs.getSalesPersonId().toString();
                                    //Log.e("IDIDD", "" + appPrefs.getSalesPersonId().toString());
                                } else {
                                    u_id = owner_id;

                            }

                        } else {
                            user_id_main = "";
                        }

                        // product_id = tv_pop_pname.getTag().toString();
                        List<NameValuePair> para = new ArrayList<NameValuePair>();
                        para.add(new BasicNameValuePair("product_id", bean_cart.get(ii).getProduct_id().toString()));
                        para.add(new BasicNameValuePair("owner_id", owner_id));
                        para.add(new BasicNameValuePair("user_id", u_id));
                        Log.e("111111111", "" + bean_cart.get(ii).getProduct_id().toString());
                        Log.e("222222222", "" + owner_id);
                        Log.e("333333333", "" + u_id);

                        isNotDone = true;
                        new GetProductDetailByQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        while (isNotDone) {

                        }
                        isNotDone = true;

                        String json = jsonData; //GetProductDetailByQty(para);

                        try {


                            //System.out.println(json);

                            if (json == null
                                    || (json.equalsIgnoreCase(""))) {
                    *//*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*//*
                                Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERROR", getLayoutInflater());
                                // loadingView.dismiss();

                            } else {
                                JSONObject jObj = new JSONObject(json);

                                String date = jObj.getString("status");

                                if (date.equalsIgnoreCase("false")) {

                                    qun = "0";
                                    kkk = 0;
                                    //Log.e("131313131331", "1");

                                    // loadingView.dismiss();
                                } else {

                                    JSONObject jobj = new JSONObject(json);


                                    if (jobj != null) {


                                        JSONArray jsonArray = jObj.getJSONArray("data");
                                        for (int iu = 0; iu < jsonArray.length(); iu++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(iu);
                                            qun = jsonObject.getString("quantity");

                                        }
                                        Log.e("2222222222", "" + qun);
                                        //Log.e("131313131331", "" + qun);
                                        kkk = 1;


                                    }


                                }

                            }
                        } catch (Exception j) {
                            j.printStackTrace();
                            //Log.e("json exce", j.getMessage());
                        }
                        Log.e("33333333333", "" + kkk);
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("product_id", bean_cart.get(ii).getProduct_id()));
                        params.add(new BasicNameValuePair("user_id", user_id_main));
                        params.add(new BasicNameValuePair("role_id", role_id));
                        Log.e("111111111", "" + bean_cart.get(ii).getProduct_id());
                        Log.e("222222222", "" + user_id_main);
                        Log.e("333333333", "" + role_id);
                        isNotDone = true;
                        new GetProductDetailByProductDetail(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        while (isNotDone) {

                        }
                        isNotDone = true;
                        String json2 = jsonData;//GetProductDetailByProductDetail(params);
                        //product_id = tv_pop_pname.getTag().toString();


                        try {

                        //System.out.println(json2);

                            if (json2.equalsIgnoreCase("")
                                    || (json2.equalsIgnoreCase(""))) {

                                Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                                loadingView.dismiss();

                            } else {
                                JSONObject jObj = new JSONObject(json2);

                                String date = jObj.getString("status");

                                if (date.equalsIgnoreCase("false")) {
                                    String Message = jObj.getString("message");

                                    Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());
                                    loadingView.dismiss();
                                } else {
                                    bean_product1.clear();
                                    bean_productOprtions.clear();

                                    JSONArray jsonwish = jObj.optJSONArray("wish_list");

                                    WishList = new ArrayList<String>();

                                    for (int i = 0; i < jsonwish.length(); i++) {
                                        WishList.add(jsonwish.get(i).toString());
                                    }

                                    //Log.e("Wish List Size", "" + WishList.size());


                                    JSONObject jobj = new JSONObject(json2);


                                    if (jobj != null) {

                                        JSONObject jObjj = jObj.getJSONObject("data");
                                        JSONObject jproduct = jObjj.getJSONObject("Product");


                                        Bean_Product bean = new Bean_Product();


                                        bean.setPro_id(jproduct.getString("id"));


                                        bean.setPro_cat_id(jproduct.getString("category_id"));
                                        bean.setPro_code(jproduct.getString("product_code"));
                                        bean.setPro_name(jproduct.getString("product_name"));
                                        // bean.setPro_label(jproduct.getString("label_id"));
                                        bean.setPro_qty(jproduct.getString("qty"));
                                        bean.setPro_mrp(jproduct.getString("mrp"));
                                        bean.setPro_sellingprice(jproduct.getString("selling_price"));
                                        // bean.setPro_shortdesc(jproduct.getString("short_description"));
                                        bean.setPro_image(jproduct.getString("image"));
                                        //   bean.setPro_moreinfo(jproduct.getString("more_info"));
                                        JSONArray jschme = jObjj.getJSONArray("Scheme");
                                        //Log.e("A1111111", "" + jschme.length());

                                        String sa = "";

                                        for (int s1 = 0; s1 < jschme.length(); s1++) {
                                            JSONObject jProductScheme = jschme.getJSONObject(s1);

                                            ArrayList<String> a = new ArrayList<String>();
                                            a.add(jProductScheme.getString("scheme_name"));
                                            //Log.e("A2222222", "" + jProductScheme.getString("scheme_name"));
                                            sa += jProductScheme.getString("scheme_name").toString() + "\n";
                                            bean.setSchemea(a);
                                        }

                                        bean.setScheme(sa);
                                        JSONObject jlabel = jObjj.getJSONObject("Label");
                                        bean.setPro_label(jlabel.getString("name"));

                                        bean_product1.add(bean);


                                        Bean_Desc bean1 = new Bean_Desc();
                                        bean1.setPro_id(jproduct.getString("id"));
                                        //   Toast.makeText(BTLProduct_Detail.this, ""+bean1.getPro_id()+"-"+jproduct.getString("id"), Toast.LENGTH_SHORT).show();
                                        bean1.setDescription(jproduct.getString("description"));
                                        bean1.setSpecification(jproduct.getString("specification"));
                                        bean1.setTechnical(jproduct.getString("technical"));
                                        bean1.setCatalogs(jproduct.getString("catalogs"));
                                        bean1.setGuarantee(jproduct.getString("guarantee"));
                                        bean1.setVideos(jproduct.getString("videos"));
                                        bean1.setGeneral(jproduct.getString("general"));
                                        bean_desc1.add(bean1);

                                        JSONArray jProducttechnical = jObjj.getJSONArray("ProductTechnicalSpecification");
                                        //Log.e("BBBB", "" + jProducttechnical.length());
                                        for (int s = 0; s < jProducttechnical.length(); s++) {
                                            JSONObject jProducttechni = jProducttechnical.getJSONObject(s);

                                            Bean_texhnicalImages beantechnical = new Bean_texhnicalImages();
                                            beantechnical.setId(jProducttechni.getString("id"));
                                            beantechnical.setImg(jProducttechni.getString("image_path"));
                                            beantechnical.setProduct_id(jProducttechni.getString("product_id"));
                                            //Log.e("BaBa", "" + jProducttechni.getString("id"));
                                            bean_technical.add(beantechnical);

                                        }
                                        //Log.e("AbAb", "" + bean_technical.size());
                                        JSONArray jProductOption = jObjj.getJSONArray("ProductOption");


                                        for (int s = 0; s < jProductOption.length(); s++) {
                                            JSONObject jProductOptiono = jProductOption.getJSONObject(s);
                                            JSONObject jPOOption = jProductOptiono.getJSONObject("Option");
                                            JSONObject jPOOptionValue = jProductOptiono.getJSONObject("OptionValue");
                                            JSONArray jPOProductOptionImage = jProductOptiono.getJSONArray("ProductOptionImage");


                                            Bean_ProductOprtion beanoption = new Bean_ProductOprtion();
                                            // arrOptionType=new ArrayList<String>();


                                            //Option
                                            beanoption.setPro_Option_id(jPOOption.getString("id"));
                                            beanoption.setPro_Option_name(jPOOption.getString("name"));


                                            //OptionValue
                                            beanoption.setPro_Option_value_name(jPOOptionValue.getString("name"));
                                            beanoption.setPro_Option_value_id(jPOOptionValue.getString("id"));

                                            //ProductOptionImage


                                            //beanoption.setPro_id(jProductOptiono.getString("product_id"));
                                            beanoption.setPro_id(jProductOptiono.getString("parent_product_id"));
                                            //Log.e("ABABABABBABBA", "" + jProductOptiono.getString("parent_product_id"));
                                            beanoption.setOption_pro_id(jProductOptiono.getString("product_id"));
                                            //Log.e("ABABABABBABBA", "" + jProductOptiono.getString("product_id"));
                                            beanoption.setPro_Option_mrp(jProductOptiono.getString("mrp"));
                                            beanoption.setPro_Option_selling_price(jProductOptiono.getString("selling_price"));
                                            beanoption.setPro_Option_procode(jProductOptiono.getString("product_code"));
                                            //Log.e("ABABABABBABBA", "" + jProductOptiono.getString("product_code"));


                                            if (jPOProductOptionImage.length() == 0) {
                                                beanoption.setPro_Option_proimage("");
                                            } else {

                                                for (int t = 0; t < jPOProductOptionImage.length(); t++) {
                                                    JSONObject jProductOptio = jPOProductOptionImage.getJSONObject(t);
                                                    beanoption.setPro_Option_proimage(jProductOptio.getString("image"));
                                                }
                                            }
                                            bean_productOprtions.add(beanoption);
                                            //   arrOptionType.add(jPOOption.getString("id"));


                                        }


                                        //Log.e("Option Size1212", "" + bean_productOprtions.size());


                                        JSONArray jpProductImage = jObjj.getJSONArray("ProductImage");

                                        //Log.e("siz from array", jpProductImage.length() + "");

                                        if (jpProductImage.length() > 0) {
                                            for (int s = 0; s < jpProductImage.length(); s++) {
                                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                                Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                                beanproductimg.setPro_id(jpimg.getString("product_id"));
                                                beanproductimg.setPro_Images(jpimg.getString("image"));

                                                bean_productImages.add(beanproductimg);

                                            }
                                        } else {
                                            Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                            beanproductimg.setPro_id(jproduct.getString("id"));
                                            beanproductimg.setPro_Images(jproduct.getString("image"));

                                            bean_productImages.add(beanproductimg);
                                        }


                                    }


                                }

                            }
                        } catch (Exception j) {
                            j.printStackTrace();
                        }

                        Log.e("444444444", "444444444444");
                        String qty = bean_cart.get(ii).getProduct_qty();

                        int q = Integer.parseInt(bean_cart.get(ii).getProduct_qty()) + Integer.parseInt(qun);

                        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                        params1.add(new BasicNameValuePair("product_id", bean_cart.get(ii).getProduct_id()));
                        params1.add(new BasicNameValuePair("user_id", user_id_main));
                        params1.add(new BasicNameValuePair("product_buy_qty", "" + q));
                        Log.e("111111111", "" + bean_cart.get(ii).getProduct_id());
                        Log.e("222222222", "" + user_id_main);
                        Log.e("333333333", "" + q);
                        isNotDone = true;
                        new GetProductDetailByCode_schme(params1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        while (isNotDone) {

                        }
                        isNotDone = true;
                        String json3 = jsonData; //GetProductDetailByCode_schme(params1);

                        //new check_schme().execute();
                        try {


                            //System.out.println(json2);

                            if (json3 == null
                                    || (json3.equalsIgnoreCase(""))) {

                                Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                                // loadingView.dismiss();

                            } else {
                                JSONObject jObj = new JSONObject(json3);

                                String date = jObj.getString("status");

                                if (date.equalsIgnoreCase("false")) {
                                    String Message = jObj.getString("message");
                                    bean_product_schme.clear();
                                    bean_schme.clear();
                                    bean_Oprtions.clear();

                                } else {

                                    JSONObject jobj = new JSONObject(json3);
                                    bean_product_schme.clear();
                                    bean_schme.clear();
                                    bean_Oprtions.clear();
                                    if (jobj != null) {

                                        bean_product_schme.clear();
                                        bean_schme.clear();
                                        bean_Oprtions.clear();

                                        JSONObject jsonArray = jObj.getJSONObject("data");
                                        // //Log.e("3333333333",""+product_id);
                                        //   for (int i = 0; i < jsonArray.length(); i++) {
                                        // JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        JSONObject jschme = jsonArray.getJSONObject("Scheme");

                                        JSONObject jproduct = jsonArray.getJSONObject("GetProduct");

                                        //Log.e("product", jproduct.toString() + "->" + jproduct.getString("id"));

                                        JSONObject jlabel = jproduct.getJSONObject("Label");


                                        Bean_Product bean = new Bean_Product();


                                        bean.setPro_id(jproduct.getString("id"));
                                        //Log.e("44444444", "" + jproduct.getString("id"));

                                        bean.setPro_cat_id(jproduct.getString("category_id"));
                                        bean.setPro_code(jproduct.getString("product_code"));
                                        bean.setPro_name(jproduct.getString("product_name"));
                                        // bean.setPro_label(jproduct.getString("label_id"));
                                        bean.setPro_qty(jproduct.getString("qty"));
                                        bean.setPro_mrp(jproduct.getString("mrp"));

                                        //Log.e("A1A1", "" + jproduct.getString("mrp").toString());
                                        bean.setPro_sellingprice(jproduct.getString("selling_price"));
                                        //Log.e("B1B1", "" + jproduct.getString("selling_price").toString());
                                        // bean.setPro_shortdesc(jproduct.getString("short_description"));
                                        bean.setPro_image(jproduct.getString("image"));


                                        bean.setScheme("");

                                        bean.setPro_label(jlabel.getString("name"));
                                        bean_product_schme.add(bean);


                                        JSONArray jProductOption = jproduct.getJSONArray("ProductOption");


                                        for (int s = 0; s < jProductOption.length(); s++) {
                                            JSONObject jProductOptiono = jProductOption.getJSONObject(s);
                                            JSONObject jPOOption = jProductOptiono.getJSONObject("Option");
                                            JSONObject jPOOptionValue = jProductOptiono.getJSONObject("OptionValue");


                                            Bean_ProductOprtion beanoption = new Bean_ProductOprtion();
                                            // arrOptionType=new ArrayList<String>();


                                            //Option
                                            beanoption.setPro_Option_id(jPOOption.getString("id"));
                                            beanoption.setPro_Option_name(jPOOption.getString("name"));


                                            //OptionValue
                                            beanoption.setPro_Option_value_name(jPOOptionValue.getString("name"));
                                            beanoption.setPro_Option_value_id(jPOOptionValue.getString("id"));


                                            bean_Oprtions.add(beanoption);
                                            //   arrOptionType.add(jPOOption.getString("id"));


                                        }


                                        Bean_Schme_value bean_sc = new Bean_Schme_value();
                                        bean_sc.setGet_qty(jschme.getString("get_prod_qty"));

                                        //Log.e("555555555", "" + jschme.getString("max_qty"));
                                        bean_sc.setBuy_qty(jschme.getString("buy_prod_qty"));
                                        bean_sc.setMax_qty(jschme.getString("max_qty"));
                                        bean_sc.setDisc_per(jschme.getString("discount_percentage"));
                                        bean_sc.setType_id(jschme.getString("type_id"));
                                        bean_sc.setScheme_name(jschme.getString("scheme_name"));
                                        bean_sc.setScheme_id(jschme.getString("id"));
                                        //Log.e("Type ID11111 : ", "" + jschme.getString("type_id"));
                                        bean_schme.add(bean_sc);
                                        //   loadingView.dismiss();

                                        //        }
                                    }


                                    //loadingView.dismiss();

                                }

                            }
                        } catch (Exception j) {
                            j.printStackTrace();
                            //Log.e("json exce", j.getMessage());
                        }
                        Log.e("55555555555555", "" + bean_product_schme.size());
                        if (bean_product_schme.size() == 0) {

                            jarray_cart = new JSONArray();
                            for (int i = 0; i < 1; i++) {
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", u_id);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_cart.get(ii).getProduct_id());
                                    jobject.put("category_id", bean_product1.get(0).getPro_cat_id());
                                    jobject.put("name", bean_cart.get(ii).getProduct_name());

                                    jobject.put("pro_code", bean_cart.get(ii).getProduct_code());
                                    jobject.put("quantity", "" + q);
                                    jobject.put("mrp", bean_product1.get(0).getPro_mrp());
                                    jobject.put("selling_price", bean_product1.get(0).getPro_sellingprice());
                                    jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                    jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                    jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                    jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                    int counter = q;
                                    double amt = Double.parseDouble(bean_product1.get(0).getPro_sellingprice());
                                    double total = amt * counter;
                                    double w1 = round(total, 2);
                                    String str = String.format("%.2f", w1);

                                    jobject.put("item_total", str);
                                    jobject.put("pro_scheme", " ");
                                    jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                    jobject.put("scheme_id", " ");
                                    jobject.put("scheme_title", " ");
                                    jobject.put("scheme_pack_id", " ");
                                    if (list_of_images.size() == 0) {
                                        jobject.put("prod_img", bean_product1.get(0).getPro_image().toString());
                                        // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                    } else {
                                        jobject.put("prod_img", list_of_images.get(0).toString());
                                        //   bean.setPro_Images(list_of_images.get(0).toString());
                                    }


                                    jarray_cart.put(jobject);
                                } catch (JSONException e) {

                                }


                            }
                            array_value.clear();
                            if (kkk == 1) {
                                Log.e("6666666666", "66666666666666");
                                new Edit_Product().execute();

                            } else {
                                Log.e("7777777777777", "777777777777777");
                                new Add_Product().execute();
                            }

                            //db.Add_Product_cart(bean);




                                      *//*  Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                                        Intent i = new Intent(Product_List.this, Product_List.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);*//*


                        } else {
                            //Log.e("Type ID : ", "" + bean_schme.get(0).getType_id().toString());
                            if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("1")) {


                                String getq = bean_schme.get(0).getGet_qty();
                                String buyq = bean_schme.get(0).getBuy_qty();
                                String maxq = bean_schme.get(0).getMax_qty();

                                double getqu = Double.parseDouble(getq);
                                double buyqu = Double.parseDouble(buyq);
                                double maxqu = Double.parseDouble(maxq);
                                int qu = q;


                                String sell = bean_product1.get(0).getPro_sellingprice();

                                double se = Double.parseDouble(bean_product1.get(0).getPro_sellingprice()) * buyqu;

                                double se1 = buyqu + getqu;

                                double fse = se / se1;
                                double w1 = round(fse, 2);
                                sell = String.format("%.2f", w1);
                                // sell = String.valueOf(fse);

                                int fqu = qu + (int) getqu;
                                jarray_cart = new JSONArray();
                                for (int i = 0; i < 1; i++) {
                                    try {
                                        JSONObject jobject = new JSONObject();

                                        jobject.put("user_id", u_id);
                                        jobject.put("role_id", role_id);
                                        jobject.put("owner_id", owner_id);
                                        jobject.put("product_id", bean_cart.get(ii).getProduct_id());
                                        jobject.put("category_id", bean_product1.get(0).getPro_cat_id());
                                        jobject.put("name", bean_cart.get(ii).getProduct_name());

                                        jobject.put("pro_code", bean_cart.get(ii).getProduct_code());
                                        jobject.put("quantity", String.valueOf((int) fqu));
                                        jobject.put("mrp", bean_product1.get(0).getPro_mrp());
                                        jobject.put("selling_price", sell);
                                        jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                        jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                        jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                        jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                        double f = fqu * Double.parseDouble(sell);
                                        double w = round(f, 2);
                                        String str = String.format("%.2f", w);
                                        jobject.put("item_total", str);
                                        jobject.put("pro_scheme", " ");
                                        jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                        jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                        jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                        jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                        if (list_of_images.size() == 0) {
                                            jobject.put("prod_img", bean_product1.get(0).getPro_image().toString());
                                            // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                        } else {
                                            jobject.put("prod_img", list_of_images.get(0).toString());
                                            //   bean.setPro_Images(list_of_images.get(0).toString());
                                        }


                                        jarray_cart.put(jobject);
                                    } catch (JSONException e) {

                                    }


                                }
                                array_value.clear();
                                if (kkk == 1) {

                                    new Edit_Product().execute();
                                    Log.e("88888888888", "8888888888888");
                                } else {

                                    new Add_Product().execute();
                                    Log.e("999999999999999", "9999999999999");
                                }


                            } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("2")) {

                                jarray_cart = new JSONArray();
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", u_id);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_cart.get(ii).getProduct_id());
                                    jobject.put("category_id", bean_product1.get(0).getPro_cat_id());
                                    jobject.put("name", bean_cart.get(ii).getProduct_name());

                                    jobject.put("pro_code", bean_cart.get(ii).getProduct_code());
                                    jobject.put("quantity", q);
                                    jobject.put("mrp", bean_product1.get(0).getPro_mrp());
                                    jobject.put("selling_price", bean_product1.get(0).getPro_sellingprice());
                                    jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                    jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                    jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                    jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                    double f = q * Double.parseDouble(bean_product1.get(0).getPro_sellingprice());
                                    double w = round(f, 2);
                                    String str = String.format("%.2f", w);
                                    jobject.put("item_total", str);
                                    jobject.put("pro_scheme", " ");
                                    jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                    jobject.put("scheme_id", " ");
                                    jobject.put("scheme_title", " ");
                                    jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                    if (list_of_images.size() == 0) {
                                        jobject.put("prod_img", bean_product1.get(0).getPro_image().toString());
                                        // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                    } else {
                                        jobject.put("prod_img", list_of_images.get(0).toString());
                                        //   bean.setPro_Images(list_of_images.get(0).toString());
                                    }


                                    jarray_cart.put(jobject);
                                } catch (JSONException e) {

                                }


                                String getq = bean_schme.get(0).getGet_qty();
                                String buyq = bean_schme.get(0).getBuy_qty();
                                String maxq = bean_schme.get(0).getMax_qty();

                                double getqu = Double.parseDouble(getq);
                                double buyqu = Double.parseDouble(buyq);
                                double maxqu = Double.parseDouble(maxq);
                                int qu = q;

                                double a = qu / buyqu;
                                double b = a * getqu;
                                if (b > maxqu) {
                                    b = maxqu;
                                } else {
                                    b = b;
                                }

                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", u_id);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                    jobject.put("category_id", bean_product_schme.get(0).getPro_cat_id());
                                    jobject.put("name", bean_product_schme.get(0).getPro_name());
                                    jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                    jobject.put("quantity", String.valueOf((int) b));
                                    jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                    //Log.e("D1D1", "" + bean_product_schme.get(0).getPro_mrp());
                                    jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                    //Log.e("E1E1", "" + bean_product_schme.get(0).getPro_sellingprice());
                                    jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id().toString());
                                    jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name().toString());
                                    jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id().toString());
                                    jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name().toString());
                                    jobject.put("item_total", "0");
                                    jobject.put("pro_scheme", bean_product1.get(ii).getPro_id());
                                    jobject.put("pack_of", bean_product1.get(ii).getPro_label());
                                    jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                    //Log.e("C1C1", "" + bean_schme.get(0).getScheme_id());
                                    jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                    jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                    jobject.put("prod_img", bean_product_schme.get(0).getPro_image());
                                    jarray_cart.put(jobject);
                                } catch (JSONException e) {

                                }


                                // db.Add_Product_cart_scheme(bean_s);

                                array_value.clear();
                                if (kkk == 1) {

                                    new Edit_Product().execute();
                                    Log.e("10101010101", "1010101010");
                                } else {

                                    new Add_Product().execute();
                                    Log.e("1211212121212", "1212121212");
                                }

                            } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("3")) {


                                String getq = bean_schme.get(0).getGet_qty();
                                String buyq = bean_schme.get(0).getBuy_qty();
                                String maxq = bean_schme.get(0).getMax_qty();

                                double getqu = Double.parseDouble(getq);
                                double buyqu = Double.parseDouble(buyq);
                                double maxqu = Double.parseDouble(maxq);
                                int qu = q;


                                String sell = bean_product1.get(0).getPro_sellingprice();

                                String disc = bean_schme.get(0).getDisc_per().toString();

                                double se = Double.parseDouble(bean_product1.get(0).getPro_sellingprice()) * Double.parseDouble(bean_schme.get(0).getDisc_per().toString());

                                double se1 = se / 100;

                                double fse = Double.parseDouble(bean_product1.get(0).getPro_sellingprice()) - se1;
                                double w = round(fse, 2);
                                sell = String.format("%.2f", w);
                                //sell = String.valueOf(fse);

                                jarray_cart = new JSONArray();
                                for (int i = 0; i < 1; i++) {
                                    try {
                                        JSONObject jobject = new JSONObject();

                                        jobject.put("user_id", u_id);
                                        jobject.put("role_id", role_id);
                                        jobject.put("owner_id", owner_id);
                                        jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                        jobject.put("category_id", bean_product_schme.get(0).getPro_cat_id());
                                        jobject.put("name", bean_product_schme.get(0).getPro_name());
                                        jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                        jobject.put("quantity", "" + q);
                                        jobject.put("mrp", bean_product1.get(0).getPro_mrp());
                                        jobject.put("selling_price", sell);
                                        jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                        jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                        jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                        jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                        double f = Double.parseDouble("" + q) * Double.parseDouble(sell);
                                        double w1 = round(f, 2);
                                        String str = String.format("%.2f", w1);
                                        jobject.put("item_total", str);
                                        jobject.put("pro_scheme", " ");
                                        jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                        jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                        jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                        jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                        if (list_of_images.size() == 0) {
                                            jobject.put("prod_img", bean_product1.get(0).getPro_image().toString());
                                            // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                        } else {
                                            jobject.put("prod_img", list_of_images.get(0).toString());
                                            //   bean.setPro_Images(list_of_images.get(0).toString());
                                        }


                                        jarray_cart.put(jobject);
                                    } catch (JSONException e) {

                                    }


                                }
                                array_value.clear();
                                if (kkk == 1) {

                                    new Edit_Product().execute();
                                    Log.e("131131313131", "131311313131");
                                } else {

                                    new Add_Product().execute();
                                    Log.e("141411414141", "14141141414");
                                }
                            }
                        }

                    }
                }*/

            }
        });

        appPrefs = new AppPrefs(Sales_Order_History_Details.this);

        db = new DatabaseHandler(Sales_Order_History_Details.this);
        bean_cart = db.get_order_history(appPrefs.getOrder_history_id());

        im_billing = (ImageView) this.findViewById(R.id.im_billing);
        im_delivery = (ImageView) this.findViewById(R.id.im_delivery);
        option_icon = (ImageView) this.findViewById(R.id.option_icon);
        option_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Sales_Order_History_Details.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_order, popupMenu.getMenu());
                popupMenu.setGravity(Gravity.CENTER);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.more_info:
                                DisplayMetrics metrics = getResources().getDisplayMetrics();
                                int width = metrics.widthPixels;
                                int height = metrics.heightPixels;


                                dialog = new Dialog(Sales_Order_History_Details.this);
                                dialog.getWindow();
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.order_detail_layout);
                                dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 2);
                                dialog.setCancelable(true);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                                lcomment = (LinearLayout) dialog.findViewById(R.id.lcomment);
                                TextView txt_comment = (TextView) dialog.findViewById(R.id.txt_comt);
                                ImageView img_att = (ImageView) dialog.findViewById(R.id.img_attach);
                                LinearLayout l_att = (LinearLayout) dialog.findViewById(R.id.l_attach);
                                ImageView btn_cancel = (ImageView) dialog.findViewById(R.id.btn_cancel);
                                l_tracking = (ListView) dialog.findViewById(R.id.lst_tracking);


                                if (role_id.toString().equalsIgnoreCase("2") || role_id.toString().equalsIgnoreCase("10")) {
                                    l_att.setVisibility(View.GONE);
                                    lcomment.setVisibility(View.GONE);
                                } else {
                                    l_att.setVisibility(View.VISIBLE);
                                    lcomment.setVisibility(View.VISIBLE);
                                }
                                btn_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                                if (appPrefs.getComment().toString().equalsIgnoreCase("") || appPrefs.getComment().toString().equalsIgnoreCase(null)) {
                                    txt_comment.setText("N/A");
                                } else {
                                    txt_comment.setText(appPrefs.getComment().toString());
                                }
                                //Log.e("12121323",""+ Globals.server_link+appPrefs.getAttachment());

                                if (appPrefs.getAttachment().toString().equalsIgnoreCase("") || appPrefs.getAttachment().toString().equalsIgnoreCase(null)) {
                                    l_att.setVisibility(View.GONE);
                                } else {
                                    l_att.setVisibility(View.VISIBLE);
                                    Picasso.with(getApplicationContext())
                                            .load(Globals.server_link + "" + appPrefs.getAttachment())
                                            .placeholder(R.drawable.btl_watermark)
                                            .into(img_att);

                                }


                                new set_order_tracking().execute();


                                dialog.show();
                                break;
                            case R.id.order_enquiry:
                                DisplayMetrics metrics1 = getResources().getDisplayMetrics();
                                int width1 = metrics1.widthPixels;
                                int height1 = metrics1.heightPixels;


                                dialog = new Dialog(Sales_Order_History_Details.this);
                                dialog.getWindow();
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.order_inq_row);
                                dialog.getWindow().setLayout((6 * width1) / 7, (2 * height1) / 2);
                                dialog.setCancelable(true);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                                lcomment = (LinearLayout) dialog.findViewById(R.id.lcomment);
                                txt_comm = (EditText) dialog.findViewById(R.id.txt_comt);
                                Button btn_clear = (Button) dialog.findViewById(R.id.btn_clear_i);
                                Button btn_send = (Button) dialog.findViewById(R.id.btn_send);
                                ImageView btn_cancel1 = (ImageView) dialog.findViewById(R.id.btn_cancel);
                                btn_cancel1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();


                                    }
                                });
                                btn_send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String comment = txt_comm.getText().toString();

                                        if (comment.isEmpty()) {
                                            Globals.Toast2(getApplicationContext(), "Please Enter Enquiry");
                                            return;
                                        }

                                        List<NameValuePair> para = new ArrayList<NameValuePair>();
                                        appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                                        para.add(new BasicNameValuePair("order_id", appPrefs.getOrder_history_id()));
                                        para.add(new BasicNameValuePair("enquiry", txt_comm.getText().toString().trim()));

                                        Log.e("111111111", "" + appPrefs.getOrder_history_id());
                                        //Log.e("222222222", "" + owner_id);
                                        //Log.e("333333333", "" + u_id);
                                        isNotDone=true;
                                        new GetOrderEnquiry(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        while(isNotDone){

                                        }
                                        String json = jsonData; //GetOrderEnquiry(para);

                                        try {


                                            //System.out.println(json);

                                            if (json == null
                                                    || (json.equalsIgnoreCase(""))) {

                                                Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                                                // loadingView.dismiss();

                                            } else {
                                                JSONObject jObj = new JSONObject(json);

                                                String date = jObj.getString("status");

                                                if (date.equalsIgnoreCase("false")) {


                                                    String Message = jObj.getString("message");
                                                    Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());
                                                    dialog.dismiss();
                                                    txt_comm.setText("");

                                                } else {

                                                    String Message = jObj.getString("message");
                                                    Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());


                                                    dialog.dismiss();
                                                    txt_comm.setText("");

                                                }


                                            }
                                        } catch (Exception j) {
                                            j.printStackTrace();
                                            //Log.e("json exce", j.getMessage());
                                        }

                                    }
                                });
                                btn_clear.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        txt_comm.setText("");
                                    }
                                });

                                dialog.show();
                                break;

                            case R.id.order_enquiry_history:
                                DisplayMetrics metrics2 = getResources().getDisplayMetrics();
                                int width2 = metrics2.widthPixels;
                                int height2 = metrics2.heightPixels;


                                dialog = new Dialog(Sales_Order_History_Details.this);
                                dialog.getWindow();
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.order_en_hi_row);
                                dialog.getWindow().setLayout((6 * width2) / 7, (2 * height2) / 2);
                                dialog.setCancelable(true);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                ImageView img_clear = (ImageView) dialog.findViewById(R.id.btn_cancel);
                                lst_en = (ListView) dialog.findViewById(R.id.lst_en_row);

                                new set_order_enquiry().execute();

                                img_clear.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        more_info = (ImageView) this.findViewById(R.id.more_info);
        sc_amt = (ScrollView) this.findViewById(R.id.sc_amt);
        setRefershData();
        if (user_data.size() != 0) {
            for (int ii = 0; ii < user_data.size(); ii++) {

                //  user_id_main = user_data.get(ii).getUser_id().toString();
                role = user_data.get(ii).getUser_type().toString();

            }

        } else {
            //user_id_main = "";
        }
        if (role_id.equalsIgnoreCase("3") && role.equalsIgnoreCase("6")) {
            sc_amt.setVisibility(View.GONE);

        } else {

            sc_amt.setVisibility(View.VISIBLE);
        }
        order_inq = (ImageView) this.findViewById(R.id.inq);
        order_inq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;


                dialog = new Dialog(Sales_Order_History_Details.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.order_inq_row);
                dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 2);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                txt_comm = (EditText) dialog.findViewById(R.id.txt_comt);
                Button btn_clear = (Button) dialog.findViewById(R.id.btn_clear_i);
                Button btn_send = (Button) dialog.findViewById(R.id.btn_send);
                ImageView btn_cancel = (ImageView) dialog.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();


                    }
                });
                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        List<NameValuePair> para = new ArrayList<NameValuePair>();
                        appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                        para.add(new BasicNameValuePair("order_id", appPrefs.getOrder_history_id()));
                        para.add(new BasicNameValuePair("enquiry", txt_comm.getText().toString().trim()));

                        Log.e("111111111", "" + appPrefs.getOrder_history_id());
                        //Log.e("222222222", "" + owner_id);
                        //Log.e("333333333", "" + u_id);
                        isNotDone=true;
                        new GetOrderEnquiry(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        while(isNotDone){

                        }
                        String json = jsonData;

                        //String json = GetOrderEnquiry(para);

                        try {


                            //System.out.println(json);

                            if (json == null
                                    || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                                // loadingView.dismiss();

                            } else {
                                JSONObject jObj = new JSONObject(json);

                                String date = jObj.getString("status");

                                if (date.equalsIgnoreCase("false")) {


                                    String Message = jObj.getString("message");
                                    Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());
                                    dialog.dismiss();
                                    txt_comm.setText("");

                                } else {

                                    String Message = jObj.getString("message");
                                    Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());


                                    dialog.dismiss();
                                    txt_comm.setText("");

                                }


                            }
                        } catch (Exception j) {
                            j.printStackTrace();
                            //Log.e("json exce", j.getMessage());
                        }

                    }
                });
                btn_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_comm.setText("");
                    }
                });

                dialog.show();
            }
        });
        more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;


                dialog = new Dialog(Sales_Order_History_Details.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.order_detail_layout);
                dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 2);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView txt_comment = (TextView) dialog.findViewById(R.id.txt_comt);
                ImageView img_att = (ImageView) dialog.findViewById(R.id.img_attach);
                LinearLayout l_att = (LinearLayout) dialog.findViewById(R.id.l_attach);
                ImageView btn_cancel = (ImageView) dialog.findViewById(R.id.btn_cancel);
                l_tracking = (ListView) dialog.findViewById(R.id.lst_tracking);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                if (appPrefs.getComment().toString().equalsIgnoreCase("") || appPrefs.getComment().toString().equalsIgnoreCase(null)) {
                    txt_comment.setText("N/A");
                } else {
                    txt_comment.setText(appPrefs.getComment().toString());
                }
                //Log.e("12121323",""+Globals.server_link+appPrefs.getAttachment());

                if (appPrefs.getAttachment().toString().equalsIgnoreCase("") || appPrefs.getAttachment().toString().equalsIgnoreCase(null)) {
                    l_att.setVisibility(View.GONE);
                } else {
                    l_att.setVisibility(View.VISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(Globals.server_link + "" + appPrefs.getAttachment().toString())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_att);
                  /*  imageloader = new ImageLoader(Sales_Order_History_Details.this);

                    imageloader.DisplayImage(Globals.server_link +""+appPrefs.getAttachment().toString(), img_att);*/
                }


                new set_order_tracking().execute();


                dialog.show();
            }
        });
        txt_orderid = (TextView) this.findViewById(R.id.order_id_de);
        txt_orderdate = (TextView) this.findViewById(R.id.order_date_de);

        order_status = (TextView) this.findViewById(R.id.order_status);
        tv_total_product = (TextView) this.findViewById(R.id.tv_total_product);
        tv_subtotal = (TextView) this.findViewById(R.id.tv_subtotal);
        tv_order_total = (TextView) this.findViewById(R.id.tv_order_total);
        lv_checkout_product_list = (ListView) this.findViewById(R.id.lv_checkout_product_list);

        tv_user_name_billing = (TextView) this.findViewById(R.id.tv_user_name_billing);
        tv_number_billing = (TextView) this.findViewById(R.id.tv_number_billing);
        tv_address_billing = (TextView) this.findViewById(R.id.tv_address_billing);
        tv_user_name_delivery = (TextView) this.findViewById(R.id.tv_user_name_delivery);
        tv_number_delivery = (TextView) this.findViewById(R.id.tv_number_delivery);
        tv_address_delivery = (TextView) this.findViewById(R.id.tv_address_delivery);

        tv_user_name_billing.setText(bean_cart.get(0).getPayment_name());
        tv_user_name_delivery.setText(bean_cart.get(0).getShipping_name());
        tv_number_billing.setText(bean_cart.get(0).getPayment_address());
        tv_number_delivery.setText(bean_cart.get(0).getShipping_address());
       /* String final_full_address = "";

        if(!user_data.get(0).getAdd1().equalsIgnoreCase("")||!user_data.get(0).getAdd1().equalsIgnoreCase("null"))
        {
            final_full_address = user_data.get(0).getAdd1();
        }

        if(!user_data.get(0).getAdd2().equalsIgnoreCase("")||!user_data.get(0).getAdd2().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +", "+ user_data.get(0).getAdd2();
        }

        if(!user_data.get(0).getAdd3().equalsIgnoreCase("")||!user_data.get(0).getAdd3().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +", "+ user_data.get(0).getAdd3();
        }

        if(!user_data.get(0).getCity_name().equalsIgnoreCase("")||!user_data.get(0).getCity_name().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +",\n"+ user_data.get(0).getCity_name();
        }
        if(!user_data.get(0).getPincode().equalsIgnoreCase("")||!user_data.get(0).getPincode().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +", "+ user_data.get(0).getPincode();
        }
        if(!user_data.get(0).getState_name().equalsIgnoreCase("")||!user_data.get(0).getState_name().equalsIgnoreCase("null"))
        {
            final_full_address = final_full_address +", "+ user_data.get(0).getState_name();
            final_full_address = final_full_address + ", India";
        }
        tv_address_delivery.setText(final_full_address);

        tv_address_billing.setText(final_full_address);*/
        if (history != null) {
            if (history.isHasCouponApplied()) {
                lnr_coupon.setVisibility(View.VISIBLE);
            }
        }

        im_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        im_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        tv_total_product.setText("" + bean_cart.size());
        if (history != null) {
            if (history.isHasCouponApplied()) {

                double total = Double.parseDouble(bean_cart.get(0).getTotal());
                double subTotal = Double.parseDouble(bean_cart.get(0).getTotal()) + Double.parseDouble(history.getDiscount_amount());
                txt_couponname.setText("Coupon (" + history.getCoupon_name() + ") Discount :");
                txt_couponamount.setText(history.getDiscount_amount());

                tv_subtotal.setText(String.valueOf(subTotal));
                tv_order_total.setText(String.valueOf(total));
            } else {
                tv_order_total.setText("" + bean_cart.get(0).getTotal());
                tv_subtotal.setText("" + bean_cart.get(0).getTotal());
            }
        } else {
            tv_order_total.setText("" + bean_cart.get(0).getTotal());
            tv_subtotal.setText("" + bean_cart.get(0).getTotal());
        }

        // tv_subtotal.setText("" + bean_cart.get(0).getTotal());
        //tv_order_total.setText("" + bean_cart.get(0).getTotal());

        txt_orderid.setText("Order Id:#" + bean_cart.get(0).getOrder_id());
        txt_orderdate.setText("Order Date:" + bean_cart.get(0).getOrder_date());
        order_status.setText("Order Status:" + bean_cart.get(0).getOrder_status_is());

        btn_pay1 = (Button) findViewById(R.id.btn_pay1);
        btn_pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i = new Intent(CheckoutPage.this, Btl_Payment.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/

                final Dialog dialog = new Dialog(Sales_Order_History_Details.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.checkout_comment_layout);


                // set the custom dialog components - text, image and button
                final EditText ed_comment = (EditText) dialog.findViewById(R.id.ed_comment);
                Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
                Button btn_skip = (Button) dialog.findViewById(R.id.btn_skip);


                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

                btn_skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


                dialog.show();


                // new send_order_details().execute();
            }
        });
        setActionBar();

        adapter = new CustomResultAdapter();
        adapter.notifyDataSetChanged();
        lv_checkout_product_list.setAdapter(adapter);


     /*   tv_subtotal.setText(appPrefs.getCart_Grand_total());
        tv_order_total.setText(appPrefs.getCart_Grand_total());*/


    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);

        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.VISIBLE);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        appPrefs = new AppPrefs(Sales_Order_History_Details.this);
        String qun = appPrefs.getCart_QTy();

        setRefershData();

        appPrefs = new AppPrefs(Sales_Order_History_Details.this);
        String qu1 = appPrefs.getCart_QTy();
        if (qu1.equalsIgnoreCase("0") || qu1.equalsIgnoreCase("")) {
            txt.setVisibility(View.GONE);
            txt.setText("");
        } else {
            if (Integer.parseInt(qu1) > 999) {
                txt.setText("999+");
                txt.setVisibility(View.VISIBLE);
            } else {
                txt.setText(qu1 + "");
                txt.setVisibility(View.VISIBLE);
            }
        }
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                appPrefs.setUser_notification("Sales_Order_History_Details");
                Intent i = new Intent(Sales_Order_History_Details.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Sales_Order_History_Details.this, SalesOrderHistory.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
      /*  img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckoutPage.this,Main_CategoryPage.class);
                startActivity(i);
            }
        });*/
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sales_Order_History_Details.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sales_Order_History_Details.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(Sales_Order_History_Details.this, SalesOrderHistory.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();


    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Sales_Order_History_Details.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid = user_array_from_db.get(i).getId();
            String user_id = user_array_from_db.get(i).getUser_id();
            String email_id = user_array_from_db.get(i).getEmail_id();
            String phone_no = user_array_from_db.get(i).getPhone_no();
            String f_name = user_array_from_db.get(i).getF_name();
            String l_name = user_array_from_db.get(i).getL_name();
            String password = user_array_from_db.get(i).getPassword();
            String gender = user_array_from_db.get(i).getGender();
            String usertype = user_array_from_db.get(i).getUser_type();
            String login_with = user_array_from_db.get(i).getLogin_with();
            String str_rid = user_array_from_db.get(i).getStr_rid();
            String add1 = user_array_from_db.get(i).getAdd1();
            String add2 = user_array_from_db.get(i).getAdd2();
            String add3 = user_array_from_db.get(i).getAdd3();
            String landmark = user_array_from_db.get(i).getLandmark();
            String pincode = user_array_from_db.get(i).getPincode();
            String state_id = user_array_from_db.get(i).getState_id();
            String state_name = user_array_from_db.get(i).getState_name();
            String city_id = user_array_from_db.get(i).getCity_id();
            String city_name = user_array_from_db.get(i).getCity_name();
            String str_response = user_array_from_db.get(i).getStr_response();


            Bean_User_data contact = new Bean_User_data();
            contact.setId(uid);
            contact.setUser_id(user_id);
            contact.setEmail_id(email_id);
            contact.setPhone_no(phone_no);
            contact.setF_name(f_name);
            contact.setL_name(l_name);
            contact.setPassword(password);
            contact.setGender(gender);
            contact.setUser_type(usertype);
            contact.setLogin_with(login_with);
            contact.setStr_rid(str_rid);
            contact.setAdd1(add1);
            contact.setAdd2(add2);
            contact.setAdd3(add3);
            contact.setLandmark(landmark);
            contact.setPincode(pincode);
            contact.setState_id(state_id);
            contact.setState_name(state_name);
            contact.setCity_id(city_id);
            contact.setCity_name(city_name);
            contact.setStr_response(str_response);
            user_data.add(contact);

        }
        db.close();
    }

    public String GetProductDetailByCode(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Product_Details", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
        return json[0];
    }

    public String GetProductDetailByCode1(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "Scheme/App_Get_Scheme_Details", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    public String GetProductDetailByQty(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetItemQty", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    public String GetProductDetailByProductDetail(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Product_Details", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
        return json[0];
    }

    public String GetProductDetailByCode_schme(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Log.e("345678903",""+product_id);
                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "Scheme/App_Get_Scheme_Details", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    public String GetOrderEnquiry(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_Order_Enquiry", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    public String GetCartByQty(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //  System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    public class send_order_details extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Sales_Order_History_Details.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("OrderUserData", "" + jarray_OrderUserData));
                //Log.e("OrderUserData", "" + jarray_OrderUserData);
                parameters.add(new BasicNameValuePair("OrderTotalData", "" + jarray_OrderTotalData));
                //Log.e("OrderTotalData", "" + jarray_OrderTotalData);
                parameters.add(new BasicNameValuePair("OrderProductData", "" + jarray_OrderProductData));
                //Log.e("OrderProductData", "" + jarray_OrderProductData);


                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_AddOrder", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", Sales_Order_History_Details.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, Sales_Order_History_Details.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, Sales_Order_History_Details.this.getLayoutInflater());

                        Intent i = new Intent(Sales_Order_History_Details.this, Thankyou_Page.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class get_address_list extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Sales_Order_History_Details.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", user_id));
                //Log.e("user_id", user_id);


                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Address/App_Get_Addresses", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", Sales_Order_History_Details.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, Sales_Order_History_Details.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();

                        JSONArray jarray_data = jObj.getJSONArray("data");

                        for (int i = 0; i < jarray_data.length(); i++) {
                            JSONObject jobject_sub = jarray_data.getJSONObject(i);
                            JSONObject jobject_address = jobject_sub.getJSONObject("Address");
                            JSONObject jobject_country = jobject_sub.getJSONObject("Country");
                            JSONObject jobject_state = jobject_sub.getJSONObject("State");
                            JSONObject jobject_city = jobject_sub.getJSONObject("City");

                            Bean_Address bean = new Bean_Address();

                            bean.setAddress_id(jobject_address.getString("id"));
                            bean.setAddress_1(jobject_address.getString("address_1"));
                            bean.setAddress_2(jobject_address.getString("address_2"));
                            bean.setAddress_3(jobject_address.getString("address_3"));
                            bean.setLandmark(jobject_address.getString("landmark"));
                            bean.setPincode(jobject_address.getString("pincode"));
                            bean.setCountry(jobject_country.getString("name"));
                            bean.setState(jobject_state.getString("name"));
                            bean.setCity(jobject_city.getString("name"));


                            String final_address = "";
                            if (jobject_address.getString("address_1").equalsIgnoreCase("") || jobject_address.getString("address_1").equalsIgnoreCase("null")) {

                            } else {
                                final_address = jobject_address.getString("address_1");
                            }

                            if (jobject_address.getString("address_2").equalsIgnoreCase("") || jobject_address.getString("address_2").equalsIgnoreCase("null")) {

                            } else {
                                final_address = final_address + ", " + jobject_address.getString("address_2");
                            }

                            if (jobject_address.getString("address_3").equalsIgnoreCase("") || jobject_address.getString("address_3").equalsIgnoreCase("null")) {

                            } else {
                                final_address = final_address + ",\n" + jobject_address.getString("address_3");
                            }

                            if (jobject_address.getString("landmark").equalsIgnoreCase("") || jobject_address.getString("landmark").equalsIgnoreCase("null")) {

                            } else {
                                final_address = final_address + ", " + jobject_address.getString("landmark");
                            }

                            if (jobject_city.getString("name").equalsIgnoreCase("") || jobject_city.getString("name").equalsIgnoreCase("null")) {

                            } else {
                                final_address = final_address + ",\n" + jobject_city.getString("name");
                            }

                            if (jobject_address.getString("pincode").equalsIgnoreCase("") || jobject_address.getString("pincode").equalsIgnoreCase("null")) {

                            } else {
                                final_address = final_address + "-" + jobject_address.getString("pincode");
                            }

                            if (jobject_state.getString("name").equalsIgnoreCase("") || jobject_state.getString("name").equalsIgnoreCase("null")) {

                            } else {
                                final_address = final_address + ", " + jobject_state.getString("name");
                            }

                            if (jobject_country.getString("name").equalsIgnoreCase("") || jobject_country.getString("name").equalsIgnoreCase("null")) {

                            } else {
                                final_address = final_address + ", " + jobject_country.getString("name");
                            }


                            array_final_address.add(final_address);
                            array_address.add(bean);
                            if (jobject_address.getString("default_address").equalsIgnoreCase("1")) {
                                tv_address_billing.setText(final_address);
                                selected_billing_address_id = jobject_address.getString("id");
                                tv_address_delivery.setText(final_address);
                                selected_shipping_address_id = jobject_address.getString("id");
                            }


                        }


                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class CustomResultAdapter extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return bean_cart.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // result_holder = new ResultHolder();

            if (convertView == null) {

                convertView = vi.inflate(R.layout.custom_orderhistory_reorder, null);

            }

            final TextView tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
            final TextView tv_product_code = (TextView) convertView.findViewById(R.id.tv_product_code);
            final TextView tv_product_attribute = (TextView) convertView.findViewById(R.id.tv_product_attribute);
            final TextView tv_pack_of = (TextView) convertView.findViewById(R.id.tv_pack_of);
            final TextView tv_product_selling_price = (TextView) convertView.findViewById(R.id.tv_product_selling_price);
            final TextView tv_product_qty = (TextView) convertView.findViewById(R.id.tv_product_qty);
            final LinearLayout l_cal = (LinearLayout) convertView.findViewById(R.id.l_cal);
            final TextView txt_schme = (TextView) convertView.findViewById(R.id.txt_schme);
            final ImageView reorder = (ImageView) convertView.findViewById(R.id.reorder_img);
            final LinearLayout lcal = (LinearLayout) convertView.findViewById(R.id.l_cal);
            final LinearLayout l_pack = (LinearLayout) convertView.findViewById(R.id.l_pack);
            final TextView tv_qty = (TextView) convertView.findViewById(R.id.tv_qty);
            Log.e("cart schme type", "" + bean_cart.get(position).getScheme_type());
            if (!bean_cart.get(position).getScheme_type().equalsIgnoreCase("2")) {
                reorder.setVisibility(View.VISIBLE);
                lcal.setVisibility(View.VISIBLE);
                //  txt_schme.setVisibility(View.GONE);
                tv_product_qty.setText(bean_cart.get(position).getProduct_qty());
            } else {
                reorder.setVisibility(View.GONE);
                lcal.setVisibility(View.GONE);
                //   txt_schme.setVisibility(View.VISIBLE);
                //    txt_schme.setText(" Schme : "+bean_cart.get(position).getOrder_schme().toString());
                tv_product_qty.setText(bean_cart.get(position).getProduct_qty());
            }

            if (bean_cart.get(position).getOrder_schme().equalsIgnoreCase(" ") || bean_cart.get(position).getOrder_schme().equalsIgnoreCase("") || bean_cart.get(position).getOrder_schme().equalsIgnoreCase("null")) {
                txt_schme.setVisibility(View.GONE);
            } else {
                txt_schme.setVisibility(View.VISIBLE);
                txt_schme.setText(" Scheme : " + bean_cart.get(position).getOrder_schme().toString());
            }

            Log.e("def", "" + bean_cart.get(position).getPack_of());
            if (bean_cart.get(position).getPack_of() == null || bean_cart.get(position).getPack_of().equalsIgnoreCase("null") || bean_cart.get(position).getPack_of().trim().isEmpty()) {
                tv_pack_of.setVisibility(View.GONE);
            } else {
                tv_pack_of.setVisibility(View.VISIBLE);
                tv_pack_of.setText(bean_cart.get(position).getPack_of().toString());
            }
            tv_pack_of.setText(bean_cart.get(position).getPack_of().toString());
            final TextView tv_product_total_cost = (TextView) convertView.findViewById(R.id.tv_product_total_cost);

            setRefershData();
            if (user_data.size() != 0) {
                for (int ii = 0; ii < user_data.size(); ii++) {

                    //  user_id_main = user_data.get(ii).getUser_id().toString();
                    role = user_data.get(ii).getUser_type().toString();

                }

            } else {
                //user_id_main = "";
            }
            if (role_id.equalsIgnoreCase("3") && role.equalsIgnoreCase("6")) {
                tv_product_selling_price.setVisibility(View.GONE);
                tv_product_total_cost.setVisibility(View.GONE);
                l_cal.setVisibility(View.GONE);
            } else {
                tv_product_selling_price.setVisibility(View.VISIBLE);
                tv_product_total_cost.setVisibility(View.VISIBLE);
                l_cal.setVisibility(View.VISIBLE);
            }

            tv_product_name.setText(bean_cart.get(position).getProduct_name());
            tv_product_code.setText("(" + bean_cart.get(position).getProduct_code() + ")");
            tv_qty.setText(bean_cart.get(position).getProduct_qty());


            // tv_pack_of.setVisibility(View.GONE);

            tv_product_selling_price.setText(bean_cart.get(position).getProduct_selling_price());
            tv_product_qty.setText(bean_cart.get(position).getProduct_qty());

            tv_product_total_cost.setText(getResources().getString(R.string.Rs) + "" + bean_cart.get(position).getProduct_total());


            String[] option = bean_cart.get(position).getProduct_option_name().toString().split(", ");
            String[] value = bean_cart.get(position).getProduct_value_name().split(", ");
            String option_value = "";

            for (int i = 0; i < option.length; i++) {
                if (i == 0) {
                    option_value = option[i] + ": " + value[i];
                } else {
                    option_value = option_value + ", " + option[i] + ": " + value[i];
                }
            }

            tv_product_attribute.setText(option_value);
            reorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    setRefershData();

                    if (user_data.size() != 0) {
                        for (int i = 0; i < user_data.size(); i++) {

                            owner_id = user_data.get(i).getUser_id().toString();

                            role_id = user_data.get(i).getUser_type().toString();

                            if (role_id.equalsIgnoreCase("6")) {

                                appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                                role_id = appPrefs.getSubSalesId().toString();
                                u_id = appPrefs.getSalesPersonId().toString();
                            } else if (role_id.equalsIgnoreCase("7")) {
                                appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                                role_id = appPrefs.getSubSalesId().toString();
                                u_id = appPrefs.getSalesPersonId().toString();
                                //Log.e("IDIDD", "" + appPrefs.getSalesPersonId().toString());
                            } else {
                                u_id = owner_id;
                            }


                        }

                    } else {
                        user_id_main = "";
                    }

                    ArrayList<String> productIDs = new ArrayList<String>();
                    ArrayList<String> productQtys = new ArrayList<String>();
                    ArrayList<String> productOptionID = new ArrayList<String>();
                    ArrayList<String> productOptionName = new ArrayList<String>();
                    ArrayList<String> productOptionValueID = new ArrayList<String>();
                    ArrayList<String> productOptionValueName = new ArrayList<String>();


                    if (!bean_cart.get(position).getScheme_type().equalsIgnoreCase("2")) {
                        productIDs.add(bean_cart.get(position).getProduct_id());
                        productQtys.add(bean_cart.get(position).getProduct_qty());
                        productOptionID.add(bean_cart.get(position).getPro_Option_id());
                        productOptionValueID.add(bean_cart.get(position).getPro_Option_value_id());
                        productOptionName.add(bean_cart.get(position).getProduct_option_name());
                        productOptionValueName.add(bean_cart.get(position).getProduct_value_name());
                    }

                    Intent intent = new Intent(getApplicationContext(), ReOrderActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putStringArrayListExtra("productIDs", productIDs);
                    intent.putStringArrayListExtra("productQtys", productQtys);
                    intent.putStringArrayListExtra("productOptionIDs", productOptionID);
                    intent.putStringArrayListExtra("productOptionNames", productOptionName);
                    intent.putStringArrayListExtra("productOptionValueIDs", productOptionValueID);
                    intent.putStringArrayListExtra("productOptionValueNames", productOptionValueName);
                    startActivity(intent);

                    // product_id = tv_pop_pname.getTag().toString();
                    /*List<NameValuePair> para = new ArrayList<NameValuePair>();
                    para.add(new BasicNameValuePair("product_id", bean_cart.get(position).getProduct_id().toString()));
                    para.add(new BasicNameValuePair("owner_id", owner_id));
                    para.add(new BasicNameValuePair("user_id", u_id));
                    //Log.e("111111111", "" + bean_cart.get(position).getProduct_id().toString());
                    //Log.e("222222222", "" + owner_id);
                    //Log.e("333333333", "" + u_id);
                    isNotDone = true;
                    new GetProductDetailByQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    while (isNotDone) {

                    }
                    isNotDone = true;
                    String json = jsonData;//GetProductDetailByQty(para);

                    try {


                        //System.out.println(json);

                        if (json == null
                                || (json.equalsIgnoreCase(""))) {
                    *//*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*//*
                            Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                            // loadingView.dismiss();

                        } else {
                            JSONObject jObj = new JSONObject(json);

                            String date = jObj.getString("status");

                            if (date.equalsIgnoreCase("false")) {

                                qun = "0";
                                kkk = 0;
                                //Log.e("131313131331", "1");

                                // loadingView.dismiss();
                            } else {

                                JSONObject jobj = new JSONObject(json);


                                if (jobj != null) {


                                    JSONArray jsonArray = jObj.getJSONArray("data");
                                    for (int iu = 0; iu < jsonArray.length(); iu++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(iu);
                                        qun = jsonObject.getString("quantity");

                                    }

                                    //Log.e("131313131331", "" + qun);
                                    kkk = 1;


                                }


                            }

                        }
                    } catch (Exception j) {
                        j.printStackTrace();
                        //Log.e("json exce", j.getMessage());
                    }

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("product_id", bean_cart.get(position).getProduct_id()));
                    params.add(new BasicNameValuePair("user_id", user_id_main));
                    params.add(new BasicNameValuePair("role_id", role_id));
                    //Log.e("111111111",""+bean_cart.get(position).getProduct_id());
                    //Log.e("222222222",""+user_id_main);
                    //Log.e("333333333",""+role_id);
                    isNotDone = true;
                    new GetProductDetailByProductDetail(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    while (isNotDone) {

                    }
                    isNotDone = true;

                    String json2 = jsonData; //GetProductDetailByProductDetail(params);
                    //product_id = tv_pop_pname.getTag().toString();


                    try {


                        //System.out.println(json2);

                        if (json2.equalsIgnoreCase("")
                                || (json2.equalsIgnoreCase(""))) {

                            Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                            loadingView.dismiss();

                        } else {
                            JSONObject jObj = new JSONObject(json2);

                            String date = jObj.getString("status");

                            if (date.equalsIgnoreCase("false")) {
                                String Message = jObj.getString("message");

                                Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());
                                loadingView.dismiss();
                            } else {

                                bean_product1.clear();
                                bean_productOprtions.clear();
                                JSONArray jsonwish = jObj.optJSONArray("wish_list");

                                WishList = new ArrayList<String>();

                                for (int i = 0; i < jsonwish.length(); i++) {
                                    WishList.add(jsonwish.get(i).toString());
                                }

                                //Log.e("Wish List Size", "" + WishList.size());


                                JSONObject jobj = new JSONObject(json2);


                                if (jobj != null) {

                                    JSONObject jObjj = jObj.getJSONObject("data");
                                    JSONObject jproduct = jObjj.getJSONObject("Product");


                                    Bean_Product bean = new Bean_Product();


                                    bean.setPro_id(jproduct.getString("id"));


                                    bean.setPro_cat_id(jproduct.getString("category_id"));
                                    bean.setPro_code(jproduct.getString("product_code"));
                                    bean.setPro_name(jproduct.getString("product_name"));
                                    // bean.setPro_label(jproduct.getString("label_id"));
                                    bean.setPro_qty(jproduct.getString("qty"));
                                    bean.setPro_mrp(jproduct.getString("mrp"));
                                    bean.setPro_sellingprice(jproduct.getString("selling_price"));
                                    // bean.setPro_shortdesc(jproduct.getString("short_description"));
                                    bean.setPro_image(jproduct.getString("image"));
                                    //   bean.setPro_moreinfo(jproduct.getString("more_info"));
                                    JSONArray jschme = jObjj.getJSONArray("Scheme");
                                    //Log.e("A1111111", "" + jschme.length());

                                    String sa = "";

                                    for (int s1 = 0; s1 < jschme.length(); s1++) {
                                        JSONObject jProductScheme = jschme.getJSONObject(s1);

                                        ArrayList<String> a = new ArrayList<String>();
                                        a.add(jProductScheme.getString("scheme_name"));
                                        //Log.e("A2222222", "" + jProductScheme.getString("scheme_name"));
                                        sa += jProductScheme.getString("scheme_name").toString() + "\n";
                                        bean.setSchemea(a);
                                    }

                                    bean.setScheme(sa);
                                    JSONObject jlabel = jObjj.getJSONObject("Label");
                                    bean.setPro_label(jlabel.getString("name"));

                                    bean_product1.add(bean);


                                    Bean_Desc bean1 = new Bean_Desc();
                                    bean1.setPro_id(jproduct.getString("id"));
                                    //   Toast.makeText(BTLProduct_Detail.this, ""+bean1.getPro_id()+"-"+jproduct.getString("id"), Toast.LENGTH_SHORT).show();
                                    bean1.setDescription(jproduct.getString("description"));
                                    bean1.setSpecification(jproduct.getString("specification"));
                                    bean1.setTechnical(jproduct.getString("technical"));
                                    bean1.setCatalogs(jproduct.getString("catalogs"));
                                    bean1.setGuarantee(jproduct.getString("guarantee"));
                                    bean1.setVideos(jproduct.getString("videos"));
                                    bean1.setGeneral(jproduct.getString("general"));
                                    bean_desc1.add(bean1);

                                    JSONArray jProducttechnical = jObjj.getJSONArray("ProductTechnicalSpecification");
                                    //Log.e("BBBB", "" + jProducttechnical.length());
                                    for (int s = 0; s < jProducttechnical.length(); s++) {
                                        JSONObject jProducttechni = jProducttechnical.getJSONObject(s);

                                        Bean_texhnicalImages beantechnical = new Bean_texhnicalImages();
                                        beantechnical.setId(jProducttechni.getString("id"));
                                        beantechnical.setImg(jProducttechni.getString("image_path"));
                                        beantechnical.setProduct_id(jProducttechni.getString("product_id"));
                                        //Log.e("BaBa", "" + jProducttechni.getString("id"));
                                        bean_technical.add(beantechnical);

                                    }
                                    //Log.e("AbAb", "" + bean_technical.size());
                                    JSONArray jProductOption = jObjj.getJSONArray("ProductOption");


                                    for (int s = 0; s < jProductOption.length(); s++) {
                                        JSONObject jProductOptiono = jProductOption.getJSONObject(s);
                                        JSONObject jPOOption = jProductOptiono.getJSONObject("Option");
                                        JSONObject jPOOptionValue = jProductOptiono.getJSONObject("OptionValue");
                                        JSONArray jPOProductOptionImage = jProductOptiono.getJSONArray("ProductOptionImage");


                                        Bean_ProductOprtion beanoption = new Bean_ProductOprtion();
                                        // arrOptionType=new ArrayList<String>();


                                        //Option
                                        beanoption.setPro_Option_id(jPOOption.getString("id"));
                                        beanoption.setPro_Option_name(jPOOption.getString("name"));


                                        //OptionValue
                                        beanoption.setPro_Option_value_name(jPOOptionValue.getString("name"));
                                        beanoption.setPro_Option_value_id(jPOOptionValue.getString("id"));

                                        //ProductOptionImage


                                        //beanoption.setPro_id(jProductOptiono.getString("product_id"));
                                        beanoption.setPro_id(jProductOptiono.getString("parent_product_id"));
                                        //Log.e("ABABABABBABBA", "" + jProductOptiono.getString("parent_product_id"));
                                        beanoption.setOption_pro_id(jProductOptiono.getString("product_id"));
                                        //Log.e("ABABABABBABBA", "" + jProductOptiono.getString("product_id"));
                                        beanoption.setPro_Option_mrp(jProductOptiono.getString("mrp"));
                                        beanoption.setPro_Option_selling_price(jProductOptiono.getString("selling_price"));
                                        beanoption.setPro_Option_procode(jProductOptiono.getString("product_code"));
                                        //Log.e("ABABABABBABBA", "" + jProductOptiono.getString("product_code"));


                                        if (jPOProductOptionImage.length() == 0) {
                                            beanoption.setPro_Option_proimage("");
                                        } else {

                                            for (int t = 0; t < jPOProductOptionImage.length(); t++) {
                                                JSONObject jProductOptio = jPOProductOptionImage.getJSONObject(t);
                                                beanoption.setPro_Option_proimage(jProductOptio.getString("image"));
                                            }
                                        }
                                        bean_productOprtions.add(beanoption);
                                        //   arrOptionType.add(jPOOption.getString("id"));


                                    }


                                    //Log.e("Option Size1212", "" + bean_productOprtions.size());


                                    JSONArray jpProductImage = jObjj.getJSONArray("ProductImage");

                                    //Log.e("siz from array", jpProductImage.length() + "");

                                    if (jpProductImage.length() > 0) {
                                        for (int s = 0; s < jpProductImage.length(); s++) {
                                            JSONObject jpimg = jpProductImage.getJSONObject(s);


                                            Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                            beanproductimg.setPro_id(jpimg.getString("product_id"));
                                            beanproductimg.setPro_Images(jpimg.getString("image"));

                                            bean_productImages.add(beanproductimg);

                                        }
                                    } else {
                                        Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                        beanproductimg.setPro_id(jproduct.getString("id"));
                                        beanproductimg.setPro_Images(jproduct.getString("image"));

                                        bean_productImages.add(beanproductimg);
                                    }


                                }


                            }

                        }
                    } catch (Exception j) {
                        j.printStackTrace();
                    }


                    String qty = bean_cart.get(position).getProduct_qty();

                    int q = Integer.parseInt(bean_cart.get(position).getProduct_qty()) + Integer.parseInt(qun);

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("product_id", bean_cart.get(position).getProduct_id()));
                    params1.add(new BasicNameValuePair("user_id", user_id_main));
                    params1.add(new BasicNameValuePair("product_buy_qty", "" + q));
                    //Log.e("111111111",""+bean_cart.get(position).getProduct_id());
                    //Log.e("222222222",""+user_id_main);
                    //Log.e("333333333",""+q);
                    isNotDone = true;
                    new GetProductDetailByCode_schme(params1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    while (isNotDone) {

                    }
                    isNotDone = true;

                    String json3 = jsonData; //GetProductDetailByCode_schme(params1);

                    //new check_schme().execute();
                    try {


                        //System.out.println(json2);

                        if (json3 == null
                                || (json3.equalsIgnoreCase(""))) {

                            Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                            // loadingView.dismiss();

                        } else {
                            JSONObject jObj = new JSONObject(json3);

                            String date = jObj.getString("status");

                            if (date.equalsIgnoreCase("false")) {
                                String Message = jObj.getString("message");
                                bean_product_schme.clear();
                                bean_schme.clear();
                                bean_Oprtions.clear();

                            } else {

                                JSONObject jobj = new JSONObject(json3);
                                bean_product_schme.clear();
                                bean_schme.clear();
                                bean_Oprtions.clear();


                                if (jobj != null) {

                                    bean_product_schme.clear();
                                    bean_schme.clear();

                                    JSONObject jsonArray = jObj.getJSONObject("data");
                                    // //Log.e("3333333333",""+product_id);
                                    //   for (int i = 0; i < jsonArray.length(); i++) {
                                    // JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONObject jschme = jsonArray.getJSONObject("Scheme");

                                    JSONObject jproduct = jsonArray.getJSONObject("GetProduct");

                                    //Log.e("product",jproduct.toString()+"->"+jproduct.getString("id"));

                                    JSONObject jlabel = jproduct.getJSONObject("Label");


                                    Bean_Product bean = new Bean_Product();


                                    bean.setPro_id(jproduct.getString("id"));
                                    //Log.e("44444444",""+jproduct.getString("id"));

                                    bean.setPro_cat_id(jproduct.getString("category_id"));
                                    bean.setPro_code(jproduct.getString("product_code"));
                                    bean.setPro_name(jproduct.getString("product_name"));
                                    // bean.setPro_label(jproduct.getString("label_id"));
                                    bean.setPro_qty(jproduct.getString("qty"));
                                    bean.setPro_mrp(jproduct.getString("mrp"));

                                    //Log.e("A1A1",""+jproduct.getString("mrp").toString());
                                    bean.setPro_sellingprice(jproduct.getString("selling_price"));
                                    //Log.e("B1B1",""+jproduct.getString("selling_price").toString());
                                    // bean.setPro_shortdesc(jproduct.getString("short_description"));
                                    bean.setPro_image(jproduct.getString("image"));


                                    bean.setScheme("");

                                    bean.setPro_label(jlabel.getString("name"));
                                    bean_product_schme.add(bean);


                                    JSONArray jProductOption = jproduct.getJSONArray("ProductOption");


                                    for (int s = 0; s < jProductOption.length(); s++) {
                                        JSONObject jProductOptiono = jProductOption.getJSONObject(s);
                                        JSONObject jPOOption = jProductOptiono.getJSONObject("Option");
                                        JSONObject jPOOptionValue = jProductOptiono.getJSONObject("OptionValue");


                                        Bean_ProductOprtion beanoption = new Bean_ProductOprtion();
                                        // arrOptionType=new ArrayList<String>();


                                        //Option
                                        beanoption.setPro_Option_id(jPOOption.getString("id"));
                                        beanoption.setPro_Option_name(jPOOption.getString("name"));


                                        //OptionValue
                                        beanoption.setPro_Option_value_name(jPOOptionValue.getString("name"));
                                        beanoption.setPro_Option_value_id(jPOOptionValue.getString("id"));


                                        bean_Oprtions.add(beanoption);
                                        //   arrOptionType.add(jPOOption.getString("id"));


                                    }


                                    Bean_Schme_value bean_sc = new Bean_Schme_value();
                                    bean_sc.setGet_qty(jschme.getString("get_prod_qty"));

                                    //Log.e("555555555",""+jschme.getString("max_qty"));
                                    bean_sc.setBuy_qty(jschme.getString("buy_prod_qty"));
                                    bean_sc.setMax_qty(jschme.getString("max_qty"));
                                    bean_sc.setDisc_per(jschme.getString("discount_percentage"));
                                    bean_sc.setType_id(jschme.getString("type_id"));
                                    bean_sc.setScheme_name(jschme.getString("scheme_name"));
                                    bean_sc.setScheme_id(jschme.getString("id"));
                                    //Log.e("Type ID11111 : ",""+jschme.getString("type_id"));
                                    bean_schme.add(bean_sc);
                                    //   loadingView.dismiss();

                                    //        }
                                }


                                //loadingView.dismiss();

                            }

                        }
                    } catch (Exception j) {
                        j.printStackTrace();
                        //Log.e("json exce",j.getMessage());
                    }
                    //Log.e("45454545",""+bean_product_schme.size());
                    if (bean_product_schme.size() == 0) {


                        for (int i = 0; i < 1; i++) {
                            try {
                                JSONObject jobject = new JSONObject();

                                jobject.put("user_id", u_id);
                                jobject.put("role_id", role_id);
                                jobject.put("owner_id", owner_id);
                                jobject.put("product_id", bean_cart.get(position).getProduct_id());
                                jobject.put("category_id", bean_product1.get(0).getPro_cat_id());
                                jobject.put("name", bean_cart.get(position).getProduct_name());

                                jobject.put("pro_code", bean_cart.get(position).getProduct_code());
                                jobject.put("quantity", "" + q);
                                jobject.put("mrp", bean_product1.get(0).getPro_mrp());
                                jobject.put("selling_price", bean_product1.get(0).getPro_sellingprice());
                                jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                int counter = q;
                                double amt = Double.parseDouble(bean_product1.get(0).getPro_sellingprice());
                                double total = amt * counter;
                                double w1 = round(total, 2);
                                String str = String.format("%.2f", w1);

                                jobject.put("item_total", str);
                                jobject.put("pro_scheme", " ");
                                jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                jobject.put("scheme_id", " ");
                                jobject.put("scheme_title", " ");
                                jobject.put("scheme_pack_id", " ");
                                if (list_of_images.size() == 0) {
                                    jobject.put("prod_img", bean_product1.get(0).getPro_image().toString());
                                    // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                } else {
                                    jobject.put("prod_img", list_of_images.get(0).toString());
                                    //   bean.setPro_Images(list_of_images.get(0).toString());
                                }


                                jarray_cart.put(jobject);
                            } catch (JSONException e) {

                            }


                        }
                        array_value.clear();
                        if (kkk == 1) {

                            new Edit_Product().execute();

                        } else {
                            new Add_Product().execute();
                        }

                        //db.Add_Product_cart(bean);




                                      *//*  Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                                        Intent i = new Intent(Product_List.this, Product_List.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);*//*


                    } else {
                        //Log.e("Type ID : ",""+bean_schme.get(0).getType_id().toString());
                        if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("1")) {


                            String getq = bean_schme.get(0).getGet_qty();
                            String buyq = bean_schme.get(0).getBuy_qty();
                            String maxq = bean_schme.get(0).getMax_qty();

                            double getqu = Double.parseDouble(getq);
                            double buyqu = Double.parseDouble(buyq);
                            double maxqu = Double.parseDouble(maxq);
                            int qu = q;


                            String sell = bean_product1.get(0).getPro_sellingprice();

                            double se = Double.parseDouble(bean_product1.get(0).getPro_sellingprice()) * buyqu;

                            double se1 = buyqu + getqu;

                            double fse = se / se1;
                            double w1 = round(fse, 2);
                            sell = String.format("%.2f", w1);
                            //sell = String.valueOf(fse);

                            int fqu = qu + (int) getqu;

                            for (int i = 0; i < 1; i++) {
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", u_id);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_cart.get(position).getProduct_id());
                                    jobject.put("category_id", bean_product1.get(0).getPro_cat_id());
                                    jobject.put("name", bean_cart.get(position).getProduct_name());

                                    jobject.put("pro_code", bean_cart.get(position).getProduct_code());
                                    jobject.put("quantity", String.valueOf((int) fqu));
                                    jobject.put("mrp", bean_product1.get(0).getPro_mrp());
                                    jobject.put("selling_price", sell);
                                    jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                    jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                    jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                    jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                    double f = fqu * Double.parseDouble(sell);
                                    double w = round(f, 2);
                                    String str = String.format("%.2f", w);
                                    jobject.put("item_total", str);
                                    jobject.put("pro_scheme", " ");
                                    jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                    jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                    jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                    jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                    if (list_of_images.size() == 0) {
                                        jobject.put("prod_img", bean_product1.get(position).getPro_image().toString());
                                        // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                    } else {
                                        jobject.put("prod_img", list_of_images.get(0).toString());
                                        //   bean.setPro_Images(list_of_images.get(0).toString());
                                    }


                                    jarray_cart.put(jobject);
                                } catch (JSONException e) {

                                }


                            }
                            array_value.clear();
                            if (kkk == 1) {

                                new Edit_Product().execute();

                            } else {

                                new Add_Product().execute();
                            }


                        } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("2")) {


                            try {
                                JSONObject jobject = new JSONObject();

                                jobject.put("user_id", u_id);
                                jobject.put("role_id", role_id);
                                jobject.put("owner_id", owner_id);
                                jobject.put("product_id", bean_cart.get(position).getProduct_id());
                                jobject.put("category_id", bean_product1.get(0).getPro_cat_id());
                                jobject.put("name", bean_cart.get(position).getProduct_name());

                                jobject.put("pro_code", bean_cart.get(position).getProduct_code());
                                jobject.put("quantity", q);
                                jobject.put("mrp", bean_product1.get(0).getPro_mrp());
                                jobject.put("selling_price", bean_product1.get(0).getPro_sellingprice());
                                jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                double f = q * Double.parseDouble(bean_product1.get(0).getPro_sellingprice());
                                double w = round(f, 2);
                                String str = String.format("%.2f", w);
                                jobject.put("item_total", str);
                                jobject.put("pro_scheme", " ");
                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                jobject.put("scheme_id", " ");
                                jobject.put("scheme_title", " ");
                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                if (list_of_images.size() == 0) {
                                    jobject.put("prod_img", bean_product1.get(position).getPro_image().toString());
                                    // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                } else {
                                    jobject.put("prod_img", list_of_images.get(0).toString());
                                    //   bean.setPro_Images(list_of_images.get(0).toString());
                                }


                                jarray_cart.put(jobject);
                            } catch (JSONException e) {

                            }


                            String getq = bean_schme.get(0).getGet_qty();
                            String buyq = bean_schme.get(0).getBuy_qty();
                            String maxq = bean_schme.get(0).getMax_qty();

                            double getqu = Double.parseDouble(getq);
                            double buyqu = Double.parseDouble(buyq);
                            double maxqu = Double.parseDouble(maxq);
                            int qu = q;

                            double a = qu / buyqu;
                            double b = a * getqu;
                            if (b > maxqu) {
                                b = maxqu;
                            } else {
                                b = b;
                            }

                            try {
                                JSONObject jobject = new JSONObject();

                                jobject.put("user_id", u_id);
                                jobject.put("role_id", role_id);
                                jobject.put("owner_id", owner_id);
                                jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                jobject.put("category_id", bean_product_schme.get(0).getPro_cat_id());
                                jobject.put("name", bean_product_schme.get(0).getPro_name());
                                jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                jobject.put("quantity", String.valueOf((int) b));
                                jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                //Log.e("D1D1",""+bean_product_schme.get(0).getPro_mrp());
                                jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                //Log.e("E1E1",""+bean_product_schme.get(0).getPro_sellingprice());
                                jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id().toString());
                                jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name().toString());
                                jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id().toString());
                                jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name().toString());
                                jobject.put("item_total", "0");
                                jobject.put("pro_scheme", bean_product1.get(position).getPro_id());
                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                //Log.e("C1C1",""+bean_schme.get(0).getScheme_id());
                                jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                jobject.put("prod_img", bean_product_schme.get(0).getPro_image());
                                jarray_cart.put(jobject);
                            } catch (JSONException e) {

                            }


                            // db.Add_Product_cart_scheme(bean_s);

                            array_value.clear();
                            if (kkk == 1) {

                                new Edit_Product().execute();

                            } else {

                                new Add_Product().execute();
                            }

                        } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("3")) {


                            String getq = bean_schme.get(0).getGet_qty();
                            String buyq = bean_schme.get(0).getBuy_qty();
                            String maxq = bean_schme.get(0).getMax_qty();

                            double getqu = Double.parseDouble(getq);
                            double buyqu = Double.parseDouble(buyq);
                            double maxqu = Double.parseDouble(maxq);
                            int qu = q;


                            String sell = bean_product1.get(0).getPro_sellingprice();

                            String disc = bean_schme.get(0).getDisc_per().toString();

                            double se = Double.parseDouble(bean_product1.get(0).getPro_sellingprice()) * Double.parseDouble(bean_schme.get(0).getDisc_per().toString());

                            double se1 = se / 100;

                            double fse = Double.parseDouble(bean_product1.get(0).getPro_sellingprice()) - se1;
                            double w = round(fse, 2);
                            sell = String.format("%.2f", w);
                            // sell = String.valueOf(fse);


                            for (int i = 0; i < 1; i++) {
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", u_id);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                    jobject.put("category_id", bean_product_schme.get(0).getPro_cat_id());
                                    jobject.put("name", bean_product_schme.get(0).getPro_name());
                                    jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                    jobject.put("quantity", "" + q);
                                    jobject.put("mrp", bean_product1.get(0).getPro_mrp());
                                    jobject.put("selling_price", sell);
                                    jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                    jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                    jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                    jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                    double f = Double.parseDouble("" + q) * Double.parseDouble(sell);
                                    double w1 = round(f, 2);
                                    String str = String.format("%.2f", w1);
                                    jobject.put("item_total", str);
                                    jobject.put("pro_scheme", " ");
                                    jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                    jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                    jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                    jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                    if (list_of_images.size() == 0) {
                                        jobject.put("prod_img", bean_product1.get(position).getPro_image().toString());
                                        // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                    } else {
                                        jobject.put("prod_img", list_of_images.get(0).toString());
                                        //   bean.setPro_Images(list_of_images.get(0).toString());
                                    }


                                    jarray_cart.put(jobject);
                                } catch (JSONException e) {

                                }


                            }
                            array_value.clear();
                            if (kkk == 1) {

                                new Edit_Product().execute();

                            } else {

                                new Add_Product().execute();
                            }
                        }


                    }*/


                }
            });


            return convertView;
        }
    }

    public class set_order_tracking extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(Sales_Order_History_Details.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                parameters.add(new BasicNameValuePair("order_id", appPrefs.getOrder_history_id().toString()));
                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_Get_Order_Tracking", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", Sales_Order_History_Details.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    Boolean date = jObj.getBoolean("status");
                    if (date == false) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, Sales_Order_History_Details.this.getLayoutInflater());

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        order_track.clear();
                        JSONArray jarray_data = jObj.getJSONArray("order_history");

                        //JSONArray jsonobjcet_order_status = jObj.getJSONArray("order_status");

                        for (int j = 0; j < jarray_data.length(); j++) {
                            JSONObject jobject_main = jarray_data.getJSONObject(j);
                            JSONObject jobject_history = jobject_main.getJSONObject("OrderHistory");
                            JSONObject jobject_status = jobject_main.getJSONObject("OrderStatus");

                            Bean_Otracking bean = new Bean_Otracking();

                            bean.setOid(jobject_history.getString("id"));
                            bean.setOdate(jobject_history.getString("created"));
                            bean.setOstatus(jobject_status.getString("order_status_name"));
                            bean.setOcomment(jobject_history.getString("comment"));
                            order_track.add(bean);

                        }


                        adapter1 = new CustomAdapterOrderHistory_tracking();
                        adapter1.notifyDataSetChanged();
                        l_tracking.setAdapter(adapter1);

                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class CustomAdapterOrderHistory_tracking extends BaseAdapter {
        int current_position = -1;
        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return order_track.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            convertView = vi.inflate(R.layout.track_row, null);


            TextView txt_date = (TextView) convertView.findViewById(R.id.txt_date);
            TextView txt_status = (TextView) convertView.findViewById(R.id.txt_status);
            TextView txt_com = (TextView) convertView.findViewById(R.id.txt_com);

            txt_date.setText(order_track.get(position).getOdate().toString());
            txt_status.setText(order_track.get(position).getOstatus().toString());
            txt_com.setText(order_track.get(position).getOcomment().toString());


            return convertView;
        }
    }

    private class GetProductDetailByQty extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetProductDetailByQty(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {

            jsonData = "";
            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetItemQty", ServiceHandler.POST, pairList);
            isNotDone = false;
            return jsonData;
        }
    }

    private class GetProductDetailByCode extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetProductDetailByCode(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {

            jsonData = "";
            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Product_Details", ServiceHandler.POST, pairList);
            isNotDone = false;
            return jsonData;
        }
    }

    private class GetProductDetailByProductDetail extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetProductDetailByProductDetail(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {

            jsonData = "";
            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Product_Details", ServiceHandler.POST, pairList);
            isNotDone = false;
            return jsonData;
        }
    }

    private class GetProductDetailByCode_schme extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetProductDetailByCode_schme(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {

            jsonData = "";
            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "Scheme/App_Get_Scheme_Details", ServiceHandler.POST, pairList);
            isNotDone = false;
            return jsonData;
        }
    }

    public class Add_Product extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Sales_Order_History_Details.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("CartData", "" + jarray_cart));


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_AddCartData", ServiceHandler.POST, parameters);

                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                        int ii = Integer.parseInt(appPrefs.getOrdercheck());
                        int j = ii - 1;
                        appPrefs.setOrdercheck("" + j);
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                        Intent i = new Intent(Sales_Order_History_Details.this, Sales_Order_History_Details.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class Edit_Product extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Sales_Order_History_Details.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("CartData", "" + jarray_cart));


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_EditCartData", ServiceHandler.POST, parameters);

                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                        int ii = Integer.parseInt(appPrefs.getOrdercheck());
                        int j = ii - 1;
                        appPrefs.setOrdercheck("" + j);
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                        Intent i = new Intent(Sales_Order_History_Details.this, Sales_Order_History_Details.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class GetOrderEnquiry extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetOrderEnquiry(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {

            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_Order_Enquiry", ServiceHandler.POST, pairList);

            isNotDone=false;

            return jsonData;
        }
    }

    public class set_order_enquiry extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(Sales_Order_History_Details.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                parameters.add(new BasicNameValuePair("order_id", appPrefs.getOrder_history_id().toString()));
                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_Get_Order_Inquiry", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", Sales_Order_History_Details.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    Boolean date = jObj.getBoolean("status");
                    if (date == false) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Sales_Order_History_Details.this, "" + Message, Sales_Order_History_Details.this.getLayoutInflater());

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        order_e_history.clear();
                        JSONArray jarray_data = jObj.getJSONArray("order_inquiry");

                        //JSONArray jsonobjcet_order_status = jObj.getJSONArray("order_status");

                        for (int j = 0; j < jarray_data.length(); j++) {
                            JSONObject jobject_main = jarray_data.getJSONObject(j);

                            Bean_Order_E_History bean = new Bean_Order_E_History();

                            bean.setEnquiry(jobject_main.getString("enquiry"));
                            bean.setEnquiry_date(jobject_main.getString("created"));

                            order_e_history.add(bean);

                        }


                        adapter2 = new CustomAdapterOrderHistory_enquiry();
                        adapter2.notifyDataSetChanged();
                        lst_en.setAdapter(adapter2);

                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class CustomAdapterOrderHistory_enquiry extends BaseAdapter {
        int current_position = -1;
        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return order_e_history.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            convertView = vi.inflate(R.layout.en_hi_row, null);


            TextView txt_date = (TextView) convertView.findViewById(R.id.e_date);
            TextView txt_enquiry = (TextView) convertView.findViewById(R.id.txt_e);


            txt_date.setText(order_e_history.get(position).getEnquiry_date().toString());
            txt_enquiry.setText(order_e_history.get(position).getEnquiry().toString());

            return convertView;
        }
    }

    public class GetCartByQty extends AsyncTask<Void, Void, String> {

        List<NameValuePair> params = new ArrayList<>();

        public GetCartByQty(List<NameValuePair> params) {
            hasCartCallFinish = true;
            this.params = params;
        }

        @Override
        protected String doInBackground(Void... param) {


            Globals.generateNoteOnSD(getApplicationContext(), "CartData/App_GetCartQty" + "\n" + params.toString());

            String json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty", ServiceHandler.POST, params);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            cartJSON = json;
            Globals.generateNoteOnSD(getApplicationContext(), cartJSON);
            try {


                //System.out.println(json);

                if (json == null
                        || (json.equalsIgnoreCase(""))) {

                    Globals.CustomToast(Sales_Order_History_Details.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                        appPrefs.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        appPrefs = new AppPrefs(Sales_Order_History_Details.this);
                        appPrefs.setCart_QTy("" + qu);


                    }

                }


            } catch (Exception j) {
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = appPrefs.getCart_QTy();
            if (qu1.equalsIgnoreCase("0") || qu1.equalsIgnoreCase("")) {
                txt.setVisibility(View.GONE);
                txt.setText("");
            } else {
                if (Integer.parseInt(qu1) > 999) {
                    txt.setText("999+");
                    txt.setVisibility(View.VISIBLE);
                } else {
                    txt.setText(qu1 + "");
                    txt.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
