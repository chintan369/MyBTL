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

public class BTL_DirectCustomer extends AppCompatActivity {
    Button save, reset;
    String str_addressmain = new String();
    String str_profilemain = new String();
    String str_responce = new String();

    EditText address_1, address_2, address_3, landmark, pincode, state_id, city_id;

    Spinner sp_state, sp_city;

    String position_state = new String();
    String position_city = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String user_id = new String();
    String json = new String();

    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();

    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    Custom_ProgressDialog loadingView;

    DatabaseHandler db;

    AppPrefs apps;
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
        setContentView(R.layout.activity_btl__direct_customer);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setActionBar();
        fetchId();
    }

    private void fetchId() {


        save = (Button) findViewById(R.id.btn_save_dist);
        reset = (Button) findViewById(R.id.btn_Reset_dist);
        address_1 = (EditText) findViewById(R.id.Et_Addressline_no1_direct);
        address_2 = (EditText) findViewById(R.id.Et_Addressline_no2_direct);
        address_3 = (EditText) findViewById(R.id.Et_Addressline_no3_direct);
        // landmark=(EditText)findViewById(R.id.Et_Addressline_no1_direct);
        pincode = (EditText) findViewById(R.id.Et_Pincode_direct);
        sp_state = (Spinner) findViewById(R.id.spn_select_state);
        sp_city = (Spinner) findViewById(R.id.spn_select_city);
       /* state_id=(EditText)findViewById(R.id.Et_State_direct);
        city_id=(EditText)findViewById(R.id.Et_city_direct);*/

        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();


        new send_state_Data().execute();


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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(address_1.getText().toString().trim().equalsIgnoreCase("")){
                      Globals.CustomToast(BTL_DirectCustomer.this, "Please enter Address1", getLayoutInflater());
                  }else if(address_2.getText().toString().trim().equalsIgnoreCase("")){
                      Globals.CustomToast(BTL_DirectCustomer.this, "Please enter Address2", getLayoutInflater());
                  }else if(address_3.getText().toString().trim().equalsIgnoreCase("")){
                      Globals.CustomToast(BTL_DirectCustomer.this, "Please enter Address3", getLayoutInflater());
                  }else if(pincode.getText().toString().trim().equalsIgnoreCase("")){
                      Globals.CustomToast(BTL_DirectCustomer.this, "Please enter Pincode", getLayoutInflater());
                  }else if(pincode.length() < 6){
                      Globals.CustomToast(BTL_DirectCustomer.this, "Pincode 6 character required", getLayoutInflater());
                  }else if(position_state.equalsIgnoreCase("0")){
                      Globals.CustomToast(BTL_DirectCustomer.this, "Please select state", getLayoutInflater());
                  }else if(position_city.equalsIgnoreCase("0")){
                      Globals.CustomToast(BTL_DirectCustomer.this, "Please select city", getLayoutInflater());
                  }else {
                      try {

                          JSONArray jObjectMain = new JSONArray();

                          for (int i = 0; i < 1; i++) {
                              JSONObject jObjectData = new JSONObject();
                              jObjectData.put("address_1", address_1.getText().toString().trim());
                              jObjectData.put("address_2", address_2.getText().toString().trim());
                              jObjectData.put("address_3", address_3.getText().toString().trim());
                              jObjectData.put("landmark", "land");
                              jObjectData.put("pincode", pincode.getText().toString().trim());
                              jObjectData.put("state_id", position_state);
                              jObjectData.put("city_id", position_city);
                              jObjectMain.put(jObjectData);
                              str_addressmain = jObjectMain.toString();

                          }

                          //System.out.println("str_addressmain" + str_addressmain);
                          //Log.e("str_addressmain", "" + str_addressmain);

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                      apps = new AppPrefs(BTL_DirectCustomer.this);
                      str_profilemain = apps.getUser_PersonalInfo();
                      str_responce = apps.getUser_SocialReInfo();


                      //System.out.println("str_profilemain" + str_profilemain);
                      //Log.e("str_profilemain", "" + str_profilemain);

                      //System.out.println("str_responce" + str_responce);
                      //Log.e("str_responce", "" + str_responce);

                      new send_Register_Data().execute();

                  }
                /*Intent i = new Intent(BTL_DirectCustomer.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BTL_DirectCustomer.this, BTL_DirectCustomer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
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
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
       /* TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(MainPage_drawer.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size()+"");*/
        img_cart.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);


        apps = new AppPrefs(BTL_DirectCustomer.this);
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
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTL_DirectCustomer.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTL_DirectCustomer.this, Business_Registration.class);

                startActivity(i);
            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTL_DirectCustomer.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTL_DirectCustomer.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTL_DirectCustomer.this, Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(BTL_DirectCustomer.this, Business_Registration.class);

        startActivity(i);

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
                        BTL_DirectCustomer.this, "");

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



                //Log.e("",""+parameters);

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
                    Globals.CustomToast(BTL_DirectCustomer.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(BTL_DirectCustomer.this,""+Message, getLayoutInflater());
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


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(BTL_DirectCustomer.this,  android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_state.setAdapter(adapter_state);

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
                        BTL_DirectCustomer.this, "");

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

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(BTL_DirectCustomer.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(BTL_DirectCustomer.this,""+Message, getLayoutInflater());
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


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(BTL_DirectCustomer.this,  android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_city.setAdapter(adapter_city);

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

    public class send_Register_Data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        BTL_DirectCustomer.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("personal_info", "" + str_profilemain));
                parameters.add(new BasicNameValuePair("address_info", "" + str_addressmain));
                parameters.add(new BasicNameValuePair("social_login_response", ""+str_responce));

                //Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                //Log.e("", "" + str_profilemain);
              //  Log.e("", "" + str_addressmain);
               // Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Registration",ServiceHandler.POST,parameters);
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
                    Globals.CustomToast(BTL_DirectCustomer.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(BTL_DirectCustomer.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        loadingView.dismiss();

                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(BTL_DirectCustomer.this, "" + Message, getLayoutInflater());
                        user_id = jObj.getString("user_id");
                        apps = new AppPrefs(BTL_DirectCustomer.this);
                        apps.setUser_LoginInfo("1");

                        setRefershData();
                        if(user_data.size() == 0){

                        }else{
                            for (int i = 0; i < user_data.size(); i++) {
                                db = new DatabaseHandler(BTL_DirectCustomer.this);
                                db.Update_User_second(address_1.getText().toString(), address_2.getText().toString(), address_3.getText().toString(), "land", pincode.getText().toString(), position_state, position_statename, position_city, position_cityname, "", user_data.get(i).getId(), user_id);
                                db.close();
                            }
                        }

                        Intent i = new Intent(BTL_DirectCustomer.this, MainPage_drawer.class);
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
        db = new DatabaseHandler(BTL_DirectCustomer.this);

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
}
