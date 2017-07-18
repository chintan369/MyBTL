package com.agraeta.user.btl;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.CompanySalesPerson.UserTypeActivity;
import com.agraeta.user.btl.DisSalesPerson.SalesTypeActivity;
import com.agraeta.user.btl.Distributor.DisMyOrders;
import com.agraeta.user.btl.Distributor.DistributorActivity;
import com.agraeta.user.btl.adapters.SupportCallAdapter;
import com.agraeta.user.btl.admin.AdminDashboard;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.SupportCallInfo;
import com.agraeta.user.btl.model.UserDetailResponse;
import com.agraeta.user.btl.model.combooffer.ComboOfferItem;
import com.agraeta.user.btl.utils.BadgeUtils;
import com.agraeta.user.btl.utils.ParallaxPageTransformer;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

//import com.nivida.user.btl.Distributor.DistributorFormActivity;


public class MainPage_drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult> {
    private static final Integer[] IMAGES = {R.drawable.image,
            R.drawable.b1,
            R.drawable.b2,
            R.drawable.image
    };
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
    public static int display_width = 0;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static MainPage_drawer instance;
    ActionBarDrawerToggle mDrawerToggle;
    TextView notification_unread;
    boolean isMdevice;
    boolean pstatus;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    int code = 1;
    ImageSwitcher imageSwitcher;
    Handler handler;
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    ArrayList<Integer> Categoryimg = new ArrayList<Integer>();
    ArrayList<Integer> offergrid = new ArrayList<Integer>();
    ArrayList<String> name = new ArrayList<String>();
    //ArrayList<Bean_Slider> slider_arra =new ArrayList<Bean_Slider>();
    ArrayList<Bean_Slider> arraylist_slider = new ArrayList<Bean_Slider>();
    ArrayList<Bean_Stickers> strick_arra = new ArrayList<Bean_Stickers>();
    ArrayList<Bean_Stickers> sticker_arra = new ArrayList<Bean_Stickers>();
    ArrayList<Bean_category> category_arra = new ArrayList<Bean_category>();
    ViewPager customviewpager;
    GridView gridView1, gridView_new;
    String[] perms = {Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.WRITE_SETTINGS, Manifest.permission.WRITE_SECURE_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION};
    ImageView image_drawer;
    ImageView img_drwaer_header;
    NavigationView navigationView;
    DatabaseHandler db;
    LinearLayout l3;
    AppPrefs apps;
    TextView marqueetextview;
    LinearLayout lmain1, lmain2, layout_comboOffers, layout_comboPacks;
    ArrayList<Integer> list_data = new ArrayList<Integer>();
    ArrayList<Integer> sticker_pro = new ArrayList<Integer>();
    ArrayList<String> list_data_name = new ArrayList<String>();
    LinearLayout.LayoutParams params;
    EditText et_search;
    Custom_ProgressDialog loadingView;
    int count = 0;
    String json = new String();
    Runnable Update;
    //ImageView img_a1,img_b1,img_c1;
    // TextView title,title_b,title_c;
    ImageLoader imageloader;
    String aa, bb, cc;
    String owner_id = new String();
    String u_id = new String();
    String role_id = new String();
    LinearLayout layout_main;
    AppPrefs app;
    LinearLayout l_strickers;
    TextView cate;
    String cartJSON = "";
    boolean hasCartCallFinish = true;
    LinearLayout layout_support;
    TextView txt;
    ArrayList<SupportCallInfo> callInfoList = new ArrayList<>();
    ArrayList<Bean_Product> latestProductList = new ArrayList<>();
    LinearLayout layout_latestProducts, layout_latestProductsLabel;
    AdminAPI adminAPI;
    List<ComboOfferItem> comboOfferItemList = new ArrayList<>();
    private ViewPager mPager;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private GoogleApiClient mGoogleApiClient;

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            //Log.e("c","cdef");
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                //Log.e("d","defg");
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    //Log.e("e","efgh");
                    return false;
                }
            }
            //Log.e("f", "fghi");
        }
        //Log.e("g","ghij");
        return dir.delete();
    }

    public static MainPage_drawer getInstance() {
        return instance;
    }

    public static boolean isMarshmallowPlusDevice() {

        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isPermissionRequestRequired(Activity activity, @NonNull String[] permissions, int requestCode) {
        if (isMarshmallowPlusDevice() && permissions.length > 0) {
            List<String> newPermissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (PERMISSION_GRANTED != activity.checkSelfPermission(permission)) {
                    newPermissionList.add(permission);

                }
            }
            if (newPermissionList.size() > 0) {
                activity.requestPermissions(newPermissionList.toArray(new String[newPermissionList.size()]), requestCode);
                return true;
            }


        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_drawer);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        isMdevice = isMarshmallowPlusDevice();
        app = new AppPrefs(MainPage_drawer.this);
        app.setCurrentPage("MAINPAGE");
        app.set_search("");
        //Log.e("isMdevice", "" + isMdevice);

        adminAPI = ServiceGenerator.getAPIServiceClass();

        layout_main = (LinearLayout) findViewById(R.id.layout_main);
        layout_support = (LinearLayout) findViewById(R.id.layout_support);
        layout_latestProducts = (LinearLayout) findViewById(R.id.layout_latestProducts);
        layout_latestProductsLabel = (LinearLayout) findViewById(R.id.layout_latestProductsLabel);
        layout_comboPacks = (LinearLayout) findViewById(R.id.layout_comboPacks);
        layout_comboOffers = (LinearLayout) findViewById(R.id.layout_comboOffers);

        new GetSupportCallInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        layout_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSupportCallDialog();
            }
        });

        app.setCurrentPage("MainPageDrawer");

        pstatus = isPermissionRequestRequired(MainPage_drawer.this, perms, code);
        setActionBar();
        instance = this;
        imageloader = new ImageLoader(getApplicationContext());
        mGoogleApiClient = new GoogleApiClient.Builder(MainPage_drawer.this)
                .addConnectionCallbacks(MainPage_drawer.this)
                .addOnConnectionFailedListener(MainPage_drawer.this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        et_search = (EditText) findViewById(R.id.et_search);
        lmain1 = (LinearLayout) findViewById(R.id.grid_1);
        lmain2 = (LinearLayout) findViewById(R.id.grid_2);
        l_strickers = (LinearLayout) findViewById(R.id.l_strickers);

        params = new LinearLayout.LayoutParams(android.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.app.ActionBar.LayoutParams.WRAP_CONTENT);


        // setLayout(sticker_pro);
        marqueetextview = (TextView) this.findViewById(R.id.MarqueeText);

        marqueetextview.setSelected(true);

        apps = new AppPrefs(MainPage_drawer.this);
        setRefershData();
        if (Globals.isConnectingToInternet1(MainPage_drawer.this) == false) {

            Globals.CustomToast(MainPage_drawer.this, "No Internet Connection", getLayoutInflater());

        } else {

            new Set_Home_Data().execute();
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (apps.getUser_LoginInfo().toString().equalsIgnoreCase("1")) {
            Menu menu = navigationView.getMenu();


            if (apps.getUserRoleId().equals(C.CUSTOMER) || apps.getUserRoleId().equals(C.CARPENTER)) {
                menu.findItem(R.id.menu_gst).setVisible(false);
            }

            MenuItem bedMenuItem = menu.findItem(R.id.login);

            MenuItem dashboardMenuItem = menu.findItem(R.id.Dashboard);
            if (apps.getUserRoleId().equalsIgnoreCase("3")) {
                dashboardMenuItem.setVisible(true);
            }

            MenuItem companysalesMenuItem = menu.findItem(R.id.CompanySales);
            if (apps.getUserRoleId().equals(C.ADMIN) || apps.getUserRoleId().equals(C.COMP_SALES_PERSON)) {
                companysalesMenuItem.setVisible(true);
            }

            MenuItem distributorsalesMenuItem = menu.findItem(R.id.DistributorSales);
            if (apps.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON)) {
                distributorsalesMenuItem.setVisible(true);
            }

            MenuItem bed1MenuItem = menu.findItem(R.id.logout);

            bedMenuItem.setTitle("My Account");
            bedMenuItem.setIcon(R.drawable.my_account);

            if (apps.getUserRoleId().equals(C.ADMIN)) {
                bedMenuItem.setVisible(false);
                menu.findItem(R.id.categories).setVisible(false);
                menu.findItem(R.id.menu_videoGallery).setVisible(false);
                menu.findItem(R.id.menu_downloads).setVisible(false);
                menu.findItem(R.id.aboutus).setVisible(false);
                menu.findItem(R.id.enquiry).setVisible(false);
                menu.findItem(R.id.bulkEnquiry).setVisible(false);
                menu.findItem(R.id.becomeDealer).setVisible(false);
                menu.findItem(R.id.contact).setVisible(false);
                menu.findItem(R.id.menu_communicate).setVisible(false);
                menu.findItem(R.id.menu_gst).setVisible(false);
            }

            bed1MenuItem.setVisible(true);

        } else {
            Menu menu = navigationView.getMenu();

            MenuItem bedMenuItem = menu.findItem(R.id.login);
            MenuItem bed1MenuItem = menu.findItem(R.id.logout);
            MenuItem menu_comboOffer = menu.findItem(R.id.menu_comboOffer);

            menu_comboOffer.setVisible(false);

            bedMenuItem.setTitle("Login");
            bedMenuItem.setIcon(R.drawable.ic_action_login);

            bed1MenuItem.setVisible(false);
        }
    }

    private void showSupportCallDialog() {
        if (callInfoList.size() != 0) {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

            View view = getLayoutInflater().inflate(R.layout.layout_dialog_supportinfo, null);
            ListView list_callInfo = (ListView) view.findViewById(R.id.list_callInfo);
            SupportCallAdapter adapter = new SupportCallAdapter(callInfoList, this);
            list_callInfo.setAdapter(adapter);


            builder.setView(view);

            final android.support.v7.app.AlertDialog dialog = builder.create();

            list_callInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + callInfoList.get(position).getContact()));
                    if (ActivityCompat.checkSelfPermission(MainPage_drawer.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE);
                        }
                        return;
                    }
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            dialog.show();
        } else {
            Globals.Toast2(this, "No Number Available for Support");
        }
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        ImageView img_ledgerbills = (ImageView) mCustomView.findViewById(R.id.img_ledgerbills);
        notification_unread = (TextView) mCustomView.findViewById(R.id.notification_unread);

        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
        apps = new AppPrefs(getApplicationContext());
        db = new DatabaseHandler(MainPage_drawer.this);
        int notification_count = db.countNotification(apps.getUserId());
        apps = new AppPrefs(MainPage_drawer.this);
        String qun = apps.getCart_QTy();

        setRefershData();

        if (user_data.size() != 0) {
            for (int i = 0; i < user_data.size(); i++) {

                owner_id = user_data.get(i).getUser_id().toString();

                role_id = user_data.get(i).getUser_type().toString();

                String userName = "";

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(MainPage_drawer.this);
                    role_id = app.getSubSalesId();
                    u_id = app.getSalesPersonId();
                    Snackbar.make(layout_main, "Taking Order for : " + app.getUserName(), Snackbar.LENGTH_LONG).show();

                } else {
                    u_id = owner_id;
                }


            }

            Log.e("user & Role", u_id + " -- " + role_id);


            Call<UserDetailResponse> userDetailResponseCall = adminAPI.getUserDetail(u_id);
            userDetailResponseCall.enqueue(new Callback<UserDetailResponse>() {
                @Override
                public void onResponse(Call<UserDetailResponse> call, Response<UserDetailResponse> response) {
                    UserDetailResponse detailResponse = response.body();

                    Log.e("response", new Gson().toJson(detailResponse));

                    if (detailResponse != null && detailResponse.isStatus()) {
                        BTL.user = detailResponse.getData().getUser();
                        BTL.distributor = detailResponse.getData().getDistributor();
                        BTL.addressList.addAll(detailResponse.getData().getAddress());
                    }
                }

                @Override
                public void onFailure(Call<UserDetailResponse> call, Throwable t) {
                    Log.e("Failure", t.getMessage());
                }
            });

            List<NameValuePair> para = new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id", u_id));

            new GetCartByQty(para).execute();

        } else {
            apps = new AppPrefs(MainPage_drawer.this);
            apps.setCart_QTy("");
        }


        String qu1 = apps.getCart_QTy();
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

        setRefershData();


        if (user_data.size() != 0) {

            app = new AppPrefs(MainPage_drawer.this);
            //if(app.getUser_LoginInfo().toString().equalsIgnoreCase("1")) {

            if (app.getUserRoleId().equalsIgnoreCase("3") || app.getUserRoleId().equalsIgnoreCase("4") || app.getUserRoleId().equalsIgnoreCase("5")) {
                img_ledgerbills.setVisibility(View.VISIBLE);
            } else {
                for (int i = 0; i < user_data.size(); i++) {

                    owner_id = user_data.get(i).getUser_id().toString();

                    role_id = user_data.get(i).getUser_type().toString();

                    if (role_id.equalsIgnoreCase("6")) {

                        app = new AppPrefs(MainPage_drawer.this);
                        role_id = app.getSubSalesId().toString();
                        u_id = app.getSalesPersonId().toString();
                        if (role_id.equalsIgnoreCase("5") || role_id.equalsIgnoreCase("4")) {
                            img_ledgerbills.setVisibility(View.VISIBLE);
                        } else {
                            img_ledgerbills.setVisibility(View.GONE);
                        }

                    } else if (role_id.equalsIgnoreCase("7")) {
                        img_ledgerbills.setVisibility(View.GONE);
                        //Log.e("IDIDD",""+app.getSalesPersonId().toString());
                    } else {
                        img_ledgerbills.setVisibility(View.GONE);
                    }
                }

            }
        } else {
            img_ledgerbills.setVisibility(View.GONE);
        }
        //}

        img_ledgerbills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(MainPage_drawer.this);
                app.setUser_notification("mainpage_drawer");
                Intent i = new Intent(MainPage_drawer.this, DisMyOrders.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("BACK", "MAIN");
                startActivity(i);
            }
        });

        img_home.setImageResource(R.drawable.ic_action_btl_search);

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage_drawer.this, Search.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    image_drawer.setImageResource(R.drawable.ic_drawer);

                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                    image_drawer.setImageResource(R.drawable.ic_action_btl_back);
                }

                mDrawerToggle = new ActionBarDrawerToggle(MainPage_drawer.this, mDrawerLayout, R.drawable.ic_drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                    /*
                     * Called when a drawer has settled in a completely closed state
                     */
                    public void onDrawerClosed(View view) {
                        Log.d("drawerToggle", "Drawer closed");
                        super.onDrawerClosed(view);
                        image_drawer.setImageResource(R.drawable.ic_drawer);
                        //getActionBar().setTitle(R.string.app_name);
                        invalidateOptionsMenu(); //Creates call to onPrepareOptionsMenu()
                    }

                    /*
                     * Called when a drawer has settled in a completely open state
                     */
                    public void onDrawerOpened(View drawerView) {
                        Log.d("drawerToggle", "Drawer opened");
                        super.onDrawerOpened(drawerView);
                        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
//                      getActionBar().setTitle("NavigationDrawer");
                        invalidateOptionsMenu();
                    }
                };
                mDrawerLayout.setDrawerListener(mDrawerToggle);

            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage_drawer.this, Main_CategoryPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apps = new AppPrefs(MainPage_drawer.this);
                apps.setUser_notification("mainpage_drawer");
                Intent i = new Intent(MainPage_drawer.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apps = new AppPrefs(MainPage_drawer.this);
                apps.setUser_notification("mainpage_drawer");
                Intent i = new Intent(MainPage_drawer.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Home) {
            Intent i = new Intent(MainPage_drawer.this, MainPage_drawer.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);


        } else if (id == R.id.login) {
            apps = new AppPrefs(MainPage_drawer.this);

            //Log.e("Login id", "" + apps.getUser_LoginInfo().toString());

            if (apps.getUser_LoginInfo().equalsIgnoreCase("1")) {

                //Intent i = new Intent(MainPage_drawer.this, Btl_Profile.class);
                Intent i = new Intent(MainPage_drawer.this, User_Profile.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else {

                Intent i = new Intent(MainPage_drawer.this, LogInPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }

        } else if (id == R.id.Dashboard) {
            Intent i = new Intent(MainPage_drawer.this, DistributorActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.menu_gst) {
            Intent intent = new Intent(getApplicationContext(), GSTFormActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.menu_comboOffer) {
            Intent intent = new Intent(getApplicationContext(), ComboOfferListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.menu_videoGallery) {
            Intent intent = new Intent(this, VideoGalleryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.menu_downloads) {
            Intent intent = new Intent(this, DownloadsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.CompanySales) {
            if (apps.getUserRoleId().equals(C.ADMIN)) {
                Intent i = new Intent(MainPage_drawer.this, AdminDashboard.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else {
                Intent i = new Intent(MainPage_drawer.this, UserTypeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        } else if (id == R.id.DistributorSales) {
            Intent i = new Intent(MainPage_drawer.this, SalesTypeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.categories) {

            //  Intent i = new Intent(MainPage_drawer.this,Main_CategoryPage.class);
            apps = new AppPrefs(MainPage_drawer.this);
            apps.setUser_CatId("0");

            Intent i = new Intent(MainPage_drawer.this, Drawer_CategoryPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.aboutus) {

            Intent i = new Intent(MainPage_drawer.this, AboutUs_Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.enquiry) {
            Intent i = new Intent(MainPage_drawer.this, Enquiry.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else if (id == R.id.becomeDealer) {
            Intent i = new Intent(getApplicationContext(), BecomeSellerActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.contact) {
            Intent i = new Intent(MainPage_drawer.this, Contact.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.logout) {

            // TODO Auto-generated method stub

            android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(
                    MainPage_drawer.this);
            // Setting Dialog Title
            alertDialog.setTitle("Log out application?");
            // Setting Dialog Message
            alertDialog
                    .setMessage("Are you sure you want to log out?");

            // Setting Icon to Dialog
            // .setIcon(R.drawable.ic_launcher);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {

                            apps = new AppPrefs(MainPage_drawer.this);
                            if (apps.getUser_LoginWith().equalsIgnoreCase("0")) {

                                apps.setUser_LoginInfo("");
                                apps.setUser_PersonalInfo("");
                                apps.setUser_SocialIdInfo("");
                                apps.setUser_SocialFirst("");
                                apps.setUser_Sociallast("");
                                apps.setUser_Socialemail("");
                                apps.setUser_SocialReInfo("");
                                apps.setUserRoleId("");
                                db = new DatabaseHandler(MainPage_drawer.this);
                                db.Delete_user_table();
                                db.Clear_ALL_table();
                                db.close();


                                Intent i = new Intent(MainPage_drawer.this, MainPage_drawer.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();

                            } else if (apps.getUser_LoginWith().equalsIgnoreCase("1")) {

                                apps.setUser_LoginInfo("");
                                apps.setUser_PersonalInfo("");
                                apps.setUser_SocialIdInfo("");
                                apps.setUser_SocialFirst("");
                                apps.setUser_Sociallast("");
                                apps.setUser_Socialemail("");
                                apps.setUser_SocialReInfo("");
                                apps.setUserRoleId("");
                                db = new DatabaseHandler(MainPage_drawer.this);
                                db.Delete_user_table();
                                db.Clear_ALL_table();
                                db.close();

                                LoginManager.getInstance().logOut();

                                Intent i = new Intent(MainPage_drawer.this, MainPage_drawer.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();

                            } else if (apps.getUser_LoginWith().equalsIgnoreCase("2")) {

                                apps.setUser_LoginInfo("");
                                apps.setUser_PersonalInfo("");
                                apps.setUser_SocialIdInfo("");
                                apps.setUser_SocialFirst("");
                                apps.setUser_Sociallast("");
                                apps.setUser_Socialemail("");
                                apps.setUser_SocialReInfo("");
                                apps.setUserRoleId("");
                                db = new DatabaseHandler(MainPage_drawer.this);
                                db.Delete_user_table();
                                db.Clear_ALL_table();
                                db.close();


                                if (mGoogleApiClient.isConnected()) {
                                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                                    mGoogleApiClient.disconnect();
                                    //mGoogleApiClient.connect();
                                    // updateUI(false);
                                    //Log.e("Gmail","Logout Sccessfully");
                                    System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");

//                                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                                            new ResultCallback<Status>() {
//                                                @Override
//                                                public void onResult(Status status) {
//                                                    // ...
//                                                }
//                                            });
                                }

                             /*   Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(intent);
                                finish();
                                System.exit(0);*/
                                Intent i = new Intent(MainPage_drawer.this, MainPage_drawer.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }

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

            android.support.v7.app.AlertDialog dialog = alertDialog.create();

            dialog.show();



           /* Intent i = new Intent(MainPage_drawer.this, Btl_Profile.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);*/
        } else if (id == R.id.nav_share) {
            try {
                String shareBody = Globals.share;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,""+s+"(Open it in Google Play Store to Download the Application)");

                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            } catch (Exception ignored) {

            }
        } else if (id == R.id.bulkEnquiry) {
            Intent intent = new Intent(getApplicationContext(), BulkEnquiryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        //Log.e("System GC", "Called");
        if (handler != null)
            handler.postDelayed(Update, 7000);
        super.onResume();
        if (app.getUser_LoginInfo().equals("1")) {
            new GetUnreadInbox().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            setRefershData();

            if (user_data.size() != 0) {
                for (int i = 0; i < user_data.size(); i++) {

                    owner_id = user_data.get(i).getUser_id().toString();

                    role_id = user_data.get(i).getUser_type().toString();

                    String userName = "";

                    if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                        app = new AppPrefs(MainPage_drawer.this);
                        role_id = app.getSubSalesId();
                        u_id = app.getSalesPersonId();
                        Snackbar.make(layout_main, "Taking Order for : " + app.getUserName(), Snackbar.LENGTH_LONG).show();

                    } else {
                        u_id = owner_id;
                    }


                }

                List<NameValuePair> para = new ArrayList<NameValuePair>();

                para.add(new BasicNameValuePair("owner_id", owner_id));
                para.add(new BasicNameValuePair("user_id", u_id));

                new GetCartByQty(para).execute();

            } else {
                apps = new AppPrefs(MainPage_drawer.this);
                apps.setCart_QTy("");
            }
        }
    }

    @Override
    protected void onPause() {

        if (handler != null)
            handler.removeCallbacks(Update);
        super.onPause();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();

        //Log.e("System GC", "Called");
    }

    private void slider() {
       /* for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);*/

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(MainPage_drawer.this, arraylist_slider));
        mPager.setPageTransformer(true, new ParallaxPageTransformer());

       /* CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);*/

      /*  final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);*/


        NUM_PAGES = arraylist_slider.size();


        // Auto start of viewpager

        handler = new Handler();
        Update = new Runnable() {
            public void run() {

                if (mPager.getCurrentItem() == NUM_PAGES - 1) {
                    mPager.setCurrentItem(0, true);
                } else {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
                }

                /*if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage, true);
                currentPage++;*/
                //Log.e("Current page",""+currentPage);
                //Log.e("NUM PAGES",""+NUM_PAGES);
                handler.postDelayed(this, 7000);
            }
        };
        /*Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1500,5000);*/

        handler.postDelayed(Update, 7000);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int mCurrentFragmentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                boolean isGoingToRightPage = position == mCurrentFragmentPosition;
                if (isGoingToRightPage) {
                    // user is going to the right page
                } else {
                    // user is going to the left page
                }
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentFragmentPosition = position;
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        swipeTimer.schedule;
    }

    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        mSignInClicked = false;

        // updateUI(true);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void onResult(People.LoadPeopleResult arg0) {
        // TODO Auto-generated method stub

    }

    private void setLayout(ArrayList<Bean_Stickers> str) {
        // TODO Auto-generated method stub

        lmain1.removeAllViews();

        //Log.e("str.size", "" + str.size());

        for (int ij = 0; ij < str.size(); ij++) {

            LinearLayout lmain = new LinearLayout(MainPage_drawer.this);
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
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    350, 300, 1.0f);
            LinearLayout l1 = new LinearLayout(MainPage_drawer.this);

            l1.setLayoutParams(par1);
            l1.setPadding(5, 5, 5, 5);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(MainPage_drawer.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 5, 5, 5);

            final LinearLayout l2 = new LinearLayout(MainPage_drawer.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 5, 5, 5);
            l2.setVisibility(View.GONE);

            count = ij;
            final ImageView img_a = new ImageView(MainPage_drawer.this);

            try {

                Picasso.with(getApplicationContext())
                        .load(Globals.server_link + str.get(ij).getImg())
                        .placeholder(R.drawable.btl_watermark)
                        .into(img_a);


            } catch (NullPointerException e) {

            }

            par21.setMargins(5, 5, 10, 5);

            img_a.setLayoutParams(par21);
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            l1.addView(img_a);


            lmain.addView(l1);

            lmain1.addView(lmain);

        }

        setLayout1(category_arra);
    }

    private void setLayout1(ArrayList<Bean_category> str) {
        // TODO Auto-generated method stub
        lmain2.removeAllViews();
        //Log.e("str",""+str.size());

        Log.e("Category Size", "->" + str.size());

        int pos = 0;
        for (int ij = 0; ij < str.size(); ij++) {

            LinearLayout lmain = new LinearLayout(MainPage_drawer.this);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            //lmain.setPadding(1, 1, 1, 1);
            lmain.setBackgroundColor(Color.parseColor("#f58634"));
            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, 200, 1.0f);

            LinearLayout.LayoutParams par22 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, 200, 1.0f);

            LinearLayout.LayoutParams par23 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams par24 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 200);

            LinearLayout.LayoutParams pargrid = new LinearLayout.LayoutParams(
                    300, 300, 1.0f);
            LinearLayout l1 = new LinearLayout(MainPage_drawer.this);
            l1.setLayoutParams(par1);

//            l1.setPadding(5, 0, 5, 0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            // l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            l3 = new LinearLayout(MainPage_drawer.this);
            l3.setLayoutParams(pargrid);
            l3.setOrientation(LinearLayout.VERTICAL);
            pargrid.setMargins(0, 0, 0, 1);
            // par21.set
            l3.setLayoutParams(pargrid);
            l3.setGravity(Gravity.CENTER);
//            l3.setBackgroundResource(R.drawable.sticker_gradient);
            l3.setTag(str.get(ij).getId().toString());
            l3.setBackgroundColor(Color.parseColor("#FFFFFF"));

            LinearLayout l4 = new LinearLayout(MainPage_drawer.this);
            l4.setLayoutParams(pargrid);
            l4.setOrientation(LinearLayout.VERTICAL);
            // pargrid.setMargins(1, 1, 1, 1);
            pargrid.setMargins(1, 0, 0, 1);
            l4.setLayoutParams(pargrid);
            l4.setGravity(Gravity.CENTER);
            //l4.setBackgroundResource(R.drawable.sticker_gradient);
            l4.setBackgroundColor(Color.parseColor("#FFFFFF"));

            /*LinearLayout l5 = new LinearLayout(MainPage_drawer.this);
            l5.setLayoutParams(pargrid);
            l5.setOrientation(LinearLayout.VERTICAL);
            //pargrid.setMargins(1, 1, 1, 1);
            pargrid.setMargins(1, 0, 0, 1);
            l5.setLayoutParams(pargrid);
            l5.setGravity(Gravity.CENTER);
//            l5.setBackgroundResource(R.drawable.sticker_gradient);
            l5.setBackgroundColor(Color.parseColor("#FFFFFF"));*/

            final LinearLayout l2 = new LinearLayout(MainPage_drawer.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 0, 5, 0);
            l2.setVisibility(View.GONE);


            count = ij;
            pos = ij;
            final ImageView img_a1 = new ImageView(MainPage_drawer.this);
            final ImageView img_c1 = new ImageView(MainPage_drawer.this);
            final ImageView img_b1 = new ImageView(MainPage_drawer.this);

            final TextView title = new TextView(MainPage_drawer.this);

            final TextView title_b = new TextView(MainPage_drawer.this);

            final TextView title_c = new TextView(MainPage_drawer.this);
            if (count == str.size()) {


            } else {
                aa = "0";
                //Log.e("AVVV", "" + Globals.server_link + str.get(count).getImg());
                // imageloader.DisplayImage(Globals.server_link+str.get(count).getImg(), img_a1);
                Picasso.with(getApplicationContext())
                        .load(Globals.server_link + str.get(count).getImg())
                        .placeholder(R.drawable.btl_watermark)
                        .into(img_a1);

                // img_a.setImageResource(str.get(count));
                img_a1.setLayoutParams(par24);
                img_a1.setTag(str.get(count).getId().toString());

                img_a1.setPadding(10, 10, 10, 10);

                l3.addView(img_a1);


                title.setText(str.get(count).getName().toString());
                title.setLayoutParams(par23);
                title.setGravity(Gravity.CENTER);
                title.setTextSize(14);
                title.setTextColor(Color.parseColor("#f58634"));
                title.setPadding(10, 10, 10, 10);
                title.setTag(str.get(count).getIs_child());
                l3.addView(title);


                l1.addView(l3);

                count = count + 1;

                if (count == str.size()) {


                } else {
                    bb = "0";


                    Picasso.with(getApplicationContext())
                            .load(Globals.server_link + str.get(count).getImg())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_b1);

                    // img_b.setImageResource(str.get(count));
                    img_b1.setLayoutParams(par24);
                    img_b1.setTag(str.get(count).getId());
                    img_b1.setPadding(10, 10, 10, 10);

                    l4.addView(img_b1);


                    title_b.setText(str.get(count).getName().toString());
                    title_b.setLayoutParams(par23);
                    title_b.setTextSize(14);
                    title_b.setTextColor(Color.parseColor("#f58634"));
                    title_b.setGravity(Gravity.CENTER);
                    title_b.setPadding(10, 10, 10, 10);
                    title_b.setTag(str.get(count).getIs_child());
                    l4.addView(title_b);


                    l1.addView(l4);

                    /*count = count + 1;

                    if(count == str.size()){


                    }else {
                        cc = "0";

                        Picasso.with(getApplicationContext())
                                .load(Globals.server_link+str.get(count).getImg())
                                .placeholder(R.drawable.btl_watermark)
                                .into(img_c1);

                        //img_c.setImageResource(str.get(count));
                        img_c1.setLayoutParams(par24);
                        img_c1.setTag(str.get(count).getId());
                        img_c1.setPadding(10, 10, 10, 10);

                        l5.addView(img_c1);


                        title_c.setText(str.get(count).getName().toString());
                        title_c.setLayoutParams(par23);
                        title_c.setTextSize(12);
                        title_c.setTextColor(Color.parseColor("#f58634"));
                        title_c.setGravity(Gravity.CENTER);
                        title_c.setPadding(10, 10, 10, 10);
                        title_c.setTag(str.get(count).getIs_child());
                        l5.addView(title_c);
                        l1.addView(l5);
                    }*/
                }
            }
            ij = count;

            lmain.addView(l1);


            lmain2.addView(lmain);


            l3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if (!aa.equalsIgnoreCase("1")) {*/
                    String CatId = img_a1.getTag().toString();
                    //Log.e("A", "" + img_a1.getTag().toString());
                    String Cat_is_child = title.getTag().toString();
                    if (Cat_is_child.equalsIgnoreCase("0")) {
                        apps = new AppPrefs(MainPage_drawer.this);
                        apps.setUser_notification("product");
                        apps.setUser_CatId(CatId);
                        apps.setPage_Data("1");
                        Intent i = new Intent(MainPage_drawer.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        //Log.e("CATID", "" + CatId);
                        apps = new AppPrefs(MainPage_drawer.this);
                        apps.setUser_notification("");
                        apps.setUser_CatId(CatId);
                        Intent i = new Intent(MainPage_drawer.this, Main_CategoryPage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
                /*}*/
            });

            l4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (!bb.equalsIgnoreCase("1")) {*/
                    String CatId = img_b1.getTag().toString();
                    //Log.e("A",""+img_b1.getTag().toString());
                    String Cat_is_child = title_b.getTag().toString();
                    if (Cat_is_child.equalsIgnoreCase("0")) {
                        apps = new AppPrefs(MainPage_drawer.this);
                        apps.setUser_notification("product");
                        apps.setUser_CatId(CatId);
                        apps.setPage_Data("1");
                        Intent i = new Intent(MainPage_drawer.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        apps = new AppPrefs(MainPage_drawer.this);
                        apps.setUser_notification("");
                        apps.setUser_CatId(CatId);
                        Intent i = new Intent(MainPage_drawer.this, Main_CategoryPage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
               /* }*/
            });
            /*l5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  *//*  if (!cc.equalsIgnoreCase("1")) {*//*
                        String CatId = img_c1.getTag().toString();
                    //Log.e("A",""+img_c1.getTag().toString());
                        String Cat_is_child = title_c.getTag().toString();
                        if (Cat_is_child.equalsIgnoreCase("0")) {
                            apps = new AppPrefs(MainPage_drawer.this);
                            apps.setUser_notification("product");
                            apps.setUser_CatId(CatId);
                            apps.setPage_Data("1");
                            Intent i = new Intent(MainPage_drawer.this, Product_List.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        } else {
                            apps = new AppPrefs(MainPage_drawer.this);
                            // apps.setUser_notification("product");
                            apps.setUser_CatId(CatId);
                            Intent i = new Intent(MainPage_drawer.this, Main_CategoryPage.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    }
                *//*}*//*
            });*/
        }
        // init(arraylist_slider);
        slider();
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainPage_drawer.this);
            builder.setMessage("Are you sure want to Exit?")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();

						/*Intent main = new Intent(MainPage_Slider.this,
                                MainPage_Slider.class);
						main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(main);*/
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();

                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {

        //Log.e("requestCode", "" + requestCode);
        //Log.e("responseCode", "" + responseCode);
        super.onActivityResult(requestCode, responseCode, intent);
        switch (requestCode) {

           /* case 64206:

                callbackManager.onActivityResult(requestCode, responseCode, intent);
                break;*/
            case 0:

                if (requestCode == RC_SIGN_IN) {

                    if (responseCode != RESULT_OK) {
                        mSignInClicked = false;
                    }

                    mIntentInProgress = false;

                    if (!mGoogleApiClient.isConnecting()) {
                        mGoogleApiClient.connect();
                    }
                }
                break;

            default:
                if (requestCode == RC_SIGN_IN) {

                    if (responseCode != RESULT_OK) {
                        mSignInClicked = false;
                    }

                    mIntentInProgress = false;

                    if (!mGoogleApiClient.isConnecting()) {
                        mGoogleApiClient.connect();
                    }
                }
                break;

        }

    }

    private void setLatestProducts() {
        if (latestProductList.size() > 0) {
            for (int i = 0; i < latestProductList.size(); i++) {
                View view = getLayoutInflater().inflate(R.layout.layout_latest_product_item, null);

                ImageView img_product = (ImageView) view.findViewById(R.id.img_product);
                TextView txt_productName = (TextView) view.findViewById(R.id.txt_productName);

                Picasso.with(this).load(Globals.IMAGE_LINK + latestProductList.get(i).getPro_image())
                        .into(img_product);

                txt_productName.setText(latestProductList.get(i).getPro_name() + " - (" + latestProductList.get(i).getPro_code() + ")");

                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        app.setproduct_id(latestProductList.get(finalI).getPro_id());
                        app.setRef_Detail("");
                        Intent i = new Intent(getApplicationContext(), BTLProduct_Detail.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });

                layout_latestProducts.addView(view);
            }
        } else {
            layout_latestProductsLabel.setVisibility(View.GONE);
        }
    }

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(MainPage_drawer.this);

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

    public String GetCartMainByQty(final List<NameValuePair> params) {

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
                    //System.out.println("error1: " + e.toString());
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

    private void setComboOfferData() {
        if (comboOfferItemList.size() > 0) {
            layout_comboOffers.setVisibility(View.VISIBLE);

            for (int i = 0; i < comboOfferItemList.size(); i++) {
                LinearLayout lmain = new LinearLayout(MainPage_drawer.this);
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
                LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                        350, 300, 1.0f);
                LinearLayout l1 = new LinearLayout(MainPage_drawer.this);

                l1.setLayoutParams(par1);
                l1.setPadding(5, 5, 5, 5);
                l1.setOrientation(LinearLayout.HORIZONTAL);
                l1.setGravity(Gravity.LEFT | Gravity.CENTER);

                LinearLayout l3 = new LinearLayout(MainPage_drawer.this);
                l3.setLayoutParams(par3);
                l3.setOrientation(LinearLayout.VERTICAL);
                l3.setPadding(5, 5, 5, 5);

                final LinearLayout l2 = new LinearLayout(MainPage_drawer.this);
                l2.setLayoutParams(par2);
                l2.setGravity(Gravity.LEFT | Gravity.CENTER);
                l2.setOrientation(LinearLayout.HORIZONTAL);
                l2.setPadding(5, 5, 5, 5);
                l2.setVisibility(View.GONE);

                final ImageView img_a = new ImageView(MainPage_drawer.this);

                try {

                    Picasso.with(getApplicationContext())
                            .load(Globals.server_link + comboOfferItemList.get(i).getImagePath())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_a);


                } catch (NullPointerException e) {

                }

                final int finalI = i;
                img_a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ComboOfferActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("combo_id", comboOfferItemList.get(finalI).getOfferID());
                        startActivity(intent);
                    }
                });

                par21.setMargins(5, 5, 10, 5);

                img_a.setLayoutParams(par21);
                img_a.setScaleType(ImageView.ScaleType.FIT_XY);
                l1.addView(img_a);


                lmain.addView(l1);

                layout_comboPacks.addView(lmain);
            }

            LinearLayout lmain = new LinearLayout(MainPage_drawer.this);
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
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    350, 300, 1.0f);
            LinearLayout l1 = new LinearLayout(MainPage_drawer.this);

            l1.setLayoutParams(par1);
            l1.setPadding(5, 5, 5, 5);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(MainPage_drawer.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 5, 5, 5);

            final LinearLayout l2 = new LinearLayout(MainPage_drawer.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 5, 5, 5);
            l2.setVisibility(View.GONE);

            final ImageView img_a = new ImageView(MainPage_drawer.this);

            try {

                Picasso.with(getApplicationContext())
                        .load(R.drawable.icon_more)
                        .placeholder(R.drawable.btl_watermark)
                        .into(img_a);


            } catch (NullPointerException e) {

            }
            img_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ComboOfferListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            par21.setMargins(5, 5, 10, 5);

            img_a.setLayoutParams(par21);
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
            l1.addView(img_a);


            lmain.addView(l1);

            layout_comboPacks.addView(lmain);
        }
    }

    public class pImageAdapter extends BaseAdapter {

        ArrayList<Integer> image_url = new ArrayList<Integer>();
        ArrayList<String> title = new ArrayList<String>();
        Context context;


        LayoutInflater layoutinflater;
        int position1;
        ImageView im;
        TextView tv;
        Activity activity;
        int display_width;
        FrameLayout framelayout_image;


        public pImageAdapter(Context context, ArrayList<Integer> categoryimg, LayoutInflater layoutinflater,
                             Activity activity) {

            // TODO Auto-generated constructor stub
            this.activity = activity;
            this.context = context;
            this.image_url = categoryimg;

            this.layoutinflater = layoutinflater;

        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return image_url.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            position1 = position;
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            display_width = (metrics.widthPixels / 2);
            if (convertView == null) {

                convertView = layoutinflater.inflate(R.layout.grid_offer, null);

            }

            framelayout_image = (FrameLayout) convertView
                    .findViewById(R.id.framelayout_image);
            im = (ImageView) convertView.findViewById(R.id.imageView1);


            framelayout_image.getLayoutParams().height = display_width;

            im.setImageResource(image_url.get(position));

            return convertView;

        }

    }

    public class Set_Home_Data extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        MainPage_drawer.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                if (apps.getUser_LoginInfo().equals("1") && user_data.size() > 0) {
                    if (apps.getUserRoleId().equals(C.ADMIN) || apps.getUserRoleId().equals(C.COMP_SALES_PERSON) || apps.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON)) {
                        parameters.add(new BasicNameValuePair("user_id", apps.getSalesPersonId()));
                        parameters.add(new BasicNameValuePair("role_id", apps.getSubSalesId()));
                    } else {
                        parameters.add(new BasicNameValuePair("user_id", apps.getUserId()));
                        parameters.add(new BasicNameValuePair("role_id", apps.getUserRoleId()));
                    }
                }

                Globals.generateNoteOnSD(getApplicationContext(), "Home/App_GetHomePageModuleData" + "\n" + parameters.toString());

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Home/App_GetHomePageModuleData", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());
                loadingView.dismiss();
                new Set_Home_Data().cancel(true);
                result = "" + 1;
                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            Globals.generateNoteOnSD(getApplicationContext(), result_1);
            try {


                if (result_1 == null
                        || (result_1.equalsIgnoreCase(""))) {

                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(MainPage_drawer.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else if (result_1.equalsIgnoreCase("1")) {

                    Globals.CustomToast(MainPage_drawer.this, "No Internet Connection", getLayoutInflater());
                    loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(MainPage_drawer.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        category_arra.clear();
                        sticker_arra.clear();
                        arraylist_slider.clear();
                        latestProductList.clear();

                        try {

                            JSONObject joj = jObj.getJSONObject("data");

                            JSONArray jArray_slider = joj.getJSONArray("sliders");
                            JSONArray jArray_sticker = joj.getJSONArray("stickers");
                            JSONArray jArray_category = joj.getJSONArray("categories");
                            JSONArray jArray_marquee_msg = joj.getJSONArray("marquee_msg");
                            JSONArray latest_products = joj.getJSONArray("latest_products");
                            JSONArray combo_data = joj.getJSONArray("combo_data");

                            for (int z = 0; z < jArray_slider.length(); z++) {

                                JSONObject j = jArray_slider.getJSONObject(z);

                                Bean_Slider bean = new Bean_Slider();
                                bean.setId(j.getString("id"));
                                bean.setImg(j.getString("app_image"));
                                bean.setLink_id(j.getString("link_id"));
                                bean.setLink_type(j.getString("link_type"));
                                bean.setHas_child(j.getString("has_child"));
                                //Log.e("app_image", "" + j.getString("app_image"));
                                arraylist_slider.add(bean);
                            }

                            //Log.e("slider_arra",""+arraylist_slider.size());

                            for (int z = 0; z < jArray_sticker.length(); z++) {

                                JSONObject j = jArray_sticker.getJSONObject(z);


                                Bean_Stickers bean = new Bean_Stickers();
                                bean.setId(j.getString("link"));
                                bean.setImg(j.getString("img"));
                                sticker_arra.add(bean);


                            }
                            //Log.e("sticker_arra",""+sticker_arra.size());
                            for (int z = 0; z < jArray_category.length(); z++) {

                                JSONObject j = jArray_category.getJSONObject(z);


                                Bean_category bean = new Bean_category();
                                bean.setId(j.getString("id"));
                                bean.setName(j.getString("name"));
                                bean.setIs_child(j.getString("is_child"));
                                //Log.e("is_child", "" + j.getString("is_child"));
                                bean.setImg(j.getString("app_image"));
                                //Log.e("app_image", "" +j.getString("app_image"));
                                category_arra.add(bean);


                            }
                            //Log.e("category_arra",""+category_arra.size());
                            for (int z = 0; z < jArray_marquee_msg.length(); z++) {

                                JSONObject j = jArray_marquee_msg.getJSONObject(z);

                                JSONObject jobject_marquee_msg = j.getJSONObject("FlashMessage");
                                String Marquee = jobject_marquee_msg.getString("msg");
                                //Log.e("marqueetextview",""+jobject_marquee_msg.getString("msg"));
                                marqueetextview.setText(Marquee);

                            }
                            if (sticker_arra.size() == 0) {
                                l_strickers.setVisibility(View.GONE);

                                lmain1.setVisibility(View.GONE);
                                setLayout1(category_arra);
                            } else {
                                l_strickers.setVisibility(View.VISIBLE);

                                lmain1.setVisibility(View.VISIBLE);
                                setLayout(sticker_arra);
                            }
                            //setLayout(sticker_arra);

                            for (int a = 0; a < combo_data.length(); a++) {
                                ComboOfferItem offerItem = new Gson().fromJson(combo_data.get(a).toString(), ComboOfferItem.class);
                                comboOfferItemList.add(offerItem);
                            }

                            setComboOfferData();

                            for (int i = 0; i < latest_products.length(); i++) {
                                JSONObject object = latest_products.getJSONObject(i);

                                JSONObject productObj = object.getJSONObject("Product");
                                JSONObject labelObj = object.getJSONObject("Label");

                                Bean_Product product = new Bean_Product();
                                product.setPro_id(productObj.getString("id"));
                                product.setPro_code(productObj.getString("product_code"));
                                product.setPro_name(productObj.getString("product_name"));
                                product.setPro_qty(productObj.getString("qty"));
                                product.setPro_mrp(productObj.getString("mrp"));
                                product.setPro_image(productObj.getString("image"));
                                product.setPackQty(Integer.parseInt(productObj.getString("pack_of_qty")));
                                product.setPro_sellingprice(productObj.getString("selling_price"));
                                product.setPro_label(labelObj.getString("name"));

                                latestProductList.add(product);
                            }

                            setLatestProducts();


                        } catch (Exception e) {
                        }

                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

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

                    Globals.CustomToast(MainPage_drawer.this, "SERVER ERROR", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String userStatus = jObj.getString("user_status");
                    if (userStatus.equals("0")) {
                        C.userInActiveDialog(MainPage_drawer.this);
                    }

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(MainPage_drawer.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(MainPage_drawer.this);
                        app.setCart_QTy("" + qu);


                    }

                }


            } catch (Exception j) {
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = apps.getCart_QTy();
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

    public class GetSupportCallInfo extends AsyncTask<Void, Void, String> {

        AppPrefs prefs;

        public GetSupportCallInfo() {
            prefs = new AppPrefs(getApplicationContext());
        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("user_id", prefs.getUserId().isEmpty() ? "0" : prefs.getUserId()));
            pairs.add(new BasicNameValuePair("role_id", prefs.getUserRoleId().isEmpty() ? "0" : prefs.getUserRoleId()));

            String json = new ServiceHandler().makeServiceCall(Globals.server_link + Globals.SUPPORT_INFO, ServiceHandler.POST, pairs);

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null && !s.isEmpty()) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getBoolean("status")) {
                        JSONArray data = object.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject main = data.getJSONObject(i);

                            SupportCallInfo info = new SupportCallInfo();
                            info.setTitle(main.getString("Title"));
                            info.setContact(main.getString("contact_no"));

                            callInfoList.add(info);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        }
    }

    private class GetUnreadInbox extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairList = new ArrayList<>();
            if (app.getUserRoleId().equals(C.ADMIN) || app.getUserRoleId().equals(C.COMP_SALES_PERSON) || app.getUserRoleId().equals(C.DISTRIBUTOR_SALES_PERSON)) {
                pairList.add(new BasicNameValuePair("owner_id", app.getUserId()));
            } else {
                pairList.add(new BasicNameValuePair("user_id", app.getUserId()));
            }

            String json = new ServiceHandler().makeServiceCall(Globals.server_link + Globals.GET_UNREAD_NOTIFICATION_COUNT, ServiceHandler.POST, pairList);

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null && !s.isEmpty()) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getBoolean("status")) {
                        int totalCount = object.getInt("total_unread_msg");
                        AppPrefs prefs = new AppPrefs(getApplicationContext());
                        prefs.setNoticount(totalCount);
                        BadgeUtils.clearBadge(getApplicationContext());
                        notification_unread.setText(String.valueOf(totalCount));
                        if (totalCount > 0) {
                            notification_unread.setVisibility(View.VISIBLE);
                            BadgeUtils.setBadge(getApplicationContext(), totalCount);
                        }


                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            } else {

            }
        }
    }

}
