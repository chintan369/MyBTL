package com.agraeta.user.btl.adapters;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.admin.ProductStockReportActivity;
import com.agraeta.user.btl.model.ProductStockResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 24-Jun-17.
 */

public class ProductStockReportAdapter extends BaseAdapter {

    List<ProductStockResponse.ProductStock> productStockList = new ArrayList<>();
    ProductStockReportActivity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;

    public ProductStockReportAdapter(List<ProductStockResponse.ProductStock> productStockList, ProductStockReportActivity activity) {
        this.productStockList = productStockList;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
        this.builder = new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return productStockList.size();
    }

    @Override
    public Object getItem(int position) {
        return productStockList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_product_stock_report_item, parent, false);

        TextView txt_productName = (TextView) view.findViewById(R.id.txt_productName);
        TextView txt_productQty = (TextView) view.findViewById(R.id.txt_productQty);
        ImageView img_info = (ImageView) view.findViewById(R.id.img_info);

        txt_productName.setText(productStockList.get(position).getProduct_name());
        txt_productQty.setText(productStockList.get(position).getTotalQty());

        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showInfoDialog(position);
            }
        });

        return view;
    }
}
