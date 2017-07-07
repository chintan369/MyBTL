package com.agraeta.user.btl.CompanySalesPerson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.admin.SingleImageViewerActivity;
import com.agraeta.user.btl.model.RegisteredUserData;
import com.squareup.picasso.Picasso;

public class RegisteredUserDSRDetailActivity extends AppCompatActivity {

    TextView txt_firmName, txt_addrLine1, txt_addrLine2, txt_addrLine3, txt_country, txt_state, txt_city, txt_area, txt_pincode, txt_contactPerson, txt_emailID, txt_mobileNo, txt_dealingBrand, txt_customerType, txt_comment, txt_jointVisitWith, txt_isPastOrder, txt_pastOrderID, txt_partyReport;
    LinearLayout layout_visitngFront,layout_visitngBack,layout_attachment;
    ImageView img_visitingFront,img_visitingBack;
    TextView txt_skip_reason;

    LinearLayout layout_attachment1,layout_attachment2,layout_attachment3,layout_attachment4;
    ImageView img_attachment1,img_attachment2,img_attachment3,img_attachment4;

    RegisteredUserData.RegisteredUser userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user_dsrdetail);

        Intent intent=getIntent();
        userData=(RegisteredUserData.RegisteredUser) intent.getSerializableExtra("userData");

        setActionBar();
        fetchIDs();

    }

    private void fetchIDs() {
        txt_firmName=(TextView) findViewById(R.id.txt_firmName);
        txt_addrLine1=(TextView) findViewById(R.id.txt_addrLine1);
        txt_addrLine2=(TextView) findViewById(R.id.txt_addrLine2);
        txt_addrLine3=(TextView) findViewById(R.id.txt_addrLine3);
        txt_country=(TextView) findViewById(R.id.txt_country);
        txt_state=(TextView) findViewById(R.id.txt_state);
        txt_city=(TextView) findViewById(R.id.txt_city);
        txt_area=(TextView) findViewById(R.id.txt_area);
        txt_pincode=(TextView) findViewById(R.id.txt_pincode);
        txt_contactPerson=(TextView) findViewById(R.id.txt_contactPerson);
        txt_emailID=(TextView) findViewById(R.id.txt_emailID);
        txt_mobileNo=(TextView) findViewById(R.id.txt_mobileNo);
        txt_dealingBrand=(TextView) findViewById(R.id.txt_dealingBrand);
        txt_customerType=(TextView) findViewById(R.id.txt_customerType);
        txt_comment=(TextView) findViewById(R.id.txt_comment);
        txt_jointVisitWith=(TextView) findViewById(R.id.txt_jointVisitWith);
        txt_isPastOrder=(TextView) findViewById(R.id.txt_isPastOrder);
        txt_pastOrderID=(TextView) findViewById(R.id.txt_pastOrderID);
        txt_partyReport = (TextView) findViewById(R.id.txt_partyReport);
        txt_skip_reason=(TextView)findViewById(R.id.txt_skip_reason);

        layout_visitngFront=(LinearLayout) findViewById(R.id.layout_visitngFront);
        layout_visitngBack=(LinearLayout) findViewById(R.id.layout_visitngBack);
        layout_attachment=(LinearLayout) findViewById(R.id.layout_attachment);
        layout_attachment1=(LinearLayout) findViewById(R.id.layout_attachment1);
        layout_attachment2=(LinearLayout) findViewById(R.id.layout_attachment2);
        layout_attachment3=(LinearLayout) findViewById(R.id.layout_attachment3);
        layout_attachment4=(LinearLayout) findViewById(R.id.layout_attachment4);

        img_visitingFront=(ImageView) findViewById(R.id.img_visitingFront);
        img_visitingBack=(ImageView) findViewById(R.id.img_visitingBack);
        img_attachment1=(ImageView) findViewById(R.id.img_attachment1);
        img_attachment2=(ImageView) findViewById(R.id.img_attachment2);
        img_attachment3=(ImageView) findViewById(R.id.img_attachment3);
        img_attachment4=(ImageView) findViewById(R.id.img_attachment4);

        final RegisteredUserData.OrderReason orderReason = userData.getOrderReason();

        txt_firmName.setText(orderReason.getFirm_name().isEmpty() ? "N/A" : orderReason.getFirm_name());
        txt_addrLine1.setText(orderReason.getAddress_1().isEmpty() ? "N/A" : orderReason.getAddress_1());
        txt_addrLine2.setText(orderReason.getAddress_2().isEmpty() ? "N/A" : orderReason.getAddress_2());
        txt_addrLine3.setText(orderReason.getAddress_3().isEmpty() ? "N/A" : orderReason.getAddress_3());
        txt_country.setText(userData.getCountry().getName().isEmpty() ? "N/A" : userData.getCountry().getName());
        txt_state.setText(userData.getState().getName().isEmpty() ? "N/A" : userData.getState().getName());
        txt_city.setText(userData.getCity().getName().isEmpty() ? "N/A" : userData.getCity().getName());
        txt_area.setText(userData.getArea().getName().isEmpty() ? "N/A" : userData.getArea().getName());
        txt_pincode.setText(orderReason.getPincode().isEmpty() ? "N/A" : orderReason.getPincode());
        txt_contactPerson.setText(orderReason.getContact_person().isEmpty() ? "N/A" : orderReason.getContact_person());
        txt_emailID.setText(orderReason.getEmail().isEmpty() ? "N/A" : orderReason.getEmail());
        txt_mobileNo.setText(orderReason.getMobile_no().isEmpty() ? "N/A" : orderReason.getMobile_no());
        txt_partyReport.setText(orderReason.getParty_report().isEmpty() ? "N/A" : orderReason.getParty_report());
        txt_dealingBrand.setText(orderReason.getDealing_in_brand().isEmpty() ? "N/A" : orderReason.getDealing_in_brand());
        txt_customerType.setText(userData.getRole().getName().isEmpty() ? "N/A" : userData.getRole().getName());
        txt_comment.setText(orderReason.getComment().isEmpty() ? "N/A" : orderReason.getComment());
        txt_jointVisitWith.setText(orderReason.getJoint_visit_with().isEmpty() ? "N/A" : orderReason.getJoint_visit_with());
        txt_skip_reason.setText(orderReason.getReason_title().isEmpty() ? "N/A" : orderReason.getReason_title());

        if(orderReason.getIs_past_order().equals("1")){
            txt_isPastOrder.setText("Yes");
            txt_pastOrderID.setText(orderReason.getOrder_id());
        }

        if(!orderReason.getVisiting_card1().isEmpty()){
            Picasso.with(this).load(Globals.server_link+orderReason.getVisiting_card1())
                    .into(img_visitingFront);
        }
        else layout_visitngFront.setVisibility(View.GONE);

        if(!orderReason.getVisiting_card2().isEmpty()){
            Picasso.with(this).load(Globals.server_link+orderReason.getVisiting_card2())
                    .into(img_visitingBack);
        }
        else layout_visitngBack.setVisibility(View.GONE);

        if(!orderReason.getAttachment1().isEmpty()){

            layout_attachment1.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(Globals.server_link+orderReason.getAttachment1())
                    .placeholder(R.drawable.btl_watermark)
                    .into(img_attachment1);

            if(!orderReason.getAttachment2().isEmpty()){

                layout_attachment2.setVisibility(View.VISIBLE);
                Picasso.with(this)
                        .load(Globals.server_link+orderReason.getAttachment2())
                        .placeholder(R.drawable.btl_watermark)
                        .into(img_attachment2);

                if(!orderReason.getAttachment3().isEmpty()){

                    layout_attachment3.setVisibility(View.VISIBLE);
                    Picasso.with(this)
                            .load(Globals.server_link+orderReason.getAttachment3())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_attachment3);

                    if(!orderReason.getAttachment4().isEmpty()){
                        layout_attachment4.setVisibility(View.VISIBLE);
                        Picasso.with(this)
                                .load(Globals.server_link+orderReason.getAttachment4())
                                .placeholder(R.drawable.btl_watermark)
                                .into(img_attachment4);
                    }
                }
            }
        }

        img_attachment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullImageView(Globals.server_link + orderReason.getAttachment1());
            }
        });

        img_attachment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullImageView(Globals.server_link + orderReason.getAttachment2());
            }
        });

        img_attachment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullImageView(Globals.server_link + orderReason.getAttachment3());
            }
        });

        img_attachment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullImageView(Globals.server_link + orderReason.getAttachment4());
            }
        });

        img_visitingFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullImageView(Globals.server_link + orderReason.getVisiting_card1());
            }
        });

        img_visitingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullImageView(Globals.server_link + orderReason.getVisiting_card2());
            }
        });
    }

    public void showFullImageView(String path) {
        Intent intent = new Intent(this, SingleImageViewerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("path", path);
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
