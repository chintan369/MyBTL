package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.LRDetailActivity;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.OrderInvoice;
import com.google.gson.Gson;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(R.layout.layout_orderinvoice_item,parent,false);

        TextView txt_invoiceNo=(TextView) view.findViewById(R.id.txt_invoiceNo);
        TextView txt_date=(TextView) view.findViewById(R.id.txt_date);
        TextView txt_total=(TextView) view.findViewById(R.id.txt_total);
        final ImageView img_options=(ImageView) view.findViewById(R.id.img_options);

        txt_invoiceNo.setText(orderInvoiceList.get(position).getOrderInvoice().getInvoice_no());
        txt_date.setText(orderInvoiceList.get(position).getOrderInvoice().getInvoice_date());
        txt_total.setText(orderInvoiceList.get(position).getOrderInvoice().getOrder_total());

        img_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(activity,img_options);
                popupMenu.getMenuInflater().inflate(R.menu.menu_order_invoice,popupMenu.getMenu());
                if(orderInvoiceList.get(position).getInvoiceLr().size()>0){
                    popupMenu.getMenu().findItem(R.id.menu_LRReport).setVisible(true);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_invoice:
                                showInvoiceItemPDF(position);
                                break;
                            case R.id.menu_LRReport:
                                goToLRDetailActivity(position);
                                break;
                        }

                        return false;
                    }
                });

                popupMenu.show();
            }
        });

        return view;
    }

    private void goToLRDetailActivity(int position) {
        Intent intent=new Intent(activity, LRDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("jsonLR",new Gson().toJson(orderInvoiceList.get(position).getInvoiceLr().get(0)));
        activity.startActivity(intent);
    }

    private void showInvoiceItemPDF(int position) {
        String pdfFile= Globals.server_link+orderInvoiceList.get(position).getOrderInvoice().getInvoice_file();
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(pdfFile),"application/pdf");
        activity.startActivity(intent);
    }
}
