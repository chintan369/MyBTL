package com.agraeta.user.btl;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Customer_Complains extends Fragment {

    Spinner sp_city, sp_state, sp_dcity, sp_dstate;
    Button btn_date, btn_submit, btn_photo,btn_invoice;
    EditText e_fname, e_lname, e_email, e_mobile, e_purchasebillno, e_dname, e_dadd, e_compalain;
    TextView txt_date, txt_photo,txt_invoice;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    String fd = new String();
    ArrayList<String> product_array = new ArrayList<String>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    HttpFileUpload httpupload;
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();

    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();

    ArrayList<String> dcityid = new ArrayList<String>();
    ArrayList<String> dcityname = new ArrayList<String>();

    ArrayList<String> dstateid = new ArrayList<String>();
    ArrayList<String> dstatename = new ArrayList<String>();
    AutoCompleteTextView autocompletetextview;

    int kk = 0;

    String s_fname = new String();
    String s_lname = new String();
    String s_email = new String();
    String s_mobile = new String();
    String s_purchsebillno = new String();
    String s_dname = new String();
    String s_dadd = new String();
    String s_complain = new String();
    String s_date = new String();
    String s_photo = new String();
    String position_state = new String();
    String position_city = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String position_dstate = new String();
    String position_dcity = new String();
    String position_dstatename = new String();
    String position_dcityname = new String();
    String position_productname = new String();
    String s_productname = new String();
    String curFileName = new String();
    String curFileName_invoice = new String();
    String FilePathsend = new String();
    String FileNamesend = new String();

    String FilePathsend1 = new String();
    String FileNamesend1 = new String();

    String strTempPath = new String();
    String Image1 = new String();
    String path1 = new String();
    String datauser = new String();
    String wa = new String();
    RadioGroup warrenty;

    RadioButton iw,ow;

    String smobile = new String();
    final static int RQS_GET_IMAGE = 1;
    final static int RQS_GET_INVOICE = 2;


    public static final int RESULT_CANCELED = 0;
    /**
     * Standard activity result: operation succeeded.
     */
    public static final int RESULT_OK = -1;
    /**
     * Start of user-defined activity results.
     */
    public static final int RESULT_FIRST_USER = 1;

    Custom_ProgressDialog loadingView;

    String json = new String();

    DatabaseHandler db;

    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_customer__complains, container, false);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        fetchId();


        return rootView;
    }

    private void fetchId() {

        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();
        dstateid.clear();
        dstatename.clear();
        dcityid.clear();
        dcityname.clear();

        btn_date = (Button) rootView.findViewById(R.id.btn_date);
        txt_date = (TextView) rootView.findViewById(R.id.txt_date);
        btn_photo = (Button) rootView.findViewById(R.id.btn_upload_photo);
        txt_photo = (TextView) rootView.findViewById(R.id.txt_photo);
        btn_submit = (Button) rootView.findViewById(R.id.btn_Cust_com_sub);

        btn_invoice = (Button) rootView.findViewById(R.id.btn_upload_invoice);
        txt_invoice = (TextView) rootView.findViewById(R.id.txt_invoice);

        autocompletetextview = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView1);
        autocompletetextview.setThreshold(2);
        sp_city = (Spinner) rootView.findViewById(R.id.spn_select_city);
        sp_dcity = (Spinner) rootView.findViewById(R.id.spn_select_Dcity);
        sp_state = (Spinner) rootView.findViewById(R.id.spn_select_state);
        sp_dstate = (Spinner) rootView.findViewById(R.id.spn_select_Dstate);


        e_fname = (EditText) rootView.findViewById(R.id.etfirst_name_Cust_com);
        e_lname = (EditText) rootView.findViewById(R.id.etlast_name_Cust_com);
        e_email = (EditText) rootView.findViewById(R.id.Et_Emailid);
        e_mobile = (EditText) rootView.findViewById(R.id.etmobile);

        warrenty=(RadioGroup)rootView.findViewById(R.id.rd_warrenty);
        wa = "2";

        iw=(RadioButton)rootView.findViewById(R.id.radio_inw);
        ow=(RadioButton)rootView.findViewById(R.id.radio_outw);

        warrenty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_inw) {
                    wa = "0";

                } else if (checkedId == R.id.radio_outw) {
                    wa = "1";
                } else {
                    wa = "2";
                }
            }
        });


        btn_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  dialog.show();

                Intent intent1 = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intent1, RQS_GET_INVOICE);
            }
        });

        e_mobile.setText("+91-");
        Selection.setSelection(e_mobile.getText(), e_mobile.getText().length());

        e_purchasebillno = (EditText) rootView.findViewById(R.id.e_cust_pbillno);
        e_dname = (EditText) rootView.findViewById(R.id.e_dealername);
        e_dadd = (EditText) rootView.findViewById(R.id.e_dadd);
        e_compalain = (EditText) rootView.findViewById(R.id.et_reson_for_complaint);

        setRefershData();
        if (user_data.size() == 0) {

        } else {
            for (int i = 0; i < user_data.size(); i++) {

                e_fname.setText(user_data.get(i).getF_name());
                e_lname.setText(user_data.get(i).getL_name());
                e_mobile.setText("+91-"+user_data.get(i).getPhone_no());
                ////Log.e("112121",user_data.get(i).getPhone_no());
                e_email.setText(user_data.get(i).getEmail_id());

                kk = 1;

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
                    s_productname = autocompletetextview.getText().toString().trim();
                    new get_produt_data().execute();
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


        sp_dstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_dstate = dstateid.get(position);
                position_dstatename = dstatename.get(position);
                if (position_dstate.equalsIgnoreCase("0")) {

                } else {
                    new send_dcity_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        sp_dcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_dcity = dcityid.get(position);
                position_dcityname = dcityname.get(position);
                if (position_dcity.equalsIgnoreCase("0")) {
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




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smobile = e_mobile.getText().toString().trim().replace("+91-","");


                //Log.e("ssssss",""+smobile);


                if (e_fname.getText().toString().trim().equalsIgnoreCase("")) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please Enter First Name", getActivity().getLayoutInflater());
                    e_fname.requestFocus();
                }else if (e_fname.length() < 2) {

                    // Toast.makeText(Business_Registration.this,"Please enter first name",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 2 character", getActivity().getLayoutInflater());
                    e_fname.requestFocus();
                } else if (e_lname.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please Enter Last Name", getActivity().getLayoutInflater());
                    e_lname.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                }  else if (e_lname.length() < 2) {
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 2 Character", getActivity().getLayoutInflater());
                    e_lname.requestFocus();
                    // Toast.makeText(Business_Registration.this,"Please enter last name",Toast.LENGTH_LONG).show();
                } else if (e_email.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please enter email address", getActivity().getLayoutInflater());
                    e_email.requestFocus();
                    //  Toast.makeText(Business_Registration.this,"Please enter email address",Toast.LENGTH_LONG).show();
                } else if (validateEmail1(e_email.getText().toString()) != true) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid email address",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(getActivity(), "Please enter a valid email address", getActivity().getLayoutInflater());
                    e_email.requestFocus();
                }else if (e_mobile.length() < 14 && !smobile.equalsIgnoreCase("")) {
                    // Toast.makeText(Business_Registration.this,"Please enter valid mobile no",Toast.LENGTH_LONG).show();
                    e_mobile.requestFocus();
                    Globals.CustomToast(getActivity(), "Mobile Number is Compulsory 10 Digit", getActivity().getLayoutInflater());
                } else if (position_state.equalsIgnoreCase("0")) {
                    Globals.CustomToast(getActivity(), "Please Select State", getActivity().getLayoutInflater());
                    sp_state.requestFocus();
                } else if (position_city.equalsIgnoreCase("0")) {
                    Globals.CustomToast(getActivity(), "Please Select city", getActivity().getLayoutInflater());
                    sp_city.requestFocus();
                }     /* else if (curFileName.toString().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please Select Product Image", getActivity().getLayoutInflater());
                    txt_photo.requestFocus();
                } */else if (autocompletetextview.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please Enter Product Name", getActivity().getLayoutInflater());
                    autocompletetextview.requestFocus();
                }/*else if (e_purchasebillno.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please Enter Purchase Bill No", getActivity().getLayoutInflater());
                } else if (txt_date.getText().toString().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please Enter Purchase Date", getActivity().getLayoutInflater());
                } else if (e_dname.getText().toString().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please Enter Dealer Name", getActivity().getLayoutInflater());
                }else if (e_dadd.getText().toString().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please Enter Dealer Address", getActivity().getLayoutInflater());
                }else if (position_dstate.equalsIgnoreCase("0")) {
                    Globals.CustomToast(getActivity(), "Please Select Dealer State", getActivity().getLayoutInflater());
                }else if (position_dcity.equalsIgnoreCase("0")) {
                    Globals.CustomToast(getActivity(), "Please Select Dealer City", getActivity().getLayoutInflater());
                }*/else if (e_compalain.getText().toString().equalsIgnoreCase("")) {
                    Globals.CustomToast(getActivity(), "Please Enter Your Reason For Complaint", getActivity().getLayoutInflater());
                    e_compalain.requestFocus();
                }else if (e_compalain.length() < 10) {
                    Globals.CustomToast(getActivity(), "Please Enter Atleast 10 Character", getActivity().getLayoutInflater());
                    e_compalain.requestFocus();
                }else {

                   /* String date = txt_date.getText().toString();
                    SimpleDateFormat input = new SimpleDateFormat("MM-dd-yyyy");
                    SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date oneWayTripDate = input.parse(date);             // parse input
                        s_date = output.format(oneWayTripDate);

                        txt_date.setText(""+s_date);

                        //Log.e("DAte", "" + s_date);

                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }*/
                    s_fname = e_fname.getText().toString().trim();
                    s_lname = e_lname.getText().toString().trim();
                    s_email =  e_email.getText().toString().trim();
                    s_mobile = e_mobile.getText().toString().trim().replace("+91-","");
                    position_productname =  autocompletetextview.getText().toString().trim();
                  /*  s_purchsebillno =  e_purchasebillno.getText().toString().trim();
                    s_date =  txt_date.getText().toString().trim();
                    s_dname =  e_dname.getText().toString().trim();
                    s_dadd =  e_dadd.getText().toString().trim();*/
                    s_complain =  e_compalain.getText().toString().trim();




                    try {

                        JSONArray jObjectMain1 = new JSONArray();


                        for (int ij = 1;ij == 1;ij++) {
                            JSONObject jObjectData1 = new JSONObject();
                            jObjectData1.put("first_name", s_fname);
                            jObjectData1.put("last_name", s_lname);
                            jObjectData1.put("email", s_email);
                            jObjectData1.put("contact_no", s_mobile);
                            jObjectData1.put("city_name", position_cityname);
                            jObjectData1.put("product_name", position_productname);
                            jObjectData1.put("reason", s_complain);

                            jObjectMain1.put(jObjectData1);
                            datauser = jObjectMain1.toString();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(""+datauser);
                    File file = new File(FilePathsend, FileNamesend);
                    FileInputStream is;

                    File file1 = new File(FilePathsend1, FileNamesend1);
                    FileInputStream is1;
                    if(wa.equalsIgnoreCase("2")){
                        wa = "";
                    }
                    try {
                       is = new FileInputStream(file);
                     //   is1 = new FileInputStream(file1);
                       // httpupload = new HttpFileUpload("http://app.nivida.in/boss/Product/App_SearchProductName", curFileName.toString(),curFileName.toString(), datauser);
                        httpupload = new HttpFileUpload(Globals.server_link+"CustomerComplaint/App_AddCustomerComplaint",s_fname,s_lname,s_email,s_mobile,curFileName.toString(),position_productname,position_statename,position_cityname,curFileName_invoice.toString(),s_complain,wa);

                       httpupload.Send_Now(is);
                       // httpupload.Send_Now(is1);
                        Globals.CustomToast(getActivity(), "Your Complains are successfully send.", getActivity().getLayoutInflater());
                        Intent i = new Intent(getActivity(), MainPage_drawer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


					/**/
                }

                   // new send_custcomplain_data().execute();



            }
        });

        final String[] items = new String[]{"Take from camera",
                "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, 11);
                } else { // pick from file
                    Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(in, 22);
                }
            }
        });

        final AlertDialog dialog = builder.create();
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  dialog.show();

                Intent intent1 = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intent1,RQS_GET_IMAGE);
            }
        });


    }

    public class send_dstate_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
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
                    Globals.CustomToast(getActivity(), "SERVER ERROR", getActivity().getLayoutInflater());
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
                            dstatename.add("Select State");
                            dstateid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                dstatename.add(sname);
                                dstateid.add(sId);


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dstatename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_dstate.setAdapter(adapter_state);

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

    public class send_dcity_Data extends AsyncTask<Void, Void, String> {
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

                parameters.add(new BasicNameValuePair("state_id", position_dstate));


                //Log.e("", "" + parameters);

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
                        dcityname.clear();
                        dcityid.clear();
                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            dcityname.add("Select City");
                            dcityid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                dcityname.add(cname);
                                dcityid.add(cId);


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dcityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_dcity.setAdapter(adapter_city);

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

    public class send_state_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
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


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_state.setAdapter(adapter_state);

                            }
                        }
                        loadingView.dismiss();

                        new send_dstate_Data().execute();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class send_city_Data extends AsyncTask<Void, Void, String> {
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


                //Log.e("", "" + parameters);

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


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                sp_city.setAdapter(adapter_city);

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

    public class get_produt_data extends AsyncTask<Void, Void, String> {
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

                parameters.add(new BasicNameValuePair("product_name", s_productname));


                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Product/App_SearchProductName", ServiceHandler.POST, parameters);
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
                        product_array.clear();

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");

                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);

                                String cname = catObj.getString("product_name");
                                //Log.e("cname:::", "" + cname);
                                product_array.add(cname);

                            }
                            //Log.e("Size:::", "" + product_array.size());
                            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (getActivity(),android.R.layout.select_dialog_item, product_array);*/

                            ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, product_array);
                            adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            //autocompletetextview.setThreshold(3);
                            autocompletetextview.setAdapter(adapter_city);
                            autocompletetextview.showDropDown();

                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
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

   /* public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {

        } else {

            if (resultCode == RESULT_OK) {

                switch (requestCode) {

                    case 11:


                        final Bitmap photo = (Bitmap) data.getExtras().get("data");
                        Thread t = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                try {
                                    //strTimeinMills = ""+System.currentTimeMillis();
                                    strTempPath = System.currentTimeMillis() + ".png";
                                    photo.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + strTempPath));
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }
                        });

                        t.start();
                        try {
                            t.join();
                        } catch (Exception e) {

                        }

                        String p = new File(Environment.getExternalStorageDirectory(), strTempPath).getAbsolutePath();
                        //System.out.println("-------- Image Path is : " + p);
                        String filename=p.substring(p.lastIndexOf("/")+1);
                        txt_photo.setText(""+filename);
                        Bitmap bm = BitmapFactory.decodeFile(new File(p).getAbsolutePath());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        Image1 = Base64.encodeToString(b, Base64.DEFAULT);

                        //System.out.println("Image1 :----------" + Image1);

                        break;

                    case 22:
                        Uri selectedImageUri = data.getData();


                        InputStream in = null;

                        try {
                            in = getActivity().getContentResolver().openInputStream(selectedImageUri);
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        Bitmap thumb = BitmapFactory.decodeStream(in, null, options);


                        String p2 = getPath(selectedImageUri);
                        //System.out.println("-------- Image Path is : " + p2);
                        String filename1=p2.substring(p2.lastIndexOf("/")+1);
                        txt_photo.setText(""+filename1);

                        //imgPrescription1.setImageBitmap(thumb);
                        path1 = new File(p2).getAbsolutePath();

                        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                        thumb.compress(Bitmap.CompressFormat.PNG, 100, baos1); //bm is the bitmap object
                        byte[] b1 = baos1.toByteArray();
                        Image1 = Base64.encodeToString(b1, Base64.DEFAULT);
                        //System.out.println("Image1 :----------" + Image1);


                        break;

                }


            }
        }
    }*/

    @SuppressWarnings("deprecation")
    private String getPath(Uri selectedImageUri) {

        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

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

    public class send_custcomplain_data extends AsyncTask<Void,Void,String>
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
                parameters.add(new BasicNameValuePair("city_name", position_cityname));
                parameters.add(new BasicNameValuePair("product_name", position_productname));
               /* parameters.add(new BasicNameValuePair("upload_photo", Image1));
                parameters.add(new BasicNameValuePair("upload_invoice", Image1));*/
                parameters.add(new BasicNameValuePair("reason", s_complain));



                //Log.e("4",""+parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall("http://app.nivida.in/boss/CustomerComplaint/App_AddCustomerComplaint",ServiceHandler.POST,parameters);
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
                }/*else if(extension.equalsIgnoreCase("jpeg")){
                    txt_photo.setText("File name : "+curFileName);
                }*/
                else{
                    txt_photo.setText("");
                    //Globals.CustomToast(getActivity(), "Please Select Pdf File", getActivity().getLayoutInflater());
                }



            }
        }else if (requestCode == RQS_GET_INVOICE){
            if (resultCode == RESULT_OK) {
                curFileName_invoice = data.getStringExtra("GetFileName");
                //Log.e("1", curFileName_invoice.toString());
                String filename = curFileName_invoice;
                String filePath = data.getStringExtra("GetPath");
                FilePathsend1 = filePath;
                FileNamesend1 = filename;
                String filenameArray[] = filename.split("\\.");
                String extension = filenameArray[filenameArray.length-1];

                if(extension.equalsIgnoreCase("pdf")){
                    txt_invoice.setText("File name : "+curFileName_invoice);
                }else if(extension.equalsIgnoreCase("doc")){
                    txt_invoice.setText("File name : "+curFileName_invoice);
                }else if(extension.equalsIgnoreCase("docx")){
                    txt_invoice.setText("File name : "+curFileName_invoice);
                }/*else if(extension.equalsIgnoreCase("txt")){
                    txt_photo.setText("File name : "+curFileName);
                }else if(extension.equalsIgnoreCase("rtf")){
                    txt_photo.setText("File name : "+curFileName);
                }*/else if(extension.equalsIgnoreCase("png")){
                    txt_invoice.setText("File name : "+curFileName_invoice);
                }else if(extension.equalsIgnoreCase("jpg")){
                    txt_invoice.setText("File name : "+curFileName_invoice);
                }/*else if(extension.equalsIgnoreCase("jpeg")){
                    txt_photo.setText("File name : "+curFileName);
                }*/
                else{
                    txt_invoice.setText("");
                    //Globals.CustomToast(getActivity(), "Please Select Pdf File", getActivity().getLayoutInflater());
                }



            }
        }
    }
}