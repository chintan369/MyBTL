package com.agraeta.user.btl.CompanySalesPerson;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Distributor.DisSalesFormActivity;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.SkipOrderUnregisterUserActivity;
import com.agraeta.user.btl.UnRegisteredUserListActivity;

public class UserTypeActivity extends AppCompatActivity {
    Button btn_next,btn_todayDSR;
    AppPrefs apps;
    RadioButton rdo_dis,rdo_ret,rdo_pro,rdo_cpro,rdo_cdealer;
    DatabaseHandler db;

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
        setContentView(R.layout.activity_user_type);
        apps=new AppPrefs(getApplicationContext());
        apps.setCurrentPage("UserType");
        rdo_dis=(RadioButton)findViewById(R.id.radio_distributor);
        rdo_ret=(RadioButton)findViewById(R.id.radio_retailer);
        rdo_pro=(RadioButton)findViewById(R.id.radio_professional);
        rdo_cpro=(RadioButton)findViewById(R.id.radio_compprof);
        rdo_cdealer=(RadioButton)findViewById(R.id.radio_DealerOEM);
        //Log.e("USEr id",""+apps.getUserId());

        rdo_dis.setChecked(true);


        rdo_dis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rdo_dis.isChecked()){
                    rdo_ret.setChecked(false);
                    rdo_pro.setChecked(false);
                    rdo_cpro.setChecked(false);
                    rdo_cdealer.setChecked(false);
                    //Log.e("rdo_dis","checked");
                }
            }
        });

        rdo_ret.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rdo_ret.isChecked()){
                    rdo_pro.setChecked(false);
                    rdo_dis.setChecked(false);
                    rdo_cpro.setChecked(false);
                    rdo_cdealer.setChecked(false);
                    //Log.e("rdo_ret","checked");
                }
            }
        });

        rdo_pro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rdo_pro.isChecked()){
                    rdo_ret.setChecked(false);
                    rdo_dis.setChecked(false);
                    rdo_cpro.setChecked(false);
                    rdo_cdealer.setChecked(false);
                    //Log.e("rdo_pro","checked");
                }
            }
        });


        rdo_cpro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rdo_cpro.isChecked()){
                    rdo_ret.setChecked(false);
                    rdo_dis.setChecked(false);
                    rdo_pro.setChecked(false);
                    rdo_cdealer.setChecked(false);
                    //Log.e("rdo_pro","checked");
                }
            }
        });

        rdo_cdealer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(rdo_cdealer.isChecked()){
                    rdo_ret.setChecked(false);
                    rdo_dis.setChecked(false);
                    rdo_pro.setChecked(false);
                    rdo_cpro.setChecked(false);
                    //Log.e("rdo_pro","checked");
                }
            }
        });


        btn_next=(Button)findViewById(R.id.btn_next);
        btn_todayDSR=(Button)findViewById(R.id.btn_todayDSR);

        btn_todayDSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), UnRegisteredUserListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rdo_dis.isChecked()){
                    //Log.e("rdo_dis",""+rdo_dis.isChecked());
                    apps.setSubSalesId("3");
                }

                if(rdo_ret.isChecked()){
                    apps.setSubSalesId("8");
                    //Log.e("rdo_ret",""+rdo_ret.isChecked());
                }

                if(rdo_pro.isChecked()){
                    apps.setSubSalesId("9");
                    //Log.e("rdo_pro",""+rdo_pro.isChecked());
                }

                if(rdo_cpro.isChecked()){
                    apps.setSubSalesId("5");
                    //Log.e("rdo_pro",""+rdo_pro.isChecked());
                }

                if(rdo_cdealer.isChecked()){
                    apps.setSubSalesId("4");
                    //Log.e("rdo_pro",""+rdo_pro.isChecked());
                }

                //Log.e("checked",""+rdo_dis.isChecked());
                //Log.e("checked",""+rdo_ret.isChecked());
                //Log.e("checked",""+rdo_pro.isChecked());
                Intent intent=new Intent(getApplicationContext(),UserListActivity.class);

                startActivity(intent);

            }
        });

        setActionBar();

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
        ImageView img_target = (ImageView) mCustomView.findViewById(R.id.image_target);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_pin =(ImageView)mCustomView.findViewById(R.id.img_pin);
        img_pin.setVisibility(View.GONE);

        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        img_cart.setVisibility(View.GONE);
        img_home.setVisibility(View.GONE);
        img_category.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);

        ImageView img_add=(ImageView)mCustomView.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DisSalesFormActivity.class);
                startActivity(intent);
                finish();
            }
        });

        img_target.setVisibility(View.VISIBLE);
        img_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),TargetAchieveList.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView img_skip=(ImageView)mCustomView.findViewById(R.id.image_skip);
        img_skip.setVisibility(View.VISIBLE);
        img_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisteredUserSkipOrderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ImageView img_logout=(ImageView)mCustomView.findViewById(R.id.image_logout);
        img_logout.setVisibility(View.VISIBLE);

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),MainPage_drawer.class);
//                startActivity(intent);
//                finish();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        UserTypeActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle("Log Out application?");
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

                                apps = new AppPrefs(UserTypeActivity.this);
                                if (apps.getUser_LoginWith().equalsIgnoreCase("0")) {

                                    apps.setUser_LoginInfo("");
                                    apps.setUser_PersonalInfo("");
                                    apps.setUser_SocialIdInfo("");
                                    apps.setUser_SocialFirst("");
                                    apps.setUser_Sociallast("");
                                    apps.setUser_Socialemail("");
                                    apps.setUser_SocialReInfo("");

                                    apps.setCsalesId("");
                                    apps.setSubSalesId("");
                                    apps.setUserRoleId("");
                                    apps.setCurrentPage("");




                                    db = new DatabaseHandler(UserTypeActivity.this);
                                    db.Delete_user_table();
                                    db.Clear_ALL_table();
                                    db.close();
                                    Intent i = new Intent(UserTypeActivity.this, MainPage_drawer.class);
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
                alertDialog.show();
            }
        });

        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
