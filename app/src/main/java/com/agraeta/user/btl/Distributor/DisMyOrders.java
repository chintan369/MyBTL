package com.agraeta.user.btl.Distributor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.BTL_Cart;
import com.agraeta.user.btl.Bean_User_data;
import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.Main_CategoryPage;
import com.agraeta.user.btl.Notification;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;
import com.agraeta.user.btl.User_Profile;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DisMyOrders extends AppCompatActivity {
    LinearLayout l_myorderlist,l_oustandingbills,l_ledgerbills;
    AppPrefs app;
    List<Bean_Balance> balanceList=new ArrayList<>();
    TextView at,dt,ar,dr;
    String userID;
    String jsonBill="";
    String back;
    String owner_id = new String();
    String u_id = new String();
    String role_id = new String();
    DatabaseHandler db;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    String cartJSON="";
    boolean hasCartCallFinish=true;

    TextView txt;
    LinearLayout layout_main;


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
        setContentView(R.layout.activity_dis_my_orders);
        layout_main = (LinearLayout) findViewById(R.id.layout_main);
        back = getIntent().getStringExtra("BACK");

        fetchid();

        setActionBar();
        app=new AppPrefs(getApplicationContext());
        app.setCurrentPage("OrderList2");
        userID=app.getUserId();

        if (app.getUserRoleId().equals(C.ADMIN) || app.getUserRoleId().equals(C.COMP_SALES_PERSON) || app.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON)) {
            userID = app.getSalesPersonId();
            Snackbar.make(layout_main, "Taking Order for : " + app.getUserName(), Snackbar.LENGTH_LONG).show();
        }

       /* setRefershData();



        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                userID =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    app = new AppPrefs(DisMyOrders.this);
                    role_id = app.getSubSalesId().toString();
                    userID = app.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    app = new AppPrefs(DisMyOrders.this);
                    role_id = app.getSubSalesId().toString();
                    userID = app.getSalesPersonId().toString();
                    //Log.e("IDIDD",""+app.getSalesPersonId().toString());
                }


            }

        }else{
            userID ="";
        }*/


        new GetBillData(this,userID).execute();

    }
    private void fetchid() {

        at=(TextView)findViewById(R.id.outstanding_txt) ;
        ar=(TextView)findViewById(R.id.outstanding_rs) ;
        dt=(TextView)findViewById(R.id.due_txt) ;
        dr=(TextView)findViewById(R.id.due_rs) ;



        l_myorderlist=(LinearLayout)findViewById(R.id.l_myorderlist);
        l_oustandingbills=(LinearLayout)findViewById(R.id.l_outstandingbills);
        l_ledgerbills=(LinearLayout)findViewById(R.id.l_ledgerbills);

        l_ledgerbills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisMyOrders.this, LegderListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        l_oustandingbills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisMyOrders.this, OutstandingListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        l_myorderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisMyOrders.this, D_OrderHistory.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                app.setCurrentPage("OrderList2");
                startActivity(i);
                finish();
            }
        });

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

        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);


        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        frame.setVisibility(View.VISIBLE);
         txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        app = new AppPrefs(DisMyOrders.this);
        String qun =app.getCart_QTy();

        setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    app = new AppPrefs(DisMyOrders.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    app = new AppPrefs(DisMyOrders.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                    //Log.e("IDIDD",""+app.getSalesPersonId().toString());
                }else{
                    u_id=owner_id;
                }


            }

            List<NameValuePair> para=new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id",u_id));

            new GetCartByQty(para).execute();



        }else{
            app = new AppPrefs(DisMyOrders.this);
            app.setCart_QTy("");
        }

        app = new AppPrefs(DisMyOrders.this);
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

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisMyOrders.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(getApplicationContext());

                if (app.getUser_notification().toString().equalsIgnoreCase("mainpage_drawer")) {
                    Intent i = new Intent(DisMyOrders.this, MainPage_drawer.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

                else {
                    if (app.getUserRoleId().equalsIgnoreCase("4") || app.getUserRoleId().equalsIgnoreCase("5") || app.getUserRoleId().equalsIgnoreCase("3")) {
                        Intent i = new Intent(DisMyOrders.this, User_Profile.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(DisMyOrders.this, DisOrderListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisMyOrders.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(DisMyOrders.this);
                app.setUser_notification("userprofile");
                Intent i = new Intent(DisMyOrders.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(DisMyOrders.this);
                app.setUser_notification("DisMyOrders");
                Intent i = new Intent(DisMyOrders.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        app = new AppPrefs(getApplicationContext());

        if (app.getUser_notification().equalsIgnoreCase("mainpage_drawer")) {
            Intent i = new Intent(DisMyOrders.this, MainPage_drawer.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            if (app.getUserRoleId().equalsIgnoreCase("4") || app.getUserRoleId().equalsIgnoreCase("5") || app.getUserRoleId().equalsIgnoreCase("3")) {
                Intent i = new Intent(DisMyOrders.this, User_Profile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else {
                Intent i = new Intent(DisMyOrders.this, DisOrderListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(DisMyOrders.this);

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

    public String GetCartByQty(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //  System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    private class GetBillData extends AsyncTask<Void,Void,String> {
        public StringBuilder sb;
        boolean status;
        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        String jsonData="";
        private String result;
        private InputStream is;

        GetBillData(Activity activity, String user_id){
            this.activity=activity;
            this.user_id=user_id;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(activity, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception ignored) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                // parameters.add(new BasicNameValuePair("distributor_id", String.valueOf(distributor_id)));
                parameters.add(new BasicNameValuePair("user_id",user_id));

                //Log.e("distributor id", "" + distributor_id+"");
                //Log.e("USER id", "" + user_id+"");

                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/APP_Party_Outstanding_Balance",ServiceHandler.POST,parameters);

                //System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
               // System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String jsonData) {
            jsonBill=jsonData;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        JSONObject dataObj=mainObject.getJSONObject("data");

                        balanceList.clear();

                        Bean_Balance balance1=new Bean_Balance();
                        //Log.e("22222222222222",""+userID);
                        balance1.setOutstandingName(dataObj.getString("total_outstanding_balance_text"));
                        //Log.e("OUTSTANDING NAME",""+dataObj.getString("total_outstanding_balance_text"));
                        balance1.setOutstandingRs(dataObj.getString("total_outstanding_balance"));
                        balance1.setDueName(dataObj.getString("total_due_amount_text"));
                        balance1.setDueRs(dataObj.getString("total_due_amount"));

                        at.setText(dataObj.getString("total_outstanding_balance_text"));
                        ar.setText(getResources().getString(R.string.Rs) + dataObj.getString("total_outstanding_balance"));
                        dt.setText(dataObj.getString("total_due_amount_text"));
                        dr.setText(getResources().getString(R.string.Rs) +dataObj.getString("total_due_amount"));



//                        for(int i=0; i<ledgerArray.length(); i++){
//                            Log.e("33333333333333333333",""+userID);
//                            JSONObject subObject=ledgerArray.getJSONObject(i);
//                            Bean_Ledger ledger=new Bean_Ledger();
//
//                            ledger.setDate(subObject.getString("DATE"));
//                            ledger.setParticulars(subObject.getString("PARTICULARS"));
//                            ledger.setVchType(subObject.getString("VOUCHERTYPE"));
//                            ledger.setVchNo(subObject.getString("VOUCHERNUMBER"));
//                            ledger.setDebit(subObject.getString("DEBIT"));
//                            ledger.setCredit(subObject.getString("CREDIT"));
//
//
//
//                            ledgerList.add(ledger);
//                        }
                        //ledgerAdapter.notifyDataSetChanged();
                        //Log.e("Size",balanceList.size()+"");
                    }
                    else{

                        Globals.CustomToast(DisMyOrders.this,""+ mainObject.getString("message"),getLayoutInflater());
                        loadingView.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    loadingView.dismiss();
                }

//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            //refreshLayout.setRefreshing(false);
            loadingView.dismiss();
        }
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

                    Globals.CustomToast(DisMyOrders.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(DisMyOrders.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(DisMyOrders.this);
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
