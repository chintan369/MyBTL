package com.agraeta.user.btl.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.AdminAPI;

public class QuotationListActivity extends AppCompatActivity {

    ListView listQuotation;
    AdminAPI adminAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_list);
    }
}
