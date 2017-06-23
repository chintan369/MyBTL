package com.agraeta.user.btl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Saved_Address extends AppCompatActivity {

    ListView lst_address;
    Button btn_add;
    String json =new String();
    String uid =new String();
    String add_id =new String();
    ImageLoader imageloader;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    Custom_ProgressDialog loadingView;
    DatabaseHandler db;
    ArrayList<Bean_Address> array_address = new ArrayList<Bean_Address>();
    ArrayList<String> array_final_address = new ArrayList<String>();
    ResultHolder result_holder;
    String role_id=new String();
    AppPrefs app;
    String owner_id = new String();
    String u_id = new String();
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
        setContentView(R.layout.activity_saved__address);

        setActionBar();
        fetchID();

        setRefershData();
        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                uid =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if(role_id.equalsIgnoreCase(C.COMP_SALES_PERSON)){

                    app =new AppPrefs(Saved_Address.this);
                    role_id=app.getSubSalesId().toString();
                    uid=app.getSalesPersonId().toString();
                }
            }

        }else{
            uid ="";
        }

        new get_Address().execute();

    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Saved_Address.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int userid =user_array_from_db.get(i).getId();
            uid=user_array_from_db.get(i).getUser_id();
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
            contact.setId(userid);
            contact.setUser_id(uid);
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

    private void fetchID() {

        lst_address =(ListView)findViewById(R.id.listView_addresst);
        btn_add=(Button)findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Saved_Address.this, Add_Address.class);
               // i.putExtra("user_id",uid);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

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
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
       /* TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(User_Profile.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size()+"");*/
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.VISIBLE);



        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        app = new AppPrefs(Saved_Address.this);
        String qun =app.getCart_QTy();

       /* setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    app = new AppPrefs(this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    app = new AppPrefs(this);
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
                    Globals.CustomToast(this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(Saved_Address.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(Saved_Address.this);
                        app.setCart_QTy(""+qu);


                    }

                }
            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

        }else{
            app = new AppPrefs(Saved_Address.this);
            app.setCart_QTy("");
        }*/

        app = new AppPrefs(Saved_Address.this);
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

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Saved_Address.this, User_Profile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Saved_Address.this,MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(Saved_Address.this);
                app.setUser_notification("Saved_Address");
                Intent i = new Intent(Saved_Address.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(Saved_Address.this, User_Profile.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    };
    public class get_Address extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Saved_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", uid));
                //Log.e("user_id", uid);



                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Address/App_Get_Addresses",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Saved_Address.this, "SERVER ERRER", Saved_Address.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        array_final_address.clear();
                        array_address.clear();
                        lst_address.setVisibility(View.VISIBLE);
                        lst_address.setAdapter(new CustomResultAdapterDoctor());
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                          Globals.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        array_final_address.clear();
                        array_address.clear();
                        JSONArray jarray_data = jObj.getJSONArray("data");

                        for(int i = 0 ; i < jarray_data.length() ; i ++)
                        {
                            JSONObject jobject_sub = jarray_data.getJSONObject(i);
                            JSONObject jobject_address = jobject_sub.getJSONObject("Address");
                            JSONObject jobject_country = jobject_sub.getJSONObject("Country");
                            JSONObject jobject_state = jobject_sub.getJSONObject("State");
                            JSONObject jobject_city = jobject_sub.getJSONObject("City");

                            Bean_Address bean = new Bean_Address();

                            bean.setAddress_id(jobject_address.getString("id"));
                            bean.setAddress_1(jobject_address.getString("address_1"));
                            bean.setAddress_2(jobject_address.getString("address_2"));
                            bean.setAddress_3(jobject_address.getString("country_id"));
                            bean.setLandmark(jobject_address.getString("landmark"));
                            bean.setPincode(jobject_address.getString("pincode"));
                            bean.setCountry(jobject_country.getString("name"));
                            bean.setState(jobject_state.getString("name"));
                            bean.setCity(jobject_city.getString("name"));
                            bean.setState_id(jobject_address.getString("state_id"));
                            bean.setCity_id(jobject_address.getString("city_id"));
                            bean.setDefult_add(jobject_address.getString("default_address"));



                            array_address.add(bean);

                        }



                        loadingView.dismiss();
                        lst_address.setVisibility(View.VISIBLE);
                        lst_address.setAdapter(new CustomResultAdapterDoctor());

                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }
    public class CustomResultAdapterDoctor extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return array_address.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            // result_holder = null;

            result_holder = new ResultHolder();

            //if (convertView == null) {

                convertView = vi.inflate(R.layout.address_row, null);

