package com.agraeta.user.btl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.agraeta.user.btl.adapters.ComboOfferListAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.combooffer.ComboOfferData;
import com.agraeta.user.btl.model.combooffer.ComboOfferItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComboOfferListActivity extends AppCompatActivity implements Callback<ComboOfferData> {

    ListView list_comboOffers;
    ComboOfferListAdapter offerListAdapter;
    List<ComboOfferItem> offerItemList=new ArrayList<>();
    AdminAPI adminAPI;
    Custom_ProgressDialog dialog;

    String userID="";
    String roleID="";
    AppPrefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_offer_list);

        prefs=new AppPrefs(this);
        adminAPI=ServiceGenerator.getAPIServiceClass();
        dialog=new Custom_ProgressDialog(this,"Please Wait...");
        dialog.setCancelable(false);

        userID=prefs.getUserId();
        roleID=prefs.getUserRoleId();

        if(roleID.equals(C.ADMIN) || roleID.equals(C.COMP_SALES_PERSON) || roleID.equals(C.DISTRIBUTOR_SALES_PERSON)){
            roleID=prefs.getSubSalesId();
            userID=prefs.getSalesPersonId();
        }

        fetchIDs();
        setActionBar();
    }

    private void fetchIDs() {
        list_comboOffers=(ListView) findViewById(R.id.list_comboOffers);
        offerListAdapter=new ComboOfferListAdapter(offerItemList,this);
        list_comboOffers.setAdapter(offerListAdapter);

        list_comboOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ComboOfferActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("combo_id",offerItemList.get(position).getOfferID());
                startActivity(intent);
            }
        });

        dialog.show();

        Log.e("UR",userID+"-->"+roleID);

        Call<ComboOfferData> offerDataCall=adminAPI.comboOfferDataCall(userID,roleID);
        offerDataCall.enqueue(this);

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
    public void onResponse(Call<ComboOfferData> call, Response<ComboOfferData> response) {
        dialog.dismiss();
        offerItemList.clear();
        ComboOfferData offerData=response.body();
        if(offerData.isStatus()){
            offerItemList.addAll(offerData.getOfferItemList());
        }
        else {
            Globals.Toast2(this,offerData.getMessage());
        }

        Log.e("Size","-->"+offerItemList.size());

        offerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<ComboOfferData> call, Throwable t) {
        dialog.dismiss();
        Globals.showError(t,this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
