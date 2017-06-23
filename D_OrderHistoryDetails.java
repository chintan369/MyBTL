package com.agraeta.user.btl.Distributor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.BTL_Cart;
import com.agraeta.user.btl.Bean_Address;
import com.agraeta.user.btl.Bean_Order_history;
import com.agraeta.user.btl.Bean_ProductCart;
import com.agraeta.user.btl.Bean_User_data;
import com.agraeta.user.btl.CompanySalesPerson.Bean_Otracking;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.Notification;
import com.agraeta.user.btl.Order_history;
import com.agraeta.user.btl.R;
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

public class D_OrderHistoryDetails extends AppCompatActivity {
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    String user_id_main = new String();
    String role_id = new String();
    TextView reorder_all;

    Button btn_pay1;
    TextView tv_total_product,tv_order_total , tv_subtotal;
    ListView lv_checkout_product_list;
    ArrayList<Bean_Order_history> bean_cart = new ArrayList<Bean_Order_history>();
    ListView l_tracking;
    CustomResultAdapter adapter;
    Custom_ProgressDialog loadingView;
    JSONArray jarray_OrderProductData = new JSONArray();
    JSONArray jarray_OrderUserData = new JSONArray();
    JSONArray jarray_OrderTotalData = new JSONArray();
    String json;
    CustomAdapterOrderHistory_tracking adapter1;
    AppPrefs appPrefs;
    Dialog dialog;
    // ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    TextView tv_user_name_billing, tv_number_billing, tv_address_billing, tv_user_name_delivery, tv_number_delivery, tv_address_delivery;


    ImageView im_billing, im_delivery;

    ArrayList<Bean_Address> array_address = new ArrayList<Bean_Address>();
    ArrayList<String> array_final_address = new ArrayList<String>();

    String selected_billing_address= "";
    String selected_shipping_address = "";
    ArrayList<Bean_Otracking> order_track = new ArrayList<Bean_Otracking>();
    String selected_billing_address_id = "";
    String selected_shipping_address_id = "";
    String comment="";

