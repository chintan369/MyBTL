package com.agraeta.user.btl;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.adapters.ExpandableSchemeAdapter;
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

public class ProductSearchForQuotationActivity extends AppCompatActivity {

    ImageView minuss, plus;
    Button buy_cart, cancel;
    EditText edt_count;
    Dialog dialog, dialogOffer;
    String product_id = new String();
    double amount1;
    String pro_id = new String();
    ArrayList<Bean_Value_Selected_Detail> array_value = new ArrayList<Bean_Value_Selected_Detail>();

    View footerLoading;

    boolean whereToBuyVisibility = false;

    String json = new String();
    ArrayList<Bean_Product> bean_product_array = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductImage> bean_productimage_array = new ArrayList<Bean_ProductImage>();
    ArrayList<Bean_ProductFeature> bean_productfeatures_array = new ArrayList<Bean_ProductFeature>();
    ArrayList<Bean_ProductTechSpec> bean_producttech_array = new ArrayList<Bean_ProductTechSpec>();
    ArrayList<Bean_ProductOprtion> bean_productoption_array = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductStdSpec> bean_productstud_array = new ArrayList<Bean_ProductStdSpec>();
    Search.ResultHolder result_holder;
    AppPrefs app;
    ArrayList<Bean_Product> bean_product_schme = new ArrayList<Bean_Product>();
    ArrayList<Bean_Schme_value> bean_schme = new ArrayList<Bean_Schme_value>();
    String page_limit = new String();
    private ArrayList<Integer> image_product = new ArrayList<Integer>();

    ImageLoader imageloader;
    int page_id = 1;
    int s = 0;
    ArrayList<Bean_Product> bean_product_db = new ArrayList<Bean_Product>();
    ArrayList<Bean_ProductFeature> bean_productFeatures_db = new ArrayList<Bean_ProductFeature>();
    ArrayList<Bean_ProductTechSpec> bean_productTechSpecs_db = new ArrayList<Bean_ProductTechSpec>();
    ArrayList<Bean_ProductStdSpec> bean_productStdSpecs_db = new ArrayList<Bean_ProductStdSpec>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions_db = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductCart> bean_productCart_db = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_ProductImage> bean_productImages_db = new ArrayList<Bean_ProductImage>();
    TextView txt_selling_price, txt_total_txt;
    String role;
    AutoCompleteTextView Search_actv;
    ArrayList<String> list_of_images = new ArrayList<String>();
    ListView List_search;
    ImageView Search_image;
    TextView tv_pop_pname, tv_pop_code, tv_pop_packof, tv_pop_mrp, tv_pop_sellingprice, tv_total, tv_txt_mrp;
    DatabaseHandler db;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_Product> bean_product1 = new ArrayList<Bean_Product>();

    ArrayList<Bean_Desc> bean_desc1 = new ArrayList<Bean_Desc>();
    ArrayList<Bean_Desc> bean_desc_db = new ArrayList<Bean_Desc>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ArrayList<Bean_ProductFeature> bean_productFeatures = new ArrayList<Bean_ProductFeature>();
    ArrayList<Bean_ProductTechSpec> bean_productTechSpecs = new ArrayList<Bean_ProductTechSpec>();
    ArrayList<Bean_ProductStdSpec> bean_productStdSpecs = new ArrayList<Bean_ProductStdSpec>();
    ArrayList<Bean_ProductOprtion> bean_productOprtions = new ArrayList<Bean_ProductOprtion>();
    ArrayList<Bean_ProductCart> bean_productCart = new ArrayList<Bean_ProductCart>();
    ArrayList<Bean_ProductImage> bean_productImages = new ArrayList<Bean_ProductImage>();
    public static ArrayList<String> arrOptionTypeID = new ArrayList<String>();
    public static ArrayList<String> arrOptionTypeName = new ArrayList<String>();
    ArrayList<Bean_Attribute> array_att = new ArrayList<Bean_Attribute>();
    String option_name = "";
    String option_id = "";
    String searchproduct;
    String user_id = new String();
    String Catid = new String();
    LinearLayout l_spinner, l_spinner_text, l_linear, l_sort;
    public static ArrayList<String> WishList = new ArrayList<String>();
    String CategoryID;
    ArrayList<ArrayList<Bean_Attribute>> array_attribute_main = new ArrayList<ArrayList<Bean_Attribute>>();
    Custom_ProgressDialog loadingView;
    String user_id_main = new String();
    String role_id = new String();
    LinearLayout l_view_spinner;
    String proW_id = new String();
    String p_code = new String();
    LinearLayout l_mrp, l_sell, l_total;
    int current_record = 0;
    int last_record = 0;
    String cartJSON = "";
    boolean hasCartCallFinish = true;

    TextView txt;


    String owner_id = new String();
    String u_id = new String();
    String qun = new String();
    int kkk = 0;
    ArrayList<Bean_ProductOprtion> bean_Oprtions = new ArrayList<Bean_ProductOprtion>();
    JSONArray jarray_cart = new JSONArray();
    ArrayList<Bean_schemeData> bean_Schme_data = new ArrayList<Bean_schemeData>();
    ArrayList<Bean_schemeData> bean_S_data = new ArrayList<Bean_schemeData>();
    String rolee = new String();
    private String jsonData = "";
    boolean isNotDone = true;

