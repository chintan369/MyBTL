package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.OrderListItem;
import com.agraeta.user.btl.model.enquiries.ProductEnquiryDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class ProductEnquiryListAdapter extends BaseAdapter {

    List<ProductEnquiryDetail> productEnquiryDetails=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public ProductEnquiryListAdapter(List<ProductEnquiryDetail> productEnquiryDetails, Activity activity) {
        this.productEnquiryDetails = productEnquiryDetails;
        this.activity = activity;

        inflater=activity.getLayoutInflater();

        builder=new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return productEnquiryDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return productEnquiryDetails.get(position);
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

        orderHolder.txt_userName.setText(productEnquiryDetails.get(position).getName());
        orderHolder.txt_mobileNo.setText(productEnquiryDetails.get(position).getContactNo());
        orderHolder.txt_onDate.setText(productEnquiryDetails.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));

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
        TextView txt_productName=(TextView) dialogView.findViewById(R.id.txt_productName);
        TextView txt_comment=(TextView) dialogView.findViewById(R.id.txt_comment);
        TextView txt_onDate=(TextView) dialogView.findViewById(R.id.txt_onDate);
        LinearLayout layout_companyName=(LinearLayout) dialogView.findViewById(R.id.layout_companyName);

        layout_companyName.setVisibility(View.GONE);

        ImageView img_close=(ImageView) dialogView.findViewById(R.id.img_close);

        txt_userName.setText(productEnquiryDetails.get(position).getName());
        txt_emailID.setText(productEnquiryDetails.get(position).getEmail());
        txt_contactNo.setText(productEnquiryDetails.get(position).getContactNo());
        txt_onDate.setText(productEnquiryDetails.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));
        txt_city.setText(productEnquiryDetails.get(position).getCityName());
        txt_state.setText(productEnquiryDetails.get(position).getStateName());
        txt_productName.setText(productEnquiryDetails.get(position).getProductName());
        txt_comment.setText(productEnquiryDetails.get(position).getComment());

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
