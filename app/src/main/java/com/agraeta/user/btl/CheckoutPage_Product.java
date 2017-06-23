package com.agraeta.user.btl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.agraeta.user.btl.CompanySalesPerson.GPSTracker;
import com.agraeta.user.btl.CompanySalesPerson.ImagePath;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.combooffer.ComboCart;
import com.agraeta.user.btl.model.combooffer.ComboOfferDetail;
import com.agraeta.user.btl.model.combooffer.ComboOfferItem;
import com.google.android.gms.vision.text.Line;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutPage_Product extends AppCompatActivity {
    private static final int EDIT_COMBO = 12;
    String sales_number;
    Dialog dialog, dialog1;
    String store_ref, phone_1;
    EditText edt_mobile;
    TextView txt_otp;
    Button btn_submit, btn_otp;
    ImageView btn_cancel1;
    EditText edt_otp;
    String user_ID1;
    String phone, otp;
    String smobile = new String();
    ArrayList<Bean_BankDetails> bankDetailsList = new ArrayList<Bean_BankDetails>();
    String jsonPerson = "";
    EditText edt_couponcode;
    Button btn_pay1, btn_discount;
    TextView tv_total_product, tv_order_total, tv_subtotal, tv_product_code, tv_disctotal;
    ListView lv_checkout_product_list;
    ArrayList<Bean_Cart_Data> bean_cart_data = new ArrayList<Bean_Cart_Data>();
    DatabaseHandler db;
    CustomResultAdapter adapter;
    Custom_ProgressDialog loadingView;
    String comm = new String();
    JSONArray jarray_OrderProductData = new JSONArray();
    JSONArray jarray_OrderUserData = new JSONArray();
    JSONArray jarray_OrderTotalData = new JSONArray();
    String json;
    LinearLayout l_cal_qty, l_cal, layout_discount;
    TextView txt_qty;
    AppPrefs appPrefs;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    TextView tv_user_name_billing, tv_number_billing, tv_address_billing, tv_user_name_delivery, tv_number_delivery, tv_address_delivery;
    String role, user_type;
    ImageView im_billing, im_delivery;
    TextView txt_photo2;

    ArrayList<Bean_Address> array_address = new ArrayList<Bean_Address>();
    ArrayList<String> array_final_address = new ArrayList<String>();
    GPSTracker gps;
    String selected_billing_address = "";
    String selected_shipping_address = "";
    ImageView minuss, plus;
    Button buy_cart, cancel;
    EditText edt_count;
    TextView tv_pop_pname, tv_pop_code, tv_pop_packof, tv_pop_mrp, tv_pop_sellingprice, tv_total, tv_txt_mrp;
    LinearLayout l_mrp, l_sell, l_totalInDialog;
    TextView txt_selling_price, txt_total_txt;
    String Selling = "";

    String selected_billing_address_id = "";
    String selected_shipping_address_id = "";
    String comment = "";
    // ArrayList<Bean_Schme_value> bean_schme = new ArrayList<Bean_Schme_value>();
    String role_id = new String();
    String product_id = new String();
    LinearLayout l_total;
    String user_id = "";
    TextView txt_subtotal;
    int selectedImageFor = 0;
    public static final int SELECT_PICTURE = 1;
    public static final int SELECT_PICTURE_KITKAT = 2;
    double lat, longt;
    String docPath1 = "";
    int kkk = 0;
    String qun = "";
    int s = 0;
    ArrayList<Bean_Product> bean_product_schme = new ArrayList<Bean_Product>();
    ArrayList<Bean_Schme_value> bean_schme = new ArrayList<Bean_Schme_value>();
    ArrayList<Bean_ProductOprtion> bean_Oprtions = new ArrayList<Bean_ProductOprtion>();
    double amount1;
    String user_id_main = "";
    JSONArray jarray_cart = new JSONArray();

    TextView titleName;
    TextView bankName;
    TextView acNo;
    TextView ifsCode;
    TextView branchName;
    TextView branchCode;
    String mainGrandTotal = "";
    String strr = new String();
    boolean hasCouponApplied;
    //ArrayList<bean_cart_data> bean_cart_data_data = new ArrayList<bean_cart_data_Data>();
    String coupon_id = "", coupon_code = "", discount_amount = "", coupon_name = "";
    //Bean_Company_Sales_User companySalesUser;
    String owner_id = new String();
    String u_id = new String();
    Button btn_continue;
    AppPrefs app;
    String cartJSON = "";
    boolean hasCartCallFinish = true;

    TextView txt;
    LinearLayout layout_productItem;

    boolean isNotDone = true;
    String jsonData = "";


    boolean isClicked = false;
    private int minOrderValue = 0;
    private String transportationText = "";

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
        setContentView(R.layout.activity_checkout_page__product);
        layout_discount = (LinearLayout) findViewById(R.id.layout_discount);
        appPrefs = new AppPrefs(getApplicationContext());
        setRefershData();

        dialog = new Dialog(CheckoutPage_Product.this);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog1 = new Dialog(CheckoutPage_Product.this);
        dialog1.getWindow();
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(CheckoutPage_Product.this);
                    role_id = app.getSubSalesId();
                    u_id = app.getSalesPersonId();
                } else {
                    u_id = owner_id;
                }


            }
        }

        /*if(role_id.equalsIgnoreCase("2")) {
        if (appPrefs.getCurrentPage().toString().equalsIgnoreCase("Cart")) {
            new GetBankInformation().execute();
        }
        }*/
        mainGrandTotal = appPrefs.getCart_Grand_total();

        if (!role_id.equals(C.CUSTOMER) && !role_id.equals(C.DISTRIBUTOR) && !role_id.isEmpty() && !role_id.equals(C.DISTRIBUTOR_PROFESSIONAL) && !role_id.equals(C.THIRD_TIER_RETAILER) && !role_id.equalsIgnoreCase(C.PROFESSIONAL)) {
            gps = new GPSTracker(this);
            if (gps.canGetLocation()) {
                lat = gps.getLatitude(); // returns latitude
                longt = gps.getLongitude(); // returns longitude
            } else {
                gps.showSettingsAlert();
            }
        }

        db = new DatabaseHandler(CheckoutPage_Product.this);
        appPrefs = new AppPrefs(CheckoutPage_Product.this);
        //  bean_cart_data =  db.Get_Product_Cart();
        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    appPrefs = new AppPrefs(CheckoutPage_Product.this);
                    role_id = appPrefs.getSubSalesId();
                    u_id = appPrefs.getSalesPersonId();
                } else {
                    u_id = owner_id;
                }


            }

        } else {
            u_id = "";
        }
        Log.e("ABBCDHSK", "" + u_id);
        Log.e("ABBCDHSK", "" + role_id);

        new get_cartdata().execute();


    }

    private void setdata() {
        tv_user_name_billing = (TextView) this.findViewById(R.id.tv_user_name_billing);
        tv_number_billing = (TextView) this.findViewById(R.id.tv_number_billing);
        tv_user_name_delivery = (TextView) this.findViewById(R.id.tv_user_name_delivery);
        tv_number_delivery = (TextView) this.findViewById(R.id.tv_number_delivery);
        layout_productItem = (LinearLayout) findViewById(R.id.layout_productItem);

        for (int i = 0; i < user_data.size(); i++) {

            user_id = user_data.get(i).getUser_id().toString();

            role_id = user_data.get(i).getUser_type().toString();

           /* tv_user_name_billing.setText(user_data.get(0).getF_name());
            tv_user_name_delivery.setText(user_data.get(0).getF_name());
            tv_number_billing.setText(user_data.get(0).getPhone_no());
            tv_number_delivery.setText(user_data.get(0).getPhone_no());*/

            if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {
                appPrefs = new AppPrefs(CheckoutPage_Product.this);
                role_id = appPrefs.getSubSalesId().toString();
                user_id = appPrefs.getSalesPersonId().toString();
            }

        }


        im_billing = (ImageView) this.findViewById(R.id.im_billing);
        im_delivery = (ImageView) this.findViewById(R.id.im_delivery);
        for (int i = 0; i < user_data.size(); i++) {

            user_id = user_data.get(i).getUser_id().toString();

            role_id = user_data.get(i).getUser_type().toString();
        }
        if (role_id.equalsIgnoreCase("2") || role_id.equalsIgnoreCase("10")) {
            im_billing.setVisibility(View.VISIBLE);
            im_delivery.setVisibility(View.VISIBLE);
        } else {
            im_billing.setVisibility(View.GONE);
            im_delivery.setVisibility(View.VISIBLE);
        }
        tv_total_product = (TextView) this.findViewById(R.id.tv_total_product);
        //tv_product_code=(TextView) this.findViewById(R.id.tv_product_code);

        tv_subtotal = (TextView) this.findViewById(R.id.tv_subtotal);
        tv_disctotal = (TextView) this.findViewById(R.id.tv_disctotal);
        tv_order_total = (TextView) this.findViewById(R.id.tv_order_total);
        lv_checkout_product_list = (ListView) this.findViewById(R.id.lv_checkout_product_list);
        l_total = (LinearLayout) this.findViewById(R.id.l_total);

        tv_address_billing = (TextView) this.findViewById(R.id.tv_address_billing);

        tv_address_delivery = (TextView) this.findViewById(R.id.tv_address_delivery);
        txt_subtotal = (TextView) findViewById(R.id.sub_total);

        setRefershData();
        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(CheckoutPage_Product.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                } else {
                    u_id = owner_id;
                }


            }
        }
        if (user_data.size() != 0) {
            for (int ii = 0; ii < user_data.size(); ii++) {

                //  user_id_main = user_data.get(ii).getUser_id().toString();
                role = user_data.get(ii).getUser_type().toString();
            }

        } else {
            //user_id_main = "";
        }
        if (role_id.equalsIgnoreCase("3") && role.equalsIgnoreCase("6")) {
            l_total.setVisibility(View.GONE);
        } else {
            l_total.setVisibility(View.VISIBLE);
        }


        for (int i = 0; i < user_data.size(); i++) {

            if (user_data.get(i).getUser_type().equalsIgnoreCase("0")) {

                txt_subtotal.setText("(VAT will be added extra)");
            } else if (user_data.get(i).getUser_type().equalsIgnoreCase("2")) {

                txt_subtotal.setText("(Tax Amount Inclusive)");
            } else if (user_data.get(i).getUser_type().equalsIgnoreCase("10")) {

                txt_subtotal.setText("(Tax Amount Inclusive)");
            }/*else if(user_data.get(i).getUser_type().equalsIgnoreCase("5")) {

                txt_subtotal.setText("(Tax Amount Inclusive)");
            }*/ else {
                txt_subtotal.setText("(VAT will be added extra)");
            }
        }


        im_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(CheckoutPage_Product.this);
                dialog.setContentView(R.layout.popup_dialog_address);

                Button btn_add_new_address = (Button) dialog.findViewById(R.id.btn_add_new_address);
                Button btn_select = (Button) dialog.findViewById(R.id.btn_select);
                if (array_final_address.size() == 0) {
                    btn_select.setVisibility(View.GONE);
                    Globals.CustomToast(CheckoutPage_Product.this, "No address", getLayoutInflater());
                } else {
                    btn_select.setVisibility(View.GONE);
                }

                btn_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selected_billing_address.toString().equalsIgnoreCase("")) {
                            dialog.dismiss();
                        } else {

                            tv_address_billing.setText(selected_billing_address);
                            dialog.dismiss();
                        }
                            /*tv_address_billing.setText(selected_billing_address);
                            dialog.dismiss();*/
                    }
                });

                btn_add_new_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CheckoutPage_Product.this, Add_Newuser_Address.class);
                        i.putExtra("USER_ID", user_id);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
                LinearLayout mLinearLayout = (LinearLayout) dialog.findViewById(R.id.layout_address_dialog);


                // create radio button
                //final RadioButton[] rb = new RadioButton[array_final_address.size()];
                RadioGroup rg = new RadioGroup(CheckoutPage_Product.this);
                rg.setOrientation(RadioGroup.VERTICAL);
                for (int i = 0; i < array_final_address.size(); i++) {
                    final RadioButton rb = new RadioButton(CheckoutPage_Product.this);
                    rg.addView(rb);

                    rb.setText(array_final_address.get(i));

                    rb.setTag(array_address.get(i).getAddress_id());
                    rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                selected_billing_address = rb.getText().toString();
                                selected_billing_address_id = rb.getTag().toString();
                                //dialog.dismiss();
                            }
                        }
                    });

                }


                mLinearLayout.addView(rg);


                dialog.show();

            }

        });

        im_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPrefs = new AppPrefs(CheckoutPage_Product.this);
                appPrefs.setaddress_id("");
                final Dialog dialog = new Dialog(CheckoutPage_Product.this);
                dialog.setContentView(R.layout.popup_dialog_address);

                Button btn_add_new_address = (Button) dialog.findViewById(R.id.btn_add_new_address);
                Button btn_select = (Button) dialog.findViewById(R.id.btn_select);
                btn_select.setVisibility(View.GONE);

                for (int i = 0; i < user_data.size(); i++) {

                    user_id = user_data.get(i).getUser_id().toString();

                    role_id = user_data.get(i).getUser_type().toString();

                    if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {
                        btn_add_new_address.setVisibility(View.GONE);
                    }

                    if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                        appPrefs = new AppPrefs(CheckoutPage_Product.this);
                        role_id = appPrefs.getSubSalesId().toString();
                        user_id = appPrefs.getSalesPersonId().toString();
                    }


                }


                if (array_final_address.size() == 0) {
                    btn_select.setVisibility(View.GONE);
                    Globals.CustomToast(CheckoutPage_Product.this, "No address", getLayoutInflater());
                } else {
                    btn_select.setVisibility(View.GONE);
                }
                btn_add_new_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CheckoutPage_Product.this, Add_Newuser_Address.class);
                        i.putExtra("USER_ID", user_id);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });

                btn_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selected_shipping_address.toString().equalsIgnoreCase("")) {
                            dialog.dismiss();
                        } else {

                            tv_address_delivery.setText(selected_shipping_address);
                            dialog.dismiss();
                        }
                    }
                });
                LinearLayout mLinearLayout = (LinearLayout) dialog.findViewById(R.id.layout_address_dialog);

                RadioGroup rg = new RadioGroup(CheckoutPage_Product.this);
                rg.setOrientation(RadioGroup.VERTICAL);
                for (int i = 0; i < array_final_address.size(); i++) {
                    final RadioButton rb = new RadioButton(CheckoutPage_Product.this);
                    rg.addView(rb);

                    rb.setText(array_final_address.get(i));
                    rb.setTag(array_address.get(i).getAddress_id());
                    rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                selected_shipping_address = rb.getText().toString();
                                selected_shipping_address_id = rb.getTag().toString();
                                tv_address_delivery.setText(selected_shipping_address);
                                dialog.dismiss();
                            }
                        }
                    });

                }

                mLinearLayout.addView(rg);


                dialog.show();
            }
        });

        //Log.e("", "User Id before web services " + user_data.get(0).getUser_id());

      /*  for (int i = 0; i < user_data.size(); i++) {

            user_id = user_data.get(i).getUser_id().toString();

            role_id = user_data.get(i).getUser_type().toString();

            tv_user_name_billing.setText(user_data.get(0).getF_name());
            tv_user_name_delivery.setText(user_data.get(0).getF_name());
            tv_number_billing.setText(user_data.get(0).getPhone_no());
            tv_number_delivery.setText(user_data.get(0).getPhone_no());

            if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                appPrefs = new AppPrefs(CheckoutPage_Product.this);
                role_id = appPrefs.getSubSalesId().toString();
                user_id = appPrefs.getSalesPersonId().toString();

//                tv_user_name_billing.setText(array_address.get(0).getFirstName());
//                tv_user_name_delivery.setText(array_address.get(0).getFirstName());
//                tv_number_billing.setText(array_address.get(0).getMobile());
//                tv_number_delivery.setText(array_address.get(0).getMobile());
            }

        }*/

        //Log.e("USERIDDD", "" + user_id);

        new get_address_list().execute();


        tv_total_product.setText("" + bean_cart_data.size());


        btn_pay1 = (Button) findViewById(R.id.btn_pay1);
        btn_pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // btn_pay1.setEnabled(false);

                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                /*if(!role_id.equals(C.CUSTOMER) && !role_id.equals(C.DISTRIBUTOR) && !role_id.isEmpty() && !role_id.equals(C.DISTRIBUTOR_PROFESSIONAL) && !role_id.equals(C.THIRD_TIER_RETAILER) && !role_id.equalsIgnoreCase(C.PROFESSIONAL)){

                }*/

                gps = new GPSTracker(CheckoutPage_Product.this);

                String grandTotalString = tv_order_total.getText().toString().isEmpty() ? "0" : tv_order_total.getText().toString();

                float grandTotal = Float.parseFloat(grandTotalString);
                if (grandTotal < minOrderValue) {
                    Globals.Toast2(getApplicationContext(), "Please Make Order of Min " + getString(R.string.ruppe_name) + " " + minOrderValue);
                } else if (!gps.canGetLocation()) {
                    gps.showSettingsAlert();
                } else {

                    lat = gps.getLatitude(); // returns latitude
                    longt = gps.getLongitude(); // returns longitude
                    //Log.e("11111", "111111111111");
                    if (tv_address_billing.getText().toString().trim().equalsIgnoreCase("") || tv_address_billing.getText().toString().trim().equalsIgnoreCase("null")) {
                        Globals.CustomToast(CheckoutPage_Product.this, "Please select shipping address", getLayoutInflater());
                    } else if (tv_address_delivery.getText().toString().trim().equalsIgnoreCase("") || tv_address_delivery.getText().toString().trim().equalsIgnoreCase("null")) {
                        Globals.CustomToast(CheckoutPage_Product.this, "Please select shipping address", getLayoutInflater());
                    } else if (selected_billing_address_id.toString().trim().equalsIgnoreCase("")) {
                        Globals.CustomToast(CheckoutPage_Product.this, "Please select shipping address", getLayoutInflater());
                    } else if (selected_shipping_address_id.toString().trim().equalsIgnoreCase("")) {
                        Globals.CustomToast(CheckoutPage_Product.this, "Please select shipping address", getLayoutInflater());
                    } else {


                        setRefershData();

                        for (int i = 0; i < user_data.size(); i++) {

                            user_type = user_data.get(i).getUser_type().toString();
                            //Log.e("user_type", "" + user_type);
                        }

                        if (user_type.equalsIgnoreCase("2") || user_type.equalsIgnoreCase("10")) {

                            try {
                                JSONObject jobject_OrderUserData = new JSONObject();
                                //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                                jobject_OrderUserData.put("user_id", user_data.get(0).getUser_id());
                                jobject_OrderUserData.put("billing_address_id", selected_billing_address_id);
                                jobject_OrderUserData.put("shipping_address_id", selected_shipping_address_id);
                                jarray_OrderUserData.put(jobject_OrderUserData);
                            } catch (JSONException e) {

                            }

                            for (int i = 0; i < bean_cart_data.size(); i++) {
                                try {

                                    product_id = bean_cart_data.get(i).getPro_scheme().toString();


                                    JSONObject jobject = new JSONObject();


                                    jobject.put("pro_id", bean_cart_data.get(i).getProduct_id());
                                    jobject.put("pro_cat_id", bean_cart_data.get(i).getCategory_id());
                                    String newString = bean_cart_data.get(i).getPro_code().toString().replace("(", "");
                                    //Log.e("avbcdf", "" + newString);
                                    String newStr = newString.replace(")", "");
                                    //Log.e("avbcdf", "" + newStr);
                                    jobject.put("pro_code", newStr);
                                    jobject.put("pro_name", bean_cart_data.get(i).getName());
                                    jobject.put("pro_qty", bean_cart_data.get(i).getQuantity());
                                    jobject.put("pro_mrp", bean_cart_data.get(i).getMrp());
                                    jobject.put("pro_sellingprice", bean_cart_data.get(i).getSelling_price());
                                    jobject.put("pro_Option_id", bean_cart_data.get(i).getOption_id());
                                    jobject.put("pro_Option_name", bean_cart_data.get(i).getOption_name());
                                    jobject.put("pro_Option_value_id", bean_cart_data.get(i).getOption_value_id());
                                    jobject.put("pro_Option_value_name", bean_cart_data.get(i).getOption_value_name());
                                    jobject.put("pro_total", bean_cart_data.get(i).getItem_total());
                                    jobject.put("pack_of", bean_cart_data.get(i).getPack_of());
                                    //Log.e("sTRSSS", "" + strr);
                                    jobject.put("pro_scheme", bean_cart_data.get(i).getScheme_id());
                                    jarray_OrderProductData.put(jobject);
                                    // }
                                } catch (JSONException e) {

                                }
                            }


                            try {
                                JSONObject jobject_OrderTotalData = new JSONObject();
                                //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                                jobject_OrderTotalData.put("grandtotal", tv_order_total.getText().toString());
                                jobject_OrderTotalData.put("comment", comment);

                                if (hasCouponApplied) {
                                    jobject_OrderTotalData.put("coupon_code", coupon_code);
                                    jobject_OrderTotalData.put("coupon_id", coupon_id);
                                    jobject_OrderTotalData.put("discount_amount", discount_amount);
                                    jobject_OrderTotalData.put("coupon_name", coupon_name);
                                }
                                jarray_OrderTotalData.put(jobject_OrderTotalData);
                            } catch (JSONException e) {

                            }
                            new send_order_details().execute();
                        } else if (appPrefs.getUserRoleId().equalsIgnoreCase("3") || user_type.equalsIgnoreCase("4") || user_type.equalsIgnoreCase("5") || user_type.equalsIgnoreCase("8") || user_type.equalsIgnoreCase("9")) {

                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            int width = metrics.widthPixels;
                            int height = metrics.heightPixels;

                            dialog.setContentView(R.layout.checkout_comment);
                            dialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);
                            //   dialog.setCancelable(true);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            final EditText edt_comment = (EditText) dialog.findViewById(R.id.edt_comment);
                            final EditText edt_transportation = (EditText) dialog.findViewById(R.id.edt_transportation);
                            btn_continue = (Button) dialog.findViewById(R.id.btn_send);
                            Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                            Button btn_upload_photo2 = (Button) dialog.findViewById(R.id.btn_upload_photo2);
                            txt_photo2 = (TextView) dialog.findViewById(R.id.txt_photo2);


                            btn_upload_photo2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selectedImageFor = 1;

                                    if (Build.VERSION.SDK_INT < 19) {
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                                    } else {
                                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, SELECT_PICTURE_KITKAT);
                                    }
                                }
                            });


                            btn_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btn_pay1.setEnabled(true);
                                    dialog.dismiss();
                                }
                            });

                            btn_continue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btn_continue.setEnabled(false);
                                    transportationText = edt_transportation.getText().toString().trim();
                                    if (transportationText.isEmpty()) {
                                        Globals.Toast2(getApplicationContext(), "Please Enter Transportation");
                                        return;
                                    }
                                    try {
                                        JSONObject jobject_OrderUserData = new JSONObject();
                                        //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                                        jobject_OrderUserData.put("user_id", user_data.get(0).getUser_id());
                                        jobject_OrderUserData.put("billing_address_id", selected_billing_address_id);
                                        jobject_OrderUserData.put("shipping_address_id", selected_shipping_address_id);
                                        jobject_OrderUserData.put("latitude", lat);
                                        jobject_OrderUserData.put("longitude", longt);
                                        jobject_OrderUserData.put("owner_id", user_data.get(0).getUser_id());
                                        jobject_OrderUserData.put("order_owner_role_id", user_data.get(0).getUser_type());
                                        jobject_OrderUserData.put("place_order_person_role_id", user_data.get(0).getUser_type());

                                        jarray_OrderUserData.put(jobject_OrderUserData);
                                    } catch (JSONException e) {

                                    }

                                    for (int i = 0; i < bean_cart_data.size(); i++) {
                                        try {


                                            JSONObject jobject = new JSONObject();

                                            if (bean_cart_data.get(i).isComboPack()) {
                                                JSONArray productArray = new JSONArray(bean_cart_data.get(i).getComboCart().getProducts());

                                                Log.e("ProductArray", productArray.toString());

                                                for (int j = 0; j < productArray.length(); j++) {
                                                    JSONObject product = productArray.getJSONObject(j);
                                                    jarray_OrderProductData.put(product);
                                                }
                                            } else {
                                                jobject.put("pro_id", bean_cart_data.get(i).getProduct_id());
                                                jobject.put("pro_cat_id", bean_cart_data.get(i).getCategory_id());
                                                String newString = bean_cart_data.get(i).getPro_code().toString().replace("(", "");
                                                //Log.e("avbcdf", "" + newString);
                                                String newStr = newString.replace(")", "");
                                                //Log.e("avbcdf", "" + newStr);
                                                jobject.put("pro_code", newStr);
                                                jobject.put("pro_name", bean_cart_data.get(i).getName());
                                                jobject.put("pro_qty", bean_cart_data.get(i).getQuantity());
                                                jobject.put("pro_mrp", bean_cart_data.get(i).getMrp());
                                                jobject.put("pro_sellingprice", bean_cart_data.get(i).getSelling_price());
                                                jobject.put("pro_Option_id", bean_cart_data.get(i).getOption_id());
                                                jobject.put("pro_Option_name", bean_cart_data.get(i).getOption_name());
                                                jobject.put("pro_Option_value_id", bean_cart_data.get(i).getOption_value_id());
                                                jobject.put("pro_Option_value_name", bean_cart_data.get(i).getOption_value_name());
                                                jobject.put("pro_total", bean_cart_data.get(i).getItem_total());
                                                jobject.put("pack_of", bean_cart_data.get(i).getPack_of());
                                                //Log.e("sTRSSS", "" + strr);
                                                jobject.put("pro_scheme", bean_cart_data.get(i).getScheme_id());
                                                jarray_OrderProductData.put(jobject);
                                            }
                                            // }
                                        } catch (JSONException e) {

                                        }
                                    }


                                    try {
                                        JSONObject jobject_OrderTotalData = new JSONObject();
                                        //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                                        jobject_OrderTotalData.put("grandtotal", tv_order_total.getText().toString());
                                        jobject_OrderTotalData.put("comment", edt_comment.getText().toString().trim());
                                        jobject_OrderTotalData.put("transportation", transportationText);
                                        transportationText = "";
                                        if (hasCouponApplied) {
                                            jobject_OrderTotalData.put("coupon_code", coupon_code);
                                            jobject_OrderTotalData.put("coupon_id", coupon_id);
                                            jobject_OrderTotalData.put("discount_amount", discount_amount);
                                            jobject_OrderTotalData.put("coupon_name", coupon_name);
                                        }
                                        jarray_OrderTotalData.put(jobject_OrderTotalData);
                                    } catch (JSONException e) {

                                    }
                                    docPath1 = txt_photo2.getText().toString();
                                    //Log.e("12121212", "" + docPath1);
                                    File file1 = new File(docPath1);
                                    long length1 = file1.length() / 1024;
                                    if (!docPath1.equalsIgnoreCase("") && length1 == 0 || length1 > 2560) {
                                        //Log.e("docpath1 size", length1 + "");
                                        Globals.CustomToast(getApplicationContext(), "Please Select Max 2.5 MB Image in Attachment", getLayoutInflater());
                                    } else {
                                        new send_B2B_details().execute();
                                    }
                                }
                            });


                            dialog.show();


                        } else if (user_type.equals(C.ADMIN) || user_type.equalsIgnoreCase("6") || user_type.equalsIgnoreCase("7")) {


                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            int width = metrics.widthPixels;
                            int height = metrics.heightPixels;

                            dialog.setContentView(R.layout.checkout_comment);
                            dialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);
                            dialog.setCancelable(false);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            final EditText edt_comment = (EditText) dialog.findViewById(R.id.edt_comment);
                            final EditText edt_transportation = (EditText) dialog.findViewById(R.id.edt_transportation);
                            Button btn_continue = (Button) dialog.findViewById(R.id.btn_send);
                            Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                            Button btn_upload_photo2 = (Button) dialog.findViewById(R.id.btn_upload_photo2);
                            txt_photo2 = (TextView) dialog.findViewById(R.id.txt_photo2);


                            btn_upload_photo2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selectedImageFor = 1;

                                    if (Build.VERSION.SDK_INT < 19) {
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                                    } else {
                                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, SELECT_PICTURE_KITKAT);
                                    }
                                }
                            });


                            btn_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btn_pay1.setEnabled(true);
                                    dialog.dismiss();


                                }
                            });

                            btn_continue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (user_type.equals(C.ADMIN)) {
                                        dialog.dismiss();
                                        try {
                                            JSONObject jobject_OrderUserData = new JSONObject();
                                            //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                                            jobject_OrderUserData.put("user_id", user_id);
                                            jobject_OrderUserData.put("billing_address_id", selected_billing_address_id);
                                            jobject_OrderUserData.put("shipping_address_id", selected_shipping_address_id);
                                            jobject_OrderUserData.put("latitude", lat);
                                            jobject_OrderUserData.put("longitude", longt);
                                            jobject_OrderUserData.put("owner_id", user_data.get(0).getUser_id());
                                            jobject_OrderUserData.put("order_owner_role_id", role_id);
                                            jobject_OrderUserData.put("place_order_person_role_id", user_data.get(0).getUser_type());

                                            jarray_OrderUserData.put(jobject_OrderUserData);
                                        } catch (JSONException e) {

                                        }

                                        for (int i = 0; i < bean_cart_data.size(); i++) {
                                            try {

                                                product_id = bean_cart_data.get(i).getPro_scheme();


                                                JSONObject jobject = new JSONObject();

                                                if (bean_cart_data.get(i).isComboPack()) {
                                                    JSONArray productArray = new JSONArray(bean_cart_data.get(i).getComboCart().getProducts());

                                                    Log.e("ProductArray", productArray.toString());

                                                    for (int j = 0; j < productArray.length(); j++) {
                                                        JSONObject product = productArray.getJSONObject(j);
                                                        jarray_OrderProductData.put(product);
                                                    }
                                                } else {
                                                    jobject.put("pro_id", bean_cart_data.get(i).getProduct_id());
                                                    jobject.put("pro_cat_id", bean_cart_data.get(i).getCategory_id());
                                                    String newString = bean_cart_data.get(i).getPro_code().replace("(", "");
                                                    //Log.e("avbcdf",""+newString);
                                                    String newStr = newString.replace(")", "");
                                                    //Log.e("avbcdf",""+newStr);
                                                    jobject.put("pro_code", newStr);
                                                    jobject.put("pro_name", bean_cart_data.get(i).getName());
                                                    jobject.put("pro_qty", bean_cart_data.get(i).getQuantity());
                                                    jobject.put("pro_mrp", bean_cart_data.get(i).getMrp());
                                                    jobject.put("pro_sellingprice", bean_cart_data.get(i).getSelling_price());
                                                    jobject.put("pro_Option_id", bean_cart_data.get(i).getOption_id());
                                                    jobject.put("pro_Option_name", bean_cart_data.get(i).getOption_name());
                                                    jobject.put("pro_Option_value_id", bean_cart_data.get(i).getOption_value_id());
                                                    jobject.put("pro_Option_value_name", bean_cart_data.get(i).getOption_value_name());
                                                    jobject.put("pro_total", bean_cart_data.get(i).getItem_total());
                                                    jobject.put("pack_of", bean_cart_data.get(i).getPack_of());
                                                    //Log.e("sTRSSS",""+strr);
                                                    jobject.put("pro_scheme", bean_cart_data.get(i).getScheme_id());
                                                    jarray_OrderProductData.put(jobject);
                                                }

                                            } catch (JSONException e) {

                                            }
                                        }


                                        try {
                                            JSONObject jobject_OrderTotalData = new JSONObject();
                                            //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                                            jobject_OrderTotalData.put("grandtotal", tv_order_total.getText().toString());
                                            jobject_OrderTotalData.put("comment", comm);
                                            jobject_OrderTotalData.put("transportation", transportationText);
                                            transportationText = "";

                                            if (hasCouponApplied) {
                                                jobject_OrderTotalData.put("coupon_code", coupon_code);
                                                jobject_OrderTotalData.put("coupon_id", coupon_id);
                                                jobject_OrderTotalData.put("discount_amount", discount_amount);
                                                jobject_OrderTotalData.put("coupon_name", coupon_name);
                                            }
                                            jarray_OrderTotalData.put(jobject_OrderTotalData);
                                        } catch (JSONException e) {

                                        }
                                        docPath1 = txt_photo2.getText().toString();
                                        //Log.e("12121212",""+docPath1);
                                        File file1 = new File(docPath1);
                                        long length1 = file1.length() / 1024;
                                        if (!docPath1.equalsIgnoreCase("") && length1 == 0 || length1 > 2560) {
                                            //Log.e("docpath1 size",length1+"");
                                            Globals.CustomToast(getApplicationContext(), "Please Select Max 2.5 MB Image in Attachment", getLayoutInflater());
                                        } else {
                                            // dialog1.dismiss();
                                            new send_B2B_details().execute();
                                        }
                                    } else {
                                        transportationText = edt_transportation.getText().toString().trim();

                                        if (transportationText.isEmpty()) {
                                            Globals.Toast2(getApplicationContext(), "Please Enter Transportation");
                                            return;
                                        }

                                        dialog.dismiss();
                                        DisplayMetrics metrics = getResources().getDisplayMetrics();
                                        int width = metrics.widthPixels;
                                        int height = metrics.heightPixels;// Dialog for OTP

                                        dialog1.setContentView(R.layout.otp_popup);
                                        dialog1.getWindow().setLayout((7 * width) / 7, (5 * height) / 5);
                                        //dialog1.setCancelable(true);
                                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                                        edt_mobile = (EditText) dialog1.findViewById(R.id.edt_mobile);
                                        btn_otp = (Button) dialog1.findViewById(R.id.btn_otp);
                                        btn_submit = (Button) dialog1.findViewById(R.id.btn_submit);
                                        txt_otp = (TextView) dialog1.findViewById(R.id.txt_otp);
                                        edt_otp = (EditText) dialog1.findViewById(R.id.edt_otp);
                                        btn_cancel1 = (ImageView) dialog1.findViewById(R.id.btn_cancel);
                                        appPrefs = new AppPrefs(getApplicationContext());
                                        user_ID1 = appPrefs.getUserId();

                                        edt_mobile.setText("+91-");
                                        Selection.setSelection(edt_mobile.getText(), edt_mobile.getText().length());

                                        btn_cancel1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog1.dismiss();
                                            }
                                        });

                                        edt_mobile.addTextChangedListener(new TextWatcher() {

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                // TODO Auto-generated method stub

                                            }

                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                                          int after) {
                                                // TODO Auto-generated method stub

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {

                                                if (!s.toString().contains("+91-")) {
                                                    edt_mobile.setText("+91-");
                                                    Selection.setSelection(edt_mobile.getText(), edt_mobile.getText().length());

                                                }

                                            }
                                        });


                                        edt_mobile.setText("+91-" + sales_number);
                                        edt_mobile.setEnabled(false);
