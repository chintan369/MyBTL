package com.agraeta.user.btl.admin;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.CompanySalesPerson.RegisteredUserSkipOrderListActivity;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.UnRegisteredUserListActivity;
import com.agraeta.user.btl.Where_To_Buy;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.Dashboard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class AdminDashboard extends AppCompatActivity implements Callback<Dashboard> {


    LinearLayout layout_mainContent;
    TextView txt_orderCount, txt_orderSales, txt_orderCountDistributor, txt_orderSalesDistributor, txt_customerCount, txt_enquiryCount;

    Button btn_viewOrders, btn_users, btn_coupons, btn_enquiries, btn_schemes, btn_services, btn_registeredDSR, btn_unRegisteredDSR, btn_productStockReport;

    ImageView image_drawer;

    Custom_ProgressDialog progressDialog;

    AppPrefs prefs;

    String fromDate = "";
    String toDate = "";

    AdminAPI adminAPI;
    Call<Dashboard> dashboardCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        prefs = new AppPrefs(getApplicationContext());

        fetchIDs();
        setActionBar();
    }

    private void fetchIDs() {
        progressDialog = new Custom_ProgressDialog(this, "Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        layout_mainContent = (LinearLayout) findViewById(R.id.layout_mainContent);

        txt_orderCount = (TextView) findViewById(R.id.txt_orderCount);
        txt_orderSales = (TextView) findViewById(R.id.txt_orderSales);
        txt_orderCountDistributor = (TextView) findViewById(R.id.txt_orderCountDistributor);
        txt_orderSalesDistributor = (TextView) findViewById(R.id.txt_orderSalesDistributor);
        txt_customerCount = (TextView) findViewById(R.id.txt_customerCount);
        txt_enquiryCount = (TextView) findViewById(R.id.txt_enquiryCount);

        btn_viewOrders = (Button) findViewById(R.id.btn_viewOrders);
        btn_users = (Button) findViewById(R.id.btn_users);
        btn_coupons = (Button) findViewById(R.id.btn_coupons);
        btn_enquiries = (Button) findViewById(R.id.btn_enquiries);
        btn_schemes = (Button) findViewById(R.id.btn_schemes);
        btn_services = (Button) findViewById(R.id.btn_services);
        btn_registeredDSR = (Button) findViewById(R.id.btn_registeredDSR);
        btn_unRegisteredDSR = (Button) findViewById(R.id.btn_unRegisteredDSR);
        btn_productStockReport = (Button) findViewById(R.id.btn_productStockReport);

        btn_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserSelection(true);
            }
        });

        btn_viewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserSelection(false);
            }
        });

        btn_enquiries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserEnquiryTypeActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btn_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CouponListActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("labelName","COUPONS");
                startActivity(intent);
            }
        });

        btn_schemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SchemeListActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("labelName","SCHEMES");
                startActivity(intent);
            }
        });

        btn_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Where_To_Buy.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btn_registeredDSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RegisteredUserSkipOrderListActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btn_unRegisteredDSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), UnRegisteredUserListActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("fromAdmin",true);
                startActivity(intent);
            }
        });

        btn_productStockReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductStockReportActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        getDahboardData();
    }

    private void getDahboardData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.server_link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        adminAPI = retrofit.create(AdminAPI.class);

        dashboardCall = adminAPI.dashBoardData(null, null);
        dashboardCall.enqueue(this);
    }

    private void goToUserSelection(boolean forUserList) {
        Intent intent = new Intent(getApplicationContext(), UserListSelectionActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("forUser", forUserList);
        startActivity(intent);
    }

    private void startCountAnimation(int number, final TextView textView) {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, number);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText("" + (int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private void startCountAnimationForRuppe(int number, final TextView textView) {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, number);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(Globals.format((int) animation.getAnimatedValue()));
            }
        });
        animator.start();
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);

        View mCustomView = mActionBar.getCustomView();
        image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_filter = (ImageView) mCustomView.findViewById(R.id.img_filter);
        ImageView image_logout = (ImageView) mCustomView.findViewById(R.id.image_logout);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        FrameLayout unread = (FrameLayout) mCustomView.findViewById(R.id.unread);

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_filter.setVisibility(View.VISIBLE);
        image_logout.setVisibility(View.VISIBLE);
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

        image_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        img_home.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        unread.setVisibility(View.GONE);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView customTitleView = (TextView) getLayoutInflater().inflate(R.layout.simple_listitem_usertype, null);
        customTitleView.setText("Filter Dates");
        customTitleView.setGravity(Gravity.CENTER);
        //customTitleView.setBackgroundColor(getResources().getColor(R.color.black));
        builder.setCustomTitle(customTitleView);

        //builder.setTitle("Filter Dates");

        View dialogView = getLayoutInflater().inflate(R.layout.layout_filter_date, null);
        final EditText edt_fromDate = (EditText) dialogView.findViewById(R.id.edt_fromDate);
        final EditText edt_toDate = (EditText) dialogView.findViewById(R.id.edt_toDate);
        Button btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        Button btn_submit = (Button) dialogView.findViewById(R.id.btn_submit);

        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        final SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        edt_fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int year = Calendar.getInstance().get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminDashboard.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        try {

                            if (!toDate.isEmpty()) {

                                Date dateFrom = sdf.parse(selectedDate);
                                Date dateTo = sdf.parse(toDate);

                                if (dateFrom.after(dateTo)) {
                                    Globals.Toast2(getApplicationContext(), "Select Date before \'To Date\'");
                                } else {
                                    edt_fromDate.setText(sdf2.format(dateFrom));
                                    fromDate = sdf.format(dateFrom);
                                }
                            } else {
                                Date dateFrom = sdf.parse(selectedDate);
                                edt_fromDate.setText(sdf2.format(dateFrom));
                                fromDate = sdf.format(dateFrom);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e("Exception", e.getMessage());
                        }
                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        edt_toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int year = Calendar.getInstance().get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminDashboard.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        try {

                            if (!fromDate.isEmpty()) {

                                Date dateTo = sdf.parse(selectedDate);
                                Date dateFrom = sdf.parse(fromDate);

                                if (dateTo.before(dateFrom)) {
                                    Globals.Toast2(getApplicationContext(), "Select Date after \'From Date\'");
                                } else {
                                    edt_toDate.setText(sdf2.format(dateTo));
                                    toDate = sdf.format(dateTo);
                                }
                            } else {
                                Date dateTo = sdf.parse(selectedDate);
                                edt_toDate.setText(sdf2.format(dateTo));
                                toDate = sdf.format(dateTo);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e("Exception", e.getMessage());
                        }
                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                    dialog.dismiss();
                    progressDialog.show();
                    dashboardCall = adminAPI.dashBoardData(fromDate, toDate);
                    dashboardCall.enqueue(AdminDashboard.this);
                } else {
                    Globals.Toast2(getApplicationContext(), "Please select both dates!");
                }
            }
        });
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Logout from Application?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                prefs.setUserId("");
                prefs.setUserRoleId("");
                prefs.setUser_LoginInfo("0");
                DatabaseHandler db = new DatabaseHandler(AdminDashboard.this);
                db.Delete_user_table();
                db.Clear_ALL_table();
                db.close();
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainPage_drawer.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("CANCEL", null);

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    public void onResponse(Call<Dashboard> call, Response<Dashboard> response) {
        progressDialog.dismiss();

        Dashboard dashboard = response.body();

        if (dashboard.isStatus()) {

            String orderValue = String.valueOf(dashboard.getData().getCompanyOrder().getOrderValue());
            String distributorOrderValue = String.valueOf(dashboard.getData().getDistributorOrder().getOrderValue());

            //if (orderValue.length() > 5)
            //txt_orderSales.setText(Globals.format(dashboard.getData().getCompanyOrder().getOrderValue()));
            //else
            startCountAnimationForRuppe(dashboard.getData().getCompanyOrder().getOrderValue(), txt_orderSales);

            //if (distributorOrderValue.length() > 5)
            //txt_orderSalesDistributor.setText(Globals.format(dashboard.getData().getDistributorOrder().getOrderValue()));
            //else
            startCountAnimationForRuppe(dashboard.getData().getDistributorOrder().getOrderValue(), txt_orderSalesDistributor);

            startCountAnimation(Integer.parseInt(dashboard.getData().getCompanyOrder().getOrderCount()), txt_orderCount);
            startCountAnimation(Integer.parseInt(dashboard.getData().getDistributorOrder().getOrderCount()), txt_orderCountDistributor);


        } else {
            Globals.Toast2(getApplicationContext(), dashboard.getMessage());
        }
    }

    @Override
    public void onFailure(Call<Dashboard> call, Throwable t) {
        progressDialog.dismiss();
        Log.e("Exception", t.getMessage());
        Globals.showError(t, getApplicationContext());
    }
}
