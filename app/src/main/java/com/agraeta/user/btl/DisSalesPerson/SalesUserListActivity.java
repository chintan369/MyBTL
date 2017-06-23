package com.agraeta.user.btl.DisSalesPerson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.agraeta.user.btl.CompanySalesPerson.BeanUserArea;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Distributor.DisSalesFormActivity;
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

public class SalesUserListActivity extends AppCompatActivity {

    SalesUserListAdapter salesUserListAdapter;
    List<Bean_Distributor_Sales> distributorSalesUserList = new ArrayList<>();
    List<BeanDisSalesArea> userDSAreaList=new ArrayList<>();
    ListView userListDS;
    String jsonDistributorSalesUser = "";
    String subdissalesID, salesID, dsRole_ID;
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
        setContentView(R.layout.activity_sales_user_list);
        setActionBar();
        prefs = new AppPrefs(getApplicationContext());
        prefs.setCurrentPage("UserList");
        subdissalesID = prefs.getSubSalesId();
        salesID = prefs.getDsalesId();
        dsRole_ID=prefs.getUserRoleId();
        //Log.e("SUB DIS SALES ID",""+subdissalesID);
        //Log.e("DISSALES ID",""+salesID);
        //Log.e("USER ROLE ID",""+dsRole_ID);

        new GetDisSalesUserData(this, subdissalesID, salesID,dsRole_ID,position1).execute();
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
                        SalesUserListActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle("Logout application?");
                // Setting Dialog Message
                alertDialog
                        .setMessage("Are you sure you want to logout?");

                // Setting Icon to Dialog
                // .setIcon(R.drawable.ic_launcher);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                apps = new AppPrefs(SalesUserListActivity.this);
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



