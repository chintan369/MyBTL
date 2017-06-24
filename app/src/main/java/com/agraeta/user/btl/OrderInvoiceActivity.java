package com.agraeta.user.btl;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.agraeta.user.btl.adapters.OrderInvoiceAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.OrderInvoice;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.utils.OrderInvoiceResponse;

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
}
