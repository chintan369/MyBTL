package com.agraeta.user.btl.Distributor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.R;

import java.util.List;

/**
 * Created by SEO on 9/20/2016.
 */
public class S_R_P_OrderListAdapter extends BaseAdapter {
    List<Bean_S_R_P> srpList;
    Context context;
    Activity activity;
    AppPrefs prefs;
    String subDisID;
    DatabaseHandler db;
    DistCallback distCallback;
    public S_R_P_OrderListAdapter(Context context, List<Bean_S_R_P> srpList, Activity activity){
        this.context=context;
        this.srpList=srpList;
        this.activity=activity;

    }

    @NonNull
    public static String getStr(String value) {
        return value;
    }

    @Override
    public int getCount() {
        return srpList.size();
    }

    @Override
    public Object getItem(int position) {
        return srpList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_srp_list,parent,false);

        final Bean_S_R_P srpPerson=srpList.get(position);

        final TextView txtName=(TextView) view.findViewById(R.id.txt_userListName);
        final ImageView imageArrow=(ImageView)view.findViewById(R.id.option_icon);
        txtName.setTag(""+srpList.get(position).getSales_id());
        LinearLayout l_salesdata=(LinearLayout)view.findViewById(R.id.l_salesdata);
//        final ImageView img_option;
//        img_option=(ImageView) view.findViewById(R.id.option_icon);

        txtName.setText(srpPerson.getFirstName()+" "+srpPerson.getLastName());
        db=new DatabaseHandler(context);

        l_salesdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        prefs=new
                        AppPrefs(context);
                        prefs.setSRP_Id(txtName.getTag().toString());
                        Intent i = new Intent(context, D_OrderHistory.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        db.Clear_ALL_table();
            }
        });
        imageArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs=new
                        AppPrefs(context);
                prefs.setSRP_Id(txtName.getTag().toString());
                prefs.setCurrentPage("OrderList1");
                Intent i = new Intent(context, D_OrderHistory.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
                db.Clear_ALL_table();
            }
        });


        prefs=new AppPrefs(context);

        subDisID=prefs.getSubSalesId();

        return view;
    }

    public void setDistCallBack(DistCallback distCallback){
        this.distCallback=distCallback;
    }

    public Bean_S_R_P getData(int position){
        return srpList.get(position);
    }

    public void updateData(List<Bean_S_R_P> srpList) {
        this.srpList=srpList;
        notifyDataSetChanged();
    }


    public interface DistCallback {
        void onOptionClick(int position, View view);

        void showInfoDialog(int position);
    }

}
