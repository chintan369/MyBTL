package com.agraeta.user.btl;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agraeta.user.btl.model.sales.SalesSubUserDataSingleAddress;
import com.agraeta.user.btl.utils.FilePath;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GSTFormActivity extends AppCompatActivity {

    AppPrefs prefs;
    DatabaseHandler db;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    LinearLayout layout_ARNCertificate, layout_GSTCertificate;
    ImageView img_ARNCertificate, img_GSTCertificate, img_removeGST, img_removeARN;
    TextView txt_ARNCertificate, txt_GSTCertificate;
    Spinner spn_supply;
    Custom_ProgressDialog dialog;

    String userID = "";
    String roleID = "";


    EditText edt_tax_payer, edt_legal_name, edt_add1, edt_add2, edt_add3, edt_email, edt_mobile, edt_city, edt_area, edt_pincode, edt_pan, edt_gstuin, edt_date, edt_arnNo, edt_datearn, edt_bussinesstype, edt_comment;

    Button btn_submit, btn_gst_certi, btn_arn_certi;

    Spinner spn_state, spn_gst;

    int mYear, mMonth, mDay;

    DatePickerDialog datePickerDialog;

    String fromDate = "";

    int FILE_PICK_CODE = 1;
    int PICK_CODE = 2;

    String gstImagePathSelected = "";
    String arnImagePathSelected = "";

    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    ArrayList<String> stateCode = new ArrayList<String>();
    ArrayAdapter<String> adapter_state;
    String position_state_selected = "";


    String taxPayerName = "", taxPayerLegalName = "", address1 = "", address2 = "", address3 = " ", emailId = "", mobileNo = "", cityName = "", areaName = "", pincodNo = "", panNo = "", gstNo = "", gstDate = "", arnDate = "", arnNo = "",
            bussinessType = "", comment = "", state = "", gstType = "", uploadGst = "", uploadArn = "", supplyType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gstform);

        dialog = new Custom_ProgressDialog(this, "");
        dialog.setCancelable(false);

        prefs = new AppPrefs(getApplicationContext());
        db = new DatabaseHandler(this);

        setActionBar();
        fetchIDs();

        setRefershData();

        Log.e("size", "" + user_data.size());

        if (user_data.size() > 0) {
            userID = user_data.get(0).getUser_id();
            roleID = user_data.get(0).getUser_type();

            Log.e("userID", "" + userID);
            Log.e("roleID", "" + roleID);

            if (roleID.equals(C.COMP_SALES_PERSON) || roleID.equals(C.DISTRIBUTOR_SALES_PERSON)) {
                userID = prefs.getSalesPersonId();
                Log.e("userId", "" + userID);
            }

            new GetUserInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userID);
        } else {
            new GetState("").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

    }

    private void fetchIDs() {

        edt_tax_payer = (EditText) findViewById(R.id.edt_tax_payer);
        edt_legal_name = (EditText) findViewById(R.id.edt_legal_name);
        edt_add1 = (EditText) findViewById(R.id.edt_add1);
        edt_add2 = (EditText) findViewById(R.id.edt_add2);
        edt_add3 = (EditText) findViewById(R.id.edt_add3);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_city = (EditText) findViewById(R.id.edt_city);
        edt_area = (EditText) findViewById(R.id.edt_area);
        edt_pincode = (EditText) findViewById(R.id.edt_pincode);
        edt_pan = (EditText) findViewById(R.id.edt_pan);
        edt_gstuin = (EditText) findViewById(R.id.edt_gstuin);
        edt_date = (EditText) findViewById(R.id.edt_date);
        edt_arnNo = (EditText) findViewById(R.id.edt_arnNo);
        edt_datearn = (EditText) findViewById(R.id.edt_datearn);
        edt_bussinesstype = (EditText) findViewById(R.id.edt_bussinesstype);
        edt_comment = (EditText) findViewById(R.id.edt_comment);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_gst_certi = (Button) findViewById(R.id.btn_gst_certi);
        btn_arn_certi = (Button) findViewById(R.id.btn_arn_certi);

        spn_state = (Spinner) findViewById(R.id.spn_state);
        spn_gst = (Spinner) findViewById(R.id.spn_gst);
        spn_supply = (Spinner) findViewById(R.id.spn_supply);

        img_ARNCertificate = (ImageView) findViewById(R.id.img_ARNCertificate);
        img_GSTCertificate = (ImageView) findViewById(R.id.img_GSTCertificate);
        img_removeGST = (ImageView) findViewById(R.id.img_removeGST);
        img_removeARN = (ImageView) findViewById(R.id.img_removeARN);


        txt_ARNCertificate = (TextView) findViewById(R.id.txt_ARNCertificate);
        txt_GSTCertificate = (TextView) findViewById(R.id.txt_GSTCertificate);

        layout_ARNCertificate = (LinearLayout) findViewById(R.id.layout_ARNCertificate);
        layout_GSTCertificate = (LinearLayout) findViewById(R.id.layout_GSTCertificate);


        adapter_state = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statename);
        spn_state.setAdapter(adapter_state);


        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gstDate();
            }
        });


        edt_datearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arnDate();
            }
        });

        img_removeGST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_GSTCertificate.setText("");
                gstImagePathSelected = "";
                layout_GSTCertificate.setVisibility(View.GONE);
            }
        });

        img_removeARN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_ARNCertificate.setText("");
                arnImagePathSelected = "";
                layout_ARNCertificate.setVisibility(View.GONE);
            }
        });

        btn_gst_certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, FILE_PICK_CODE);

            }
        });


        btn_arn_certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_CODE);


            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taxPayerName = edt_tax_payer.getText().toString().trim();
                String taxPayerLegalName = edt_legal_name.getText().toString().trim();
                String address1 = edt_add1.getText().toString().trim();
                String address2 = edt_add2.getText().toString().trim();
                String address3 = edt_add3.getText().toString().trim();
                String emailId = edt_email.getText().toString().trim();
                String mobileNo = edt_mobile.getText().toString().trim();
                String cityName = edt_city.getText().toString().trim();
                String areaName = edt_area.getText().toString().trim();
                String pincodNo = edt_pincode.getText().toString().trim();
                String panNo = edt_pan.getText().toString().trim();
                String gstNo = edt_gstuin.getText().toString().trim();
                String gstDate = edt_date.getText().toString().trim();
                String arnDate = edt_datearn.getText().toString().trim();
                String arnNo = edt_arnNo.getText().toString().trim();
                String comment = edt_comment.getText().toString().trim();
                String bussinessType = edt_bussinesstype.getText().toString().trim();

                String selectedStateCode = stateCode.get(spn_state.getSelectedItemPosition());
                String uploadGst = gstImagePathSelected;
                String uploadArn = arnImagePathSelected;

                if (taxPayerName.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Tax Payer Name");
                    edt_tax_payer.requestFocus();
                } else if (taxPayerLegalName.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Tax Payer Legal Name");
                    edt_legal_name.requestFocus();
                } else if (address1.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Address Line1");
                    edt_add1.requestFocus();
                } else if (address2.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Address Line2");
                    edt_add2.requestFocus();
                } else if (address3.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Address Line3");
                    edt_add3.requestFocus();
                } else if (cityName.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter City Name");
                    edt_city.requestFocus();
                } else if (pincodNo.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Pincode");
                    edt_pincode.requestFocus();
                } else if (pincodNo.length() < 6) {
                    Globals.Toast2(getApplicationContext(), "Please Enter 6 Digit Pincode");
                    edt_pincode.requestFocus();
                } else if (emailId.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Email ID");
                    edt_email.requestFocus();
                } else if (!C.validEmail(emailId)) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Valid Email ID");
                    edt_email.requestFocus();
                } else if (mobileNo.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Contact No.");
                    edt_mobile.requestFocus();
                } else if (mobileNo.length() < 10) {
                    Globals.Toast2(getApplicationContext(), "Please Enter 10 Digit Contact No.");
                    edt_mobile.requestFocus();
                } else if (spn_state.getSelectedItemPosition() == 0) {
                    Globals.Toast2(getApplicationContext(), "Please Select State");
                    spn_state.requestFocus();
                } else if (panNo.isEmpty() || !C.isValidPAN(panNo)) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Valid PAN No.");
                    edt_pan.requestFocus();
                } else if (gstNo.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter GST No.");
                    edt_gstuin.requestFocus();
                } else if (!isValidGST(selectedStateCode, gstNo, panNo)) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Valid GST No.");
                } else if (spn_gst.getSelectedItemPosition() == 0) {
                    Globals.Toast2(getApplicationContext(), "Please Select GST Type");
                    spn_gst.requestFocus();
                } else if (gstDate.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter GST Issue Date");
                    edt_date.requestFocus();
                } else if (!arnNo.isEmpty() && !C.isAlphaNumeric15Digit(arnNo)) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Valid ARN No.");
                    edt_arnNo.requestFocus();
                } else if (bussinessType.isEmpty()) {
                    Globals.Toast2(getApplicationContext(), "Please Enter Business Type");
                    edt_bussinesstype.requestFocus();
                } else if (spn_supply.getSelectedItemPosition() == 0) {
                    Globals.Toast2(getApplicationContext(), "Please Select Supply Type");
                    spn_supply.requestFocus();
                } else {
                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                    parameters.add(new BasicNameValuePair("tax_payer", taxPayerName));
                    parameters.add(new BasicNameValuePair("tax_payer_legal_name", taxPayerLegalName));
                    parameters.add(new BasicNameValuePair("address1", address1));
                    parameters.add(new BasicNameValuePair("address2", address2));
                    parameters.add(new BasicNameValuePair("address3", address3));
                    parameters.add(new BasicNameValuePair("area_id", areaName));
                    parameters.add(new BasicNameValuePair("city_id", cityName));
                    parameters.add(new BasicNameValuePair("pincode", pincodNo));
                    parameters.add(new BasicNameValuePair("email_id", emailId));
                    parameters.add(new BasicNameValuePair("contact_no", mobileNo));
                    parameters.add(new BasicNameValuePair("state_id", stateid.get(spn_state.getSelectedItemPosition())));
                    parameters.add(new BasicNameValuePair("company_pan", panNo));
                    parameters.add(new BasicNameValuePair("gstin_uin", gstNo));
                    parameters.add(new BasicNameValuePair("gst_type", spn_gst.getSelectedItem().toString()));
                    parameters.add(new BasicNameValuePair("gst_date_of_issue", gstDate));
                    parameters.add(new BasicNameValuePair("arn_date_of_issue", arnDate));
                    parameters.add(new BasicNameValuePair("arn_num", arnNo));
                    parameters.add(new BasicNameValuePair("upload_gst_certificate", uploadGst));
                    parameters.add(new BasicNameValuePair("upload_arn_certificate", uploadArn));
                    parameters.add(new BasicNameValuePair("type_of_business", bussinessType));
                    parameters.add(new BasicNameValuePair("type_of_supply", spn_supply.getSelectedItem().toString()));
                    parameters.add(new BasicNameValuePair("comments", comment));

                    if (user_data.size() > 0) {
                        parameters.add(new BasicNameValuePair("user_id", userID));
                    }

                    new SendGSTDetails(parameters).execute();


                }


            }
        });
    }

    private void arnDate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        fromDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;


                        edt_datearn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                    }
                    //
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();


    }

    private void gstDate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        fromDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;


                        edt_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                    }
                    //
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();


    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(this);

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

    private void setUserDataFromLive(SalesSubUserDataSingleAddress currentUserData) {


        if (currentUserData.getDistributor().getGstin_uin() != null && !currentUserData.getDistributor().getGstin_uin().isEmpty()) {
            Globals.Toast2(getApplicationContext(), "You have already filled GST Form!");
            onBackPressed();
            return;
        }

        edt_tax_payer.setText(currentUserData.getDistributor().getFirm_name());
        edt_tax_payer.setEnabled(false);
        edt_legal_name.setText(currentUserData.getDistributor().getFirm_name());
        edt_add1.setText(currentUserData.getAddress().getAddress_1());
        edt_add1.setEnabled(false);
        edt_add2.setText(currentUserData.getAddress().getAddress_2());
        edt_add2.setEnabled(false);
        edt_add3.setText(currentUserData.getAddress().getAddress_3());
        edt_add3.setEnabled(false);
        edt_email.setText(currentUserData.getUser().getEmail_id());
        edt_email.setEnabled(false);
        edt_mobile.setText(currentUserData.getUser().getPhone_no());
        edt_mobile.setEnabled(false);
        edt_pan.setText(currentUserData.getDistributor().getTally_pan_no());
        edt_city.setText(currentUserData.getAddress().getCity().getName());
        edt_city.setEnabled(false);
        edt_area.setText(currentUserData.getAddress().getArea().getName());
        edt_area.setEnabled(false);
        edt_pincode.setText(currentUserData.getAddress().getPincode());
        edt_pincode.setEnabled(false);

        spn_state.setEnabled(false);

        new GetState(currentUserData.getAddress().getState_id()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private boolean isValidGST(String currentStateID, String GSTNo, String PANNo) {
        int length = GSTNo.length();

        if (PANNo.isEmpty() || !C.isValidPAN(PANNo)) {
            return false;
        }

        if (length < 15) {
            return false;
        }

        String stateCodeEntered = GSTNo.substring(0, 2);
        if (!currentStateID.equals(stateCodeEntered)) {
            return false;
        }

        String panNOEntered = GSTNo.substring(2, 12);
        if (!panNOEntered.equals(PANNo)) {
            return false;
        }

        String thirteenDigit = GSTNo.substring(12, 13);

        Pattern pattern = Pattern.compile("[A-Z1-9]{1}");

        Matcher matcher = pattern.matcher(thirteenDigit);

        if (!matcher.matches()) {
            return false;
        }

        String fourteenDigit = GSTNo.substring(13, 14);
        if (!fourteenDigit.equals("Z")) {
            return false;
        }

        String fifteenthDigit = GSTNo.substring(14, 15);

        Pattern patternL = Pattern.compile("[A-Z0-9]{1}");

        Matcher matcherL = patternL.matcher(fifteenthDigit);

        return matcherL.matches();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {


            if (requestCode == FILE_PICK_CODE) {
                if (data.getData() != null) {
                    Uri dataUri = data.getData();

                    Log.e("URI", dataUri.toString());

                    gstImagePathSelected = FilePath.getPath(this, dataUri);

                    txt_GSTCertificate.setText(gstImagePathSelected);


                    //noinspection ConstantConditions
                    Picasso.with(this)
                            .load(new File(gstImagePathSelected))
                            .into(img_GSTCertificate);

                    layout_GSTCertificate.setVisibility(View.VISIBLE);


                } else {
                    Toast.makeText(this, "Sorry, File not Found!", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == PICK_CODE) {


                Uri dataUri = data.getData();

                Log.e("URI", dataUri.toString());

                arnImagePathSelected = FilePath.getPath(this, dataUri);

                txt_ARNCertificate.setText(arnImagePathSelected);


                //noinspection ConstantConditions
                Picasso.with(this)
                        .load(new File(arnImagePathSelected))
                        .into(img_ARNCertificate);

                layout_ARNCertificate.setVisibility(View.VISIBLE);


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
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        img_notification.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_home.setVisibility(View.GONE);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public class GetUserInfo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> pairList = new ArrayList<>();
            pairList.add(new BasicNameValuePair("user_id", params[0]));

            String json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/APP_User_Info", ServiceHandler.POST, pairList);


            Log.e("para", "" + pairList);
            Log.e("json", "" + json);

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            if (s != null && !s.isEmpty()) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getBoolean("status")) {
                        SalesSubUserDataSingleAddress currentUserData = new Gson().fromJson(object.getString("data"), SalesSubUserDataSingleAddress.class);
                        setUserDataFromLive(currentUserData);
                    }
                } catch (Exception e) {

                }
            }


        }
    }

    public class GetState extends AsyncTask<String, Void, String> {

        String userStateID = "";

        public GetState(String userStateID) {
            this.userStateID = userStateID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        @Override
        protected String doInBackground(String... params) {


            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("country_id", "1"));

            String json = new ServiceHandler().makeServiceCall(Globals.server_link + "State/App_GetState", ServiceHandler.POST, parameters);


            Log.e("parameters", "" + parameters);


            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();
            statename.add("Select State");
            stateid.add("0");
            stateCode.add("0");

            if (s != null && !s.isEmpty()) {


                try {
                    JSONObject mainObject = new JSONObject(s);


                    if (mainObject.getBoolean("status")) {

                        JSONArray dataArray = mainObject.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject object = dataArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String code = object.getString("state_code");


                            stateid.add(id);
                            statename.add(name);
                            stateCode.add(code);


                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter_state.notifyDataSetChanged();

                if (user_data.size() > 0) {
                    for (int i = 0; i < stateid.size(); i++) {
                        if (userStateID.equals(stateid.get(i))) {
                            spn_state.setSelection(i);
                            break;
                        }
                    }
                }
            } else {
                Globals.Toast2(getApplicationContext(), "Server Error Occurred\nPlease Try After Sometime");
            }
        }
    }

    public class SendGSTDetails extends AsyncTask<String, Void, String> {

        List<NameValuePair> parameters = new ArrayList<>();

        public SendGSTDetails(List<NameValuePair> params) {
            this.parameters = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }


        @Override
        protected String doInBackground(String... params) {


            String json = new ServiceHandler().makeServiceCall(Globals.server_link + "GstForm/App_Add_GST_Details", ServiceHandler.POST, parameters, 1);

            Log.e("parameters", "" + parameters);
            Log.e("json", "---" + json);


            return json;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();


            if (s != null && !s.isEmpty()) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getBoolean("status")) {
                        Globals.Toast2(getApplicationContext(), object.getString("message"));
                        onBackPressed();
                    } else {
                        Globals.Toast2(getApplicationContext(), object.getString("message"));
                    }

                } catch (Exception e) {

                }
            } else {
                Globals.Toast2(getApplicationContext(), "Server Error Occurred\nPlease Try After Sometime");
            }
        }
    }
}


