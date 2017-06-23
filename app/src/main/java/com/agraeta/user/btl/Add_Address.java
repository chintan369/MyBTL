package com.agraeta.user.btl;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add_Address  extends AppCompatActivity {

    EditText et_addline1,et_addline2,et_addline3,et_pincode,et_landmark;
    Spinner spn_state,spn_city,spn_country;
    Button btn_save,btn_reset;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    ArrayList<String> countryid = new ArrayList<String>();
    ArrayList<String> countryname = new ArrayList<String>();
    EditText e_fname, e_lname, e_email, e_mobile;
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    Custom_ProgressDialog loadingView;
    String position_state = new String();
    String position_city = new String();
    String position_country = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String position_countryname = new String();
    String user_id_main = new String();
    String json = new String();
    String userid,add1,add2,add3,lmark,pcode,sid,cid,f_name,l_name,email,mobile;
    DatabaseHandler db;
    String smobile = new String();
    String role_id=new String();
    String owner_id = new String();
    String u_id = new String();

    AppPrefs app;

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
        setContentView(R.layout.activity_add__newuser__address);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        FetchId();
        setRefershData();



        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if(role_id.equals(C.COMP_SALES_PERSON)){

                    app =new AppPrefs(Add_Address.this);
                    role_id=app.getSubSalesId().toString();
                    user_id_main=app.getSalesPersonId().toString();
                }
            }

        }else{
            user_id_main ="";
        }
        SetActionBar();

        new send_country_Data().execute();
    }

    private void FetchId() {
        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();
        et_addline1=(EditText)findViewById(R.id.et_addline1);
        et_addline2=(EditText)findViewById(R.id.et_addline2);
        et_addline3=(EditText)findViewById(R.id.et_addline3);
        et_pincode=(EditText)findViewById(R.id.et_pincode);

        et_landmark=(EditText)findViewById(R.id.et_landmark);

        btn_reset=(Button)findViewById(R.id.btn_reset);
        btn_save=(Button)findViewById(R.id.btn_save);
        spn_country=(Spinner)findViewById(R.id.spn_country);
        spn_state=(Spinner)findViewById(R.id.spn_state);
        spn_city=(Spinner)findViewById(R.id.spn_city);
        e_fname = (EditText)findViewById(R.id.etfirst_name_Cust_com);
        e_lname = (EditText)findViewById(R.id.etlast_name_Cust_com);
        e_email = (EditText)findViewById(R.id.Et_Emailid);
        e_mobile = (EditText)findViewById(R.id.etmobile);


        spn_country.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_pincode.getWindowToken(), 0);
                return false;
            }
        }) ;

        spn_state.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_pincode.getWindowToken(), 0);
                return false;
            }
        }) ;

        spn_city.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_pincode.getWindowToken(), 0);
                return false;
            }
        }) ;


        e_mobile.setText("+91-");
        Selection.setSelection(e_mobile.getText(), e_mobile.getText().length());
        setRefershData();
        if (user_data.size() == 0) {

        } else {
            for (int i = 0; i < user_data.size(); i++) {

                e_fname.setText(user_data.get(i).getF_name());
                e_lname.setText(user_data.get(i).getL_name());
                e_mobile.setText("+91-"+user_data.get(i).getPhone_no());
                //Log.e("112121",user_data.get(i).getPhone_no());
                e_email.setText(user_data.get(i).getEmail_id());

                // kk = 1;

            }
        }
        e_mobile.addTextChangedListener(new TextWatcher() {

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
                    e_mobile.setText("+91-");
                    Selection.setSelection(e_mobile.getText(), e_mobile.getText().length());

                }

            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                smobile = e_mobile.getText().toString().trim().replace("+91-", "");


                //Log.e("ssssss", "" + smobile);


                if (e_fname.getText().toString().trim().equalsIgnoreCase("")) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Add_Address.this, "Please Enter First Name", getLayoutInflater());
                    e_fname.requestFocus();
                } else if (e_fname.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Add_Address.this, "Please Enter Atleast 2 character", getLayoutInflater());
                    e_fname.requestFocus();
                } else if (e_lname.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(Add_Address.this, "Please Enter Last Name", getLayoutInflater());
                    e_lname.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (e_lname.length() < 2) {
                    Globals.CustomToast(Add_Address.this, "Please Enter Atleast 2 Character", getLayoutInflater());
                    e_lname.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (e_email.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(Add_Address.this, "Please enter email address", getLayoutInflater());
                    e_email.requestFocus();
                    //  Toast.makeText(Business_Registration.this,"Please enter email address",Toast.LENGTH_LONG).show();
                } else if (validateEmail1(e_email.getText().toString()) != true) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Add_Address.this, "Please enter a valid email address", getLayoutInflater());
                    e_email.requestFocus();
                } else if (smobile.equalsIgnoreCase("")) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Add_Address.this, "Please Enter Mobile Number", getLayoutInflater());
                    e_mobile.requestFocus();
                } else if (e_mobile.length() < 14) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Add_Address.this, "Mobile Number is Compulsory 10 Digit", getLayoutInflater());
                    e_mobile.requestFocus();
                }
                else if (et_addline1.getText().toString().equalsIgnoreCase("")) {
                    Globals.CustomToast(Add_Address.this, "Please Enter Address Line1", getLayoutInflater());
                    et_addline1.requestFocus();
                } else if (et_pincode.getText().toString().equalsIgnoreCase("")) {
                    Globals.CustomToast(Add_Address.this, "Please Enter Address Pincode", getLayoutInflater());
                    et_pincode.requestFocus();
                } else if (et_pincode.length() < 6) {
                    Globals.CustomToast(Add_Address.this, "Pincode 6 character required", getLayoutInflater());
                    et_pincode.requestFocus();
                }else if (position_country.equalsIgnoreCase("0")) {
                    Globals.CustomToast(Add_Address.this, "Please Select Country", getLayoutInflater());
                    spn_country.requestFocus();
                }  else if (position_state.equalsIgnoreCase("0")) {
                    Globals.CustomToast(Add_Address.this, "Please Select State", getLayoutInflater());
                    spn_state.requestFocus();
                } else if (position_city.equalsIgnoreCase("0")) {
                    Globals.CustomToast(Add_Address.this, "Please Select city", getLayoutInflater());
                    spn_city.requestFocus();
                } else {

                    f_name = e_fname.getText().toString().trim();
                    l_name = e_lname.getText().toString().trim();
                    email= e_email.getText().toString().trim();
                    mobile = e_mobile.getText().toString().trim();
                    add1 = et_addline1.getText().toString().trim();
                    add2 = et_addline2.getText().toString().trim();
                    add3 = et_addline3.getText().toString().trim();
                    lmark = et_landmark.getText().toString().trim();
                    pcode = et_pincode.getText().toString().trim();

                    new set_Add_Address().execute();

                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_addline1.setText("");
                et_addline2.setText("");
                et_addline3.setText("");
                et_pincode.setText("");
                et_landmark.setText("");
                spn_state.setSelection(-1);
                spn_city.setSelection(-1);
                spn_country.setSelection(-1);

            }
        });

        spn_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_country = countryid.get(position);
                position_countryname = countryname.get(position);
                if (position_country.equalsIgnoreCase("0")) {
//Globals.CustomToast(Add_Newuser_Address.this,"Please Select State",getLayoutInflater());
                } else {
                    new send_state_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if (position_state.equalsIgnoreCase("0")) {
//Globals.CustomToast(Add_Newuser_Address.this,"Please Select State",getLayoutInflater());
                } else {
                    new send_city_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if (position_city.equalsIgnoreCase("0")) {
//                    Globals.CustomToast(Add_Newuser_Address.this,"Please Select City",getLayoutInflater());
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


    }

    private void SetActionBar() {

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

        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        img_cart.setVisibility(View.VISIBLE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        app = new AppPrefs(Add_Address.this);
        String qun =app.getCart_QTy();

       /* setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    app = new AppPrefs(Add_Address.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    app = new AppPrefs(Add_Address.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                    //Log.e("IDIDD",""+app.getSalesPersonId().toString());
                }else{
                    u_id=owner_id;
                }


            }

            List<NameValuePair> para=new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id",u_id));


            String json=GetCartByQty(para);

            try {


                //System.out.println(json);

                if (json==null
                        || (json.equalsIgnoreCase(""))) {
                    *//*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*//*
                    Globals.CustomToast(Add_Address.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(Add_Address.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(Add_Address.this);
                        app.setCart_QTy(""+qu);


                    }

                }
            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

        }else{
            app = new AppPrefs(Add_Address.this);
            app.setCart_QTy("");
        }*/

        app = new AppPrefs(Add_Address.this);
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
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Add_Address.this, Saved_Address.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(Add_Address.this);
                app.setUser_notification("Add_Address");
                Intent i = new Intent(Add_Address.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Add_Address.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(Add_Address.this, Saved_Address.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    };

    public class send_state_Data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Add_Address.this, "");

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

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"State/App_GetState",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
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
                    Globals.CustomToast(Add_Address.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Add_Address.this,""+Message, getLayoutInflater());
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


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(Add_Address.this,  android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_state.setAdapter(adapter_state);


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
    public class send_city_Data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Add_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("state_id", position_state));



                //Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"City/App_GetCity",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
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
            cityname.clear();
            cityid.clear();
            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Add_Address.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Add_Address.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
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


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(Add_Address.this,  android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_city.setAdapter(adapter_city);

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

    public class set_Add_Address extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Add_Address.this, "");

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
                parameters.add(new BasicNameValuePair("first_name", f_name));
                parameters.add(new BasicNameValuePair("last_name", l_name));
                parameters.add(new BasicNameValuePair("email", email));
                parameters.add(new BasicNameValuePair("mobile", mobile));
                parameters.add(new BasicNameValuePair("address_1", add1));
                parameters.add(new BasicNameValuePair("address_2", add2));
                parameters.add(new BasicNameValuePair("address_3", add3));
              //  parameters.add(new BasicNameValuePair("landmark", lmark));
                parameters.add(new BasicNameValuePair("pincode", pcode));
                parameters.add(new BasicNameValuePair("country_id", position_country));
                parameters.add(new BasicNameValuePair("state_id", position_state));
                parameters.add(new BasicNameValuePair("city_id", position_city));
                parameters.add(new BasicNameValuePair("default_address", "0"));

                //Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Address/App_Add_Address",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
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
                    Globals.CustomToast(Add_Address.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Add_Address.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        loadingView.dismiss();

                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Add_Address.this, "" + Message, getLayoutInflater());

                        Intent i = new Intent(Add_Address.this, Saved_Address.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }
    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Add_Address.this);

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
    private boolean validateEmail1(String email) {
        // TODO Auto-generated method stub

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
    public class send_country_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Add_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                //  parameters.add(new BasicNameValuePair("state_id", position_state));


                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Country/App_GetCountry", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                ////System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                ////System.out.println("error1: " + e.toString());

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
                    Globals.CustomToast(Add_Address.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Add_Address.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            countryname.add("Select Country");
                            countryid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                countryname.add(cname);
                                countryid.add(cId);


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(Add_Address.this, android.R.layout.simple_spinner_item, countryname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_country.setAdapter(adapter_city);

                            }
                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

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
                    //  System.out.println("error1: " + e.toString());
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
}
