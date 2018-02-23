package com.agraeta.user.btl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.GetCartItemQuantityResponse;
import com.agraeta.user.btl.model.GetSchemeDetailResponse;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Product_List extends AppCompatActivity {
    public static final String[] pro_name = {"DOOR CONTROL", "GLASS FITTIN", "GLASS SLIDING"};
    public static final String[] pro_delar_prize = {" 630.00", "1400.00", "600.00"};
    public static final String[] prod_no = {"1", "1", "1"};
    public static final String[] pro_mrp = {"745.00", "1500.00", "660.00"};
    public static final String[] pro_margin = {"15.45%", "9%", "10%"};
    public static final String[] pro_prize = {" 630.00", "1400.00", "600.00"};
    public static ArrayList<String> arrOptionTypeID = new ArrayList<String>();
    public static ArrayList<String> arrOptionTypeName = new ArrayList<String>();
    public static ArrayList<String> WishList = new ArrayList<String>();
    int flag = 0;
    EditText search_edit;
    ImageView search, grid, list;
    TextView marqee;
    GridView list_product;
    ListView pro_listed;
    LinearLayout sort;
    AppPrefs app;
    int s = 0;
    int kkk = 0;
    String option_name = "";
    String option_id = "";
    String role;
    DatabaseHandler db;
    LinearLayout l_spinner, l_spinner_text, l_linear, l_sort, l_view_spinner;
    String cartJSON = "";
    boolean hasCartCallFinish = true;
    boolean whereToBuyVisibility = true;
    TextView txt;
    boolean isNotDone = true;
    String jsonData = "";
    String[] perms = {android.Manifest.permission.SYSTEM_ALERT_WINDOW, android.Manifest.permission.WRITE_SETTINGS, android.Manifest.permission.WRITE_SECURE_SETTINGS, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_FINE_LOCATION};
    Dialog dialog, dialogOffer;
    ResultHolder result_holder;
    LinearLayout l_filter;
    ImageView im, img_wish, img_shooping;
    TextView tv_pop_pname, tv_pop_code, tv_pop_packof, tv_pop_mrp, tv_pop_sellingprice, tv_total, tv_txt_mrp;
    Button btn_buynow;
    ImageView minuss, plus;
    Button buy_cart, cancel;
    EditText edt_count;
    TextView p_prize1;
    int page_id = 1;
    String page_limit = "";
    ArrayList<Bean_Product> bean_product1 = new ArrayList<Bean_Product>();
    ArrayList<Bean_Desc> bean_desc1 = new ArrayList<Bean_Desc>();
    TextView txt_selling_price, txt_total_txt;
    ArrayList<Bean_Product> bean_product_db = new ArrayList<Bean_Product>();
    ArrayList<Bean_Desc> bean_desc_db = new ArrayList<Bean_Desc>();
    JSONArray jarray_cart = new JSONArray();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ArrayList<Bean_ProductFeature> bean_productFeatures = new ArrayList<Bean_ProductFeature>();
    ArrayList<Bean_ProductTechSpec> bean_productTechSpecs = new ArrayList<Bean_ProductTechSpec>();
    ArrayList<Bean_ProductStdSpec> bean_productStdSpecs = new ArrayList<Bean_ProductStdSpec>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductOprtion> bean_Oprtions = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions_db = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductCart> bean_productCart = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_ProductImage> bean_productImages = new ArrayList<Bean_ProductImage>();
    ArrayList<Bean_ProductImage> bean_productImages_db = new ArrayList<Bean_ProductImage>();
    ArrayList<Bean_schemeData> bean_Schme_data = new ArrayList<Bean_schemeData>();
    ArrayList<Bean_schemeData> bean_S_data = new ArrayList<Bean_schemeData>();
    String owner_id = "";
    String u_id = "";
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    ArrayList<ArrayList<Bean_Attribute>> array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();
    double amount1;
    ArrayList<String> list_of_images = new ArrayList<String>();
    ArrayList<Bean_Attribute> array_att = new ArrayList<Bean_Attribute>();
    String product_id = "";
    ImageLoader imageloader;
    String json = "";
    String Catid = "";
    String user_id = "";
    String user_id_main = "";
    String pro_id = "";
    String proW_id = "";
    String flagscroll = "";
    String role_id = "";
    Custom_ProgressDialog loadingView;
    int last_record = 0;
    int current_record = 0;
    int last_position = 0;
    LinearLayout l_mrp, l_sell, l_total;
    String p_code = "";
    ArrayList<Bean_Product> bean_product_schme = new ArrayList<Bean_Product>();
    ArrayList<Bean_Schme_value> bean_schme = new ArrayList<Bean_Schme_value>();
    String qun = "";
    ArrayList<Bean_Value_Selected_Detail> array_value = new ArrayList<Bean_Value_Selected_Detail>();
    String rolee = "";
    LinearLayout layout_productList;
    AdminAPI adminAPI;
    private ArrayList<Integer> image_product = new ArrayList<Integer>();

    public static boolean isMarshmallowPlusDevice() {

        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isPermissionRequestRequired(Activity activity, @NonNull String[] permissions, int requestCode) {
        if (isMarshmallowPlusDevice() && permissions.length > 0) {
            List<String> newPermissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (PERMISSION_GRANTED != activity.checkSelfPermission(permission)) {
                    newPermissionList.add(permission);

                }
            }
            if (newPermissionList.size() > 0) {
                activity.requestPermissions(newPermissionList.toArray(new String[newPermissionList.size()]), requestCode);
                return true;
            }


        }

        return false;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        //Log.e("System GC", "Called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list);

        Log.e("Yess i am category page", "->");

        adminAPI = ServiceGenerator.getAPIServiceClass();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        layout_productList = (LinearLayout) findViewById(R.id.layout_productList);

        fetchid();
        isPermissionRequestRequired(Product_List.this, perms, 1);

        setRefershData();


        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(Product_List.this);
                    role_id = app.getSubSalesId();
                    user_id_main = app.getSalesPersonId();

                    Snackbar.make(layout_productList, "Taking Order for : " + app.getUserName(), Snackbar.LENGTH_LONG).show();
                }


            }

        } else {
            user_id_main = "";
        }

        setActionBar();


        image_product.add(R.drawable.doorcontrol);
        image_product.add(R.drawable.doorfitting);
        image_product.add(R.drawable.glasssliding);

        app = new AppPrefs(Product_List.this);
        Catid = app.getUser_CatId();
        Log.e("CatId",Catid);

        page_id = 1;


        new get_marquee().execute();


       /* search_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_List.this,Search.class);
                startActivity(i);
            }
        });*/

    }

    private void fetchid() {
        flagscroll = "2";
        pro_listed = (ListView) findViewById(R.id.listView_product);
        l_filter = (LinearLayout) findViewById(R.id.lin_filter);
        Log.e("hello","111111");

        /*search_edit=(EditText)findViewById(R.id.edit_search_pro);
        search=(ImageView)findViewById(R.id.Image_search_pro);*/
        sort = (LinearLayout) findViewById(R.id.sort);
        grid = (ImageView) findViewById(R.id.grid_ic);
        marqee = (TextView) findViewById(R.id.tvmarquee);
        marqee.setSelected(true);
        list_product = (GridView) findViewById(R.id.gridView3);

        l_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                app = new AppPrefs(Product_List.this);
                /*app.setProduct_Sort("");
                app.setFilter_Option("");
                app.setFilter_SubCat("");
                app.setFilter_Cat("");
                app.setFilter_Sort("");*/

                //db = new DatabaseHandler(Product_List.this);
                //db.LogoutFilter();
                //db.close();
                db = new DatabaseHandler(getApplicationContext());
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(Product_List.this, Product_Filter.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        list_product.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                int totalItems = bean_product1.size();
                int lastVisibleItem = list_product.getLastVisiblePosition();

                if (lastVisibleItem == totalItems - 1) {

                /*}*/

                /*if (scrollState == SCROLL_STATE_IDLE) {*/

                    page_id = page_id + 1;

                    //Log.e("Page Limit", "" + page_limit);
                    //Log.e("Page ID", "" + page_id);
                    if (page_id > Integer.parseInt(page_limit)) {

                    } else {
                        current_record = list_product.getCount();
                        last_record = current_record - last_record;
                        app = new AppPrefs(Product_List.this);
                        String page = app.getPage_Data();
                        flagscroll = "2";
                        if (page.equalsIgnoreCase("1")) {
                            new get_Product().execute();
                        } else if (page.equalsIgnoreCase("2")) {

                            new get_ProductFilter().execute();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        pro_listed.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                int totalItems = bean_product1.size();
                int lastVisibleItem = list_product.getLastVisiblePosition();

                if (lastVisibleItem == totalItems - 1) {

                    page_id = page_id + 1;

                    //Log.e("Page Limit", "" + page_limit);
                    //Log.e("Page ID", "" + page_id);
                    if (page_id > Integer.parseInt(page_limit)) {

                    } else {
                        current_record = list_product.getCount();
                        last_record = current_record - last_record;
                        app = new AppPrefs(Product_List.this);
                        String page = app.getPage_Data();
                        flagscroll = "1";
                        if (page.equalsIgnoreCase("1")) {
                            new get_Product().execute();
                        } else if (page.equalsIgnoreCase("2")) {

                            new get_ProductFilter().execute();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0) {
                    grid.setImageResource(R.drawable.ic_action_list);
                    list_product.setVisibility(View.GONE);
                    pro_listed.setVisibility(View.VISIBLE);
                    pro_listed.setAdapter(new CustomResultAdapterDoctor1());
                    flag = 1;
                    flagscroll = "1";
                } else {
                    grid.setImageResource(R.drawable.ic_action_grid);
                    list_product.setVisibility(View.VISIBLE);
                    pro_listed.setVisibility(View.GONE);
                    list_product.setAdapter(new CustomResultAdapterDoctor());
                    flag = 0;
                    flagscroll = "2";
                }


            }
        });

    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);

        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);

        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        app = new AppPrefs(Product_List.this);
        String qun = app.getCart_QTy();

        setRefershData();

        dialog = new Dialog(Product_List.this);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        dialog.setCanceledOnTouchOutside(false);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogOffer = new Dialog(Product_List.this);
        dialogOffer.getWindow();
        dialogOffer.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOffer.setCancelable(false);


        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(Product_List.this);
                    role_id = app.getSubSalesId();
                    u_id = app.getSalesPersonId();
                } else {
                    u_id = owner_id;
                }


            }

            List<NameValuePair> para = new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id", u_id));

            new GetCartByQty(para).execute();


        } else {
            app = new AppPrefs(Product_List.this);
            app.setCart_QTy("");
        }

        app = new AppPrefs(Product_List.this);
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
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(getApplicationContext());
                db.Delete_ALL_table();
                db.close();
                app = new AppPrefs(Product_List.this);
                app.setProduct_Sort("");
                app.setFilter_Option("");
                app.setFilter_SubCat("");
                app.setFilter_Cat("");
                app.setFilter_Sort("");
                Intent i = new Intent(Product_List.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(getApplicationContext());
                db.LogoutFilter();
                db.Delete_ALL_table();
                db.close();
                app = new AppPrefs(Product_List.this);
                app.setProduct_Sort("");
                app.setFilter_Option("");
                app.setFilter_SubCat("");
                app.setFilter_Cat("");
                app.setFilter_Sort("");
                Intent i = new Intent(Product_List.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(getApplicationContext());
                db.Delete_ALL_table();
                db.close();
                app = new AppPrefs(Product_List.this);
                app.setUser_notification("product");
                Intent i = new Intent(Product_List.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(getApplicationContext());
                db.Delete_ALL_table();
                db.close();
                app = new AppPrefs(Product_List.this);
                app.setProduct_Sort("");
                app.setFilter_Option("");
                app.setFilter_SubCat("");
                app.setFilter_Cat("");
                app.setFilter_Sort("");
                app = new AppPrefs(Product_List.this);
                app.setUser_notification("product");
                Intent i = new Intent(Product_List.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void GetCartQtyCall() {
        setRefershData();
        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(Product_List.this);
                    role_id = app.getSubSalesId();
                    u_id = app.getSalesPersonId();
                } else {
                    u_id = owner_id;
                }


            }

            List<NameValuePair> para = new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id", u_id));

            new GetCartByQty(para).execute();


        } else {
            app = new AppPrefs(Product_List.this);
            app.setCart_QTy("");
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        app = new AppPrefs(Product_List.this);
        app.setProduct_Sort("");
        app.setFilter_Option("");
        app.setFilter_SubCat("");
        app.setFilter_Cat("");
        app.setFilter_Sort("");
        db = new DatabaseHandler(Product_List.this);
        db.LogoutFilter();
        db.Delete_ALL_table();
        db.close();
     /*   Intent i = new Intent(Product_List.this,MainPage_drawer.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);*/
        finish();
    }

    public void SetRefershDataProduct() {
        // TODO Auto-generated method stub
        bean_product_db.clear();
        db = new DatabaseHandler(Product_List.this);

        ArrayList<Bean_Product> user_array_from_db = db.Get_Product();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int Pro_id = user_array_from_db.get(i).getId();

            String KEY_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_PRO_CODE = user_array_from_db.get(i).getPro_code();
            String KEY_PRO_NAME = user_array_from_db.get(i).getPro_name();
            String KEY_PRO_LABEL = user_array_from_db.get(i).getPro_label();
            String KEY_PRO_MRP = user_array_from_db.get(i).getPro_mrp();
            String KEY_PRO_SELLINGPRICE = user_array_from_db.get(i).getPro_sellingprice();
            String KEY_PRO_SHORTDESC = user_array_from_db.get(i).getPro_shortdesc();
            String KEY_PRO_MOREINFO = user_array_from_db.get(i).getPro_moreinfo();
            String KEY_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_PRO_QTY = user_array_from_db.get(i).getPro_qty();
            String KEY_PRO_IMAGE = user_array_from_db.get(i).getPro_image();


            Bean_Product contact = new Bean_Product();

            contact.setId(Pro_id);
            contact.setPro_id(KEY_PRO_ID);
            contact.setPro_code(KEY_PRO_CODE);
            contact.setPro_name(KEY_PRO_NAME);
            contact.setPro_label(KEY_PRO_LABEL);
            contact.setPro_mrp(KEY_PRO_MRP);
            contact.setPro_sellingprice(KEY_PRO_SELLINGPRICE);
            contact.setPro_shortdesc(KEY_PRO_SHORTDESC);
            contact.setPro_moreinfo(KEY_PRO_MOREINFO);
            contact.setPro_cat_id(KEY_PRO_CAT_ID);
            contact.setPro_qty(KEY_PRO_QTY);
            contact.setPro_image(KEY_PRO_IMAGE);


            bean_product_db.add(contact);


        }
        db.close();
    }

    public void SetRefershDataProductDesc() {
        // TODO Auto-generated method stub
        bean_desc_db.clear();
        db = new DatabaseHandler(Product_List.this);

        ArrayList<Bean_Desc> user_array_from_db = db.Get_ProductDesc();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int Pro_id = user_array_from_db.get(i).getId();

            String KEY_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_VALUE_D_DESCRIPTION = user_array_from_db.get(i).getDescription();
            String KEY_VALUE_D_SPECIFICATION = user_array_from_db.get(i).getSpecification();
            String KEY_VALUE_D_TECHNICAL = user_array_from_db.get(i).getTechnical();
            String KEY_VALUE_D_CATALOGS = user_array_from_db.get(i).getCatalogs();
            String KEY_VALUE_D_GUARANTEE = user_array_from_db.get(i).getGuarantee();
            String KEY_VALUE_D_VIDEOS = user_array_from_db.get(i).getVideos();
            String KEY_VALUE_D_GENERAL = user_array_from_db.get(i).getGeneral();


            Bean_Desc contact = new Bean_Desc();

            contact.setId(Pro_id);
            contact.setPro_id(KEY_PRO_ID);
            contact.setDescription(KEY_VALUE_D_DESCRIPTION);
            contact.setSpecification(KEY_VALUE_D_SPECIFICATION);
            contact.setTechnical(KEY_VALUE_D_TECHNICAL);
            contact.setCatalogs(KEY_VALUE_D_CATALOGS);
            contact.setGuarantee(KEY_VALUE_D_GUARANTEE);
            contact.setVideos(KEY_VALUE_D_VIDEOS);
            contact.setGeneral(KEY_VALUE_D_GENERAL);


            bean_desc_db.add(contact);


        }
        db.close();
    }

    private void setRefershDataCart() {
        // TODO Auto-generated method stub
        bean_productCart.clear();
        db = new DatabaseHandler(Product_List.this);

        ArrayList<Bean_ProductCart> user_array_from_db = db.Get_Product_Cart();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int cart_id = user_array_from_db.get(i).getId();
            String KEY_CART_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_CART_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_CART_PRO_IMAGES = user_array_from_db.get(i).getPro_Images();
            String KEY_CART_PRO_CODE = user_array_from_db.get(i).getPro_code();
            String KEY_CART_PRO_NAME = user_array_from_db.get(i).getPro_name();
            String KEY_CART_PRO_QTY = user_array_from_db.get(i).getPro_qty();
            String KEY_CART_PRO_MRP = user_array_from_db.get(i).getPro_mrp();
            String KEY_CART_PRO_SELLING_PRICE = user_array_from_db.get(i).getPro_sellingprice();
            String KEY_CART_PRO_SHORTDESC = user_array_from_db.get(i).getPro_shortdesc();
            String KEY_CART_PRO_OPTIONID = user_array_from_db.get(i).getPro_Option_id();
            String KEY_CART_PRO_OPTION_NAME = user_array_from_db.get(i).getPro_Option_name();
            String KEY_CART_PRO_OPTION_VALUEID = user_array_from_db.get(i).getPro_Option_value_id();
            String KEY_CART_PRO_OPTION_VALUENAME = user_array_from_db.get(i).getPro_Option_value_name();
            String KEY_CART_PRO_TOTAL = user_array_from_db.get(i).getPro_total();

            Bean_ProductCart contact = new Bean_ProductCart();
            contact.setId(cart_id);
            contact.setPro_id(KEY_CART_PRO_ID);
            contact.setPro_cat_id(KEY_CART_PRO_CAT_ID);
            contact.setPro_Images(KEY_CART_PRO_IMAGES);
            contact.setPro_code(KEY_CART_PRO_CODE);
            contact.setPro_name(KEY_CART_PRO_NAME);
            contact.setPro_qty(KEY_CART_PRO_QTY);
            contact.setPro_mrp(KEY_CART_PRO_MRP);
            contact.setPro_sellingprice(KEY_CART_PRO_SELLING_PRICE);
            contact.setPro_shortdesc(KEY_CART_PRO_SHORTDESC);
            contact.setPro_Option_id(KEY_CART_PRO_OPTIONID);
            contact.setPro_Option_name(KEY_CART_PRO_OPTION_NAME);
            contact.setPro_Option_value_id(KEY_CART_PRO_OPTION_VALUEID);
            contact.setPro_Option_value_name(KEY_CART_PRO_OPTION_VALUENAME);
            contact.setPro_total(KEY_CART_PRO_TOTAL);

            bean_productCart.add(contact);


        }
        db.close();
    }

    private void setRefershDataFeture() {
        // TODO Auto-generated method stub
        bean_productFeatures.clear();
        db = new DatabaseHandler(Product_List.this);

        ArrayList<Bean_ProductFeature> user_array_from_db = db.Get_ProductFeature();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int feature_id = user_array_from_db.get(i).getId();
            String KEY_FEATURE_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_FEATURE_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_FEATURE_PRO_FETURE_ID = user_array_from_db.get(i).getPro_feature_id();
            String KEY_FEATURE_NAME = user_array_from_db.get(i).getPro_feature_name();
            String KEY_FEATURE_VALUE = user_array_from_db.get(i).getPro_feature_value();

            Bean_ProductFeature contact = new Bean_ProductFeature();
            contact.setId(feature_id);
            contact.setPro_id(KEY_FEATURE_PRO_ID);
            contact.setPro_cat_id(KEY_FEATURE_PRO_CAT_ID);
            contact.setPro_feature_id(KEY_FEATURE_PRO_FETURE_ID);
            contact.setPro_feature_name(KEY_FEATURE_NAME);
            contact.setPro_feature_value(KEY_FEATURE_VALUE);

            bean_productFeatures.add(contact);


        }
        db.close();
    }

    private void setRefershDataImage() {
        // TODO Auto-generated method stub
        bean_productImages_db.clear();
        db = new DatabaseHandler(Product_List.this);

        ArrayList<Bean_ProductImage> user_array_from_db = db.Get_ProductImage();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {


            int image_id = user_array_from_db.get(i).getId();
            String KEY_IMAGE_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_IMAGE_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_IMAGE_PRO_IMAGES = user_array_from_db.get(i).getPro_Images();


            Bean_ProductImage contact = new Bean_ProductImage();
            contact.setId(image_id);
            contact.setPro_id(KEY_IMAGE_PRO_ID);
            contact.setPro_cat_id(KEY_IMAGE_PRO_CAT_ID);
            contact.setPro_Images(KEY_IMAGE_PRO_IMAGES);


            bean_productImages_db.add(contact);


        }
        db.close();
    }

    private void setRefershDataOption() {
        // TODO Auto-generated method stub
        bean_productOprtions_db.clear();
        db = new DatabaseHandler(Product_List.this);

        ArrayList<Bean_ProductOprtion> user_array_from_db = db.Get_Product_Option();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int option_id = user_array_from_db.get(i).getId();
            String KEY_OPTION_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_OPTION_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_OPTION_PRO_OPTION_ID = user_array_from_db.get(i).getPro_Option_id();
            String KEY_OPTION_NAME = user_array_from_db.get(i).getPro_Option_name();
            String KEY_OPTION_VALUE_ID = user_array_from_db.get(i).getPro_Option_value_id();
            String KEY_OPTION_VALUENAME = user_array_from_db.get(i).getPro_Option_value_name();
            String KEY_OPTION_MRP = user_array_from_db.get(i).getPro_Option_mrp();
            String KEY_OPTION_SELLINGPRICE = user_array_from_db.get(i).getPro_Option_selling_price();
            String KEY_OPTION_PROIMAGE = user_array_from_db.get(i).getPro_Option_proimage();
            String KEY_OPTION_PROCODE = user_array_from_db.get(i).getPro_Option_procode();
            String KEY_OPTION_PROID = user_array_from_db.get(i).getOption_pro_id();


            Bean_ProductOprtion contact = new Bean_ProductOprtion();

            contact.setId(option_id);
            contact.setPro_id(KEY_OPTION_PRO_ID);
            contact.setPro_cat_id(KEY_OPTION_PRO_CAT_ID);
            contact.setPro_Option_id(KEY_OPTION_PRO_OPTION_ID);
            contact.setPro_Option_name(KEY_OPTION_NAME);
            contact.setPro_Option_value_id(KEY_OPTION_VALUE_ID);
            contact.setPro_Option_value_name(KEY_OPTION_VALUENAME);
            contact.setPro_Option_mrp(KEY_OPTION_MRP);
            contact.setPro_Option_selling_price(KEY_OPTION_SELLINGPRICE);
            contact.setPro_Option_proimage(KEY_OPTION_PROIMAGE);
            contact.setPro_Option_procode(KEY_OPTION_PROCODE);
            contact.setOption_pro_id(KEY_OPTION_PROID);


            bean_productOprtions_db.add(contact);


        }
        db.close();
    }

    private void setRefershDataStdspec() {
        // TODO Auto-generated method stub
        bean_productStdSpecs.clear();
        db = new DatabaseHandler(Product_List.this);

        ArrayList<Bean_ProductStdSpec> user_array_from_db = db.Get_Productstdspec();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {


            int stdspec_id = user_array_from_db.get(i).getId();
            String KEY_STDSPEC_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_STDSPEC_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_STDSPEC_STDSPEC_ID = user_array_from_db.get(i).getPro_Stdspec_id();
            String KEY_STDSPEC_NAME = user_array_from_db.get(i).getPro_Stdspec_name();
            String KEY_STDSPEC_VALUE = user_array_from_db.get(i).getPro_Stdspec_value();

            Bean_ProductStdSpec contact = new Bean_ProductStdSpec();

            contact.setId(stdspec_id);
            contact.setPro_id(KEY_STDSPEC_PRO_ID);
            contact.setPro_cat_id(KEY_STDSPEC_PRO_CAT_ID);
            contact.setPro_Stdspec_id(KEY_STDSPEC_STDSPEC_ID);
            contact.setPro_Stdspec_name(KEY_STDSPEC_NAME);
            contact.setPro_Stdspec_value(KEY_STDSPEC_VALUE);

            bean_productStdSpecs.add(contact);


        }
        db.close();
    }

    private void setRefershDataTechDesc() {
        // TODO Auto-generated method stub
        bean_productTechSpecs.clear();
        db = new DatabaseHandler(Product_List.this);

        ArrayList<Bean_ProductTechSpec> user_array_from_db = db.Get_Producttechspec();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {


            int techdesc_id = user_array_from_db.get(i).getId();
            String KEY_TECHSPEC_PRO_ID = user_array_from_db.get(i).getPro_id();
            String KEY_TECHSPEC_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_TECHSPEC_TECHSPEC_ID = user_array_from_db.get(i).getPro_Techspec_id();
            String KEY_TECHSPEC_NAME = user_array_from_db.get(i).getPro_Techspec_name();
            String KEY_TECHSPEC_VALUE = user_array_from_db.get(i).getPro_Techspec_value();

            Bean_ProductTechSpec contact = new Bean_ProductTechSpec();

            contact.setId(techdesc_id);
            contact.setPro_id(KEY_TECHSPEC_PRO_ID);
            contact.setPro_cat_id(KEY_TECHSPEC_PRO_CAT_ID);
            contact.setPro_Techspec_id(KEY_TECHSPEC_TECHSPEC_ID);
            contact.setPro_Techspec_name(KEY_TECHSPEC_NAME);
            contact.setPro_Techspec_value(KEY_TECHSPEC_VALUE);

            bean_productTechSpecs.add(contact);


        }
        db.close();
    }

    private void showOfferDialog(final int position, String selectedProductID) {

        Globals.generateNoteOnSD(getApplicationContext(), "12121212121");

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialogOffer.setContentView(R.layout.scheme_layout);
        dialogOffer.getWindow().setLayout((6 * width) / 7, (4 * height) / 8);
        dialogOffer.setCancelable(true);
        dialogOffer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageView btn_cancel = (ImageView) dialogOffer.findViewById(R.id.btn_cancel);
        bean_S_data.clear();
        ArrayList<String> distinctSchemeIDs = new ArrayList<>();

        Log.e("size", "" + bean_Schme_data.size());


        for (int i = 0; i < bean_Schme_data.size(); i++) {

            if (bean_Schme_data.get(i).getSchme_prod_id().equalsIgnoreCase(selectedProductID)) {
                if (!distinctSchemeIDs.contains(bean_Schme_data.get(i).getSchme_id())) {
                    distinctSchemeIDs.add(bean_Schme_data.get(i).getSchme_id());
                    Bean_schemeData beans = new Bean_schemeData();
                    beans.setSchme_id(bean_Schme_data.get(i).getSchme_id());
                    Log.e("ID", "" + bean_product1.get(position).getPro_id());
                    Log.e("name", "" + bean_Schme_data.get(i).getSchme_name());
                    beans.setSchme_name(bean_Schme_data.get(i).getSchme_name());
                    beans.setCategory_id(bean_Schme_data.get(i).getCategory_id());
                    beans.setSchme_qty(bean_Schme_data.get(i).getSchme_qty());
                    beans.setSchme_buy_prod_id(bean_Schme_data.get(i).getSchme_buy_prod_id());
                    beans.setSchme_prod_id(bean_Schme_data.get(i).getSchme_prod_id());

                    bean_S_data.add(beans);
                }
            }


        }
        ExpandableListView listSchemes = (ExpandableListView) dialogOffer.findViewById(R.id.listSchemes);

        final List<String> schemeHeaders = new ArrayList<String>();
        final HashMap<String, List<Bean_schemeData>> schemeChildList = new HashMap<String, List<Bean_schemeData>>();

        List<Bean_schemeData> extraSpecialScheme = new ArrayList<Bean_schemeData>();
        List<Bean_schemeData> specialScheme = new ArrayList<Bean_schemeData>();
        List<Bean_schemeData> generalScheme = new ArrayList<Bean_schemeData>();

        for (int i = 0; i < bean_S_data.size(); i++) {
            if (bean_S_data.get(i).getCategory_id().equals(C.EXTRA_SPECIAL_SCHEME)) {
                extraSpecialScheme.add(bean_S_data.get(i));
            } else if (bean_S_data.get(i).getCategory_id().equals(C.SPECIAL_SCHEME)) {
                specialScheme.add(bean_S_data.get(i));
            } else {
                generalScheme.add(bean_S_data.get(i));
            }
        }

        if (extraSpecialScheme.size() > 0) {
            schemeHeaders.add("App Offers");
            schemeChildList.put(schemeHeaders.get(schemeHeaders.size() - 1), extraSpecialScheme);
        }

        if (specialScheme.size() > 0) {
            schemeHeaders.add("Special Offers");
            schemeChildList.put(schemeHeaders.get(schemeHeaders.size() - 1), specialScheme);
        }

        if (generalScheme.size() > 0) {
            schemeHeaders.add("Offers");
            schemeChildList.put(schemeHeaders.get(schemeHeaders.size() - 1), generalScheme);
        }

        final ExpandableSchemeAdapter schemeAdapter = new ExpandableSchemeAdapter(schemeHeaders, schemeChildList);
        listSchemes.setAdapter(schemeAdapter);

        listSchemes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                setRefershData();
                String selectedSchemeID = ((Bean_schemeData) schemeAdapter.getChild(groupPosition, childPosition)).getSchme_id();
                if (user_data.size() != 0) {
                    for (int i = 0; i < user_data.size(); i++) {

                        owner_id = user_data.get(i).getUser_id();

                        role_id = user_data.get(i).getUser_type();

                        if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                            app = new AppPrefs(Product_List.this);
                            role_id = app.getSubSalesId();
                            u_id = app.getSalesPersonId();
                        } else {
                            u_id = owner_id;
                        }


                    }


                    // product_id = tv_pop_pname.getTag().toString();
                    List<NameValuePair> para = new ArrayList<NameValuePair>();
                    para.add(new BasicNameValuePair("product_id", bean_product1.get(position).getPro_id()));
                    para.add(new BasicNameValuePair("owner_id", owner_id));
                    para.add(new BasicNameValuePair("user_id", u_id));
                    Globals.generateNoteOnSD(getApplicationContext(), "12121212121" + para.toString());
                    Log.e("111111111", "" + bean_product1.get(position).getPro_id());
                    Log.e("222222222", "" + owner_id);
                    Log.e("333333333", "" + u_id);
                    isNotDone = true;
                    new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    while (isNotDone) {
                        Globals.generateNoteOnSD(getApplicationContext(), "In while Loop");
                    }
                    isNotDone = true;

                    String json = jsonData;

                    Log.e("JSON UP", json);
                    jsonData = "";

                    try {
                        Globals.generateNoteOnSD(getApplicationContext(), "JSON Return" + json);

                        //System.out.println(json);

                        if (json.isEmpty()) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                            Globals.CustomToast(Product_List.this, "SERVER ERROR", getLayoutInflater());
                            // loadingView.dismiss();

                        } else {
                            JSONObject jObj = new JSONObject(json);

                            boolean date = jObj.getBoolean("status");

                            if (!date) {

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

                        Globals.generateNoteOnSD(getApplicationContext(), "Exception : " + j.getMessage());
                        //Log.e("json exce",j.getMessage());
                    }
                    Log.e("kkk", "" + kkk);
                    product_id = bean_product1.get(position).getPro_id();
                    String qty = schemeChildList.get(schemeHeaders.get(groupPosition)).get(childPosition).getSchme_qty();
                    Log.e("qty", "" + qty);

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("product_id", product_id));
                    params.add(new BasicNameValuePair("user_id", user_id_main));
                    params.add(new BasicNameValuePair("product_buy_qty", qty));
                    params.add(new BasicNameValuePair("scheme_id", selectedSchemeID));
                    Log.e("111111111", "" + product_id);
                    Log.e("222222222", "" + user_id_main);
                    Log.e("333333333", "" + qty);

                    jsonData = "";
                    isNotDone = true;
                    new GetProductDetailByCode(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    while (isNotDone) {

                    }
                    isNotDone = true;
                    String jsons = jsonData; //GetProductDetailByCode(params);

                    Log.e("Return JSON", jsons);

                    //new check_schme().execute();
                    try {


                        //System.out.println(json);

                        if (jsons == null
                                || (jsons.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                            Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                            // loadingView.dismiss();

                        } else {
                            JSONObject jObj = new JSONObject(jsons);

                            String date = jObj.getString("status");

                            if (date.equalsIgnoreCase("false")) {
                                String Message = jObj.getString("message");
                                bean_product_schme.clear();
                                bean_schme.clear();
                                //Log.e("11111111",""+product_id);
                                //   Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                                // loadingView.dismiss();
                            } else {

                                JSONObject jobj = new JSONObject(jsons);

                                bean_product_schme.clear();
                                bean_schme.clear();

                                JSONObject jsonArray = jobj.getJSONObject("data");
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


                                //loadingView.dismiss();

                            }

                        }
                    } catch (Exception j) {
                        j.printStackTrace();
                        Log.e("JSON Exception", j.getMessage());
                        //Log.e("json exce",j.getMessage());
                    }
                    //Log.e("45454545",""+bean_product_schme.size());
                    if (bean_schme.size() == 0) {
                        Log.e("1111111111111", "" + qty);
                    } else {
                        Log.e("2222222222222222", "" + qty);
                        //Log.e("Type ID : ",""+bean_schme.get(0).getType_id().toString());
                        if (bean_schme.get(0).getType_id().equalsIgnoreCase("1")) {
                            Log.e("Scheme Option", "1 ->Option");
                            jarray_cart = new JSONArray();

                            String getq = bean_schme.get(0).getGet_qty();
                            String buyq = bean_schme.get(0).getBuy_qty();
                            String maxq = bean_schme.get(0).getMax_qty();

                            double getqu = Double.parseDouble(getq);
                            double buyqu = Double.parseDouble(buyq);
                            double maxqu = Double.parseDouble(maxq);
                            int qu = Integer.parseInt(qty);


                            String sell = bean_product1.get(position).getPro_sellingprice();

                            double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * buyqu;

                            double se1 = buyqu + getqu;

                            double fse = se / se1;
                            double w = round(fse, 2);
                            sell = String.format("%.2f", w);
                            //sell = String.valueOf(fse);

                            int fqu = qu + (int) getqu;


                            for (int i = 0; i < 1; i++) {
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", u_id);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_product1.get(position).getPro_id());
                                    jobject.put("category_id", Catid);
                                    jobject.put("name", bean_product1.get(position).getPro_name());

                                    jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                    jobject.put("quantity", String.valueOf(fqu));
                                    jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                    jobject.put("selling_price", sell);
                                    jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                    jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                    jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                    jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                    double f = fqu * Double.parseDouble(sell);
                                    double w1 = round(f, 2);
                                    String str = String.format("%.2f", w1);
                                    jobject.put("item_total", str);
                                    jobject.put("pro_scheme", " ");
                                    jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                    jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                    jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                    jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                    if (list_of_images.size() == 0) {
                                        jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                        // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                    } else {
                                        jobject.put("prod_img", list_of_images.get(0));
                                        //   bean.setPro_Images(list_of_images.get(0).toString());
                                    }


                                    jarray_cart.put(jobject);
                                } catch (JSONException e) {

                                }


                            }
                            array_value.clear();
                            dialog.dismiss();
                            Log.e("Data 3217", jarray_cart.toString());
                            Log.e("kkkkkkk", "" + kkk);
                            if (kkk == 1) {
                                Log.e("01","-->");
                                new Edit_Product().execute();

                            } else {

                                new Add_Product().execute();
                                Log.e("1","-->");
                            }


                        } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {
                            Log.e("Scheme Option", "2 ->Option");
                            jarray_cart = new JSONArray();
                            try {
                                JSONObject jobject = new JSONObject();

                                jobject.put("user_id", u_id);
                                jobject.put("role_id", role_id);
                                jobject.put("owner_id", owner_id);
                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                jobject.put("category_id", Catid);
                                jobject.put("name", bean_product1.get(position).getPro_name());

                                jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                jobject.put("quantity", qty);
                                jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                jobject.put("selling_price", bean_product1.get(position).getPro_sellingprice());
                                jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                double f = Integer.parseInt(qty) * Double.parseDouble(bean_product1.get(position).getPro_sellingprice());
                                double w1 = round(f, 2);
                                String str = String.format("%.2f", w1);
                                jobject.put("item_total", str);
                                jobject.put("pro_scheme", " ");
                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                jobject.put("scheme_id", " ");
                                jobject.put("scheme_title", " ");
                                jobject.put("scheme_pack_id", " ");
                                if (list_of_images.size() == 0) {
                                    jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                    // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                } else {
                                    jobject.put("prod_img", list_of_images.get(0));
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
                            //double maxqu = Double.parseDouble(maxq);
                            int qu = Integer.parseInt(qty);

                            int a = (int) (qu / buyqu);
                            int b = (int) (a * getqu);
                            /*if (b > maxqu) {
                                b = maxqu;
                            } else {
                                b = b;
                            }*/


                            try {
                                JSONObject jobject = new JSONObject();

                                jobject.put("user_id", u_id);
                                jobject.put("role_id", role_id);
                                jobject.put("owner_id", owner_id);
                                jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                jobject.put("category_id",Catid);
                                jobject.put("name", bean_product_schme.get(0).getPro_name());
                                jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                jobject.put("quantity", String.valueOf(b));
                                jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                //Log.e("D1D1",""+bean_product_schme.get(0).getPro_mrp());
                                jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                //Log.e("E1E1",""+bean_product_schme.get(0).getPro_sellingprice());
                                jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id());
                                jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name());
                                jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id());
                                jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name());
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
                            Log.e("Data 3330", jarray_cart.toString());
                            //dialogOffer.dismiss();
                            Log.e("kkkkkkk", "" + kkk);
                            if (kkk == 1) {
                                Log.e("02","-->");
                                new Edit_Product().execute();

                            } else {

                                new Add_Product().execute();
                                Log.e("2","-->");
                            }

                        } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("3")) {
                            Log.e("Scheme Option", "3 ->Option");


                            String getq = bean_schme.get(0).getGet_qty();
                            String buyq = bean_schme.get(0).getBuy_qty();
                            String maxq = bean_schme.get(0).getMax_qty();

                            double getqu = Double.parseDouble(getq);
                            double buyqu = Double.parseDouble(buyq);
                            double maxqu = Double.parseDouble(maxq);
                            int qu = Integer.parseInt(qty);


                            String sell = bean_product1.get(position).getPro_sellingprice();

                            String disc = bean_schme.get(0).getDisc_per();

                            double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * Double.parseDouble(bean_schme.get(0).getDisc_per());

                            double se1 = se / 100;

                            double fse = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) - se1;
                            double w1 = round(fse, 2);
                            sell = String.format("%.2f", w1);
                            jarray_cart = new JSONArray();

                            for (int i = 0; i < 1; i++) {
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", u_id);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_product1.get(position).getPro_id());
                                    jobject.put("category_id", Catid);
                                    jobject.put("name", bean_product1.get(position).getPro_name());

                                    jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                    jobject.put("quantity", qty);
                                    jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                    jobject.put("selling_price", sell);
                                    jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                    jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                    jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                    jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                    double f = Double.parseDouble(qty) * Double.parseDouble(sell);
                                    double w = round(f, 2);
                                    String str = String.format("%.2f", w);
                                    jobject.put("item_total", str);
                                    jobject.put("pro_scheme", " ");
                                    jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                    jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                    jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                    jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                    if (list_of_images.size() == 0) {
                                        jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                        // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                    } else {
                                        jobject.put("prod_img", list_of_images.get(0));
                                        //   bean.setPro_Images(list_of_images.get(0).toString());
                                    }


                                    jarray_cart.put(jobject);
                                } catch (JSONException e) {

                                }


                            }
                            array_value.clear();
                            Log.e("Data 3414", jarray_cart.toString());
                            Log.e("kkkkkkk", "" + kkk);
                            if (kkk == 1) {
                                Log.e("03","-->");
                                new Edit_Product().execute();

                            } else {

                                new Add_Product().execute();
                                Log.e("3","-->");
                            }
                        }
                    }

                } else {

                    Globals.CustomToast(Product_List.this, "Please Login First", getLayoutInflater());
                    user_id_main = "";

                }

                dialogOffer.dismiss();

                return true;
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOffer.dismiss();
            }
        });
        dialogOffer.show();

    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Product_List.this);

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

        Globals.generateNoteOnSD(getApplicationContext(), "9275 -> " + params.toString());

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
            Globals.generateNoteOnSD(getApplicationContext(), "In scheme while Loop");
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
            Globals.generateNoteOnSD(getApplicationContext(), "In Buy Now Loop");
        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    private void showAddToCartProductDialog(final int position, final int editType, final String currentQunatity, final String[] selectedProductID) {


        final int[] kkk = {editType};
        final String[] qun = {currentQunatity};

        boolean isFirstTimeLoaded = true;

        dialog.setContentView(R.layout.popup_cart);
        minuss = (ImageView) dialog.findViewById(R.id.minus);
        plus = (ImageView) dialog.findViewById(R.id.plus);
        buy_cart = (Button) dialog.findViewById(R.id.Btn_Buy_online11);
        cancel = (Button) dialog.findViewById(R.id.cancel);
        edt_count = (EditText) dialog.findViewById(R.id.edt_count);
        edt_count.setSelection(edt_count.getText().length());
        final ImageView img_offerDialog = (ImageView) dialog.findViewById(R.id.img_offerDialog);

        final TextView txt_availableScheme = (TextView) dialog.findViewById(R.id.txt_availableScheme);

        if (bean_product1.get(position).getScheme() == null || bean_product1.get(position).getScheme().isEmpty()) {
            img_offerDialog.setVisibility(View.GONE);
        }

        img_offerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOfferDialog(position, selectedProductID[0]);
            }
        });


        l_view_spinner = (LinearLayout) findViewById(R.id.l_view_spinner);
        tv_pop_pname = (TextView) dialog.findViewById(R.id.product_name);
        tv_pop_code = (TextView) dialog.findViewById(R.id.product_code);
        tv_pop_packof = (TextView) dialog.findViewById(R.id.product_packof);
        tv_pop_mrp = (TextView) dialog.findViewById(R.id.product_mrp);
        tv_txt_mrp = (TextView) dialog.findViewById(R.id.txt_mrp_text);
        tv_pop_sellingprice = (TextView) dialog.findViewById(R.id.product_sellingprice);
        tv_total = (TextView) dialog.findViewById(R.id.txt_total);
        l_spinner = (LinearLayout) dialog.findViewById(R.id.l_spinner);
        l_spinner_text = (LinearLayout) dialog.findViewById(R.id.l_spinnertext);
        tv_pop_pname.setText(bean_product1.get(position).getPro_name());
        tv_pop_code.setText(" ( " + bean_product1.get(position).getPro_code() + " )");
        tv_pop_packof.setText(bean_product1.get(position).getPro_label());
        tv_pop_mrp.setText(bean_product1.get(position).getPro_mrp());
        tv_pop_sellingprice.setText(bean_product1.get(position).getPro_sellingprice());
        tv_pop_sellingprice.setTag(bean_product1.get(position).getPro_sellingprice());
        l_mrp = (LinearLayout) dialog.findViewById(R.id.l_mrp);
        l_sell = (LinearLayout) dialog.findViewById(R.id.l_sell);
        l_total = (LinearLayout) dialog.findViewById(R.id.l_total);
        txt_selling_price = (TextView) dialog.findViewById(R.id.txt_selling_price);
        txt_total_txt = (TextView) dialog.findViewById(R.id.txt_total_txt);
        double t1 = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * 1.0;
        double w1 = round(t1, 2);
        String str1 = String.format("%.2f", w1);
        Log.e("TOTAL1", "" + str1);
        tv_total.setText(str1);
        //  tv_total.setText(bean_product1.get(position).getPro_sellingprice().toString());
        final int[] minPackOfQty = {bean_product1.get(position).getPackQty()};
        if (kkk[0] == 1) {
            int currentQty = Integer.parseInt(qun[0]);
            int remainedQtyToFill = C.modOf(currentQty, minPackOfQty[0]);
            int k = minPackOfQty[0];
            if (currentQty < minPackOfQty[0] && remainedQtyToFill > 0)
                k = currentQty + remainedQtyToFill;
            else if (currentQty > minPackOfQty[0] && remainedQtyToFill > 0)
                k = currentQty - remainedQtyToFill + minPackOfQty[0];
            else
                k = currentQty + minPackOfQty[0];

            edt_count.setText(String.valueOf(k));
        } else {
            edt_count.setText(String.valueOf(minPackOfQty[0]));
        }
        double temp_total = Double.parseDouble(tv_total.getText().toString());
        double t = Double.parseDouble(tv_total.getText().toString()) * Double.parseDouble(edt_count.getText().toString());
        double w = round(t, 2);
        String str = String.format("%.2f", w);
        Log.e("TOTAL2", "" + str);
        tv_total.setText(str);
        int temp_count = Integer.parseInt(edt_count.getText().toString());

        double mrp = Double.parseDouble(tv_pop_mrp.getText().toString());
        double sellingprice = Double.parseDouble(tv_pop_sellingprice.getText().toString());
        setRefershData();
        if (user_data.size() != 0) {
            for (int ii = 0; ii < user_data.size(); ii++) {

                //  user_id_main = user_data.get(ii).getUser_id().toString();
                role = user_data.get(ii).getUser_type();

            }

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

        arrOptionTypeID.clear();
        arrOptionTypeName.clear();
        arrOptionTypeID = new ArrayList<String>();
        arrOptionTypeName = new ArrayList<String>();
        if (bean_productOprtions.size() != 0) {
            ArrayList<Bean_Attribute> array_attributes = new ArrayList<Bean_Attribute>();

            //Log.e("121212 ::",""+bean_productOprtions.size());

            final ArrayList<Bean_ProductOprtion> currentOptions = new ArrayList<Bean_ProductOprtion>();

            for (int c = 0; c < bean_productOprtions.size(); c++) {
                //Log.e("232323 ::",""+bean_productOprtions.get(c).getPro_id());
                //Log.e("343434 ::",""+bean_product1.get(position).getPro_id());
                if (bean_product1.get(position).getPro_id().equalsIgnoreCase(bean_productOprtions.get(c).getPro_id())) {

                    currentOptions.add(bean_productOprtions.get(c));
                    Bean_Attribute bean_attribute = new Bean_Attribute();
                    arrOptionTypeID.add(bean_productOprtions.get(c).getPro_Option_id());
                    arrOptionTypeName.add("Select " + bean_productOprtions.get(c).getPro_Option_name());
                    bean_attribute.setOption_id(bean_productOprtions.get(c).getPro_Option_id());
                    bean_attribute.setOption_name(bean_productOprtions.get(c).getPro_Option_name());

                    //OptionValue
                    bean_attribute.setValue_name(bean_productOprtions.get(c).getPro_Option_value_name());
                    //Log.e("Value Name", "------" + bean_productOprtions.get(c).getPro_Option_value_name());
                    bean_attribute.setValue_id(bean_productOprtions.get(c).getPro_Option_value_id());

                    //ProductOptionImage
                    bean_attribute.setValue_image(bean_productOprtions.get(c).getPro_Option_proimage());


                    bean_attribute.setValue_product_code(bean_productOprtions.get(c).getPro_Option_procode());
                    bean_attribute.setValue_mrp(bean_productOprtions.get(c).getPro_Option_mrp());
                    bean_attribute.setValue_selling_price(bean_productOprtions.get(c).getPro_Option_selling_price());
                    bean_attribute.setOption_pro_id(bean_productOprtions.get(c).getOption_pro_id());

                    //Log.e("Product ID", "------" + bean_attribute.getOption_pro_id());

                    //Log.e("Product ID11", "------" + bean_productOprtions.get(c).getOption_pro_id());
                    array_attributes.add(bean_attribute);
                }
            }
            if (arrOptionTypeID.size() != 0) {


                ArrayList<String> temp_array = new ArrayList<String>();
                for (int i = 0; i < arrOptionTypeID.size(); i++) {
                    temp_array.add(arrOptionTypeID.get(i));
                }
                LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionTypeID);

                arrOptionTypeID.clear();
                //Creating Arraylist without duplicate values
                arrOptionTypeID = new ArrayList<String>(listToSet);


                //Log.e("AAAABBB", "1 - " + arrOptionTypeID.size() + " 2- "+temp_array.size());


                for (int p = 0; p < arrOptionTypeID.size(); p++) {
                    //Log.e("arrOptionTypeID", "----------" + arrOptionTypeID.get(p));
                }
                for (int p = 0; p < temp_array.size(); p++) {
                    //Log.e("temp_array", "----------" + temp_array .get(p));
                }

                for (int p = 0; p < array_attributes.size(); p++) {
                    //Log.e("array_attributes", "----------" + array_attributes .get(p).getOption_id());
                }

                array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();

                for (int p = 0; p < arrOptionTypeID.size(); p++) {

                    ArrayList<Bean_Attribute> array_attribute = new ArrayList<Bean_Attribute>();

                    for (int s = 0; s < temp_array.size(); s++) {
                        if (temp_array.get(s).equalsIgnoreCase(arrOptionTypeID.get(p))) {
                            ////Log.e("", p + "---" + "option ID - " + temp_array.get(s) + "List hash ID  - " + arrOptionTypeID.get(p));
                            Bean_Attribute bean_demo = new Bean_Attribute();
                            bean_demo.setOption_id(array_attributes.get(s).getOption_id());
                            //Log.e("Option Id", "-----" + array_attributes.get(s).getOption_id());
                            bean_demo.setOption_name(array_attributes.get(s).getOption_name());
                            bean_demo.setValue_id(array_attributes.get(s).getValue_id());
                            bean_demo.setValue_name(array_attributes.get(s).getValue_name());
                            //Log.e("Value Name", "-----" + array_attributes.get(s).getValue_name());
                            bean_demo.setValue_mrp(array_attributes.get(s).getValue_mrp());
                            bean_demo.setValue_product_code(array_attributes.get(s).getValue_product_code());
                            bean_demo.setValue_selling_price(array_attributes.get(s).getValue_selling_price());
                            bean_demo.setValue_image(array_attributes.get(s).getValue_image());

                            //Log.e("bean_demo_images",""+array_attributes.get(s).getValue_image());

                            bean_demo.setOption_pro_id(array_attributes.get(s).getOption_pro_id());
                            array_attribute.add(bean_demo);
                        }
                    }
                    //Log.e("", "new bean size -- " + array_attribute.size());

                    array_attribute_main.add(array_attribute);
                    //Log.e("", "new bean size -- " + array_attribute_main.size());
                }


                for (int i = 0; i < array_attribute_main.size(); i++) {
                    Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                    bean.setValue_id("0");
                    bean.setValue_name(" ");
                    bean.setValue_mrp("00");
                    array_value.add(bean);
                }
                for (int k = 0; k < array_attribute_main.size(); k++) {

                    final TextView text = new TextView(Product_List.this);

                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                    params1.setMargins(2, 2, 2, 2);

                    text.setLayoutParams(params1);

                    text.setTextColor(Color.parseColor("#000000"));

                    text.setTextSize(12);

                    l_spinner_text.addView(text);

                    final Spinner spinner = new Spinner(Product_List.this);

                    spinner.setBackgroundResource(R.drawable.spinner_border);

                    spinner.setTag("" + k);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                    params.setMargins(2, 2, 2, 2);

                    spinner.setLayoutParams(params);

                    spinner.setAdapter(new MyAdapter(Product_List.this, R.layout.textview, array_attribute_main.get(k)));

                    l_spinner.addView(spinner);

                    array_att = new ArrayList<Bean_Attribute>();
                    array_att = array_attribute_main.get(k);


                    for (int tt = 0; tt < array_att.size(); tt++) {


                        text.setText("Select " + array_att.get(tt).getOption_name());


                        if (k == 0) {
                            option_id = array_att.get(tt).getOption_id();
                            option_name = array_att.get(tt).getOption_name();
                        } else {
                            option_id = option_id + ", " + array_att.get(tt).getOption_id();
                            option_name = option_name + ", " + array_att.get(tt).getOption_name();
                        }

                        //Log.e("Option id", "" + option_id);
                    }
                    final int finalK = k;
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   final int position1, long id) {
                            // TODO Auto-generated method stub

                            // position1=""+position;

                            minPackOfQty[0] = currentOptions.get(position1).getMinPackOfQty();

                            int pos = Integer.parseInt(spinner.getTag().toString());
                            ArrayList<Bean_Attribute> att_array = new ArrayList<Bean_Attribute>();
                            att_array = array_attribute_main.get(pos);

                            selectedProductID[0] = att_array.get(position1).getOption_pro_id();

                            boolean foundScheme = false;
                            for (int a = 0; a < bean_Schme_data.size(); a++) {
                                if (bean_Schme_data.get(a).getSchme_prod_id().equals(selectedProductID[0])) {
                                    foundScheme = true;
                                    break;
                                }
                            }

                            if (foundScheme)
                                img_offerDialog.setVisibility(View.VISIBLE);
                            else img_offerDialog.setVisibility(View.GONE);
                            //Log.e("Position",""+att_array.get(position1).getValue_name());
                            //Log.e("Position",""+att_array.get(position1).getValue_id());
                            //Log.e("Position", "" + att_array.get(position1).getValue_mrp());
                            //Log.e("Images", "" + att_array.get(position1).getValue_image().toString());

                            Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                            bean.setValue_id(att_array.get(position1).getValue_id());
                            bean.setValue_name(att_array.get(position1).getValue_name());
                            bean.setValue_mrp(att_array.get(position1).getValue_mrp());

                            array_value.set(finalK, bean);

                            //Log.e("", "ID :- " + array_value.get(finalK).getValue_id());
                            //Log.e("", "Name :- " + array_value.get(finalK).getValue_name());
                            //Log.e("", "MRP :- " + array_value.get(finalK).getValue_mrp());
                            //Log.e("", "Product Id :- " + att_array.get(position1).getOption_pro_id());
                            //Log.e("", "Product Code :- " + att_array.get(position1).getValue_product_code().toString());

                            //  tv_pop_code.setText(" ( " + att_array.get(position1).getValue_product_code().toString() + " )");

                            if (att_array.get(position1).getValue_image().equalsIgnoreCase("") || att_array.get(position1).getValue_image().equalsIgnoreCase("null")) {


                            } else {
                                //Log.e("Images", "" + att_array.get(position1).getValue_image().toString());
                                list_of_images.clear();
                                list_of_images.add(att_array.get(position1).getValue_image());
                                //adapter.notifyDataSetChanged();
                            }

                            if (att_array.get(position1).getValue_selling_price().equalsIgnoreCase("0")) {

                            } else {


                                setRefershData();

                                if (user_data.size() != 0) {
                                    for (int i = 0; i < user_data.size(); i++) {

                                        owner_id = user_data.get(i).getUser_id();

                                        role_id = user_data.get(i).getUser_type();

                                        if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                            app = new AppPrefs(Product_List.this);
                                            role_id = app.getSubSalesId();
                                            u_id = app.getSalesPersonId();
                                        } else {
                                            u_id = owner_id;
                                        }


                                    }

                                } else {
                                    user_id_main = "";
                                }


                                loadingView.show();

                                Call<GetCartItemQuantityResponse> itemQuantityResponseCall = adminAPI.getCartItemQuantity(att_array.get(position1).getOption_pro_id(), owner_id, u_id);

                                final ArrayList<Bean_Attribute> finalAtt_array = att_array;
                                itemQuantityResponseCall.enqueue(new Callback<GetCartItemQuantityResponse>() {
                                    @Override
                                    public void onResponse(Call<GetCartItemQuantityResponse> call, Response<GetCartItemQuantityResponse> response) {
                                        loadingView.dismiss();
                                        GetCartItemQuantityResponse itemQuantityResponse = response.body();
                                        Log.e("gson", "--->" + new Gson().toJson(itemQuantityResponse));
                                        if (itemQuantityResponse != null) {
                                            if (itemQuantityResponse.isStatus()) {
                                                qun[0] = itemQuantityResponse.getData().get(0).getQuantity();
                                                kkk[0] = 1;
                                            } else {
                                                qun[0] = "1";
                                                kkk[0] = 0;
                                            }

                                        } else {
                                            qun[0] = "1";
                                            kkk[0] = 0;
                                        }

                                        updateProductItem();
                                    }


                                    @Override
                                    public void onFailure(Call<GetCartItemQuantityResponse> call, Throwable throwable) {
                                        loadingView.dismiss();

                                        qun[0] = "1";
                                        kkk[0] = 0;
                                        updateProductItem();
                                        Globals.showError(throwable, getApplicationContext());


                                    }


                                    private void updateProductItem() {

                                        if (kkk[0] == 1) {
                                            int currentQty = Integer.parseInt(qun[0]);
                                            int remainedQtyToFill = C.modOf(currentQty, minPackOfQty[0]);
                                            int k = minPackOfQty[0];
                                            if (currentQty < minPackOfQty[0] && remainedQtyToFill > 0)
                                                k = currentQty + remainedQtyToFill;
                                            else if (currentQty > minPackOfQty[0] && remainedQtyToFill > 0)
                                                k = currentQty - remainedQtyToFill + minPackOfQty[0];
                                            else
                                                k = currentQty + minPackOfQty[0];

                                            edt_count.setText(String.valueOf(k));
                                        } else {
                                            edt_count.setText(String.valueOf(minPackOfQty[0]));
                                        }

                                        tv_pop_mrp.setText("" + finalAtt_array.get(position1).getValue_mrp());
                                        //   tv_pop_pname.setText(""+bean_product1.get(position).getPro_name()+"-"+att_array.get(position1).getValue_name().toString());
                                        tv_pop_sellingprice.setText("" + finalAtt_array.get(position1).getValue_selling_price());
                                        tv_pop_pname.setTag("" + finalAtt_array.get(position1).getOption_pro_id());
                                        if (finalAtt_array.get(position1).getValue_product_code().equalsIgnoreCase("null") || finalAtt_array.get(position1).getValue_product_code().equalsIgnoreCase("")) {
                                            //Log.e("121212",""+att_array.get(position1).getValue_product_code().toString());
                                            tv_pop_code.setText(" (" + p_code + ")");


                                        } else {
                                            //Log.e("131313",""+att_array.get(position1).getValue_product_code().toString());
                                            tv_pop_code.setText(" (" + finalAtt_array.get(position1).getValue_product_code() + ")");
                                            ArrayList<Bean_ProductCart> array_product_cart1 = new ArrayList<Bean_ProductCart>();
                                            array_product_cart1 = db.is_product_in_cart(tv_pop_code.getText().toString());

                                            if (array_product_cart1.size() == 0) {

                                            } else {

                                                int temp_count = Integer.parseInt(edt_count.getText().toString());
                                                int temp_total_count = temp_count + Integer.parseInt(array_product_cart1.get(0).getPro_qty());
                                                double temp_total = Double.parseDouble(tv_total.getText().toString());
                                                edt_count.setText("" + temp_total_count);
                                                //   tv_total.setText("" + (temp_total + Float.parseFloat(array_product_cart1.get(0).getPro_total().toString().replace("", ""))));
                                                //result_holder.tvproduct_code.setText("ABC");
                                            }
                                        }
                                        tv_pop_sellingprice.setTag(finalAtt_array.get(position1).getValue_selling_price());

                                        if (edt_count.getText().toString().equalsIgnoreCase("1")) {
                                            double finalValue = Double.parseDouble(finalAtt_array.get(position1).getValue_selling_price());
                                            double w = round(finalValue, 2);
                                            String str = String.format("%.2f", w);
                                            Log.e("TOTAL3", "" + str);
                                            tv_total.setText(str);

                                        } else {
                                            int counter = Integer.parseInt(edt_count.getText().toString());
                                            double amt = Double.parseDouble(tv_pop_sellingprice.getText().toString());
                                            double total = amt * counter;
                                            //float finalValue = (float)(Math.round( total * 100 ) / 100);
                                            double w = round(total, 2);
                                            String str = String.format("%.2f", w);
                                            Log.e("TOTAL4", "" + str);
                                            tv_total.setText(str);

                                        }


                                    }


                                });


                                // product_id = tv_pop_pname.getTag().toString();
                                /*List<NameValuePair> para = new ArrayList<NameValuePair>();
                                para.add(new BasicNameValuePair("product_id", att_array.get(position1).getOption_pro_id()));
                                para.add(new BasicNameValuePair("owner_id", owner_id));
                                para.add(new BasicNameValuePair("user_id", u_id));
                                //Log.e("111111111",""+product_id);
                                //Log.e("222222222",""+owner_id);
                                //Log.e("333333333",""+u_id);
                                isNotDone = true;
                                new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                while (isNotDone) {

                                }
                                isNotDone = true;

                                String json = jsonData;

                                //String json=GetProductDetailByQty(para);

                                try {


                                    //System.out.println(json);

                                    if (json == null
                                            || (json.equalsIgnoreCase(""))) {
                    *//*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*//*
                                        Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
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
*/

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });

                }


            }


        } else {

        }


        dialog.show();
        //   result_holder.btn_buyonline.setEnabled(true);
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
                        //int minPackOfQty = bean_product1.get(position).getPackQty();
                        if (s < minPackOfQty[0]) {
                            s = 0;
                        } else {
                            int toCutOutQty = s % minPackOfQty[0];

                            Log.e("toCutOut Minus", "-->" + toCutOutQty);

                            if (toCutOutQty > 0)
                                s = s - toCutOutQty;
                            else
                                s = s - minPackOfQty[0];
                        }
                        //s = s - 1;
                        edt_count.setText(String.valueOf(s));
                        int counter = s;
                        double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                        double total = amt * counter;
                        double finalValue = (double) (Math.round(total * 100) / 100);
                        double w = round(total, 2);
                        String str = String.format("%.2f", w);
                        Log.e("TOTAL7", "" + str);
                        tv_total.setText(str);
                    }
                }
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
                //int minPackOfQty = bean_product1.get(position).getPackQty();
                if (s == 0) {
                    s = s + minPackOfQty[0];
                } else if (s > 0) {

                    int remainder = C.modOf(minPackOfQty[0], s);

                    if (s < minPackOfQty[0]) {

                        if (remainder > 0) {
                            s = s + (minPackOfQty[0] - s);
                        } else {
                            s = s + minPackOfQty[0];
                        }
                    } else if (s >= minPackOfQty[0]) {
                        if (remainder > 0) {
                            s = s - remainder + minPackOfQty[0];
                        } else {
                            s = s + minPackOfQty[0];
                        }
                    }
                }
                //s = s + 1;
                edt_count.setText(String.valueOf(s));
                int counter = s;
                double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                double total = amt * counter;
                //float finalValue = (float)(Math.round( total * 100 ) / 100);
                double w = round(total, 2);
                String str = String.format("%.2f", w);
                Log.e("TOTAL8", "" + str);
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
                    buy_cart.setEnabled(false);
                    tv_total.setText("0.00");
                } else {
                    buy_cart.setEnabled(true);
                    //int minPackOfQty = bean_product1.get(position).getPackQty();

                                    /*if(qty>=minPackOfQty[0] && C.modOf(qty,minPackOfQty[0])==0){
                                        String productpricce = tv_pop_sellingprice.getTag().toString();
                                        amount1 = Double.parseDouble(s.toString());
                                        amount1 = Double.parseDouble(productpricce) * amount1;
                                        //float finalValue = (float)(Math.round( amount1 * 100 ) / 100);
                                        double w = round(amount1, 2);
                                        String str = String.format("%.2f", w);
                                        Log.e("TOTAL12", "" + str);
                                        tv_total.setText(str);
                                    }
                                    else{
                                        tv_total.setText("");
                                    }*/

                    String productpricce = tv_pop_sellingprice.getTag().toString();
                    amount1 = Double.parseDouble(s.toString());
                    amount1 = Double.parseDouble(productpricce) * amount1;
                    //float finalValue = (float)(Math.round( amount1 * 100 ) / 100);
                    double w = round(amount1, 2);
                    String str = String.format("%.2f", w);
                    Log.e("TOTAL12", "" + str);
                    tv_total.setText(str);

                    ArrayList<Bean_schemeData> currentProductScheme = new ArrayList<Bean_schemeData>();

                    for (int i = 0; i < bean_Schme_data.size(); i++) {
                        if (bean_Schme_data.get(i).getSchme_prod_id().equals(selectedProductID[0])) {
                            currentProductScheme.add(bean_Schme_data.get(i));
                        }
                    }

                    if (currentProductScheme.size() > 0) {

                        int selectedPos = 0;

                        for (int i = 0; i < currentProductScheme.size(); i++) {
                            int schemeQty = Integer.parseInt(currentProductScheme.get(i).getSchme_qty());
                            if (qty < schemeQty) {
                                selectedPos = i;
                                break;
                            } else {
                                selectedPos = currentProductScheme.size() - 1;
                            }
                        }

                        txt_availableScheme.setText(currentProductScheme.get(selectedPos).getSchme_name());
                    } else txt_availableScheme.setText("");


                }

                                /*if(Integer.parseInt(edt_count.getText().toString()) == 0 ){
                                    String productpricce = tv_pop_sellingprice.getTag().toString();
                                    edt_count.setText("1");
                                    double amt = Double.parseDouble(productpricce);
                                    double w = round(amt , 2);
                                    String str = String.format("%.2f", w);
                                    Log.e("TOTAL10",""+str);
                                    tv_total.setText(str);
                                  //  tv_total.setText(productpricce);
                                }
                                else if(edt_count.getText().toString().equals("")) {
                                    // Do nothing here
                                    String productpricce = tv_pop_sellingprice.getTag().toString();
                                    edt_count.setText("1");
                                    double amt = Double.parseDouble(productpricce);
                                    double w = round(amt , 2);
                                    String str = String.format("%.2f", w);
                                    Log.e("TOTAL11",""+str);
                                    tv_total.setText(str);
                                   // tv_total.setText(productpricce);
                                }
                                else if (s.length() != 0) { // do your work here }
                                    String productpricce = tv_pop_sellingprice.getTag().toString();
                                    amount1 = Double.parseDouble(s.toString());
                                    amount1 = Double.parseDouble(productpricce) * amount1;
                                    //float finalValue = (float)(Math.round( amount1 * 100 ) / 100);
                                    double w = round(amount1 , 2);
                                    String str = String.format("%.2f", w);
                                    Log.e("TOTAL12",""+str);
                                    tv_total.setText(str);


                                } else {
                                    String productpricce = tv_pop_sellingprice.getTag().toString();
                                    edt_count.setText("1");
                                    double amt = Double.parseDouble(productpricce);
                                    double w = round(amt , 2);
                                    String str = String.format("%.2f", w);
                                    Log.e("TOTAL13",""+str);
                                    tv_total.setText(str);
                                 //ftvg   tv_total.setText(productpricce);
						*//*if(txt_count.getText().toString().equalsIgnoreCase("0")){
                            txt_amount.setEnabled(false);
						}
						*//*

                                }*/
            }
        });

        buy_cart.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                if (user_data.size() == 0) {

                    Intent i = new Intent(Product_List.this, LogInPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);


                } else {

                    String enteredQty = edt_count.getText().toString().trim();
                    int currentQty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);
                    //int minPackOfQty = bean_product1.get(position).getPackQty();

                    if (currentQty < minPackOfQty[0] || C.modOf(currentQty, minPackOfQty[0]) > 0) {

                        C.showMinPackAlert(Product_List.this, minPackOfQty[0]);
                        //Globals.Toast(getApplicationContext(), "Please enter quantity in multiple of "+minPackOfQty+" or tap + / - button");
                        Log.e("Mod Rem", "-->" + C.modOf(currentQty, minPackOfQty[0]));
                    } else {

                        product_id = tv_pop_pname.getTag().toString();
                        String qty = edt_count.getText().toString();


                        bean_product_schme.clear();
                        bean_schme.clear();
                        loadingView.show();


                        Call<GetSchemeDetailResponse> schemeDetailResponseCall = adminAPI.getSchemeDetailResponse(product_id, user_id_main, qty);
                        schemeDetailResponseCall.enqueue(new Callback<GetSchemeDetailResponse>() {
                            @Override
                            public void onResponse(Call<GetSchemeDetailResponse> call, Response<GetSchemeDetailResponse> response) {
                                loadingView.dismiss();
                                GetSchemeDetailResponse schemeDetailResponse = response.body();
                                if (schemeDetailResponse != null) {
                                    if (schemeDetailResponse.isStatus()) {
                                        bean_product_schme.add(schemeDetailResponse.getData().getGetProduct());
                                        for (int i = 0; i < schemeDetailResponse.getData().getGetProduct().getProductOption().size(); i++) {
                                            Bean_ProductOprtion beanoption = new Bean_ProductOprtion();
                                            // arrOptionType=new ArrayList<String>();


                                            //Option
                                            beanoption.setPro_Option_id(schemeDetailResponse.getData().getGetProduct().getOptionID());
                                            beanoption.setPro_Option_name(schemeDetailResponse.getData().getGetProduct().getOptionName());


                                            //OptionValue
                                            beanoption.setPro_Option_value_name(schemeDetailResponse.getData().getGetProduct().getOptionValueName());
                                            beanoption.setPro_Option_value_id(schemeDetailResponse.getData().getGetProduct().getOptionValueID());


                                            bean_Oprtions.add(beanoption);
                                        }
                                        bean_schme.add(schemeDetailResponse.getData().getScheme());


                                    }
                                }
                                proceedForNextStepAfterScheme();

                            }

                            @Override
                            public void onFailure(Call<GetSchemeDetailResponse> call, Throwable t) {
                                loadingView.dismiss();
                                dialog.dismiss();
                                Globals.showError(t, getApplicationContext());

                            }

                            void proceedForNextStepAfterScheme() {

                                Log.e("proceed", "-->" + bean_product_schme.size());

                                if (bean_product_schme.size() == 0) {
                                    //Log.e("555555555",""+bean_product_schme.size());
                                    Bean_ProductCart bean = new Bean_ProductCart();
                                    String value_id = "";
                                    String value_name = "";


                                    for (int i = 0; i < array_value.size(); i++) {
                                        if (i == 0) {
                                            value_name = array_value.get(i).getValue_name();
                                            value_id = array_value.get(i).getValue_id();
                                        } else {
                                            value_id = value_id + ", " + array_value.get(i).getValue_id();
                                            value_name = value_name + ", " + array_value.get(i).getValue_name();

                                        }
                                    }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                    bean.setPro_id(tv_pop_pname.getTag().toString());
                                    //Log.e("-- ", "producttt Id " + tv_pop_pname.getTag().toString());
                                    // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                    bean.setPro_cat_id(Catid);
                                    //Log.e("Cat_ID....", "" + Catid);
                                    if (list_of_images.size() == 0) {
                                        bean.setPro_Images(bean_product1.get(position).getPro_image());
                                    } else {
                                        bean.setPro_Images(list_of_images.get(0));
                                    }
                                    // bean.setPro_Images(list_of_images.get(position));
                                    //bean.setPro_code(result_holder.tvproduct_code.getText().toString());
                                    bean.setPro_code(tv_pop_code.getText().toString().trim());
                                    //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                    bean.setPro_name(tv_pop_pname.getText().toString());
                                    bean.setPro_qty(edt_count.getText().toString());
                                    bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                    bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString());
                                    bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                    bean.setPro_Option_id(option_id);
                                    //Log.e("-- ", "Option Id " + option_id);

                                    bean.setPro_Option_name(option_name);
                                    //Log.e("-- ", "Option Name " + option_name);

                                    bean.setPro_Option_value_id(value_id);

                                    //Log.e("", "Value Name " + value_id);
                                    bean.setPro_Option_value_name(value_name);
                                    //Log.e("", "Value Name " + value_name);

                                    bean.setPro_total(tv_total.getText().toString());
                                    bean.setPro_schme("");

                                    if (jarray_cart.length() > 0)
                                        jarray_cart = new JSONArray();
                                    for (int i = 0; i < 1; i++) {
                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", u_id);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", tv_pop_pname.getTag().toString());
                                            jobject.put("category_id", Catid);
                                            jobject.put("name", tv_pop_pname.getText().toString());
                                            String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                            String aString = newString.trim().replace(")", "");
                                            jobject.put("pro_code", aString.trim());
                                            jobject.put("quantity", edt_count.getText().toString());
                                            jobject.put("mrp", tv_pop_mrp.getText().toString());
                                            jobject.put("selling_price", tv_pop_sellingprice.getText().toString());
                                            jobject.put("option_id", option_id);
                                            jobject.put("option_name", option_name);
                                            jobject.put("option_value_id", value_id);
                                            jobject.put("option_value_name", value_name);
                                            jobject.put("item_total", tv_total.getText().toString());
                                            jobject.put("pro_scheme", " ");
                                            jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                            jobject.put("scheme_id", " ");
                                            jobject.put("scheme_title", " ");
                                            jobject.put("scheme_pack_id", " ");
                                            if (list_of_images.size() == 0) {
                                                jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                            } else {
                                                jobject.put("prod_img", list_of_images.get(0));
                                                //   bean.setPro_Images(list_of_images.get(0).toString());
                                            }


                                            jarray_cart.put(jobject);
                                        } catch (JSONException e) {

                                        }


                                    }
                                    Log.e("Data 2224", jarray_cart.toString());
                                    array_value.clear();
                                    if (kkk[0] == 1) {
                                        Log.e("04","-->");
                                        new Edit_Product().execute();

                                    } else {

                                        new Add_Product().execute();
                                        Log.e("4","-->");
                                    }

                                    //db.Add_Product_cart(bean);



                                      /*  Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                                        Intent i = new Intent(Product_List.this, Product_List.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);*/


                                } else {
                                    //Log.e("Type ID : ",""+bean_schme.get(0).getType_id().toString());
                                    if (bean_schme.get(0).getType_id().equalsIgnoreCase("1")) {

                                        //Log.e("66666666666", "" + bean_product_schme.size());
                                        Bean_ProductCart bean = new Bean_ProductCart();
                                        String value_id = "";
                                        String value_name = "";


                                        for (int i = 0; i < array_value.size(); i++) {
                                            if (i == 0) {
                                                value_name = array_value.get(i).getValue_name();
                                                value_id = array_value.get(i).getValue_id();
                                            } else {
                                                value_id = value_id + ", " + array_value.get(i).getValue_id();
                                                value_name = value_name + ", " + array_value.get(i).getValue_name();

                                            }
                                        }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                        bean.setPro_id(tv_pop_pname.getTag().toString());
                                        //Log.e("-- ", "producttt Id " + tv_pop_pname.getTag().toString());
                                        // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                        bean.setPro_cat_id(Catid);
                                        //Log.e("Cat_ID....", "" + Catid);
                                        if (list_of_images.size() == 0) {
                                            bean.setPro_Images(bean_product1.get(position).getPro_image());
                                        } else {
                                            bean.setPro_Images(list_of_images.get(0));
                                        }
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
                                        String sell = tv_pop_sellingprice.getText().toString();

                                        double se = Double.parseDouble(tv_pop_sellingprice.getText().toString()) * buyqu;

                                        double se1 = buyqu + getqu;

                                        double fse = se / se1;
                                        double w = round(fse, 2);
                                        sell = String.format("%.2f", w);
                                        //sell = String.valueOf(fse);

                                        int fqu = qu + (int) getqu;

                                        bean.setPro_qty(String.valueOf(fqu));
                                        bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                        bean.setPro_sellingprice(sell);
                                        bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                        bean.setPro_Option_id(option_id);
                                        //Log.e("-- ", "Option Id " + option_id);

                                        bean.setPro_Option_name(option_name);
                                        //Log.e("-- ", "Option Name " + option_name);

                                        bean.setPro_Option_value_id(value_id);

                                        //Log.e("", "Value Name " + value_id);
                                        bean.setPro_Option_value_name(value_name);
                                        //Log.e("", "Value Name " + value_name);

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
                                                jobject.put("product_id", tv_pop_pname.getTag().toString());
                                                jobject.put("category_id", Catid);
                                                jobject.put("name", tv_pop_pname.getText().toString());
                                                String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                String aString = newString.trim().replace(")", "");
                                                jobject.put("pro_code", aString.trim());
                                                jobject.put("quantity", String.valueOf(fqu));
                                                jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                jobject.put("selling_price", sell);
                                                jobject.put("option_id", option_id);
                                                jobject.put("option_name", option_name);
                                                jobject.put("option_value_id", value_id);
                                                jobject.put("option_value_name", value_name);
                                                double f = fqu * Double.parseDouble(sell);
                                                double w1 = round(f, 2);
                                                String str = String.format("%.2f", w1);
                                                jobject.put("item_total", str);
                                                jobject.put("pro_scheme", " ");
                                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                if (list_of_images.size() == 0) {
                                                    jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                    // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                                } else {
                                                    jobject.put("prod_img", list_of_images.get(0));
                                                    //   bean.setPro_Images(list_of_images.get(0).toString());
                                                }


                                                jarray_cart.put(jobject);
                                            } catch (JSONException e) {

                                            }


                                        }
                                        Log.e("Data 2391", jarray_cart.toString());
                                        array_value.clear();
                                        if (kkk[0] == 1) {
                                            Log.e("05","-->");
                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("5","-->");
                                        }


                                    } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {

                                        //Log.e("66666666666", "" + bean_product_schme.size());
                                        Bean_ProductCart bean = new Bean_ProductCart();
                                        String value_id = "";
                                        String value_name = "";


                                        for (int i = 0; i < array_value.size(); i++) {
                                            if (i == 0) {
                                                value_name = array_value.get(i).getValue_name();
                                                value_id = array_value.get(i).getValue_id();
                                            } else {
                                                value_id = value_id + ", " + array_value.get(i).getValue_id();
                                                value_name = value_name + ", " + array_value.get(i).getValue_name();

                                            }
                                        }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                        bean.setPro_id(tv_pop_pname.getTag().toString());
                                        //Log.e("-- ", "producttt Id " + tv_pop_pname.getTag().toString());
                                        // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                        bean.setPro_cat_id(Catid);
                                        //Log.e("Cat_ID....", "" + Catid);
                                        if (list_of_images.size() == 0) {
                                            bean.setPro_Images(bean_product1.get(position).getPro_image());
                                        } else {
                                            bean.setPro_Images(list_of_images.get(0));
                                        }
                                        // bean.setPro_Images(list_of_images.get(position));
                                        //bean.setPro_code(result_holder.tvproduct_code.getText().toString());
                                        bean.setPro_code(tv_pop_code.getText().toString().trim());
                                        //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                        bean.setPro_name(tv_pop_pname.getText().toString());
                                        bean.setPro_qty(edt_count.getText().toString());
                                        bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                        bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString());
                                        bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                        bean.setPro_Option_id(option_id);
                                        //Log.e("-- ", "Option Id " + option_id);

                                        bean.setPro_Option_name(option_name);
                                        //Log.e("-- ", "Option Name " + option_name);

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
                                            jobject.put("product_id", tv_pop_pname.getTag().toString());
                                            jobject.put("category_id", Catid);
                                            jobject.put("name", tv_pop_pname.getText().toString());
                                            String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                            String aString = newString.trim().replace(")", "");
                                            jobject.put("pro_code", aString.trim());
                                            jobject.put("quantity", edt_count.getText().toString());
                                            jobject.put("mrp", tv_pop_mrp.getText().toString());
                                            jobject.put("selling_price", tv_pop_sellingprice.getText().toString());
                                            jobject.put("option_id", option_id);
                                            jobject.put("option_name", option_name);
                                            jobject.put("option_value_id", value_id);
                                            jobject.put("option_value_name", value_name);
                                            jobject.put("item_total", tv_total.getText().toString());
                                            jobject.put("pro_scheme", " ");
                                            jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                            jobject.put("scheme_id", " ");
                                            jobject.put("scheme_title", " ");
                                            jobject.put("scheme_pack_id", " ");
                                            if (list_of_images.size() == 0) {
                                                jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                            } else {
                                                jobject.put("prod_img", list_of_images.get(0));
                                                //   bean.setPro_Images(list_of_images.get(0).toString());
                                            }


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


                                        for (int i = 0; i < array_value.size(); i++) {
                                            if (i == 0) {
                                                value_name1 = array_value.get(i).getValue_name();
                                                value_id1 = array_value.get(i).getValue_id();
                                            } else {
                                                value_id1 = value_id1 + ", " + array_value.get(i).getValue_id();
                                                value_name1 = value_name1 + ", " + array_value.get(i).getValue_name();

                                            }
                                        }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                        bean_s.setPro_id(bean_product_schme.get(0).getPro_id());
                                        //Log.e("-- ", "producttt Id " + tv_pop_pname.getTag().toString());
                                        // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                        bean_s.setPro_cat_id(bean_product_schme.get(0).getPro_cat_id());
                                        //Log.e("Cat_ID....", "" + Catid);
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
                                                /*if (b > maxqu) {
                                                    b = maxqu;
                                                } else {
                                                    b = b;
                                                }*/


                                        bean_s.setPro_qty(String.valueOf(b));


                                        bean_s.setPro_mrp("0");
                                        bean_s.setPro_sellingprice("0");
                                        bean_s.setPro_shortdesc(bean_product_schme.get(0).getPro_label());
                                        bean_s.setPro_Option_id(option_id);
                                        //Log.e("-- ", "Option Id " + option_id);

                                        bean_s.setPro_Option_name(option_name);
                                        //Log.e("-- ", "Option Name " + option_name);

                                        bean_s.setPro_Option_value_id(value_id);

                                        //Log.e("", "Value Name " + value_id);
                                        bean_s.setPro_Option_value_name(value_name);
                                        //Log.e("", "Value Name " + value_name);
                                        bean.setPro_schme_name(bean_schme.get(0).getScheme_name());
                                        bean_s.setPro_total("0");
                                        bean_s.setPro_schme("" + tv_pop_pname.getTag().toString());
                                           /* if (String.valueOf((int) b).toString().equalsIgnoreCase("0")) {
                                                db.delete_product_from_cart_list_schme(tv_pop_pname.getTag().toString());
                                            } else {

                                                db.update_product_from_Schme(tv_pop_pname.getTag().toString(), String.valueOf(b));
                                            }*/

                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", u_id);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                            jobject.put("category_id",Catid);
                                            jobject.put("name", bean_product_schme.get(0).getPro_name());
                                            jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                            jobject.put("quantity", String.valueOf(b));
                                            jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                            //Log.e("D1D1",""+bean_product_schme.get(0).getPro_mrp());
                                            jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                            //Log.e("E1E1",""+bean_product_schme.get(0).getPro_sellingprice());
                                            jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id());
                                            jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name());
                                            jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id());
                                            jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name());
                                            jobject.put("item_total", "0");
                                            jobject.put("pro_scheme", "" + tv_pop_pname.getTag().toString());
                                            jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                            jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                            //Log.e("C1C1",""+bean_schme.get(0).getScheme_id());
                                            jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                            jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                            jobject.put("prod_img", bean_product_schme.get(0).getPro_image());


                                            jarray_cart.put(jobject);
                                        } catch (JSONException e) {

                                        }

                                        Log.e("Data 2630", jarray_cart.toString());


                                        // db.Add_Product_cart_scheme(bean_s);

                                        array_value.clear();
                                        if (kkk[0] == 1) {
                                            Log.e("06","-->");
                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("6","-->");
                                        }

                                    } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("3")) {

                                        //Log.e("66666666666", "" + bean_product_schme.size());
                                        Bean_ProductCart bean = new Bean_ProductCart();
                                        String value_id = "";
                                        String value_name = "";


                                        for (int i = 0; i < array_value.size(); i++) {
                                            if (i == 0) {
                                                value_name = array_value.get(i).getValue_name();
                                                value_id = array_value.get(i).getValue_id();
                                            } else {
                                                value_id = value_id + ", " + array_value.get(i).getValue_id();
                                                value_name = value_name + ", " + array_value.get(i).getValue_name();

                                            }
                                        }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                        bean.setPro_id(tv_pop_pname.getTag().toString());
                                        //Log.e("-- ", "producttt Id " + tv_pop_pname.getTag().toString());
                                        // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                        bean.setPro_cat_id(Catid);
                                        //Log.e("Cat_ID....", "" + Catid);
                                        if (list_of_images.size() == 0) {
                                            bean.setPro_Images(bean_product1.get(position).getPro_image());
                                        } else {
                                            bean.setPro_Images(list_of_images.get(0));
                                        }
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
                                        String sell = tv_pop_sellingprice.getText().toString();

                                        String disc = bean_schme.get(0).getDisc_per();

                                        double se = Double.parseDouble(tv_pop_sellingprice.getText().toString()) * Double.parseDouble(bean_schme.get(0).getDisc_per());

                                        double se1 = se / 100;

                                        double fse = Double.parseDouble(tv_pop_sellingprice.getText().toString()) - se1;
                                        double w1 = round(fse, 2);
                                        sell = String.format("%.2f", w1);
                                        // sell = String.valueOf(fse);

                                        //  int fqu = qu +Math.round(getqu);

                                        bean.setPro_qty(edt_count.getText().toString());
                                        bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                        bean.setPro_sellingprice(sell);
                                        bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                        bean.setPro_Option_id(option_id);
                                        //Log.e("-- ", "Option Id " + option_id);

                                        bean.setPro_Option_name(option_name);
                                        //Log.e("-- ", "Option Name " + option_name);

                                        bean.setPro_Option_value_id(value_id);

                                        //Log.e("", "Value Name " + value_id);
                                        bean.setPro_Option_value_name(value_name);
                                        //Log.e("", "Value Name " + value_name);

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
                                                jobject.put("product_id", tv_pop_pname.getTag().toString());
                                                jobject.put("category_id", Catid);
                                                jobject.put("name", tv_pop_pname.getText().toString());
                                                String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                String aString = newString.trim().replace(")", "");
                                                jobject.put("pro_code", aString.trim());
                                                jobject.put("quantity", edt_count.getText().toString());
                                                jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                jobject.put("selling_price", sell);
                                                jobject.put("option_id", option_id);
                                                jobject.put("option_name", option_name);
                                                jobject.put("option_value_id", value_id);
                                                jobject.put("option_value_name", value_name);
                                                double f = Double.parseDouble(edt_count.getText().toString()) * Double.parseDouble(sell);
                                                double w = round(f, 2);
                                                String str = String.format("%.2f", w);
                                                jobject.put("item_total", str);
                                                jobject.put("pro_scheme", " ");
                                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                if (list_of_images.size() == 0) {
                                                    jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                    // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                                } else {
                                                    jobject.put("prod_img", list_of_images.get(0));
                                                    //   bean.setPro_Images(list_of_images.get(0).toString());
                                                }


                                                jarray_cart.put(jobject);
                                            } catch (JSONException e) {

                                            }


                                        }

                                        Log.e("Data 2692", jarray_cart.toString());
                                        array_value.clear();
                                        if (kkk[0] == 1) {
                                            Log.e("07","-->");
                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("7","-->");
                                        }
                                    }


                                       /* Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                                        Intent i = new Intent(Product_List.this, Product_List.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);*/
                                }

                            }
                        });



                        /*List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("product_id", product_id));
                        params.add(new BasicNameValuePair("user_id", user_id_main));
                        params.add(new BasicNameValuePair("product_buy_qty", qty));
                        //Log.e("111111111",""+product_id);
                        //Log.e("222222222",""+user_id_main);
                        //Log.e("333333333",""+qty);
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
                    *//*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*//*
                                Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
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
                          *//*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*//*


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


                                    //loadingView.dismiss();

                                }

                            }
                        } catch (Exception j) {
                            j.printStackTrace();
                            Log.e("json exce", j.getMessage());
                        }*/
                        //Log.e("45454545",""+bean_product_schme.size());


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

    static class ResultHolder {

        LinearLayout l, layout_prices;
        TextView tvproduct_name, tvproduct_code, tv_product_mrp, tv_product_sellingprice, tvproduct_packof, txt_mrp, txt_selling, txt_pack, off_tag;
        Button btn_buyonline, BTN_wheretobuy_list, btn_enquiry;
        ImageView img_photo, img_wish, img_shopping, img_offer;
        TextView nav;

    }

    public class CustomResultAdapterDoctor extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bean_product1.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            // result_holder = null;

            result_holder = new ResultHolder();

            //   if (convertView == null) {

            convertView = vi.inflate(R.layout.product_list_single, null);

            //  }

            result_holder.tvproduct_name = (TextView) convertView
                    .findViewById(R.id.product_name);
            result_holder.tvproduct_code = (TextView) convertView
                    .findViewById(R.id.product_code);
            result_holder.tvproduct_packof = (TextView) convertView
                    .findViewById(R.id.product_packof);
            result_holder.tv_product_mrp = (TextView) convertView
                    .findViewById(R.id.product_mrp);
            result_holder.tv_product_sellingprice = (TextView) convertView
                    .findViewById(R.id.product_sellingprice);
            result_holder.img_photo = (ImageView) convertView
                    .findViewById(R.id.imageView2);
            result_holder.img_wish = (ImageView) convertView
                    .findViewById(R.id.img_wish);
            result_holder.img_shopping = (ImageView) convertView
                    .findViewById(R.id.img_shooping);
            result_holder.txt_mrp = (TextView) convertView
                    .findViewById(R.id.txt_mrp_main);
            result_holder.btn_buyonline = (Button) convertView
                    .findViewById(R.id.btn_buyonline);
            result_holder.BTN_wheretobuy_list = (Button) convertView
                    .findViewById(R.id.BTN_wheretobuy_list);
            result_holder.txt_selling = (TextView) convertView
                    .findViewById(R.id.txt_selling);
            result_holder.txt_pack = (TextView) convertView
                    .findViewById(R.id.txt_pack);
            result_holder.off_tag = (TextView) convertView.findViewById(R.id.off_tag);
            result_holder.img_offer = (ImageView) convertView.findViewById(R.id.img_offer);
            result_holder.btn_enquiry = (Button) convertView.findViewById(R.id.btn_enquiry);

            result_holder.BTN_wheretobuy_list.setTag(bean_product1.get(position).getPro_id());

            result_holder.layout_prices = (LinearLayout) convertView.findViewById(R.id.layout_prices);

            if (!whereToBuyVisibility) {
                result_holder.BTN_wheretobuy_list.setVisibility(View.GONE);
            } else {
                result_holder.BTN_wheretobuy_list.setVisibility(View.VISIBLE);
            }


            setRefershData();


            if (user_data.size() != 0) {
                for (int i = 0; i < user_data.size(); i++) {

                    rolee = user_data.get(i).getUser_type();

                }

            } else {
                user_id_main = "";
            }

            if (rolee.equals(C.ADMIN) || rolee.equalsIgnoreCase("6") || rolee.equalsIgnoreCase("7")) {
                result_holder.img_wish.setVisibility(View.GONE);
                result_holder.img_shopping.setVisibility(View.GONE);
            } else {
                result_holder.img_wish.setVisibility(View.VISIBLE);
                result_holder.img_shopping.setVisibility(View.VISIBLE);
            }


            //Log.e("product_id",""+result_holder.BTN_wheretobuy_list.getTag().toString());

            result_holder.BTN_wheretobuy_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    app = new AppPrefs(Product_List.this);
                    app.setProduct_Sort("");
                    app.setFilter_Option("");
                    app.setFilter_SubCat("");
                    app.setFilter_Cat("");
                    app.setFilter_Sort("");
                    db = new DatabaseHandler(Product_List.this);
                    db.Delete_ALL_table();
                    db.close();
                    Intent i = new Intent(Product_List.this, Where_To_Buy.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });

            result_holder.tvproduct_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    app = new AppPrefs(Product_List.this);
                    app.setproduct_id(bean_product1.get(position).getPro_id());
                    app.setRef_Detail("ProductList");
                    Intent i = new Intent(Product_List.this, BTLProduct_Detail.class);

                    //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });

            result_holder.tvproduct_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    app = new AppPrefs(Product_List.this);
                    app.setproduct_id(bean_product1.get(position).getPro_id());
                    app.setRef_Detail("ProductList");
                    Intent i = new Intent(Product_List.this, BTLProduct_Detail.class);

                    //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });

            result_holder.img_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    app = new AppPrefs(Product_List.this);
                    app.setproduct_id(bean_product1.get(position).getPro_id());
                    app.setRef_Detail("ProductList");
                    Intent i = new Intent(Product_List.this, BTLProduct_Detail.class);

                    //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });


            if (user_data.size() != 0) {
                pro_id = result_holder.BTN_wheretobuy_list.getTag().toString();
                setRefershData();


                if (user_data.size() != 0) {
                    for (int i = 0; i < user_data.size(); i++) {

                        rolee = user_data.get(i).getUser_type();

                    }

                } else {
                    user_id_main = "";
                }
                if (rolee.equals(C.ADMIN) || rolee.equalsIgnoreCase("6") || rolee.equalsIgnoreCase("7")) {
                    result_holder.img_wish.setVisibility(View.GONE);
                    result_holder.img_shopping.setVisibility(View.GONE);
                } else {
                    result_holder.img_wish.setVisibility(View.VISIBLE);
                    result_holder.img_shopping.setVisibility(View.VISIBLE);
                }
                result_holder.txt_mrp.setVisibility(View.VISIBLE);
                result_holder.btn_buyonline.setVisibility(View.VISIBLE);
                result_holder.tvproduct_packof.setVisibility(View.VISIBLE);
                result_holder.tv_product_sellingprice.setVisibility(View.VISIBLE);
                result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                //  result_holder.off_tag.setVisibility(View.VISIBLE);
                result_holder.txt_selling.setVisibility(View.VISIBLE);
                // result_holder.BTN_wheretobuy_list.setVisibility(View.VISIBLE);
                result_holder.txt_pack.setVisibility(View.VISIBLE);


                if (WishList.size() != 0) {

                    String st = WishList.toString();
                    //Log.e("12122", st);
                    //Log.e("pro_id",""+pro_id);
                    if (WishList.contains(pro_id)) {
                        result_holder.img_wish.setImageResource(R.drawable.whishlist_fill);
                        result_holder.img_wish.setTag("1");
                        //Log.e("1111", "111");
                    } else {
                        result_holder.img_wish.setImageResource(R.drawable.whishlist);
                        result_holder.img_wish.setTag("0");
                        //Log.e("0000","000");
                    }


                } else if (WishList.size() == 0) {
                    result_holder.img_wish.setImageResource(R.drawable.whishlist);
                    result_holder.img_wish.setTag("0");
                    //Log.e("0000", "000");
                }

                for (int i = 0; i < user_data.size(); i++) {

                    user_id = user_data.get(i).getUser_id();

                }

            } else {
                result_holder.img_wish.setVisibility(View.GONE);
                result_holder.img_shopping.setVisibility(View.GONE);
                result_holder.txt_mrp.setVisibility(View.VISIBLE);
                result_holder.btn_buyonline.setVisibility(View.VISIBLE);
                result_holder.tvproduct_packof.setVisibility(View.VISIBLE);
                result_holder.tv_product_sellingprice.setVisibility(View.VISIBLE);
                result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                //  result_holder.off_tag.setVisibility(View.VISIBLE);
                result_holder.txt_selling.setVisibility(View.VISIBLE);
                //  result_holder.BTN_wheretobuy_list.setVisibility(View.GONE);
                result_holder.txt_pack.setVisibility(View.VISIBLE);
                //  result_holder.img_offer.setVisibility(View.GONE);
            }

            result_holder.img_wish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pro_id = bean_product1.get(position).getPro_id();
                    proW_id = result_holder.BTN_wheretobuy_list.getTag().toString();

                    //Log.e("wishlist pro",""+pro_id+"---"+proW_id+"----"+WishList.toString());
                    //Log.e("wishlist con",""+WishList.contains(pro_id));
                    if (WishList.size() != 0) {
                        if (WishList.contains(pro_id)) {


                            result_holder.img_wish.setTag("0");
                            new delete_wish_list().execute();

                        } else {

                            result_holder.img_wish.setTag("1");
                            new set_wish_list().execute();
                        }
                    } else if (WishList.size() == 0) {
                        result_holder.img_wish.setTag("1");
                        new set_wish_list().execute();

                    }


                }
            });

            result_holder.img_shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pro_id = bean_product1.get(position).getPro_id();
                    Intent i = new Intent(Product_List.this, Add_to_shoppinglist.class);
                    app.setRef_Shopping("product_list");
                    app.setshopping_pro_id(pro_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });

            result_holder.btn_buyonline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  result_holder.btn_buyonline.setClickable(false);
                    result_holder.btn_buyonline.setEnabled(false);*/
                    setRefershData();
                    final String[] selectedProductID = {bean_product1.get(position).getPro_id()};
                    if (user_data.size() != 0) {
                        for (int i = 0; i < user_data.size(); i++) {

                            owner_id = user_data.get(i).getUser_id();

                            role_id = user_data.get(i).getUser_type();

                            if (role_id.equalsIgnoreCase("6")) {

                                app = new AppPrefs(Product_List.this);
                                role_id = app.getSubSalesId();
                                u_id = app.getSalesPersonId();
                            } else if (role_id.equalsIgnoreCase("7")) {
                                app = new AppPrefs(Product_List.this);
                                role_id = app.getSubSalesId();
                                u_id = app.getSalesPersonId();
                                //Log.e("IDIDD",""+app.getSalesPersonId().toString());
                            } else {
                                u_id = owner_id;
                            }


                        }


                        loadingView.show();

                        Call<GetCartItemQuantityResponse> itemQuantityResponseCall = adminAPI.getCartItemQuantity(bean_product1.get(position).getPro_id(), owner_id, u_id);


                        itemQuantityResponseCall.enqueue(new Callback<GetCartItemQuantityResponse>() {
                            @Override
                            public void onResponse(Call<GetCartItemQuantityResponse> call, Response<GetCartItemQuantityResponse> response) {
                                loadingView.dismiss();
                                GetCartItemQuantityResponse itemQuantityResponse = response.body();

                                if (itemQuantityResponse != null) {
                                    if (itemQuantityResponse.isStatus()) {
                                        showAddToCartProductDialog(position, 1, itemQuantityResponse.getData().get(0).getQuantity(), selectedProductID);
                                    } else {

                                        showAddToCartProductDialog(position, 0, "1", selectedProductID);
                                    }
                                } else {

                                    Globals.defaultError(getApplicationContext());
                                    showAddToCartProductDialog(position, 0, "1", selectedProductID);

                                }


                            }

                            @Override
                            public void onFailure(Call<GetCartItemQuantityResponse> call, Throwable t) {

                                loadingView.dismiss();
                                Globals.showError(t, getApplicationContext());
                                showAddToCartProductDialog(position, 0, "1", selectedProductID);


                            }
                        });


                        // product_id = tv_pop_pname.getTag().toString();
                        /*List<NameValuePair> para = new ArrayList<NameValuePair>();
                        para.add(new BasicNameValuePair("product_id", bean_product1.get(position).getPro_id()));
                        para.add(new BasicNameValuePair("owner_id", owner_id));
                        para.add(new BasicNameValuePair("user_id", u_id));
                        //Log.e("111111111",""+product_id);
                        //Log.e("222222222",""+owner_id);
                        //Log.e("333333333",""+u_id);

                        isNotDone = true;
                        new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
                                Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
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
*/


                    } else {
                        Globals.CustomToast(Product_List.this, "Please Login First", getLayoutInflater());
                        user_id_main = "";
                    }
                }
            });

            result_holder.img_offer.setTag(bean_product1.get(position).getScheme());
            if (bean_product1.get(position).getScheme().equalsIgnoreCase("") || bean_product1.get(position).getScheme().equalsIgnoreCase("null")) {

                result_holder.img_offer.setVisibility(View.GONE);
            } else {
                result_holder.img_offer.setVisibility(View.VISIBLE);
            }
            result_holder.img_offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Globals.generateNoteOnSD(getApplicationContext(), "12121212121");

                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;

                    dialogOffer.setContentView(R.layout.scheme_layout);
                    dialogOffer.getWindow().setLayout((6 * width) / 7, (4 * height) / 8);
                    dialogOffer.setCancelable(true);
                    dialogOffer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                    ImageView btn_cancel = (ImageView) dialogOffer.findViewById(R.id.btn_cancel);
                    bean_S_data.clear();
                    ArrayList<String> distinctSchemeIDs = new ArrayList<String>();
                    Log.e("size", "" + bean_Schme_data.size());


                    for (int i = 0; i < bean_Schme_data.size(); i++) {

                        if (bean_Schme_data.get(i).getSchme_prod_id().equalsIgnoreCase(bean_product1.get(position).getPro_id())) {

                            if (!distinctSchemeIDs.contains(bean_Schme_data.get(i).getSchme_id())) {
                                distinctSchemeIDs.add(bean_Schme_data.get(i).getSchme_id());
                                Bean_schemeData beans = new Bean_schemeData();
                                beans.setSchme_id(bean_Schme_data.get(i).getSchme_id());
                                Log.e("ID", "" + bean_product1.get(position).getPro_id());
                                Log.e("name", "" + bean_Schme_data.get(i).getSchme_name());
                                beans.setSchme_name(bean_Schme_data.get(i).getSchme_name());
                                beans.setSchme_qty(bean_Schme_data.get(i).getSchme_qty());
                                beans.setCategory_id(bean_Schme_data.get(i).getCategory_id());
                                beans.setSchme_buy_prod_id(bean_Schme_data.get(i).getSchme_buy_prod_id());
                                beans.setSchme_prod_id(bean_Schme_data.get(i).getSchme_prod_id());

                                bean_S_data.add(beans);
                            }
                        }


                    }
                    ListView list_a = (ListView) dialogOffer.findViewById(R.id.list_schme);
                    ExpandableListView listSchemes = (ExpandableListView) dialogOffer.findViewById(R.id.listSchemes);

                    final List<String> schemeHeaders = new ArrayList<String>();
                    final HashMap<String, List<Bean_schemeData>> schemeChildList = new HashMap<String, List<Bean_schemeData>>();

                    List<Bean_schemeData> extraSpecialScheme = new ArrayList<Bean_schemeData>();
                    List<Bean_schemeData> specialScheme = new ArrayList<Bean_schemeData>();
                    List<Bean_schemeData> generalScheme = new ArrayList<Bean_schemeData>();

                    for (int i = 0; i < bean_S_data.size(); i++) {
                        if (bean_S_data.get(i).getCategory_id().equals(C.EXTRA_SPECIAL_SCHEME)) {
                            extraSpecialScheme.add(bean_S_data.get(i));
                        } else if (bean_S_data.get(i).getCategory_id().equals(C.SPECIAL_SCHEME)) {
                            specialScheme.add(bean_S_data.get(i));
                        } else {
                            generalScheme.add(bean_S_data.get(i));
                        }
                    }

                    if (extraSpecialScheme.size() > 0) {
                        schemeHeaders.add("App Offers");
                        schemeChildList.put(schemeHeaders.get(schemeHeaders.size() - 1), extraSpecialScheme);
                    }

                    if (specialScheme.size() > 0) {
                        schemeHeaders.add("Special Offers");
                        schemeChildList.put(schemeHeaders.get(schemeHeaders.size() - 1), specialScheme);
                    }

                    if (generalScheme.size() > 0) {
                        schemeHeaders.add("Offers");
                        schemeChildList.put(schemeHeaders.get(schemeHeaders.size() - 1), generalScheme);
                    }

                    final ExpandableSchemeAdapter schemeAdapter = new ExpandableSchemeAdapter(schemeHeaders, schemeChildList);
                    listSchemes.setAdapter(schemeAdapter);

                    listSchemes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                            setRefershData();
                            String selectedSchemeID = ((Bean_schemeData) schemeAdapter.getChild(groupPosition, childPosition)).getSchme_id();
                            if (user_data.size() != 0) {
                                for (int i = 0; i < user_data.size(); i++) {

                                    owner_id = user_data.get(i).getUser_id();

                                    role_id = user_data.get(i).getUser_type();

                                    if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                        app = new AppPrefs(Product_List.this);
                                        role_id = app.getSubSalesId();
                                        u_id = app.getSalesPersonId();
                                    } else {
                                        u_id = owner_id;
                                    }


                                }


                                // product_id = tv_pop_pname.getTag().toString();
                                List<NameValuePair> para = new ArrayList<NameValuePair>();
                                para.add(new BasicNameValuePair("product_id", bean_product1.get(position).getPro_id()));
                                para.add(new BasicNameValuePair("owner_id", owner_id));
                                para.add(new BasicNameValuePair("user_id", u_id));
                                Globals.generateNoteOnSD(getApplicationContext(), "12121212121" + para.toString());
                                Log.e("111111111", "" + bean_product1.get(position).getPro_id());
                                Log.e("222222222", "" + owner_id);
                                Log.e("333333333", "" + u_id);
                                isNotDone = true;
                                new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                while (isNotDone) {
                                    Globals.generateNoteOnSD(getApplicationContext(), "In while Loop");
                                }
                                isNotDone = true;

                                String json = jsonData;

                                Log.e("JSON UP", json);
                                jsonData = "";

                                try {
                                    Globals.generateNoteOnSD(getApplicationContext(), "JSON Return" + json);

                                    //System.out.println(json);

                                    if (json.isEmpty()) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERROR",
                            Toast.LENGTH_SHORT).show();*/
                                        Globals.CustomToast(Product_List.this, "SERVER ERROR", getLayoutInflater());
                                        // loadingView.dismiss();

                                    } else {
                                        JSONObject jObj = new JSONObject(json);

                                        boolean date = jObj.getBoolean("status");

                                        if (!date) {

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

                                    Globals.generateNoteOnSD(getApplicationContext(), "Exception : " + j.getMessage());
                                    //Log.e("json exce",j.getMessage());
                                }
                                Log.e("kkk", "" + kkk);
                                product_id = bean_product1.get(position).getPro_id();
                                String qty = schemeChildList.get(schemeHeaders.get(groupPosition)).get(childPosition).getSchme_qty();
                                Log.e("qty", "" + qty);

                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("product_id", product_id));
                                params.add(new BasicNameValuePair("user_id", user_id_main));
                                params.add(new BasicNameValuePair("product_buy_qty", qty));
                                params.add(new BasicNameValuePair("scheme_id", selectedSchemeID));
                                Log.e("111111111", "" + product_id);
                                Log.e("222222222", "" + user_id_main);
                                Log.e("333333333", "" + qty);

                                jsonData = "";
                                isNotDone = true;
                                new GetProductDetailByCode(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                while (isNotDone) {

                                }
                                isNotDone = true;
                                String jsons = jsonData; //GetProductDetailByCode(params);

                                Log.e("Return JSON", jsons);

                                //new check_schme().execute();
                                try {


                                    //System.out.println(json);

                                    if (jsons == null
                                            || (jsons.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                        Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                                        // loadingView.dismiss();

                                    } else {
                                        JSONObject jObj = new JSONObject(jsons);

                                        String date = jObj.getString("status");

                                        if (date.equalsIgnoreCase("false")) {
                                            String Message = jObj.getString("message");
                                            bean_product_schme.clear();
                                            bean_schme.clear();
                                            //Log.e("11111111",""+product_id);
                                            //   Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                                            // loadingView.dismiss();
                                        } else {

                                            JSONObject jobj = new JSONObject(jsons);

                                            bean_product_schme.clear();
                                            bean_schme.clear();

                                            JSONObject jsonArray = jobj.getJSONObject("data");
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


                                            //loadingView.dismiss();

                                        }

                                    }
                                } catch (Exception j) {
                                    j.printStackTrace();
                                    Log.e("JSON Exception", j.getMessage());
                                    //Log.e("json exce",j.getMessage());
                                }
                                //Log.e("45454545",""+bean_product_schme.size());
                                if (bean_schme.size() == 0) {
                                    Log.e("1111111111111", "" + qty);
                                } else {
                                    Log.e("2222222222222222", "" + qty);
                                    //Log.e("Type ID : ",""+bean_schme.get(0).getType_id().toString());
                                    if (bean_schme.get(0).getType_id().equalsIgnoreCase("1")) {
                                        Log.e("Scheme Option", "1 ->Option");
                                        jarray_cart = new JSONArray();

                                        String getq = bean_schme.get(0).getGet_qty();
                                        String buyq = bean_schme.get(0).getBuy_qty();
                                        String maxq = bean_schme.get(0).getMax_qty();

                                        double getqu = Double.parseDouble(getq);
                                        double buyqu = Double.parseDouble(buyq);
                                        double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);


                                        String sell = bean_product1.get(position).getPro_sellingprice();

                                        double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * buyqu;

                                        double se1 = buyqu + getqu;

                                        double fse = se / se1;
                                        double w = round(fse, 2);
                                        sell = String.format("%.2f", w);
                                        //sell = String.valueOf(fse);

                                        int fqu = qu + (int) getqu;


                                        for (int i = 0; i < 1; i++) {
                                            try {
                                                JSONObject jobject = new JSONObject();

                                                jobject.put("user_id", u_id);
                                                jobject.put("role_id", role_id);
                                                jobject.put("owner_id", owner_id);
                                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                                jobject.put("category_id", Catid);
                                                jobject.put("name", bean_product1.get(position).getPro_name());

                                                jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                                jobject.put("quantity", String.valueOf(fqu));
                                                jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                                jobject.put("selling_price", sell);
                                                jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                                jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                                jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                                double f = fqu * Double.parseDouble(sell);
                                                double w1 = round(f, 2);
                                                String str = String.format("%.2f", w1);
                                                jobject.put("item_total", str);
                                                jobject.put("pro_scheme", " ");
                                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                if (list_of_images.size() == 0) {
                                                    jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                    // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                                } else {
                                                    jobject.put("prod_img", list_of_images.get(0));
                                                    //   bean.setPro_Images(list_of_images.get(0).toString());
                                                }

                                                jarray_cart.put(jobject);
                                            } catch (JSONException e) {

                                            }


                                        }
                                        array_value.clear();
                                        dialog.dismiss();
                                        Log.e("Data 3217", jarray_cart.toString());
                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {
                                            Log.e("08","-->");
                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("8","-->");
                                        }


                                    } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {
                                        Log.e("Scheme Option", "2 ->Option");
                                        jarray_cart = new JSONArray();
                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", u_id);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", bean_product1.get(position).getPro_id());
                                            jobject.put("category_id", Catid);
                                            jobject.put("name", bean_product1.get(position).getPro_name());

                                            jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                            jobject.put("quantity", qty);
                                            jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                            jobject.put("selling_price", bean_product1.get(position).getPro_sellingprice());
                                            jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                            jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                            jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                            jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                            double f = Integer.parseInt(qty) * Double.parseDouble(bean_product1.get(position).getPro_sellingprice());
                                            double w1 = round(f, 2);
                                            String str = String.format("%.2f", w1);
                                            jobject.put("item_total", str);
                                            jobject.put("pro_scheme", " ");
                                            jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                            jobject.put("scheme_id", " ");
                                            jobject.put("scheme_title", " ");
                                            jobject.put("scheme_pack_id", " ");
                                            if (list_of_images.size() == 0) {
                                                jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                            } else {
                                                jobject.put("prod_img", list_of_images.get(0));
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
                                        //double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);

                                        int a = (int) (qu / buyqu);
                                        int b = (int) (a * getqu);
                                        /*if (b > maxqu) {
                                            b = maxqu;
                                        } else {
                                            b = b;
                                        }*/


                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", u_id);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                            jobject.put("category_id", Catid);
                                            jobject.put("name", bean_product_schme.get(0).getPro_name());
                                            jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                            jobject.put("quantity", String.valueOf(b));
                                            jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                            //Log.e("D1D1",""+bean_product_schme.get(0).getPro_mrp());
                                            jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                            //Log.e("E1E1",""+bean_product_schme.get(0).getPro_sellingprice());
                                            jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id());
                                            jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name());
                                            jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id());
                                            jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name());
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
                                        Log.e("Data 3330", jarray_cart.toString());
                                        //dialogOffer.dismiss();
                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {
                                            Log.e("09","-->");
                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("9","-->");
                                        }

                                    } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("3")) {
                                        Log.e("Scheme Option", "3 ->Option");


                                        String getq = bean_schme.get(0).getGet_qty();
                                        String buyq = bean_schme.get(0).getBuy_qty();
                                        String maxq = bean_schme.get(0).getMax_qty();

                                        double getqu = Double.parseDouble(getq);
                                        double buyqu = Double.parseDouble(buyq);
                                        double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);


                                        String sell = bean_product1.get(position).getPro_sellingprice();

                                        String disc = bean_schme.get(0).getDisc_per();

                                        double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * Double.parseDouble(bean_schme.get(0).getDisc_per());

                                        double se1 = se / 100;

                                        double fse = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) - se1;
                                        double w1 = round(fse, 2);
                                        sell = String.format("%.2f", w1);
                                        jarray_cart = new JSONArray();

                                        for (int i = 0; i < 1; i++) {
                                            try {
                                                JSONObject jobject = new JSONObject();

                                                jobject.put("user_id", u_id);
                                                jobject.put("role_id", role_id);
                                                jobject.put("owner_id", owner_id);
                                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                                jobject.put("category_id", Catid);
                                                jobject.put("name", bean_product1.get(position).getPro_name());

                                                jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                                jobject.put("quantity", qty);
                                                jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                                jobject.put("selling_price", sell);
                                                jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                                jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                                jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                                double f = Double.parseDouble(qty) * Double.parseDouble(sell);
                                                double w = round(f, 2);
                                                String str = String.format("%.2f", w);
                                                jobject.put("item_total", str);
                                                jobject.put("pro_scheme", " ");
                                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                if (list_of_images.size() == 0) {
                                                    jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                    // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                                } else {
                                                    jobject.put("prod_img", list_of_images.get(0));
                                                    //   bean.setPro_Images(list_of_images.get(0).toString());
                                                }

                                                jarray_cart.put(jobject);
                                            } catch (JSONException e) {

                                            }


                                        }
                                        array_value.clear();
                                        Log.e("Data 3414", jarray_cart.toString());
                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {
                                            Log.e("010","-->");
                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("10","-->");
                                        }
                                    }
                                }

                            } else {

                                Globals.CustomToast(Product_List.this, "Please Login First", getLayoutInflater());
                                user_id_main = "";

                            }

                            dialogOffer.dismiss();

                            return true;
                        }
                    });


                    list_a.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int ii, long id) {

                            setRefershData();

                            if (user_data.size() != 0) {
                                for (int i = 0; i < user_data.size(); i++) {

                                    owner_id = user_data.get(i).getUser_id();

                                    role_id = user_data.get(i).getUser_type();

                                    if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                        app = new AppPrefs(Product_List.this);
                                        role_id = app.getSubSalesId();
                                        u_id = app.getSalesPersonId();
                                    } else {
                                        u_id = owner_id;
                                    }


                                }


                                // product_id = tv_pop_pname.getTag().toString();
                                List<NameValuePair> para = new ArrayList<NameValuePair>();
                                para.add(new BasicNameValuePair("product_id", bean_product1.get(position).getPro_id()));
                                para.add(new BasicNameValuePair("owner_id", owner_id));
                                para.add(new BasicNameValuePair("user_id", u_id));
                                Globals.generateNoteOnSD(getApplicationContext(), "12121212121" + para.toString());
                                Log.e("111111111", "" + bean_product1.get(position).getPro_id());
                                Log.e("222222222", "" + owner_id);
                                Log.e("333333333", "" + u_id);
                                isNotDone = true;
                                new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                while (isNotDone) {
                                    Globals.generateNoteOnSD(getApplicationContext(), "In while Loop");
                                }
                                isNotDone = true;

                                String json = jsonData;

                                Log.e("JSON UP", json);
                                jsonData = "";

                                try {
                                    Globals.generateNoteOnSD(getApplicationContext(), "JSON Return" + json);

                                    //System.out.println(json);

                                    if (json.isEmpty()) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                        Globals.CustomToast(Product_List.this, "SERVER ERROR", getLayoutInflater());
                                        // loadingView.dismiss();

                                    } else {
                                        JSONObject jObj = new JSONObject(json);

                                        boolean date = jObj.getBoolean("status");

                                        if (!date) {

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

                                    Globals.generateNoteOnSD(getApplicationContext(), "Exception : " + j.getMessage());
                                    //Log.e("json exce",j.getMessage());
                                }
                                Log.e("kkk", "" + kkk);
                                product_id = bean_product1.get(position).getPro_id();
                                String qty = bean_S_data.get(ii).getSchme_qty();
                                Log.e("qty", "" + qty);

                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("product_id", product_id));
                                params.add(new BasicNameValuePair("user_id", u_id));
                                params.add(new BasicNameValuePair("product_buy_qty", qty));
                                Log.e("111111111", "" + product_id);
                                Log.e("222222222", "" + user_id_main);
                                Log.e("333333333", "" + qty);

                                jsonData = "";
                                isNotDone = true;
                                new GetProductDetailByCode(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                while (isNotDone) {

                                }
                                isNotDone = true;
                                String jsons = jsonData; //GetProductDetailByCode(params);

                                Log.e("Return JSON", jsons);

                                //new check_schme().execute();
                                try {


                                    //System.out.println(json);

                                    if (jsons == null
                                            || (jsons.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                        Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                                        // loadingView.dismiss();

                                    } else {
                                        JSONObject jObj = new JSONObject(jsons);

                                        String date = jObj.getString("status");

                                        if (date.equalsIgnoreCase("false")) {
                                            String Message = jObj.getString("message");
                                            bean_product_schme.clear();
                                            bean_schme.clear();
                                            //Log.e("11111111",""+product_id);
                                            //   Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                                            // loadingView.dismiss();
                                        } else {

                                            JSONObject jobj = new JSONObject(jsons);

                                            bean_product_schme.clear();
                                            bean_schme.clear();

                                            JSONObject jsonArray = jobj.getJSONObject("data");
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


                                            //loadingView.dismiss();

                                        }

                                    }
                                } catch (Exception j) {
                                    j.printStackTrace();
                                    Log.e("JSON Exception", j.getMessage());
                                    //Log.e("json exce",j.getMessage());
                                }
                                //Log.e("45454545",""+bean_product_schme.size());
                                if (bean_schme.size() == 0) {
                                    Log.e("1111111111111", "" + qty);
                                } else {
                                    Log.e("2222222222222222", "" + qty);
                                    //Log.e("Type ID : ",""+bean_schme.get(0).getType_id().toString());
                                    if (bean_schme.get(0).getType_id().equalsIgnoreCase("1")) {
                                        Log.e("Scheme Option", "1 ->Option");
                                        jarray_cart = new JSONArray();

                                        String getq = bean_schme.get(0).getGet_qty();
                                        String buyq = bean_schme.get(0).getBuy_qty();
                                        String maxq = bean_schme.get(0).getMax_qty();

                                        double getqu = Double.parseDouble(getq);
                                        double buyqu = Double.parseDouble(buyq);
                                        double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);


                                        String sell = bean_product1.get(position).getPro_sellingprice();

                                        double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * buyqu;

                                        double se1 = buyqu + getqu;

                                        double fse = se / se1;
                                        double w = round(fse, 2);
                                        sell = String.format("%.2f", w);
                                        //sell = String.valueOf(fse);

                                        int fqu = qu + (int) getqu;


                                        for (int i = 0; i < 1; i++) {
                                            try {
                                                JSONObject jobject = new JSONObject();

                                                jobject.put("user_id", u_id);
                                                jobject.put("role_id", role_id);
                                                jobject.put("owner_id", owner_id);
                                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                                jobject.put("category_id", Catid);
                                                jobject.put("name", bean_product1.get(position).getPro_name());

                                                jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                                jobject.put("quantity", String.valueOf(fqu));
                                                jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                                jobject.put("selling_price", sell);
                                                jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                                jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                                jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                                double f = fqu * Double.parseDouble(sell);
                                                double w1 = round(f, 2);
                                                String str = String.format("%.2f", w1);
                                                jobject.put("item_total", str);
                                                jobject.put("pro_scheme", " ");
                                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                if (list_of_images.size() == 0) {
                                                    jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                    // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                                } else {
                                                    jobject.put("prod_img", list_of_images.get(0));
                                                    //   bean.setPro_Images(list_of_images.get(0).toString());
                                                }

                                                jarray_cart.put(jobject);
                                            } catch (JSONException e) {

                                            }


                                        }
                                        array_value.clear();
                                        dialog.dismiss();
                                        Log.e("Data 3217", jarray_cart.toString());
                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {
                                            Log.e("011","-->");
                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("11","-->");
                                        }


                                    } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {
                                        Log.e("Scheme Option", "2 ->Option");
                                        jarray_cart = new JSONArray();
                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", u_id);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", bean_product1.get(position).getPro_id());
                                            jobject.put("category_id", Catid);
                                            jobject.put("name", bean_product1.get(position).getPro_name());

                                            jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                            jobject.put("quantity", qty);
                                            jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                            jobject.put("selling_price", bean_product1.get(position).getPro_sellingprice());
                                            jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                            jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                            jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                            jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                            double f = Integer.parseInt(qty) * Double.parseDouble(bean_product1.get(position).getPro_sellingprice());
                                            double w1 = round(f, 2);
                                            String str = String.format("%.2f", w1);
                                            jobject.put("item_total", str);
                                            jobject.put("pro_scheme", " ");
                                            jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                            jobject.put("scheme_id", " ");
                                            jobject.put("scheme_title", " ");
                                            jobject.put("scheme_pack_id", " ");
                                            if (list_of_images.size() == 0) {
                                                jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                            } else {
                                                jobject.put("prod_img", list_of_images.get(0));
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
                                        //double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);

                                        int a = (int) (qu / buyqu);
                                        int b = (int) (a * getqu);
                                        /*if (b > maxqu) {
                                            b = maxqu;
                                        } else {
                                            b = b;
                                        }*/


                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", u_id);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                            jobject.put("category_id", Catid);
                                            jobject.put("name", bean_product_schme.get(0).getPro_name());
                                            jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                            jobject.put("quantity", String.valueOf(b));
                                            jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                            //Log.e("D1D1",""+bean_product_schme.get(0).getPro_mrp());
                                            jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                            //Log.e("E1E1",""+bean_product_schme.get(0).getPro_sellingprice());
                                            jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id());
                                            jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name());
                                            jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id());
                                            jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name());
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
                                        Log.e("Data 3330", jarray_cart.toString());
                                        //dialogOffer.dismiss();
                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {
                                            Log.e("012","-->");
                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("12","-->");
                                        }

                                    } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("3")) {
                                        Log.e("Scheme Option", "3 ->Option");


                                        String getq = bean_schme.get(0).getGet_qty();
                                        String buyq = bean_schme.get(0).getBuy_qty();
                                        String maxq = bean_schme.get(0).getMax_qty();

                                        double getqu = Double.parseDouble(getq);
                                        double buyqu = Double.parseDouble(buyq);
                                        double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);


                                        String sell = bean_product1.get(position).getPro_sellingprice();

                                        String disc = bean_schme.get(0).getDisc_per();

                                        double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * Double.parseDouble(bean_schme.get(0).getDisc_per());

                                        double se1 = se / 100;

                                        double fse = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) - se1;
                                        double w1 = round(fse, 2);
                                        sell = String.format("%.2f", w1);
                                        jarray_cart = new JSONArray();

                                        for (int i = 0; i < 1; i++) {
                                            try {
                                                JSONObject jobject = new JSONObject();

                                                jobject.put("user_id", u_id);
                                                jobject.put("role_id", role_id);
                                                jobject.put("owner_id", owner_id);
                                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                                jobject.put("category_id", Catid);
                                                jobject.put("name", bean_product1.get(position).getPro_name());

                                                jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                                jobject.put("quantity", qty);
                                                jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                                jobject.put("selling_price", sell);
                                                jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                                jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                                jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                                double f = Double.parseDouble(qty) * Double.parseDouble(sell);
                                                double w = round(f, 2);
                                                String str = String.format("%.2f", w);
                                                jobject.put("item_total", str);
                                                jobject.put("pro_scheme", " ");
                                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                if (list_of_images.size() == 0) {
                                                    jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                    // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                                } else {
                                                    jobject.put("prod_img", list_of_images.get(0));
                                                    //   bean.setPro_Images(list_of_images.get(0).toString());
                                                }

                                                jarray_cart.put(jobject);
                                            } catch (JSONException e) {

                                            }


                                        }
                                        array_value.clear();
                                        Log.e("Data 3414", jarray_cart.toString());
                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {
                                            Log.e("013","-->");
                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("13","-->");
                                        }
                                    }
                                }

                            } else {

                                Globals.CustomToast(Product_List.this, "Please Login First", getLayoutInflater());
                                user_id_main = "";

                            }

                            dialogOffer.dismiss();
                        }
                    });

                    list_a.setVisibility(View.VISIBLE);

                    list_a.setAdapter(new CustomResultAdapterschme());
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogOffer.dismiss();
                        }
                    });
                    dialogOffer.show();

                }
            });

            result_holder.tvproduct_name.setText(bean_product1.get(position)
                    .getPro_name());
            result_holder.tvproduct_code.setText("(" + bean_product1.get(position)
                    .getPro_code() + ")");

            result_holder.tv_product_mrp.setText(bean_product1.get(position)
                    .getPro_mrp());
            result_holder.tv_product_sellingprice.setText(bean_product1.get(position)
                    .getPro_sellingprice());
            p_code = bean_product1.get(position).getPro_code();
            double mrp = Double.parseDouble(result_holder.tv_product_mrp.getText().toString());
            double sellingprice = Double.parseDouble(result_holder.tv_product_sellingprice.getText().toString());
            if (mrp > sellingprice) {
                result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                result_holder.txt_mrp.setVisibility(View.VISIBLE);
                //  result_holder.off_tag.setVisibility(View.VISIBLE);


                /*double off = mrp - sellingprice;
                double offa = off * 100;
                double o = offa / mrp;
                int a = (int) o;*/

                float percent = 100 - (Float.parseFloat(bean_product1.get(position)
                        .getPro_sellingprice()) * 100 / Float.parseFloat(bean_product1.get(position)
                        .getPro_mrp()));


                result_holder.off_tag.setText(String.format("%.0f", percent) + "% OFF");

                result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                        .getPro_mrp());

                result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                        .getPro_sellingprice());
                result_holder.tv_product_mrp.setPaintFlags(result_holder.tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            } else {
                result_holder.tv_product_mrp.setVisibility(View.GONE);
                result_holder.txt_mrp.setVisibility(View.GONE);
                result_holder.off_tag.setVisibility(View.GONE);
                result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position).getPro_sellingprice());
            }
            if (user_data.size() != 0) {
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
                    result_holder.tv_product_mrp.setVisibility(View.GONE);
                    result_holder.txt_mrp.setVisibility(View.GONE);
                    result_holder.off_tag.setVisibility(View.GONE);
                    result_holder.tv_product_sellingprice.setVisibility(View.GONE);
                    result_holder.txt_selling.setVisibility(View.GONE);


                } else {

                    p_code = bean_product1.get(position).getPro_code();
                    if (mrp > sellingprice) {
                        result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                        result_holder.txt_mrp.setVisibility(View.VISIBLE);
                        //   result_holder.off_tag.setVisibility(View.VISIBLE);
                        /*double off = mrp - sellingprice;
                        double offa = off * 100;
                        double o = offa / mrp;
                        int a = (int) o;*/

                        /*float percent = 100 - (Float.parseFloat(bean_product1.get(position)
                                .getPro_sellingprice()) * 100 / Float.parseFloat(bean_product1.get(position)
                                .getPro_mrp()));

                        result_holder.off_tag.setText(String.format("%.0f", percent) + "% OFF");*/
                        double percent = (Double.parseDouble(bean_product1.get(position).getPro_mrp()) - Double.parseDouble(bean_product1.get(position).getPro_sellingprice())) * 100 / Double.parseDouble(bean_product1.get(position).getPro_mrp());


                        result_holder.off_tag.setText(String.format("%.1f", percent) + "% OFF");
                        result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position).getPro_mrp());

                        result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position).getPro_sellingprice());
                        result_holder.tv_product_mrp.setPaintFlags(result_holder.tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                    } else {
                        result_holder.tv_product_mrp.setVisibility(View.GONE);
                        result_holder.txt_mrp.setVisibility(View.GONE);
                        result_holder.off_tag.setVisibility(View.GONE);
                        result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position).getPro_sellingprice());
                    }
                }
            }
            result_holder.tvproduct_packof.setText(bean_product1.get(position)
                    .getPro_label());


            imageloader = new ImageLoader(Product_List.this);

            //Log.e("11111111111111111111",""+bean_product1.get(position).getPro_image());

            // imageloader.DisplayImage(Globals.server_link +"files/" +bean_product1.get(position).getPro_image(), result_holder.img_photo);
            Picasso.with(getApplicationContext())
                    .load(Globals.server_link + "files/" + bean_product1.get(position).getPro_image())
                    .placeholder(R.drawable.btl_watermark)
                    .into(result_holder.img_photo);

            int mrpPrice = (int) Float.parseFloat(bean_product1.get(position).getPro_mrp());

            if (mrpPrice <= 0) {
                result_holder.btn_enquiry.setVisibility(View.VISIBLE);
                result_holder.btn_buyonline.setVisibility(View.GONE);
                result_holder.layout_prices.setVisibility(View.INVISIBLE);
                result_holder.img_offer.setVisibility(View.GONE);
            }

            result_holder.btn_enquiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), NonMRPProductEnquiry.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("productName", bean_product1.get(position).getPro_name());
                    intent.putExtra("productID", bean_product1.get(position).getPro_id());
                    startActivity(intent);
                }
            });

            return convertView;
        }

    }

    private class ExpandableSchemeAdapter extends BaseExpandableListAdapter {

        List<String> dataHeaderList = new ArrayList<>();
        HashMap<String, List<Bean_schemeData>> dataChildList;

        public ExpandableSchemeAdapter(List<String> dataHeaderList, HashMap<String, List<Bean_schemeData>> dataChildList) {
            this.dataHeaderList = dataHeaderList;
            this.dataChildList = dataChildList;
        }

        @Override
        public int getGroupCount() {
            return dataHeaderList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return dataChildList.get(dataHeaderList.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return dataHeaderList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return dataChildList.get(dataHeaderList.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_group_header, parent, false);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);

            String groupHeader = getGroup(groupPosition) + " (" + getChildrenCount(groupPosition) + ")";

            lblListHeader.setText(groupHeader);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            Bean_schemeData schemeData = (Bean_schemeData) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_child_item, parent, false);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            lblListHeader.setText(schemeData.getSchme_name());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public class MyAdapter extends ArrayAdapter<Bean_Attribute> {

        ArrayList<Bean_Attribute> array;
        int k = 0;

        public MyAdapter(Context context, int textViewResourceId, ArrayList<Bean_Attribute> objects) {
            super(context, textViewResourceId, objects);
            this.array = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.textview, parent, false);

            TextView label = (TextView) row.findViewById(R.id.tv_row);
         /*   if(k == 0){
                label.setText("Select "+array.get(position).getOption_name());
                k++;
                position = 0;
            }else {*/
            label.setText(array.get(position).getValue_name());
            /*}*/
            return row;
        }
    }

    public class CustomResultAdapterDoctor1 extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bean_product1.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            // result_holder = null;

            result_holder = new ResultHolder();

            //   if (convertView == null) {

            convertView = vi.inflate(R.layout.prod_list, null);

            //  }

            result_holder.tvproduct_name = (TextView) convertView
                    .findViewById(R.id.product_name);
            result_holder.tvproduct_code = (TextView) convertView
                    .findViewById(R.id.product_code);
            result_holder.tvproduct_packof = (TextView) convertView
                    .findViewById(R.id.product_packof);
            result_holder.tv_product_mrp = (TextView) convertView
                    .findViewById(R.id.product_mrp);
            result_holder.tv_product_sellingprice = (TextView) convertView
                    .findViewById(R.id.product_sellingprice);
            result_holder.img_photo = (ImageView) convertView
                    .findViewById(R.id.imageView2);
            result_holder.img_wish = (ImageView) convertView
                    .findViewById(R.id.img_wish);
            result_holder.img_shopping = (ImageView) convertView
                    .findViewById(R.id.img_shooping);
            result_holder.txt_mrp = (TextView) convertView
                    .findViewById(R.id.txt_mrp_main);
            result_holder.btn_buyonline = (Button) convertView
                    .findViewById(R.id.btn_buyonline);
            result_holder.BTN_wheretobuy_list = (Button) convertView
                    .findViewById(R.id.BTN_wheretobuy_list);
            result_holder.txt_selling = (TextView) convertView
                    .findViewById(R.id.txt_selling);
            result_holder.txt_pack = (TextView) convertView
                    .findViewById(R.id.txt_pack);
            result_holder.off_tag = (TextView) convertView.findViewById(R.id.off_tag);
            result_holder.img_offer = (ImageView) convertView.findViewById(R.id.img_offer);
            result_holder.btn_enquiry = (Button) convertView.findViewById(R.id.btn_enquiry);

            result_holder.layout_prices = (LinearLayout) convertView.findViewById(R.id.layout_prices);

            result_holder.BTN_wheretobuy_list.setTag(bean_product1.get(position).getPro_id());
            setRefershData();

            if (whereToBuyVisibility) {
                result_holder.BTN_wheretobuy_list.setVisibility(View.VISIBLE);
            } else {
                result_holder.BTN_wheretobuy_list.setVisibility(View.GONE);
            }


            if (user_data.size() != 0) {
                for (int i = 0; i < user_data.size(); i++) {

                    rolee = user_data.get(i).getUser_type();

                }

            } else {
                user_id_main = "";
            }
            if (rolee.equals(C.ADMIN) || rolee.equalsIgnoreCase("6") || rolee.equalsIgnoreCase("7")) {
                result_holder.img_wish.setVisibility(View.GONE);
                result_holder.img_shopping.setVisibility(View.GONE);
            } else {
                result_holder.img_wish.setVisibility(View.VISIBLE);
                result_holder.img_shopping.setVisibility(View.VISIBLE);
            }

            //Log.e("product_id",""+result_holder.BTN_wheretobuy_list.getTag().toString());

            result_holder.BTN_wheretobuy_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    app = new AppPrefs(Product_List.this);
                    app.setProduct_Sort("");
                    app.setFilter_Option("");
                    app.setFilter_SubCat("");
                    app.setFilter_Cat("");
                    app.setFilter_Sort("");
                    db = new DatabaseHandler(Product_List.this);
                    db.Delete_ALL_table();
                    db.close();
                    Intent i = new Intent(Product_List.this, Where_To_Buy.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }
            });

            result_holder.tvproduct_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if(WishList.size() != 0) {

                        String st = WishList.toString();
                        //Log.e("111111111", st);
                        if (st.contains(pro_id)) {

                            app= new AppPrefs(Product_List.this);
                            app.setwishid("1");

                        } else {
                            app= new AppPrefs(Product_List.this);
                            app.setwishid("2");

                        }
                    }*/
                    app = new AppPrefs(Product_List.this);
                    app.setproduct_id(bean_product1.get(position).getPro_id());
                    app.setRef_Detail("ProductList");
                    Intent i = new Intent(Product_List.this, BTLProduct_Detail.class);

                    //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });

            result_holder.tvproduct_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if(WishList.size() != 0) {

                        String st = WishList.toString();
                        //Log.e("111111111", st);
                        if (st.contains(pro_id)) {

                            app= new AppPrefs(Product_List.this);
                            app.setwishid("1");

                        } else {
                            app= new AppPrefs(Product_List.this);
                            app.setwishid("2");

                        }
                    }*/
                    app = new AppPrefs(Product_List.this);
                    app.setproduct_id(bean_product1.get(position).getPro_id());
                    app.setRef_Detail("ProductList");
                    Intent i = new Intent(Product_List.this, BTLProduct_Detail.class);

                    //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });

            result_holder.img_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if(WishList.size() != 0) {

                        String st = WishList.toString();
                        //Log.e("111111111", st);
                        if (st.contains(pro_id)) {

                            app= new AppPrefs(Product_List.this);
                            app.setwishid("1");

                        } else {
                            app= new AppPrefs(Product_List.this);
                            app.setwishid("2");

                        }
                    }*/
                    app = new AppPrefs(Product_List.this);
                    app.setproduct_id(bean_product1.get(position).getPro_id());
                    app.setRef_Detail("ProductList");
                    Intent i = new Intent(Product_List.this, BTLProduct_Detail.class);

                    //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });


            if (user_data.size() != 0) {
                pro_id = result_holder.BTN_wheretobuy_list.getTag().toString();
                setRefershData();


                if (user_data.size() != 0) {
                    for (int i = 0; i < user_data.size(); i++) {

                        rolee = user_data.get(i).getUser_type();

                    }

                } else {
                    user_id_main = "";
                }
                if (rolee.equals(C.ADMIN) || rolee.equalsIgnoreCase("6") || rolee.equalsIgnoreCase("7")) {
                    result_holder.img_wish.setVisibility(View.GONE);
                    result_holder.img_shopping.setVisibility(View.GONE);
                } else {
                    result_holder.img_wish.setVisibility(View.VISIBLE);
                    result_holder.img_shopping.setVisibility(View.VISIBLE);
                }
                result_holder.txt_mrp.setVisibility(View.VISIBLE);
                result_holder.btn_buyonline.setVisibility(View.VISIBLE);
                result_holder.tvproduct_packof.setVisibility(View.VISIBLE);
                result_holder.tv_product_sellingprice.setVisibility(View.VISIBLE);
                result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                //   result_holder.off_tag.setVisibility(View.VISIBLE);
                result_holder.txt_selling.setVisibility(View.VISIBLE);
                // result_holder.BTN_wheretobuy_list.setVisibility(View.VISIBLE);
                result_holder.txt_pack.setVisibility(View.VISIBLE);

                if (WishList.size() != 0) {

                    String st = WishList.toString();
                    //Log.e("12122", st);
                    //Log.e("pro_id",""+pro_id);
                    if (WishList.contains(pro_id)) {
                        result_holder.img_wish.setImageResource(R.drawable.whishlist_fill);
                        result_holder.img_wish.setTag("1");
                        //Log.e("1111", "111");
                    } else {
                        result_holder.img_wish.setImageResource(R.drawable.whishlist);
                        result_holder.img_wish.setTag("0");
                        //Log.e("0000","000");
                    }


                } else if (WishList.size() == 0) {
                    result_holder.img_wish.setImageResource(R.drawable.whishlist);
                    result_holder.img_wish.setTag("0");
                    //Log.e("0000", "000");
                }

                for (int i = 0; i < user_data.size(); i++) {

                    user_id = user_data.get(i).getUser_id();

                }

            } else {
                result_holder.img_wish.setVisibility(View.GONE);
                result_holder.img_shopping.setVisibility(View.GONE);
                result_holder.txt_mrp.setVisibility(View.VISIBLE);
                result_holder.btn_buyonline.setVisibility(View.VISIBLE);
                result_holder.tvproduct_packof.setVisibility(View.VISIBLE);
                result_holder.tv_product_sellingprice.setVisibility(View.VISIBLE);
                result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                //  result_holder.off_tag.setVisibility(View.VISIBLE);
                result_holder.txt_selling.setVisibility(View.VISIBLE);
                //  result_holder.BTN_wheretobuy_list.setVisibility(View.GONE);
                result_holder.txt_pack.setVisibility(View.VISIBLE);
                //  result_holder.img_offer.setVisibility(View.GONE);
            }

            result_holder.img_wish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pro_id = bean_product1.get(position).getPro_id();
                    proW_id = result_holder.BTN_wheretobuy_list.getTag().toString();

                    //Log.e("wishlist pro",""+pro_id+"---"+proW_id+"----"+WishList.toString());
                    //Log.e("wishlist con",""+WishList.contains(pro_id));
                    if (WishList.size() != 0) {
                        if (WishList.contains(pro_id)) {


                            result_holder.img_wish.setTag("0");
                            new delete_wish_list().execute();

                        } else {

                            result_holder.img_wish.setTag("1");
                            new set_wish_list().execute();
                        }
                    } else if (WishList.size() == 0) {
                        result_holder.img_wish.setTag("1");
                        new set_wish_list().execute();

                    }


                 /*   //Log.e("WishList",""+result_holder.img_wish.getTag().toString());
                    if(result_holder.img_wish.getTag().toString().equals("1")){
                      *//*  result_holder.img_wish.setImageResource(R.drawable.whishlist);
                        *//*

                       result_holder.img_wish.setTag("0");
                        new delete_wish_list().execute();

                    }else if(result_holder.img_wish.getTag().toString().equals("0")) {
                       *//* result_holder.img_wish.setImageResource(R.drawable.whishlist_fill);
                       *//*
                        result_holder.img_wish.setTag("1");
                        new set_wish_list().execute();
                    }*/

                }
            });

            result_holder.img_shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pro_id = bean_product1.get(position).getPro_id().toString();
                    Intent i = new Intent(Product_List.this, Add_to_shoppinglist.class);
                    app.setRef_Shopping("product_list");
                    app.setshopping_pro_id(pro_id);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });

            result_holder.btn_buyonline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRefershData();
                    if (user_data.size() != 0) {
                        for (int i = 0; i < user_data.size(); i++) {

                            owner_id = user_data.get(i).getUser_id();

                            role_id = user_data.get(i).getUser_type();

                            if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                app = new AppPrefs(Product_List.this);
                                role_id = app.getSubSalesId();
                                u_id = app.getSalesPersonId();
                            } else {
                                u_id = owner_id;
                            }


                        }


                        // product_id = tv_pop_pname.getTag().toString();
                        List<NameValuePair> para = new ArrayList<NameValuePair>();
                        para.add(new BasicNameValuePair("product_id", bean_product1.get(position).getPro_id()));
                        para.add(new BasicNameValuePair("owner_id", owner_id));
                        para.add(new BasicNameValuePair("user_id", u_id));
                        Log.e("111111111",""+product_id);
                        Log.e("222222222",""+owner_id);
                        Log.e("333333333",""+u_id);
                        isNotDone = true;
                        new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        while (isNotDone) {

                        }
                        isNotDone = true;

                        String json = jsonData;
                        //String json=GetProductDetailByQty(para);

                        try {


                            //System.out.println(json);

                            if (json == null
                                    || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
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
                        final String[] selectedProductID = {bean_product1.get(position).getPro_id()};
                        dialog.setContentView(R.layout.popup_cart);
                        minuss = (ImageView) dialog.findViewById(R.id.minus);
                        plus = (ImageView) dialog.findViewById(R.id.plus);
                        buy_cart = (Button) dialog.findViewById(R.id.Btn_Buy_online11);
                        cancel = (Button) dialog.findViewById(R.id.cancel);
                        edt_count = (EditText) dialog.findViewById(R.id.edt_count);
                        edt_count.setSelection(edt_count.getText().length());
                        final ImageView img_offerDialog = (ImageView) dialog.findViewById(R.id.img_offerDialog);

                        final TextView txt_availableScheme = (TextView) dialog.findViewById(R.id.txt_availableScheme);

                        if (bean_product1.get(position).getScheme() == null || bean_product1.get(position).getScheme().isEmpty()) {
                            img_offerDialog.setVisibility(View.GONE);
                        }

                        img_offerDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showOfferDialog(position, selectedProductID[0]);
                            }
                        });


                        l_view_spinner = (LinearLayout) findViewById(R.id.l_view_spinner);
                        tv_pop_pname = (TextView) dialog.findViewById(R.id.product_name);
                        tv_pop_code = (TextView) dialog.findViewById(R.id.product_code);
                        tv_pop_packof = (TextView) dialog.findViewById(R.id.product_packof);
                        tv_pop_mrp = (TextView) dialog.findViewById(R.id.product_mrp);
                        tv_txt_mrp = (TextView) dialog.findViewById(R.id.txt_mrp_text);
                        tv_pop_sellingprice = (TextView) dialog.findViewById(R.id.product_sellingprice);
                        tv_total = (TextView) dialog.findViewById(R.id.txt_total);
                        l_spinner = (LinearLayout) dialog.findViewById(R.id.l_spinner);
                        l_spinner_text = (LinearLayout) dialog.findViewById(R.id.l_spinnertext);
                        tv_pop_pname.setText(bean_product1.get(position).getPro_name());
                        tv_pop_code.setText(" ( " + bean_product1.get(position).getPro_code() + " )");
                        tv_pop_packof.setText(bean_product1.get(position).getPro_label());
                        tv_pop_mrp.setText(bean_product1.get(position).getPro_mrp());
                        tv_pop_sellingprice.setText(bean_product1.get(position).getPro_sellingprice());
                        tv_pop_sellingprice.setTag(bean_product1.get(position).getPro_sellingprice());
                        l_mrp = (LinearLayout) dialog.findViewById(R.id.l_mrp);
                        l_sell = (LinearLayout) dialog.findViewById(R.id.l_sell);
                        l_total = (LinearLayout) dialog.findViewById(R.id.l_total);
                        txt_selling_price = (TextView) dialog.findViewById(R.id.txt_selling_price);
                        txt_total_txt = (TextView) dialog.findViewById(R.id.txt_total_txt);
                        double t1 = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * 1.0;
                        double w = round(t1, 2);
                        String str1 = String.format("%.2f", w);
                        tv_total.setText(str1);
                        //  tv_total.setText(bean_product1.get(position).getPro_sellingprice());
                        final int[] minPackOfQty = {bean_product1.get(position).getPackQty()};
                        if (kkk == 1) {
                            int currentQty = Integer.parseInt(qun);
                            int remainedQtyToFill = C.modOf(currentQty, minPackOfQty[0]);
                            int k = minPackOfQty[0];
                            if (currentQty < minPackOfQty[0] && remainedQtyToFill > 0)
                                k = currentQty + remainedQtyToFill;
                            else if (currentQty > minPackOfQty[0] && remainedQtyToFill > 0)
                                k = currentQty - remainedQtyToFill + minPackOfQty[0];
                            else
                                k = currentQty + minPackOfQty[0];

                            edt_count.setText(String.valueOf(k));
                        } else {
                            edt_count.setText(String.valueOf(minPackOfQty[0]));
                        }
                        double temp_total = Double.parseDouble(tv_total.getText().toString());
                        double t = Double.parseDouble(tv_total.getText().toString()) * Double.parseDouble(edt_count.getText().toString());
                        double w1 = round(t, 2);
                        String str = String.format("%.2f", w1);
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
                       /* if(mrp > sellingprice){
                            tv_pop_mrp.setVisibility(View.VISIBLE);
                            tv_txt_mrp.setVisibility(View.VISIBLE);
                            tv_pop_mrp.setPaintFlags(tv_pop_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        }else{
                            tv_pop_mrp.setVisibility(View.GONE);
                            tv_txt_mrp.setVisibility(View.GONE);

                        }*/

                        arrOptionTypeID.clear();
                        arrOptionTypeName.clear();
                        arrOptionTypeID = new ArrayList<String>();
                        arrOptionTypeName = new ArrayList<String>();
                        if (bean_productOprtions.size() != 0) {
                            ArrayList<Bean_Attribute> array_attributes = new ArrayList<Bean_Attribute>();

                            //Log.e("121212 ::",""+bean_productOprtions.size());

                            final ArrayList<Bean_ProductOprtion> currentProductOption = new ArrayList<Bean_ProductOprtion>();

                            for (int c = 0; c < bean_productOprtions.size(); c++) {
                                //Log.e("232323 ::",""+bean_productOprtions.get(c).getPro_id());
                                //Log.e("343434 ::",""+bean_product1.get(position).getPro_id());
                                if (bean_product1.get(position).getPro_id().equalsIgnoreCase(bean_productOprtions.get(c).getPro_id())) {
                                    currentProductOption.add(bean_productOprtions.get(c));
                                    Bean_Attribute bean_attribute = new Bean_Attribute();
                                    arrOptionTypeID.add(bean_productOprtions.get(c).getPro_Option_id());
                                    arrOptionTypeName.add("Select " + bean_productOprtions.get(c).getPro_Option_name());
                                    bean_attribute.setOption_id(bean_productOprtions.get(c).getPro_Option_id());
                                    bean_attribute.setOption_name(bean_productOprtions.get(c).getPro_Option_name());

                                    //OptionValue
                                    bean_attribute.setValue_name(bean_productOprtions.get(c).getPro_Option_value_name());
                                    //Log.e("Value Name", "------" + bean_productOprtions.get(c).getPro_Option_value_name());
                                    bean_attribute.setValue_id(bean_productOprtions.get(c).getPro_Option_value_id());

                                    //ProductOptionImage
                                    bean_attribute.setValue_image(bean_productOprtions.get(c).getPro_Option_proimage());


                                    bean_attribute.setValue_product_code(bean_productOprtions.get(c).getPro_Option_procode());
                                    bean_attribute.setValue_mrp(bean_productOprtions.get(c).getPro_Option_mrp());
                                    bean_attribute.setValue_selling_price(bean_productOprtions.get(c).getPro_Option_selling_price());
                                    bean_attribute.setOption_pro_id(bean_productOprtions.get(c).getOption_pro_id());

                                    //Log.e("Product ID", "------" + bean_attribute.getOption_pro_id());

                                    //Log.e("Product ID11", "------" + bean_productOprtions.get(c).getOption_pro_id());
                                    array_attributes.add(bean_attribute);
                                }
                            }
                            if (arrOptionTypeID.size() != 0) {


                                ArrayList<String> temp_array = new ArrayList<String>();
                                for (int i = 0; i < arrOptionTypeID.size(); i++) {
                                    temp_array.add(arrOptionTypeID.get(i));
                                }
                                LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionTypeID);

                                arrOptionTypeID.clear();
                                //Creating Arraylist without duplicate values
                                arrOptionTypeID = new ArrayList<String>(listToSet);


                                //Log.e("AAAABBB", "1 - " + arrOptionTypeID.size() + " 2- "+temp_array.size());


                                for (int p = 0; p < arrOptionTypeID.size(); p++) {
                                    //Log.e("arrOptionTypeID", "----------" + arrOptionTypeID.get(p));
                                }
                                for (int p = 0; p < temp_array.size(); p++) {
                                    //Log.e("temp_array", "----------" + temp_array .get(p));
                                }

                                for (int p = 0; p < array_attributes.size(); p++) {
                                    //Log.e("array_attributes", "----------" + array_attributes .get(p).getOption_id());
                                }

                                array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();

                                for (int p = 0; p < arrOptionTypeID.size(); p++) {

                                    ArrayList<Bean_Attribute> array_attribute = new ArrayList<Bean_Attribute>();

                                    for (int s = 0; s < temp_array.size(); s++) {
                                        if (temp_array.get(s).equalsIgnoreCase(arrOptionTypeID.get(p))) {
                                            ////Log.e("", p + "---" + "option ID - " + temp_array.get(s) + "List hash ID  - " + arrOptionTypeID.get(p));
                                            Bean_Attribute bean_demo = new Bean_Attribute();
                                            bean_demo.setOption_id(array_attributes.get(s).getOption_id());
                                            //Log.e("Option Id", "-----" + array_attributes.get(s).getOption_id());
                                            bean_demo.setOption_name(array_attributes.get(s).getOption_name());
                                            bean_demo.setValue_id(array_attributes.get(s).getValue_id());
                                            bean_demo.setValue_name(array_attributes.get(s).getValue_name());
                                            //Log.e("Value Name", "-----" + array_attributes.get(s).getValue_name());
                                            bean_demo.setValue_mrp(array_attributes.get(s).getValue_mrp());
                                            bean_demo.setValue_product_code(array_attributes.get(s).getValue_product_code());
                                            bean_demo.setValue_selling_price(array_attributes.get(s).getValue_selling_price());
                                            bean_demo.setValue_image(array_attributes.get(s).getValue_image());

                                            //Log.e("bean_demo_images",""+array_attributes.get(s).getValue_image());

                                            bean_demo.setOption_pro_id(array_attributes.get(s).getOption_pro_id());
                                            array_attribute.add(bean_demo);
                                        }
                                    }
                                    //Log.e("", "new bean size -- " + array_attribute.size());

                                    array_attribute_main.add(array_attribute);
                                    //Log.e("", "new bean size -- " + array_attribute_main.size());
                                }


                                for (int i = 0; i < array_attribute_main.size(); i++) {
                                    Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                                    bean.setValue_id("0");
                                    bean.setValue_name(" ");
                                    bean.setValue_mrp("00");
                                    array_value.add(bean);
                                }
                                for (int k = 0; k < array_attribute_main.size(); k++) {

                                    final TextView text = new TextView(Product_List.this);

                                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                    params1.setMargins(2, 2, 2, 2);

                                    text.setLayoutParams(params1);

                                    text.setTextColor(Color.parseColor("#000000"));

                                    text.setTextSize(12);

                                    l_spinner_text.addView(text);

                                    final Spinner spinner = new Spinner(Product_List.this);

                                    spinner.setBackgroundResource(R.drawable.spinner_border);

                                    spinner.setTag("" + k);

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                    params.setMargins(2, 2, 2, 2);

                                    spinner.setLayoutParams(params);

                                    spinner.setAdapter(new MyAdapter(Product_List.this, R.layout.textview, array_attribute_main.get(k)));

                                    l_spinner.addView(spinner);

                                    array_att = new ArrayList<Bean_Attribute>();
                                    array_att = array_attribute_main.get(k);


                                    for (int tt = 0; tt < array_att.size(); tt++) {


                                        text.setText("Select " + array_att.get(tt).getOption_name());


                                        if (k == 0) {
                                            option_id = array_att.get(tt).getOption_id();
                                            option_name = array_att.get(tt).getOption_name();
                                        } else {
                                            option_id = option_id + ", " + array_att.get(tt).getOption_id();
                                            option_name = option_name + ", " + array_att.get(tt).getOption_name();
                                        }

                                        //Log.e("Option id", "" + option_id);
                                    }
                                    final int finalK = k;
                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view,
                                                                   int position1, long id) {
                                            // TODO Auto-generated method stub

                                            // position1=""+position;

                                            minPackOfQty[0] = currentProductOption.get(position1).getMinPackOfQty();

                                            int pos = Integer.parseInt(spinner.getTag().toString());
                                            ArrayList<Bean_Attribute> att_array = new ArrayList<Bean_Attribute>();
                                            att_array = array_attribute_main.get(pos);

                                            selectedProductID[0] = att_array.get(position1).getOption_pro_id();

                                            boolean foundScheme = false;
                                            for (int a = 0; a < bean_Schme_data.size(); a++) {
                                                if (bean_Schme_data.get(a).getSchme_prod_id().equals(selectedProductID[0])) {
                                                    foundScheme = true;
                                                    break;
                                                }
                                            }

                                            if (foundScheme)
                                                img_offerDialog.setVisibility(View.VISIBLE);
                                            else img_offerDialog.setVisibility(View.GONE);

                                            //Log.e("Position",""+att_array.get(position1).getValue_name());
                                            //Log.e("Position",""+att_array.get(position1).getValue_id());
                                            //Log.e("Position", "" + att_array.get(position1).getValue_mrp());
                                            //Log.e("Images", "" + att_array.get(position1).getValue_image().toString());

                                            Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                                            bean.setValue_id(att_array.get(position1).getValue_id());
                                            bean.setValue_name(att_array.get(position1).getValue_name());
                                            bean.setValue_mrp(att_array.get(position1).getValue_mrp());

                                            array_value.set(finalK, bean);


                                            if (att_array.get(position1).getValue_image().equalsIgnoreCase("") || att_array.get(position1).getValue_image().equalsIgnoreCase("null")) {


                                            } else {
                                                //Log.e("Images", "" + att_array.get(position1).getValue_image().toString());
                                                list_of_images.clear();
                                                list_of_images.add(att_array.get(position1).getValue_image());
                                                //adapter.notifyDataSetChanged();
                                            }

                                            if (att_array.get(position1).getValue_selling_price().equalsIgnoreCase("0")) {

                                            } else {


                                                setRefershData();

                                                if (user_data.size() != 0) {
                                                    for (int i = 0; i < user_data.size(); i++) {

                                                        owner_id = user_data.get(i).getUser_id().toString();

                                                        role_id = user_data.get(i).getUser_type().toString();

                                                        if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                                            app = new AppPrefs(Product_List.this);
                                                            role_id = app.getSubSalesId();
                                                            u_id = app.getSalesPersonId();
                                                        } else {
                                                            u_id = owner_id;
                                                        }


                                                    }

                                                } else {
                                                    user_id_main = "";
                                                }
                                                // product_id = tv_pop_pname.getTag().toString();
                                                List<NameValuePair> para = new ArrayList<NameValuePair>();
                                                para.add(new BasicNameValuePair("product_id", att_array.get(position1).getOption_pro_id()));
                                                para.add(new BasicNameValuePair("owner_id", owner_id));
                                                para.add(new BasicNameValuePair("user_id", u_id));
                                                //Log.e("111111111",""+product_id);
                                                //Log.e("222222222",""+owner_id);
                                                //Log.e("333333333",""+u_id);
                                                isNotDone = true;
                                                new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                while (isNotDone) {

                                                }
                                                isNotDone = true;

                                                String json = jsonData;

                                                //String json=GetProductDetailByQty(para);

                                                try {


                                                    //System.out.println(json);

                                                    if (json == null
                                                            || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                                        Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
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

                                                //final int minPackOfQty=bean_product1.get(position).getPackQty();
                                                if (kkk == 1) {
                                                    int currentQty = Integer.parseInt(qun);
                                                    int remainedQtyToFill = C.modOf(currentQty, minPackOfQty[0]);
                                                    int k = minPackOfQty[0];
                                                    if (currentQty < minPackOfQty[0] && remainedQtyToFill > 0)
                                                        k = currentQty + remainedQtyToFill;
                                                    else if (currentQty > minPackOfQty[0] && remainedQtyToFill > 0)
                                                        k = currentQty - remainedQtyToFill + minPackOfQty[0];
                                                    else
                                                        k = currentQty + minPackOfQty[0];

                                                    edt_count.setText(String.valueOf(k));
                                                } else {
                                                    edt_count.setText(String.valueOf(minPackOfQty[0]));
                                                }

                                                tv_pop_mrp.setText("" + att_array.get(position1).getValue_mrp());
                                                tv_pop_sellingprice.setText("" + att_array.get(position1).getValue_selling_price());

                                                tv_pop_pname.setTag("" + att_array.get(position1).getOption_pro_id());
                                                if (att_array.get(position1).getValue_product_code().equalsIgnoreCase("null") || att_array.get(position1).getValue_product_code().equalsIgnoreCase("")) {
                                                    //Log.e("121212",""+att_array.get(position1).getValue_product_code());
                                                    tv_pop_code.setText(" (" + p_code + ")");

                                                } else {
                                                    //Log.e("131313",""+att_array.get(position1).getValue_product_code());
                                                    tv_pop_code.setText(" (" + att_array.get(position1).getValue_product_code() + ")");
                                                    ArrayList<Bean_ProductCart> array_product_cart1 = new ArrayList<Bean_ProductCart>();
                                                    array_product_cart1 = db.is_product_in_cart(tv_pop_code.getText().toString());

                                                    if (array_product_cart1.size() == 0) {

                                                        //   edt_count.setText("1");
                                                      /*  if(tv_pop_code.getText().toString().equalsIgnoreCase("("+p_code+")")){

                                                        }else{
                                                            edt_count.setText("1");
                                                        }*/

                                                    } else {

                                                        int temp_count = Integer.parseInt(edt_count.getText().toString());
                                                        int temp_total_count = temp_count + Integer.parseInt(array_product_cart1.get(0).getPro_qty().toString());
                                                        double temp_total = Double.parseDouble(tv_total.getText().toString());
                                                        edt_count.setText("" + temp_total_count);
                                                        //   tv_total.setText("" + (temp_total + Float.parseFloat(array_product_cart1.get(0).getPro_total().toString().replace("", ""))));
                                                        //result_holder.tvproduct_code.setText("ABC");
                                                    }
                                                }
                                                tv_pop_sellingprice.setTag(att_array.get(position1).getValue_selling_price());

                                                if (edt_count.getText().toString().equalsIgnoreCase("1")) {
                                                    double finalValue = Double.parseDouble(att_array.get(position1).getValue_selling_price().toString());
                                                    double w1 = round(finalValue, 2);
                                                    String str = String.format("%.2f", w1);
                                                    tv_total.setText(str);

                                                } else {
                                                    int counter = Integer.parseInt(edt_count.getText().toString());
                                                    double amt = Double.parseDouble(tv_pop_sellingprice.getText().toString());
                                                    double total = amt * counter;
                                                    double finalValue = (double) (Math.round(total * 100) / 100);
                                                    double w1 = round(finalValue, 2);
                                                    String str = String.format("%.2f", w1);
                                                    tv_total.setText(str);

                                                }
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            // TODO Auto-generated method stub

                                        }
                                    });

                                }


                            }


                        } else {
                            //  GlobalVariable.CustomToast(Product_List.this, "ALLL NO DATA", getLayoutInflater());
                          /*  if(bean_productOprtions.size() == 0){
                                l_view_spinner.setVisibility(View.GONE);
                            }else{
                                l_view_spinner.setVisibility(View.VISIBLE);
                            }*/
                        }


                        dialog.show();

                        String c = edt_count.getText().toString();
                        //Log.e("float - ",""+edt_count.getText().toString());
                        s = Integer.parseInt(c);
                        minuss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String c = edt_count.getText().toString();
                                //Log.e("float - ",""+edt_count.getText().toString());
                                if (!c.isEmpty()) {
                                    s = Integer.parseInt(c);
                                    if (s > 0) {
                                        //int minPackOfQty = bean_product1.get(position).getPackQty();
                                        if (s < minPackOfQty[0]) {
                                            s = 0;
                                        } else {
                                            int toCutOutQty = s % minPackOfQty[0];

                                            Log.e("toCutOut Minus", "-->" + toCutOutQty);

                                            if (toCutOutQty > 0)
                                                s = s - toCutOutQty;
                                            else
                                                s = s - minPackOfQty[0];
                                        }
                                        //s = s - 1;
                                        edt_count.setText(String.valueOf(s));
                                        int counter = s;
                                        double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                                        double total = amt * counter;
                                        double finalValue = (double) (Math.round(total * 100) / 100);
                                        double w1 = round(total, 2);
                                        String str = String.format("%.2f", w1);
                                        tv_total.setText(str);


                                    }
                                }

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
                                //int minPackOfQty = bean_product1.get(position).getPackQty();
                                if (s == 0) {
                                    s = s + minPackOfQty[0];
                                } else if (s > 0) {

                                    int remainder = C.modOf(minPackOfQty[0], s);

                                    if (s < minPackOfQty[0]) {

                                        if (remainder > 0) {
                                            s = s + (minPackOfQty[0] - s);
                                        } else {
                                            s = s + minPackOfQty[0];
                                        }
                                    } else if (s >= minPackOfQty[0]) {
                                        if (remainder > 0) {
                                            s = s - remainder + minPackOfQty[0];
                                        } else {
                                            s = s + minPackOfQty[0];
                                        }
                                    }
                                }
                                //s = s + 1;
                                edt_count.setText(String.valueOf(s));
                                int counter = s;
                                double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                                double total = amt * counter;
                                double finalValue = (double) (Math.round(total * 100) / 100);
                                double w1 = round(total, 2);
                                String str = String.format("%.2f", w1);
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

                                double amt = Double.parseDouble(productpricce);
                                double w1 = round(amt , 2);
                                String str = String.format("%.2f", w1);
                                tv_total.setText(str);

                                // tv_total.setText(productpricce);
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
                                    buy_cart.setEnabled(false);
                                    tv_total.setText("0.00");
                                } else {
                                    buy_cart.setEnabled(true);
                                    //int minPackOfQty = bean_product1.get(position).getPackQty();

                                    /*if(qty>=minPackOfQty[0] && C.modOf(qty,minPackOfQty[0])==0){
                                        String productpricce = tv_pop_sellingprice.getTag().toString();
                                        amount1 = Double.parseDouble(s.toString());
                                        amount1 = Double.parseDouble(productpricce) * amount1;
                                        // float finalValue = (float)(Math.round( amount1 * 100 ) / 100);
                                        double w1 = round(amount1, 2);
                                        String str = String.format("%.2f", w1);
                                        tv_total.setText(str);
                                    }
                                    else {
                                        tv_total.setText("");
                                    }*/

                                    String productpricce = tv_pop_sellingprice.getTag().toString();
                                    amount1 = Double.parseDouble(s.toString());
                                    amount1 = Double.parseDouble(productpricce) * amount1;
                                    // float finalValue = (float)(Math.round( amount1 * 100 ) / 100);
                                    double w1 = round(amount1, 2);
                                    String str = String.format("%.2f", w1);
                                    tv_total.setText(str);

                                    ArrayList<Bean_schemeData> currentProductScheme = new ArrayList<Bean_schemeData>();

                                    for (int i = 0; i < bean_Schme_data.size(); i++) {
                                        if (bean_Schme_data.get(i).getSchme_prod_id().equals(selectedProductID[0])) {
                                            currentProductScheme.add(bean_Schme_data.get(i));
                                        }
                                    }

                                    int selectedPos = -1;
                                    for (int i = 0; i < currentProductScheme.size(); i++) {
                                        int schemeQty = Integer.parseInt(currentProductScheme.get(i).getSchme_qty());

                                        if (qty < schemeQty) {
                                            selectedPos = i;
                                            break;
                                        } else {
                                            selectedPos = currentProductScheme.size() - 1;
                                        }

                                    }

                                    if (currentProductScheme.size() > 0 && selectedPos >= 0) {
                                        txt_availableScheme.setText(currentProductScheme.get(selectedPos).getSchme_name());
                                    } else txt_availableScheme.setText("");

                                }
                            }
                        });

                        buy_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (user_data.size() == 0) {

                                    Intent i = new Intent(Product_List.this, LogInPage.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);


                                } else {

                                    String enteredQty = edt_count.getText().toString().trim();
                                    int currentQty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);
                                    //int minPackOfQty = bean_product1.get(position).getPackQty();

                                    if (currentQty < minPackOfQty[0] || C.modOf(currentQty, minPackOfQty[0]) > 0) {
                                        C.showMinPackAlert(Product_List.this, minPackOfQty[0]);
                                        //Globals.Toast(getApplicationContext(), "Please enter quantity in multiple of "+minPackOfQty+" or tap + / - button");
                                        Log.e("Mod Rem", "-->" + C.modOf(currentQty, minPackOfQty[0]));
                                    } else {
                                        product_id = tv_pop_pname.getTag().toString();
                                        String qty = edt_count.getText().toString();

                                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("product_id", product_id));
                                        params.add(new BasicNameValuePair("user_id", user_id_main));
                                        params.add(new BasicNameValuePair("product_buy_qty", qty));
                                        Log.e("111111111",""+product_id);
                                        Log.e("222222222",""+user_id_main);
                                        Log.e("333333333",""+qty);

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
                                                Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                                                // loadingView.dismiss();

                                            } else {
                                                JSONObject jObj = new JSONObject(json);

                                                String date = jObj.getString("status");

                                                if (date.equalsIgnoreCase("false")) {
                                                    String Message = jObj.getString("message");
                                                    bean_product_schme.clear();
                                                    bean_schme.clear();

                                                } else {

                                                    JSONObject jobj = new JSONObject(json);

                                                    bean_product_schme.clear();
                                                    bean_schme.clear();

                                                    JSONObject jsonArray = jObj.getJSONObject("data");

                                                    JSONObject jschme = jsonArray.getJSONObject("Scheme");

                                                    JSONObject jproduct = jsonArray.getJSONObject("GetProduct");

                                                    JSONObject jlabel = jproduct.getJSONObject("Label");

                                                    Bean_Product bean = new Bean_Product();

                                                    bean.setPro_id(jproduct.getString("id"));

                                                    bean.setPro_cat_id(jproduct.getString("category_id"));
                                                    bean.setPro_code(jproduct.getString("product_code"));
                                                    bean.setPro_name(jproduct.getString("product_name"));
                                                    // bean.setPro_label(jproduct.getString("label_id"));
                                                    bean.setPro_qty(jproduct.getString("qty"));
                                                    bean.setPro_mrp(jproduct.getString("mrp"));

                                                    bean.setPro_sellingprice(jproduct.getString("selling_price"));

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
                                            Log.e("json exce", j.getMessage());
                                        }
                                        //Log.e("45454545",""+bean_product_schme.size());
                                        if (bean_product_schme.size() == 0) {
                                            //Log.e("555555555",""+bean_product_schme.size());
                                            Bean_ProductCart bean = new Bean_ProductCart();
                                            String value_id = "";
                                            String value_name = "";


                                            for (int i = 0; i < array_value.size(); i++) {
                                                if (i == 0) {
                                                    value_name = array_value.get(i).getValue_name();
                                                    value_id = array_value.get(i).getValue_id();
                                                } else {
                                                    value_id = value_id + ", " + array_value.get(i).getValue_id();
                                                    value_name = value_name + ", " + array_value.get(i).getValue_name();

                                                }
                                            }


                                            bean.setPro_id(tv_pop_pname.getTag().toString());

                                            bean.setPro_cat_id(Catid);
                                            //Log.e("Cat_ID....", "" + Catid);
                                            if (list_of_images.size() == 0) {
                                                bean.setPro_Images(bean_product1.get(position).getPro_image());
                                            } else {
                                                bean.setPro_Images(list_of_images.get(0).toString());
                                            }
                                            // bean.setPro_Images(list_of_images.get(position));
                                            //bean.setPro_code(result_holder.tvproduct_code.getText().toString());
                                            bean.setPro_code(tv_pop_code.getText().toString().trim());
                                            //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                            bean.setPro_name(tv_pop_pname.getText().toString());
                                            bean.setPro_qty(edt_count.getText().toString());
                                            bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                            bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString());
                                            bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                            bean.setPro_Option_id(option_id);
                                            //Log.e("-- ", "Option Id " + option_id);

                                            bean.setPro_Option_name(option_name);
                                            //Log.e("-- ", "Option Name " + option_name);

                                            bean.setPro_Option_value_id(value_id);

                                            //Log.e("", "Value Name " + value_id);
                                            bean.setPro_Option_value_name(value_name);
                                            //Log.e("", "Value Name " + value_name);

                                            bean.setPro_total(tv_total.getText().toString());
                                            bean.setPro_schme("");

                                            for (int i = 0; i < 1; i++) {
                                                try {
                                                    JSONObject jobject = new JSONObject();

                                                    jobject.put("user_id", u_id);
                                                    jobject.put("role_id", role_id);
                                                    jobject.put("owner_id", owner_id);
                                                    jobject.put("product_id", tv_pop_pname.getTag().toString());
                                                    jobject.put("category_id", Catid);
                                                    jobject.put("name", tv_pop_pname.getText().toString());
                                                    String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                    String aString = newString.toString().trim().replace(")", "");
                                                    jobject.put("pro_code", aString.toString().trim());
                                                    jobject.put("quantity", edt_count.getText().toString());
                                                    jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                    jobject.put("selling_price", tv_pop_sellingprice.getText().toString());
                                                    jobject.put("option_id", option_id);
                                                    jobject.put("option_name", option_name);
                                                    jobject.put("option_value_id", value_id);
                                                    jobject.put("option_value_name", value_name);
                                                    jobject.put("item_total", tv_total.getText().toString());
                                                    jobject.put("pro_scheme", " ");
                                                    jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                    jobject.put("scheme_id", " ");
                                                    jobject.put("scheme_title", " ");
                                                    jobject.put("scheme_pack_id", " ");
                                                    if (list_of_images.size() == 0) {
                                                        jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                        // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                                    } else {
                                                        jobject.put("prod_img", list_of_images.get(0).toString());
                                                        //   bean.setPro_Images(list_of_images.get(0).toString());
                                                    }


                                                    jarray_cart.put(jobject);
                                                } catch (JSONException e) {

                                                }


                                            }
                                            Log.e("Data 4898", jarray_cart.toString());
                                            array_value.clear();
                                            if (kkk == 1) {
                                                Log.e("014","-->");
                                                new Edit_Product().execute();

                                            } else {

                                                new Add_Product().execute();
                                                Log.e("14","-->");
                                            }

                                            //db.Add_Product_cart(bean);




                                      /*  Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                                        Intent i = new Intent(Product_List.this, Product_List.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);*/


                                        } else {
                                            //Log.e("Type ID : ",""+bean_schme.get(0).getType_id().toString());
                                            if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("1")) {

                                                //Log.e("66666666666", "" + bean_product_schme.size());
                                                Bean_ProductCart bean = new Bean_ProductCart();
                                                String value_id = "";
                                                String value_name = "";


                                                for (int i = 0; i < array_value.size(); i++) {
                                                    if (i == 0) {
                                                        value_name = array_value.get(i).getValue_name();
                                                        value_id = array_value.get(i).getValue_id();
                                                    } else {
                                                        value_id = value_id + ", " + array_value.get(i).getValue_id();
                                                        value_name = value_name + ", " + array_value.get(i).getValue_name();

                                                    }
                                                }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                                bean.setPro_id(tv_pop_pname.getTag().toString());

                                                bean.setPro_cat_id(Catid);
                                                //Log.e("Cat_ID....", "" + Catid);
                                                if (list_of_images.size() == 0) {
                                                    bean.setPro_Images(bean_product1.get(position).getPro_image());
                                                } else {
                                                    bean.setPro_Images(list_of_images.get(0).toString());
                                                }
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
                                                String sell = tv_pop_sellingprice.getText().toString();

                                                double se = Double.parseDouble(tv_pop_sellingprice.getText().toString()) * buyqu;

                                                double se1 = buyqu + getqu;

                                                double fse = se / se1;
                                                double w1 = round(fse, 2);
                                                sell = String.format("%.2f", w1);
                                                // sell = String.valueOf(fse);

                                                int fqu = qu + (int) getqu;

                                                bean.setPro_qty(String.valueOf(fqu));
                                                bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                                bean.setPro_sellingprice(sell);
                                                bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                                bean.setPro_Option_id(option_id);
                                                //Log.e("-- ", "Option Id " + option_id);

                                                bean.setPro_Option_name(option_name);
                                                //Log.e("-- ", "Option Name " + option_name);

                                                bean.setPro_Option_value_id(value_id);

                                                //Log.e("", "Value Name " + value_id);
                                                bean.setPro_Option_value_name(value_name);
                                                //Log.e("", "Value Name " + value_name);

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
                                                        jobject.put("product_id", tv_pop_pname.getTag().toString());
                                                        jobject.put("category_id", Catid);
                                                        jobject.put("name", tv_pop_pname.getText().toString());
                                                        String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                        String aString = newString.toString().trim().replace(")", "");
                                                        jobject.put("pro_code", aString.toString().trim());
                                                        jobject.put("quantity", String.valueOf(fqu));
                                                        jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                        jobject.put("selling_price", sell);
                                                        jobject.put("option_id", option_id);
                                                        jobject.put("option_name", option_name);
                                                        jobject.put("option_value_id", value_id);
                                                        jobject.put("option_value_name", value_name);
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
                                                            jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                            // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                                        } else {
                                                            jobject.put("prod_img", list_of_images.get(0).toString());
                                                            //   bean.setPro_Images(list_of_images.get(0).toString());
                                                        }


                                                        jarray_cart.put(jobject);
                                                    } catch (JSONException e) {

                                                    }


                                                }
                                                Log.e("Data 5065", jarray_cart.toString());
                                                array_value.clear();
                                                if (kkk == 1) {
                                                    Log.e("015","-->");
                                                    new Edit_Product().execute();

                                                } else {

                                                    new Add_Product().execute();
                                                    Log.e("15","-->");
                                                }


                                            } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {

                                                //Log.e("66666666666", "" + bean_product_schme.size());
                                                Bean_ProductCart bean = new Bean_ProductCart();
                                                String value_id = "";
                                                String value_name = "";


                                                for (int i = 0; i < array_value.size(); i++) {
                                                    if (i == 0) {
                                                        value_name = array_value.get(i).getValue_name();
                                                        value_id = array_value.get(i).getValue_id();
                                                    } else {
                                                        value_id = value_id + ", " + array_value.get(i).getValue_id();
                                                        value_name = value_name + ", " + array_value.get(i).getValue_name();

                                                    }
                                                }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                                bean.setPro_id(tv_pop_pname.getTag().toString());
                                                //Log.e("-- ", "producttt Id " + tv_pop_pname.getTag().toString());
                                                // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                                bean.setPro_cat_id(Catid);
                                                //Log.e("Cat_ID....", "" + Catid);
                                                if (list_of_images.size() == 0) {
                                                    bean.setPro_Images(bean_product1.get(position).getPro_image());
                                                } else {
                                                    bean.setPro_Images(list_of_images.get(0).toString());
                                                }
                                                // bean.setPro_Images(list_of_images.get(position));
                                                //bean.setPro_code(result_holder.tvproduct_code.getText().toString());
                                                bean.setPro_code(tv_pop_code.getText().toString().trim());
                                                //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                                bean.setPro_name(tv_pop_pname.getText().toString());
                                                bean.setPro_qty(edt_count.getText().toString());
                                                bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                                bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString());
                                                bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                                bean.setPro_Option_id(option_id);
                                                //Log.e("-- ", "Option Id " + option_id);

                                                bean.setPro_Option_name(option_name);
                                                //Log.e("-- ", "Option Name " + option_name);

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
                                                    jobject.put("product_id", tv_pop_pname.getTag().toString());
                                                    jobject.put("category_id", Catid);
                                                    jobject.put("name", tv_pop_pname.getText().toString());
                                                    String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                    String aString = newString.toString().trim().replace(")", "");
                                                    jobject.put("pro_code", aString.toString().trim());
                                                    jobject.put("quantity", edt_count.getText().toString());
                                                    jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                    jobject.put("selling_price", tv_pop_sellingprice.getText().toString());
                                                    jobject.put("option_id", option_id);
                                                    jobject.put("option_name", option_name);
                                                    jobject.put("option_value_id", value_id);
                                                    jobject.put("option_value_name", value_name);
                                                    jobject.put("item_total", tv_total.getText().toString());
                                                    jobject.put("pro_scheme", " ");
                                                    jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                    jobject.put("scheme_id", " ");
                                                    jobject.put("scheme_title", " ");
                                                    jobject.put("scheme_pack_id", " ");
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


                                                for (int i = 0; i < array_value.size(); i++) {
                                                    if (i == 0) {
                                                        value_name1 = array_value.get(i).getValue_name();
                                                        value_id1 = array_value.get(i).getValue_id();
                                                    } else {
                                                        value_id1 = value_id1 + ", " + array_value.get(i).getValue_id();
                                                        value_name1 = value_name1 + ", " + array_value.get(i).getValue_name();

                                                    }
                                                }


                                                bean_s.setPro_id(bean_product_schme.get(0).getPro_id());

                                                bean_s.setPro_cat_id(bean_product_schme.get(0).getPro_cat_id());

                                                bean_s.setPro_Images(bean_product_schme.get(0).getPro_image());

                                                bean_s.setPro_code(bean_product_schme.get(0).getPro_code());

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
                                                /*if (b > maxqu) {
                                                    b = maxqu;
                                                }*/


                                                bean_s.setPro_qty(String.valueOf(b));


                                                bean_s.setPro_mrp("0");
                                                bean_s.setPro_sellingprice("0");
                                                bean_s.setPro_shortdesc(bean_product_schme.get(0).getPro_label());
                                                bean_s.setPro_Option_id(option_id);
                                                //Log.e("-- ", "Option Id " + option_id);

                                                bean_s.setPro_Option_name(option_name);
                                                //Log.e("-- ", "Option Name " + option_name);

                                                bean_s.setPro_Option_value_id(value_id);

                                                //Log.e("", "Value Name " + value_id);
                                                bean_s.setPro_Option_value_name(value_name);
                                                //Log.e("", "Value Name " + value_name);
                                                bean.setPro_schme_name(bean_schme.get(0).getScheme_name());
                                                bean_s.setPro_total("0");
                                                bean_s.setPro_schme("" + tv_pop_pname.getTag().toString());
                                           /* if (String.valueOf((int) b).toString().equalsIgnoreCase("0")) {
                                                db.delete_product_from_cart_list_schme(tv_pop_pname.getTag().toString());
                                            } else {

                                                db.update_product_from_Schme(tv_pop_pname.getTag().toString(), String.valueOf(b));
                                            }*/

                                                try {
                                                    JSONObject jobject = new JSONObject();

                                                    jobject.put("user_id", u_id);
                                                    jobject.put("role_id", role_id);
                                                    jobject.put("owner_id", owner_id);
                                                    jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                                    jobject.put("category_id", Catid);
                                                    jobject.put("name", bean_product_schme.get(0).getPro_name());
                                                    jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                                    jobject.put("quantity", String.valueOf(b));
                                                    jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                                    //Log.e("D1D1",""+bean_product_schme.get(0).getPro_mrp());
                                                    jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                                    //Log.e("E1E1",""+bean_product_schme.get(0).getPro_sellingprice());
                                                    jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id());
                                                    jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name());
                                                    jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id());
                                                    jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name());
                                                    jobject.put("item_total", "0");
                                                    jobject.put("pro_scheme", "" + tv_pop_pname.getTag().toString());
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
                                                Log.e("Data 5297", jarray_cart.toString());
                                                array_value.clear();
                                                if (kkk == 1) {
                                                    Log.e("016","-->");
                                                    new Edit_Product().execute();

                                                } else {

                                                    new Add_Product().execute();
                                                    Log.e("16","-->");

                                                }

                                            } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("3")) {

                                                //Log.e("66666666666", "" + bean_product_schme.size());
                                                Bean_ProductCart bean = new Bean_ProductCart();
                                                String value_id = "";
                                                String value_name = "";


                                                for (int i = 0; i < array_value.size(); i++) {
                                                    if (i == 0) {
                                                        value_name = array_value.get(i).getValue_name();
                                                        value_id = array_value.get(i).getValue_id();
                                                    } else {
                                                        value_id = value_id + ", " + array_value.get(i).getValue_id();
                                                        value_name = value_name + ", " + array_value.get(i).getValue_name();

                                                    }
                                                }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/


                                                bean.setPro_id(tv_pop_pname.getTag().toString());
                                                //Log.e("-- ", "producttt Id " + tv_pop_pname.getTag().toString());
                                                // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                                bean.setPro_cat_id(Catid);
                                                //Log.e("Cat_ID....", "" + Catid);
                                                if (list_of_images.size() == 0) {
                                                    bean.setPro_Images(bean_product1.get(position).getPro_image());
                                                } else {
                                                    bean.setPro_Images(list_of_images.get(0).toString());
                                                }
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
                                                String sell = tv_pop_sellingprice.getText().toString();

                                                String disc = bean_schme.get(0).getDisc_per();

                                                double se = Double.parseDouble(tv_pop_sellingprice.getText().toString()) * Double.parseDouble(bean_schme.get(0).getDisc_per().toString());

                                                double se1 = se / 100;

                                                double fse = Double.parseDouble(tv_pop_sellingprice.getText().toString()) - se1;
                                                double w = round(fse, 2);
                                                sell = String.format("%.2f", w);
                                                //sell = String.valueOf(fse);

                                                //  int fqu = qu +Math.round(getqu);

                                                bean.setPro_qty(edt_count.getText().toString());
                                                bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                                bean.setPro_sellingprice(sell);
                                                bean.setPro_shortdesc(bean_product1.get(position).getPro_label());
                                                bean.setPro_Option_id(option_id);
                                                //Log.e("-- ", "Option Id " + option_id);

                                                bean.setPro_Option_name(option_name);
                                                //Log.e("-- ", "Option Name " + option_name);

                                                bean.setPro_Option_value_id(value_id);

                                                //Log.e("", "Value Name " + value_id);
                                                bean.setPro_Option_value_name(value_name);
                                                //Log.e("", "Value Name " + value_name);

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
                                                        jobject.put("product_id", tv_pop_pname.getTag().toString());
                                                        jobject.put("category_id", Catid);
                                                        jobject.put("name", tv_pop_pname.getText().toString());
                                                        String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                        String aString = newString.trim().replace(")", "");
                                                        jobject.put("pro_code", aString.trim());
                                                        jobject.put("quantity", edt_count.getText().toString());
                                                        jobject.put("mrp", tv_pop_mrp.getText().toString());
                                                        jobject.put("selling_price", sell);
                                                        jobject.put("option_id", option_id);
                                                        jobject.put("option_name", option_name);
                                                        jobject.put("option_value_id", value_id);
                                                        jobject.put("option_value_name", value_name);
                                                        double f = Double.parseDouble(edt_count.getText().toString()) * Double.parseDouble(sell);
                                                        double w1 = round(f, 2);
                                                        String str = String.format("%.2f", w1);
                                                        jobject.put("item_total", str);
                                                        jobject.put("pro_scheme", " ");
                                                        jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                        jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                        jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                        jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                        if (list_of_images.size() == 0) {
                                                            jobject.put("prod_img", bean_product1.get(position).getPro_image());
                                                            // bean.setPro_Images(bean_product1.get(position).getPro_image());
                                                        } else {
                                                            jobject.put("prod_img", list_of_images.get(0).toString());
                                                            //   bean.setPro_Images(list_of_images.get(0).toString());
                                                        }


                                                        jarray_cart.put(jobject);
                                                    } catch (JSONException e) {

                                                    }


                                                }
                                                Log.e("Data 5452", jarray_cart.toString());
                                                array_value.clear();
                                                if (kkk == 1) {
                                                    Log.e("017","-->");
                                                    new Edit_Product().execute();

                                                } else {

                                                    new Add_Product().execute();
                                                    Log.e("17","-->");
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


                    } else {
                        Globals.CustomToast(Product_List.this, "Please Login First", getLayoutInflater());
                        user_id_main = "";
                    }
                }
            });

            result_holder.img_offer.setTag(bean_product1.get(position).getScheme());
            if (bean_product1.get(position).getScheme().equalsIgnoreCase("") || bean_product1.get(position).getScheme().equalsIgnoreCase("null")) {

                result_holder.img_offer.setVisibility(View.GONE);
            } else {
                result_holder.img_offer.setVisibility(View.VISIBLE);
            }
            result_holder.img_offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;

                    dialogOffer.setContentView(R.layout.scheme_layout);
                    dialogOffer.getWindow().setLayout((6 * width) / 7, (4 * height) / 8);
                    dialogOffer.setCancelable(true);
                    dialogOffer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                    ImageView btn_cancel = (ImageView) dialogOffer.findViewById(R.id.btn_cancel);
                    bean_S_data.clear();
                    ArrayList<String> distinctSchemeIDs = new ArrayList<String>();
                    Log.e("size", "" + bean_Schme_data.size());


                    for (int i = 0; i < bean_Schme_data.size(); i++) {

                        if (bean_Schme_data.get(i).getSchme_prod_id().equalsIgnoreCase(bean_product1.get(position).getPro_id())) {

                            if (!distinctSchemeIDs.contains(bean_Schme_data.get(i).getSchme_id())) {
                                distinctSchemeIDs.add(bean_Schme_data.get(i).getSchme_id());
                                Bean_schemeData beans = new Bean_schemeData();
                                beans.setSchme_id(bean_Schme_data.get(i).getSchme_id());
                                Log.e("ID", "" + bean_product1.get(position).getPro_id());
                                Log.e("name", "" + bean_Schme_data.get(i).getSchme_name());
                                beans.setSchme_name(bean_Schme_data.get(i).getSchme_name());
                                beans.setCategory_id(bean_Schme_data.get(i).getCategory_id());
                                beans.setSchme_qty(bean_Schme_data.get(i).getSchme_qty());
                                beans.setSchme_buy_prod_id(bean_Schme_data.get(i).getSchme_buy_prod_id());
                                beans.setSchme_prod_id(bean_Schme_data.get(i).getSchme_prod_id());

                                bean_S_data.add(beans);
                            }
                        } else {

                        }


                    }

                    ExpandableListView listSchemes = (ExpandableListView) dialogOffer.findViewById(R.id.listSchemes);

                    final List<String> schemeHeaders = new ArrayList<String>();
                    final HashMap<String, List<Bean_schemeData>> schemeChildList = new HashMap<String, List<Bean_schemeData>>();

                    List<Bean_schemeData> extraSpecialScheme = new ArrayList<Bean_schemeData>();
                    List<Bean_schemeData> specialScheme = new ArrayList<Bean_schemeData>();
                    List<Bean_schemeData> generalScheme = new ArrayList<Bean_schemeData>();

                    for (int i = 0; i < bean_S_data.size(); i++) {
                        if (bean_S_data.get(i).getCategory_id().equals(C.EXTRA_SPECIAL_SCHEME)) {
                            extraSpecialScheme.add(bean_S_data.get(i));
                        } else if (bean_S_data.get(i).getCategory_id().equals(C.SPECIAL_SCHEME)) {
                            specialScheme.add(bean_S_data.get(i));
                        } else {
                            generalScheme.add(bean_S_data.get(i));
                        }
                    }

                    if (extraSpecialScheme.size() > 0) {
                        schemeHeaders.add("App Offers");
                        schemeChildList.put(schemeHeaders.get(schemeHeaders.size() - 1), extraSpecialScheme);
                    }

                    if (specialScheme.size() > 0) {
                        schemeHeaders.add("Special Offers");
                        schemeChildList.put(schemeHeaders.get(schemeHeaders.size() - 1), specialScheme);
                    }

                    if (generalScheme.size() > 0) {
                        schemeHeaders.add("Offers");
                        schemeChildList.put(schemeHeaders.get(schemeHeaders.size() - 1), generalScheme);
                    }

                    final ExpandableSchemeAdapter schemeAdapter = new ExpandableSchemeAdapter(schemeHeaders, schemeChildList);
                    listSchemes.setAdapter(schemeAdapter);

                    listSchemes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                            setRefershData();
                            String selectedSchemeID = ((Bean_schemeData) schemeAdapter.getChild(groupPosition, childPosition)).getSchme_id();
                            if (user_data.size() != 0) {
                                for (int i = 0; i < user_data.size(); i++) {

                                    owner_id = user_data.get(i).getUser_id().toString();

                                    role_id = user_data.get(i).getUser_type().toString();

                                    if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                        app = new AppPrefs(Product_List.this);
                                        role_id = app.getSubSalesId();
                                        u_id = app.getSalesPersonId();
                                    } else {
                                        u_id = owner_id;
                                    }


                                }


                                // product_id = tv_pop_pname.getTag().toString();
                                List<NameValuePair> para = new ArrayList<NameValuePair>();
                                para.add(new BasicNameValuePair("product_id", bean_product1.get(position).getPro_id()));
                                para.add(new BasicNameValuePair("owner_id", owner_id));
                                para.add(new BasicNameValuePair("user_id", u_id));
                                Log.e("111111111", "" + bean_product1.get(position).getPro_id());
                                Log.e("222222222", "" + owner_id);
                                Log.e("333333333", "" + u_id);
                                isNotDone = true;
                                new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                while (isNotDone) {

                                }
                                isNotDone = true;

                                String json = jsonData;
                                //String json = GetProductDetailByQty(para);

                                try {


                                    //System.out.println(json);

                                    if (json == null
                                            || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                        Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
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
                                Log.e("kkk", "" + kkk);
                                product_id = bean_product1.get(position).getPro_id();
                                String qty = schemeChildList.get(schemeHeaders.get(groupPosition)).get(childPosition).getSchme_qty();
                                Log.e("qty", "" + qty);

                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("product_id", product_id));
                                params.add(new BasicNameValuePair("user_id", user_id_main));
                                params.add(new BasicNameValuePair("product_buy_qty", qty));
                                params.add(new BasicNameValuePair("scheme_id", selectedSchemeID));
                                Log.e("111111111", "" + product_id);
                                Log.e("222222222", "" + user_id_main);
                                Log.e("333333333", "" + qty);

                                isNotDone = true;
                                new GetProductDetailByCode(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                while (isNotDone) {

                                }
                                isNotDone = true;
                                String jsons = jsonData; //GetProductDetailByCode(params);

                                //new check_schme().execute();
                                try {


                                    //System.out.println(json);

                                    if (jsons == null
                                            || (jsons.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                        Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                                        // loadingView.dismiss();

                                    } else {
                                        JSONObject jObj = new JSONObject(jsons);

                                        String date = jObj.getString("status");

                                        if (date.equalsIgnoreCase("false")) {
                                            String Message = jObj.getString("message");
                                            bean_product_schme.clear();
                                            bean_schme.clear();
                                            //Log.e("11111111",""+product_id);
                                            //   Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                                            // loadingView.dismiss();
                                        } else {

                                            JSONObject jobj = new JSONObject(jsons);


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


                                            //loadingView.dismiss();

                                        }

                                    }
                                } catch (Exception j) {
                                    j.printStackTrace();
                                    //Log.e("json exce",j.getMessage());
                                }
                                //Log.e("45454545",""+bean_product_schme.size());
                                if (bean_product_schme.size() == 0) {
                                    Log.e("1111111111111", "" + qty);
                                } else {
                                    Log.e("2222222222222222", "" + qty);
                                    //Log.e("Type ID : ",""+bean_schme.get(0).getType_id().toString());
                                    if (bean_schme.get(0).getType_id().equalsIgnoreCase("1")) {

                                        jarray_cart = new JSONArray();

                                        String getq = bean_schme.get(0).getGet_qty();
                                        String buyq = bean_schme.get(0).getBuy_qty();
                                        String maxq = bean_schme.get(0).getMax_qty();

                                        double getqu = Double.parseDouble(getq);
                                        double buyqu = Double.parseDouble(buyq);
                                        double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);


                                        String sell = bean_product1.get(position).getPro_sellingprice();

                                        double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice().toString()) * buyqu;

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
                                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                                jobject.put("category_id", Catid);
                                                jobject.put("name", bean_product1.get(position).getPro_name());

                                                jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                                jobject.put("quantity", String.valueOf(fqu));
                                                jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                                jobject.put("selling_price", sell);
                                                jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                                jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                                jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
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
                                                    jobject.put("prod_img", bean_product1.get(position).getPro_image());
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
                                        Log.e("Data 5868", jarray_cart.toString());
                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {
                                            Log.e("018","-->");
                                            new Edit_Product().execute();
                                            dialogOffer.dismiss();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("18","-->");
                                            dialogOffer.dismiss();
                                        }


                                    } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {

                                        jarray_cart = new JSONArray();
                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", u_id);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", bean_product1.get(position).getPro_id());
                                            jobject.put("category_id", Catid);
                                            jobject.put("name", bean_product1.get(position).getPro_name());

                                            jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                            jobject.put("quantity", qty);
                                            jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                            jobject.put("selling_price", bean_product1.get(position).getPro_sellingprice());
                                            jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                            jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                            jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                            jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                            double f = Integer.parseInt(qty) * Double.parseDouble(bean_product1.get(position).getPro_sellingprice());
                                            double w = round(f, 2);
                                            String str = String.format("%.2f", w);
                                            jobject.put("item_total", str);
                                            jobject.put("pro_scheme", " ");
                                            jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                            jobject.put("scheme_id", " ");
                                            jobject.put("scheme_title", " ");
                                            jobject.put("scheme_pack_id", " ");
                                            if (list_of_images.size() == 0) {
                                                jobject.put("prod_img", bean_product1.get(position).getPro_image());
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
                                        //double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);

                                        int a = (int) (qu / buyqu);
                                        int b = (int) (a * getqu);
                                        /*if (b > maxqu) {
                                            b = maxqu;
                                        } else {
                                            b = b;
                                        }*/


                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", u_id);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                            jobject.put("category_id", Catid);
                                            jobject.put("name", bean_product_schme.get(0).getPro_name());
                                            jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                            jobject.put("quantity", String.valueOf(b));
                                            jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                            //Log.e("D1D1",""+bean_product_schme.get(0).getPro_mrp());
                                            jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                            //Log.e("E1E1",""+bean_product_schme.get(0).getPro_sellingprice());
                                            jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id());
                                            jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name());
                                            jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id());
                                            jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name());
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
                                        Log.e("Data 5983", jarray_cart.toString());
                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {
                                            Log.e("019","-->");
                                            new Edit_Product().execute();
                                            dialogOffer.dismiss();

                                        } else {

                                            new Add_Product().execute();
                                            Log.e("19","-->");
                                            dialogOffer.dismiss();
                                        }

                                    } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("3")) {


                                        String getq = bean_schme.get(0).getGet_qty();
                                        String buyq = bean_schme.get(0).getBuy_qty();
                                        String maxq = bean_schme.get(0).getMax_qty();

                                        double getqu = Double.parseDouble(getq);
                                        double buyqu = Double.parseDouble(buyq);
                                        double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);


                                        String sell = bean_product1.get(position).getPro_sellingprice();

                                        String disc = bean_schme.get(0).getDisc_per();

                                        double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * Double.parseDouble(bean_schme.get(0).getDisc_per());

                                        double se1 = se / 100;

                                        double fse = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) - se1;
                                        double w = round(fse, 2);
                                        sell = String.format("%.2f", w);
                                        jarray_cart = new JSONArray();

                                        for (int i = 0; i < 1; i++) {
                                            try {
                                                JSONObject jobject = new JSONObject();

                                                jobject.put("user_id", u_id);
                                                jobject.put("role_id", role_id);
                                                jobject.put("owner_id", owner_id);
                                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                                jobject.put("category_id", Catid);
                                                jobject.put("name", bean_product1.get(position).getPro_name());

                                                jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                                jobject.put("quantity", qty);
                                                jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                                jobject.put("selling_price", sell);
                                                jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                                jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                                jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                                double f = Double.parseDouble(qty) * Double.parseDouble(sell);
                                                double w1 = round(f, 2);
                                                String str = String.format("%.2f", w1);
                                                jobject.put("item_total", str);
                                                jobject.put("pro_scheme", " ");
                                                jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                if (list_of_images.size() == 0) {
                                                    jobject.put("prod_img", bean_product1.get(position).getPro_image());
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
                                        Log.e("Data 6067", jarray_cart.toString());
                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {
                                            Log.e("020","-->");
                                            new Edit_Product().execute();
                                            dialogOffer.dismiss();
                                        } else {

                                            new Add_Product().execute();
                                            Log.e("20","-->");
                                            dialogOffer.dismiss();
                                        }
                                    }
                                }

                            } else {

                                Globals.CustomToast(Product_List.this, "Please Login First", getLayoutInflater());
                                user_id_main = "";

                            }

                            return true;
                        }
                    });


                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogOffer.dismiss();
                        }
                    });
                    dialogOffer.show();

                }
            });


            result_holder.tvproduct_name.setText(bean_product1.get(position)
                    .getPro_name());
            result_holder.tvproduct_code.setText("(" + bean_product1.get(position)
                    .getPro_code() + ")");

            result_holder.tv_product_mrp.setText(bean_product1.get(position)
                    .getPro_mrp());
            result_holder.tv_product_sellingprice.setText(bean_product1.get(position)
                    .getPro_sellingprice());
            p_code = bean_product1.get(position).getPro_code();
            double mrp = Double.parseDouble(result_holder.tv_product_mrp.getText().toString());
            double sellingprice = Double.parseDouble(result_holder.tv_product_sellingprice.getText().toString());
            if (mrp > sellingprice) {
                result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                result_holder.txt_mrp.setVisibility(View.VISIBLE);
                //    result_holder.off_tag.setVisibility(View.VISIBLE);


               /* double off = mrp - sellingprice;
                double offa = off * 100;
                double o = offa / mrp;
                int a = (int) o;*/

                /*float percent = 100 - (Float.parseFloat(bean_product1.get(position)
                        .getPro_sellingprice()) * 100 / Float.parseFloat(bean_product1.get(position)
                        .getPro_mrp()));

                result_holder.off_tag.setText(String.format("%.0f", percent) + "% OFF");*/
                double percent = (Double.parseDouble(bean_product1.get(position)
                        .getPro_mrp()) - Double.parseDouble(bean_product1.get(position)
                        .getPro_sellingprice())) * 100 / Double.parseDouble(bean_product1.get(position)
                        .getPro_mrp());

                result_holder.off_tag.setText(String.format("%.0f", percent) + "% OFF");


                result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                        .getPro_mrp());

                result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                        .getPro_sellingprice());
                result_holder.tv_product_mrp.setPaintFlags(result_holder.tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            } else {
                result_holder.tv_product_mrp.setVisibility(View.GONE);
                result_holder.txt_mrp.setVisibility(View.GONE);
                result_holder.off_tag.setVisibility(View.GONE);
                result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                        .getPro_sellingprice());
            }
            if (user_data.size() != 0) {
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
                    result_holder.tv_product_mrp.setVisibility(View.GONE);
                    result_holder.txt_mrp.setVisibility(View.GONE);
                    result_holder.off_tag.setVisibility(View.GONE);
                    result_holder.tv_product_sellingprice.setVisibility(View.GONE);
                    result_holder.txt_selling.setVisibility(View.GONE);


                } else {

                    p_code = bean_product1.get(position).getPro_code();
                    if (mrp > sellingprice) {
                        result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                        result_holder.txt_mrp.setVisibility(View.VISIBLE);
                        //    result_holder.off_tag.setVisibility(View.VISIBLE);
                        /*double off = mrp - sellingprice;
                        double offa = off * 100;
                        double o = offa / mrp;
                        int a = (int) o;*/

                        /*float percent = 100 - (Float.parseFloat(bean_product1.get(position)
                                .getPro_sellingprice()) * 100 / Float.parseFloat(bean_product1.get(position)
                                .getPro_mrp()));


                        result_holder.off_tag.setText(String.format("%.0f", percent) + "% OFF");*/
                        double percent = (Double.parseDouble(bean_product1.get(position).getPro_mrp()) - Double.parseDouble(bean_product1.get(position).getPro_sellingprice())) * 100 / Double.parseDouble(bean_product1.get(position).getPro_mrp());


                        result_holder.off_tag.setText(String.format("%.1f", percent) + "% OFF");
                        result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                                .getPro_mrp());

                        result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                                .getPro_sellingprice());
                        result_holder.tv_product_mrp.setPaintFlags(result_holder.tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                    } else {
                        result_holder.tv_product_mrp.setVisibility(View.GONE);
                        result_holder.txt_mrp.setVisibility(View.GONE);
                        result_holder.off_tag.setVisibility(View.GONE);
                        result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                                .getPro_sellingprice());
                    }
                }
            }
            result_holder.tvproduct_packof.setText(bean_product1.get(position)
                    .getPro_label());


            imageloader = new ImageLoader(Product_List.this);

            //Log.e("11111111111111111111",""+bean_product1.get(position).getPro_image());

            // imageloader.DisplayImage(Globals.server_link +"files/" +bean_product1.get(position).getPro_image(), result_holder.img_photo);
            Picasso.with(getApplicationContext())
                    .load(Globals.server_link + "files/" + bean_product1.get(position).getPro_image())
                    .placeholder(R.drawable.btl_watermark)
                    .into(result_holder.img_photo);


            float mrpPrice = Float.parseFloat(bean_product1.get(position).getPro_mrp());

            if (mrpPrice <= 0) {
                result_holder.btn_enquiry.setVisibility(View.VISIBLE);
                result_holder.btn_buyonline.setVisibility(View.GONE);
                result_holder.layout_prices.setVisibility(View.INVISIBLE);
                result_holder.img_offer.setVisibility(View.GONE);
            }

            result_holder.btn_enquiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), NonMRPProductEnquiry.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("productName", bean_product1.get(position).getPro_name());
                    intent.putExtra("productID", bean_product1.get(position).getPro_id());
                    startActivity(intent);
                }
            });

            return convertView;
        }

    }

    public class get_Product extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("category_id", Catid));

                parameters.add(new BasicNameValuePair("page", "" + page_id));

                parameters.add(new BasicNameValuePair("user_id", user_id_main));

                parameters.add(new BasicNameValuePair("role_id", role_id));


                Log.e("--", "--" + parameters.toString());

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_GetProductList", ServiceHandler.POST, parameters);

                System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            loadingView.dismiss();

            Log.e("JSON ProList", result_1);

            try {


                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Product_List.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        //bean_productImages.clear();
                        db = new DatabaseHandler(Product_List.this);
                        db.Delete_ALL_table();
                        db.close();

                        page_limit = jObj.getString("total_page_count");

                        JSONArray jsonwish = jObj.optJSONArray("wish_list");

                        whereToBuyVisibility = jObj.getString("where_to_buy").equals("1");

                        WishList = new ArrayList<String>();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }


                        JSONObject jobj = new JSONObject(result_1);


                        JSONArray jsonArray = jObj.optJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jproduct = jsonObject.getJSONObject("Product");


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
                            String minPackOfQty = jproduct.getString("pack_of_qty");
                            bean.setPackQty(Integer.parseInt(minPackOfQty.isEmpty() ? "1" : minPackOfQty));
                            //   bean.setPro_moreinfo(jproduct.getString("more_info"));
                            JSONArray jschme = jsonObject.getJSONArray("Scheme");
                            //Log.e("A1111111",""+jschme.length());

                            String sa = "";

                            for (int s1 = 0; s1 < jschme.length(); s1++) {
                                JSONObject jProductScheme = jschme.getJSONObject(s1);

                                ArrayList<String> a = new ArrayList<String>();
                                a.add(jProductScheme.getString("scheme_name"));
                                //Log.e("A2222222",""+jProductScheme.getString("scheme_name"));
                                sa += jProductScheme.getString("scheme_name").toString() + "\n";
                                bean.setSchemea(a);
                            }


                            //Log.e("A3333333",""+sa);
                            bean.setScheme(sa);
                            // bean.setScheme(jschme.getString("scheme_name"));
                            JSONObject jlabel = jsonObject.getJSONObject("Label");
                            bean.setPro_label(jlabel.getString("name"));
                            bean_product1.add(bean);
                            Bean_Desc bean1 = new Bean_Desc();
                            bean1.setPro_id(jproduct.getString("id"));
                            //   Toast.makeText(Product_List.this, ""+bean1.getPro_id()+"-"+jproduct.getString("id"), Toast.LENGTH_SHORT).show();
                            bean1.setDescription(jproduct.getString("description"));
                            bean1.setSpecification(jproduct.getString("specification"));
                            bean1.setTechnical(jproduct.getString("technical"));
                            bean1.setCatalogs(jproduct.getString("catalogs"));
                            bean1.setGuarantee(jproduct.getString("guarantee"));
                            bean1.setVideos(jproduct.getString("videos"));
                            bean1.setGeneral(jproduct.getString("general"));
                            bean_desc1.add(bean1);



                                  /*  JSONArray jProductFeature = jsonObject.getJSONArray("ProductFeature");

                                    for (int f = 0; f < jProductFeature.length(); f++) {
                                        JSONObject jproProductFeature = jProductFeature.getJSONObject(f);
                                        JSONObject jsubobject1 = jproProductFeature.getJSONObject("Attribute");

                                        Bean_ProductFeature beanf = new Bean_ProductFeature();


                                        beanf.setPro_feature_id(jsubobject1.getString("id"));
                                        beanf.setPro_id(jproProductFeature.getString("product_id"));
                                        beanf.setPro_feature_name(jsubobject1.getString("name"));
                                        beanf.setPro_feature_value(jproProductFeature.getString("attribute_value"));


                                        //beanf.setPro_name(jsonobject.getString("product_name"));


                                        bean_productFeatures.add(beanf);

                                    }*/

                            JSONArray jProductOption = jsonObject.getJSONArray("ProductOption");


                            for (int s = 0; s < jProductOption.length(); s++) {
                                JSONObject jProductOptiono = jProductOption.getJSONObject(s);
                                JSONObject productObject = jProductOptiono.getJSONObject("Product");
                                JSONObject jPOOption = jProductOptiono.getJSONObject("Option");
                                JSONObject jPOOptionValue = jProductOptiono.getJSONObject("OptionValue");
                                JSONArray jPOProductOptionImage = jProductOptiono.getJSONArray("ProductOptionImage");
                                JSONArray schemeArray = jProductOptiono.getJSONArray("Scheme");

                                for (int m = 0; m < schemeArray.length(); m++) {
                                    JSONObject schemeObj = schemeArray.getJSONObject(m);

                                    Bean_schemeData beans = new Bean_schemeData();
                                    beans.setSchme_id(schemeObj.getString("id"));
                                    beans.setSchme_name(schemeObj.getString("scheme_name"));
                                    beans.setSchme_qty(schemeObj.getString("buy_prod_qty"));
                                    beans.setCategory_id(schemeObj.getString("category_id"));
                                    beans.setSchme_buy_prod_id(schemeObj.getString("buy_prod_id"));
                                    beans.setSchme_prod_id(jProductOptiono.getString("product_id"));
                                    bean_Schme_data.add(beans);
                                }

                                Bean_ProductOprtion beanoption = new Bean_ProductOprtion();
                                // arrOptionType=new ArrayList<String>();


                                //Option
                                beanoption.setPro_Option_id(jPOOption.getString("id"));
                                beanoption.setPro_Option_name(jPOOption.getString("name"));
                                beanoption.setMinPackOfQty(Integer.parseInt(productObject.getString("pack_of_qty")));

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


                            //Log.e("Option Size1212",""+bean_productOprtions.size());

                               /* LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                                arrOptionType.clear();
                                //Creating Arraylist without duplicate values
                                arrOptionType = new ArrayList<String>(listToSet);

                                //Log.e("AAAA", "" + arrOptionType.size());

                                arrOptionSize.add(""+arrOptionType);*/

                                 /*   JSONArray jprostdspec = jsonObject.getJSONArray("ProductStandardSpecification");

                                    for (int s = 0; s < jprostdspec.length(); s++) {
                                        JSONObject jpss = jprostdspec.getJSONObject(s);
                                        JSONObject jpssAttribute = jpss.getJSONObject("Attribute");

                                        Bean_ProductStdSpec beanstudspec = new Bean_ProductStdSpec();

//attribute
                                        beanstudspec.setPro_Stdspec_id(jpssAttribute.getString("id"));
                                        beanstudspec.setPro_Stdspec_name(jpssAttribute.getString("name"));

                                        beanstudspec.setPro_id(jpss.getString("product_id"));
                                        beanstudspec.setPro_Stdspec_value(jpss.getString("attribute_value"));


                                        bean_productStdSpecs.add(beanstudspec);

                                    }*/


                            JSONArray jpProductImage = jsonObject.getJSONArray("ProductImage");

                            for (int s = 0; s < jpProductImage.length(); s++) {
                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                beanproductimg.setPro_id(jpimg.getString("product_id"));
                                beanproductimg.setPro_Images(jpimg.getString("image"));

                                bean_productImages.add(beanproductimg);

                            }

                        }
                        //Log.e("Size:Product ::", "" + bean_product1.size());

                        //Log.e("Size:Desc ::", "" + bean_desc1.size());

                        //Log.e("Size:ProductOptions ::", "" + bean_productOprtions.size());

                        //Log.e("Size:ProductIamge ::", "" + bean_productImages.size());

                        SetRefershDataProduct();
                        if (bean_product_db.size() == 0) {
                            //Log.e("Datbase....11", "" + bean_product_db.size());
                            for (int a = 0; a < bean_product1.size(); a++) {
                                //Log.e("List....11", "" + bean_product1.size());
                                db = new DatabaseHandler(Product_List.this);
                                db.Add_Product(new Bean_Product(bean_product1.get(a).getPro_id(),
                                        bean_product1.get(a).getPro_code(),
                                        bean_product1.get(a).getPro_name(),
                                        bean_product1.get(a).getPro_label(),
                                        bean_product1.get(a).getPro_mrp(),
                                        bean_product1.get(a).getPro_sellingprice(),
                                        bean_product1.get(a).getPro_shortdesc(),
                                        bean_product1.get(a).getPro_moreinfo(),
                                        bean_product1.get(a).getPro_cat_id(),
                                        bean_product1.get(a).getPro_qty(),
                                        bean_product1.get(a).getPro_image()));
                                db.close();

                            }
                        } else {

                            //Log.e("Datbase....22", "" + bean_product_db.size());
                            for (int a = 0; a < bean_product1.size(); a++) {
                                //Log.e("List....22", "" + bean_product1.size());
                                int k = 0;
                                for (int b = 0; b < bean_product_db.size(); b++) {
                                    if (k != 1) {
                                        if (bean_product_db.get(b).getPro_id().equalsIgnoreCase(bean_product1.get(a).getPro_id())) {

                                        } else {
                                            db = new DatabaseHandler(Product_List.this);
                                            db.Add_Product(new Bean_Product(bean_product1.get(a).getPro_id(),
                                                    bean_product1.get(a).getPro_code(),
                                                    bean_product1.get(a).getPro_name(),
                                                    bean_product1.get(a).getPro_label(),
                                                    bean_product1.get(a).getPro_mrp(),
                                                    bean_product1.get(a).getPro_sellingprice(),
                                                    bean_product1.get(a).getPro_shortdesc(),
                                                    bean_product1.get(a).getPro_moreinfo(),
                                                    bean_product1.get(a).getPro_cat_id(),
                                                    bean_product1.get(a).getPro_qty(),
                                                    bean_product1.get(a).getPro_image()));
                                            db.close();
                                            k = 1;
                                        }
                                    }
                                }


                            }

                        }
                        SetRefershDataProduct();
                        //Log.e("Product Size", "" + bean_product_db.size());
                        setRefershDataOption();
                        if (bean_productOprtions_db.size() == 0) {

                            for (int a = 0; a < bean_productOprtions.size(); a++) {
                                //Log.e("123", "" + bean_productOprtions.size());
                                db = new DatabaseHandler(Product_List.this);
                                db.Add_Product_Option(new Bean_ProductOprtion(bean_productOprtions.get(a).getPro_id(),
                                        bean_productOprtions.get(a).getPro_cat_id(),
                                        bean_productOprtions.get(a).getPro_Option_id(),
                                        bean_productOprtions.get(a).getPro_Option_name(),
                                        bean_productOprtions.get(a).getPro_Option_value_id(),
                                        bean_productOprtions.get(a).getPro_Option_value_name(),
                                        bean_productOprtions.get(a).getPro_Option_mrp(),
                                        bean_productOprtions.get(a).getPro_Option_selling_price(),
                                        bean_productOprtions.get(a).getPro_Option_proimage(),
                                        bean_productOprtions.get(a).getPro_Option_procode()

                                ));
                                db.close();

                            }
                        } else {

                            //Log.e("Datbase....22", "" + bean_productOprtions.size());
                            for (int a = 0; a < bean_productOprtions.size(); a++) {
                                //Log.e("List....22", "" + bean_productOprtions.size());
                                int k = 0;
                                for (int b = 0; b < bean_productOprtions_db.size(); b++) {
                                    if (k != 1) {
                                        if (bean_productOprtions_db.get(b).getPro_id().equalsIgnoreCase(bean_productOprtions.get(a).getPro_id())) {

                                        } else {
                                            db = new DatabaseHandler(Product_List.this);
                                            db.Add_Product_Option(new Bean_ProductOprtion(bean_productOprtions.get(a).getPro_id(),
                                                    bean_productOprtions.get(a).getPro_cat_id(),
                                                    bean_productOprtions.get(a).getPro_Option_id(),
                                                    bean_productOprtions.get(a).getPro_Option_name(),
                                                    bean_productOprtions.get(a).getPro_Option_value_id(),
                                                    bean_productOprtions.get(a).getPro_Option_value_name(),
                                                    bean_productOprtions.get(a).getPro_Option_mrp(),
                                                    bean_productOprtions.get(a).getPro_Option_selling_price(),
                                                    bean_productOprtions.get(a).getPro_Option_proimage(),
                                                    bean_productOprtions.get(a).getPro_Option_procode()

                                            ));
                                            db.close();
                                            k = 1;
                                        }
                                    }
                                }


                            }

                        }
                        setRefershDataOption();
                        //Log.e("Option Size", "" + bean_productOprtions_db.size());

                        setRefershDataImage();
                        if (bean_productImages_db.size() == 0) {

                            for (int a = 0; a < bean_productImages.size(); a++) {
                                //Log.e("123", "" + bean_productImages.size());
                                db = new DatabaseHandler(Product_List.this);
                                db.Add_ProductImage(new Bean_ProductImage(bean_productImages.get(a).getPro_id(),
                                        bean_productImages.get(a).getPro_cat_id(),
                                        bean_productImages.get(a).getPro_Images())
                                );
                                db.close();

                            }
                        } else {

                            //Log.e("Datbase....22", "" + bean_productImages.size());
                            for (int a = 0; a < bean_productImages.size(); a++) {
                                //Log.e("List....22", "" + bean_productImages.size());
                                int k = 0;
                                for (int b = 0; b < bean_productImages_db.size(); b++) {
                                    if (k != 1) {
                                        if (bean_productImages_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_productImages.get(a).getPro_id().toString())) {

                                        } else {
                                            db = new DatabaseHandler(Product_List.this);
                                            db.Add_ProductImage(new Bean_ProductImage(bean_productImages.get(a).getPro_id(),
                                                    bean_productImages.get(a).getPro_cat_id(),
                                                    bean_productImages.get(a).getPro_Images())
                                            );
                                            db.close();
                                            k = 1;
                                        }
                                    }
                                }


                            }

                        }
                        setRefershDataImage();
                        //Log.e("Image Size", "" + bean_productImages_db.size());

                        SetRefershDataProductDesc();


                        if (bean_desc_db.size() == 0) {

                            for (int a = 0; a < bean_desc1.size(); a++) {
                                //Log.e("123", "" + bean_desc1.size());
                                db = new DatabaseHandler(Product_List.this);
                                db.Add_Product_desc(new Bean_Desc(bean_desc1.get(a).getPro_id(),
                                        bean_desc1.get(a).getDescription(),
                                        bean_desc1.get(a).getSpecification(),
                                        bean_desc1.get(a).getTechnical(),
                                        bean_desc1.get(a).getCatalogs(),
                                        bean_desc1.get(a).getGuarantee(),
                                        bean_desc1.get(a).getVideos(),
                                        bean_desc1.get(a).getGeneral())

                                );
                                db.close();

                            }
                        } else {

                            //Log.e("Datbase....22", "" + bean_desc1.size());
                            for (int a = 0; a < bean_desc1.size(); a++) {
                                //Log.e("List....22", "" + bean_desc1.size());
                                int k = 0;
                                for (int b = 0; b < bean_desc_db.size(); b++) {
                                    if (k != 1) {
                                        if (bean_desc_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_desc1.get(a).getPro_id().toString())) {

                                        } else {
                                            db = new DatabaseHandler(Product_List.this);
                                            db.Add_Product_desc(new Bean_Desc(bean_desc1.get(a).getPro_id(),
                                                    bean_desc1.get(a).getDescription(),
                                                    bean_desc1.get(a).getSpecification(),
                                                    bean_desc1.get(a).getTechnical(),
                                                    bean_desc1.get(a).getCatalogs(),
                                                    bean_desc1.get(a).getGuarantee(),
                                                    bean_desc1.get(a).getVideos(),
                                                    bean_desc1.get(a).getGeneral())

                                            );
                                            db.close();
                                            k = 1;
                                        }
                                    }
                                }


                            }

                        }
                        SetRefershDataProductDesc();
                        //Log.e("bean_desc_db Size", "" + bean_desc_db.size());


                        // list_product.setSelection(current_record);
                        int firstVisPos = list_product.getFirstVisiblePosition();

                        list_product.setSelection(firstVisPos);

                        if (flagscroll.equalsIgnoreCase("2")) {

                            list_product.setVisibility(View.VISIBLE);

                            list_product.setAdapter(new CustomResultAdapterDoctor());
                        } else if (flagscroll.equalsIgnoreCase("1")) {

                            list_product.setVisibility(View.VISIBLE);

                            list_product.setAdapter(new CustomResultAdapterDoctor1());
                        }

                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class get_Product1 extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("category_id", Catid));

                parameters.add(new BasicNameValuePair("page", "" + page_id));

                parameters.add(new BasicNameValuePair("user_id", user_id_main));

                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_GetProductList", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        page_limit = jObj.getString("total_page_count");

                        JSONArray jsonwish = jObj.optJSONArray("wish_list");

                        WishList = new ArrayList<String>();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }


                       /* if (page_id > Integer.parseInt(page_limit)) {

                        } else {*/

                        JSONObject jobj = new JSONObject(result_1);
                          /*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*/


                        JSONArray jsonArray = jObj.optJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jproduct = jsonObject.getJSONObject("Product");


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

                            JSONObject jlabel = jsonObject.getJSONObject("Label");
                            bean.setPro_label(jlabel.getString("name"));

                            bean_product1.add(bean);


                                /*JSONArray jProductFeature = jsonObject.getJSONArray("ProductFeature");

                                for (int f = 0; f < jProductFeature.length(); f++) {
                                    JSONObject jproProductFeature = jProductFeature.getJSONObject(f);
                                    JSONObject jsubobject1 = jproProductFeature.getJSONObject("Attribute");

                                    Bean_ProductFeature beanf = new Bean_ProductFeature();


                                    beanf.setPro_feature_id(jsubobject1.getString("id"));
                                    beanf.setPro_id(jproProductFeature.getString("product_id"));
                                    beanf.setPro_feature_name(jsubobject1.getString("name"));
                                    beanf.setPro_feature_value(jproProductFeature.getString("attribute_value"));


                                    //beanf.setPro_name(jsonobject.getString("product_name"));


                                    bean_productFeatures.add(beanf);

                                }*/

                            JSONArray jProductOption = jsonObject.getJSONArray("ProductOption");


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

                                //Log.e("ABABABABBABBA",""+jProductOptiono.getString("product_id"));
                                beanoption.setPro_id(jProductOptiono.getString("product_id"));
                                beanoption.setPro_Option_mrp(jProductOptiono.getString("mrp"));
                                beanoption.setPro_Option_selling_price(jProductOptiono.getString("selling_price"));

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

                               /* LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                                arrOptionType.clear();
                                //Creating Arraylist without duplicate values
                                arrOptionType = new ArrayList<String>(listToSet);

                                //Log.e("AAAA", "" + arrOptionType.size());

                                arrOptionSize.add(""+arrOptionType);*/
