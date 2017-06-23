package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.OrderInvoice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 23-Jun-17.
 */

public class OrderInvoiceAdapter extends BaseAdapter {
    List<OrderInvoice> orderInvoiceList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    public OrderInvoiceAdapter(List<OrderInvoice> orderInvoiceList, Activity activity) {
        this.orderInvoiceList = orderInvoiceList;
        this.activity = activity;
        this.inflater=activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return orderInvoiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderInvoiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(R.layout.layout_orderinvoice_item,parent,false);

        TextView txt_invoiceNo=(TextView) view.findViewById(R.id.txt_invoiceNo);
        TextView txt_date=(TextView) view.findViewById(R.id.txt_date);
        TextView txt_total=(TextView) view.findViewById(R.id.txt_total);
        ImageView img_options=(ImageView) view.findViewById(R.id.img_options);

        txt_invoiceNo.setText(orderInvoiceList.get(position).getOrderInvoice().getInvoice_no());
        txt_date.setText(orderInvoiceList.get(position).getOrderInvoice().getInvoice_date());
        txt_total.setText(orderInvoiceList.get(position).getOrderInvoice().getOrder_total());

        img_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
