package com.agraeta.user.btl.admin;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Distributor.LegderListActivity;
import com.agraeta.user.btl.Distributor.OutstandingListActivity;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.TallyBill;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminTallyActivity extends AppCompatActivity implements Callback<TallyBill> {


    LinearLayout layout_myorderlist,layout_outstandingbills,layout_ledgerbills;
    String userID="";
    Custom_ProgressDialog dialog;

    TextView outstanding_txt,outstanding_rs,due_txt,due_rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_my_orders); //Reused layout of Distributor Orders

        Intent intent=getIntent();
        userID=intent.getStringExtra("userID");

        Log.e("userID",userID);

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {

        dialog=new Custom_ProgressDialog(this,"");
        dialog.setCancelable(false);

        layout_myorderlist=(LinearLayout) findViewById(R.id.l_myorderlist);
        layout_outstandingbills=(LinearLayout) findViewById(R.id.l_outstandingbills);
        layout_ledgerbills=(LinearLayout) findViewById(R.id.l_ledgerbills);

        outstanding_txt=(TextView) findViewById(R.id.outstanding_txt);
        outstanding_rs=(TextView) findViewById(R.id.outstanding_rs);
        due_txt=(TextView) findViewById(R.id.due_txt);
        due_rs=(TextView) findViewById(R.id.due_rs);

        layout_myorderlist.setVisibility(View.GONE);

        AdminAPI adminAPI= ServiceGenerator.getAPIServiceClass();

        layout_ledgerbills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), LegderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        layout_outstandingbills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), OutstandingListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        dialog.show();
        Call<TallyBill> tallyDataCall=adminAPI.tallyBillCall(userID);
        tallyDataCall.enqueue(this);

    }

    @Override
    public void onResponse(Call<TallyBill> call, Response<TallyBill> response) {

        dialog.dismiss();
        TallyBill tallyBill=response.body();

        if(tallyBill.isStatus()){
            outstanding_txt.setText(tallyBill.getBillData().getOutstandingLabel());
            outstanding_rs.setText(tallyBill.getBillData().getOutstandingAmount());
            due_txt.setText(tallyBill.getBillData().getDueAmountLabel());
            due_rs.setText(tallyBill.getBillData().getDueAmount());
        }
        else{
            Globals.Toast2(this,tallyBill.getMessage());
        }
    }

    @Override
    public void onFailure(Call<TallyBill> call, Throwable t) {
        dialog.dismiss();
        Globals.showError(t,this);
    }

    @Override
    public void onBackPressed() {
        finish();
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
}
