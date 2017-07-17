package com.agraeta.user.btl;



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
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CorporateOffice extends Fragment {

    EditText e_fname,e_lname,e_email,e_mobile,e_msg;

    String s_fname = new String();
    String s_lname = new String();
    String s_email = new String();
    String s_mobile = new String();
    String s_msg = new String();

    Button btn_submit;

    View rootView;

    Custom_ProgressDialog loadingView;

    String json = new String();

    DatabaseHandler db;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_corporate_office, container, false);

        fetchId();

        return  rootView;
    }

    private void fetchId() {

        e_fname=(EditText)rootView.findViewById(R.id.etfirst_name_corpo_offi);
        e_lname=(EditText)rootView.findViewById(R.id.etlast_name_corpo_offi);
        e_email=(EditText)rootView.findViewById(R.id.Et_Emailid_corpo_offi);
        e_mobile=(EditText)rootView.findViewById(R.id.etmobile_corpo_offi);
        e_msg=(EditText)rootView.findViewById(R.id.etmessage_corpo_offi);
        btn_submit=(Button)rootView.findViewById(R.id.co_submit);
        e_mobile.setText("+91-");
        setRefershData();
        if(user_data.size() == 0){

        }else{
            for (int i = 0; i < user_data.size(); i++) {

                e_fname.setText(user_data.get(i).getF_name());
                e_lname.setText(user_data.get(i).getL_name());
                e_mobile.setText("+91-"+user_data.get(i).getPhone_no());
                e_email.setText(user_data.get(i).getEmail_id());

            }
        }
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
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (e_fname.getText().toString().trim().equalsIgnoreCase("")) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "First Name is required", getActivity().getLayoutInflater());
                } else if (e_lname.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Last Name is required", getActivity().getLayoutInflater());
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (e_mobile.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Mobile Number is Compulsory", getActivity().getLayoutInflater());
                    //Toast.makeText(Business_Registration.this,"Please enter mobile no",Toast.LENGTH_LONG).show();
                } else if (e_mobile.length() < 14) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Mobile Number is Compulsory 10 Digit", getActivity().getLayoutInflater());
                } else if (e_email.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please enter email address", getActivity().getLayoutInflater());
                    //  Toast.makeText(Business_Registration.this,"Please enter email address",Toast.LENGTH_LONG).show();
                } else if (validateEmail1(e_email.getText().toString()) != true) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please enter a valid email address", getActivity().getLayoutInflater());
                }
                else if (e_msg.getText().toString().trim().equalsIgnoreCase("")) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please enter message", getActivity().getLayoutInflater());
                }
                else {

                    s_fname = e_fname.getText().toString().trim();
                    s_lname = e_lname.getText().toString().trim();
                    s_email = e_email.getText().toString().trim();
                    s_mobile = e_mobile.getText().toString().trim();
                    s_msg = e_msg.getText().toString().trim();

                    new Send_coenquiry_data().execute();

                }
            }
        });


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

    public class Send_coenquiry_data extends AsyncTask<Void,Void,String>
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
                parameters.add(new BasicNameValuePair("first_name", s_fname));
                parameters.add(new BasicNameValuePair("last_name", s_lname));
                parameters.add(new BasicNameValuePair("email", s_email));
                parameters.add(new BasicNameValuePair("contact_no", s_mobile));
                parameters.add(new BasicNameValuePair("message", s_msg));

                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"CorporateOffice/App_AddCorporateOffice",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
               // System.out.println("error1: " + e.toString());

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