/*  JSONArray jprostdspec = jsonObject.getJSONArray("ProductStandardSpecification");

                                for (int s = 0; s < jprostdspec.length(); s++) {
                                    JSONObject jpss = jprostdspec.getJSONObject(s);
                                    JSONObject jpssAttribute = jpss.getJSONObject("Attribute");

                                    Bean_ProductStdSpec beanstudspec = new Bean_ProductStdSpec();

//attribute
                                    beanstudspec.setPro_Stdspec_id(jpssAttribute.getString("id"));
                                    beanstudspec.setPro_Stdspec_name(jpssAttribute.getString("name"));

                                    beanstudspec.setPro_id(jpss.getString("product_id"));
                                    beanstudspec.setPro_Stdspec_value(jpss.getString("attribute_value"));


                                    bean_productStdSpecs.add(beanstudspec);

                                }
                                JSONArray jprotechs = jsonObject.getJSONArray("ProductTechnicalSpecification");

                                for (int s = 0; s < jprotechs.length(); s++) {
                                    JSONObject jpts = jprotechs.getJSONObject(s);
                                    JSONObject jpssAttribute = jpts.getJSONObject("Attribute");


                                    Bean_ProductTechSpec beantechspec = new Bean_ProductTechSpec();

                                    beantechspec.setPro_id(jpts.getString("product_id"));
                                    beantechspec.setPro_Techspec_value(jpts.getString("attribute_value"));

                                    beantechspec.setPro_Techspec_id(jpssAttribute.getString("id"));
                                    beantechspec.setPro_Techspec_name(jpssAttribute.getString("name"));


                                    bean_productTechSpecs.add(beantechspec);

                                }*/


                            JSONArray jpProductImage = jsonObject.getJSONArray("ProductImage");

                            for (int s = 0; s < jpProductImage.length(); s++) {
                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                beanproductimg.setPro_id(jpimg.getString("product_id"));
                                beanproductimg.setPro_Images(jpimg.getString("image"));

                                bean_productImages.add(beanproductimg);

                            }

                        }
                        //Log.e("Size:Product ::", "" + bean_product1.size());
                        //Log.e("Size:TechSpecification", "" + bean_productTechSpecs.size());
                        //Log.e("Size:ProductOptions ::", "" + bean_productOprtions.size());
                        //Log.e("Size:ProductFeatures ::", "" + bean_productFeatures.size());
                        //Log.e("Size:ProductIamge ::", "" + bean_productImages.size());


                        list_product.setVisibility(View.VISIBLE);

                        list_product.setAdapter(new CustomResultAdapterDoctor());


                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class get_marquee extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("id", "1"));

                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "FlashMessage/App_GetFlashMessage", ServiceHandler.POST, parameters);
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

                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        JSONArray jsonArray = jObj.optJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jmarquee = jsonObject.getJSONObject("FlashMessage");

                            String s_text = jmarquee.getString("msg");
                            //Log.e("S_text", "" + s_text);
                            marqee.setText(Html.fromHtml(s_text));

                        }
                        loadingView.dismiss();
                        bean_product1.clear();
                        bean_productTechSpecs.clear();
                        bean_productOprtions.clear();
                        bean_productFeatures.clear();
                        bean_productStdSpecs.clear();

                        app = new AppPrefs(Product_List.this);
                        String page = app.getPage_Data();
                        String sort = app.getProduct_Sort();
                        if (page.equalsIgnoreCase("1")) {
                            if (sort.equalsIgnoreCase("1")) {
                                new get_Product_Popularity().execute();
                            } else if (sort.equalsIgnoreCase("2")) {
                                new get_Product_LowtoHigh().execute();

                            } else if (sort.equalsIgnoreCase("3")) {
                                new get_Product_HightoLow().execute();

                            } else if (sort.equalsIgnoreCase("4")) {
                                new get_Product_Newest().execute();

                            } else {
                                current_record = 0;
                                new get_Product().execute();

                            }


                        } else if (page.equalsIgnoreCase("2")) {
                            current_record = 0;
                            new get_ProductFilter().execute();
                        }

                    }
                }
            } catch (Exception ja) {
                ja.printStackTrace();


            }

        }

    }

    public class get_ProductFilter extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                app = new AppPrefs(Product_List.this);

                String Catgory = app.getFilter_Cat();
                String SubCatgory = app.getFilter_SubCat();
                String Option = app.getFilter_Option();
                String Sort = app.getFilter_Sort();


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                //   parameters.add(new BasicNameValuePair("category_id", "2"));

                parameters.add(new BasicNameValuePair("page", "" + page_id));

                parameters.add(new BasicNameValuePair("user_id", user_id_main));

                parameters.add(new BasicNameValuePair("role_id", role_id));

                parameters.add(new BasicNameValuePair("category", Catgory));
                parameters.add(new BasicNameValuePair("sub_category", SubCatgory));
                parameters.add(new BasicNameValuePair("option", Option));
                parameters.add(new BasicNameValuePair("sorting", Sort));

                //Log.e("", "" + parameters);
                //System.out.println(parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Filter_ProductList", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        page_limit = jObj.getString("total_page_count");

                        JSONArray jsonwish = jObj.optJSONArray("wish_list");

                        WishList = new ArrayList<String>();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }

                        //Log.e("Wish List Size", "" + WishList.size());

                       /* if (page_id > Integer.parseInt(page_limit)) {

                        } else {*/

                        JSONObject jobj = new JSONObject(result_1);
                          /*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*/


                        JSONArray jsonArray = jObj.optJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jproduct = jsonObject.getJSONObject("Product");


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
                            JSONArray jschme = jsonObject.getJSONArray("Scheme");
                            //Log.e("A1111111",""+jschme.length());

                            String sa = "";

                            for (int s1 = 0; s1 < jschme.length(); s1++) {
                                JSONObject jProductScheme = jschme.getJSONObject(s1);

                                ArrayList<String> a = new ArrayList<String>();
                                a.add(jProductScheme.getString("scheme_name"));
                                //Log.e("A2222222",""+jProductScheme.getString("scheme_name"));
                                sa += jProductScheme.getString("scheme_name").toString() + "\n";
                                bean.setSchemea(a);
                            }


                            //Log.e("A3333333",""+s);
                            bean.setScheme(sa);
                            JSONObject jlabel = jsonObject.getJSONObject("Label");
                            bean.setPro_label(jlabel.getString("name"));
                            bean_product1.add(bean);
                            Bean_Desc bean1 = new Bean_Desc();
                            bean1.setPro_id(jproduct.getString("id"));
                            //   Toast.makeText(Product_List.this, ""+bean1.getPro_id()+"-"+jproduct.getString("id"), Toast.LENGTH_SHORT).show();
                            bean1.setDescription(jproduct.getString("description"));
                            bean1.setSpecification(jproduct.getString("specification"));
                            bean1.setTechnical(jproduct.getString("technical"));
                            bean1.setCatalogs(jproduct.getString("catalogs"));
                            bean1.setGuarantee(jproduct.getString("guarantee"));
                            bean1.setVideos(jproduct.getString("videos"));
                            bean1.setGeneral(jproduct.getString("general"));
                            bean_desc1.add(bean1);



                                  /*  JSONArray jProductFeature = jsonObject.getJSONArray("ProductFeature");

                                    for (int f = 0; f < jProductFeature.length(); f++) {
                                        JSONObject jproProductFeature = jProductFeature.getJSONObject(f);
                                        JSONObject jsubobject1 = jproProductFeature.getJSONObject("Attribute");

                                        Bean_ProductFeature beanf = new Bean_ProductFeature();


                                        beanf.setPro_feature_id(jsubobject1.getString("id"));
                                        beanf.setPro_id(jproProductFeature.getString("product_id"));
                                        beanf.setPro_feature_name(jsubobject1.getString("name"));
                                        beanf.setPro_feature_value(jproProductFeature.getString("attribute_value"));


                                        //beanf.setPro_name(jsonobject.getString("product_name"));


                                        bean_productFeatures.add(beanf);

                                    }*/

                            JSONArray jProductOption = jsonObject.getJSONArray("ProductOption");


                            for (int s = 0; s < jProductOption.length(); s++) {
                                JSONObject jProductOptiono = jProductOption.getJSONObject(s);
                                JSONObject jPOOption = jProductOptiono.getJSONObject("Option");
                                JSONObject jPOOptionValue = jProductOptiono.getJSONObject("OptionValue");
                                JSONArray jPOProductOptionImage = jProductOptiono.getJSONArray("ProductOptionImage");

                                JSONArray schemeArray = jProductOptiono.getJSONArray("Scheme");
                                for (int m = 0; m < schemeArray.length(); m++) {
                                    JSONObject schemeObj = schemeArray.getJSONObject(m);

                                    Bean_schemeData beans = new Bean_schemeData();
                                    beans.setSchme_id(schemeObj.getString("id"));
                                    beans.setSchme_name(schemeObj.getString("scheme_name"));
                                    beans.setSchme_qty(schemeObj.getString("buy_prod_qty"));
                                    beans.setCategory_id(schemeObj.getString("category_id"));
                                    beans.setSchme_buy_prod_id(schemeObj.getString("buy_prod_id"));
                                    beans.setSchme_prod_id(jProductOptiono.getString("product_id"));
                                    bean_Schme_data.add(beans);
                                }
                                Bean_ProductOprtion beanoption = new Bean_ProductOprtion();
                                // arrOptionType=new ArrayList<String>();


                                //Option
                                beanoption.setPro_Option_id(jPOOption.getString("id"));
                                beanoption.setPro_Option_name(jPOOption.getString("name"));

                                //OptionValue
                                beanoption.setPro_Option_value_name(jPOOptionValue.getString("name"));
                                beanoption.setPro_Option_value_id(jPOOptionValue.getString("id"));

                                //ProductOptionImage

                                beanoption.setPro_id(jProductOptiono.getString("parent_product_id"));
                                //Log.e("ABABABABBABBA", "" + jProductOptiono.getString("parent_product_id"));
                                beanoption.setOption_pro_id(jProductOptiono.getString("product_id"));
                                //Log.e("ABABABABBABBA", "" + jProductOptiono.getString("product_id"));
                                beanoption.setPro_Option_mrp(jProductOptiono.getString("mrp"));
                                beanoption.setPro_Option_selling_price(jProductOptiono.getString("selling_price"));
                                beanoption.setPro_Option_procode(jProductOptiono.getString("product_code"));
                                //Log.e("ABABABABBABBA", "" + jProductOptiono.getString("product_code"));

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

                               /* LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                                arrOptionType.clear();
                                //Creating Arraylist without duplicate values
                                arrOptionType = new ArrayList<String>(listToSet);

                                //Log.e("AAAA", "" + arrOptionType.size());

                                arrOptionSize.add(""+arrOptionType);*/

                                 /*   JSONArray jprostdspec = jsonObject.getJSONArray("ProductStandardSpecification");

                                    for (int s = 0; s < jprostdspec.length(); s++) {
                                        JSONObject jpss = jprostdspec.getJSONObject(s);
                                        JSONObject jpssAttribute = jpss.getJSONObject("Attribute");

                                        Bean_ProductStdSpec beanstudspec = new Bean_ProductStdSpec();

//attribute
                                        beanstudspec.setPro_Stdspec_id(jpssAttribute.getString("id"));
                                        beanstudspec.setPro_Stdspec_name(jpssAttribute.getString("name"));

                                        beanstudspec.setPro_id(jpss.getString("product_id"));
                                        beanstudspec.setPro_Stdspec_value(jpss.getString("attribute_value"));


                                        bean_productStdSpecs.add(beanstudspec);

                                    }*/


                            JSONArray jpProductImage = jsonObject.getJSONArray("ProductImage");

                            for (int s = 0; s < jpProductImage.length(); s++) {
                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                beanproductimg.setPro_id(jpimg.getString("product_id"));
                                beanproductimg.setPro_Images(jpimg.getString("image"));

                                bean_productImages.add(beanproductimg);

                            }

                        }
                        //Log.e("Size:Product ::", "" + bean_product1.size());

                        //Log.e("Size:Desc ::", "" + bean_desc1.size());

                        //Log.e("Size:ProductOptions ::", "" + bean_productOprtions.size());

                        //Log.e("Size:ProductIamge ::", "" + bean_productImages.size());

                        SetRefershDataProduct();
                        if (bean_product_db.size() == 0) {
                            //Log.e("Datbase....11", "" + bean_product_db.size());
                            for (int a = 0; a < bean_product1.size(); a++) {
                                //Log.e("List....11", "" + bean_product1.size());
                                db = new DatabaseHandler(Product_List.this);
                                db.Add_Product(new Bean_Product(bean_product1.get(a).getPro_id(),
                                        bean_product1.get(a).getPro_code(),
                                        bean_product1.get(a).getPro_name(),
                                        bean_product1.get(a).getPro_label(),
                                        bean_product1.get(a).getPro_mrp(),
                                        bean_product1.get(a).getPro_sellingprice(),
                                        bean_product1.get(a).getPro_shortdesc(),
                                        bean_product1.get(a).getPro_moreinfo(),
                                        bean_product1.get(a).getPro_cat_id(),
                                        bean_product1.get(a).getPro_qty(),
                                        bean_product1.get(a).getPro_image()));
                                db.close();

                            }
                        } else {

                            //Log.e("Datbase....22", "" + bean_product_db.size());
                            for (int a = 0; a < bean_product1.size(); a++) {
                                //Log.e("List....22", "" + bean_product1.size());
                                int k = 0;
                                for (int b = 0; b < bean_product_db.size(); b++) {
                                    if (k != 1) {
                                        if (bean_product_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_product1.get(a).getPro_id().toString())) {

                                        } else {
                                            db = new DatabaseHandler(Product_List.this);
                                            db.Add_Product(new Bean_Product(bean_product1.get(a).getPro_id(),
                                                    bean_product1.get(a).getPro_code(),
                                                    bean_product1.get(a).getPro_name(),
                                                    bean_product1.get(a).getPro_label(),
                                                    bean_product1.get(a).getPro_mrp(),
                                                    bean_product1.get(a).getPro_sellingprice(),
                                                    bean_product1.get(a).getPro_shortdesc(),
                                                    bean_product1.get(a).getPro_moreinfo(),
                                                    bean_product1.get(a).getPro_cat_id(),
                                                    bean_product1.get(a).getPro_qty(),
                                                    bean_product1.get(a).getPro_image()));
                                            db.close();
                                            k = 1;
                                        }
                                    }
                                }


                            }

                        }
                        SetRefershDataProduct();
                        //Log.e("Product Size", "" + bean_product_db.size());
                        setRefershDataOption();
                        if (bean_productOprtions_db.size() == 0) {

                            for (int a = 0; a < bean_productOprtions.size(); a++) {
                                //Log.e("123", "" + bean_productOprtions.size());
                                db = new DatabaseHandler(Product_List.this);
                                db.Add_Product_Option(new Bean_ProductOprtion(bean_productOprtions.get(a).getPro_id(),
                                        bean_productOprtions.get(a).getPro_cat_id(),
                                        bean_productOprtions.get(a).getPro_Option_id(),
                                        bean_productOprtions.get(a).getPro_Option_name(),
                                        bean_productOprtions.get(a).getPro_Option_value_id(),
                                        bean_productOprtions.get(a).getPro_Option_value_name(),
                                        bean_productOprtions.get(a).getPro_Option_mrp(),
                                        bean_productOprtions.get(a).getPro_Option_selling_price(),
                                        bean_productOprtions.get(a).getPro_Option_proimage(),
                                        bean_productOprtions.get(a).getPro_Option_procode()

                                ));
                                db.close();

                            }
                        } else {

                            //Log.e("Datbase....22", "" + bean_productOprtions.size());
                            for (int a = 0; a < bean_productOprtions.size(); a++) {
                                //Log.e("List....22", "" + bean_productOprtions.size());
                                int k = 0;
                                for (int b = 0; b < bean_productOprtions_db.size(); b++) {
                                    if (k != 1) {
                                        if (bean_productOprtions_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_productOprtions.get(a).getPro_id().toString())) {

                                        } else {
                                            db = new DatabaseHandler(Product_List.this);
                                            db.Add_Product_Option(new Bean_ProductOprtion(bean_productOprtions.get(a).getPro_id(),
                                                    bean_productOprtions.get(a).getPro_cat_id(),
                                                    bean_productOprtions.get(a).getPro_Option_id(),
                                                    bean_productOprtions.get(a).getPro_Option_name(),
                                                    bean_productOprtions.get(a).getPro_Option_value_id(),
                                                    bean_productOprtions.get(a).getPro_Option_value_name(),
                                                    bean_productOprtions.get(a).getPro_Option_mrp(),
                                                    bean_productOprtions.get(a).getPro_Option_selling_price(),
                                                    bean_productOprtions.get(a).getPro_Option_proimage(),
                                                    bean_productOprtions.get(a).getPro_Option_procode()

                                            ));
                                            db.close();
                                            k = 1;
                                        }
                                    }
                                }


                            }

                        }
                        setRefershDataOption();
                        //Log.e("Option Size", "" + bean_productOprtions_db.size());

                        setRefershDataImage();
                        if (bean_productImages_db.size() == 0) {

                            for (int a = 0; a < bean_productImages.size(); a++) {
                                //Log.e("123", "" + bean_productImages.size());
                                db = new DatabaseHandler(Product_List.this);
                                db.Add_ProductImage(new Bean_ProductImage(bean_productImages.get(a).getPro_id(),
                                        bean_productImages.get(a).getPro_cat_id(),
                                        bean_productImages.get(a).getPro_Images())
                                );
                                db.close();

                            }
                        } else {

                            //Log.e("Datbase....22", "" + bean_productImages.size());
                            for (int a = 0; a < bean_productImages.size(); a++) {
                                //Log.e("List....22", "" + bean_productImages.size());
                                int k = 0;
                                for (int b = 0; b < bean_productImages_db.size(); b++) {
                                    if (k != 1) {
                                        if (bean_productImages_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_productImages.get(a).getPro_id().toString())) {

                                        } else {
                                            db = new DatabaseHandler(Product_List.this);
                                            db.Add_ProductImage(new Bean_ProductImage(bean_productImages.get(a).getPro_id(),
                                                    bean_productImages.get(a).getPro_cat_id(),
                                                    bean_productImages.get(a).getPro_Images())
                                            );
                                            db.close();
                                            k = 1;
                                        }
                                    }
                                }


                            }

                        }
                        setRefershDataImage();
                        //Log.e("Image Size", "" + bean_productImages_db.size());

                        SetRefershDataProductDesc();


                        if (bean_desc_db.size() == 0) {

                            for (int a = 0; a < bean_desc1.size(); a++) {
                                //Log.e("123", "" + bean_desc1.size());
                                db = new DatabaseHandler(Product_List.this);
                                db.Add_Product_desc(new Bean_Desc(bean_desc1.get(a).getPro_id(),
                                        bean_desc1.get(a).getDescription(),
                                        bean_desc1.get(a).getSpecification(),
                                        bean_desc1.get(a).getTechnical(),
                                        bean_desc1.get(a).getCatalogs(),
                                        bean_desc1.get(a).getGuarantee(),
                                        bean_desc1.get(a).getVideos(),
                                        bean_desc1.get(a).getGeneral())

                                );
                                db.close();

                            }
                        } else {

                            //Log.e("Datbase....22", "" + bean_desc1.size());
                            for (int a = 0; a < bean_desc1.size(); a++) {
                                //Log.e("List....22", "" + bean_desc1.size());
                                int k = 0;
                                for (int b = 0; b < bean_desc_db.size(); b++) {
                                    if (k != 1) {
                                        if (bean_desc_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_desc1.get(a).getPro_id().toString())) {

                                        } else {
                                            db = new DatabaseHandler(Product_List.this);
                                            db.Add_Product_desc(new Bean_Desc(bean_desc1.get(a).getPro_id(),
                                                    bean_desc1.get(a).getDescription(),
                                                    bean_desc1.get(a).getSpecification(),
                                                    bean_desc1.get(a).getTechnical(),
                                                    bean_desc1.get(a).getCatalogs(),
                                                    bean_desc1.get(a).getGuarantee(),
                                                    bean_desc1.get(a).getVideos(),
                                                    bean_desc1.get(a).getGeneral())

                                            );
                                            db.close();
                                            k = 1;
                                        }
                                    }
                                }


                            }

                        }
                        SetRefershDataProductDesc();
                        //Log.e("bean_desc_db Size", "" + bean_desc_db.size());


                        int firstVisPos = list_product.getFirstVisiblePosition();
                        list_product.setSelection(firstVisPos);
                        if (flagscroll.equalsIgnoreCase("2")) {

                            list_product.setVisibility(View.VISIBLE);

                            list_product.setAdapter(new CustomResultAdapterDoctor());
                        } else if (flagscroll.equalsIgnoreCase("1")) {

                            list_product.setVisibility(View.VISIBLE);

                            list_product.setAdapter(new CustomResultAdapterDoctor1());
                        }


                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class set_wish_list extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", user_id_main));
                parameters.add(new BasicNameValuePair("role_id", role_id));
                parameters.add(new BasicNameValuePair("product_id", pro_id));

                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/App_WishList", ServiceHandler.POST, parameters);
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

                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();

                      /*  page_id =1;
                        page_limit = "";

                        bean_product1.clear();
                        bean_productTechSpecs.clear();
                        bean_productOprtions.clear();
                        bean_productFeatures.clear();
                        bean_productStdSpecs.clear();

                        new get_Product().execute();*/
                        db = new DatabaseHandler(getApplicationContext());
                        db.Delete_ALL_table();
                        db.close();
                        Intent i = new Intent(Product_List.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        /*JSONArray jsonArray = jObj.optJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jmarquee = jsonObject.getJSONObject("FlashMessage");

                            String s_text = jmarquee.getString("message");
                            //Log.e("S_text", "" + s_text);
                            marqee.setText(Html.fromHtml(s_text));
                        }

                        loadingView.dismiss();
                        bean_product1.clear();
                        bean_productTechSpecs.clear();
                        bean_productOprtions.clear();
                        bean_productFeatures.clear();
                        bean_productStdSpecs.clear();
                        new get_Product().execute();*/

                    }
                }
            } catch (Exception ja) {
                ja.printStackTrace();


            }

        }

    }

    public class get_Product_Newest extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("category_id", Catid));

                parameters.add(new BasicNameValuePair("page", "" + page_id));

                parameters.add(new BasicNameValuePair("user_id", user_id_main));

                parameters.add(new BasicNameValuePair("new_arrival", ""));

                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_GetProductList", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        page_limit = jObj.getString("total_page_count");

                        JSONArray jsonwish = jObj.optJSONArray("wish_list");

                        WishList = new ArrayList<String>();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }


                       /* if (page_id > Integer.parseInt(page_limit)) {

                        } else {*/

                        JSONObject jobj = new JSONObject(result_1);
                          /*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*/


                        JSONArray jsonArray = jObj.optJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jproduct = jsonObject.getJSONObject("Product");


                            Bean_Product bean = new Bean_Product();


                            bean.setPro_id(jproduct.getString("id"));


                            bean.setPro_cat_id(jproduct.getString("category_id"));
                            bean.setPro_code(jproduct.getString("product_code"));
                            bean.setPro_name(jproduct.getString("product_name"));
                            // bean.setPro_label(jproduct.getString("label_id"));
                            bean.setPro_qty(jproduct.getString("qty"));
                            bean.setPro_mrp(jproduct.getString("mrp"));
                            bean.setPro_sellingprice(jproduct.getString("selling_price"));
                            //  bean.setPro_shortdesc(jproduct.getString("short_description"));
                            bean.setPro_image(jproduct.getString("image"));
                            //   bean.setPro_moreinfo(jproduct.getString("more_info"));

                            JSONObject jlabel = jsonObject.getJSONObject("Label");
                            bean.setPro_label(jlabel.getString("name"));

                            bean_product1.add(bean);


                               /* JSONArray jProductFeature = jsonObject.getJSONArray("ProductFeature");

                                for (int f = 0; f < jProductFeature.length(); f++) {
                                    JSONObject jproProductFeature = jProductFeature.getJSONObject(f);
                                    JSONObject jsubobject1 = jproProductFeature.getJSONObject("Attribute");

                                    Bean_ProductFeature beanf = new Bean_ProductFeature();


                                    beanf.setPro_feature_id(jsubobject1.getString("id"));
                                    beanf.setPro_id(jproProductFeature.getString("product_id"));
                                    beanf.setPro_feature_name(jsubobject1.getString("name"));
                                    beanf.setPro_feature_value(jproProductFeature.getString("attribute_value"));


                                    //beanf.setPro_name(jsonobject.getString("product_name"));


                                    bean_productFeatures.add(beanf);

                                }*/

                            JSONArray jProductOption = jsonObject.getJSONArray("ProductOption");


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

                                //Log.e("ABABABABBABBA",""+jProductOptiono.getString("product_id"));
                                beanoption.setPro_id(jProductOptiono.getString("product_id"));
                                beanoption.setPro_Option_mrp(jProductOptiono.getString("mrp"));
                                beanoption.setPro_Option_selling_price(jProductOptiono.getString("selling_price"));

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

                               /* LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                                arrOptionType.clear();
                                //Creating Arraylist without duplicate values
                                arrOptionType = new ArrayList<String>(listToSet);

                                //Log.e("AAAA", "" + arrOptionType.size());

                                arrOptionSize.add(""+arrOptionType);*/

                               /* JSONArray jprostdspec = jsonObject.getJSONArray("ProductStandardSpecification");

                                for (int s = 0; s < jprostdspec.length(); s++) {
                                    JSONObject jpss = jprostdspec.getJSONObject(s);
                                    JSONObject jpssAttribute = jpss.getJSONObject("Attribute");

                                    Bean_ProductStdSpec beanstudspec = new Bean_ProductStdSpec();

//attribute
                                    beanstudspec.setPro_Stdspec_id(jpssAttribute.getString("id"));
                                    beanstudspec.setPro_Stdspec_name(jpssAttribute.getString("name"));

                                    beanstudspec.setPro_id(jpss.getString("product_id"));
                                    beanstudspec.setPro_Stdspec_value(jpss.getString("attribute_value"));


                                    bean_productStdSpecs.add(beanstudspec);

                                }
                                JSONArray jprotechs = jsonObject.getJSONArray("ProductTechnicalSpecification");

                                for (int s = 0; s < jprotechs.length(); s++) {
                                    JSONObject jpts = jprotechs.getJSONObject(s);
                                    JSONObject jpssAttribute = jpts.getJSONObject("Attribute");


                                    Bean_ProductTechSpec beantechspec = new Bean_ProductTechSpec();

                                    beantechspec.setPro_id(jpts.getString("product_id"));
                                    beantechspec.setPro_Techspec_value(jpts.getString("attribute_value"));

                                    beantechspec.setPro_Techspec_id(jpssAttribute.getString("id"));
                                    beantechspec.setPro_Techspec_name(jpssAttribute.getString("name"));


                                    bean_productTechSpecs.add(beantechspec);

                                }*/

                            JSONArray jpProductImage = jsonObject.getJSONArray("ProductImage");

                            for (int s = 0; s < jpProductImage.length(); s++) {
                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                beanproductimg.setPro_id(jpimg.getString("product_id"));
                                beanproductimg.setPro_Images(jpimg.getString("image"));

                                bean_productImages.add(beanproductimg);

                            }

                        }


                        list_product.setVisibility(View.VISIBLE);

                        list_product.setAdapter(new CustomResultAdapterDoctor());


                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class get_Product_Popularity extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("category_id", Catid));

                parameters.add(new BasicNameValuePair("page", "" + page_id));

                parameters.add(new BasicNameValuePair("user_id", user_id_main));
                //parameters.add(new BasicNameValuePair("user_id", "1"));

                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_GetProductList", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        page_limit = jObj.getString("total_page_count");

                        JSONArray jsonwish = jObj.optJSONArray("wish_list");

                        WishList = new ArrayList<String>();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }


                       /* if (page_id > Integer.parseInt(page_limit)) {

                        } else {*/

                        JSONObject jobj = new JSONObject(result_1);
                          /*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*/


                        JSONArray jsonArray = jObj.optJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jproduct = jsonObject.getJSONObject("Product");


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

                            JSONObject jlabel = jsonObject.getJSONObject("Label");
                            bean.setPro_label(jlabel.getString("name"));

                            bean_product1.add(bean);


                             /*   JSONArray jProductFeature = jsonObject.getJSONArray("ProductFeature");

                                for (int f = 0; f < jProductFeature.length(); f++) {
                                    JSONObject jproProductFeature = jProductFeature.getJSONObject(f);
                                    JSONObject jsubobject1 = jproProductFeature.getJSONObject("Attribute");

                                    Bean_ProductFeature beanf = new Bean_ProductFeature();


                                    beanf.setPro_feature_id(jsubobject1.getString("id"));
                                    beanf.setPro_id(jproProductFeature.getString("product_id"));
                                    beanf.setPro_feature_name(jsubobject1.getString("name"));
                                    beanf.setPro_feature_value(jproProductFeature.getString("attribute_value"));


                                    //beanf.setPro_name(jsonobject.getString("product_name"));


                                    bean_productFeatures.add(beanf);

                                }*/

                            JSONArray jProductOption = jsonObject.getJSONArray("ProductOption");


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

                                //Log.e("ABABABABBABBA",""+jProductOptiono.getString("product_id"));
                                beanoption.setPro_id(jProductOptiono.getString("product_id"));
                                beanoption.setPro_Option_mrp(jProductOptiono.getString("mrp"));
                                beanoption.setPro_Option_selling_price(jProductOptiono.getString("selling_price"));

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

                               /* LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                                arrOptionType.clear();
                                //Creating Arraylist without duplicate values
                                arrOptionType = new ArrayList<String>(listToSet);

                                //Log.e("AAAA", "" + arrOptionType.size());

                                arrOptionSize.add(""+arrOptionType);*/

                              /*  JSONArray jprostdspec = jsonObject.getJSONArray("ProductStandardSpecification");

                                for (int s = 0; s < jprostdspec.length(); s++) {
                                    JSONObject jpss = jprostdspec.getJSONObject(s);
                                    JSONObject jpssAttribute = jpss.getJSONObject("Attribute");

                                    Bean_ProductStdSpec beanstudspec = new Bean_ProductStdSpec();

//attribute
                                    beanstudspec.setPro_Stdspec_id(jpssAttribute.getString("id"));
                                    beanstudspec.setPro_Stdspec_name(jpssAttribute.getString("name"));

                                    beanstudspec.setPro_id(jpss.getString("product_id"));
                                    beanstudspec.setPro_Stdspec_value(jpss.getString("attribute_value"));


                                    bean_productStdSpecs.add(beanstudspec);

                                }
                                JSONArray jprotechs = jsonObject.getJSONArray("ProductTechnicalSpecification");

                                for (int s = 0; s < jprotechs.length(); s++) {
                                    JSONObject jpts = jprotechs.getJSONObject(s);
                                    JSONObject jpssAttribute = jpts.getJSONObject("Attribute");


                                    Bean_ProductTechSpec beantechspec = new Bean_ProductTechSpec();

                                    beantechspec.setPro_id(jpts.getString("product_id"));
                                    beantechspec.setPro_Techspec_value(jpts.getString("attribute_value"));

                                    beantechspec.setPro_Techspec_id(jpssAttribute.getString("id"));
                                    beantechspec.setPro_Techspec_name(jpssAttribute.getString("name"));


                                    bean_productTechSpecs.add(beantechspec);

                                }*/

                            JSONArray jpProductImage = jsonObject.getJSONArray("ProductImage");

                            for (int s = 0; s < jpProductImage.length(); s++) {
                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                beanproductimg.setPro_id(jpimg.getString("product_id"));
                                beanproductimg.setPro_Images(jpimg.getString("image"));

                                bean_productImages.add(beanproductimg);

                            }

                        }
                        //Log.e("Size:Product ::", "" + bean_product1.size());
                        //Log.e("Size:TechSpecification", "" + bean_productTechSpecs.size());
                        //Log.e("Size:ProductOptions ::", "" + bean_productOprtions.size());
                        //Log.e("Size:ProductFeatures ::", "" + bean_productFeatures.size());
                        //Log.e("Size:ProductIamge ::", "" + bean_productImages.size());


                        list_product.setVisibility(View.VISIBLE);

                        list_product.setAdapter(new CustomResultAdapterDoctor());


                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class get_Product_LowtoHigh extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("category_id", Catid));

                parameters.add(new BasicNameValuePair("page", "" + page_id));

                parameters.add(new BasicNameValuePair("user_id", user_id_main));

                parameters.add(new BasicNameValuePair("price_ascending", ""));

                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_GetProductList", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        page_limit = jObj.getString("total_page_count");

                        JSONArray jsonwish = jObj.optJSONArray("wish_list");

                        WishList = new ArrayList<String>();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }


                       /* if (page_id > Integer.parseInt(page_limit)) {

                        } else {*/

                        JSONObject jobj = new JSONObject(result_1);
                          /*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*/


                        JSONArray jsonArray = jObj.optJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jproduct = jsonObject.getJSONObject("Product");


                            Bean_Product bean = new Bean_Product();


                            bean.setPro_id(jproduct.getString("id"));


                            bean.setPro_cat_id(jproduct.getString("category_id"));
                            bean.setPro_code(jproduct.getString("product_code"));
                            bean.setPro_name(jproduct.getString("product_name"));
                            // bean.setPro_label(jproduct.getString("label_id"));
                            bean.setPro_qty(jproduct.getString("qty"));
                            bean.setPro_mrp(jproduct.getString("mrp"));
                            bean.setPro_sellingprice(jproduct.getString("selling_price"));
                            //  bean.setPro_shortdesc(jproduct.getString("short_description"));
                            bean.setPro_image(jproduct.getString("image"));
                            //   bean.setPro_moreinfo(jproduct.getString("more_info"));

                            JSONObject jlabel = jsonObject.getJSONObject("Label");
                            bean.setPro_label(jlabel.getString("name"));

                            bean_product1.add(bean);


                             /*   JSONArray jProductFeature = jsonObject.getJSONArray("ProductFeature");

                                for (int f = 0; f < jProductFeature.length(); f++) {
                                    JSONObject jproProductFeature = jProductFeature.getJSONObject(f);
                                    JSONObject jsubobject1 = jproProductFeature.getJSONObject("Attribute");

                                    Bean_ProductFeature beanf = new Bean_ProductFeature();


                                    beanf.setPro_feature_id(jsubobject1.getString("id"));
                                    beanf.setPro_id(jproProductFeature.getString("product_id"));
                                    beanf.setPro_feature_name(jsubobject1.getString("name"));
                                    beanf.setPro_feature_value(jproProductFeature.getString("attribute_value"));


                                    //beanf.setPro_name(jsonobject.getString("product_name"));


                                    bean_productFeatures.add(beanf);

                                }*/

                            JSONArray jProductOption = jsonObject.getJSONArray("ProductOption");


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

                                //Log.e("ABABABABBABBA",""+jProductOptiono.getString("product_id"));
                                beanoption.setPro_id(jProductOptiono.getString("product_id"));
                                beanoption.setPro_Option_mrp(jProductOptiono.getString("mrp"));
                                beanoption.setPro_Option_selling_price(jProductOptiono.getString("selling_price"));

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

                               /* LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                                arrOptionType.clear();
                                //Creating Arraylist without duplicate values
                                arrOptionType = new ArrayList<String>(listToSet);

                                //Log.e("AAAA", "" + arrOptionType.size());

                                arrOptionSize.add(""+arrOptionType);*/

                              /*  JSONArray jprostdspec = jsonObject.getJSONArray("ProductStandardSpecification");

                                for (int s = 0; s < jprostdspec.length(); s++) {
                                    JSONObject jpss = jprostdspec.getJSONObject(s);
                                    JSONObject jpssAttribute = jpss.getJSONObject("Attribute");

                                    Bean_ProductStdSpec beanstudspec = new Bean_ProductStdSpec();

//attribute
                                    beanstudspec.setPro_Stdspec_id(jpssAttribute.getString("id"));
                                    beanstudspec.setPro_Stdspec_name(jpssAttribute.getString("name"));

                                    beanstudspec.setPro_id(jpss.getString("product_id"));
                                    beanstudspec.setPro_Stdspec_value(jpss.getString("attribute_value"));


                                    bean_productStdSpecs.add(beanstudspec);

                                }
                                JSONArray jprotechs = jsonObject.getJSONArray("ProductTechnicalSpecification");

                                for (int s = 0; s < jprotechs.length(); s++) {
                                    JSONObject jpts = jprotechs.getJSONObject(s);
                                    JSONObject jpssAttribute = jpts.getJSONObject("Attribute");


                                    Bean_ProductTechSpec beantechspec = new Bean_ProductTechSpec();

                                    beantechspec.setPro_id(jpts.getString("product_id"));
                                    beantechspec.setPro_Techspec_value(jpts.getString("attribute_value"));

                                    beantechspec.setPro_Techspec_id(jpssAttribute.getString("id"));
                                    beantechspec.setPro_Techspec_name(jpssAttribute.getString("name"));


                                    bean_productTechSpecs.add(beantechspec);

                                }*/

                            JSONArray jpProductImage = jsonObject.getJSONArray("ProductImage");

                            for (int s = 0; s < jpProductImage.length(); s++) {
                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                beanproductimg.setPro_id(jpimg.getString("product_id"));
                                beanproductimg.setPro_Images(jpimg.getString("image"));

                                bean_productImages.add(beanproductimg);

                            }

                        }
                        //Log.e("Size:Product ::", "" + bean_product1.size());
                        //Log.e("Size:TechSpecification", "" + bean_productTechSpecs.size());
                        //Log.e("Size:ProductOptions ::", "" + bean_productOprtions.size());
                        //Log.e("Size:ProductFeatures ::", "" + bean_productFeatures.size());
                        //Log.e("Size:ProductIamge ::", "" + bean_productImages.size());


                        list_product.setVisibility(View.VISIBLE);

                        list_product.setAdapter(new CustomResultAdapterDoctor());


                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class get_Product_HightoLow extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("category_id", Catid));

                parameters.add(new BasicNameValuePair("page", "" + page_id));

                parameters.add(new BasicNameValuePair("user_id", user_id_main));
                parameters.add(new BasicNameValuePair("price_decending", ""));

                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_GetProductList", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        page_limit = jObj.getString("total_page_count");

                        JSONArray jsonwish = jObj.optJSONArray("wish_list");

                        WishList = new ArrayList<String>();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }


                       /* if (page_id > Integer.parseInt(page_limit)) {

                        } else {*/

                        JSONObject jobj = new JSONObject(result_1);
                          /*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*/


                        JSONArray jsonArray = jObj.optJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject jproduct = jsonObject.getJSONObject("Product");


                            Bean_Product bean = new Bean_Product();


                            bean.setPro_id(jproduct.getString("id"));


                            bean.setPro_cat_id(jproduct.getString("category_id"));
                            bean.setPro_code(jproduct.getString("product_code"));
                            bean.setPro_name(jproduct.getString("product_name"));
                            // bean.setPro_label(jproduct.getString("label_id"));
                            bean.setPro_qty(jproduct.getString("qty"));
                            bean.setPro_mrp(jproduct.getString("mrp"));
                            bean.setPro_sellingprice(jproduct.getString("selling_price"));
                            //  bean.setPro_shortdesc(jproduct.getString("short_description"));
                            bean.setPro_image(jproduct.getString("image"));
                            //   bean.setPro_moreinfo(jproduct.getString("more_info"));

                            JSONObject jlabel = jsonObject.getJSONObject("Label");
                            bean.setPro_label(jlabel.getString("name"));

                            bean_product1.add(bean);


                             /*   JSONArray jProductFeature = jsonObject.getJSONArray("ProductFeature");

                                for (int f = 0; f < jProductFeature.length(); f++) {
                                    JSONObject jproProductFeature = jProductFeature.getJSONObject(f);
                                    JSONObject jsubobject1 = jproProductFeature.getJSONObject("Attribute");

                                    Bean_ProductFeature beanf = new Bean_ProductFeature();


                                    beanf.setPro_feature_id(jsubobject1.getString("id"));
                                    beanf.setPro_id(jproProductFeature.getString("product_id"));
                                    beanf.setPro_feature_name(jsubobject1.getString("name"));
                                    beanf.setPro_feature_value(jproProductFeature.getString("attribute_value"));


                                    //beanf.setPro_name(jsonobject.getString("product_name"));


                                    bean_productFeatures.add(beanf);

                                }*/

                            JSONArray jProductOption = jsonObject.getJSONArray("ProductOption");


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

                                //Log.e("ABABABABBABBA",""+jProductOptiono.getString("product_id"));
                                beanoption.setPro_id(jProductOptiono.getString("product_id"));
                                beanoption.setPro_Option_mrp(jProductOptiono.getString("mrp"));
                                beanoption.setPro_Option_selling_price(jProductOptiono.getString("selling_price"));

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

                               /* LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                                arrOptionType.clear();
                                //Creating Arraylist without duplicate values
                                arrOptionType = new ArrayList<String>(listToSet);

                                //Log.e("AAAA", "" + arrOptionType.size());

                                arrOptionSize.add(""+arrOptionType);*/

                             /*   JSONArray jprostdspec = jsonObject.getJSONArray("ProductStandardSpecification");

                                for (int s = 0; s < jprostdspec.length(); s++) {
                                    JSONObject jpss = jprostdspec.getJSONObject(s);
                                    JSONObject jpssAttribute = jpss.getJSONObject("Attribute");

                                    Bean_ProductStdSpec beanstudspec = new Bean_ProductStdSpec();

//attribute
                                    beanstudspec.setPro_Stdspec_id(jpssAttribute.getString("id"));
                                    beanstudspec.setPro_Stdspec_name(jpssAttribute.getString("name"));

                                    beanstudspec.setPro_id(jpss.getString("product_id"));
                                    beanstudspec.setPro_Stdspec_value(jpss.getString("attribute_value"));


                                    bean_productStdSpecs.add(beanstudspec);

                                }
                                JSONArray jprotechs = jsonObject.getJSONArray("ProductTechnicalSpecification");

                                for (int s = 0; s < jprotechs.length(); s++) {
                                    JSONObject jpts = jprotechs.getJSONObject(s);
                                    JSONObject jpssAttribute = jpts.getJSONObject("Attribute");


                                    Bean_ProductTechSpec beantechspec = new Bean_ProductTechSpec();

                                    beantechspec.setPro_id(jpts.getString("product_id"));
                                    beantechspec.setPro_Techspec_value(jpts.getString("attribute_value"));

                                    beantechspec.setPro_Techspec_id(jpssAttribute.getString("id"));
                                    beantechspec.setPro_Techspec_name(jpssAttribute.getString("name"));


                                    bean_productTechSpecs.add(beantechspec);

                                }*/

                            JSONArray jpProductImage = jsonObject.getJSONArray("ProductImage");

                            for (int s = 0; s < jpProductImage.length(); s++) {
                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                Bean_ProductImage beanproductimg = new Bean_ProductImage();

                                beanproductimg.setPro_id(jpimg.getString("product_id"));
                                beanproductimg.setPro_Images(jpimg.getString("image"));

                                bean_productImages.add(beanproductimg);

                            }

                        }
                        //Log.e("Size:Product ::", "" + bean_product1.size());
                        //Log.e("Size:TechSpecification", "" + bean_productTechSpecs.size());
                        //Log.e("Size:ProductOptions ::", "" + bean_productOprtions.size());
                        //Log.e("Size:ProductFeatures ::", "" + bean_productFeatures.size());
                        //Log.e("Size:ProductIamge ::", "" + bean_productImages.size());


                        list_product.setVisibility(View.VISIBLE);

                        list_product.setAdapter(new CustomResultAdapterDoctor());


                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class delete_wish_list extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                //parameters.add(new BasicNameValuePair("category_id", "2"));

                parameters.add(new BasicNameValuePair("product_id", pro_id));

                parameters.add(new BasicNameValuePair("user_id", user_id_main));

                parameters.add(new BasicNameValuePair("role_id", role_id));

                //Log.e("pro_id", "" + pro_id);
                //Log.e("user_id", "" + user_id_main);
                //Log.e("parameters", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/App_DeleteWishList", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        db = new DatabaseHandler(getApplicationContext());
                        db.Delete_ALL_table();
                        db.close();
                        Intent i = new Intent(Product_List.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
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
            Log.e("parameters code", pairList.toString());
        }

        @Override
        protected String doInBackground(Void... params) {
            jsonData = "";
            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "Scheme/App_Get_Scheme_Details", ServiceHandler.POST, pairList);
            isNotDone = false;
            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            isNotDone = false;
        }
    }

    public class GetProductDetailQty extends AsyncTask<Void, Void, String> {

        List<NameValuePair> params = new ArrayList<>();
        Custom_ProgressDialog loading;

        public GetProductDetailQty(List<NameValuePair> params) {
            this.params = params;
            loading = new Custom_ProgressDialog(Product_List.this, "");
            loading.setCancelable(false);
            loading.show();
        }

        @Override
        protected String doInBackground(Void... param) {
            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetItemQty", ServiceHandler.POST, params);
            isNotDone = false;
            System.out.println("array: " + jsonData);
            Globals.generateNoteOnSD(getApplicationContext(), "array: " + jsonData.toString());
            return jsonData;

        }

        @Override
        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            loading.dismiss();
            Globals.generateNoteOnSD(getApplicationContext(), "error1: " + result1.toString());
            isNotDone = false;
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
                        Product_List.this, "");
                buy_cart.setEnabled(true);
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

                Log.e("Product", "Added -> " + parameters.toString());

                //Log.e("CART DATA",""+jarray_cart);

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

            jarray_cart = new JSONArray();

            try {

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        /*Intent i = new Intent(Product_List.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);*/
                        loadingView.dismiss();
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }
            GetCartQtyCall();

        }
    }
   /* public String GetCartByQty(final List<NameValuePair> params){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty",ServiceHandler.POST,params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){

        }
        //Log.e("my json",json[0]);
        return json[0];
    }*/

    public class Edit_Product extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_List.this, "");
                buy_cart.setEnabled(true);
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

                Log.e("CART DATA", "" + jarray_cart);

                Log.e("Product", "Edited -> " + parameters.toString());

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

            jarray_cart = new JSONArray();

            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                        /*Intent i = new Intent(Product_List.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);*/
                        loadingView.dismiss();

                        //  Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                       /* Intent i = new Intent(Product_List.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);*/
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

            GetCartQtyCall();

        }
    }

    public class CustomResultAdapterschme extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bean_S_data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            // result_holder = null;


            //   if (convertView == null) {

            convertView = vi.inflate(R.layout.schme_row, null);

            //  }

            TextView txt_name = (TextView) convertView
                    .findViewById(R.id.txt_schme);

            txt_name.setText(bean_S_data.get(position).getSchme_name());


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

                    Globals.CustomToast(Product_List.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);
                    String userStatus = jObj.getString("user_status");
                    if (userStatus.equals("0")) {
                        C.userInActiveDialog(Product_List.this);
                    }
                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(Product_List.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(Product_List.this);
                        app.setCart_QTy("" + qu);


                    }

                }


            } catch (Exception j) {
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = app.getCart_QTy();

            Log.e("Current Qty", "-->" + qu1);

            if (qu1.equalsIgnoreCase("0") || qu1.isEmpty()) {
                txt.setVisibility(View.GONE);
                txt.setText("");
            } else {
                txt.setVisibility(View.VISIBLE);
                if (Integer.parseInt(qu1) > 999) {
                    txt.setText("999+");
                } else {
                    txt.setText(qu1);

                }
            }
        }
    }
}
