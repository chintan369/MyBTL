package com.agraeta.user.btl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Btl_Edit_profile extends AppCompatActivity {

    EditText edt_first,edt_last,edt_mobile,edt_email;
    Custom_ProgressDialog loadingView;
    RadioGroup Gender;
    String firstname = new String();
    String lastname = new String();
    String gender = new String();
    String mobile = new String();
    String email = new String();
    String json=new String();
    String uid=new String();
    String emai=new String();
    RadioButton rd_male,rd_female;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    Button registration1,reset;
    DatabaseHandler db;
    String role_id = new String();
    String owner_id = new String();
    String u_id = new String();
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

        setContentView(R.layout.activity_btl__edit_profile);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRefershData();
        setActionBar();
        fetchId();

    }
    private void fetchId() {

        Gender=(RadioGroup)findViewById(R.id.gender);
        gender = "2";
       
        rd_male=(RadioButton)findViewById(R.id.radio_male);
        rd_female=(RadioButton)findViewById(R.id.radio_female);

        edt_first =(EditText)findViewById(R.id.etfirst_name);
        edt_last =(EditText)findViewById(R.id.etlast_name);
        edt_mobile =(EditText)findViewById(R.id.etmobile);
        edt_email =(EditText)findViewById(R.id.Et_Emailid);
        edt_mobile.setText("+91-");

        registration1=(Button)findViewById(R.id.btn_Reg1);
        reset=(Button)findViewById(R.id.btn_Reset);
        setRefershData();
        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {
                role_id=user_data.get(i).getUser_type().toString();

                if(role_id.toString().equalsIgnoreCase("2")){
                    edt_first.setEnabled(true);
                    edt_last.setEnabled(true);
                    edt_mobile.setEnabled(false);
                    //edt_email.setEnabled(true);
                    registration1.setVisibility(View.VISIBLE);
                    reset.setVisibility(View.VISIBLE);
                }else{
                    edt_first.setEnabled(false);
                    edt_last.setEnabled(false);
                    edt_mobile.setEnabled(false);
                    edt_email.setEnabled(false);
                    registration1.setVisibility(View.GONE);
                    reset.setVisibility(View.GONE);
                }


            }

        }else{
            edt_first.setEnabled(false);
            edt_last.setEnabled(false);
            edt_mobile.setEnabled(false);
            edt_email.setEnabled(false);
            registration1.setVisibility(View.GONE);
            reset.setVisibility(View.GONE);
        }


        Selection.setSelection(edt_mobile.getText(), edt_mobile.getText().length());

        setRefershData();

        if(user_data.size() != 0){

            for (int i = 0; i < user_data.size(); i++) {

                uid=user_data.get(i).getUser_id();

                edt_first.setText(user_data.get(i).getF_name());
                edt_last.setText(user_data.get(i).getL_name());
                edt_mobile.setText("+91-"+user_data.get(i).getPhone_no());
                edt_email.setText(user_data.get(i).getEmail_id());



                if (user_data.get(i).getGender().toString().equalsIgnoreCase("0")) {
                    rd_female.setChecked(true);
                    gender = "0";
                } else if (user_data.get(i).getGender().toString().equalsIgnoreCase("1")) {
                    rd_male.setChecked(true);
                    gender = "1";
                }


            }
        }

        edt_mobile.addTextChangedListener(new TextWatcher() {

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
                    edt_mobile.setText("+91-");
                    Selection.setSelection(edt_mobile.getText(), edt_mobile.getText().length());

                }

            }
        });




        Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_male) {
                    gender = "1";

                } else if (checkedId == R.id.radio_female) {
                    gender = "0";
                } else {
                    gender = "2";
                }
            }
        });


        registration1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_first.getText().toString().trim().equalsIgnoreCase("")) {

                    // Toast.makeText(Btl_Edit_profile.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Btl_Edit_profile.this, "Please Enter First Name", getLayoutInflater());
                    edt_first.requestFocus();
                } else if (edt_first.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Btl_Edit_profile.this, "Please Enter Atleast 2 character", getLayoutInflater());
                    edt_first.requestFocus();
                } else if (edt_last.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(Btl_Edit_profile.this, "Please Enter Last Name", getLayoutInflater());
                    // Toast.makeText(Btl_Edit_profile.this,"Please enter last name",Toast.LENGTH_LONG).show();
                    edt_last.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                }else if (edt_last.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Btl_Edit_profile.this, "Please Enter Atleast 2 character", getLayoutInflater());
                    edt_last.requestFocus();
                } else if (edt_mobile.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(Btl_Edit_profile.this, "Please Enter Mobile Number", getLayoutInflater());
                    edt_mobile.requestFocus();
                    //Toast.makeText(Btl_Edit_profile.this,"Please enter mobile no",Toast.LENGTH_LONG).show();
                } else if (edt_mobile.length() < 14) {
                    // Toast.makeText(Btl_Edit_profile.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Btl_Edit_profile.this, "Please Enter Valid 10 Digit Mobile Number", getLayoutInflater());
                    edt_mobile.requestFocus();
                } else {

                        firstname = edt_first.getText().toString().trim();
                        lastname = edt_last.getText().toString().trim();

                        mobile =   edt_mobile.getText().toString().trim().replace("+91-","");
                        Log.e("mobile",""+mobile);
                        email = edt_email.getText().toString().trim();

                        new send_Update_Data().execute();


                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Btl_Edit_profile.this, Btl_Edit_profile.class);
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
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        /*TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(User_Profile.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size()+"");*/
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        img_notification.setVisibility(View.GONE);

        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        img_cart.setVisibility(View.VISIBLE);

        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        apps = new AppPrefs(Btl_Edit_profile.this);
        String qun =apps.getCart_QTy();

      /*  setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    apps = new AppPrefs(Btl_Edit_profile.this);
                    role_id = apps.getSubSalesId().toString();
                    u_id = apps.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    apps = new AppPrefs(Btl_Edit_profile.this);
                    role_id = apps.getSubSalesId().toString();
                    u_id = apps.getSalesPersonId().toString();
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
                    Globals.CustomToast(Btl_Edit_profile.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        apps = new AppPrefs(Btl_Edit_profile.this);
                        apps.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        apps = new AppPrefs(Btl_Edit_profile.this);
                        apps.setCart_QTy(""+qu);


                    }

                }
            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

        }else{
            apps = new AppPrefs(Btl_Edit_profile.this);
            apps.setCart_QTy("");
        }*/

        apps = new AppPrefs(Btl_Edit_profile.this);
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
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Btl_Edit_profile.this, User_Profile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apps = new AppPrefs(Btl_Edit_profile.this);
                apps.setUser_notification("Btl_Edit_profile");
                Intent i = new Intent(Btl_Edit_profile.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Btl_Edit_profile.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(Btl_Edit_profile.this, User_Profile.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Btl_Edit_profile.this);

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

    public class send_Update_Data extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Btl_Edit_profile.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("id", uid));
                parameters.add(new BasicNameValuePair("first_name", firstname));
                parameters.add(new BasicNameValuePair("last_name", lastname));
                parameters.add(new BasicNameValuePair("gender", gender));
                parameters.add(new BasicNameValuePair("phone_no", mobile));


                //Log.e("1", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                //Log.e("2", "" + email);

                // Log.e("4",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/App_EditProfile", ServiceHandler.POST, parameters);
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
                   /* Toast.makeText(Btl_Edit_profile.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Btl_Edit_profile.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Btl_Edit_profile.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Btl_Edit_profile.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Btl_Edit_profile.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Btl_Edit_profile.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();

                        setRefershData();


                        for (int i = 0; i < user_data.size(); i++) {
                            db = new DatabaseHandler(Btl_Edit_profile.this);
                            db.Update_User_edit(mobile, firstname, lastname, gender, user_data.get(i).getId());
                            db.close();

                        }

                        Intent i = new Intent(Btl_Edit_profile.this, User_Profile.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }
}