//                            }

                                        //Log.e("mobile from array", array_address.get(0).getMobile() + "-->");

                                        btn_otp.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                smobile = edt_mobile.getText().toString().trim().replace("+91-", "");
                                                phone = edt_mobile.getText().toString().trim();
                                                if (edt_mobile.getText().toString().trim().equalsIgnoreCase("+91-")) {
                                                    Globals.CustomToast(CheckoutPage_Product.this, "Please Enter Mobile Number", getLayoutInflater());
                                                    edt_mobile.requestFocus();
                                                } else if (edt_mobile.length() < 14 && !smobile.equalsIgnoreCase("")) {
                                                    Globals.CustomToast(CheckoutPage_Product.this, "Please Enter Valid 10 Digit Mobile Number", getLayoutInflater());
                                                    edt_mobile.requestFocus();
                                                } else {
                                                    //Log.e("5555555555", "" + appPrefs.getUserId());
                                                    phone = edt_mobile.getText().toString().trim().replace("+91-", "");
                                                    // phone_1=edt_mobile.getText().toString()replace("+91-","");
                                                    new Generate_OTP(CheckoutPage_Product.this, appPrefs.getUserId(), appPrefs.getSalesPersonId()).execute();

                                                }

                                            }
                                        });

                                        btn_submit.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                otp = edt_otp.getText().toString().trim();
                                                if (edt_otp.getText().toString().trim().equalsIgnoreCase("")) {
                                                    Toast.makeText(CheckoutPage_Product.this, "Please Enter OTP", Toast.LENGTH_LONG).show();
                                                    // Global.CustomToast(Activity_Login.this,"Please Enter OTP",getLayoutInflater());

                                                } else {

                                                    otp = edt_otp.getText().toString().trim();
                                                    comm = edt_comment.getText().toString();
                                                    if (!isClicked) {
                                                        isClicked = true;
                                                        new check_otp(CheckoutPage_Product.this, store_ref, otp).execute();
                                                    }

                                                    //Log.e("22222222", "" + appPrefs.getREF_No());

                                                }
                                            }
                                        });
                                        dialog1.show();
                                    }
                                }
                            });
                            dialog.show();


                        } else {

                            try {
                                JSONObject jobject_OrderUserData = new JSONObject();
                                //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                                jobject_OrderUserData.put("user_id", user_data.get(0).getUser_id());
                                jobject_OrderUserData.put("billing_address_id", selected_billing_address_id);
                                jobject_OrderUserData.put("shipping_address_id", selected_shipping_address_id);
                                jarray_OrderUserData.put(jobject_OrderUserData);
                            } catch (JSONException e) {

                            }

                            for (int i = 0; i < bean_cart_data.size(); i++) {
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("pro_id", bean_cart_data.get(i).getProduct_id());
                                    jobject.put("pro_cat_id", bean_cart_data.get(i).getCategory_id());
                                    jobject.put("pro_code", bean_cart_data.get(i).getPro_code());
                                    jobject.put("pro_name", bean_cart_data.get(i).getName());
                                    jobject.put("pro_qty", bean_cart_data.get(i).getQuantity());
                                    jobject.put("pro_mrp", bean_cart_data.get(i).getMrp());
                                    jobject.put("pro_sellingprice", bean_cart_data.get(i).getSelling_price());
                                    jobject.put("pro_Option_id", bean_cart_data.get(i).getOption_id());
                                    jobject.put("pro_Option_name", bean_cart_data.get(i).getOption_name());
                                    jobject.put("pro_Option_value_id", bean_cart_data.get(i).getOption_value_id());
                                    jobject.put("pro_Option_value_name", bean_cart_data.get(i).getOption_value_name());
                                    jobject.put("pro_total", bean_cart_data.get(i).getItem_total());
                                    jobject.put("pack_of", bean_cart_data.get(i).getPack_of());
                                    jarray_OrderProductData.put(jobject);


                                } catch (JSONException e) {

                                }
                            }


                            try {
                                JSONObject jobject_OrderTotalData = new JSONObject();
                                //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                                jobject_OrderTotalData.put("grandtotal", tv_order_total.getText().toString());
                                jobject_OrderTotalData.put("comment", comment);
                                if (hasCouponApplied) {
                                    jobject_OrderTotalData.put("coupon_code", coupon_code);
                                    jobject_OrderTotalData.put("coupon_id", coupon_id);
                                    jobject_OrderTotalData.put("discount_amount", discount_amount);
                                    jobject_OrderTotalData.put("coupon_name", coupon_name);
                                }
                                jarray_OrderTotalData.put(jobject_OrderTotalData);
                            } catch (JSONException e) {

                            }
                            new send_order_details().execute();
                        }


                    }


                }
            }
        });

        btn_discount = (Button) findViewById(R.id.btn_discount);
        btn_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CheckoutPage_Product.this);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.custom_promocode_dialog, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle("Apply Coupon");

                edt_couponcode = (EditText) dialogView.findViewById(R.id.edt_couponcode);
                final Button btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
                Button btn_apply = (Button) dialogView.findViewById(R.id.btn_apply);

                final AlertDialog b = dialogBuilder.create();
                b.show();

                //Log.e("grand_total",appPrefs.getCart_Grand_total());

                btn_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code = edt_couponcode.getText().toString().trim();

                        if (code.equalsIgnoreCase("")) {
                            Globals.CustomToast(getApplicationContext(), "Please Enter Coupon Code", getLayoutInflater());
                            //Toast(getApplicationContext(),"Please Enter Coupon Code",getLayoutInflater());
                            edt_couponcode.requestFocus();
                        } else {
                            new CheckCouponData(CheckoutPage_Product.this, edt_couponcode.getText().toString()).execute();
                            b.dismiss();
                        }
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.cancel();
                    }
                });

            }
        });


        setActionBar();

        /*adapter = new CustomResultAdapter();
        adapter.notifyDataSetChanged();
        lv_checkout_product_list.setAdapter(adapter);*/

        showProductItems();

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();


    }

    private float getCartGrandTotal() {
        float grandTotal = 0;

        for (int i = 0; i < bean_cart_data.size(); i++) {

            if (bean_cart_data.get(i).isComboPack()) {
                grandTotal += Float.parseFloat(bean_cart_data.get(i).getComboCart().getTotal_selling_price());
            } else {
                grandTotal += Float.parseFloat(bean_cart_data.get(i).getItem_total());
            }
        }

        return grandTotal;
    }

    private void showProductItems() {
        layout_productItem.removeAllViews();

        Log.e("Data", new Gson().toJson(bean_cart_data));

        for (int position = 0; position < bean_cart_data.size(); position++) {

            View convertView = null;

            if (bean_cart_data.get(position).isComboPack()) {
                convertView = getLayoutInflater().inflate(R.layout.layout_combo_cart, null);

                TextView txt_comboName = (TextView) convertView.findViewById(R.id.txt_comboName);
                TextView txt_mrpPrice = (TextView) convertView.findViewById(R.id.txt_mrpPrice);
                TextView txt_sellingPrice = (TextView) convertView.findViewById(R.id.txt_sellingPrice);
                TextView txt_totalQty = (TextView) convertView.findViewById(R.id.txt_totalQty);
                ImageView img_editCombo = (ImageView) convertView.findViewById(R.id.img_editCombo);
                ImageView img_deleteCombo = (ImageView) convertView.findViewById(R.id.img_deleteCombo);

                txt_comboName.setText(bean_cart_data.get(position).getComboData().getOfferTitle());
                txt_mrpPrice.setText(bean_cart_data.get(position).getComboCart().getTotal_mrp());
                txt_sellingPrice.setText(bean_cart_data.get(position).getComboCart().getTotal_selling_price());
                txt_totalQty.setText(bean_cart_data.get(position).getComboCart().getProductQty());

                img_deleteCombo.setVisibility(View.GONE);

                final int finalPosition2 = position;
                img_editCombo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ComboOfferActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("editMode", true);
                        intent.putExtra("combo_cart_id", bean_cart_data.get(finalPosition2).getComboCart().getId());
                        intent.putExtra("combo_id", bean_cart_data.get(finalPosition2).getComboCart().getCombo_id());
                        startActivityForResult(intent, EDIT_COMBO);
                    }
                });
            } else {
                convertView = getLayoutInflater().inflate(R.layout.checkout_product_list, null);

                final TextView tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
                final TextView tv_product_code = (TextView) convertView.findViewById(R.id.tv_product_code);
                final TextView tv_product_attribute = (TextView) convertView.findViewById(R.id.tv_product_attribute);
                final TextView tv_pack_of = (TextView) convertView.findViewById(R.id.tv_pack_of);
                final TextView tv_product_selling_price = (TextView) convertView.findViewById(R.id.tv_product_selling_price);
                final TextView tv_product_qty = (TextView) convertView.findViewById(R.id.tv_product_qty);
                final TextView txt_scheme = (TextView) convertView.findViewById(R.id.txt_scheme);
                final TextView txt_discount = (TextView) convertView.findViewById(R.id.txt_discount);
                //  final TextView tv_total_product = (TextView) convertView.findViewById(R.id.tv_total_product);
                final TextView tv_product_total_cost = (TextView) convertView.findViewById(R.id.tv_product_total_cost);
                LinearLayout l_cal = (LinearLayout) convertView.findViewById(R.id.l_cal);
                LinearLayout l_cal_qty = (LinearLayout) convertView.findViewById(R.id.l_call_qty);
                TextView txt_qty = (TextView) convertView.findViewById(R.id.tv_qty);


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
                    l_cal.setVisibility(View.GONE);
                    l_cal_qty.setVisibility(View.VISIBLE);
                } else {
                    l_cal.setVisibility(View.VISIBLE);
                    l_cal_qty.setVisibility(View.GONE);
                }

                double mrpPrice = Double.parseDouble(bean_cart_data.get(position).getMrp());
                double sellingPrice = Double.parseDouble(bean_cart_data.get(position).getSelling_price());

                if (mrpPrice > sellingPrice) {
                    double offPrice = mrpPrice - sellingPrice;

                    Log.e("Off Prices", "MRP " + mrpPrice + ", SP " + sellingPrice + ", offPrice " + offPrice);

                    double offPercent = (offPrice * 100);
                    double percent = offPercent / mrpPrice;

                    Log.e("Off Percent", percent + " %");

                    int discount = (int) percent;

                    Log.e("Discount", discount + " %");

                    txt_discount.setText("Discount : " + discount + " % OFF");
                }


                tv_product_name.setText(bean_cart_data.get(position).getName());
                tv_product_code.setText(bean_cart_data.get(position).getPro_code());
                tv_pack_of.setText(bean_cart_data.get(position).getPack_of());

                if (bean_cart_data.get(position).getScheme_title().toString().equalsIgnoreCase(" ")) {
                    txt_scheme.setVisibility(View.GONE);
                } else {
                    txt_scheme.setVisibility(View.VISIBLE);
                    txt_scheme.setText(bean_cart_data.get(position).getScheme_title().toString());
                }

                tv_product_selling_price.setText(bean_cart_data.get(position).getSelling_price());
                tv_product_qty.setText(bean_cart_data.get(position).getQuantity());
                txt_qty.setText(bean_cart_data.get(position).getQuantity());
                double t = Double.parseDouble(bean_cart_data.get(position).getItem_total());
                String str = String.format("%.2f", t);
                tv_product_total_cost.setText(getResources().getString(R.string.Rs) + "" + str);


                String[] option = bean_cart_data.get(position).getOption_name().toString().split(", ");
                String[] value = bean_cart_data.get(position).getOption_value_name().split(", ");
                String option_value = "";
                if (bean_cart_data.get(position).getOption_name().equalsIgnoreCase("") || bean_cart_data.get(position).getOption_name().equalsIgnoreCase("null")) {
                    tv_product_attribute.setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < option.length; i++) {
                        if (i == 0) {
                            option_value = option[i] + ": " + value[i];
                        } else {
                            option_value = option_value + ", " + option[i] + ": " + value[i];
                        }
                    }

                    tv_product_attribute.setText(option_value);
                }

                final int finalPosition = position;
                final int finalPosition1 = position;
                tv_product_qty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final int selectedPosition = finalPosition;

                        setRefershData();

                        if (user_data.size() != 0) {
                            for (int i = 0; i < user_data.size(); i++) {

                                owner_id = user_data.get(i).getUser_id().toString();

                                role_id = user_data.get(i).getUser_type().toString();

                                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                    app = new AppPrefs(getApplicationContext());
                                    role_id = app.getSubSalesId().toString();
                                    u_id = app.getSalesPersonId().toString();
                                } else {
                                    u_id = owner_id;

                                }

                                user_id_main = u_id;


                            }

                        } else {
                            //user_id_main = "";
                        }
                        // product_id = bean_cart_data.get(position).getProduct_id();
                        List<NameValuePair> para = new ArrayList<NameValuePair>();
                        para.add(new BasicNameValuePair("product_id", bean_cart_data.get(selectedPosition).getProduct_id()));
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
                                Globals.CustomToast(getApplicationContext(), "SERVER ERRER", getLayoutInflater());
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

                        dialog = new Dialog(CheckoutPage_Product.this);
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
                        tv_pop_pname.setText(bean_cart_data.get(finalPosition).getName());
                        tv_pop_code.setText(" ( " + bean_cart_data.get(finalPosition).getPro_code() + " )");
                        tv_pop_packof.setText(bean_cart_data.get(finalPosition).getPack_of());
                        tv_pop_mrp.setText(bean_cart_data.get(finalPosition).getMrp());
                        tv_pop_sellingprice.setText(bean_cart_data.get(finalPosition).getSelling_price());
                        tv_pop_sellingprice.setTag(bean_cart_data.get(finalPosition).getSelling_price());
                        l_mrp = (LinearLayout) dialog.findViewById(R.id.l_mrp);
                        l_sell = (LinearLayout) dialog.findViewById(R.id.l_sell);
                        l_totalInDialog = (LinearLayout) dialog.findViewById(R.id.l_total);
                        txt_selling_price = (TextView) dialog.findViewById(R.id.txt_selling_price);
                        txt_total_txt = (TextView) dialog.findViewById(R.id.txt_total_txt);
                        tv_total.setText(bean_cart_data.get(finalPosition).getSelling_price());
                        //  l_view_spinner.setVisibility(View.GONE);
                        //  tv_total.setText(bean_product1.get(position).getPro_sellingprice().toString());
                        final int minPackOfQty = bean_cart_data.get(finalPosition).getMinPackOfQty();
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
                        final String str = String.format("%.2f", t);
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

                                user_id_main = user_data.get(ii).getUser_id().toString();
                                role = user_data.get(ii).getUser_type().toString();

                                if (role.equals(C.ADMIN) || role.equals(C.COMP_SALES_PERSON) || role.equals(C.DISTRIBUTOR_SALES_PERSON)) {
                                    user_id_main = app.getSalesPersonId();
                                }

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
                                        int minPackOfQty = bean_cart_data.get(finalPosition).getMinPackOfQty();
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
                                int minPackOfQty = bean_cart_data.get(finalPosition).getMinPackOfQty();
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
                                /*if (s.length() == 0) { // do your work here }
                                    String productpricce = tv_pop_sellingprice.getTag().toString();
                                    edt_count.setText("1");
                                    double t = Double.parseDouble(productpricce);
                                    String str = String.format("%.2f", t);

                                    tv_total.setText(str);
                                }*/

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
                                    int minPackOfQty = bean_cart_data.get(finalPosition).getMinPackOfQty();

                                    if (qty >= minPackOfQty && C.modOf(qty, minPackOfQty) == 0) {
                                        String productpricce = tv_pop_sellingprice.getTag().toString();
                                        amount1 = Double.parseDouble(s.toString());
                                        amount1 = Double.parseDouble(productpricce) * amount1;
                                        //float finalValue = (float)(Math.round( amount1 * 100 ) / 100);

                                        String str = String.format("%.2f", amount1);
                                        tv_total.setText(str);
                                    } else {
                                        tv_total.setText(str);
                                    }

                                }
                            }
                        });

                        buy_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (user_data.size() == 0) {

                                    Intent i = new Intent(CheckoutPage_Product.this, LogInPage.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);


                                } else {

                                    String enteredQty = edt_count.getText().toString().trim();
                                    int currentQty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);
                                    int minPackOfQty = bean_cart_data.get(finalPosition1).getMinPackOfQty();

                                    if (currentQty < minPackOfQty || C.modOf(currentQty, minPackOfQty) > 0) {

                                        C.showMinPackAlert(CheckoutPage_Product.this, minPackOfQty);
                                        //Globals.Toast(getApplicationContext(), "Please enter quantity in multiple of "+minPackOfQty+" or tap + / - button");
                                        Log.e("Mod Rem", "-->" + C.modOf(currentQty, minPackOfQty));
                                    } else {
                                        product_id = bean_cart_data.get(finalPosition1).getProduct_id();

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
                                        Log.e("Detail", jsonData);
                                        try {


                                            //System.out.println(json1);

                                            if (json1 == null
                                                    || (json1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                                Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", getLayoutInflater());
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
                                        params1.add(new BasicNameValuePair("role_id", role_id));
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
                                        Log.e("JSON", jsonData);

                                        //new check_schme().execute();
                                        try {


                                            //System.out.println(json);

                                            if (json == null
                                                    || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                                Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", getLayoutInflater());
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


                                            bean.setPro_id(bean_cart_data.get(finalPosition1).getProduct_id());
                                            //Log.e("-- ", "producttt Id " + bean_cart_data.get(position).getProduct_id());

                                            bean.setPro_code(tv_pop_code.getText().toString().trim());
                                            //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                            bean.setPro_name(tv_pop_pname.getText().toString());
                                            bean.setPro_qty(edt_count.getText().toString());
                                            bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                            bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString());
                                            bean.setPro_shortdesc(bean_cart_data.get(finalPosition1).getPack_of());


                                            bean.setPro_total(tv_total.getText().toString());
                                            bean.setPro_schme("");

                                            for (int i = 0; i < 1; i++) {
                                                try {
                                                    JSONObject jobject = new JSONObject();

                                                    jobject.put("user_id", u_id);
                                                    jobject.put("role_id", role_id);
                                                    jobject.put("owner_id", owner_id);
                                                    jobject.put("product_id", bean_cart_data.get(finalPosition1).getProduct_id());
                                                    jobject.put("category_id", bean_cart_data.get(finalPosition1).getCategory_id());
                                                    jobject.put("name", tv_pop_pname.getText().toString());
                                                    String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                    String aString = newString.trim().replace(")", "");
                                                    jobject.put("pro_code", aString.trim());
                                                    jobject.put("quantity", edt_count.getText().toString());
                                                    jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                    jobject.put("selling_price", Selling);
                                                    jobject.put("option_id", bean_cart_data.get(finalPosition1).getOption_id().toString());
                                                    jobject.put("option_name", bean_cart_data.get(finalPosition1).getOption_name().toString());
                                                    jobject.put("option_value_id", bean_cart_data.get(finalPosition1).getOption_value_id().toString());
                                                    jobject.put("option_value_name", bean_cart_data.get(finalPosition1).getOption_value_name().toString());

                                                    double am = Double.parseDouble(Selling) * Double.parseDouble(edt_count.getText().toString());
                                                    String str = String.format("%.2f", am);

                                                    jobject.put("item_total", str);
                                                    jobject.put("pro_scheme", " ");
                                                    jobject.put("pack_of", bean_cart_data.get(finalPosition1).getPack_of().toString());
                                                    jobject.put("scheme_id", " ");
                                                    jobject.put("scheme_title", " ");
                                                    jobject.put("scheme_pack_id", " ");
                                                    jobject.put("prod_img", bean_cart_data.get(finalPosition1).getProd_img().toString());


                                                    jarray_cart.put(jobject);
                                                } catch (JSONException e) {

                                                }


                                            }

                                            if (kkk == 1) {

                                                Log.e("Line", "1974");
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


                                                bean.setPro_id(bean_cart_data.get(finalPosition1).getProduct_id());
                                                //Log.e("-- ", "producttt Id " + bean_cart_data.get(position).getProduct_id());
                                                // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                                bean.setPro_cat_id(bean_cart_data.get(finalPosition1).getCategory_id().toString());

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

                                                double se = Double.parseDouble(Selling) * buyqu;

                                                double se1 = buyqu + getqu;

                                                double fse = se / se1;
                                                sell = String.format("%.2f", fse);
                                                // sell = String.valueOf(fse);

                                                int fqu = qu + (int) getqu;

                                                bean.setPro_qty(String.valueOf((int) fqu));
                                                bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                                bean.setPro_sellingprice(sell);
                                                bean.setPro_shortdesc(bean_cart_data.get(finalPosition1).getPack_of());
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
                                                        jobject.put("product_id", bean_cart_data.get(finalPosition1).getProduct_id());
                                                        jobject.put("category_id", bean_cart_data.get(finalPosition1).getCategory_id().toString());
                                                        jobject.put("name", tv_pop_pname.getText().toString());
                                                        String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                        String aString = newString.toString().trim().replace(")", "");
                                                        jobject.put("pro_code", aString.toString().trim());
                                                        jobject.put("quantity", String.valueOf((int) fqu));
                                                        jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                        jobject.put("selling_price", sell);
                                                        jobject.put("option_id", bean_cart_data.get(finalPosition1).getOption_id().toString());
                                                        jobject.put("option_name", bean_cart_data.get(finalPosition1).getOption_name().toString());
                                                        jobject.put("option_value_id", bean_cart_data.get(finalPosition1).getOption_value_id().toString());
                                                        jobject.put("option_value_name", bean_cart_data.get(finalPosition1).getOption_value_name().toString());
                                                        double f = fqu * Double.parseDouble(sell);
                                                        String str = String.format("%.2f", f);
                                                        jobject.put("item_total", str);
                                                        jobject.put("pro_scheme", " ");
                                                        jobject.put("pack_of", bean_cart_data.get(finalPosition1).getPack_of().toString());
                                                        jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                        jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                        jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                        jobject.put("prod_img", bean_cart_data.get(finalPosition1).getProd_img().toString());


                                                        jarray_cart.put(jobject);
                                                    } catch (JSONException e) {

                                                    }


                                                }

                                                if (kkk == 1) {

                                                    Log.e("Line", "2101");
                                                    new Edit_Product().execute();

                                                } else {

                                                    new Add_Product().execute();
                                                }


                                            } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("2")) {

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


                                                bean.setPro_id(bean_cart_data.get(finalPosition1).getProduct_id());
                                                //Log.e("-- ", "producttt Id " + bean_cart_data.get(position).getProduct_id());
                                                // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                                bean.setPro_cat_id(bean_cart_data.get(finalPosition1).getCategory_id().toString());
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
                                                    jobject.put("product_id", bean_cart_data.get(finalPosition1).getProduct_id());
                                                    jobject.put("category_id", bean_cart_data.get(finalPosition1).getCategory_id().toString());
                                                    jobject.put("name", tv_pop_pname.getText().toString());
                                                    String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                    String aString = newString.toString().trim().replace(")", "");
                                                    jobject.put("pro_code", aString.toString().trim());
                                                    jobject.put("quantity", edt_count.getText().toString());
                                                    jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                    jobject.put("selling_price", tv_pop_sellingprice.getText().toString());
                                                    jobject.put("option_id", bean_cart_data.get(finalPosition1).getOption_id().toString());
                                                    jobject.put("option_name", bean_cart_data.get(finalPosition1).getOption_name().toString());
                                                    jobject.put("option_value_id", bean_cart_data.get(finalPosition1).getOption_value_id().toString());
                                                    jobject.put("option_value_name", bean_cart_data.get(finalPosition1).getOption_value_name().toString());
                                                    jobject.put("item_total", tv_total.getText().toString());
                                                    jobject.put("pro_scheme", " ");
                                                    jobject.put("pack_of", bean_cart_data.get(finalPosition1).getPack_of().toString());
                                                    jobject.put("scheme_id", " ");
                                                    jobject.put("scheme_title", " ");
                                                    jobject.put("scheme_pack_id", " ");
                                                    jobject.put("prod_img", bean_cart_data.get(finalPosition1).getProd_img().toString());

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
                                                double maxqu = Double.parseDouble(maxq);
                                                int qu = Integer.parseInt(edt_count.getText().toString());

                                                double a = qu / buyqu;
                                                double b = a * getqu;
                                                if (b > maxqu) {
                                                    b = maxqu;
                                                } else {
                                                    b = b;
                                                }


                                                bean_s.setPro_qty(String.valueOf((int) b));


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
                                                bean_s.setPro_schme("" + bean_cart_data.get(finalPosition1).getProduct_id());
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
                                                    jobject.put("quantity", String.valueOf((int) b));
                                                    jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                                    jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                                    jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id().toString());
                                                    jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name().toString());
                                                    jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id().toString());
                                                    jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name().toString());
                                                    jobject.put("item_total", "0");
                                                    jobject.put("pro_scheme", "" + bean_cart_data.get(finalPosition1).getProduct_id());
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

                                                    Log.e("Line", "2335");
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


                                                bean.setPro_id(bean_cart_data.get(finalPosition1).getProduct_id());
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
                                                        jobject.put("product_id", bean_cart_data.get(finalPosition1).getProduct_id());
                                                        jobject.put("category_id", bean_cart_data.get(finalPosition1).getCategory_id());
                                                        jobject.put("name", tv_pop_pname.getText().toString());
                                                        String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                        String aString = newString.toString().trim().replace(")", "");
                                                        jobject.put("pro_code", aString.toString().trim());
                                                        jobject.put("quantity", edt_count.getText().toString());
                                                        jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                        jobject.put("selling_price", sell);
                                                        jobject.put("option_id", bean_cart_data.get(finalPosition1).getOption_id().toString());
                                                        jobject.put("option_name", bean_cart_data.get(finalPosition1).getOption_name().toString());
                                                        jobject.put("option_value_id", bean_cart_data.get(finalPosition1).getOption_value_id().toString());
                                                        jobject.put("option_value_name", bean_cart_data.get(finalPosition1).getOption_value_name().toString());
                                                        double f = Double.parseDouble(edt_count.getText().toString()) * Double.parseDouble(sell);
                                                        String str = String.format("%.2f", f);
                                                        jobject.put("item_total", str);
                                                        jobject.put("pro_scheme", " ");
                                                        jobject.put("pack_of", bean_cart_data.get(finalPosition1).getPack_of().toString());
                                                        jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                        jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                        jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                        jobject.put("prod_img", bean_cart_data.get(finalPosition1).getProd_img().toString().toString());

                                                        jarray_cart.put(jobject);
                                                    } catch (JSONException e) {

                                                    }


                                                }

                                                if (kkk == 1) {

                                                    Log.e("Line", "2481");
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
            }


            layout_productItem.addView(convertView);

        }

        float productGrandTotal = getCartGrandTotal();
        hasCouponApplied = false;
        discount_amount = "";
        layout_discount.setVisibility(View.GONE);
        coupon_id = "";
        coupon_code = "";
        coupon_name = "";


        //double t = Double.parseDouble(appPrefs.getCart_Grand_total());
        String str = String.format("%.2f", productGrandTotal);
        tv_subtotal.setText(str);
        tv_order_total.setText(str);
    }

    public class Add_Product extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        CheckoutPage_Product.this, "");

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

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            jarray_cart = new JSONArray();
            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(CheckoutPage_Product.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(CheckoutPage_Product.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                        new get_cartdata(true).execute();
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class Edit_Product extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        CheckoutPage_Product.this, "");

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

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            jarray_cart = new JSONArray();

            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(CheckoutPage_Product.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(CheckoutPage_Product.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                        new get_cartdata(true).execute();
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

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

            Log.e("PairList", pairList.toString());

            isNotDone = false;
            Globals.generateNoteOnSD(getApplicationContext(), jsonData);

            return jsonData;
        }
    }

    public class send_order_details extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        CheckoutPage_Product.this, "");
                btn_pay1.setEnabled(true);
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

                Log.e("4", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_AddOrder", ServiceHandler.POST, parameters);
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
                    Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", CheckoutPage_Product.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(CheckoutPage_Product.this, "" + Message, CheckoutPage_Product.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        /*appPrefs = new AppPrefs(CheckoutPage_Product.this);
                        appPrefs.setaddress_id("");
                        db= new DatabaseHandler(CheckoutPage_Product.this);
                        db.Delete_ALL_table();
                        db.Clear_ALL_table();
                        db.clear_cart();
                        db.close();*/

                        JSONArray payUArray = jObj.getJSONArray("payu_parameters");

                        PayuData payuData = new PayuData();

                        for (int i = 0; i < payUArray.length(); i++) {
                            JSONObject keyValue = payUArray.getJSONObject(i);

                            switch (i) {
                                case 0:
                                    payuData.setAction(keyValue.getString("value"));
                                    break;
                                case 1:
                                    payuData.setKey(keyValue.getString("value"));
                                    break;
                                case 2:
                                    payuData.setHash(keyValue.getString("value"));
                                    break;
                                case 3:
                                    payuData.setTxnid(keyValue.getString("value"));
                                    break;
                                case 4:
                                    payuData.setAmount(keyValue.getString("value"));
                                    break;
                                case 5:
                                    payuData.setProductinfo(keyValue.getString("value"));
                                    break;
                                case 6:
                                    payuData.setFirstname(keyValue.getString("value"));
                                    break;
                                case 7:
                                    payuData.setLastname(keyValue.getString("value"));
                                    break;
                                case 8:
                                    payuData.setEmail(keyValue.getString("value"));
                                    break;
                                case 9:
                                    payuData.setPhone(keyValue.getString("value"));
                                    break;
                                case 10:
                                    payuData.setAddress1(keyValue.getString("value"));
                                    break;
                                case 11:
                                    payuData.setPincode(keyValue.getString("value"));
                                    break;
                                case 12:
                                    payuData.setCity(keyValue.getString("value"));
                                    break;
                                case 13:
                                    payuData.setState(keyValue.getString("value"));
                                    break;
                                case 14:
                                    payuData.setCountry(keyValue.getString("value"));
                                    break;
                                case 15:
                                    payuData.setSurl(keyValue.getString("value"));
                                    break;
                                case 16:
                                    payuData.setFurl(keyValue.getString("value"));
                                    break;
                                case 17:
                                    payuData.setService_provider(keyValue.getString("value"));
                                    break;
                                case 18:
                                    payuData.setSubmit(keyValue.getString("value"));
                                    break;

                            }
                        }

                        Intent i = new Intent(CheckoutPage_Product.this, PayUMentActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("orderID", jObj.getString("data"));
                        i.putExtra("orderAmount", String.valueOf(jObj.getInt("amount")));
                        i.putExtra("payUData", payuData);
                        startActivity(i);

                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        /*setRefershData();

                        if(user_data.size() != 0){
                            for (int i = 0; i < user_data.size(); i++) {

                                owner_id =user_data.get(i).getUser_id().toString();

                                // role_id=user_data.get(i).getUser_type().toString();

                                if (role_id.equalsIgnoreCase("6") ) {

                                    appPrefs = new AppPrefs(CheckoutPage_Product.this);
                                    // role_id = app.getSubSalesId().toString();
                                    u_id = appPrefs.getSalesPersonId().toString();
                                }else if(role_id.equalsIgnoreCase("7")){
                                    appPrefs = new AppPrefs(CheckoutPage_Product.this);
                                    //  role_id = app.getSubSalesId().toString();
                                    u_id = appPrefs.getSalesPersonId().toString();
                                    //Log.e("IDIDD",""+appPrefs.getSalesPersonId().toString());
                                }else{
                                    u_id=owner_id;
                                }


                            }



                        }else{

                        }
                        List<NameValuePair> para=new ArrayList<NameValuePair>();

                        para.add(new BasicNameValuePair("owner_id", owner_id));
                        para.add(new BasicNameValuePair("user_id",u_id));

                        new GetCartDelete(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
*/
                        //Log.e("222222222",""+owner_id);
                        //Log.e("333333333",""+u_id);
                        //String json=GetCartDelete(para);


                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

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

            Log.e("Detail", pairList.toString());

            isNotDone = false;

            return jsonData;
        }
    }

    public class send_B2B_details extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        Custom_ProgressDialog loadingView;

        protected void onPreExecute() {
            super.onPreExecute();

            loadingView = new Custom_ProgressDialog(
                    CheckoutPage_Product.this, "");
            loadingView.setIndeterminate(false);
            loadingView.setCancelable(false);
            loadingView.show();

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
                if (!docPath1.equalsIgnoreCase(""))
                    parameters.add(new BasicNameValuePair("attachment_1", docPath1));

                Log.e("4", parameters.toString());

                //json = new ServiceHandler().makeServiceCall(GlobalVariable.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_Add_B2B_Order", ServiceHandler.POST, parameters, 2);
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

            Log.e("json", "-->" + result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", CheckoutPage_Product.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");
                    if (!date) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(CheckoutPage_Product.this, "" + Message, CheckoutPage_Product.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        appPrefs = new AppPrefs(CheckoutPage_Product.this);
                        appPrefs.setaddress_id("");
                        db = new DatabaseHandler(CheckoutPage_Product.this);
                        db.Delete_ALL_table();
                        db.Clear_ALL_table();
                        db.clear_cart();
                        db.close();

                        Intent i = new Intent(CheckoutPage_Product.this, Thankyou_Page.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("orderID", jObj.getString("data"));
                        i.putExtra("orderAmount", jObj.getString("amount"));
                        startActivity(i);

                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        /*setRefershData();

                        if(user_data.size() != 0){
                            for (int i = 0; i < user_data.size(); i++) {

                                owner_id =user_data.get(i).getUser_id().toString();

                                 role_id=user_data.get(i).getUser_type().toString();

                                if (role_id.equalsIgnoreCase("6") ) {

                                    appPrefs = new AppPrefs(CheckoutPage_Product.this);
                                     role_id = appPrefs.getSubSalesId().toString();
                                    u_id = appPrefs.getSalesPersonId().toString();
                                }else if(role_id.equalsIgnoreCase("7")){
                                    appPrefs = new AppPrefs(CheckoutPage_Product.this);
                                      role_id = appPrefs.getSubSalesId().toString();
                                    u_id = appPrefs.getSalesPersonId().toString();
                                    //Log.e("IDIDD",""+appPrefs.getSalesPersonId().toString());
                                }else{
                                    u_id=owner_id;
                                }


                            }



                        }else{

                        }
                        List<NameValuePair> para=new ArrayList<NameValuePair>();

                        para.add(new BasicNameValuePair("owner_id", owner_id));
                        para.add(new BasicNameValuePair("user_id",u_id));

                        loadingView.dismiss();

                        new GetCartDelete(para).execute();*/

                        Log.e("222222222", "" + owner_id);
                        Log.e("333333333", "" + u_id);
                        //String json=GetCartDelete(para);

                        /*try {


                            //System.out.println(json);

                            if (json==null
                                    || (json.equalsIgnoreCase(""))) {
                    *//*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*//*
                                Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", getLayoutInflater());
                                // loadingView.dismiss();

                            } else {
                                JSONObject jObj1 = new JSONObject(json);

                                String date1 = jObj.getString("status");


                                if (date1.equalsIgnoreCase("false")) {
                                    String Message1 = jObj.getString("message");
                                    Globals.CustomToast(CheckoutPage_Product.this, ""+Message1, getLayoutInflater());
                                    loadingView.dismiss();
                                    // loadingView.dismiss();
                                } else {
                                    Globals.CustomToast(CheckoutPage_Product.this, "" + Message, CheckoutPage_Product.this.getLayoutInflater());
                                    Intent i = new Intent(CheckoutPage_Product.this,Thankyou_Page.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    appPrefs = new AppPrefs(CheckoutPage_Product.this);
                                    appPrefs.setaddress_id("");
                                    db= new DatabaseHandler(CheckoutPage_Product.this);
                                    db.Delete_ALL_table();
                                    db.clear_cart();
                                    db.close();
                                    loadingView.dismiss();
                                }

                            }
                        }catch(Exception j){
                            j.printStackTrace();
                            //Log.e("json exce",j.getMessage());
                        }*/
//                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                Log.e("Exception", j.getMessage());
                j.printStackTrace();
            }

        }
    }

    public class send_B2BS_details extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        CheckoutPage_Product.this, "");

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
                if (!docPath1.equalsIgnoreCase(""))
                    parameters.add(new BasicNameValuePair("attachment_1", docPath1));

                //Log.e("4", parameters.toString());

                //json = new ServiceHandler().makeServiceCall(GlobalVariable.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_Add_B2B_Sales_Person_Order", ServiceHandler.POST, parameters, 2);
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
            Log.e("json", "-->" + result_1);
            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", CheckoutPage_Product.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");
                    if (!date) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(CheckoutPage_Product.this, "" + Message, CheckoutPage_Product.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        appPrefs = new AppPrefs(CheckoutPage_Product.this);
                        appPrefs.setaddress_id("");
                        db = new DatabaseHandler(CheckoutPage_Product.this);
                        db.Delete_ALL_table();
                        db.Clear_ALL_table();
                        db.clear_cart();
                        db.close();

                        Intent i = new Intent(CheckoutPage_Product.this, Thankyou_Page.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("orderID", jObj.getString("data"));
                        i.putExtra("orderAmount", jObj.getString("amount"));
                        startActivity(i);

                        /*setRefershData();

                        if(user_data.size() != 0){
                            for (int i = 0; i < user_data.size(); i++) {

                                owner_id =user_data.get(i).getUser_id().toString();

                                // role_id=user_data.get(i).getUser_type().toString();

                                if (role_id.equalsIgnoreCase("6") ) {

                                    appPrefs = new AppPrefs(CheckoutPage_Product.this);
                                    // role_id = app.getSubSalesId().toString();
                                    u_id = appPrefs.getSalesPersonId().toString();
                                }else if(role_id.equalsIgnoreCase("7")){
                                    appPrefs = new AppPrefs(CheckoutPage_Product.this);
                                    //  role_id = app.getSubSalesId().toString();
                                    u_id = appPrefs.getSalesPersonId().toString();
                                    //Log.e("IDIDD",""+appPrefs.getSalesPersonId().toString());
                                }else{
                                    u_id=owner_id;
                                }


                            }



                        }else{

                        }
                        List<NameValuePair> para=new ArrayList<NameValuePair>();

                        para.add(new BasicNameValuePair("owner_id", owner_id));
                        para.add(new BasicNameValuePair("user_id",u_id));
                        loadingView.dismiss();
                        new GetCartDelete(para).execute();*/

                        //Log.e("222222222",""+owner_id);
                        //Log.e("333333333",""+u_id);
                        //String json=GetCartDelete(para);

                        /*try {


                            //System.out.println(json);

                            if (json==null
                                    || (json.equalsIgnoreCase(""))) {
                    *//*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*//*
                                Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", getLayoutInflater());
                                // loadingView.dismiss();

                            } else {
                                JSONObject jObj1 = new JSONObject(json);

                                String date1 = jObj.getString("status");


                                if (date1.equalsIgnoreCase("false")) {
                                    String Message1 = jObj.getString("message");
                                    Globals.CustomToast(CheckoutPage_Product.this, ""+Message1, getLayoutInflater());
                                    // loadingView.dismiss();
                                } else {
                                    Globals.CustomToast(CheckoutPage_Product.this, "" + Message, CheckoutPage_Product.this.getLayoutInflater());
                                    Intent i = new Intent(CheckoutPage_Product.this,Thankyou_Page.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    appPrefs = new AppPrefs(CheckoutPage_Product.this);
                                    appPrefs.setaddress_id("");
                                    db= new DatabaseHandler(CheckoutPage_Product.this);
                                    db.Delete_ALL_table();
                                    db.clear_cart();
                                    db.close();

                                }

                            }
                        }catch(Exception j){
                            j.printStackTrace();
                            //Log.e("json exce",j.getMessage());
                        }

                        loadingView.dismiss();
*/
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class get_address_list extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        CheckoutPage_Product.this, "");

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
                Log.e("user_id--checkout", user_id);


                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(GlobalVariable.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Address/App_Get_Addresses", ServiceHandler.POST, parameters);
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
                    Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", CheckoutPage_Product.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        // Globals.CustomToast(CheckoutPage_Product.this, "" + Message, CheckoutPage_Product.this.getLayoutInflater());
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
                            app = new AppPrefs(CheckoutPage_Product.this);

                            bean.setAddress_id(jobject_address.getString("id"));
                            bean.setFirstName(jobject_address.getString("first_name") + " " + jobject_address.getString("last_name"));

                            app.setfi_name(jobject_address.getString("first_name"));
                            app.setl_name(jobject_address.getString("last_name"));
                            app.setemail(jobject_address.getString("email"));
                            app.setphone(jobject_address.getString("mobile"));

                            bean.setMobile(jobject_address.getString("mobile"));
                            bean.setAddress_1(jobject_address.getString("address_1"));
                            bean.setAddress_2(jobject_address.getString("address_2"));
                            bean.setAddress_3(jobject_address.getString("address_3"));
                            bean.setLandmark(jobject_address.getString("landmark"));
                            bean.setPincode(jobject_address.getString("pincode"));
                            bean.setCountry(jobject_country.getString("name"));
                            bean.setState(jobject_state.getString("name"));
                            bean.setCity(jobject_city.getString("name"));
                            bean.setDefult_add(jobject_address.getString("default_address"));

                            String final_address = "";
                            if (jobject_address.getString("first_name").equalsIgnoreCase("") || jobject_address.getString("first_name").equalsIgnoreCase("null")) {

                            } else {
                                final_address = jobject_address.getString("first_name");
                            }

                            if (jobject_address.getString("mobile").equalsIgnoreCase("") || jobject_address.getString("mobile").equalsIgnoreCase("null")) {

                            } else {
                                final_address = jobject_address.getString("mobile");
                            }

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
                            if (bean.getFirstName().equalsIgnoreCase("") && role_id.equalsIgnoreCase("2")) {
                                tv_user_name_billing.setText(user_data.get(0).getF_name() + " " + user_data.get(0).getL_name());
                                tv_user_name_delivery.setText(user_data.get(0).getF_name() + " " + user_data.get(0).getL_name());
                                tv_number_billing.setText(user_data.get(0).getPhone_no());
                                tv_number_delivery.setText(user_data.get(0).getPhone_no());
                            } else {

                                tv_user_name_billing.setText(bean.getFirstName());
                                tv_user_name_delivery.setText(bean.getFirstName());
                                tv_number_billing.setText(bean.getMobile());
                                tv_number_delivery.setText(bean.getMobile());
                            }

                            if (jobject_address.getString("default_address").equalsIgnoreCase("1")) {

                                if (bean.getFirstName().equalsIgnoreCase("") && role_id.equalsIgnoreCase("2")) {
                                    tv_user_name_billing.setText(user_data.get(0).getF_name() + " " + user_data.get(0).getL_name());
                                    tv_user_name_delivery.setText(user_data.get(0).getF_name() + " " + user_data.get(0).getL_name());
                                    tv_number_billing.setText(user_data.get(0).getPhone_no());
                                    tv_number_delivery.setText(user_data.get(0).getPhone_no());
                                } else {

                                    tv_user_name_billing.setText(bean.getFirstName());
                                    tv_user_name_delivery.setText(bean.getFirstName());
                                    tv_number_billing.setText(bean.getMobile());
                                    tv_number_delivery.setText(bean.getMobile());
                                }

                               /* tv_user_name_billing.setText(bean.getFirstName());
                                tv_user_name_delivery.setText(bean.getFirstName());
                                tv_number_billing.setText(bean.getMobile());*/
                                sales_number = bean.getMobile();
                                //Log.e("sales_mobile_number",""+sales_number);
                                // tv_number_delivery.setText(bean.getMobile());
                                tv_address_billing.setText(final_address);
                                selected_billing_address_id = jobject_address.getString("id");
                                app = new AppPrefs(CheckoutPage_Product.this);
                                tv_address_delivery.setText(final_address);
                                tv_address_delivery.setText(final_address);
                                selected_shipping_address_id = jobject_address.getString("id");


                            }

                            Log.e("ABCLKD", "" + bean.getFirstName());
                            Log.e("ssssss", "" + bean.getMobile());


                        }


                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
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
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
       /* TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(AboutUs_Activity.this);
        bean_cart_data_data =  db.Get_Product_Cart();
        db.close();*/
        /*txt.setText(bean_cart_data_data.size() + "");*/
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        txt.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        app = new AppPrefs(CheckoutPage_Product.this);
        String qun = app.getCart_QTy();

        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(CheckoutPage_Product.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                } else {
                    u_id = owner_id;
                }


            }

            List<NameValuePair> para = new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id", u_id));

            new GetCartByQty(para).execute();


        } else {
            app = new AppPrefs(CheckoutPage_Product.this);
            app.setCart_QTy("");
        }

        app = new AppPrefs(CheckoutPage_Product.this);
        String qu1 = app.getCart_QTy();
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
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CheckoutPage_Product.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                appPrefs.setCurrentPage("");
                appPrefs.setaddress_id("");

            }
        });
        /*img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTL_Cart.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });*/
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(CheckoutPage_Product.this);
                app.setUser_notification("CheckoutPage_Product");
                Intent i = new Intent(CheckoutPage_Product.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CheckoutPage_Product.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

       /* img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Notification.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });*/
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
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

    public void onBackPressed() {

        Intent i = new Intent(CheckoutPage_Product.this, BTL_Cart.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        appPrefs.setCurrentPage("");
        appPrefs.setaddress_id("");
    }

    public class CustomResultAdapter extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return bean_cart_data.size();
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

                convertView = vi.inflate(R.layout.checkout_product_list, null);

            }

            final TextView tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
            final TextView tv_product_code = (TextView) convertView.findViewById(R.id.tv_product_code);
            final TextView tv_product_attribute = (TextView) convertView.findViewById(R.id.tv_product_attribute);
            final TextView tv_pack_of = (TextView) convertView.findViewById(R.id.tv_pack_of);
            final TextView tv_product_selling_price = (TextView) convertView.findViewById(R.id.tv_product_selling_price);
            final TextView tv_product_qty = (TextView) convertView.findViewById(R.id.tv_product_qty);
            final TextView txt_scheme = (TextView) convertView.findViewById(R.id.txt_scheme);
            //  final TextView tv_total_product = (TextView) convertView.findViewById(R.id.tv_total_product);
            final TextView tv_product_total_cost = (TextView) convertView.findViewById(R.id.tv_product_total_cost);
            l_cal = (LinearLayout) convertView.findViewById(R.id.l_cal);
            l_cal_qty = (LinearLayout) convertView.findViewById(R.id.l_call_qty);
            txt_qty = (TextView) convertView.findViewById(R.id.tv_qty);


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
                l_cal.setVisibility(View.GONE);
                l_cal_qty.setVisibility(View.VISIBLE);
            } else {
                l_cal.setVisibility(View.VISIBLE);
                l_cal_qty.setVisibility(View.GONE);
            }


            tv_product_name.setText(bean_cart_data.get(position).getName());
            tv_product_code.setText(bean_cart_data.get(position).getPro_code());
            tv_pack_of.setText(bean_cart_data.get(position).getPack_of());

            if (bean_cart_data.get(position).getScheme_title().toString().equalsIgnoreCase(" ")) {
                txt_scheme.setVisibility(View.GONE);
            } else {
                txt_scheme.setVisibility(View.VISIBLE);
                txt_scheme.setText(bean_cart_data.get(position).getScheme_title().toString());
            }

            tv_product_selling_price.setText(bean_cart_data.get(position).getSelling_price());
            tv_product_qty.setText(bean_cart_data.get(position).getQuantity());
            txt_qty.setText(bean_cart_data.get(position).getQuantity());
            double t = Double.parseDouble(bean_cart_data.get(position).getItem_total());
            String str = String.format("%.2f", t);
            tv_product_total_cost.setText(getResources().getString(R.string.Rs) + "" + str);


            String[] option = bean_cart_data.get(position).getOption_name().toString().split(", ");
            String[] value = bean_cart_data.get(position).getOption_value_name().split(", ");
            String option_value = "";
            if (bean_cart_data.get(position).getOption_name().toString().equalsIgnoreCase("") || bean_cart_data.get(position).getOption_name().toString().equalsIgnoreCase("null")) {
                tv_product_attribute.setVisibility(View.GONE);
            } else {
                for (int i = 0; i < option.length; i++) {
                    if (i == 0) {
                        option_value = option[i] + ": " + value[i];
                    } else {
                        option_value = option_value + ", " + option[i] + ": " + value[i];
                    }
                }

                tv_product_attribute.setText(option_value);
            }


            return convertView;
        }
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(CheckoutPage_Product.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid = user_array_from_db.get(i).getId();
            String user_id = user_array_from_db.get(i).getUser_id();
            //Log.e("",""+user_array_from_db.get(i).getUser_id());

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
            // Toast.makeText(getApplicationContext(),""+user_id,Toast.LENGTH_SHORT).show();
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT_COMBO) {
                new get_cartdata(true).execute();
            } else if (data.getData() != null) {
                Uri originalUri = null;
                if (requestCode == SELECT_PICTURE) {
                    originalUri = data.getData();
                } else if (requestCode == SELECT_PICTURE_KITKAT) {
                    originalUri = data.getData();
                    getContentResolver().takePersistableUriPermission(originalUri, (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
                }

                if (selectedImageFor == 1)
                    txt_photo2.setText(ImagePath.getPath(getApplicationContext(), originalUri));
                /*else if(selectedImageFor==2)
                    txt_photo2.setText(ImagePath.getPath(getApplicationContext(),originalUri));*/
            }
        }
    }

    public class CheckCouponData extends AsyncTask<Void, Void, String> {

        Activity activity;
        String couponID;
        Custom_ProgressDialog dialog;

        public CheckCouponData(Activity activity, String couponID) {
            dialog = new Custom_ProgressDialog(activity, "");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();
            this.activity = activity;
            this.couponID = couponID;
        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("coupon_code", couponID));
            parameters.add(new BasicNameValuePair("user_id", appPrefs.getUserId()));
            parameters.add(new BasicNameValuePair("grand_total", String.format(Locale.getDefault(),"%.2f",getCartGrandTotal())));
            json = new ServiceHandler().makeServiceCall(Globals.server_link + "Coupon/App_get_coupon_data", ServiceHandler.POST, parameters);
            //String json=new ServiceHandler().makeServiceCall(GlobalVariable.link+GlobalVariable.API_CHECK_COUPONCODE,ServiceHandler.POST,parameters);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            //Log.e("JSon Coupon",json);

            try {
                JSONObject object = new JSONObject(json);

                if (object.getBoolean("status")) {
                    JSONObject data = object.getJSONObject("data");
                    JSONObject coupon = data.getJSONObject("Coupon");

                    String coupon_id1 = coupon.getString("id");
                    String name = coupon.getString("name");
                    String code = coupon.getString("code");
                    String type = coupon.getString("type");
                    String discount = coupon.getString("discount");
                    String total = coupon.getString("total");
                    String date_start = coupon.getString("date_start");
                    String date_end = coupon.getString("date_end");
                    String uses_total = coupon.getString("uses_total");
                    String total_used_coupon = coupon.getString("total_used_coupon");
                    String max_discount = coupon.getString("max_discount");

                    //String grand_total = appPrefs.getCart_Grand_total();

                    double grandTotal = getCartGrandTotal();
                    //Log.e("totals :",max_discount+" "+grandTotal);

//                    int discountPercent=Integer.parseInt(discount);
//
//
//                    int toCutAmount=(int) (Math.ceil(grandTotal*discountPercent/100));
//                    //Log.e("toCutAmount",toCutAmount+"");
//
//                    int minValue=getMinimumValue(toCutAmount,Integer.parseInt(max_discount));
//
//                    //Log.e("minValue",minValue+"");
//
//                    int toSetGrandTotal=(int) (grandTotal-minValue);
//
//                    layout_discount.setVisibility(View.VISIBLE);
//                    tv_disctotal.setText(String.format("%.2f",(float) minValue));
//
//                    tv_order_total.setText(String.format("%.2f",(float) toSetGrandTotal));

                    double toCutAmount = 0.00f;

                    if (type.equalsIgnoreCase("P")) {
                        int discountPercent = Integer.parseInt(discount);

                        toCutAmount = (grandTotal * discountPercent / 100);
                        //Log.e("toCutAmount",toCutAmount+"");
                    } else if (type.equalsIgnoreCase("F")) {
                        toCutAmount = Double.parseDouble(discount);
                        //Log.e("toCutAmount",toCutAmount+"");
                    }

                    double minValue = getMinimumValue(toCutAmount, Double.parseDouble(max_discount));

                    //Log.e("minValue",minValue+"");

                    double toSetGrandTotal = (grandTotal - minValue);

                    layout_discount.setVisibility(View.VISIBLE);
                    tv_disctotal.setText(String.format("%.2f",minValue));

                    tv_order_total.setText(String.format(Locale.ENGLISH, "%.2f", toSetGrandTotal));
                    hasCouponApplied = true;
                    coupon_id = coupon_id1;
                    coupon_code = code;
                    coupon_name = name;
                    discount_amount = String.valueOf(minValue);
                } else {
                    Globals.CustomToast(CheckoutPage_Product.this, object.getString("message"), getLayoutInflater());
                    //C.Toast(activity.getApplicationContext(),object.getString("message"),getLayoutInflater());
                }

            } catch (JSONException j) {
                //Log.e("JSON Error",j.getMessage());
            } finally {
                dialog.dismiss();
            }
        }
    }

    public double getMinimumValue(double value1, double value2) {
        if (value1 < value2)
            return value1;
        else if (value2 < value1)
            return value2;

        return value1;
    }

    private void showDetailInfoDialog() {

        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.popup_bank_details_checkout, null);
        dialogBuilder.setView(dialogView);
        //final Bean_BankDetails bankPerson = new Bean_BankDetails();


        titleName = (TextView) dialogView.findViewById(R.id.txt_title);
        bankName = (TextView) dialogView.findViewById(R.id.txt_bank);
        acNo = (TextView) dialogView.findViewById(R.id.txt_account);
        ifsCode = (TextView) dialogView.findViewById(R.id.txt_code);
        branchName = (TextView) dialogView.findViewById(R.id.txt_branch);
        branchCode = (TextView) dialogView.findViewById(R.id.txt_branchcode);
        final ImageView cancel = (ImageView) dialogView.findViewById(R.id.btn_cancel);

        //new GetBankInformation().execute();


        titleName.setText("Your order has been received and will be processed once payment has been confirmed. ");
        bankName.setText(bankDetailsList.get(0).getBankName());
        acNo.setText(bankDetailsList.get(0).getAccountNo());
        ifsCode.setText(bankDetailsList.get(0).getIfsCode());
        branchName.setText(bankDetailsList.get(0).getBranchName());
        branchCode.setText(bankDetailsList.get(0).getBranchCode());
     /*   titleName.setText(bankPerson.getTitleLine());
        bankName.setText(bankPerson.getBankName());
        acNo.setText(bankPerson.getAccountNo());
        ifsCode.setText(bankPerson.getIfsCode());
        branchName.setText(bankPerson.getBranchName());
        branchCode.setText(bankPerson.getBranchCode());*/


        //new GetDisRetInformation(activity,disUserID).execute();


        final android.app.AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), CheckoutPage_Product.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
                b.dismiss();


            }
        });

        b.show();


    }

    public class GetBankInformation extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        //String user_id;
        String jsonData = "";

