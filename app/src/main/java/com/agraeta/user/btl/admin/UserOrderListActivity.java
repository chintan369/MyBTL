package com.agraeta.user.btl.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.adapters.UserOrderListAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.OrderData;
import com.agraeta.user.btl.model.OrderListItem;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserOrderListActivity extends AppCompatActivity implements Callback<OrderData> {

    String userID="";
    ListView list_orders;
    List<OrderListItem> orderListItems=new ArrayList<>();
    UserOrderListAdapter orderListAdapter;
    Call<OrderData> orderDataCall;
    EditText edt_search;
    private String roleID = "";
    private String roleName = "";
    private boolean isSpecificUser = false;
    private int page = 1;
    private AdminAPI adminAPI;
    private View footerLoadingView;
    private Custom_ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_list);

        Intent intent=getIntent();
        roleID=intent.getStringExtra("roleID");
        roleName=intent.getStringExtra("roleName");
        isSpecificUser=intent.getBooleanExtra("specificUser",false);
        userID=intent.getStringExtra("userID");

        Log.e("UserDet",roleID+", "+userID);

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
        list_orders=(ListView) findViewById(R.id.list_orders);
        orderListAdapter=new UserOrderListAdapter(orderListItems,this);
        list_orders.setAdapter(orderListAdapter);

        txt_roleName.setText(roleName);
        edt_search.setHint("SEARCH " + roleName);

        list_orders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String orderID=orderListAdapter.getOrderID(position);

                Intent intent=new Intent(getApplicationContext(),UserOrderDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("orderID",orderID);
                startActivity(intent);
            }
        });

        footerLoadingView=getLayoutInflater().inflate(R.layout.list_footer_loading,null);

        progressDialog=new Custom_ProgressDialog(this,"Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.server_link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        adminAPI= retrofit.create(AdminAPI.class);

        if(isSpecificUser) orderDataCall=adminAPI.orderData(userID,roleID,page);
        else orderDataCall=adminAPI.orderData(null,roleID,page);

        orderDataCall.enqueue(this);

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

                List<OrderListItem> searchedUserList = new ArrayList<>();

                if (searchKey.isEmpty()) {
                    searchedUserList = orderListItems;
                } else {
                    for (int i = 0; i < orderListItems.size(); i++) {
                        if (orderListItems.get(i).order.getName().toLowerCase().contains(searchKey)) {
                            searchedUserList.add(orderListItems.get(i));
                        }
                    }
                }

                if (searchedUserList.size() == 0) {
                    Globals.Toast2(getApplicationContext(), "No User Found!");
                }

                orderListAdapter.updateData(searchedUserList);
            }
        });
    }

    @Override
    public void onResponse(Call<OrderData> call, Response<OrderData> response) {
        progressDialog.dismiss();
        list_orders.removeFooterView(footerLoadingView);

        OrderData orderData=response.body();

        if(orderData.isStatus()){

            int maxPage = orderData.getTotalPage();

            orderListItems.addAll(orderData.getOrderList());
            orderListAdapter.notifyDataSetChanged();

            if(page<maxPage)
            {
                page++;
                if(isSpecificUser) orderDataCall=adminAPI.orderData(userID,roleID,page);
                else orderDataCall=adminAPI.orderData(null,roleID,page);

                orderDataCall.enqueue(this);
                list_orders.addFooterView(footerLoadingView);
            }
        }
        else{
            Globals.Toast2(getApplicationContext(),orderData.getMessage());
        }
    }

    @Override
    public void onFailure(Call<OrderData> call, Throwable t) {
        progressDialog.dismiss();
        list_orders.removeFooterView(footerLoadingView);
        if(t instanceof UnknownHostException || t instanceof ConnectException)
            Globals.noInternet(getApplicationContext());
        else Globals.defaultError(getApplicationContext());

        Log.e("Exception",t.getMessage());
    }
}
