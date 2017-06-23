package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.enquiries.CareerDetail;
import com.agraeta.user.btl.model.enquiries.CorporateOfficeDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class CorpOfficeListAdapter extends BaseAdapter {

    List<CorporateOfficeDetail> officeDetailList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public CorpOfficeListAdapter(List<CorporateOfficeDetail> officeDetailList, Activity activity) {
        this.officeDetailList = officeDetailList;
        this.activity = activity;

        inflater=activity.getLayoutInflater();

        builder=new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return officeDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return officeDetailList.get(position);
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
            orderHolder.img_info= (ImageView) convertView.findViewById(R.id.img_info);

            convertView.setTag(orderHolder);
        }
        else {
            orderHolder= (OrderHolder) convertView.getTag();
        }

        orderHolder.txt_userName.setText(officeDetailList.get(position).getName());
        orderHolder.txt_mobileNo.setText(officeDetailList.get(position).getContactNo());
        orderHolder.txt_onDate.setText(officeDetailList.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));

        orderHolder.img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformationDialog(position);
            }
        });

        return convertView;
    }

    private class OrderHolder{
        TextView txt_userName,txt_mobileNo,txt_onDate;
        ImageView img_info;
    }

    private void showInformationDialog(final int position){
        View dialogView=inflater.inflate(R.layout.layout_dialog_enquiry_info,null);

        TextView txt_userName=(TextView) dialogView.findViewById(R.id.txt_userName);
        TextView txt_emailID=(TextView) dialogView.findViewById(R.id.txt_emailID);
        TextView txt_contactNo=(TextView) dialogView.findViewById(R.id.txt_contactNo);
        LinearLayout layout_productName= (LinearLayout) dialogView.findViewById(R.id.layout_productName);
        LinearLayout layout_companyName= (LinearLayout) dialogView.findViewById(R.id.layout_companyName);
        LinearLayout layout_city= (LinearLayout) dialogView.findViewById(R.id.layout_city);
        LinearLayout layout_state= (LinearLayout) dialogView.findViewById(R.id.layout_state);
        TextView txt_onDate=(TextView) dialogView.findViewById(R.id.txt_onDate);
        TextView txt_labelEnquiry=(TextView) dialogView.findViewById(R.id.txt_labelEnquiry);
        TextView txt_comment=(TextView) dialogView.findViewById(R.id.txt_comment);

        ImageView img_close=(ImageView) dialogView.findViewById(R.id.img_close);

        txt_labelEnquiry.setText("Message");

        layout_productName.setVisibility(View.GONE);
        layout_companyName.setVisibility(View.GONE);
        layout_city.setVisibility(View.GONE);
        layout_state.setVisibility(View.GONE);

        txt_userName.setText(officeDetailList.get(position).getName());
        txt_emailID.setText(officeDetailList.get(position).getEmail());
        txt_contactNo.setText(officeDetailList.get(position).getContactNo());
        txt_comment.setText(officeDetailList.get(position).getMessage());
        txt_onDate.setText(officeDetailList.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));

        builder.setView(dialogView);

        dialog=builder.create();

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
