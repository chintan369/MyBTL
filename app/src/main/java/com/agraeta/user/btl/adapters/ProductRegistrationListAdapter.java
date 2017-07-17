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
import com.agraeta.user.btl.model.enquiries.ProductRegistrationDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class ProductRegistrationListAdapter extends BaseAdapter {

    List<ProductRegistrationDetail> registrationDetailList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public ProductRegistrationListAdapter(List<ProductRegistrationDetail> registrationDetailList, Activity activity) {
        this.registrationDetailList = registrationDetailList;
        this.activity = activity;

        inflater=activity.getLayoutInflater();

        builder=new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return registrationDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return registrationDetailList.get(position);
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

        orderHolder.txt_userName.setText(registrationDetailList.get(position).getName());
        orderHolder.txt_mobileNo.setText(registrationDetailList.get(position).getContactNo());
        orderHolder.txt_onDate.setText(registrationDetailList.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));

        orderHolder.img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformationDialog(position);
            }
        });

        return convertView;
    }

    private void showInformationDialog(final int position){
        View dialogView=inflater.inflate(R.layout.layout_dialog_enquiry_info,null);

        TextView txt_userName=(TextView) dialogView.findViewById(R.id.txt_userName);
        TextView txt_emailID=(TextView) dialogView.findViewById(R.id.txt_emailID);
        TextView txt_contactNo=(TextView) dialogView.findViewById(R.id.txt_contactNo);
        TextView txt_city=(TextView) dialogView.findViewById(R.id.txt_city);
        TextView txt_state=(TextView) dialogView.findViewById(R.id.txt_state);
        TextView txt_productName=(TextView) dialogView.findViewById(R.id.txt_productName);
        LinearLayout layout_companyName= (LinearLayout) dialogView.findViewById(R.id.layout_companyName);
        LinearLayout layout_purchaseDate= (LinearLayout) dialogView.findViewById(R.id.layout_purchaseDate);
        LinearLayout layout_invoice= (LinearLayout) dialogView.findViewById(R.id.layout_invoice);
        TextView txt_onDate=(TextView) dialogView.findViewById(R.id.txt_onDate);
        TextView txt_labelEnquiry=(TextView) dialogView.findViewById(R.id.txt_labelEnquiry);
        TextView txt_comment=(TextView) dialogView.findViewById(R.id.txt_comment);
        TextView txt_purchaseDate=(TextView) dialogView.findViewById(R.id.txt_purchaseDate);
        TextView txt_invoiceLink=(TextView) dialogView.findViewById(R.id.txt_invoiceLink);

        TextView txt_cityLabel=(TextView)dialogView.findViewById(R.id.txt_cityLabel);
        TextView txt_stateLabel=(TextView)dialogView.findViewById(R.id.txt_stateLabel);

        txt_cityLabel.setText("Purchase City");
        txt_stateLabel.setText("Purchase State");

        ImageView img_close=(ImageView) dialogView.findViewById(R.id.img_close);

        txt_labelEnquiry.setText("Dealer Name");

        layout_companyName.setVisibility(View.GONE);
        layout_purchaseDate.setVisibility(View.VISIBLE);
        layout_invoice.setVisibility(View.VISIBLE);

        txt_userName.setText(registrationDetailList.get(position).getName());
        txt_emailID.setText(registrationDetailList.get(position).getEmail());
        txt_contactNo.setText(registrationDetailList.get(position).getContactNo());
        txt_onDate.setText(registrationDetailList.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));
        txt_city.setText(registrationDetailList.get(position).getCityName());
        txt_state.setText(registrationDetailList.get(position).getStateName());
        txt_productName.setText(registrationDetailList.get(position).getProductName());
        txt_comment.setText(registrationDetailList.get(position).getRetailerName());
        txt_purchaseDate.setText(registrationDetailList.get(position).getPurchaseDate());


        if(!registrationDetailList.get(position).getInvoice().isEmpty()){
            layout_invoice.setVisibility(View.VISIBLE);
            txt_invoiceLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setType("*/*");
                    intent.setData(Uri.parse(Globals.server_link+registrationDetailList.get(position).getInvoice()));
                    activity.startActivity(intent);
                }
            });
        }

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

    private class OrderHolder {
        TextView txt_userName, txt_mobileNo, txt_onDate;
        ImageView img_info;
    }
}
