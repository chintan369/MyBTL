package com.agraeta.user.btl;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.CompanySalesPerson.Bean_invoice;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
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


public class Order_history extends ActionBarActivity {
    final File myDir = new File("/sdcard/BTL/Order/");
    ListView lst_orderhistory;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ArrayList<Bean_Order_history> array_order = new ArrayList<Bean_Order_history>();
    ArrayList<Bean_inv> bean_inv = new ArrayList<Bean_inv>();
    ArrayList<Bean_Order_history> array_remove_duplicate = new ArrayList<Bean_Order_history>();
    ArrayList<Bean_Order_history> bean_cart = new ArrayList<Bean_Order_history>();
    Dialog dialog;
    Custom_ProgressDialog loadingView;
    String json;
    int current_position1 = -1;
    CustomAdapterOrderHistory_invoice adapter1;
    String user_id_main = "";
    CustomAdapterOrderHistory adapter;
    DatabaseHandler db;
    ProgressDialog pDialog;
    AppPrefs appPrefs;
    LinearLayout lin_filter;
    ListView l_tracking;
    ArrayList<Bean_invoice> bean_invoice = new ArrayList<Bean_invoice>();
    ArrayList<Bean_Filter_Status> array_status = new ArrayList<Bean_Filter_Status>();
    Button btn_back;
    String filesToSend = "";
    String owner_id = "";
    String u_id = "";
    String role_id = "";
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
        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id();

