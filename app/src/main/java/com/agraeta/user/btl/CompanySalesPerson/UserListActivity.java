package com.agraeta.user.btl.CompanySalesPerson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Distributor.DisSalesFormActivity;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;
import com.agraeta.user.btl.model.RegisteredUserTourResponse;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    UserListAdapter userListAdapter;
    List<Bean_Company_Sales_User> companySalesUserList = new ArrayList<>();
    List<BeanUserArea> userAreaList=new ArrayList<>();
    ListView userList;
    String jsonCompanySalesUser = "";
    String subUserID, salesID, csRole_ID;
    int position1;
    AppPrefs prefs,apps;
    DatabaseHandler db;
    SwipeRefreshLayout refreshLayout;
    EditText search_name;
    TextView txt_nodata;

    ArrayList<String> area_id=new ArrayList<>();
    ArrayList<String> area_names=new ArrayList<>();
    List<BeanUserArea> areaList=new ArrayList<>();

    ArrayAdapter<String> adp_area;
    Spinner search_area;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
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
        setContentView(R.layout.activity_user_list);
        setActionBar();
        prefs = new AppPrefs(getApplicationContext());
        prefs.setCurrentPage("UserList");
        subUserID = prefs.getSubSalesId();
        salesID = prefs.getCsalesId();
        csRole_ID=prefs.getUserRoleId();

        Log.e("IDS",subUserID+"><"+salesID+"><"+csRole_ID);

        new GetComSalesUserData(this, subUserID, salesID,csRole_ID, position1).execute();
        getId();


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

        ImageView img_logout=(ImageView)mCustomView.findViewById(R.id.image_logout);
        img_logout.setVisibility(View.VISIBLE);
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),MainPage_drawer.class);
//                startActivity(intent);
//                finish();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        UserListActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle("Log Out application?");
                // Setting Dialog Message
                alertDialog
                        .setMessage("Are you sure you want to log out?");

                // Setting Icon to Dialog
                // .setIcon(R.drawable.ic_launcher);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                apps = new AppPrefs(UserListActivity.this);
                                if (apps.getUser_LoginWith().equalsIgnoreCase("0")) {

                                    apps.setUser_LoginInfo("");
                                    apps.setUser_PersonalInfo("");
                                    apps.setUser_SocialIdInfo("");
                                    apps.setUser_SocialFirst("");
                                    apps.setUser_Sociallast("");
                                    apps.setUser_Socialemail("");
                                    apps.setUser_SocialReInfo("");

                                    apps.setCsalesId("");
                                    apps.setSubSalesId("");
                                    apps.setUserRoleId("");
                                    apps.setCurrentPage("");




                                    db = new DatabaseHandler(UserListActivity.this);
                                    db.Delete_user_table();
                                    db.Clear_ALL_table();
                                    db.close();
                                    Intent i = new Intent(UserListActivity.this, MainPage_drawer.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();

                                }
                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Write your code here to invoke NO event
                                dialog.cancel();
                            }
                        });
                // Showing Alert Message
                alertDialog.show();
            }
        });

        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UserTypeActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void getId() {
        txt_nodata=(TextView) findViewById(R.id.txt_nodata);
        search_name=(EditText) findViewById(R.id.search_name);
        search_area=(Spinner) findViewById(R.id.search_area);

        userList = (ListView) findViewById(R.id.lsView_user);
        userListAdapter = new UserListAdapter(this, companySalesUserList, this);
        userListAdapter.setSubRoleID(subUserID);
        userList.setAdapter(userListAdapter);
        userList.setDivider(null);

        for (int i = 0; i < companySalesUserList.size(); i++) {
            for (int j = 0; j < areaList.size(); j++) {
                if (areaList.get(j).getArea_id().equalsIgnoreCase(companySalesUserList.get(i).areaId)) {
                    companySalesUserList.get(i).setAreaName(areaList.get(j).getArea_name());
                }
            }
        }

        adp_area=new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_status,area_names);
        search_area.setAdapter(adp_area);


        search_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int pos=search_area.getSelectedItemPosition();

                //Log.e("selected pos",""+pos);

                if(pos==0){
                    //Log.e("Selection at ","0");
                    String name=search_name.getText().toString().trim();
                    if(name.equalsIgnoreCase("")){

                        if(companySalesUserList.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }

                        userListAdapter.updateData(companySalesUserList);
                    }
                    else {
                        List<Bean_Company_Sales_User> distributorPersonListForSearch=new ArrayList<Bean_Company_Sales_User>();

                        for(int i=0; i<companySalesUserList.size(); i++){
                            if(companySalesUserList.get(i).getFirstname().toLowerCase().startsWith(name.toLowerCase()) ||
                                    companySalesUserList.get(i).getLastname().toLowerCase().startsWith(name.toLowerCase())){
                                distributorPersonListForSearch.add(companySalesUserList.get(i));
                            }
                        }
                        if(distributorPersonListForSearch.size() == 0){
                        Globals.CustomToast(UserListActivity.this,"No data Found",getLayoutInflater());

                        }

                        if(distributorPersonListForSearch.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }

                        userListAdapter.updateData(distributorPersonListForSearch);
                    }
                }
                else {
                    String areaId=area_id.get(pos);


                    //Log.e("areaID",areaId);

                    String name=search_name.getText().toString().trim();

                    if(name.equalsIgnoreCase("")){
                        List<Bean_Company_Sales_User> distributorPersonListForSearch=new ArrayList<Bean_Company_Sales_User>();

                        for(int i=0; i<companySalesUserList.size(); i++){
                            if(companySalesUserList.get(i).getAreaId().equalsIgnoreCase(areaId)){
                                distributorPersonListForSearch.add(companySalesUserList.get(i));
                            }
                        }
                        if(distributorPersonListForSearch.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }

                        userListAdapter.updateData(distributorPersonListForSearch);
                    }
                    else {
                        List<Bean_Company_Sales_User> distributorPersonListForSearch=new ArrayList<Bean_Company_Sales_User>();

                        for(int i=0; i<companySalesUserList.size(); i++){
                            if(companySalesUserList.get(i).getAreaId().equalsIgnoreCase(areaId)){
                                distributorPersonListForSearch.add(companySalesUserList.get(i));
                            }
                        }

                        for(int i=0; i<distributorPersonListForSearch.size(); i++){
                            if(!distributorPersonListForSearch.get(i).getFirstname().toLowerCase().startsWith(name.toLowerCase()) &&
                                    !distributorPersonListForSearch.get(i).getLastname().toLowerCase().startsWith(name.toLowerCase())){
                                distributorPersonListForSearch.remove(i);
                            }
                        }
                        if(distributorPersonListForSearch.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }
                        userListAdapter.updateData(distributorPersonListForSearch);
                    }
                }
            }
        });

        search_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(search_area.getSelectedItemPosition()==0){
                    String name=search_name.getText().toString().trim();
                    if(name.equalsIgnoreCase("")){

                        if(companySalesUserList.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }
                        userListAdapter.updateData(companySalesUserList);
                    }
                    else {
                        List<Bean_Company_Sales_User> distributorPersonListForSearch=new ArrayList<Bean_Company_Sales_User>();

                        for(int i=0; i<companySalesUserList.size(); i++){
                            if(companySalesUserList.get(i).getFirstname().toLowerCase().startsWith(name.toLowerCase()) ||
                                    companySalesUserList.get(i).getLastname().toLowerCase().startsWith(name.toLowerCase())){
                                distributorPersonListForSearch.add(companySalesUserList.get(i));
                            }
                        }
                        if(distributorPersonListForSearch.size() == 0){
                            Globals.CustomToast(UserListActivity.this,"No data Found",getLayoutInflater());
                        }
                        if(distributorPersonListForSearch.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }

                        userListAdapter.updateData(distributorPersonListForSearch);
                    }
                }
                else {
                    int pos=search_area.getSelectedItemPosition();
                    String areaId=area_id.get(pos);

                    //Log.e("areaID",areaId);

                    String name=search_name.getText().toString().trim();

                    if(name.equalsIgnoreCase("")){
                        List<Bean_Company_Sales_User> distributorPersonListForSearch=new ArrayList<Bean_Company_Sales_User>();

                        for(int i=0; i<companySalesUserList.size(); i++){
                            if(companySalesUserList.get(i).getAreaId().equalsIgnoreCase(areaId)){
                                distributorPersonListForSearch.add(companySalesUserList.get(i));
                            }
                        }

                        if(distributorPersonListForSearch.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }

                        userListAdapter.updateData(distributorPersonListForSearch);
                    }
                    else {
                        List<Bean_Company_Sales_User> distributorPersonListForSearch=new ArrayList<Bean_Company_Sales_User>();

                        for(int i=0; i<companySalesUserList.size(); i++){
                            if(companySalesUserList.get(i).getAreaId().equalsIgnoreCase(areaId)){
                                distributorPersonListForSearch.add(companySalesUserList.get(i));
                            }
                        }

                        for(int i=0; i<distributorPersonListForSearch.size(); i++){
                            if(!distributorPersonListForSearch.get(i).getFirstname().toLowerCase().startsWith(name.toLowerCase()) &&
                                    !distributorPersonListForSearch.get(i).getLastname().toLowerCase().startsWith(name.toLowerCase())){
                                distributorPersonListForSearch.remove(i);
                            }
                        }

                        if(distributorPersonListForSearch.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }
                        userListAdapter.updateData(distributorPersonListForSearch);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), UserTypeActivity.class);
        startActivity(intent);
        finish();
    }

    public class GetComSalesUserData extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        Custom_ProgressDialog loadingView;
        Activity activity;
        String subSalesID;
        String csalesID;
        String csRole_ID;
        String jsonData = "";
        int position;
        private String result;
        private InputStream is;

        //        public GetDisSalesData(Activity activity,int distributor_id){
