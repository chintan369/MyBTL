package com.agraeta.user.btl;

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
import android.widget.Button;
import android.widget.EditText;
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


public class Bussiness_enquiry extends Fragment {


    ArrayList<String> business_array = new ArrayList<String>();

    EditText e_fname, e_lname, e_email, e_mobile, e_companyname, e_tinno, e_othertype, e_convenient, e_enquiry;

    Spinner sp_city, sp_state, sp_business_name;

    Button btn_save;

    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();

    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();

    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();


    String s_fname = new String();
    String s_lname = new String();
    String s_email = new String();
    String s_mobile = new String();
    String s_companyname = new String();
    String s_tinno = new String();
    String s_othertype = new String();
    String s_convenient = new String();
    String s_enquiry = new String();
    String smobile = new String();

    String position_state = new String();
    String position_city = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String position_bussiness = new String();

    Custom_ProgressDialog loadingView;

    String json = new String();

    DatabaseHandler db;
    View rootView;
    boolean isFirstTime = true;

    AppPrefs prefs;

    TextView txt_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isFirstTime = true;
        rootView = inflater.inflate(R.layout.activity_bussiness_enquiry, container, false);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        business_array = new ArrayList<String>();

        txt_title = (TextView) rootView.findViewById(R.id.txt_title);


        prefs = new AppPrefs(getContext());


        if (prefs.isBulkOrder()) {

            txt_title.setText("Bulk Order Enquiry");

        } else {

            txt_title.setText("Business Enquiry");
        }


        business_array.add("Dealer");
        business_array.add("Distributor");
        business_array.add("OEM");
        business_array.add("Architects");
        business_array.add("Glass Fabricator");
        business_array.add("Furniture Manufacturer");
        business_array.add("Interior Designer");
        business_array.add("Carpenters");
        business_array.add("Others");

        fetchId();