                role_id = user_data.get(i).getUser_type();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    appPrefs = new AppPrefs(Order_history.this);
                    role_id = appPrefs.getSubSalesId();
                    u_id = appPrefs.getSalesPersonId();
                } else {
                    u_id = owner_id;
                }


            }

            List<NameValuePair> para = new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id", u_id));

            new GetCartByQty(para).execute();


        } else {
            appPrefs = new AppPrefs(Order_history.this);
            appPrefs.setCart_QTy("");
        }
        turnGPSOn();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        db = new DatabaseHandler(Order_history.this);
        db.Delete_Order_histroy();
        appPrefs = new AppPrefs(Order_history.this);
        fetchID();
        setActionBar();

        setRefershData();
        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                user_id_main = user_data.get(i).getUser_id();

            }

        } else {
            user_id_main = "";
        }
        new get_order_history().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        turnGPSOFF();
    }

    private void turnGPSOFF() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    private void turnGPSOn() {
        try {

            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        } catch (Exception e) {
            Log.e("Exception GPS", e.getMessage());
        }
    }

    private void fetchID() {
        lst_orderhistory = (ListView) findViewById(R.id.lst_orderhistory);

        lst_orderhistory.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int currentLastVisiblePosition = lst_orderhistory.getLastVisiblePosition();
                int totalItems = array_remove_duplicate.size() - 1;

                if (currentLastVisiblePosition == totalItems && currentPage < totalPages) {
                    currentPage++;
                    new get_order_history().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        lin_filter = (LinearLayout) findViewById(R.id.lin_filter);
        btn_back = (Button) findViewById(R.id.btn_back);
        appPrefs = new AppPrefs(Order_history.this);
        if (!appPrefs.getOrder_history_filter_order_status().equalsIgnoreCase("") || !appPrefs.getOrder_history_filter_predefine().equalsIgnoreCase("") || !appPrefs.getOrder_history_filter_to_date().equalsIgnoreCase("") || !appPrefs.getOrder_history_filter_from_date().equalsIgnoreCase("")) {
            btn_back.setVisibility(View.VISIBLE);
        } else {
            btn_back.setVisibility(View.GONE);
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPrefs.setOrder_history_filter_order_status("");
                appPrefs.setOrder_history_filter_predefine("");
                appPrefs.setOrder_history_filter_to_date("");
                appPrefs.setOrder_history_filter_from_date("");

                db.Delete_Order_histroy();
                Intent i = new Intent(Order_history.this, Order_history.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        lin_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Order_history.this, Order_History_Filter.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        lst_orderhistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               /* Intent intent = new Intent(Order_history.this,Order_History_Details.class);
                intent.putExtra("order_array",array_order);
                startActivity(intent);*/

            }
        });
    }

    private void reOrderForOrder(List<Bean_Order_history> productList) {
        //TODO REORDER WORK HERE

        Log.e("productList", "-->" + productList.size());

        ArrayList<String> productIDs = new ArrayList<String>();
        ArrayList<String> productQtys = new ArrayList<String>();
        ArrayList<String> productOptionID = new ArrayList<String>();
        ArrayList<String> productOptionName = new ArrayList<String>();
        ArrayList<String> productOptionValueID = new ArrayList<String>();
        ArrayList<String> productOptionValueName = new ArrayList<String>();

        for (int ii = 0; ii < productList.size(); ii++) {

            if (!productList.get(ii).getScheme_type().equalsIgnoreCase("2")) {
                productIDs.add(productList.get(ii).getProduct_id());
                productQtys.add(productList.get(ii).getProduct_qty());
                productOptionID.add(productList.get(ii).getPro_Option_id());
                productOptionValueID.add(productList.get(ii).getPro_Option_value_id());
                productOptionName.add(productList.get(ii).getProduct_option_name());
                productOptionValueName.add(productList.get(ii).getProduct_value_name());
            }
        }

        Intent intent = new Intent(getApplicationContext(), ReOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putStringArrayListExtra("productIDs", productIDs);
        intent.putStringArrayListExtra("productQtys", productQtys);
        intent.putStringArrayListExtra("productOptionIDs", productOptionID);
        intent.putStringArrayListExtra("productOptionNames", productOptionName);
        intent.putStringArrayListExtra("productOptionValueIDs", productOptionValueID);
        intent.putStringArrayListExtra("productOptionValueNames", productOptionValueName);
        startActivity(intent);
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
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        /*TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        db = new DatabaseHandler(User_Profile.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        txt.setText(bean_cart.size()+"");*/
        img_notification.setVisibility(View.GONE);
        img_cart.setVisibility(View.VISIBLE);
        frame.setVisibility(View.VISIBLE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        appPrefs = new AppPrefs(Order_history.this);
        String qun = appPrefs.getCart_QTy();

        appPrefs = new AppPrefs(Order_history.this);
        String qu1 = appPrefs.getCart_QTy();
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
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPrefs = new AppPrefs(Order_history.this);
                appPrefs.setUser_notification("Order_history");
                Intent i = new Intent(Order_history.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*appPrefs.setOrder_history_filter_order_status("");
                appPrefs.setOrder_history_filter_predefine("");
                appPrefs.setOrder_history_filter_to_date("");
                appPrefs.setOrder_history_filter_from_date("");

                db.Delete_Order_histroy();
                Intent i = new Intent(Order_history.this, User_Profile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();*/

                onBackPressed();
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
                Intent i = new Intent(Order_history.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Order_history.this, Notification.class);
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
        db = new DatabaseHandler(Order_history.this);

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
        // TODO Auto-generated method stub
        appPrefs.setOrder_history_filter_order_status("");
        appPrefs.setOrder_history_filter_predefine("");
        appPrefs.setOrder_history_filter_to_date("");
        appPrefs.setOrder_history_filter_from_date("");

        db.Delete_Order_histroy();
        Intent i = new Intent(Order_history.this, User_Profile.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

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

    private class CustomAdapterOrderHistory extends BaseAdapter {
        int current_position = -1;
        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return array_remove_duplicate.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = vi.inflate(R.layout.order_history_row, null);

            }

            //if(position%2==0)
            //convertView.setBackgroundResource(R.drawable.bg_order_odd);

            LinearLayout layout_order_history = (LinearLayout) convertView.findViewById(R.id.layout_order_history);

            TextView order_customer_name = (TextView) convertView.findViewById(R.id.order_customer_name);
            TextView tv_order_status = (TextView) convertView.findViewById(R.id.tv_order_status);
            TextView tv_order_date = (TextView) convertView.findViewById(R.id.tv_order_date);
            TextView tv_order_id = (TextView) convertView.findViewById(R.id.order_id);

            CardView layout_cardView = (CardView) convertView.findViewById(R.id.layout_cardView);

            if (array_remove_duplicate.get(position).isOdd())
                layout_cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            else
                layout_cardView.setCardBackgroundColor(Color.parseColor("#FFE0B2"));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appPrefs = new AppPrefs(Order_history.this);
                    appPrefs.setOrder_history_id(array_remove_duplicate.get(position).getOrder_id());
                    appPrefs.setComment(array_remove_duplicate.get(position).getComment());
                    appPrefs.setAttachment(array_remove_duplicate.get(position).getInvoice_prefix());
                    db = new DatabaseHandler(Order_history.this);
                    bean_cart = db.get_order_history(appPrefs.getOrder_history_id());
                    appPrefs.setOrder("" + bean_cart.size());
                    appPrefs.setOrdercheck("" + bean_cart.size());
                    Intent intent = new Intent(Order_history.this, Order_History_Details.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("order", array_remove_duplicate.get(position));
                    //intent.putExtra("order_array",array_order);
                    startActivity(intent);
                }
            });

            TextView txt_orderID = (TextView) convertView.findViewById(R.id.txt_orderID);
            TextView txt_orderDate = (TextView) convertView.findViewById(R.id.txt_orderDate);
            TextView txt_orderStatus = (TextView) convertView.findViewById(R.id.txt_orderStatus);
            TextView txt_orderAmount = (TextView) convertView.findViewById(R.id.txt_orderAmount);
            final ImageView img_options = (ImageView) convertView.findViewById(R.id.img_options);

            img_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(Order_history.this, v);
                    menu.getMenuInflater().inflate(R.menu.menu_order_history_listoption, menu.getMenu());

                    if (bean_inv.get(position).getOrder_invoice().equalsIgnoreCase("0")) {
                        menu.getMenu().findItem(R.id.menu_invoice).setVisible(false);
                    } else {
                        menu.getMenu().findItem(R.id.menu_pdf).setVisible(false);
                    }

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.menu_pdf:
                                    Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                                    pdfIntent.setType("application/pdf");
                                    pdfIntent.setData(Uri.parse(Globals.server_link + array_remove_duplicate.get(position).getOrder_pdf()));
                                    startActivity(pdfIntent);
                                    break;
                                case R.id.menu_invoice:
                                    Intent invoiceActivity = new Intent(getApplicationContext(), OrderInvoiceActivity.class);
                                    invoiceActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    invoiceActivity.putExtra("orderID", array_remove_duplicate.get(position).getOrder_id());
                                    startActivity(invoiceActivity);
                                    break;
                                case R.id.menu_reorder:
                                    reOrderForOrder(array_remove_duplicate.get(position).getProductList());
                                    break;
                            }

                            return true;
                        }
                    });

                    menu.show();
                }
            });

            txt_orderID.setText(array_remove_duplicate.get(position).getOrder_id());
            txt_orderDate.setText(array_remove_duplicate.get(position).getOrder_date().split(" ")[0]);
            txt_orderStatus.setText(array_remove_duplicate.get(position).getOrder_status_is());
            txt_orderAmount.setText(getResources().getString(R.string.ruppe_name) + " " + array_remove_duplicate.get(position).getTotal());

            TextView tv_total = (TextView) convertView.findViewById(R.id.tv_total);
            ImageView im_details = (ImageView) convertView.findViewById(R.id.im_details);
            ImageView im_pdfdownload = (ImageView) convertView.findViewById(R.id.im_pdfdownload);
            ImageView im_invoice = (ImageView) convertView.findViewById(R.id.im_invoice);

            if (bean_inv.get(position).getOrder_invoice().equalsIgnoreCase("0")) {
                im_invoice.setVisibility(View.INVISIBLE);
            } else {
                im_invoice.setVisibility(View.VISIBLE);
            }


            order_customer_name.setText(array_remove_duplicate.get(position).getShipping_name());


            tv_total.setText("Total: " + getResources().getString(R.string.Rs) + array_remove_duplicate.get(position).getTotal());
            tv_order_date.setText("Date: " + array_remove_duplicate.get(position).getOrder_date());
            tv_total.setText("Total: " + array_remove_duplicate.get(position).getTotal());
            tv_order_status.setText("Status: " + array_remove_duplicate.get(position).getOrder_status_is());
            tv_order_id.setText("Order Id: #" + array_remove_duplicate.get(position).getOrder_id());


