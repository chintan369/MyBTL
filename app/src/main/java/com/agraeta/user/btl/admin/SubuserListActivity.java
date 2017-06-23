package com.agraeta.user.btl.admin;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.adapters.UserListAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.User;
import com.agraeta.user.btl.model.UserList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.agraeta.user.btl.Globals.showError;

public class SubuserListActivity extends AppCompatActivity implements Callback<UserList> {

    private ListView list_users;
    private final List<User> userList = new ArrayList<>();
    private UserListAdapter userListAdapter;

    private String roleID = "";
    private String userType="";
    private String roleName = "";
    private String ownerID="";
    private int page = 1;

    private AdminAPI adminAPI;
    private Call<UserList> userListCallback;

    private View footerLoadingView;

    private Custom_ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        Intent intent=getIntent();
        ownerID=intent.getStringExtra("ownerID");
        roleID=intent.getStringExtra("roleID");
        userType=intent.getStringExtra("userType");
        roleName=intent.getStringExtra("userName");

        Log.e("Data",ownerID+","+roleID+","+userType);

        setActionBar();
        fetchID();
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
        img_notification.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_home.setVisibility(View.GONE);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void fetchID() {

        TextView txt_roleName = (TextView) findViewById(R.id.txt_roleName);
        list_users = (ListView) findViewById(R.id.list_users);
        userListAdapter = new UserListAdapter(userList, this,true);
        list_users.setAdapter(userListAdapter);

        txt_roleName.setText(roleName);

        footerLoadingView = getLayoutInflater().inflate(R.layout.list_footer_loading, null);

        progressDialog = new Custom_ProgressDialog(this, "Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.server_link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        adminAPI = retrofit.create(AdminAPI.class);

        if(roleID.equals(C.COMP_SALES_PERSON) || roleID.equals(C.DISTRIBUTOR_SALES_PERSON))
            userListCallback=adminAPI.userListUnderSales(ownerID,roleID,userType);
        else userListCallback = adminAPI.userList(ownerID,userType, page);

        userListCallback.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserList> call, Response<UserList> response) {
        progressDialog.dismiss();
        list_users.removeFooterView(footerLoadingView);
        UserList userResponse = response.body()==null ? new UserList() : response.body();

        if (userResponse.isStatus()) {
            page++;
            int maxPage = userResponse.getTotalPage();
            List<User> userData = userResponse.getUserList();

            Log.e("Current Max", page + " --> " + maxPage);

            userList.addAll(userData);
            userListAdapter.notifyDataSetChanged();

            if (page <= maxPage) {
                if(roleID.equals(C.COMP_SALES_PERSON) || roleID.equals(C.DISTRIBUTOR_SALES_PERSON))
                    userListCallback=adminAPI.userListUnderSales(ownerID,roleID,userType);
                else userListCallback = adminAPI.userList(ownerID,roleID, page);

                userListCallback.enqueue(this);
                list_users.addFooterView(footerLoadingView);
            }
        } else {
            Globals.Toast2(getApplicationContext(), userResponse.getMessage());
        }
    }

    @Override
    public void onFailure(Call<UserList> call, Throwable t) {
        progressDialog.dismiss();
        list_users.removeFooterView(footerLoadingView);

        showError(t, getApplicationContext());

        Log.e("ERROR", t.getMessage());
    }
}
