package com.agraeta.user.btl;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Add_to_shoppinglist extends AppCompatActivity {

    Button btn_addnewgroup, btn_addtoshopping, btn_add_groupname;
    Spinner spn_grouplist;
    Dialog myDialog;
    ArrayAdapter<String> spinnerAdapter;
    TextView tv_groupname_edit;
    String json = new String();
    Custom_ProgressDialog loadingView;
    EditText et_add_groupname;
    String spnid=new String();
    ArrayList<String> bean_listid = new ArrayList<String>();
    ArrayList<String> bean_listname = new ArrayList<String>();
    String newGroupname;
    String owner_id = new String();
    String u_id = new String();
    AppPrefs app;
    String pid;
    String user_id_main=new String();
    DatabaseHandler db;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    String role_id;

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
        setContentView(R.layout.activity_add_to_shoppinglist);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setActionBar();
        fetchID();
        app = new AppPrefs(Add_to_shoppinglist.this);

        pid = app.getshopping_pro_id().toString();


        setRefershData();
        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(Add_to_shoppinglist.this);
                    role_id = app.getSubSalesId().toString();
                    user_id_main = app.getSalesPersonId().toString();
                }


            }

        }else{
            user_id_main ="";
        }


        //Log.e("pid", "" + pid);
        new set_spinner_list().execute();

    }

    private void fetchID() {
        btn_addnewgroup = (Button) findViewById(R.id.btn_addnewgroup);
        btn_addtoshopping = (Button) findViewById(R.id.btn_addtoshopping);
        spn_grouplist = (Spinner) findViewById(R.id.spn_grouplist);

        /*spn_grouplist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnid = "" + spn_grouplist.getSelectedItemPosition();
                Log.e("spnid", "" + spnid.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        spn_grouplist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                spnid = bean_listid.get(position);
               // position_dstatename = dstatename.get(position);
                if (spnid.equalsIgnoreCase("0")) {

                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        btn_addnewgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myDialog = new Dialog(Add_to_shoppinglist.this);
                myDialog.getWindow();

                myDialog
                        .requestWindowFeature(Window.FEATURE_NO_TITLE);
                myDialog.setContentView(R.layout.shoppinglist_row);

                tv_groupname_edit = (TextView) myDialog
                        .findViewById(R.id.tv_groupname_edit);
                et_add_groupname = (EditText) myDialog
                        .findViewById(R.id.et_add_groupname);
                btn_add_groupname = (Button) myDialog
                        .findViewById(R.id.btn_add_groupname);


                btn_add_groupname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et_add_groupname.getText().toString().trim().equalsIgnoreCase("")) {
                            Globals.CustomToast(Add_to_shoppinglist.this, "Please Insert Name", getLayoutInflater());

                        } else {
                            newGroupname = et_add_groupname.getText().toString();
                            //Log.e("Add new name", "" + newGroupname);
                            app.setadd_newGroup(newGroupname.toString());
                            //app.setadd_newGroup(newGroupname.toString());
                            new add_newgroup().execute();


                        }
                    }
                });


                myDialog.setCancelable(true);
                myDialog.show();


                //  myDialog.dismiss();
            }
        });
        btn_addtoshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Globals.CustomToast(Add_to_shoppinglist.this, "" + spnid + ":" + pid, getLayoutInflater());
                new add_toShoppinglist().execute();
                //   Globals.CustomToast(Add_to_shoppinglist.this, "" + spnid + ":" + pid, getLayoutInflater());
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
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
       /*
        db = new DatabaseHandler(MainPage_drawer.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size() + "");*/
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.VISIBLE);
        app = new AppPrefs(Add_to_shoppinglist.this);
        String qun =app.getCart_QTy();

      /*  setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    app = new AppPrefs(Add_to_shoppinglist.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    app = new AppPrefs(Add_to_shoppinglist.this);
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
                    Globals.CustomToast(Add_to_shoppinglist.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(Add_to_shoppinglist.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(Add_to_shoppinglist.this);
                        app.setCart_QTy(""+qu);


                    }

                }
            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

        }else{
            app = new AppPrefs(Add_to_shoppinglist.this);
            app.setCart_QTy("");
        }*/

        app = new AppPrefs(Add_to_shoppinglist.this);
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
                app =new AppPrefs(Add_to_shoppinglist.this);
                if(app.getRef_Shopping().toString().equalsIgnoreCase("product_list")) {
                    Intent i = new Intent(Add_to_shoppinglist.this, Product_List.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if(app.getRef_Shopping().toString().equalsIgnoreCase("product_detail")){
                    Intent i = new Intent(Add_to_shoppinglist.this, BTLProduct_Detail.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if(app.getRef_Shopping().toString().equalsIgnoreCase("Search")){
                    Intent i = new Intent(Add_to_shoppinglist.this, Search.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(Add_to_shoppinglist.this);
                app.setUser_notification("Add_to_shoppinglist");
                Intent i = new Intent(Add_to_shoppinglist.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_to_shoppinglist.this,MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub

        app =new AppPrefs(Add_to_shoppinglist.this);
        if(app.getRef_Shopping().toString().equalsIgnoreCase("product_list")) {
            Intent i = new Intent(Add_to_shoppinglist.this, Product_List.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }else if(app.getRef_Shopping().toString().equalsIgnoreCase("product_detail")){
            Intent i = new Intent(Add_to_shoppinglist.this, BTLProduct_Detail.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }else if(app.getRef_Shopping().toString().equalsIgnoreCase("Search")){
            Intent i = new Intent(Add_to_shoppinglist.this, Search.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

    };


    public class set_spinner_list extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Add_to_shoppinglist.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("user_id", user_id_main));


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "ShoppingList/App_GetShoppingList", ServiceHandler.POST, parameters);

                //System.out.println("array: " + json);

                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


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

                    Globals.CustomToast(Add_to_shoppinglist.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(Add_to_shoppinglist.this, "" + Message, getLayoutInflater());
                        bean_listname.add("Select List");
                        bean_listid.add("0");
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        JSONArray jmarquee_msg = jObj.getJSONArray("data");

                        for (int i = 0; i < jmarquee_msg.length(); i++) {
                            JSONObject jsonobject = jmarquee_msg.getJSONObject(i);

                            String list_id = jsonobject.getString("id");
                            String list_name = jsonobject.getString("list_name");

                            bean_listid.add(list_id);
                            bean_listname.add(list_name);
                            //Log.e("", "" + bean_listname.size());
                            if( bean_listname.size() == 0){
                                Globals.CustomToast(Add_to_shoppinglist.this, "Please First add shopping Group", getLayoutInflater());
                            }else {

                                spinnerAdapter = new ArrayAdapter<String>(Add_to_shoppinglist.this, R.layout.textview, bean_listname);
                                spn_grouplist.setAdapter(spinnerAdapter);
                            }

                        }

                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }


    public class add_newgroup extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Add_to_shoppinglist.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("user_id", user_id_main));
                parameters.add(new BasicNameValuePair("list_name", app.getadd_newGroup()));
                //Log.e("add_newgroup_webserv", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "ShoppingList/App_AddShoppingList", ServiceHandler.POST, parameters);

                //System.out.println("array: " + json);

                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {

                    Globals.CustomToast(Add_to_shoppinglist.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(Add_to_shoppinglist.this, "" + Message, getLayoutInflater());

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(Add_to_shoppinglist.this, "" + Message, getLayoutInflater());
                        myDialog.dismiss();

                        Intent i = new Intent(Add_to_shoppinglist.this, Add_to_shoppinglist.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                      /*  JSONObject jO = jObj.getJSONObject("data");
                        JSONObject jU = jO.getJSONObject("FlashMessage");


                        String mrquee= jU.getString("msg");
                        product_detail_marquee.setText(mrquee.toString());
                        Log.e("mrquee",""+mrquee.toString());*/


                       /* JSONArray jmarquee_msg = jObj.getJSONArray("data");

                        for (int i = 0; i < jmarquee_msg.length(); i++) {
                            JSONObject jsonobject = jmarquee_msg.getJSONObject(i);

                            String list_id= jsonobject.getString("id");
                            String list_name= jsonobject.getString("list_name");

                            bean_listid.add(list_id);
                            bean_listname.add(list_name);
                            Log.e("", "" + bean_listname.size());
                            spinnerAdapter = new ArrayAdapter<String>(Add_to_shoppinglist.this, android.R.layout.simple_spinner_item, bean_listname);
                            spn_grouplist.setAdapter(spinnerAdapter);



                        }*/

                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }


        }
    }
    public class add_toShoppinglist extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Add_to_shoppinglist.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("list_id",spnid));
                parameters.add(new BasicNameValuePair("product_id", pid));
                //Log.e("addto_shoppinglistserv", "" + parameters);
              //  Log.e("spnid", "" + spnid);
                //Log.e("pid", "" + pid);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "ShoppingListProduct/App_AddShoppingListProduct", ServiceHandler.POST, parameters);

                //System.out.println("array: " + json);

                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {

                    Globals.CustomToast(Add_to_shoppinglist.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(Add_to_shoppinglist.this, "" + Message, getLayoutInflater());
                        //Log.e("message toast",""+Message);

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(Add_to_shoppinglist.this, "" + Message, getLayoutInflater());
                        app =new AppPrefs(Add_to_shoppinglist.this);
                        if(app.getRef_Shopping().toString().equalsIgnoreCase("product_list")) {
                            Intent i = new Intent(Add_to_shoppinglist.this, Product_List.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }else if(app.getRef_Shopping().toString().equalsIgnoreCase("product_detail")){
                            Intent i = new Intent(Add_to_shoppinglist.this, BTLProduct_Detail.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }else if(app.getRef_Shopping().toString().equalsIgnoreCase("Search")){
                            Intent i = new Intent(Add_to_shoppinglist.this, Search.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        // myDialog.dismiss();
                      /*  JSONObject jO = jObj.getJSONObject("data");
                        JSONObject jU = jO.getJSONObject("FlashMessage");


                        String mrquee= jU.getString("msg");
                        product_detail_marquee.setText(mrquee.toString());
                        Log.e("mrquee",""+mrquee.toString());*/


                       /* JSONArray jmarquee_msg = jObj.getJSONArray("data");

                        for (int i = 0; i < jmarquee_msg.length(); i++) {
                            JSONObject jsonobject = jmarquee_msg.getJSONObject(i);

                            String list_id= jsonobject.getString("id");
                            String list_name= jsonobject.getString("list_name");

                            bean_listid.add(list_id);
                            bean_listname.add(list_name);
                            Log.e("", "" + bean_listname.size());
                            spinnerAdapter = new ArrayAdapter<String>(Add_to_shoppinglist.this, android.R.layout.simple_spinner_item, bean_listname);
                            spn_grouplist.setAdapter(spinnerAdapter);



                        }*/

                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }


        }
    }
    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Add_to_shoppinglist.this);

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
}
