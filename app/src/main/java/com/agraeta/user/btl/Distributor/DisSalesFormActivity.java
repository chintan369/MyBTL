package com.agraeta.user.btl.Distributor;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;
import com.agraeta.user.btl.WebServiceCaller;

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

public class DisSalesFormActivity extends AppCompatActivity {

    EditText et_first_name_dis_sal,et_last_name_dis_sal,et_mobile_dis_sal,et_emailid_dis_sal,et_alt_mobile_dis_sal,et_panno_dis_sal,et_aadhaarcardno_dis_sal,et_password_dis_sal;
    Button btn_submit_sal,btn_reset_sal;
    String json = new String();
    String[] status={"Select Status","Active","Inactive"};
    Bean_Dis_Sales disSalesList;
    Custom_ProgressDialog loadingView;
    String profile_array="";

    boolean hasEmailChecked=false;
    boolean hasMobileChecked=false;
    boolean hasAltMobileChecked=false;

    boolean mobileavailable=false;
    boolean altMobileavailable=false;

    boolean emailAvailable=false;

    // ArrayAdapter<String> status_adp;
    ArrayAdapter<String> adapter;
    Spinner spn_status;
    List<String> listOfStatus=new ArrayList<String>();

    String smobile = new String();
    String amobile=new String();
    String pannumber = new String();
    String aadharnumber=new String();

    String s_fname = new String();
    String s_lname = new String();
    String s_mobile = new String();
    String s_amobile = new String();
    String s_email = new String();
    String s_pancardno = new String();
    String s_aadhaarcardno = new String();
    String s_status = new String();

    //String dataSales = new String();

    AppPrefs prefs;
    String userID;


    DatabaseHandler db;

