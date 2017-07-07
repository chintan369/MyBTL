package com.agraeta.user.btl;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Shooping_List extends AppCompatActivity {

    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    DatabaseHandler db;
    AppPrefs app;
    String user_id_main = new String();
    ArrayList<String> bean_listid = new ArrayList<String>();
    ArrayList<Bean_ShoppingList> bean_shopping = new ArrayList<Bean_ShoppingList>();
    ArrayList<String> bean_listname = new ArrayList<String>();
    String json = new String();
    Custom_ProgressDialog loadingView;
    ListView layout_product;
    int count = 0;
    String owner_id = new String();
    String u_id = new String();
    String listidfinal;
    TextView tv_groupname_edit;
    EditText et_add_groupname;
    Button btn_add_groupname;
    String name;
    ResultHolder result_holder;
    Dialog myDialog;
    LinearLayout.LayoutParams params;
    String newGroupname;
    Button btnaddnew;
    String role_id;
    String cartJSON="";
    boolean hasCartCallFinish=true;

    TextView txt;


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
        setContentView(R.layout.activity_shooping__list);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        params = new LinearLayout.LayoutParams(android.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        setActionBar();
        fetchID();

        setRefershData();
        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(Shooping_List.this);
                    role_id = app.getSubSalesId().toString();
                    user_id_main = app.getSalesPersonId().toString();
                }


            }

        }else{
            user_id_main ="";
        }
        new set_spinner_list().execute();
    }
    private void fetchID() {

        layout_product=(ListView)findViewById(R.id.layout_product);
        layout_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Shooping_List.this,Shopping_Product_view.class);
                app= new AppPrefs(Shooping_List.this);
                   /* app.setproduct_id(bean_shopping.get(position)
                            .getListid());*/
                app.setlist_id(bean_shopping.get(position).getListid());
                //Log.e("setlist_id",""+bean_shopping.get(position).getListid());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        btnaddnew=(Button)findViewById(R.id.btnaddnew);
        btnaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myDialog = new Dialog(Shooping_List.this);
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
                        if (et_add_groupname.getText().toString().equalsIgnoreCase("")
                                ) {


                        }
                        name = et_add_groupname.getText().toString();
                        new add_toShoppinglist().execute();
                    }
                });


                myDialog.setCancelable(true);
                myDialog.show();


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
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.VISIBLE);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
       /* TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(User_Profile.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size() + "");*/
         txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        app = new AppPrefs(Shooping_List.this);
        String qun =app.getCart_QTy();

        setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(Shooping_List.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else{
                    u_id=owner_id;
                }


            }

            List<NameValuePair> para=new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id",u_id));

            new GetCartByQty(para).execute();


        }else{
            app = new AppPrefs(Shooping_List.this);
            app.setCart_QTy("");
        }

        app = new AppPrefs(Shooping_List.this);
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
                Intent i = new Intent(Shooping_List.this, User_Profile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Shooping_List.this,MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(Shooping_List.this);
                app.setUser_notification("Shooping_List");
                Intent i = new Intent(Shooping_List.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(Shooping_List.this, User_Profile.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

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
                        Shooping_List.this, "");

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

                //Log.e("viewlist", "" + parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "ShoppingList/App_GetShoppingList", ServiceHandler.POST, parameters);

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

                    Globals.CustomToast(Shooping_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(Shooping_List.this, "" + Message, getLayoutInflater());

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");


                      /*  JSONObject jO = jObj.getJSONObject("data");
                        JSONObject jU = jO.getJSONObject("FlashMessage");


                        String mrquee= jU.getString("msg");
                        product_detail_marquee.setText(mrquee.toString());
                        //Log.e("mrquee",""+mrquee.toString());*/


                        JSONArray jmarquee_msg = jObj.getJSONArray("data");

                        for (int i = 0; i < jmarquee_msg.length(); i++) {
                            JSONObject jsonobject = jmarquee_msg.getJSONObject(i);


                            Bean_ShoppingList bean = new Bean_ShoppingList();
                            bean.setListid(jsonobject.getString("id"));
                            bean.setListname(jsonobject.getString("list_name"));

                            bean_shopping.add(bean);

                            //Log.e("bean_shopping", "" + bean_shopping.size());


                        }

                        loadingView.dismiss();

                        layout_product.setAdapter(new CustomResultAdapterList());
                        //setLayout(bean_shopping);

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
                        Shooping_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("user_id",user_id_main));
                parameters.add(new BasicNameValuePair("list_name",name));
                //Log.e("addto_shoppinglistserv", "" + parameters);

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

                    Globals.CustomToast(Shooping_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(Shooping_List.this, "" + Message, getLayoutInflater());

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(Shooping_List.this, "" + Message, getLayoutInflater());
                        myDialog.dismiss();

                        Intent i = new Intent(Shooping_List.this, Shooping_List.class);
                        startActivity(i);


                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }


        }
    }


    static class ResultHolder {

        TextView listname;

        ImageView img_view;
        ImageView img_edit;
        ImageView img_delete;


    }
    public class CustomResultAdapterList extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater)getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bean_shopping.size();
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

            if (convertView == null) {

                convertView = vi.inflate(R.layout.shopping_row, null);

            }

            result_holder.listname = (TextView) convertView
                    .findViewById(R.id.listname);
            result_holder.img_view = (ImageView) convertView
                    .findViewById(R.id.img_view);
            result_holder.img_edit = (ImageView) convertView
                    .findViewById(R.id.img_edit);
            result_holder.img_delete = (ImageView) convertView
                    .findViewById(R.id.img_delete);

            result_holder.img_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Shooping_List.this,Shopping_Product_view.class);
                    app= new AppPrefs(Shooping_List.this);
                   /* app.setproduct_id(bean_shopping.get(position)
                            .getListid());*/
                    app.setlist_id(bean_shopping.get(position).getListid());
                    //Log.e("setlist_id",""+bean_shopping.get(position).getListid());
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });
            result_holder.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*app = new AppPrefs(Shooping_List.this);
                    app.setproduct_id(bean_shopping.get(position)
                            .getListid());*/

                    myDialog = new Dialog(Shooping_List.this);
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

                    tv_groupname_edit.setText("Edit List Name");
                    et_add_groupname.setText(bean_shopping.get(position)
                            .getListname());

                    btn_add_groupname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (et_add_groupname.getText().toString().trim().equalsIgnoreCase("")) {
                                Globals.CustomToast(Shooping_List.this, "Please Insert Name", getLayoutInflater());

                            } else {
                                listidfinal=bean_shopping.get(position)
                                        .getListid();
                                newGroupname = et_add_groupname.getText().toString();
                                //Log.e("Add new name", "" + newGroupname);



                                new edit_shoppinglist_name().execute();

                            }
                        }
                    });


                    myDialog.setCancelable(true);
                    myDialog.show();


                    //  myDialog.dismiss();
                }
            });



            result_holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listidfinal=bean_shopping.get(position)
                            .getListid();
                    String namelist=bean_shopping.get(position)
                            .getListname();



                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            Shooping_List.this);
                    // Setting Dialog Title
                    alertDialog.setTitle("Delete " + namelist + " ?");
                    // Setting Dialog Messag
                    alertDialog
                            .setMessage("Are you sure you want to delete "+namelist+" List ?");


                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    new delete_shoppinglist().execute();

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


            result_holder.listname.setText(bean_shopping.get(position)
                    .getListname());








            return convertView;
        }


    }

    public class edit_shoppinglist_name extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Shooping_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("user_id",user_id_main));
                parameters.add(new BasicNameValuePair("list_id",listidfinal));
                parameters.add(new BasicNameValuePair("list_name",newGroupname));
                //Log.e("addto_shoppinglistserv", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "ShoppingList/App_EditShoppingList", ServiceHandler.POST, parameters);

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

                    Globals.CustomToast(Shooping_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(Shooping_List.this, "" + Message, getLayoutInflater());

                        loadingView.dismiss();

                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(Shooping_List.this, "" + Message, getLayoutInflater());
                        myDialog.dismiss();

                        Intent i = new Intent(Shooping_List.this, Shooping_List.class);
                        startActivity(i);


                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }


        }
    }
    public class delete_shoppinglist extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Shooping_List.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("user_id",user_id_main));
                parameters.add(new BasicNameValuePair("list_id",listidfinal));

                //Log.e("addto_shoppinglistserv", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "ShoppingList/App_DeleteShoppingList", ServiceHandler.POST, parameters);

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

                    Globals.CustomToast(Shooping_List.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(Shooping_List.this, "" + Message, getLayoutInflater());

                        loadingView.dismiss();

                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(Shooping_List.this, "" + Message, getLayoutInflater());


                        Intent i = new Intent(Shooping_List.this, Shooping_List.class);
                        startActivity(i);
                       // dialog.cancel();
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
        db = new DatabaseHandler(Shooping_List.this);

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
    public class GetCartByQty extends AsyncTask<Void, Void, String>{

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

                    Globals.CustomToast(Shooping_List.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);
                    String userStatus = jObj.getString("user_status");
                    if (userStatus.equals("0")) {
                        C.userInActiveDialog(Shooping_List.this);
                    }
                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(Shooping_List.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(Shooping_List.this);
                        app.setCart_QTy(""+qu);


                    }

                }


            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

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
        }
    }
}
