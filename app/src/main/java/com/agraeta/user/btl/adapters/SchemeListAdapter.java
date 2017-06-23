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
import com.agraeta.user.btl.model.coupons.SchemeDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 28-Mar-17.
 */

public class SchemeListAdapter extends BaseAdapter {

    List<SchemeDetail> schemeDetailList = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog diallog;

    public SchemeListAdapter(List<SchemeDetail> schemeDetailList, Activity activity) {
        this.schemeDetailList = schemeDetailList;
        this.activity = activity;
        inflater = activity.getLayoutInflater();

        builder=new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return schemeDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return schemeDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_scheme_listitem, null);
            holder = new ViewHolder();

            holder.txt_schemeTitle = (TextView) convertView.findViewById(R.id.txt_schemeTitle);
            holder.txt_schemeType = (TextView) convertView.findViewById(R.id.txt_schemeType);
            holder.txt_buyProduct = (TextView) convertView.findViewById(R.id.txt_buyProduct);
            holder.txt_freeProduct = (TextView) convertView.findViewById(R.id.txt_freeProduct);
            holder.txt_startDate = (TextView) convertView.findViewById(R.id.txt_startDate);
            holder.txt_expiryDate = (TextView) convertView.findViewById(R.id.txt_expiryDate);
            holder.img_info = (ImageView) convertView.findViewById(R.id.img_info);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_schemeTitle.setText(schemeDetailList.get(position).getScheme().getSchemeTitle());
        holder.txt_schemeType.setText(schemeDetailList.get(position).getScheme().getSchemeTypeID());
        holder.txt_buyProduct.setText(schemeDetailList.get(position).getBuyProduct().getProductName()+", "+schemeDetailList.get(position).getScheme().getBuyQuantity()+" Quantity");
        holder.txt_freeProduct.setText(schemeDetailList.get(position).getGetFreeProduct().getProductName()+", "+schemeDetailList.get(position).getScheme().getGetFreeQuantity()+" Quantity");
        holder.txt_startDate.setText(schemeDetailList.get(position).getScheme().getStartDate());
        holder.txt_expiryDate.setText(schemeDetailList.get(position).getScheme().getExpiryDate());

        holder.img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformation(position);
            }
        });

        return convertView;
    }

    private void showInformation(int position) {
        View view=inflater.inflate(R.layout.layout_dialog_scheme_detail,null);

        TextView txt_schemeTitle=(TextView) view.findViewById(R.id.txt_schemeTitle);
        TextView txt_schemeName=(TextView) view.findViewById(R.id.txt_schemeName);
        TextView txt_schemeType=(TextView) view.findViewById(R.id.txt_schemeType);
        TextView txt_buyQuantity=(TextView) view.findViewById(R.id.txt_buyQuantity);
        TextView txt_freeQuantity=(TextView) view.findViewById(R.id.txt_freeQuantity);
        TextView txt_buyProduct=(TextView) view.findViewById(R.id.txt_buyProduct);
        TextView txt_freeProduct=(TextView) view.findViewById(R.id.txt_freeProduct);
        TextView txt_maxFreeQuantity=(TextView) view.findViewById(R.id.txt_maxFreeQuantity);
        TextView txt_status=(TextView) view.findViewById(R.id.txt_status);
        TextView txt_validFromDate=(TextView) view.findViewById(R.id.txt_validFromDate);
        ImageView img_close=(ImageView) view.findViewById(R.id.img_close);

        txt_schemeTitle.setText(schemeDetailList.get(position).getScheme().getSchemeTitle());
        txt_schemeName.setText(schemeDetailList.get(position).getScheme().getSchemeName());
        txt_schemeType.setText(schemeDetailList.get(position).getScheme().getSchemeTypeID());
        txt_buyProduct.setText(schemeDetailList.get(position).getBuyProduct().getProductName());
        txt_buyQuantity.setText(schemeDetailList.get(position).getScheme().getBuyQuantity());
        txt_freeProduct.setText(schemeDetailList.get(position).getGetFreeProduct().getProductName());
        txt_freeQuantity.setText(schemeDetailList.get(position).getScheme().getGetFreeQuantity());
        txt_maxFreeQuantity.setText(schemeDetailList.get(position).getScheme().getMaxGetQuantity());
        txt_status.setText(schemeDetailList.get(position).getScheme().getStatus());
        txt_validFromDate.setText(schemeDetailList.get(position).getScheme().getStartDate()+" To "+schemeDetailList.get(position).getScheme().getExpiryDate());

        builder.setView(view);

        diallog=builder.create();

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diallog.dismiss();
            }
        });

        diallog.show();
    }

    private class ViewHolder {
        TextView txt_schemeTitle, txt_schemeType, txt_buyProduct, txt_freeProduct, txt_startDate, txt_expiryDate;
        ImageView img_info;
    }
}