//        db = new DatabaseHandler(Product_List.this);
//        bean_cart =  db.Get_Product_Cart();
//        db.close();
//        if(bean_cart.size() == 0){
//            txt.setVisibility(View.GONE);
//            txt.setText("");
//        }else{
//            txt.setVisibility(View.VISIBLE);
//            txt.setText(bean_cart.size()+"");
//        }

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),MainPage_drawer.class);
//                startActivity(intent);
//                finish();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        UserTypeActivity.this);
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

                                apps = new AppPrefs(UserTypeActivity.this);
                                if (apps.getUser_LoginWith().equalsIgnoreCase("0")) {

                                    apps.setUser_LoginInfo("");
                                    apps.setUser_PersonalInfo("");
                                    apps.setUser_SocialIdInfo("");
                                    apps.setUser_SocialFirst("");
                                    apps.setUser_Sociallast("");
                                    apps.setUser_Socialemail("");
                                    apps.setUser_SocialReInfo("");

                                    apps.setCsalesId("");
                                    apps.setSubSalesId("");
                                    apps.setUserRoleId("");
                                    apps.setCurrentPage("");




                                    db = new DatabaseHandler(UserTypeActivity.this);
                                    db.Delete_user_table();
                                    db.Clear_ALL_table();
                                    db.close();
                                    Intent i = new Intent(UserTypeActivity.this, MainPage_drawer.class);
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
                alertDialog.show();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    public void onBackPressed() {
//                Intent intent=new Intent(getApplicationContext(),MainPage_drawer.class);
//                startActivity(intent);
//                finish();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                UserTypeActivity.this);
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

                        apps = new AppPrefs(UserTypeActivity.this);
                        if (apps.getUser_LoginWith().equalsIgnoreCase("0")) {

                            apps.setUser_LoginInfo("");
                            apps.setUser_PersonalInfo("");
                            apps.setUser_SocialIdInfo("");
                            apps.setUser_SocialFirst("");
                            apps.setUser_Sociallast("");
                            apps.setUser_Socialemail("");
                            apps.setUser_SocialReInfo("");

                            apps.setCsalesId("");
                            apps.setSubSalesId("");
                            apps.setUserRoleId("");
                            apps.setCurrentPage("");




                            db = new DatabaseHandler(UserTypeActivity.this);
                            db.Delete_user_table();
                            db.Clear_ALL_table();
                            db.close();
                            Intent i = new Intent(UserTypeActivity.this, MainPage_drawer.class);
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
        alertDialog.show();
    }



}
