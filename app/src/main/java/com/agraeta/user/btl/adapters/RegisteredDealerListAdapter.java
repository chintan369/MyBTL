package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.admin.RegisteredDealerDetailActivity;
import com.agraeta.user.btl.model.enquiries.BusinessEnquiryDetail;
import com.agraeta.user.btl.model.enquiries.RegisteredDealerData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class RegisteredDealerListAdapter extends BaseAdapter {

    List<RegisteredDealerData.RegisteredDealer> dealerList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public RegisteredDealerListAdapter(List<RegisteredDealerData.RegisteredDealer> dealerList, Activity activity) {
        this.dealerList = dealerList;
        this.activity = activity;

        inflater=activity.getLayoutInflater();

        builder=new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return dealerList.size();
    }

    @Override
    public Object getItem(int position) {
        return dealerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        OrderHolder orderHolder;

        if(convertView==null){
            convertView=inflater.inflate(R.layout.layout_enquiry_listitem_admin,null);
            orderHolder=new OrderHolder();

            orderHolder.txt_userName=(TextView) convertView.findViewById(R.id.txt_userName);
            orderHolder.txt_mobileNo=(TextView) convertView.findViewById(R.id.txt_mobileNo);
            orderHolder.txt_onDate=(TextView) convertView.findViewById(R.id.txt_onDate);
            orderHolder.txt_onDate.setVisibility(View.GONE);
            orderHolder.img_info= (ImageView) convertView.findViewById(R.id.img_info);

            convertView.setTag(orderHolder);
        }
        else {
            orderHolder= (OrderHolder) convertView.getTag();
        }

        orderHolder.txt_userName.setText(dealerList.get(position).getSeller().getFirm_name());
        orderHolder.txt_mobileNo.setText(dealerList.get(position).getSeller().getFirst_name()+" "+dealerList.get(position).getSeller().getLast_name());

        orderHolder.img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, RegisteredDealerDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("dealer",dealerList.get(position));
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    private class OrderHolder{
        TextView txt_userName,txt_mobileNo,txt_onDate;
        ImageView img_info;
    }
}
