package com.agraeta.user.btl.admin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.OrderEnquiryData;
import com.agraeta.user.btl.model.OrderTrackingData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserOrderEnquiryHistoryActivity extends AppCompatActivity implements Callback<OrderEnquiryData> {

    ListView list_enquiry;
    ImageView btn_cancel;

    String orderID="";

    Custom_ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_en_hi_row);

        Intent intent=getIntent();
        orderID=intent.getStringExtra("orderID");

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {
        progressDialog=new Custom_ProgressDialog(this,"Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        list_enquiry=(ListView) findViewById(R.id.lst_en_row);
        btn_cancel= (ImageView) findViewById(R.id.btn_cancel);

        btn_cancel.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.server_link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AdminAPI adminAPI = retrofit.create(AdminAPI.class);

        Call<OrderEnquiryData> orderEnquiryDataCall = adminAPI.enquiryDataCall(orderID);
        orderEnquiryDataCall.enqueue(this);
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
        img_notification.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_home.setVisibility(View.GONE);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onResponse(Call<OrderEnquiryData> call, Response<OrderEnquiryData> response) {
        progressDialog.dismiss();

        OrderEnquiryData data=response.body();

        if(data.isStatus()){
            OrderEnquiryAdapter enquiryAdapter=new OrderEnquiryAdapter(data.getOrderEnquiryList());
            list_enquiry.setAdapter(enquiryAdapter);
        }
        else {
            Globals.Toast2(getApplicationContext(),data.getMessage());
        }
    }

    @Override
    public void onFailure(Call<OrderEnquiryData> call, Throwable t) {
        progressDialog.dismiss();

        Globals.showError(t,getApplicationContext());
    }

    private class OrderEnquiryAdapter extends BaseAdapter {
        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        List<OrderEnquiryData.OrderEnquiry> orderEnquiryList=new ArrayList<>();

        public OrderEnquiryAdapter(List<OrderEnquiryData.OrderEnquiry> orderEnquiryList) {
            this.orderEnquiryList = orderEnquiryList;
        }

        @Override
        public int getCount() {
            return orderEnquiryList.size();
        }

        @Override
        public Object getItem(int position) {
            return orderEnquiryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            convertView = vi.inflate(R.layout.en_hi_row, null);


            TextView txt_date = (TextView) convertView.findViewById(R.id.e_date);
            TextView txt_enquiry = (TextView) convertView.findViewById(R.id.txt_e);


            txt_date.setText(orderEnquiryList.get(position).getOnDate("dd/MM/yyyy","dd MMM yyyy"));
            txt_enquiry.setText(orderEnquiryList.get(position).getEnquiry());

            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
