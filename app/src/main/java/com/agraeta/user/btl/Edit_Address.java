package com.agraeta.user.btl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

public class Edit_Address extends AppCompatActivity {

    EditText edt_add1,edt_add2,edt_add3,edt_pincode;

    Spinner sp_state,sp_city,sp_country;

    String addId = new String();
    String add1 = new String();
    String add2 = new String();
    String add3 = new String();
    String pincode = new String();
    String state = new String();
    String country = new String();
    String city = new String();
    Button save,reset;

    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();

    ArrayList<String> countryid = new ArrayList<String>();
    ArrayList<String> countryname = new ArrayList<String>();

    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();

    String position_state = new String();
    String position_city = new String();
    String position_country = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String position_countryname = new String();
    String owner_id = new String();
    String u_id = new String();
    String role_id = new String();
    Custom_ProgressDialog loadingView;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    DatabaseHandler db;
    String cartJSON="";
    boolean hasCartCallFinish=true;

    TextView txt;



    AppPrefs apps;
    String json = new String();
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
        setContentView(R.layout.activity_edit__address);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        SetActionBar();
        addId = getIntent().getExtras().getString("addId").toString();
         add1 =getIntent().getExtras().getString("add1").toString();
         add2 = getIntent().getExtras().getString("add2").toString();
         add3 = getIntent().getExtras().getString("add3").toString();
         pincode = getIntent().getExtras().getString("pincode").toString();
        country = getIntent().getExtras().getString("country").toString();
         state = getIntent().getExtras().getString("state").toString();
         city = getIntent().getExtras().getString("city").toString();