//            }

            result_holder.tvaddress = (TextView) convertView
                    .findViewById(R.id.address);

            result_holder.img_edit = (ImageView) convertView
                    .findViewById(R.id.add_edit);
            result_holder.img_delete = (ImageView) convertView
                    .findViewById(R.id.add_delete);
            result_holder.img_on = (ImageView) convertView
                    .findViewById(R.id.img_on);

            result_holder.img_on.setTag(array_address.get(position).getAddress_id());

            if(array_address.get(position).getDefult_add().equalsIgnoreCase("1")){
                result_holder.img_on.setImageResource(R.drawable.on);
            }else{
                result_holder.img_on.setImageResource(R.drawable.off);
            }

            result_holder.img_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    add_id= array_address.get(position).getAddress_id();

                    new default_address().execute();


                }
            });
            String final_address = "";
            if(array_address.get(position).getAddress_1().equalsIgnoreCase("")||array_address.get(position).getAddress_1().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = array_address.get(position).getAddress_1();
            }

            if(array_address.get(position).getAddress_2().equalsIgnoreCase("")||array_address.get(position).getAddress_2().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ array_address.get(position).getAddress_2();
            }

            if(array_address.get(position).getAddress_3().equalsIgnoreCase("")||array_address.get(position).getAddress_3().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+",\n"+ array_address.get(position).getAddress_3();
            }

            if(array_address.get(position).getLandmark().equalsIgnoreCase("")||array_address.get(position).getLandmark().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ array_address.get(position).getLandmark();
            }

            if(array_address.get(position).getCity().equalsIgnoreCase("")||array_address.get(position).getCity().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+",\n"+ array_address.get(position).getCity();
            }

            if(array_address.get(position).getPincode().equalsIgnoreCase("")||array_address.get(position).getPincode().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+"-"+ array_address.get(position).getPincode();
            }

            if(array_address.get(position).getState().equalsIgnoreCase("")||array_address.get(position).getState().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ array_address.get(position).getState();
            }

            if(array_address.get(position).getCountry().equalsIgnoreCase("")||array_address.get(position).getCountry().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ array_address.get(position).getCountry();
            }

            result_holder.tvaddress.setText(final_address);

            result_holder.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Saved_Address.this, Edit_Address.class);
                    i.putExtra("fname",array_address.get(position).getFirstName());
                    i.putExtra("mobile",array_address.get(position).getMobile());
                    i.putExtra("addId",array_address.get(position).getAddress_id());
                    i.putExtra("add1",array_address.get(position).getAddress_1());
                    i.putExtra("add2",array_address.get(position).getAddress_2());
                    i.putExtra("add3",array_address.get(position).getLandmark());
                    i.putExtra("pincode",array_address.get(position).getPincode());
                    i.putExtra("country",array_address.get(position).getAddress_3());
                    i.putExtra("state",array_address.get(position).getState_id());
                    i.putExtra("city",array_address.get(position).getCity_id());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });
            result_holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            Saved_Address.this);
                    // Setting Dialog Title
                    alertDialog.setTitle("Delete Address?");
                    // Setting Dialog Message
                    alertDialog
                            .setMessage("Are you sure you want to delete address?");


                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    add_id= array_address.get(position).getAddress_id();

                                 new delete_address().execute();

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

            return convertView;
        }

    }
    static class ResultHolder {


        TextView tvaddress;

        ImageView img_edit, img_delete,img_on;


    }
    public class delete_address extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Saved_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("address_id", add_id));
                //Log.e("user_id", uid);



                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Address/App_Remove_Address",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Saved_Address.this, "SERVER ERRER", Saved_Address.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();



                        Globals.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());
                        loadingView.dismiss();

                        new get_Address().execute();




                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class default_address extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Saved_Address.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", uid));
                parameters.add(new BasicNameValuePair("address_id", add_id));
                //Log.e("user_id", uid);



                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link+"Address/App_Set_Default_Addresses",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                   /* Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Saved_Address.this, "SERVER ERRER", Saved_Address.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();



                        Globals.CustomToast(Saved_Address.this, "" + Message, Saved_Address.this.getLayoutInflater());
                        loadingView.dismiss();

                        new get_Address().execute();




                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
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
}