//        public GetBankInformation(Activity activity,String user_id){
//            this.activity=activity;
//            this.user_id=user_id;;
//        }


        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(activity, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                //parameters.add(new BasicNameValuePair("user_id",user_id));

                ////Log.e("distributor id", "" + distributor_id+"");
                //Log.e("user id", "" + user_id + "");
                ////Log.e("role id", "" + role_id+"");


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "TallyCompany/App_BankDetails", ServiceHandler.POST, parameters);

                //System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            jsonPerson = jsonData;

            if (!jsonData.equalsIgnoreCase("")) {
                try {
                    JSONObject mainObject = new JSONObject(jsonData);
                    if (mainObject.getBoolean("status")) {
                        JSONArray dataArray = mainObject.getJSONArray("data");

                        bankDetailsList.clear();

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject subObject = dataArray.getJSONObject(i);
                            //JSONObject orderReason = subObject.getJSONObject("OrderReason");

                            Bean_BankDetails bank_PERSON = new Bean_BankDetails();

                            //bank_PERSON.setTitleLine(subObject.getString("id"));
                            bank_PERSON.setBankName(subObject.getString("bank_name"));
                            //Log.e("BANK NAME", "" + subObject.getString("bank_name"));
                            bank_PERSON.setAccountNo(subObject.getString("account_no"));
                            //Log.e("ACCOUNT NO", "" + subObject.getString("account_no"));
                            bank_PERSON.setIfsCode(subObject.getString("ifs_code"));
                            //Log.e("IFS CODE", "" + subObject.getString("ifs_code"));
                            bank_PERSON.setBranchName(subObject.getString("branch"));
                            //Log.e("BRANCH", "" + subObject.getString("branch"));
                            bank_PERSON.setBranchCode(subObject.getString("branch_code"));
                            //Log.e("BRANCH CODE", "" + subObject.getString("branch_code"));


                            bankDetailsList.add(bank_PERSON);
                        }


                        //skipListAdapter.notifyDataSetChanged();
                        //Log.e("Size", bankDetailsList.size() + "");
                    } else {

                        Globals.CustomToast(CheckoutPage_Product.this, "" + mainObject.getString("message").toString(), getLayoutInflater());
                        loadingView.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    //loadingView.dismiss();
                }
            }

            // loadingView.dismiss();
            showDetailInfoDialog();
        }
    }

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

    public class Generate_OTP extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        String subSales_id;
        String user_ID;
        String jsonData = "";
        //int position;

        public Generate_OTP(Activity activity, String user_ID, String subSales_id) {
            this.activity = activity;
            this.user_ID = user_ID;
            this.subSales_id = subSales_id;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        CheckoutPage_Product.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                //Log.e("77777777","77777777");

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("receiver_id", user_ID));
                parameters.add(new BasicNameValuePair("sender_id", subSales_id));
                parameters.add(new BasicNameValuePair("mobile_no", phone));


                //Log.e("","" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/APP_Generate_OTP", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
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

            //Log.e("ttttttttt","ttttt");
            super.onPostExecute(result_1);

