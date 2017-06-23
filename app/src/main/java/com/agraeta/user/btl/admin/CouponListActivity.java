package com.agraeta.user.btl.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.adapters.CouponListAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.coupons.CouponData;
import com.agraeta.user.btl.model.coupons.CouponDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponListActivity extends AppCompatActivity implements Callback<CouponData> {

    TextView txt_labelName;
    ListView list_coupons;
    List<CouponDetail> couponDetailList=new ArrayList<>();
    CouponListAdapter couponListAdapter;
    AdminAPI adminAPI;
    Call<CouponData> couponDataCall;

    int page=1;

    String fromDate="";
    String toDate="";
    String labelName="";

    Custom_ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);

        Intent intent=getIntent();
        labelName=intent.getStringExtra("labelName");

        setActionBar();
        fetchIDs();
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);

        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_filter = (ImageView) mCustomView.findViewById(R.id.img_filter);
        FrameLayout unread = (FrameLayout) mCustomView.findViewById(R.id.unread);

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        img_home.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        unread.setVisibility(View.GONE);

        img_filter.setVisibility(View.VISIBLE);
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

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

        final String previousFromDate = fromDate;
        final String previousToDate = toDate;

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

        try {
            edt_fromDate.setText(sdf2.format(sdf.parse(fromDate)));
            edt_toDate.setText(sdf2.format(sdf.parse(toDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        edt_fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int year = Calendar.getInstance().get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CouponListActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(CouponListActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                fromDate = previousFromDate;
                toDate = previousToDate;
                dialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                    dialog.dismiss();
                    progressDialog.show();
                    page = 1;
                    couponDataCall.cancel();
                    couponDetailList.clear();
                    couponListAdapter.notifyDataSetChanged();
                    couponDataCall = adminAPI.couponDataCall(fromDate, toDate, page);
                    couponDataCall.enqueue(CouponListActivity.this);
                } else {
                    Globals.Toast2(getApplicationContext(), "Please select both dates!");
                }
            }
        });
    }

    private void fetchIDs() {
        progressDialog=new Custom_ProgressDialog(this,"");
        progressDialog.setCancelable(false);
        txt_labelName=(TextView) findViewById(R.id.txt_labelName);
        list_coupons= (ListView) findViewById(R.id.list_coupons);
        couponListAdapter=new CouponListAdapter(couponDetailList,this);
        list_coupons.setAdapter(couponListAdapter);

        txt_labelName.setText(labelName);

        adminAPI= ServiceGenerator.getAPIServiceClass();

        progressDialog.show();
        couponDataCall=adminAPI.couponDataCall(null,null,page);
        couponDataCall.enqueue(this);

    }

    @Override
    public void onResponse(Call<CouponData> call, Response<CouponData> response) {
        progressDialog.dismiss();
        CouponData data=response.body();
        if(data.isStatus()){
            page++;
            couponDetailList.addAll(data.getCouponDetailList());
            couponListAdapter.notifyDataSetChanged();

            if(page<=data.getTotalPage()){

                if(fromDate.isEmpty() && toDate.isEmpty()){
                    couponDataCall=adminAPI.couponDataCall(null,null,page);
                }
                else couponDataCall=adminAPI.couponDataCall(fromDate,toDate,page);

                couponDataCall.enqueue(this);
            }
        }
        else {
            Globals.Toast2(this,data.getMessage());
        }
    }

    @Override
    public void onFailure(Call<CouponData> call, Throwable t) {
        progressDialog.dismiss();
        Globals.showError(t,this);
    }
}
