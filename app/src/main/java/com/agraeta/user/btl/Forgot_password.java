package com.agraeta.user.btl;

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
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Forgot_password extends AppCompatActivity {

    EditText etreset_mobile_number;

    Button btn_resetpassword;
    String email = new String();
    Custom_ProgressDialog loadingView;
    String json = new String();
    TextView txt_forget;

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
        setContentView(R.layout.activity_forgot_password);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        fetchID();
        setActionBar();

    }
    private void fetchID()
    {
        btn_resetpassword=(Button)findViewById(R.id.btn_resetpassword);
        etreset_mobile_number=(EditText)findViewById(R.id.etreset_mobile_number);
        txt_forget=(TextView)findViewById(R.id.txt_forget);
        txt_forget.setVisibility(View.GONE);
        btn_resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etreset_mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    Globals.CustomToast(Forgot_password.this, "Email ID is required", getLayoutInflater());

                }else {
                    email = etreset_mobile_number.getText().toString().trim();
                    new Send_forget_data().execute();
                }
              /*  Intent i = new Intent(Forgot_password.this,MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
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
        img_category.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Forgot_password.this, LogInPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Forgot_password.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Forgot_password.this,Main_CategoryPage.class);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Forgot_password.this,Notification.class);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Forgot_password.this,Cart.class);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public class Send_forget_data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Forgot_password.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {



                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("email_id", email));



                //Log.e("2", "" + email);

                //   Log.e("4", "" + parameters);
                http://app.nivida.in/agraeta/User/App_forgot_password
               json = new ServiceHandler().makeServiceCall(Globals.server_link + "User/App_forgot_password", ServiceHandler.POST, parameters);
                //json = new ServiceHandler().makeServiceCall("http://app.nivida.in/agraeta/User/App_forgot_password", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);

                return json;
            } catch (Exception e) {
                e.printStackTrace();
               // System.out.println("error1: " + e.toString());

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
                   /* Toast.makeText(LogInPage.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Forgot_password.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                         // Globals.CustomToast(Forgot_password.this, "" + Message, getLayoutInflater());
                        txt_forget.setVisibility(View.VISIBLE);
                        txt_forget.setText(Message);
                        //Toast.makeText(LogInPage.this,""+Message,Toast.LENGTH_LONG).show();
                        loadingView.dismiss();

                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(Forgot_password.this, "" + Message, getLayoutInflater());
                        // Toast.makeText(LogInPage.this,""+Message,Toast.LENGTH_LONG).show();

                        loadingView.dismiss();
                        txt_forget.setVisibility(View.VISIBLE);
                        txt_forget.setText(Message);
                        Intent i = new Intent(Forgot_password.this, MainPage_drawer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent i = new Intent(Forgot_password.this, LogInPage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }
}
