package com.agraeta.user.btl;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.model.OrderInvoice;
import com.google.gson.Gson;

import org.json.JSONObject;

public class LRDetailActivity extends AppCompatActivity {

    TextView txt_transportarName,txt_designation, txt_LRNo, txt_LRDate,txt_bundels,txt_remarks;
    ImageView img_download, img_info;

    String jsonLR="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrdetail);

        Intent intent=getIntent();
        jsonLR=intent.getStringExtra("jsonLR");

        fetchIDs();
    }

    private void fetchIDs() {
        txt_transportarName=(TextView) findViewById(R.id.txt_transportarName);
        txt_designation=(TextView) findViewById(R.id.txt_designation);
        txt_LRNo=(TextView) findViewById(R.id.txt_LRNo);
        txt_LRDate=(TextView) findViewById(R.id.txt_LRDate);
        txt_bundels=(TextView) findViewById(R.id.txt_bundels);
        txt_remarks=(TextView) findViewById(R.id.txt_remarks);

        img_download=(ImageView) findViewById(R.id.img_download);
        img_info=(ImageView) findViewById(R.id.img_info);

        try{
            final OrderInvoice.InvoiceLR invoiceLR=new Gson().fromJson(jsonLR, OrderInvoice.InvoiceLR.class);

            txt_transportarName.setText(invoiceLR.getLrDetail().getTransporter_name());
            txt_designation.setText(invoiceLR.getLrDetail().getDestination());
            txt_LRNo.setText(invoiceLR.getLrDetail().getLr_no());
            txt_LRDate.setText(invoiceLR.getLrDetail().getLr_date());
            txt_bundels.setText(invoiceLR.getLrDetail().getNo_bundles());
            txt_remarks.setText(invoiceLR.getLrDetail().getRemarks());

            img_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(Globals.server_link+invoiceLR.getLrDetail().getCourier_receipt_file()),"application/*");
                    startActivity(intent);
                }
            });

        }catch (Exception e){
            Log.e("LR JSON Exception",e.getMessage());
        }


    }
}
