package com.agraeta.user.btl;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.adapters.ExpandableSchemeAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.GetCartItemQuantityResponse;
import com.agraeta.user.btl.model.GetSchemeDetailResponse;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chaitalee on 7/15/2016.
 */
public class BTLProduct_Detail extends AppCompatActivity {
    public static ArrayList<String> arrOptionTypeID = new ArrayList<String>();
    // public static ArrayList<String> arrOptionTypeID =new ArrayList<String>();
    public static ArrayList<String> moreinfo_arry = new ArrayList<String>();
    // public static ArrayList<String> arrOptionTypeName =new ArrayList<String>();
    public static ArrayList<String> arrOptionTypeName = new ArrayList<String>();
    public static ArrayList<String> WishList = new ArrayList<String>();
    private static ViewPager mPager, mViewPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    final File myDir = new File("/sdcard/BTL/Gallery");
    TextView txt_high, product_detail_marquee;
    String option_name = "";
    String option_id = "";
    TextView tv_pop_pname, tv_pop_code, tv_pop_packof, tv_pop_mrp, tv_pop_sellingprice, tv_total, tv_txt_mrp;
    LinearLayout layout_imagegrid, Feature_text_layout, option_text_layout, l_video_upper, l_video_lower, layout_youtube_forimg, l_view_spinner;
    int count = 0;
    ArrayList<ArrayList<Bean_Attribute>> array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();
    ArrayList<Integer> list_data = new ArrayList<Integer>();
    ArrayList<Bean_Attribute> array_att = new ArrayList<Bean_Attribute>();
    String CategoryID = "";
    ArrayList<Bean_Value_Selected_Detail> array_value = new ArrayList<Bean_Value_Selected_Detail>();
    LinearLayout.LayoutParams params;
    double amount1;
    TextView off_tag;
    ProgressDialog pDialog;
    Button btn_buyonline_product_detail;
    ArrayList<String> list_of_images = new ArrayList<String>();
    //ResultHolder result_holder;
    Grid_ImageAdpter imgadpter;
    LinearLayout l_spinner, l_spinner_text, l_linear, l_sort;
    ArrayList<Bean_ProductImage> bean_data = new ArrayList<Bean_ProductImage>();
    ArrayList<Bean_Product> bean_product1 = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductFeature> bean_productFeatures = new ArrayList<Bean_ProductFeature>();
    ArrayList<Bean_ProductTechSpec> bean_productTechSpecs = new ArrayList<Bean_ProductTechSpec>();
    ArrayList<Bean_ProductStdSpec> bean_productStdSpecs = new ArrayList<Bean_ProductStdSpec>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductCart> bean_productCart = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_ProductImage> bean_productImages = new ArrayList<Bean_ProductImage>();
    //ArrayList<Bean_ProductCatalog> bean_ProductCatalog = new ArrayList<Bean_ProductCatalog>();
    ArrayList<String> pro_img = new ArrayList<String>();
    String role;
    String cartJSON = "";
    boolean hasCartCallFinish = true;
    TextView txt;
    boolean isNotDone = true;
    String jsonData = "";
    // ArrayList<Product_Youtube> bean_productyoutube = new ArrayList<Product_Youtube>();
    DatabaseHandler db;
    String json = "";
    ImageLoader imageloader;
    String Imgpath, Imgproid, ImgProcode, ImgProPrice, ImgProName;
    Custom_ProgressDialog loadingView;
    ImageView minuss, plus;
    Button buy_cart, cancel, btn_wherebuy;
    EditText edt_count;
    TextView p_prize1;
    String url, setthumbnail;
    int s = 0;

    String O_Product_id = "";
    ArrayList<Bean_Desc> bean_desc_db = new ArrayList<Bean_Desc>();
    ArrayList<Bean_texhnicalImages> bean_technical = new ArrayList<Bean_texhnicalImages>();

