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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class career_activity extends AppCompatActivity {
    String firstname,lastname,emailid,mobile,retailername,autoproduct,functionalname;
  //  DatabaseHandler db;
    HttpFileUpload1 httpupload;
    String curFileName;
    String FilePathsend = new String();
    String FileNamesend = new String();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    Spinner Func_area;
    String f_name, l_name, email_id, phone_no, city_name;
    View rootView;
    String position_state = new String();
    String position_city = new String();
    String position_statename = new String();
    String position_cityname = new String();
    Button btn_submit_careers,btn_Reset_careers;
    Custom_ProgressDialog loadingView;
    String json = new String();
    Button btnfilechoose;
    String datauser = new String();
    DatabaseHandler db;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    AppPrefs app;
    String owner_id = new String();
    String u_id = new String();
    String role_id=new String();
TextView tv_filename;
    final static int RQS_GET_IMAGE = 1;
    String jobid,jobname;
    Spinner spn_state_Careers,spn_city_careers;
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    EditText etf,etl,etemail,etmob,etfunctional;
    String cartJSON="";
    boolean hasCartCallFinish=true;

    TextView txt;


    String smobile = new String();
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
        setContentView(R.layout.activity_careers);

        career_activity.this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setActionBar();
        setRefershData();
        try{
            AppPrefs app = new AppPrefs(career_activity.this);
            jobid=app.getjobid().toString();
            jobname=app.getjobname().toString();


            //Log.e("jobid", "" + jobid);
            //Log.e("jobid",""+jobname);

            tv_filename=(TextView)findViewById(R.id.tv_filename);
            btnfilechoose=(Button)findViewById(R.id.btnfilechoose);
            etf=(EditText)findViewById(R.id.etfirst_name_careers);
            etl=(EditText)findViewById(R.id.etlast_name_careers);
            etemail=(EditText)findViewById(R.id.et_email_careers);
            etmob=(EditText)findViewById(R.id.et_mobile_careers);
            etmob.setText("+91-");
            Selection.setSelection(etmob.getText(), etmob.getText().length());
            etmob.addTextChangedListener(new TextWatcher() {

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
                        etmob.setText("+91-");
                        Selection.setSelection(etmob.getText(), etmob.getText().length());

                    }

                }
            });
            etfunctional=(EditText)findViewById(R.id.et_functional_area_careers);

            spn_city_careers=(Spinner)findViewById(R.id.spn_city_careers);
            spn_state_Careers=(Spinner)findViewById(R.id.spn_state_Careers);

            btn_submit_careers=(Button)findViewById(R.id.btn_submit_careers);
            btn_Reset_careers=(Button)findViewById(R.id.btn_Reset_careers);
            setRefershData();
            if (user_data.size() == 0) {

            } else {
                for (int i = 0; i < user_data.size(); i++) {

                    etf.setText(f_name);
                    etl.setText(l_name);
                    etemail.setText(email_id);
                    etmob.setText("+91-"+phone_no);

                }
            }
            etfunctional.setText(jobname);

            btnfilechoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(career_activity.this, FileChooser.class);
                    startActivityForResult(intent1,RQS_GET_IMAGE);
                }
            });


            new send_state_Data().execute();
        }catch (NullPointerException e)
        {
            //Log.e("exception",""+e.toString());
        }

        spn_state_Careers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if (position_state.equalsIgnoreCase("0")) {

                } else {
                    try
                    {  //Log.e("position_state", "" + position_state);
                        //Log.e("position_statename", "" + position_statename);
                        new send_city_Data().execute();

                    }catch(Exception e)
                    {
                        //Log.e("send_city_Data",""+e.toString());
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_city_careers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub


                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if (position_city.equalsIgnoreCase("0")) {

                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        btn_submit_careers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smobile = etmob.getText().toString().trim().replace("+91-","");


                //Log.e("ssssss",""+smobile);
                if (etf.getText().toString().trim().equalsIgnoreCase("")) {


                    Globals.CustomToast(career_activity.this, "Please Enter First Name", career_activity.this.getLayoutInflater());
                    etf.requestFocus();

                } else if (etf.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(career_activity.this, "Please Enter Atleast 2 character", getLayoutInflater());
                    etf.requestFocus();
                }else if (etl.getText().toString().trim().equalsIgnoreCase("")) {

                    Globals.CustomToast(career_activity.this, "Please Enter Last Name", career_activity.this.getLayoutInflater());
                    etl.requestFocus();

                } else if (etl.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(career_activity.this, "Please Enter Atleast 2 character", getLayoutInflater());
                    etl.requestFocus();
                } else if (etemail.getText().toString().trim().equalsIgnoreCase("")) {
                    etemail.requestFocus();
                    Globals.CustomToast(career_activity.this, "Please enter email address", career_activity.this.getLayoutInflater());

                } else if (validateEmail1(etemail.getText().toString()) != true) {
                    etemail.requestFocus();
                    Globals.CustomToast(career_activity.this, "Please enter a valid email address", career_activity.this.getLayoutInflater());

                } else if (etmob.length() < 14 && !smobile.equalsIgnoreCase("")) {
                    etmob.requestFocus();
                    Globals.CustomToast(career_activity.this, "Mobile Number is Compulsory 10 Digit", career_activity.this.getLayoutInflater());

                }
                else if (position_state.equalsIgnoreCase("0")) {
                    spn_state_Careers.requestFocus();

                    Globals.CustomToast(career_activity.this, "Please Select State", career_activity.this.getLayoutInflater());

                } else if (position_city.equalsIgnoreCase("0")) {
                    spn_city_careers.requestFocus();

                    Globals.CustomToast(career_activity.this, "Please Select city", career_activity.this.getLayoutInflater());

                } else if (tv_filename.getText().toString().equalsIgnoreCase("")) {
                    tv_filename.requestFocus();

                    Globals.CustomToast(career_activity.this, "Please Upload Your Resume", career_activity.this.getLayoutInflater());

                }else {

                    firstname = etf.getText().toString();
                    lastname = etl.getText().toString();
                    emailid = etemail.getText().toString();
                    mobile = etmob.getText().toString();
                    functionalname = etfunctional.getText().toString();
                    //Log.e("00000000000000000000", "" + firstname + " " + lastname + " " + emailid + "" + mobile + "" + functionalname);

                    File file = new File(FilePathsend, FileNamesend);
                    FileInputStream is;

                    try {
                        is = new FileInputStream(file);
                        // httpupload = new HttpFileUpload("http://app.nivida.in/boss/Product/App_SearchProductName", curFileName.toString(),curFileName.toString(), datauser);
                        httpupload = new HttpFileUpload1(Globals.server_link+"Career/App_AddCareer", curFileName.toString(), firstname,lastname,emailid,mobile,position_cityname,position_statename,jobid,jobname);
                        httpupload.Send_Now(is);
                        Globals.CustomToast(career_activity.this, "Successfully Send",getLayoutInflater());
                        Intent i = new Intent(career_activity.this, MainPage_drawer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                   // new submit_Product().execute();
                }

            }
        });
        
  
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == RQS_GET_IMAGE){
            if (resultCode == RESULT_OK) {
                curFileName = data.getStringExtra("GetFileName");
                //Log.e("1", curFileName.toString());
                String filename = curFileName;
                String filePath = data.getStringExtra("GetPath");
                FilePathsend = filePath;
                FileNamesend = filename;
                String filenameArray[] = filename.split("\\.");
                String extension = filenameArray[filenameArray.length-1];

                if(extension.equalsIgnoreCase("pdf")){
                    tv_filename.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("doc")){
                    tv_filename.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("docx")){
                    tv_filename.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("txt")){
                    tv_filename.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("rtf")) {
                    tv_filename.setText("File name : "+curFileName);
                }
                else{

                    tv_filename.setText("");
                }



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
        img_notification.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
         txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        app = new AppPrefs(career_activity.this);
        String qun =app.getCart_QTy();

        setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                 role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(career_activity.this);
                     role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else{
                    u_id=owner_id;
                }


            }

            List<NameValuePair> para=new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id",u_id));

            new GetCartByQty(para).execute();



        }else{
            app = new AppPrefs(career_activity.this);
            app.setCart_QTy("");
        }

        app = new AppPrefs(career_activity.this);
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
       /* db = new DatabaseHandler(career_activity.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        if(bean_cart.size() == 0){
            txt.setVisibility(View.GONE);
            txt.setText("");
        }else{
            txt.setText(bean_cart.size()+"");
            txt.setVisibility(View.VISIBLE);
        }*/

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(career_activity.this, Enquiry.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
                finish();
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(career_activity.this);
                app.setUser_notification("Enquiry");
                Intent i = new Intent(career_activity.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(career_activity.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(career_activity.this);
                app.setUser_notification("Enquiry");
                Intent i = new Intent(career_activity.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    public class submit_Product extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        career_activity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("first_name",firstname));
                parameters.add(new BasicNameValuePair("last_name", lastname));
                parameters.add(new BasicNameValuePair("email", emailid));
                parameters.add(new BasicNameValuePair("contact_no", mobile));
                parameters.add(new BasicNameValuePair("city_name", position_cityname));
                parameters.add(new BasicNameValuePair("vacancy_id", jobid));
                parameters.add(new BasicNameValuePair("vacancy_name", functionalname));
                parameters.add(new BasicNameValuePair("career_file", ""));







                //Log.e("submit data",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Career/App_AddCareer",ServiceHandler.POST,parameters);

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
                    Globals.CustomToast(career_activity.this, "SERVER ERRER", career_activity.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(career_activity.this,""+Message, career_activity.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                   /*     if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            cityname.add("Select City");
                            cityid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");


                            }
                        }*/
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
                        career_activity.this, "");

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
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
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
                    Globals.CustomToast(career_activity.this, "SERVER ERRER", career_activity.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(career_activity.this,""+Message, career_activity.this.getLayoutInflater());
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


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(career_activity.this,  android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_city_careers.setAdapter(adapter_city);

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
    private boolean validateEmail1(String email) {
        // TODO Auto-generated method stub

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

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
                        career_activity.this, "");

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
                    Globals.CustomToast(career_activity.this, "SERVER ERRER", career_activity.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(career_activity.this,""+Message, career_activity.this.getLayoutInflater());
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


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(career_activity.this,  android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_state_Careers.setAdapter(adapter_state);

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
    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(career_activity.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();



        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid =user_array_from_db.get(i).getId();
            String user_id =user_array_from_db.get(i).getUser_id();
            email_id = user_array_from_db.get(i).getEmail_id();
            phone_no =user_array_from_db.get(i).getPhone_no();
            f_name = user_array_from_db.get(i).getF_name();
            l_name = user_array_from_db.get(i).getL_name();
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
            city_name = user_array_from_db.get(i).getCity_name();
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
    public void onBackPressed() {
        // TODO Auto-generated method stub

       /* Intent i = new Intent(career_activity.this, Enquiry.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);*/
        finish();

    };
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

                    Globals.CustomToast(career_activity.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(career_activity.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(career_activity.this);
                        app.setCart_QTy(""+qu);


                    }

                }


            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

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
        }
    }

}
