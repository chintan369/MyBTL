package com.agraeta.user.btl.CompanySalesPerson;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.adapters.RegisteredUserDSRAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.RegisteredUserData;
import com.agraeta.user.btl.model.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisteredUserSkipOrderListActivity extends AppCompatActivity {

    ListView listSkipOrders;
    List<RegisteredUserData.RegisteredUser> userList=new ArrayList<>();
    RegisteredUserDSRAdapter adapter;

    AdminAPI adminAPI;
    AppPrefs prefs;

    Custom_ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user_skip_order_list);

        prefs=new AppPrefs(this);

        dialog=new Custom_ProgressDialog(this,"Please wait...");
        dialog.setCancelable(false);

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {

        adminAPI= ServiceGenerator.getAPIServiceClass();

        listSkipOrders=(ListView) findViewById(R.id.listSkipOrders);
        adapter=new RegisteredUserDSRAdapter(userList,this);
        listSkipOrders.setAdapter(adapter);

        dialog.show();

        Log.e("RoleUser",prefs.getUserRoleId()+" - "+prefs.getUserId());

        final Call<RegisteredUserData> getAllRegisteredUserDSR;
        if(prefs.getUserRoleId().equals(C.ADMIN)){
            getAllRegisteredUserDSR=adminAPI.getAllRegisteredUserDSR(null);
        }
        else {
            getAllRegisteredUserDSR=adminAPI.getAllRegisteredUserDSR(prefs.getUserId());
        }

        getAllRegisteredUserDSR.enqueue(new Callback<RegisteredUserData>() {
            @Override
            public void onResponse(Call<RegisteredUserData> call, Response<RegisteredUserData> response) {
                dialog.dismiss();
                RegisteredUserData userData=response.body();
                if(userData!=null){
                    if(userData.isStatus()){
                        userList.addAll(userData.getData());
                    }
                    else {
                        Globals.Toast2(getApplicationContext(),userData.getMessage());
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RegisteredUserData> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });

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
