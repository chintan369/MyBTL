package com.agraeta.user.btl.Distributor;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.CompanySalesPerson.BeanUserArea;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class S_R_P_OrderList extends AppCompatActivity {
    S_R_P_OrderListAdapter s_r_p_orderListAdapter;
    List<Bean_S_R_P> srpList = new ArrayList<>();
    List<BeanUserArea> userAreaList=new ArrayList<>();
    ListView userList;
    String jsonSales = "";
    String jsonRP = "";
    String subUserID, salesID, csRole_ID,userID,roleID;
    int position1;
    AppPrefs prefs,apps;
    DatabaseHandler db;
    SwipeRefreshLayout refreshLayout;
    EditText search_name;

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
        setContentView(R.layout.activity_s__r__p__order_list);
        prefs=new AppPrefs(getApplicationContext());
        prefs.setCurrentPage("OrderList1");
        userID=prefs.getUserId();
        roleID=prefs.getSubDisId();

        fetchIDs();
        setActionBar();
        //Log.e("uuuuuuuuuu",""+userID);
        if(roleID.equalsIgnoreCase("8") || roleID.equalsIgnoreCase("9"))
        {
            new GetDisProfData(this,userID,roleID).execute();
        }
        else{
            new GetDisSalesData(this,userID).execute();
        }



//        sales
    }
    private void fetchIDs() {
        search_name=(EditText) findViewById(R.id.search_name);
        userList=(ListView) findViewById(R.id.lsView_srp);
//        refreshLayout=(SwipeRefreshLayout) findViewById(R.id.refreshView);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshLayout.setRefreshing(false);
//                new GetDisProfData(DisProfListActivity.this,prefs.getUserId(),prefs.getSubDisId()).execute();
//            }
//        });
//
//        refreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(false);
//            }
//        });

        s_r_p_orderListAdapter=new S_R_P_OrderListAdapter(this, srpList , this);
        userList.setAdapter(s_r_p_orderListAdapter);
        userList.setDivider(null);

        search_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String name=search_name.getText().toString().trim();
                if(name.equalsIgnoreCase("")){
                    s_r_p_orderListAdapter.updateData(srpList);
                }
                else {
                    List<Bean_S_R_P> professionalPersonListForSearch=new ArrayList<Bean_S_R_P>();

                    for(int i=0; i<srpList.size(); i++){
                        if(srpList.get(i).getFirstName().toLowerCase().startsWith(name.toLowerCase()) ||
                                srpList.get(i).getLastName().toLowerCase().startsWith(name.toLowerCase())){
                            professionalPersonListForSearch.add(srpList.get(i));
                        }
                        if(professionalPersonListForSearch.size() == 0){
                            Globals.CustomToast(S_R_P_OrderList.this,"No data Found",getLayoutInflater());
                        }

                        s_r_p_orderListAdapter.updateData(professionalPersonListForSearch);
                    }
                }
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
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        img_cart.setVisibility(View.GONE);
        img_home.setVisibility(View.VISIBLE);
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
//        db = new DatabaseHandler(Product_List.this);
//        bean_cart =  db.Get_Product_Cart();
//        db.close();
//        if(bean_cart.size() == 0){
//            txt.setVisibility(View.GONE);
//            txt.setText("");
//        }else{
//            txt.setVisibility(View.VISIBLE);
//            txt.setText(bean_cart.size()+"");
//        }

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db= new DatabaseHandler(Product_List.this);
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
                Intent i = new Intent(S_R_P_OrderList.this,DisOrderListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                finish();
            }
        });