    TextView tv_product_mrp, tv_product_selling_price, tv_product_name, tv_product_code, tv_packof, video_text;
    ImageView img_prodetail_wishlist, img_prodetail_shoppinglist, img_full;
    LinearLayout l_desc, l_Std_spec, l_technical, l_features, l_videos, l_more, l_video;
    LinearLayout l_desc_detail, l_std_spec_detail, l_technical_detail, l_features_detail, l_videos_detail, l_more_info1, moreinfo_text_layout, standard_text_layout, technical_text_layout;
    ImageView img_desc, img_std_spec, img_technical, img_features, img_videos, img_more, videoyoutube_img;
    TextView text_desc, text_std_spec, text_features, text_Tech_spec, text_option, more_Info_text, tvbullet;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    LinearLayout l_description_main, l_Feature_main, l_videos_main, l_technical_main, l_standard_technical_main, l_more_Info_main, l_youtubevideo_main;
    ArrayList<Bean_Desc> bean_desc1 = new ArrayList<Bean_Desc>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    Boolean desc = false, spec = false, technical = false, catalogue = false, features = false, videos = false, more = false, videoupper = false;
    String pid;
    AppPrefs app;
    String user_id_main = "";
    String role_id = "";
    ArrayList<Bean_ProductImage> bean_productImages_db = new ArrayList<Bean_ProductImage>();
    LinearLayout l_mrp, l_sell, l_total;
    TextView txt_selling_price, txt_total_txt;
    ImageView img_offer;
    /*  private static final Integer[] IMAGES = {R.drawable.boss1,
              R.drawable.boss2,
              R.drawable.boss3,
              R.drawable.boss4

      };*/
    String wishid = "";
    String mrp, sellingprice;
    Dialog dialog, dialogOffer, dialogDesc;
    Spinner sp_option;
    TextView txt_optionname, txt_mrp, txt_sell;
    String product_id = "";
    String s_description = "";
    String s_specification = "";
    String s_technical = "";
    String s_catalogs = "";
    String s_guarantee = "";
    String s_videos = "";
    LinearLayout detail_price, l_sellrs, l_mrprs;
    String s_general = "";
    TextView txt_product;
    int selected_image_position = 0;
    ImageView share_this;
    String filesToSend;
    ArrayList<Bean_Product> bean_product_schme = new ArrayList<Bean_Product>();
    ArrayList<Bean_Schme_value> bean_schme = new ArrayList<Bean_Schme_value>();
    LinearLayout l_mmain;
    ArrayList<Bean_schemeData> bean_S_data = new ArrayList<Bean_schemeData>();
    String owner_id = "";
    String u_id = "";
    String qun = "";
    int kkk = 0;
    int ik = 0;
    String rolee = "";
    ArrayList<Bean_ProductOprtion> bean_Oprtions = new ArrayList<Bean_ProductOprtion>();
    JSONArray jarray_cart = new JSONArray();
    ArrayList<Bean_schemeData> bean_Schme_data = new ArrayList<Bean_schemeData>();
    boolean whereToBuyVisibility = true;
    Button btn_enquiry;
    LinearLayout layout_detail;
    AdminAPI adminAPI;
    //private SectionsPagerAdapter mSectionsPagerAdapter;
    private ArrayList<String> ImagesArray = new ArrayList<String>();

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btlprodcut__detailpage);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        layout_detail = (LinearLayout) findViewById(R.id.layout_detail);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        setActionBar();
        jarray_cart = new JSONArray();

        dialog = new Dialog(BTLProduct_Detail.this);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        dialog.setCanceledOnTouchOutside(false);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogDesc = new Dialog(BTLProduct_Detail.this);
        dialogDesc.setCanceledOnTouchOutside(false);
        dialogDesc.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogOffer = new Dialog(BTLProduct_Detail.this);
        dialogOffer.getWindow();
        dialogOffer.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOffer.setCancelable(false);

        db = new DatabaseHandler(getApplicationContext());
        db.Delete_ALL_table();

        Intent i1 = getIntent();
        array_attribute_main.clear();
        list_data.clear();
        array_att.clear();
        array_value.clear();
        arrOptionTypeID.clear();
        moreinfo_arry.clear();
        arrOptionTypeName.clear();
        WishList.clear();
        list_of_images.clear();
        bean_data.clear();
        ImagesArray.clear();
        bean_product1.clear();
        bean_productFeatures.clear();
        bean_productTechSpecs.clear();
        bean_productStdSpecs.clear();
        bean_productOprtions.clear();
        bean_productCart.clear();
        bean_productImages.clear();
        pro_img.clear();

        bean_cart.clear();
        bean_desc1.clear();
        user_data.clear();


        app = new AppPrefs(BTLProduct_Detail.this);
        pid = app.getproduct_id();
        adminAPI = ServiceGenerator.getAPIServiceClass();
        //Log.e("pid", "" + pid);


        CategoryID = app.getUser_CatId();
        imageloader = new ImageLoader(BTLProduct_Detail.this);
        setRefershData();
        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id();
                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(BTLProduct_Detail.this);
                    role_id = app.getSubSalesId();
                    user_id_main = app.getSalesPersonId();
                    Snackbar.make(layout_detail, "Taking Order for : " + app.getUserName(), Snackbar.LENGTH_LONG).show();
                }

            }

        } else {
            user_id_main = "";
        }


        fetchID();
        new set_marquee().execute();


        l_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!desc) {

                   /* img_desc.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
                    img_std_spec.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_technical.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    //img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_features.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_videos.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_more.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_video_lower.setVisibility(View.GONE);

                    l_desc_detail.setVisibility(View.VISIBLE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.GONE);*/

                    webview_popup(s_description);

                       /* text_desc.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\n " +
                                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. ");
*/
                    desc = true;

                } else {
                    desc = false;
                  /*  img_desc.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_desc_detail.setVisibility(View.GONE);*/


                }


            }

        });

        l_Std_spec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spec == false) {

                   /* img_desc.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_std_spec.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
                    img_technical.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    //img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_features.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_videos.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_more.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.VISIBLE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.GONE);*/
                    // text_std_spec.setText("Available in 5 Colour Combinations to suit your kitchen");
                    //Log.e("s_guarantee", "" + s_guarantee);
                    webview_popup(s_guarantee);
                    spec = true;

                } else {
                    spec = false;
                   /* img_std_spec.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_std_spec_detail.setVisibility(View.GONE);*/


                }


            }

        });

        l_technical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (technical == false) {


                  /*  img_more.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_desc.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_std_spec.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_technical.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
                    // img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_features.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_videos.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.VISIBLE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.GONE);*/

                       /* text_Tech_spec.setText("Power\t160 W\n" +
                                "Voltage\t230 V AC\n" +
                                "Frequency\t50 Hz\n" +
                                "Speed\t2 Speeds\n" +
                                "Motor Speed\t12000 RPM");*/

                    webview_popup(s_catalogs);
                    technical = true;

                } else {
                    technical = false;
                  /*  img_technical.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_technical_detail.setVisibility(View.GONE);*/


                }

            }

        });

        l_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (features == false) {

                  /*  img_more.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_desc.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_std_spec.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_technical.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    //       img_catalogue.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_features.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
                    img_videos.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.VISIBLE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.GONE);*/
                    //Log.e("specifiction", "" + s_specification);
                    webview_popup(s_specification);
                    //  webview_popup(s_catalogs);
                    features = true;

                     /*   text_features.setText("3 SS Blades to do a variety of jobs\n" +
                                "Wall Mounting Stand");*/

                } else {
                    features = false;
                   /* img_features.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_features_detail.setVisibility(View.GONE);*/


                }

            }

        });
        l_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (videos == false) {

                   /* img_desc.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_more.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_std_spec.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_technical.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_features.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_videos.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
                    l_more_info1.setVisibility(View.GONE);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.VISIBLE);
                    l_video_lower.setVisibility(View.GONE);*/
                    //webview_popup(s_technical);
                    webview_technical(bean_technical);
                    //webview_popup(s_guarantee);
                    videos = true;

                } else {
                    videos = false;
                 /*   img_videos.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_videos_detail.setVisibility(View.GONE);*/


                }

            }

        });


        l_video_upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (videoupper == false) {


                    webview_popup(s_general);
                    videoupper = true;

                } else {
                    videoupper = false;


                }

            }

        });

        l_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (more == false) {

                    webview_popup(s_videos);

                    more = true;


                } else {
                    more = false;


                }


            }
        });


        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 1.0f);

        //  imageslider();


        list_data = new ArrayList<Integer>();

        list_data.add(R.drawable.a);
        list_data.add(R.drawable.a);
        list_data.add(R.drawable.a);
        list_data.add(R.drawable.a);


        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        btn_buyonline_product_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRefershData();
                final int[] minPackOfQty = {bean_product1.get(0).getPackQty()};
                final String[] selectedProductID = {pid};
                if (user_data.size() != 0) {
                    for (int i = 0; i < user_data.size(); i++) {

                        owner_id = user_data.get(i).getUser_id().toString();

                        role_id = user_data.get(i).getUser_type().toString();

                        if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                            app = new AppPrefs(BTLProduct_Detail.this);
                            role_id = app.getSubSalesId();
                            u_id = app.getSalesPersonId();
                        } else {
                            u_id = owner_id;
                        }


                    }

                    loadingView.show();
                    Call<GetCartItemQuantityResponse> getCartItemQuantityResponseCall = adminAPI.getCartItemQuantity(bean_product1.get(0).getPro_id(), owner_id, u_id);

                    // Log.e("WEB PARA------->",bean_product1.get(0).getPro_id()+"****"+owner_id+"****"+u_id);
                    getCartItemQuantityResponseCall.enqueue(new Callback<GetCartItemQuantityResponse>() {
                        @Override
                        public void onResponse(Call<GetCartItemQuantityResponse> call, Response<GetCartItemQuantityResponse> response) {
                            loadingView.dismiss();
                            GetCartItemQuantityResponse itemQuantityResponse = response.body();
                            if (itemQuantityResponse != null) {
                                if (itemQuantityResponse.isStatus()) {
                                    showAddToCartProductDialog(0, 1, itemQuantityResponse.getData().get(0).getQuantity(), selectedProductID);

                                    Log.e("It is working properly", ">");
                                } else {
                                    showAddToCartProductDialog(0, 0, "1", selectedProductID);
                                }

                            } else {
                                Globals.defaultError(getApplicationContext());
                                showAddToCartProductDialog(0, 0, "1", selectedProductID);

                            }

                        }

                        @Override
                        public void onFailure(Call<GetCartItemQuantityResponse> call, Throwable t) {
                            loadingView.dismiss();
                            Globals.showError(t, getApplicationContext());
                            showAddToCartProductDialog(0, 0, "1", selectedProductID);

                        }
                    });


                    // product_id = tv_pop_pname.getTag();
                    /*final List<NameValuePair> para = new ArrayList<NameValuePair>();
                    para.add(new BasicNameValuePair("product_id", bean_product1.get(0).getPro_id()));
                    para.add(new BasicNameValuePair("owner_id", owner_id));
                    para.add(new BasicNameValuePair("user_id", u_id));
                    //Log.e("111111111", "" + product_id);
                    //Log.e("222222222", "" + owner_id);
                    //Log.e("333333333", "" + u_id);

                    Globals.generateNoteOnSD(getApplicationContext(), "Line 592");

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

                            Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERROR", getLayoutInflater());
                            // loadingView.dismiss();

                        } else {
                            JSONObject jObj = new JSONObject(json);

                            String date = jObj.getString("status");

                            if (date.equalsIgnoreCase("false")) {

                                qun = "1";
                                kkk = 0;
                                //Log.e("131313131331", "1");

                                // loadingView.dismiss();
                            } else {

                                JSONObject jobj = new JSONObject(json);


                                JSONArray jsonArray = jObj.getJSONArray("data");
                                for (int iu = 0; iu < jsonArray.length(); iu++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(iu);
                                    qun = jsonObject.getString("quantity");

                                }

                                //Log.e("131313131331", "" + qun);
                                kkk = 1;


                            }

                        }
                    } catch (Exception j) {
                        j.printStackTrace();
                        //Log.e("json exce", j.getMessage());
                    }
*/


                } else {
                    Globals.CustomToast(BTLProduct_Detail.this, "Please Login First", getLayoutInflater());
                    user_id_main = "";
                }

            }

        });
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

        if (bean_product1.get(0).getScheme() == null || bean_product1.get(0).getScheme().isEmpty()) {
            img_offerDialog.setVisibility(View.GONE);
        }

        img_offerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOfferDialog(selectedProductID[0]);
            }
        });

        l_view_spinner = (LinearLayout) findViewById(R.id.l_view_spinner);
        tv_pop_pname = (TextView) dialog.findViewById(R.id.product_name);
        tv_pop_code = (TextView) dialog.findViewById(R.id.product_code);
        tv_pop_packof = (TextView) dialog.findViewById(R.id.product_packof);
        tv_pop_mrp = (TextView) dialog.findViewById(R.id.product_mrp);
        tv_txt_mrp = (TextView) dialog.findViewById(R.id.txt_mrp_text);
        l_mrp = (LinearLayout) dialog.findViewById(R.id.l_mrp);
        l_sell = (LinearLayout) dialog.findViewById(R.id.l_sell);
        l_total = (LinearLayout) dialog.findViewById(R.id.l_total);
        tv_pop_sellingprice = (TextView) dialog.findViewById(R.id.product_sellingprice);
        tv_total = (TextView) dialog.findViewById(R.id.txt_total);
        txt_selling_price = (TextView) dialog.findViewById(R.id.txt_selling_price);
        txt_total_txt = (TextView) dialog.findViewById(R.id.txt_total_txt);
        l_spinner = (LinearLayout) dialog.findViewById(R.id.l_spinner);
        l_spinner_text = (LinearLayout) dialog.findViewById(R.id.l_spinnertext);
        tv_pop_pname.setText(tv_product_name.getText().toString());
        tv_pop_code.setText(" " + tv_product_code.getText().toString() + " ");
        tv_pop_packof.setText(tv_packof.getText().toString());
        tv_pop_mrp.setText(tv_product_mrp.getText().toString());
        tv_pop_sellingprice.setText(tv_product_selling_price.getText().toString());
        tv_pop_sellingprice.setTag(tv_product_selling_price.getText().toString());

        tv_total.setText(tv_product_selling_price.getText().toString());
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
        int temp_count = Integer.parseInt(edt_count.getText().toString());
                /*int temp_total_count = temp_count + Integer.parseInt(array_product_cart.get(0).getPro_qty().toString());
                float temp_total = Float.parseFloat(tv_total.getText().toString());
                edt_count.setText("" + temp_total_count);*/
        double temp_total = Double.parseDouble(tv_total.getText().toString());
        double t = Double.parseDouble(tv_total.getText().toString()) * Double.parseDouble(edt_count.getText().toString());
        double w = round(t, 2);
        String str = String.format("%.2f", w);
        tv_total.setText(str);

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

        arrOptionTypeID.clear();
        arrOptionTypeName.clear();
        arrOptionTypeID = new ArrayList<String>();
        arrOptionTypeName = new ArrayList<String>();
        if (bean_productOprtions.size() != 0) {
            ArrayList<Bean_Attribute> array_attributes = new ArrayList<Bean_Attribute>();

            //Log.e("121212 ::", "" + bean_productOprtions.size());

            for (int c = 0; c < bean_productOprtions.size(); c++) {
                //Log.e("232323 ::", "" + bean_productOprtions.get(c).getPro_id());
                //Log.e("343434 ::", "" + pid);
                if (pid.equalsIgnoreCase(pid)) {
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

            //Log.e("45454545 ::", "" + array_attributes.size());

            if (arrOptionTypeID.size() != 0) {


                ArrayList<String> temp_array = new ArrayList<String>();
                for (int i = 0; i < arrOptionTypeID.size(); i++) {
                    temp_array.add(arrOptionTypeID.get(i));
                }
                LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionTypeID);

                arrOptionTypeID.clear();
                //Creating Arraylist without duplicate values
                arrOptionTypeID = new ArrayList<String>(listToSet);



                            /* LinkedHashSet<String> listToSet1 = new LinkedHashSet<String>(arrOptionTypeName);

                             arrOptionTypeName.clear();
                             //Creating Arraylist without duplicate values
                             arrOptionTypeName = new ArrayList<String>(listToSet1);

                             //Log.e("AAAABBBccc", "" + arrOptionTypeName.size());*/
                //Log.e("AAAABBB", "1 - " + arrOptionTypeID.size() + " 2- " + temp_array.size());
                //int z = 0;

                             /*for(int p = 0 ; p < temp_array.size() ; p ++)
                             {
                                   // Bean_Attribute
                                 for(int s = 0 ; s < arrOptionTypeID.size() ; s++)
                                 {
                                     if(temp_array.get(p).equalsIgnoreCase(arrOptionTypeID.get(s)))
                                     {
                                         //Log.e("", p + "---" + "option ID - " + temp_array.get(p) + "List hash ID  - " + arrOptionTypeID.get(s));
                                       //  bean_attribute.setOption_id();
                                     }

                                 }

                             }*/

                for (int p = 0; p < arrOptionTypeID.size(); p++) {
                    //Log.e("arrOptionTypeID", "----------" + arrOptionTypeID.get(p));
                }
                for (int p = 0; p < temp_array.size(); p++) {
                    //Log.e("temp_array", "----------" + temp_array.get(p));
                }

                for (int p = 0; p < array_attributes.size(); p++) {
                    //Log.e("array_attributes", "----------" + array_attributes.get(p).getOption_id());
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

                    final TextView text = new TextView(BTLProduct_Detail.this);

                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                    params1.setMargins(2, 2, 2, 2);

                    text.setLayoutParams(params1);

                              /*  ArrayList<Bean_Attribute> array_att = new ArrayList<Bean_Attribute>();
                                array_att = array_attribute_main.get(k);

                                text.setText(""+array_att.get(0).getOption_name());*/

                    text.setTextColor(Color.parseColor("#000000"));

                    text.setTextSize(12);

                    l_spinner_text.addView(text);

                    final Spinner spinner = new Spinner(BTLProduct_Detail.this);

                    spinner.setBackgroundResource(R.drawable.spinner_border);


                    spinner.setTag("" + k);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                    params.setMargins(2, 2, 2, 2);

                    spinner.setLayoutParams(params);

                    spinner.setAdapter(new MyAdapter(BTLProduct_Detail.this, R.layout.textview, array_attribute_main.get(k)));

                    l_spinner.addView(spinner);

                    array_att = new ArrayList<Bean_Attribute>();
                    array_att = array_attribute_main.get(k);


                    for (int t1 = 0; t1 < array_att.size(); t1++) {


                        text.setText("Select " + array_att.get(t1).getOption_name());


                        if (k == 0) {
                            option_id = array_att.get(t1).getOption_id();
                            option_name = array_att.get(t1).getOption_name();
                        } else {
                            option_id = option_id + ", " + array_att.get(t1).getOption_id();
                            option_name = option_name + ", " + array_att.get(t1).getOption_name();
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

                            minPackOfQty[0] = bean_productOprtions.get(position1).getMinPackOfQty();

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

                            //Log.e("Position", "" + att_array.get(position1).getValue_name());
                            //Log.e("Position", "" + att_array.get(position1).getValue_id());
                            //Log.e("Position", "" + att_array.get(position1).getValue_mrp());


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
                            tv_pop_pname.setTag(att_array.get(position1).getOption_pro_id());
                            //  tv_pop_code.setText(" ( " + att_array.get(position1).getValue_product_code().toString() + " )");
                            // tv_pop_pname.setText(att_array.get(position1).getOption_name());
                            if (att_array.get(position1).getValue_image().equalsIgnoreCase("") || att_array.get(position1).getValue_image().equalsIgnoreCase("null")) {


                            } else {
                                list_of_images.add(0, att_array.get(position1).getValue_image());
                                //adapter.notifyDataSetChanged();
                            }

                            if (att_array.get(position1).getValue_selling_price().toString().equalsIgnoreCase("0")) {

                            } else {


                                setRefershData();

                                if (user_data.size() != 0) {
                                    for (int i = 0; i < user_data.size(); i++) {

                                        owner_id = user_data.get(i).getUser_id().toString();

                                        role_id = user_data.get(i).getUser_type().toString();

                                        if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                            app = new AppPrefs(BTLProduct_Detail.this);
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
                                        // change the product name here in pop
                                        // tv_pop_pname.setText(finalAtt_array.get(position1).value_name);
                                        Log.e("WEB PARA------->", finalAtt_array.get(position1).getOption_pro_id() + "****" + owner_id + "****" + u_id + "" + finalAtt_array.get(position1).getOption_name());
                                        tv_pop_mrp.setText("" + finalAtt_array.get(position1).getValue_mrp());
                                        tv_pop_sellingprice.setText("" + finalAtt_array.get(position1).getValue_selling_price());

                                        if (finalAtt_array.get(position1).getValue_product_code().equalsIgnoreCase("null") || finalAtt_array.get(position1).getValue_product_code().equalsIgnoreCase("")) {
                                            //Log.e("121212", "" + att_array.get(position1).getValue_product_code().toString());
                                            tv_pop_code.setText(" " + tv_product_code.getText().toString() + "");

                                        } else {
                                            //Log.e("131313", "" + att_array.get(position1).getValue_product_code().toString());
                                            //result_holder.tvproduct_code.setText("ABC");

                                        }
                                        tv_pop_code.setText(" (" + finalAtt_array.get(position1).getValue_product_code() + ")");
                                        tv_pop_sellingprice.setTag(finalAtt_array.get(position1).getValue_selling_price());
                                        if (edt_count.getText().toString().equalsIgnoreCase("1")) {
                                            double t = Double.parseDouble(finalAtt_array.get(position1).getValue_selling_price());
                                            double w = round(t, 2);
                                            String str = String.format("%.2f", w);
                                            tv_total.setText(str);


                                        } else {
                                            int counter = Integer.parseInt(edt_count.getText().toString());
                                            double amt = Double.parseDouble(tv_pop_sellingprice.getText().toString());
                                            double total = amt * counter;
                                            double w = round(total, 2);
                                            //  float finalValue = (float) (Math.round(total * 100) / 100);
                                            String str = String.format("%.2f", w);
                                            tv_total.setText(str);
                                        }


                                    }


                                    // product_id = tv_pop_pname.getTag().toString();
                                /*List<NameValuePair> para = new ArrayList<NameValuePair>();
                                para.add(new BasicNameValuePair("product_id", att_array.get(position1).getOption_pro_id().toString()));
                                para.add(new BasicNameValuePair("owner_id", owner_id));
                                para.add(new BasicNameValuePair("user_id", u_id));
                                //Log.e("111111111", "" + product_id);
                                //Log.e("222222222", "" + owner_id);
                                //Log.e("333333333", "" + u_id);
                                Globals.generateNoteOnSD(getApplicationContext(), "Line 1020");
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
                                        Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERRER", getLayoutInflater());
                                        // loadingView.dismiss();

                                    } else {
                                        JSONObject jObj = new JSONObject(json);

                                        String date = jObj.getString("status");

                                        if (date.equalsIgnoreCase("false")) {

                                            qun[0] = "1";
                                            kkk[0] = 0;
                                            //Log.e("131313131331", "1");

                                            // loadingView.dismiss();
                                        } else {

                                            JSONObject jobj = new JSONObject(json);


                                            JSONArray jsonArray = jObj.getJSONArray("data");
                                            for (int iu = 0; iu < jsonArray.length(); iu++) {
                                                JSONObject jsonObject = jsonArray.getJSONObject(iu);
                                                qun = jsonObject.getString("quantity");

                                            }

                                            //Log.e("131313131331", "" + qun);
                                            kkk = 1;


                                        }

                                    }
                                } catch (Exception j) {
                                    j.printStackTrace();
                                    //Log.e("json exce", j.getMessage());
                                }
*/
                                });
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

        String c = edt_count.getText().toString();
        //Log.e("float - ", "" + edt_count.getText().toString());
        s = Integer.parseInt(c);
        minuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = edt_count.getText().toString();
                if (!c.isEmpty()) {
                    s = Integer.parseInt(c);
                    if (s > 0) {
                        int mMinPackOfQty = minPackOfQty[0];
                        if (s < mMinPackOfQty) {
                            s = 0;
                        } else {
                            int toCutOutQty = s % mMinPackOfQty;

                            Log.e("toCutOut Minus", "-->" + toCutOutQty);

                            if (toCutOutQty > 0)
                                s = s - toCutOutQty;
                            else
                                s = s - mMinPackOfQty;
                        }
                        //s = s - 1;
                        edt_count.setText(String.valueOf(s));
                        int counter = s;
                        double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                        double total = amt * counter;
                        //float finalValue = (float) (Math.round(total * 100) / 100);
                        double w = round(total, 2);
                        String str = String.format("%.2f", w);
                        tv_total.setText(str);
                    }
                }
            }
        });
        String c1 = edt_count.getText().toString();
        //Log.e("float - ", "" + edt_count.getText().toString());
        // s=Integer.parseInt(c1);
        s = Integer.parseInt(c1);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c1 = edt_count.getText().toString();

                c1 = c1.isEmpty() ? "0" : c1;
                //Log.e("float - ", "" + edt_count.getText().toString());
                // s=Integer.parseInt(c1);
                s = Integer.parseInt(c1);
                int mMinPackOfQty = minPackOfQty[0];
                if (s == 0) {
                    s = s + mMinPackOfQty;
                } else if (s > 0) {

                    if (s < mMinPackOfQty) {
                        int remainedToFullfill = mMinPackOfQty - s;

                        if (remainedToFullfill > 0) {
                            s = s + remainedToFullfill;
                        } else {
                            s = s + mMinPackOfQty;
                        }
                    } else if (s >= mMinPackOfQty) {
                        int toCutOutQuantity = s % mMinPackOfQty;
                        if (toCutOutQuantity > 0) {
                            s = s - toCutOutQuantity + mMinPackOfQty;
                        } else {
                            s = s + mMinPackOfQty;
                        }
                    }
                }


                //s = s + minPackOfQty;
                edt_count.setText(String.valueOf(s));
                int counter = s;
                double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                double total = amt * counter;
                //  float finalValue = (float) (Math.round(total * 100) / 100);
                double w = round(total, 2);
                String str = String.format("%.2f", w);
                tv_total.setText(str);

                // Toast.makeText(getApplicationContext(),"Total "+to,Toast.LENGTH_SHORT).show();
            }
        });

        edt_count.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                String enteredQty = edt_count.getText().toString().trim();
                int qty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);

                if (qty <= 0) {
                    buy_cart.setEnabled(false);
                    tv_total.setText("0.00");
                    txt_availableScheme.setText("");
                } else {
                    buy_cart.setEnabled(true);
                    int mMinPackOfQty = minPackOfQty[0];

                    Log.e("Modulo Rem", "-->" + qty % mMinPackOfQty);

                                /*if (qty >= mMinPackOfQty && C.modOf(qty,mMinPackOfQty) == 0) {

                                    String productpricce = tv_pop_sellingprice.getTag().toString();
                                    amount1 = Double.parseDouble(s.toString());
                                    amount1 = Double.parseDouble(productpricce) * amount1;
                                    //float finalValue = (float) (Math.round(amount1 * 100) / 100);
                                    double w = round(amount1, 2);
                                    String str = String.format("%.2f", w);
                                    tv_total.setText(str);
                                } else {
                                    tv_total.setText("");
                                }*/

                    String productpricce = tv_pop_sellingprice.getTag().toString();
                    amount1 = Double.parseDouble(s.toString());
                    amount1 = Double.parseDouble(productpricce) * amount1;
                    //float finalValue = (float) (Math.round(amount1 * 100) / 100);
                    double w = round(amount1, 2);
                    String str = String.format("%.2f", w);
                    tv_total.setText(str);

                    int schemeSelectedPosition = -1;

                    ArrayList<Bean_schemeData> selectedProductSchemes = new ArrayList<Bean_schemeData>();

                    for (int i = 0; i < bean_Schme_data.size(); i++) {
                        if (bean_Schme_data.get(i).getSchme_prod_id().equals(selectedProductID[0])) {
                            selectedProductSchemes.add(bean_Schme_data.get(i));
                        }
                    }

                    for (int i = 0; i < selectedProductSchemes.size(); i++) {
                        int schemeQty = Integer.parseInt(selectedProductSchemes.get(i).getSchme_qty());

                        //Log.e("Scheme minQty","Qty : "+schemeQty+", ProductID - "+bean_product1.get(0).getPro_id());

                        Log.e("Scheme Pro ID", selectedProductSchemes.get(i).getSchme_prod_id() + " - " + selectedProductID[0]);

                        if (selectedProductSchemes.get(i).getSchme_prod_id().equals(selectedProductID[0])) {
                            if (qty < schemeQty) {
                                schemeSelectedPosition = i;
                                break;
                            } else {
                                schemeSelectedPosition = selectedProductSchemes.size() - 1;
                            }
                        }
                    }

                    if (selectedProductSchemes.size() > 0 && schemeSelectedPosition >= 0) {
                        txt_availableScheme.setText(selectedProductSchemes.get(schemeSelectedPosition).getSchme_name());
                    } else {
                        txt_availableScheme.setText("");
                    }

                }
            }
        });

        buy_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setRefershData();

                if (user_data.size() == 0) {

                    Intent i = new Intent(BTLProduct_Detail.this, LogInPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);


                } else {

                    String enteredQty = edt_count.getText().toString().trim();
                    int currentQty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);
                    //int minPackOfQty = bean_product1.get(0).getPackQty();

                    if (currentQty < minPackOfQty[0] || C.modOf(currentQty, minPackOfQty[0]) > 0) {
                        C.showMinPackAlert(BTLProduct_Detail.this, minPackOfQty[0]);
                        Log.e("Mod Rem", "-->" + C.modOf(currentQty, minPackOfQty[0]));
                    } else {

                        if (user_data.size() != 0) {
                            for (int i = 0; i < user_data.size(); i++) {

                                user_id_main = user_data.get(i).getUser_id();

                                role_id = user_data.get(i).getUser_type();

                                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                    app = new AppPrefs(BTLProduct_Detail.this);
                                    role_id = app.getSubSalesId();
                                    user_id_main = app.getSalesPersonId();
                                }


                            }

                        } else {
                            user_id_main = "";
                        }
                        product_id = tv_pop_pname.getTag().toString();
                        String qty = edt_count.getText().toString();

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


                            private void proceedForNextStepAfterScheme() {

                                if (bean_product_schme.size() == 0) {
                                    //Log.e("555555555", "" + bean_product_schme.size());
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
                                    bean.setPro_cat_id(CategoryID);
                                    //Log.e("Cat_ID....", "" + CategoryID);
                                    if (list_of_images.size() == 0) {
                                        bean.setPro_Images(bean_product1.get(0).getPro_image().toString());
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
                                    bean.setPro_shortdesc(bean_product1.get(0).getPro_label());
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
                                            jobject.put("category_id", CategoryID);
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
                                    if (kkk[0] == 1) {

                                        new Edit_Product().execute();

                                    } else {

                                        new Add_Product().execute();
                                    }

                                    //db.Add_Product_cart(bean);




                                      /*  Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                                        Intent i = new Intent(Product_List.this, Product_List.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);*/


                                } else {
                                    //Log.e("Type ID : ", "" + bean_schme.get(0).getType_id().toString());
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
                                        //Log.e("-- ", "producttt Id " + tv_pop_pname.getTag().toString());
                                        // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                        bean.setPro_cat_id(CategoryID);
                                        //Log.e("Cat_ID....", "" + CategoryID);
                                        if (list_of_images.size() == 0) {
                                            bean.setPro_Images(bean_product1.get(0).getPro_image().toString());
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
                                        // sell = String.valueOf(fse);

                                        int fqu = qu + (int) getqu;

                                        bean.setPro_qty(String.valueOf(fqu));
                                        bean.setPro_mrp(tv_pop_mrp.getText().toString());
                                        bean.setPro_sellingprice(sell);
                                        bean.setPro_shortdesc(bean_product1.get(0).getPro_label());
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
                                                jobject.put("category_id", CategoryID);
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
                                        if (kkk[0] == 1) {

                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
                                        }


                                    } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("2")) {

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
                                        bean.setPro_cat_id(CategoryID);
                                        //Log.e("Cat_ID....", "" + CategoryID);
                                        if (list_of_images.size() == 0) {
                                            bean.setPro_Images(bean_product1.get(0).getPro_image().toString());
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
                                        bean.setPro_shortdesc(bean_product1.get(0).getPro_label());
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
                                            jobject.put("category_id", CategoryID);
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
                                        //Log.e("Cat_ID....", "" + CategoryID);
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
                                            jobject.put("pro_scheme", "" + tv_pop_pname.getTag().toString());
                                            jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                            jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                            jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                            jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                            jobject.put("prod_img", bean_product_schme.get(0).getPro_image());


                                            jarray_cart.put(jobject);
                                        } catch (JSONException e) {

                                        }


                                        // db.Add_Product_cart_scheme(bean_s);

                                        array_value.clear();
                                        if (kkk[0] == 1) {

                                            new Edit_Product().execute();

                                        } else {

                                            new Add_Product().execute();
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
                                        bean.setPro_cat_id(CategoryID);
                                        //Log.e("Cat_ID....", "" + CategoryID);
                                        if (list_of_images.size() == 0) {
                                            bean.setPro_Images(bean_product1.get(0).getPro_image());
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
                                        bean.setPro_shortdesc(bean_product1.get(0).getPro_label());
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
                                                jobject.put("category_id", CategoryID);
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
                                                jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                                jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                if (list_of_images.size() == 0) {
                                                    jobject.put("prod_img", bean_product1.get(0).getPro_image());
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
                                        if (kkk[0] == 1) {

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

                        });


                    }
                }






                        /*List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("product_id", product_id));
                        params.add(new BasicNameValuePair("user_id", user_id_main));
                        params.add(new BasicNameValuePair("product_buy_qty", qty));
                        //Log.e("111111111", "" + product_id);
                        //Log.e("CARTTTTT", "" + user_id_main);
                        //Log.e("333333333", "" + qty);
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
                                Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERRER", getLayoutInflater());
                                // loadingView.dismiss();

                            } else {
                                JSONObject jObj = new JSONObject(json);

                                String date = jObj.getString("status");

                                if (date.equalsIgnoreCase("false")) {
                                    String Message = jObj.getString("message");
                                    bean_product_schme.clear();
                                    bean_schme.clear();
                                    //Log.e("11111111", "" + product_id);
                                    //   Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                                    // loadingView.dismiss();
                                } else {

                                    JSONObject jobj = new JSONObject(json);
                                    //Log.e("22222222222", "" + product_id);
                          *//*  bean_product1.clear();
                            bean_productTechSpecs.clear();
                            bean_productOprtions.clear();
                            bean_productFeatures.clear();
                            bean_productStdSpecs.clear();*//*


                                    bean_product_schme.clear();
                                    bean_schme.clear();

                                    JSONObject jsonArray = jObj.getJSONObject("data");
                                    //Log.e("3333333333", "" + product_id);
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


                                    //loadingView.dismiss();

                                }

                            }
                        } catch (Exception j) {
                            j.printStackTrace();
                            //Log.e("json exce", j.getMessage());
                        }*/
                //Log.e("45454545", "" + bean_product_schme.size());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void showOfferDialog(String selectedProductID) {
        {

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            dialogOffer.setContentView(R.layout.scheme_layout);
            dialogOffer.getWindow().setLayout((6 * width) / 7, (4 * height) / 8);
            dialogOffer.setCancelable(true);
            dialogOffer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            ImageView btn_cancel = (ImageView) dialogOffer.findViewById(R.id.btn_cancel);
            bean_S_data.clear();

            Log.e("size", "" + bean_Schme_data.size());


            for (int i = 0; i < bean_Schme_data.size(); i++) {

                if (bean_Schme_data.get(i).getSchme_prod_id().equalsIgnoreCase(selectedProductID)) {
                    Bean_schemeData beans = new Bean_schemeData();
                    beans.setSchme_id(bean_Schme_data.get(i).getSchme_id());
                    Log.e("ID", "" + pid);
                    Log.e("name", "" + bean_Schme_data.get(i).getSchme_name());
                    beans.setSchme_name(bean_Schme_data.get(i).getSchme_name());
                    beans.setCategory_id(bean_Schme_data.get(i).getCategory_id());
                    beans.setSchme_qty(bean_Schme_data.get(i).getSchme_qty());
                    beans.setSchme_buy_prod_id(bean_Schme_data.get(i).getSchme_buy_prod_id());
                    beans.setSchme_prod_id(bean_Schme_data.get(i).getSchme_prod_id());

                    bean_S_data.add(beans);


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

            final ExpandableSchemeAdapter schemeAdapter = new ExpandableSchemeAdapter(getApplicationContext(), schemeHeaders, schemeChildList);
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

                                app = new AppPrefs(BTLProduct_Detail.this);
                                role_id = app.getSubSalesId();
                                user_id_main = app.getSalesPersonId();
                            } else {
                                user_id_main = owner_id;
                            }


                        }


                        // product_id = tv_pop_pname.getTag().toString();
                        List<NameValuePair> para = new ArrayList<NameValuePair>();
                        para.add(new BasicNameValuePair("product_id", pid));
                        para.add(new BasicNameValuePair("owner_id", owner_id));
                        para.add(new BasicNameValuePair("user_id", user_id_main));
                        Log.e("111111111", "" + pid);
                        Log.e("222222222", "" + owner_id);
                        Log.e("333333333", "" + u_id);
                        Globals.generateNoteOnSD(getApplicationContext(), "Line 5176");

                        isNotDone = true;
                        new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        while (isNotDone) {

                        }
                        isNotDone = true;
                        Globals.generateNoteOnSD(getApplicationContext(), "Return JSON 5184");

                        String json = jsonData; //GetProductDetailByQty(para);
                        jsonData = "";

                        try {


                            //System.out.println(json);

                            if (json == null || json.isEmpty()) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERROR", getLayoutInflater());
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
                            Globals.generateNoteOnSD(getApplicationContext(), j.getMessage());
                            //Log.e("json exce",j.getMessage());
                        }
                        Log.e("kkk", "" + kkk);
                        product_id = pid;
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
                        isNotDone = false;

                        Globals.generateNoteOnSD(getApplicationContext(), "Return JSON 5259 ->" + jsonData);

                        String jsons = jsonData; //GetProductDetailByCode(params);
                        jsonData = "";

                        //new check_schme().execute();
                        try {


                            //System.out.println(json);

                            if (jsons == null || jsons.isEmpty()) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERROR", getLayoutInflater());
                                // loadingView.dismiss();

                            } else {
                                JSONObject jObj = new JSONObject(jsons);

                                boolean date = jObj.getBoolean("status");

                                if (!date) {
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
                            Globals.generateNoteOnSD(getApplicationContext(), j.getMessage());
                            //Log.e("json exce",j.getMessage());
                        }

                        Globals.generateNoteOnSD(getApplicationContext(), "Line Exceuted Till 5404 ->" + bean_product_schme.size());
                        //Log.e("45454545",""+bean_product_schme.size());
                        if (bean_product_schme.size() == 0) {
                            Log.e("1111111111111", "" + qty);
                            dialogOffer.dismiss();
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
                                //double maxqu = Double.parseDouble(maxq);
                                int qu = Integer.parseInt(qty);


                                String sell = tv_product_selling_price.getText().toString();

                                double se = Double.parseDouble(tv_product_selling_price.getText().toString()) * buyqu;

                                double se1 = buyqu + getqu;

                                double fse = se / se1;
                                double w = round(fse, 2);
                                sell = String.format("%.2f", w);
                                //sell = String.valueOf(fse);

                                int fqu = qu + (int) getqu;


                                for (int i = 0; i < 1; i++) {
                                    try {
                                        JSONObject jobject = new JSONObject();

                                        jobject.put("user_id", user_id_main);
                                        jobject.put("role_id", role_id);
                                        jobject.put("owner_id", owner_id);
                                        jobject.put("product_id", pid);
                                        jobject.put("category_id", CategoryID);
                                        jobject.put("name", tv_product_name.getText().toString());

                                        String newString = tv_product_code.getText().toString().trim().replace("(", "");
                                        String aString = newString.toString().trim().replace(")", "");
                                        jobject.put("pro_code", aString.toString().trim());
                                        jobject.put("quantity", String.valueOf(fqu));
                                        jobject.put("mrp", tv_product_mrp.getText().toString());
                                        jobject.put("selling_price", sell);
                                        jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                        jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                        jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                        jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                        double f = fqu * Double.parseDouble(sell);
                                        double w1 = round(f, 2);
                                        String str = String.format("%.2f", w1);
                                        jobject.put("item_total", str);
                                        jobject.put("pro_scheme", " ");
                                        jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                        jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                        jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                        jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                        if (list_of_images.size() == 0) {
                                            jobject.put("prod_img", bean_product1.get(0).getPro_image());
                                            // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                        } else {
                                            jobject.put("prod_img", list_of_images.get(0));
                                            //   bean.setPro_Images(list_of_images.get(0).toString());
                                        }


                                        jarray_cart.put(jobject);
                                    } catch (JSONException e) {
                                        Globals.generateNoteOnSD(getApplicationContext(), "5481 ->" + e.getMessage());
                                    }


                                }
                                array_value.clear();

                                Log.e("kkkkkkk", "" + kkk);
                                if (kkk == 1) {

                                    Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5491");
                                    new Edit_Product().execute();
                                    dialogOffer.dismiss();
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                } else {
                                    Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5495");
                                    new Add_Product().execute();
                                    dialogOffer.dismiss();
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }


                            } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {

                                jarray_cart = new JSONArray();
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", user_id_main);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", pid);
                                    jobject.put("category_id", CategoryID);
                                    jobject.put("name", tv_product_name.getText().toString());
                                    String newString = tv_product_code.getText().toString().trim().replace("(", "");
                                    String aString = newString.trim().replace(")", "");
                                    jobject.put("pro_code", aString.trim());
                                    jobject.put("quantity", qty);
                                    jobject.put("mrp", tv_product_mrp.getText().toString());
                                    jobject.put("selling_price", tv_product_selling_price.getText().toString());
                                    jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                    jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                    jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                    jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                    double f = Integer.parseInt(qty) * Double.parseDouble(tv_product_selling_price.getText().toString());
                                    double w1 = round(f, 2);
                                    String str = String.format("%.2f", w1);
                                    jobject.put("item_total", str);
                                    jobject.put("pro_scheme", " ");
                                    jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                    jobject.put("scheme_id", " ");
                                    jobject.put("scheme_title", " ");
                                    jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                    if (list_of_images.size() == 0) {
                                        jobject.put("prod_img", bean_product1.get(0).getPro_image());
                                        // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                    } else {
                                        jobject.put("prod_img", list_of_images.get(0));
                                        //   bean.setPro_Images(list_of_images.get(0).toString());
                                    }


                                    jarray_cart.put(jobject);
                                } catch (JSONException e) {
                                    Globals.generateNoteOnSD(getApplicationContext(), "5542 -> " + e.getMessage());
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

                                    jobject.put("user_id", user_id_main);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                    jobject.put("category_id", bean_product_schme.get(0).getPro_cat_id());
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
                                    jobject.put("pro_scheme", bean_product1.get(0).getPro_id());
                                    jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                    jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                    //Log.e("C1C1",""+bean_schme.get(0).getScheme_id());
                                    jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                    jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                    jobject.put("prod_img", bean_product_schme.get(0).getPro_image());


                                    jarray_cart.put(jobject);
                                } catch (JSONException e) {
                                    Globals.generateNoteOnSD(getApplicationContext(), "5596 ->" + e.getMessage());
                                }


                                // db.Add_Product_cart_scheme(bean_s);

                                array_value.clear();

                                Log.e("kkkkkkk", "" + kkk);
                                if (kkk == 1) {
                                    Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5607");
                                    new Edit_Product().execute();
                                    dialogOffer.dismiss();
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                } else {
                                    Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5611");
                                    new Add_Product().execute();
                                    dialogOffer.dismiss();
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }

                            } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("3")) {


                                String getq = bean_schme.get(0).getGet_qty();
                                String buyq = bean_schme.get(0).getBuy_qty();
                                String maxq = bean_schme.get(0).getMax_qty();

                                double getqu = Double.parseDouble(getq);
                                double buyqu = Double.parseDouble(buyq);
                                //double maxqu = Double.parseDouble(maxq);
                                int qu = Integer.parseInt(qty);


                                String sell = tv_product_selling_price.getText().toString();

                                String disc = bean_schme.get(0).getDisc_per();

                                double se = Double.parseDouble(tv_product_selling_price.getText().toString()) * Double.parseDouble(bean_schme.get(0).getDisc_per());

                                double se1 = se / 100;

                                double fse = Double.parseDouble(tv_product_selling_price.getText().toString()) - se1;
                                double w1 = round(fse, 2);
                                sell = String.format("%.2f", w1);
                                jarray_cart = new JSONArray();

                                for (int i = 0; i < 1; i++) {
                                    try {
                                        JSONObject jobject = new JSONObject();

                                        jobject.put("user_id", user_id_main);
                                        jobject.put("role_id", role_id);
                                        jobject.put("owner_id", owner_id);
                                        jobject.put("product_id", pid);
                                        jobject.put("category_id", CategoryID);
                                        jobject.put("name", tv_product_name.getText().toString());
                                        String newString = tv_product_code.getText().toString().trim().replace("(", "");
                                        String aString = newString.trim().replace(")", "");
                                        jobject.put("pro_code", aString.trim());
                                        jobject.put("quantity", qty);
                                        jobject.put("mrp", tv_product_mrp.getText().toString());
                                        jobject.put("selling_price", sell);
                                        jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                        jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                        jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                        jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                        double f = Double.parseDouble(qty) * Double.parseDouble(sell);
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
                                        Globals.generateNoteOnSD(getApplicationContext(), "5683 ->" + e.getMessage());
                                    }


                                }
                                array_value.clear();

                                Log.e("kkkkkkk", "" + kkk);
                                if (kkk == 1) {
                                    Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5692");
                                    new Edit_Product().execute();
                                    dialogOffer.dismiss();
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                } else {
                                    Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5696");
                                    new Add_Product().execute();
                                    dialogOffer.dismiss();
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                            }
                        }

                    } else {
                        dialogOffer.dismiss();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Globals.CustomToast(BTLProduct_Detail.this, "Please Login First", getLayoutInflater());
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        //Log.e("System GC", "Called");
    }

    private void fetchID() {
        btn_enquiry = (Button) findViewById(R.id.btn_enquiry);
        tv_product_mrp = (TextView) findViewById(R.id.tv_product_mrp);
        tv_product_selling_price = (TextView) findViewById(R.id.tv_product_selling_price);
        tv_product_name = (TextView) findViewById(R.id.tv_product_name);
        tv_product_code = (TextView) findViewById(R.id.tv_product_code);
        tv_packof = (TextView) findViewById(R.id.tv_packof);
        tvbullet = (TextView) findViewById(R.id.tvbullet);
        off_tag = (TextView) findViewById(R.id.off_tag);
        // txt_product=(TextView)findViewById(R.id.txt_product);
        detail_price = (LinearLayout) findViewById(R.id.detail_price);
        l_description_main = (LinearLayout) findViewById(R.id.l_description_main);
        l_Feature_main = (LinearLayout) findViewById(R.id.l_Feature_main);
        l_videos_main = (LinearLayout) findViewById(R.id.l_videos_main);
        l_technical_main = (LinearLayout) findViewById(R.id.l_technical_main);
        l_standard_technical_main = (LinearLayout) findViewById(R.id.l_standard_technical_main);
        l_more_Info_main = (LinearLayout) findViewById(R.id.l_more_Info_main);
        l_youtubevideo_main = (LinearLayout) findViewById(R.id.l_youtubevideo_main);
        btn_buyonline_product_detail = (Button) findViewById(R.id.btn_buyonline_product_detail);
        l_sellrs = (LinearLayout) findViewById(R.id.l_sellrs);
        l_mrprs = (LinearLayout) findViewById(R.id.l_mrprs);
        txt_sell = (TextView) findViewById(R.id.txt_sell);
        txt_mrp = (TextView) findViewById(R.id.txt_mrp);
        sp_option = (Spinner) findViewById(R.id.sp_option);
        txt_optionname = (TextView) findViewById(R.id.txt_optionname);
        l_mmain = (LinearLayout) findViewById(R.id.l_mmain);
        share_this = (ImageView) findViewById(R.id.share_this);

        share_this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_Image();
                //fetch_image_for_share(position);
            }
        });


        mPager = (ViewPager) findViewById(R.id.pager);
        img_prodetail_wishlist = (ImageView) findViewById(R.id.img_prodetail_wishlist);
        img_prodetail_shoppinglist = (ImageView) findViewById(R.id.img_prodetail_shoppinglist);
        setRefershData();
        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                rolee = user_data.get(i).getUser_type().toString();

            }

        } else {
            user_id_main = "";
        }

        if (rolee.equals(C.ADMIN) || rolee.equalsIgnoreCase("6") || rolee.equalsIgnoreCase("7")) {
            img_prodetail_wishlist.setVisibility(View.GONE);
            img_prodetail_shoppinglist.setVisibility(View.GONE);
        } else {
            img_prodetail_wishlist.setVisibility(View.VISIBLE);
            img_prodetail_shoppinglist.setVisibility(View.VISIBLE);
        }
        img_full = (ImageView) findViewById(R.id.img_full);
        img_offer = (ImageView) findViewById(R.id.img_offer);

        product_detail_marquee = (TextView) this.findViewById(R.id.product_detail_marquee);
        product_detail_marquee.setSelected(true);


        layout_imagegrid = (LinearLayout) findViewById(R.id.layout_imagegrid);
        Feature_text_layout = (LinearLayout) findViewById(R.id.Feature_text_layout);
        moreinfo_text_layout = (LinearLayout) findViewById(R.id.moreinfo_text_layout);
        standard_text_layout = (LinearLayout) findViewById(R.id.standard_text_layout);
        technical_text_layout = (LinearLayout) findViewById(R.id.technical_text_layout);
        layout_youtube_forimg = (LinearLayout) findViewById(R.id.layout_youtube_forimg);

        l_desc = (LinearLayout) findViewById(R.id.l_description);
        l_Std_spec = (LinearLayout) findViewById(R.id.l_standard_technical);
        l_technical = (LinearLayout) findViewById(R.id.l_technical);
        option_text_layout = (LinearLayout) findViewById(R.id.option_text_layout);

        l_features = (LinearLayout) findViewById(R.id.l_Feature);
        l_videos = (LinearLayout) findViewById(R.id.l_videos);
        l_more = (LinearLayout) findViewById(R.id.l_more_Info);


        l_desc_detail = (LinearLayout) findViewById(R.id.l_description_detail);
        l_std_spec_detail = (LinearLayout) findViewById(R.id.l_standard_technical_detail);
        l_technical_detail = (LinearLayout) findViewById(R.id.l_technical_detail);
        l_features_detail = (LinearLayout) findViewById(R.id.l_Feature_detail);
        l_videos_detail = (LinearLayout) findViewById(R.id.l_videos_detail);
        l_more_info1 = (LinearLayout) findViewById(R.id.l_more_Info_detail);
        l_video_upper = (LinearLayout) findViewById(R.id.l_video_upper);
        l_video_lower = (LinearLayout) findViewById(R.id.l_video_lower);

        img_desc = (ImageView) findViewById(R.id.desc_image);
        img_std_spec = (ImageView) findViewById(R.id.standard_technical_image);
        img_technical = (ImageView) findViewById(R.id.technical_image);

        img_features = (ImageView) findViewById(R.id.Feature_image);
        img_videos = (ImageView) findViewById(R.id.videos_image);
        videoyoutube_img = (ImageView) findViewById(R.id.videoyoutube_img);
        img_more = (ImageView) findViewById(R.id.more_Info_image);

        text_desc = (TextView) findViewById(R.id.desc_text);
        text_std_spec = (TextView) findViewById(R.id.standard_technical_text);
        text_features = (TextView) findViewById(R.id.Feature_text);
        text_Tech_spec = (TextView) findViewById(R.id.technical_text);
        more_Info_text = (TextView) findViewById(R.id.more_Info_text);
        btn_wherebuy = (Button) findViewById(R.id.btn_wherebuy);
        btn_wherebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(BTLProduct_Detail.this);
                app.setProduct_Sort("");
                app.setFilter_Option("");
                app.setFilter_SubCat("");
                app.setFilter_Cat("");
                app.setFilter_Sort("");
                db = new DatabaseHandler(BTLProduct_Detail.this);
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(BTLProduct_Detail.this, Where_To_Buy.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        tv_product_mrp.setPaintFlags(tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

   /* more_Info_text.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SetRefershDataProduct();

            if (bean_product1.size() != 0) {

                for (int i = 0; i < bean_product1.size(); i++) {

                    if (bean_product1.get(i).getPro_id().equalsIgnoreCase(pid)) {

                        new DownloadFile().execute("http://app.nivida.in/boss" + bean_product1.get(i).getPro_moreinfo().toString(), "ProductDetail.pdf");
                    }
                }
            }

        }
    });*/


       /* btn_wherebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTLProduct_Detail.this,Where_toBuy.class);

                startActivity(i);
            }
        });*/
        img_prodetail_shoppinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTLProduct_Detail.this, Add_to_shoppinglist.class);
                app = new AppPrefs(BTLProduct_Detail.this);
                app.setRef_Shopping("product_detail");
                app.setshopping_pro_id(pid);
                app.setproduct_id(pid);
                startActivity(i);
            }
        });

        /*img_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTLProduct_Detail.this, Event_Gallery_photo_view_Activity.class);
                app.setproduct_id(pid);
                //Log.e("img_full",""+pid);
                startActivity(i);
            }
        });*/

        img_full.setDrawingCacheEnabled(true);

        img_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTLProduct_Detail.this, Event_Gallery_photo_view_Activity.class);
                app.setproduct_id(pid);
                //Log.e("img_full", "" + pid);
                i.putExtra("position", selected_image_position);
                i.putExtra("imgProCode", bean_product1.get(0).getPro_code());
                i.putExtra("imgProName", bean_product1.get(0).getPro_name());
                // i.putExtra("mylist", bean_productImages);
                //Log.e("pos selected", "" + selected_image_position);
                startActivity(i);
            }
        });


        img_prodetail_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              /*  if(img_prodetail_wishlist.getTag().equals(R.drawable.whishlist)){

                    //   Globals.CustomToast(BTLProduct_Detail.this,"add",getLayoutInflater());
                    new set_wish_list().execute();
                    img_prodetail_wishlist.setTag(R.drawable.whishlist_fill);
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist_fill);
                    wishid ="1";
                }
                else if(img_prodetail_wishlist.getTag().equals(R.drawable.whishlist_fill)) {
                    //  Globals.CustomToast(BTLProduct_Detail.this,"delete",getLayoutInflater());
                    new delete_wish_list().execute();
                    img_prodetail_wishlist.setTag(R.drawable.whishlist);
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
                    wishid ="2";
                }*/

                if (WishList.size() != 0) {

                    String st = WishList.toString();
                    //Log.e("111111111", st);
                    if (WishList.contains(pid)) {

                        img_prodetail_wishlist.setImageResource(R.drawable.whishlist_fill);
                        img_prodetail_wishlist.setTag(R.drawable.whishlist_fill);
                        new delete_wish_list().execute();

                    } else {
                        img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
                        img_prodetail_wishlist.setTag(R.drawable.whishlist);
                        new set_wish_list().execute();

                    }
                } else if (WishList.size() == 0) {
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
                    img_prodetail_wishlist.setTag(R.drawable.whishlist);
                    new set_wish_list().execute();

                }

            }
        });

    }

    private void shareImage() {
        String filePath = "";
        img_full.destroyDrawingCache();
        img_full.setDrawingCacheEnabled(true);
        img_full.destroyDrawingCache();
        img_full.buildDrawingCache();
        Bitmap bitmap = img_full.getDrawingCache();
        try {

            File rootFile = new File(Environment.getExternalStorageDirectory() + "/BTL/Gallery");
            if (rootFile.exists())
                Log.e("Dir D", "-->" + deleteDriectory(rootFile.getAbsolutePath()));
            rootFile.mkdir();
            File imagePath = new File(rootFile + File.separator + ImgProcode + "_" + ImgProName + ".jpg");

            if (imagePath.exists())
                Log.e("File D", "-->" + imagePath.delete());

            imagePath.createNewFile();
            //fileName = filePath+File.separator+fileName;
            OutputStream ostream = new FileOutputStream(imagePath);
            filePath = imagePath.getAbsolutePath();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.flush();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String ic = "<html><body><b>Item Code :</b></body></html>";
        String in = "<html><body><b>Item Name :</b></body></html>";
        String id = "<html><body><b>Item Description :</b></body></html>";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*|text/html");
        //share.setAction(Intent.ACTION_SEND_MULTIPLE);
        share.putExtra(Intent.EXTRA_SUBJECT, "" + ImgProcode + "(" + ImgProName + ")");
        //share.putExtra(Intent.EXTRA_TEXT,Html.fromHtml("<html><body><b>Item Code :</b></body></html>")  + "(" + ImgProcode + ")" + "\n\n"+Html.fromHtml("<b>Item Name :\t</b>") + ImgProName+"\n\n"+Html.fromHtml("<b>Item Description :\t</b>")+Html.fromHtml(Html.fromHtml(s_description).toString()));

        share.putExtra(Intent.EXTRA_TEXT, "Item Code :  " + "(" + ImgProcode + ")" + "\n\nItem Name :  " + ImgProName + "\n\nItem Description :  " + Html.fromHtml(Html.fromHtml(s_description).toString()));
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        // share.putExtra(Intent.EXTRA_STREAM,)


        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(share, "Share via"));

        img_full.destroyDrawingCache();
        img_full.setDrawingCacheEnabled(false);
    }

    private boolean deleteDriectory(String path) {

        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) {
            String[] children = directory.list();
            for (int i = 0; i < children.length; i++) {
                new File(directory, children[i]).delete();
            }

            directory.delete();

            return true;
        }

        return false;
    }

    private void set_Image() {

        if (bean_product1.size() != 0) {

            for (int i = 0; i < bean_product1.size(); i++) {

                if (bean_product1.get(i).getPro_id().equalsIgnoreCase(pid)) {

                    Imgpath = bean_product1.get(i).getPro_image();
                    Imgproid = bean_product1.get(i).getPro_id();
                    ImgProcode = bean_product1.get(i).getPro_code();
                    ImgProName = bean_product1.get(i).getPro_name();
                    ImgProPrice = bean_product1.get(i).getPro_sellingprice();
                    shareImage();
                    //fetch_image_for_share(bean_product1.get(i).getPro_image());

                }
            }
        }
    }

    private void setLayout(ArrayList<String> str1) {
        // TODO Auto-generated method stub

        layout_imagegrid.removeAllViews();
        //Log.e("1", str1.size() + "");

        for (int ij = 0; ij < str1.size(); ij++) {

            //Log.e("2", ij + "");
            LinearLayout.LayoutParams par12 = new LinearLayout.LayoutParams(150, 150);
            LinearLayout lmain = new LinearLayout(BTLProduct_Detail.this);
            lmain.setLayoutParams(par12);
            lmain.setBackgroundResource(R.drawable.background_gradiant);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            lmain.setPadding(1, 2, 1, 2);

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    150, 150);

            LinearLayout l1 = new LinearLayout(BTLProduct_Detail.this);
            l1.setLayoutParams(par1);
            l1.setPadding(5, 0, 5, 0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(BTLProduct_Detail.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 0, 5, 0);

            final LinearLayout l2 = new LinearLayout(BTLProduct_Detail.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 0, 5, 0);
            l2.setVisibility(View.GONE);
            final ImageView img_a = new ImageView(BTLProduct_Detail.this);
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            par21.setMargins(5, 5, 5, 5);
            // imageloader.DisplayImage(Globals.server_link + "files/" + str1.get(ij), img_a);
            Picasso.with(getApplicationContext())
                    .load(Globals.server_link + "files/" + str1.get(ij))
                    .placeholder(R.drawable.btl_watermark)
                    .into(img_a);

            img_a.setId(ij);
            img_a.setTag(Globals.server_link + "files/" + str1.get(ij));

            img_a.setPadding(5, 0, 5, 0);

            img_a.setLayoutParams(par21);
          /*  if (ij == 0) {

                img_a.setImageResource(str1.get().getPro_Images());
                img_a.setId(ij);
                img_a.setTag(ij);

                img_a.setPadding(5, 0, 5, 0);

                img_a.setLayoutParams(par21);
            } else if (ij == 1) {
                img_a.setId(ij);
                img_a.setImageResource(R.drawable.boss2);
                img_a.setTag(ij);
                img_a.setPadding(5, 5, 5, 5);

                img_a.setLayoutParams(par21);
            }*//* else if (ij == 2) {
                img_a.setId(ij);
                img_a.setImageResource(R.drawable.boss3);
                img_a.setTag(ij);
                img_a.setPadding(5, 0, 5, 0);

                img_a.setLayoutParams(par21);
            } else if (ij == 3) {
                img_a.setId(ij);
                img_a.setImageResource(R.drawable.boss4);
                img_a.setTag(ij);
                img_a.setPadding(5, 0, 5, 0);

                img_a.setLayoutParams(par21);
   }*/
            final int Ij = ij;
            img_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    //Log.e("IMAGETAG",""+img_a.getTag().toString());
//                    imageloader.DisplayImage(img_a.getTag().toString(), img_full);
                    //Log.e("IMAGETAG", "" + img_a.getTag().toString());
                    // imageloader.DisplayImage(img_a.getTag().toString(), R.drawable.btl_noimage, img_full);
                    Picasso.with(getApplicationContext())
                            .load(img_a.getTag().toString())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_full);

                    selected_image_position = Ij;


                }
            });
            lmain.addView(img_a);
            layout_imagegrid.addView(lmain);


        }
    }



   /* public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {



            switch(position)
            {
                case 0:
                    return new Features();
                case 1:
                    return new Technical_Specifications();
                case 2:
                    return new Standard_Specifications();
                case 3:
                    return new More_Information();
            }

            return null;
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Features";
                case 1:
                    return "Technical Specifications";
                case 2:
                    return "Standard Specifications";
                case 4:
                    return "More Information";

            }
            return null;
        }
    }*/

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SetRefershDataProduct() {
        // TODO Auto-generated method stub
        bean_product1.clear();
        db = new DatabaseHandler(BTLProduct_Detail.this);

        ArrayList<Bean_Product> user_array_from_db = db.Get_Product();


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


            bean_product1.add(contact);


        }
        db.close();
    }

    private void setRefershDataCart() {
        // TODO Auto-generated method stub
        bean_productCart.clear();
        db = new DatabaseHandler(BTLProduct_Detail.this);

        ArrayList<Bean_ProductCart> user_array_from_db = db.Get_Product_Cart();


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
        db = new DatabaseHandler(BTLProduct_Detail.this);

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
        bean_productImages.clear();
        db = new DatabaseHandler(BTLProduct_Detail.this);

        ArrayList<Bean_ProductImage> user_array_from_db = db.Get_ProductImage();

        //Log.e("image count",user_array_from_db.size()+"");


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


            bean_productImages.add(contact);


        }

        //Log.e("total is imgs",bean_productImages.size()+"");
        db.close();
    }

    private void setRefershDataOption() {
        // TODO Auto-generated method stub
        bean_productCart.clear();
        db = new DatabaseHandler(BTLProduct_Detail.this);

        ArrayList<Bean_ProductOprtion> user_array_from_db = db.Get_Product_Option();


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


            bean_productOprtions.add(contact);


        }
        db.close();
    }

    private void setRefershDataStdspec() {
        // TODO Auto-generated method stub
        bean_productStdSpecs.clear();
        db = new DatabaseHandler(BTLProduct_Detail.this);

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

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(BTLProduct_Detail.this);

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
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        img_notification.setVisibility(View.GONE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
       /* db = new DatabaseHandler(BTLProduct_Detail.this);
        bean_cart = db.Get_Product_Cart();
        db.close();
        if (bean_cart.size() == 0) {
            txt.setVisibility(View.GONE);
            txt.setText("");
        } else {
            txt.setVisibility(View.VISIBLE);
            txt.setText(bean_cart.size() + "");
        }*/
        app = new AppPrefs(BTLProduct_Detail.this);
        String qun = app.getCart_QTy();

        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(BTLProduct_Detail.this);
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
            app = new AppPrefs(BTLProduct_Detail.this);
            app.setCart_QTy("");
        }

        app = new AppPrefs(BTLProduct_Detail.this);
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
                /*bean_productImages.clear();
                db = new DatabaseHandler(BTLProduct_Detail.this);
                db.Delete_ALL_table();
                db.close();
                app = new AppPrefs(BTLProduct_Detail.this);
                app.setwishid("");
                if (app.getRef_Detail().toString().equalsIgnoreCase("ProductList")) {
                    Intent i = new Intent(BTLProduct_Detail.this, Product_List.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getRef_Detail().toString().equalsIgnoreCase("Search")) {
                    Intent i = new Intent(BTLProduct_Detail.this, Search.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getRef_Detail().toString().equalsIgnoreCase("wish")) {
                    Intent i = new Intent(BTLProduct_Detail.this, Btl_WishList.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (app.getRef_Detail().toString().equalsIgnoreCase("shopping")) {
                    Intent i = new Intent(BTLProduct_Detail.this, Shopping_Product_view.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }*/

                onBackPressed();
            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(BTLProduct_Detail.this);
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(BTLProduct_Detail.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(BTLProduct_Detail.this);
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(BTLProduct_Detail.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                app = new AppPrefs(BTLProduct_Detail.this);
                app.setUser_notification("product_detail");
                Intent i = new Intent(BTLProduct_Detail.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(BTLProduct_Detail.this);
                app.setUser_notification("product_detail");
                Intent i = new Intent(BTLProduct_Detail.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onimg_prodetail_wishlistressed() {
        // TODO Auto-generated method stub
        bean_productImages.clear();
        db = new DatabaseHandler(BTLProduct_Detail.this);
        db.Delete_ALL_table();
        db.close();
        app = new AppPrefs(BTLProduct_Detail.this);
        app.setwishid("");
        Intent i = new Intent(BTLProduct_Detail.this, Product_List.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    public void webview_popup(String html_code) {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialogDesc.setContentView(R.layout.webview_popup);
        dialogDesc.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);
        dialogDesc.setCancelable(true);
        dialogDesc.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        WebView webview_product_details = (WebView) dialogDesc.findViewById(R.id.webview_product_details);
        webview_product_details.getSettings().setJavaScriptEnabled(true);
        ImageView img_cancel = (ImageView) dialogDesc.findViewById(R.id.btn_cancel);
        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDesc.dismiss();
            }
        });

        webview_product_details.loadData("<HTML><body> " + html_code + " </body></HTML>", "text/html", null);

        dialogDesc.show();

    }

    public void webview_technical(ArrayList<Bean_texhnicalImages> str) {

        Intent i = new Intent(BTLProduct_Detail.this, Technical_Gallery.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("FILES_TO_SEND", str);
        i.putExtra("productCode", bean_product1.get(0).getPro_code());
        i.putExtra("productName", bean_product1.get(0).getPro_name());
        startActivity(i);


    }

    public void SetRefershDataProductDesc() {
        // TODO Auto-generated method stub
        bean_desc_db.clear();
        db = new DatabaseHandler(BTLProduct_Detail.this);

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

    public void onBackPressed() {
        bean_productImages.clear();
        db = new DatabaseHandler(BTLProduct_Detail.this);
        db.Delete_ALL_table();
        db.close();
        app = new AppPrefs(BTLProduct_Detail.this);
        app.setwishid("");
        if (app.getRef_Detail().equalsIgnoreCase("ProductList")) {
            Intent i = new Intent(BTLProduct_Detail.this, Product_List.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getRef_Detail().equalsIgnoreCase("Search")) {
            Intent i = new Intent(BTLProduct_Detail.this, Search.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getRef_Detail().equalsIgnoreCase("wish")) {
            Intent i = new Intent(BTLProduct_Detail.this, Btl_WishList.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (app.getRef_Detail().equalsIgnoreCase("shopping")) {
            Intent i = new Intent(BTLProduct_Detail.this, Shopping_Product_view.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            finish();
        }
    }

    private void Setdata() {

        if (whereToBuyVisibility) {
            btn_wherebuy.setVisibility(View.VISIBLE);
        } else {
            btn_wherebuy.setVisibility(View.GONE);
        }

        if (WishList.size() != 0) {

            String st = WishList.toString();
            //Log.e("111111111", st);
            if (WishList.contains(pid)) {

                app = new AppPrefs(BTLProduct_Detail.this);
                app.setwishid("1");

            } else {
                app = new AppPrefs(BTLProduct_Detail.this);
                app.setwishid("2");

            }
        }

      /*  wishid=app.getwishid();
        //Log.e("wishid", "" + wishid);
        //Log.e("wishid1111",""+wishid.toString());
        if (wishid.toString().equalsIgnoreCase("1")) {

            img_prodetail_wishlist.setImageResource(R.drawable.whishlist_fill);
            img_prodetail_wishlist.setTag(R.drawable.whishlist_fill);
            //Log.e("wishid1", "" + wishid.toString());
        }
        else if(wishid.toString().equalsIgnoreCase("2"))
        {
            img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
            img_prodetail_wishlist.setTag(R.drawable.whishlist);
            //Log.e("wishid2", "" + wishid.toString());
        }*/

        setRefershData();
        if (user_data.size() != 0) {


            setRefershData();
            if (user_data.size() != 0) {
                for (int i = 0; i < user_data.size(); i++) {

                    rolee = user_data.get(i).getUser_type().toString();

                }

            } else {
                user_id_main = "";
            }

            if (rolee.equals(C.ADMIN) || rolee.equalsIgnoreCase("6") || rolee.equalsIgnoreCase("7")) {
                img_prodetail_wishlist.setVisibility(View.GONE);
                img_prodetail_shoppinglist.setVisibility(View.GONE);
            } else {
                img_prodetail_wishlist.setVisibility(View.VISIBLE);
                img_prodetail_shoppinglist.setVisibility(View.VISIBLE);
            }
            if (WishList.size() != 0) {

                String st = WishList.toString();
                //Log.e("111111111", st);
                if (WishList.contains(pid)) {

                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist_fill);
                    img_prodetail_wishlist.setTag(R.drawable.whishlist_fill);

                } else {
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
                    img_prodetail_wishlist.setTag(R.drawable.whishlist);

                }
            } else if (WishList.size() == 0) {
                img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
                img_prodetail_wishlist.setTag(R.drawable.whishlist);

            }

            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(getApplicationContext());
                    role_id = app.getSubSalesId();
                    user_id_main = app.getSalesPersonId();
                }

            }

        } else {

            img_prodetail_wishlist.setVisibility(View.GONE);
            img_prodetail_shoppinglist.setVisibility(View.GONE);
        }
        if (bean_product1.size() != 0) {

            for (ik = 0; ik < bean_product1.size(); ik++) {

                if (bean_product1.get(ik).getPro_id().equalsIgnoreCase(pid)) {

                    CategoryID = bean_product1.get(ik).getPro_cat_id();
                    tv_product_name.setText(bean_product1.get(ik).getPro_name());
                    //Log.e("name", pid + bean_product1.get(i).getPro_name());
                    tv_product_code.setText("(" + bean_product1.get(ik).getPro_code() + ")");
                    //Log.e("code", pid + bean_product1.get(i).getPro_code());
                    mrp = bean_product1.get(ik).getPro_mrp();

                    float mrpPrice = mrp.isEmpty() ? 0 : Float.parseFloat(mrp);


                    tv_product_mrp.setText("" + bean_product1.get(ik).getPro_mrp());
                    //Log.e("mrp", pid + bean_product1.get(i).getPro_mrp());
                    sellingprice = bean_product1.get(ik).getPro_sellingprice();
                    tv_product_selling_price.setText("" + bean_product1.get(ik).getPro_sellingprice());
                    /*double off= Double.parseDouble(bean_product1.get(ik).getPro_mrp()) - Double.parseDouble(bean_product1.get(ik).getPro_sellingprice());
                    double offa = off * 100;
                    double o = offa / Double.parseDouble(bean_product1.get(ik).getPro_mrp());
                    int a = (int)o;*/

/*
                    float percent1 = 100 - (Float.parseFloat(bean_product1.get(ik).getPro_sellingprice()) * 100 / Float.parseFloat(bean_product1.get(ik).getPro_mrp()));
*/

                    double percent1 = (Double.parseDouble(bean_product1.get(ik).getPro_mrp()) - Double.parseDouble(bean_product1.get(ik).getPro_sellingprice())) * 100 / Double.parseDouble(bean_product1.get(ik).getPro_mrp());


                    off_tag.setText(String.format("%.1f", percent1) + "% OFF");

                    //Log.e("sellngprice", pid + bean_product1.get(i).getPro_sellingprice());
                    tv_packof.setText(bean_product1.get(ik).getPro_label());
                    //Log.e("label", pid + bean_product1.get(i).getPro_label());
                    text_desc.setText(Html.fromHtml(bean_product1.get(ik).getPro_shortdesc()));
                    //Log.e("desc", pid + bean_product1.get(i).getPro_shortdesc());

                    // imageloader.DisplayImage(Globals.server_link + "files/" + bean_product1.get(i).getPro_image(), img_full);
                    Picasso.with(getApplicationContext())
                            .load(Globals.server_link + "files/" + bean_product1.get(ik).getPro_image())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_full);

                    //Log.e("mainproimg", pid + bean_product1.get(i).getPro_image());
                    // String moreInformation="http://app.nivida.in/boss"+bean_product1.get(i).getPro_moreinfo();
                    // //Log.e("more_information", pid + bean_product1.get(i).getPro_moreinfo());
                    // //Log.e("mainproimg", pid + bean_product1.get(i).getPro_image());
                    // more_Info_text.setText(moreInformation.toString());
                    l_mrprs.setVisibility(View.VISIBLE);
                    l_sellrs.setVisibility(View.VISIBLE);
                    txt_mrp.setVisibility(View.VISIBLE);
                    txt_sell.setVisibility(View.VISIBLE);
                    //Log.e("111111", "" + tv_product_mrp.getText().toString());
                    //Log.e("111111", "" + tv_product_selling_price.getText().toString());
                    double mrp = Double.parseDouble(tv_product_mrp.getText().toString());
                    double sellingprice = Double.parseDouble(tv_product_selling_price.getText().toString());
                    if (mrp > sellingprice) {
                        l_mrprs.setVisibility(View.VISIBLE);
                        tv_product_mrp.setVisibility(View.VISIBLE);
                        txt_mrp.setVisibility(View.VISIBLE);
                        //  off_tag.setVisibility(View.VISIBLE);
                        //Log.e("22222", "22222");
                        tv_product_mrp.setPaintFlags(tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    } else {
                        tv_product_mrp.setVisibility(View.GONE);
                        txt_mrp.setVisibility(View.GONE);
                        l_mrprs.setVisibility(View.GONE);
                        off_tag.setVisibility(View.GONE);
                        //Log.e("33333", "33333");
                    }
                    //Log.e("111111", "11111");
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
                        l_mrprs.setVisibility(View.GONE);
                        l_sellrs.setVisibility(View.GONE);
                        txt_mrp.setVisibility(View.GONE);
                        txt_sell.setVisibility(View.GONE);
                        off_tag.setVisibility(View.GONE);


                    } else {
                        //Log.e("111111", "111111");
                        l_mrprs.setVisibility(View.VISIBLE);
                        l_sellrs.setVisibility(View.VISIBLE);
                        txt_mrp.setVisibility(View.VISIBLE);
                        txt_sell.setVisibility(View.VISIBLE);
                        //  off_tag.setVisibility(View.VISIBLE);

                        //Log.e("111111", "" + tv_product_mrp.getText().toString());
                        //Log.e("111111", "" + tv_product_selling_price.getText().toString());
                        double mrp1 = Double.parseDouble(tv_product_mrp.getText().toString());
                        double sellingprice1 = Double.parseDouble(tv_product_selling_price.getText().toString());
                        if (mrp1 > sellingprice1) {
                            l_mrprs.setVisibility(View.VISIBLE);
                            tv_product_mrp.setVisibility(View.VISIBLE);
                            txt_mrp.setVisibility(View.VISIBLE);
                            //  off_tag.setVisibility(View.VISIBLE);
                            //Log.e("22222", "22222");
                            tv_product_mrp.setPaintFlags(tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        } else {
                            tv_product_mrp.setVisibility(View.GONE);
                            txt_mrp.setVisibility(View.GONE);
                            l_mrprs.setVisibility(View.GONE);
                            off_tag.setVisibility(View.GONE);
                            //Log.e("33333", "33333");
                        }
                    }

                    //Log.e("44444", "444444");
                    img_offer.setTag(bean_product1.get(ik).getScheme());
                    if (bean_product1.get(ik).getScheme().equalsIgnoreCase("") || bean_product1.get(ik).getScheme().equalsIgnoreCase(null) || bean_product1.get(ik).getScheme().equalsIgnoreCase("null")) {

                        img_offer.setVisibility(View.GONE);
                    } else {

                        img_offer.setVisibility(View.VISIBLE);
                        DisplayMetrics metrics = getResources().getDisplayMetrics();
                        int width = metrics.widthPixels;
                        int height = metrics.heightPixels;

                        dialogOffer.setContentView(R.layout.scheme_layout);
                        dialogOffer.getWindow().setLayout((6 * width) / 7, (4 * height) / 8);
                        dialogOffer.setCancelable(true);
                        dialogOffer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        ImageView btn_cancel = (ImageView) dialogOffer.findViewById(R.id.btn_cancel);
                        bean_S_data.clear();

                        Log.e("size", "" + bean_Schme_data.size());


                        for (int ij = 0; ij < bean_Schme_data.size(); ij++) {

                            if (bean_Schme_data.get(ij).getSchme_prod_id().equalsIgnoreCase(pid)) {
                                Bean_schemeData beans = new Bean_schemeData();
                                beans.setSchme_id(bean_Schme_data.get(ij).getSchme_id());
                                Log.e("ID", "" + pid);
                                Log.e("name", "" + bean_Schme_data.get(ij).getSchme_name());
                                beans.setSchme_name(bean_Schme_data.get(ij).getSchme_name());
                                beans.setCategory_id(bean_Schme_data.get(ij).getCategory_id());
                                beans.setSchme_qty(bean_Schme_data.get(ij).getSchme_qty());
                                beans.setSchme_buy_prod_id(bean_Schme_data.get(ij).getSchme_buy_prod_id());
                                beans.setSchme_prod_id(bean_Schme_data.get(ij).getSchme_prod_id());

                                bean_S_data.add(beans);


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

                        final ExpandableSchemeAdapter schemeAdapter = new ExpandableSchemeAdapter(getApplicationContext(), schemeHeaders, schemeChildList);
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

                                            app = new AppPrefs(BTLProduct_Detail.this);
                                            role_id = app.getSubSalesId().toString();
                                            u_id = app.getSalesPersonId().toString();
                                        } else {
                                            u_id = owner_id;
                                        }


                                    }


                                    // product_id = tv_pop_pname.getTag().toString();
                                    List<NameValuePair> para = new ArrayList<NameValuePair>();
                                    para.add(new BasicNameValuePair("product_id", pid));
                                    para.add(new BasicNameValuePair("owner_id", owner_id));
                                    para.add(new BasicNameValuePair("user_id", u_id));
                                    Log.e("111111111", "" + pid);
                                    Log.e("222222222", "" + owner_id);
                                    Log.e("333333333", "" + u_id);
                                    isNotDone = true;
                                    new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                    while (isNotDone) {

                                    }
                                    String json = jsonData; //GetProductDetailByQty(para);
                                    jsonData = "";

                                    Globals.generateNoteOnSD(getApplicationContext(), "->Return JSON" + json);

                                    try {


                                        //System.out.println(json);

                                        if (json == null
                                                || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                            Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERRER", getLayoutInflater());
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
                                    product_id = pid;
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
                                    jsonData = "";

                                    //new check_schme().execute();
                                    try {


                                        //System.out.println(json);

                                        if (jsons == null
                                                || (jsons.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                            Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERRER", getLayoutInflater());
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
                                        if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("1")) {

                                            jarray_cart = new JSONArray();

                                            String getq = bean_schme.get(0).getGet_qty();
                                            String buyq = bean_schme.get(0).getBuy_qty();
                                            String maxq = bean_schme.get(0).getMax_qty();

                                            double getqu = Double.parseDouble(getq);
                                            double buyqu = Double.parseDouble(buyq);
                                            //double maxqu = Double.parseDouble(maxq);
                                            int qu = Integer.parseInt(qty);


                                            String sell = tv_product_selling_price.getText().toString();

                                            double se = Double.parseDouble(tv_product_selling_price.getText().toString()) * buyqu;

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
                                                    jobject.put("product_id", pid);
                                                    jobject.put("category_id", CategoryID);
                                                    jobject.put("name", tv_product_name.getText().toString());

                                                    String newString = tv_product_code.getText().toString().trim().replace("(", "");
                                                    String aString = newString.trim().replace(")", "");
                                                    jobject.put("pro_code", aString.trim());
                                                    jobject.put("quantity", String.valueOf(fqu));
                                                    jobject.put("mrp", tv_product_mrp.getText().toString());
                                                    jobject.put("selling_price", sell);
                                                    jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                                    jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                                    jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                                    jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                                    double f = fqu * Double.parseDouble(sell);
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

                                            Log.e("kkkkkkk", "" + kkk);
                                            if (kkk == 1) {

                                                new Edit_Product().execute();
                                                dialogOffer.dismiss();
                                                if (dialog.isShowing()) {
                                                    dialog.dismiss();
                                                }
                                            } else {

                                                new Add_Product().execute();
                                                dialogOffer.dismiss();
                                                if (dialog.isShowing()) {
                                                    dialog.dismiss();
                                                }
                                            }


                                        } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {

                                            jarray_cart = new JSONArray();
                                            try {
                                                JSONObject jobject = new JSONObject();

                                                jobject.put("user_id", u_id);
                                                jobject.put("role_id", role_id);
                                                jobject.put("owner_id", owner_id);
                                                jobject.put("product_id", pid);
                                                jobject.put("category_id", CategoryID);
                                                jobject.put("name", tv_product_name.getText().toString());
                                                String newString = tv_product_code.getText().toString().trim().replace("(", "");
                                                String aString = newString.toString().trim().replace(")", "");
                                                jobject.put("pro_code", aString.toString().trim());
                                                jobject.put("quantity", qty);
                                                jobject.put("mrp", tv_product_mrp.getText().toString());
                                                jobject.put("selling_price", tv_product_selling_price.getText().toString());
                                                jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                                jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                                jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                                double f = Integer.parseInt(qty) * Double.parseDouble(tv_product_selling_price.getText().toString());
                                                double w1 = round(f, 2);
                                                String str = String.format("%.2f", w1);
                                                jobject.put("item_total", str);
                                                jobject.put("pro_scheme", " ");
                                                jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                                jobject.put("scheme_id", " ");
                                                jobject.put("scheme_title", " ");
                                                jobject.put("scheme_pack_id", " ");
                                                if (list_of_images.size() == 0) {
                                                    jobject.put("prod_img", bean_product1.get(0).getPro_image());
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
                                                jobject.put("category_id", bean_product_schme.get(0).getPro_cat_id());
                                                jobject.put("name", bean_product_schme.get(0).getPro_name());
                                                jobject.put("pro_code", bean_product_schme.get(0).getPro_code());
                                                jobject.put("quantity", String.valueOf(b));
                                                jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                                //Log.e("D1D1",""+bean_product_schme.get(0).getPro_mrp());
                                                jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                                //Log.e("E1E1",""+bean_product_schme.get(0).getPro_sellingprice());
                                                jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id().toString());
                                                jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name().toString());
                                                jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name());
                                                jobject.put("item_total", "0");
                                                jobject.put("pro_scheme", bean_product1.get(0).getPro_id().toString());
                                                jobject.put("pack_of", bean_product1.get(0).getPro_label());
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

                                            Log.e("kkkkkkk", "" + kkk);
                                            if (kkk == 1) {

                                                new Edit_Product().execute();
                                                dialogOffer.dismiss();
                                                if (dialog.isShowing()) {
                                                    dialog.dismiss();
                                                }
                                            } else {

                                                new Add_Product().execute();
                                                dialogOffer.dismiss();
                                                if (dialog.isShowing()) {
                                                    dialog.dismiss();
                                                }
                                            }

                                        } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("3")) {


                                            String getq = bean_schme.get(0).getGet_qty();
                                            String buyq = bean_schme.get(0).getBuy_qty();
                                            String maxq = bean_schme.get(0).getMax_qty();

                                            double getqu = Double.parseDouble(getq);
                                            double buyqu = Double.parseDouble(buyq);
                                            //double maxqu = Double.parseDouble(maxq);
                                            int qu = Integer.parseInt(qty);


                                            String sell = tv_product_selling_price.getText().toString();

                                            String disc = bean_schme.get(0).getDisc_per().toString();

                                            double se = Double.parseDouble(tv_product_selling_price.getText().toString()) * Double.parseDouble(bean_schme.get(0).getDisc_per().toString());

                                            double se1 = se / 100;

                                            double fse = Double.parseDouble(tv_product_selling_price.getText().toString()) - se1;
                                            double w1 = round(fse, 2);
                                            sell = String.format("%.2f", w1);
                                            jarray_cart = new JSONArray();

                                            for (int i = 0; i < 1; i++) {
                                                try {
                                                    JSONObject jobject = new JSONObject();

                                                    jobject.put("user_id", u_id);
                                                    jobject.put("role_id", role_id);
                                                    jobject.put("owner_id", owner_id);
                                                    jobject.put("product_id", pid);
                                                    jobject.put("category_id", CategoryID);
                                                    jobject.put("name", tv_product_name.getText().toString());
                                                    String newString = tv_product_code.getText().toString().trim().replace("(", "");
                                                    String aString = newString.toString().trim().replace(")", "");
                                                    jobject.put("pro_code", aString.toString().trim());
                                                    jobject.put("quantity", qty);
                                                    jobject.put("mrp", tv_product_mrp.getText().toString());
                                                    jobject.put("selling_price", sell);
                                                    jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id().toString());
                                                    jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name().toString());
                                                    jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id().toString());
                                                    jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name().toString());
                                                    double f = Double.parseDouble(qty) * Double.parseDouble(sell);
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

                                            Log.e("kkkkkkk", "" + kkk);
                                            if (kkk == 1) {

                                                new Edit_Product().execute();
                                                dialogOffer.dismiss();
                                                if (dialog.isShowing()) {
                                                    dialog.dismiss();
                                                }
                                            } else {

                                                new Add_Product().execute();
                                                dialogOffer.dismiss();
                                                if (dialog.isShowing()) {
                                                    dialog.dismiss();
                                                }
                                            }
                                        }
                                    }

                                } else {

                                    Globals.CustomToast(BTLProduct_Detail.this, "Please Login First", getLayoutInflater());
                                    user_id_main = "";

                                }
                                dialogOffer.dismiss();
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
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

                        if (mrpPrice > 0) {
                            dialogOffer.show();
                        }
                    }

                    if (mrpPrice <= 0) {
                        detail_price.setVisibility(View.GONE);
                        btn_buyonline_product_detail.setVisibility(View.GONE);
                        img_offer.setVisibility(View.GONE);
                        btn_enquiry.setVisibility(View.VISIBLE);
                        btn_enquiry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), NonMRPProductEnquiry.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("productName", bean_product1.get(0).getPro_name());
                                intent.putExtra("productID", bean_product1.get(0).getPro_id());
                                startActivity(intent);
                            }
                        });
                    }
                } else {

                }

            }
        } else {
            //  Globals.CustomToast(BTLProduct_Detail.this,"No Data",getLayoutInflater());

        }
        img_offer.setOnClickListener(new View.OnClickListener() {
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

                Log.e("size", "" + bean_Schme_data.size());


                for (int i = 0; i < bean_Schme_data.size(); i++) {

                    if (bean_Schme_data.get(i).getSchme_prod_id().equalsIgnoreCase(pid)) {
                        Bean_schemeData beans = new Bean_schemeData();
                        beans.setSchme_id(bean_Schme_data.get(i).getSchme_id());
                        Log.e("ID", "" + pid);
                        Log.e("name", "" + bean_Schme_data.get(i).getSchme_name());
                        beans.setSchme_name(bean_Schme_data.get(i).getSchme_name());
                        beans.setCategory_id(bean_Schme_data.get(i).getCategory_id());
                        beans.setSchme_qty(bean_Schme_data.get(i).getSchme_qty());
                        beans.setSchme_buy_prod_id(bean_Schme_data.get(i).getSchme_buy_prod_id());
                        beans.setSchme_prod_id(bean_Schme_data.get(i).getSchme_prod_id());

                        bean_S_data.add(beans);
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

                final ExpandableSchemeAdapter schemeAdapter = new ExpandableSchemeAdapter(getApplicationContext(), schemeHeaders, schemeChildList);
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

                                    app = new AppPrefs(BTLProduct_Detail.this);
                                    role_id = app.getSubSalesId();
                                    user_id_main = app.getSalesPersonId();
                                } else {
                                    user_id_main = owner_id;
                                }


                            }


                            // product_id = tv_pop_pname.getTag().toString();
                            List<NameValuePair> para = new ArrayList<NameValuePair>();
                            para.add(new BasicNameValuePair("product_id", pid));
                            para.add(new BasicNameValuePair("owner_id", owner_id));
                            para.add(new BasicNameValuePair("user_id", user_id_main));
                            Log.e("111111111", "" + pid);
                            Log.e("222222222", "" + owner_id);
                            Log.e("333333333", "" + u_id);
                            Globals.generateNoteOnSD(getApplicationContext(), "Line 5176");

                            isNotDone = true;
                            new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            while (isNotDone) {

                            }
                            isNotDone = true;
                            Globals.generateNoteOnSD(getApplicationContext(), "Return JSON 5184");

                            String json = jsonData; //GetProductDetailByQty(para);
                            jsonData = "";

                            try {


                                //System.out.println(json);

                                if (json == null || json.isEmpty()) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                    Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERROR", getLayoutInflater());
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
                                Globals.generateNoteOnSD(getApplicationContext(), j.getMessage());
                                //Log.e("json exce",j.getMessage());
                            }
                            Log.e("kkk", "" + kkk);
                            product_id = pid;
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
                            isNotDone = false;

                            Globals.generateNoteOnSD(getApplicationContext(), "Return JSON 5259 ->" + jsonData);

                            String jsons = jsonData; //GetProductDetailByCode(params);
                            jsonData = "";

                            //new check_schme().execute();
                            try {


                                //System.out.println(json);

                                if (jsons == null || jsons.isEmpty()) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                    Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERROR", getLayoutInflater());
                                    // loadingView.dismiss();

                                } else {
                                    JSONObject jObj = new JSONObject(jsons);

                                    boolean date = jObj.getBoolean("status");

                                    if (!date) {
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
                                Globals.generateNoteOnSD(getApplicationContext(), j.getMessage());
                                //Log.e("json exce",j.getMessage());
                            }

                            Globals.generateNoteOnSD(getApplicationContext(), "Line Exceuted Till 5404 ->" + bean_product_schme.size());
                            //Log.e("45454545",""+bean_product_schme.size());
                            if (bean_product_schme.size() == 0) {
                                Log.e("1111111111111", "" + qty);
                                dialogOffer.dismiss();
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
                                    //double maxqu = Double.parseDouble(maxq);
                                    int qu = Integer.parseInt(qty);


                                    String sell = tv_product_selling_price.getText().toString();

                                    double se = Double.parseDouble(tv_product_selling_price.getText().toString()) * buyqu;

                                    double se1 = buyqu + getqu;

                                    double fse = se / se1;
                                    double w = round(fse, 2);
                                    sell = String.format("%.2f", w);
                                    //sell = String.valueOf(fse);

                                    int fqu = qu + (int) getqu;


                                    for (int i = 0; i < 1; i++) {
                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", user_id_main);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", pid);
                                            jobject.put("category_id", CategoryID);
                                            jobject.put("name", tv_product_name.getText().toString());

                                            String newString = tv_product_code.getText().toString().trim().replace("(", "");
                                            String aString = newString.toString().trim().replace(")", "");
                                            jobject.put("pro_code", aString.toString().trim());
                                            jobject.put("quantity", String.valueOf(fqu));
                                            jobject.put("mrp", tv_product_mrp.getText().toString());
                                            jobject.put("selling_price", sell);
                                            jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                            jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                            jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                            jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                            double f = fqu * Double.parseDouble(sell);
                                            double w1 = round(f, 2);
                                            String str = String.format("%.2f", w1);
                                            jobject.put("item_total", str);
                                            jobject.put("pro_scheme", " ");
                                            jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                            jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                            jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                            jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                            if (list_of_images.size() == 0) {
                                                jobject.put("prod_img", bean_product1.get(0).getPro_image());
                                                // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                            } else {
                                                jobject.put("prod_img", list_of_images.get(0));
                                                //   bean.setPro_Images(list_of_images.get(0).toString());
                                            }


                                            jarray_cart.put(jobject);
                                        } catch (JSONException e) {
                                            Globals.generateNoteOnSD(getApplicationContext(), "5481 ->" + e.getMessage());
                                        }


                                    }
                                    array_value.clear();

                                    Log.e("kkkkkkk", "" + kkk);
                                    if (kkk == 1) {

                                        Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5491");
                                        new Edit_Product().execute();
                                        dialogOffer.dismiss();
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                    } else {
                                        Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5495");
                                        new Add_Product().execute();
                                        dialogOffer.dismiss();
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                    }


                                } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {

                                    jarray_cart = new JSONArray();
                                    try {
                                        JSONObject jobject = new JSONObject();

                                        jobject.put("user_id", user_id_main);
                                        jobject.put("role_id", role_id);
                                        jobject.put("owner_id", owner_id);
                                        jobject.put("product_id", pid);
                                        jobject.put("category_id", CategoryID);
                                        jobject.put("name", tv_product_name.getText().toString());
                                        String newString = tv_product_code.getText().toString().trim().replace("(", "");
                                        String aString = newString.trim().replace(")", "");
                                        jobject.put("pro_code", aString.trim());
                                        jobject.put("quantity", qty);
                                        jobject.put("mrp", tv_product_mrp.getText().toString());
                                        jobject.put("selling_price", tv_product_selling_price.getText().toString());
                                        jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                        jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                        jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                        jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                        double f = Integer.parseInt(qty) * Double.parseDouble(tv_product_selling_price.getText().toString());
                                        double w1 = round(f, 2);
                                        String str = String.format("%.2f", w1);
                                        jobject.put("item_total", str);
                                        jobject.put("pro_scheme", " ");
                                        jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                        jobject.put("scheme_id", " ");
                                        jobject.put("scheme_title", " ");
                                        jobject.put("scheme_pack_id", " ");
                                        if (list_of_images.size() == 0) {
                                            jobject.put("prod_img", bean_product1.get(0).getPro_image());
                                            // bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                        } else {
                                            jobject.put("prod_img", list_of_images.get(0));
                                            //   bean.setPro_Images(list_of_images.get(0).toString());
                                        }


                                        jarray_cart.put(jobject);
                                    } catch (JSONException e) {
                                        Globals.generateNoteOnSD(getApplicationContext(), "5542 -> " + e.getMessage());
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

                                        jobject.put("user_id", user_id_main);
                                        jobject.put("role_id", role_id);
                                        jobject.put("owner_id", owner_id);
                                        jobject.put("product_id", bean_product_schme.get(0).getPro_id());
                                        jobject.put("category_id", bean_product_schme.get(0).getPro_cat_id());
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
                                        jobject.put("pro_scheme", bean_product1.get(0).getPro_id());
                                        jobject.put("pack_of", bean_product1.get(0).getPro_label());
                                        jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                        //Log.e("C1C1",""+bean_schme.get(0).getScheme_id());
                                        jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                        jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                        jobject.put("prod_img", bean_product_schme.get(0).getPro_image());


                                        jarray_cart.put(jobject);
                                    } catch (JSONException e) {
                                        Globals.generateNoteOnSD(getApplicationContext(), "5596 ->" + e.getMessage());
                                    }


                                    // db.Add_Product_cart_scheme(bean_s);

                                    array_value.clear();

                                    Log.e("kkkkkkk", "" + kkk);
                                    if (kkk == 1) {
                                        Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5607");
                                        new Edit_Product().execute();
                                        dialogOffer.dismiss();
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                    } else {
                                        Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5611");
                                        new Add_Product().execute();
                                        dialogOffer.dismiss();
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                    }

                                } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("3")) {


                                    String getq = bean_schme.get(0).getGet_qty();
                                    String buyq = bean_schme.get(0).getBuy_qty();
                                    String maxq = bean_schme.get(0).getMax_qty();

                                    double getqu = Double.parseDouble(getq);
                                    double buyqu = Double.parseDouble(buyq);
                                    //double maxqu = Double.parseDouble(maxq);
                                    int qu = Integer.parseInt(qty);


                                    String sell = tv_product_selling_price.getText().toString();

                                    String disc = bean_schme.get(0).getDisc_per();

                                    double se = Double.parseDouble(tv_product_selling_price.getText().toString()) * Double.parseDouble(bean_schme.get(0).getDisc_per());

                                    double se1 = se / 100;

                                    double fse = Double.parseDouble(tv_product_selling_price.getText().toString()) - se1;
                                    double w1 = round(fse, 2);
                                    sell = String.format("%.2f", w1);
                                    jarray_cart = new JSONArray();

                                    for (int i = 0; i < 1; i++) {
                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", user_id_main);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", pid);
                                            jobject.put("category_id", CategoryID);
                                            jobject.put("name", tv_product_name.getText().toString());
                                            String newString = tv_product_code.getText().toString().trim().replace("(", "");
                                            String aString = newString.trim().replace(")", "");
                                            jobject.put("pro_code", aString.trim());
                                            jobject.put("quantity", qty);
                                            jobject.put("mrp", tv_product_mrp.getText().toString());
                                            jobject.put("selling_price", sell);
                                            jobject.put("option_id", bean_productOprtions.get(0).getPro_Option_id());
                                            jobject.put("option_name", bean_productOprtions.get(0).getPro_Option_name());
                                            jobject.put("option_value_id", bean_productOprtions.get(0).getPro_Option_value_id());
                                            jobject.put("option_value_name", bean_productOprtions.get(0).getPro_Option_value_name());
                                            double f = Double.parseDouble(qty) * Double.parseDouble(sell);
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
                                            Globals.generateNoteOnSD(getApplicationContext(), "5683 ->" + e.getMessage());
                                        }


                                    }
                                    array_value.clear();

                                    Log.e("kkkkkkk", "" + kkk);
                                    if (kkk == 1) {
                                        Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5692");
                                        new Edit_Product().execute();
                                        dialogOffer.dismiss();
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                    } else {
                                        Globals.generateNoteOnSD(getApplicationContext(), "Line Ex 5696");
                                        new Add_Product().execute();
                                        dialogOffer.dismiss();
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                    }
                                }
                            }

                        } else {
                            dialogOffer.dismiss();
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Globals.CustomToast(BTLProduct_Detail.this, "Please Login First", getLayoutInflater());
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
        if (bean_desc1.size() != 0) {


            for (int i = 0; i < bean_desc1.size(); i++) {

                //Toast.makeText(BTLProduct_Detail.this,""+bean_desc_db.get(i).getPro_id().toString() + "-"+pid,Toast.LENGTH_LONG).show();

                if (bean_desc1.get(i).getPro_id().equalsIgnoreCase(pid)) {

                    //Log.e("111111111111111111", "1111111111111111111");

                    // more_Info_text.setText(moreInformation.toString());

                    s_description = bean_desc1.get(i).getDescription();
                    //Log.e("desc", "" + s_description);
                    s_specification = bean_desc1.get(i).getSpecification();
                    //Log.e("spec", "" + s_specification);
                /*    s_technical = bean_desc1.get(i).getTechnical().toString();
                    //Log.e("tech",""+s_technical);*/
                    s_catalogs = bean_desc1.get(i).getCatalogs();
                    //Log.e("cate", "" + s_catalogs);
                    s_guarantee = bean_desc1.get(i).getGuarantee();
                    //Log.e("guar", "" + s_guarantee);
                    s_videos = bean_desc1.get(i).getVideos();
                    //Log.e("video", "" + s_videos);
                    s_general = bean_desc1.get(i).getGeneral();
                    //Log.e("gene", "" + s_general);

                }

            }

            if (s_description.equalsIgnoreCase("") || s_description.equalsIgnoreCase("null")) {
                l_description_main.setVisibility(View.GONE);
                //Log.e("s_description", "" + s_description.toString());
            } else {
                l_description_main.setVisibility(View.VISIBLE);
                //Log.e("s_description", "" + s_description.toString());

            }

            if (s_specification.equalsIgnoreCase("") || s_specification.equalsIgnoreCase("null")) {
                l_Feature_main.setVisibility(View.GONE);
                //Log.e("s_specification", "" + s_specification.toString());

            } else {
                l_Feature_main.setVisibility(View.VISIBLE);
                //Log.e("s_specification", "" + s_specification.toString());
            }

            if (bean_technical.size() == 0) {
                l_videos_main.setVisibility(View.GONE);
                //Log.e("s_technical", "" + s_technical.toString());
            } else {
                l_videos_main.setVisibility(View.VISIBLE);
                //Log.e("s_technical", "" + s_technical.toString());
            }

            if (s_catalogs.equalsIgnoreCase("") || s_catalogs.equalsIgnoreCase("null")) {
                l_technical_main.setVisibility(View.GONE);
                //Log.e("s_catalogs", "" + s_catalogs.toString());
            } else {
                l_technical_main.setVisibility(View.VISIBLE);
                //Log.e("s_catalogs", "" + s_catalogs.toString());
            }

            if (s_guarantee.equalsIgnoreCase("") || s_guarantee.equalsIgnoreCase("null")) {
                l_standard_technical_main.setVisibility(View.GONE);
                //Log.e("s_guarantee", "" + s_guarantee.toString());
            } else {
                l_standard_technical_main.setVisibility(View.VISIBLE);
                //Log.e("s_guarantee", "" + s_guarantee.toString());
            }

            if (s_videos.equalsIgnoreCase("") || s_videos.equalsIgnoreCase("null")) {
                l_more_Info_main.setVisibility(View.GONE);
                //Log.e("aaaaa", "" + s_videos.toString());
            } else {
                l_more_Info_main.setVisibility(View.VISIBLE);
                //Log.e("aaaaa", "" + s_videos.toString());
            }

            if (s_general.equalsIgnoreCase("") || s_general.equalsIgnoreCase("null")) {
                l_youtubevideo_main.setVisibility(View.GONE);
                //Log.e("bbbbbb", "" + s_general.toString());
            } else {
                l_youtubevideo_main.setVisibility(View.VISIBLE);
                //Log.e("bbbbbb", "" + s_general.toString());
            }
        } else {

        }
        setRefershDataCart();


        // setRefershDataImage();

        //Log.e("Images_size12121", "" + bean_productImages.size());
        if (bean_productImages.size() != 0) {

            for (int i = 0; i < bean_productImages.size(); i++) {

                //Log.e("pid", "" + bean_productImages.get(i).getPro_id() + ":" + pid);
                if (bean_productImages.get(i).getPro_id().equalsIgnoreCase(pid)) {
                    //Log.e("bean_productImages_loop", "" + i);
                    pro_img.add((bean_productImages.get(i).getPro_Images()));
                    //Log.e("aaaaaaaaa", "" + i);
                }


            }
            setLayout(pro_img);
        } else {
            //Log.e("setRefershDataImage", "" + bean_productImages.size());
        }


        //  setRefershDataOption();
        if (bean_productOprtions.size() != 0) {
            ArrayList<String> StringArrayop = new ArrayList<String>();
            for (int i = 0; i < bean_productOprtions.size(); i++) {

                final LinearLayout lhorizontalop = new LinearLayout(BTLProduct_Detail.this);
                lhorizontalop.setOrientation(LinearLayout.HORIZONTAL);
                lhorizontalop.setPadding(5, 5, 5, 5);
                final TextView tvop = new TextView(BTLProduct_Detail.this);
                tvop.setPadding(5, 0, 5, 0);
                tvop.setTextSize(13);
                tvop.setTextColor(Color.BLACK);
                final TextView tvoption = new TextView(BTLProduct_Detail.this);
                tvoption.setTextColor(Color.BLACK);
                tvoption.setTextSize(14);
                final TextView tvtitle = new TextView(BTLProduct_Detail.this);
                tvtitle.setTextColor(Color.BLACK);
                tvtitle.setTextSize(12);
                if (pid.equalsIgnoreCase(pid)) {

                    Spanned optionv = Html.fromHtml("<b>" + bean_productOprtions.get(i).getPro_Option_name() + "</b> ");


                    //  StringArrayop.add("\u2022" + " " + bean_productOprtions.get(i).getPro_Option_value_name());
                    // stringdot.add("\u2022");

                    tvop.setText("\u2022");

                    tvoption.setText(optionv + ": " + bean_productOprtions.get(i).getPro_Option_value_name());

                    lhorizontalop.addView(tvop);
                    lhorizontalop.addView(tvoption);
                    //   tvtitle.setText(features.toString());
                    //   Feature_text_layout.addView(tvtitle);
                    option_text_layout.addView(lhorizontalop);

                } else {

                }


            }
        }
        array_attribute_main.clear();
        if (bean_productOprtions.size() != 0) {
            ArrayList<Bean_Attribute> array_attributes1 = new ArrayList<Bean_Attribute>();

            //Log.e("121212 ::", "" + bean_productOprtions.size());

            for (int c = 0; c < bean_productOprtions.size(); c++) {
                //Log.e("232323 ::", "" + bean_productOprtions.get(c).getPro_id());
                //Log.e("343434 ::", "" + pid);
                if (pid.equalsIgnoreCase(pid)) {
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
                    array_attributes1.add(bean_attribute);
                }
            }

            //Log.e("45454545 ::", "" + array_attributes1.size());

            if (arrOptionTypeID.size() != 0) {


                ArrayList<String> temp_array = new ArrayList<String>();
                for (int i = 0; i < arrOptionTypeID.size(); i++) {
                    temp_array.add(arrOptionTypeID.get(i));
                }
                LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionTypeID);

                arrOptionTypeID.clear();
                //Creating Arraylist without duplicate values
                arrOptionTypeID = new ArrayList<String>(listToSet);



                            /* LinkedHashSet<String> listToSet1 = new LinkedHashSet<String>(arrOptionTypeName);

                             arrOptionTypeName.clear();
                             //Creating Arraylist without duplicate values
                             arrOptionTypeName = new ArrayList<String>(listToSet1);

                             //Log.e("AAAABBBccc", "" + arrOptionTypeName.size());*/
                //Log.e("AAAABBB", "1 - " + arrOptionTypeID.size() + " 2- " + temp_array.size());
                //int z = 0;

                             /*for(int p = 0 ; p < temp_array.size() ; p ++)
                             {
                                   // Bean_Attribute
                                 for(int s = 0 ; s < arrOptionTypeID.size() ; s++)
                                 {
                                     if(temp_array.get(p).equalsIgnoreCase(arrOptionTypeID.get(s)))
                                     {
                                         //Log.e("", p + "---" + "option ID - " + temp_array.get(p) + "List hash ID  - " + arrOptionTypeID.get(s));
                                       //  bean_attribute.setOption_id();
                                     }

                                 }

                             }*/

                for (int p = 0; p < arrOptionTypeID.size(); p++) {
                    //Log.e("arrOptionTypeID", "----------" + arrOptionTypeID.get(p));
                }
                for (int p = 0; p < temp_array.size(); p++) {
                    //Log.e("temp_array", "----------" + temp_array.get(p));
                }

                for (int p = 0; p < array_attributes1.size(); p++) {
                    //Log.e("array_attributes", "----------" + array_attributes1.get(p).getOption_id());
                }

                array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();

                for (int p = 0; p < arrOptionTypeID.size(); p++) {

                    ArrayList<Bean_Attribute> array_attribute = new ArrayList<Bean_Attribute>();

                    //Log.e("size", "" + temp_array.size());

                    for (int s = 0; s < temp_array.size(); s++) {
                        if (temp_array.get(s).equalsIgnoreCase(arrOptionTypeID.get(p))) {
                            ////Log.e("", p + "---" + "option ID - " + temp_array.get(s) + "List hash ID  - " + arrOptionTypeID.get(p));
                            Bean_Attribute bean_demo = new Bean_Attribute();
                            bean_demo.setOption_id(array_attributes1.get(s).getOption_id());
                            //Log.e("Option Id", "-----" + array_attributes1.get(s).getOption_id());
                            bean_demo.setOption_name(array_attributes1.get(s).getOption_name());
                            bean_demo.setValue_id(array_attributes1.get(s).getValue_id());
                            bean_demo.setValue_name(array_attributes1.get(s).getValue_name());
                            //Log.e("Value Name", "-----" + array_attributes1.get(s).getValue_name());
                            bean_demo.setValue_mrp(array_attributes1.get(s).getValue_mrp());
                            bean_demo.setValue_product_code(array_attributes1.get(s).getValue_product_code());
                            bean_demo.setValue_selling_price(array_attributes1.get(s).getValue_selling_price());
                            bean_demo.setValue_image(array_attributes1.get(s).getValue_image());

                            bean_demo.setOption_pro_id(array_attributes1.get(s).getOption_pro_id());
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

                    sp_option.setTag("" + k);
                    //Log.e("SIZEEE", "" + array_attribute_main.get(k).size());
                    sp_option.setAdapter(new MyAdapter(BTLProduct_Detail.this, R.layout.textview, array_attribute_main.get(k)));
                    array_attribute_main.get(k);

                    array_att = new ArrayList<Bean_Attribute>();
                    array_att = array_attribute_main.get(k);


                    for (int t = 0; t < array_att.size(); t++) {

                        //Log.e("Option Name", "" + array_att.get(t).getOption_name().toString());
                        txt_optionname.setText("Select " + array_att.get(t).getOption_name().toString());


                        if (k == 0) {
                            option_id = array_att.get(t).getOption_id();
                            option_name = array_att.get(t).getOption_name();
                        } else {
                            option_id = option_id + ", " + array_att.get(t).getOption_id();
                            option_name = option_name + ", " + array_att.get(t).getOption_name();
                        }

                        //Log.e("Option id", "" + option_id);
                    }
                    final int finalK = k;
                    sp_option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position1, long id) {
                            // TODO Auto-generated method stub

                            // position1=""+position;


                            int pos = Integer.parseInt(sp_option.getTag().toString());
                            ArrayList<Bean_Attribute> att_array = new ArrayList<Bean_Attribute>();

                            att_array = array_attribute_main.get(pos);

                           /* //Log.e("Position", "" + att_array.get(position1).getValue_name());
                            //Log.e("Position", "" + att_array.get(position1).getValue_id());
                            //Log.e("Position", "" + att_array.get(position1).getValue_mrp());*/

                            O_Product_id = att_array.get(position1).getOption_pro_id().toString();
                            //Log.e("O_Product_id", "" + O_Product_id);
                            if (pid.equalsIgnoreCase(O_Product_id)) {

                            } else {
                                app = new AppPrefs(BTLProduct_Detail.this);
                                app.setproduct_id(O_Product_id);


                                Intent i = new Intent(BTLProduct_Detail.this, BTLProduct_Detail.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });


                }
            }

        }
    }

    private void fetch_image_for_share(String index) {
        //Log.e("22222",""+index);
        new DownloadFileFromURL().execute(index);

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
            Globals.generateNoteOnSD(getApplicationContext(), "In GetItemQty While");
        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    private void GetCartQtyCall() {
        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(BTLProduct_Detail.this);
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
            app = new AppPrefs(BTLProduct_Detail.this);
            app.setCart_QTy("");
        }
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

    public class SlidingImage_Adapter_pDetail extends PagerAdapter {


        private ArrayList<String> IMAGES;
        private LayoutInflater inflater;

        private Context context;


        public SlidingImage_Adapter_pDetail(Context context, ArrayList<String> bean_productImages) {
            this.context = context;
            this.IMAGES = bean_productImages;

            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return ImagesArray.size();

        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.slidingimages_layout_pdetail, view, false);

            assert imageLayout != null;
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);

            try {

                // imageloader.DisplayImage(ImagesArray.get(position), imageView);
                Picasso.with(getApplicationContext())
                        .load(ImagesArray.get(position))
                        .placeholder(R.drawable.btl_watermark)
                        .into(imageView);

                //Log.e("", "" + ImagesArray.get(position));
            } catch (Exception e) {
                //Log.e("", "" + ImagesArray.get(position));
            }

           /* imageView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {

                    Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(BTLProduct_Detail.this,Event_Gallery_photo_view_Activity.class);
                    startActivity(i);
                }
            });*/


            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }

    public class set_marquee extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        BTLProduct_Detail.this, "");

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


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "FlashMessage/App_GetFlashMessage", ServiceHandler.POST, parameters);

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

                    Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");


                      /*  JSONObject jO = jObj.getJSONObject("data");
                        JSONObject jU = jO.getJSONObject("FlashMessage");


                        String mrquee= jU.getString("msg");
                        product_detail_marquee.setText(mrquee.toString());
                        //Log.e("mrquee",""+mrquee.toString());*/


                        JSONArray jmarquee_msg = jObj.getJSONArray("data");

                        for (int i = 0; i < jmarquee_msg.length(); i++) {
                            JSONObject jsonobject = jmarquee_msg.getJSONObject(i);
                            JSONObject jobject_marquee_msg = jsonobject.getJSONObject("FlashMessage");
                            String msg_marquee = jobject_marquee_msg.getString("msg");
                            product_detail_marquee.setText(msg_marquee.toString());


                        }

                        loadingView.dismiss();
                        new get_Product_detail_data().execute();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    /*  private void imageslider() {
          for (int i = 0; i < bean_productImages.size(); i++)
              ImagesArray.add(bean_productImages.get(i).getPro_Images());
          //Log.e("Imagearray",""+ImagesArray.size());



          mPager.setAdapter(new SlidingImage_Adapter_pDetail(BTLProduct_Detail.this, ImagesArray));


          CirclePageIndicator indicator = (CirclePageIndicator)
                  findViewById(R.id.indicator);

          indicator.setViewPager(mPager);



      }
  */
    public class CustomPagerAdapter extends PagerAdapter {

        public Object instantiateItem(View collection, final int position) {

            LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.viewpager_row, null);

            ((ViewPager) collection).addView(view, 0);


            ImageView ia = (ImageView) view.findViewById(R.id.img_flipperimag);

            try {
//                imageloader.DisplayImage(bean_productImages.get(position)
//                        .getPro_Images(), ia);
                Picasso.with(getApplicationContext())
                        .load(bean_productImages.get(position).getPro_Images())
                        .placeholder(R.drawable.btl_watermark)
                        .into(ia);
            } catch (Exception e) {
                //Log.e("Error", e.toString());
            }

           /* ia.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(getActivity(), Slider_Product.class);
                    startActivity(i);
                }
            });

            if (position == 0) {
                im_previous.setVisibility(View.GONE);
                im_next.setVisibility(View.VISIBLE);
            } else if (position == array_slider.size() - 1) {
                im_next.setVisibility(View.GONE);
                im_previous.setVisibility(View.VISIBLE);
            }else
            {
                im_previous.setVisibility(View.VISIBLE);
                im_next.setVisibility(View.VISIBLE);
            }
            im_next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (position == 0 || position < (array_slider.size() - 1)) {
                        customviewpager.setCurrentItem(position + 1);
                        //Log.e("", "Size :- " + array_slider.size()
                                + " position " + (position + 1));
                    }

                    else if (position == (array_slider.size() - 1)) {
                        //Log.e("", "Size :- " + array_slider.size()
                                + " position " + (position + 1));
                    }

                }
            });
            im_previous.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (position == 0) {

                    } else if (position == (array_slider.size() - 1)
                            || position > 0) {
                        customviewpager.setCurrentItem(position - 1);
                        //Log.e("", "Size :- " + array_slider.size()
                                + " position " + (position - 1));
                    }
                }
            });*/
            /*
             * title.setText(bean.get(position).getTitle());
			 *
			 * if (bean.get(position).getImage().equalsIgnoreCase("") ||
			 * bean.get(position).getImage().equalsIgnoreCase("null")) {
			 * //Log.e("", "Inside null===" + bean.get(position).getImage());
			 * ia.setImageResource(R.drawable.docicon);
			 *
			 * } else {
			 *
			 * byte [] encodeByte=Base64.decode(bean.get(position).getImage()
			 * ,Base64.DEFAULT); Bitmap
			 * bitmap=BitmapFactory.decodeByteArray(encodeByte, 0,
			 * encodeByte.length); ia.setImageBitmap(bitmap);
			 *
			 * //Log.e("", "Inside else===" + bean.get(position).getImage());
			 * imageloader.DisplayImage(bean.get(position).getImage(),
			 * R.drawable.docicon, ia); }
			 */
            // ia.setImageResource(Integer.parseInt(bean.get(position).getImage()));

            return view;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public int getCount() {

            return bean_productImages.size();

        }
    }

    /*private void setLayout_video(final ArrayList<Product_Youtube> str) {
        // TODO Auto-generated method stub

        l_video_lower.removeAllViews();

        //Log.e("str.size", "" + str.size());

        for (int ij = 0; ij < str.size(); ij++) {

            LinearLayout lmain = new LinearLayout(BTLProduct_Detail.this);
            //  params.setMargins(7, 7, 7, 7);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            lmain.setPadding(2, 2, 2, 2);

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    200, 200, 1.0f);
            LinearLayout l1 = new LinearLayout(BTLProduct_Detail.this);

            l1.setLayoutParams(par1);
            l1.setPadding(5, 5, 5, 5);
            l1.setOrientation(LinearLayout.VERTICAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(BTLProduct_Detail.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 5, 5, 5);

            final LinearLayout l2 = new LinearLayout(BTLProduct_Detail.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 5, 5, 5);
            l2.setVisibility(View.GONE);

            count = ij;
            final ImageView img_a = new ImageView(BTLProduct_Detail.this);
            img_a.setTag(ij);
            // img_a.setImageResource(str.get(count));
            //Log.e("count", "" + count);
            //Log.e("img", "" + str.get(count).getPro_youtube_url());
            try {

                url = str.get(count).getPro_youtube_url();
                String setthumbnail = "http://img.youtube.com/vi/" + url + "/0.jpg";
                //Log.e("setthumbnail", url + " : " + setthumbnail);
                app.setyoutube_api(str.get(count).getPro_youtube_url());
                imageloader.DisplayImage(setthumbnail, R.drawable.ic_temp_logo, img_a);

            } catch (NullPointerException e) {
                //Log.e("Error", "" + e);
            }


            par21.setMargins(5, 5, 5, 5);

            img_a.setLayoutParams(par21);
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            l1.addView(img_a);

            img_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppPrefs app= new AppPrefs(BTLProduct_Detail.this);

                    //Log.e("url_tonext_page",""+str.get(Integer.parseInt(img_a.getTag().toString())).getPro_youtube_url());
                    Intent i = new Intent(BTLProduct_Detail.this,Video_View_Activity.class);
                    app.setyoutube_api(str.get(Integer.parseInt(img_a.getTag().toString())).getPro_youtube_url());
                    startActivity(i);
                }
            });
            lmain.addView(l1);

            l_video_lower.addView(lmain);

        }

    }*/
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

    public class set_wish_list extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        BTLProduct_Detail.this, "");

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
                parameters.add(new BasicNameValuePair("product_id", pid));

                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/App_WishList", ServiceHandler.POST, parameters);
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

                    Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();

                      /*  page_id =1;
                        page_limit = "";*/

                        bean_product1.clear();
                        bean_productTechSpecs.clear();
                        bean_productOprtions.clear();
                        bean_productFeatures.clear();
                        bean_productStdSpecs.clear();

                        Intent i = new Intent(BTLProduct_Detail.this, BTLProduct_Detail.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


                    }
                }
            } catch (Exception ja) {
                ja.printStackTrace();


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
                        BTLProduct_Detail.this, "");

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
                parameters.add(new BasicNameValuePair("product_id", pid));
                parameters.add(new BasicNameValuePair("role_id", role_id));

                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/App_DeleteWishList", ServiceHandler.POST, parameters);
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

                    Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();

                      /*  page_id =1;
                        page_limit = "";*/

                        bean_product1.clear();
                        bean_productTechSpecs.clear();
                        bean_productOprtions.clear();
                        bean_productFeatures.clear();
                        bean_productStdSpecs.clear();

                        Intent i = new Intent(BTLProduct_Detail.this, BTLProduct_Detail.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


                    }
                }
            } catch (Exception ja) {
                ja.printStackTrace();


            }

        }

    }

    public class get_Product_detail_data extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                detail_price.setVisibility(View.GONE);
                l_mmain.setVisibility(View.GONE);
                loadingView = new Custom_ProgressDialog(
                        BTLProduct_Detail.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                parameters.add(new BasicNameValuePair("product_id", "" + pid));

                parameters.add(new BasicNameValuePair("user_id", user_id_main));

                parameters.add(new BasicNameValuePair("role_id", role_id));

                //Log.e("user_id_main", "" + user_id_main);

                //Log.e("product_id", "" + pid);
                //Log.e("role_id", "" + role_id);
                Log.e("jso", "-->" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Product_Details", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {


                        // page_limit = jObj.getString("total_page_count");

                        JSONArray jsonwish = jObj.optJSONArray("wish_list");

                        whereToBuyVisibility = jObj.getString("where_to_buy").equals("1");

                        WishList = new ArrayList<String>();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }

                        //Log.e("Wish List Size", "" + WishList.size());


                        JSONObject jobj = new JSONObject(result_1);


                        JSONObject jObjj = jObj.getJSONObject("data");
                        JSONObject jproduct = jObjj.getJSONObject("Product");


                        //JSONArray jsonArray = jObj.optJSONArray("data");

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
                        String packQty = jproduct.getString("pack_of_qty");
                        bean.setPackQty(Integer.parseInt(packQty.isEmpty() ? "1" : packQty));

                        Log.e("Pack Of Qty", "+->" + packQty);
                        //   bean.setPro_moreinfo(jproduct.getString("more_info"));
                        JSONArray jschme = jObjj.getJSONArray("Scheme");
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
                        Log.e("AbAb", "" + bean_technical.size());
                        JSONArray jProductOption = jObjj.getJSONArray("ProductOption");


                        for (int s = 0; s < jProductOption.length(); s++) {
                            JSONObject jProductOptiono = jProductOption.getJSONObject(s);
                            JSONObject productObject = jProductOptiono.getJSONObject("Product");
                            JSONObject jPOOption = jProductOptiono.getJSONObject("Option");
                            JSONObject jPOOptionValue = jProductOptiono.getJSONObject("OptionValue");
                            JSONArray jPOProductOptionImage = jProductOptiono.getJSONArray("ProductOptionImage");
                            JSONArray SchemeArray = jProductOptiono.getJSONArray("Scheme");


                            for (int m = 0; m < SchemeArray.length(); m++) {

                                JSONObject schemeObj = SchemeArray.getJSONObject(m);

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


                        //Log.e("Option Size1212", "" + bean_productOprtions.size());


                        JSONArray jpProductImage = jObjj.getJSONArray("ProductImage");

                        //Log.e("siz from array",jpProductImage.length()+"");

                        Bean_ProductImage beanproductimg = new Bean_ProductImage();

                        beanproductimg.setPro_id(jproduct.getString("id"));
                        beanproductimg.setPro_Images(jproduct.getString("image"));

                        bean_productImages.add(beanproductimg);

                        if (jpProductImage.length() > 0) {

                            for (int s = 0; s < jpProductImage.length(); s++) {
                                JSONObject jpimg = jpProductImage.getJSONObject(s);


                                Bean_ProductImage beanproductimg1 = new Bean_ProductImage();

                                beanproductimg1.setPro_id(jpimg.getString("product_id"));
                                beanproductimg1.setPro_Images(jpimg.getString("image"));

                                bean_productImages.add(beanproductimg1);

                            }
                        }


                        try {
                            if (bean_productImages_db.size() == 0) {
                                for (int a = 0; a < bean_productImages.size(); a++) {

                                    db = new DatabaseHandler(BTLProduct_Detail.this);
                                    db.Add_ProductImage(new Bean_ProductImage(bean_productImages.get(a).getPro_id(),
                                            bean_productImages.get(a).getPro_cat_id(),
                                            bean_productImages.get(a).getPro_Images())
                                    );
                                    db.close();
                                }
                                //Log.e("bean_pro_image_after", "" + bean_productImages_db.size());
                            } else {

                                //Log.e("Datbase....22", "" + bean_productImages.size());
                                for (int a = 0; a < bean_productImages.size(); a++) {
                                    //Log.e("List....22", "" + bean_productImages.size());
                                    int k = 0;
                                    for (int b = 0; b < bean_productImages_db.size(); b++) {
                                        if (k != 1) {
                                            if (bean_productImages_db.get(b).getPro_id().equalsIgnoreCase(bean_productImages.get(a).getPro_id())) {

                                            } else {
                                                db = new DatabaseHandler(BTLProduct_Detail.this);
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
                        } catch (Exception e) {
                            Log.e("Exception JSON", e.getMessage());
                        }

                        setRefershDataImage();

                        //Log.e("Image Size", "" + bean_productImages_db.size());

                        //Log.e("Size:Product ::", "" + bean_product1.size());

                        //Log.e("Size:Desc ::", "" + bean_desc1.size());

                        //Log.e("Size:ProductOptions ::", "" + bean_productOprtions.size());

                        //Log.e("Size:ProductIamge ::", "" + bean_productImages.size());


                        //Log.e("bean_desc_db Size", "" + bean_desc_db.size());

                        //Log.e("bean_technical size", "" + bean_technical.size());


                        loadingView.dismiss();
                        l_mmain.setVisibility(View.VISIBLE);
                        detail_price.setVisibility(View.VISIBLE);
                        Setdata();

                    }

                }
            } catch (Exception j) {
                Log.e("Exception JSON1", j.getMessage());
                j.printStackTrace();
            }

        }
    }

    public class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //activity.showDialog(0);
            //Log.e("3333","100");
           /* loadingView = new Custom_ProgressDialog(
                    activity, "");

            loadingView.setCancelable(false);
            loadingView.show();*/

            pDialog = new ProgressDialog(BTLProduct_Detail.this);
            pDialog.setMessage("Please Wait Downloading Image");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();


        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... i) {

            try {


                //pDialog.setMessage("Please Wait Downloading Image");
                File temp_dir = new File(myDir + "/" + ImgProcode + ".png");
                if (temp_dir.exists()) {
                    filesToSend = myDir + "/" + ImgProcode + ".png";
                } else {
                    File dir = new File(myDir + "");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    //Log.e("4444444",""+Globals.server_link + "files/" + Imgpath);
                    URL url = new URL(Globals.server_link + "files/" + Imgpath);
                    //Log.e("FILE_NAME", "File name is " + ImgProcode+".png");
                    //Log.e("FILE_URLLINK", "File URL is "+Globals.server_link + "files/" + Imgpath);
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // this will be useful so that you can show a typical 0-100% progress bar
                    long fileLength = connection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(new File(dir + "/" + ImgProcode + ".png"));

                    filesToSend = dir + "/" + ImgProcode + ".png";
                    //Log.e("", "File to send :- "+dir+"/" + ImgProcode+".png");
                    byte data[] = new byte[1024];
                    long total = 0;
                    //int count;


                    int count;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        //  publishProgress((int) ((total * 100)/fileLength));
                        long total1 = (total * 100) / fileLength;
                        publishProgress(total1 + "");
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }

            } catch (Exception e) {
                //Log.e("Error: ", ""+e);
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
       /* protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }*/

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //activity.dismissDialog(0);
            pDialog.dismiss();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_SUBJECT, "" + ImgProcode);
            intent.putExtra(Intent.EXTRA_TEXT, "Item Name : " + ImgProName + " (" + ImgProcode + ")\nItem Price : " + ImgProPrice);
            intent.setType("image/jpeg");  //This example is sharing jpeg images.

            ArrayList<Uri> files = new ArrayList<Uri>();


            File file = new File(filesToSend);
            Uri uri = Uri.fromFile(file);
            files.add(uri);


            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);


            // Displaying downloaded image into image view
            // Reading image path from sdcard
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
            // pDialog.dismiss();
            // setting downloaded into image view
            // my_image.setImageDrawable(Drawable.createFromPath(imagePath));
        }

        /*public void execute(String s) {
        }*/
    }

    private class GetProductDetailByCode extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetProductDetailByCode(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {

            jsonData = "";

            Globals.generateNoteOnSD(getApplicationContext(), pairList.toString());

            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "Scheme/App_Get_Scheme_Details", ServiceHandler.POST, pairList);

            isNotDone = false;
            return jsonData;
        }
    }

    private class GetProductDetailQty extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList;

        public GetProductDetailQty(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {

            Globals.generateNoteOnSD(getApplicationContext(), pairList.toString());

            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetItemQty", ServiceHandler.POST, pairList);
            isNotDone = false;

            Globals.generateNoteOnSD(getApplicationContext(), "JSON In ->" + isNotDone + "--" + jsonData);

            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //isNotDone=false;
            //Globals.generateNoteOnSD(getApplicationContext(),"->"+isNotDone);
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
                        BTLProduct_Detail.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {
            Globals.generateNoteOnSD(getApplicationContext(), "Add Product");
            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("CartData", "" + jarray_cart));


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_AddCartData", ServiceHandler.POST, parameters);

                Log.e("Webservice", "CartData/App_AddCartData" + "\n" + parameters.toString());

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
                    Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());
                        /*Intent i = new Intent(BTLProduct_Detail.this, BTLProduct_Detail.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);*/
                        loadingView.dismiss();

                        GetCartQtyCall();

                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());

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
                        BTLProduct_Detail.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            Globals.generateNoteOnSD(getApplicationContext(), "Edit Product");

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("CartData", "" + jarray_cart));


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_EditCartData", ServiceHandler.POST, parameters);

                Log.e("Webservice", "CartData/App_EditCartData" + "\n" + parameters.toString());
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
                    Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        loadingView.dismiss();
                        Globals.CustomToast(BTLProduct_Detail.this, "" + Message, getLayoutInflater());
                        /*Intent i = new Intent(BTLProduct_Detail.this, BTLProduct_Detail.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);*/
                        GetCartQtyCall();
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

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

                if (json == null || (json.equalsIgnoreCase(""))) {

                    Globals.CustomToast(BTLProduct_Detail.this, "SERVER ERROR", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);
                    String userStatus = jObj.getString("user_status");
                    if (userStatus.equals("0")) {
                        C.userInActiveDialog(BTLProduct_Detail.this);
                    }
                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(BTLProduct_Detail.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(BTLProduct_Detail.this);
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