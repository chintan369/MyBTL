package com.agraeta.user.btl.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.enquiries.RegisteredDealerData;
import com.squareup.picasso.Picasso;

public class RegisteredDealerDetailActivity extends AppCompatActivity {

    TextView txt_firstName,txt_lastName,txt_mobileNo,txt_emailID,txt_firmName,txt_alternateNo,txt_addrLine1,txt_addrLine2,txt_state,txt_city,txt_pincode,txt_panNo,txt_serviceTaxNo,txt_vatTinNo;

    LinearLayout layout_panDoc,layout_serviceTaxDoc,layout_vatTinDoc;

    ImageView img_panITDoc,img_serviceTaxDoc,img_vatTinDoc;

    RegisteredDealerData.RegisteredDealer dealer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_dealer_detail);

        Intent intent=getIntent();
        dealer=(RegisteredDealerData.RegisteredDealer) intent.getSerializableExtra("dealer");

        fetchID();
    }

    private void fetchID() {
        txt_firstName=(TextView) findViewById(R.id.txt_firstName);
        txt_lastName=(TextView) findViewById(R.id.txt_lastName);
        txt_mobileNo=(TextView) findViewById(R.id.txt_mobileNo);
        txt_emailID=(TextView) findViewById(R.id.txt_emailID);
        txt_firmName=(TextView) findViewById(R.id.txt_firmName);
        txt_alternateNo=(TextView) findViewById(R.id.txt_alternateNo);
        txt_addrLine1=(TextView) findViewById(R.id.txt_addrLine1);
        txt_addrLine2=(TextView) findViewById(R.id.txt_addrLine2);
        txt_state=(TextView) findViewById(R.id.txt_state);
        txt_city=(TextView) findViewById(R.id.txt_city);
        txt_pincode=(TextView) findViewById(R.id.txt_pincode);
        txt_panNo=(TextView) findViewById(R.id.txt_panNo);
        txt_serviceTaxNo=(TextView) findViewById(R.id.txt_serviceTaxNo);
        txt_vatTinNo=(TextView) findViewById(R.id.txt_vatTinNo);

        layout_panDoc=(LinearLayout) findViewById(R.id.layout_panDoc);
        layout_serviceTaxDoc=(LinearLayout) findViewById(R.id.layout_serviceTaxDoc);
        layout_vatTinDoc=(LinearLayout) findViewById(R.id.layout_vatTinDoc);

        img_panITDoc=(ImageView) findViewById(R.id.img_panITDoc);
        img_serviceTaxDoc=(ImageView) findViewById(R.id.img_serviceTaxDoc);
        img_vatTinDoc=(ImageView) findViewById(R.id.img_vatTinDoc);

        txt_firstName.setText(dealer.getSeller().getFirst_name().isEmpty() ? "N/A" : dealer.getSeller().getFirst_name());
        txt_lastName.setText(dealer.getSeller().getLast_name().isEmpty() ? "N/A" : dealer.getSeller().getLast_name());
        txt_mobileNo.setText(dealer.getSeller().getPhone_no().isEmpty() ? "N/A" : dealer.getSeller().getPhone_no());
        txt_emailID.setText(dealer.getSeller().getEmail_id().isEmpty() ? "N/A" : dealer.getSeller().getEmail_id());
        txt_firmName.setText(dealer.getSeller().getFirm_name().isEmpty() ? "N/A" : dealer.getSeller().getFirm_name());
        txt_alternateNo.setText(dealer.getSeller().getAlternate_no().isEmpty() ? "N/A" : dealer.getSeller().getAlternate_no());
        txt_addrLine1.setText(dealer.getSeller().getAddress_1().isEmpty() ? "N/A" : dealer.getSeller().getAddress_1());
        txt_addrLine2.setText(dealer.getSeller().getAddress_2().isEmpty() ? "N/A" : dealer.getSeller().getAddress_2());
        txt_state.setText(dealer.getState().getName().isEmpty() ? "N/A" : dealer.getState().getName());
        txt_city.setText(dealer.getCity().getName().isEmpty() ? "N/A" : dealer.getCity().getName());
        txt_pincode.setText(dealer.getSeller().getPincode().isEmpty() ? "N/A" : dealer.getSeller().getPincode());
        txt_panNo.setText(dealer.getSeller().getPan_no().isEmpty() ? "N/A" : dealer.getSeller().getPan_no());
        txt_serviceTaxNo.setText(dealer.getSeller().getService_tax_no().isEmpty() ? "N/A" : dealer.getSeller().getService_tax_no());
        txt_vatTinNo.setText(dealer.getSeller().getVat_tin_no().isEmpty() ? "N/A" : dealer.getSeller().getVat_tin_no());

        if(dealer.getSeller().getUpload_pan().isEmpty()){
            layout_panDoc.setVisibility(View.GONE);
        }
        else {
            Picasso.with(this)
                    .load(Globals.server_link+dealer.getSeller().getUpload_pan())
                    .placeholder(R.drawable.btl_watermark)
                    .into(img_panITDoc);
        }

        if(dealer.getSeller().getUpload_service_tax().isEmpty()){
            layout_serviceTaxDoc.setVisibility(View.GONE);
        }
        else {
            Picasso.with(this)
                    .load(Globals.server_link+dealer.getSeller().getUpload_service_tax())
                    .placeholder(R.drawable.btl_watermark)
                    .into(img_serviceTaxDoc);
        }

        if(dealer.getSeller().getUpload_vat_tin().isEmpty()){
            layout_vatTinDoc.setVisibility(View.GONE);
        }
        else {
            Picasso.with(this)
                    .load(Globals.server_link+dealer.getSeller().getUpload_vat_tin())
                    .placeholder(R.drawable.btl_watermark)
                    .into(img_vatTinDoc);
        }

    }
}