//        img_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                Intent i = new Intent(Product_List.this, Main_CategoryPage.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app = new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
                Intent i = new Intent(S_R_P_OrderList.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
//        img_notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app = new AppPrefs(Product_List.this);
//                app.setUser_notification("product");
//                Intent i = new Intent(Product_List.this, Notification.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
//        img_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                app = new AppPrefs(Product_List.this);
//                app.setUser_notification("product");
//                Intent i = new Intent(Product_List.this, BTL_Cart.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DisOrderListActivity.class);
        startActivity(intent);
        finish();
    }

    public class GetDisProfData extends AsyncTask<Void,Void,String> {
        public StringBuilder sb;
        boolean status;
        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        String role_id;
        String jsonData="";
        private String result;
        private InputStream is;

        //        public GetDisSalesData(Activity activity,int distributor_id){
//            this.activity=activity;
//            this.distributor_id=distributor_id;
//        }
        public GetDisProfData(Activity activity,String user_id,String role_id){
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

                ////Log.e("distributor id", "" + distributor_id+"");
                //Log.e("user id", "" + user_id+"");
                //Log.e("role id", "" + role_id+"");


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/APP_Distributor_User_Details",ServiceHandler.POST,parameters);

                Log.e("parameters2", "--->" + parameters);

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
            jsonRP=jsonData;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        JSONArray dataArray=mainObject.getJSONArray("data");

                        srpList.clear();

                        for(int i=0; i<dataArray.length(); i++){
                            JSONObject subObject=dataArray.getJSONObject(i);
                            Bean_S_R_P profDistributor=new Bean_S_R_P();

                            profDistributor.setSales_id(subObject.getInt("id"));
                            prefs.setDisUserId22(String.valueOf(subObject.getInt("id")));
                            profDistributor.setFirstName(subObject.getString("first_name"));
                            //salesDistributor.setUser_id(subObject.getString("id"));
                            profDistributor.setLastName(subObject.getString("last_name"));
                            profDistributor.setEmailid(subObject.getString("email_id"));
                            profDistributor.setPhone_no(subObject.getString("phone_no"));
                            profDistributor.setStatus(subObject.getString("status"));
                            prefs.setStatusPR22(subObject.getString("status"));
                            //salesDistributor.setPancardno(subObject.getString("pancard_sales"));
                            // salesDistributor.setAltPhone_no(subObject.getString("alternate_no"));
                            //salesDistributor.setAadhaarcardno(subObject.getString("aadhar_card_sales"));
                            // salesDistributor.setPassword(subObject.getString("password"));


                            srpList.add(profDistributor);
                        }
                        s_r_p_orderListAdapter.notifyDataSetChanged();
                        //Log.e("Size",srpList.size()+"");
                    }

                    else{

                        Globals.CustomToast(S_R_P_OrderList.this,""+mainObject.getString("message").toString(),getLayoutInflater());
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

//            refreshLayout.setRefreshing(false);
            loadingView.dismiss();
        }
    }

    public class GetDisSalesData extends AsyncTask<Void,Void,String> {
        public StringBuilder sb;
        boolean status;
        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        String jsonData="";
        private String result;
        private InputStream is;

        //        public GetDisSalesData(Activity activity,int distributor_id){
//            this.activity=activity;
//            this.distributor_id=distributor_id;
//        }
        public GetDisSalesData(Activity activity,String user_id){
            this.activity=activity;
            this.user_id=user_id;
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

                ////Log.e("distributor id", "" + distributor_id+"");
                //Log.e("distributor id", "" + user_id+"");

                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/APP_Distributor_sales_person",ServiceHandler.POST,parameters);

                Log.e("parameters", "--->" + parameters);

                //System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
              //  System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            jsonSales=jsonData;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        JSONArray dataArray=mainObject.getJSONArray("data");

                        srpList.clear();

                        for(int i=0; i<dataArray.length(); i++){
                            JSONObject subObject=dataArray.getJSONObject(i);
                            Bean_S_R_P salesDistributor=new Bean_S_R_P();

                            salesDistributor.setSales_id(subObject.getInt("id"));
                            salesDistributor.setFirstName(subObject.getString("first_name"));
                            //salesDistributor.setUser_id(subObject.getString("id"));
                            salesDistributor.setLastName(subObject.getString("last_name"));
                            salesDistributor.setEmailid(subObject.getString("email_id"));
                            salesDistributor.setPhone_no(subObject.getString("phone_no"));
                            salesDistributor.setStatus(subObject.getString("status"));
                            //Log.e("LIST STATUS",""+subObject.getString("status"));
                            salesDistributor.setPancardno(subObject.getString("pancard_sales"));
                            salesDistributor.setAltPhone_no(subObject.getString("alternate_no"));
                            salesDistributor.setAadhaarcardno(subObject.getString("aadhar_card_sales"));
                            // salesDistributor.setPassword(subObject.getString("password"));


                            srpList.add(salesDistributor);
                        }
                        s_r_p_orderListAdapter.notifyDataSetChanged();
                        //Log.e("Size",srpList.size()+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //refreshLayout.setRefreshing(false);
            loadingView.dismiss();
        }
    }

}