//            try {
            try {

                // JSONObject jObjj = new JSONObject(result_1);
                JSONObject j = new JSONObject(result_1);
                Boolean date = j.getBoolean("status");

                if (date == false) {
                    Globals.CustomToast(CheckoutPage_Product.this, "" + j.getString("message").toString(), getLayoutInflater());
                    loadingView.dismiss();
                } else {

                    JSONObject dataObj = j.getJSONObject("data");
                    //Log.e("rrrrrrrrr", "nhgjhgj");
                    appPrefs = new AppPrefs(getApplicationContext());
                    appPrefs.setREF_No(dataObj.getString("reference_no"));
                    store_ref = appPrefs.getREF_No();

                    //Log.e("uuuuu",""+store_ref);


//                    store_reference = appPrefs.getReference_no();
//                    //Log.e("rrrrrrrrr", "" + store_reference);
                    //Toast.makeText(CheckoutPage_Product.this,""+Message,Toast.LENGTH_LONG).show();
                    Globals.CustomToast(CheckoutPage_Product.this, "" + j.getString("message").toString(), getLayoutInflater());
                    loadingView.dismiss();
                    edt_otp.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.VISIBLE);
                    txt_otp.setVisibility(View.VISIBLE);

                    edt_mobile.setEnabled(false);
                    btn_otp.setVisibility(View.GONE);

                    loadingView.dismiss();


                }
            }


