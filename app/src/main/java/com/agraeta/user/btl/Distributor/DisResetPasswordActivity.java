package com.agraeta.user.btl.Distributor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.ImageLoader;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DisResetPasswordActivity extends AppCompatActivity {
    Button btn_change;
    String oldpassword,newpassword,sid,ID;
    EditText etold_pass,etnew_pass,etconf_pass;
    String json =new String();
    ImageLoader imageloader;
    //ArrayList<Bean_Dis_Sales> disSalesList = new ArrayList<Bean_Dis_Sales>();
    Bean_Dis_Sales disSalesList;
    Custom_ProgressDialog loadingView;
    DatabaseHandler db;
    AppPrefs app;
    String profile_array="";

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
        setContentView(R.layout.activity_dis_reset_password);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent i=getIntent();
        disSalesList=(Bean_Dis_Sales) i.getSerializableExtra("salesPerson");
       // app=new AppPrefs(getApplicationContext());
        //sid=app.getSalesId();

        setActionBar();

        fetchID();

        //setRefershData();
    }

//    private void setRefershData() {
//        // TODO Auto-generated method stub
//        user_data.clear();
//        db = new DatabaseHandler(DisResetPasswordActivity.this);
//
//        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();
//
//        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();
//
//        for (int i = 0; i < user_array_from_db.size(); i++) {
//
//            int userid =user_array_from_db.get(i).getId();
//            uid=user_array_from_db.get(i).getUser_id();
//            String email_id = user_array_from_db.get(i).getEmail_id();
//            String phone_no =user_array_from_db.get(i).getPhone_no();
//            String f_name = user_array_from_db.get(i).getF_name();
//            String l_name = user_array_from_db.get(i).getL_name();
//            String password = user_array_from_db.get(i).getPassword();
//            String gender = user_array_from_db.get(i).getGender();
//            String usertype = user_array_from_db.get(i).getUser_type();
//            String login_with = user_array_from_db.get(i).getLogin_with();
//            String str_rid = user_array_from_db.get(i).getStr_rid();
//            String add1 = user_array_from_db.get(i).getAdd1();
//            String add2 = user_array_from_db.get(i).getAdd2();
//            String add3 = user_array_from_db.get(i).getAdd3();
//            String landmark = user_array_from_db.get(i).getLandmark();
//            String pincode = user_array_from_db.get(i).getPincode();
//            String state_id = user_array_from_db.get(i).getState_id();
//            String state_name = user_array_from_db.get(i).getState_name();
//            String city_id = user_array_from_db.get(i).getCity_id();
//            String city_name = user_array_from_db.get(i).getCity_name();
//            String str_response = user_array_from_db.get(i).getStr_response();
//
//
//            Bean_User_data contact = new Bean_User_data();
//            contact.setId(userid);
//            contact.setUser_id(uid);
//            contact.setEmail_id(email_id);
//            contact.setPhone_no(phone_no);
//            contact.setF_name(f_name);
//            contact.setL_name(l_name);
//            contact.setPassword(password);
//            contact.setGender(gender);
//            contact.setUser_type(usertype);
//            contact.setLogin_with(login_with);
//            contact.setStr_rid(str_rid);
//            contact.setAdd1(add1);
//            contact.setAdd2(add2);
//            contact.setAdd3(add3);
//            contact.setLandmark(landmark);
//            contact.setPincode(pincode);
//            contact.setState_id(state_id);
//            contact.setState_name(state_name);
//            contact.setCity_id(city_id);
//            contact.setCity_name(city_name);
//            contact.setStr_response(str_response);
//            user_data.add(contact);
//
//
//        }
//        db.close();
//    }

    private void fetchID() {
        //etold_pass=(EditText)findViewById(R.id.et_reset_newpassword);
        etnew_pass=(EditText)findViewById(R.id.et_reset_confirm_newpassword);
        etconf_pass=(EditText)findViewById(R.id.et_change_confirm_newpassword);
        btn_change=(Button)findViewById(R.id.btn_reset);


        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (etold_pass.getText().toString().trim().equalsIgnoreCase("")) {
//                    Globals.CustomToast(DisResetPasswordActivity.this, "Old Password is compulsory", getLayoutInflater());
//                    // Toast.makeText(Business_Registration.this,"Please enter password",Toast.LENGTH_LONG).show();
//                } else if (etold_pass.length() < 6) {
//                    //  Toast.makeText(Business_Registration.this,"Password minimum 6 character required",Toast.LENGTH_LONG).show();
//                    Globals.CustomToast(DisResetPasswordActivity.this, "Old Password minimum 6 character required", getLayoutInflater());
//                }

                String newpass=etnew_pass.getText().toString().trim();
                String confirmpass=etconf_pass.getText().toString().trim();

                 if (newpass.equalsIgnoreCase("")) {
                    Globals.CustomToast(DisResetPasswordActivity.this, "New Password is compulsory", getLayoutInflater());
                    // Toast.makeText(Business_Registration.this,"Please enter password",Toast.LENGTH_LONG).show();
                } else if (newpass.length() < 6) {
                    //  Toast.makeText(Business_Registration.this,"Password minimum 6 character required",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(DisResetPasswordActivity.this, "New Password minimum 6 character required", getLayoutInflater());
                } else if (confirmpass.equalsIgnoreCase("")) {
                    //Toast.makeText(Business_Registration.this,"Please enter confirm password",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(DisResetPasswordActivity.this, "Please enter confirm password", getLayoutInflater());
                } else if (!etconf_pass.getText().toString().equals(etnew_pass.getText().toString())) {
                    //  Toast.makeText(Business_Registration.this,"Confirm password did not match",Toast.LENGTH_LONG).show();
                    Globals.CustomToast(DisResetPasswordActivity.this, "Confirm password did not match", getLayoutInflater());
                } else {
                     app = new AppPrefs(DisResetPasswordActivity.this);
                     //Log.e("1111",""+app.getSalesId());
                     ID=app.getSalesId();
                     //Log.e("IDDD",""+ID);
                     newpassword=etnew_pass.getText().toString();
                     new update_password().execute();

                    //oldpassword=etold_pass.getText().toString();


                    /* try {
                         JSONArray jObjectMain1 = new JSONArray();
                         // JSONObject jObjectData = new JSONObject();

                         for (int i = 0; i < 1; i++) {
                             JSONObject jObjectData = new JSONObject();

                             app = new AppPrefs(DisResetPasswordActivity.this);
                             ID=app.getSalesId();
                             jObjectData.put("id",ID);
                            // jObjectData.put("id",String.valueOf(disSalesList.getSales_id()));
                                Log.e("SUB SALES ID",""+ID);
                             jObjectData.put("password", newpass);

                             jObjectMain1.put(jObjectData);
                             profile_array = jObjectMain1.toString();

                             new update_password().execute();
                         }

                         System.out.println("" + profile_array);
                     } catch (JSONException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }*/


                    //new update_password().execute();

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
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        /*TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(User_Profile.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size() + "");*/
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisResetPasswordActivity.this,MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

      /*  img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Change_password.this,Main_CategoryPage.class);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Change_password.this,Notification.class);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Change_password.this,Cart.class);
                startActivity(i);
            }
        });*/
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        finish();

    };

    public class update_password extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        DisResetPasswordActivity.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("id",ID));

                //parameters.add(new BasicNameValuePair("old_password",oldpassword ));
                parameters.add(new BasicNameValuePair("password",newpassword ));



                //Log.e("id", "200");
                //Log.e("newpassword", "" + newpassword);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Rest_Password",ServiceHandler.POST,parameters);

                //System.out.println("array: " + json);
                return json;
            }

            catch (Exception e)
            {
                e.printStackTrace();
              //  System.out.println("error1: " + e.toString());

                return json;

            }
            }
          /*  try {

                JSONArray jArray=new JSONArray(profile_array);
                JSONObject profile=jArray.getJSONObject(0);

                app = new AppPrefs(DisResetPasswordActivity.this);
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //parameters.add(new BasicNameValuePair("sales_person_id",profile_array));
                parameters.add(new BasicNameValuePair("id", app.getSalesId().toString()));
                parameters.add(new BasicNameValuePair("password", profile.getString("password")));

                Log.e("11111","" + app.getSalesId().toString());
                Log.e("22222","" + profile.getString("password"));

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"User/App_Rest_Password",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("arrayedited: " + json);
                return json;

            }

            catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }

        }*/

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(DisResetPasswordActivity.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(DisResetPasswordActivity.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);
                        String Message = jObj.getString("message");
                        Globals.CustomToast(DisResetPasswordActivity.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                        /*Intent i = new Intent(DisResetPasswordActivity.this, DisSalesListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        i.putExtra("salesPerson",salesPerson);
                        startActivity(i);*/
                        onBackPressed();


                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }
}
