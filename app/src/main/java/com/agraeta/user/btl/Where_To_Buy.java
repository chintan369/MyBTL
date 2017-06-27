package com.agraeta.user.btl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Where_To_Buy extends AppCompatActivity {

    Spinner spnstate,spncity;
    String owner_id = new String();
    String u_id = new String();
    String role_id = new String();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    String cartJSON="";
    boolean hasCartCallFinish=true;

    TextView txt;


    EditText et_pincode;
    Button btn_search,btn_filter;
    ListView layout_display;
    TextView tvslectcity,tvpincode,tvstate;
    String position_state = new String();
    String position_city = new String();
    String json = new String();
    String position_statename = new String();
    String position_cityname = new String();
    ResultHolder result_holder;
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    int count = 0;
    DatabaseHandler db;
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    Custom_ProgressDialog loadingView;
    ArrayList<Bean_WhereToBuy> bean_wheretobuy = new ArrayList<Bean_WhereToBuy>();
    String pincode_final;
    AppPrefs apps;
    ImageView mapImg;
    LinearLayout.LayoutParams params;
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
        setContentView(R.layout.activity_service_center);
        apps = new AppPrefs(Where_To_Buy.this);
        Where_To_Buy.this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        params = new LinearLayout.LayoutParams(android.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        //  setActionBar();
        setActionBar();
        fetchID();
        
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
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        img_cart.setVisibility(View.VISIBLE);
         txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        apps = new AppPrefs(Where_To_Buy.this);
        String qun =apps.getCart_QTy();

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent i = new Intent(Where_To_Buy.this,Product_List.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
                onBackPressed();
            }
        });

        if (apps.getUserRoleId().equals(C.ADMIN)) {
            img_cart.setVisibility(View.GONE);
            img_home.setVisibility(View.GONE);
            frame.setVisibility(View.GONE);
            txt.setVisibility(View.GONE);
            img_notification.setVisibility(View.GONE);
            img_category.setVisibility(View.GONE);

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            return;
        }

        setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    apps = new AppPrefs(Where_To_Buy.this);
                    role_id = apps.getSubSalesId().toString();
                    u_id = apps.getSalesPersonId().toString();
                }else{
                    u_id=owner_id;
                }
            }

            List<NameValuePair> para=new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id",u_id));

            new GetCartByQty(para).execute();
        }else{
            apps = new AppPrefs(Where_To_Buy.this);
            apps.setCart_QTy("");
        }

        apps = new AppPrefs(Where_To_Buy.this);
        String qu1 = apps.getCart_QTy();
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


        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Where_To_Buy.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Where_To_Buy.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apps = new AppPrefs(Where_To_Buy.this);
                apps.setUser_notification("Where_To_Buy");
                Intent i = new Intent(Where_To_Buy.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub

        /*Intent i = new Intent(Where_To_Buy.this,Product_List.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);*/
        finish();
    }

    private void fetchID()
    {
        spnstate=(Spinner)findViewById(R.id.spnstate);
        spncity=(Spinner)findViewById(R.id.spncity);
        btn_search=(Button)findViewById(R.id.btn_search);
        et_pincode=(EditText)findViewById(R.id.et_pincode);
        layout_display=(ListView)findViewById(R.id.layout_display);
        tvslectcity=(TextView)findViewById(R.id.tvslectcity);
        tvstate=(TextView)findViewById(R.id.tvstate);
        tvpincode=(TextView)findViewById(R.id.tvpincode);
        //mapImg=(ImageView)findViewById(R.id.mapImg);
        btn_filter=(Button)findViewById(R.id.btn_filter);
        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();


        new send_state_Data().execute();

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateid.clear();
                statename.clear();
                cityid.clear();
                cityname.clear();
                et_pincode.setText("");
                btn_filter.setVisibility(View.GONE);
                spnstate.setVisibility(View.VISIBLE);
                spncity.setVisibility(View.GONE);
                btn_search.setVisibility(View.VISIBLE);
                et_pincode.setVisibility(View.GONE);
                layout_display.setVisibility(View.GONE);
                tvslectcity.setVisibility(View.GONE);
                tvstate.setVisibility(View.VISIBLE);
                tvpincode.setVisibility(View.GONE);
                new send_state_Data().execute();
            }
        });

        spnstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                position_state = stateid.get(position);
                position_statename = statename.get(position);
                //spncity.setVisibility(View.VISIBLE);

                if (position_state.equalsIgnoreCase("0")) {
                    //  Globals.CustomToast(Where_To_Buy.this.getApplicationContext(), "Please Select State", Where_To_Buy.this.getLayoutInflater());
                } else {
                    tvslectcity.setVisibility(View.VISIBLE);
                    //  tvpincode.setVisibility(View.VISIBLE);
                    spncity.setVisibility(View.VISIBLE);
                    new send_city_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spncity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if (position_city.equalsIgnoreCase("0")) {
                    //Globals.CustomToast(Where_To_Buy.this.getApplicationContext(), "Please Select City", Where_To_Buy.this.getLayoutInflater());
                } else {
                    et_pincode.setText("");
                    et_pincode.setVisibility(View.VISIBLE);
                    tvpincode.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position_state.equalsIgnoreCase("0")){
                    Globals.CustomToast(Where_To_Buy.this.getApplicationContext(), "Please Select State", Where_To_Buy.this.getLayoutInflater());
                }else if(position_city.equalsIgnoreCase("0")){
                    Globals.CustomToast(Where_To_Buy.this.getApplicationContext(), "Please Select City", Where_To_Buy.this.getLayoutInflater());
                }else if (et_pincode.length() < 6 && !et_pincode.getText().toString().equalsIgnoreCase("")) {

                    Globals.CustomToast(Where_To_Buy.this, "Pincode 6 character required",getLayoutInflater());
                }
              /*  else if (et_pincode.getText().toString().equalsIgnoreCase("")) {

                    Globals.CustomToast(Where_To_Buy.this.getApplicationContext(), "Please Enter Pincode", Where_To_Buy.this.getLayoutInflater());
                }*/ else {
                    pincode_final = et_pincode.getText().toString();

                    btn_filter.setVisibility(View.VISIBLE);
                    spnstate.setVisibility(View.GONE);
                    spncity.setVisibility(View.GONE);
                    btn_search.setVisibility(View.GONE);
                    et_pincode.setVisibility(View.GONE);

                    tvslectcity.setVisibility(View.GONE);
                    tvstate.setVisibility(View.GONE);
                    tvpincode.setVisibility(View.GONE);

                    new get_wheretobuydetails().execute();
                    // mapImg.setVisibility(View.VISIBLE);
                }


            }
        });
    }

  /*  private void setActionBar() {

        // TODO Auto-generated method stub
        android.support.v7.app.ActionBar mActionBar = ;
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        //  ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);

        img_cart.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.back_min);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Where_To_Buy.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Where_To_Buy.this, Business_Registration.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Where_To_Buy.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Where_To_Buy.this, Cart1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
    }*/

    private void setLayout(ArrayList<Bean_WhereToBuy> str) {
        // TODO Auto-generated method stub

        layout_display.removeAllViews();

        //Log.e("str.size", "" + str.size());

        for (int ij = 0; ij < str.size(); ij++) {

            LinearLayout lmain = new LinearLayout(Where_To_Buy.this);
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
            LinearLayout.LayoutParams vi = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 1);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    200, 200, 1.0f);
            LinearLayout l1 = new LinearLayout(Where_To_Buy.this);

            l1.setLayoutParams(par1);
            l1.setPadding(5, 5, 5, 5);
            l1.setOrientation(LinearLayout.VERTICAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(Where_To_Buy.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 5, 5, 5);

            final LinearLayout l2 = new LinearLayout(Where_To_Buy.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 5, 5, 5);
            l2.setVisibility(View.GONE);

            count = ij;
            final ImageView img_a = new ImageView(Where_To_Buy.this);
            final TextView tv_brachname = new TextView(Where_To_Buy.this);
            final TextView tv_address1 = new TextView(Where_To_Buy.this);
            final TextView tvaddress2 = new TextView(Where_To_Buy.this);
            final View viewline = new View(Where_To_Buy.this);

            tv_brachname.setPadding(5, 5, 5, 5);
            tv_address1.setPadding(5, 2, 5, 2);
            tvaddress2.setPadding(5, 2, 2, 5);
            tv_brachname.setTextColor(Color.BLACK);
            tv_address1.setTextColor(Color.BLACK);
            tvaddress2.setTextColor(Color.BLACK);
            viewline.setLayoutParams(vi);
            viewline.setBackgroundColor(Color.BLACK);

            // img_a.setImageResource(str.get(count));
            //Log.e("count", "" + count);
            // Log.e("img", "" + str.get(count).getId());
            try {


                String branch_name = str.get(count).getBranch_name();
                String address_1 = str.get(count).getAddress_1() + "," + str.get(count).getAddress_2();
                String address_2 = str.get(count).getCity() + "," + str.get(count).getState() + "," + str.get(count).getPincode();

                //Log.e("branchname", "" + branch_name);
                //Log.e("address_1", "" + address_1);
                //Log.e("address_2", "" + address_2);


                tv_brachname.setText(branch_name);
                tv_address1.setText(address_1);
                tvaddress2.setText(address_2);
            } catch (NullPointerException e) {
                //Log.e("Error", "" + e);
            }


            par21.setMargins(5, 5, 5, 5);

            img_a.setLayoutParams(par21);
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            //   l1.addView(img_a);
            l1.addView(tv_brachname);
            l1.addView(tv_address1);
            l1.addView(tvaddress2);
            l1.addView(viewline);




           /* img_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppPrefs app= new AppPrefs(Where_To_Buy.this);

                    Log.e("url_tonext_page",url);
                    Intent i = new Intent(Where_To_Buy.this,Video_View_Activity.class);
                    app.setyoutube_api(url);
                    startActivity(i);
                }
            });*/
            lmain.addView(l1);

            layout_display.addView(lmain);

        }

    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Where_To_Buy.this);

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

    static class ResultHolder {


        TextView tvaddress;

        ImageView img_edit, img_delete, img_on;


    }

    public class send_state_Data extends AsyncTask<Void,Void,String>
    {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Where_To_Buy.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("country_id", "1"));



                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"State/App_GetState_Selected",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

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
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Where_To_Buy.this, "SERVER ERROR", Where_To_Buy.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        // Globals.CustomToast(Where_To_Buy.this,""+Message, Where_To_Buy.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            statename.add("Select State");
                            stateid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                statename.add(sname);
                                stateid.add(sId);


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(Where_To_Buy.this,  android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnstate.setAdapter(adapter_state);

                            }
                        }
                        loadingView.dismiss();
                        // new send_city_Data().execute();
                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class send_city_Data extends AsyncTask<Void,Void,String>
    {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Where_To_Buy.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("state_id",""+ position_state));

                //Log.e("state_id", "" + position_state);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"City/App_GetCity_Selected",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
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

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Where_To_Buy.this, "SERVER ERRER", Where_To_Buy.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Where_To_Buy.this,""+Message, Where_To_Buy.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        //Log.e("result_1", "" + result_1);
                        JSONObject jobj = new JSONObject(result_1);
                        cityname.clear();
                        cityid.clear();
                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            cityname.add("Select City");
                            cityid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                cityname.add(cname);
                                cityid.add(cId);


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(Where_To_Buy.this,  android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spncity.setAdapter(adapter_city);

                            }
                        }
                        loadingView.dismiss();

                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class get_wheretobuydetails extends AsyncTask<Void,Void,String>
    {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Where_To_Buy.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("state_id",""+ position_state));
                parameters.add(new BasicNameValuePair("city_id",""+ position_city));
                //parameters.add(new BasicNameValuePair("pincode",""+ pincode_final));
                parameters.add(new BasicNameValuePair("pincode",""+ pincode_final));

                Log.e("params",parameters.toString());


                //Log.e("where to buy web", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"ServiceCenter/App_GetServiceCenter",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
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

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Where_To_Buy.this, "SERVER ERROR", Where_To_Buy.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Where_To_Buy.this,""+Message, Where_To_Buy.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            bean_wheretobuy.clear();
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);




                                Bean_WhereToBuy bean = new Bean_WhereToBuy();
                                bean.setId(catObj.getString("id"));
                                bean.setBranch_name(catObj.getString("branch_name"));
                                bean.setPincode(catObj.getString("pincode"));
                                bean.setAddress_1(catObj.getString("address_1"));
                                //bean.setAddress_2(catObj.getString("address_2"));
                                bean.setCity(catObj.getString("city"));
                                bean.setState(catObj.getString("state"));
                                bean.setLatitude(catObj.getString("latitude"));
                                bean.setLongitude(catObj.getString("longitude"));


                                bean_wheretobuy.add(bean);



                            }
                        }
                        loadingView.dismiss();
                        layout_display.setVisibility(View.VISIBLE);
                        layout_display.setAdapter(new CustomResultAdapterDoctor());
                        //  setLayout(bean_wheretobuy);
                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class CustomResultAdapterDoctor extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) Where_To_Buy.this.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bean_wheretobuy.size();
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

            if (convertView == null) {

                convertView = vi.inflate(R.layout.address_row, null);

            }

            result_holder.tvaddress = (TextView) convertView
                    .findViewById(R.id.address);

            result_holder.img_edit = (ImageView) convertView
                    .findViewById(R.id.add_edit);
            result_holder.img_delete = (ImageView) convertView.findViewById(R.id.add_delete);
            result_holder.img_on = (ImageView) convertView.findViewById(R.id.img_on);


            result_holder.img_edit.setImageResource(R.drawable.glob);
            result_holder.img_delete.setVisibility(View.GONE);
            result_holder.img_edit.setVisibility(View.GONE);
            result_holder.img_on.setVisibility(View.GONE);
            String final_address = "";
            if(bean_wheretobuy.get(position).getBranch_name().equalsIgnoreCase("")||bean_wheretobuy.get(position).getBranch_name().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = bean_wheretobuy.get(position).getBranch_name();
            }

            if(bean_wheretobuy.get(position).getAddress_1().equalsIgnoreCase("")||bean_wheretobuy.get(position).getAddress_1().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+",\n"+ bean_wheretobuy.get(position).getAddress_1();
            }

            if(bean_wheretobuy.get(position).getAddress_2()==null || bean_wheretobuy.get(position).getAddress_2().isEmpty() ||bean_wheretobuy.get(position).getAddress_2().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ bean_wheretobuy.get(position).getAddress_2();
            }



            if(bean_wheretobuy.get(position).getCity().equalsIgnoreCase("")||bean_wheretobuy.get(position).getCity().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+",\n"+ bean_wheretobuy.get(position).getCity();
            }

            if(bean_wheretobuy.get(position).getPincode().equalsIgnoreCase("")||bean_wheretobuy.get(position).getPincode().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+"-"+ bean_wheretobuy.get(position).getPincode();
            }

            if(bean_wheretobuy.get(position).getState().equalsIgnoreCase("")||bean_wheretobuy.get(position).getState().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ bean_wheretobuy.get(position).getState();
            }

            if((bean_wheretobuy.get(position).getLatitude()!=null && bean_wheretobuy.get(position).getLongitude()!=null) ||
                    (!bean_wheretobuy.get(position).getLatitude().equals("null") && !bean_wheretobuy.get(position).getLongitude().equals("null"))){
                result_holder.img_edit.setVisibility(View.VISIBLE);
                result_holder.img_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float latitude=Float.parseFloat(bean_wheretobuy.get(position).getLatitude());
                        float longitude=Float.parseFloat(bean_wheretobuy.get(position).getLongitude());
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f",latitude,longitude);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                });
            }



            result_holder.tvaddress.setText(final_address);

            return convertView;
        }

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

                    Globals.CustomToast(Where_To_Buy.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        apps = new AppPrefs(Where_To_Buy.this);
                        apps.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        apps = new AppPrefs(Where_To_Buy.this);
                        apps.setCart_QTy(""+qu);


                    }

                }


            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = apps.getCart_QTy();
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