//            layout_order_history.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    appPrefs.setOrder_history_id(array_remove_duplicate.get(position).getOrder_id());
//                    Intent intent = new Intent(Order_history.this, Order_History_Details.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    //intent.putExtra("order_array",array_order);
//                    startActivity(intent);
//                }
//            });

            im_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appPrefs = new AppPrefs(Order_history.this);
                    appPrefs.setOrder_history_id(array_remove_duplicate.get(position).getOrder_id());
                    appPrefs.setComment(array_remove_duplicate.get(position).getComment());
                    appPrefs.setAttachment(array_remove_duplicate.get(position).getInvoice_prefix());
                    db = new DatabaseHandler(Order_history.this);
                    bean_cart = db.get_order_history(appPrefs.getOrder_history_id());
                    appPrefs.setOrder("" + bean_cart.size());
                    appPrefs.setOrdercheck("" + bean_cart.size());
                    Intent intent = new Intent(Order_history.this, Order_History_Details.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("order", array_remove_duplicate.get(position));
                    //intent.putExtra("order_array",array_order);
                    startActivity(intent);
                }
            });


            im_pdfdownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current_position = position;
                    new DownloadFileFromURL().execute();
                }
            });
            im_invoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appPrefs = new AppPrefs(Order_history.this);
                    appPrefs.setOrder_history_id(array_remove_duplicate.get(position).getOrder_id());
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;


                    dialog = new Dialog(Order_history.this);
                    dialog.getWindow();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.invoice_layout);
                    dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 2);
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                    ImageView btn_cancel = (ImageView) dialog.findViewById(R.id.btn_cancel);
                    l_tracking = (ListView) dialog.findViewById(R.id.lst_tracking);

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    new set_order_tracking().execute();


                    dialog.show();
                }
            });

            return convertView;
        }

        class DownloadFileFromURL extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread
             * Show Progress Bar Dialog
             */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //activity.showDialog(0);


                pDialog = new ProgressDialog(Order_history.this);
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


                    //pDialog.setMessage("Please Wait Downloading Image");
                    File temp_dir = new File(myDir + "" + array_remove_duplicate.get(current_position).getOrder_date() + "_" + array_remove_duplicate.get(current_position).getOrder_id() + ".pdf");
                    if (temp_dir.exists()) {
                        filesToSend = myDir + "" + array_remove_duplicate.get(current_position).getOrder_date() + "_" + array_remove_duplicate.get(current_position).getOrder_id() + ".pdf";


                    } else {
                        File dir = new File(myDir + "");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }


                        Log.e("ABCCCC", "" + Globals.server_link + array_remove_duplicate.get(current_position).getOrder_pdf());
                        URL url = new URL(Globals.server_link + array_remove_duplicate.get(current_position).getOrder_pdf());

                        URLConnection connection = url.openConnection();
                        connection.connect();

                        // this will be useful so that you can show a typical 0-100% progress bar
                        long fileLength = connection.getContentLength();

                        // download the file
                        InputStream input = new BufferedInputStream(url.openStream());
                        OutputStream output = new FileOutputStream(new File(myDir + "" + array_remove_duplicate.get(current_position).getOrder_date() + "_" + array_remove_duplicate.get(current_position).getOrder_id() + ".pdf"));

                        filesToSend = myDir + "" + array_remove_duplicate.get(current_position).getOrder_date() + "_" + array_remove_duplicate.get(current_position).getOrder_id() + ".pdf";
                        //Log.e("", "out put :- "+myDir+""+array_remove_duplicate.get(current_position).getOrder_date()+"_"+array_remove_duplicate.get(current_position).getOrder_id()+".pdf");
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

                File file = new File(myDir + "" + array_remove_duplicate.get(current_position).getOrder_date() + "_" + array_remove_duplicate.get(current_position).getOrder_id() + ".pdf");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);


            }

        }
    }

    public class get_order_history extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(Order_history.this, "");

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

                if (appPrefs.getOrder_history_filter_order_status().equalsIgnoreCase("")) {

                } else {
                    parameters.add(new BasicNameValuePair("order_status", appPrefs.getOrder_history_filter_order_status()));
                }
                if (appPrefs.getOrder_history_filter_from_date().equalsIgnoreCase("")) {

                } else {
                    parameters.add(new BasicNameValuePair("from_date", appPrefs.getOrder_history_filter_from_date()));
                }

                if (appPrefs.getOrder_history_filter_to_date().equalsIgnoreCase("")) {

                } else {
                    parameters.add(new BasicNameValuePair("to_date", appPrefs.getOrder_history_filter_to_date()));
                }

                if (appPrefs.getOrder_history_filter_predefine().equalsIgnoreCase("")) {

                } else {
                    parameters.add(new BasicNameValuePair("predefine", appPrefs.getOrder_history_filter_predefine()));
                }

                parameters.add(new BasicNameValuePair("page", String.valueOf(currentPage)));


                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_Get_Order", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //   System.out.println("error1: " + e.toString());

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
                    Globals.CustomToast(Order_history.this, "SERVER ERRER", Order_history.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Order_history.this, "" + Message, Order_history.this.getLayoutInflater());


                        JSONArray jsonobjcet_order_status = jObj.getJSONArray("order_status");

                        for (int j = 0; j < jsonobjcet_order_status.length(); j++) {
                            JSONObject jobject_main = jsonobjcet_order_status.getJSONObject(j);
                            JSONObject jobject_status = jobject_main.getJSONObject("OrderStatus");
                            Bean_Filter_Status bean = new Bean_Filter_Status();
                            bean.setId(jobject_status.getString("id"));
                            bean.setFilter_name(jobject_status.getString("order_status_name"));
                            array_status.add(bean);

                        }
                        db.Add_Filter_Status(array_status);
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");

                        JSONArray jarray_data = jObj.getJSONArray("data");

                        int lastVisiblePosition = 0;
                        if (currentPage > 1) {
                            lastVisiblePosition = lst_orderhistory.getFirstVisiblePosition();
                        }

                        JSONArray jsonobjcet_order_status = jObj.getJSONArray("order_status");
                        totalPages = jObj.getInt("total_pages");
                        if (currentPage == 1) {
                            for (int j = 0; j < jsonobjcet_order_status.length(); j++) {
                                JSONObject jobject_main = jsonobjcet_order_status.getJSONObject(j);
                                JSONObject jobject_status = jobject_main.getJSONObject("OrderStatus");
                                Bean_Filter_Status bean = new Bean_Filter_Status();
                                bean.setId(jobject_status.getString("id"));
                                bean.setFilter_name(jobject_status.getString("order_status_name"));
                                array_status.add(bean);

                            }
                            db.Add_Filter_Status(array_status);
                        }

                        for (int i = 0; i < jarray_data.length(); i++) {
                            JSONObject jobject_main = jarray_data.getJSONObject(i);

                            JSONObject jobject_order = jobject_main.getJSONObject("Order");


                            String order_id = jobject_order.getString("id");
                            String invoice_no = jobject_order.getString("invoice_no");
                            String invoice_prefix = jobject_order.getString("invoice_prefix");
                            String user_id = jobject_order.getString("user_id");
                            String role_id = jobject_order.getString("role_id");
                            String billing_address_id = jobject_order.getString("billing_address_id");
                            String shipping_address_id = jobject_order.getString("shipping_address_id");
                            String first_name = jobject_order.getString("firstname");
                            String last_name = jobject_order.getString("lastname");
                            String email = jobject_order.getString("email");
                            String mobile = jobject_order.getString("mobile");
                            String payment_name = jobject_order.getString("payment_firstname") + " " + jobject_order.getString("payment_lastname");

                            Bean_inv bea = new Bean_inv();
                            bea.setOrder_id(order_id);
                            bea.setOrder_invoice(jobject_main.getString("OrderInvoice").toString());
                            bean_inv.add(bea);

                            String final_payment_address = "";
                            if (jobject_order.getString("payment_address_1").equalsIgnoreCase("") || jobject_order.getString("payment_address_1").equalsIgnoreCase("null")) {

                            } else {
                                final_payment_address = jobject_order.getString("payment_address_1");
                            }


                            if (jobject_order.getString("payment_address_2").equalsIgnoreCase("") || jobject_order.getString("payment_address_2").equalsIgnoreCase("null")) {

                            } else {
                                final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_address_2");
                            }

                            if (jobject_order.getString("payment_address_3").equalsIgnoreCase("") || jobject_order.getString("payment_address_3").equalsIgnoreCase("null")) {

                            } else {
                                final_payment_address = final_payment_address + ",\n" + jobject_order.getString("payment_address_3");
                            }

                            if (jobject_order.getString("payment_city").equalsIgnoreCase("") || jobject_order.getString("payment_city").equalsIgnoreCase("null")) {

                            } else {
                                final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_city");
                            }

                            if (jobject_order.getString("payment_postcode").equalsIgnoreCase("") || jobject_order.getString("payment_postcode").equalsIgnoreCase("null")) {

                            } else {
                                final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_postcode");
                            }

                            if (jobject_order.getString("payment_state").equalsIgnoreCase("") || jobject_order.getString("payment_state").equalsIgnoreCase("null")) {

                            } else {
                                final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_state");
                            }

                            if (jobject_order.getString("payment_country").equalsIgnoreCase("") || jobject_order.getString("payment_country").equalsIgnoreCase("null")) {

                            } else {
                                final_payment_address = final_payment_address + ", " + jobject_order.getString("payment_country");
                            }


                            String payment_address = final_payment_address;
                            String payment_method = jobject_order.getString("payment_method");
                            String payment_code = jobject_order.getString("payment_code");
                            String shipping_name = jobject_order.getString("shipping_firstname") + " " + jobject_order.getString("shipping_lastname");

                            String final_shipping_address = "";
                            if (jobject_order.getString("shipping_address_1").equalsIgnoreCase("") || jobject_order.getString("shipping_address_1").equalsIgnoreCase("null")) {

                            } else {
                                final_shipping_address = jobject_order.getString("shipping_address_1");
                            }

                            if (jobject_order.getString("shipping_address_2").equalsIgnoreCase("") || jobject_order.getString("shipping_address_2").equalsIgnoreCase("null")) {

                            } else {
                                final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_address_2");
                            }

                            if (jobject_order.getString("shipping_address_3").equalsIgnoreCase("") || jobject_order.getString("shipping_address_3").equalsIgnoreCase("null")) {

                            } else {
                                final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_address_3");
                            }

                            if (jobject_order.getString("shipping_city").equalsIgnoreCase("") || jobject_order.getString("shipping_city").equalsIgnoreCase("null")) {

                            } else {
                                final_shipping_address = final_shipping_address + ",\n" + jobject_order.getString("shipping_city");
                            }

                            if (jobject_order.getString("shipping_postcode").equalsIgnoreCase("") || jobject_order.getString("shipping_postcode").equalsIgnoreCase("null")) {

                            } else {
                                final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_postcode");
                            }

                            if (jobject_order.getString("shipping_state").equalsIgnoreCase("") || jobject_order.getString("shipping_state").equalsIgnoreCase("null")) {

                            } else {
                                final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_state");
                            }

                            if (jobject_order.getString("shipping_country").equalsIgnoreCase("") || jobject_order.getString("shipping_country").equalsIgnoreCase("null")) {

                            } else {
                                final_shipping_address = final_shipping_address + ", " + jobject_order.getString("shipping_country");
                            }

                            String shipping_address = final_shipping_address;
                            String shipping_cost = jobject_order.getString("shipping_cost");
                            String comment = jobject_order.getString("comment");
                            //String comment  = jobject_order.getString("comment");

                            String attachment = jobject_order.getString("attachment_1");
                            String total = jobject_order.getString("total");
                            String order_status_is = jobject_order.getString("order_status_id");
                            String order_pdf = jobject_order.getString("order_pdf");
                            String order_date = jobject_order.getString("created");
                            JSONArray jarray_OrderProduct = jobject_main.getJSONArray("OrderProduct");
                            /*s = jobject_main.getString("OrderInvoice");*/
                            String discount = jobject_order.getString("discount_amount");
                            String coupon_id = "", coupon_name = "", coupon_amount = "";
                            if (Integer.parseInt(discount) > 0) {
                                JSONObject couponHistory = jobject_main.getJSONObject("CouponHistory");
                                coupon_amount = couponHistory.getString("amount");
                                coupon_name = couponHistory.getJSONObject("Coupon").getString("name");
                            }

                            List<Bean_Order_history> productList = new ArrayList<>();

                            for (int j = 0; j < jarray_OrderProduct.length(); j++) {

                                JSONObject jobject_OrderProduct = jarray_OrderProduct.getJSONObject(j);
                                Bean_Order_history bean = new Bean_Order_history();
                                bean.setOdd(i % 2 != 0);

                                Bean_Order_history productItem = new Bean_Order_history();
                                productItem.setOrder_id(jobject_OrderProduct.getString("order_id"));
                                productItem.setProduct_id(jobject_OrderProduct.getString("product_id"));
                                productItem.setCategoryID(jobject_OrderProduct.getString("category_id"));
                                productItem.setProduct_qty(jobject_OrderProduct.getString("quantity"));
                                productItem.setPro_Option_id(jobject_OrderProduct.getString("option_id"));
                                productItem.setProduct_option_name(jobject_OrderProduct.getString("option_name").replace("Select", "").trim());
                                productItem.setPro_Option_value_id(jobject_OrderProduct.getString("option_value_id"));
                                productItem.setProduct_value_name(jobject_OrderProduct.getString("option_value_name"));
                                productItem.setScheme_type(jobject_OrderProduct.getString("scheme_type"));
                                productList.add(productItem);

                                bean.setId(jobject_OrderProduct.getString("id"));
                                bean.setProduct_id(jobject_OrderProduct.getString("product_id"));
                                bean.setProduct_name(jobject_OrderProduct.getString("name"));
                                String product_code = jobject_OrderProduct.getString("pro_code").replace("(", "");
                                product_code = product_code.replace(")", "");
                                bean.setProduct_code(product_code);
                                bean.setProduct_qty(jobject_OrderProduct.getString("quantity"));
                                bean.setProduct_selling_price(jobject_OrderProduct.getString("selling_price"));
                                bean.setProduct_total(jobject_OrderProduct.getString("item_total"));
                                //Log.e("abcabac",""+jobject_OrderProduct.getString("item_total"));
                                //Log.e("abcbjhiuhikfhdh","h;auhgdih");
                                bean.setProduct_option_name(jobject_OrderProduct.getString("option_name"));
                                bean.setProduct_value_name(jobject_OrderProduct.getString("option_value_name"));
                                bean.setPro_Option_id(jobject_OrderProduct.getString("option_id"));
                                bean.setPro_Option_value_id(jobject_OrderProduct.getString("option_value_id"));
                                bean.setOrder_schme(jobject_OrderProduct.getString("pro_scheme"));
                                //Log.e("ABCSLKJFKLS",""+jobject_OrderProduct.getString("pro_scheme"));
                                bean.setPack_of(jobject_OrderProduct.getString("pack_of"));
                                //Log.e("asasaassaassas",""+jobject_OrderProduct.getString("pack_of"));
                                bean.setOrder_id(order_id);
                                bean.setInvoice_no(invoice_no);
                                bean.setInvoice_prefix(attachment);
                                bean.setUser_id(user_id);
                                bean.setRole_id(role_id);
                                bean.setBilling_address_id(billing_address_id);
                                bean.setShipping_address_id(shipping_address_id);
                                bean.setFirst_name(first_name);
                                bean.setLast_name(last_name);
                                bean.setEmail(email);
                                bean.setMobile(mobile);
                                bean.setPayment_name(payment_name);
                                bean.setPayment_address(payment_address);
                                bean.setPayment_method(payment_method);
                                bean.setPayment_code(payment_code);
                                bean.setShipping_name(shipping_name);
                                bean.setShipping_address(shipping_address);
                                bean.setShipping_cost(shipping_cost);
                                bean.setComment(comment);
                                bean.setTotal(total);
                                bean.setOrder_status_is(order_status_is);
                                bean.setOrder_pdf(order_pdf);
                                bean.setOrder_date(order_date);
                                bean.setScheme_type(jobject_OrderProduct.getString("scheme_type"));
                                //Log.e("schme",""+jobject_OrderProduct.getString("scheme_type"));
                                if (Integer.parseInt(discount) > 0) {
                                    bean.setHasCouponApplied(true);
                                    bean.setCoupon_name(coupon_name);
                                    bean.setDiscount_amount(coupon_amount);
                                } else {
                                    bean.setHasCouponApplied(false);
                                }

                                array_order.add(bean);
                                db.Add_ORDER_HISTORY(bean);
                            }
                            //array_remove_duplicate = array_order;

                            for (Bean_Order_history event : array_order) {
                                boolean isFound = false;
                                // check if the event name exists in noRepeat
                                for (Bean_Order_history e : array_remove_duplicate) {
                                    if (e.getOrder_id().equals(event.getOrder_id()))
                                        isFound = true;
                                }

                                if (!isFound) {
                                    event.setProductList(productList);
                                    array_remove_duplicate.add(event);
                                }
                            }

                            //Toast.makeText(Order_history.this, ""+array_remove_duplicate.size(), Toast.LENGTH_SHORT).show();


                        }

                        adapter = new CustomAdapterOrderHistory();
                        adapter.notifyDataSetChanged();
                        lst_orderhistory.setAdapter(adapter);
                        lst_orderhistory.setSelection(lastVisiblePosition);
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class set_order_tracking extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(Order_history.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception ignored) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                appPrefs = new AppPrefs(Order_history.this);
                parameters.add(new BasicNameValuePair("order_id", appPrefs.getOrder_history_id()));
                //Log.e("4", "" + parameters);

                //json = new ServiceHandler().makeServiceCall(Globals.server_link+"ProductEnquiry/App_AddProductEnquiry",ServiceHandler.POST,parameters);
                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Order/App_Get_Order_Tracking", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //    System.out.println("error1: " + e.toString());

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
                    Globals.CustomToast(Order_history.this, "SERVER ERRER", Order_history.this.getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    Boolean date = jObj.getBoolean("status");
                    if (!date) {
                        String Message = jObj.getString("message");
                        // Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Order_history.this, "" + Message, Order_history.this.getLayoutInflater());

                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        bean_invoice.clear();
                        JSONArray jarray_data = jObj.getJSONArray("order_invoice");

                        //JSONArray jsonobjcet_order_status = jObj.getJSONArray("order_status");

                        for (int j = 0; j < jarray_data.length(); j++) {
                            JSONObject jobject_main = jarray_data.getJSONObject(j);

                            Bean_invoice bean = new Bean_invoice();

                            bean.setIno(jobject_main.getString("invoice_no"));
                            bean.setIdate(jobject_main.getString("invoice_date"));
                            bean.setItotal(jobject_main.getString("order_total"));
                            bean.setIpdf(jobject_main.getString("invoice_file"));
                            bean_invoice.add(bean);

                        }


                        adapter1 = new CustomAdapterOrderHistory_invoice();
                        adapter1.notifyDataSetChanged();
                        l_tracking.setAdapter(adapter1);

                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class CustomAdapterOrderHistory_invoice extends BaseAdapter {
        //  int current_position1 = -1;
        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return bean_invoice.size();
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


            convertView = vi.inflate(R.layout.invoice_row, null);


            TextView txt_no = (TextView) convertView.findViewById(R.id.txt_no);
            TextView txt_date = (TextView) convertView.findViewById(R.id.txt_date);
            TextView txt_total = (TextView) convertView.findViewById(R.id.txt_total);
            ImageView img_pdf = (ImageView) convertView.findViewById(R.id.im_pdf);

            txt_no.setText(bean_invoice.get(position).getIno().toString());
            txt_date.setText(bean_invoice.get(position).getIdate().toString());
            txt_total.setText(getResources().getString(R.string.Rs) + "" + bean_invoice.get(position).getItotal().toString());
            img_pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current_position1 = position;
                    new DownloadFileFromURL_a().execute();

                }
            });

            return convertView;
        }
    }

    private class DownloadFileFromURL_a extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //activity.showDialog(0);


            pDialog = new ProgressDialog(Order_history.this);
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


                //pDialog.setMessage("Please Wait Downloading Image");
                File temp_dir = new File(myDir + "" + bean_invoice.get(current_position1).getIdate() + "_" + bean_invoice.get(current_position1).getIno() + ".pdf");
                if (temp_dir.exists()) {
                    filesToSend = myDir + "" + bean_invoice.get(current_position1).getIdate() + "_" + bean_invoice.get(current_position1).getIno() + ".pdf";


                } else {
                    File dir = new File(myDir + "");
                    if (dir.exists() == false) {
                        dir.mkdirs();
                    }


                    URL url = new URL(Globals.server_link + bean_invoice.get(current_position1).getIpdf());

                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // this will be useful so that you can show a typical 0-100% progress bar
                    long fileLength = connection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(new File(myDir + "" + bean_invoice.get(current_position1).getIdate() + "_" + bean_invoice.get(current_position1).getIno()) + ".pdf");

                    filesToSend = myDir + "" + bean_invoice.get(current_position1).getIdate() + "_" + bean_invoice.get(current_position1).getIno() + ".pdf";
                    //Log.e("", "out put :- "+myDir+""+bean_invoice.get(current_position1).getIdate()+"_"+bean_invoice.get(current_position1).getIno()+".pdf");
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

            File file = new File(myDir + "" + bean_invoice.get(current_position1).getIdate() + "_" + bean_invoice.get(current_position1).getIno() + ".pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);


        }

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

                    Globals.CustomToast(Order_history.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        appPrefs = new AppPrefs(Order_history.this);
                        appPrefs.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        appPrefs = new AppPrefs(Order_history.this);
                        appPrefs.setCart_QTy("" + qu);


                    }

                }


            } catch (Exception j) {
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = appPrefs.getCart_QTy();
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
