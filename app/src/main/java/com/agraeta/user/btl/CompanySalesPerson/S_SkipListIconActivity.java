package com.agraeta.user.btl.CompanySalesPerson;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Bean_User_data;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.DisSalesPerson.SalesTypeActivity;
import com.agraeta.user.btl.Distributor.DisSalesFormActivity;
import com.agraeta.user.btl.Distributor.DistributorActivity;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class S_SkipListIconActivity extends AppCompatActivity {
    TextView txt_nodata;
    ListView list_skip;
    SwipeRefreshLayout refreshLayout;
    SkipListAdapter skipListAdapter;
    List<Bean_SkipIcon> skipIconList=new ArrayList<>();
    String jsonSkip="";
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    DatabaseHandler db;

    AppPrefs prefs;

    Toolbar toolbar;
    ImageView img_drawer,img_home,img_notification,img_cart,img_add;
    FrameLayout unread;
    TextView message_tv;
    String userID,roleID;
    EditText search_name,search_name1;
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
        setContentView(R.layout.activity_s__skip_list_icon);
        prefs=new AppPrefs(getApplicationContext());
       /* userID=prefs.getUserId();
        roleID=prefs.getUserRoleId();*/


        setRefershData();



        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                userID = user_data.get(i).getUser_id().toString();

                roleID=user_data.get(i).getUser_type().toString();

            }

        }else{
            userID ="";
        }


        getId();
        setActionBar();
        Log.e("uuuuuuuuuu",""+userID);
        Log.e("rrrrrrrrrr",""+roleID);
        new GetSkipData(this,userID,roleID).execute();
    }

    private void getId() {
        txt_nodata=(TextView) findViewById(R.id.txt_nodata);
        search_name=(EditText) findViewById(R.id.search_name);
        search_name1=(EditText) findViewById(R.id.search_name1);
        list_skip = (ListView) findViewById(R.id.lsView_skip);
        skipListAdapter = new SkipListAdapter(this, skipIconList, this);
        list_skip.setAdapter(skipListAdapter);
        list_skip.setDivider(null);

        if(prefs.getUserRoleId().equalsIgnoreCase("3")){
            search_name1.setVisibility(View.VISIBLE);
        }
        else {
            search_name1.setVisibility(View.GONE);
        }

//        if(skipIconList.size()==0){
//            txt_nodata.setVisibility(View.VISIBLE);
//        }
//        else {
//            txt_nodata.setVisibility(View.GONE);
//        }


        search_name1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name=search_name.getText().toString().trim().toLowerCase();
                String ownername=search_name1.getText().toString().trim().toLowerCase();
                updateDataByName(name,ownername);
            }
        });

        search_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = search_name.getText().toString().trim().toLowerCase();

                if(prefs.getUserRoleId().equalsIgnoreCase("3")){
                    String ownername=search_name1.getText().toString().trim().toLowerCase();
                    updateDataByName(name,ownername);
                }
                else {
                    updateDataByName(name,"");
                }
            }
        });

        new GetSkipData(this,userID,roleID).execute();

    }

    public void updateDataByName(String name, String ownername){

        List<Bean_SkipIcon> searchList=new ArrayList<Bean_SkipIcon>();

        if(name.equalsIgnoreCase("") && ownername.equalsIgnoreCase("")){
            searchList=skipIconList;
        }
        else if(!name.equalsIgnoreCase("") && ownername.equalsIgnoreCase("")){
            for(int i=0; i<skipIconList.size(); i++){
                String skipname=skipIconList.get(i).getSkipForFname().toLowerCase();
                if(skipname.contains(name)){
                    searchList.add(skipIconList.get(i));
                }
            }

            for(int i=0; i<skipIconList.size(); i++){
                String skipname=skipIconList.get(i).getSkipForLname().toLowerCase();
                if(skipname.contains(name)){
                    searchList.add(skipIconList.get(i));
                }
            }
        }
        else if(name.equalsIgnoreCase("") && !ownername.equalsIgnoreCase("")){
            for(int i=0; i<skipIconList.size(); i++){
                String username=skipIconList.get(i).getSkipByFname().toLowerCase();
                if(username.contains(ownername)){
                    searchList.add(skipIconList.get(i));
                }
            }
            for(int i=0; i<skipIconList.size(); i++){
                String username=skipIconList.get(i).getSkipByLname().toLowerCase();
                if(username.contains(ownername)){
                    searchList.add(skipIconList.get(i));
                }
            }
        }
        else if(!name.equalsIgnoreCase("") && !ownername.equalsIgnoreCase("")){
            for(int i=0; i<skipIconList.size(); i++){
                String skipname=skipIconList.get(i).getSkipForFname().toLowerCase();
                String username=skipIconList.get(i).getSkipForLname().toLowerCase();
                String skipname1=skipIconList.get(i).getSkipByFname().toLowerCase();
                String username1=skipIconList.get(i).getSkipByLname().toLowerCase();
                if(skipname.contains(name) && username.contains(ownername) && skipname1.contains(name) && username1.contains(ownername)){
                    searchList.add(skipIconList.get(i));
                }
            }
        }
        skipListAdapter.updateData(searchList);


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
        img_cart.setVisibility(View.GONE);
        img_home.setVisibility(View.GONE);
        img_category.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);

        ImageView img_add=(ImageView)mCustomView.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DisSalesFormActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getCurrentPage().toString().equalsIgnoreCase("UserType")) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(S_SkipListIconActivity.this, UserTypeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

                if(prefs.getCurrentPage().toString().equalsIgnoreCase("SalesType")) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(S_SkipListIconActivity.this, SalesTypeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

                if(prefs.getCurrentPage().toString().equalsIgnoreCase("DistributorType")) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(S_SkipListIconActivity.this, DistributorActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
                }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public class GetSkipData extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        String role_id;
        String jsonData="";

        //        public GetDisSalesData(Activity activity,int distributor_id){
//            this.activity=activity;
//            this.distributor_id=distributor_id;
//        }
        public GetSkipData(Activity activity,String user_id,String role_id){
            this.activity=activity;
            this.user_id=user_id;
            this.role_id=role_id;
        }

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

                // parameters.add(new BasicNameValuePair("distributor_id", String.valueOf(distributor_id)));
                parameters.add(new BasicNameValuePair("user_id",user_id));
                parameters.add(new BasicNameValuePair("role_id",role_id));

                //Log.e("distributor id", "" + distributor_id+"");
                //Log.e("user id", "" + user_id+"");
                //Log.e("role id", "" + role_id+"");


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"OrderReason/App_GetSkipOrder",ServiceHandler.POST,parameters);

                //System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            jsonSkip=jsonData;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        JSONArray dataArray=mainObject.getJSONArray("data");

                        skipIconList.clear();

                        for(int i=0; i<dataArray.length(); i++){
                            JSONObject subObject=dataArray.getJSONObject(i);
                            JSONObject orderReason = subObject.getJSONObject("OrderReason");

                            Bean_SkipIcon skipPERSON=new Bean_SkipIcon();

                            skipPERSON.setId(orderReason.getString("id"));
                            skipPERSON.setUser_id(orderReason.getString("user_id"));
                            skipPERSON.setSkip_person_id(orderReason.getString("skip_person_id"));
                            skipPERSON.setReason_title(orderReason.getString("reason_title"));
                            skipPERSON.setOther_reason_title(orderReason.getString("other_reason_title"));
                            skipPERSON.setLatitude(orderReason.getString("latitude"));
                            skipPERSON.setLongitude(orderReason.getString("longitude"));
                            skipPERSON.setAttachment_1(orderReason.getString("attachment_1"));
                            skipPERSON.setAttachment_2(orderReason.getString("attachment_2"));
                            skipPERSON.setComment(orderReason.getString("comment"));
                            skipPERSON.setCreated(orderReason.getString("created"));
                            skipPERSON.setModified(orderReason.getString("modified"));

                            JSONObject userObject = subObject.getJSONObject("User");
                            skipPERSON.setSkipByFname(userObject.getString("first_name"));
                            skipPERSON.setSkipByLname(userObject.getString("last_name"));

                            JSONObject orderUserObject = subObject.getJSONObject("OrderUser");
                            skipPERSON.setSkipForFname(orderUserObject.getString("first_name"));
                            skipPERSON.setSkipForLname(orderUserObject.getString("last_name"));


                            skipIconList.add(skipPERSON);
                        }
                        skipListAdapter.notifyDataSetChanged();
                        //Log.e("Size",skipIconList.size()+"");
                    }


                    else{

                        Globals.CustomToast(S_SkipListIconActivity.this,""+mainObject.getString("message").toString(),getLayoutInflater());
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

           // refreshLayout.setRefreshing(false);
            loadingView.dismiss();
        }
    }
    public void onBackPressed() {

        if(prefs.getCurrentPage().equalsIgnoreCase("UserType")) {
            // TODO Auto-generated method stub
            Intent i = new Intent(S_SkipListIconActivity.this, UserTypeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            finish();
        }

        if(prefs.getCurrentPage().equalsIgnoreCase("SalesType")) {
            // TODO Auto-generated method stub
            Intent i = new Intent(S_SkipListIconActivity.this, SalesTypeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            finish();
        }

        if(prefs.getCurrentPage().equalsIgnoreCase("DistributorType")) {
            // TODO Auto-generated method stub
            Intent i = new Intent(S_SkipListIconActivity.this, DistributorActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            finish();
        }
    }
    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(S_SkipListIconActivity.this);

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
}