        return rootView;
    }

    private void fetchId() {
        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();

        e_fname = (EditText) rootView.findViewById(R.id.etfirst_name_Cust_com);
        e_lname = (EditText) rootView.findViewById(R.id.etlast_name_Cust_com);
        e_email = (EditText) rootView.findViewById(R.id.Et_Emailid);
        e_mobile = (EditText) rootView.findViewById(R.id.etmobile);
        e_mobile.setText("+91-");
        Selection.setSelection(e_mobile.getText(), e_mobile.getText().length());
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
        e_companyname = (EditText) rootView.findViewById(R.id.etProname_Cust_com);
        e_tinno = (EditText) rootView.findViewById(R.id.et_tinno);
        e_othertype = (EditText) rootView.findViewById(R.id.e_otherbussiness);
        e_convenient = (EditText) rootView.findViewById(R.id.et_convenienttime);
        e_enquiry = (EditText) rootView.findViewById(R.id.et_yourenquiry);

        sp_city = (Spinner) rootView.findViewById(R.id.spn_select_city);
        sp_state = (Spinner) rootView.findViewById(R.id.spn_select_state);
        sp_business_name = (Spinner) rootView.findViewById(R.id.business_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, business_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_business_name.setAdapter(spinnerArrayAdapter);

        setRefershData();

        if (user_data.size() == 0) {

        } else {
            for (int i = 0; i < user_data.size(); i++) {

                e_fname.setText(user_data.get(i).getF_name());
                e_lname.setText(user_data.get(i).getL_name());
                // e_mobile.setText(user_data.get(i).getPhone_no());
                e_mobile.setText("+91-" + user_data.get(i).getPhone_no());
                e_email.setText(user_data.get(i).getEmail_id());

            }
        }

        btn_save = (Button) rootView.findViewById(R.id.btn_Cust_com_sub);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smobile = e_mobile.getText().toString().trim().replace("+91-", "");


                //Log.e("ssssss",""+smobile);
                if (e_fname.getText().toString().trim().equalsIgnoreCase("")) {
                    e_fname.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please Enter First Name", getActivity().getLayoutInflater());
                } else if (e_fname.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 2 character", getActivity().getLayoutInflater());
                    e_fname.requestFocus();
                } else if (e_lname.getText().toString().trim().equalsIgnoreCase("")) {
                    e_lname.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter Last Name", getActivity().getLayoutInflater());
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (e_lname.length() < 2) {
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 2 Character", getActivity().getLayoutInflater());
                    e_lname.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (e_email.getText().toString().trim().equalsIgnoreCase("")) {
                    e_email.requestFocus();
                    Globals.CustomToast(getActivity(), "Please enter email address", getActivity().getLayoutInflater());
                    //  Toast.makeText(Business_Registration.this,"Please enter email address",Toast.LENGTH_LONG).show();
                } else if (validateEmail1(e_email.getText().toString()) != true) {
                    e_email.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please enter a valid email address", getActivity().getLayoutInflater());
                } else if (e_mobile.length() < 14 && !smobile.equalsIgnoreCase("")) {
                    e_mobile.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Mobile Number is Compulsory 10 Digit", getActivity().getLayoutInflater());
                } else if (position_state.equalsIgnoreCase("0")) {
                    sp_state.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Select State", getActivity().getLayoutInflater());
                } else if (position_city.equalsIgnoreCase("0")) {
                    sp_city.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Select city", getActivity().getLayoutInflater());
                } else if (e_enquiry.getText().toString().trim().equalsIgnoreCase("")) {
                    e_enquiry.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter your Enquiry Details", getActivity().getLayoutInflater());
                } else if (e_enquiry.length() < 10) {
                    Globals.CustomToast(getActivity(), "Please Enter Enquiry Atleast 10 Character", getActivity().getLayoutInflater());
                    e_enquiry.requestFocus();
                } else if (e_companyname.getText().toString().trim().equalsIgnoreCase("")) {
                    e_companyname.requestFocus();
                    Globals.CustomToast(getActivity(), "Please Enter Company Name", getActivity().getLayoutInflater());
                } else {

                    s_fname = e_fname.getText().toString().trim();
                    s_lname = e_lname.getText().toString().trim();
                    s_email = e_email.getText().toString().trim();
                    s_mobile = e_mobile.getText().toString().trim().replace("+91-", "");
                    s_companyname = e_companyname.getText().toString().trim();
                    s_tinno = e_tinno.getText().toString().trim();
                    s_othertype = e_othertype.getText().toString().trim();
                    s_convenient = e_convenient.getText().toString().trim();
                    s_enquiry = e_enquiry.getText().toString().trim();

                    new Set_Bussniess_data().execute();

                }

            }
        });

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

        sp_business_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;

                position_bussiness = business_array.get(position);
                if (position_bussiness.equalsIgnoreCase("0")) {
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

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

    public void setTitle(String title) {

    }

    public class send_state_Data extends AsyncTask<Void, Void, String> {
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

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "State/App_GetState", ServiceHandler.POST, parameters);
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
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(), "" + Message, getActivity().getLayoutInflater());
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

                            Log.e("Size", stateid.size() + " - " + BTL.addressList.size());

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
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class send_city_Data extends AsyncTask<Void, Void, String> {
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

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "City/App_GetCity", ServiceHandler.POST, parameters);
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
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(), "" + Message, getActivity().getLayoutInflater());
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
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class Set_Bussniess_data extends AsyncTask<Void, Void, String> {
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
                parameters.add(new BasicNameValuePair("company_name", s_companyname));

                parameters.add(new BasicNameValuePair("enquiry", s_enquiry));

 /*parameters.add(new BasicNameValuePair("company_name", s_companyname));
                parameters.add(new BasicNameValuePair("tin_no", s_tinno));
                parameters.add(new BasicNameValuePair("type_of_business", position_bussiness));
                parameters.add(new BasicNameValuePair("other_business_type", s_othertype));
                parameters.add(new BasicNameValuePair("time_to_contact", s_convenient));*/
                //Log.e("4",""+parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "BusinessEnquiry/App_AddBusinessEnquiry", ServiceHandler.POST, parameters);
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
                        if (prefs.isBulkOrder())
                            prefs.setBulkOrder(false);
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(), "" + Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                        Intent i = new Intent(getActivity(), MainPage_drawer.class);
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