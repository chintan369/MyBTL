package com.agraeta.user.btl;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ServiceCenter extends Fragment {

    Spinner spnstate,spncity;
    EditText et_pincode;
    Button btn_search,btn_filter;
    ListView layout_display;
    TextView tvslectcity,tvpincode,tvstate;
    String position_state = new String();
    String position_city = new String();
    String json = new String();
    String position_statename = new String();
    String position_cityname = new String();
    ResultHolder result_holder;
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    int count = 0;
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    Custom_ProgressDialog loadingView;
    ArrayList<Bean_WhereToBuy> bean_wheretobuy = new ArrayList<Bean_WhereToBuy>();
    String pincode_final;
    AppPrefs apps;
    ImageView mapImg;
    LinearLayout.LayoutParams params;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         rootView = inflater.inflate(R.layout.activity_service_center, container, false);
        apps = new AppPrefs(getActivity());
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        params = new LinearLayout.LayoutParams(android.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        //  setActionBar();
        fetchID();

        return  rootView;
    }

    private void fetchID()
    {
        spnstate=(Spinner)rootView.findViewById(R.id.spnstate);
        spncity=(Spinner)rootView.findViewById(R.id.spncity);
        btn_search=(Button)rootView.findViewById(R.id.btn_search);
        et_pincode=(EditText)rootView.findViewById(R.id.et_pincode);
        layout_display=(ListView)rootView.findViewById(R.id.layout_display);
        tvslectcity=(TextView)rootView.findViewById(R.id.tvslectcity);
        tvstate=(TextView)rootView.findViewById(R.id.tvstate);
        tvpincode=(TextView)rootView.findViewById(R.id.tvpincode);
        //mapImg=(ImageView)rootView.findViewById(R.id.mapImg);
        btn_filter=(Button)rootView.findViewById(R.id.btn_filter);
        stateid.clear();
        statename.clear();
        cityid.clear();
        cityname.clear();


        new send_state_Data().execute();

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateid.clear();
                statename.clear();
                cityid.clear();
                cityname.clear();
                et_pincode.setText("");
                btn_filter.setVisibility(View.GONE);
                spnstate.setVisibility(View.VISIBLE);
                spncity.setVisibility(View.GONE);
                btn_search.setVisibility(View.VISIBLE);
                et_pincode.setVisibility(View.GONE);
                layout_display.setVisibility(View.GONE);
                tvslectcity.setVisibility(View.GONE);
                tvstate.setVisibility(View.VISIBLE);
                tvpincode.setVisibility(View.GONE);
                new send_state_Data().execute();
            }
        });

        spnstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                position_state = stateid.get(position);
                position_statename = statename.get(position);
                //spncity.setVisibility(View.VISIBLE);

                if (position_state.equalsIgnoreCase("0")) {
                    //  Globals.CustomToast(getActivity().getApplicationContext(), "Please Select State", getActivity().getLayoutInflater());
                } else {
                    tvslectcity.setVisibility(View.VISIBLE);
                  //  tvpincode.setVisibility(View.VISIBLE);
                    spncity.setVisibility(View.VISIBLE);
                    new send_city_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spncity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if (position_city.equalsIgnoreCase("0")) {
                    //Globals.CustomToast(getActivity().getApplicationContext(), "Please Select City", getActivity().getLayoutInflater());
                } else {
                    et_pincode.setText("");

                    et_pincode.setVisibility(View.VISIBLE);
                    tvpincode.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position_state.equalsIgnoreCase("0")){
                    Globals.CustomToast(getActivity().getApplicationContext(), "Please Select State", getActivity().getLayoutInflater());
                }else if(position_city.equalsIgnoreCase("0")){
                    Globals.CustomToast(getActivity().getApplicationContext(), "Please Select City", getActivity().getLayoutInflater());
                }else if (et_pincode.length() < 6 && !et_pincode.getText().toString().equalsIgnoreCase("")) {

                    Globals.CustomToast(getActivity(), "Pincode 6 character required", getActivity().getLayoutInflater());
                }
               /* else if (et_pincode.getText().toString().equalsIgnoreCase("")) {

                    Globals.CustomToast(getActivity().getApplicationContext(), "Please Enter Pincode", getActivity().getLayoutInflater());
                } else if(et_pincode.length() < 6){
                    Globals.CustomToast(getActivity().getApplicationContext(), "Pincode 6 character required",  getActivity().getLayoutInflater());
                }*/else {
                    pincode_final = et_pincode.getText().toString();

                    btn_filter.setVisibility(View.VISIBLE);
                    spnstate.setVisibility(View.GONE);
                    spncity.setVisibility(View.GONE);
                    btn_search.setVisibility(View.GONE);
                    et_pincode.setVisibility(View.GONE);

                    tvslectcity.setVisibility(View.GONE);
                    tvstate.setVisibility(View.GONE);
                    tvpincode.setVisibility(View.GONE);

                    new get_wheretobuydetails().execute();
                   // mapImg.setVisibility(View.VISIBLE);
                }


            }
        });
    }

  /*  private void setActionBar() {

        // TODO Auto-generated method stub
        android.support.v7.app.ActionBar mActionBar = ;
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        //  ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);

        img_cart.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.back_min);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Business_Registration.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Cart1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
    }*/

    private void setLayout(ArrayList<Bean_WhereToBuy> str) {
        // TODO Auto-generated method stub

        layout_display.removeAllViews();

        //Log.e("str.size", "" + str.size());

        for (int ij = 0; ij < str.size(); ij++) {

            LinearLayout lmain = new LinearLayout(getActivity());
            //  params.setMargins(7, 7, 7, 7);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            lmain.setPadding(2, 2, 2, 2);

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams vi = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 1);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    200, 200, 1.0f);
            LinearLayout l1 = new LinearLayout(getActivity());

            l1.setLayoutParams(par1);
            l1.setPadding(5, 5, 5, 5);
            l1.setOrientation(LinearLayout.VERTICAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(getActivity());
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 5, 5, 5);

            final LinearLayout l2 = new LinearLayout(getActivity());
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 5, 5, 5);
            l2.setVisibility(View.GONE);

            count = ij;
            final ImageView img_a = new ImageView(getActivity());
            final TextView tv_brachname = new TextView(getActivity());
            final TextView tv_address1 = new TextView(getActivity());
            final TextView tvaddress2 = new TextView(getActivity());
            final View viewline = new View(getActivity());

            tv_brachname.setPadding(5, 5, 5, 5);
            tv_address1.setPadding(5, 2, 5, 2);
            tvaddress2.setPadding(5, 2, 2, 5);
            tv_brachname.setTextColor(Color.BLACK);
            tv_address1.setTextColor(Color.BLACK);
            tvaddress2.setTextColor(Color.BLACK);
            viewline.setLayoutParams(vi);
            viewline.setBackgroundColor(Color.BLACK);

            // img_a.setImageResource(str.get(count));
            //Log.e("count", "" + count);
            //Log.e("img", "" + str.get(count).getId());
            try {


                String branch_name = str.get(count).getBranch_name();
                String address_1 = str.get(count).getAddress_1() + "," + str.get(count).getAddress_2();
                String address_2 = str.get(count).getCity() + "," + str.get(count).getState() + "," + str.get(count).getPincode();

                //Log.e("branchname", "" + branch_name);
                //Log.e("address_1", "" + address_1);
                //Log.e("address_2", "" + address_2);


                tv_brachname.setText(branch_name);
                tv_address1.setText(address_1);
                tvaddress2.setText(address_2);
            } catch (NullPointerException e) {
                //Log.e("Error", "" + e);
            }


            par21.setMargins(5, 5, 5, 5);

            img_a.setLayoutParams(par21);
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            //   l1.addView(img_a);
            l1.addView(tv_brachname);
            l1.addView(tv_address1);
            l1.addView(tvaddress2);
            l1.addView(viewline);




           /* img_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppPrefs app= new AppPrefs(getActivity());

                    //Log.e("url_tonext_page",url);
                    Intent i = new Intent(getActivity(),Video_View_Activity.class);
                    app.setyoutube_api(url);
                    startActivity(i);
                }
            });*/
            lmain.addView(l1);

            layout_display.addView(lmain);

        }

    }

    static class ResultHolder {


        TextView tvaddress;

        ImageView img_edit, img_delete;


    }

    public class send_state_Data extends AsyncTask<Void,Void,String>
    {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        getActivity(), "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("country_id", "1"));



                //Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"State/App_GetState_Selected",ServiceHandler.POST,parameters);
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
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        // Globals.CustomToast(getActivity(),""+Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            statename.add("Select State");
                            stateid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                statename.add(sname);
                                stateid.add(sId);


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, statename);
                                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnstate.setAdapter(adapter_state);

                            }
                        }
                        loadingView.dismiss();
                        // new send_city_Data().execute();
                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class send_city_Data extends AsyncTask<Void,Void,String>
    {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        getActivity(), "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("state_id",""+ position_state));

                //Log.e("state_id", "" + position_state);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"City/App_GetCity_Selected",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
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
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(),""+Message, getActivity().getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        //Log.e("result_1", "" + result_1);
                        JSONObject jobj = new JSONObject(result_1);
                        cityname.clear();
                        cityid.clear();
                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            cityname.add("Select City");
                            cityid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                cityname.add(cname);
                                cityid.add(cId);


                                ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, cityname);
                                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spncity.setAdapter(adapter_city);

                            }
                        }
                        loadingView.dismiss();

                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class get_wheretobuydetails extends AsyncTask<Void,Void,String>
    {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        getActivity(), "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("state_id",""+ position_state));
                parameters.add(new BasicNameValuePair("city_id",""+ position_city));
                //parameters.add(new BasicNameValuePair("pincode",""+ pincode_final));
                parameters.add(new BasicNameValuePair("pincode",""+ pincode_final));




                //Log.e("where to buy web", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"ServiceCenter/App_GetServiceCenter",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(Globals.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);

                Log.e("parameters", "---->" + parameters);
                Log.e("json", json);
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
            loadingView.dismiss();
            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(getActivity(), "SERVER ERRER", getActivity().getLayoutInflater());


                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(getActivity(),""+Message, getActivity().getLayoutInflater());

                    } else {

                        Log.e("Testing1", "Testing");

                            JSONArray categories = jObj.getJSONArray("data");
                            bean_wheretobuy.clear();
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = categories.getJSONObject(i);

                                Log.e("Testing2", "Testing");


                                Bean_WhereToBuy bean = new Bean_WhereToBuy();
                                bean.setId(catObj.getString("id"));
                                Log.e("id", "-->" + catObj.getString("id"));
                                Log.e("id1", "-->" + bean.getId());
                                bean.setBranch_name(catObj.getString("branch_name"));
                                bean.setPincode(catObj.getString("pincode"));
                                bean.setAddress_1(catObj.getString("address_1"));
                                //  bean.setAddress_2(catObj.getString("address_2"));
                                bean.setCity(catObj.getString("city"));
                                bean.setState(catObj.getString("state"));
                                bean.setLatitude(catObj.getString("latitude"));
                                bean.setLongitude(catObj.getString("longitude"));



                                bean_wheretobuy.add(bean);

                                Log.e("getDatas", "--->" + new Gson().toJson(bean_wheretobuy));


                            }

                        Log.e("Testing3", "Testing");

                        layout_display.setVisibility(View.VISIBLE);
                        CustomResultAdapterDoctor customResultAdapterDoctor = new CustomResultAdapterDoctor();
                        layout_display.setAdapter(customResultAdapterDoctor);
                        customResultAdapterDoctor.notifyDataSetChanged();
                        Log.e("finally", "set");


                      //  setLayout(bean_wheretobuy);
                    }
                }
            }catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class CustomResultAdapterDoctor extends BaseAdapter {

        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return bean_wheretobuy.size();
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

                convertView = vi.inflate(R.layout.address_row, null);

            }

            Log.e("weareHere", "weare");

            result_holder.tvaddress = (TextView) convertView
                    .findViewById(R.id.address);

            result_holder.img_edit = (ImageView) convertView
                    .findViewById(R.id.add_edit);
            result_holder.img_delete = (ImageView) convertView
                    .findViewById(R.id.add_delete);

            result_holder.img_edit.setImageResource(R.drawable.glob);
            result_holder.img_delete.setVisibility(View.GONE);
            result_holder.img_edit.setVisibility(View.GONE);

            String final_address = "";
            if(bean_wheretobuy.get(position).getBranch_name().equalsIgnoreCase("")||bean_wheretobuy.get(position).getBranch_name().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = bean_wheretobuy.get(position).getBranch_name();
            }

            if(bean_wheretobuy.get(position).getAddress_1().equalsIgnoreCase("")||bean_wheretobuy.get(position).getAddress_1().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+",\n"+ bean_wheretobuy.get(position).getAddress_1();
            }

            if(bean_wheretobuy.get(position).getAddress_2().equalsIgnoreCase("")||bean_wheretobuy.get(position).getAddress_2().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ bean_wheretobuy.get(position).getAddress_2();
            }



            if(bean_wheretobuy.get(position).getCity().equalsIgnoreCase("")||bean_wheretobuy.get(position).getCity().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+",\n"+ bean_wheretobuy.get(position).getCity();
            }

            if(bean_wheretobuy.get(position).getPincode().equalsIgnoreCase("")||bean_wheretobuy.get(position).getPincode().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+"-"+ bean_wheretobuy.get(position).getPincode();
            }

            if(bean_wheretobuy.get(position).getState().equalsIgnoreCase("")||bean_wheretobuy.get(position).getState().equalsIgnoreCase("null"))
            {

            }
            else
            {
                final_address = final_address+", "+ bean_wheretobuy.get(position).getState();
            }



            result_holder.tvaddress.setText(final_address);

            result_holder.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity(), Map_Address.class);
                    i.putExtra("lat",bean_wheretobuy.get(position).getLatitude());
                    i.putExtra("log",bean_wheretobuy.get(position).getLongitude());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            });


            return convertView;
        }

    }


}