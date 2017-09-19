package com.agraeta.user.btl.admin;

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
import android.widget.TextView;

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

public class UsersListActivity extends AppCompatActivity implements Callback<UserList> {

    private final List<User> userList = new ArrayList<>();
    EditText edt_search;
    private ListView list_users;
    private UserListAdapter userListAdapter;
    private String roleID = "";
    private String roleName = "";
    private int page = 1;
    private AdminAPI adminAPI;
    private Call<UserList> userListCallback;
    private View footerLoadingView;
    private Custom_ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        Intent intent = getIntent();
        roleID = intent.getStringExtra("roleID");
        roleName = intent.getStringExtra("roleName");
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

        edt_search = (EditText) findViewById(R.id.edt_search);
        TextView txt_roleName = (TextView) findViewById(R.id.txt_roleName);
        list_users = (ListView) findViewById(R.id.list_users);
        userListAdapter = new UserListAdapter(userList, this);
        list_users.setAdapter(userListAdapter);

        txt_roleName.setText(roleName);
        edt_search.setHint("SEARCH " + roleName);

        footerLoadingView = getLayoutInflater().inflate(R.layout.list_footer_loading, null);

        progressDialog = new Custom_ProgressDialog(this, "Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.server_link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        adminAPI = retrofit.create(AdminAPI.class);

        userListCallback = adminAPI.userList(null, roleID, page);

        Log.e("roleId", roleID);
        Log.e("page", "" + page);

        userListCallback.enqueue(this);


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchKey = edt_search.getText().toString().trim().toLowerCase();

                List<User> searchedUserList = new ArrayList<>();

                if (searchKey.isEmpty()) {
                    searchedUserList = userList;
                } else {
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList.get(i).getFirst_name().toLowerCase().contains(searchKey) || userList.get(i).getLast_name().toLowerCase().contains(searchKey)) {
                            searchedUserList.add(userList.get(i));
                        }
                    }
                }

                if (searchedUserList.size() == 0) {
                    Globals.Toast2(getApplicationContext(), "No User Found!");
                }

                userListAdapter.updateData(searchedUserList);
            }
        });

    }

    @Override
    public void onResponse(Call<UserList> call, Response<UserList> response) {
        progressDialog.dismiss();
        list_users.removeFooterView(footerLoadingView);
        UserList userResponse = response.body();

        if (userResponse.isStatus()) {

            Log.e("status", "" + userResponse.isStatus());

            int maxPage = userResponse.getTotalPage();

            //Log.e("totalPageCount","maxPage="+maxPage);

            List<User> userData = userResponse.getUserList();

            Log.e("Current Max", page + " --> " + maxPage);

            userList.addAll(userData);
            userListAdapter.notifyDataSetChanged();

            if (page < maxPage) {
                page++;
                userListCallback = adminAPI.userList(null, roleID, page);
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
