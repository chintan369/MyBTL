package com.agraeta.user.btl.admin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.OrderTrackingData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderTrackingHistoryActivity extends AppCompatActivity implements Callback<OrderTrackingData> {

    LinearLayout lcomment, l_attach;
    ScrollView layout_commentScroll;
    TextView txt_comt;
    ImageView img_attach, btn_cancel;
    ListView lst_tracking;

    String comment = "";
    String attachmentPath = "";
    String orderID = "";

    Custom_ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_layout);

        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");
        comment = intent.getStringExtra("comment").trim();
        attachmentPath = intent.getStringExtra("attachmentPath").trim();

        Log.e("OCA",orderID+"->"+comment+"->"+attachmentPath);

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {
        progressDialog = new Custom_ProgressDialog(this, "Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        lcomment = (LinearLayout) findViewById(R.id.lcomment);
        l_attach = (LinearLayout) findViewById(R.id.l_attach);
        layout_commentScroll = (ScrollView) findViewById(R.id.layout_commentScroll);
        txt_comt = (TextView) findViewById(R.id.txt_comt);
        img_attach = (ImageView) findViewById(R.id.img_attach);
        btn_cancel = (ImageView) findViewById(R.id.btn_cancel);
        lst_tracking = (ListView) findViewById(R.id.lst_tracking);

        btn_cancel.setVisibility(View.GONE);

        if(comment.isEmpty()) layout_commentScroll.setVisibility(View.GONE);

        if (attachmentPath == null || attachmentPath.isEmpty()) l_attach.setVisibility(View.GONE);
        else {
            Picasso.with(this)
                    .load(Globals.IMAGE_LINK + attachmentPath)
                    .placeholder(R.drawable.btl_watermark)
                    .into(img_attach);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.server_link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AdminAPI adminAPI = retrofit.create(AdminAPI.class);

        Call<OrderTrackingData> orderTrackingDataCall = adminAPI.trackingDataCall(orderID);
        orderTrackingDataCall.enqueue(this);

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
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResponse(Call<OrderTrackingData> call, Response<OrderTrackingData> response) {
        progressDialog.dismiss();

        OrderTrackingData trackingData = response.body();

        if (trackingData.isStatus()) {
            txt_comt.setText(comment);
            lst_tracking.setAdapter(new TrackingHistoryAdapter(trackingData.getOrderTrackingData()));
        } else {
            Globals.Toast2(getApplicationContext(), trackingData.getMessage());
        }

    }

    @Override
    public void onFailure(Call<OrderTrackingData> call, Throwable t) {
        progressDialog.dismiss();
        Globals.showError(t, getApplicationContext());
    }

    private class TrackingHistoryAdapter extends BaseAdapter {
        LayoutInflater vi = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        ArrayList<OrderTrackingData.OrderTracking> orderTrackingList = new ArrayList<>();

        public TrackingHistoryAdapter(ArrayList<OrderTrackingData.OrderTracking> orderTrackingList) {
            this.orderTrackingList = orderTrackingList;
        }

        @Override
        public int getCount() {
            return orderTrackingList.size();
        }

        @Override
        public Object getItem(int position) {
            return orderTrackingList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = vi.inflate(R.layout.track_row, null);

            TextView txt_date = (TextView) view.findViewById(R.id.txt_date);
            TextView txt_status = (TextView) view.findViewById(R.id.txt_status);
            TextView txt_com = (TextView) view.findViewById(R.id.txt_com);

            txt_date.setText(orderTrackingList.get(position).getOrderHistory().getOnDate("dd/MM/yyyy", "dd MMM yy"));
            txt_status.setText(orderTrackingList.get(position).getOrderStatus().getStatus());
            txt_com.setText(orderTrackingList.get(position).getOrderHistory().getStatusComment());

            return view;
        }
    }
}