    ArrayList<String> searchItemIDs = new ArrayList<>();
    ArrayList<String> searchItemNames = new ArrayList<>();
    ArrayAdapter<String> searchItemAdapter;
    private String quotationID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search_for_quotation);

        Intent intent=getIntent();
        quotationID=intent.getStringExtra("quotationID");

        footerLoading = getLayoutInflater().inflate(R.layout.list_footer_loading, null);


        dialog = new Dialog(ProductSearchForQuotationActivity.this);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        dialog.setCanceledOnTouchOutside(false);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogOffer = new Dialog(ProductSearchForQuotationActivity.this);
        dialogOffer.getWindow();
        dialogOffer.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOffer.setCancelable(false);

        setRefershData();
        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id().toString();
                role_id = user_data.get(i).getUser_type().toString();
                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(ProductSearchForQuotationActivity.this);
                    role_id = app.getSubSalesId().toString();
                    user_id_main = app.getSalesPersonId().toString();
                }

            }

        } else {
            user_id_main = "";
        }

        setActionBar();
        fetchId();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    private void fetchId() {
        Search_actv = (AutoCompleteTextView) findViewById(R.id.Search_Actv);
        Search_actv.setThreshold(1);

        List_search = (ListView) findViewById(R.id.List_search);
        Search_image = (ImageView) findViewById(R.id.image_search_pro);
        List_search.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                int totalListItems = List_search.getCount();
                int lastVisiblePosition = List_search.getLastVisiblePosition();

                if (lastVisiblePosition == (totalListItems - 1)) {
                    if (page_id < Integer.parseInt(page_limit)) {
                        page_id++;
                        List_search.addFooterView(footerLoading);
                        new get_Product(false).execute();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        app = new AppPrefs(ProductSearchForQuotationActivity.this);
        if (app.get_search().equalsIgnoreCase("")) {
            Search_actv.setText(app.get_search().toString());
        } else {
            Search_actv.setText(app.get_search().toString());
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            searchproduct = app.get_search();
            page_id = 1;
            new get_Product().execute();
            bean_product1.clear();
        }

        Search_actv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    if (Search_actv.getText().toString().equalsIgnoreCase("")
                            ) {

                        app = new AppPrefs(ProductSearchForQuotationActivity.this);
                        app.set_search("");
                        Globals.CustomToast(ProductSearchForQuotationActivity.this, "Please Enter Product Name", getLayoutInflater());

                    } else {
                        getWindow().setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                        searchproduct = Search_actv.getText().toString();
                        app = new AppPrefs(ProductSearchForQuotationActivity.this);
                        app.set_search(searchproduct);
                        page_id = 1;
                        new get_Product().execute();
                        bean_product1.clear();
                    }
                    return true;
                }
                return false;
            }
        });

        Search_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Search_actv.getText().toString().equalsIgnoreCase("")
                        ) {
                    app = new AppPrefs(ProductSearchForQuotationActivity.this);
                    app.set_search("");
                    Globals.CustomToast(ProductSearchForQuotationActivity.this, "Please Enter Product Name", getLayoutInflater());

                } else {
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    searchproduct = Search_actv.getText().toString();
                    app = new AppPrefs(ProductSearchForQuotationActivity.this);
                    app.set_search(searchproduct);
                    page_id = 1;
                    new get_Product().execute();
                    bean_product1.clear();
                }

            }
        });
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(ProductSearchForQuotationActivity.this);

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
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);

        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        img_notification.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        // img_cart.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        img_home.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        txt.setVisibility(View.GONE);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onBackPressed() {
        AppPrefs prefs=new AppPrefs(this);
        prefs.set_search("");
        setResult(RESULT_OK);
        finish();
    }

    public class get_Product extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        boolean isFirstTime = true;

        public get_Product() {
        }

        public get_Product(boolean isFirstTime) {
            this.isFirstTime = isFirstTime;
        }

        protected void onPreExecute() {
            super.onPreExecute();

            if (isFirstTime) {
                try {
                    loadingView = new Custom_ProgressDialog(
                            ProductSearchForQuotationActivity.this, "");

                    loadingView.setCancelable(false);
                    loadingView.show();

                } catch (Exception e) {

                }
            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("filter_name", searchproduct));
                parameters.add(new BasicNameValuePair("user_id", user_id_main));
                parameters.add(new BasicNameValuePair("role_id", role_id));
                parameters.add(new BasicNameValuePair("page", "" + page_id));

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Search_ProductList", ServiceHandler.POST, parameters);

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

            if (isFirstTime) {
                bean_product1.clear();
                bean_productTechSpecs.clear();
                bean_productOprtions.clear();
                bean_productFeatures.clear();
                bean_productStdSpecs.clear();
            }

            try {

                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(ProductSearchForQuotationActivity.this, "SERVER ERRER", getLayoutInflater());


                    if (!isFirstTime) {
                        List_search.removeFooterView(footerLoading);
                    } else {
                        loadingView.dismiss();
                    }


                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(ProductSearchForQuotationActivity.this, "" + Message, getLayoutInflater());

                        if (!isFirstTime) {
                            List_search.removeFooterView(footerLoading);
                        } else {
                            loadingView.dismiss();
                        }
                    } else {


                        page_limit = jObj.getString("total_page_count");
                        //Log.e("page_limit",""+page_limit);
                        JSONArray jsonwish = jObj.optJSONArray("wish_list");
                        whereToBuyVisibility = jObj.getString("where_to_buy").equals("1");

                        WishList = new ArrayList<String>();

                        for (int i = 0; i < jsonwish.length(); i++) {
                            WishList.add(jsonwish.get(i).toString());
                        }

                        JSONObject jobj = new JSONObject(result_1);


                        if (jobj != null) {


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

                                    Bean_schemeData beans = new Bean_schemeData();
                                    beans.setSchme_id(jProductScheme.getString("id"));
                                    beans.setSchme_name(jProductScheme.getString("scheme_name"));
                                    beans.setSchme_qty(jProductScheme.getString("buy_prod_qty"));
                                    beans.setCategory_id(jProductScheme.getString("category_id"));
                                    beans.setSchme_buy_prod_id(jProductScheme.getString("buy_prod_id"));
                                    beans.setSchme_prod_id(jproduct.getString("id"));

                                    Log.e("prod", "" + jproduct.getString("id"));
                                    Log.e("prod", "" + jProductScheme.getString("scheme_name"));

                                    bean_Schme_data.add(beans);


                                    ArrayList<String> a = new ArrayList<String>();
                                    a.add(jProductScheme.getString("scheme_name"));
                                    //Log.e("A2222222",""+jProductScheme.getString("scheme_name"));
                                    sa += jProductScheme.getString("scheme_name").toString() + "\n";
                                    bean.setSchemea(a);
                                }


                                //Log.e("A3333333",""+sa);
                                bean.setScheme(sa);
                                JSONObject jlabel = jsonObject.getJSONObject("Label");
                                bean.setPro_label(jlabel.getString("name"));
                                bean_product1.add(bean);
                                Bean_Desc bean1 = new Bean_Desc();
                                bean1.setPro_id(jproduct.getString("id"));
                                //   Toast.makeText(ProductSearchForQuotationActivity.this, ""+bean1.getPro_id()+"-"+jproduct.getString("id"), Toast.LENGTH_SHORT).show();
                                bean1.setDescription(jproduct.getString("description"));
                                bean1.setSpecification(jproduct.getString("specification"));
                                bean1.setTechnical(jproduct.getString("technical"));
                                bean1.setCatalogs(jproduct.getString("catalogs"));
                                bean1.setGuarantee(jproduct.getString("guarantee"));
                                bean1.setVideos(jproduct.getString("videos"));
                                bean1.setGeneral(jproduct.getString("general"));
                                bean_desc1.add(bean1);

                                JSONArray jProductOption = jsonObject.getJSONArray("ProductOption");


                                for (int s = 0; s < jProductOption.length(); s++) {
                                    JSONObject jProductOptiono = jProductOption.getJSONObject(s);
                                    JSONObject productObject=jProductOptiono.getJSONObject("Product");
                                    JSONObject jPOOption = jProductOptiono.getJSONObject("Option");
                                    JSONObject jPOOptionValue = jProductOptiono.getJSONObject("OptionValue");

                                    JSONArray jPOProductOptionImage = jProductOptiono.getJSONArray("ProductOptionImage");


                                    Bean_ProductOprtion beanoption = new Bean_ProductOprtion();
                                    // arrOptionType=new ArrayList<String>();


                                    //Option
                                    beanoption.setPro_Option_id(jPOOption.getString("id"));
                                    beanoption.setPro_Option_name(jPOOption.getString("name"));
                                    Log.e("1223", "" + jPOOption.getString("name"));

                                    beanoption.setMinPackOfQty(Integer.parseInt(productObject.getString("pack_of_qty")));

                                    //OptionValue
                                    beanoption.setPro_Option_value_name(jPOOptionValue.getString("name"));
                                    Log.e("1223", "" + jPOOptionValue.getString("name"));
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
                                    db = new DatabaseHandler(ProductSearchForQuotationActivity.this);
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
                                            if (bean_product_db.get(b).getPro_id().toString().equalsIgnoreCase(bean_product1.get(a).getPro_id())) {

                                            } else {
                                                db = new DatabaseHandler(ProductSearchForQuotationActivity.this);
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
                                    db = new DatabaseHandler(ProductSearchForQuotationActivity.this);
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
                                                db = new DatabaseHandler(ProductSearchForQuotationActivity.this);
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
                                    db = new DatabaseHandler(ProductSearchForQuotationActivity.this);
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
                                                db = new DatabaseHandler(ProductSearchForQuotationActivity.this);
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
                                    db = new DatabaseHandler(ProductSearchForQuotationActivity.this);
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
                                                db = new DatabaseHandler(ProductSearchForQuotationActivity.this);
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
                        }
                        int firstVisPos = List_search.getFirstVisiblePosition();
                        //  List_search.setSelection(firstVisPos);
                        List_search.setVisibility(View.VISIBLE);
                        if (!isFirstTime) {
                            List_search.removeFooterView(footerLoading);
                        } else {
                            loadingView.dismiss();
                        }

                        List_search.setAdapter(new CustomResultAdapterDoctor());
                        List_search.setSelection(current_record);
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    private void SetRefershDataProduct() {
        // TODO Auto-generated method stub
        bean_product_db.clear();
        db = new DatabaseHandler(ProductSearchForQuotationActivity.this);

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


            bean_product_db.add(contact);


        }
        db.close();
    }

    private void setRefershDataOption() {
        // TODO Auto-generated method stub
        bean_productOprtions_db.clear();
        db = new DatabaseHandler(ProductSearchForQuotationActivity.this);

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


            bean_productOprtions_db.add(contact);


        }
        db.close();
    }

    private void setRefershDataImage() {
        // TODO Auto-generated method stub
        bean_productImages_db.clear();
        db = new DatabaseHandler(ProductSearchForQuotationActivity.this);

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

    public void SetRefershDataProductDesc() {
        // TODO Auto-generated method stub
        bean_desc_db.clear();
        db = new DatabaseHandler(ProductSearchForQuotationActivity.this);

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

            result_holder = new Search.ResultHolder();

            //  if (convertView == null) {

            convertView = vi.inflate(R.layout.product_list_single, null);

            //   }

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
            result_holder.BTN_wheretobuy_list = (Button) convertView.findViewById(R.id.BTN_wheretobuy_list);
            result_holder.btn_enquiry = (Button) convertView.findViewById(R.id.btn_enquiry);
            result_holder.layout_prices = (LinearLayout) convertView.findViewById(R.id.layout_prices);
            result_holder.txt_selling = (TextView) convertView
                    .findViewById(R.id.txt_selling);
            result_holder.txt_pack = (TextView) convertView
                    .findViewById(R.id.txt_pack);
            result_holder.off_tag = (TextView) convertView.findViewById(R.id.off_tag);
            result_holder.img_offer = (ImageView) convertView.findViewById(R.id.img_offer);
            result_holder.BTN_wheretobuy_list.setTag(bean_product1.get(position).getPro_id().toString());
            setRefershData();


            result_holder.BTN_wheretobuy_list.setVisibility(View.GONE);

            if (user_data.size() != 0) {
                for (int i = 0; i < user_data.size(); i++) {

                    rolee = user_data.get(i).getUser_type().toString();

                }

            } else {
                user_id_main = "";
            }

            if (rolee.equalsIgnoreCase("6") || rolee.equalsIgnoreCase("7")) {
                result_holder.img_wish.setVisibility(View.GONE);
                result_holder.img_shopping.setVisibility(View.GONE);
            } else {
                result_holder.img_wish.setVisibility(View.VISIBLE);
                result_holder.img_shopping.setVisibility(View.VISIBLE);
            }

            if (user_data.size() != 0) {
                pro_id = result_holder.BTN_wheretobuy_list.getTag().toString();


                setRefershData();
                if (user_data.size() != 0) {
                    for (int i = 0; i < user_data.size(); i++) {

                        rolee = user_data.get(i).getUser_type().toString();

                    }

                } else {
                    user_id_main = "";
                }

                result_holder.txt_mrp.setVisibility(View.VISIBLE);
                result_holder.btn_buyonline.setVisibility(View.VISIBLE);
                result_holder.tvproduct_packof.setVisibility(View.VISIBLE);
                result_holder.tv_product_sellingprice.setVisibility(View.VISIBLE);
                result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                result_holder.off_tag.setVisibility(View.VISIBLE);
                result_holder.txt_selling.setVisibility(View.VISIBLE);
                // result_holder.BTN_wheretobuy_list.setVisibility(View.VISIBLE);
                result_holder.txt_pack.setVisibility(View.VISIBLE);


                for (int i = 0; i < user_data.size(); i++) {

                    user_id = user_data.get(i).getUser_id().toString();

                }

            } else {
                result_holder.img_wish.setVisibility(View.GONE);
                result_holder.img_shopping.setVisibility(View.GONE);
                result_holder.txt_mrp.setVisibility(View.VISIBLE);
                result_holder.btn_buyonline.setVisibility(View.VISIBLE);
                result_holder.tvproduct_packof.setVisibility(View.VISIBLE);
                result_holder.tv_product_sellingprice.setVisibility(View.VISIBLE);
                result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                result_holder.off_tag.setVisibility(View.VISIBLE);
                result_holder.txt_selling.setVisibility(View.VISIBLE);
                //  result_holder.BTN_wheretobuy_list.setVisibility(View.GONE);
                result_holder.txt_pack.setVisibility(View.VISIBLE);
            }

            result_holder.img_wish.setVisibility(View.GONE);
            result_holder.img_shopping.setVisibility(View.GONE);

            result_holder.btn_buyonline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setRefershData();

                    if (user_data.size() != 0) {
                        for (int i = 0; i < user_data.size(); i++) {

                            owner_id = user_data.get(i).getUser_id().toString();

                            role_id = user_data.get(i).getUser_type().toString();

                            if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                app = new AppPrefs(ProductSearchForQuotationActivity.this);
                                role_id = app.getSubSalesId().toString();
                                user_id_main = app.getSalesPersonId().toString();
                            } else {
                                user_id_main = owner_id;
                            }


                        }


                        // product_id = tv_pop_pname.getTag().toString();
                        final List<NameValuePair> para = new ArrayList<NameValuePair>();
                        para.add(new BasicNameValuePair("product_id", bean_product1.get(position).getPro_id().toString()));
                        para.add(new BasicNameValuePair("owner_id", owner_id));
                        para.add(new BasicNameValuePair("user_id", user_id_main));
                        para.add(new BasicNameValuePair("quotation_id", quotationID));

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
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                Globals.CustomToast(ProductSearchForQuotationActivity.this, "SERVER ERRER", getLayoutInflater());
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


                        dialog.setContentView(R.layout.popup_cart);
                        minuss = (ImageView) dialog.findViewById(R.id.minus);
                        plus = (ImageView) dialog.findViewById(R.id.plus);
                        buy_cart = (Button) dialog.findViewById(R.id.Btn_Buy_online11);
                        cancel = (Button) dialog.findViewById(R.id.cancel);
                        edt_count = (EditText) dialog.findViewById(R.id.edt_count);
                        edt_count.setSelection(edt_count.getText().length());

                        ImageView img_offerDialog = (ImageView) dialog.findViewById(R.id.img_offerDialog);

                        final TextView txt_availableScheme = (TextView) dialog.findViewById(R.id.txt_availableScheme);

                        if (bean_product1.get(position).getScheme() == null || bean_product1.get(position).getScheme().isEmpty()) {
                            img_offerDialog.setVisibility(View.GONE);
                        }

                        img_offerDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showOfferDialog(position);
                            }
                        });

                        final String[] productIDbyTag = {""};
                        l_view_spinner = (LinearLayout) findViewById(R.id.l_view_spinner);
                        final TextView tv_pop_pname = (TextView) dialog.findViewById(R.id.product_name);
                        tv_pop_code = (TextView) dialog.findViewById(R.id.product_code);
                        tv_pop_packof = (TextView) dialog.findViewById(R.id.product_packof);
                        tv_pop_mrp = (TextView) dialog.findViewById(R.id.product_mrp);
                        tv_txt_mrp = (TextView) dialog.findViewById(R.id.txt_mrp_text);
                        tv_pop_sellingprice = (TextView) dialog.findViewById(R.id.product_sellingprice);
                        tv_total = (TextView) dialog.findViewById(R.id.txt_total);
                        l_spinner = (LinearLayout) dialog.findViewById(R.id.l_spinner);
                        l_spinner_text = (LinearLayout) dialog.findViewById(R.id.l_spinnertext);
                        tv_pop_pname.setText(bean_product1.get(position).getPro_name().toString());
                        tv_pop_code.setText(" ( " + bean_product1.get(position).getPro_code().toString() + " )");
                        tv_pop_packof.setText(bean_product1.get(position).getPro_label().toString());
                        tv_pop_mrp.setText(bean_product1.get(position).getPro_mrp().toString());
                        tv_pop_sellingprice.setText(bean_product1.get(position).getPro_sellingprice().toString());
                        tv_pop_sellingprice.setTag(bean_product1.get(position).getPro_sellingprice().toString());
                        l_mrp = (LinearLayout) dialog.findViewById(R.id.l_mrp);
                        l_sell = (LinearLayout) dialog.findViewById(R.id.l_sell);
                        l_total = (LinearLayout) dialog.findViewById(R.id.l_total);
                        txt_selling_price = (TextView) dialog.findViewById(R.id.txt_selling_price);
                        txt_total_txt = (TextView) dialog.findViewById(R.id.txt_total_txt);
                        tv_total.setText(bean_product1.get(position).getPro_sellingprice().toString());
                        //  tv_total.setText(bean_product1.get(position).getPro_sellingprice().toString());
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
                        //float t = Float.parseFloat(productpricce);
                        double w1 = round(t, 2);
                        String str = String.format("%.2f", w1);
                        tv_total.setText(str);
                        int temp_count = Integer.parseInt(edt_count.getText().toString());