                                    db = new DatabaseHandler(SalesUserListActivity.this);
                                    db.Delete_user_table();
                                    db.Clear_ALL_table();
                                    db.close();
                                    Intent i = new Intent(SalesUserListActivity.this, MainPage_drawer.class);
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
                Intent intent=new Intent(getApplicationContext(),SalesTypeActivity.class);
                startActivity(intent);
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
//        img_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app = new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                Intent i = new Intent(Product_List.this, MainPage_drawer.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
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
    private void getId() {
        txt_nodata=(TextView) findViewById(R.id.txt_nodata);
        search_name=(EditText) findViewById(R.id.search_name);
        search_area=(Spinner) findViewById(R.id.search_area);
//        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshView);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshLayout.setRefreshing(false);
//                new GetDisSalesUserData(SalesUserListActivity.this, prefs.getSubSalesId(), prefs.getDsalesId(),prefs.getUserRoleId() ,position1).execute();
//            }
//        });
//
//        refreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(false);
//            }
//        });
        userListDS = (ListView) findViewById(R.id.lsView_user);
        salesUserListAdapter = new SalesUserListAdapter(this, distributorSalesUserList, this);
        userListDS.setAdapter(salesUserListAdapter);
        userListDS.setDivider(null);

//        if(distributorSalesUserList.size()==0){
//            txt_nodata.setVisibility(View.VISIBLE);
//        }
//        else {
//            txt_nodata.setVisibility(View.GONE);
//        }


        for (int i = 0; i < distributorSalesUserList.size(); i++) {
            for (int j = 0; j < areaList.size(); j++) {
                if (areaList.get(j).getArea_id().equalsIgnoreCase(distributorSalesUserList.get(i).areaId)) {
                    distributorSalesUserList.get(i).setAreaName(areaList.get(j).getArea_name());
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

                        if(distributorSalesUserList.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }
                        salesUserListAdapter.updateData(distributorSalesUserList);
                    }
                    else {
                        List<Bean_Distributor_Sales> distributorPersonListForSearch=new ArrayList<Bean_Distributor_Sales>();

                        for(int i=0; i<distributorSalesUserList.size(); i++){
                            if(distributorSalesUserList.get(i).getFirstname().toLowerCase().startsWith(name.toLowerCase()) ||
                                    distributorSalesUserList.get(i).getLastname().toLowerCase().startsWith(name.toLowerCase())){
                                distributorPersonListForSearch.add(distributorSalesUserList.get(i));
                            }
                        }

                        salesUserListAdapter.updateData(distributorPersonListForSearch);
                    }
                }
                else {
                    String areaId=area_id.get(pos);

                    //Log.e("areaID",areaId);

                    String name=search_name.getText().toString().trim();

                    if(name.equalsIgnoreCase("")){
                        List<Bean_Distributor_Sales> distributorPersonListForSearch=new ArrayList<Bean_Distributor_Sales>();

                        for(int i=0; i<distributorSalesUserList.size(); i++){
                            if(distributorSalesUserList.get(i).getAreaId().equalsIgnoreCase(areaId)){
                                distributorPersonListForSearch.add(distributorSalesUserList.get(i));
                            }
                        }
                        salesUserListAdapter.updateData(distributorPersonListForSearch);
                    }
                    else {
                        List<Bean_Distributor_Sales> distributorPersonListForSearch=new ArrayList<Bean_Distributor_Sales>();

                        for(int i=0; i<distributorSalesUserList.size(); i++){
                            if(distributorSalesUserList.get(i).getAreaId().equalsIgnoreCase(areaId)){
                                distributorPersonListForSearch.add(distributorSalesUserList.get(i));
                            }
                        }

                        for(int i=0; i<distributorPersonListForSearch.size(); i++){
                            if(!distributorPersonListForSearch.get(i).getFirstname().toLowerCase().startsWith(name.toLowerCase()) &&
                                    !distributorPersonListForSearch.get(i).getLastname().toLowerCase().startsWith(name.toLowerCase())){
                                distributorPersonListForSearch.remove(i);
                            }
                        }

                        salesUserListAdapter.updateData(distributorPersonListForSearch);
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

//                        if(distributorSalesUserList.size()==0){
//                            //Log.e("visibility","shown");
//                            txt_nodata.setVisibility(View.VISIBLE);
//                        }
//                        else {
//                            txt_nodata.setVisibility(View.GONE);
//                        }
                        salesUserListAdapter.updateData(distributorSalesUserList);
                    }
                    else {
                        List<Bean_Distributor_Sales> distributorPersonListForSearch=new ArrayList<Bean_Distributor_Sales>();

                        for(int i=0; i<distributorSalesUserList.size(); i++){
                            if(distributorSalesUserList.get(i).getFirstname().toLowerCase().startsWith(name.toLowerCase()) ||
                                    distributorSalesUserList.get(i).getLastname().toLowerCase().startsWith(name.toLowerCase())){
                                distributorPersonListForSearch.add(distributorSalesUserList.get(i));
                            }
                        }
                        if(distributorPersonListForSearch.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }

                        salesUserListAdapter.updateData(distributorPersonListForSearch);
                    }
                }
                else {
                    int pos=search_area.getSelectedItemPosition();
                    String areaId=area_id.get(pos);

                    //Log.e("areaID",areaId);

                    String name=search_name.getText().toString().trim();

                    if(name.equalsIgnoreCase("")){
                        List<Bean_Distributor_Sales> distributorPersonListForSearch=new ArrayList<Bean_Distributor_Sales>();

                        for(int i=0; i<distributorSalesUserList.size(); i++){
                            if(distributorSalesUserList.get(i).getAreaId().equalsIgnoreCase(areaId)){
                                distributorPersonListForSearch.add(distributorSalesUserList.get(i));
                            }
                        }
                        if(distributorPersonListForSearch.size()==0){
                            //Log.e("visibility","shown");
                            txt_nodata.setVisibility(View.VISIBLE);
                        }
                        else {
                            txt_nodata.setVisibility(View.GONE);
                        }

                        salesUserListAdapter.updateData(distributorPersonListForSearch);
                    }
                    else {
                        List<Bean_Distributor_Sales> distributorPersonListForSearch=new ArrayList<Bean_Distributor_Sales>();

                        for(int i=0; i<distributorSalesUserList.size(); i++){
                            if(distributorSalesUserList.get(i).getAreaId().equalsIgnoreCase(areaId)){
                                distributorPersonListForSearch.add(distributorSalesUserList.get(i));
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

                        salesUserListAdapter.updateData(distributorPersonListForSearch);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public class GetDisSalesUserData extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        String subdissalesID;
        String salesID;
        String dsRole_ID;
        String jsonData = "";
        int position;

        //        public GetDisSalesData(Activity activity,int distributor_id){
//            this.activity=activity;
//            this.distributor_id=distributor_id;
//        }
        public GetDisSalesUserData(Activity activity, String subdissalesID, String salesID, String dsRole_ID, int position) {
            this.activity = activity;
            this.subdissalesID = subdissalesID;
            this.salesID = salesID;
            this.dsRole_ID=dsRole_ID;
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

                parameters.add(new BasicNameValuePair("user_id", salesID));
                parameters.add(new BasicNameValuePair("user_type_id", subdissalesID));
                parameters.add(new BasicNameValuePair("role_id", dsRole_ID));


                //parameters.add(new BasicNameValuePair("search_by_name", csalesID));
                // parameters.add(new BasicNameValuePair("area_ids", subSalesID));

                //Log.e("COMPANY SALES", "" + salesID + "");
                //Log.e("USER TYPE", "" + subdissalesID + "");
                //Log.e("USER ROLE ID", "" + dsRole_ID + "");

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
            jsonDistributorSalesUser = jsonData;
            //Log.e("ARRAYYYYY",""+jsonData);

            if (!jsonData.equalsIgnoreCase("")) {
                try {
                    JSONObject mainObject = new JSONObject(jsonData);
                    if (mainObject.getBoolean("status")) {

                        JSONArray dataArray = mainObject.getJSONArray("data");

                        //JSONArray addressArray=mainObject.getJSONArray("area");
                        distributorSalesUserList.clear();

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject subObject = dataArray.getJSONObject(i);
                            Bean_Distributor_Sales distributor_Sales = new Bean_Distributor_Sales();

                            distributor_Sales.setUserDisSalesId(subObject.getString("id"));
                            distributor_Sales.setFirstname(subObject.getString("first_name"));
                            //Log.e("firstname", "" + subObject.getString("first_name"));
                            //salesDistributor.setUser_id(subObject.getString("id"));
                            distributor_Sales.setLastname(subObject.getString("last_name"));
                            distributor_Sales.setAreaId(subObject.getString("area_id"));

                            distributorSalesUserList.add(distributor_Sales);
                        }

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

                        //Log.e("Area Size",areaList.size()+"");

                        //Log.e("Area Size",areaList.size()+"");

//                        JSONArray addressArray=mainObject.getJSONArray("area");
//                        for (int j = 0; j < dataArray.length(); j++) {
//                            JSONObject subObject1 = addressArray.getJSONObject(j);
//                            BeanUserArea userArea = new BeanUserArea();
//
//                            userArea.setArea_id(subObject1.getString("id"));
//                            userArea.setArea_name(subObject1.getString("name"));
//
//
//                            //Log.e("firstname", "" + subObject1.getString("first_name"));
//                            //salesDistributor.setUser_id(subObject.getString("id"));
//                            userAreaList.add(userArea);
//                        }


                        salesUserListAdapter.notifyDataSetChanged();
                        //Log.e("Size", distributorSalesUserList.size() + "");
                    }
                    else{

                        Globals.CustomToast(SalesUserListActivity.this,""+mainObject.getString("message").toString(),getLayoutInflater());
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

          //  refreshLayout.setRefreshing(false);
            //refreshLayout.setRefreshing(false);
            loadingView.dismiss();
        }
    }
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),SalesTypeActivity.class);
        startActivity(intent);
        finish();
    }
}
