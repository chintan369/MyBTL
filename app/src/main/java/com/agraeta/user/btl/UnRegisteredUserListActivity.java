package com.agraeta.user.btl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.agraeta.user.btl.adapters.UnregisteredUserAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.UnregisteredUserData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnRegisteredUserListActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_EDIT = 36;
    AdminAPI adminAPI;
    ListView list_unregisterUser;
    EditText edt_search;
    UnregisteredUserAdapter userAdapter;
    AppPrefs prefs;
    Custom_ProgressDialog dialog;
    List<UnregisteredUserData.UserData> userList=new ArrayList<>();

    boolean fromAdmin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unregistered_user_list);

        Intent intent=getIntent();
        fromAdmin=intent.getBooleanExtra("fromAdmin",false);

        prefs=new AppPrefs(this);
        dialog=new Custom_ProgressDialog(this,"");
        dialog.setCancelable(false);

        setActionBar();

        fetchIDs();
    }

    private void fetchIDs() {
        adminAPI= ServiceGenerator.getAPIServiceClass();
        edt_search=(EditText) findViewById(R.id.edt_search);
        list_unregisterUser=(ListView) findViewById(R.id.list_unregisterUser);

        fromAdmin = prefs.getUserRoleId().equals(C.ADMIN);

        userAdapter=new UnregisteredUserAdapter(userList,this);
        userAdapter.setFromAdmin(fromAdmin);
        list_unregisterUser.setAdapter(userAdapter);

        Log.e("UserID","-->"+prefs.getUserId());

        getAllUnregisteredUserList();

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchKey=edt_search.getText().toString().trim().toLowerCase();

                List<UnregisteredUserData.UserData> searchedUserList=new ArrayList<UnregisteredUserData.UserData>();

                if(searchKey.isEmpty()){
                    searchedUserList=userList;
                }
                else {
                    for(int i=0; i<userList.size(); i++){
                        if(userList.get(i).getUnRegisteredUser().getFirm_name().toLowerCase().contains(searchKey)){
                            searchedUserList.add(userList.get(i));
                        }
                    }
                }

                if (searchedUserList.size() == 0) {
                    Globals.Toast2(getApplicationContext(), "No User Found!");
                }

                userAdapter.updateData(searchedUserList);
            }
        });
    }

    private void getAllUnregisteredUserList() {
        dialog.show();
        Call<UnregisteredUserData> getUnregisteredUserCall=adminAPI.getAllUnregisteredUser(prefs.getUserId());

        if(fromAdmin) getUnregisteredUserCall=adminAPI.getAllUnregisteredUser(null);

        getUnregisteredUserCall.enqueue(new Callback<UnregisteredUserData>() {
            @Override
            public void onResponse(Call<UnregisteredUserData> call, Response<UnregisteredUserData> response) {
                dialog.dismiss();
                UnregisteredUserData data=response.body();
                if(data.isStatus()){
                    userList.addAll(data.getData());
                }
                else {
                    Globals.Toast2(getApplicationContext(),data.getMessage());
                }

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UnregisteredUserData> call, Throwable t) {
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

        if(!fromAdmin)
            img_home.setVisibility(View.VISIBLE);
        else
            img_home.setVisibility(View.GONE);

        img_home.setImageResource(R.drawable.add_icon);

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SkipOrderUnregisterUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,REQUEST_ADD_EDIT);
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_ADD_EDIT && resultCode==RESULT_OK){
            userList.clear();
            userAdapter.notifyDataSetChanged();
            getAllUnregisteredUserList();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
