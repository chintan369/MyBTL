package com.agraeta.user.btl.Distributor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
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

public class DisSalesListActivity extends AppCompatActivity implements DisSalesAdapter.DisSalesCallback {
    TextView txt_nodata;

//    ListView listView;
//    Context context;
//    public static String[] salesList=new String[]{"ABC","DEF",
//            "GHI","JKL","MNO",
//            "PQR","STU","VWX","YZ"};
//
////    String[] salesCompany=new String[]{"Sunpharma","Flipcart",
////            "Amazon","Ebay","Nike","Laavi","Fasttrack","Kaliber",
////            "Nikon"};
//    ArrayList salesNames;
    ListView list_sales;
    SwipeRefreshLayout refreshLayout;
    DisSalesAdapter disSalesAdapter;
    List<Bean_Dis_Sales> disSalesList=new ArrayList<>();
    String jsonSalesPerson="";

    AppPrefs prefs;

    Toolbar toolbar;
    ImageView img_drawer,img_home,img_notification,img_cart,img_add;
    FrameLayout unread;
    TextView message_tv;
    String userID;
    EditText search_name;

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
        setContentView(R.layout.activity_dis_sales_list);
        prefs=new AppPrefs(getApplicationContext());
        userID=prefs.getUserId();

        fetchIDs();
        setActionBar();
        //Log.e("uuuuuuuuuu",""+userID);
        new GetDisSalesData(this,userID).execute();

        }

    private void fetchIDs() {
        txt_nodata=(TextView) findViewById(R.id.txt_nodata);
        search_name=(EditText) findViewById(R.id.search_name);
        list_sales=(ListView) findViewById(R.id.lsView_sales);
        refreshLayout=(SwipeRefreshLayout) findViewById(R.id.refreshView);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                new GetDisSalesData(DisSalesListActivity.this,prefs.getUserId()).execute();
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });

        disSalesAdapter=new DisSalesAdapter(this, disSalesList , this);
        list_sales.setAdapter(disSalesAdapter);
        disSalesAdapter.setCallBack(this);
        list_sales.setDivider(null);
//        if(disSalesList.size()==0){
//            txt_nodata.setVisibility(View.VISIBLE);
//        }
//        else {
//            txt_nodata.setVisibility(View.GONE);
//        }


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

                    if(disSalesList.size()==0){
                        //Log.e("visibility","shown");
                        txt_nodata.setVisibility(View.VISIBLE);
                    }
                    else {
                        txt_nodata.setVisibility(View.GONE);
                    }
                    disSalesAdapter.updateData(disSalesList);
                }
                else {
                    List<Bean_Dis_Sales> salesPersonListForSearch=new ArrayList<Bean_Dis_Sales>();

                    for(int i=0; i<disSalesList.size(); i++){
                        if(disSalesList.get(i).getFirstName().toLowerCase().startsWith(name.toLowerCase()) ||
                                disSalesList.get(i).getLastName().toLowerCase().startsWith(name.toLowerCase())){
                            salesPersonListForSearch.add(disSalesList.get(i));
                        }
                        if(salesPersonListForSearch.size() == 0){
                            Globals.CustomToast(DisSalesListActivity.this,"No data Found",getLayoutInflater());
                        }

                        disSalesAdapter.updateData(salesPersonListForSearch);
                    }
                }
            }
        });
    }
    @Override
    public void deleteUser(String saless_id , String dis_id, int position) {
        //Log.e("Delete User","Method Called");
        new DeleteUser(DisSalesListActivity.this, saless_id ,dis_id , position).execute();
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
        img_add.setVisibility(View.VISIBLE);
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
                Intent i = new Intent(DisSalesListActivity.this,DistributorActivity.class);
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
                Intent i = new Intent(DisSalesListActivity.this, MainPage_drawer.class);
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

    public class GetDisSalesData extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        String jsonData="";

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
            jsonSalesPerson=jsonData;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        JSONArray dataArray=mainObject.getJSONArray("data");

                        disSalesList.clear();

                        for(int i=0; i<dataArray.length(); i++){
                            JSONObject subObject=dataArray.getJSONObject(i);
                            Bean_Dis_Sales salesDistributor=new Bean_Dis_Sales();

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


                            disSalesList.add(salesDistributor);
                        }
                        disSalesAdapter.notifyDataSetChanged();
                        //Log.e("Size",disSalesList.size()+"");
                    }
                    else{

                        Globals.CustomToast(DisSalesListActivity.this,""+mainObject.getString("message").toString(),getLayoutInflater());
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

            refreshLayout.setRefreshing(false);
            loadingView.dismiss();
        }
    }

    public class DeleteUser extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        String salesss_id;
        String dis_id;
        String jsonData="";
        int position;

        public DeleteUser(Activity activity,String salesss_id, String dis_id, int position){
            this.activity=activity;
            this.salesss_id=salesss_id;
            this.dis_id=dis_id;
            this.position=position;
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
           // //Log.e("IDS",user_id+" "+position);

            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("sales_person_id", salesss_id));
                parameters.add(new BasicNameValuePair("user_id", dis_id));

                ////Log.e("distributor id", "" + user_id);

                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Delete_Distributor_Sales_Person",ServiceHandler.POST,parameters);

                //System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
                //Log.e("eXeCptioN",e.getMessage());
                //System.out.println("Exception " + e.toString());
                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String jsonData) {
            //Log.e("jsonData post",jsonData);

            if(!jsonData.equalsIgnoreCase("")){
                //Log.e("JSON","Full");
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        Globals.CustomToast(activity.getApplicationContext(),mainObject.getString("message"),activity.getLayoutInflater());
                        disSalesList.remove(position);
                        disSalesAdapter.notifyDataSetChanged();
                        //Log.e("Size",disSalesList.size()+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Log.e("eXCEption",e.getMessage());
                }
            }
            loadingView.dismiss();
        }
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(DisSalesListActivity.this,DistributorActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        finish();
    }


    }

