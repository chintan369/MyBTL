package com.agraeta.user.btl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.agraeta.user.btl.adapters.OrderInvoiceAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.OrderInvoice;
import com.agraeta.user.btl.model.OrderInvoiceResponse;
import com.agraeta.user.btl.model.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInvoiceActivity extends AppCompatActivity {

    ListView listTracking;
    List<OrderInvoice> orderInvoiceList=new ArrayList<>();
    OrderInvoiceAdapter invoiceAdapter;
    String orderID="";

    AdminAPI adminAPI;
    Custom_ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_invoice);
        Intent intent=getIntent();
        orderID=intent.getStringExtra("orderID");

        adminAPI= ServiceGenerator.getAPIServiceClass();
        dialog=new Custom_ProgressDialog(this,"");
        dialog.setCancelable(false);

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {
        listTracking=(ListView) findViewById(R.id.listTracking);
        invoiceAdapter=new OrderInvoiceAdapter(orderInvoiceList,this);
        listTracking.setAdapter(invoiceAdapter);

        dialog.show();
        Call<OrderInvoiceResponse> orderInvoiceResponseCall=adminAPI.orderInvoiceCall(orderID);
        orderInvoiceResponseCall.enqueue(new Callback<OrderInvoiceResponse>() {
            @Override
            public void onResponse(Call<OrderInvoiceResponse> call, Response<OrderInvoiceResponse> response) {
                dialog.dismiss();
                OrderInvoiceResponse invoiceResponse=response.body();
                if(invoiceResponse !=null){
                    if(invoiceResponse.isStatus()){
                        orderInvoiceList.addAll(invoiceResponse.getOrder_invoice());
                    }
                    else {
                        Globals.Toast2(getApplicationContext(),invoiceResponse.getMessage());
                    }
                    invoiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<OrderInvoiceResponse> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });
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

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
}