//            this.activity=activity;
//            this.distributor_id=distributor_id;
//        }
        public GetComSalesUserData(Activity activity, String subSalesID, String csalesID, String csRole_ID, int position) {
            this.activity = activity;
            this.subSalesID = subSalesID;
            this.csalesID = csalesID;
            this.csRole_ID=csRole_ID;
            this.position = position;
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

                parameters.add(new BasicNameValuePair("user_id", csalesID));
                parameters.add(new BasicNameValuePair("user_type_id", subSalesID));
                parameters.add(new BasicNameValuePair("role_id", csRole_ID));



                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "User/APP_Distributor_For_Company_Sales_person", ServiceHandler.POST, parameters);

                //System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
             //   System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            jsonCompanySalesUser = jsonData;

            if (!jsonData.equalsIgnoreCase("")) {
                try {
                    JSONObject mainObject = new JSONObject(jsonData);
                    if (mainObject.getBoolean("status")) {
                        JSONArray dataArray = mainObject.getJSONArray("data");
                        //JSONArray addressArray=mainObject.getJSONArray("area");
                        companySalesUserList.clear();

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject subObject = dataArray.getJSONObject(i);
                            JSONObject User = subObject.getJSONObject("User");
                            JSONObject Distributor = subObject.getJSONObject("Distributor");
                            JSONArray AddressArray = subObject.getJSONArray("Address");
                            Bean_Company_Sales_User companySales = new Bean_Company_Sales_User();

                            companySales.setUserSalesId(User.getString("id"));
                            companySales.setFirstname(User.getString("first_name"));
                            companySales.setLastname(User.getString("last_name"));

                            if (AddressArray.length() > 0) {
                                companySales.setAreaId(((JSONObject) AddressArray.get(0)).getString("area_id"));
                            }

                            Gson gson = new Gson();

                            companySales.getUserData().setUser(gson.fromJson(User.toString(), com.agraeta.user.btl.model.User.class));

                            companySales.getUserData().setDistributor(gson.fromJson(Distributor.toString(), RegisteredUserTourResponse.Distributor.class));

                            Type listType = new TypeToken<List<RegisteredUserTourResponse.Address>>() {
                            }.getType();
                            List<RegisteredUserTourResponse.Address> addressList = gson.fromJson(AddressArray.toString(), listType);

                            companySales.getUserData().setAddress(addressList);

                            companySalesUserList.add(companySales);
                        }
                        areaList.clear();
                        area_id.clear();
                        area_names.clear();
                        JSONArray areaArray=mainObject.getJSONArray("area");

                        area_id.add("0");
                        area_names.add("Select Area");

                        for(int i=0; i<areaArray.length(); i++){
                            JSONObject areaObj=areaArray.getJSONObject(i);

                            BeanUserArea area=new BeanUserArea();
                            area.setArea_id(areaObj.getString("id"));
                            area.setArea_name(areaObj.getString("name"));

                            //Log.e("Area ID",areaObj.getString("id"));
                            //Log.e("Area Name",areaObj.getString("name"));
                            areaList.add(area);

                            area_id.add(areaObj.getString("id"));
                            area_names.add(areaObj.getString("name"));
                        }

                        adp_area.notifyDataSetChanged();




                        userListAdapter.notifyDataSetChanged();
                        //Log.e("Size", companySalesUserList.size() + "");
                    }

                    else{

                      Globals.CustomToast(UserListActivity.this,""+mainObject.getString("message").toString(),getLayoutInflater());
                        loadingView.dismiss();
                        if(companySalesUserList.size()==0){
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    loadingView.dismiss();
                    if(companySalesUserList.size()==0){
                        txt_nodata.setVisibility(View.VISIBLE);
                    }
                    else {
                        txt_nodata.setVisibility(View.GONE);
                    }

                }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

           // refreshLayout.setRefreshing(false);
            //refreshLayout.setRefreshing(false);
            loadingView.dismiss();
            if(companySalesUserList.size()==0){
                txt_nodata.setVisibility(View.VISIBLE);
            }
            else {
                txt_nodata.setVisibility(View.GONE);
            }

        }
    }

}
