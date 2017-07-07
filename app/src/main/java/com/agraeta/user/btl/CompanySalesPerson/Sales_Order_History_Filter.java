package com.agraeta.user.btl.CompanySalesPerson;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.BTLProdcut_Detailpage;
import com.agraeta.user.btl.BTL_Cart;
import com.agraeta.user.btl.Bean_Filter_Status;
import com.agraeta.user.btl.Bean_User_data;
import com.agraeta.user.btl.Btl_WishList;
import com.agraeta.user.btl.C;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.Notification;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by chaitalee on 9/20/2016.
 */
public class Sales_Order_History_Filter extends AppCompatActivity {

    ListView list_filter_status, list_filter_days;
    DatabaseHandler db;
    ArrayList<Bean_Filter_Status> array_filter_status = new ArrayList<Bean_Filter_Status>();
    ArrayList<Bean_Filter_Status> array_filter_date = new ArrayList<Bean_Filter_Status>();
    CustomAdapterFilterStatus adapter;
    TextView tv_status, tv_date;
    Dialog dialog;
    String cartJSON="";
    boolean hasCartCallFinish=true;

    TextView txt;


    String fd = new String();
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    AppPrefs appPrefs;
    int click_filter_type = -1;
    String owner_id = new String();
    String u_id = new String();
    String role_id = new String();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    LinearLayout apply, clear_all;
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
        setContentView(R.layout.activity_order__history__filter);
        db = new DatabaseHandler(Sales_Order_History_Filter.this);
        appPrefs = new AppPrefs(Sales_Order_History_Filter.this);
        array_filter_status = db.get_order_history_filter_status();

        array_filter_date.add(new Bean_Filter_Status("2_custom", "Custom"));
        array_filter_date.add(new Bean_Filter_Status("0", "Today"));
        array_filter_date.add(new Bean_Filter_Status("1", "Yesterday"));
        array_filter_date.add(new Bean_Filter_Status("7", "Last 7 Days"));
        array_filter_date.add(new Bean_Filter_Status("15", "Last 15 Days"));
        array_filter_date.add(new Bean_Filter_Status("30", "Last 30 Days"));

        setActionBar();

        fetchID();
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

        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
         txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        appPrefs = new AppPrefs(Sales_Order_History_Filter.this);
        String qun =appPrefs.getCart_QTy();

        setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    appPrefs = new AppPrefs(Sales_Order_History_Filter.this);
                    role_id = appPrefs.getSubSalesId().toString();
                    u_id = appPrefs.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    appPrefs = new AppPrefs(Sales_Order_History_Filter.this);
                    role_id = appPrefs.getSubSalesId().toString();
                    u_id = appPrefs.getSalesPersonId().toString();
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
            appPrefs = new AppPrefs(Sales_Order_History_Filter.this);
            appPrefs.setCart_QTy("");
        }

        appPrefs = new AppPrefs(Sales_Order_History_Filter.this);
        String qu1 = appPrefs.getCart_QTy();
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
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPrefs = new AppPrefs(Sales_Order_History_Filter.this);
                appPrefs.setUser_notification("Sales_Order_History_Filter");
                Intent i = new Intent(Sales_Order_History_Filter.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPrefs.setOrder_history_filter_order_status("");
                appPrefs.setOrder_history_filter_to_date("");
                appPrefs.setOrder_history_filter_from_date("");
                appPrefs.setOrder_history_filter_predefine("");
                Intent i = new Intent(Sales_Order_History_Filter.this, SalesOrderHistory.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
      /*  img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckoutPage.this,Main_CategoryPage.class);
                startActivity(i);
            }
        });*/
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sales_Order_History_Filter.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sales_Order_History_Filter.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void fetchID()
    {
        list_filter_status = (ListView) findViewById(R.id.list_status);
        list_filter_days = (ListView) findViewById(R.id.list_day);
        apply = (LinearLayout) findViewById(R.id.apply);
        clear_all = (LinearLayout) findViewById(R.id.clear_all);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sales_Order_History_Filter.this,SalesOrderHistory.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appPrefs.setOrder_history_filter_order_status("");
                appPrefs.setOrder_history_filter_to_date("");
                appPrefs.setOrder_history_filter_from_date("");
                appPrefs.setOrder_history_filter_predefine("");


                Intent i = new Intent(Sales_Order_History_Filter.this,Sales_Order_History_Filter.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });


        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_date = (TextView) findViewById(R.id.tv_date);
        list_filter_status.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list_filter_days.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        list_filter_status.setVisibility(View.VISIBLE);
        list_filter_days.setVisibility(View.GONE);

        adapter = new CustomAdapterFilterStatus(array_filter_status);
        adapter.notifyDataSetChanged();
        list_filter_status.setAdapter(adapter);
        list_filter_status.setVisibility(View.VISIBLE);
        list_filter_days.setVisibility(View.GONE);
        tv_status.setBackgroundColor(Color.parseColor("#f58634"));
        click_filter_type = 1;


        tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_filter_type = 1;
                adapter = new CustomAdapterFilterStatus(array_filter_status);
                adapter.notifyDataSetChanged();
                list_filter_status.setAdapter(adapter);
                list_filter_status.setVisibility(View.VISIBLE);
                list_filter_days.setVisibility(View.GONE);
                tv_date.setBackground(null);
                tv_status.setBackgroundColor(Color.parseColor("#f58634"));


            }
        });

        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_filter_type = 2;
                adapter = new CustomAdapterFilterStatus(array_filter_date);
                adapter.notifyDataSetChanged();
                list_filter_days.setAdapter(adapter);
                list_filter_status.setVisibility(View.GONE);
                list_filter_days.setVisibility(View.VISIBLE);
                tv_date.setBackgroundColor(Color.parseColor("#f58634"));
                tv_status.setBackground(null);

            }
        });



    }

    private class CustomAdapterFilterStatus extends BaseAdapter implements Checkable
    {
        private boolean checked;
        ArrayList<Bean_Filter_Status> array_bean = new ArrayList<Bean_Filter_Status>();
        LayoutInflater vi;
        private List<Checkable> checkableViews;
        Integer selected_position = -1;
        CustomAdapterFilterStatus(ArrayList<Bean_Filter_Status> array)
        {
            this.array_bean = array;
            vi = (LayoutInflater)getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return array_bean.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = vi.inflate(R.layout.order_history_filter_row, null);

            }

            final CheckBox cb = (CheckBox) convertView.findViewById(R.id.rd_order_filter);

            // Toast.makeText(getApplicationContext(),"clicked - "+position+"  selected - "+selected_position,Toast.LENGTH_SHORT).show();


            cb.setText(array_bean.get(position).getFilter_name());

            if (position == selected_position) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }

            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(click_filter_type==2) {
                        if (array_bean.get(position).getId().equalsIgnoreCase("2_custom")) {
                            date_popup();
                            //Toast.makeText(getApplicationContext(),"clicked - "+array_bean.get(position).getId(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            appPrefs.setOrder_history_filter_predefine(array_bean.get(position).getId());
                            // Toast.makeText(getApplicationContext(),"clicked - "+array_bean.get(position).getId(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(click_filter_type==1)
                    {
                        appPrefs.setOrder_history_filter_order_status(array_bean.get(position).getId());
                        // Toast.makeText(getApplicationContext(),"clicked - "+array_bean.get(position).getId(),Toast.LENGTH_SHORT).show();
                    }

                    if (cb.isChecked()) {
                        selected_position = position;
                    } else {
                        selected_position = -1;
                    }
                    adapter.notifyDataSetChanged();
                }
            });

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                }
            });
            return convertView;
        }

        @Override
        public void setChecked(boolean checked) {
            this.checked = checked;
            for (Checkable c : checkableViews) {
                c.setChecked(checked);
            }
        }

        @Override
        public boolean isChecked() {
            return checked;
        }

        @Override
        public void toggle() {

        }
    }

    private void date_popup()
    {
        dialog = new Dialog(Sales_Order_History_Filter.this);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        dialog.setCanceledOnTouchOutside(false);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.date_popup);
        final EditText ed_from_date = (EditText)dialog.findViewById(R.id.ed_from_date);
        final EditText ed_to_date = (EditText)dialog.findViewById(R.id.ed_to_date);
        final Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        final Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        dialog.show();


        ed_from_date.setText(appPrefs.getOrder_history_filter_from_date());
        ed_to_date.setText(appPrefs.getOrder_history_filter_to_date());

        ed_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(Sales_Order_History_Filter.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int dayOfMonth, int monthOfYear, int year) {
                        Calendar newDate = Calendar.getInstance();

                        fromDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                        // newDate.set(year, monthOfYear, dayOfMonth);
                        newDate.set(dayOfMonth, monthOfYear, year);

                        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        fd = dateFormatter.format(newDate.getTime());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                        //  s_txtdate = dateFormat.format(newDate.getTime());

                        ed_from_date.setText(dateFormatter.format(newDate.getTime()));

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.show();
            }
        });

        ed_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(Sales_Order_History_Filter.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();

                        fromDatePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                        newDate.set(year, monthOfYear, dayOfMonth);
                        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        fd = dateFormatter.format(newDate.getTime());

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                        //  s_txtdate = dateFormat.format(newDate.getTime());

                        ed_to_date.setText(dateFormatter.format(newDate.getTime()));

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.show();
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_from_date.getText().toString().equalsIgnoreCase(""))
                {
                    Globals.CustomToast(Sales_Order_History_Filter.this,"Please enter from date",getLayoutInflater());
                } else if(ed_to_date.getText().toString().equalsIgnoreCase(""))
                {
                    Globals.CustomToast(Sales_Order_History_Filter.this,"Please enter to date",getLayoutInflater());
                }
                else {
                    //Log.e("from_date", "" + ed_from_date.getText().toString());
                 //   Log.e("to_date", "" +ed_to_date.getText().toString());
                    appPrefs.setOrder_history_filter_from_date(ed_from_date.getText().toString());
                    appPrefs.setOrder_history_filter_to_date(ed_to_date.getText().toString());
                    appPrefs.setOrder_history_filter_predefine("");
                    dialog.dismiss();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appPrefs.setOrder_history_filter_order_status("");
        appPrefs.setOrder_history_filter_to_date("");
        appPrefs.setOrder_history_filter_from_date("");
        appPrefs.setOrder_history_filter_predefine("");
        Intent i = new Intent(Sales_Order_History_Filter.this,SalesOrderHistory.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Sales_Order_History_Filter.this);

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
    public String GetCartByQty(final List<NameValuePair> params){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty",ServiceHandler.POST,params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //  System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){

        }
        //Log.e("my json",json[0]);
        return json[0];
    }
    public class GetCartByQty extends AsyncTask<Void, Void, String> {

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

                    Globals.CustomToast(Sales_Order_History_Filter.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);
                    String userStatus = jObj.getString("user_status");
                    if (userStatus.equals("0")) {
                        C.userInActiveDialog(Sales_Order_History_Filter.this);
                    }
                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        appPrefs = new AppPrefs(Sales_Order_History_Filter.this);
                        appPrefs.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        appPrefs = new AppPrefs(Sales_Order_History_Filter.this);
                        appPrefs.setCart_QTy(""+qu);


                    }

                }


            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = appPrefs.getCart_QTy();
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

