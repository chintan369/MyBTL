package com.agraeta.user.btl.Distributor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.Toast;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.HttpFileUpload;
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


public class EditDisSalesActivity extends AppCompatActivity {

    EditText et_first_name_dis_sal,et_last_name_dis_sal,et_mobile_dis_sal1,et_emailid_dis_sal,et_alt_mobile_dis_sal1,et_panno_dis_sal1,et_aadhaarcardno_dis_sal1;
    Button btn_submit_sal1,btn_cancel_sal;
    Bean_Dis_Sales disSalesList;
    //Spinner spn_status;
    String s_mobile = new String();
    String[] statuslist={"Select Status","Active","Inactive"};
    ArrayAdapter<String> status_adp;
    TextView txt_change_password;

    ArrayAdapter<String> adapter;
    Spinner spn_status;
    List<String> listOfStatus=new ArrayList<String>();

    boolean hasEmailChecked=false;
    boolean hasMobileChecked=false;
    boolean hasAltMobileChecked=false;

    boolean mobileavailable=false;
    boolean altMobileavailable=false;

    boolean emailAvailable=false;


    AppPrefs app;
    Toolbar toolbar;
    ImageView img_drawer,img_home,img_notification,img_cart;
    FrameLayout unread;
    TextView message_tv;
    String smobile = new String();
    String amobile = new String();
    String pannumber = new String();
    String aadharnumber=new String();
    String profile_array="";
    Custom_ProgressDialog loadingView;
    String json = new String();
    String FilePathsend = new String();
    String FileNamesend = new String();
    String dataSalesEdit=new String();
    String wa = new String();
    HttpFileUpload httpupload;

    String firstname = new String();
    String lastname = new String();
    String email_id = new String();
    String phone_no = new String();
    String pancard_sales = new String();
    String alternate_no = new String();
    String aadhar_card_sales = new String();
    int userID;
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
        setContentView(R.layout.activity_edit_dis_sales);
        setActionBar();
        Intent intent=getIntent();
        disSalesList=(Bean_Dis_Sales) intent.getSerializableExtra("salesPerson");
        //Log.e("dddddddddddddddd",""+disSalesList);
        fetchID();
        userID=disSalesList.getSales_id();
        //Log.e("sales id edit",""+userID);

