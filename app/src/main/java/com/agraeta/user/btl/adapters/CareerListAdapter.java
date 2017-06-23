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
import com.agraeta.user.btl.model.enquiries.BusinessEnquiryDetail;
import com.agraeta.user.btl.model.enquiries.CareerDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class CareerListAdapter extends BaseAdapter {

    List<CareerDetail> careerDetailList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public CareerListAdapter(List<CareerDetail> careerDetailList, Activity activity) {
        this.careerDetailList = careerDetailList;
        this.activity = activity;

        inflater=activity.getLayoutInflater();

        builder=new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return careerDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return careerDetailList.get(position);
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

        orderHolder.txt_userName.setText(careerDetailList.get(position).getName());
        orderHolder.txt_mobileNo.setText(careerDetailList.get(position).getContactNo());
        orderHolder.txt_onDate.setText(careerDetailList.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));

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
        TextView txt_city=(TextView) dialogView.findViewById(R.id.txt_city);
        TextView txt_state=(TextView) dialogView.findViewById(R.id.txt_state);
        LinearLayout layout_productName= (LinearLayout) dialogView.findViewById(R.id.layout_productName);
        LinearLayout layout_companyName= (LinearLayout) dialogView.findViewById(R.id.layout_companyName);
        LinearLayout layout_comment= (LinearLayout) dialogView.findViewById(R.id.layout_comment);
        LinearLayout layout_vacancy= (LinearLayout) dialogView.findViewById(R.id.layout_vacancy);
        LinearLayout layout_document= (LinearLayout) dialogView.findViewById(R.id.layout_document);
        TextView txt_onDate=(TextView) dialogView.findViewById(R.id.txt_onDate);
        TextView txt_vacancyName=(TextView) dialogView.findViewById(R.id.txt_vacancyName);
        TextView txt_documentLink=(TextView) dialogView.findViewById(R.id.txt_documentLink);

        ImageView img_close=(ImageView) dialogView.findViewById(R.id.img_close);

        layout_productName.setVisibility(View.GONE);
        layout_companyName.setVisibility(View.GONE);
        layout_comment.setVisibility(View.GONE);
        layout_vacancy.setVisibility(View.VISIBLE);
        layout_document.setVisibility(View.VISIBLE);

        txt_userName.setText(careerDetailList.get(position).getName());
        txt_emailID.setText(careerDetailList.get(position).getEmail());
        txt_contactNo.setText(careerDetailList.get(position).getContactNo());
        txt_onDate.setText(careerDetailList.get(position).getOnDate("dd/MM/yyyy","dd/MM/yyyy"));
        txt_city.setText(careerDetailList.get(position).getCityName());
        txt_state.setText(careerDetailList.get(position).getStateName());
        txt_vacancyName.setText(careerDetailList.get(position).getVacancyName());

        txt_documentLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setType("*/*");
                intent.setData(Uri.parse(Globals.server_link+careerDetailList.get(position).getDocument()));
                activity.startActivity(intent);
            }
        });

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
