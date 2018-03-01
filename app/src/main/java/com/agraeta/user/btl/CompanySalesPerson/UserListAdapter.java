package com.agraeta.user.btl.CompanySalesPerson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SEO on 9/2/2016.
 */
public class UserListAdapter extends BaseAdapter implements Serializable {
    List<Bean_Company_Sales_User> companySalesUserList;
    Context context;
    Activity activity;
    AppPrefs prefs;
    String subsalesID;
    DatabaseHandler db;
    DistCallback distCallback;

    String subUserID = "";

    public UserListAdapter(Context context, List<Bean_Company_Sales_User> companySalesUserList, Activity activity) {
        this.context = context;
        this.companySalesUserList = companySalesUserList;
        this.activity = activity;
    }

    @NonNull
    public static String getStr(String value) {
        return value;
    }

    @Override
    public int getCount() {
        return companySalesUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return companySalesUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_userlist_layout, parent, false);

        final Bean_Company_Sales_User salesComapnyPerson = companySalesUserList.get(position);

        final TextView txtName = (TextView) view.findViewById(R.id.txt_userListName);
        txtName.setTag("" + companySalesUserList.get(position).getUserSalesId());
        LinearLayout l_salesdata = (LinearLayout) view.findViewById(R.id.l_salesdata);
        final ImageView img_option;
        img_option = (ImageView) view.findViewById(R.id.option_icon);

        txtName.setText(salesComapnyPerson.getUserData().getDistributor().getFirm_name());
        db = new DatabaseHandler(context);

        img_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(activity, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_take_skip_order, popupMenu.getMenu());
                popupMenu.setGravity(Gravity.CENTER);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.takeorder:
                                prefs = new AppPrefs(context);
                                prefs.setSalesPersonId(txtName.getTag().toString());
                                prefs.setUserName(companySalesUserList.get(position).getUserData().getDistributor().getFirm_name());
                                db.Clear_ALL_table();
                                Globals.generateNoteOnSD(context, "Sales Start");
                                Intent i = new Intent(context, MainPage_drawer.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(i);
                                Globals.generateNoteOnSD(context, "Sales Activity Start");
                                activity.finish();
                                break;
                            case R.id.skiporder:
                                Intent intent = new Intent(context, RegisteredUserSkipOrderActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("salesComapnyPerson", getUserID(position));
                                intent.putExtra("firmName", companySalesUserList.get(position).getUserData().getDistributor().getFirm_name());
                                intent.putExtra("userRoleID", subUserID);
                                intent.putExtra("userData", companySalesUserList.get(position).getUserData());
                                Log.e("userdata","--->"+new Gson().toJson(companySalesUserList.get(position).getUserData()));
                                context.startActivity(intent);
                                break;
                            case R.id.order:
                                prefs = new AppPrefs(context);
                                prefs.setUserName(companySalesUserList.get(position).getUserData().getDistributor().getFirm_name());
                                prefs.setSalesPersonId(companySalesUserList.get(position).getUserSalesId());
                                Intent ii = new Intent(context, SalesOrderHistory.class);
                                ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ii.putExtra("salesComapnyPerson", getUserID(position));
                                context.startActivity(ii);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        prefs = new AppPrefs(context);

        subsalesID = prefs.getSubSalesId();

        return view;
    }

    public void setDistCallBack(DistCallback distCallback) {
        this.distCallback = distCallback;
    }

    public void setSubRoleID(String subUserID) {
        this.subUserID = subUserID;
    }

    public Bean_Company_Sales_User getData(int position) {
        return companySalesUserList.get(position);
    }

    public String getUserID(int position) {
        return getStr(companySalesUserList.get(position).getUserSalesId());
    }

    public void updateData(List<Bean_Company_Sales_User> companySalesUserList) {
        this.companySalesUserList = companySalesUserList;
        notifyDataSetChanged();
    }

    public interface DistCallback {
        void onOptionClick(int position, View view);

        void showInfoDialog(int position);
    }
}