        //Log.e("STATUSSSSS",""+ disSalesList.getStatus());


    }

    private void fetchID() {
        txt_change_password=(TextView)findViewById(R.id.txt_change_password);
        et_first_name_dis_sal=(EditText)findViewById(R.id.et_first_name_dis_sal);
        et_last_name_dis_sal=(EditText)findViewById(R.id.et_last_name_dis_sal);
        et_mobile_dis_sal1=(EditText)findViewById(R.id.et_mobile_dis_sal);
        et_alt_mobile_dis_sal1=(EditText)findViewById(R.id.et_alt_mobile_dis_sal);
        et_emailid_dis_sal=(EditText)findViewById(R.id.et_emailid_dis_sal);
        et_panno_dis_sal1=(EditText)findViewById(R.id.et_panno_dis_sal);
        et_aadhaarcardno_dis_sal1=(EditText)findViewById(R.id.et_aadhaarcardno_dis_sal);
        btn_submit_sal1=(Button)findViewById(R.id.btn_submit_sal);
        btn_cancel_sal=(Button)findViewById(R.id.btn_cancel_sal);
        spn_status=(Spinner)findViewById(R.id.spn_select);
        et_emailid_dis_sal.setEnabled(false);

        et_first_name_dis_sal.setText(disSalesList.getFirstName());
        et_last_name_dis_sal.setText(disSalesList.getLastName());
        et_mobile_dis_sal1.setText("+91-" + disSalesList.getPhone_no());



        spn_status.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_aadhaarcardno_dis_sal1.getWindowToken(), 0);
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


        et_mobile_dis_sal1.addTextChangedListener(new TextWatcher() {

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
                    et_mobile_dis_sal1.setText("+91-");
                    Selection.setSelection(et_mobile_dis_sal1.getText(),et_mobile_dis_sal1.getText().length());

                }
                if(hasMobileChecked)
                    hasMobileChecked=true;
            }
        });

        et_alt_mobile_dis_sal1.setText("+91-");
        Selection.setSelection(et_alt_mobile_dis_sal1.getText(), et_alt_mobile_dis_sal1.getText().length());

        et_alt_mobile_dis_sal1.addTextChangedListener(new TextWatcher() {

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
                    et_alt_mobile_dis_sal1.setText("+91-");
                    Selection.setSelection(et_alt_mobile_dis_sal1.getText(),et_alt_mobile_dis_sal1.getText().length());

                }
                if(hasAltMobileChecked)
                    hasAltMobileChecked=false;

            }
        });

        adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_status,statuslist);
        spn_status.setAdapter(adapter);

        if(disSalesList.getStatus().equalsIgnoreCase("1")) {
            spn_status.setSelection(1);
        }
        if (disSalesList.getStatus().equalsIgnoreCase("0"))
        {
            spn_status.setSelection(2);
        }



        et_mobile_dis_sal1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                smobile = et_mobile_dis_sal1.getText().toString().trim().replace("+91-", "");
                if (hasFocus) {
                    //Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();

                } else {
                    if (smobile.equalsIgnoreCase("")) {

                    }
                    else {
                        s_mobile = et_mobile_dis_sal1.toString().trim();

                        //new send_Uni_Mobile_Data_A().execute();
                    }
                    //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

        et_alt_mobile_dis_sal1.setText(disSalesList.getAltPhone_no());

        et_emailid_dis_sal.setText(disSalesList.getEmailid());

        et_panno_dis_sal1.setText(disSalesList.getPancardno());

        et_aadhaarcardno_dis_sal1.setText(disSalesList.getAadhaarcardno());

        txt_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(EditDisSalesActivity.this);
                //Log.e("1111",""+String.valueOf(disSalesList.getSales_id()));
                app.setSalesId(String.valueOf(disSalesList.getSales_id()));
                //Log.e("1111",""+app.getSalesId());
                Intent i = new Intent(EditDisSalesActivity.this, DisResetPasswordActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btn_submit_sal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                smobile= et_mobile_dis_sal1.getText().toString().trim().replace("+91-","");
                amobile=et_alt_mobile_dis_sal1.getText().toString().trim().replace("+91-","");
                pannumber = et_panno_dis_sal1.getText().toString().trim();
                aadharnumber = et_aadhaarcardno_dis_sal1.getText().toString().trim();

                String fname=et_first_name_dis_sal.getText().toString().trim();
                String lname=et_last_name_dis_sal.getText().toString().trim();
                String phone=et_mobile_dis_sal1.getText().toString().trim();
                String alt_phone=et_alt_mobile_dis_sal1.getText().toString().trim();
                String email=et_emailid_dis_sal.getText().toString();
                String pancardno=et_panno_dis_sal1.getText().toString();
                String aadhaarcardno=et_aadhaarcardno_dis_sal1.getText().toString().trim();
                String status=String.valueOf(spn_status.getSelectedItemPosition());


//                if(status.equalsIgnoreCase("1"))
//                    status="1";
//              if(status.equalsIgnoreCase("2"))
//                  status="0";
                if(status.equalsIgnoreCase("1"))
                {
                    status="1";

                }
                else if(status.equalsIgnoreCase("2"))
                {
                    status="0";

                }



                if(!hasMobileChecked && phone.replace("+91-","").length()==10){
                    /*List<NameValuePair> pairs=new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("phone_no",phone.replace("+91-","")));*/
                    String json = WebServiceCaller.CheckUniqueMobile(phone.replace("+91-",""),String.valueOf(disSalesList.getSales_id()));

                    //Log.e("json",json);

                    try{
                        hasMobileChecked=true;
                        JSONObject object=new JSONObject(json);
                        mobileavailable = object.getBoolean("status");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Log.e("Exception",e.getMessage());
                    }

                }

                if(!alt_phone.replace("+91-","").isEmpty() && !hasAltMobileChecked && alt_phone.replace("+91-","").length()==10){
                    //Log.e("condition",""+!alt_phone.replace("+91-","").isEmpty()+""+!hasAltMobileChecked+""+ alt_phone.replace("+91-","").length());
                    List<NameValuePair> pairs=new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("phone_no",alt_phone.replace("+91-","")));
                    String json = WebServiceCaller.CheckUniqueMobile(alt_phone.replace("+91-",""),String.valueOf(disSalesList.getSales_id()));

                    //Log.e("json",json);

                    try{
                        hasAltMobileChecked=true;
                        JSONObject object=new JSONObject(json);
                        altMobileavailable = object.getBoolean("status");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Log.e("Exception",e.getMessage());
                    }

                }

                if (et_first_name_dis_sal.getText().toString().trim().equalsIgnoreCase("")) {
                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getApplicationContext(), "Please Enter First Name", getLayoutInflater());
                    et_first_name_dis_sal.requestFocus();
                }
                else if (et_first_name_dis_sal.length() < 2) {
                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getApplicationContext(), "Please Enter Atleast 2 character", getLayoutInflater());
                    et_first_name_dis_sal.requestFocus();
                }
                else if (et_last_name_dis_sal.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getApplicationContext(), "Please Enter Last Name",getLayoutInflater());
                    et_last_name_dis_sal.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                }
                else if (et_last_name_dis_sal.length() < 2) {
                    Globals.CustomToast(getApplicationContext(), "Please Enter Atleast 2 Character",getLayoutInflater());
                    et_last_name_dis_sal.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                }
                else if(et_mobile_dis_sal1.getText().toString().trim().equalsIgnoreCase("+91-")){
                    //Globals.CustomToast(Business_Registration.this, "Mobile Number is Compulsory", getLayoutInflater());
                    Globals.CustomToast(EditDisSalesActivity.this, "Please Enter Mobile Number", getLayoutInflater());
                    et_mobile_dis_sal1.requestFocus();
                    et_mobile_dis_sal1.setSelection(et_mobile_dis_sal1.getText().toString().length());
                    //Toast.makeText(Business_Registration.this,"Please enter mobile no",Toast.LENGTH_LONG).show();
                }
                else if (et_mobile_dis_sal1.length() < 14 && !smobile.equalsIgnoreCase("")) {

                    // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(EditDisSalesActivity.this, "Please Enter Valid 10 Digit Mobile Number", getLayoutInflater());
                    et_mobile_dis_sal1.requestFocus();
                }
                else if(!mobileavailable){
                    hasMobileChecked=false;
                    Globals.CustomToast(EditDisSalesActivity.this, "Please Enter Unique Mobile Number", getLayoutInflater());
                    et_mobile_dis_sal1.requestFocus();
                }
                else if (et_alt_mobile_dis_sal1.length() < 14 && !amobile.equalsIgnoreCase("")) {
                    Globals.CustomToast(EditDisSalesActivity.this, "Please Enter Valid 10 Digit A_Mobile Number", getLayoutInflater());
                    et_alt_mobile_dis_sal1.requestFocus();
                }
                else if(!et_alt_mobile_dis_sal1.getText().toString().replace("+91-","").isEmpty() && !altMobileavailable){
                    hasAltMobileChecked=false;
                    Globals.CustomToast(EditDisSalesActivity.this, "Please Enter Unique Alternate Mobile Number", getLayoutInflater());
                    et_alt_mobile_dis_sal1.requestFocus();
                }