//                        int temp_total_count = temp_count + Integer.parseInt(array_product_cart.get(0).getPro_qty().toString());
//                        //Log.e("11121212121",""+temp_total_count);
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


                        arrOptionTypeID.clear();
                        arrOptionTypeName.clear();
                        arrOptionTypeID = new ArrayList<String>();
                        arrOptionTypeName = new ArrayList<String>();
                        if (bean_productOprtions.size() != 0) {
                            ArrayList<Bean_Attribute> array_attributes = new ArrayList<Bean_Attribute>();

                            //Log.e("121212 ::",""+bean_productOprtions.size());

                            final ArrayList<Bean_ProductOprtion> currentOption=new ArrayList<Bean_ProductOprtion>();

                            for (int c = 0; c < bean_productOprtions.size(); c++) {
                                //Log.e("232323 ::",""+bean_productOprtions.get(c).getPro_id());
                                //Log.e("343434 ::",""+bean_product1.get(position).getPro_id());
                                if (bean_product1.get(position).getPro_id().equalsIgnoreCase(bean_productOprtions.get(c).getPro_id())) {
                                    currentOption.add(bean_productOprtions.get(c));
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
                                    bean.setValue_name("Test");
                                    bean.setValue_mrp("00");
                                    array_value.add(bean);
                                }
                                for (int k = 0; k < array_attribute_main.size(); k++) {

                                    final TextView text = new TextView(ProductSearchForQuotationActivity.this);

                                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                    params1.setMargins(2, 2, 2, 2);

                                    text.setLayoutParams(params1);

                                    text.setTextColor(Color.parseColor("#000000"));

                                    text.setTextSize(12);

                                    l_spinner_text.addView(text);

                                    final Spinner spinner = new Spinner(ProductSearchForQuotationActivity.this);

                                    spinner.setBackgroundResource(R.drawable.spinner_border);

                                    spinner.setTag("" + k);

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                    params.setMargins(2, 2, 2, 2);

                                    spinner.setLayoutParams(params);

                                    spinner.setAdapter(new MyAdapter(ProductSearchForQuotationActivity.this, R.layout.textview, array_attribute_main.get(k)));

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

                                    if (array_attribute_main.size() > 0 && array_attribute_main.get(0).size() > 0) {
                                        productIDbyTag[0] = array_attribute_main.get(0).get(0).getOption_pro_id();
                                    }

                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view,
                                                                   int position1, long id) {
                                            // TODO Auto-generated method stub

                                            // position1=""+position;
                                            minPackOfQty[0] =currentOption.get(position1).getMinPackOfQty();
                                            int pos = Integer.parseInt(spinner.getTag().toString());
                                            ArrayList<Bean_Attribute> att_array = new ArrayList<Bean_Attribute>();
                                            att_array = array_attribute_main.get(pos);

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
                                                list_of_images.add(att_array.get(position1).getValue_image().toString());
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

                                                            app = new AppPrefs(ProductSearchForQuotationActivity.this);
                                                            role_id = app.getSubSalesId().toString();
                                                            u_id = app.getSalesPersonId().toString();
                                                        } else {
                                                            u_id = owner_id;
                                                        }


                                                    }

                                                } else {
                                                    user_id_main = "";
                                                }
                                                // product_id = tv_pop_pname.getTag().toString();
                                                List<NameValuePair> para = new ArrayList<NameValuePair>();
                                                para.add(new BasicNameValuePair("product_id", att_array.get(position1).getOption_pro_id().toString()));
                                                para.add(new BasicNameValuePair("owner_id", owner_id));
                                                para.add(new BasicNameValuePair("user_id", u_id));
                                                para.add(new BasicNameValuePair("quotation_id", quotationID));
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
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                                        Globals.CustomToast(ProductSearchForQuotationActivity.this, "SERVER ERROR", getLayoutInflater());
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


                                                            if (jobj != null) {


                                                                JSONArray jsonArray = jObj.getJSONArray("data");
                                                                for (int iu = 0; iu < jsonArray.length(); iu++) {
                                                                    JSONObject jsonObject = jsonArray.getJSONObject(iu);
                                                                    qun = jsonObject.getString("quantity");

                                                                }

                                                                //Log.e("131313131331",""+qun);
                                                                kkk = 1;


                                                            }


                                                        }

                                                    }
                                                } catch (Exception j) {
                                                    j.printStackTrace();
                                                    //Log.e("json exce",j.getMessage());
                                                }

                                                //final int minPackOfQty = bean_product1.get(position).getPackQty();
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

                                                productIDbyTag[0] = att_array.get(position1).getOption_pro_id();

                                                tv_pop_pname.setTag("" + att_array.get(position1).getOption_pro_id());
                                                if (att_array.get(position1).getValue_product_code().equalsIgnoreCase("null") || att_array.get(position1).getValue_product_code().equalsIgnoreCase("")) {
                                                    //Log.e("121212",""+att_array.get(position1).getValue_product_code().toString());
                                                    tv_pop_code.setText(" (" + p_code + ")");

                                                } else {
                                                    //Log.e("131313",""+att_array.get(position1).getValue_product_code().toString());
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
                                                        int temp_total_count = temp_count + Integer.parseInt(array_product_cart1.get(0).getPro_qty());
                                                        double temp_total = Double.parseDouble(tv_total.getText().toString());
                                                        edt_count.setText("" + temp_total_count);
                                                        //   tv_total.setText("" + (temp_total + Float.parseFloat(array_product_cart1.get(0).getPro_total().toString().replace("", ""))));
                                                        //result_holder.tvproduct_code.setText("ABC");
                                                    }
                                                }
                                                tv_pop_sellingprice.setTag(att_array.get(position1).getValue_selling_price());

                                                if (edt_count.getText().toString().equalsIgnoreCase("1")) {
                                                    double t = Double.parseDouble(att_array.get(position1).getValue_selling_price());
                                                    double w1 = round(t, 2);
                                                    String str = String.format("%.2f", w1);
                                                    tv_total.setText(str);

                                                } else {
                                                    int counter = Integer.parseInt(edt_count.getText().toString());
                                                    double amt = Double.parseDouble(tv_pop_sellingprice.getText().toString());
                                                    double total = amt * counter;
                                                    double w1 = round(total, 2);
                                                    //float finalValue = (float)(Math.round( total * 100 ) / 100);
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
                                        //float finalValue = (float)(Math.round( total * 100 ) / 100);
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
                                // float finalValue = (float)(Math.round( total * 100 ) / 100);
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
                                    double t = Double.parseDouble(productpricce);
                                    double w1 = round(t, 2);
                                    String str = String.format("%.2f", w1);
                                    tv_total.setText(str);
                                }*/

                            }

                            public void beforeTextChanged(CharSequence s, int start,
                                                          int count, int after) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                                String enteredQty = edt_count.getText().toString().trim();
                                int qty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);

                                if (qty <= 0) {
                                    buy_cart.setEnabled(false);
                                    tv_total.setText("0.00");
                                    //txt_availableScheme.setText("");
                                } else {
                                    buy_cart.setEnabled(true);
                                    //int minPackOfQty = bean_product1.get(position).getPackQty();

                                    if (qty >= minPackOfQty[0] && C.modOf(qty, minPackOfQty[0]) == 0) {
                                        String productpricce = tv_pop_sellingprice.getTag().toString();
                                        amount1 = Double.parseDouble(s.toString());
                                        amount1 = Double.parseDouble(productpricce) * amount1;
                                        //  float finalValue = (float)(Math.round( amount1 * 100 ) / 100);
                                        double w1 = round(amount1, 2);
                                        String str = String.format("%.2f", w1);
                                        tv_total.setText(str);
                                    } else {
                                        tv_total.setText("");
                                    }

                                    ArrayList<Bean_schemeData> currentProductScheme = new ArrayList<Bean_schemeData>();

                                    for (int i = 0; i < bean_Schme_data.size(); i++) {
                                        if (bean_Schme_data.get(i).getSchme_prod_id().equals(bean_product1.get(position).getPro_id())) {
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
                            }
                        });

                        buy_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                jarray_cart=new JSONArray();

                                if (user_data.size() == 0) {

                                    Intent i = new Intent(ProductSearchForQuotationActivity.this, LogInPage.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                } else {

                                    String enteredQty = edt_count.getText().toString().trim();
                                    int currentQty = Integer.parseInt(enteredQty.isEmpty() ? "0" : enteredQty);
                                    //int minPackOfQty = bean_product1.get(position).getPackQty();

                                    if (currentQty<minPackOfQty[0] || C.modOf(currentQty, minPackOfQty[0]) > 0) {
                                        C.showMinPackAlert(ProductSearchForQuotationActivity.this,minPackOfQty[0]);
                                        //Globals.Toast(getApplicationContext(), "Please enter quantity in multiple of "+minPackOfQty+" or tap + / - button");
                                        Log.e("Mod Rem", "-->" + C.modOf(currentQty, minPackOfQty[0]));
                                    } else {
                                        product_id = productIDbyTag[0]; //tv_pop_pname.getTag().toString();
                                        String qty = edt_count.getText().toString();

                                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("product_id", product_id));
                                        params.add(new BasicNameValuePair("user_id", user_id_main));
                                        params.add(new BasicNameValuePair("product_buy_qty", qty));
                                        //Log.e("111111111",""+product_id);
                                        //Log.e("222222222",""+user_id_main);
                                        //Log.e("333333333",""+qty);

                                        isNotDone = true;
                                        new GetProductDetailCode(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        while (isNotDone) {

                                        }
                                        isNotDone = true;
                                        String json = jsonData; //GetProductDetailByCode(params);
                                        jsonData = "";

                                        Log.e("Scheme JSON", "-->" + json);

                                        //new check_schme().execute();
                                        try {


                                            //System.out.println(json);

                                            if (json == null
                                                    || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                                Globals.CustomToast(ProductSearchForQuotationActivity.this, "SERVER ERROR", getLayoutInflater());
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


                                            bean.setPro_id(productIDbyTag[0]);//tv_pop_pname.getTag().toString());
                                            //Log.e("-- ", "producttt Id " + tv_pop_pname.getTag().toString());
                                            // Toast.makeText(getApplicationContext(),"Product ID "+bean_product1.get(position).getPro_id(), Toast.LENGTH_LONG).show();
                                            bean.setPro_cat_id(bean_product1.get(position).getPro_cat_id());
                                            //Log.e("Cat_ID....", "" + bean_product1.get(position).getPro_cat_id().toString());
                                            if (list_of_images.size() == 0) {
                                                bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
                                            } else {
                                                bean.setPro_Images(list_of_images.get(0).toString());
                                            }
                                            // bean.setPro_Images(list_of_images.get(position));
                                            //bean.setPro_code(result_holder.tvproduct_code.getText().toString());
                                            bean.setPro_code(tv_pop_code.getText().toString().trim());
                                            //Log.e("-- ", "pro_code " + tv_pop_code.getText().toString());
                                            bean.setPro_name(productIDbyTag[0]);//tv_pop_pname.getText().toString());
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
                                                    jobject.put("product_id", productIDbyTag[0]);//tv_pop_pname.getTag().toString());
                                                    jobject.put("category_id", bean_product1.get(position).getPro_cat_id().toString());
                                                    Log.e("ABCs", "" + bean_product1.get(position).getPro_cat_id().toString());
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
                                                        jobject.put("prod_img", list_of_images.get(0));
                                                        //   bean.setPro_Images(list_of_images.get(0).toString());
                                                    }


                                                    jarray_cart.put(jobject);
                                                } catch (JSONException e) {

                                                }


                                            }
                                            array_value.clear();
                                            if (kkk == 1) {
                                                new Edit_Product(jarray_cart.toString()).execute();
                                            } else {

                                                new Add_Product(jarray_cart.toString()).execute();
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
                                                bean.setPro_cat_id(bean_product1.get(position).getPro_cat_id());
                                                //Log.e("Cat_ID....", "" + bean_product1.get(position).getPro_cat_id().toString());
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

                                                double se = Double.parseDouble(tv_pop_sellingprice.getText().toString()) * buyqu;

                                                double se1 = buyqu + getqu;

                                                double fse = se / se1;
                                                double w1 = round(fse, 2);
                                                sell = String.format("%.2f", w1);
                                                //sell = String.valueOf(fse);

                                                int fqu = qu + (int) getqu;

                                                bean.setPro_qty(String.valueOf((int) fqu));
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
                                                        jobject.put("category_id", bean_product1.get(position).getPro_cat_id().toString());
                                                        Log.e("ABCdf", "" + bean_product1.get(position).getPro_cat_id().toString());
                                                        jobject.put("name", tv_pop_pname.getText().toString());
                                                        String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                        String aString = newString.toString().trim().replace(")", "");
                                                        jobject.put("pro_code", aString.toString().trim());
                                                        jobject.put("quantity", String.valueOf((int) fqu));
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

                                                    new Edit_Product(jarray_cart.toString()).execute();

                                                } else {

                                                    new Add_Product(jarray_cart.toString()).execute();
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
                                                bean.setPro_cat_id(bean_product1.get(position).getPro_cat_id().toString());
                                                //Log.e("Cat_ID....", "" + bean_product1.get(position).getPro_cat_id().toString());
                                                if (list_of_images.size() == 0) {
                                                    bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
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
                                                    jobject.put("category_id", bean_product1.get(position).getPro_cat_id().toString());
                                                    Log.e("ABCkjh", "" + bean_product1.get(position).getPro_cat_id().toString());
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
                                                //Log.e("Cat_ID....", "" + bean_product1.get(position).getPro_cat_id().toString());
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
                                                    jobject.put("quantity", String.valueOf((int) b));
                                                    jobject.put("mrp", bean_product_schme.get(0).getPro_mrp());
                                                    jobject.put("selling_price", bean_product_schme.get(0).getPro_sellingprice());
                                                    jobject.put("option_id", bean_Oprtions.get(0).getPro_Option_id().toString());
                                                    jobject.put("option_name", bean_Oprtions.get(0).getPro_Option_name().toString());
                                                    jobject.put("option_value_id", bean_Oprtions.get(0).getPro_Option_value_id().toString());
                                                    jobject.put("option_value_name", bean_Oprtions.get(0).getPro_Option_value_name().toString());
                                                    jobject.put("item_total", "0");
                                                    jobject.put("pro_scheme", "" + tv_pop_pname.getTag().toString());
                                                    jobject.put("pack_of", bean_product1.get(position).getPro_label());
                                                    jobject.put("scheme_id", bean_schme.get(0).getScheme_id());
                                                    jobject.put("scheme_title", bean_schme.get(0).getScheme_name());
                                                    jobject.put("scheme_pack_id", bean_schme.get(0).getScheme_id());
                                                    jobject.put("prod_img", bean_product_schme.get(0).getPro_image());


                                                    jarray_cart.put(jobject);
                                                } catch (JSONException e) {

                                                }


                                                // db.Add_Product_cart_scheme(bean_s);

                                                array_value.clear();
                                                if (kkk == 1) {

                                                    new Edit_Product(jarray_cart.toString()).execute();

                                                } else {

                                                    new Add_Product(jarray_cart.toString()).execute();
                                                }

                                            } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("3")) {

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
                                                bean.setPro_cat_id(bean_product1.get(position).getPro_cat_id().toString());
                                                //Log.e("Cat_ID....", "" + bean_product1.get(position).getPro_cat_id().toString());
                                                if (list_of_images.size() == 0) {
                                                    bean.setPro_Images(bean_product1.get(position).getPro_image().toString());
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

                                                String disc = bean_schme.get(0).getDisc_per().toString();

                                                double se = Double.parseDouble(tv_pop_sellingprice.getText().toString()) * Double.parseDouble(bean_schme.get(0).getDisc_per().toString());

                                                double se1 = se / 100;

                                                double fse = Double.parseDouble(tv_pop_sellingprice.getText().toString()) - se1;
                                                double w = round(fse, 2);
                                                sell = String.format("%.2f", w);
                                                //  sell = String.valueOf(fse);

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
                                                        jobject.put("category_id", bean_product1.get(position).getPro_cat_id().toString());
                                                        Log.e("ABCuy", "" + bean_product1.get(position).getPro_cat_id().toString());
                                                        jobject.put("name", tv_pop_pname.getText().toString());
                                                        String newString = tv_pop_code.getText().toString().trim().replace("(", "");
                                                        String aString = newString.toString().trim().replace(")", "");
                                                        jobject.put("pro_code", aString.toString().trim());
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

                                                    new Edit_Product(jarray_cart.toString()).execute();

                                                } else {

                                                    new Add_Product(jarray_cart.toString()).execute();
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
                        Globals.CustomToast(ProductSearchForQuotationActivity.this, "Please Login First", getLayoutInflater());
                        user_id_main = "";
                    }
                }
            });

            result_holder.img_offer.setTag(bean_product1.get(position).getScheme());
            //Log.e("asdsadsadasd",""+bean_product1.get(position).getScheme().toString());
            if (bean_product1.get(position).getScheme().equalsIgnoreCase("") || bean_product1.get(position).getScheme().equalsIgnoreCase(null) || bean_product1.get(position).getScheme().equalsIgnoreCase("null")) {

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

                    Log.e("size", "" + bean_Schme_data.size());


                    for (int i = 0; i < bean_Schme_data.size(); i++) {

                        if (bean_Schme_data.get(i).getSchme_prod_id().equalsIgnoreCase(bean_product1.get(position).getPro_id())) {
                            Bean_schemeData beans = new Bean_schemeData();
                            beans.setSchme_id(bean_Schme_data.get(i).getSchme_id());
                            beans.setCategory_id(bean_Schme_data.get(i).getCategory_id());
                            beans.setSchme_name(bean_Schme_data.get(i).getSchme_name());
                            beans.setSchme_qty(bean_Schme_data.get(i).getSchme_qty());
                            beans.setSchme_buy_prod_id(bean_Schme_data.get(i).getSchme_buy_prod_id());
                            beans.setSchme_prod_id(bean_Schme_data.get(i).getSchme_prod_id());

                            bean_S_data.add(beans);


                        } else {

                        }


                    }

                    ExpandableListView listSchemes = (ExpandableListView) dialogOffer.findViewById(R.id.listSchemes);

                    final List<String> schemeHeaders=new ArrayList<String>();
                    final HashMap<String,List<Bean_schemeData>> schemeChildList=new HashMap<String, List<Bean_schemeData>>();

                    List<Bean_schemeData> extraSpecialScheme=new ArrayList<Bean_schemeData>();
                    List<Bean_schemeData> specialScheme=new ArrayList<Bean_schemeData>();
                    List<Bean_schemeData> generalScheme=new ArrayList<Bean_schemeData>();

                    for(int i=0; i<bean_S_data.size(); i++){
                        if(bean_S_data.get(i).getCategory_id().equals(C.EXTRA_SPECIAL_SCHEME)){
                            extraSpecialScheme.add(bean_S_data.get(i));
                        }
                        else if(bean_S_data.get(i).getCategory_id().equals(C.SPECIAL_SCHEME)){
                            specialScheme.add(bean_S_data.get(i));
                        }
                        else {
                            generalScheme.add(bean_S_data.get(i));
                        }
                    }

                    if(extraSpecialScheme.size()>0){
                        schemeHeaders.add("Extra Special Schemes");
                        schemeChildList.put(schemeHeaders.get(schemeHeaders.size()-1),extraSpecialScheme);
                    }

                    if(specialScheme.size()>0){
                        schemeHeaders.add("Special Schemes");
                        schemeChildList.put(schemeHeaders.get(schemeHeaders.size()-1),specialScheme);
                    }

                    if(generalScheme.size()>0){
                        schemeHeaders.add("General Schemes");
                        schemeChildList.put(schemeHeaders.get(schemeHeaders.size()-1),generalScheme);
                    }

                    ExpandableSchemeAdapter schemeAdapter=new ExpandableSchemeAdapter(getApplicationContext(),schemeHeaders,schemeChildList);
                    listSchemes.setAdapter(schemeAdapter);

                    listSchemes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            jarray_cart=new JSONArray();
                            setRefershData();

                            if (user_data.size() != 0) {
                                for (int i = 0; i < user_data.size(); i++) {

                                    owner_id = user_data.get(i).getUser_id();

                                    role_id = user_data.get(i).getUser_type();

                                    if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                                        app = new AppPrefs(ProductSearchForQuotationActivity.this);
                                        role_id = app.getSubSalesId();
                                        user_id_main = app.getSalesPersonId();
                                    } else {
                                        user_id_main = owner_id;
                                    }


                                }


                                // product_id = tv_pop_pname.getTag().toString();
                                List<NameValuePair> para = new ArrayList<NameValuePair>();
                                para.add(new BasicNameValuePair("product_id", bean_product1.get(position).getPro_id()));
                                para.add(new BasicNameValuePair("owner_id", owner_id));
                                para.add(new BasicNameValuePair("user_id", user_id_main));
                                para.add(new BasicNameValuePair("quotation_id", quotationID));
                                Log.e("111111111", "" + bean_product1.get(position).getPro_id());
                                Log.e("222222222", "" + owner_id);
                                Log.e("333333333", "" + user_id_main);

                                isNotDone = true;
                                new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                while (isNotDone) {

                                }
                                isNotDone = true;

                                String json = jsonData; //GetProductDetailByQty(para);
                                jsonData = "";

                                try {


                                    //System.out.println(json);

                                    if (json == null
                                            || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                        Globals.CustomToast(ProductSearchForQuotationActivity.this, "SERVER ERROR", getLayoutInflater());
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


                                            if (jobj != null) {


                                                JSONArray jsonArray = jObj.getJSONArray("data");
                                                for (int iu = 0; iu < jsonArray.length(); iu++) {
                                                    JSONObject jsonObject = jsonArray.getJSONObject(iu);
                                                    qun = jsonObject.getString("quantity");

                                                }

                                                Log.e("131313131331", "" + qun);
                                                kkk = 1;


                                            }


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
                                Log.e("111111111", "" + product_id);
                                Log.e("222222222", "" + user_id_main);
                                Log.e("333333333", "" + qty);

                                isNotDone = true;
                                new GetProductDetailCode(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                while (isNotDone) {

                                }
                                isNotDone = true;
                                String jsons = jsonData; //GetProductDetailByCode(params);


                                Globals.generateNoteOnSD(getApplicationContext(), "4163 ->" + jsonData);

                                jsonData = "";
                                //new check_schme().execute();
                                try {


                                    //System.out.println(json);

                                    if (jsons == null
                                            || (jsons.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                                        Globals.CustomToast(ProductSearchForQuotationActivity.this, "SERVER ERRER", getLayoutInflater());
                                        // loadingView.dismiss();

                                    } else {
                                        JSONObject jObj = new JSONObject(jsons);

                                        String date = jObj.getString("status");

                                        if (date.equalsIgnoreCase("false")) {
                                            String Message = jObj.getString("message");
                                            bean_product_schme.clear();
                                            bean_schme.clear();
                                            bean_Oprtions.clear();
                                            //Log.e("11111111",""+product_id);
                                            //   Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                                            // loadingView.dismiss();
                                        } else {

                                            JSONObject jobj = new JSONObject(jsons);

                                            bean_product_schme.clear();
                                            bean_schme.clear();
                                            bean_Oprtions.clear();
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

                                Globals.generateNoteOnSD(getApplicationContext(), "Line Executed Till 4294 ->" + bean_product_schme.size());
                                //Log.e("45454545",""+bean_product_schme.size());
                                if (bean_product_schme.size() == 0) {
                                    Log.e("1111111111111", "" + qty);
                                } else {
                                    Log.e("2222222222222222", "" + qty);
                                    Log.e("Type ID : ", "" + bean_schme.get(0).getType_id());
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

                                        double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * buyqu;

                                        double se1 = buyqu + getqu;

                                        double fse = se / se1;

                                        double se3 = round(fse, 2);

                                        sell = String.format("%.2f", se3);
                                        Log.e("fse", "" + se3);
                                        //sell = String.valueOf(fse);


                                        int fqu = qu + (int) getqu;


                                        for (int i = 0; i < 1; i++) {
                                            try {
                                                JSONObject jobject = new JSONObject();

                                                jobject.put("user_id", user_id_main);
                                                jobject.put("role_id", role_id);
                                                jobject.put("owner_id", owner_id);
                                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                                jobject.put("category_id", bean_product1.get(position).getPro_cat_id());
                                                Log.e("ABCrew", "" + bean_product1.get(position).getPro_cat_id());
                                                jobject.put("name", bean_product1.get(position).getPro_name());

                                                jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                                jobject.put("quantity", String.valueOf((int) fqu));
                                                jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                                jobject.put("selling_price", sell);
                                                Log.e("111111111", "" + sell);
                                                jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                                jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                                Log.e("111111111", "" + bean_productOprtions.get(position).getPro_Option_name());
                                                jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                                Log.e("111111111", "" + bean_productOprtions.get(position).getPro_Option_value_name());
                                                double f = fqu * Double.parseDouble(sell);
                                                double s = round(f, 2);
                                                String str = String.format("%.2f", s);
                                                Log.e("111111111", "" + str);
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
                                                Globals.generateNoteOnSD(getApplicationContext(), e.getMessage());
                                            }


                                        }
                                        array_value.clear();

                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {

                                            new Edit_Product(jarray_cart.toString()).execute();
                                            dialogOffer.dismiss();

                                        } else {

                                            new Add_Product(jarray_cart.toString()).execute();
                                            dialogOffer.dismiss();
                                        }


                                    } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {

                                        jarray_cart = new JSONArray();
                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", user_id_main);
                                            jobject.put("role_id", role_id);
                                            jobject.put("owner_id", owner_id);
                                            jobject.put("product_id", bean_product1.get(position).getPro_id());
                                            jobject.put("category_id", bean_product1.get(position).getPro_cat_id());
                                            Log.e("ABCqwqw", "" + bean_product1.get(position).getPro_cat_id());
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
                                        double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);

                                        double a = qu / buyqu;
                                        double b = a * getqu;
                                        if (b > maxqu) {
                                            b = maxqu;
                                        } else {
                                            b = b;
                                        }


                                        try {
                                            JSONObject jobject = new JSONObject();

                                            jobject.put("user_id", user_id_main);
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
                                            jobject.put("pro_scheme", bean_product1.get(position).getPro_id().toString());
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

                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {

                                            new Edit_Product(jarray_cart.toString()).execute();
                                            dialogOffer.dismiss();
                                        } else {

                                            new Add_Product(jarray_cart.toString()).execute();
                                            dialogOffer.dismiss();
                                        }

                                    } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("3")) {


                                        String getq = bean_schme.get(0).getGet_qty();
                                        String buyq = bean_schme.get(0).getBuy_qty();
                                        String maxq = bean_schme.get(0).getMax_qty();

                                        double getqu = Double.parseDouble(getq);
                                        double buyqu = Double.parseDouble(buyq);
                                        double maxqu = Double.parseDouble(maxq);
                                        int qu = Integer.parseInt(qty);


                                        String sell = bean_product1.get(position).getPro_sellingprice();

                                        String disc = bean_schme.get(0).getDisc_per().toString();

                                        double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * Double.parseDouble(bean_schme.get(0).getDisc_per().toString());

                                        double se1 = se / 100;

                                        double fse = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) - se1;
                                        double w = round(fse, 2);
                                        sell = String.format("%.2f", w);
                                        jarray_cart = new JSONArray();

                                        for (int i = 0; i < 1; i++) {
                                            try {
                                                JSONObject jobject = new JSONObject();

                                                jobject.put("user_id", user_id_main);
                                                jobject.put("role_id", role_id);
                                                jobject.put("owner_id", owner_id);
                                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                                jobject.put("category_id", bean_product1.get(position).getPro_cat_id());
                                                Log.e("ABC", "" + bean_product1.get(position).getPro_cat_id());
                                                jobject.put("name", bean_product1.get(position).getPro_name());

                                                jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                                jobject.put("quantity", qty);
                                                jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                                jobject.put("selling_price", sell);
                                                Log.e("111111111", "" + sell);
                                                jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                                jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                                jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                                jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                                double f = Double.parseDouble(qty) * Double.parseDouble(sell);
                                                double w1 = round(f, 2);
                                                String str = String.format("%.2f", w1);
                                                Log.e("111111111", "" + str);

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

                                        Log.e("kkkkkkk", "" + kkk);
                                        if (kkk == 1) {

                                            new Edit_Product(jarray_cart.toString()).execute();
                                            dialogOffer.dismiss();
                                        } else {

                                            new Add_Product(jarray_cart.toString()).execute();
                                            dialogOffer.dismiss();
                                        }
                                    }
                                }

                            } else {

                                Globals.CustomToast(ProductSearchForQuotationActivity.this, "Please Login First", getLayoutInflater());
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

            result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position).getPro_mrp());

            result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position).getPro_sellingprice());
           /* //Log.e("1111111",""+bean_product1.get(position)
                    .getPro_name());*/
            result_holder.tvproduct_name.setText(bean_product1.get(position)
                    .getPro_name());
           /* //Log.e("2222222",""+bean_product1.get(position)
                    .getPro_code());*/
            result_holder.tvproduct_code.setText("(" + bean_product1.get(position)
                    .getPro_code() + ")");



            double mrp1 = Double.parseDouble(bean_product1.get(position)
                    .getPro_mrp());

            double sellingprice1 = Double.parseDouble(bean_product1.get(position)
                    .getPro_sellingprice());

            p_code = bean_product1.get(position).getPro_code();
            if (mrp1 > sellingprice1) {
                result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                result_holder.off_tag.setVisibility(View.VISIBLE);
                result_holder.txt_mrp.setVisibility(View.VISIBLE);

                float percent = 100 - (Float.parseFloat(bean_product1.get(position).getPro_sellingprice()) * 100 / Float.parseFloat(bean_product1.get(position).getPro_mrp()));

                result_holder.off_tag.setText(String.format("%.0f", percent) + "% OFF");

                result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position).getPro_mrp());

                result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position).getPro_sellingprice());
                result_holder.tv_product_mrp.setPaintFlags(result_holder.tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            } else {
                result_holder.tv_product_mrp.setVisibility(View.GONE);
                result_holder.off_tag.setVisibility(View.GONE);
                result_holder.txt_mrp.setVisibility(View.GONE);
                result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position).getPro_sellingprice());
            }

            setRefershData();
            if (user_data.size() != 0) {
                for (int ii = 0; ii < user_data.size(); ii++) {


                    role = user_data.get(ii).getUser_type();

                }

            } else {

            }



            if (role_id.equalsIgnoreCase("3") && role.equalsIgnoreCase("6")) {
                //  if(role_id.equalsIgnoreCase("3")){
                result_holder.txt_mrp.setVisibility(View.GONE);
                result_holder.tv_product_sellingprice.setVisibility(View.GONE);
                result_holder.tv_product_mrp.setVisibility(View.GONE);
                result_holder.off_tag.setVisibility(View.GONE);
                result_holder.txt_selling.setVisibility(View.GONE);

                result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                        .getPro_mrp());
                result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                        .getPro_sellingprice());
            } else {

                if (user_data.size() != 0) {
                    result_holder.txt_mrp.setVisibility(View.VISIBLE);
                    result_holder.tv_product_sellingprice.setVisibility(View.VISIBLE);
                    result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                    result_holder.txt_selling.setVisibility(View.VISIBLE);
                    result_holder.off_tag.setVisibility(View.VISIBLE);
                    float percent = 100 - (Float.parseFloat(bean_product1.get(position).getPro_sellingprice()) * 100 / Float.parseFloat(bean_product1.get(position).getPro_mrp()));

                    result_holder.off_tag.setText(String.format("%.0f", percent) + "% OFF");
                    result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                            .getPro_mrp());
                    result_holder.tv_product_sellingprice.setText(bean_product1.get(position)
                            .getPro_sellingprice());
                    result_holder.tvproduct_name.setText(bean_product1.get(position)
                            .getPro_name());
                    result_holder.tvproduct_code.setText("(" + bean_product1.get(position)
                            .getPro_code() + ")");

                    result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                            .getPro_mrp());
                    result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                            .getPro_sellingprice());

                    double mrp = Double.parseDouble(bean_product1.get(position)
                            .getPro_mrp());
                    //Log.e("MRPMRP",""+mrp);
                    double sellingprice = Double.parseDouble(bean_product1.get(position)
                            .getPro_sellingprice());
                    //Log.e("sellingprice",""+sellingprice);


                    p_code = bean_product1.get(position).getPro_code();
                    if (mrp > sellingprice) {
                        result_holder.tv_product_mrp.setVisibility(View.VISIBLE);
                        result_holder.txt_mrp.setVisibility(View.VISIBLE);
                        result_holder.off_tag.setVisibility(View.VISIBLE);

                        float percent1 = 100 - (Float.parseFloat(bean_product1.get(position).getPro_sellingprice()) * 100 / Float.parseFloat(bean_product1.get(position).getPro_mrp()));

                        result_holder.off_tag.setText(String.format("%.0f", percent1) + "% OFF");
                        result_holder.tv_product_mrp.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                                .getPro_mrp());

                        result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                                .getPro_sellingprice());
                        result_holder.tv_product_mrp.setPaintFlags(result_holder.tv_product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    } else {
                        result_holder.tv_product_mrp.setVisibility(View.GONE);
                        result_holder.off_tag.setVisibility(View.GONE);
                        result_holder.txt_mrp.setVisibility(View.GONE);
                        result_holder.tv_product_sellingprice.setText(getResources().getString(R.string.Rs) + bean_product1.get(position)
                                .getPro_sellingprice());

                    }
                }
            }
            result_holder.tvproduct_packof.setText(bean_product1.get(position)
                    .getPro_label());


            imageloader = new ImageLoader(ProductSearchForQuotationActivity.this);

            Picasso.with(getApplicationContext())
                    .load(Globals.server_link + "files/" + bean_product1.get(position).getPro_image())
                    .placeholder(R.drawable.btl_watermark)
                    .into(result_holder.img_photo);

            int mrpPrice=(int) Float.parseFloat(bean_product1.get(position).getPro_mrp());

            if(mrpPrice<=0){
                result_holder.btn_enquiry.setVisibility(View.VISIBLE);
                result_holder.btn_buyonline.setVisibility(View.GONE);
                result_holder.layout_prices.setVisibility(View.INVISIBLE);
                result_holder.img_offer.setVisibility(View.GONE);
            }

            result_holder.btn_enquiry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),NonMRPProductEnquiry.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("productName",bean_product1.get(position).getPro_name());
                    intent.putExtra("productID",bean_product1.get(position).getPro_id());
                    startActivity(intent);
                }
            });

            return convertView;
        }

    }

    private class GetProductDetailCode extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetProductDetailCode(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {

            jsonData = "";

            Globals.generateNoteOnSD(getApplicationContext(), pairList.toString());

            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "Scheme/App_Get_Scheme_Details", ServiceHandler.POST, pairList);

            Log.e("params", "Scheme/App_Get_Scheme_Details" + "\n" + pairList.toString());

            isNotDone = false;

            return jsonData;
        }
    }

    private class GetProductDetailQty extends AsyncTask<Void, Void, String> {

        List<NameValuePair> pairList = new ArrayList<>();

        public GetProductDetailQty(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected String doInBackground(Void... params) {

            jsonData = "";

            jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "QuotationCart/App_Get_Quotation_Product_Qty", ServiceHandler.POST, pairList);

            isNotDone = false;
            return jsonData;
        }
    }

    public class Add_Product extends AsyncTask<Void, Void, String>  {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        String data="";

        public Add_Product(String data) {
            this.data = data;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        ProductSearchForQuotationActivity.this, "");

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

                        Globals.CustomToast(getApplicationContext(), "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(getApplicationContext(), "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        // Globals.CustomToast(Product_List.this, "Item insert in cart", getLayoutInflater());
                        //TODO WORK FOR SOMETHING...
                        //new get_cartdata(true).execute();
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
        String data="";

        public Edit_Product(String data) {
            this.data = data;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        ProductSearchForQuotationActivity.this, "");

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
                    Globals.CustomToast(getApplicationContext(), "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(getApplicationContext(), "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        Globals.CustomToast(getApplicationContext(), "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        //TODO WORK FOR SOMETHING...
                        //new get_cartdata(true).execute();
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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

    private void showOfferDialog(final int position) {


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

            if (bean_Schme_data.get(i).getSchme_prod_id().equalsIgnoreCase(bean_product1.get(position).getPro_id())) {
                Bean_schemeData beans = new Bean_schemeData();
                beans.setSchme_id(bean_Schme_data.get(i).getSchme_id());
                Log.e("ID", "" + bean_product1.get(position).getPro_id().toString());
                Log.e("name", "" + bean_Schme_data.get(i).getSchme_name().toString());
                beans.setSchme_name(bean_Schme_data.get(i).getSchme_name());
                beans.setCategory_id(bean_Schme_data.get(i).getCategory_id());
                beans.setSchme_qty(bean_Schme_data.get(i).getSchme_qty());
                beans.setSchme_buy_prod_id(bean_Schme_data.get(i).getSchme_buy_prod_id());
                beans.setSchme_prod_id(bean_Schme_data.get(i).getSchme_prod_id());

                bean_S_data.add(beans);

            }


        }

        ExpandableListView listSchemes = (ExpandableListView) dialogOffer.findViewById(R.id.listSchemes);

        final List<String> schemeHeaders=new ArrayList<String>();
        final HashMap<String,List<Bean_schemeData>> schemeChildList=new HashMap<String, List<Bean_schemeData>>();

        List<Bean_schemeData> extraSpecialScheme=new ArrayList<Bean_schemeData>();
        List<Bean_schemeData> specialScheme=new ArrayList<Bean_schemeData>();
        List<Bean_schemeData> generalScheme=new ArrayList<Bean_schemeData>();

        for(int i=0; i<bean_S_data.size(); i++){
            if(bean_S_data.get(i).getCategory_id().equals(C.EXTRA_SPECIAL_SCHEME)){
                extraSpecialScheme.add(bean_S_data.get(i));
            }
            else if(bean_S_data.get(i).getCategory_id().equals(C.SPECIAL_SCHEME)){
                specialScheme.add(bean_S_data.get(i));
            }
            else {
                generalScheme.add(bean_S_data.get(i));
            }
        }

        if(extraSpecialScheme.size()>0){
            schemeHeaders.add("Extra Special Schemes");
            schemeChildList.put(schemeHeaders.get(schemeHeaders.size()-1),extraSpecialScheme);
        }

        if(specialScheme.size()>0){
            schemeHeaders.add("Special Schemes");
            schemeChildList.put(schemeHeaders.get(schemeHeaders.size()-1),specialScheme);
        }

        if(generalScheme.size()>0){
            schemeHeaders.add("General Schemes");
            schemeChildList.put(schemeHeaders.get(schemeHeaders.size()-1),generalScheme);
        }

        ExpandableSchemeAdapter schemeAdapter=new ExpandableSchemeAdapter(getApplicationContext(),schemeHeaders,schemeChildList);
        listSchemes.setAdapter(schemeAdapter);

        listSchemes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                jarray_cart=new JSONArray();

                setRefershData();

                if (user_data.size() != 0) {
                    for (int i = 0; i < user_data.size(); i++) {

                        owner_id = user_data.get(i).getUser_id();

                        role_id = user_data.get(i).getUser_type();

                        if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                            app = new AppPrefs(ProductSearchForQuotationActivity.this);
                            role_id = app.getSubSalesId();
                            user_id_main = app.getSalesPersonId();
                        } else {
                            user_id_main = owner_id;
                        }


                    }


                    // product_id = tv_pop_pname.getTag().toString();
                    List<NameValuePair> para = new ArrayList<NameValuePair>();
                    para.add(new BasicNameValuePair("product_id", bean_product1.get(position).getPro_id()));
                    para.add(new BasicNameValuePair("owner_id", owner_id));
                    para.add(new BasicNameValuePair("user_id", user_id_main));
                    Log.e("111111111", "" + bean_product1.get(position).getPro_id());
                    Log.e("222222222", "" + owner_id);
                    Log.e("333333333", "" + user_id_main);

                    isNotDone = true;
                    new GetProductDetailQty(para).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    while (isNotDone) {

                    }
                    isNotDone = true;

                    String json = jsonData; //GetProductDetailByQty(para);
                    jsonData = "";

                    try {


                        //System.out.println(json);

                        if (json == null
                                || (json.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                            Globals.CustomToast(ProductSearchForQuotationActivity.this, "SERVER ERROR", getLayoutInflater());
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


                                if (jobj != null) {


                                    JSONArray jsonArray = jObj.getJSONArray("data");
                                    for (int iu = 0; iu < jsonArray.length(); iu++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(iu);
                                        qun = jsonObject.getString("quantity");

                                    }

                                    Log.e("131313131331", "" + qun);
                                    kkk = 1;


                                }


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
                    Log.e("111111111", "" + product_id);
                    Log.e("222222222", "" + user_id_main);
                    Log.e("333333333", "" + qty);

                    isNotDone = true;
                    new GetProductDetailCode(params).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    while (isNotDone) {

                    }
                    isNotDone = true;
                    String jsons = jsonData; //GetProductDetailByCode(params);


                    Globals.generateNoteOnSD(getApplicationContext(), "4163 ->" + jsonData);

                    jsonData = "";
                    //new check_schme().execute();
                    try {


                        //System.out.println(json);

                        if (jsons == null
                                || (jsons.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                            Globals.CustomToast(ProductSearchForQuotationActivity.this, "SERVER ERROR", getLayoutInflater());
                            // loadingView.dismiss();

                        } else {
                            JSONObject jObj = new JSONObject(jsons);

                            String date = jObj.getString("status");

                            if (date.equalsIgnoreCase("false")) {
                                String Message = jObj.getString("message");
                                bean_product_schme.clear();
                                bean_schme.clear();
                                bean_Oprtions.clear();
                                //Log.e("11111111",""+product_id);
                                //   Globals.CustomToast(Product_List.this, "" + Message, getLayoutInflater());
                                // loadingView.dismiss();
                            } else {

                                JSONObject jobj = new JSONObject(jsons);

                                bean_product_schme.clear();
                                bean_schme.clear();
                                bean_Oprtions.clear();
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

                    Globals.generateNoteOnSD(getApplicationContext(), "Line Executed Till 4294 ->" + bean_product_schme.size());
                    //Log.e("45454545",""+bean_product_schme.size());
                    if (bean_product_schme.size() == 0) {
                        Log.e("1111111111111", "" + qty);
                    } else {
                        Log.e("2222222222222222", "" + qty);
                        Log.e("Type ID : ", "" + bean_schme.get(0).getType_id());
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

                            double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * buyqu;

                            double se1 = buyqu + getqu;

                            double fse = se / se1;

                            double se3 = round(fse, 2);

                            sell = String.format("%.2f", se3);
                            Log.e("fse", "" + se3);
                            //sell = String.valueOf(fse);


                            int fqu = qu + (int) getqu;


                            for (int i = 0; i < 1; i++) {
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", user_id_main);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_product1.get(position).getPro_id());
                                    jobject.put("category_id", bean_product1.get(position).getPro_cat_id());
                                    Log.e("ABCrew", "" + bean_product1.get(position).getPro_cat_id());
                                    jobject.put("name", bean_product1.get(position).getPro_name());

                                    jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                    jobject.put("quantity", String.valueOf((int) fqu));
                                    jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                    jobject.put("selling_price", sell);
                                    Log.e("111111111", "" + sell);
                                    jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                    jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                    Log.e("111111111", "" + bean_productOprtions.get(position).getPro_Option_name());
                                    jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                    jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                    Log.e("111111111", "" + bean_productOprtions.get(position).getPro_Option_value_name());
                                    double f = fqu * Double.parseDouble(sell);
                                    double s = round(f, 2);
                                    String str = String.format("%.2f", s);
                                    Log.e("111111111", "" + str);
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
                                    Globals.generateNoteOnSD(getApplicationContext(), e.getMessage());
                                }


                            }
                            array_value.clear();

                            Log.e("kkkkkkk", "" + kkk);
                            if (kkk == 1) {

                                new Edit_Product(jarray_cart.toString()).execute();
                                dialogOffer.dismiss();

                            } else {

                                new Add_Product(jarray_cart.toString()).execute();
                                dialogOffer.dismiss();
                            }


                        } else if (bean_schme.get(0).getType_id().equalsIgnoreCase("2")) {

                            jarray_cart = new JSONArray();
                            try {
                                JSONObject jobject = new JSONObject();

                                jobject.put("user_id", user_id_main);
                                jobject.put("role_id", role_id);
                                jobject.put("owner_id", owner_id);
                                jobject.put("product_id", bean_product1.get(position).getPro_id());
                                jobject.put("category_id", bean_product1.get(position).getPro_cat_id());
                                Log.e("ABCqwqw", "" + bean_product1.get(position).getPro_cat_id());
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
                            double maxqu = Double.parseDouble(maxq);
                            int qu = Integer.parseInt(qty);

                            double a = qu / buyqu;
                            double b = a * getqu;
                            if (b > maxqu) {
                                b = maxqu;
                            } else {
                                b = b;
                            }


                            try {
                                JSONObject jobject = new JSONObject();

                                jobject.put("user_id", user_id_main);
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
                                jobject.put("pro_scheme", bean_product1.get(position).getPro_id().toString());
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

                            Log.e("kkkkkkk", "" + kkk);
                            if (kkk == 1) {

                                new Edit_Product(jarray_cart.toString()).execute();
                                dialogOffer.dismiss();
                            } else {

                                new Add_Product(jarray_cart.toString()).execute();
                                dialogOffer.dismiss();
                            }

                        } else if (bean_schme.get(0).getType_id().toString().equalsIgnoreCase("3")) {


                            String getq = bean_schme.get(0).getGet_qty();
                            String buyq = bean_schme.get(0).getBuy_qty();
                            String maxq = bean_schme.get(0).getMax_qty();

                            double getqu = Double.parseDouble(getq);
                            double buyqu = Double.parseDouble(buyq);
                            double maxqu = Double.parseDouble(maxq);
                            int qu = Integer.parseInt(qty);


                            String sell = bean_product1.get(position).getPro_sellingprice();

                            String disc = bean_schme.get(0).getDisc_per().toString();

                            double se = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) * Double.parseDouble(bean_schme.get(0).getDisc_per().toString());

                            double se1 = se / 100;

                            double fse = Double.parseDouble(bean_product1.get(position).getPro_sellingprice()) - se1;
                            double w = round(fse, 2);
                            sell = String.format("%.2f", w);
                            jarray_cart = new JSONArray();

                            for (int i = 0; i < 1; i++) {
                                try {
                                    JSONObject jobject = new JSONObject();

                                    jobject.put("user_id", user_id_main);
                                    jobject.put("role_id", role_id);
                                    jobject.put("owner_id", owner_id);
                                    jobject.put("product_id", bean_product1.get(position).getPro_id());
                                    jobject.put("category_id", bean_product1.get(position).getPro_cat_id());
                                    Log.e("ABC", "" + bean_product1.get(position).getPro_cat_id());
                                    jobject.put("name", bean_product1.get(position).getPro_name());

                                    jobject.put("pro_code", bean_product1.get(position).getPro_code());
                                    jobject.put("quantity", qty);
                                    jobject.put("mrp", bean_product1.get(position).getPro_mrp());
                                    jobject.put("selling_price", sell);
                                    Log.e("111111111", "" + sell);
                                    jobject.put("option_id", bean_productOprtions.get(position).getPro_Option_id());
                                    jobject.put("option_name", bean_productOprtions.get(position).getPro_Option_name());
                                    jobject.put("option_value_id", bean_productOprtions.get(position).getPro_Option_value_id());
                                    jobject.put("option_value_name", bean_productOprtions.get(position).getPro_Option_value_name());
                                    double f = Double.parseDouble(qty) * Double.parseDouble(sell);
                                    double w1 = round(f, 2);
                                    String str = String.format("%.2f", w1);
                                    Log.e("111111111", "" + str);

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

                            Log.e("kkkkkkk", "" + kkk);
                            if (kkk == 1) {

                                new Edit_Product(jarray_cart.toString()).execute();
                                dialogOffer.dismiss();
                            } else {

                                new Add_Product(jarray_cart.toString()).execute();
                                dialogOffer.dismiss();
                            }
                        }
                    }

                } else {
                    dialogOffer.dismiss();
                    Globals.CustomToast(ProductSearchForQuotationActivity.this, "Please Login First", getLayoutInflater());
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
