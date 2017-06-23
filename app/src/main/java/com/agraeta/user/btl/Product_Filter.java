package com.agraeta.user.btl;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Product_Filter extends AppCompatActivity {

    LinearLayout l_list, l_radio, l_clear, l_apply;

    TextView cate, sub_cate, options, sorting;

    RadioGroup rd_g;
    Custom_ProgressDialog loadingView;
    String owner_id = new String();
    String u_id = new String();
    String role_id = new String();


    String json = new String();
    LinearLayout.LayoutParams params;

    String cat_data = new String();
    String sub_cat_data = new String();
    String value_data = new String();

    AppPrefs appPrefs;
    int key;

    DatabaseHandler db;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ArrayList<Bean_Filter_Cat> bean_cat = new ArrayList<Bean_Filter_Cat>();
    ArrayList<Bean_Filter_SubCat> bean_subcat = new ArrayList<Bean_Filter_SubCat>();
    ArrayList<Bean_Filter_value> bean_value = new ArrayList<Bean_Filter_value>();
    ArrayList<Bean_Filter_option> bean_option = new ArrayList<Bean_Filter_option>();

    ArrayList<Bean_F_Sorting> bean_sort = new ArrayList<Bean_F_Sorting>();

    ArrayList<Bean_Filter_value> array_att = new ArrayList<Bean_Filter_value>();
    ArrayList<Bean_F_Cat> bean_d_cat = new ArrayList<Bean_F_Cat>();
    ArrayList<Bean_F_Subcat> bean_d_subcat = new ArrayList<Bean_F_Subcat>();
    ArrayList<Bean_F_Value> bean_d_value = new ArrayList<Bean_F_Value>();
    String cartJSON = "";
    boolean hasCartCallFinish = true;

    TextView txt;


    int ij, j;

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
        setContentView(R.layout.activity_product__filter);
        db = new DatabaseHandler(Product_Filter.this);
        params = new LinearLayout.LayoutParams(android.app.ActionBar.LayoutParams.FILL_PARENT,
                android.app.ActionBar.LayoutParams.WRAP_CONTENT);

        setActionBar();

        bean_sort = new ArrayList<Bean_F_Sorting>();

        bean_sort.add(new Bean_F_Sorting("1", "Low To High"));
        bean_sort.add(new Bean_F_Sorting("2", "High To Low"));
        bean_sort.add(new Bean_F_Sorting("3", "New Arrival"));
        bean_sort.add(new Bean_F_Sorting("4", "Popularity"));

        appPrefs = new AppPrefs(Product_Filter.this);
        //appPrefs.setSort_Radio("1");
        fetchId();

        l_list.setVisibility(View.VISIBLE);
        new SetCategory().execute();
    }

    private void fetchId() {
        l_list = (LinearLayout) findViewById(R.id.l_main);
        cate = (TextView) findViewById(R.id.category);
        sub_cate = (TextView) findViewById(R.id.subcategory);
        options = (TextView) findViewById(R.id.option);
        sorting = (TextView) findViewById(R.id.sorting);
        l_radio = (LinearLayout) findViewById(R.id.l_radio);
        rd_g = (RadioGroup) findViewById(R.id.radioGroup1);
        l_clear = (LinearLayout) findViewById(R.id.clear_all);
        l_apply = (LinearLayout) findViewById(R.id.apply);

        if (user_data.size() == 0) {
            sorting.setVisibility(View.GONE);
        }


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Set_Referash_Data();
                if(bean_d_cat.size()==0){
                    Globals.CustomToast(Product_Filter.this, "Please select Category", getLayoutInflater());
                }
                else {
                    l_list.setVisibility(View.VISIBLE);
                    l_list.removeAllViews();

                    l_radio.setVisibility(View.GONE);


                    cate.setBackgroundColor(Color.parseColor("#44f58634"));
                    sub_cate.setBackgroundColor(Color.parseColor("#44f58634"));
                    options.setBackgroundColor(Color.parseColor("#f58634"));
                    sorting.setBackgroundColor(Color.parseColor("#44f58634"));


                    cate.setTypeface(null, Typeface.NORMAL);
                    sub_cate.setTypeface(null, Typeface.NORMAL);
                    options.setTypeface(null, Typeface.BOLD);
                    sorting.setTypeface(null, Typeface.NORMAL);


                    if (bean_d_cat.size() == 0) {
                        l_list.removeAllViews();
                        db = new DatabaseHandler(Product_Filter.this);
                        db.LogoutSubcategory();
                        db.close();
                        Globals.CustomToast(Product_Filter.this, "Please select Category", getLayoutInflater());

                    } else {
                        cat_data = "";
                        for (int i = 0; i < bean_d_cat.size(); i++) {
                            if (i == 0) {
                                cat_data = bean_d_cat.get(i).getCat_id();
                                //Log.e("", cat_data);
                            } else {
                                cat_data = cat_data + ","
                                        + bean_d_cat.get(i).getCat_id();
                                //Log.e("", cat_data);
                            }
                        }
                        new SetCategoryOption().execute();
                        //    new SetSubCAtegory().execute();
                    }
                }




            }
        });

        sub_cate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Set_Referash_Data();

                if(bean_d_cat.size()==0){
                    Globals.CustomToast(Product_Filter.this, "Please select Category", getLayoutInflater());
                }
                else {
                    l_list.setVisibility(View.VISIBLE);
                    l_list.removeAllViews();

                    l_radio.setVisibility(View.GONE);


                    cate.setBackgroundColor(Color.parseColor("#44f58634"));
                    sub_cate.setBackgroundColor(Color.parseColor("#f58634"));
                    options.setBackgroundColor(Color.parseColor("#44f58634"));
                    sorting.setBackgroundColor(Color.parseColor("#44f58634"));


                    cate.setTypeface(null, Typeface.NORMAL);
                    sub_cate.setTypeface(null, Typeface.BOLD);
                    options.setTypeface(null, Typeface.NORMAL);
                    sorting.setTypeface(null, Typeface.NORMAL);



                    if (bean_d_cat.size() == 0) {
                        l_list.removeAllViews();
                        db = new DatabaseHandler(Product_Filter.this);
                        db.LogoutSubcategory();
                        db.close();


                    } else {
                        cat_data = "";
                        for (int i = 0; i < bean_d_cat.size(); i++) {
                            if (i == 0) {
                                cat_data = bean_d_cat.get(i).getCat_id();
                                //Log.e("", cat_data);
                            } else {
                                cat_data = cat_data + ","
                                        + bean_d_cat.get(i).getCat_id();
                                //Log.e("", cat_data);
                            }
                        }

                        new SetSubCAtegory().execute();
                    }
                }


            }
        });
        cate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                l_list.setVisibility(View.VISIBLE);
                l_list.removeAllViews();

                l_radio.setVisibility(View.GONE);


                cate.setBackgroundColor(Color.parseColor("#f58634"));
                sub_cate.setBackgroundColor(Color.parseColor("#44f58634"));
                options.setBackgroundColor(Color.parseColor("#44f58634"));
                sorting.setBackgroundColor(Color.parseColor("#44f58634"));


                cate.setTypeface(null, Typeface.BOLD);
                sub_cate.setTypeface(null, Typeface.NORMAL);
                options.setTypeface(null, Typeface.NORMAL);
                sorting.setTypeface(null, Typeface.NORMAL);


                new SetCategory().execute();

            }
        });


        sorting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                l_list.setVisibility(View.GONE);
                l_radio.setVisibility(View.VISIBLE);


                cate.setBackgroundColor(Color.parseColor("#44f58634"));
                sub_cate.setBackgroundColor(Color.parseColor("#44f58634"));
                options.setBackgroundColor(Color.parseColor("#44f58634"));
                sorting.setBackgroundColor(Color.parseColor("#f58634"));


                cate.setTypeface(null, Typeface.NORMAL);
                sub_cate.setTypeface(null, Typeface.NORMAL);
                options.setTypeface(null, Typeface.NORMAL);
                sorting.setTypeface(null, Typeface.BOLD);


                setLayoutSort(bean_sort);

            }
        });

        l_clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                db = new DatabaseHandler(Product_Filter.this);
                db.LogoutFilter();
                db.close();
                appPrefs = new AppPrefs(Product_Filter.this);

                appPrefs.setSort_Radio("");

                Globals.CustomToast(Product_Filter.this, "Clear All Filter Sucessfully...", getLayoutInflater());

                l_list.setVisibility(View.VISIBLE);

                l_radio.setVisibility(View.GONE);
                cate.setBackgroundColor(Color.parseColor("#f58634"));
                sub_cate.setBackgroundColor(Color.parseColor("#44f58634"));
                options.setBackgroundColor(Color.parseColor("#44f58634"));
                sorting.setBackgroundColor(Color.parseColor("#44f58634"));


                cate.setTypeface(null, Typeface.BOLD);
                sub_cate.setTypeface(null, Typeface.NORMAL);
                options.setTypeface(null, Typeface.NORMAL);
                sorting.setTypeface(null, Typeface.NORMAL);


                new SetCategory().execute();

            }
        });

        l_apply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                appPrefs = new AppPrefs(Product_Filter.this);

                Set_Referash_Data_SubCAT();

                Set_Referash_Data();

                if (user_data.size() == 0) {
                    sorting.setVisibility(View.GONE);
                    if (bean_d_cat.size() == 0) {
                        Globals.CustomToast(Product_Filter.this, "Please select Category", getLayoutInflater());

                    } else {

                        Set_Referash_Data();
                        try {

                            JSONArray jObjectMain = new JSONArray();

                            for (int i = 0; i < bean_d_cat.size(); i++) {
                                JSONObject jObjectData = new JSONObject();

                                jObjectData.put("id", bean_d_cat.get(i).getCat_id());

                                jObjectMain.put(jObjectData);
                                cat_data = jObjectMain.toString();

                            }

                            //System.out.println("cat_data" + cat_data);
                            //Log.e("cat_data", "" + cat_data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Set_Referash_Data_SubCAT();
                        try {

                            JSONArray jObjectMain = new JSONArray();

                            for (int i = 0; i < bean_d_subcat.size(); i++) {
                                JSONObject jObjectData = new JSONObject();

                                jObjectData.put("id", bean_d_subcat.get(i).getSub_cat_id());

                                jObjectMain.put(jObjectData);
                                sub_cat_data = jObjectMain.toString();

                            }

                            //System.out.println("sub_cat_data" + sub_cat_data);
                            //Log.e("sub_cat_data", "" + sub_cat_data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Set_Referash_Data_value();


                        try {

                            JSONArray jObjectMain = new JSONArray();

                            for (int i = 0; i < bean_d_value.size(); i++) {
                                JSONObject jObjectData = new JSONObject();

                                jObjectData.put("opt_id", bean_d_value.get(i).getValue_Opt_name());
                                jObjectData.put("value_id", bean_d_value.get(i).getValue_id());

                                jObjectMain.put(jObjectData);
                                value_data = jObjectMain.toString();

                            }

                            //System.out.println("value_data" + value_data);
                            //Log.e("value_data", "" + value_data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        appPrefs = new AppPrefs(Product_Filter.this);
                        String Sort = appPrefs.getSort_Radio();
                        //Log.e("Sort", Sort);

                        appPrefs.setPage_Data("2");
                        appPrefs.setFilter_Cat(cat_data);
                        appPrefs.setFilter_SubCat(sub_cat_data);
                        appPrefs.setFilter_Option(value_data);
                        appPrefs.setFilter_Sort(Sort);

                        Intent i = new Intent(Product_Filter.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


                    }


                } else {

                    if (bean_d_cat.size() == 0) {
                        Globals.CustomToast(Product_Filter.this, "Please select Category", getLayoutInflater());
                    } else if (appPrefs.getSort_Radio().equalsIgnoreCase("")) {
                        Globals.CustomToast(Product_Filter.this, "Please select sorting", getLayoutInflater());

                    } else {

                        Set_Referash_Data();
                        try {

                            JSONArray jObjectMain = new JSONArray();

                            for (int i = 0; i < bean_d_cat.size(); i++) {
                                JSONObject jObjectData = new JSONObject();

                                jObjectData.put("id", bean_d_cat.get(i).getCat_id());

                                jObjectMain.put(jObjectData);
                                cat_data = jObjectMain.toString();

                            }

                            //System.out.println("cat_data" + cat_data);
                            //Log.e("cat_data", "" + cat_data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Set_Referash_Data_SubCAT();
                        try {

                            JSONArray jObjectMain = new JSONArray();

                            for (int i = 0; i < bean_d_subcat.size(); i++) {
                                JSONObject jObjectData = new JSONObject();

                                jObjectData.put("id", bean_d_subcat.get(i).getSub_cat_id());

                                jObjectMain.put(jObjectData);
                                sub_cat_data = jObjectMain.toString();

                            }

                            //System.out.println("sub_cat_data" + sub_cat_data);
                            //Log.e("sub_cat_data", "" + sub_cat_data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Set_Referash_Data_value();


                        try {

                            JSONArray jObjectMain = new JSONArray();

                            for (int i = 0; i < bean_d_value.size(); i++) {
                                JSONObject jObjectData = new JSONObject();

                                jObjectData.put("opt_id", bean_d_value.get(i).getValue_Opt_name());
                                jObjectData.put("value_id", bean_d_value.get(i).getValue_id());

                                jObjectMain.put(jObjectData);
                                value_data = jObjectMain.toString();

                            }

                            //System.out.println("value_data" + value_data);
                            //Log.e("value_data", "" + value_data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        appPrefs = new AppPrefs(Product_Filter.this);
                        String Sort = appPrefs.getSort_Radio();
                        //Log.e("Sort", Sort);

                        appPrefs.setPage_Data("2");
                        appPrefs.setFilter_Cat(cat_data);
                        appPrefs.setFilter_SubCat(sub_cat_data);
                        appPrefs.setFilter_Option(value_data);
                        appPrefs.setFilter_Sort(Sort);

                        Intent i = new Intent(Product_Filter.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


                    }


                }
            }
        });

    }

    private void SetOptionLayout(ArrayList<Bean_Filter_option> str) {
        // TODO Auto-generated method stub
        l_list.removeAllViews();

        for (ij = 0; ij < str.size(); ij++) {

            final LinearLayout lmain = new LinearLayout(Product_Filter.this);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.VERTICAL);
            lmain.setPadding(1, 1, 1, 0);
            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 1, 0);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT, 2.0f);


            final LinearLayout l1 = new LinearLayout(Product_Filter.this);
            l1.setLayoutParams(par1);
            l1.setOrientation(LinearLayout.VERTICAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);


            final LinearLayout l2 = new LinearLayout(Product_Filter.this);
            l2.setLayoutParams(par1);
            l2.setOrientation(LinearLayout.VERTICAL);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.removeAllViews();

            final TextView tname = new TextView(Product_Filter.this);
            tname.setTextColor(Color.parseColor("#f58634"));
            tname.setText(str.get(ij).getOpt_name().toString());
            /* txt_name.setWidth(200); */
            tname.setMaxLines(2);
            tname.setTextSize(14);
            tname.setGravity(Gravity.CENTER | Gravity.LEFT);
            tname.setEllipsize(TextUtils.TruncateAt.END);
            tname.setTag(str.get(ij).getOpt_id().toString());
            l1.addView(tname);


            array_att = new ArrayList<Bean_Filter_value>();
            array_att = str.get(ij).getValue_list();

            //Log.e("Array Size", "" + array_att.size());


            for (j = 0; j < array_att.size(); j++) {

                if (tname.getTag().toString().equalsIgnoreCase(array_att.get(j).getValue_opt_id())) {

                    final LinearLayout l = new LinearLayout(Product_Filter.this);
                    l.setLayoutParams(par1);
                    l.setOrientation(LinearLayout.HORIZONTAL);
                    l.setGravity(Gravity.START | Gravity.CENTER);

                    final CheckBox check = new CheckBox(Product_Filter.this);
                    Set_Referash_Data_value();

                    if (bean_d_value.size() != 0) {
                        int k = 0;
                        for (int i = 0; i < bean_d_value.size(); i++) {
                            if (k != 1) {
                                if (array_att.get(j).getValue_id().equalsIgnoreCase(bean_d_value.get(i).getValue_id())) {
                                    check.setChecked(true);
                                    check.setTag(array_att.get(j).getValue_id());
                                    k = 1;
                                } else {
                                    check.setChecked(false);
                                    check.setTag(array_att.get(j).getValue_id());

                                }
                            }
                        }
                    } else {
                        check.setChecked(false);
                        check.setTag(array_att.get(j).getValue_id());
                    }
                    l.addView(check);

                    final TextView txt_name = new TextView(Product_Filter.this);
                    txt_name.setTextColor(Color.parseColor("#000000"));
                    txt_name.setText(array_att.get(j).getValue_name());
            /* txt_name.setWidth(200); */
                    txt_name.setMaxLines(2);
                    txt_name.setTextSize(14);
                    txt_name.setGravity(Gravity.CENTER | Gravity.START);
                    txt_name.setEllipsize(TextUtils.TruncateAt.END);
                    txt_name.setTag(array_att.get(j).getValue_name());
                    l.addView(txt_name);

                    final View v = new View(Product_Filter.this);
                    v.setLayoutParams(par2);
                    v.setBackgroundColor(Color.parseColor("#E4E4E4"));

                    check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            // TODO Auto-generated method stub
                            if (isChecked) // true if the checkbox is checked, false
                            // if unchecked
                            {

                                String valueid = buttonView.getTag().toString();

                                String valuename = txt_name.getTag().toString();

                                String valueoptionId = tname.getTag().toString();

                                Set_Referash_Data_value();

                                if (bean_d_value.size() == 0) {
                                    db = new DatabaseHandler(Product_Filter.this);
                                    db.Add_Value(new Bean_F_Value(valueid, valuename, valueoptionId));
                                    db.close();
                                } else {

                                    int k = 0;
                                    for (int i = 0; i < bean_d_value.size(); i++) {

                                        if (valueid.equalsIgnoreCase(bean_d_value.get(i).getValue_id())) {

                                        } else {
                                            if (k != 1) {

                                                db = new DatabaseHandler(
                                                        Product_Filter.this);
                                                db.Add_Value(new Bean_F_Value(valueid, valuename, valueoptionId));
                                                db.close();
                                            }
                                            k = 1;
                                        }
                                    }
                                }
                            } else {


                                String valueid = buttonView.getTag().toString();

                                Set_Referash_Data_value();
                                for (int j = 0; j < bean_d_value.size(); j++) {
                                    if (valueid.equalsIgnoreCase(bean_d_value.get(j).getValue_id())) {
                                        db = new DatabaseHandler(Product_Filter.this);
                                        db.Delete_Value(bean_d_value.get(j).getId());
                                        db.close();
                                    }
                                }
                            }
                        }

                    });


                    // lmain.addView(l1);
//                lmain.addView(l1);

                    //l2.addView(v);

                    l2.addView(l);
                }
            }

            lmain.removeAllViews();
            lmain.addView(l1);
            lmain.addView(l2);

            l_list.addView(lmain);

        }

    }

    private void setLayoutSort(ArrayList<Bean_F_Sorting> str) {
        // TODOAuto-generated method stub
        l_list.removeAllViews();
        l_radio.removeAllViews();

        rd_g.removeAllViews();

        for (ij = 0; ij < str.size(); ij++) {

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 1, 0);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT, 2.0f);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            final RadioButton radioButtonView = new RadioButton(Product_Filter.this);

            radioButtonView.setText(str.get(ij).getSort_name());
            radioButtonView.setTextColor(Color.parseColor("#000000"));
            radioButtonView.setTextSize(14);
            radioButtonView.setTag(str.get(ij).getSort_id()); //

            radioButtonView.setChecked(false);

			/*if(appPrefs.getSort_Radio().equalsIgnoreCase("")||appPrefs.getSort_Radio().equalsIgnoreCase("null"))
			{
				//Log.e("", "sort prefs- "+appPrefs.getSort_Radio());
				radioButtonView.setChecked(true);
			}*/

            if (appPrefs.getSort_Radio().equalsIgnoreCase(str.get(ij).getSort_id())) {
                radioButtonView.setChecked(true);
                //appPrefs.setSort_Radio("");
                key = 1;
            } else {
                radioButtonView.setChecked(false);
                // rd_g.clearCheck();
            }
            if (appPrefs.getSort_Radio().equalsIgnoreCase("") || appPrefs.getSort_Radio().equalsIgnoreCase("null")) {
                if (str.get(ij).getSort_id().equalsIgnoreCase("4")) {
                    //Log.e("", "sort prefs- " + appPrefs.getSort_Radio());
                    radioButtonView.setChecked(true);
                    appPrefs.setSort_Radio(radioButtonView.getTag().toString());
                }

            }
            radioButtonView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub

                    if (isChecked) {

                        // rd_g.clearCheck();
                        appPrefs.setSort_Radio(radioButtonView.getTag().toString());
                        setLayoutSort(bean_sort);

                    }


                }
            });
            rd_g.addView(radioButtonView, p);
            // rd_g.check(1);
        }

        l_radio.addView(rd_g);
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
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_category.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.VISIBLE);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        img_home.setVisibility(View.VISIBLE);

        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        appPrefs = new AppPrefs(Product_Filter.this);
        String qun = appPrefs.getCart_QTy();

        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    appPrefs = new AppPrefs(Product_Filter.this);
                    role_id = appPrefs.getSubSalesId();
                    u_id = appPrefs.getSalesPersonId();
                } else {
                    u_id = owner_id;
                }


            }

            List<NameValuePair> para = new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id", u_id));

            new GetCartByQty(para).execute();


        } else {
            appPrefs = new AppPrefs(Product_Filter.this);
            appPrefs.setCart_QTy("");
        }

        appPrefs = new AppPrefs(Product_Filter.this);
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
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPrefs = new AppPrefs(Product_Filter.this);
                appPrefs.setUser_notification("Product_Filter");
                Intent i = new Intent(Product_Filter.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = new DatabaseHandler(Product_Filter.this);
                db.LogoutFilter();
                db.close();
                appPrefs = new AppPrefs(Product_Filter.this);
                appPrefs.setPage_Data("1");
                Intent i = new Intent(Product_Filter.this, Product_List.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_Filter.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        db = new DatabaseHandler(Product_Filter.this);
        db.LogoutFilter();
        db.close();
        appPrefs = new AppPrefs(Product_Filter.this);
        appPrefs.setPage_Data("1");
        Intent i = new Intent(Product_Filter.this, Product_List.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    ;


    public class SetCategory extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_Filter.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Category/App_Get_Filters", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_Filter.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_Filter.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {


                        JSONObject jobj = new JSONObject(result_1);


                        bean_cat.clear();
                        bean_subcat.clear();
                        bean_value.clear();
                        bean_option.clear();


                        JSONArray jsonArray = jObj.optJSONArray("category");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Bean_Filter_Cat bean = new Bean_Filter_Cat();


                            bean.setCat_id(jsonObject.getString("id"));
                            bean.setCat_name(jsonObject.getString("name"));

                            bean_cat.add(bean);

                        }

                           /* JSONArray jsonOption = jObj.optJSONArray("options");


                            for (int i = 0; i < jsonOption.length(); i++) {
                                JSONObject jsonObject = jsonOption.getJSONObject(i);
                                JSONObject jsonOpti = jsonObject.getJSONObject("Option");

                                Bean_Filter_option bean = new Bean_Filter_option();

                                //Log.e("121212122", "" + jsonOpti.getString("id"));

                                bean.setOpt_id(jsonOpti.getString("id"));
                                bean.setOpt_name(jsonOpti.getString("name"));

                                JSONArray jArray = jsonObject.getJSONArray("OptionValue");

                                for (int j = 0; j < jArray.length(); j++) {

                                    JSONObject jsonObj = jArray.getJSONObject(j);


                                    Bean_Filter_value bea = new Bean_Filter_value();

                                    bea.setValue_id(jsonObj.getString("id"));
                                    bea.setValue_name(jsonObj.getString("name"));
                                    bea.setValue_opt_id(jsonOpti.getString("id"));

                                    bean_value.add(bea);

                                }

                                bean.setValue_list(bean_value);

                                bean_option.add(bean);

                            }
*/

                        setLayout(bean_cat);


                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class SetCategoryOption extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_Filter.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("cat_ids", cat_data));

                //System.out.println("cat_ids - " + cat_data);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Product/App_Get_Options", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_Filter.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_Filter.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {


                        JSONObject jobj = new JSONObject(result_1);


                        if (jobj != null) {

                            // bean_cat.clear();
                            //   bean_subcat.clear();
                            bean_value.clear();
                            bean_option.clear();


                           /* JSONArray jsonArray = jObj.optJSONArray("category");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Bean_Filter_Cat bean = new Bean_Filter_Cat();


                                bean.setCat_id(jsonObject.getString("id"));
                                bean.setCat_name(jsonObject.getString("name"));

                                bean_cat.add(bean);

                            }*/

                            JSONArray jsonOption = jObj.optJSONArray("options");


                            for (int i = 0; i < jsonOption.length(); i++) {
                                JSONObject jsonObject = jsonOption.getJSONObject(i);
                                JSONObject jsonOpti = jsonObject.getJSONObject("Option");

                                Bean_Filter_option bean = new Bean_Filter_option();

                                //Log.e("121212122", "" + jsonOpti.getString("id"));

                                bean.setOpt_id(jsonOpti.getString("id"));
                                bean.setOpt_name(jsonOpti.getString("name"));

                                JSONArray jArray = jsonObject.getJSONArray("OptionValue");

                                for (int j = 0; j < jArray.length(); j++) {

                                    JSONObject jsonObj = jArray.getJSONObject(j);


                                    Bean_Filter_value bea = new Bean_Filter_value();

                                    bea.setValue_id(jsonObj.getString("id"));
                                    bea.setValue_name(jsonObj.getString("name"));
                                    bea.setValue_opt_id(jsonOpti.getString("id"));

                                    bean_value.add(bea);

                                }

                                bean.setValue_list(bean_value);

                                bean_option.add(bean);

                            }
                        }

                        SetOptionLayout(bean_option);


                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Product_Filter.this);

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

    public class SetSubCAtegory extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Product_Filter.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("cat_ids", cat_data));

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Category/App_Get_Sub_Category", ServiceHandler.POST, parameters);

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
                    Globals.CustomToast(Product_Filter.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(Product_Filter.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {


                        JSONObject jobj = new JSONObject(result_1);


                        if (jobj != null) {


                            bean_subcat.clear();


                            JSONArray jsonArray = jObj.optJSONArray("data");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Bean_Filter_SubCat bean = new Bean_Filter_SubCat();


                                bean.setSub_cat_id(jsonObject.getString("id"));
                                bean.setSub_cat_name(jsonObject.getString("name"));

                                bean_subcat.add(bean);

                            }
                        }

                        setSubLayout(bean_subcat);


                        loadingView.dismiss();

                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public void setLayout(ArrayList<Bean_Filter_Cat> str) {
        // TODO Auto-generated method stub
        l_list.removeAllViews();

        for (ij = 0; ij < str.size(); ij++) {

            final LinearLayout lmain = new LinearLayout(Product_Filter.this);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.VERTICAL);
            lmain.setPadding(1, 1, 1, 0);
            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 1, 0);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT, 2.0f);

            LinearLayout l1 = new LinearLayout(Product_Filter.this);
            l1.setLayoutParams(par1);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            final CheckBox check = new CheckBox(Product_Filter.this);
            Set_Referash_Data();

            if (bean_d_cat.size() != 0) {
                int k = 0;
                for (int i = 0; i < bean_d_cat.size(); i++) {
                    if (k != 1) {
                        if (str.get(ij).getCat_id().equalsIgnoreCase(bean_d_cat.get(i).getCat_id())) {
                            check.setChecked(true);
                            check.setTag(str.get(ij).getCat_id());
                            k = 1;
                        } else {
                            check.setChecked(false);
                            check.setTag(str.get(ij).getCat_id());
                        }
                    }
                }
            } else {
                check.setChecked(false);
                check.setTag(str.get(ij).getCat_id());
            }
            l1.addView(check);

            final TextView txt_name = new TextView(Product_Filter.this);
            txt_name.setTextColor(Color.parseColor("#000000"));
            txt_name.setText(str.get(ij).getCat_name());
			/* txt_name.setWidth(200); */
            txt_name.setMaxLines(2);
            txt_name.setTextSize(14);
            txt_name.setGravity(Gravity.CENTER | Gravity.LEFT);
            txt_name.setEllipsize(TextUtils.TruncateAt.END);
            txt_name.setTag(str.get(ij).getCat_name());
            l1.addView(txt_name);

            final View v = new View(Product_Filter.this);
            v.setLayoutParams(par2);
            v.setBackgroundColor(Color.parseColor("#E4E4E4"));

            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) // true if the checkbox is checked, false
                    // if unchecked
                    {

                        String catid = buttonView.getTag().toString();

                        String catname = txt_name.getTag().toString();

                        Set_Referash_Data();
                        if (bean_d_cat.size() == 0) {
                            db = new DatabaseHandler(Product_Filter.this);
                            db.Add_Category(new Bean_F_Cat(catid, catname));
                            db.close();
                        } else {

                            int k = 0;
                            for (int i = 0; i < bean_d_cat.size(); i++) {

                                if (catid.equalsIgnoreCase(bean_d_cat.get(i).getCat_id().toString())) {

                                } else {
                                    if (k != 1) {

                                        db = new DatabaseHandler(
                                                Product_Filter.this);
                                        db.Add_Category(new Bean_F_Cat(catid, catname));
                                        db.close();
                                    }
                                    k = 1;
                                }
                            }
                        }
                    } else {


                        String catid = buttonView.getTag().toString();

                        Set_Referash_Data();
                        for (int j = 0; j < bean_d_cat.size(); j++) {
                            if (catid.equalsIgnoreCase(bean_d_cat.get(j).getCat_id().toString())) {
                                db = new DatabaseHandler(Product_Filter.this);
                                db.Delete_Cat(bean_d_cat.get(j).getId());
                                db.close();
                            }
                        }
                    }
                }

            });

            lmain.addView(l1);
            lmain.addView(v);

            l_list.addView(lmain);

        }

    }

    protected void Set_Referash_Data() {

        bean_d_cat.clear();
        db = new DatabaseHandler(Product_Filter.this);

        //Log.e("category", appPrefs.getFilter_Cat()+"\n"+appPrefs.getFilter_SubCat()+"\n"+appPrefs.getFilter_Option());

        ArrayList<Bean_F_Cat> category_array_from_db = db.Get_F_Cat();

        /*if(!appPrefs.getFilter_Cat().isEmpty()){
            String categories=appPrefs.getFilter_Cat();

            try {
                JSONArray categoryArray=new JSONArray(categories);

                for(int i=0; i<categoryArray.length(); i++){
                    JSONObject mainObj=categoryArray.getJSONObject(i);

                    Bean_F_Cat cnt = new Bean_F_Cat();
                    cnt.setCat_id(mainObj.getString("id"));
                    bean_d_cat.add(cnt);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/

        for (int i = 0; i < category_array_from_db.size(); i++) {

            int tidno = category_array_from_db.get(i).getId();
            String catid = category_array_from_db.get(i).getCat_id();
            String catname = category_array_from_db.get(i).getCat_name();

            Bean_F_Cat cnt = new Bean_F_Cat();
            cnt.setId(tidno);
            cnt.setCat_id(catid);
            cnt.setCat_name(catname);

            bean_d_cat.add(cnt);
        }
        db.close();
    }

    protected void Set_Referash_Data_value() {

        bean_d_value.clear();
        db = new DatabaseHandler(Product_Filter.this);

        ArrayList<Bean_F_Value> category_array_from_db = db.Get_F_Value();

        for (int i = 0; i < category_array_from_db.size(); i++) {

            int tidno = category_array_from_db.get(i).getId();
            String valueid = category_array_from_db.get(i).getValue_id();
            String valuename = category_array_from_db.get(i).getValue_name();
            String valueoption = category_array_from_db.get(i).getValue_Opt_name();

            Bean_F_Value cnt = new Bean_F_Value();
            cnt.setId(tidno);
            cnt.setValue_id(valueid);
            cnt.setValue_name(valuename);
            cnt.setValue_Opt_name(valueoption);

            bean_d_value.add(cnt);
        }
        db.close();
    }

    protected void Set_Referash_Data_SubCAT() {
        // TODO Auto-generated method stub
        bean_d_subcat.clear();
        db = new DatabaseHandler(Product_Filter.this);

        ArrayList<Bean_F_Subcat> category_array_from_db = db.Get_F_SubCat();

        for (int i = 0; i < category_array_from_db.size(); i++) {

            int tidno = category_array_from_db.get(i).getId();
            String subcatid = category_array_from_db.get(i).getSub_cat_id();
            String subcatname = category_array_from_db.get(i).getSub_cat_name();

            Bean_F_Subcat cnt = new Bean_F_Subcat();
            cnt.setId(tidno);
            cnt.setSub_cat_id(subcatid);
            cnt.setSub_cat_name(subcatname);


            bean_d_subcat.add(cnt);
        }
        db.close();
    }

    public void setSubLayout(ArrayList<Bean_Filter_SubCat> str) {
        // TODO Auto-generated method stub
        l_list.removeAllViews();

        for (ij = 0; ij < str.size(); ij++) {

            final LinearLayout lmain = new LinearLayout(Product_Filter.this);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.VERTICAL);
            lmain.setPadding(1, 1, 1, 0);
            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 1, 0);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT, 2.0f);

            LinearLayout l1 = new LinearLayout(Product_Filter.this);
            l1.setLayoutParams(par1);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            final CheckBox check = new CheckBox(Product_Filter.this);
            Set_Referash_Data_SubCAT();

            if (bean_d_subcat.size() != 0) {
                int k = 0;
                for (int i = 0; i < bean_d_subcat.size(); i++) {
                    if (k != 1) {
                        if (str.get(ij).getSub_cat_id().equalsIgnoreCase(bean_d_subcat.get(i).getSub_cat_id())) {
                            check.setChecked(true);
                            check.setTag(str.get(ij).getSub_cat_id());
                            k = 1;
                        } else {
                            check.setChecked(false);
                            check.setTag(str.get(ij).getSub_cat_id());

                        }
                    }
                }
            } else {
                check.setChecked(false);
                check.setTag(str.get(ij).getSub_cat_id());
            }
            l1.addView(check);

            final TextView txt_name = new TextView(Product_Filter.this);
            txt_name.setTextColor(Color.parseColor("#000000"));
            txt_name.setText(str.get(ij).getSub_cat_name());
			/* txt_name.setWidth(200); */
            txt_name.setMaxLines(2);
            txt_name.setTextSize(14);
            txt_name.setGravity(Gravity.CENTER | Gravity.LEFT);
            txt_name.setEllipsize(TextUtils.TruncateAt.END);
            txt_name.setTag(str.get(ij).getSub_cat_name());
            l1.addView(txt_name);

            final View v = new View(Product_Filter.this);
            v.setLayoutParams(par2);
            v.setBackgroundColor(Color.parseColor("#E4E4E4"));
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) // true if the checkbox is checked, false
                    // if unchecked
                    {


                        String subcatid = buttonView.getTag().toString();

                        String subcatname = txt_name.getTag().toString();

                        Set_Referash_Data_SubCAT();
                        if (bean_d_subcat.size() == 0) {
                            db = new DatabaseHandler(Product_Filter.this);
                            db.Add_SubCategory(new Bean_F_Subcat(subcatid, subcatname));
                            db.close();
                        } else {

                            int k = 0;
                            for (int i = 0; i < bean_d_subcat.size(); i++) {

                                if (subcatid.equalsIgnoreCase(bean_d_subcat.get(i).getSub_cat_id())) {

                                } else {
                                    if (k != 1) {

                                        db = new DatabaseHandler(
                                                Product_Filter.this);
                                        db.Add_SubCategory(new Bean_F_Subcat(subcatid, subcatname));
                                        db.close();
                                    }
                                    k = 1;
                                }
                            }
                        }
                    } else {


                        String subcatid = buttonView.getTag().toString();

                        Set_Referash_Data_SubCAT();
                        for (int j = 0; j < bean_d_subcat.size(); j++) {
                            if (subcatid.equalsIgnoreCase(bean_d_subcat.get(j).getSub_cat_id())) {
                                db = new DatabaseHandler(Product_Filter.this);
                                db.Delete_SubCat(bean_d_subcat.get(j).getId());
                                db.close();
                            }
                        }
                    }
                }

            });

            lmain.addView(l1);
            lmain.addView(v);
            l_list.addView(lmain);

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
                    //  System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
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

                    Globals.CustomToast(Product_Filter.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        appPrefs = new AppPrefs(Product_Filter.this);
                        appPrefs.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        appPrefs = new AppPrefs(Product_Filter.this);
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