//                //System.out.println(result_1);
//
//                if (result_1.equalsIgnoreCase("")
//                        || (result_1.equalsIgnoreCase(""))) {
//                    Toast.makeText(CheckoutPage_Product.this,"SERVER ERRER",Toast.LENGTH_LONG).show();
//                    //Global.CustomToast(Activity_Login.this, "SERVER ERRER", getLayoutInflater());
//                    loadingView.dismiss();
//
//                } else {
//                    JSONObject jObj = new JSONObject(result_1);
//
//                    String date = jObj.getString("status");
//                    if (date.equalsIgnoreCase("false")) {
//                        String Message = jObj.getString("message");
//                        Toast.makeText(CheckoutPage_Product.this,""+Message,Toast.LENGTH_LONG).show();
//                        // Global.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
//                        loadingView.dismiss();
//                    } else {
//
//
//                        String VERIFY = jObj.getString("is_verified");
//                        String Message = jObj.getString("message");
//
//                        if(VERIFY.equalsIgnoreCase("1")){
//                            Toast.makeText(CheckoutPage_Product.this,""+Message,Toast.LENGTH_LONG).show();
//                            // Global.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
//                            loadingView.dismiss();
//
//                            appPrefs = new AppPrefs(CheckoutPage_Product.this);
//                            appPrefs.setis_verify("1");
//                            Intent i = new Intent(CheckoutPage_Product.this, Thankyou_Page.class);
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(i);
//                            finish();
//                        }else if(VERIFY.equalsIgnoreCase("0")){
//                            Toast.makeText(CheckoutPage_Product.this,""+Message,Toast.LENGTH_LONG).show();
//                            //Global.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
//                            loadingView.dismiss();
//                            edt_otp.setVisibility(View.VISIBLE);
//                            btn_submit.setVisibility(View.VISIBLE);
//
//                            edt_mobile.setEnabled(false);
//                            //edt_phone.setEnabled(false);
//                        }
//
//
//                    }
//                }
//            }

            catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class check_otp extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        String ref_no;
        String otp_no;
        String jsonData = "";
        //int position;

        public check_otp(Activity activity, String ref_no, String otp_no) {
            this.activity = activity;
            this.ref_no = ref_no;
            this.otp_no = otp_no;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        CheckoutPage_Product.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //parameters.add(new BasicNameValuePair("email", email));
                appPrefs = new AppPrefs(CheckoutPage_Product.this);
                parameters.add(new BasicNameValuePair("reference_no", ref_no));
                // //Log.e("3333333331111",""+appPrefs.getREF_No());
                parameters.add(new BasicNameValuePair("otp", otp_no));

                //Log.e("","" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/APP_Verify_OTP", ServiceHandler.POST, parameters);
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
                    Toast.makeText(CheckoutPage_Product.this, "SERVER ERRER", Toast.LENGTH_LONG).show();
                    //Global.CustomToast(Activity_Login.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();
                    isClicked = false;

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    Boolean date = jObj.getBoolean("status");
                    if (!date) {
                        String Message = jObj.getString("message");
                        isClicked = false;
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        //Toast.makeText(CheckoutPage_Product.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(CheckoutPage_Product.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Toast.makeText(CheckoutPage_Product.this, "" + Message, Toast.LENGTH_LONG).show();
                        loadingView.dismiss();
                        appPrefs = new AppPrefs(CheckoutPage_Product.this);
                        //appPrefs.setis_verify("1");

                        // Dialog for Checkout popup


                        try {
                            JSONObject jobject_OrderUserData = new JSONObject();
                            //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                            jobject_OrderUserData.put("user_id", user_id);
                            jobject_OrderUserData.put("billing_address_id", selected_billing_address_id);
                            jobject_OrderUserData.put("shipping_address_id", selected_shipping_address_id);
                            jobject_OrderUserData.put("latitude", lat);
                            jobject_OrderUserData.put("longitude", longt);
                            jobject_OrderUserData.put("owner_id", user_data.get(0).getUser_id());
                            jobject_OrderUserData.put("order_owner_role_id", role_id);
                            jobject_OrderUserData.put("place_order_person_role_id", user_data.get(0).getUser_type());

                            jarray_OrderUserData.put(jobject_OrderUserData);
                        } catch (JSONException e) {

                        }

                        for (int i = 0; i < bean_cart_data.size(); i++) {
                            try {

                                product_id = bean_cart_data.get(i).getPro_scheme();


                                JSONObject jobject = new JSONObject();

                                if (bean_cart_data.get(i).isComboPack()) {
                                    JSONArray productArray = new JSONArray(bean_cart_data.get(i).getComboCart().getProducts());

                                    Log.e("ProductArray", productArray.toString());

                                    for (int j = 0; j < productArray.length(); j++) {
                                        JSONObject product = productArray.getJSONObject(j);
                                        jarray_OrderProductData.put(product);
                                    }
                                } else {
                                    jobject.put("pro_id", bean_cart_data.get(i).getProduct_id());
                                    jobject.put("pro_cat_id", bean_cart_data.get(i).getCategory_id());
                                    String newString = bean_cart_data.get(i).getPro_code().replace("(", "");
                                    //Log.e("avbcdf",""+newString);
                                    String newStr = newString.replace(")", "");
                                    //Log.e("avbcdf",""+newStr);
                                    jobject.put("pro_code", newStr);
                                    jobject.put("pro_name", bean_cart_data.get(i).getName());
                                    jobject.put("pro_qty", bean_cart_data.get(i).getQuantity());
                                    jobject.put("pro_mrp", bean_cart_data.get(i).getMrp());
                                    jobject.put("pro_sellingprice", bean_cart_data.get(i).getSelling_price());
                                    jobject.put("pro_Option_id", bean_cart_data.get(i).getOption_id());
                                    jobject.put("pro_Option_name", bean_cart_data.get(i).getOption_name());
                                    jobject.put("pro_Option_value_id", bean_cart_data.get(i).getOption_value_id());
                                    jobject.put("pro_Option_value_name", bean_cart_data.get(i).getOption_value_name());
                                    jobject.put("pro_total", bean_cart_data.get(i).getItem_total());
                                    jobject.put("pack_of", bean_cart_data.get(i).getPack_of());
                                    //Log.e("sTRSSS",""+strr);
                                    jobject.put("pro_scheme", bean_cart_data.get(i).getScheme_id());
                                    jarray_OrderProductData.put(jobject);
                                }

                            } catch (JSONException e) {

                            }
                        }


                        try {
                            JSONObject jobject_OrderTotalData = new JSONObject();
                            //jobject_OrderUserData.put("user_id", user_array_from_db.get(0).getUser_id());
                            jobject_OrderTotalData.put("grandtotal", tv_order_total.getText().toString());
                            jobject_OrderTotalData.put("comment", comm);
                            jobject_OrderTotalData.put("transportation", transportationText);
                            transportationText = "";

                            if (hasCouponApplied) {
                                jobject_OrderTotalData.put("coupon_code", coupon_code);
                                jobject_OrderTotalData.put("coupon_id", coupon_id);
                                jobject_OrderTotalData.put("discount_amount", discount_amount);
                                jobject_OrderTotalData.put("coupon_name", coupon_name);
                            }
                            jarray_OrderTotalData.put(jobject_OrderTotalData);
                        } catch (JSONException e) {

                        }
                        docPath1 = txt_photo2.getText().toString();
                        //Log.e("12121212",""+docPath1);
                        File file1 = new File(docPath1);
                        long length1 = file1.length() / 1024;
                        if (!docPath1.equalsIgnoreCase("") && length1 == 0 || length1 > 2560) {
                            //Log.e("docpath1 size",length1+"");
                            Globals.CustomToast(getApplicationContext(), "Please Select Max 2.5 MB Image in Attachment", getLayoutInflater());
                        } else {
                            // dialog1.dismiss();
                            new send_B2B_details().execute();
                        }


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                // dialog1.dismiss();

            }

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public class get_cartdata extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        boolean afterEditing = false;

        public get_cartdata() {
        }

        public get_cartdata(boolean afterEditing) {
            this.afterEditing = afterEditing;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        CheckoutPage_Product.this, "");

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
                //Log.e("owner_id", "" + owner_id);

                //Log.e("", "" + parameters);

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
            bean_cart_data.clear();
            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(CheckoutPage_Product.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {


                        JSONObject jobj = new JSONObject(result_1);


                        JSONArray jsonArray = jObj.optJSONArray("data");
                        JSONArray comboArray = jObj.optJSONArray("combo_cart");
                        minOrderValue = Integer.parseInt(jObj.getString("minimum_order_value"));


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
                            bean.setProd_img(jsonObject.getString("prod_img"));
                            String minPackOfQty = jsonObject.getString("pack_of_qty");
                            bean.setMinPackOfQty(Integer.parseInt(minPackOfQty.isEmpty() ? "1" : minPackOfQty));

                            bean_cart_data.add(bean);

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
                        if (afterEditing) showProductItems();
                        else setdata();
                    }

                }
            } catch (Exception j) {
                loadingView.dismiss();
                j.printStackTrace();
            }
            loadingView.dismiss();

        }
    }

    private class GetCartDelete extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();
        Custom_ProgressDialog dialog;

        public GetCartDelete(List<NameValuePair> pairList) {
            this.pairList = pairList;
            dialog = new Custom_ProgressDialog(CheckoutPage_Product.this, "");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_DeleteCart", ServiceHandler.POST, pairList);
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            try {


                //System.out.println(json);

                if (json == null
                        || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date1 = jObj.getString("status");


                    if (date1.equalsIgnoreCase("false")) {
                        //String Message1 = jObj.getString("message");
                        //Globals.CustomToast(CheckoutPage_Product.this, ""+Message1, getLayoutInflater());
                        // loadingView.dismiss();
                    } else {
                        //Globals.CustomToast(CheckoutPage_Product.this, "" + jObj.getString("message"), CheckoutPage_Product.this.getLayoutInflater());
                        Intent i = new Intent(CheckoutPage_Product.this, Thankyou_Page.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        appPrefs = new AppPrefs(CheckoutPage_Product.this);
                        appPrefs.setaddress_id("");
                        db = new DatabaseHandler(CheckoutPage_Product.this);
                        db.Delete_ALL_table();
                        db.clear_cart();
                        db.close();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            dialog.dismiss();
        }
    }

    public String GetCartDelete(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_DeleteCart", ServiceHandler.POST, params);

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

                    Globals.CustomToast(CheckoutPage_Product.this, "SERVER ERROR", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(CheckoutPage_Product.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(CheckoutPage_Product.this);
                        app.setCart_QTy("" + qu);


                    }

                }


            } catch (Exception j) {
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = app.getCart_QTy();
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