        FetchId();
    }

    private void FetchId() {

        save = (Button) findViewById(R.id.btn_save_dist);
        reset = (Button) findViewById(R.id.btn_Reset_dist);
        edt_add1 = (EditText) findViewById(R.id.Et_Addressline_no1_direct);
        edt_add2 = (EditText) findViewById(R.id.Et_Addressline_no2_direct);
        edt_add3 = (EditText) findViewById(R.id.Et_Addressline_no3_direct);

        edt_pincode = (EditText) findViewById(R.id.Et_Pincode_direct);
        sp_country = (Spinner) findViewById(R.id.spn_select_country);
        sp_state = (Spinner) findViewById(R.id.spn_select_state);
        sp_city = (Spinner) findViewById(R.id.spn_select_city);

        countryid.clear();
        countryname.clear();
        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();

        if(add1.equalsIgnoreCase("") || add1.equalsIgnoreCase("null")){

        }else{
            edt_add1.setText(add1);
        }

        if(add2.equalsIgnoreCase("") || add2.equalsIgnoreCase("null")){

        }else{
            edt_add2.setText(add2);
        }

        if(add3.equalsIgnoreCase("") || add3.equalsIgnoreCase("null")){

        }else{
            edt_add3.setText(add3);
        }

        if(pincode.equalsIgnoreCase("") || pincode.equalsIgnoreCase("null")){

        }else{
            edt_pincode.setText(pincode);
        }

        
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                edt_add1.setText("");
                edt_add2.setText("");
                edt_add3.setText("");
                edt_pincode.setText("");
                stateid.clear();
                statename.clear();
                cityid.clear();
                cityname.clear();
                countryid.clear();
                countryname.clear();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_add1.getText().toString().trim().equalsIgnoreCase("")) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Edit_Address.this, "Please Enter Address 1", Edit_Address.this.getLayoutInflater());
                    edt_add1.requestFocus();
                } /*else if (edt_add2.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(Edit_Address.this, "Please Enter Address 2", Edit_Address.this.getLayoutInflater());
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (edt_add3.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(Edit_Address.this, "Please Enter Address 3", Edit_Address.this.getLayoutInflater());
                    //  Toast.makeText(Business_Registration.this,"Please enter email address",Toast.LENGTH_LONG).show();
                }*/ else if (edt_pincode.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(Edit_Address.this, "Please Enter Pincode", Edit_Address.this.getLayoutInflater());
                    edt_pincode.requestFocus();
                } else if (edt_pincode.length() < 6) {
                    Globals.CustomToast(Edit_Address.this, "Pincode 6 character required", getLayoutInflater());
                    edt_pincode.requestFocus();
                } else if (position_country.equalsIgnoreCase("0")) {
                    Globals.CustomToast(Edit_Address.this, "Please Select Country", Edit_Address.this.getLayoutInflater());
                    sp_country.requestFocus();
                } else if (position_state.equalsIgnoreCase("0")) {
                    sp_state.requestFocus();
                    Globals.CustomToast(Edit_Address.this, "Please Select State", Edit_Address.this.getLayoutInflater());
                } else if (position_city.equalsIgnoreCase("0")) {
                    sp_city.requestFocus();
                    Globals.CustomToast(Edit_Address.this, "Please Select city", Edit_Address.this.getLayoutInflater());
                } else {

                    add1 = edt_add1.getText().toString().trim();
                    add2 = edt_add2.getText().toString().trim();
                    add3 = edt_add3.getText().toString().trim();
                    pincode = edt_pincode.getText().toString().trim();
                    country = position_country;
                    state = position_state;
                    city = position_city;


                    new set_edit_Address().execute();

                }
            }
        });
        new send_country_Data().execute();
        //new send_state_Data().execute();

        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_country = countryid.get(position);
                position_countryname = countryname.get(position);
                if(position_country.equalsIgnoreCase("0")){}
                else{
                    new send_state_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if(position_state.equalsIgnoreCase("0")){

                }
                else{
                    new send_city_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if(position_city.equalsIgnoreCase("0")){}
                else{

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
        img_cart.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        apps= new AppPrefs(Edit_Address.this);
        String qun =apps.getCart_QTy();

        setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    apps = new AppPrefs(Edit_Address.this);
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
            apps = new AppPrefs(Edit_Address.this);
            apps.setCart_QTy("");
        }

        apps = new AppPrefs(Edit_Address.this);
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
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Edit_Address.this, Saved_Address.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apps = new AppPrefs(Edit_Address.this);
                apps.setUser_notification("Edit_Address");
                Intent i = new Intent(Edit_Address.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Edit_Address.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(Edit_Address.this, Saved_Address.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    };
    public class send_country_Data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Edit_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

               // parameters.add(new BasicNameValuePair("country_id", "1"));



                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Country/App_GetCountry",ServiceHandler.POST,parameters);
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
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Edit_Address.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Edit_Address.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            countryname.add("Select Country");
                            countryid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                countryname.add(sname);
                                countryid.add(sId);


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(Edit_Address.this,  android.R.layout.simple_spinner_item, countryname);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_country.setAdapter(adapter_state);
                                if(country.equalsIgnoreCase("") || country.equalsIgnoreCase("null")){

                                }else{
                                    for (int j = 0; j < countryid.size(); j++) {

                                        //Log.e("", "" + countryid.get(j) + "   -- " + countryid);
                                        if (countryid.get(j).equalsIgnoreCase(country)) {
                                            sp_country.setSelection(j);
                                            position_country = country;
                                        }
                                    }
                                }


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
                        Edit_Address.this, "");

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
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Edit_Address.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Edit_Address.this,""+Message, getLayoutInflater());
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


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(Edit_Address.this,  android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_state.setAdapter(adapter_state);
                                if(state.equalsIgnoreCase("") || state.equalsIgnoreCase("null")){

                                }else{
                                    for (int j = 0; j < stateid.size(); j++) {

                                        //Log.e("", "" + stateid.get(j) + "   -- " + stateid);
                                        if (stateid.get(j).equalsIgnoreCase(state)) {
                                            sp_state.setSelection(j);
                                            position_state = state;
                                        }
                                    }
                                }
                               

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
                        Edit_Address.this, "");

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
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Edit_Address.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Edit_Address.this,""+Message, getLayoutInflater());
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


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(Edit_Address.this,  android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_city.setAdapter(adapter_city);

                                if(city.equalsIgnoreCase("") || city.equalsIgnoreCase("null")){

                                }else{
                                    for (int j = 0; j < cityid.size(); j++) {

                                        //Log.e("", "" + cityid.get(j) + "   -- " + cityid);
                                        if (cityid.get(j).equalsIgnoreCase(city)) {
                                            sp_city.setSelection(j);
                                            position_city = city;
                                        }
                                    }
                                }

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

    public class set_edit_Address extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Edit_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                parameters.add(new BasicNameValuePair("id", "" + addId));
                parameters.add(new BasicNameValuePair("address_1", "" + add1));
                parameters.add(new BasicNameValuePair("address_2", ""+add2));
                parameters.add(new BasicNameValuePair("address_3", "" + add3));
            //    parameters.add(new BasicNameValuePair("landmark", ""));
                parameters.add(new BasicNameValuePair("pincode", ""+pincode));
                parameters.add(new BasicNameValuePair("country_id", "1"));
                parameters.add(new BasicNameValuePair("state_id", "" + position_state));
                parameters.add(new BasicNameValuePair("city_id", ""+position_city));


                //Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Address/App_Edit_Address",ServiceHandler.POST,parameters);
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
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Edit_Address.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Edit_Address.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        loadingView.dismiss();

                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Edit_Address.this, "" + Message, getLayoutInflater());

                        Intent i = new Intent(Edit_Address.this, Saved_Address.class);
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
        db = new DatabaseHandler(Edit_Address.this);

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

                    Globals.CustomToast(Edit_Address.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        apps = new AppPrefs(Edit_Address.this);
                        apps.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        apps = new AppPrefs(Edit_Address.this);
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
