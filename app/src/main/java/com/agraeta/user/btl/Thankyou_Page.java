package com.agraeta.user.btl;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Thankyou_Page extends AppCompatActivity {
    ArrayList<Bean_BankDetails> bankDetailsList = new ArrayList<Bean_BankDetails>();
    String jsonPerson = "";
    DatabaseHandler db;
    Button btnhome;
    TextView titleName;
    TextView bankName, txt_name11, txt_thankMessage;
    TextView acNo;
    TextView ifsCode;
    TextView branchName;
    TextView branchCode;
    LinearLayout bank_detail;
    String owner_id = "";
    String u_id = "";
    String role_id = "";
    AppPrefs appPrefs;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();

    String thankMessage="";

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
        setContentView(R.layout.activity_thankyou__page);
        btnhome=(Button)findViewById(R.id.btnhome);
        titleName = (TextView) findViewById(R.id.txt_title);
        txt_thankMessage = (TextView) findViewById(R.id.txt_thankMessage);
        bankName = (TextView) findViewById(R.id.txt_bank);
        txt_name11 = (TextView) findViewById(R.id.txt_name11);
        acNo = (TextView) findViewById(R.id.txt_account);
        ifsCode = (TextView)findViewById(R.id.txt_code);
        branchName = (TextView) findViewById(R.id.txt_branch);
        branchCode = (TextView) findViewById(R.id.txt_branchcode);
        bank_detail=(LinearLayout)findViewById(R.id.bank_detail);


        Intent intent=getIntent();
        String orderID=intent.getStringExtra("orderID");
        String orderAmount=intent.getStringExtra("orderAmount");

        thankMessage="We appreciate your order.<br/>Your Order Confirmation number is <b>#"+orderID+"</b><br/>"+
                "Order amount is <b> \u20B9 "+orderAmount+"</b><br/>"+
                "We will update you the despatch details at the earliest.";

        txt_thankMessage.setText(Html.fromHtml(thankMessage));


       // final ImageView cancel = (ImageView) findViewById(R.id.btn_cancel);
        setActionBar();
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db= new DatabaseHandler(Thankyou_Page.this);
              //  db.Delete_user_table();
                db.Clear_ALL_table();
                db.Delete_ALL_table();
                db.clear_cart();
                db.close();
                Intent i = new Intent(Thankyou_Page.this,MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        setRefershData();

        if(user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    appPrefs = new AppPrefs(Thankyou_Page.this);
                    role_id = appPrefs.getSubSalesId().toString();
                    u_id = appPrefs.getSalesPersonId().toString();
                } else {
                    u_id = owner_id;
                }


            }
        }
        if(role_id.equalsIgnoreCase(C.CUSTOMER) || role_id.equalsIgnoreCase(C.CARPENTER)){
            bank_detail.setVisibility(View.GONE);
        }else{
            bank_detail.setVisibility(View.VISIBLE);
            new GetBankInformation().execute();
        }

    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        db= new DatabaseHandler(Thankyou_Page.this);
        db.Delete_ALL_table();
        db.clear_cart();
        db.close();
        Intent i = new Intent(Thankyou_Page.this,MainPage_drawer.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);
        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
       /* TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(AboutUs_Activity.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size() + "");*/
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        img_home.setVisibility(View.GONE);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db= new DatabaseHandler(Thankyou_Page.this);
                db.Delete_ALL_table();
                db.clear_cart();
                db.close();
                Intent i = new Intent(Thankyou_Page.this,MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        /*img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BTL_Cart.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });*//*
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CheckoutPage_Product.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

       *//* img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Notification.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });*/
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Thankyou_Page.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid = user_array_from_db.get(i).getId();
            String user_id = user_array_from_db.get(i).getUser_id();
            //Log.e("",""+user_array_from_db.get(i).getUser_id());

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
            // Toast.makeText(getApplicationContext(),""+user_id,Toast.LENGTH_SHORT).show();
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

    public class GetBankInformation extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        Custom_ProgressDialog loadingView;
        Activity activity;
        //String user_id;
        String jsonData = "";
        private String result;
        private InputStream is;

//        public GetBankInformation(Activity activity,String user_id){
//            this.activity=activity;
//            this.user_id=user_id;;
//        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(activity, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                //parameters.add(new BasicNameValuePair("user_id",user_id));

                ////Log.e("distributor id", "" + distributor_id+"");
               // //Log.e("user id", "" + user_id + "");
                ////Log.e("role id", "" + role_id+"");


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "TallyCompany/App_BankDetails", ServiceHandler.POST, parameters);

                //System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
               // System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            jsonPerson = jsonData;

            if (!jsonData.equalsIgnoreCase("")) {
                try {
                    JSONObject mainObject = new JSONObject(jsonData);
                    if (mainObject.getBoolean("status")) {
                        JSONArray dataArray = mainObject.getJSONArray("data");

                        bankDetailsList.clear();

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject subObject = dataArray.getJSONObject(i);
                            //JSONObject orderReason = subObject.getJSONObject("OrderReason");

                            Bean_BankDetails bank_PERSON = new Bean_BankDetails();

                            //bank_PERSON.setTitleLine(subObject.getString("id"));
                            bank_PERSON.setBankName(subObject.getString("bank_name"));
                            //Log.e("BANK NAME", "" + subObject.getString("bank_name"));
                            bank_PERSON.setAccountNo(subObject.getString("account_no"));
                            //Log.e("ACCOUNT NO", "" + subObject.getString("account_no"));
                            bank_PERSON.setIfsCode(subObject.getString("ifs_code"));
                            //Log.e("IFS CODE", "" + subObject.getString("ifs_code"));
                            bank_PERSON.setBranchName(subObject.getString("branch"));
                            //Log.e("BRANCH", "" + subObject.getString("branch"));
                            bank_PERSON.setBranchCode(subObject.getString("account_name"));
                            //Log.e("BRANCH CODE", "" + subObject.getString("branch_code"));

                            //titleName.setText("Your order has been received and will be processed once payment has been confirmed. ");




                            bankDetailsList.add(bank_PERSON);
                        }

                        txt_name11.setText(bankDetailsList.get(0).getBranchCode());
                        bankName.setText(bankDetailsList.get(0).getBankName());
                        acNo.setText(bankDetailsList.get(0).getAccountNo());
                        ifsCode.setText(bankDetailsList.get(0).getIfsCode());
                        branchName.setText(bankDetailsList.get(0).getBranchName());
                        branchCode.setText(bankDetailsList.get(0).getBranchCode());

                        //bankName.setText(bankDetailsList.get(0).getBranchName());

                        //Log.e("Size", bankDetailsList.size() + "");
                    } else {

                        Globals.CustomToast(Thankyou_Page.this, "" + mainObject.getString("message").toString(), getLayoutInflater());
                        loadingView.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    //loadingView.dismiss();
                }
            }

            // loadingView.dismiss();
            //showDetailInfoDialog();
        }
    }

}

