package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.coupons.CouponDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 28-Mar-17.
 */

public class CouponListAdapter extends BaseAdapter {

    List<CouponDetail> couponDetailList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public CouponListAdapter(List<CouponDetail> couponDetailList, Activity activity) {
        this.couponDetailList = couponDetailList;
        this.activity = activity;
        inflater=activity.getLayoutInflater();

        builder=new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return couponDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return couponDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null){
            convertView=inflater.inflate(R.layout.layout_coupon_listitem,null);
            holder=new ViewHolder();

            holder.txt_couponName=(TextView) convertView.findViewById(R.id.txt_couponName);
            holder.txt_couponCode=(TextView) convertView.findViewById(R.id.txt_couponCode);
            holder.txt_couponType=(TextView) convertView.findViewById(R.id.txt_couponType);
            holder.txt_startDate=(TextView) convertView.findViewById(R.id.txt_startDate);
            holder.txt_expiryDate=(TextView) convertView.findViewById(R.id.txt_expiryDate);
            holder.img_info= (ImageView) convertView.findViewById(R.id.img_info);

            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.txt_couponName.setText(couponDetailList.get(position).getCouponName());
        holder.txt_couponCode.setText("Code : "+couponDetailList.get(position).getCouponCode());
        holder.txt_couponType.setText(couponDetailList.get(position).getCouponType());
        holder.txt_startDate.setText(couponDetailList.get(position).getStartDate());
        holder.txt_expiryDate.setText(couponDetailList.get(position).getExpiryDate());

        holder.img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoDialog(position);
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView txt_couponName,txt_couponCode,txt_couponType,txt_startDate,txt_expiryDate;
        ImageView img_info;
    }

    private void showInfoDialog(int position){
        View dialogView=inflater.inflate(R.layout.layout_dialog_coupon_detail,null);

        ImageView img_close=(ImageView) dialogView.findViewById(R.id.img_close);
        TextView txt_couponTitle=(TextView) dialogView.findViewById(R.id.txt_couponTitle);
        TextView txt_couponCode=(TextView) dialogView.findViewById(R.id.txt_couponCode);
        TextView txt_couponType=(TextView) dialogView.findViewById(R.id.txt_couponType);
        TextView txt_minOrderAmount=(TextView) dialogView.findViewById(R.id.txt_minOrderAmount);
        TextView txt_usedCoupons=(TextView) dialogView.findViewById(R.id.txt_usedCoupons);
        TextView txt_discount=(TextView) dialogView.findViewById(R.id.txt_discount);
        TextView txt_maxDiscountValue=(TextView) dialogView.findViewById(R.id.txt_maxDiscountValue);
        TextView txt_multipleUseTime=(TextView) dialogView.findViewById(R.id.txt_multipleUseTime);
        TextView txt_status=(TextView) dialogView.findViewById(R.id.txt_status);
        TextView txt_validFromDate=(TextView) dialogView.findViewById(R.id.txt_validFromDate);

        txt_couponTitle.setText(couponDetailList.get(position).getCouponName());
        txt_couponCode.setText(couponDetailList.get(position).getCouponCode());
        txt_couponType.setText(couponDetailList.get(position).getCouponType());
        txt_discount.setText(couponDetailList.get(position).getDiscount());
        txt_minOrderAmount.setText(couponDetailList.get(position).getMinOrderValue());
        txt_usedCoupons.setText(couponDetailList.get(position).getTotalUsed());
        txt_maxDiscountValue.setText(couponDetailList.get(position).getMaxDiscount());
        txt_multipleUseTime.setText(couponDetailList.get(position).getMultipleTimeUses());
        txt_status.setText(couponDetailList.get(position).getStatus());
        txt_validFromDate.setText(couponDetailList.get(position).getStartDate()+" To "+couponDetailList.get(position).getExpiryDate());

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
