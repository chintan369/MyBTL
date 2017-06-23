package com.agraeta.user.btl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.agraeta.user.btl.model.OrderInvoice;

import java.util.ArrayList;
import java.util.List;

public class OrderInvoiceActivity extends AppCompatActivity {

    ListView listTracking;
    List<OrderInvoice> orderInvoiceList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_invoice);
    }
}
