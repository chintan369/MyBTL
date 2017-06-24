package com.agraeta.user.btl.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.agraeta.user.btl.R;

public class ProductStockReportActivity extends AppCompatActivity {

    ListView listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_stock_report);
    }
}