    View rootView;

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
        setContentView(R.layout.activity_dis_sales_form);
//        Intent intent=getIntent();
//        disSalesList=(Bean_Dis_Sales) intent.getSerializableExtra("salesPerson");
        prefs=new AppPrefs(getApplicationContext());
        userID=prefs.getUserId();
        getid();
        setActionBar();
    }

    private void getid() {
        et_first_name_dis_sal=(EditText)findViewById(R.id.et_first_name_dis_sal);
        et_last_name_dis_sal=(EditText)findViewById(R.id.et_last_name_dis_sal);
        et_mobile_dis_sal=(EditText)findViewById(R.id.et_mobile_dis_sal);
        et_alt_mobile_dis_sal=(EditText)findViewById(R.id.et_alt_mobile_dis_sal);
        et_emailid_dis_sal=(EditText)findViewById(R.id.et_emailid_dis_sal);
        et_panno_dis_sal=(EditText)findViewById(R.id.et_panno_dis_sal);
        et_aadhaarcardno_dis_sal=(EditText)findViewById(R.id.et_aadhaarcardno_dis_sal);
        et_password_dis_sal=(EditText)findViewById(R.id.et_password_dis_sal);
        btn_submit_sal=(Button)findViewById(R.id.btn_submit_sal);
        btn_reset_sal=(Button)findViewById(R.id.btn_reset_sal);
        spn_status=(Spinner)findViewById(R.id.spn_select);

        btn_reset_sal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_first_name_dis_sal.setText("");
                et_last_name_dis_sal.setText("");
                et_mobile_dis_sal.setText("");
                et_alt_mobile_dis_sal.setText("");
                et_emailid_dis_sal.setText("");
                et_panno_dis_sal.setText("");
                et_aadhaarcardno_dis_sal.setText("");
                et_password_dis_sal.setText("");
                spn_status.setSelection(0);
            }
        });

        spn_status.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_password_dis_sal.getWindowToken(), 0);
                return false;
            }
        }) ;

        et_emailid_dis_sal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!hasEmailChecked){
                    hasEmailChecked=false;
                }
            }
        });

        et_mobile_dis_sal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().contains("+91-")) {
                    et_mobile_dis_sal.setText("+91-");
                    Selection.setSelection(et_mobile_dis_sal.getText(),et_mobile_dis_sal.getText().length());

                }

                if(hasMobileChecked)
                    hasMobileChecked=true;
            }
        });

        adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_status,status);

        spn_status.setAdapter(adapter);



        et_mobile_dis_sal.setText("+91-");
        Selection.setSelection(et_mobile_dis_sal.getText(), et_mobile_dis_sal.getText().length());

        et_alt_mobile_dis_sal.setText("+91-");
        Selection.setSelection(et_alt_mobile_dis_sal.getText(), et_alt_mobile_dis_sal.getText().length());

        et_alt_mobile_dis_sal.addTextChangedListener(new TextWatcher() {

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
                    et_alt_mobile_dis_sal.setText("+91-");
                    Selection.setSelection(et_alt_mobile_dis_sal.getText(),et_alt_mobile_dis_sal.getText().length());

                }

                if(hasAltMobileChecked)
                    hasAltMobileChecked=false;

            }
        });

        et_emailid_dis_sal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();

                }
                else
                {
                    if (et_emailid_dis_sal.getText().toString().trim().equalsIgnoreCase("")) {

                    } else if(!validateEmail1(et_emailid_dis_sal.getText().toString())) {

                    }
                    else {
                        s_email = et_emailid_dis_sal.getText().toString().trim();

                       // new send_Email1_Data().execute();
                    }
                    //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

//        et_emailid_dis_sal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus){
//
//                    if(!validateEmail1(et_emailid_dis_sal.getText().toString())){
//                        Globals.CustomToast(getApplicationContext(),"Please enter Valid Email Address",getLayoutInflater());
//                    }
//                    else {
//                        String json=WebServiceCaller.CheckUniqueEmail(et_emailid_dis_sal.getText().toString());
//                        if(json!=null && !json.equalsIgnoreCase("")){
//
//                            //Log.e("JSON","Email JSON"+json);
//                            try {
//                                JSONObject jsonObject=new JSONObject(json);
//
//                                if(jsonObject.getBoolean("status")){
//                                    Globals.CustomToast(getApplicationContext(),jsonObject.getString("message"),getLayoutInflater());
//                                    emailAvailable=true;
//                                }
//                                else {
//                                    Globals.CustomToast(getApplicationContext(),jsonObject.getString("message"),getLayoutInflater());
//                                    emailAvailable=false;
//                                }
//                                hasEmailChecked=true;
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//        });

        et_mobile_dis_sal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                smobile = et_mobile_dis_sal.getText().toString().trim().replace("+91-", "");
                if (hasFocus) {
                    //Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();

                } else {
                    if (smobile.equalsIgnoreCase("")) {

                    }
                    else {
                        s_mobile = et_mobile_dis_sal.getText().toString().trim();

                        //new send_Uni_Mobile_Data().execute();
                    }
                    //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });




        btn_submit_sal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("11111111111",""+prefs.getUserId());
                smobile = et_mobile_dis_sal.getText().toString().trim().replace("+91-", "");
                amobile = et_alt_mobile_dis_sal.getText().toString().trim().replace("+91-", "");
                pannumber = et_panno_dis_sal.getText().toString().trim();
                aadharnumber = et_aadhaarcardno_dis_sal.getText().toString().trim();

                //Log.e("2222222222",""+prefs.getUserId());
                String fname = et_first_name_dis_sal.getText().toString().trim();
                String lname = et_last_name_dis_sal.getText().toString().trim();
                String phone = et_mobile_dis_sal.getText().toString().trim();
                String alt_phone = et_alt_mobile_dis_sal.getText().toString().trim();
                String email = et_emailid_dis_sal.getText().toString();
                String pancardno = et_panno_dis_sal.getText().toString();
                String aadhaarcardno = et_aadhaarcardno_dis_sal.getText().toString().trim();
                String password=et_password_dis_sal.getText().toString().trim();
                // String status = String.valueOf(spn_status.getSelectedItemPosition());
                String status;
                if(spn_status.getSelectedItemPosition()==1)
                    status="1";
                else
                    status="0";

                if(!hasMobileChecked && phone.replace("+91-","").length()==10){
                    /*List<NameValuePair> pairs=new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("phone_no",phone.replace("+91-","")));*/
                    String json = WebServiceCaller.CheckUniqueMobile(phone.replace("+91-",""),"0");

                    //Log.e("json",json);

                    try{
                        hasMobileChecked=true;
                        JSONObject object=new JSONObject(json);
                        if(object.getBoolean("status")){
                            mobileavailable=true;
                        }
                        else {
                            mobileavailable=false;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Log.e("Exception",e.getMessage());
                    }

                }

                if(!alt_phone.replace("+91-","").isEmpty() && !hasAltMobileChecked && alt_phone.replace("+91-","").length()==10){
                    //Log.e("condition",""+!alt_phone.replace("+91-","").isEmpty()+""+!hasAltMobileChecked+""+ alt_phone.replace("+91-","").length());
                    List<NameValuePair> pairs=new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("phone_no",alt_phone.replace("+91-","")));
                    String json = WebServiceCaller.CheckUniqueMobile(alt_phone.replace("+91-",""),"0");

                    //Log.e("json",json);

                    try{
                        hasAltMobileChecked=true;
                        JSONObject object=new JSONObject(json);
                        if(object.getBoolean("status")){
                            altMobileavailable=true;
                        }
                        else {
                            altMobileavailable=false;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Log.e("Exception",e.getMessage());
                    }

                }

                 if(!hasEmailChecked && validateEmail1(email)){
                    /*List<NameValuePair> pairs=new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("email_id",email));*/
                    String json = WebServiceCaller.CheckUniqueEmail(email);

                    //Log.e("json",json);

                    try{
                        hasEmailChecked=true;
                        JSONObject object=new JSONObject(json);
                        if(object.getBoolean("status")){
                            emailAvailable=true;
                        }
                        else {
                            emailAvailable=false;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Log.e("Exception",e.getMessage());
                    }

                }


                //Log.e("333333333333",""+prefs.getUserId());

//                if (status.equalsIgnoreCase("0"))
//                    status = "1";
//                else status = "0";

//                if (status.equalsIgnoreCase("1"))
//                    status="1";
//                if (status.equalsIgnoreCase("2"))
//                    status="0";

                //Log.e("444444444444",""+prefs.getUserId());

                if (et_first_name_dis_sal.getText().toString().trim().equalsIgnoreCase("")) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getApplicationContext(), "Please Enter First Name", getLayoutInflater());
                    et_first_name_dis_sal.requestFocus();
                } else if (et_first_name_dis_sal.length() < 2) {
                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getApplicationContext(), "Please Enter Atleast 2 character", getLayoutInflater());
                    et_first_name_dis_sal.requestFocus();
                } else if (et_last_name_dis_sal.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getApplicationContext(), "Please Enter Last Name", getLayoutInflater());
                    et_last_name_dis_sal.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (et_last_name_dis_sal.length() < 2) {
                    Globals.CustomToast(getApplicationContext(), "Please Enter Atleast 2 Character", getLayoutInflater());
                    et_last_name_dis_sal.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (et_mobile_dis_sal.getText().toString().trim().equalsIgnoreCase("+91-")) {
                    //Globals.CustomToast(Business_Registration.this, "Mobile Number is Compulsory", getLayoutInflater());
                    Globals.CustomToast(DisSalesFormActivity.this, "Please Enter Mobile Number", getLayoutInflater());
                    et_mobile_dis_sal.requestFocus();
                    //Toast.makeText(Business_Registration.this,"Please enter mobile no",Toast.LENGTH_LONG).show();
                } else if (et_mobile_dis_sal.length() < 14 && !smobile.equalsIgnoreCase("")) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(DisSalesFormActivity.this, "Please Enter Valid 10 Digit Mobile Number", getLayoutInflater());
                    et_mobile_dis_sal.requestFocus();
                }
                else if(!mobileavailable){
                   // hasMobileChecked=false;
                    Globals.CustomToast(DisSalesFormActivity.this, "Please Enter Unique Mobile Number", getLayoutInflater());
                    et_mobile_dis_sal.requestFocus();
                }
                else if (et_alt_mobile_dis_sal.length() < 14 && !amobile.equalsIgnoreCase("")) {
                    Globals.CustomToast(DisSalesFormActivity.this, "Please Enter Valid 10 Digit Alternate Mobile Number", getLayoutInflater());
                    et_alt_mobile_dis_sal.requestFocus();
                }
                else if(!et_alt_mobile_dis_sal.getText().toString().replace("+91-","").isEmpty() && !altMobileavailable){
                    hasAltMobileChecked=false;
                    Globals.CustomToast(DisSalesFormActivity.this, "Please Enter Unique Alternate Mobile Number", getLayoutInflater());
                    et_alt_mobile_dis_sal.requestFocus();
                }
                /*else if (et_emailid_dis_sal.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getApplicationContext(), "Please enter email address", getLayoutInflater());
                    et_emailid_dis_sal.requestFocus();
                }*/
                else if(email.equalsIgnoreCase("")){
                    //C.Toast(getApplicationContext(),"Please enter Email Address",getLayoutInflater());
                    Globals.CustomToast(getApplicationContext(), "Please enter Email Address", getLayoutInflater());
                    et_emailid_dis_sal.requestFocus();
                }
                else if(!validateEmail1(email)){
                    //C.Toast(getApplicationContext(),"Please enter Valid Email Address",getLayoutInflater());
                    Globals.CustomToast(getApplicationContext(), "Please enter Valid Email Address", getLayoutInflater());
                    et_emailid_dis_sal.requestFocus();
                }
//                else if(!hasEmailChecked){
//                    et_emailid_dis_sal.requestFocus();
//                }
                else if(!emailAvailable){
                    hasEmailChecked=false;
                    Globals.CustomToast(getApplicationContext(), "Please enter Unique Email Address", getLayoutInflater());
                    et_emailid_dis_sal.requestFocus();
                }
                else if (et_panno_dis_sal.length() < 10 && !pannumber.equalsIgnoreCase("") && !validatePANNUM(pancardno)) {
                    Globals.CustomToast(DisSalesFormActivity.this, "Please Enter Valid PAN Number", getLayoutInflater());
                    et_panno_dis_sal.requestFocus();
                }
                else if (et_aadhaarcardno_dis_sal.length() < 12 && !aadharnumber.equalsIgnoreCase("") && !validateAADHARCARDNUM(aadhaarcardno)) {
                    Globals.CustomToast(DisSalesFormActivity.this, "Please Enter Valid Aadhar Card Number", getLayoutInflater());
                    et_aadhaarcardno_dis_sal.requestFocus();
                }
                else if(password.equalsIgnoreCase("")){
                    //C.Toast(getApplicationContext(),"Please enter Password",getLayoutInflater());
                    Globals.CustomToast(getApplicationContext(), "Please enter Password", getLayoutInflater());
                    et_password_dis_sal.requestFocus();
                }
                else if(password.length()<6){
                    //C.Toast(getApplicationContext(),"Please enter at least 6 char Password",getLayoutInflater());
                    Globals.CustomToast(getApplicationContext(), "Please enter at least 6 char Password", getLayoutInflater());
                    et_password_dis_sal.requestFocus();
                }
                else if(spn_status.getSelectedItemPosition()==0){
                    Globals.CustomToast(getApplicationContext(), "Please Select Status", getLayoutInflater());
                }



//                else if (validatePANNUM(et_panno_dis_sal.getText().toString()) != true) {
//                    Globals.CustomToast(DisSalesFormActivity.this, "Please Enter Valid PAN Number", getLayoutInflater());
//                    et_panno_dis_sal.requestFocus();
//                }


                else {
                    //Log.e("5555555555",""+prefs.getUserId());
//                    s_fname = et_first_name_dis_sal.getText().toString().trim();
//                    s_lname = et_last_name_dis_sal.getText().toString().trim();
//                    s_mobile =  et_mobile_dis_sal.getText().toString().trim();
//                    s_amobile =  et_alt_mobile_dis_sal.getText().toString().trim();
//                    s_email =  et_emailid_dis_sal.getText().toString().trim();
//                    s_pancardno =  et_panno_dis_sal.getText().toString().trim();
//                    s_aadhaarcardno =  et_aadhaarcardno_dis_sal.getText().toString().trim();

                    try {

                        JSONArray jObjectMain1 = new JSONArray();

                        //Log.e("kkk",""+prefs.getUserId());
                        for (int ij = 1; ij == 1; ij++) {
                            JSONObject jObjectData1 = new JSONObject();
                            //Log.e("bbbbbbbb",""+prefs.getUserId());
                            //jObjectData1.put("user_id",String.valueOf(disSalesList.getUser_id()));
                            jObjectData1.put("user_id", prefs.getUserId());
                            //Log.e("aaaaaaaaaaa",""+prefs.getUserId());
                            jObjectData1.put("first_name", fname);
                            jObjectData1.put("last_name", lname);
                            jObjectData1.put("phone_no", phone);
                            jObjectData1.put("email_id", email);
                            jObjectData1.put("status",status);
                            jObjectData1.put("pancard_sales", pancardno);
                            jObjectData1.put("alternate_no", alt_phone);  //change the data
                            jObjectData1.put("aadhar_card_sales", aadhaarcardno);
                            jObjectData1.put("password", password);


                            jObjectMain1.put(jObjectData1);
                            // dataSales = jObjectMain1.toString();
                            profile_array = jObjectMain1.toString();

                            new Add_DisSalesData().execute();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                // new send_custcomplain_data().execute();

            }
        });


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

    private boolean validatePANNUM(String pan)
    {
        Pattern pattern;
        Matcher matcher;
        String PAN_PATTERN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        pattern = Pattern.compile(PAN_PATTERN);
        matcher = pattern.matcher(pan);
        return matcher.matches();
    }

    private boolean validateAADHARCARDNUM(String aadhar)
    {
        Pattern pattern;
        Matcher matcher;
        String AADHAR_PATTERN = "[0-9]{12}";
        pattern = Pattern.compile(AADHAR_PATTERN);
        matcher = pattern.matcher(aadhar);
        return matcher.matches();
    }

    public class Add_DisSalesData extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
//        private Bean_Dis_Sales sales;
//
//        public Update_DisSalesData(Bean_Dis_Sales sales){
//            this.sales=sales;
//
//        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(DisSalesFormActivity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                JSONArray jArray=new JSONArray(profile_array);
                JSONObject profile=jArray.getJSONObject(0);


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //parameters.add(new BasicNameValuePair("sales_person_id",profile_array));
                parameters.add(new BasicNameValuePair("user_id", profile.getString("user_id")));
                parameters.add(new BasicNameValuePair("first_name", profile.getString("first_name")));
                parameters.add(new BasicNameValuePair("last_name", profile.getString("last_name")));
                parameters.add(new BasicNameValuePair("email_id", profile.getString("email_id")));
                parameters.add(new BasicNameValuePair("phone_no", profile.getString("phone_no")));
                parameters.add(new BasicNameValuePair("status", profile.getString("status")));
                parameters.add(new BasicNameValuePair("pancard_sales", profile.getString("pancard_sales")));
                parameters.add(new BasicNameValuePair("alternate_no", profile.getString("alternate_no")));
                parameters.add(new BasicNameValuePair("aadhar_card_sales", profile.getString("aadhar_card_sales")));
                parameters.add(new BasicNameValuePair("password", profile.getString("password")));

                ////Log.e("","" + profile_array);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Add_Distributor_Sales_Person",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("arrayedited: " + json);
                return json;

            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error: " + e.toString());

            }

            return json;

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Globals.CustomToast(getApplicationContext(),"SERVER ERRER", getLayoutInflater());
                    //Global.CustomToast(Activity_Login.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                         Globals.CustomToast(DisSalesFormActivity.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        loadingView.dismiss();
                        //  String VERIFY = jObj.getString("true");
                        String Message = jObj.getString("message");
                        Globals.CustomToast(DisSalesFormActivity.this,""+Message, getLayoutInflater());
                        Intent i = new Intent(DisSalesFormActivity.this, DisSalesListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

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
                        DisSalesFormActivity.this, "");

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
                parameters.add(new BasicNameValuePair("email_id", "" + s_email));

                ////Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                ////Log.e("", "" + s_email);
                ////Log.e("",""+parameters);

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

            hasEmailChecked=true;

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1==null
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(DisSalesFormActivity.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(DisSalesFormActivity.this,""+Message, getLayoutInflater());
                        et_emailid_dis_sal.setText("");
                        et_emailid_dis_sal.requestFocus();
                        loadingView.dismiss();
                        emailAvailable=false;
                    } else {
                        //  loadingView.dismiss();

                        emailAvailable=true;
                        String Message = jObj.getString("message");
                          //Globals.CustomToast(getApplicationContext(), "" + Message, getLayoutInflater());
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
        img_cart.setVisibility(View.GONE);
        img_home.setVisibility(View.VISIBLE);
        img_category.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        ImageView img_add=(ImageView)mCustomView.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DisSalesFormActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
//        db = new DatabaseHandler(Product_List.this);
//        bean_cart =  db.Get_Product_Cart();
//        db.close();
//        if(bean_cart.size() == 0){
//            txt.setVisibility(View.GONE);
//            txt.setText("");
//        }else{
//            txt.setVisibility(View.VISIBLE);
//            txt.setText(bean_cart.size()+"");
//        }

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db= new DatabaseHandler(Product_List.this);
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
                Intent i = new Intent(DisSalesFormActivity.this,DisSalesListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                finish();
            }
        });
