package com.agraeta.user.btl;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.CompanySalesPerson.SalesOrderHistory;
import com.agraeta.user.btl.CompanySalesPerson.Sales_Order_History_Details;
import com.agraeta.user.btl.CompanySalesPerson.Sales_Order_History_Filter;
import com.agraeta.user.btl.DisSalesPerson.Dist_SalesOrderHistory;
import com.agraeta.user.btl.DisSalesPerson.Dist_Sales_Order_History_Details;
import com.agraeta.user.btl.DisSalesPerson.Dist_Sales_Order_History_Filter;
import com.agraeta.user.btl.Distributor.D_OrderHistory;
import com.agraeta.user.btl.Distributor.D_OrderHistoryDetails;
import com.agraeta.user.btl.Distributor.D_OrderHistoryFilter;
import com.agraeta.user.btl.Distributor.DisMyOrders;
import com.agraeta.user.btl.Distributor.DisOrderListActivity;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.combooffer.ComboCart;
import com.agraeta.user.btl.model.combooffer.ComboOfferItem;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BTL_Cart extends AppCompatActivity {
    private static final int EDIT_COMBO = 12;
    private static final int REQUEST_QUOTATION = 15;
    static double grand_total = 0;
    LinearLayout l_list;
    Button btl_checkout, bt_connshooping;
    TextView textview, tv_grand_total;
    //  ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_Cart_Data> bean_cart_data = new ArrayList<Bean_Cart_Data>();
    LinearLayout.LayoutParams params;
    AppPrefs app;
    String Grandtotal = "0.00";
    String str = "";
    DatabaseHandler db;
    String owner_id = new String();
    String u_id = new String();
    EditText edt_count;
    ListView list_cart_product;
    LinearLayout l_spinner, l_spinner_text, l_linear, l_sort, l_view_spinner;
    CustomResultAdapter adapter;
    ImageLoader imageloader;
    LinearLayout cart_gone, cart_visible;
    AppPrefs appPrefs;
    String user_id_main = new String();
    String role_id = new String();
    ArrayList<Double> array_total_price = new ArrayList<Double>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    double amount1;
    ImageView minuss, plus;
    Button buy_cart, cancel;
    String json = new String();
    JSONArray jarray_cart = new JSONArray();
    String role;
    TextView tv_pop_pname, tv_pop_code, tv_pop_packof, tv_pop_mrp, tv_pop_sellingprice, tv_total, tv_txt_mrp;
    Dialog dialog;
    String productpricce = new String();
    Button my_quotation;
    TextView txt_subtotal, txt_cart, summary;
    LinearLayout l_totall, layout_sub_total;
    String product_id = new String();
    String pt_id = new String();
    ArrayList<Bean_Schme_value> bean_schme = new ArrayList<Bean_Schme_value>();
    int kkk = 0;
    String qun = new String();
    String ss = new String();
    LinearLayout l_mrp, l_sell, l_total;
    TextView txt_selling_price, txt_total_txt;
    Custom_ProgressDialog loadingView;
    ArrayList<Bean_Product> bean_product_schme = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductOprtion> bean_Oprtions = new ArrayList<Bean_ProductOprtion>();
    int s = 0;
    String sche_id = new String();
    String Selling = new String();
    LinearLayout l_s;

    Button btn_deleteAll;

    boolean isNotDone = true;
    String jsonData = "";

    int minOrderValue = 0;

    AdminAPI adminAPI;
    LinearLayout layout_main;
    private String quotationID = "";

    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btl__cart);
        layout_main = (LinearLayout) findViewById(R.id.layout_main);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        imageloader = new ImageLoader(BTL_Cart.this);
        appPrefs = new AppPrefs(BTL_Cart.this);
        appPrefs.setCurrentPage("Cart");
        setActionBar();
        setRefershData();

        btn_deleteAll=(Button) findViewById(R.id.btn_deleteAll);
        btn_deleteAll.setVisibility(View.GONE);

        adminAPI= ServiceGenerator.getAPIServiceClass();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(BTL_Cart.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                    Snackbar.make(layout_main, "Taking Order for : " + app.getUserName(), Snackbar.LENGTH_LONG).show();
                } else {
                    u_id = owner_id;
                }


            }

        } else {
            u_id = "";
        }

        my_quotation = (Button) findViewById(R.id.my_quotation);
        new get_cartdata().execute();


    }

    private void setdata() {

        setRefershData();

        tv_grand_total = (TextView) findViewById(R.id.tv_grand_total);
        l_list = (LinearLayout) findViewById(R.id.list_cart);
        cart_gone = (LinearLayout) findViewById(R.id.cart_gone);
        cart_visible = (LinearLayout) findViewById(R.id.cart_visible);
        btl_checkout = (Button) findViewById(R.id.btl_checkout);
        bt_connshooping = (Button) findViewById(R.id.con_shopping);
        list_cart_product = (ListView) findViewById(R.id.list_cart_product);
        txt_subtotal = (TextView) findViewById(R.id.sub_total);
        txt_cart = (TextView) findViewById(R.id.txt_cart);
        l_totall = (LinearLayout) findViewById(R.id.l_totall);
        layout_sub_total = (LinearLayout) findViewById(R.id.layout_sub_total);
        summary = (TextView) findViewById(R.id.summary);
        l_s = (LinearLayout) findViewById(R.id.l_s);


        btn_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NameValuePair> pairList=new ArrayList<NameValuePair>();
                pairList.add(new BasicNameValuePair("owner_id",owner_id));
                pairList.add(new BasicNameValuePair("user_id",u_id));

                new GetCartDelete(pairList).execute();
            }
        });

        for (int i = 0; i < user_data.size(); i++) {

            user_id_main = user_data.get(i).getUser_id().toString();
            role_id = user_data.get(i).getUser_type().toString();
            //my_quotation.setVisibility(View.VISIBLE);
            if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                app = new AppPrefs(BTL_Cart.this);
                role_id = app.getSubSalesId().toString();
                user_id_main = app.getSalesPersonId().toString();
            }

            if (role_id.equalsIgnoreCase("0")) {

                txt_subtotal.setText("(GST will be added extra)");
            } else if (role_id.equalsIgnoreCase("2")) {

                txt_subtotal.setText("(GST Amount Inclusive)");
            } else if (role_id.equalsIgnoreCase("10")) {

                txt_subtotal.setText("(GST Amount Inclusive)");
            } else {
                txt_subtotal.setText("(GST will be added extra)");
            }
        }

        my_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(quotationID.isEmpty()){
                    new GetNewQuotationID(owner_id,user_id_main,role_id).execute();
                }
                else {
                    Log.e("ids",user_id_main+" "+quotationID);
                    Intent intent=new Intent(getApplicationContext(),My_Quotation.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("quotationID",quotationID);
                    startActivityForResult(intent,REQUEST_QUOTATION);
                }

                /*app = new AppPrefs(BTL_Cart.this);
                app.setqutation_id("1");
                Intent i = new Intent(BTL_Cart.this, My_Quotation.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
            }
        });
        if (appPrefs.getUserRoleId().equalsIgnoreCase("6"))
            if (appPrefs.getSubSalesId().equalsIgnoreCase("3")) {
                layout_sub_total.setVisibility(View.GONE);
                summary.setVisibility(View.GONE);

                l_s.setVisibility(View.GONE);
            } else {
                layout_sub_total.setVisibility(View.VISIBLE);
                summary.setVisibility(View.VISIBLE);
                //my_quotation.setVisibility(View.VISIBLE);

                l_s.setVisibility(View.VISIBLE);
            }

        if (user_data.size() == 0) {

            cart_gone.setVisibility(View.VISIBLE);
            cart_visible.setVisibility(View.GONE);
            Globals.CustomToast(BTL_Cart.this, "Please Login First", getLayoutInflater());

        } else {
            //Log.e("1212232321321", "" + bean_cart_data.size());
            //Log.e("1212232321321", "");

            cart_gone.setVisibility(View.GONE);
            cart_visible.setVisibility(View.VISIBLE);
            // my_quotation.setVisibility(View.VISIBLE);
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id().toString();
                role_id = user_data.get(i).getUser_type().toString();
                //my_quotation.setVisibility(View.VISIBLE);
                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(BTL_Cart.this);
                    role_id = app.getSubSalesId().toString();
                    user_id_main = app.getSalesPersonId().toString();
                }

                if (role_id.equalsIgnoreCase("0")) {

                    txt_subtotal.setText("(VAT will be added extra)");
                } else if (role_id.equalsIgnoreCase("2")) {

                    txt_subtotal.setText("(Tax Amount Inclusive)");
                } else if (role_id.equalsIgnoreCase("10")) {

                    txt_subtotal.setText("(Tax Amount Inclusive)");
                } else {
                    txt_subtotal.setText("(VAT will be added extra)");
                }


            }
            setRefershData();
            if (user_data.size() != 0) {
                for (int ii = 0; ii < user_data.size(); ii++) {

                    //  user_id_main = user_data.get(ii).getUser_id().toString();
                    role = user_data.get(ii).getUser_type();

                }

            } else {
                //user_id_main = "";
            }
            if (role_id.equalsIgnoreCase("3") && role.equalsIgnoreCase("6")) {
                l_totall.setVisibility(View.GONE);
            } else {
                l_totall.setVisibility(View.VISIBLE);
            }

        }


        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,//
                ActionBar.LayoutParams.WRAP_CONTENT, 1.0f);
        /*db = new DatabaseHandler(BTL_Cart.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();*/

        grand_total = 0;

        for (int i = 0; i < bean_cart_data.size(); i++) {
            //

            // grand_total = grand_total + array_total_price.get(i);
            if (bean_cart_data.get(i).isComboPack()) {
                grand_total += Float.parseFloat(bean_cart_data.get(i).getComboCart().getTotal_selling_price());
            } else {
                if (bean_cart_data.get(i).getItem_total().equalsIgnoreCase("null")) {

                } else {
                    array_total_price.add(Double.parseDouble(bean_cart_data.get(i).getItem_total()));
                    grand_total = grand_total + Double.parseDouble(bean_cart_data.get(i).getItem_total().replace("", ""));
                }
            }

            //Log.e("grand Total", "" + grand_total);

        }
        //float t = Float.parseFloat(grand_total);
        String str = String.format("%.2f", grand_total);
        tv_grand_total.setText(str);
        textview = (TextView) this.findViewById(R.id.MarqueeText);

        textview.setSelected(true);

        bt_connshooping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTL_Cart.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btl_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_data.size() == 0) {
                    Intent i = new Intent(BTL_Cart.this, LogInPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (bean_cart_data.size() == 0) {
                    Globals.CustomToast(BTL_Cart.this, "Please Insert some Product in Cart", getLayoutInflater());
                } else {

                    if (grand_total < minOrderValue) {
                        Globals.CustomToast(BTL_Cart.this, "Please Make Order of Min " + getString(R.string.ruppe_name) + " " + minOrderValue, getLayoutInflater());
                    } else {
                        appPrefs.setCart_Grand_total(tv_grand_total.getText().toString());
                        Intent i = new Intent(BTL_Cart.this, CheckoutPage_Product.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }


                }

            }
        });
        if (bean_cart_data.size() == 0) {
            list_cart_product.setVisibility(View.GONE);
            txt_cart.setVisibility(View.VISIBLE);
        } else {
            //Log.e("3434243243243",""+bean_cart_data.size());
            list_cart_product.setVisibility(View.VISIBLE);
            txt_cart.setVisibility(View.GONE);
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id().toString();
                role_id = user_data.get(i).getUser_type().toString();
                //my_quotation.setVisibility(View.VISIBLE);
                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(BTL_Cart.this);
                    role_id = app.getSubSalesId().toString();
                    user_id_main = app.getSalesPersonId().toString();
                }


            }

            adapter = new CustomResultAdapter();
            //   adapter.notifyDataSetChanged();
            list_cart_product.setAdapter(adapter);
        }
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(BTL_Cart.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

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

    private void setActionBar() {

        // TODO Auto-generated method stub
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);
        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);

        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(BTL_Cart.this);
                if (app.getUser_notification().toString().equalsIgnoreCase("mainpage_drawer")) {
                    Intent i = new Intent(BTL_Cart.this, MainPage_drawer.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("product")) {

                    db.close();
                    Intent i = new Intent(BTL_Cart.this, Product_List.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("product_detail")) {
                    Intent i = new Intent(BTL_Cart.this, BTLProduct_Detail.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("category")) {
                    Intent i = new Intent(BTL_Cart.this, Main_CategoryPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("aboutus")) {
                    Intent i = new Intent(BTL_Cart.this, AboutUs_Activity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Enquiry")) {
                    Intent i = new Intent(BTL_Cart.this, Enquiry.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("userprofile")) {
                    Intent i = new Intent(BTL_Cart.this, User_Profile.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("contact")) {
                    Intent i = new Intent(BTL_Cart.this, Contact.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("wish")) {
                    Intent i = new Intent(BTL_Cart.this, Btl_WishList.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("search")) {
                    Intent i = new Intent(BTL_Cart.this, Search.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("shopping")) {
                    Intent i = new Intent(BTL_Cart.this, Shopping_Product_view.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Add_Address")) {
                    Intent i = new Intent(BTL_Cart.this, Add_Address.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Add_Newuser_Address")) {
                    Intent i = new Intent(BTL_Cart.this, Add_Newuser_Address.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Add_to_shoppinglist")) {
                    Intent i = new Intent(BTL_Cart.this, Add_to_shoppinglist.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Btl_Edit_profile")) {
                    Intent i = new Intent(BTL_Cart.this, Btl_Edit_profile.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Change_password")) {
                    Intent i = new Intent(BTL_Cart.this, Change_password.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("CheckoutPage_Product")) {
                    Intent i = new Intent(BTL_Cart.this, CheckoutPage_Product.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Edit_Address")) {
                    Intent i = new Intent(BTL_Cart.this, Edit_Address.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("My_Quatation_Info")) {
                    Intent i = new Intent(BTL_Cart.this, My_Quatation_Info.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("My_Quotation")) {
                    Intent i = new Intent(BTL_Cart.this, My_Quotation.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Notification")) {
                    Intent i = new Intent(BTL_Cart.this, Notification.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Order_history")) {
                    Intent i = new Intent(BTL_Cart.this, Order_history.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Order_History_Details")) {
                    Intent i = new Intent(BTL_Cart.this, Order_History_Details.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Order_History_Filter")) {
                    Intent i = new Intent(BTL_Cart.this, Order_History_Filter.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Product_Filter")) {
                    Intent i = new Intent(BTL_Cart.this, Product_Filter.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("ProductPage")) {
                    Intent i = new Intent(BTL_Cart.this, ProductPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Saved_Address")) {
                    Intent i = new Intent(BTL_Cart.this, Saved_Address.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Shooping_List")) {
                    Intent i = new Intent(BTL_Cart.this, Shooping_List.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Technical_Gallery")) {
                    Intent i = new Intent(BTL_Cart.this, Technical_Gallery.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Where_To_Buy")) {
                    Intent i = new Intent(BTL_Cart.this, Where_To_Buy.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Sales_Order_History_Details")) {
                    Intent i = new Intent(BTL_Cart.this, Sales_Order_History_Details.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Sales_Order_History_Filter")) {
                    Intent i = new Intent(BTL_Cart.this, Sales_Order_History_Filter.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("SalesOrderHistory")) {
                    Intent i = new Intent(BTL_Cart.this, SalesOrderHistory.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Dist_Sales_Order_History_Details")) {
                    Intent i = new Intent(BTL_Cart.this, Dist_Sales_Order_History_Details.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Dist_Sales_Order_History_Filter")) {
                    Intent i = new Intent(BTL_Cart.this, Dist_Sales_Order_History_Filter.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Dist_SalesOrderHistory")) {
                    Intent i = new Intent(BTL_Cart.this, Dist_SalesOrderHistory.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("D_OrderHistory")) {
                    Intent i = new Intent(BTL_Cart.this, D_OrderHistory.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("D_OrderHistoryDetails")) {
                    Intent i = new Intent(BTL_Cart.this, D_OrderHistoryDetails.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("D_OrderHistoryFilter")) {
                    Intent i = new Intent(BTL_Cart.this, D_OrderHistoryFilter.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("DisMyOrders")) {
                    Intent i = new Intent(BTL_Cart.this, DisMyOrders.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("DisOrderListActivity")) {
                    Intent i = new Intent(BTL_Cart.this, DisOrderListActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getUser_notification().toString().equalsIgnoreCase("Map_Address")) {
                    Intent i = new Intent(BTL_Cart.this, Map_Address.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    Intent i = new Intent(BTL_Cart.this, MainPage_drawer.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BTL_Cart.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void setLayout(ArrayList<Bean_cartlist> str) {
        // TODO Auto-generated method stub

        l_list.removeAllViews();
        //Log.e("1", str.size() + "");

        for (int ij = 0; ij < str.size(); ij++) {

            //Log.e("2", ij + "");

            LinearLayout lmain = new LinearLayout(BTL_Cart.this);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            lmain.setPadding(1, 2, 1, 2);

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    200, 130, 1.0f);
            LinearLayout.LayoutParams par22 = new LinearLayout.LayoutParams(
                    30, 30);
            LinearLayout.LayoutParams par23 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, 90, 1.0f);

            LinearLayout l1 = new LinearLayout(BTL_Cart.this);
            l1.setLayoutParams(par1);
            l1.setPadding(5, 0, 5, 0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(BTL_Cart.this);

            l3.setLayoutParams(par3);
            //l3.setGravity(Gravity.LEFT | Gravity.CENTER);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 0, 5, 0);

            LinearLayout l4 = new LinearLayout(BTL_Cart.this);

            l4.setLayoutParams(par3);
            //l3.setGravity(Gravity.LEFT | Gravity.CENTER);
            l4.setOrientation(LinearLayout.VERTICAL);
            l4.setPadding(5, 0, 5, 0);

            LinearLayout l5 = new LinearLayout(BTL_Cart.this);

            l5.setLayoutParams(par3);
            l5.setGravity(Gravity.CENTER_VERTICAL);
            l5.setOrientation(LinearLayout.VERTICAL);
            l5.setPadding(5, 0, 5, 0);

            final LinearLayout l2 = new LinearLayout(BTL_Cart.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 0, 5, 0);


            final ImageView img_a = new ImageView(BTL_Cart.this);
            par21.setMargins(5, 5, 5, 5);
            img_a.setImageResource(str.get(ij).getImg_pro());
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            // img_a.setBackgroundResource(R.drawable.background_gradiant);
            img_a.setPadding(5, 0, 5, 0);
            img_a.setLayoutParams(par21);
         /*   l4.addView(img_a);*/

            final TextView txt_name = new TextView(BTL_Cart.this);
            txt_name.setText(str.get(ij).getPro_name());
            txt_name.setTextColor(Color.parseColor("#000000"));
            txt_name.setTextSize(12);
            txt_name.setTypeface(null, Typeface.BOLD);
            l3.addView(txt_name);

            final TextView txt_name_pack = new TextView(BTL_Cart.this);
            txt_name_pack.setText("Pack of : 1");
            txt_name_pack.setTextColor(Color.parseColor("#000000"));
            txt_name_pack.setTextSize(12);
            txt_name_pack.setTypeface(null, Typeface.BOLD);
            l3.addView(txt_name_pack);

            final TextView txt_rs = new TextView(BTL_Cart.this);
            txt_rs.setText(str.get(ij).getPro_oprice());
            txt_rs.setTextColor(Color.parseColor("#000000"));
            txt_rs.setTextSize(12);
            txt_rs.setTypeface(null, Typeface.BOLD);
            l2.addView(txt_rs);

            final ImageView img_cross = new ImageView(BTL_Cart.this);
            img_cross.setImageResource(R.drawable.ic_content_clear);
            img_cross.setPadding(5, 0, 5, 0);
            img_cross.setLayoutParams(par22);
            l2.addView(img_cross);


            final EditText edt_qun = new EditText(BTL_Cart.this);
            edt_qun.setText("1");
            edt_qun.setTextColor(Color.parseColor("#000000"));
            edt_qun.setBackgroundResource(R.drawable.background_gradiant);
            edt_qun.setInputType(InputType.TYPE_CLASS_NUMBER);
            edt_qun.setPadding(20, 20, 20, 20);
            edt_qun.setTextSize(12);
            l2.addView(edt_qun);

            final TextView txt_ors = new TextView(BTL_Cart.this);
            txt_ors.setText("=" + str.get(ij).getPro_cprice());
            txt_ors.setTextColor(Color.parseColor("#00AA49"));
            txt_ors.setTextSize(12);
            txt_ors.setTypeface(null, Typeface.BOLD);
            l2.addView(txt_ors);

            l3.addView(l2);


            final ImageView img_delete = new ImageView(BTL_Cart.this);
            par23.gravity = Gravity.CENTER_VERTICAL;
            img_delete.setImageResource(R.drawable.ic_action_delete);
            img_delete.setPadding(5, 0, 5, 0);
            img_delete.setLayoutParams(par23);
            l5.addView(img_delete);

            lmain.addView(img_a);
            lmain.addView(l3);
            lmain.addView(l5);

            l_list.addView(lmain);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_COMBO && resultCode == RESULT_OK) {
            new get_cartdata().execute();
        } else if (requestCode == REQUEST_QUOTATION && resultCode == RESULT_OK) {
            new get_cartdata().execute();
        }
    }

    public void onBackPressed() {
        app = new AppPrefs(BTL_Cart.this);
        if (app.getUser_notification().toString().equalsIgnoreCase("mainpage_drawer")) {
            Intent i = new Intent(BTL_Cart.this, MainPage_drawer.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("product")) {
                   /* db = new DatabaseHandler(BTL_Cart.this);
                    db.LogoutFilter();
                    db.Delete_ALL_table();
            db.close();*/
            Intent i = new Intent(BTL_Cart.this, Product_List.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("product_detail")) {
            Intent i = new Intent(BTL_Cart.this, BTLProduct_Detail.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("category")) {
            Intent i = new Intent(BTL_Cart.this, Main_CategoryPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("aboutus")) {
            Intent i = new Intent(BTL_Cart.this, AboutUs_Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Enquiry")) {
            Intent i = new Intent(BTL_Cart.this, Enquiry.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("userprofile")) {
            Intent i = new Intent(BTL_Cart.this, User_Profile.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("contact")) {
            Intent i = new Intent(BTL_Cart.this, Contact.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("wish")) {
            Intent i = new Intent(BTL_Cart.this, Btl_WishList.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("search")) {
            Intent i = new Intent(BTL_Cart.this, Search.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("shopping")) {
            Intent i = new Intent(BTL_Cart.this, Shopping_Product_view.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Add_Address")) {
            Intent i = new Intent(BTL_Cart.this, Add_Address.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Add_Newuser_Address")) {
            Intent i = new Intent(BTL_Cart.this, Add_Newuser_Address.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Add_to_shoppinglist")) {
            Intent i = new Intent(BTL_Cart.this, Add_to_shoppinglist.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Btl_Edit_profile")) {
            Intent i = new Intent(BTL_Cart.this, Btl_Edit_profile.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Change_password")) {
            Intent i = new Intent(BTL_Cart.this, Change_password.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("CheckoutPage_Product")) {
            Intent i = new Intent(BTL_Cart.this, CheckoutPage_Product.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Edit_Address")) {
            Intent i = new Intent(BTL_Cart.this, Edit_Address.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("My_Quatation_Info")) {
            Intent i = new Intent(BTL_Cart.this, My_Quatation_Info.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("My_Quotation")) {
            Intent i = new Intent(BTL_Cart.this, My_Quotation.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Notification")) {
            Intent i = new Intent(BTL_Cart.this, Notification.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Order_history")) {
            Intent i = new Intent(BTL_Cart.this, Order_history.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Order_History_Details")) {
            Intent i = new Intent(BTL_Cart.this, Order_History_Details.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Order_History_Filter")) {
            Intent i = new Intent(BTL_Cart.this, Order_History_Filter.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Product_Filter")) {
            Intent i = new Intent(BTL_Cart.this, Product_Filter.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("ProductPage")) {
            Intent i = new Intent(BTL_Cart.this, ProductPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Saved_Address")) {
            Intent i = new Intent(BTL_Cart.this, Saved_Address.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Shooping_List")) {
            Intent i = new Intent(BTL_Cart.this, Shooping_List.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Technical_Gallery")) {
            Intent i = new Intent(BTL_Cart.this, Technical_Gallery.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Where_To_Buy")) {
            Intent i = new Intent(BTL_Cart.this, Where_To_Buy.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Sales_Order_History_Details")) {
            Intent i = new Intent(BTL_Cart.this, Sales_Order_History_Details.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Sales_Order_History_Filter")) {
            Intent i = new Intent(BTL_Cart.this, Sales_Order_History_Filter.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("SalesOrderHistory")) {
            Intent i = new Intent(BTL_Cart.this, SalesOrderHistory.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Dist_Sales_Order_History_Details")) {
            Intent i = new Intent(BTL_Cart.this, Dist_Sales_Order_History_Details.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Dist_Sales_Order_History_Filter")) {
            Intent i = new Intent(BTL_Cart.this, Dist_Sales_Order_History_Filter.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Dist_SalesOrderHistory")) {
            Intent i = new Intent(BTL_Cart.this, Dist_SalesOrderHistory.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("D_OrderHistory")) {
            Intent i = new Intent(BTL_Cart.this, D_OrderHistory.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("D_OrderHistoryDetails")) {
            Intent i = new Intent(BTL_Cart.this, D_OrderHistoryDetails.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("D_OrderHistoryFilter")) {
            Intent i = new Intent(BTL_Cart.this, D_OrderHistoryFilter.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("DisMyOrders")) {
            Intent i = new Intent(BTL_Cart.this, DisMyOrders.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("DisOrderListActivity")) {
            Intent i = new Intent(BTL_Cart.this, DisOrderListActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getUser_notification().toString().equalsIgnoreCase("Map_Address")) {
            Intent i = new Intent(BTL_Cart.this, Map_Address.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Intent i = new Intent(BTL_Cart.this, MainPage_drawer.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

    }

    public String GetProductSelling(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Log.e("345678903",""+product_id);
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
        //Log.e("my json",json[0]);
        return json[0];
    }

    /*  static class ResultHolder {


        TextView product_name,product_code,product_packof,txt_mrp_main,product_mrp, product_sellingprice,p_del_prize,txt_total, tv_option_value;
        Button btn_buyonline;
        ImageView imageView2,plus,minus,img_shooping;
        EditText edt_count;0

    }*/

    public String GetProductDetailByCode(final List<NameValuePair> params) {

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

    public String GetProductDetailByQty(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Log.e("345678903",""+product_id);
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

    private class GetCartDelete extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();
        Custom_ProgressDialog dialog;

        public GetCartDelete(List<NameValuePair> pairList) {
            this.pairList = pairList;
            loadingView = new Custom_ProgressDialog(BTL_Cart.this, "");
            loadingView.setCancelable(false);
            loadingView.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_DeleteCart", ServiceHandler.POST, pairList);
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            loadingView.dismiss();
            try {


                //System.out.println(json);

                if (json == null
                        || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(BTL_Cart.this, "SERVER ERROR", getLayoutInflater());
                    // loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date1 = jObj.getString("status");


                    if (date1.equalsIgnoreCase("false")) {
                        //String Message1 = jObj.getString("message");
                        //Globals.CustomToast(CheckoutPage_Product.this, ""+Message1, getLayoutInflater());
                        // loadingView.dismiss();
                    } else {

                        Globals.CustomToast(getApplicationContext(), jObj.getString("message"), getLayoutInflater());
                        bean_cart_data.clear();
                        Intent i = new Intent(BTL_Cart.this, BTL_Cart.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }


        }
    }

    public class CustomResultAdapter extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return bean_cart_data.size();
        }

        @Override
        public void notifyDataSetChanged() {
            bean_cart_data.clear();
            super.notifyDataSetChanged();
        }

        @Override
        public Object getItem(int position) {
            return bean_cart_data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (bean_cart_data.get(position).isComboPack()) {
                convertView=vi.inflate(R.layout.layout_combo_cart,null);

                TextView txt_comboName=(TextView) convertView.findViewById(R.id.txt_comboName);
                TextView txt_mrpPrice=(TextView) convertView.findViewById(R.id.txt_mrpPrice);
                TextView txt_sellingPrice=(TextView) convertView.findViewById(R.id.txt_sellingPrice);
                TextView txt_totalQty=(TextView) convertView.findViewById(R.id.txt_totalQty);
                TextView txt_discount = (TextView) convertView.findViewById(R.id.txt_discount);
                ImageView img_editCombo=(ImageView) convertView.findViewById(R.id.img_editCombo);
                ImageView img_deleteCombo=(ImageView) convertView.findViewById(R.id.img_deleteCombo);

                txt_mrpPrice.setPaintFlags(txt_mrpPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                float mrpPrice = Float.parseFloat(bean_cart_data.get(position).getComboCart().getTotal_mrp());
                float sellingPrice = Float.parseFloat(bean_cart_data.get(position).getComboCart().getTotal_selling_price());

                if (mrpPrice > sellingPrice) {
                    double discount = (sellingPrice * 100) / mrpPrice;
                    discount = 100 - discount;
                    if (discount >= 1) {
                        txt_discount.setVisibility(View.VISIBLE);
                        txt_discount.setText("Discount : " + String.format(Locale.getDefault(), "%.2f", discount) + " % OFF");
                    }
                }

                txt_comboName.setText(bean_cart_data.get(position).getComboData().getOfferTitle());
                txt_mrpPrice.setText(getResources().getString(R.string.Rs) + " " + bean_cart_data.get(position).getComboCart().getTotal_mrp());
                txt_sellingPrice.setText(getResources().getString(R.string.Rs) + " " + bean_cart_data.get(position).getComboCart().getTotal_selling_price());
                txt_totalQty.setText(bean_cart_data.get(position).getComboCart().getProductQty());

                img_editCombo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(),ComboOfferActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("editMode",true);
                        intent.putExtra("combo_cart_id",bean_cart_data.get(position).getComboCart().getId());
                        intent.putExtra("combo_id",bean_cart_data.get(position).getComboCart().getCombo_id());
                        startActivityForResult(intent,EDIT_COMBO);
                    }
                });

                img_deleteCombo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingView=new Custom_ProgressDialog(BTL_Cart.this,"Please Wait...");
                        loadingView.setCancelable(false);
                        loadingView.show();

                        //String json=new Gson().toJson(bean_cart_data);

                        Call<AppModel> modelCall=adminAPI.deleteCombo(bean_cart_data.get(position).getComboCart().getId());
                        modelCall.enqueue(new Callback<AppModel>() {
                            @Override
                            public void onResponse(Call<AppModel> call, Response<AppModel> response) {
                                loadingView.dismiss();
                                Globals.Toast2(getApplicationContext(),response.body().getMessage());

                                if(response.body().isStatus()){
                                    Intent i = new Intent(BTL_Cart.this, BTL_Cart.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onFailure(Call<AppModel> call, Throwable t) {
                                loadingView.dismiss();
                            }
                        });
                    }
                });

            } else {

                convertView = vi.inflate(R.layout.cart_row, null);

                final TextView product_name = (TextView) convertView.findViewById(R.id.product_name);
                final TextView product_code = (TextView) convertView.findViewById(R.id.product_code);
                final TextView product_packof = (TextView) convertView.findViewById(R.id.product_packof);
                final TextView txt_mrp_main = (TextView) convertView.findViewById(R.id.txt_mrp_main);
                final TextView product_mrp = (TextView) convertView.findViewById(R.id.product_mrp);
                final TextView product_sellingprice = (TextView) convertView.findViewById(R.id.product_sellingprice);
                final LinearLayout l_lmrp = (LinearLayout) convertView.findViewById(R.id.l_lmrp);
                final LinearLayout l_lselling = (LinearLayout) convertView.findViewById(R.id.l_lselling);
                final LinearLayout l_ltotal = (LinearLayout) convertView.findViewById(R.id.l_ltotal);
                final TextView txt_total = (TextView) convertView.findViewById(R.id.txt_total);
                final TextView tv_option_value = (TextView) convertView.findViewById(R.id.tv_option_value);
                final TextView edt_count1 = (TextView) convertView.findViewById(R.id.cart_edit);
                final LinearLayout l_ll = (LinearLayout) convertView.findViewById(R.id.l_ll);
                final TextView l_m = (TextView) convertView.findViewById(R.id.l_m);
                final TextView multiple = (TextView) convertView.findViewById(R.id.multiple);
                final TextView a_product_sellingprice = (TextView) convertView.findViewById(R.id.a_product_sellingprice);
                final TextView rs = (TextView) convertView.findViewById(R.id.rs);
                final TextView tv_Scheme = (TextView) convertView.findViewById(R.id.tv_Scheme);
                final TextView off_tag = (TextView) convertView.findViewById(R.id.off_tag);

                edt_count1.setInputType(InputType.TYPE_CLASS_NUMBER);

                final ImageView plus1 = (ImageView) convertView.findViewById(R.id.plus);
                final ImageView minus = (ImageView) convertView.findViewById(R.id.minus);
                final ImageView img_shooping = (ImageView) convertView.findViewById(R.id.img_shooping);
                final ImageView imageView2 = (ImageView) convertView.findViewById(R.id.imageView2);

                if (!bean_cart_data.get(position).getPro_scheme().trim().equalsIgnoreCase("")) {
                    product_mrp.setVisibility(View.GONE);
                    product_sellingprice.setVisibility(View.GONE);
                    a_product_sellingprice.setVisibility(View.GONE);
                    rs.setVisibility(View.GONE);
                    multiple.setVisibility(View.GONE);
                    l_m.setVisibility(View.GONE);
                    l_lselling.setVisibility(View.GONE);
                    l_ltotal.setVisibility(View.GONE);
                    txt_total.setVisibility(View.GONE);
                    tv_option_value.setText(bean_cart_data.get(position).getOption_name() + ":" + bean_cart_data.get(position).getOption_value_name() + "\n" + "QTY : " + bean_cart_data.get(position).getQuantity());
                    plus1.setVisibility(View.GONE);
                    l_ll.setVisibility(View.GONE);
                    minus.setVisibility(View.GONE);
                    img_shooping.setVisibility(View.GONE);
                    edt_count1.setVisibility(View.GONE);
                    product_name.setText(bean_cart_data.get(position).getName());
                    product_name.setTag(bean_cart_data.get(position).getProduct_id());
                    product_code.setText(bean_cart_data.get(position).getPro_code());
                    product_packof.setText(bean_cart_data.get(position).getPack_of());
                    l_lmrp.setVisibility(View.GONE);
                    off_tag.setText("Free");
                    if (bean_cart_data.get(position).getScheme_title().toString().equalsIgnoreCase(" ")) {
                        tv_Scheme.setVisibility(View.GONE);
                    } else {
                        tv_Scheme.setVisibility(View.VISIBLE);
                        tv_Scheme.setText("Scheme - " + bean_cart_data.get(position).getScheme_title().toString());
                    }
                    Picasso.with(getApplicationContext()).load(Globals.server_link + "files/" + bean_cart_data.get(position).getProd_img()).placeholder(R.drawable.btl_watermark).into(imageView2);
                } else {
                    l_ll.setVisibility(View.VISIBLE);
                    product_mrp.setVisibility(View.VISIBLE);
                    product_sellingprice.setVisibility(View.VISIBLE);
                    a_product_sellingprice.setVisibility(View.VISIBLE);
                    rs.setVisibility(View.VISIBLE);
                    l_lmrp.setVisibility(View.VISIBLE);
                    multiple.setVisibility(View.VISIBLE);
                    l_m.setVisibility(View.VISIBLE);
                    l_lselling.setVisibility(View.VISIBLE);
                    l_ltotal.setVisibility(View.VISIBLE);
                    txt_total.setVisibility(View.VISIBLE);
                    tv_option_value.setVisibility(View.GONE);
                    plus1.setVisibility(View.GONE);
                    l_ll.setVisibility(View.VISIBLE);
                    minus.setVisibility(View.GONE);
                    img_shooping.setVisibility(View.VISIBLE);
                    edt_count1.setVisibility(View.VISIBLE);
                    product_name.setText(bean_cart_data.get(position).getName());
                    product_name.setTag(bean_cart_data.get(position).getProduct_id());
                    product_code.setText(bean_cart_data.get(position).getPro_code());
                    product_packof.setText(bean_cart_data.get(position).getPack_of());

                    product_mrp.setText(bean_cart_data.get(position).getMrp());
                    product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
                    a_product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
                    if (bean_cart_data.get(position).getScheme_title().equalsIgnoreCase(" ")) {
                        tv_Scheme.setVisibility(View.GONE);
                    } else {
                        tv_Scheme.setVisibility(View.VISIBLE);
                        tv_Scheme.setText(bean_cart_data.get(position).getScheme_title());
                    }

                    product_sellingprice.setTag(bean_cart_data.get(position).getSelling_price());

                    double mrp = Double.parseDouble(bean_cart_data.get(position).getMrp());
                    double sellingprice = Double.parseDouble(bean_cart_data.get(position).getSelling_price());
                    // Log.e("MRP", "" + mrp);
                    // Log.e("sellingprice", "" + sellingprice);
                    if (mrp > sellingprice) {

                        l_lmrp.setVisibility(View.VISIBLE);

                        off_tag.setVisibility(View.VISIBLE);


                        double off = mrp - sellingprice;
                        double offa = off * 100;
                        double o = offa / mrp;
                        int a = (int) o;

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

                            off_tag.setText(String.valueOf(a) + "% OFF");
                            off_tag.setVisibility(View.INVISIBLE);
                        } else {
                            off_tag.setText(String.valueOf(a) + "% OFF");
                            off_tag.setVisibility(View.VISIBLE);
                        }
                        //Log.e("111111111", "" + mrp);
                        product_mrp.setText(bean_cart_data.get(position).getMrp());

                        product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
                        a_product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());

                        product_mrp.setPaintFlags(product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    } else {
                        l_lmrp.setVisibility(View.GONE);
                        //Log.e("22222222222", "" + mrp);
                        product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
                        a_product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
                        off_tag.setVisibility(View.GONE);

                    }
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
                        l_lmrp.setVisibility(View.GONE);
                        l_lselling.setVisibility(View.GONE);
                        l_ltotal.setVisibility(View.GONE);
                        a_product_sellingprice.setVisibility(View.GONE);
                        rs.setVisibility(View.GONE);
                        multiple.setVisibility(View.GONE);
                    } else {
                        l_lmrp.setVisibility(View.VISIBLE);
                        l_lselling.setVisibility(View.VISIBLE);
                        l_ltotal.setVisibility(View.VISIBLE);
                        rs.setVisibility(View.VISIBLE);
                        a_product_sellingprice.setVisibility(View.VISIBLE);
                        multiple.setVisibility(View.VISIBLE);
                        double mrp1 = Double.parseDouble(bean_cart_data.get(position).getMrp());
                        double sellingprice1 = Double.parseDouble(bean_cart_data.get(position).getSelling_price());
                        //Log.e("MRP", "" + mrp1);
                        // Log.e("sellingprice", "" + sellingprice1);
                        if (mrp1 > sellingprice1) {
                            l_lmrp.setVisibility(View.VISIBLE);
                            //Log.e("111111111", "" + mrp);
                            product_mrp.setText(bean_cart_data.get(position).getMrp());

                            product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
                            a_product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
                            product_mrp.setPaintFlags(product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        } else {
                            l_lmrp.setVisibility(View.GONE);
                            //Log.e("22222222222", "" + mrp);
                            product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
                            a_product_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
                        }
                    }
                    //Log.e("AAAAAAAAAAAAAAAAAA", "" + bean_cart_data.get(position).getItem_total());
                    double t = Double.parseDouble(bean_cart_data.get(position).getItem_total());
                    String str = String.format("%.2f", t);
                    // txt_total.setText(str);
                    txt_total.setText(bean_cart_data.get(position).getItem_total());
                    edt_count1.setText(bean_cart_data.get(position).getQuantity());
                    edt_count1.setTag(bean_cart_data.get(position).getQuantity());

                    String[] option = bean_cart_data.get(position).getOption_name().toString().split(", ");
                    String[] value = bean_cart_data.get(position).getOption_value_name().split(", ");
                    String option_value = "";

                    Picasso.with(getApplicationContext()).load(Globals.server_link + "files/" + bean_cart_data.get(position).getProd_img()).placeholder(R.drawable.btl_watermark).into(imageView2);

                    //imageloader.DisplayImage(Globals.server_link + "files/" + bean_cart.get(position).getPro_Images(), imageView2);
                    //Log.e("Image :- ", Globals.server_link + "files/" + bean_cart_data.get(position).getProd_img());

                    if (bean_cart_data.get(position).getOption_name().equalsIgnoreCase("") || bean_cart_data.get(position).getOption_name().equalsIgnoreCase("null")) {
                        tv_option_value.setVisibility(View.GONE);
                    } else {


                        tv_option_value.setVisibility(View.VISIBLE);
                        for (int i = 0; i < option.length; i++) {
                            if (i == 0) {
                                //Log.e("" + option, "" + option[i]);
                                option_value = option[i] + ": " + value[i];
                            } else {
                                //  Log.e("" + option, "" + option[i]);
                                option_value = option_value + ", " + option[i] + ": " + value[i];
                            }
                        }


                        tv_option_value.setText(option_value);

                    }

                    edt_count1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            setRefershData();

                            if (user_data.size() != 0) {
                                for (int i = 0; i < user_data.size(); i++) {

                                    owner_id = user_data.get(i).getUser_id();

                                    role_id = user_data.get(i).getUser_type();

                                    if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                        app = new AppPrefs(BTL_Cart.this);
                                        role_id = app.getSubSalesId();
                                        u_id = app.getSalesPersonId();
                                    } else {
                                        u_id = owner_id;
                                    }


                                }

                            } else {
                                user_id_main = "";
                            }
                            // product_id = bean_cart_data.get(position).getProduct_id();
                            List<NameValuePair> para = new ArrayList<NameValuePair>();
                            para.add(new BasicNameValuePair("product_id", bean_cart_data.get(position).getProduct_id().toString()));
                            para.add(new BasicNameValuePair("owner_id", owner_id));
                            para.add(new BasicNameValuePair("user_id", u_id));
                            //Log.e("111111111",""+product_id);
                            // Log.e("222222222",""+owner_id);
                            //  Log.e("333333333",""+u_id);

                            Globals.generateNoteOnSD(getApplicationContext(), "1149 -> " + para.toString());

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
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                    Globals.CustomToast(BTL_Cart.this, "SERVER ERROR", getLayoutInflater());
                                    // loadingView.dismiss();

                                } else {
                                    JSONObject jObj = new JSONObject(json);

                                    String date = jObj.getString("status");

                                    if (date.equalsIgnoreCase("false")) {

                                        qun = "1";
                                        kkk = 0;
                                        //Log.e("131313131331","1");

                                        // loadingView.dismiss();
                                    } else {

                                        JSONObject jobj = new JSONObject(json);


                                        JSONArray jsonArray = jObj.getJSONArray("data");
                                        for (int iu = 0; iu < jsonArray.length(); iu++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(iu);
                                            qun = jsonObject.getString("quantity");

                                        }

                                        //Log.e("131313131331",""+qun);
                                        kkk = 1;


                                    }

                                }
                            } catch (Exception j) {
                                j.printStackTrace();
                                //Log.e("json exce",j.getMessage());
                            }

                            dialog = new Dialog(BTL_Cart.this);
                            Window window = dialog.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            dialog.setCanceledOnTouchOutside(false);
                            wlp.gravity = Gravity.BOTTOM;
                            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                            window.setAttributes(wlp);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.pop_lay);
                            minuss = (ImageView) dialog.findViewById(R.id.minus);
                            plus = (ImageView) dialog.findViewById(R.id.plus);
                            buy_cart = (Button) dialog.findViewById(R.id.Btn_Buy_online11);
                            cancel = (Button) dialog.findViewById(R.id.cancel);
                            edt_count = (EditText) dialog.findViewById(R.id.edt_count);
                            edt_count.setSelection(edt_count.getText().length());

                            final TextView txt_availableScheme = (TextView) dialog.findViewById(R.id.txt_availableScheme);


                            //  l_view_spinner=(LinearLayout)findViewById(R.id.l_view_spinner);
                            tv_pop_pname = (TextView) dialog.findViewById(R.id.product_name);
                            tv_pop_code = (TextView) dialog.findViewById(R.id.product_code);
                            tv_pop_packof = (TextView) dialog.findViewById(R.id.product_packof);
                            tv_pop_mrp = (TextView) dialog.findViewById(R.id.product_mrp);
                            tv_txt_mrp = (TextView) dialog.findViewById(R.id.txt_mrp_text);
                            tv_pop_sellingprice = (TextView) dialog.findViewById(R.id.product_sellingprice);
                            tv_total = (TextView) dialog.findViewById(R.id.txt_total);
                            //  l_spinner=(LinearLayout)dialog.findViewById(R.id.l_spinner);
                            // l_spinner_text=(LinearLayout)dialog.findViewById(R.id.l_spinnertext);
                            tv_pop_pname.setText(bean_cart_data.get(position).getName().toString());
                            tv_pop_code.setText(" ( " + bean_cart_data.get(position).getPro_code().toString() + " )");
                            tv_pop_packof.setText(bean_cart_data.get(position).getPack_of().toString());
                            tv_pop_mrp.setText(bean_cart_data.get(position).getMrp().toString());
                            tv_pop_sellingprice.setText(bean_cart_data.get(position).getSelling_price().toString());
                            tv_pop_sellingprice.setTag(bean_cart_data.get(position).getSelling_price().toString());
                            l_mrp = (LinearLayout) dialog.findViewById(R.id.l_mrp);
                            l_sell = (LinearLayout) dialog.findViewById(R.id.l_sell);
                            l_total = (LinearLayout) dialog.findViewById(R.id.l_total);
                            txt_selling_price = (TextView) dialog.findViewById(R.id.txt_selling_price);
                            txt_total_txt = (TextView) dialog.findViewById(R.id.txt_total_txt);
                            tv_total.setText(bean_cart_data.get(position).getSelling_price().toString());
                            //  l_view_spinner.setVisibility(View.GONE);
                            //  tv_total.setText(bean_product1.get(position).getPro_sellingprice().toString());
                            final int minPackOfQty = bean_cart_data.get(position).getMinPackOfQty();
                            if (kkk == 1) {
                                int currentQty = Integer.parseInt(qun);
                                int remainedQtyToFill = C.modOf(currentQty, minPackOfQty);
                                int k = minPackOfQty;
                                if (currentQty < minPackOfQty && remainedQtyToFill > 0)
                                    k = currentQty + remainedQtyToFill;
                                else if (currentQty > minPackOfQty && remainedQtyToFill > 0)
                                    k = currentQty - remainedQtyToFill + minPackOfQty;
                                else
                                    k = currentQty + minPackOfQty;

                                edt_count.setText(String.valueOf(k));
                            } else {
                                edt_count.setText(String.valueOf(minPackOfQty));
                            }
                            double temp_total = Double.parseDouble(tv_total.getText().toString());
                            double t = Double.parseDouble(tv_total.getText().toString()) * Double.parseDouble(edt_count.getText().toString());
                            //float t = Float.parseFloat(productpricce);
                            String str = String.format("%.2f", t);
                            tv_total.setText(str);
                            int temp_count = Integer.parseInt(edt_count.getText().toString());
//                        int temp_total_count = temp_count + Integer.parseInt(array_product_cart.get(0).getPro_qty().toString());
//                        Log.e("11121212121",""+temp_total_count);
//                        float temp_total = Float.parseFloat(tv_total.getText().toString());
//                        edt_count.setText(""+temp_total_count);
//                        tv_total.setText(""+(temp_total + Float.parseFloat(array_product_cart.get(0).getPro_total().toString().replace("", ""))));

                            double mrp = Double.parseDouble(tv_pop_mrp.getText().toString());
                            double sellingprice = Double.parseDouble(tv_pop_sellingprice.getText().toString());
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
                                //if (role_id.equalsIgnoreCase("3")) {
                                l_mrp.setVisibility(View.GONE);
                                tv_txt_mrp.setVisibility(View.GONE);
                                tv_pop_sellingprice.setVisibility(View.GONE);
                                l_sell.setVisibility(View.GONE);
                                l_total.setVisibility(View.GONE);
                                txt_selling_price.setVisibility(View.GONE);
                                txt_total_txt.setVisibility(View.GONE);
                            } else {
                                l_mrp.setVisibility(View.VISIBLE);
                                tv_txt_mrp.setVisibility(View.VISIBLE);
                                tv_pop_sellingprice.setVisibility(View.VISIBLE);
                                l_sell.setVisibility(View.VISIBLE);
                                l_total.setVisibility(View.VISIBLE);

                                if (mrp > sellingprice) {
                                    tv_pop_mrp.setVisibility(View.VISIBLE);
                                    tv_txt_mrp.setVisibility(View.VISIBLE);
                                    l_mrp.setVisibility(View.VISIBLE);
                                    tv_pop_mrp.setPaintFlags(tv_pop_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                                } else {
                                    tv_pop_mrp.setVisibility(View.GONE);
                                    tv_txt_mrp.setVisibility(View.GONE);
                                    l_mrp.setVisibility(View.GONE);
                                }
                            }
                       /* if(mrp > sellingprice){
                            tv_pop_mrp.setVisibility(View.VISIBLE);
                            tv_txt_mrp.setVisibility(View.VISIBLE);
                            tv_pop_mrp.setPaintFlags(tv_pop_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        }else{
                            tv_pop_mrp.setVisibility(View.GONE);
                            tv_txt_mrp.setVisibility(View.GONE);

                        }*/


                            dialog.show();

                            String c = edt_count.getText().toString();
                            //Log.e("float - ",""+edt_count.getText().toString());
                            s = Integer.parseInt(c);
                            minuss.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String c = edt_count.getText().toString();

                                    if (!c.isEmpty()) {
                                        s = Integer.parseInt(c);
                                        if (s > 0) {
                                            int minPackOfQty = bean_cart_data.get(position).getMinPackOfQty();
                                            if (s < minPackOfQty) {
                                                s = 0;
                                            } else {
                                                int toCutOutQty = s % minPackOfQty;

                                                Log.e("toCutOut Minus", "-->" + toCutOutQty);

                                                if (toCutOutQty > 0)
                                                    s = s - toCutOutQty;
                                                else
                                                    s = s - minPackOfQty;
                                            }
                                            edt_count.setText(String.valueOf(s));
                                            int counter = s;
                                            double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                                            double total = amt * counter;
                                            //float finalValue = (float)(Math.round( total * 100 ) / 100);

                                            String str = String.format("%.2f", total);
                                            // String str = String.valueOf(total);
                                            tv_total.setText(str);
                                        }
                                    }
                                    //Log.e("float - ",""+edt_count.getText().toString());
                                /*s = Integer.parseInt(c);
                                if (s == 0) {
                                    //GlobalVariable.CustomToast(Product_List.this, "Its impossible", getLayoutInflater());
                                    edt_count.setText("1");
                                    double t = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                                    String str = String.format("%.2f", t);
                                    tv_total.setText(str);

                                } else {

                                    s = s - 1;
                                    if (s == 0) {
                                        edt_count.setText("1");
                                        double t = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                                        String str = String.format("%.2f", t);
                                        tv_total.setText(str);
                                    } else {
                                        //   edt_count.setText(String.valueOf(s));
                                        edt_count.setText(String.valueOf(s));
                                        int counter = s;
                                        double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                                        double total = amt * counter;
                                        //float finalValue = (float)(Math.round( total * 100 ) / 100);

                                        String str = String.format("%.2f", total);
                                        // String str = String.valueOf(total);
                                        tv_total.setText(str);


                                    }


                                    // Toast.makeText(activity,"Total "+to,Toast.LENGTH_SHORT).show();
                                }*/
                                }
                            });
                            String c1 = edt_count.getText().toString();
                            //Log.e("float - ",""+edt_count.getText().toString());
                            // s=Integer.parseInt(c1);
                            s = Integer.parseInt(c1);

                            plus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String c1 = edt_count.getText().toString();
                                    c1 = c1.isEmpty() ? "0" : c1;
                                    //Log.e("float - ",""+edt_count.getText().toString());
                                    // s=Integer.parseInt(c1);
                                    s = Integer.parseInt(c1);
                                    int minPackOfQty = bean_cart_data.get(position).getMinPackOfQty();
                                    if (s == 0) {
                                        s = s + minPackOfQty;
                                    } else if (s > 0) {

                                        int remainder = C.modOf(minPackOfQty, s);

                                        if (s < minPackOfQty) {

                                            if (remainder > 0) {
                                                s = s + (minPackOfQty - s);
                                            } else {
                                                s = s + minPackOfQty;
                                            }
                                        } else if (s >= minPackOfQty) {
                                            if (remainder > 0) {
                                                s = s - remainder + minPackOfQty;
                                            } else {
                                                s = s + minPackOfQty;
                                            }
                                        }
                                    }
                                    edt_count.setText(String.valueOf(s));
                                    int counter = s;
                                    double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                                    double total = amt * counter;
                                    //   float finalValue = (float)(Math.round( total * 100 ) / 100);

                                    String str = String.format("%.2f", total);
                                    tv_total.setText(str);

                                    // Toast.makeText(getApplicationContext(),"Total "+to,Toast.LENGTH_SHORT).show();
                                }
                            });

                            edt_count.addTextChangedListener(new TextWatcher() {

                                public void onTextChanged(CharSequence s, int start,
                                                          int before, int count) {

                                }

                                public void beforeTextChanged(CharSequence s, int start,
                                                              int count, int after) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    // TODO Auto-generated method stub

                                    String enteredQty = edt_count.getText().toString().trim();
                                    int qty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);

                                    if (qty <= 0) {
                                        tv_total.setText("0.00");
                                        buy_cart.setEnabled(false);
                                    } else {
                                        buy_cart.setEnabled(true);

                                        int minPackOfQty = bean_cart_data.get(position).getMinPackOfQty();

                                        /*if (qty >= minPackOfQty && C.modOf(qty, minPackOfQty) == 0) {
                                            String productpricce = tv_pop_sellingprice.getTag().toString();
                                            amount1 = Double.parseDouble(s.toString());
                                            amount1 = Double.parseDouble(productpricce) * amount1;
                                            //float finalValue = (float)(Math.round( amount1 * 100 ) / 100);

                                            String str = String.format("%.2f", amount1);
                                            tv_total.setText(str);
                                        } else {
                                            tv_total.setText("");
                                        }*/

                                        String productpricce = tv_pop_sellingprice.getTag().toString();
                                        amount1 = Double.parseDouble(s.toString());
                                        amount1 = Double.parseDouble(productpricce) * amount1;
                                        //float finalValue = (float)(Math.round( amount1 * 100 ) / 100);

                                        String str = String.format("%.2f", amount1);
                                        tv_total.setText(str);



                                    }
                                }
                            });

                            buy_cart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (user_data.size() == 0) {

                                        Intent i = new Intent(BTL_Cart.this, LogInPage.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);


                                    } else {

                                        String enteredQty = edt_count.getText().toString().trim();
                                        int currentQty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);
                                        int minPackOfQty = bean_cart_data.get(position).getMinPackOfQty();

                                        if (currentQty<minPackOfQty || C.modOf(currentQty, minPackOfQty) > 0) {

                                            C.showMinPackAlert(BTL_Cart.this, minPackOfQty);
                                            //Globals.Toast(getApplicationContext(), "Please enter quantity in multiple of "+minPackOfQty+" or tap + / - button");
                                            Log.e("Mod Rem", "-->" + C.modOf(currentQty, minPackOfQty));
                                        } else {
                                            product_id = bean_cart_data.get(position).getProduct_id();

                                            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                                            params1.add(new BasicNameValuePair("product_id", product_id));
                                            params1.add(new BasicNameValuePair("user_id", user_id_main));
                                            params1.add(new BasicNameValuePair("role_id", role_id));

                                            isNotDone = true;
                                            new GetProductSelling(params1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                            while (isNotDone) {

                                            }
                                            isNotDone = true;

                                            String json1 = jsonData; //GetProductSelling(params1);
                                            Log.e("JSON", jsonData);
                                            try {


                                                //System.out.println(json1);

                                                if (json1 == null
                                                        || (json1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                                    Globals.CustomToast(BTL_Cart.this, "SERVER ERRER", getLayoutInflater());
                                                    // loadingView.dismiss();

                                                } else {
                                                    JSONObject jObj = new JSONObject(json1);

                                                    String date = jObj.getString("status");

                                                    if (date.equalsIgnoreCase("false")) {
                                                        String Message = jObj.getString("message");
                                                        bean_product_schme.clear();
                                                        bean_schme.clear();
                                                        //Log.e("11111111",""+product_id);

                                                    } else {

                                                        JSONObject jobj = new JSONObject(json1);
                                                        //Log.e("22222222222",""+product_id);


                                                        JSONObject jObjj = jObj.getJSONObject("data");
                                                        JSONObject jproduct = jObjj.getJSONObject("Product");

                                                        Selling = jproduct.getString("selling_price").toString();
                                                        //Log.e("Selling","1223123"+Selling);


                                                    }

                                                }
                                            } catch (Exception j) {
                                                j.printStackTrace();
                                                //Log.e("json exce",j.getMessage());
                                            }


                                            String qty = edt_count.getText().toString();

                                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                                            params.add(new BasicNameValuePair("product_id", product_id));
                                            params.add(new BasicNameValuePair("user_id", user_id_main));
                                            params.add(new BasicNameValuePair("product_buy_qty", qty));
                                            //Log.e("111111111",""+product_id);
                                            //  Log.e("222222222",""+user_id_main);
                                            //  Log.e("333333333",""+qty);

                                            isNotDone = true;
                                            new GetProductDetailByCode(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                            while (isNotDone) {

                                            }
                                            isNotDone = true;

                                            String json = jsonData; //GetProductDetailByCode(params);

                                            Globals.generateNoteOnSD(getApplicationContext(),json);

                                            //new check_schme().execute();
                                            try {


                                                //System.out.println(json);

                                                if (json == null
                                                        || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                                    Globals.CustomToast(BTL_Cart.this, "SERVER ERRER", getLayoutInflater());
                                                    // loadingView.dismiss();

                                                } else {
                                                    JSONObject jObj = new JSONObject(json);

                                                    String date = jObj.getString("status");

                                                    if (date.equalsIgnoreCase("false")) {
                                                        String Message = jObj.getString("message");
                                                        bean_product_schme.clear();
                                                        bean_schme.clear();
                                                        //Log.e("11111111",""+product_id);
                                                        //   Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                                                        // loadingView.dismiss();
                                                    } else {

                                                        JSONObject jobj = new JSONObject(json);
                                                        //Log.e("22222222222",""+product_id);
                          /*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*/

                                                        bean_product_schme.clear();
                                                        bean_schme.clear();

                                                        JSONObject jsonArray = jObj.getJSONObject("data");
                                                        //Log.e("3333333333",""+product_id);
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
                                                        bean.setPro_sellingprice(jproduct.getString("selling_price"));

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


                                                        //loadingView.dismiss();

                                                    }

                                                }
                                            } catch (Exception j) {
                                                j.printStackTrace();
                                                //Log.e("json exce",j.getMessage());
                                            }
                                            //Log.e("45454545",""+bean_product_schme.size());
                                            if (bean_product_schme.size() == 0) {
                                                //Log.e("555555555",""+bean_product_schme.size());
                                                Bean_ProductCart bean = new Bean_ProductCart();
                                      /*  String value_id = "";
                                        String value_name = "";*/


                                                bean.setPro_id(bean_cart_data.get(position).getProduct_id());
                                                //Log.e("-- ", "producttt Id " + bean_cart_data.get(position).getProduct_id());

                                                bean.setPro_code(tv_pop_code.getText().toString().trim());
                                                //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                                bean.setPro_name(tv_pop_pname.getText().toString());
                                                bean.setPro_qty(edt_count.getText().toString());
                                                bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                                bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString());
                                                bean.setPro_shortdesc(bean_cart_data.get(position).getPack_of());


                                                bean.setPro_total(tv_total.getText().toString());
                                                bean.setPro_schme("");

                                                for (int i = 0; i < 1; i++) {
                                                    try {
                                                        JSONObject jobject = new JSONObject();

                                                        jobject.put("user_id", u_id);
                                                        jobject.put("role_id", role_id);
                                                        jobject.put("owner_id", owner_id);
                                                        jobject.put("product_id", bean_cart_data.get(position).getProduct_id());
                                                        jobject.put("category_id", bean_cart_data.get(position).getCategory_id().toString());
                                                        jobject.put("name", tv_pop_pname.getText().toString());
                                                        String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                        String aString = newString.toString().trim().replace(")", "");
                                                        jobject.put("pro_code", aString.toString().trim());
                                                        jobject.put("quantity", edt_count.getText().toString());
                                                        jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                        jobject.put("selling_price", Selling);
                                                        jobject.put("option_id", bean_cart_data.get(position).getOption_id().toString());
                                                        jobject.put("option_name", bean_cart_data.get(position).getOption_name().toString());
                                                        jobject.put("option_value_id", bean_cart_data.get(position).getOption_value_id().toString());
                                                        jobject.put("option_value_name", bean_cart_data.get(position).getOption_value_name().toString());

                                                        double am = Double.parseDouble(Selling) * Double.parseDouble(edt_count.getText().toString());
                                                        String str = String.format("%.2f", am);

                                                        jobject.put("item_total", str);
                                                        jobject.put("pro_scheme", " ");
                                                        jobject.put("pack_of", bean_cart_data.get(position).getPack_of().toString());
                                                        jobject.put("scheme_id", " ");
                                                        jobject.put("scheme_title", " ");
                                                        jobject.put("scheme_pack_id", " ");
                                                        jobject.put("prod_img", bean_cart_data.get(position).getProd_img().toString());


                                                        jarray_cart.put(jobject);
                                                    } catch (JSONException e) {

                                                    }


                                                }

                                                if (kkk == 1) {

                                                    Log.e("Line", "1821");
                                                    new Edit_Product().execute();

                                                } else {

                                                    new Add_Product().execute();
                                                }


                                            } else {
                                                //Log.e("Type ID : ",""+bean_schme.get(0).getType_id().toString());
                                                if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("1")) {

                                                    //Log.e("66666666666", "" + bean_product_schme.size());
                                                    Bean_ProductCart bean = new Bean_ProductCart();
                                                    String value_id = "";
                                                    String value_name = "";


                                                    bean.setPro_id(bean_cart_data.get(position).getProduct_id());
                                                    //Log.e("-- ", "producttt Id " + bean_cart_data.get(position).getProduct_id());
                                                    // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                                    bean.setPro_cat_id(bean_cart_data.get(position).getCategory_id().toString());

                                                    bean.setPro_code(tv_pop_code.getText().toString().trim());
                                                    //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                                    bean.setPro_name(tv_pop_pname.getText().toString());

                                                    String getq = bean_schme.get(0).getGet_qty();
                                                    String buyq = bean_schme.get(0).getBuy_qty();
                                                    String maxq = bean_schme.get(0).getMax_qty();

                                                    double getqu = Double.parseDouble(getq);
                                                    double buyqu = Double.parseDouble(buyq);
                                                    //double maxqu = Double.parseDouble(maxq);
                                                    int qu = Integer.parseInt(edt_count.getText().toString());

                                           /* float a = qu / buyqu;
                                            float b = a * getqu;
                                            if (b > maxqu) {
                                                b = maxqu;
                                            } else {
                                                b = b;
                                            }


                                            bean_s.setPro_qty(String.valueOf((int) b));
                                            */
                                                    String sell = Selling;

                                                    double se = Double.parseDouble(Selling) * buyqu;

                                                    double se1 = buyqu + getqu;

                                                    double fse = se / se1;
                                                    sell = String.format("%.2f", fse);
                                                    // sell = String.valueOf(fse);

                                                    int fqu = qu + (int) getqu;

                                                    bean.setPro_qty(String.valueOf(fqu));
                                                    bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                                    bean.setPro_sellingprice(sell);
                                                    bean.setPro_shortdesc(bean_cart_data.get(position).getPack_of());
                                           /* bean.setPro_Option_id(option_id);
                                            Log.e("-- ", "Option Id " + option_id);

                                            bean.setPro_Option_name(option_name);
                                            Log.e("-- ", "Option Name " + option_name);

                                            bean.setPro_Option_value_id(value_id);

                                            Log.e("", "Value Name " + value_id);
                                            bean.setPro_Option_value_name(value_name);
                                            Log.e("", "Value Name " + value_name);*/

                                                    bean.setPro_total(tv_total.getText().toString());
                                                    bean.setPro_schme("");
                                                    bean.setPro_schme_name(bean_schme.get(0).getScheme_name());
                                                    // db.Add_Product_cart(bean);


                                                    // db.Add_Product_cart_scheme(bean_s);

                                                    // array_value.clear();

                                                    for (int i = 0; i < 1; i++) {
                                                        try {
                                                            JSONObject jobject = new JSONObject();

                                                            jobject.put("user_id", u_id);
                                                            jobject.put("role_id", role_id);
                                                            jobject.put("owner_id", owner_id);
                                                            jobject.put("product_id", bean_cart_data.get(position).getProduct_id());
                                                            jobject.put("category_id", bean_cart_data.get(position).getCategory_id().toString());
                                                            jobject.put("name", tv_pop_pname.getText().toString());
                                                            String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                            String aString = newString.toString().trim().replace(")", "");
                                                            jobject.put("pro_code", aString.toString().trim());
                                                            jobject.put("quantity", String.valueOf(fqu));
                                                            jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                            jobject.put("selling_price", sell);
                                                            jobject.put("option_id", bean_cart_data.get(position).getOption_id().toString());
                                                            jobject.put("option_name", bean_cart_data.get(position).getOption_name().toString());
                                                            jobject.put("option_value_id", bean_cart_data.get(position).getOption_value_id().toString());
                                                            jobject.put("option_value_name", bean_cart_data.get(position).getOption_value_name().toString());
                                                            double f = fqu * Double.parseDouble(sell);
                                                            String str = String.format("%.2f", f);
                                                            jobject.put("item_total", str);
                                                            jobject.put("pro_scheme", " ");
                                                            jobject.put("pack_of", bean_cart_data.get(position).getPack_of().toString());
                                                            jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                            jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                            jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                            jobject.put("prod_img", bean_cart_data.get(position).getProd_img().toString());


                                                            jarray_cart.put(jobject);
                                                        } catch (JSONException e) {

                                                        }


                                                    }

                                                    if (kkk == 1) {

                                                        Log.e("Line", "1948");
                                                        new Edit_Product().execute();

                                                    } else {

                                                        new Add_Product().execute();
                                                    }


                                                } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {

                                                    //Log.e("66666666666", "" + bean_product_schme.size());
                                                    Bean_ProductCart bean = new Bean_ProductCart();
                                                    String value_id = "";
                                                    String value_name = "";


                                          /*  for (int i = 0; i < array_value.size(); i++) {
                                                if (i == 0) {
                                                    value_name = array_value.get(i).getValue_name();
                                                    value_id = array_value.get(i).getValue_id();
                                                } else {
                                                    value_id = value_id + ", " + array_value.get(i).getValue_id();
                                                    value_name = value_name + ", " + array_value.get(i).getValue_name();

                                                }
                                            }
*/

                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                                    bean.setPro_id(bean_cart_data.get(position).getProduct_id());
                                                    //Log.e("-- ", "producttt Id " + bean_cart_data.get(position).getProduct_id());
                                                    // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                                    bean.setPro_cat_id(bean_cart_data.get(position).getCategory_id().toString());
                                         /*   Log.e("Cat_ID....", "" + Catid);
                                            if (list_of_images.size() == 0) {
                                                bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                            } else {
                                                bean.setPro_Images(list_of_images.get(0).toString());
                                            }*/
                                                    // bean.setPro_Images(list_of_images.get(position));
                                                    //bean.setPro_code(result_holder.tvproduct_code.getText().toString());
                                                    bean.setPro_code(tv_pop_code.getText().toString().trim());
                                                    //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                                    bean.setPro_name(tv_pop_pname.getText().toString());
                                                    bean.setPro_qty(edt_count.getText().toString());
                                                    bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                                    bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString());
                                          /*  bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                            bean.setPro_Option_id(option_id);
                                            Log.e("-- ", "Option Id " + option_id);

                                            bean.setPro_Option_name(option_name);
                                            Log.e("-- ", "Option Name " + option_name);
*/
                                                    bean.setPro_Option_value_id(value_id);

                                                    //Log.e("", "Value Name " + value_id);
                                                    bean.setPro_Option_value_name(value_name);
                                                    //Log.e("", "Value Name " + value_name);

                                                    bean.setPro_total(tv_total.getText().toString());
                                                    bean.setPro_schme("");
                                                    bean.setPro_schme_name("");
                                                    //db.Add_Product_cart(bean);

                                            /*for (int i = 0; i < 1; i++) {*/
                                                    try {
                                                        JSONObject jobject = new JSONObject();

                                                        jobject.put("user_id", u_id);
                                                        jobject.put("role_id", role_id);
                                                        jobject.put("owner_id", owner_id);
                                                        jobject.put("product_id", bean_cart_data.get(position).getProduct_id());
                                                        jobject.put("category_id", bean_cart_data.get(position).getCategory_id());
                                                        jobject.put("name", tv_pop_pname.getText().toString());
                                                        String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                        String aString = newString.trim().replace(")", "");
                                                        jobject.put("pro_code", aString.trim());
                                                        jobject.put("quantity", edt_count.getText().toString());
                                                        jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                        jobject.put("selling_price", tv_pop_sellingprice.getText().toString());
                                                        jobject.put("option_id", bean_cart_data.get(position).getOption_id());
                                                        jobject.put("option_name", bean_cart_data.get(position).getOption_name());
                                                        jobject.put("option_value_id", bean_cart_data.get(position).getOption_value_id());
                                                        jobject.put("option_value_name", bean_cart_data.get(position).getOption_value_name());
                                                        jobject.put("item_total", tv_total.getText().toString());
                                                        jobject.put("pro_scheme", " ");
                                                        jobject.put("pack_of", bean_cart_data.get(position).getPack_of().toString());
                                                        jobject.put("scheme_id", " ");
                                                        jobject.put("scheme_title", " ");
                                                        jobject.put("scheme_pack_id", " ");
                                                        jobject.put("prod_img", bean_cart_data.get(position).getProd_img().toString());

                                                        jarray_cart.put(jobject);
                                                    } catch (JSONException e) {

                                                    }


                                                    //}
                                                    //  array_value.clear();
                                           /* if(kkk == 1) {

                                                new Edit_Product().execute();

                                            }else{

                                                new Add_Product().execute();
                                            }*/

                                                    Bean_ProductCart bean_s = new Bean_ProductCart();
                                                    String value_id1 = "";
                                                    String value_name1 = "";


                                          /*  for (int i = 0; i < array_value.size(); i++) {
                                                if (i == 0) {
                                                    value_name1 = array_value.get(i).getValue_name();
                                                    value_id1 = array_value.get(i).getValue_id();
                                                } else {
                                                    value_id1 = value_id1 + ", " + array_value.get(i).getValue_id();
                                                    value_name1 = value_name1 + ", " + array_value.get(i).getValue_name();

                                                }
                                            }

*/
                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                                    bean_s.setPro_id(bean_product_schme.get(0).getPro_id());
                                                    //Log.e("-- ", "producttt Id " + bean_cart_data.get(position).getProduct_id());
                                                    // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                                    bean_s.setPro_cat_id(bean_product_schme.get(0).getPro_cat_id());
                                                    //Log.e("Cat_ID....", "" + bean_cart_data.get(position).getCategory_id().toString());
                                        /*if (list_of_images.size() == 0) {
                                            bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                        } else {
                                            bean.setPro_Images(list_of_images.get(0).toString());
                                        }*/
                                                    bean_s.setPro_Images(bean_product_schme.get(0).getPro_image());
                                                    // bean.setPro_Images(list_of_images.get(position));
                                                    //bean.setPro_code(result_holder.tvproduct_code.getText().toString());
                                                    bean_s.setPro_code(bean_product_schme.get(0).getPro_code());
                                                    //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                                    bean_s.setPro_name(bean_product_schme.get(0).getPro_name());

                                                    String getq = bean_schme.get(0).getGet_qty();
                                                    String buyq = bean_schme.get(0).getBuy_qty();
                                                    String maxq = bean_schme.get(0).getMax_qty();

                                                    double getqu = Double.parseDouble(getq);
                                                    double buyqu = Double.parseDouble(buyq);
                                                    //double maxqu = Double.parseDouble(maxq);
                                                    int qu = Integer.parseInt(edt_count.getText().toString());

                                                    int a = (int) (qu / buyqu);
                                                    int b = (int) (a * getqu);

                                                    Log.e("Qtys", a + " --- " + b);

                                                   /* if (b > maxqu) {
                                                        b = maxqu;
                                                    } else {
                                                        b = b;
                                                    }*/


                                                    bean_s.setPro_qty(String.valueOf(b));


                                                    bean_s.setPro_mrp("0");
                                                    bean_s.setPro_sellingprice("0");
                                                    bean_s.setPro_shortdesc(bean_product_schme.get(0).getPro_label());
                                         /*   bean_s.setPro_Option_id(option_id);
                                            Log.e("-- ", "Option Id " + option_id);

                                            bean_s.setPro_Option_name(option_name);
                                            Log.e("-- ", "Option Name " + option_name);

                                            bean_s.setPro_Option_value_id(value_id);

                                            Log.e("", "Value Name " + value_id);
                                            bean_s.setPro_Option_value_name(value_name);
                                            Log.e("", "Value Name " + value_name);*/
                                                    bean.setPro_schme_name(bean_schme.get(0).getScheme_name());
                                                    bean_s.setPro_total("0");
                                                    bean_s.setPro_schme("" + bean_cart_data.get(position).getProduct_id());
                                           /* if (String.valueOf((int) b).toString().equalsIgnoreCase("0")) {
                                                db.delete_product_from_cart_list_schme(bean_cart_data.get(position).getProduct_id());
                                            } else {

                                                db.update_product_from_Schme(bean_cart_data.get(position).getProduct_id(), String.valueOf(b));
                                            }*/

                                                    try {
                                                        JSONObject jobject = new JSONObject();

                                                        jobject.put("user_id", u_id);
                                                        jobject.put("role_id", role_id);
                                                        jobject.put("owner_id", owner_id);
                                                        jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                                        jobject.put("category_id", bean_product_schme.get(0).getPro_cat_id());
                                                        jobject.put("name", bean_product_schme.get(0).getPro_name());
                                                        jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                                        jobject.put("quantity", String.valueOf(b));
                                                        jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                                        jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                                        jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id().toString());
                                                        jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name().toString());
                                                        jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id().toString());
                                                        jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name().toString());
                                                        jobject.put("item_total", "0");
                                                        jobject.put("pro_scheme", "" + bean_cart_data.get(position).getProduct_id());
                                                        jobject.put("pack_of", bean_product_schme.get(0).getPro_label());
                                                        jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                        jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                        jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                        jobject.put("prod_img", bean_product_schme.get(0).getPro_image());


                                                        jarray_cart.put(jobject);
                                                    } catch (JSONException e) {

                                                    }


                                                    // db.Add_Product_cart_scheme(bean_s);


                                                    if (kkk == 1) {

                                                        Log.e("Line", "2182");
                                                        new Edit_Product().execute();

                                                    } else {

                                                        new Add_Product().execute();
                                                    }

                                                } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("3")) {

                                                    //Log.e("66666666666", "" + bean_product_schme.size());
                                                    Bean_ProductCart bean = new Bean_ProductCart();
                                                    String value_id = "";
                                                    String value_name = "";


                                         /*   for (int i = 0; i < array_value.size(); i++) {
                                                if (i == 0) {
                                                    value_name = array_value.get(i).getValue_name();
                                                    value_id = array_value.get(i).getValue_id();
                                                } else {
                                                    value_id = value_id + ", " + array_value.get(i).getValue_id();
                                                    value_name = value_name + ", " + array_value.get(i).getValue_name();

                                                }
                                            }

*/
                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                                    bean.setPro_id(bean_cart_data.get(position).getProduct_id());
                                                    //  Log.e("-- ", "producttt Id " + bean_cart_data.get(position).getProduct_id());
                                                    // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                          /*  bean.setPro_cat_id(Catid);
                                            Log.e("Cat_ID....", "" + Catid);
                                            if (list_of_images.size() == 0) {
                                                bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                            } else {
                                                bean.setPro_Images(list_of_images.get(0).toString());
                                            }*/
                                                    // bean.setPro_Images(list_of_images.get(position));
                                                    //bean.setPro_code(result_holder.tvproduct_code.getText().toString());
                                                    bean.setPro_code(tv_pop_code.getText().toString().trim());
                                                    //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                                    bean.setPro_name(tv_pop_pname.getText().toString());

                                                    String getq = bean_schme.get(0).getGet_qty();
                                                    String buyq = bean_schme.get(0).getBuy_qty();
                                                    String maxq = bean_schme.get(0).getMax_qty();

                                                    double getqu = Double.parseDouble(getq);
                                                    double buyqu = Double.parseDouble(buyq);
                                                    double maxqu = Double.parseDouble(maxq);
                                                    int qu = Integer.parseInt(edt_count.getText().toString());

                                           /* float a = qu / buyqu;
                                            float b = a * getqu;
                                            if (b > maxqu) {
                                                b = maxqu;
                                            } else {
                                                b = b;
                                            }


                                            bean_s.setPro_qty(String.valueOf((int) b));
                                            */
                                                    String sell = Selling;

                                                    String disc = bean_schme.get(0).getDisc_per().toString();

                                                    double se = Double.parseDouble(Selling) * Double.parseDouble(bean_schme.get(0).getDisc_per().toString());

                                                    double se1 = se / 100;

                                                    double fse = Double.parseDouble(Selling) - se1;

                                                    // sell = String.valueOf(fse);
                                                    sell = String.format("%.2f", fse);
                                                    //  int fqu = qu +Math.round(getqu);

                                                    bean.setPro_qty(edt_count.getText().toString());
                                                    bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                                    bean.setPro_sellingprice(sell);
                                         /*   bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                            bean.setPro_Option_id(option_id);
                                            Log.e("-- ", "Option Id " + option_id);

                                            bean.setPro_Option_name(option_name);
                                            Log.e("-- ", "Option Name " + option_name);

                                            bean.setPro_Option_value_id(value_id);

                                            Log.e("", "Value Name " + value_id);
                                            bean.setPro_Option_value_name(value_name);
                                            Log.e("", "Value Name " + value_name);*/

                                                    bean.setPro_total(tv_total.getText().toString());
                                                    bean.setPro_schme("");
                                                    bean.setPro_schme_name(bean_schme.get(0).getScheme_name());
                                                    // db.Add_Product_cart(bean);


                                                    // db.Add_Product_cart_scheme(bean_s);

                                                    for (int i = 0; i < 1; i++) {
                                                        try {
                                                            JSONObject jobject = new JSONObject();

                                                            jobject.put("user_id", u_id);
                                                            jobject.put("role_id", role_id);
                                                            jobject.put("owner_id", owner_id);
                                                            jobject.put("product_id", bean_cart_data.get(position).getProduct_id());
                                                            jobject.put("category_id", bean_cart_data.get(position).getCategory_id());
                                                            jobject.put("name", tv_pop_pname.getText().toString());
                                                            String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                            String aString = newString.toString().trim().replace(")", "");
                                                            jobject.put("pro_code", aString.toString().trim());
                                                            jobject.put("quantity", edt_count.getText().toString());
                                                            jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                            jobject.put("selling_price", sell);
                                                            jobject.put("option_id", bean_cart_data.get(position).getOption_id().toString());
                                                            jobject.put("option_name", bean_cart_data.get(position).getOption_name().toString());
                                                            jobject.put("option_value_id", bean_cart_data.get(position).getOption_value_id().toString());
                                                            jobject.put("option_value_name", bean_cart_data.get(position).getOption_value_name().toString());
                                                            double f = Double.parseDouble(edt_count.getText().toString()) * Double.parseDouble(sell);
                                                            String str = String.format("%.2f", f);
                                                            jobject.put("item_total", str);
                                                            jobject.put("pro_scheme", " ");
                                                            jobject.put("pack_of", bean_cart_data.get(position).getPack_of().toString());
                                                            jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                            jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                            jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                            jobject.put("prod_img", bean_cart_data.get(position).getProd_img().toString().toString());

                                                            jarray_cart.put(jobject);
                                                        } catch (JSONException e) {

                                                        }


                                                    }

                                                    if (kkk == 1) {

                                                        Log.e("Line", "2328");
                                                        new Edit_Product().execute();

                                                    } else {

                                                        new Add_Product().execute();
                                                    }
                                                }


                                       /* Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                                        Intent i = new Intent(Product_List.this, Product_List.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);*/
                                            }


                                            dialog.dismiss();
                                        }
                                    }
                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener()

                            {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }

                            });


                        }
                    });


                    img_shooping.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (user_data.size() != 0) {
                                for (int i = 0; i < user_data.size(); i++) {

                                    owner_id = user_data.get(i).getUser_id().toString();

                                    role_id = user_data.get(i).getUser_type().toString();

                                    if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                        app = new AppPrefs(BTL_Cart.this);
                                        role_id = app.getSubSalesId().toString();
                                        u_id = app.getSalesPersonId().toString();
                                    } else {
                                        u_id = owner_id;
                                    }


                                }

                            } else {
                                u_id = "";
                            }
                            pt_id = bean_cart_data.get(position).getProduct_id();
                            sche_id = bean_cart_data.get(position).getId();
                            //Log.e("21112111",""+sche_id);
                            new delete_product().execute();
                        }
                    });

                }
            }

            return convertView;
        }
    }

    private class GetProductSelling extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetProductSelling(List<NameValuePair> pairList) {
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

    private class GetProductDetailByCode extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetProductDetailByCode(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {
            jsonData = "";
            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "Scheme/App_Get_Scheme_Details", ServiceHandler.POST, pairList);
            isNotDone = false;

            Globals.generateNoteOnSD(getApplicationContext(),jsonData);

            return jsonData;
        }
    }

    public class get_cartdata extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            bean_cart_data.clear();
            try {
                loadingView = new Custom_ProgressDialog(
                        BTL_Cart.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                parameters.add(new BasicNameValuePair("user_id", u_id));
                parameters.add(new BasicNameValuePair("role_id", role_id));
                parameters.add(new BasicNameValuePair("owner_id", owner_id));

                //Log.e("user_id_main", "" + u_id);
                //  Log.e("owner_id", "" + owner_id);

                // Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartData", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(BTL_Cart.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    quotationID = jObj.getString("quotation_id");


                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTL_Cart.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        btn_deleteAll.setVisibility(View.GONE);
                        setdata();

                        if (quotationID.isEmpty() || (appPrefs.getUserRoleId().equals(C.COMP_SALES_PERSON) && role_id.equals(C.DISTRIBUTOR))) {
                            my_quotation.setVisibility(View.GONE);
                        }

                    } else {


                        JSONObject jobj = new JSONObject(result_1);
                        btn_deleteAll.setVisibility(View.VISIBLE);

                        JSONArray jsonArray = jObj.optJSONArray("data");
                        JSONArray comboArray = jObj.optJSONArray("combo_cart");

                        String minOrderValueGiven = jObj.getString("minimum_order_value");

                        minOrderValue = Integer.parseInt(minOrderValueGiven.equals("null") ? "0" : minOrderValueGiven);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Bean_Cart_Data bean = new Bean_Cart_Data();
                            bean.setId(jsonObject.getString("id"));
                            //Log.e("pro_IDDD", "" + jsonObject.getString("id"));
                            bean.setUser_id(jsonObject.getString("user_id"));
                            bean.setRole_id(jsonObject.getString("role_id"));
                            bean.setOwner_id(jsonObject.getString("owner_id"));
                            bean.setProduct_id(jsonObject.getString("product_id"));
                            bean.setCategory_id(jsonObject.getString("category_id"));
                            bean.setName(jsonObject.getString("name"));
                            bean.setPro_code(jsonObject.getString("pro_code"));
                            //Log.e("pro_code", "" + jsonObject.getString("pro_code"));
                            bean.setQuantity(jsonObject.getString("quantity"));
                            bean.setMrp(jsonObject.getString("mrp"));
                            bean.setSelling_price(jsonObject.getString("selling_price"));
                            bean.setOption_id(jsonObject.getString("option_id"));
                            bean.setOption_name(jsonObject.getString("option_name"));
                            bean.setOption_value_id(jsonObject.getString("option_value_id"));
                            bean.setOption_value_name(jsonObject.getString("option_value_name"));
                            bean.setItem_total(jsonObject.getString("item_total"));
                            bean.setPro_scheme(jsonObject.getString("pro_scheme"));
                            bean.setPack_of(jsonObject.getString("pack_of"));
                            bean.setScheme_id(jsonObject.getString("scheme_id"));
                            bean.setScheme_title(jsonObject.getString("scheme_title"));
                            //Log.e("SCHEME_TITLE",""+jsonObject.getString("scheme_title"));
                            // bean.setScheme_title(jsonObject.getString("scheme_title"));
                            bean.setScheme_pack_id(jsonObject.getString("scheme_pack_id"));
                            bean.setProd_img(jsonObject.getString("prod_img"));
                            String minPackOfQty = jsonObject.getString("pack_of_qty");
                            bean.setMinPackOfQty(Integer.parseInt(minPackOfQty.isEmpty() ? "1" : minPackOfQty));

                            bean_cart_data.add(bean);

                        }

                        if(quotationID.isEmpty() && jsonArray.length()==0){
                            my_quotation.setVisibility(View.GONE);
                        }
                        else {

                            if (!(appPrefs.getUserRoleId().equals(C.COMP_SALES_PERSON) && role_id.equals(C.DISTRIBUTOR))) {
                                my_quotation.setVisibility(View.VISIBLE);
                            }
                        }

                        Gson gson = new Gson();

                        for (int i = 0; i < comboArray.length(); i++) {
                            JSONObject main = comboArray.getJSONObject(i);
                            Bean_Cart_Data cartData = new Bean_Cart_Data();
                            cartData.setComboPack(true);

                            ComboOfferItem comboPack = gson.fromJson(main.optJSONObject("ComboPack").toString(), ComboOfferItem.class);

                            cartData.setComboData(comboPack);

                            ComboCart comboCart = gson.fromJson(main.getJSONObject("ComboCart").toString(), ComboCart.class);

                            cartData.setComboCart(comboCart);

                            bean_cart_data.add(cartData);

                        }
                        //Log.e("bean_cart_data.size", "" + bean_cart_data.size());


                        loadingView.dismiss();
                        setdata();
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
                Log.e("Exception", "-->" + j.getMessage());
            }



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

            Globals.generateNoteOnSD(getApplicationContext(), "->" + jsonData);

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
                        BTL_Cart.this, "");

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


                //Log.e("Cart_Array",""+jarray_cart);
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
            jarray_cart=new JSONArray();
            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(BTL_Cart.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTL_Cart.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTL_Cart.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                        Intent i = new Intent(BTL_Cart.this, BTL_Cart.class);
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
                        BTL_Cart.this, "");

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

                Log.e("Cart_Array", "" + jarray_cart);

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
            jarray_cart=new JSONArray();
            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(BTL_Cart.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTL_Cart.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTL_Cart.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                        Intent i = new Intent(BTL_Cart.this, BTL_Cart.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class delete_product extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        BTL_Cart.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("product_id", "" + pt_id));
                parameters.add(new BasicNameValuePair("owner_id", "" + owner_id));
                parameters.add(new BasicNameValuePair("user_id", "" + u_id));
                parameters.add(new BasicNameValuePair("scheme_pack_id", "" + sche_id));
                //Log.e("21112111",""+sche_id);
                //Log.e("111111",""+pt_id);
                //Log.e("4444444444",""+parameters);

                Log.e("PARAMS DEL", parameters.toString());


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_DeleteCartData", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(BTL_Cart.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTL_Cart.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTL_Cart.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                        bean_cart_data.clear();
                        Intent i = new Intent(BTL_Cart.this, BTL_Cart.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    private class GetNewQuotationID extends AsyncTask<Void,Void, String> {

        Custom_ProgressDialog dialog;
        String owner_id="", user_id_main="", roleId="";

        public GetNewQuotationID(String owner_id, String user_id_main, String roleId) {

            dialog=new Custom_ProgressDialog(BTL_Cart.this,"Please Wait...");
            dialog.setCancelable(false);
            dialog.show();

            this.owner_id = owner_id;
            this.user_id_main = user_id_main;
            this.roleId = roleId;
        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairs=new ArrayList<>();
            pairs.add(new BasicNameValuePair("user_id",user_id_main));
            pairs.add(new BasicNameValuePair("owner_id",owner_id));
            pairs.add(new BasicNameValuePair("role_id",roleId));

            String json=new ServiceHandler().makeServiceCall(Globals.server_link+Globals.CREATE_QUOTATION,ServiceHandler.POST,pairs);

            Log.e("Params",pairs.toString());

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();

            Log.e("JSON","->"+s);

            if(s==null || s.isEmpty()){
                Globals.defaultError(getApplicationContext());
            }
            else {
                try {
                    JSONObject object=new JSONObject(s);

                    if(object.getBoolean("status")){
                        quotationID=object.getString("quotation_id");
                        Intent intent=new Intent(getApplicationContext(),My_Quotation.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("quotationID",quotationID);
                        startActivityForResult(intent,REQUEST_QUOTATION);
                    }
                    else {
                        Globals.Toast2(getApplicationContext(),object.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Exception",e.getMessage());
                }
            }
        }
    }
}
