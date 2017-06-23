package com.agraeta.user.btl.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.EnquiryCounts;
import com.agraeta.user.btl.model.ServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserEnquiryTypeActivity extends AppCompatActivity implements Callback<EnquiryCounts> {

    CardView layout_productEnquiry,layout_productRegistration,layout_businessEnquiry,layout_customerComplaint,layout_carrer,layout_corpOfficeEnquiry,layout_quotation,layout_registeredDealer;

    TextView txt_countProductEnquiry,txt_countProductRegistration,txt_countbusinessEnquiry,txt_countcustomerComplaint,txt_countCareer,txt_countcorpOfficeEnquiry,txt_countQuotation,txt_countRegisteredDealer;

    Custom_ProgressDialog progressDialog;

    String fromDate = "";
    String toDate = "";

    Call<EnquiryCounts> enquiryCountsCall;

    AdminAPI adminAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_enquiry_type);

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

                DatePickerDialog datePickerDialog = new DatePickerDialog(UserEnquiryTypeActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(UserEnquiryTypeActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    enquiryCountsCall = adminAPI.enquiryCountCall(fromDate, toDate);
                    enquiryCountsCall.enqueue(UserEnquiryTypeActivity.this);
                } else {
                    Globals.Toast2(getApplicationContext(), "Please select both dates!");
                }
            }
        });
    }

    private void fetchIDs() {
        Retrofit retrofit= ServiceGenerator.getService();
        adminAPI=retrofit.create(AdminAPI.class);

        progressDialog=new Custom_ProgressDialog(this,"Please wait");
        progressDialog.setCancelable(false);

        layout_productEnquiry=(CardView) findViewById(R.id.layout_productEnquiry);
        layout_productRegistration=(CardView) findViewById(R.id.layout_productRegistration);
        layout_businessEnquiry=(CardView) findViewById(R.id.layout_businessEnquiry);
        layout_customerComplaint=(CardView) findViewById(R.id.layout_customerComplaint);
        layout_carrer=(CardView) findViewById(R.id.layout_carrer);
        layout_corpOfficeEnquiry=(CardView) findViewById(R.id.layout_corpOfficeEnquiry);
        layout_quotation=(CardView) findViewById(R.id.layout_quotation);
        layout_registeredDealer=(CardView) findViewById(R.id.layout_registeredDealer);

        txt_countProductEnquiry=(TextView) findViewById(R.id.txt_countProductEnquiry);
        txt_countProductRegistration=(TextView) findViewById(R.id.txt_countProductRegistration);
        txt_countbusinessEnquiry=(TextView) findViewById(R.id.txt_countbusinessEnquiry);
        txt_countcustomerComplaint=(TextView) findViewById(R.id.txt_countcustomerComplaint);
        txt_countCareer=(TextView) findViewById(R.id.txt_countCareer);
        txt_countcorpOfficeEnquiry=(TextView) findViewById(R.id.txt_countcorpOfficeEnquiry);
        txt_countQuotation=(TextView) findViewById(R.id.txt_countQuotation);
        txt_countRegisteredDealer=(TextView) findViewById(R.id.txt_countRegisteredDealer);

        layout_productEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UserProductEnquiryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("enquiryName","PRODUCT ENQUIRY");
                startActivity(intent);
            }
        });

        layout_productRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProductRegistrationListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("enquiryName","PRODUCT REGISTRATION");
                startActivity(intent);
            }
        });

        layout_businessEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UserBusinessEnquiryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("enquiryName","BUSINESS ENQUIRY");
                startActivity(intent);
            }
        });

        layout_customerComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CustomerComplaintActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("enquiryName","CUSTOMER COMPLAINTS");
                startActivity(intent);
            }
        });

        layout_carrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CareerListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("enquiryName","CAREERS");
                startActivity(intent);
            }
        });

        layout_corpOfficeEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CorpOfficeListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("enquiryName","CORP. OFFICE ENQUIRY");
                startActivity(intent);
            }
        });

        layout_registeredDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisteredDealerListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("enquiryName","Registered Dealer");
                startActivity(intent);
            }
        });

        progressDialog.show();
        enquiryCountsCall=adminAPI.enquiryCountCall(null,null);
        enquiryCountsCall.enqueue(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResponse(Call<EnquiryCounts> call, Response<EnquiryCounts> response) {
        progressDialog.dismiss();
        EnquiryCounts enquiryCounts=response.body();

        if(enquiryCounts.isStatus()){
            Globals.startCountAnimation(enquiryCounts.getCountData().getProductEnquiry().getCount(),txt_countProductEnquiry);
            Globals.startCountAnimation(enquiryCounts.getCountData().getProductRegistration().getCount(),txt_countProductRegistration);
            Globals.startCountAnimation(enquiryCounts.getCountData().getBusinessEnquiry().getCount(),txt_countbusinessEnquiry);
            Globals.startCountAnimation(enquiryCounts.getCountData().getCareer().getCount(),txt_countCareer);
            Globals.startCountAnimation(enquiryCounts.getCountData().getCorporateOffice().getCount(),txt_countcorpOfficeEnquiry);
            Globals.startCountAnimation(enquiryCounts.getCountData().getCustomerComplain().getCount(),txt_countcustomerComplaint);
            Globals.startCountAnimation(enquiryCounts.getCountData().getQuotation().getCount(),txt_countQuotation);
            Globals.startCountAnimation(enquiryCounts.getCountData().getSeller().getCount(),txt_countRegisteredDealer);
        }
        else {
            Globals.Toast2(getApplicationContext(),enquiryCounts.getMessage());
        }
    }

    @Override
    public void onFailure(Call<EnquiryCounts> call, Throwable t) {
        progressDialog.dismiss();
        Globals.showError(t,getApplicationContext());
    }
}
