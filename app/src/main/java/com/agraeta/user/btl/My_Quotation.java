package com.agraeta.user.btl;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import com.agraeta.user.btl.adapters.QuotationListAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class My_Quotation extends AppCompatActivity implements QuotationListAdapter.QuotationListener {

    private static final int REQUEST_ADD_BTL_PRODUCT = 12;
    private static final int REQUEST_SEND_QUOTATION = 15;
    AppPrefs app;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_Quotation> bean_quotation = new ArrayList<Bean_Quotation>();
    ArrayList<Bean_Quotation> bean_quo = new ArrayList<Bean_Quotation>();
    DatabaseHandler db;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ImageView minuss, plus;
    Button btn_quotation;
    ListView lst_quotation;
    TextView txt_quotation;
    CustomResultAdapter adapter;
    LinearLayout l_mrp, l_sell, l_total;
    double amount1;
    String Selling = new String();
    int s = 0;
    Dialog dialog;
    String option_value = "";
    TextView tv_pop_pname, tv_pop_code, tv_pop_packof, tv_pop_mrp, tv_pop_sellingprice, tv_total, tv_txt_mrp;
    int flag = 0;
    EditText pp_name, pp_code, pp_uprice, pp_qty;
    TextView pp_total;
    EditText et_title, et_toname, et_email, et_cemail;
    JSONArray jarray_Quotation = new JSONArray();
    String user_id_mai = new String();
    TextView txt_selling_price, txt_total_txt;
    String role_id_main = new String();
    String email_main = new String();
    String emailcc = new String();
    String title_main = new String();
    String toname = new String();
    Custom_ProgressDialog loadingView;
    String owner_id = new String();
    String u_id = new String();
    ArrayList<Bean_Schme_value> bean_schme = new ArrayList<Bean_Schme_value>();
    String role_id = new String();
    ArrayList<Bean_Cart_Data> bean_cart_data = new ArrayList<Bean_Cart_Data>();
    String json;
    ArrayList<Bean_Product> bean_product_schme = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductOprtion> bean_Oprtions = new ArrayList<Bean_ProductOprtion>();
    String product_id = new String();
    Button buy_cart, cancel;
    int kkk = 0;
    String qun = new String();
    EditText edt_count;
    String role = new String();
    String cartJSON = "";
    boolean hasCartCallFinish = true;

    TextView txt;

    String quotationID = "";

    FloatingActionButton fab_addProduct;
    QuotationListAdapter listAdapter;
    ImageView img_deleteQuotation;
    private String user_id_main="";
    private boolean isNotDone=false;
    private String jsonData="";

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
        setContentView(R.layout.activity_my_quotation);

        Intent intent = getIntent();
        quotationID = intent.getStringExtra("quotationID");

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setRefershData();
        setActionBar();
        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(My_Quotation.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                } else {
                    u_id = owner_id;
                }


            }

        } else {
            u_id = "";
        }
        new get_cartdata().execute();
        // bean_cart =  db.Get_Product_Cart_qu();


    }

    private void setdata() {
        setRefershData();
        fetchId();
    }

    private void fetchId() {

        img_deleteQuotation=(ImageView) findViewById(R.id.img_deleteQuotation);
        btn_quotation = (Button) findViewById(R.id.send_quotation);
        lst_quotation = (ListView) findViewById(R.id.list_quatation);
        txt_quotation = (TextView) findViewById(R.id.txt_quotation);
        fab_addProduct = (FloatingActionButton) findViewById(R.id.fab_addProduct);

        listAdapter = new QuotationListAdapter(bean_cart_data, this, this);
        lst_quotation.setAdapter(listAdapter);

        fab_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(My_Quotation.this, fab_addProduct);
                menu.getMenuInflater().inflate(R.menu.menu_add_quotation, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_addBTLProduct) {
                            Intent intent=new Intent(getApplicationContext(),ProductSearchForQuotationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("quotationID",quotationID);
                            startActivityForResult(intent,REQUEST_ADD_BTL_PRODUCT);
                        } else if (item.getItemId() == R.id.menu_addOtherProduct) {
                            Intent intent=new Intent(getApplicationContext(),AddOtherProductForQuotation.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("quotationID",quotationID);
                            startActivityForResult(intent,REQUEST_ADD_BTL_PRODUCT);
                        }
                        return true;
                    }
                });
                menu.show();
            }
        });

        img_deleteQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteWholeQuotation().execute();
            }
        });

        btn_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float subTotalPrice=0;

                for(int i=0; i<bean_cart_data.size(); i++){
                    String totalPrice=bean_cart_data.get(i).getItem_total();
                    totalPrice = totalPrice.isEmpty() ? "0" : totalPrice;
                    float itemTotal=Float.parseFloat(totalPrice);

                    subTotalPrice += itemTotal;
                }

                Intent intent=new Intent(getApplicationContext(),SendQuotationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("subTotal",String.format(Locale.getDefault(),"%.2f",subTotalPrice));
                intent.putExtra("quotationID",quotationID);
                startActivityForResult(intent,REQUEST_SEND_QUOTATION);
            }
            /*{

                if(bean_cart_data.size()>0){
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;


                    dialog = new Dialog(My_Quotation.this);
                    dialog.getWindow();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.send_quotation_layout);
                    dialog.getWindow().setLayout((6 * width) / 7, (5 * height) / 7);
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                    et_title = (EditText) dialog.findViewById(R.id.et_title);
                    et_toname = (EditText) dialog.findViewById(R.id.et_toname);
                    et_email = (EditText) dialog.findViewById(R.id.et_email);
                    et_cemail = (EditText) dialog.findViewById(R.id.et_ccemail);
                    Button btn_sumbit = (Button) dialog.findViewById(R.id.btn_submit);
                    Button btn_Resett = (Button) dialog.findViewById(R.id.btn_Resettt);
                    ImageView btn_cancel = (ImageView) dialog.findViewById(R.id.btn_cancel);

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    btn_Resett.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            et_title.setText("");
                            et_toname.setText("");
                            et_email.setText("");
                            et_cemail.setText("");
                        }
                    });

                    btn_sumbit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (et_title.getText().toString().equalsIgnoreCase("")) {
                                Globals.CustomToast(My_Quotation.this, "Please Enter Title", getLayoutInflater());
                                et_title.requestFocus();
                            } else if (et_toname.getText().toString().equalsIgnoreCase("")) {
                                Globals.CustomToast(My_Quotation.this, "Please Enter To Name", getLayoutInflater());
                                et_toname.requestFocus();
                            } else if (et_email.getText().toString().equalsIgnoreCase("")) {
                                Globals.CustomToast(My_Quotation.this, "Please Enter Email Id", getLayoutInflater());
                                et_email.requestFocus();
                            } else if (validateEmail1(et_email.getText().toString()) != true) {
                                // Toast.makeText(Business_Registration.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                                Globals.CustomToast(My_Quotation.this, "Please enter a valid email address", getLayoutInflater());
                                et_email.requestFocus();
                            } else if (validateEmail1(et_cemail.getText().toString()) != true && !et_cemail.getText().toString().equalsIgnoreCase("")) {
                                et_cemail.requestFocus();
                                // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                                Globals.CustomToast(My_Quotation.this, "Please enter a valid CC email addresst", getLayoutInflater());
                            } else {
                                setRefershData();
                                for (int i = 0; i < user_data.size(); i++) {

                                    role_id_main = user_data.get(i).getUser_type().toString();
                                    user_id_mai = user_data.get(i).getUser_id().toString();
                                }


                                email_main = et_email.getText().toString();
                                emailcc = et_cemail.getText().toString();
                                title_main = et_title.getText().toString();
                                toname = et_toname.getText().toString();


                                for (int i = 0; i < bean_quotation.size(); i++) {
                                    try {
                                        JSONObject jobject = new JSONObject();

                                   *//* String[] option = bean_quotation.get(i).getQU_pro_Option_name().toString().split(", ");
                                    String[] value = bean_quotation.get(i).getQU_pro_Option_value_name().split(", ");

                                    //Log.e("133",""+bean_quotation.get(i).getQU_pro_Option_name().toString());
                                    for (int i1 = 0; i1 < option.length; i1++) {
                                        if (i1 == 0) {
                                            //Log.e("" + option, "" + option[i]);
                                            option_value = option[i1] + ": " + value[i1];
                                        } else {
                                            //Log.e("" + option, "" + option[i1]);
                                            option_value = option_value + ", " + option[i1] + ": " + value[i1];
                                        }
                                    }*//*
                                        String s = bean_quotation.get(i).getQU_PRO_NAME() + "" + bean_quotation.get(i).getQU_PRO_CODE();


                                        jobject.put("pro_name", s);
                                        jobject.put("pro_qty", bean_quotation.get(i).getQU_PRO_QTY());
                                        jobject.put("pro_sellingprice", bean_quotation.get(i).getQU_PRO_SELLING_PRICE());
                                        jobject.put("pro_per", bean_quotation.get(i).getQU_pro_Option_id());
                                        jobject.put("pro_total", bean_quotation.get(i).getQU_PRO_TOTAL());

                                        jarray_Quotation.put(jobject);
                                    } catch (JSONException e) {

                                    }


                                }
                                new send_quotation().execute();
                            }
                        }
                    });

                    dialog.show();
                }
                else {
                    Globals.Toast2(getApplicationContext(),"Please Add Product in Quotation");
                }



            }*/
        });

        if (bean_cart_data.size() == 0) {
            lst_quotation.setVisibility(View.GONE);
            txt_quotation.setVisibility(View.VISIBLE);
        } else {
            lst_quotation.setVisibility(View.VISIBLE);
            txt_quotation.setVisibility(View.GONE);
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
        img_cart.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);

        img_notification.setVisibility(View.GONE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        txt.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        app = new AppPrefs(My_Quotation.this);
        String qun = app.getCart_QTy();

        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(My_Quotation.this);
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
            app = new AppPrefs(My_Quotation.this);
            app.setCart_QTy("");
        }

        app = new AppPrefs(My_Quotation.this);
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
                onBackPressed();

            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(My_Quotation.this);
                app.setUser_notification("My_Quotation");
                Intent i = new Intent(My_Quotation.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(My_Quotation.this);
                db.Delete_Quotation();
                Intent i = new Intent(My_Quotation.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onQuantityEdit(final int position) {
        final JSONArray jarray_cart=new JSONArray();
        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(this);
                    role_id = app.getSubSalesId();
                    u_id = app.getSalesPersonId();
                    user_id_main=u_id;
                } else {
                    u_id = owner_id;
                    user_id_main=u_id;
                }


            }

        } else {
            user_id_main = "";
        }
        // product_id = bean_cart_data.get(position).getProduct_id();
        List<NameValuePair> para = new ArrayList<NameValuePair>();
        para.add(new BasicNameValuePair("product_id", bean_cart_data.get(position).getProduct_id()));
        para.add(new BasicNameValuePair("owner_id", owner_id));
        para.add(new BasicNameValuePair("user_id", u_id));

        Globals.generateNoteOnSD(getApplicationContext(), "1149 -> " + para.toString());

        isNotDone = true;
        qun=bean_cart_data.get(position).getQuantity();

        dialog = new Dialog(this);
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

        buy_cart.setText("Add to Quotation");

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
        tv_pop_pname.setText(bean_cart_data.get(position).getName());
        tv_pop_code.setText(" ( " + bean_cart_data.get(position).getPro_code() + " )");
        tv_pop_packof.setText(bean_cart_data.get(position).getPack_of());
        tv_pop_mrp.setText(bean_cart_data.get(position).getMrp());
        tv_pop_sellingprice.setText(bean_cart_data.get(position).getSelling_price());
        tv_pop_sellingprice.setTag(bean_cart_data.get(position).getSelling_price());
        l_mrp = (LinearLayout) dialog.findViewById(R.id.l_mrp);
        l_sell = (LinearLayout) dialog.findViewById(R.id.l_sell);
        l_total = (LinearLayout) dialog.findViewById(R.id.l_total);
        txt_selling_price = (TextView) dialog.findViewById(R.id.txt_selling_price);
        txt_total_txt = (TextView) dialog.findViewById(R.id.txt_total_txt);
        tv_total.setText(bean_cart_data.get(position).getSelling_price());
        //  l_view_spinner.setVisibility(View.GONE);
        //  tv_total.setText(bean_product1.get(position).getPro_sellingprice().toString());
        final int minPackOfQty = bean_cart_data.get(position).getMinPackOfQty();

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
        double temp_total = Double.parseDouble(tv_total.getText().toString());
        double t = Double.parseDouble(tv_total.getText().toString()) * Double.parseDouble(edt_count.getText().toString());
        //float t = Float.parseFloat(productpricce);
        String str = String.format("%.2f", t);
        tv_total.setText(str);
        int temp_count = Integer.parseInt(edt_count.getText().toString());

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
            }
        });
        String c1 = edt_count.getText().toString();
        s = Integer.parseInt(c1);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c1 = edt_count.getText().toString();
                c1 = c1.isEmpty() ? "0" : c1;
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


                String str = String.format("%.2f", total);
                tv_total.setText(str);
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

                    if (qty >= minPackOfQty && C.modOf(qty, minPackOfQty) == 0) {
                        String productpricce = tv_pop_sellingprice.getTag().toString();
                        amount1 = Double.parseDouble(s.toString());
                        amount1 = Double.parseDouble(productpricce) * amount1;
                        //float finalValue = (float)(Math.round( amount1 * 100 ) / 100);

                        String str = String.format("%.2f", amount1);
                        tv_total.setText(str);
                    } else {
                        tv_total.setText("");
                    }


                }
            }
        });

        buy_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_data.size() == 0) {

                    Intent i = new Intent(My_Quotation.this, LogInPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);


                } else {

                    String enteredQty = edt_count.getText().toString().trim();
                    int currentQty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);
                    int minPackOfQty = bean_cart_data.get(position).getMinPackOfQty();

                    if (currentQty < minPackOfQty || C.modOf(currentQty, minPackOfQty) > 0) {

                        C.showMinPackAlert(My_Quotation.this, minPackOfQty);
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
                                Globals.CustomToast(My_Quotation.this, "SERVER ERROR", getLayoutInflater());
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

                        if(Selling==null || Selling.isEmpty()){
                            Selling=bean_cart_data.get(position).getSelling_price();
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

                        //new check_schme().execute();
                        try {


                            //System.out.println(json);

                            if (json == null
                                    || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                Globals.CustomToast(My_Quotation.this, "SERVER ERRER", getLayoutInflater());
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

                                Log.e("Line", "1821");
                                new Edit_Product(jarray_cart.toString()).execute();


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


                                    new Edit_Product(jarray_cart.toString()).execute();


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

                                double a = qu / buyqu;
                                double b = a * getqu;
                                /*if (b > maxqu) {
                                    b = maxqu;
                                } else {
                                    b = b;
                                }*/


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
                                    jobject.put("quantity", String.valueOf((int) b));
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



                                    new Edit_Product(jarray_cart.toString()).execute();

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


                                    new Edit_Product(jarray_cart.toString()).execute();
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

    @Override
    public void onQuantityDelete(int position) {
        String productID=bean_cart_data.get(position).getProduct_id();
        String userID=bean_cart_data.get(position).getUser_id();
        String ownerID=bean_cart_data.get(position).getOwner_id();

        new DeleteQuotationProduct(productID,userID,ownerID).execute();
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(My_Quotation.this);

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

    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private boolean validateEmail1(String email) {
        // TODO Auto-generated method stub

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_BTL_PRODUCT && resultCode == RESULT_OK) {
            new get_cartdata(true).execute();
        } else if (requestCode == REQUEST_SEND_QUOTATION && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    public class Add_Product extends AsyncTask<Void, Void, String>  {
        public StringBuilder sb;
        boolean status;
        String data = "";
        private String result;
        private InputStream is;

        public Add_Product(String data) {
            this.data = data;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        My_Quotation.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("Quotation_Data", data));
                parameters.add(new BasicNameValuePair("quotation_id", quotationID));


                //Log.e("Cart_Array",""+jarray_cart);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + Globals.ADD_QUOTATION, ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(getApplicationContext(), "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(My_Quotation.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(My_Quotation.this, "" + Message, getLayoutInflater());
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
        public StringBuilder sb;
        boolean status;
        String data = "";
        private String result;
        private InputStream is;

        public Edit_Product(String data) {
            this.data = data;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        My_Quotation.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("Quotation_Data", data));
                parameters.add(new BasicNameValuePair("quotation_id", quotationID));


                Log.e("params",parameters.toString());

                json = new ServiceHandler().makeServiceCall(Globals.server_link + Globals.EDIT_QUOTATION, ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(My_Quotation.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(My_Quotation.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(My_Quotation.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();

                        new get_cartdata(true).execute();
                    }

                }
            } catch (Exception j) {
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


            return jsonData;
        }
    }

    public class CustomResultAdapter extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return bean_quotation.size();
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


            convertView = vi.inflate(R.layout.quotation_row, null);


            final TextView product_name = (TextView) convertView.findViewById(R.id.p_name);
            final TextView product_qty = (TextView) convertView.findViewById(R.id.p_qty);
            final TextView product_price = (TextView) convertView.findViewById(R.id.p_price);
            final TextView product_total = (TextView) convertView.findViewById(R.id.p_total);
            ImageView minus = (ImageView) convertView.findViewById(R.id.minus);
            ImageView pluss = (ImageView) convertView.findViewById(R.id.plus);
            final TextView p_schme = (TextView) convertView.findViewById(R.id.p_schme);

            if (!bean_quotation.get(position).getQu_pro_scheme().toString().trim().equalsIgnoreCase("")) {
                minus.setVisibility(View.GONE);
                pluss.setVisibility(View.GONE);

            } else {
                minus.setVisibility(View.GONE);
                pluss.setVisibility(View.GONE);
            }

            if (bean_quotation.get(position).getQu_scheme_title().toString().equalsIgnoreCase(" ") || bean_quotation.get(position).getQu_scheme_title().toString().equalsIgnoreCase("") || bean_quotation.get(position).getQu_scheme_title().toString().equalsIgnoreCase("null")) {
                p_schme.setVisibility(View.GONE);
            } else {
                p_schme.setVisibility(View.VISIBLE);
                p_schme.setText("Scheme - " + bean_quotation.get(position).getQu_scheme_title().toString());
                //Log.e("Scheme_name", "" + bean_cart_data.get(position).getScheme_title());
            }

            // if(flag == 0) {
            String[] option = bean_quotation.get(position).getQU_pro_Option_name().toString().split(", ");
            String[] value = bean_quotation.get(position).getQU_pro_Option_value_name().split(", ");

            //Log.e("133",""+bean_quotation.get(position).getQU_pro_Option_name().toString());
            for (int i = 0; i < option.length; i++) {
                if (i == 0) {
                    //Log.e("" + option, "" + option[i]);
                    option_value = option[i] + ": " + value[i];
                } else {
                    //Log.e("" + option, "" + option[i]);
                    option_value = option_value + ", " + option[i] + ": " + value[i];
                }
            }
            product_name.setText(bean_quotation.get(position).getQU_PRO_NAME() + "\n" + bean_quotation.get(position).getQU_PRO_CODE());


            product_qty.setText(bean_quotation.get(position).getQU_PRO_QTY());
            product_qty.setTag(bean_quotation.get(position).getQU_PRO_ID());
            //Log.e("1314151",""+bean_quotation.get(position).getQU_PRO_ID());
            product_price.setText(bean_quotation.get(position).getQU_PRO_SELLING_PRICE());
            product_price.setTag(bean_quotation.get(position).getQU_PRO_SELLING_PRICE());
            double qty = Double.parseDouble(bean_quotation.get(position).getQU_PRO_QTY());
            double price = Double.parseDouble(bean_quotation.get(position).getQU_PRO_SELLING_PRICE());

            amount1 = qty;
            double a = price;
            amount1 = a * amount1;

            String str = String.format("%.2f", amount1);
            product_total.setText(str);

            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();

            for (int i = 0; i < bean_quotation.size(); i++) {

                //Log.e("11111",""+bean_quotation.get(i).getQU_pro_Option_name());
                //Log.e("2222",""+bean_quotation.get(i).getQU_pro_Option_value_name());
                //Log.e("3333",""+bean_quotation.get(i).getQU_PRO_QTY());
                //Log.e("4444",""+bean_quotation.get(i).getQU_PRO_NAME());

            }
        }
    }

    public class send_quotation extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        My_Quotation.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                parameters.add(new BasicNameValuePair("user_id", "" + user_id_mai));
                parameters.add(new BasicNameValuePair("role_id", "" + role_id_main));
                parameters.add(new BasicNameValuePair("email_id", "" + email_main));
                parameters.add(new BasicNameValuePair("email_cc", "" + emailcc));
                parameters.add(new BasicNameValuePair("title", "" + title_main));
                parameters.add(new BasicNameValuePair("to_name", "" + toname));
                parameters.add(new BasicNameValuePair("content", "" + jarray_Quotation));


                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(GlobalVariable.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Quotation/App_Add_Quotation", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //   System.out.println("error1: " + e.toString());

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
                    Globals.CustomToast(My_Quotation.this, "SERVER ERRER", My_Quotation.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    Boolean date = jObj.getBoolean("status");
                    if (date == false) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(My_Quotation.this, "" + Message, My_Quotation.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(My_Quotation.this, "" + Message, My_Quotation.this.getLayoutInflater());

                        Intent i = new Intent(My_Quotation.this, My_Quotation.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        db = new DatabaseHandler(My_Quotation.this);
                        db.Delete_Quotation();
                        db.close();
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class get_cartdata extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        boolean afterEditing = false;
        private String result;
        private InputStream is;

        public get_cartdata(){
            afterEditing=false;
        }

        public get_cartdata(boolean afterEdititng){
            this.afterEditing=afterEdititng;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        My_Quotation.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                parameters.add(new BasicNameValuePair("quotation_id", quotationID));
                Log.e("Quotation ID", quotationID);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "QuotationCart/App_Get_Quotation_Data", ServiceHandler.POST, parameters);

                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //   System.out.println("error1: " + e.toString());

                return json;

            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            bean_cart_data.clear();
            if(afterEditing){
                listAdapter.notifyDataSetChanged();
            }
            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(My_Quotation.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(My_Quotation.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        new DeleteWholeQuotation().execute();
                        //setdata();
                    } else {


                        JSONObject jobj = new JSONObject(result_1);


                        if (jobj != null) {


                            JSONArray jsonArray = jObj.optJSONArray("data");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Bean_Cart_Data bean = new Bean_Cart_Data();
                                bean.setId(jsonObject.getString("id"));
                                bean.setQuotationID(jsonObject.getString("quotation_id"));
                                bean.setCartID(jsonObject.getString("cart_id"));
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
                                bean.setIsBTLProduct(jsonObject.getString("is_btl_product"));
                                try {
                                    bean.setMinPackOfQty(Integer.parseInt(jsonObject.getString("pack_of_qty")));
                                } catch (Exception e) {
                                    bean.setMinPackOfQty(1);
                                }

                                bean_cart_data.add(bean);

                            }
                            //Log.e("bean_cart_data.size", "" + bean_cart_data.size());
                        }

                        loadingView.dismiss();

                        if(afterEditing){
                            listAdapter.notifyDataSetChanged();
                        }
                        else{
                            setdata();
                        }
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

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

                    Globals.CustomToast(My_Quotation.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(My_Quotation.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(My_Quotation.this);
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

    private class DeleteQuotationProduct extends AsyncTask<Void, Void, String> {
        String productID="", userID="", ownerID="";
        Custom_ProgressDialog dialog;

        public DeleteQuotationProduct(String productID, String userID, String ownerID) {
            this.productID = productID;
            this.userID = userID;
            this.ownerID = ownerID;

            dialog=new Custom_ProgressDialog(My_Quotation.this,"");
            dialog.setCancelable(false);
            dialog.show();
        }


        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairs=new ArrayList<>();
            pairs.add(new BasicNameValuePair("product_id",productID));
            pairs.add(new BasicNameValuePair("user_id",userID));
            pairs.add(new BasicNameValuePair("owner_id",ownerID));
            pairs.add(new BasicNameValuePair("quotation_id",quotationID));

            String json=new ServiceHandler().makeServiceCall(Globals.server_link+Globals.DELETE_QUOTATION_PRODUCT,ServiceHandler.POST,pairs);

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();

            if(s==null || s.isEmpty()){
                Globals.defaultError(getApplicationContext());
            }
            else {
                try {
                    JSONObject object=new JSONObject(s);

                    if(object.getBoolean("status")){
                        Globals.Toast2(getApplicationContext(),object.getString("message"));
                        new get_cartdata(true).execute();
                    }
                    else {
                        Globals.Toast2(getApplicationContext(),object.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class DeleteWholeQuotation extends AsyncTask<Void, Void, String>{

        Custom_ProgressDialog dialog;

        public DeleteWholeQuotation(){
            dialog=new Custom_ProgressDialog(My_Quotation.this,"");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairs=new ArrayList<>();
            pairs.add(new BasicNameValuePair("quotation_id",quotationID));

            String json=new ServiceHandler().makeServiceCall(Globals.server_link+Globals.DELETE_QUOTATION, ServiceHandler.POST,pairs);

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            if(s==null || s.isEmpty()){
                Globals.defaultError(getApplicationContext());
            }
            else {
                try {
                    JSONObject object=new JSONObject(s);

                    if(object.getBoolean("status")){
                        Globals.Toast2(getApplicationContext(),object.getString("message"));
                        setResult(RESULT_OK);
                        finish();
                    }
                    else {
                        Globals.Toast2(getApplicationContext(),object.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
