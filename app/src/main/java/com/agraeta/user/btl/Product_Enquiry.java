package com.agraeta.user.btl;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
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

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Product_Enquiry extends Fragment {


    ArrayList<String> product_array = new ArrayList<String>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    EditText f_name,l_name,email,mobile_no,guarantee,cashmemo,yourcomment,productrate;
    Spinner sp_state,sp_city,sp_product_name;
    TextView purchase_date;
    Button btn_date,btn_submit;
    View rootView;
    LinearLayout l_login,l_wlogin;

    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();

    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();

    String s_fname = "";
    String s_lname = "";
    String s_email = new String();
    String s_mobile = new String();
    String s_city = new String();
    String s_state = new String();
    String s_pname = new String();
    String s_productname = "";
    String s_prate = new String();
    String s_complain = new String();
    String s_guarantee = new String();
    String s_cashmemo = new String();
    String s_txtdate = new String();


    String position_state = new String();
    String position_city = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String position_productname = new String();
    String s_sugpname = new String();
    String smobile = new String();
    String fd = new String();
    AutoCompleteTextView autocompletetextview;
    String[] arr = { "Paries,France", "PA,United States","Parana,Brazil", "Padua,Italy", "Pasadena,CA,United States"};
    Custom_ProgressDialog loadingView;
    String json = new String();
    DatabaseHandler db;
    boolean isFirstTime = true;
    AppPrefs prefs;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_product_enquiry, container, false);
        
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        prefs = new AppPrefs(getContext());

        l_login=(LinearLayout)rootView.findViewById(R.id.login_layout);
        l_wlogin=(LinearLayout)rootView.findViewById(R.id.without_login);

        setRefershData();

      /*  if(user_data.size() == 0){

            l_login.setVisibility(View.GONE);
            l_wlogin.setVisibility(View.GONE);
            Intent i = new Intent(getActivity(), LogInPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }else{
            l_login.setVisibility(View.GONE);
            l_wlogin.setVisibility(View.VISIBLE);
        }
*/
        FetchId();

        return  rootView;
    }

    private void FetchId() {

        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();

        f_name=(EditText)rootView.findViewById(R.id.etfirst_name_Cust_com);
        l_name=(EditText)rootView.findViewById(R.id.etlast_name_Cust_com);
        email=(EditText)rootView.findViewById(R.id.Et_Emailid);
        mobile_no=(EditText)rootView.findViewById(R.id.etmobile);
        mobile_no.setText("+91-");
        Selection.setSelection(mobile_no.getText(), mobile_no.getText().length());
        mobile_no.addTextChangedListener(new TextWatcher() {

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
                    mobile_no.setText("+91-");
                    Selection.setSelection(mobile_no.getText(), mobile_no.getText().length());

                }

            }
        });
        guarantee=(EditText)rootView.findViewById(R.id.et_guarantee);
        cashmemo=(EditText)rootView.findViewById(R.id.et_cashmemono);
        yourcomment=(EditText)rootView.findViewById(R.id.et_product_enquiry_comment);
        productrate=(EditText)rootView.findViewById(R.id.et_productrate);

        /*product_array = new ArrayList<String>();

        product_array.add("Floor Hinges");
        product_array.add("Bottom Patch");
        product_array.add("Top Patch");*/

        autocompletetextview = (AutoCompleteTextView)rootView.findViewById(R.id.autoCompleteTextView1);
        autocompletetextview.setThreshold(2);



        autocompletetextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 3) {
                    s_sugpname = autocompletetextview.getText().toString().trim();
                    new get_produt_data().execute();
                }

            }
        });



        setRefershData();

        if(user_data.size() == 0){

        }else{
            for (int i = 0; i < user_data.size(); i++) {

                f_name.setText(user_data.get(i).getF_name());
                l_name.setText(user_data.get(i).getL_name());
             //   mobile_no.setText(user_data.get(i).getPhone_no());
                mobile_no.setText("+91-"+user_data.get(i).getPhone_no());
                email.setText(user_data.get(i).getEmail_id());

            }
        }

        sp_state =(Spinner)rootView.findViewById(R.id.spn_select_state);
        sp_city =(Spinner)rootView.findViewById(R.id.spn_select_city);
        sp_product_name =(Spinner)rootView.findViewById(R.id.product_spinner);

        purchase_date=(TextView)rootView.findViewById(R.id.txt_date);
        btn_date=(Button)rootView.findViewById(R.id.btn_date);

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        btn_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

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

              //  s_txtdate = dateFormat.format(newDate.getTime());

                purchase_date.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        btn_submit=(Button)rootView.findViewById(R.id.btn_Cust_com_sub);

        new send_state_Data().execute();


        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if (position_state.equalsIgnoreCase("0")) {

                } else {
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
                if (position_city.equalsIgnoreCase("0")) {
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

       /* sp_product_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;

                position_productname = product_array.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });*/


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smobile = mobile_no.getText().toString().trim().replace("+91-","");


                //Log.e("ssssss",""+smobile);
                if (f_name.getText().toString().trim().equalsIgnoreCase("")) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please Enter First Name", getActivity().getLayoutInflater());
                    f_name.requestFocus();
                } else if (f_name.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 2 character", getActivity().getLayoutInflater());
                    f_name.requestFocus();
                } else if (l_name.getText().toString().trim().equalsIgnoreCase("")) {
                    l_name.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter Last Name", getActivity().getLayoutInflater());
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (l_name.length() < 2) {
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 2 Character", getActivity().getLayoutInflater());
                    l_name.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.requestFocus();
                    Globals.CustomToast(getActivity(), "Please enter email address", getActivity().getLayoutInflater());
                    //  Toast.makeText(Business_Registration.this,"Please enter email address",Toast.LENGTH_LONG).show();
                } else if (validateEmail1(email.getText().toString()) != true) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                    email.requestFocus();
                    Globals.CustomToast(getActivity(), "Please enter a valid email address", getActivity().getLayoutInflater());
                } else if (mobile_no.length() < 14 && !smobile.equalsIgnoreCase("")) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    mobile_no.requestFocus();
                    Globals.CustomToast(getActivity(), "Mobile Number is Compulsory 10 Digit", getActivity().getLayoutInflater());
                } else if (position_state.equalsIgnoreCase("0")) {
                    Globals.CustomToast(getActivity(), "Please Select State", getActivity().getLayoutInflater());
                    sp_state.requestFocus();
                } else if (position_city.equalsIgnoreCase("0")) {
                    sp_city.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Select city", getActivity().getLayoutInflater());
                } else if (autocompletetextview.getText().toString().trim().equalsIgnoreCase("")) {
                    autocompletetextview.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter Product Name", getActivity().getLayoutInflater());
                }
               else if (yourcomment.getText().toString().trim().equalsIgnoreCase("")) {
                    yourcomment.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter Your Query", getActivity().getLayoutInflater());
                } else if (yourcomment.length() < 10) {
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 10 Character", getActivity().getLayoutInflater());
                    yourcomment.requestFocus();
                }else {
                    s_fname = f_name.getText().toString().trim();
                    s_lname = l_name.getText().toString().trim();
                    s_email = email.getText().toString().trim();
                    s_mobile = mobile_no.getText().toString().trim().replace("+91-","");
                    s_city = position_cityname;
                 //   s_pname = position_productname;
                    s_complain = yourcomment.getText().toString().trim();
                    s_guarantee = guarantee.getText().toString().trim();
                    s_cashmemo = cashmemo.getText().toString().trim();
                    s_prate = productrate.getText().toString().trim();
                    s_productname = autocompletetextview.getText().toString().trim();
                    //Log.e("s_productname",""+s_productname);
                    String date = purchase_date.getText().toString();
                    SimpleDateFormat input = new SimpleDateFormat("MM-dd-yyyy");
                    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date oneWayTripDate = input.parse(date);             // parse input
                        s_txtdate = output.format(oneWayTripDate);

                        //Log.e("DAte", "" + s_txtdate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    new Send_ProductE_Data().execute();

                }

            }
        });




        /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, product_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_product_name.setAdapter(spinnerArrayAdapter);*/


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

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(getActivity());

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

    public class send_state_Data extends AsyncTask<Void,Void,String>
    {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(getActivity(), "");

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

                            }

                            ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, statename);
                            adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_state.setAdapter(adapter_state);

                            if (isFirstTime && BTL.addressList.size() > 0 && (!prefs.getUserRoleId().equals(C.ADMIN) && !prefs.getUserRoleId().equals(C.COMP_SALES_PERSON) && !prefs.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON))) {
                                Log.e("Entered", "To check " + BTL.addressList.get(0).getState_id());
                                int pos = 0;
                                for (int k = 0; k < statename.size(); k++) {
                                    if (stateid.get(k).equals(BTL.addressList.get(0).getState_id())) {
                                        Log.e("State ID", stateid.get(k));
                                        pos = k;
                                        break;
                                    }
                                }
                                if (pos == 0) isFirstTime = false;
                                sp_state.setSelection(pos);

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
        public StringBuilder sb;
        boolean status;
        private String result;
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

                            }

                            ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cityname);
                            adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_city.setAdapter(adapter_city);

                            if (isFirstTime && BTL.addressList.size() > 0 && (!prefs.getUserRoleId().equals(C.ADMIN) && !prefs.getUserRoleId().equals(C.COMP_SALES_PERSON) && !prefs.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON))) {
                                int pos = 0;
                                for (int k = 0; k < cityname.size(); k++) {
                                    if (cityid.get(k).equals(BTL.addressList.get(0).getCity_id())) {
                                        pos = k;
                                        break;
                                    }
                                }
                                isFirstTime = false;
                                sp_city.setSelection(pos);
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
        public StringBuilder sb;
        boolean status;
        private String result;
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
                            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (getActivity(),android.R.layout.select_dialog_item, product_array);*/

                            ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, product_array);
                            adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            //autocompletetextview.setThreshold(3);
                            autocompletetextview.setAdapter(adapter_city);
                            autocompletetextview.showDropDown();

                        }
                        loadingView.dismiss();

                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class Send_ProductE_Data extends AsyncTask<Void,Void,String>
    {
        public StringBuilder sb;
        boolean status;
        private String result;
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
                parameters.add(new BasicNameValuePair("first_name", s_fname));
                parameters.add(new BasicNameValuePair("last_name", s_lname));
                parameters.add(new BasicNameValuePair("email", s_email));
                parameters.add(new BasicNameValuePair("contact_no", s_mobile));
                parameters.add(new BasicNameValuePair("city_name", position_cityname));
                parameters.add(new BasicNameValuePair("state_name", position_statename));
                parameters.add(new BasicNameValuePair("product_name", s_productname));
                parameters.add(new BasicNameValuePair("comment", s_complain));

                //Log.e("4",""+parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
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
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(), "" + Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(), "" + Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                        Intent i = new Intent(getActivity(), MainPage_drawer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

}