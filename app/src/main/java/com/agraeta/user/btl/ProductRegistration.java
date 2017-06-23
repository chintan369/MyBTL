package com.agraeta.user.btl;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductRegistration extends Fragment {
    Custom_ProgressDialog loadingView;
    String json = new String();
    String firstname,lastname,emailid,mobile,retailername,autoproduct;
    String position1,s_sugpname;
    String f_name,l_name,email_id,phone_no,city_name;
    Button btn_submit_product_registration;
    Spinner spn_pro_reg_city,spn_pro_reg_state;

    ArrayList<String> product_array = new ArrayList<String>();
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    DatabaseHandler db;
    EditText et_fname,et_lname,et_email,et_mobile,et_retailername;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    String position_state = new String();
    String position_city = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String str_product_registration = new String();
    final static int RQS_GET_IMAGE = 1;
    String curFileName = new String();
    String FilePathsend = new String();
    String FileNamesend = new String();
    HttpFileUpload2 httpupload;

    public static final int RESULT_CANCELED = 0;
    /**
     * Standard activity result: operation succeeded.
     */
    public static final int RESULT_OK = -1;
    /**
     * Start of user-defined activity results.
     */
    public static final int RESULT_FIRST_USER = 1;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    String fd = new String();
    String s_date;
    String smobile = new String();
    AutoCompleteTextView autocomplete_productname;
    View rootView;
    TextView txt_date,txt_photo;
    Button btn_date,btn_photo,btn_Login;
    LinearLayout l_login,l_wlogin;
    
    TextView tvfname,tvlname,tvemail,tvproname,tvstate,tvcity,tvenquiry,tvcname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_product_registration, container, false);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
       // setRefershData();

        l_login=(LinearLayout)rootView.findViewById(R.id.login_layout);
        l_wlogin=(LinearLayout)rootView.findViewById(R.id.without_login);

        setRefershData();

        if(user_data.size() == 0){

            l_login.setVisibility(View.VISIBLE);
            l_wlogin.setVisibility(View.GONE);
            //Log.e("11111", "11111");

            btn_Login = (Button)rootView.findViewById(R.id.btn_Login);
            btn_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), LogInPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });



        }else{
            l_login.setVisibility(View.GONE);
            l_wlogin.setVisibility(View.VISIBLE);
            //Log.e("22222", "22222");
        }


        fetchID();






        return  rootView;
    }
    private  void fetchID()
    {
        spn_pro_reg_city=(Spinner)rootView.findViewById(R.id.spn_pro_reg_city);
        spn_pro_reg_state=(Spinner)rootView.findViewById(R.id.spn_pro_reg_state);

        btn_submit_product_registration=(Button)rootView.findViewById(R.id.btn_submit_product_registration);
        autocomplete_productname=(AutoCompleteTextView)rootView.findViewById(R.id.autocomplete_productname);
        autocomplete_productname.setThreshold(2);
        et_fname=(EditText)rootView.findViewById(R.id.et_product_registration_fname);
        et_lname=(EditText)rootView.findViewById(R.id.et_product_registration_lname);
        et_email=(EditText)rootView.findViewById(R.id.et_product_registration_email);
        et_mobile=(EditText)rootView.findViewById(R.id.et_product_registration_mobile);
        et_mobile.setText("+91-");
        Selection.setSelection(et_mobile.getText(), et_mobile.getText().length());
        et_mobile.addTextChangedListener(new TextWatcher() {

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
                    et_mobile.setText("+91-");
                    Selection.setSelection(et_mobile.getText(), et_mobile.getText().length());

                }

            }
        });
        et_retailername=(EditText)rootView.findViewById(R.id.et_product_registration_retailername);


        btn_date = (Button) rootView.findViewById(R.id.btn_date);
        txt_date = (TextView) rootView.findViewById(R.id.txt_date);
        btn_photo = (Button) rootView.findViewById(R.id.btn_upload_photo);
        txt_photo = (TextView) rootView.findViewById(R.id.txt_photo);

        et_fname.setText(f_name);
        et_lname.setText(l_name);
        et_email.setText(email_id);
        et_mobile.setText("+91-"+phone_no);

        autocomplete_productname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 3) {
                    s_sugpname = autocomplete_productname.getText().toString().trim();
                    new get_produt_data().execute();
                }

            }
        });
        new send_state_Data().execute();

        spn_pro_reg_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if (position_state.equalsIgnoreCase("0")) {

                } else {
                    new send_city_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_pro_reg_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


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

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        btn_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                fromDatePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fd = dateFormatter.format(newDate.getTime());

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

                txt_date.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  dialog.show();

                Intent intent1 = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intent1,RQS_GET_IMAGE);
            }
        });

        btn_submit_product_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smobile = et_mobile.getText().toString().trim().replace("+91-","");


                //Log.e("ssssss",""+smobile);
                if (et_fname.getText().toString().trim().equalsIgnoreCase("")) {

                    et_fname.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter First Name", getActivity().getLayoutInflater());

                }else if (et_fname.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 2 character", getActivity().getLayoutInflater());
                    et_fname.requestFocus();
                } else if (et_lname.getText().toString().trim().equalsIgnoreCase("")) {
                    et_lname.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter Last Name", getActivity().getLayoutInflater());

                } else if (et_lname.length() < 2) {
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 2 Character", getActivity().getLayoutInflater());
                    et_lname.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                }  else if (et_email.getText().toString().trim().equalsIgnoreCase("")) {
                    et_email.requestFocus();
                    Globals.CustomToast(getActivity(), "Please enter email address", getActivity().getLayoutInflater());

                } else if (validateEmail1(et_email.getText().toString()) != true) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                    et_email.requestFocus();
                    Globals.CustomToast(getActivity(), "Please enter a valid email address", getActivity().getLayoutInflater());
                }  else if (et_mobile.length() < 14 && !smobile.equalsIgnoreCase("")) {
                    et_mobile.requestFocus();
                    Globals.CustomToast(getActivity(), "Mobile Number is Compulsory 10 Digit", getActivity().getLayoutInflater());

                }else if (position_state.equalsIgnoreCase("0")) {
                    spn_pro_reg_state.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Select State", getActivity().getLayoutInflater());
                } else if (position_city.equalsIgnoreCase("0")) {
                    spn_pro_reg_city.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Select city", getActivity().getLayoutInflater());
                } else if (autocomplete_productname.getText().toString().equalsIgnoreCase("")) {
                    autocomplete_productname.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter Product Name", getActivity().getLayoutInflater());
                } else if (txt_date.getText().toString().trim().equalsIgnoreCase("")) {
                    txt_date.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter Product Purchse Date", getActivity().getLayoutInflater());
                }else if (txt_photo.getText().toString().equalsIgnoreCase("")) {
                    txt_photo.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Upload Invoice Copy", getActivity().getLayoutInflater());
                }else {

                    String date = txt_date.getText().toString();
                    SimpleDateFormat input = new SimpleDateFormat("MM-dd-yyyy");
                    SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date oneWayTripDate = input.parse(date);             // parse input
                        s_date = output.format(oneWayTripDate);

                        txt_date.setText(""+s_date);

                        //Log.e("DAte", "" + s_date);

                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                     firstname=et_fname.getText().toString().trim();
                     lastname=et_lname.getText().toString().trim();
                     emailid=et_email.getText().toString().trim();
                     mobile=et_mobile.getText().toString().trim().replace("+91-","");
                     retailername=et_retailername.getText().toString().trim();
                     autoproduct=autocomplete_productname.getText().toString();



                    File file = new File(FilePathsend, FileNamesend);
                    FileInputStream is;

                    try {
                        is = new FileInputStream(file);
                        // httpupload = new HttpFileUpload("http://app.nivida.in/boss/Product/App_SearchProductName", curFileName.toString(),curFileName.toString(), datauser);
                        httpupload = new HttpFileUpload2(Globals.server_link+"ProductRegistration/App_AddProductRegistration",firstname,lastname,emailid,mobile,curFileName.toString(),autoproduct,position_statename,position_cityname,retailername,s_date);
                        httpupload.Send_Now(is);
                        Globals.CustomToast(getActivity(), "Successfully Register Your Product", getActivity().getLayoutInflater());
                        Intent i = new Intent(getActivity(), MainPage_drawer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                  //  new submit_Product().execute();
                }

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
                        getActivity(), "");

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
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(),""+Message, getActivity().getLayoutInflater());
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


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_pro_reg_state.setAdapter(adapter_state);

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
    public class get_produt_data extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        getActivity(), "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("product_name", s_sugpname));



                //Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Product/App_SearchProductName",ServiceHandler.POST,parameters);
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
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(),""+Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);
                        product_array.clear();

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");

                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);

                                String cname = catObj.getString("product_name");
                                //Log.e("cname:::",""+cname);
                                product_array.add(cname);

                            }
                            //Log.e("Size:::",""+product_array.size());


                            ArrayAdapter<String> adap = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, product_array);
                            adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                            autocomplete_productname.setAdapter(adap);
                            autocomplete_productname.showDropDown();
                            loadingView.dismiss();
                        }


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
                        getActivity(), "");

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
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(),""+Message, getActivity().getLayoutInflater());
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


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_pro_reg_city.setAdapter(adapter_city);

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
                        getActivity(), "");

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
                parameters.add(new BasicNameValuePair("product_name", autoproduct));
                parameters.add(new BasicNameValuePair("retailer_name", retailername));







                //Log.e("",""+parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductRegistration/App_AddProductRegistration",ServiceHandler.POST,parameters);
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
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(),""+Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

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


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spn_pro_reg_city.setAdapter(adapter_city);

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
        db = new DatabaseHandler(getActivity());

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

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
                    txt_photo.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("doc")){
                    txt_photo.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("docx")){
                    txt_photo.setText("File name : "+curFileName);
                }/*else if(extension.equalsIgnoreCase("txt")){
                    txt_photo.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("rtf")){
                    txt_photo.setText("File name : "+curFileName);
                }*/else if(extension.equalsIgnoreCase("png")){
                    txt_photo.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("jpg")){
                    txt_photo.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("jpeg")){
                    txt_photo.setText("File name : "+curFileName);
                }
                else{
                    txt_photo.setText("");
                    //Globals.CustomToast(getActivity(), "Please Select Pdf File", getActivity().getLayoutInflater());
                }



            }
        }
    }
}