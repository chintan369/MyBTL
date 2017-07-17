package com.agraeta.user.btl.admin;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;

import java.util.ArrayList;
import java.util.Arrays;

public class UserListSelectionActivity extends AppCompatActivity {

    ListView list_users;
    ArrayAdapter<String> usersAdapter;


    String[] usersRoleNameArray=new String[]{"CUSTOMER","DISTRIBUTOR","DIRECT DEALER","PROFESSIONAL","COMP. SALES PERSON","DISTRIBUTOR SALES PERSON","DISTRIBUTOR RETAILER","DISTRIBUTOR PROFESSIONAL","CARPENTER"};
    String[] userRoleIDArray=new String[]{"2","3","4","5","6","7","8","9","10"};

    ArrayList<String> usersRoleName=new ArrayList<>();
    ArrayList<String> usersRoleID=new ArrayList<>();

    boolean forUserList=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_selection);

        Intent intent=getIntent();
        forUserList=intent.getBooleanExtra("forUser",true);

        addUserRoles();
        setActionBar();
        fetchIDs();
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

    private void fetchIDs() {
        list_users=(ListView) findViewById(R.id.list_users);
        usersAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_listitem_usertype,usersRoleName);
        list_users.setAdapter(usersAdapter);

        list_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(forUserList){
                    Intent intent=new Intent(getApplicationContext(),UsersListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("roleID",usersRoleID.get(position));
                    Log.e("roleID",""+usersRoleID.get(position));
                    intent.putExtra("roleName",usersRoleName.get(position));
                    Log.e("roleName",""+usersRoleName.get(position));
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(getApplicationContext(),UserOrderListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("roleID",usersRoleID.get(position));
                    Log.e("roleID",""+usersRoleID.get(position));
                    intent.putExtra("roleName",usersRoleName.get(position));
                    Log.e("roleName",""+usersRoleName.get(position));
                    startActivity(intent);
                }
            }
        });
    }

    private void addUserRoles() {
        usersRoleName.addAll(Arrays.asList(usersRoleNameArray));
        usersRoleID.addAll(Arrays.asList(userRoleIDArray));
    }
}