//                else if (et_emailid_dis_sal.getText().toString().trim().equalsIgnoreCase("")) {
//                    Globals.CustomToast(getApplicationContext(), "Please enter email address", getLayoutInflater());
//                    et_emailid_dis_sal.requestFocus();
//                }
//                else if(!validateEmail1(email)){
//                    //C.Toast(getApplicationContext(),"Please enter Valid Email Address",getLayoutInflater());
//                    Globals.CustomToast(getApplicationContext(), "Please enter Valid Email Address", getLayoutInflater());
//                    et_emailid_dis_sal.requestFocus();
//                }
                else if (et_panno_dis_sal1.length() < 10 && !pannumber.equalsIgnoreCase("") && !validatePANNUM(pancardno)) {
                    Globals.CustomToast(EditDisSalesActivity.this, "Please Enter Valid PAN Number", getLayoutInflater());
                    et_panno_dis_sal1.requestFocus();
                }
                else if (et_aadhaarcardno_dis_sal1.length() < 12 && !aadharnumber.equalsIgnoreCase("") && !validateAADHARCARDNUM(aadhaarcardno)) {
                    Globals.CustomToast(EditDisSalesActivity.this, "Please Enter Valid Aadhar Card Number", getLayoutInflater());
                    et_aadhaarcardno_dis_sal1.requestFocus();
                }
                else if(spn_status.getSelectedItemPosition()==0){
                    Globals.CustomToast(getApplicationContext(), "Please Select Status", getLayoutInflater());
                }


                else {

                    Globals.CustomToast(getApplicationContext(), "Profile Updated Successfully", getLayoutInflater());
                    try {
                        JSONArray jObjectMain1 = new JSONArray();

                        for (int i = 0; i < 1; i++) {
                            JSONObject jObjectData = new JSONObject();
                            // user = cn.getName().toString();
                            //jObjectData.put("id", );
                            jObjectData.put("sales_person_id",String.valueOf(disSalesList.getSales_id()));
                            app = new AppPrefs(EditDisSalesActivity.this);
                            //Log.e("111122222333333",""+String.valueOf(disSalesList.getSales_id()));
                            app.setSalesId(String.valueOf(disSalesList.getSales_id()));
                            //Log.e("1111",""+app.getSalesId());
                            jObjectData.put("first_name", fname);
                            jObjectData.put("last_name", lname);
                            jObjectData.put("email_id", email);
                            jObjectData.put("phone_no", phone);
                            jObjectData.put("status", status);
                            jObjectData.put("pancard_sales", pancardno);
                            jObjectData.put("alternate_no", alt_phone);
                            jObjectData.put("aadhar_card_sales", aadhaarcardno);

                            jObjectMain1.put(jObjectData);
                            profile_array = jObjectMain1.toString();

                            new Update_DisSalesData().execute();
                        }

                        //System.out.println("" + profile_array);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


            }
        });

        btn_cancel_sal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DisSalesListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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
                Intent i = new Intent(EditDisSalesActivity.this,DisSalesListActivity.class);
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
                Intent i = new Intent(EditDisSalesActivity.this, MainPage_drawer.class);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DisSalesListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public class Update_DisSalesData extends AsyncTask<Void, Void, String> {

        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(EditDisSalesActivity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                JSONArray jArray = new JSONArray(profile_array);
                JSONObject profile = jArray.getJSONObject(0);


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //parameters.add(new BasicNameValuePair("sales_person_id",profile_array));
                parameters.add(new BasicNameValuePair("sales_person_id", profile.getString("sales_person_id")));
                parameters.add(new BasicNameValuePair("first_name", profile.getString("first_name")));
                parameters.add(new BasicNameValuePair("last_name", profile.getString("last_name")));
                parameters.add(new BasicNameValuePair("email_id", profile.getString("email_id")));
                parameters.add(new BasicNameValuePair("phone_no", profile.getString("phone_no")));
                parameters.add(new BasicNameValuePair("status", profile.getString("status")));
                //Log.e("STATUS EDITED",""+profile.getString("status"));
                parameters.add(new BasicNameValuePair("pancard_sales", profile.getString("pancard_sales")));
                parameters.add(new BasicNameValuePair("alternate_no", profile.getString("alternate_no")));
                parameters.add(new BasicNameValuePair("aadhar_card_sales", profile.getString("aadhar_card_sales")));

                //Log.e("","" + profile_array);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/App_Edit_Distributor_Sales_Person", ServiceHandler.POST, parameters);
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
                    Globals.CustomToast(getApplicationContext(), "SERVER ERRER", getLayoutInflater());
                    //  Toast.makeText(EditDisSalesActivity.this,"SERVER ERRER",Toast.LENGTH_LONG).show();
                    //Global.CustomToast(Activity_Login.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(getApplicationContext(), "" + Message, getLayoutInflater());
                        //Toast.makeText(EditDisSalesActivity.this,""+Message,Toast.LENGTH_LONG).show();
                        // Global.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {


                        //  String VERIFY = jObj.getString("true");
                        String Message = jObj.getString("message");
                        Toast.makeText(EditDisSalesActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(EditDisSalesActivity.this, DisSalesListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }

    }

    public class send_Uni_Mobile_Data_A extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        EditDisSalesActivity.this, "");

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
                parameters.add(new BasicNameValuePair("user_id", "" + userID));
                parameters.add(new BasicNameValuePair("phone_no", "" + s_mobile));
//                user_id
//                        phone_no

                ////Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                //Log.e("", "" + s_mobile);
                //Log.e("", "" + userID);

                //Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_CheckUniqueMobile",ServiceHandler.POST,parameters);

                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                // System.out.println("error1: " + e.toString());

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
                    Globals.CustomToast(EditDisSalesActivity.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(EditDisSalesActivity.this,""+Message, getLayoutInflater());
                        mobileavailable=false;
                        et_mobile_dis_sal1.setText("");
                        et_mobile_dis_sal1.requestFocus();
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
}
