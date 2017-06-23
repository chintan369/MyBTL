package com.agraeta.user.btl.Distributor;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
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
import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
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

public class LegderListActivity extends AppCompatActivity {
    ListView listview_ledger;
    LedgerAdapter ledgerAdapter;
    List<Bean_Ledger> ledgerList=new ArrayList<>();
    String jsonLedger="";
    TextView debit,credit,sdate,edate;
    Bean_Ledger bean_ledger;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    Bean_User_data bean_user_data;

    AppPrefs prefs,app;

    Toolbar toolbar;
    ImageView img_drawer,img_home,img_notification,img_cart,img_add;
    FrameLayout unread;
    TextView message_tv,txtName,txtopening,txtClosing,txtCurrentCredit,txtCurrentDebit,opening_credit,closing_credit;
    String userID;
    EditText search_name;
    DatabaseHandler db;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    String fd = new String();
    String td = new String();
    String fromd = new String();
    String tod = new String();
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
        setContentView(R.layout.activity_legder_list);
        prefs=new AppPrefs(getApplicationContext());

        if(prefs.getUserRoleId().equals(C.ADMIN)){
            Intent intent=getIntent();
            userID=intent.getStringExtra("userID");
        }
        else {
            userID=prefs.getUserId();
        }
       // setActionBar();
        fetchIDs();

