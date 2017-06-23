package com.agraeta.user.btl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;

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

public class Business_Registration extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult> {
    Button registration,registration1,reset;
    RadioGroup CustomerType,Gender;
    RadioButton Dealer,distributer,Direct_customer,Sales_person, other,Professional;
    Custom_ProgressDialog loadingView;
    EditText edt_first,edt_last,edt_mobile,edt_email,edt_password,edt_cpassword;

    String firstname = new String();
    String lastname = new String();
    String gender = new String();
    String mobile = new String();
    String email = new String();
    String password = new String();
    String usertype = new String();
    String loginwith = new String();
    String json = new String();
    String str_personalmain=new String();
    String str_responceIdmain=new String();
    String user_id=new String();
    String fname=new String();
    String lname=new String();
    String emai=new String();
    String smobile=new String();
    RadioButton rd_male,rd_female;

    String str_profilemain = new String();
    String str_responce = new String();

    DatabaseHandler db;

    ArrayList<Bean_Role> role_array=new ArrayList<Bean_Role>();

    AppPrefs apps;
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;


    private boolean mIntentInProgress;

    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;
    private GoogleApiClient mGoogleApiClient;
    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient.clearDefaultAccountAndReconnect();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business__registration);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setActionBar();
        mGoogleApiClient = new GoogleApiClient.Builder(Business_Registration.this)
                .addConnectionCallbacks(Business_Registration.this)
                .addOnConnectionFailedListener(Business_Registration.this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        fetchId();

        new get_role_data().execute();



        //create radio buttons

    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Business_Registration.this);

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

    private void fetchId()
    {

        CustomerType=(RadioGroup)findViewById(R.id.R_grp_cust_type);


        //  CustomerType.getCheckedRadioButtonId();
        Gender=(RadioGroup)findViewById(R.id.gender);
        gender = "2";
        /*distributer=(RadioButton)findViewById(R.id.radio_typ1);
        Dealer=(RadioButton)findViewById(R.id.radio_typ2);
        Sales_person=(RadioButton)findViewById(R.id.radio_typ3);
        Direct_customer=(RadioButton)findViewById(R.id.radio_typ5);
        Professional=(RadioButton)findViewById(R.id.radio_typ4);
        other=(RadioButton)findViewById(R.id.radio_typ6);
*/
        rd_male=(RadioButton)findViewById(R.id.radio_male);
        rd_female=(RadioButton)findViewById(R.id.radio_female);



        edt_first =(EditText)findViewById(R.id.etfirst_name);
        edt_last =(EditText)findViewById(R.id.etlast_name);
        edt_mobile =(EditText)findViewById(R.id.etmobile);

        edt_mobile.setText("+91-");
        Selection.setSelection(edt_mobile.getText(), edt_mobile.getText().length());
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

        edt_email =(EditText)findViewById(R.id.Et_Emailid);
        edt_password =(EditText)findViewById(R.id.Et_pass);
        edt_cpassword=(EditText)findViewById(R.id.Et_conf_pass);

        apps =new AppPrefs(Business_Registration.this);
        fname=apps.getUser_SocialFirst();
        lname=apps.getUser_Sociallast();
        emai=apps.getUser_Socialemail();

        setRefershData();

        if(user_data.size() != 0){

            for (int i = 0; i < user_data.size(); i++) {

                edt_first.setText(user_data.get(i).getF_name());
                edt_last.setText(user_data.get(i).getL_name());
                edt_mobile.setText(user_data.get(i).getPhone_no());
                edt_email.setText(user_data.get(i).getEmail_id());
                edt_password.setText(user_data.get(i).getPassword());
                edt_cpassword.setText(user_data.get(i).getPassword());


                if (user_data.get(i).getGender().toString().equalsIgnoreCase("0")) {
                    rd_female.setChecked(true);
                    gender = "0";
                } else if (user_data.get(i).getGender().toString().equalsIgnoreCase("1")) {
                    rd_male.setChecked(true);
                    gender = "1";
                }
                if (user_data.get(i).getUser_type().toString().equalsIgnoreCase("1")) {
                    Direct_customer.setChecked(true);
                }

            }        }

        //Log.e("email",""+emai);

        if(!fname.equalsIgnoreCase("")){
            edt_first.setText(fname);
        }
        if(!lname.equalsIgnoreCase("")){
            edt_last.setText(lname);
        }
        if(!emai.equalsIgnoreCase("")){
            edt_email.setText(emai);
        }

        edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();

                } else {
                    if (edt_email.getText().toString().trim().equalsIgnoreCase("")) {

                    } else if (validateEmail1(edt_email.getText().toString()) != true) {

                    } else {
                        email = edt_email.getText().toString().trim();

                        new send_Email1_Data().execute();
                    }
                    //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

      /*  CustomerType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio_typ1) {

                } else if (checkedId == R.id.radio_typ2) {

                } else if (checkedId == R.id.radio_typ3) {

                } else if (checkedId == R.id.radio_typ4) {

                } else if (checkedId == R.id.radio_typ5) {

                } else if (checkedId == R.id.radio_typ6) {

                } else {

                }
            }
        });*/

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


        registration1=(Button)findViewById(R.id.btn_Reg1);
        reset=(Button)findViewById(R.id.btn_Reset);


        registration1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_first.getText().toString().trim().equalsIgnoreCase("")){

                   // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    //Globals.CustomToast(Business_Registration.this,"First Name is required",getLayoutInflater());
                    Globals.CustomToast(Business_Registration.this,"Please Enter First Name",getLayoutInflater());
                    edt_first.requestFocus();
                } else if (edt_first.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Business_Registration.this, "Please Enter Atleast 2 character", getLayoutInflater());
                    edt_first.requestFocus();
                }else if(edt_last.getText().toString().trim().equalsIgnoreCase("")){
                    //Globals.CustomToast(Business_Registration.this, "Last Name is required", getLayoutInflater());
                    Globals.CustomToast(Business_Registration.this, "Please Enter Last Name", getLayoutInflater());
                    edt_last.requestFocus();
                   // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                }else if (edt_last.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Business_Registration.this, "Please Enter Atleast 2 character", getLayoutInflater());
                    edt_last.requestFocus();
                }
                else if(edt_email.getText().toString().trim().equalsIgnoreCase("")){
                    Globals.CustomToast(Business_Registration.this, "Please Enter Email Address", getLayoutInflater());
                    edt_email.requestFocus();
                  //  Toast.makeText(Business_Registration.this,"Please enter email address",Toast.LENGTH_LONG).show();
                }else if(validateEmail1(edt_email.getText().toString()) != true){
                   // Toast.makeText(Business_Registration.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Business_Registration.this, "Please Enter A Valid Email Address", getLayoutInflater());
                    edt_email.requestFocus();
                } else if(edt_mobile.getText().toString().trim().equalsIgnoreCase("")){
                    //Globals.CustomToast(Business_Registration.this, "Mobile Number is Compulsory", getLayoutInflater());
                    Globals.CustomToast(Business_Registration.this, "Please Enter Mobile Number", getLayoutInflater());
                    edt_mobile.requestFocus();
                    //Toast.makeText(Business_Registration.this,"Please enter mobile no",Toast.LENGTH_LONG).show();
                }else if (edt_mobile.length() < 14) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Business_Registration.this, "Please Enter Valid 10 Digit Mobile Number", getLayoutInflater());
                    edt_mobile.requestFocus();
                }else if(edt_password.getText().toString().trim().equalsIgnoreCase("")){
                    Globals.CustomToast(Business_Registration.this, "Password is compulsory", getLayoutInflater());
                   // Toast.makeText(Business_Registration.this,"Please enter password",Toast.LENGTH_LONG).show();
                }else if (edt_password.length() < 6) {
                  //  Toast.makeText(Business_Registration.this,"Password minimum 6 character required",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(Business_Registration.this, "Password minimum 6 character required", getLayoutInflater());
                    edt_password.requestFocus();
                } else if(CustomerType.getCheckedRadioButtonId() == 0) {
                    Globals.CustomToast(Business_Registration.this, "Please Select User Type", getLayoutInflater());
                }else{
                        usertype = "2";
                        //Toast.makeText(getApplicationContext(),"Direct_customer",Toast.LENGTH_LONG).show();
                        firstname = edt_first.getText().toString().trim();
                        lastname = edt_last.getText().toString().trim();

                        mobile = edt_mobile.getText().toString().trim().replace("+91-","");
                    Log.e("mobile",""+mobile);
                        email = edt_email.getText().toString().trim();
                        password = edt_password.getText().toString().trim();

                        apps =new AppPrefs(Business_Registration.this);

                        loginwith = apps.getUser_LoginWith();

                       new send_Email_Data().execute();


                                    }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Business_Registration.this, Business_Registration.class);
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
      /*  TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(MainPage_drawer.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size()+"");*/
        img_cart.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apps = new AppPrefs(Business_Registration.this);
                if (apps.getUser_LoginWith().equalsIgnoreCase("0")) {

                    apps.setUser_LoginInfo("");
                    apps.setUser_PersonalInfo("");
                    apps.setUser_SocialIdInfo("");
                    apps.setUser_SocialFirst("");
                    apps.setUser_Sociallast("");
                    apps.setUser_Socialemail("");
                    apps.setUser_SocialReInfo("");


                    db = new DatabaseHandler(Business_Registration.this);
                    db.Delete_user_table();
                    db.close();
                    Intent i = new Intent(Business_Registration.this, LogInPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                } else if (apps.getUser_LoginWith().equalsIgnoreCase("1")) {

                    apps.setUser_LoginInfo("");
                    apps.setUser_PersonalInfo("");
                    apps.setUser_SocialIdInfo("");
                    apps.setUser_SocialFirst("");
                    apps.setUser_Sociallast("");
                    apps.setUser_Socialemail("");
                    apps.setUser_SocialReInfo("");
                    db = new DatabaseHandler(Business_Registration.this);
                    db.Delete_user_table();
                    db.close();

                    LoginManager.getInstance().logOut();
                    Intent i = new Intent(Business_Registration.this, LogInPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                } else if (apps.getUser_LoginWith().equalsIgnoreCase("2")) {

                    apps.setUser_LoginInfo("");
                    apps.setUser_PersonalInfo("");
                    apps.setUser_SocialIdInfo("");
                    apps.setUser_SocialFirst("");
                    apps.setUser_Sociallast("");
                    apps.setUser_Socialemail("");
                    apps.setUser_SocialReInfo("");
                    db = new DatabaseHandler(Business_Registration.this);
                    db.Delete_user_table();
                    db.close();

                    if (mGoogleApiClient.isConnected()) {
                        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                        mGoogleApiClient.disconnect();
                        mGoogleApiClient.connect();
                        // updateUI(false);
                        System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
                    }

                    Intent i = new Intent(Business_Registration.this, MainPage_drawer.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }

            }
        });


        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();

            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Business_Registration.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Business_Registration.this,Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Business_Registration.this,BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void logout() {
        apps = new AppPrefs(Business_Registration.this);
        if (apps.getUser_LoginWith().equalsIgnoreCase("0")) {

            apps.setUser_LoginInfo("");
            apps.setUser_PersonalInfo("");
            apps.setUser_SocialIdInfo("");
            apps.setUser_SocialFirst("");
            apps.setUser_Sociallast("");
            apps.setUser_Socialemail("");
            apps.setUser_SocialReInfo("");


            db = new DatabaseHandler(Business_Registration.this);
            db.Delete_user_table();
            db.close();
            Intent i = new Intent(Business_Registration.this, LogInPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else if (apps.getUser_LoginWith().equalsIgnoreCase("1")) {

            apps.setUser_LoginInfo("");
            apps.setUser_PersonalInfo("");
            apps.setUser_SocialIdInfo("");
            apps.setUser_SocialFirst("");
            apps.setUser_Sociallast("");
            apps.setUser_Socialemail("");
            apps.setUser_SocialReInfo("");
            db = new DatabaseHandler(Business_Registration.this);
            db.Delete_user_table();
            db.close();

            LoginManager.getInstance().logOut();
            Intent i = new Intent(Business_Registration.this, LogInPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else if (apps.getUser_LoginWith().equalsIgnoreCase("2")) {

            apps.setUser_LoginInfo("");
            apps.setUser_PersonalInfo("");
            apps.setUser_SocialIdInfo("");
            apps.setUser_SocialFirst("");
            apps.setUser_Sociallast("");
            apps.setUser_Socialemail("");
            apps.setUser_SocialReInfo("");
            db = new DatabaseHandler(Business_Registration.this);
            db.Delete_user_table();
            db.close();

            if (mGoogleApiClient.isConnected()) {
                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                mGoogleApiClient.disconnect();
                mGoogleApiClient.connect();
                // updateUI(false);
                System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
            }

            Intent i = new Intent(Business_Registration.this, LogInPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

       /* Intent i = new Intent(Business_Registration.this, LogInPage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);*/
        logout();

    };

    public class send_Email_Data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Business_Registration.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("email_id", "" + email));



                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_CheckUniqueEmail",ServiceHandler.POST,parameters);
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
                    Globals.CustomToast(Business_Registration.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Business_Registration.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        loadingView.dismiss();

                       // new send_Register_Data().execute();
                        apps = new AppPrefs(Business_Registration.this);
                        str_responceIdmain= apps.getUser_SocialIdInfo();
                       /* if(str_responceIdmain.equalsIgnoreCase("") && loginwith.equalsIgnoreCase("2")){
                            str_responceIdmain = email;
                        }*/
                        try {



                            JSONArray jObjectMain = new JSONArray();

                            for (int i=0 ;i<1;i++)
                            {
                                JSONObject jObjectData = new JSONObject();

                                jObjectData.put("email_id", email);
                                jObjectData.put("phone_no", mobile);
                                jObjectData.put("first_name",firstname);
                                jObjectData.put("last_name", lastname);
                                jObjectData.put("password", password);
                                jObjectData.put("gender", gender);
                                jObjectData.put("user_type", usertype);
                                jObjectData.put("login_with", loginwith);
                                jObjectData.put("social_login_id", str_responceIdmain);
                                //jObjectData.put("social_login_response","{\"last_name\":\"Shah\",\"id\":\"113567702375491\",\"first_name\":\"Sid\",\"gender\":\"male\",\"name\":\"Sid Shah\"}");


                                jObjectMain.put(jObjectData);
                                str_personalmain = jObjectMain.toString();

                            }

                            //System.out.println("str_personalmain"+str_personalmain);
                            //Log.e("str_personalmain", "" + str_personalmain);

                            apps = new AppPrefs(Business_Registration.this);
                            apps.setUser_PersonalInfo(str_personalmain);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String Message = jObj.getString("message");
                       // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                       // Globals.CustomToast(Business_Registration.this,""+Message,getLayoutInflater());
                        loadingView.dismiss();

                        setRefershData();

                        if(user_data.size() == 0){
                            db = new DatabaseHandler(Business_Registration.this);
                            Log.e("dsdfd",""+usertype);
                            db.Add_Contact(new Bean_User_data("",email,mobile,firstname,lastname,password,gender,usertype,loginwith,str_responceIdmain,"","","","","","","","","",""));
                            db.close();
                        }else{
                            for (int i = 0; i < user_data.size(); i++) {
                                db = new DatabaseHandler(Business_Registration.this);
                                Log.e("dsdfd",""+usertype);
                                db.Update_User_first(email, mobile, firstname, lastname, password, gender, loginwith, str_responceIdmain, user_data.get(i).getId(), usertype);
                                db.close();
                            }
                        }

                        apps = new AppPrefs(Business_Registration.this);
                        str_profilemain = apps.getUser_PersonalInfo();
                        str_responce = apps.getUser_SocialReInfo();


                        //System.out.println("str_profilemain" + str_profilemain);
                        //Log.e("str_profilemain", "" + str_profilemain);

                        //System.out.println("str_responce" + str_responce);
                        //Log.e("str_responce", "" + str_responce);

                        new send_Register_Data().execute();

                      /*  Intent i = new Intent(Business_Registration.this, BTL_DirectCustomer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);*/
                    }
                }}catch(JSONException j){
                    j.printStackTrace();
                }

            }
    }

    public class send_Email1_Data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Business_Registration.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                /*parameters.add(new BasicNameValuePair("first_name", firstname));
                parameters.add(new BasicNameValuePair("last_name", ""+lastname));
                parameters.add(new BasicNameValuePair("gender", ""+gender));
                parameters.add(new BasicNameValuePair("phone_no", ""+mobile));
                parameters.add(new BasicNameValuePair("email_id", ""+email));
                parameters.add(new BasicNameValuePair("password", ""+pass));
                parameters.add(new BasicNameValuePair("user_type", ""+cutType));
                parameters.add(new BasicNameValuePair("login_with", "0"));*/
                parameters.add(new BasicNameValuePair("email_id", "" + email));

                ////Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                //Log.e("", "" + email);
                //Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_CheckUniqueEmail",ServiceHandler.POST,parameters);

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
                    Globals.CustomToast(Business_Registration.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Business_Registration.this,""+Message, getLayoutInflater());

                        loadingView.dismiss();
                    } else {
                        //  loadingView.dismiss();


                        String Message = jObj.getString("message");
                      //  Globals.CustomToast(Business_Registration.this, "" + Message, getLayoutInflater());
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        loadingView.dismiss();
                        /*Intent i = new Intent(Business_Registration.this, BTL_DirectCustomer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);*/
                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }
    public class get_role_data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Business_Registration.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                ////Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);


                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Role/App_GetRoles",ServiceHandler.POST,parameters);

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
                    Globals.CustomToast(Business_Registration.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Business_Registration.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        //  loadingView.dismiss();

                        JSONArray jsonArray = jObj.optJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Bean_Role bean = new Bean_Role();

                            bean.setRole_id(jsonObject.getString("id"));
                            bean.setRole_name(jsonObject.getString("name"));

                            role_array.add(bean);
                        }

                        //Log.e("role_array_size",""+role_array.size());
                        for (int i = 0; i < role_array.size(); i++) {
                            RadioButton radioButton = new RadioButton(Business_Registration.this);
                            radioButton.setText(role_array.get(i).getRole_name());
                            radioButton.setId(Integer.parseInt(role_array.get(i).getRole_id()));
                            CustomerType.addView(radioButton);
                        }

                        loadingView.dismiss();

                    }
                }}catch(JSONException j){
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
                        Business_Registration.this, "");

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
               // parameters.add(new BasicNameValuePair("address_info", "" + str_addressmain));
                parameters.add(new BasicNameValuePair("social_login_response", "" + str_responce));

                ////Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                //Log.e("", "" + str_profilemain);
               // //Log.e("", "" + str_addressmain);
                //Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Registration",ServiceHandler.POST,parameters);
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
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Business_Registration.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Business_Registration.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        loadingView.dismiss();

                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Business_Registration.this, "" + Message, getLayoutInflater());
                        user_id = jObj.getString("user_id");
                        apps = new AppPrefs(Business_Registration.this);
                        apps.setUser_LoginInfo("1");
                        apps.setUserRoleId("2");

                        setRefershData();
                        if(user_data.size() == 0){

                        }else{
                            for (int i = 0; i < user_data.size(); i++) {
                                db = new DatabaseHandler(Business_Registration.this);
                                db.Update_User_second("", "","", "", "", "", "", "", "", "", user_data.get(i).getId(), user_id);
                                db.close();
                            }
                        }

                        Intent i = new Intent(Business_Registration.this, MainPage_drawer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    @Override
    protected void onPause() {
        if(mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting()){
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.clearDefaultAccountAndReconnect();
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting()){
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.clearDefaultAccountAndReconnect();
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
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

    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        mSignInClicked = false;

        // updateUI(true);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    public void onResult(People.LoadPeopleResult arg0) {

    }

}