//        img_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                Intent i = new Intent(Product_List.this, Main_CategoryPage.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app = new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
                Intent i = new Intent(DisSalesFormActivity.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
//        img_notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app = new AppPrefs(Product_List.this);
//                app.setUser_notification("product");
//                Intent i = new Intent(Product_List.this, Notification.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
//        img_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                app = new AppPrefs(Product_List.this);
//                app.setUser_notification("product");
//                Intent i = new Intent(Product_List.this, BTL_Cart.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public static String CheckUniqueMail(final String email, final String API){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("email_id", "" + email));

                    ////Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                    ////Log.e("", "" + email);
                   // //Log.e("",""+parameters);

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link+API,ServiceHandler.POST,parameters);
                    //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
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
        return json[0];
    }

    public class send_Uni_Mobile_Data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        DisSalesFormActivity.this, "");

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
                // parameters.add(new BasicNameValuePair("user_id", "" + userID));
                parameters.add(new BasicNameValuePair("phone_no", "" + s_mobile));
//                user_id
//                        phone_no

                ////Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                ////Log.e("", "" + s_mobile);
                ////Log.e("", "" + userID);

               // //Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_CheckUniqueMobile",ServiceHandler.POST,parameters);

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
                    Globals.CustomToast(DisSalesFormActivity.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(DisSalesFormActivity.this,""+Message, getLayoutInflater());
                        et_mobile_dis_sal.setText("");
                        mobileavailable=false;
                        et_mobile_dis_sal.requestFocus();
                        loadingView.dismiss();
                    } else {
                        //  loadingView.dismiss();

                        mobileavailable=true;
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


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),DisSalesListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
