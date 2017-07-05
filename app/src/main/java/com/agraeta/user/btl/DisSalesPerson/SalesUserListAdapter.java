package com.agraeta.user.btl.DisSalesPerson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
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
import com.agraeta.user.btl.CompanySalesPerson.RegisteredUserSkipOrderActivity;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.MainPage_drawer;
import com.agraeta.user.btl.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chaitalee on 9/14/2016.
 */
public class SalesUserListAdapter extends BaseAdapter implements Serializable{
    List<Bean_Distributor_Sales> distributorSalesUserList;
    Context context;
    Activity activity;
    AppPrefs prefs;
    String subdissalesID;
    DatabaseHandler db;
    DistCallback distCallback;

    public SalesUserListAdapter(Context context, List<Bean_Distributor_Sales> distributorSalesUserList, Activity activity){
        this.context=context;
        this.distributorSalesUserList=distributorSalesUserList;
        this.activity=activity;
        prefs = new AppPrefs(context);

    }

    @NonNull
    public static String getStr(String value) {
        return value;
    }

    @Override
    public int getCount() {
        return distributorSalesUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return distributorSalesUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_userlist_layout,parent,false);

        final Bean_Distributor_Sales salesDistributorPerson=distributorSalesUserList.get(position);

        final TextView txtName=(TextView) view.findViewById(R.id.txt_userListName);
        txtName.setTag(""+distributorSalesUserList.get(position).getUserDisSalesId());
        //Log.e("IDIDD",""+distributorSalesUserList.get(position).getUserDisSalesId().toString());
        LinearLayout l_salesdata=(LinearLayout)view.findViewById(R.id.l_salesdata);
        final ImageView img_option;
        img_option=(ImageView) view.findViewById(R.id.option_icon);

        txtName.setText(salesDistributorPerson.getUserData().getDistributor().getFirm_name());
        db=new DatabaseHandler(context);

        img_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(activity,v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_take_skip_order,popupMenu.getMenu());
                popupMenu.setGravity(Gravity.CENTER);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.takeorder:
                                prefs.setSalesPersonId(txtName.getTag().toString());
                                prefs.setUserName(distributorSalesUserList.get(position).getUserData().getDistributor().getFirm_name());
                                //Log.e("IDIDD",""+txtName.getTag().toString());
                                Intent i = new Intent(context, MainPage_drawer.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(i);
                                db.Clear_ALL_table();
                                break;
                            case R.id.skiporder:
                                Intent intent = new Intent(context, RegisteredUserSkipOrderActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("salesComapnyPerson", distributorSalesUserList.get(position).getUserDisSalesId());
                                intent.putExtra("firmName", distributorSalesUserList.get(position).getUserData().getDistributor().getFirm_name());
                                intent.putExtra("userRoleID", prefs.getSubSalesId());
                                intent.putExtra("userData", distributorSalesUserList.get(position).getUserData());
                                context.startActivity(intent);
                                break;
                            case R.id.order:
                                prefs=new AppPrefs(context);
                                prefs.setUserName(distributorSalesUserList.get(position).getUserData().getDistributor().getFirm_name());
                                prefs.setSalesPersonId(txtName.getTag().toString());
                                Intent ii=new Intent(context,Dist_SalesOrderHistory.class);
                                ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ii.putExtra("salesComapnyPerson",getUserID1(position));
                                context.startActivity(ii);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        prefs=new AppPrefs(context);

        subdissalesID=prefs.getSubSalesId();

        return view;
    }

    public void setDistCallBack(DistCallback distCallback){
        this.distCallback=distCallback;
    }

    public Bean_Distributor_Sales getData(int position){
        return distributorSalesUserList.get(position);
    }

    public String getUserID1(int position){
        return getStr(distributorSalesUserList.get(position).getUserDisSalesId());
    }

    public void updateData(List<Bean_Distributor_Sales> distributorSalesUserList) {
        this.distributorSalesUserList=distributorSalesUserList;
        notifyDataSetChanged();
    }

    public interface DistCallback {
        void onOptionClick(int position, View view);

        void showInfoDialog(int position);
    }
}