    String user_id="";
    TextView order_status;
    DatabaseHandler db;
    TextView txt_orderid,txt_orderdate;
    Button more_info;
    TextView txt_couponname, txt_couponamount;
    LinearLayout lnr_amountdetails, lnr_moreinfo, lnr_coupon;
    Bean_Order_history history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__history__details);

        appPrefs = new AppPrefs(D_OrderHistoryDetails.this);

        db = new DatabaseHandler(D_OrderHistoryDetails.this);
        bean_cart = db.get_order_history(appPrefs.getOrder_history_id());

        setRefershData();



        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    appPrefs = new AppPrefs(D_OrderHistoryDetails.this);
                    role_id = appPrefs.getSubSalesId().toString();
                    user_id_main = appPrefs.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    appPrefs = new AppPrefs(D_OrderHistoryDetails.this);
                    role_id = appPrefs.getSubSalesId().toString();
                    user_id_main = appPrefs.getSalesPersonId().toString();
                    Log.e("IDIDD",""+appPrefs.getSalesPersonId().toString());
                }


            }

        }else{
            user_id_main ="";
        }
        Intent intent = getIntent();
        history = (Bean_Order_history) intent.getSerializableExtra("order");
        lnr_coupon = (LinearLayout) findViewById(R.id.lnr_coupon);
        txt_couponname = (TextView) findViewById(R.id.txt_coupon_name);
        txt_couponamount = (TextView) findViewById(R.id.tv_coupondiscount);

        reorder_all = (TextView) findViewById(R.id.reorder_txt);
        reorder_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < bean_cart.size(); i++) {
                    Bean_Order_history orderHistory = bean_cart.get(i);

                    ArrayList<Bean_ProductCart> array_product_cart = new ArrayList<Bean_ProductCart>();

                    db = new DatabaseHandler(D_OrderHistoryDetails.this);
                    Log.e("gfdgdfgfdg",""+orderHistory.getProduct_code());
                    array_product_cart=  db.is_product_in_cart("("+orderHistory.getProduct_code().toString()+")");

                    Log.e("New array product size", "" + array_product_cart.size());

                    if (array_product_cart.size() > 0)
                        Log.e("PRO QTY", array_product_cart.get(0).getPro_qty());

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("product_id", bean_cart.get(i).getProduct_id().toString()));
                    params.add(new BasicNameValuePair("user_id", user_id_main));
                    params.add(new BasicNameValuePair("role_id", role_id));


                    Log.e("params", params.toString());

                    Custom_ProgressDialog progressDialog = new Custom_ProgressDialog(D_OrderHistoryDetails.this, "");
                    progressDialog.setIndeterminate(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    String json = GetProductDetailByCode(params);

                    // String json = new GetProductDetail(params);
//                    String json = new String();
//                    json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Product_Details", ServiceHandler.POST, params);

                    progressDialog.dismiss();

                    if (json != null && !json.equalsIgnoreCase("")) {

                        try {

                            JSONObject mainOnj = new JSONObject(json);

                            if (mainOnj.getBoolean("status")) {
                                JSONObject data = mainOnj.getJSONObject("data");
                                JSONObject product = data.getJSONObject("Product");
                                JSONObject label = data.getJSONObject("Label");


                                Bean_ProductCart bean = new Bean_ProductCart();
                                String value_id = "";
                                String value_name = "";
                                value_name = orderHistory.getProduct_value_name();
                                value_id = orderHistory.getPro_Option_value_id();
                                bean.setPro_id(orderHistory.getProduct_id());
                                bean.setPro_cat_id(product.getString("category_id"));
                                Log.e("Cat_ID....", "" + product.getString("category_id"));
                                bean.setPro_Images(product.getString("image"));

                                bean.setPro_code(" ("+product.getString("product_code").toString()+")");
                                Log.e("-- ", "pro_code " + product.getString("product_code"));
                                Log.e("PRODUCT_CODE",""+" ( "+product.getString("product_code").toString()+" )");
                                bean.setPro_name(product.getString("product_name"));

                                String quantity = "0";

                                if (array_product_cart.size() > 0) {
                                    quantity = array_product_cart.get(0).getPro_qty();
                                    Log.e("ABDCD",""+quantity);
                                }
                                Log.e("cfgfdgtr",""+orderHistory.getProduct_qty());
                                int qty = Integer.parseInt(quantity) +Integer.parseInt(orderHistory.getProduct_qty());
                                Log.e("ABDCD",""+qty);
                                Log.e("Qty", "" + qty);

                                bean.setPro_qty(String.valueOf(qty));
                                bean.setPro_mrp(product.getString("mrp"));
                                bean.setPro_sellingprice(product.getString("selling_price"));
                                bean.setPro_shortdesc("1 " + label.getString("name"));
                                bean.setPro_Option_id(orderHistory.getPro_Option_id());
                                Log.e("--", "Option Id " + orderHistory.getPro_Option_value_id());

                                bean.setPro_Option_name(orderHistory.getProduct_option_name());
                                Log.e("-- ", "Option Name " + orderHistory.getProduct_option_name());

                                bean.setPro_Option_value_id(value_id);

                                Log.e("", "Value Name " + value_id);
                                bean.setPro_Option_value_name(value_name);
                                Log.e("", "Value Name " + value_name);

                                int total = Integer.parseInt(product.getString("selling_price")) * qty;

                                bean.setPro_total(String.valueOf(total));


                                db.Add_Product_cart(bean);
                                // C.Toast(getApplicationContext(), "Product Inserted into Cart", getLayoutInflater());
                                Globals.CustomToast(D_OrderHistoryDetails.this, "Product Inserted into Cart", D_OrderHistoryDetails.this.getLayoutInflater());

                            } else {
                                //C.Toast(getApplicationContext(), mainOnj.getString("message"), getLayoutInflater());
                                Globals.CustomToast(D_OrderHistoryDetails.this,mainOnj.getString("message"), D_OrderHistoryDetails.this.getLayoutInflater());

                            }

                        } catch (JSONException j) {
                            Log.e("JSON Exception", j.getMessage());
                        }
                    } else {
                        // C.Toast(getApplicationContext(), "SERVER ERROR", getLayoutInflater());
                        Globals.CustomToast(D_OrderHistoryDetails.this,"SERVER ERROR", D_OrderHistoryDetails.this.getLayoutInflater());

                    }
                }
            }
        });

        im_billing = (ImageView) this.findViewById(R.id.im_billing);
        im_delivery = (ImageView) this.findViewById(R.id.im_delivery);

        txt_orderid = (TextView) this.findViewById(R.id.order_id_de);
        txt_orderdate = (TextView) this.findViewById(R.id.order_date_de);
        order_status= (TextView) this.findViewById(R.id.order_status);

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
        more_info=(Button)this.findViewById(R.id.more_info);
        tv_user_name_billing.setText(bean_cart.get(0).getPayment_name());
        tv_user_name_delivery.setText(bean_cart.get(0).getShipping_name());
        tv_number_billing.setText(bean_cart.get(0).getPayment_address());
        tv_number_delivery.setText(bean_cart.get(0).getShipping_address());

        more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;


                dialog = new Dialog(D_OrderHistoryDetails.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.order_detail_layout);
                dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 2);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView txt_comment = (TextView)dialog.findViewById(R.id.txt_comt);
                ImageView img_att = (ImageView) dialog.findViewById(R.id.img_attach);
                LinearLayout l_att=(LinearLayout)dialog.findViewById(R.id.l_attach);
                ImageView btn_cancel=(ImageView)dialog.findViewById(R.id.btn_cancel);
                l_tracking=(ListView)dialog.findViewById(R.id.lst_tracking);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                appPrefs = new AppPrefs(D_OrderHistoryDetails.this);
                if(appPrefs.getComment().toString().equalsIgnoreCase("") || appPrefs.getComment().toString().equalsIgnoreCase(null)){
                    txt_comment.setText("N/A");
                }else{
                    txt_comment.setText(appPrefs.getComment().toString());
                }
                Log.e("12121323",""+ Globals.server_link+appPrefs.getAttachment());

                if(appPrefs.getAttachment().toString().equalsIgnoreCase("") || appPrefs.getAttachment().toString().equalsIgnoreCase(null)){
                    l_att.setVisibility(View.GONE);
                }else{
                    l_att.setVisibility(View.VISIBLE);
                    Picasso.with(getApplicationContext())
                            .load(Globals.server_link+appPrefs.getAttachment())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_att);
                  /*  imageloader = new ImageLoader(Sales_Order_History_Details.this);

                    imageloader.DisplayImage(Globals.server_link +""+appPrefs.getAttachment().toString(), img_att);*/
                }


                new set_order_tracking().execute();


                dialog.show();
            }
        });
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

        if(history!=null)
        {
            if(history.isHasCouponApplied())
            {
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
        //tv_subtotal.setText("" + bean_cart.get(0).getTotal());
        //tv_order_total.setText("" + bean_cart.get(0).getTotal());

        if (history != null) {
            if (history.isHasCouponApplied()) {

                float total = Float.parseFloat(bean_cart.get(0).getTotal());
                float subTotal = Float.parseFloat(bean_cart.get(0).getTotal()) + Float.parseFloat(history.getDiscount_amount());
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

        order_status.setText("Order Status:"+bean_cart.get(0).getOrder_status_is());
        txt_orderid.setText("Order Id:#"+bean_cart.get(0).getOrder_id());
        txt_orderdate.setText("Order Date:"+bean_cart.get(0).getOrder_date());

        btn_pay1=(Button)findViewById(R.id.btn_pay1);
        btn_pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent i = new Intent(CheckoutPage.this, Btl_Payment.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/

                final Dialog dialog = new Dialog(D_OrderHistoryDetails.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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


    public class send_order_details extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        D_OrderHistoryDetails.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("OrderUserData", ""+jarray_OrderUserData));
                Log.e("OrderUserData", "" + jarray_OrderUserData);
                parameters.add(new BasicNameValuePair("OrderTotalData", "" + jarray_OrderTotalData));
                Log.e("OrderTotalData", "" + jarray_OrderTotalData);
                parameters.add(new BasicNameValuePair("OrderProductData", "" + jarray_OrderProductData));
                Log.e("OrderProductData", "" + jarray_OrderProductData);


                Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Order/App_AddOrder",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(D_OrderHistoryDetails.this, "SERVER ERRER", D_OrderHistoryDetails.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(D_OrderHistoryDetails.this, "" + Message, D_OrderHistoryDetails.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(D_OrderHistoryDetails.this, "" + Message, D_OrderHistoryDetails.this.getLayoutInflater());

                        Intent i = new Intent(D_OrderHistoryDetails.this,Thankyou_Page.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        loadingView.dismiss();

                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class get_address_list extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        D_OrderHistoryDetails.this, "");

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
                Log.e("user_id", user_id);



                Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Address/App_Get_Addresses",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(D_OrderHistoryDetails.this, "SERVER ERRER", D_OrderHistoryDetails.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(D_OrderHistoryDetails.this, "" + Message, D_OrderHistoryDetails.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();

                        JSONArray jarray_data = jObj.getJSONArray("data");

                        for(int i = 0 ; i < jarray_data.length() ; i ++)
                        {
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
                            if(jobject_address.getString("address_1").equalsIgnoreCase("")||jobject_address.getString("address_1").equalsIgnoreCase("null"))
                            {

                            }
                            else
                            {
                                final_address = jobject_address.getString("address_1");
                            }

                            if(jobject_address.getString("address_2").equalsIgnoreCase("")||jobject_address.getString("address_2").equalsIgnoreCase("null"))
                            {

                            }
                            else
                            {
                                final_address = final_address+", "+ jobject_address.getString("address_2");
                            }

                            if(jobject_address.getString("address_3").equalsIgnoreCase("")||jobject_address.getString("address_3").equalsIgnoreCase("null"))
                            {

                            }
                            else
                            {
                                final_address = final_address+",\n"+ jobject_address.getString("address_3");
                            }

                            if(jobject_address.getString("landmark").equalsIgnoreCase("")||jobject_address.getString("landmark").equalsIgnoreCase("null"))
                            {

                            }
                            else
                            {
                                final_address = final_address+", "+ jobject_address.getString("landmark");
                            }

                            if(jobject_city.getString("name").equalsIgnoreCase("")||jobject_city.getString("name").equalsIgnoreCase("null"))
                            {

                            }
                            else
                            {
                                final_address = final_address+",\n"+ jobject_city.getString("name");
                            }

                            if(jobject_address.getString("pincode").equalsIgnoreCase("")||jobject_address.getString("pincode").equalsIgnoreCase("null"))
                            {

                            }
                            else
                            {
                                final_address = final_address+"-"+ jobject_address.getString("pincode");
                            }

                            if(jobject_state.getString("name").equalsIgnoreCase("")||jobject_state.getString("name").equalsIgnoreCase("null"))
                            {

                            }
                            else
                            {
                                final_address = final_address+", "+ jobject_state.getString("name");
                            }

                            if(jobject_country.getString("name").equalsIgnoreCase("")||jobject_country.getString("name").equalsIgnoreCase("null"))
                            {

                            }
                            else
                            {
                                final_address = final_address+", "+ jobject_country.getString("name");
                            }



                            array_final_address.add(final_address);
                            array_address.add(bean);
                            if(jobject_address.getString("default_address").equalsIgnoreCase("1"))
                            {
                                tv_address_billing.setText(final_address);
                                selected_billing_address_id = jobject_address.getString("id");
                                tv_address_delivery.setText(final_address);
                                selected_shipping_address_id = jobject_address.getString("id");
                            }


                        }


                        loadingView.dismiss();

                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
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
        img_cart.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(D_OrderHistoryDetails.this,BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(D_OrderHistoryDetails.this,D_OrderHistory.class);
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
                Intent i = new Intent(D_OrderHistoryDetails.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(D_OrderHistoryDetails.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }


    public class CustomResultAdapter extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater)getSystemService(
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
            final TextView tv_product_attribute = (TextView) convertView.findViewById(R.id.tv_product_attribute);
            final  TextView tv_pack_of = (TextView) convertView.findViewById(R.id.tv_pack_of);
            final TextView tv_product_selling_price = (TextView) convertView.findViewById(R.id.tv_product_selling_price);
            final TextView tv_product_qty = (TextView) convertView.findViewById(R.id.tv_product_qty);
            final TextView txt_schme = (TextView) convertView.findViewById(R.id.txt_schme);
            final TextView tv_product_total_cost = (TextView) convertView.findViewById(R.id.tv_product_total_cost);
            final ImageView reorder = (ImageView) convertView.findViewById(R.id.reorder_img);
            final LinearLayout lcal = (LinearLayout) convertView.findViewById(R.id.l_cal);
            final LinearLayout l_pack = (LinearLayout) convertView.findViewById(R.id.l_pack);
            if(bean_cart.get(position).getOrder_schme().equalsIgnoreCase("") || bean_cart.get(position).getOrder_schme().equalsIgnoreCase("null")){
                reorder.setVisibility(View.VISIBLE);
                lcal.setVisibility(View.VISIBLE);
                txt_schme.setVisibility(View.GONE);
                tv_product_qty.setText(bean_cart.get(position).getProduct_qty());
            }else {
                reorder.setVisibility(View.GONE);
                lcal.setVisibility(View.GONE);
                txt_schme.setVisibility(View.VISIBLE);
                txt_schme.setText(" Schme : "+bean_cart.get(position).getOrder_schme().toString());
                tv_product_qty.setText(bean_cart.get(position).getProduct_qty());

            }

            if(bean_cart.get(position).getPack_of().equalsIgnoreCase("null") ||bean_cart.get(position).getPack_of().equalsIgnoreCase("")) {
                tv_pack_of.setVisibility(View.GONE);
            }
            else{
                tv_pack_of.setVisibility(View.VISIBLE);
                tv_pack_of.setText(bean_cart.get(position).getPack_of().toString());
            }


            tv_product_name.setText(bean_cart.get(position).getProduct_name()+" "+bean_cart.get(position).getProduct_code());




            tv_pack_of.setVisibility(View.GONE);

            tv_product_selling_price.setText(bean_cart.get(position).getProduct_selling_price());
            tv_product_qty.setText(bean_cart.get(position).getProduct_qty());

            tv_product_total_cost.setText(getResources().getString(R.string.Rs)+""+bean_cart.get(position).getProduct_total());


            String[] option = bean_cart.get(position).getProduct_option_name().toString().split(", ");
            String[] value = bean_cart.get(position).getProduct_value_name().split(", ");
            String option_value = "";

            for(int i = 0 ; i < option.length ; i++)
            {
                if(i==0) {
                    option_value = option[i] + ": " + value[i];
                }
                else
                {
                    option_value = option_value +", "+ option[i] + ": " + value[i];
                }
            }

            tv_product_attribute.setText(option_value);

            reorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bean_Order_history orderHistory = bean_cart.get(position);

                    ArrayList<Bean_ProductCart> array_product_cart = new ArrayList<Bean_ProductCart>();

                    db = new DatabaseHandler(D_OrderHistoryDetails.this);
                    array_product_cart=  db.is_product_in_cart("("+orderHistory.getProduct_code().toString()+")");
                    // array_product_cart = db.is_product_in_cart(orderHistory.getProduct_code());
                    Log.e("product code",orderHistory.getProduct_code());
                    Log.e("New array product size", "" + array_product_cart.size());

                    if (array_product_cart.size() > 0)
                        Log.e("PRO QTY", array_product_cart.get(0).getPro_qty());

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("product_id", bean_cart.get(position).getProduct_id().toString()));
                    params.add(new BasicNameValuePair("user_id", user_id_main));
                    params.add(new BasicNameValuePair("role_id", role_id));

                    Log.e("params", params.toString());

                   /* Custom_ProgressDialog progressDialog = new Custom_ProgressDialog(Order_History_Details.this, "");
                    progressDialog.setIndeterminate(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();*/
                    String json = GetProductDetailByCode(params);
//                    String json = new String();
//                    json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Product_Details", ServiceHandler.POST, params);
//                   progressDialog.dismiss();

                    if (json != null && !json.equalsIgnoreCase("")) {

                        try {

                            JSONObject mainOnj = new JSONObject(json);

                            if (mainOnj.getBoolean("status")) {
                                JSONObject data = mainOnj.getJSONObject("data");
                                JSONObject product = data.getJSONObject("Product");
                                JSONObject label = data.getJSONObject("Label");


                                Bean_ProductCart bean = new Bean_ProductCart();
                                String value_id = "";
                                String value_name = "";
                                value_name = orderHistory.getProduct_value_name();
                                //value_id = orderHistory.getProduct_value_id();
                                bean.setPro_id(orderHistory.getProduct_id());
                                bean.setPro_cat_id(product.getString("category_id"));
                                Log.e("Cat_ID....", "" + product.getString("category_id"));
                                bean.setPro_Images(product.getString("image"));
                                bean.setPro_code(" ("+product.getString("product_code").toString()+")");
                                Log.e("-- ", "pro_code " + product.getString("product_code"));
                                bean.setPro_name(product.getString("product_name"));

                                String quantity = "0";

                                if (array_product_cart.size() > 0) {
                                    quantity = array_product_cart.get(0).getPro_qty();
                                }

                                int qty = Integer.parseInt(quantity) + Integer.parseInt(orderHistory.getProduct_qty());

                                Log.e("Qty", "" + qty);

                                bean.setPro_qty((String.valueOf(qty)));
                                bean.setPro_mrp(product.getString("mrp"));
                                bean.setPro_sellingprice(product.getString("selling_price"));
                                bean.setPro_shortdesc("1 " + label.getString("name"));
                                bean.setPro_Option_id(orderHistory.getPro_Option_id());
                                Log.e("--", "Option Id " + orderHistory.getPro_Option_id());

                                bean.setPro_Option_name(orderHistory.getProduct_option_name());
                                Log.e("-- ", "Option Name " + orderHistory.getProduct_option_name());

                                bean.setPro_Option_value_id(value_id);

                                Log.e("", "Value Name " + value_id);
                                bean.setPro_Option_value_name(value_name);
                                Log.e("", "Value Name " + value_name);

                                int total = Integer.parseInt(product.getString("selling_price")) * qty;

                                bean.setPro_total(String.valueOf(total));

                                db.Add_Product_cart(bean);

                                //C.Toast(getApplicationContext(), "Product Inserted into Cart", getLayoutInflater());
                                Globals.CustomToast(D_OrderHistoryDetails.this, "Product Inserted into Cart", D_OrderHistoryDetails.this.getLayoutInflater());
                            } else {
                                // C.Toast(getApplicationContext(), mainOnj.getString("message"), getLayoutInflater());
                                Globals.CustomToast(D_OrderHistoryDetails.this,mainOnj.getString("message"), D_OrderHistoryDetails.this.getLayoutInflater());

                            }

                        } catch (JSONException j) {
                            Log.e("JSON Exception", j.getMessage());
                        }
                    } else {
                        // C.Toast(getApplicationContext(), "SERVER ERROR", getLayoutInflater());
                        Globals.CustomToast(D_OrderHistoryDetails.this,"SERVER ERROR", D_OrderHistoryDetails.this.getLayoutInflater());

                    }


                }
            });


            return convertView;
        }
    }


    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(D_OrderHistoryDetails.this, D_OrderHistory.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();


    };

    public class set_order_tracking extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(D_OrderHistoryDetails.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                appPrefs = new AppPrefs(D_OrderHistoryDetails.this);
                parameters.add(new BasicNameValuePair("order_id", appPrefs.getOrder_history_id().toString()));
                Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Order/App_Get_Order_Tracking",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(D_OrderHistoryDetails.this, "SERVER ERRER", D_OrderHistoryDetails.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    Boolean date = jObj.getBoolean("status");
                    if (date == false) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(D_OrderHistoryDetails.this, "" + Message, D_OrderHistoryDetails.this.getLayoutInflater());

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        order_track.clear();
                        JSONArray jarray_data = jObj.getJSONArray("order_history");

                        //JSONArray jsonobjcet_order_status = jObj.getJSONArray("order_status");

                        for(int j = 0 ; j <jarray_data.length() ; j++)
                        {
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
                }}catch(JSONException j){
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

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(D_OrderHistoryDetails.this);

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

    public String GetProductDetailByCode(final List<NameValuePair> params){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link+ "Product/App_Get_Product_Details",ServiceHandler.POST,params);

                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){

        }
        return json[0];
    }
}
