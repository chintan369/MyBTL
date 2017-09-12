package com.agraeta.user.btl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.model.OrderInvoice;
import com.google.gson.Gson;

public class LRDetailActivity extends AppCompatActivity {

    TextView txt_transportarName, txt_designation, txt_LRNo, txt_LRDate, txt_bundels, txt_remarks, txt_invoiceNoDate, txt_ccAttach;
    ImageView img_download;

    String jsonLR="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrdetail);

        Intent intent=getIntent();
        jsonLR=intent.getStringExtra("jsonLR");
        Log.e("datas", "------->" + jsonLR);

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {
        txt_transportarName=(TextView) findViewById(R.id.txt_transportarName);
        txt_designation=(TextView) findViewById(R.id.txt_designation);
        txt_LRNo=(TextView) findViewById(R.id.txt_LRNo);
        txt_LRDate=(TextView) findViewById(R.id.txt_LRDate);
        txt_bundels=(TextView) findViewById(R.id.txt_bundels);
        txt_remarks=(TextView) findViewById(R.id.txt_remarks);
        txt_invoiceNoDate = (TextView) findViewById(R.id.txt_invoiceNoDate);
        txt_ccAttach = (TextView) findViewById(R.id.txt_ccAttach);

        img_download=(ImageView) findViewById(R.id.img_download);

        try{
            final OrderInvoice.InvoiceLR invoiceLR=new Gson().fromJson(jsonLR, OrderInvoice.InvoiceLR.class);
            txt_transportarName.setText(invoiceLR.getLrDetail().getTransporter_name());
            txt_designation.setText(invoiceLR.getLrDetail().getDestination());
            txt_LRNo.setText(invoiceLR.getLrDetail().getLr_no());
            txt_LRDate.setText(invoiceLR.getLrDetail().getLr_date());
            txt_bundels.setText(invoiceLR.getLrDetail().getNo_bundles());
            txt_ccAttach.setText(invoiceLR.getLrDetail().getCc_attached());
            txt_remarks.setText(invoiceLR.getLrDetail().getRemarks());
            txt_invoiceNoDate.setText(invoiceLR.getLrDetail().getInvoice_no() + "\n\n" + invoiceLR.getLrDetail().getInvoice_date());


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
