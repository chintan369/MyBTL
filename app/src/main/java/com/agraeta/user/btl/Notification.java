package com.agraeta.user.btl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.notifications.InboxResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Notification extends AppCompatActivity implements Callback<InboxResponse> {

    ListView list_notification;
    NotificationAdapter notificationAdapter;
    DatabaseHandler db;
    AppPrefs prefs;
    Button btn_clearall;
    String ownerID = new String();
    String userID = new String();
    String roleID = new String();
    List<InboxResponse.InboxData> notificationList=new ArrayList<>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    TextView txt_noNotification;

    AdminAPI adminAPI;
    int currentPage=1;
    int totalPages=1;

    Custom_ProgressDialog dialog;

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
        setContentView(R.layout.activity_notification);
        db=new DatabaseHandler(getApplicationContext());
        prefs=new AppPrefs(getApplicationContext());

        dialog=new Custom_ProgressDialog(this,"Please Wait...");
        dialog.setCancelable(false);

        setActionBar();
        fetchID();
    }

    private void fetchID() {
        list_notification=(ListView) findViewById(R.id.list_notification);
        txt_noNotification=(TextView) findViewById(R.id.txt_noNotification);
        notificationAdapter=new NotificationAdapter(notificationList,this);
        list_notification.setAdapter(notificationAdapter);

        adminAPI= ServiceGenerator.getAPIServiceClass();


        dialog.show();

        new GetNotificationList(true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        btn_clearall=(Button) findViewById(R.id.btn_clearall);
        btn_clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationList.size()>0){
                    clearAllNotifications();
                }
                else{
                    Globals.CustomToast(getApplicationContext(),"No Notifications in the List to Delete",getLayoutInflater());

                }
            }
        });
    }

    private void clearAllNotifications() {
        dialog.show();
        Call<AppModel> modelCall;
        if(prefs.getUserRoleId().equals(C.ADMIN) || prefs.getUserRoleId().equals(C.COMP_SALES_PERSON) || prefs.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON)){
            modelCall=adminAPI.clearNotificationCall(prefs.getUserId(),null);
        }
        else {
            modelCall=adminAPI.clearNotificationCall(null,prefs.getUserId());
        }

        modelCall.enqueue(new Callback<AppModel>() {
            @Override
            public void onResponse(Call<AppModel> call, Response<AppModel> response) {
                dialog.dismiss();
                AppModel model=response.body();
                if(model!=null){
                    if(model.isStatus()){
                        notificationList.clear();
                        notificationAdapter.notifyDataSetChanged();
                        Globals.Toast2(getApplicationContext(),model.getMessage());
                    }
                    else {
                        Globals.Toast2(getApplicationContext(),model.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<AppModel> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });

    }

    private void getNotificationList() {
        Call<InboxResponse> inboxResponseCall;
        if(prefs.getUserRoleId().equals(C.ADMIN) || prefs.getUserRoleId().equals(C.COMP_SALES_PERSON) || prefs.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON)){
            inboxResponseCall=adminAPI.inboxResponseCall(prefs.getUserId(),null,String.valueOf(currentPage));
        }
        else {
            inboxResponseCall=adminAPI.inboxResponseCall(null,prefs.getUserId(),String.valueOf(currentPage));
        }

        inboxResponseCall.enqueue(this);
    }

    public void onBackPressed() {
        prefs = new AppPrefs(Notification.this);
        if (prefs.getUser_notification().toString().equalsIgnoreCase("mainpage_drawer")) {
            Intent i = new Intent(Notification.this, MainPage_drawer.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }  else {
            Intent i = new Intent(Notification.this, User_Profile.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(mActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        final ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);

        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        //img_home.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        //img_home.setImageResource(R.drawable.btl_logo);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        String qun =prefs.getCart_QTy();

       /* setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    app = new AppPrefs(Notification.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    app = new AppPrefs(Notification.this);
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


            String json=GetCartByQty(para);

            try {


                //System.out.println(json);

                if (json==null
                        || (json.equalsIgnoreCase(""))) {
                    *//*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*//*
                    Globals.CustomToast(Notification.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(Notification.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(Notification.this);
                        app.setCart_QTy(""+qu);


                    }

                }
            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

        }else{
            app = new AppPrefs(Notification.this);
            app.setCart_QTy("");
        }*/

        String qu1 = prefs.getCart_QTy();
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
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Notification.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefs = new AppPrefs(Notification.this);
                if (prefs.getUser_notification().toString().equalsIgnoreCase("mainpage_drawer")) {
                    Intent i = new Intent(Notification.this, MainPage_drawer.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }  else {
                    Intent i = new Intent(Notification.this, User_Profile.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
//                Intent i = new Intent(Notification.this, MainPage_drawer.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                finish();
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Notification.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.setUser_notification("Notification");
                Intent i = new Intent(Notification.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Notification.this);

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

    @Override
    public void onResponse(Call<InboxResponse> call, Response<InboxResponse> response) {
        dialog.dismiss();
        InboxResponse inboxResponse=response.body();
        if(inboxResponse!=null){
            if(inboxResponse.isStatus()){
                totalPages=inboxResponse.getTotalPage();
                notificationList.addAll(inboxResponse.getData());
                notificationAdapter.notifyDataSetChanged();
                if(currentPage<totalPages){
                    currentPage++;
                    getNotificationList();
                }
            }
            else {
                Globals.Toast2(this,inboxResponse.getMessage());
                txt_noNotification.setVisibility(View.VISIBLE);
            }
        }
        else {
            Globals.defaultError(this);
        }
    }

    @Override
    public void onFailure(Call<InboxResponse> call, Throwable t) {
        dialog.dismiss();
        Log.e("Error","--"+t.getMessage());
        Globals.showError(t,this);
    }

    private class GetNotificationList extends AsyncTask<Void, Void, String>{

        boolean isFirstTime=true;

        public GetNotificationList(boolean isFirstTime) {
            this.isFirstTime = isFirstTime;
            if(isFirstTime){
                dialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairList=new ArrayList<>();
            pairList.add(new BasicNameValuePair("page",String.valueOf(currentPage)));
            if(prefs.getUserRoleId().equals(C.ADMIN) || prefs.getUserRoleId().equals(C.COMP_SALES_PERSON) || prefs.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON)){
                pairList.add(new BasicNameValuePair("owner_id",prefs.getUserId()));
            }
            else {
                pairList.add(new BasicNameValuePair("user_id",prefs.getUserId()));
            }

            String json=new ServiceHandler().makeServiceCall(Globals.server_link+Globals.GET_NOTIFICATION_LIST,ServiceHandler.POST,pairList);

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(s!=null && !s.isEmpty()){
                try{
                    JSONObject object=new JSONObject(s);

                    if(object.getBoolean("status")){
                        totalPages=object.getInt("total_pages");
                        Type inboxListType = new TypeToken<ArrayList<InboxResponse.InboxData>>(){}.getType();

                        List<InboxResponse.InboxData> inboxDataList = new Gson().fromJson(object.getJSONArray("data").toString(), inboxListType);

                        notificationList.addAll(inboxDataList);
                        notificationAdapter.notifyDataSetChanged();
                        if(currentPage<totalPages){
                            currentPage++;
                            new GetNotificationList(false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                    }
                    else {
                        txt_noNotification.setVisibility(View.VISIBLE);
                        Globals.Toast2(getApplicationContext(),object.getString("message"));
                    }

                }catch (Exception e){
                    Log.e("Exception",e.getMessage());
                    Globals.defaultError(getApplicationContext());
                }
            }
            else {
                Globals.defaultError(getApplicationContext());
            }
        }
    }
}