        //Log.e("uuuuuuuuuu",""+userID);
        new GetLedgerData(this,userID).execute();


    }

    private void fetchIDs() {
        search_name=(EditText) findViewById(R.id.search_name);
        listview_ledger=(ListView) findViewById(R.id.lsView_ledger);
        opening_credit=(TextView)findViewById(R.id.opening_credit);
        closing_credit=(TextView)findViewById(R.id.closing_credit);


        ledgerAdapter=new LedgerAdapter(this, ledgerList , this);
        listview_ledger.setAdapter(ledgerAdapter);
        //ledgerAdapter.setCallBack(this);
        listview_ledger.setDivider(null);

        sdate=(TextView)findViewById(R.id.txt_sdate);
        edate=(TextView)findViewById(R.id.txt_edate);

        from_date = (EditText)findViewById(R.id.startdate);
        to_date = (EditText)findViewById(R.id.enddate);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of the month
        cal.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        String firstDate = df1.format(cal.getTime());
        from_date.setText(""+firstDate);
        sdate.setText(""+firstDate);
        System.out.println("Start of the month:       " + firstDate);
        System.out.println("... in milliseconds:      " + cal.getTimeInMillis());

        // get start of the next month
        cal.add(Calendar.MONTH, 1);
        System.out.println("Start of the next month:  " + cal.getTime());
        System.out.println("... in milliseconds:      " + cal.getTimeInMillis());

        final Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String topDate = df.format(c.getTime());
        // time = df.format(c.getTime());
        to_date.setText(""+topDate);
        edate.setText(""+topDate);
        System.out.println("... current date:      " + topDate);

        from_date.setInputType(InputType.TYPE_NULL);
        to_date.setInputType(InputType.TYPE_NULL);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
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

      /*  from_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus)
                {
                    fromDatePickerDialog.show();


                    //Show your calender here
                }else{
                    //Hide your calender here
                }
            }
        });

        to_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus)
                {
                    toDatePickerDialog.show(); //Show your calender here


                }else
                {
                    //Hide your calender here
                }
            }
        });*/
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fd = dateFormatter.format(newDate.getTime());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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



                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
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
	             /*   if(convertedDate.equals(convertedDate1))
	                {
	                	Toast.makeText(getApplicationContext(), "To_Date is must be greater than to From Date", Toast.LENGTH_SHORT).show();

	                }
	                else */
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
                        new GetLedgerData_Filter(LegderListActivity.this,userID).execute();
                    }
                    /*list.setVisibility(View.VISIBLE);
                    txt_sum.setVisibility(View.VISIBLE);
                    vaa.setVisibility(View.VISIBLE);
                    txt_nodata.setVisibility(View.GONE);
                    Set_Referash_Data();*/

                }

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                td = dateFormatter.format(newDate.getTime());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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

                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
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
	              /*  if(convertedDate.equals(convertedDate1))
	                {
	                	Toast.makeText(getApplicationContext(), "To_Date is must be greater than to From Date", Toast.LENGTH_SHORT).show();

	                }
	                else*/ if(convertedDate.after(convertedDate1)){

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
                        new GetLedgerData_Filter(LegderListActivity.this,userID).execute();
                    }
                 /*   list.setVisibility(View.VISIBLE);
                    txt_sum.setVisibility(View.VISIBLE);
                    vaa.setVisibility(View.VISIBLE);
                    txt_nodata.setVisibility(View.GONE);
                    Set_Referash_Data();*/

                }

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        debit=(TextView)findViewById(R.id.debit);
        credit=(TextView)findViewById(R.id.credit);


        txtName=(TextView)findViewById(R.id.txt_ledgername);
        txtopening=(TextView) findViewById(R.id.txt_opening);
        txtClosing=(TextView) findViewById(R.id.txt_closing);
        txtCurrentCredit=(TextView) findViewById(R.id.txt_currentCredit);
        txtCurrentDebit=(TextView)findViewById(R.id.txt_currentDebit);

        setRefershData();



        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {


                txtName.setText(user_data.get(i).getF_name()+" "+user_data.get(i).getL_name());

            }

        }else{

        }

    }

    public class GetLedgerData extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        String jsonData="";

        public GetLedgerData(Activity activity,String user_id){
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
                //Log.e("USER id", "" + user_id+"");

                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/APP_Party_Ledger_Report",ServiceHandler.POST,parameters);

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
            jsonLedger=jsonData;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        //Log.e("111111111111",""+userID);
                        //JSONArray dataArray=mainObject.getJSONArray("data");
                        JSONObject dataObj=mainObject.getJSONObject("data");
                        JSONObject balanceObj=dataObj.getJSONObject("balance");
                        JSONArray ledgerArray=dataObj.getJSONArray("ledger");


                        ledgerList.clear();

                        Bean_Ledger ledger1=new Bean_Ledger();
                        //Log.e("22222222222222",""+userID);
                        ledger1.setOpeningBalance(balanceObj.getString("opening_balance"));
                        //Log.e("opening",""+balanceObj.getString("opening_balance"));
                        ledger1.setClosingBalance(balanceObj.getString("closing_balance"));
                        ledger1.setCurrentTotalDebit(balanceObj.getString("current_total_debit"));
                        ledger1.setCurrentTotalCredit(balanceObj.getString("current_total_credit"));

                        if(balanceObj.getString("opening_type").toString().equalsIgnoreCase("DEBIT")){
                            txtopening.setText(getResources().getString(R.string.Rs) +balanceObj.getString("opening_balance").toString());
                            opening_credit.setVisibility(View.INVISIBLE);
                            txtopening.setVisibility(View.VISIBLE);
                        }else{
                            opening_credit.setText(getResources().getString(R.string.Rs) +balanceObj.getString("opening_balance").toString());
                            opening_credit.setVisibility(View.VISIBLE);
                            txtopening.setVisibility(View.INVISIBLE);
                        }
                        if(balanceObj.getString("closing_type").toString().equalsIgnoreCase("DEBIT")){
                            txtClosing.setText(getResources().getString(R.string.Rs) +balanceObj.getString("closing_balance").toString());
                            txtClosing.setVisibility(View.VISIBLE);
                            closing_credit.setVisibility(View.INVISIBLE);
                        }else{
                            closing_credit.setText(getResources().getString(R.string.Rs) +balanceObj.getString("closing_balance").toString());
                            txtClosing.setVisibility(View.INVISIBLE);
                            closing_credit.setVisibility(View.VISIBLE);
                        }



                        txtCurrentCredit.setText(getResources().getString(R.string.Rs) +balanceObj.getString("current_total_credit"));
                        txtCurrentDebit.setText(getResources().getString(R.string.Rs) +balanceObj.getString("current_total_debit"));



                        for(int i=0; i<ledgerArray.length(); i++){
                            //Log.e("33333333333333333333",""+userID);
                            JSONObject subObject=ledgerArray.getJSONObject(i);
                            Bean_Ledger ledger=new Bean_Ledger();

                            ledger.setDate(subObject.getString("DATE"));
                            ledger.setParticulars(subObject.getString("PARTICULARS"));
                            ledger.setVchType(subObject.getString("VOUCHERTYPE"));
                            ledger.setVchNo(subObject.getString("VOUCHERNUMBER"));
                            ledger.setDebit(subObject.getString("DEBIT"));
                            ledger.setCredit(subObject.getString("CREDIT"));



                            ledgerList.add(ledger);
                        }
                        ledgerAdapter.notifyDataSetChanged();
                        //Log.e("Size",ledgerList.size()+"");
                    }else{

                        Globals.CustomToast(LegderListActivity.this,""+mainObject.getString("message").toString(),getLayoutInflater());
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
    public class GetLedgerData_Filter extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        Custom_ProgressDialog loadingView;
        Activity activity;
        //int distributor_id;
        String user_id;
        String jsonData="";

        public GetLedgerData_Filter(Activity activity,String user_id){
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

                ////Log.e("distributor id", "" + distributor_id+"");
                //Log.e("USER id", "" + user_id+"");

                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"User/APP_Party_Ledger_Report",ServiceHandler.POST,parameters);

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
            jsonLedger=jsonData;

            if(!jsonData.equalsIgnoreCase("")){
                try {
                    JSONObject mainObject=new JSONObject(jsonData);
                    if(mainObject.getBoolean("status")){
                        //Log.e("111111111111",""+userID);
                        //JSONArray dataArray=mainObject.getJSONArray("data");
                        JSONObject dataObj=mainObject.getJSONObject("data");
                        JSONObject balanceObj=dataObj.getJSONObject("balance");
                        JSONArray ledgerArray=dataObj.getJSONArray("ledger");


                        ledgerList.clear();

                        Bean_Ledger ledger1=new Bean_Ledger();
                        //Log.e("22222222222222",""+userID);
                        ledger1.setOpeningBalance(balanceObj.getString("opening_balance"));
                        //Log.e("opening",""+balanceObj.getString("opening_balance"));
                        ledger1.setClosingBalance(balanceObj.getString("closing_balance"));
                        ledger1.setCurrentTotalDebit(balanceObj.getString("current_total_debit"));
                        ledger1.setCurrentTotalCredit(balanceObj.getString("current_total_credit"));

                        txtopening.setText(getResources().getString(R.string.Rs) +balanceObj.getString("opening_balance").toString());
                        txtClosing.setText(getResources().getString(R.string.Rs) +balanceObj.getString("closing_balance").toString());
                        txtCurrentCredit.setText(getResources().getString(R.string.Rs) +balanceObj.getString("current_total_credit"));
                        txtCurrentDebit.setText(getResources().getString(R.string.Rs) +balanceObj.getString("current_total_debit"));



                        for(int i=0; i<ledgerArray.length(); i++){
                            //Log.e("33333333333333333333",""+userID);
                            JSONObject subObject=ledgerArray.getJSONObject(i);
                            Bean_Ledger ledger=new Bean_Ledger();

                            ledger.setDate(subObject.getString("DATE"));
                            ledger.setParticulars(subObject.getString("PARTICULARS"));
                            ledger.setVchType(subObject.getString("VOUCHERTYPE"));
                            ledger.setVchNo(subObject.getString("VOUCHERNUMBER"));
                            ledger.setDebit(subObject.getString("DEBIT"));
                            ledger.setCredit(subObject.getString("CREDIT"));



                            ledgerList.add(ledger);
                        }
                        ledgerAdapter.notifyDataSetChanged();
                        //Log.e("Size",ledgerList.size()+"");
                    }
                    else{

                        Globals.CustomToast(LegderListActivity.this,""+mainObject.getString("message").toString(),getLayoutInflater());
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
        db = new DatabaseHandler(LegderListActivity.this);

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
        finish();
    }


}
