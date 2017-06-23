package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.enquiries.BusinessEnquiryDetail;
import com.agraeta.user.btl.model.enquiries.ProductEnquiryDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class BusinessEnquiryListAdapter extends BaseAdapter {

    List<BusinessEnquiryDetail> businessEnquiryDetailList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public BusinessEnquiryListAdapter(List<BusinessEnquiryDetail> businessEnquiryDetailList, Activity activity) {
        this.businessEnquiryDetailList = businessEnquiryDetailList;
        this.activity = activity;

        inflater=activity.getLayoutInflater();

        builder=new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return businessEnquiryDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return businessEnquiryDetailList.get(position);
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

        orderHolder.txt_userName.setText(businessEnquiryDetailList.get(position).getName());
        orderHolder.txt_mobileNo.setText(businessEnquiryDetailList.get(position).getContactNo());
        orderHolder.txt_onDate.setText(businessEnquiryDetailList.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));

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

    private void showInformationDialog(int position){
        View dialogView=inflater.inflate(R.layout.layout_dialog_enquiry_info,null);

        TextView txt_userName=(TextView) dialogView.findViewById(R.id.txt_userName);
        TextView txt_emailID=(TextView) dialogView.findViewById(R.id.txt_emailID);
        TextView txt_contactNo=(TextView) dialogView.findViewById(R.id.txt_contactNo);
        TextView txt_city=(TextView) dialogView.findViewById(R.id.txt_city);
        TextView txt_state=(TextView) dialogView.findViewById(R.id.txt_state);
        LinearLayout layout_productName= (LinearLayout) dialogView.findViewById(R.id.layout_productName);
        TextView txt_comment=(TextView) dialogView.findViewById(R.id.txt_comment);
        TextView txt_onDate=(TextView) dialogView.findViewById(R.id.txt_onDate);
        TextView txt_labelEnquiry=(TextView) dialogView.findViewById(R.id.txt_labelEnquiry);
        TextView txt_companyName=(TextView) dialogView.findViewById(R.id.txt_companyName);

        txt_labelEnquiry.setText("Enquiry");

        ImageView img_close=(ImageView) dialogView.findViewById(R.id.img_close);

        layout_productName.setVisibility(View.GONE);

        txt_userName.setText(businessEnquiryDetailList.get(position).getName());
        txt_emailID.setText(businessEnquiryDetailList.get(position).getEmail());
        txt_contactNo.setText(businessEnquiryDetailList.get(position).getContactNo());
        txt_onDate.setText(businessEnquiryDetailList.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));
        txt_city.setText(businessEnquiryDetailList.get(position).getCityName());
        txt_state.setText(businessEnquiryDetailList.get(position).getStateName());
        txt_comment.setText(businessEnquiryDetailList.get(position).getComment());
        txt_companyName.setText(businessEnquiryDetailList.get(position).getCompanyName());

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
