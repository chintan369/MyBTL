package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Distributor.LegderListActivity;
import com.agraeta.user.btl.Distributor.OutstandingListActivity;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.admin.AdminTallyActivity;
import com.agraeta.user.btl.admin.SubuserListActivity;
import com.agraeta.user.btl.admin.UserOrderListActivity;
import com.agraeta.user.btl.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class UserListAdapter extends BaseAdapter {

    List<User> userList = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    boolean isSubuser = false;
    AppPrefs prefs;

    public UserListAdapter(List<User> userList, Activity activity) {
        this.userList = userList;
        this.activity = activity;
        this.prefs = new AppPrefs(activity);
        inflater = (LayoutInflater) activity.getLayoutInflater();

        builder = new AlertDialog.Builder(activity);
    }

    public UserListAdapter(List<User> userList, Activity activity, boolean isSubuser) {
        this.isSubuser = isSubuser;
        this.userList = userList;
        this.activity = activity;

        inflater = (LayoutInflater) activity.getLayoutInflater();

        builder = new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_userlist_admin, parent, false);

        TextView txt_userName = (TextView) view.findViewById(R.id.txt_userName);
        ImageView img_info = (ImageView) view.findViewById(R.id.img_info);
        ImageView img_moreOptions = (ImageView) view.findViewById(R.id.img_moreOptions);

        txt_userName.setText(userList.get(position).getFirst_name() + " " + userList.get(position).getLast_name());

        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserInfo(position);
            }
        });

        img_moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreOptions(position, v);
            }
        });

        return view;
    }

    private void showMoreOptions(final int position, View view) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_admin_user_option, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.CENTER);

        final String role = userList.get(position).getRole_id();


        switch (role) {
            case C.CUSTOMER:
                popupMenu.getMenu().findItem(R.id.menu_takeOrder).setVisible(false);
                break;
            case C.CARPENTER:
                popupMenu.getMenu().findItem(R.id.menu_takeOrder).setVisible(false);
                break;
            case C.COMP_SALES_PERSON:
                //popupMenu.getMenu().findItem(R.id.menu_userOrderHistory).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_takeOrder).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_userMyRetailers).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_directDealer).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_userCompDistributor).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_professionals).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_distributorProfessional).setVisible(true);
                break;
            case C.DISTRIBUTOR:
                popupMenu.getMenu().findItem(R.id.menu_userMyRetailers).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_userMySalesPersons).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_professionals).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_distributorProfessional).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_professionals).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_tallyReport).setVisible(true);
                break;
            case C.DISTRIBUTOR_SALES_PERSON:
                //popupMenu.getMenu().findItem(R.id.menu_userOrderHistory).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_takeOrder).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_userMyRetailers).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_professionals).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_distributorProfessional).setVisible(true);
                popupMenu.getMenu().findItem(R.id.menu_professionals).setVisible(false);
                break;
            case C.DIRECT_DEALER:
                popupMenu.getMenu().findItem(R.id.menu_tallyReport).setVisible(true);
                break;
            case C.PROFESSIONAL:
                popupMenu.getMenu().findItem(R.id.menu_tallyReport).setVisible(true);
                break;
        }


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_takeOrder:
                        goToTakeOrder(position);
                        break;
                    case R.id.menu_userOrderHistory:
                        goToOrderHistory(position);
                        break;
                    case R.id.menu_userMyRetailers:
                        goToSelectedUserType(position, C.THIRD_TIER_RETAILER);
                        break;
                    case R.id.menu_userMySalesPersons:
                        goToSelectedUserType(position, C.DISTRIBUTOR_SALES_PERSON);
                        break;
                    case R.id.menu_userCompDistributor:
                        goToSelectedUserType(position, C.DISTRIBUTOR);
                        break;
                    case R.id.menu_professionals:
                        goToSelectedUserType(position, C.PROFESSIONAL);
                        break;
                    case R.id.menu_distributorProfessional:
                        goToSelectedUserType(position, C.DISTRIBUTOR_PROFESSIONAL);
                        break;
                    case R.id.menu_directDealer:
                        goToSelectedUserType(position, C.DIRECT_DEALER);
                        break;
                    case R.id.menu_tallyReport:
                        goToTallyReportActivity(position);
                        break;
                }

                return true;
            }
        });

        popupMenu.show();

    }

    private void goToTakeOrder(int position) {
        prefs.setSubSalesId(userList.get(position).getRole_id());
        prefs.setSalesPersonId(userList.get(position).getId());
        prefs.setUserName(userList.get(position).getName());
        Intent intent=new Intent(activity, MainPage_drawer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    private void goToTallyReportActivity(int position) {
        Intent intent =new Intent(activity, AdminTallyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("userID",userList.get(position).getId());
        activity.startActivity(intent);
    }

    private void goToOrderHistory(int position) {
        Intent intent = new Intent(activity, UserOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("specificUser", true);
        intent.putExtra("userID", userList.get(position).getId());
        intent.putExtra("roleID", userList.get(position).getRole_id());
        intent.putExtra("roleName", userList.get(position).getName());
        activity.startActivity(intent);
    }

    private void goToSelectedUserType(int position, String roleID) {
        Intent intent = new Intent(activity, SubuserListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ownerID", userList.get(position).getId());
        intent.putExtra("roleID", userList.get(position).getRole_id());
        intent.putExtra("userType", roleID);
        intent.putExtra("userName", userList.get(position).getName());
        activity.startActivity(intent);
    }

    private void showUserInfo(int position) {

        TextView customTitleView = (TextView) inflater.inflate(R.layout.simple_listitem_usertype, null);

        customTitleView.setText(userList.get(position).getFirst_name() + " " + userList.get(position).getLast_name());
        customTitleView.setTextColor(activity.getResources().getColor(R.color.black_000));

        builder.setCustomTitle(customTitleView);

        View dialogView = inflater.inflate(R.layout.layout_userinfo_dialog, null);

        TextView txt_emailID = (TextView) dialogView.findViewById(R.id.txt_emailID);
        TextView txt_mobileNo = (TextView) dialogView.findViewById(R.id.txt_mobileNo);
        TextView txt_registeredOn = (TextView) dialogView.findViewById(R.id.txt_registeredOn);
        Button btn_ok = (Button) dialogView.findViewById(R.id.btn_ok);

        txt_emailID.setText(userList.get(position).getEmail_id().replace("\r", "").replace("\n", ""));
        txt_mobileNo.setText(userList.get(position).getPhone_no());
        txt_registeredOn.setText(userList.get(position).getRegisteredOn());

        builder.setView(dialogView);

        dialog = builder.create();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
