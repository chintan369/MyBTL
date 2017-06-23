package com.agraeta.user.btl.CompanySalesPerson;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Bean_User_data;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.DisSalesPerson.SalesTypeActivity;
import com.agraeta.user.btl.Distributor.DisSalesFormActivity;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TargetAchieveList extends AppCompatActivity {
    ListView listview_target;
    TargetAchieveAdapter targetAchieveAdapter;
    ArrayList<BeanTargetAchieve> targetAchieveList=new ArrayList<>();
    String jsonTarget="";
    TextView sdate,edate;
    BeanTargetAchieve beanTargetAchieve;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    Bean_User_data bean_user_data;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    String fd = new String();
    String td = new String();
    String fromd = new String();
    String tod = new String();
    AppPrefs prefs,app;

    Toolbar toolbar;
    ImageView img_drawer,img_home,img_notification,img_cart,img_add;
    FrameLayout unread;
    TextView message_tv,txtName,txttotalopen,txttotalpend,txt_to;
    String userID;
    EditText search_name;
    DatabaseHandler db;
    EditText from_date,to_date;

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
        setContentView(R.layout.activity_target_achieve_list);
        fetchid();
        prefs=new AppPrefs(getApplicationContext());
        userID=prefs.getUserId();
        // setActionBar();
        setActionBar();

        //Log.e("uuuuuuuuuu",""+userID);
        new GetTargetAchieveData(this,userID).execute();

    }

    private void fetchid() {
            //search_name=(EditText) findViewById(R.id.search_name);
            listview_target=(ListView) findViewById(R.id.lsView_target);

            targetAchieveAdapter=new TargetAchieveAdapter(this, targetAchieveList , this);
            listview_target.setAdapter(targetAchieveAdapter);
            //ledgerAdapter.setCallBack(this);
            listview_target.setDivider(null);

            sdate=(TextView)findViewById(R.id.txt_sdate);
            edate=(TextView)findViewById(R.id.txt_edate);
            from_date = (EditText)findViewById(R.id.startdate);
            to_date = (EditText)findViewById(R.id.enddate);
            txt_to=(TextView)findViewById(R.id.txt_to);
            from_date.setInputType(InputType.TYPE_NULL);
            to_date.setInputType(InputType.TYPE_NULL);

            dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            from_date.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    fromDatePickerDialog.show();

                }
            });
            to_date.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    toDatePickerDialog.show();


                }
            });

            Calendar newCalendar = Calendar.getInstance();
            fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    fd = dateFormatter.format(newDate.getTime());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(fd);

                    } catch (ParseException | java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yyyy");
                    Date convertedDate1 = new Date();
                    try {
                        try {
                            convertedDate1 = dateFormat1.parse(td);
                        } catch (java.text.ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if(convertedDate.after(convertedDate1)){
                        if(td.equalsIgnoreCase(""))
                        {
                            from_date.setText(dateFormatter.format(newDate.getTime()));
                            sdate.setText(dateFormatter.format(newDate.getTime()));
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Please enter Date between From Date and To Date", Toast.LENGTH_SHORT).show();
                        }

                    }

                    else{

                        from_date.setText(dateFormatter.format(newDate.getTime()));
                        sdate.setText(dateFormatter.format(newDate.getTime()));
                        fromd = from_date.getText().toString();

                        if(!from_date.getText().toString().equalsIgnoreCase("") && !to_date.getText().toString().equalsIgnoreCase("")){
                            txt_to.setVisibility(View.VISIBLE);
                            new GetTargetAchieveData_Filter(TargetAchieveList.this,userID).execute();
                        }else{
                            txt_to.setVisibility(View.GONE);
                        }

                    }

                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    td = dateFormatter.format(newDate.getTime());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(fd);

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (java.text.ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yyyy");
                    Date convertedDate1 = new Date();
                    try {
                        try {
                            convertedDate1 = dateFormat1.parse(td);
                        } catch (java.text.ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if(convertedDate.after(convertedDate1)){

                        if(fd.equalsIgnoreCase(""))
                        {
                            to_date.setText(dateFormatter.format(newDate.getTime()));
                            edate.setText(dateFormatter.format(newDate.getTime()));


                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Please enter Date between From Date and To Date", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else{


                        to_date.setText(dateFormatter.format(newDate.getTime()));
                        edate.setText(dateFormatter.format(newDate.getTime()));
                        tod=to_date.getText().toString();
                        if(!from_date.getText().toString().equalsIgnoreCase("") && !to_date.getText().toString().equalsIgnoreCase("")){
                            txt_to.setVisibility(View.VISIBLE);
                            new GetTargetAchieveData_Filter(TargetAchieveList.this,userID).execute();
                        }else{
                            txt_to.setVisibility(View.GONE);
                        }

                    }

                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            txtName=(TextView)findViewById(R.id.txt_salesname);
        //txtName.setText(targetAchieveList.get(0).getSalesName());

//            txttotalopen=(TextView) findViewById(R.id.txt_opening_amt);
//            txttotalpend=(TextView)findViewById(R.id.txt_pending_amt);
//
//            setRefershData();
//
//
//
//            if(user_data.size() != 0){
//                for (int i = 0; i < user_data.size(); i++) {
//
//
//                    txtName.setText(user_data.get(i).getF_name()+" "+user_data.get(i).getL_name());
//
//                }
//
//            }else{
//
//            }

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
                    Intent i = new Intent(TargetAchieveList.this, UserTypeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

                if(prefs.getCurrentPage().toString().equalsIgnoreCase("SalesType")) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(TargetAchieveList.this, SalesTypeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

//                if(prefs.getCurrentPage().toString().equalsIgnoreCase("DistributorType")) {
//                    // TODO Auto-generated method stub
//                    Intent i = new Intent(S_SkipListIconActivity.this, DistributorActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(i);
//                    finish();
//                }
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public class GetTargetAchieveData_Filter extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        String jsonData="";

        public GetTargetAchieveData_Filter(Activity activity,String user_id){
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
                parameters.add(new BasicNameValuePair("start_date",fromd));
                parameters.add(new BasicNameValuePair("end_date",tod));

                //Log.e("distributor id", "" + distributor_id+"");
                //Log.e("USER id", "" + user_id+"");
              //  Log.e("start_date", "" + fromd+"");
               // Log.e("end_date", "" + tod+"");

                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Get_Sales_Person_Performance",ServiceHandler.POST,parameters);

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
            jsonTarget=jsonData;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        //Log.e("111111111111",""+userID);
                        JSONArray dataArry=mainObject.getJSONArray("data");

                        targetAchieveList.clear();

                        for(int i=0; i<dataArry.length(); i++){
                            JSONObject subObject=dataArry.getJSONObject(i);

                            BeanTargetAchieve targetAchieve=new BeanTargetAchieve();

                            JSONObject userObj=subObject.getJSONObject("User");
                            targetAchieve.setSalesName(userObj.getString("first_name")+""+userObj.getString("last_name"));



                            JSONArray salesTargetArry=subObject.getJSONArray("SalesTarget");
                            for(int j=0; j<salesTargetArry.length(); j++){
                                JSONObject subObject2=salesTargetArry.getJSONObject(j);

                                targetAchieve.setId(subObject2.getInt("category_id"));
                                targetAchieve.setTargetQuality(subObject2.getString("category_qty"));
                                targetAchieve.setAchieveQuality(subObject2.getString("achieved_qty"));

                                JSONObject category=subObject2.getJSONObject("Category");
                                targetAchieve.setCategoryName(category.getString("name"));

                                //disSalesList.add(salesDistributor);
                            }


                            targetAchieveList.add(targetAchieve);
                        }
                        txtName.setText(targetAchieveList.get(0).getSalesName());

                        targetAchieveAdapter.notifyDataSetChanged();
                        //Log.e("Size",targetAchieveList.size()+"");

                    }else{

                        Globals.CustomToast(TargetAchieveList.this,""+mainObject.getString("message").toString(),getLayoutInflater());
                        loadingView.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    loadingView.dismiss();
                }
            }
            loadingView.dismiss();
            //refreshLayout.setRefreshing(false);

        }
    }

    public class GetTargetAchieveData extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        String jsonData="";

        public GetTargetAchieveData(Activity activity,String user_id){
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

                //Log.e("distributor id", "" + distributor_id+"");
                //Log.e("USER id", "" + user_id+"");

                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Get_Sales_Person_Performance",ServiceHandler.POST,parameters);

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
            jsonTarget=jsonData;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        //Log.e("111111111111",""+userID);
                        JSONArray dataArry=mainObject.getJSONArray("data");

                        targetAchieveList.clear();

                        for(int i=0; i<dataArry.length(); i++){
                            JSONObject subObject=dataArry.getJSONObject(i);

                            BeanTargetAchieve targetAchieve=new BeanTargetAchieve();

                            JSONObject userObj=subObject.getJSONObject("User");
                            targetAchieve.setSalesName(userObj.getString("first_name")+" "+userObj.getString("last_name"));
                            //txtName.setText(userObj.getString("first_name")+""+subObject.getString("last_name"));

                            JSONArray salesTargetArry=subObject.getJSONArray("SalesTarget");
                            for(int j=0; j<salesTargetArry.length(); j++){
                                JSONObject subObject2=salesTargetArry.getJSONObject(j);

                                targetAchieve.setId(subObject2.getInt("category_id"));
                                targetAchieve.setTargetQuality(subObject2.getString("category_qty"));
                                //Log.e("yyyyyyyyy",""+subObject2.getString("category_qty"));
                                targetAchieve.setAchieveQuality(subObject2.getString("achieved_qty"));

                                JSONObject category=subObject2.getJSONObject("Category");
                                targetAchieve.setCategoryName(category.getString("name"));

                            }


                            targetAchieveList.add(targetAchieve);
                        }

                        if(targetAchieveList.size()==0)
                        {
                            Globals.CustomToast(TargetAchieveList.this,"No Data Found",getLayoutInflater());
                        }

                    txtName.setText(targetAchieveList.get(0).getSalesName());

                        targetAchieveAdapter.notifyDataSetChanged();
                        //Log.e("Size",targetAchieveList.size()+"");

                    }else{
                            Globals.CustomToast(TargetAchieveList.this,""+mainObject.getString("message").toString(),getLayoutInflater());
                            loadingView.dismiss();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    loadingView.dismiss();
                }
            }

            //refreshLayout.setRefreshing(false);
            loadingView.dismiss();
        }
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(TargetAchieveList.this);

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
    public void onBackPressed() {

        if(prefs.getCurrentPage().toString().equalsIgnoreCase("UserType")) {
            // TODO Auto-generated method stub
            Intent i = new Intent(TargetAchieveList.this, UserTypeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        if(prefs.getCurrentPage().toString().equalsIgnoreCase("SalesType")) {
            // TODO Auto-generated method stub
            Intent i = new Intent(TargetAchieveList.this, SalesTypeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

    }

}

