package com.agraeta.user.btl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.RegisteredUserData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class My_Quatation_Info extends AppCompatActivity {
    AppPrefs app;
    ArrayList<Bean_Quotation> bean_quo = new ArrayList<Bean_Quotation>();
    DatabaseHandler db;
    ArrayList<Bean_Quotation> bean_quotation = new ArrayList<Bean_Quotation>();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ArrayList<Bean_Quotation_Info> quotation_info = new ArrayList<Bean_Quotation_Info>();
    ListView list_quatation_info;
    TextView txt_quotation;
    CustomResultAdapter adapter;
    Custom_ProgressDialog loadingView;
    String user_id_main = new String();
    String role_id = new String();
    String json = new String();
    ProgressDialog pDialog;
    int current_position = -1;
    final File myDir = new File("/sdcard/BTL/Quotation/");
    String filesToSend = "";
    String owner_id = new String();
    String u_id = new String();
    String cartJSON = "";
    boolean hasCartCallFinish = true;

    TextView txt;

    int currentPage = 1;
    int totalPages = 1;


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
        setContentView(R.layout.activity_my_quatation_info);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        app = new AppPrefs(My_Quatation_Info.this);
        setRefershData();
        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                Log.e("userRole", user_id_main + " " + role_id);

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {
                    role_id = app.getSubSalesId();
                    user_id_main = app.getSalesPersonId();
                }

            }

        } else {
            user_id_main = "";
        }
        setActionBar();
        fetchId();
    }

    private void fetchId() {


        list_quatation_info = (ListView) findViewById(R.id.list_quatation_info);

        list_quatation_info.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastVisiblePosition = list_quatation_info.getLastVisiblePosition();
                int totalItems = quotation_info.size() - 1;

                if (currentPage < totalPages) {
                    if (lastVisiblePosition == totalItems) {
                        currentPage++;
                        new get_quotation().execute();
                    }
                }
            }
        });

        txt_quotation = (TextView) findViewById(R.id.txt_quotation);
      /*  if(user_data.size() != 0) {*/
        new get_quotation().execute();


    }

    public class get_quotation extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        My_Quatation_Info.this, "");

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

                parameters.add(new BasicNameValuePair("role_id", role_id));
                parameters.add(new BasicNameValuePair("page", String.valueOf(currentPage)));

                Log.e("params", parameters.toString());


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Quotation/App_GetQuotation", ServiceHandler.POST, parameters);

                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                // System.out.println("error1: " + e.toString());

                return json;

            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {


                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(My_Quatation_Info.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    Boolean date = jObj.getBoolean("status");

                    if (!date) {
                        String Message = jObj.getString("message");

                        Globals.CustomToast(My_Quatation_Info.this, "" + Message, getLayoutInflater());
                        list_quatation_info.setVisibility(View.GONE);
                        txt_quotation.setVisibility(View.VISIBLE);

                        loadingView.dismiss();
                    } else {

                        JSONObject jobj = new JSONObject(result_1);


                        if (jobj != null) {

                            totalPages = jObj.getInt("total_pages");
                            JSONArray jsonArray = jObj.optJSONArray("data");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONObject jquo = jsonObject.getJSONObject("Quotation");
                                JSONObject jquser = jsonObject.getJSONObject("User");
                                JSONObject Distributor = jquser.getJSONObject("Distributor");
                                JSONObject Owner = jsonObject.getJSONObject("Owner");


                                Bean_Quotation_Info bean = new Bean_Quotation_Info();

                                bean.setTitle(jquo.getString("title"));

                                bean.setFullname(jquser.getString("first_name") + " " + jquser.getString("last_name"));
                                bean.setEmailto(jquo.getString("email_id"));
                                bean.setEmailcc(jquo.getString("email_cc"));

                                bean.setDate(jquo.getString("created"));
                                bean.setDownload(jquo.getString("file_path"));

                                bean.setFirmName(Distributor.getString("firm_name"));
                                bean.setOwnerName(Owner.getString("first_name") + " " + Owner.getString("last_name"));

                                quotation_info.add(bean);
                            }
                        }

                        loadingView.dismiss();
                        if (quotation_info.size() == 0) {
                            list_quatation_info.setVisibility(View.GONE);
                            txt_quotation.setVisibility(View.VISIBLE);
                        } else {

                            list_quatation_info.setVisibility(View.VISIBLE);
                            txt_quotation.setVisibility(View.GONE);
                            adapter = new CustomResultAdapter();
                            adapter.notifyDataSetChanged();
                            list_quatation_info.setAdapter(adapter);
                        }
                    }

                }
            } catch (Exception j) {
                j.printStackTrace();
            }

        }
    }

    public class CustomResultAdapter extends BaseAdapter {
        //int current_position = -1;
        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return quotation_info.size();
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

            // result_holder = new ResultHolder();


            // if (convertView == null) {

            convertView = vi.inflate(R.layout.uo_info_row, null);


            //   }
            final TextView title = (TextView) convertView.findViewById(R.id.t_title);
            final TextView name = (TextView) convertView.findViewById(R.id.t_name);
            final TextView eto = (TextView) convertView.findViewById(R.id.t_emailto);
            final TextView ecc = (TextView) convertView.findViewById(R.id.t_emailcc);
            final TextView date = (TextView) convertView.findViewById(R.id.t_date);
            final TextView txt_quotationFor = (TextView) convertView.findViewById(R.id.txt_quotationFor);
            final TextView txt_takenBy = (TextView) convertView.findViewById(R.id.txt_takenBy);
            final ImageView img_reOrderAll=(ImageView) convertView.findViewById(R.id.img_reOrderAll);

            ImageView pdf = (ImageView) convertView.findViewById(R.id.im_pdf);

            title.setText(quotation_info.get(position).getTitle());
            name.setText(quotation_info.get(position).getFullname());
            eto.setText(quotation_info.get(position).getEmailto());

            if (quotation_info.get(position).getEmailcc().toString().equalsIgnoreCase("") || quotation_info.get(position).getEmailcc().toString().equalsIgnoreCase(null)) {
                ecc.setText("N/A");
            } else {
                ecc.setText(quotation_info.get(position).getEmailcc());
            }
            date.setText(quotation_info.get(position).getDate());

            if (quotation_info.get(position).getFirmName().replace("null", "").trim().isEmpty()) {
                txt_quotationFor.setText(quotation_info.get(position).getFirmName());
            } else {
                txt_quotationFor.setText(quotation_info.get(position).getFullname());
            }

            txt_takenBy.setText(quotation_info.get(position).getOwnerName());

            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current_position = position;
                    new DownloadFileFromURL().execute();
                }
            });

            img_reOrderAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();


        }
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
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);

        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.VISIBLE);
        frame.setVisibility(View.VISIBLE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        app = new AppPrefs(My_Quatation_Info.this);
        String qun = app.getCart_QTy();

        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(My_Quatation_Info.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                } else {
                    u_id = owner_id;
                }


            }

            List<NameValuePair> para = new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id", u_id));

            new GetCartByQty(para).execute();


        } else {
            app = new AppPrefs(My_Quatation_Info.this);
            app.setCart_QTy("");
        }

        app = new AppPrefs(My_Quatation_Info.this);
        String qu1 = app.getCart_QTy();
        if (qu1.equalsIgnoreCase("0") || qu1.equalsIgnoreCase("")) {
            txt.setVisibility(View.GONE);
            txt.setText("");
        } else {
            if (Integer.parseInt(qu1) > 999) {
                txt.setText("999+");
                txt.setVisibility(View.VISIBLE);
            } else {
                txt.setText(qu1 + "");
                txt.setVisibility(View.VISIBLE);
            }
        }
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(My_Quatation_Info.this);
                app.setquo("1");
                Intent i = new Intent(My_Quatation_Info.this, User_Profile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(My_Quatation_Info.this);
                app.setUser_notification("My_Quatation_Info");
                Intent i = new Intent(My_Quatation_Info.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(My_Quatation_Info.this);
                db.Delete_Quotation();
                Intent i = new Intent(My_Quatation_Info.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(My_Quatation_Info.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid = user_array_from_db.get(i).getId();
            String user_id = user_array_from_db.get(i).getUser_id();
            String email_id = user_array_from_db.get(i).getEmail_id();
            String phone_no = user_array_from_db.get(i).getPhone_no();
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
        app = new AppPrefs(My_Quatation_Info.this);
        app.setquo("1");
        Intent i = new Intent(My_Quatation_Info.this, User_Profile.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {


        String filePath = "";

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //activity.showDialog(0);


            pDialog = new ProgressDialog(My_Quatation_Info.this);
            pDialog.setMessage("Please Wait Downloading Invoice PDF");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... i) {

            try {


                filePath = myDir + File.separator + quotation_info.get(current_position).getDate().replace("/", "_") + "_" + quotation_info.get(current_position).getTitle() + ".pdf";

                filePath = filePath.replace(" ", "_");
                filePath = filePath.replace(":", "_");

                Log.e("Path", "->" + filePath);
                //pDialog.setMessage("Please Wait Downloading Image");
                File temp_dir = new File(filePath);
                if (temp_dir.exists()) {
                    filesToSend = filePath;


                } else {
                    File dir = new File(myDir + "");
                    if (dir.exists() == false) {
                        dir.mkdirs();
                    }

                    Log.e("ABCCCC", "" + Globals.server_link1 + quotation_info.get(current_position).getDownload());
                    URL url = new URL(Globals.server_link1 + quotation_info.get(current_position).getDownload());

                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // this will be useful so that you can show a typical 0-100% progress bar
                    long fileLength = connection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(new File(filePath));

                    filesToSend = filePath;
                    //Log.e("", "out put :- "+myDir+""+quotation_info.get(current_position).getDate()+"_"+quotation_info.get(current_position).getTitle()+".pdf");
                    byte data[] = new byte[1024];
                    long total = 0;
                    //int count;


                    int count;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        //  publishProgress((int) ((total * 100)/fileLength));
                        long total1 = (total * 100) / fileLength;
                        publishProgress(total1 + "");
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }

            } catch (Exception e) {
                //Log.e("Error: ", ""+e);
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //activity.dismissDialog(0);
            pDialog.dismiss();

            Log.e("filePath", filePath);

            File file = new File(filePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);


        }

    }

    public String GetCartByQty(final List<NameValuePair> params) {

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty", ServiceHandler.POST, params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //  System.out.println("error1: " + e.toString());
                    notDone[0] = false;

                }
            }
        });
        thread.start();
        while (notDone[0]) {

        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    public class GetCartByQty extends AsyncTask<Void, Void, String> {

        List<NameValuePair> params = new ArrayList<>();

        public GetCartByQty(List<NameValuePair> params) {
            hasCartCallFinish = true;
            this.params = params;
        }

        @Override
        protected String doInBackground(Void... param) {


            Globals.generateNoteOnSD(getApplicationContext(), "CartData/App_GetCartQty" + "\n" + params.toString());

            String json = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty", ServiceHandler.POST, params);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            cartJSON = json;
            Globals.generateNoteOnSD(getApplicationContext(), cartJSON);
            try {


                //System.out.println(json);

                if (json == null
                        || (json.equalsIgnoreCase(""))) {

                    Globals.CustomToast(My_Quatation_Info.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(My_Quatation_Info.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(My_Quatation_Info.this);
                        app.setCart_QTy("" + qu);


                    }

                }


            } catch (Exception j) {
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = app.getCart_QTy();
            if (qu1.equalsIgnoreCase("0") || qu1.equalsIgnoreCase("")) {
                txt.setVisibility(View.GONE);
                txt.setText("");
            } else {
                if (Integer.parseInt(qu1) > 999) {
                    txt.setText("999+");
                    txt.setVisibility(View.VISIBLE);
                } else {
                    txt.setText(qu1 + "");
                    txt.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
