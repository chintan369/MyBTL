package com.agraeta.user.btl;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class BTLProdcut_Detailpage extends AppCompatActivity {
    TextView txt_high,product_detail_marquee;
    String option_name = "";
    String option_id = "";
    TextView tv_pop_pname,tv_pop_code,tv_pop_packof,tv_pop_mrp,tv_pop_sellingprice,tv_total,tv_txt_mrp;
    LinearLayout layout_imagegrid,Feature_text_layout,option_text_layout,l_video_upper,l_video_lower,layout_youtube_forimg;
    int count = 0;
    ArrayList<ArrayList<Bean_Attribute>> array_attribute_main =new  ArrayList<ArrayList<Bean_Attribute>>();
    ArrayList<Integer> list_data = new ArrayList<Integer>();
    ArrayList<Bean_Attribute> array_att = new ArrayList<Bean_Attribute>();
    String CategoryID;
    ArrayList<Bean_Value_Selected_Detail> array_value = new ArrayList<Bean_Value_Selected_Detail>();
    LinearLayout.LayoutParams params;
    public static ArrayList<String> arrOptionTypeID =new ArrayList<String>();
    public static ArrayList<String> moreinfo_arry =new ArrayList<String>();
    public static ArrayList<String> arrOptionTypeName =new ArrayList<String>();
    double amount1;
    public static ArrayList<String> WishList =new ArrayList<String>();

    Button btn_buyonline_product_detail;
    ArrayList<String> list_of_images = new ArrayList<String>();
    //ResultHolder result_holder;
    LinearLayout l_spinner,l_spinner_text,l_linear,l_sort;
    ArrayList<Bean_ProductImage> bean_data = new ArrayList<Bean_ProductImage>();
    //private SectionsPagerAdapter mSectionsPagerAdapter;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    ArrayList<Bean_Product> bean_product1 = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductFeature> bean_productFeatures = new ArrayList<Bean_ProductFeature>();
    ArrayList<Bean_ProductTechSpec> bean_productTechSpecs = new ArrayList<Bean_ProductTechSpec>();
    ArrayList<Bean_ProductStdSpec> bean_productStdSpecs = new ArrayList<Bean_ProductStdSpec>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductCart> bean_productCart = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_ProductImage> bean_productImages = new ArrayList<Bean_ProductImage>();
    //ArrayList<Bean_ProductCatalog> bean_ProductCatalog = new ArrayList<Bean_ProductCatalog>();
    ArrayList<String> pro_img = new ArrayList<String>();
   // ArrayList<Product_Youtube> bean_productyoutube = new ArrayList<Product_Youtube>();
    DatabaseHandler db;

    String json = new String();
    private static ViewPager mPager,mViewPager;
    ImageLoader imageloader;

    Custom_ProgressDialog loadingView;
    ImageView minuss,plus;
    Button buy_cart,cancel,btn_wherebuy;
    EditText edt_count;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    TextView p_prize1;
    String url,setthumbnail;
    int s = 0;
    ArrayList<Bean_Desc> bean_desc_db = new ArrayList<Bean_Desc>();
    TextView tv_product_mrp,tv_product_selling_price,tv_product_name,tv_product_code,tv_packof,video_text;
    ImageView img_prodetail_wishlist,img_prodetail_shoppinglist,img_full;
    LinearLayout l_desc,l_Std_spec,l_technical,l_features,l_videos,l_more,l_video;
    LinearLayout l_desc_detail,l_std_spec_detail,l_technical_detail,l_features_detail,l_videos_detail,l_more_info1,moreinfo_text_layout,standard_text_layout,technical_text_layout;
    ImageView img_desc,img_std_spec,img_technical,img_features,img_videos,img_more,videoyoutube_img;
    TextView text_desc,text_std_spec,text_features,text_Tech_spec,text_option,more_Info_text,tvbullet;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    LinearLayout l_description_main,l_Feature_main,l_videos_main,l_technical_main,l_standard_technical_main,l_more_Info_main,l_youtubevideo_main;
    String owner_id = new String();
    String u_id = new String();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    Boolean desc = false,spec = false,technical = false,catalogue=false,features=false,videos=false ,more=false,videoupper=false ;
    String pid;
    AppPrefs app;
    String user_id_main = new String();
    String role_id = new String();
    /*  private static final Integer[] IMAGES = {R.drawable.boss1,
              R.drawable.boss2,
              R.drawable.boss3,
              R.drawable.boss4

      };*/
    String wishid = new String();
    String mrp,sellingprice;
    Dialog dialog;

    String s_description = new String();
    String s_specification = new String();
    String s_technical = new String();
    String s_catalogs = new String();
    String s_guarantee = new String();
    String s_videos = new String();
    LinearLayout detail_price;
    String s_general = new String();
    TextView txt_product;

    String cartJSON="";
    boolean hasCartCallFinish=true;

    TextView txt;
    LinearLayout layout_detail;


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
        setContentView(R.layout.activity_btlprodcut__detailpage);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        layout_detail=(LinearLayout) findViewById(R.id.layout_detail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        setActionBar();
        Intent i1 = getIntent();


        app = new AppPrefs(BTLProdcut_Detailpage.this);
        pid = app.getproduct_id().toString();
        //Log.e("pid", "" + pid);
        wishid=app.getwishid();
        //Log.e("wishid", "" + wishid);
        CategoryID=app.getUser_CatId().toString();
        imageloader= new ImageLoader(BTLProdcut_Detailpage.this);



        setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main =user_data.get(i).getUser_id();
                role_id =user_data.get(i).getUser_type();

                if(role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)){
                    Snackbar.make(layout_detail,"Taking Order for : "+app.getUserName(),Snackbar.LENGTH_LONG).show();
                }
            }

        }else{
            user_id_main ="";
        }

        SetRefershDataProductDesc();
        //Log.e("bean_desc_db.size()",""+bean_desc_db.size());
        if (bean_desc_db.size() != 0) {



            for (int i = 0; i < bean_desc_db.size(); i++) {

                //Toast.makeText(BTLProdcut_Detailpage.this,""+bean_desc_db.get(i).getPro_id().toString() + "-"+pid,Toast.LENGTH_LONG).show();

                if (bean_desc_db.get(i).getPro_id().equalsIgnoreCase(pid)) {

                    //Log.e("111111111111111111","1111111111111111111");

                    // more_Info_text.setText(moreInformation.toString());

                    s_description = bean_desc_db.get(i).getDescription().toString();
                    //Log.e("desc",""+s_description);
                    s_specification =  bean_desc_db.get(i).getSpecification().toString();
                    //Log.e("spec",""+s_specification);
                    s_technical = bean_desc_db.get(i).getTechnical().toString();
                    //Log.e("tech",""+s_technical);
                    s_catalogs =  bean_desc_db.get(i).getCatalogs().toString();
                    //Log.e("cate",""+s_catalogs);
                    s_guarantee =  bean_desc_db.get(i).getGuarantee().toString();
                    //Log.e("guar",""+s_guarantee);
                    s_videos = bean_desc_db.get(i).getVideos().toString();
                    //Log.e("video",""+s_videos);
                    s_general =  bean_desc_db.get(i).getGeneral().toString();
                    //Log.e("gene",""+s_general);

                } else {

                }

            }
        }
        fetchID();
        new set_marquee().execute();



        SetRefershDataProduct();


        if (bean_product1.size() != 0) {

            for (int i = 0; i < bean_product1.size(); i++) {

                if (bean_product1.get(i).getPro_id().equalsIgnoreCase(pid)) {


                    tv_product_name.setText(bean_product1.get(i).getPro_name());
                    //Log.e("name", pid + bean_product1.get(i).getPro_name());
                    tv_product_code.setText("(" + bean_product1.get(i).getPro_code() + ")");
                    //Log.e("code", pid + bean_product1.get(i).getPro_code());
                    mrp=bean_product1.get(i).getPro_mrp();
                    tv_product_mrp.setText("\u20B9" + bean_product1.get(i).getPro_mrp());
                    //Log.e("mrp", pid + bean_product1.get(i).getPro_mrp());
                    sellingprice=bean_product1.get(i).getPro_sellingprice();
                    tv_product_selling_price.setText("\u20B9"+bean_product1.get(i).getPro_sellingprice());
                    //Log.e("sellngprice", pid + bean_product1.get(i).getPro_sellingprice());
                    tv_packof.setText(bean_product1.get(i).getPro_label());
                    //Log.e("label", pid + bean_product1.get(i).getPro_label());
                    text_desc.setText(Html.fromHtml(bean_product1.get(i).getPro_shortdesc()));
                    //Log.e("desc", pid + bean_product1.get(i).getPro_shortdesc());

                   // imageloader.DisplayImage(Globals.server_link + "files/" + bean_product1.get(i).getPro_image(),  img_full);
                    Picasso.with(getApplicationContext())
                            .load(Globals.server_link + "files/" + bean_product1.get(i).getPro_image())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_full);

                    //Log.e("mainproimg", pid + bean_product1.get(i).getPro_image());
                    String moreInformation="http://app.nivida.in/boss"+bean_product1.get(i).getPro_moreinfo();
                    //Log.e("more_information", pid + bean_product1.get(i).getPro_moreinfo());

                    // more_Info_text.setText(moreInformation.toString());




                } else {

                }

            }
        }



        setRefershDataCart();


        setRefershDataImage();

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
        }else {
            //Log.e("setRefershDataImage", "" + bean_productImages.size());
        }


        setRefershDataOption();
        if (bean_productOprtions.size() != 0) {
            ArrayList<String> StringArrayop = new ArrayList<String>();
            for (int i = 0; i < bean_productOprtions.size(); i++) {

                final  LinearLayout lhorizontalop = new LinearLayout(BTLProdcut_Detailpage.this);
                lhorizontalop.setOrientation(LinearLayout.HORIZONTAL);
                lhorizontalop.setPadding(5, 5, 5, 5);
                final  TextView tvop = new TextView(BTLProdcut_Detailpage.this);
                tvop.setPadding(5, 0, 5, 0);
                tvop.setTextSize(13);
                tvop.setTextColor(Color.BLACK);
                final   TextView tvoption = new TextView(BTLProdcut_Detailpage.this);
                tvoption.setTextColor(Color.BLACK);
                tvoption.setTextSize(14);
                final   TextView tvtitle = new TextView(BTLProdcut_Detailpage.this);
                tvtitle.setTextColor(Color.BLACK);
                tvtitle.setTextSize(12);
                if (bean_productOprtions.get(i).getPro_id().equalsIgnoreCase(pid)) {

                    Spanned optionv = Html.fromHtml( "<b>"+bean_productOprtions.get(i).getPro_Option_name()+"</b> ");


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


        l_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (desc == false) {

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
                    //Log.e("s_guarantee",""+s_guarantee);
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
                    //Log.e("specifiction",""+s_specification);
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
                    webview_popup(s_technical);
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

                    /*img_desc.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_more.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_std_spec.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_technical.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);

                    img_features.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_videos.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);
                    l_more_info1.setVisibility(View.GONE);
                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    //l_catalogue_detail.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.VISIBLE);*/
                    webview_popup(s_general);
                    videoupper = true;

                } else {
                    videoupper = false;
                  /*  videoyoutube_img.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_video_lower.setVisibility(View.GONE);
*/

                }

            }

        });

        l_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (more == false) {


                   /* img_desc.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_std_spec.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_technical.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    videoyoutube_img.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);

                    img_features.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_videos.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    img_more.setImageResource(R.drawable.ic_hardware_keyboard_arrow_down);

                    l_desc_detail.setVisibility(View.GONE);
                    l_std_spec_detail.setVisibility(View.GONE);
                    l_technical_detail.setVisibility(View.GONE);
                    l_video_lower.setVisibility(View.GONE);
                    l_features_detail.setVisibility(View.GONE);
                    l_videos_detail.setVisibility(View.GONE);
                    l_more_info1.setVisibility(View.VISIBLE);*/
                    webview_popup(s_videos);

                    more = true;


                } else {
                    more = false;
                  /*  img_more.setImageResource(R.drawable.ic_hardware_keyboard_arrow_right);
                    l_more_info1.setVisibility(View.GONE);
*/

                }


            }
        });


        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT,1.0f);

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
                   /* Globals.CustomToast(Product_List.this, "" + bean_product1.get(position)
                            .getPro_id(), getLayoutInflater());*/
                dialog = new Dialog(BTLProdcut_Detailpage.this);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                dialog.setCanceledOnTouchOutside(false);
                wlp.gravity = Gravity.BOTTOM;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_cart);
                minuss = (ImageView)dialog.findViewById(R.id.minus);
                plus =(ImageView)dialog.findViewById(R.id.plus);
                buy_cart=(Button)dialog.findViewById(R.id.Btn_Buy_online11);
                cancel=(Button)dialog.findViewById(R.id.cancel);
                edt_count=(EditText)dialog.findViewById(R.id.edt_count);
                edt_count.setSelection(edt_count.getText().length());


                tv_pop_pname=(TextView)dialog.findViewById(R.id.product_name);
                tv_pop_code=(TextView)dialog.findViewById(R.id.product_code);
                tv_pop_packof=(TextView)dialog.findViewById(R.id.product_packof);
                tv_pop_mrp=(TextView)dialog.findViewById(R.id.product_mrp);
                tv_txt_mrp=(TextView)dialog.findViewById(R.id.txt_mrp_text);
                tv_pop_sellingprice=(TextView)dialog.findViewById(R.id.product_sellingprice);
                tv_total=(TextView)dialog.findViewById(R.id.txt_total);
                l_spinner=(LinearLayout)dialog.findViewById(R.id.l_spinner);
                l_linear=(LinearLayout)dialog.findViewById(R.id.l_view_spinner);
                l_spinner_text=(LinearLayout)dialog.findViewById(R.id.l_spinnertext);
                tv_pop_pname.setText(tv_product_name.getText().toString());
                tv_pop_code.setText(tv_product_code.getText().toString());
                tv_pop_packof.setText(tv_packof.getText().toString());
                tv_pop_mrp.setText(mrp.toString());
                tv_pop_sellingprice.setText(sellingprice.toString());
                tv_pop_sellingprice.setTag(sellingprice.toString());
                tv_total.setText(mrp.toString());


                double mrp = Double.parseDouble(tv_pop_mrp.getText().toString());
                double sellingprice = Double.parseDouble(tv_pop_sellingprice.getText().toString());

                if(mrp > sellingprice){
                    tv_pop_mrp.setVisibility(View.VISIBLE);
                    tv_txt_mrp.setVisibility(View.VISIBLE);
                    tv_pop_mrp.setPaintFlags(tv_pop_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                }else{
                    tv_pop_mrp.setVisibility(View.GONE);
                    tv_txt_mrp.setVisibility(View.GONE);
                }

                arrOptionTypeID.clear();
                arrOptionTypeName.clear();
                arrOptionTypeID = new ArrayList<String>();
                arrOptionTypeName = new ArrayList<String>();
                if(bean_productOprtions.size() != 0){
                    l_linear.setVisibility(View.VISIBLE);
                    ArrayList<Bean_Attribute> array_attributes = new ArrayList<Bean_Attribute>();

                    for (int c = 0; c < bean_productOprtions.size(); c++) {

                        if (pid.equalsIgnoreCase(bean_productOprtions.get(c).getPro_id())) {
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

                            bean_attribute.setPro_id(bean_productOprtions.get(c).getPro_id());
                            //Log.e("Product_id", "------" + bean_productOprtions.get(c).getPro_id());
                            array_attributes.add(bean_attribute);
                        }
                    }
                    if (arrOptionTypeID.size() != 0) {


                        ArrayList<String> temp_array = new ArrayList<String>() ;
                        for(int i = 0 ; i < arrOptionTypeID.size() ; i ++)
                        {
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
                        //Log.e("AAAABBB", "1 - " + arrOptionTypeID.size() + " 2- "+temp_array.size());
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

                        for(int p = 0 ; p < arrOptionTypeID.size() ; p ++)
                        {
                            //Log.e("arrOptionTypeID", "----------" + arrOptionTypeID.get(p));
                        }
                        for(int p = 0 ; p < temp_array.size() ; p ++)
                        {
                            //Log.e("temp_array", "----------" + temp_array .get(p));
                        }

                        for(int p = 0 ; p < array_attributes.size() ; p ++)
                        {
                            //Log.e("array_attributes", "----------" + array_attributes .get(p).getOption_id());
                        }

                        array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();

                        for(int p = 0 ; p < arrOptionTypeID.size() ; p ++)
                        {

                            ArrayList<Bean_Attribute> array_attribute = new ArrayList<Bean_Attribute>();

                            for(int s = 0 ; s < temp_array.size() ; s++)
                            {
                                if(temp_array.get(s).equalsIgnoreCase(arrOptionTypeID.get(p))) {
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
                                    bean_demo.setValue_selling_price(  array_attributes.get(s).getValue_selling_price());
                                    bean_demo.setValue_image(array_attributes.get(s).getValue_image());
                                    array_attribute.add(bean_demo);
                                }
                            }
                            //Log.e("", "new bean size -- " + array_attribute.size());

                            array_attribute_main.add(array_attribute);
                            //Log.e("", "new bean size -- " + array_attribute_main.size());
                        }

                        for(int i = 0 ; i < array_attribute_main.size() ; i++)
                        {
                            Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                            bean.setValue_id("0");
                            bean.setValue_name("Test");
                            bean.setValue_mrp("00");
                            array_value.add(bean);
                        }

                        for(int k = 0 ; k <array_attribute_main.size() ; k ++) {

                            final TextView text = new TextView(BTLProdcut_Detailpage.this);

                            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);

                            params1.setMargins(2, 2, 2, 2);

                            text.setLayoutParams(params1);

                              /*  ArrayList<Bean_Attribute> array_att = new ArrayList<Bean_Attribute>();
                                array_att = array_attribute_main.get(k);

                                text.setText(""+array_att.get(0).getOption_name());*/

                            text.setTextColor(Color.parseColor("#000000"));

                            text.setTextSize(12);

                            l_spinner_text.addView(text);

                            final Spinner spinner = new Spinner(BTLProdcut_Detailpage.this);

                            spinner.setBackgroundResource(R.drawable.spinner_border);



                            spinner.setTag("" + k);

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);

                            params.setMargins(2, 2, 2, 2);

                            spinner.setLayoutParams(params);

                            spinner.setAdapter(new MyAdapter(BTLProdcut_Detailpage.this, R.layout.textview, array_attribute_main.get(k)));

                            l_spinner.addView(spinner);

                            array_att = new ArrayList<Bean_Attribute>();
                            array_att = array_attribute_main.get(k);


                            for(int t = 0 ; t < array_att.size() ; t ++){

                                //Log.e("Option Name", "" + array_att.get(t).getOption_name());
                                text.setText("Select "+array_att.get(t).getOption_name());

                                if (k == 0) {
                                    option_id = array_att.get(t).getOption_id();
                                    option_name = array_att.get(t).getOption_name();
                                } else {
                                    option_id = option_id + ", " + array_att.get(t).getOption_id();
                                    option_name = option_name + ", " + array_att.get(t).getOption_name();
                                }


                            }
                            final int finalK=k;
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position1, long id) {
                                    // TODO Auto-generated method stub

                                    // position1=""+position;

                                    int pos = Integer.parseInt(spinner.getTag().toString());
                                    ArrayList<Bean_Attribute> att_array = new ArrayList<Bean_Attribute>();
                                    att_array =array_attribute_main.get(pos);

                                    //Log.e("Position",""+att_array.get(position1).getValue_name());
                                    //Log.e("Position", "" + att_array.get(position1).getValue_id());
                                    //Log.e("Position", "" + att_array.get(position1).getValue_mrp());

                                    //Log.e("Product_id", "" + att_array.get(position1).getPro_id());

                                    Bean_Value_Selected_Detail bean = new Bean_Value_Selected_Detail();
                                    bean.setValue_id(att_array.get(position1).getValue_id());
                                    bean.setValue_name(att_array.get(position1).getValue_name());
                                    bean.setValue_mrp(att_array.get(position1).getValue_mrp());

                                    array_value.set(finalK, bean);




                                    //Log.e("", "ID :- " + array_value.get(finalK).getValue_id());
                                    //Log.e("", "Name :- " + array_value.get(finalK).getValue_name());
                                    //Log.e("", "MRP :- " + array_value.get(finalK).getValue_mrp());

                                    if(att_array.get(position1).getValue_image().equalsIgnoreCase("")||att_array.get(position1).getValue_image().equalsIgnoreCase("null"))
                                    {


                                    }
                                    else
                                    {
                                        list_of_images.set(position1, att_array.get(position1).getValue_image());
                                        //adapter.notifyDataSetChanged();
                                    }

                                    if(att_array.get(position1).getValue_selling_price().toString().equalsIgnoreCase("0")){

                                    }else {
                                        tv_pop_mrp.setText(att_array.get(position1).getValue_mrp().toString());
                                        tv_pop_sellingprice.setText(att_array.get(position1).getValue_selling_price().toString());
                                        if(att_array.get(position1).getValue_product_code().toString().equalsIgnoreCase("null")||att_array.get(position1).getValue_product_code().toString().equalsIgnoreCase(""))
                                        {

                                        }
                                        else
                                        {
                                            tv_product_code.setText(" (" + att_array.get(position1).getValue_product_code() + ")");
                                        }
                                        tv_pop_sellingprice.setTag(att_array.get(position1).getValue_selling_price().toString());
                                        if(edt_count.getText().toString().equalsIgnoreCase("1")){
                                            tv_total.setText(att_array.get(position1).getValue_selling_price().toString());
                                        }else {
                                            int counter = Integer.parseInt(edt_count.getText().toString());
                                            double amt = Double.parseDouble(tv_pop_sellingprice.getText().toString());
                                            double total = amt * counter;
                                            double finalValue = (double)(Math.round( total * 100 ) / 100);
                                            String str = String.format("%.2f", total);
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
                                    /*
                             //Log.e("",""+"option ID - "+temp_array.get(1) + "List hash ID  - "+arrOptionTypeID.get(1));
                             if(temp_array.get(0).equalsIgnoreCase(arrOptionTypeID.get(0)))
                             {
                                 //System.out.println("option ID - "+temp_array.get(p) + "List hash ID  - "+arrOptionTypeID.get(z));
                                 //Log.e("------",""+"option ID - "+temp_array.get(p) + "List hash ID  - "+arrOptionTypeID.get(z));
                             }
                             else
                             {
                                 z++;
                                 //System.out.println("option ID - "+temp_array.get(p) + "List hash ID  - "+arrOptionTypeID.get(z));
                                 //Log.e("", "" + "option ID - " + temp_array.get(p) + "List hash ID  - " + arrOptionTypeID.get(z));
                             }*/
                            /*for (int s = 0; s < arrOptionTypeID.size(); s++) {



                                    Spinner spinner = new Spinner(Product_List.this);

                                    spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                                    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(Product_List.this,
                                            android.R.layout.simple_spinner_dropdown_item, );
                                    spinner.setAdapter(spinnerArrayAdapter);

                                    l_spinner.addView(spinner);

                            }*/


                    }



                        /*LinkedHashSet<String> listToSet = new LinkedHashSet<String>(arrOptionType);

                        arrOptionType.clear();
                        //Creating Arraylist without duplicate values
                        arrOptionType = new ArrayList<String>(listToSet);

                        //Log.e("AAAA", "" + arrOptionType.size());

                        List newList = new ArrayList(new LinkedHashSet(
                                arrOptionType));
                        Iterator it = newList.iterator();
                        while (it.hasNext()) {
                            //System.out.println(it.next());

                        }*/


                }
                else{
                    l_linear.setVisibility(View.GONE);
                    //   Globals.CustomToast(BTLProdcut_Detailpage.this, "ALLL NO DATA", getLayoutInflater());
                }


                dialog.show();

                String c=edt_count.getText().toString();
                s=Integer.parseInt(c);
                minuss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String c1=edt_count.getText().toString();
                        s=Integer.parseInt(c1);
                        if(s==0)
                        {
                            //Globals.CustomToast(Product_List.this, "Its impossible", getLayoutInflater());
                            edt_count.setText("1");
                            tv_total.setText(tv_pop_sellingprice.getTag().toString());

                        }
                        else
                        {

                            s = s - 1;
                            if(s==0){
                                edt_count.setText("1");
                                tv_total.setText(tv_pop_sellingprice.getTag().toString());
                            }else {
                                //   edt_count.setText(String.valueOf(s));
                                edt_count.setText(String.valueOf(s));
                                int counter = s;
                                double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                                double total = amt * counter;
                                double finalValue = (double)(Math.round( total * 100 ) / 100);

                                String str = String.format("%.2f", total);
                                tv_total.setText(str);



                            }


                            // Toast.makeText(activity,"Total "+to,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                String c1=edt_count.getText().toString();
                s=Integer.parseInt(c1);
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String c1=edt_count.getText().toString();
                        s=Integer.parseInt(c1);
                        s = s + 1;
                        edt_count.setText(String.valueOf(s));
                        int counter = s;
                        double amt = Double.parseDouble(tv_pop_sellingprice.getTag().toString());
                        double total = amt * counter;
                        double finalValue = (double)(Math.round( total * 100 ) / 100);

                        String str = String.format("%.2f", total);
                        tv_total.setText(str);

                        // Toast.makeText(getApplicationContext(),"Total "+to,Toast.LENGTH_SHORT).show();
                    }
                });

                edt_count.addTextChangedListener(new TextWatcher() {

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        if (s.length() == 0) { // do your work here }
                            String productpricce = tv_pop_sellingprice.getTag().toString();
                            edt_count.setText("1");
                            tv_total.setText(productpricce);
                        }

                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub
                        if (s.length() != 0) { // do your work here }
                            String productpricce = tv_pop_sellingprice.getTag().toString();
                            amount1 = Double.parseDouble(s.toString());
                            amount1 = Double.parseDouble(productpricce) * amount1;
                            double finalValue = (double)(Math.round( amount1 * 100 ) / 100);

                            String str = String.format("%.2f", amount1);
                            tv_total.setText(str);


                        } else {
                            String productpricce = tv_pop_sellingprice.getTag().toString();
                            edt_count.setText("1");
                            tv_total.setText(productpricce);
						/*if(txt_count.getText().toString().equalsIgnoreCase("0")){
							txt_amount.setEnabled(false);
						}
						*/

                        }
                    }
                });

                buy_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Globals.CustomToast(BTLProdcut_Detailpage.this, "Item insert in cart", getLayoutInflater());
                        Bean_ProductCart bean = new Bean_ProductCart();
                        String value_id = "";
                        String value_name = "";


                        for(int i = 0 ; i < array_value.size() ; i  ++)
                        {
                            if(i==0)
                            {
                                value_name = array_value.get(i).getValue_name();
                                value_id = array_value.get(i).getValue_id();
                            }
                            else
                            {
                                value_id = value_id+", "+array_value.get(i).getValue_id();
                                value_name = value_name +", "+ array_value.get(i).getValue_name();

                            }
                        }


                            /*option_id_array = new HashSet<String>(Arrays.asList(option_id_array)).toArray(new String[option_id_array.length]);
                            option_name_array = new HashSet<String>(Arrays.asList(option_name_array)).toArray(new String[option_id_array.length]);*/



                        bean.setPro_id(pid);
                        // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                        bean.setPro_cat_id(CategoryID);
                        bean.setPro_Images(img_full.toString());
                        bean.setPro_code(tv_product_code.getText().toString());
                        bean.setPro_name(tv_pop_pname.getText().toString());
                        bean.setPro_qty(edt_count.getText().toString());
                        bean.setPro_mrp(tv_pop_mrp.getText().toString());
                        bean.setPro_sellingprice(tv_pop_sellingprice.getText().toString());
                        bean.setPro_shortdesc(text_desc.getText().toString());
                        bean.setPro_Option_id(option_id);
                        //Log.e("-- ", "Option Id " + option_id);

                        bean.setPro_Option_name(option_name);
                        //Log.e("-- ", "Option Name " + option_name);

                        bean.setPro_Option_value_id(value_id);

                        //Log.e("", "Value Name " + value_id);
                        bean.setPro_Option_value_name(value_name);
                        //Log.e("", "Value Name " + value_name);

                        bean.setPro_total(tv_total.getText().toString());

                        db.Add_Product_cart(bean);

                        array_value.clear();





                        dialog.dismiss();
                    }

                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void fetchID()
    {
        tv_product_mrp=(TextView)findViewById(R.id.tv_product_mrp);
        tv_product_selling_price=(TextView)findViewById(R.id.tv_product_selling_price);
        tv_product_name=(TextView)findViewById(R.id.tv_product_name);
        tv_product_code=(TextView)findViewById(R.id.tv_product_code);
        tv_packof=(TextView)findViewById(R.id.tv_packof);
        tvbullet=(TextView)findViewById(R.id.tvbullet);
       // txt_product=(TextView)findViewById(R.id.txt_product);
        detail_price=(LinearLayout)findViewById(R.id.detail_price);
        l_description_main =(LinearLayout)findViewById(R.id.l_description_main);
        l_Feature_main =(LinearLayout)findViewById(R.id.l_Feature_main);
        l_videos_main =(LinearLayout)findViewById(R.id.l_videos_main);
        l_technical_main =(LinearLayout)findViewById(R.id.l_technical_main);
        l_standard_technical_main =(LinearLayout)findViewById(R.id.l_standard_technical_main);
        l_more_Info_main =(LinearLayout)findViewById(R.id.l_more_Info_main);
        l_youtubevideo_main =(LinearLayout)findViewById(R.id.l_youtubevideo_main);
        btn_buyonline_product_detail = (Button) findViewById(R.id.btn_buyonline_product_detail);

        setRefershData();
        if(user_data.size() == 0){
            detail_price.setVisibility(View.GONE);
            btn_buyonline_product_detail.setVisibility(View.GONE);
        }else{
            detail_price.setVisibility(View.VISIBLE);
            btn_buyonline_product_detail.setVisibility(View.VISIBLE);
        }



        if(s_description.toString().equalsIgnoreCase("") || s_description.toString().equalsIgnoreCase("null")){
            l_description_main.setVisibility(View.GONE);

        }else{
            l_description_main.setVisibility(View.VISIBLE);
        }

        if(s_specification.toString().equalsIgnoreCase("")|| s_specification.toString().equalsIgnoreCase("null")){
            l_Feature_main.setVisibility(View.GONE);
        }else{
            l_Feature_main.setVisibility(View.VISIBLE);
        }

        if(s_technical.toString().equalsIgnoreCase("")|| s_technical.toString().equalsIgnoreCase("null")){
            l_videos_main.setVisibility(View.GONE);
        }else{
            l_videos_main.setVisibility(View.VISIBLE);
        }

        if(s_catalogs.toString().equalsIgnoreCase("")||s_catalogs.toString().equalsIgnoreCase("null")){
            l_technical_main.setVisibility(View.GONE);
        }else{
            l_technical_main.setVisibility(View.VISIBLE);
        }

        if(s_guarantee.toString().equalsIgnoreCase("")||s_guarantee.toString().equalsIgnoreCase("null")){
            l_standard_technical_main.setVisibility(View.GONE);
        }else{
            l_standard_technical_main.setVisibility(View.VISIBLE);
        }

        if(s_videos.toString().equalsIgnoreCase("")||s_videos.toString().equalsIgnoreCase("null")){
            l_more_Info_main.setVisibility(View.GONE);
        }else{
            l_more_Info_main.setVisibility(View.VISIBLE);
        }

        if(s_general.toString().equalsIgnoreCase("")||s_general.toString().equalsIgnoreCase("null")){
            l_youtubevideo_main.setVisibility(View.GONE);
        }else{
            l_youtubevideo_main.setVisibility(View.VISIBLE);
        }


        mPager = (ViewPager) findViewById(R.id.pager);
        img_prodetail_wishlist=(ImageView)findViewById(R.id.img_prodetail_wishlist);
        img_prodetail_shoppinglist=(ImageView)findViewById(R.id.img_prodetail_shoppinglist);
        img_full=(ImageView) findViewById(R.id.img_full);

        product_detail_marquee = (TextView) this.findViewById(R.id.product_detail_marquee);
        product_detail_marquee.setSelected(true);



        layout_imagegrid =(LinearLayout)findViewById(R.id.layout_imagegrid);
        Feature_text_layout=(LinearLayout)findViewById(R.id.Feature_text_layout);
        moreinfo_text_layout=(LinearLayout)findViewById(R.id.moreinfo_text_layout);
        standard_text_layout=(LinearLayout)findViewById(R.id.standard_text_layout);
        technical_text_layout=(LinearLayout)findViewById(R.id.technical_text_layout);
        layout_youtube_forimg=(LinearLayout)findViewById(R.id.layout_youtube_forimg);


        l_desc = (LinearLayout)findViewById(R.id.l_description);
        l_Std_spec =(LinearLayout)findViewById(R.id.l_standard_technical);
        l_technical=(LinearLayout)findViewById(R.id.l_technical);
        option_text_layout=(LinearLayout)findViewById(R.id.option_text_layout);

        l_features=(LinearLayout)findViewById(R.id.l_Feature);
        l_videos=(LinearLayout)findViewById(R.id.l_videos);
        l_more = (LinearLayout)findViewById(R.id.l_more_Info);



        l_desc_detail = (LinearLayout)findViewById(R.id.l_description_detail);
        l_std_spec_detail =(LinearLayout)findViewById(R.id.l_standard_technical_detail);
        l_technical_detail=(LinearLayout)findViewById(R.id.l_technical_detail);
        l_features_detail=(LinearLayout)findViewById(R.id.l_Feature_detail);
        l_videos_detail=(LinearLayout)findViewById(R.id.l_videos_detail);
        l_more_info1 = (LinearLayout)findViewById(R.id.l_more_Info_detail);
        l_video_upper= (LinearLayout)findViewById(R.id.l_video_upper);
        l_video_lower= (LinearLayout)findViewById(R.id.l_video_lower);

        img_desc = (ImageView)findViewById(R.id.desc_image);
        img_std_spec =(ImageView)findViewById(R.id.standard_technical_image);
        img_technical=(ImageView)findViewById(R.id.technical_image);

        img_features=(ImageView)findViewById(R.id.Feature_image);
        img_videos=(ImageView)findViewById(R.id.videos_image);
        videoyoutube_img=(ImageView)findViewById(R.id.videoyoutube_img);
        img_more=(ImageView)findViewById(R.id.more_Info_image);

        text_desc=(TextView)findViewById(R.id.desc_text);
        text_std_spec=(TextView)findViewById(R.id.standard_technical_text);
        text_features=(TextView)findViewById(R.id.Feature_text);
        text_Tech_spec=(TextView)findViewById(R.id.technical_text);
        more_Info_text=(TextView)findViewById(R.id.more_Info_text);
        btn_wherebuy=(Button)findViewById(R.id.btn_wherebuy);
        btn_wherebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(BTLProdcut_Detailpage.this);
                app.setProduct_Sort("");
                app.setFilter_Option("");
                app.setFilter_SubCat("");
                app.setFilter_Cat("");
                app.setFilter_Sort("");
                db = new DatabaseHandler(BTLProdcut_Detailpage.this);
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(BTLProdcut_Detailpage.this, Where_To_Buy.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        tv_product_mrp.setPaintFlags(tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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
        }
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
                Intent i = new Intent(BTLProdcut_Detailpage.this,Where_toBuy.class);

                startActivity(i);
            }
        });*/
        img_prodetail_shoppinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTLProdcut_Detailpage.this,Add_to_shoppinglist.class);
                app.setproduct_id(pid);
                startActivity(i);
            }
        });

        /*img_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTLProdcut_Detailpage.this, Event_Gallery_photo_view_Activity.class);
                app.setproduct_id(pid);
                //Log.e("img_full",""+pid);
                startActivity(i);
            }
        });*/

        setRefershData();
        if(user_data.size() != 0){


            img_prodetail_wishlist.setVisibility(View.VISIBLE);
            img_prodetail_shoppinglist.setVisibility(View.VISIBLE);
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
            }

            for (int i = 0; i < user_data.size(); i++) {

                user_id_main =user_data.get(i).getUser_id().toString();

            }

        }else{

            img_prodetail_wishlist.setVisibility(View.GONE);
            img_prodetail_shoppinglist.setVisibility(View.GONE);
        }

        img_prodetail_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(img_prodetail_wishlist.getTag().equals(R.drawable.whishlist)){

                 //   Globals.CustomToast(BTLProdcut_Detailpage.this,"add",getLayoutInflater());
                    new set_wish_list().execute();
                    img_prodetail_wishlist.setTag(R.drawable.whishlist_fill);
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist_fill);
                    wishid ="1";
                }
                else if(img_prodetail_wishlist.getTag().equals(R.drawable.whishlist_fill)) {
                  //  Globals.CustomToast(BTLProdcut_Detailpage.this,"delete",getLayoutInflater());
                    new delete_wish_list().execute();
                    img_prodetail_wishlist.setTag(R.drawable.whishlist);
                    img_prodetail_wishlist.setImageResource(R.drawable.whishlist);
                    wishid ="2";
                }

            }
        });

    }


    private void setLayout(ArrayList<String> str1) {
        // TODO Auto-generated method stub

        layout_imagegrid.removeAllViews();
        //Log.e("1", str1.size() + "");

        for (int ij = 0; ij < str1.size(); ij++) {

            //Log.e("2", ij + "");
            LinearLayout.LayoutParams par12 = new LinearLayout.LayoutParams(100, 100);
            LinearLayout lmain = new LinearLayout(BTLProdcut_Detailpage.this);
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
                    100, 100);

            LinearLayout l1 = new LinearLayout(BTLProdcut_Detailpage.this);
            l1.setLayoutParams(par1);
            l1.setPadding(5, 0, 5, 0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(BTLProdcut_Detailpage.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 0, 5, 0);

            final LinearLayout l2 = new LinearLayout(BTLProdcut_Detailpage.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 0, 5, 0);
            l2.setVisibility(View.GONE);
            final ImageView img_a = new ImageView(BTLProdcut_Detailpage.this);
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            par21.setMargins(5, 5, 5, 5);
           // imageloader.DisplayImage(Globals.server_link + "files/" + str1.get(ij),  img_a);
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
                    //Log.e("IMAGETAG",""+img_a.getTag().toString());
                   // imageloader.DisplayImage(img_a.getTag().toString(), img_full);
                    Picasso.with(getApplicationContext())
                            .load(img_a.getTag().toString())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_full);


                }
            });
            lmain.addView(img_a);
            layout_imagegrid.addView(lmain);



        }
    }

   
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

            try{

               // imageloader.DisplayImage(ImagesArray.get(position), imageView);
                Picasso.with(getApplicationContext())
                        .load(ImagesArray.get(position))
                        .placeholder(R.drawable.btl_watermark)
                        .into(imageView);
                //Log.e("", "" + ImagesArray.get(position));
            }catch(Exception e)
            {
                //Log.e("",""+ImagesArray.get(position));
            }

           /* imageView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {

                    Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(BTLProdcut_Detailpage.this,Event_Gallery_photo_view_Activity.class);
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

    public void SetRefershDataProduct(){
        // TODO Auto-generated method stub
        bean_product1.clear();
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Bean_Product> user_array_from_db = db.Get_Product();



        for (int i = 0; i < user_array_from_db.size(); i++) {

            int Pro_id =user_array_from_db.get(i).getId();

            String KEY_PRO_ID =user_array_from_db.get(i).getPro_id();
            String KEY_PRO_CODE = user_array_from_db.get(i).getPro_code();
            String KEY_PRO_NAME =user_array_from_db.get(i).getPro_name();
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
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Bean_ProductCart> user_array_from_db = db.Get_Product_Cart();



        for (int i = 0; i < user_array_from_db.size(); i++) {

            int cart_id =user_array_from_db.get(i).getId();
            String KEY_CART_PRO_ID =user_array_from_db.get(i).getPro_id();
            String KEY_CART_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_CART_PRO_IMAGES =user_array_from_db.get(i).getPro_Images();
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
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Bean_ProductFeature> user_array_from_db = db.Get_ProductFeature();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int feature_id =user_array_from_db.get(i).getId();
            String KEY_FEATURE_PRO_ID =user_array_from_db.get(i).getPro_id();
            String KEY_FEATURE_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_FEATURE_PRO_FETURE_ID =user_array_from_db.get(i).getPro_feature_id();
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
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Bean_ProductImage> user_array_from_db = db.Get_ProductImage();



        for (int i = 0; i < user_array_from_db.size(); i++) {



            int image_id =user_array_from_db.get(i).getId();
            String KEY_IMAGE_PRO_ID =user_array_from_db.get(i).getPro_id();
            String KEY_IMAGE_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_IMAGE_PRO_IMAGES =user_array_from_db.get(i).getPro_Images();



            Bean_ProductImage contact = new Bean_ProductImage();
            contact.setId(image_id);
            contact.setPro_id(KEY_IMAGE_PRO_ID);
            contact.setPro_cat_id(KEY_IMAGE_PRO_CAT_ID);
            contact.setPro_Images(KEY_IMAGE_PRO_IMAGES);


            bean_productImages.add(contact);


        }
        db.close();
    }

    private void setRefershDataOption() {
        // TODO Auto-generated method stub
        bean_productCart.clear();
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Bean_ProductOprtion> user_array_from_db = db.Get_Product_Option();



        for (int i = 0; i < user_array_from_db.size(); i++) {

            int option_id =user_array_from_db.get(i).getId();
            String KEY_OPTION_PRO_ID =user_array_from_db.get(i).getPro_id();
            String KEY_OPTION_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_OPTION_PRO_OPTION_ID =user_array_from_db.get(i).getPro_Option_id();
            String KEY_OPTION_NAME =user_array_from_db.get(i).getPro_Option_name();
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
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Bean_ProductStdSpec> user_array_from_db = db.Get_Productstdspec();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {


            int stdspec_id =user_array_from_db.get(i).getId();
            String KEY_STDSPEC_PRO_ID =user_array_from_db.get(i).getPro_id();
            String KEY_STDSPEC_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_STDSPEC_STDSPEC_ID =user_array_from_db.get(i).getPro_Stdspec_id();
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
   /* private void setRefershDataYouTube() {
        // TODO Auto-generated method stub
        bean_productyoutube.clear();
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Product_Youtube> user_array_from_db = db.Get_ProductYoutube();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {



            int youtube_id =user_array_from_db.get(i).getId();
            String KEY_YOUTUBE_PRO_ID =user_array_from_db.get(i).getPro_id();
            String KEY_YOUTUBE_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_YOUTUBE_URL = user_array_from_db.get(i).getPro_youtube_url();


            Product_Youtube contact = new Product_Youtube();

            contact.setId(youtube_id);
            contact.setPro_id(KEY_YOUTUBE_PRO_ID);
            contact.setPro_cat_id(KEY_YOUTUBE_PRO_CAT_ID);
            contact.setPro_youtube_url(KEY_YOUTUBE_URL);


            bean_productyoutube.add(contact);


        }
        db.close();
    }

    private void setRefershDataTechDesc() {
        // TODO Auto-generated method stub
        bean_productTechSpecs.clear();
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Bean_ProductTechSpec> user_array_from_db = db.Get_Producttechspec();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {



            int techdesc_id =user_array_from_db.get(i).getId();
            String KEY_TECHSPEC_PRO_ID =user_array_from_db.get(i).getPro_id();
            String KEY_TECHSPEC_PRO_CAT_ID = user_array_from_db.get(i).getPro_cat_id();
            String KEY_TECHSPEC_TECHSPEC_ID =user_array_from_db.get(i).getPro_Techspec_id();
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
    }*/

    public class set_marquee extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        BTLProdcut_Detailpage.this, "");

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

                    Globals.CustomToast(BTLProdcut_Detailpage.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(BTLProdcut_Detailpage.this, "" + Message, getLayoutInflater());

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



          mPager.setAdapter(new SlidingImage_Adapter_pDetail(BTLProdcut_Detailpage.this, ImagesArray));


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


            ImageView  ia = (ImageView) view.findViewById(R.id.img_flipperimag);

            try
            {
                //imageloader.DisplayImage(bean_productImages.get(position).getPro_Images(),  ia);
                Picasso.with(getApplicationContext())
                        .load(bean_productImages.get(position).getPro_Images())
                        .placeholder(R.drawable.btl_watermark)
                        .into(ia);

            }catch(Exception e)
            {
                //Log.e("Error",e.toString());
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
            return arg0 == ((View) arg1);

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

            LinearLayout lmain = new LinearLayout(BTLProdcut_Detailpage.this);
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
            LinearLayout l1 = new LinearLayout(BTLProdcut_Detailpage.this);

            l1.setLayoutParams(par1);
            l1.setPadding(5, 5, 5, 5);
            l1.setOrientation(LinearLayout.VERTICAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(BTLProdcut_Detailpage.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 5, 5, 5);

            final LinearLayout l2 = new LinearLayout(BTLProdcut_Detailpage.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 5, 5, 5);
            l2.setVisibility(View.GONE);

            count = ij;
            final ImageView img_a = new ImageView(BTLProdcut_Detailpage.this);
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

                    AppPrefs app= new AppPrefs(BTLProdcut_Detailpage.this);

                    //Log.e("url_tonext_page",""+str.get(Integer.parseInt(img_a.getTag().toString())).getPro_youtube_url());
                    Intent i = new Intent(BTLProdcut_Detailpage.this,Video_View_Activity.class);
                    app.setyoutube_api(str.get(Integer.parseInt(img_a.getTag().toString())).getPro_youtube_url());
                    startActivity(i);
                }
            });
            lmain.addView(l1);

            l_video_lower.addView(lmain);

        }

    }*/
    public class MyAdapter extends ArrayAdapter<Bean_Attribute> {

        ArrayList<Bean_Attribute>  array;
        int k =0;
        public  MyAdapter(Context context, int textViewResourceId,   ArrayList<Bean_Attribute> objects) {
            super(context, textViewResourceId, objects);
            this.array = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.textview, parent, false);

            TextView label=(TextView)row.findViewById(R.id.tv_row);
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
   /* private class DownloadFile extends AsyncTask<String, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {

                loadingView = new Custom_ProgressDialog(
                        BTLProdcut_Detailpage.this, "");

                loadingView.setCancelable(false);
                loadingView.show();
            } catch (Exception e) {

            }

        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf

            File dir = new File(Environment.getExternalStorageDirectory() + "/BOSS");
            if(dir.exists() && dir.isDirectory()) {
                // do something here
                String extStorageDirectory1 = Environment.getExternalStorageDirectory().toString();
                File folder1 = new File(extStorageDirectory1, "BOSS");
                //  folder1.delete();
                if (folder1.isDirectory()) {
                    String[] children = folder1.list();
                    for (int i = 0; i < children.length; i++) {
                        new File(folder1, children[i]).delete();
                    }
                }
            }


            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "BOSS");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            loadingView.dismiss();

            File file = new File(Environment.getExternalStorageDirectory()+"/BOSS/"+"ProductDetail.pdf");
            PackageManager packageManager = getPackageManager();
            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            testIntent.setType("application/pdf");
            List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);

            return null;
        }


    }*/
    public class set_wish_list extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        BTLProdcut_Detailpage.this, "");

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
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_WishList", ServiceHandler.POST, parameters);
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

                    Globals.CustomToast(BTLProdcut_Detailpage.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(BTLProdcut_Detailpage.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTLProdcut_Detailpage.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();

                      /*  page_id =1;
                        page_limit = "";*/

                        bean_product1.clear();
                        bean_productTechSpecs.clear();
                        bean_productOprtions.clear();
                        bean_productFeatures.clear();
                        bean_productStdSpecs.clear();

                        //  new get_Product().execute();
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
            }catch(Exception ja){
                ja.printStackTrace();


            }

        }

    }

    public class delete_wish_list extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        BTLProdcut_Detailpage.this, "");

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

                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_DeleteWishList", ServiceHandler.POST, parameters);
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

                    Globals.CustomToast(BTLProdcut_Detailpage.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(BTLProdcut_Detailpage.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(BTLProdcut_Detailpage.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();

                      /*  page_id =1;
                        page_limit = "";*/

                        bean_product1.clear();
                        bean_productTechSpecs.clear();
                        bean_productOprtions.clear();
                        bean_productFeatures.clear();
                        bean_productStdSpecs.clear();

                        //  new get_Product().execute();
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
            }catch(Exception ja){
                ja.printStackTrace();


            }

        }

    }
    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid =user_array_from_db.get(i).getId();
            String user_id =user_array_from_db.get(i).getUser_id();
            String email_id = user_array_from_db.get(i).getEmail_id();
            String phone_no =user_array_from_db.get(i).getPhone_no();
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
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        img_notification.setVisibility(View.GONE);
        frame.setVisibility(View.VISIBLE);
        txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        app = new AppPrefs(BTLProdcut_Detailpage.this);
        String qun =app.getCart_QTy();

        setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                 role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(BTLProdcut_Detailpage.this);
                     role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else{
                    u_id=owner_id;
                }


            }

            List<NameValuePair> para=new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id",u_id));

            new GetCartByQty(para).execute();



        }else{
            app = new AppPrefs(BTLProdcut_Detailpage.this);
            app.setCart_QTy("");
        }

        app = new AppPrefs(BTLProdcut_Detailpage.this);
        String qu1 = app.getCart_QTy();
        if(qu1.equalsIgnoreCase("0") || qu1.equalsIgnoreCase("")){
            txt.setVisibility(View.GONE);
            txt.setText("");
        }else{
            if(Integer.parseInt(qu1) > 999){
                txt.setText("999+");
                txt.setVisibility(View.VISIBLE);
            }else {
                txt.setText(qu1 + "");
                txt.setVisibility(View.VISIBLE);
            }
        }
       /* db = new DatabaseHandler(BTLProdcut_Detailpage.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        if(bean_cart.size() == 0){
            txt.setVisibility(View.GONE);
            txt.setText("");
        }else{
            txt.setVisibility(View.VISIBLE);
            txt.setText(bean_cart.size()+"");
        }*/

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean_productImages.clear();
                db= new DatabaseHandler(BTLProdcut_Detailpage.this);
                db.Delete_ALL_table();
                db.close();
                app= new AppPrefs(BTLProdcut_Detailpage.this);
                app.setwishid("");
                if(app.getRef_Detail().toString().equalsIgnoreCase("ProductList")) {
                    Intent i = new Intent(BTLProdcut_Detailpage.this, Product_List.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else if(app.getRef_Detail().toString().equalsIgnoreCase("Search")) {
                    Intent i = new Intent(BTLProdcut_Detailpage.this, Search.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db= new DatabaseHandler(BTLProdcut_Detailpage.this);
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(BTLProdcut_Detailpage.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db= new DatabaseHandler(BTLProdcut_Detailpage.this);
                db.Delete_ALL_table();
                db.close();
                Intent i = new Intent(BTLProdcut_Detailpage.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                app = new AppPrefs(BTLProdcut_Detailpage.this);
                app.setUser_notification("product_detail");
                Intent i = new Intent(BTLProdcut_Detailpage.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(BTLProdcut_Detailpage.this);
                app.setUser_notification("product_detail");
                Intent i = new Intent(BTLProdcut_Detailpage.this, BTL_Cart.class);
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
        db= new DatabaseHandler(BTLProdcut_Detailpage.this);
        db.Delete_ALL_table();
        db.close();
        app= new AppPrefs(BTLProdcut_Detailpage.this);
        app.setwishid("");
        Intent i = new Intent(BTLProdcut_Detailpage.this, Product_List.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    };

    public void webview_popup (String html_code)
    {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;


        dialog = new Dialog(BTLProdcut_Detailpage.this);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.webview_popup);
        dialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 5);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        WebView webview_product_details = (WebView) dialog.findViewById(R.id.webview_product_details);

        webview_product_details.loadData("<HTML><body> " + html_code + " </body></HTML>", "text/html", null);

        dialog.show();

    }

    public void SetRefershDataProductDesc(){
        // TODO Auto-generated method stub
        bean_desc_db.clear();
        db = new DatabaseHandler(BTLProdcut_Detailpage.this);

        ArrayList<Bean_Desc> user_array_from_db = db.Get_ProductDesc();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int Pro_id =user_array_from_db.get(i).getId();

            String KEY_PRO_ID =user_array_from_db.get(i).getPro_id();
            String KEY_VALUE_D_DESCRIPTION = user_array_from_db.get(i).getDescription();
            String KEY_VALUE_D_SPECIFICATION =user_array_from_db.get(i).getSpecification();
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
        db= new DatabaseHandler(BTLProdcut_Detailpage.this);
        db.Delete_ALL_table();
        db.close();
        app= new AppPrefs(BTLProdcut_Detailpage.this);
        app.setwishid("");
        if(app.getRef_Detail().toString().equalsIgnoreCase("ProductList")) {
            Intent i = new Intent(BTLProdcut_Detailpage.this, Product_List.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else if(app.getRef_Detail().toString().equalsIgnoreCase("Search")) {
            Intent i = new Intent(BTLProdcut_Detailpage.this, Search.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    public String GetCartByQty(final List<NameValuePair> params){

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
    }

    public class GetCartByQty extends AsyncTask<Void, Void, String>{

        List<NameValuePair> params=new ArrayList<>();

        public GetCartByQty(List<NameValuePair> params){
            hasCartCallFinish=true;
            this.params=params;
        }

        @Override
        protected String doInBackground(Void... param) {



            Globals.generateNoteOnSD(getApplicationContext(),"CartData/App_GetCartQty"+"\n"+params.toString());

            String json=new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty",ServiceHandler.POST,params);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            cartJSON=json;
            Globals.generateNoteOnSD(getApplicationContext(),cartJSON);
            try {


                //System.out.println(json);

                if (json==null
                        || (json.equalsIgnoreCase(""))) {

                    Globals.CustomToast(BTLProdcut_Detailpage.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);
                    String userStatus = jObj.getString("user_status");
                    if (userStatus.equals("0")) {
                        C.userInActiveDialog(BTLProdcut_Detailpage.this);
                    }
                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(BTLProdcut_Detailpage.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(BTLProdcut_Detailpage.this);
                        app.setCart_QTy(""+qu);


                    }

                }


            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = app.getCart_QTy();
            if(qu1.equalsIgnoreCase("0") || qu1.equalsIgnoreCase("")){
                txt.setVisibility(View.GONE);
                txt.setText("");
            }else{
                if(Integer.parseInt(qu1) > 999){
                    txt.setText("999+");
                    txt.setVisibility(View.VISIBLE);
                }else {
                    txt.setText(qu1 + "");
                    txt.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
