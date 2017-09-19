package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.Bean_Cart_Data;
import com.agraeta.user.btl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 05-Jun-17.
 */

public class QuotationListAdapter extends BaseAdapter {

    List<Bean_Cart_Data> quotationList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;
    QuotationListener quotationListener;

    public QuotationListAdapter(List<Bean_Cart_Data> quotationList, Activity activity,QuotationListener quotationListener) {
        this.quotationList = quotationList;
        this.activity = activity;
        this.quotationListener=quotationListener;
        this.inflater=activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return quotationList.size();
    }

    @Override
    public Object getItem(int position) {
        return quotationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.checkout_product_list, parent,false);

        final TextView tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
        final TextView tv_product_code = (TextView) view.findViewById(R.id.tv_product_code);
        ImageView img_delete=(ImageView) view.findViewById(R.id.img_delete);
        final TextView tv_product_attribute = (TextView) view.findViewById(R.id.tv_product_attribute);
        final TextView tv_pack_of = (TextView) view.findViewById(R.id.tv_pack_of);
        final TextView tv_product_selling_price = (TextView) view.findViewById(R.id.tv_product_selling_price);
        final TextView tv_product_qty = (TextView) view.findViewById(R.id.tv_product_qty);
        final TextView txt_scheme = (TextView) view.findViewById(R.id.txt_scheme);
        final TextView txt_discount = (TextView) view.findViewById(R.id.txt_discount);
        //  final TextView tv_total_product = (TextView) convertView.findViewById(R.id.tv_total_product);
        final TextView tv_product_total_cost = (TextView) view.findViewById(R.id.tv_product_total_cost);
        LinearLayout l_cal = (LinearLayout) view.findViewById(R.id.l_cal);
        LinearLayout l_cal_qty = (LinearLayout) view.findViewById(R.id.l_call_qty);
        TextView txt_qty = (TextView) view.findViewById(R.id.tv_qty);

        float totalPrice = Float.parseFloat(quotationList.get(position).getItem_total());

        if (totalPrice == 0) {
            l_cal.setVisibility(View.GONE);
        }

        if(quotationList.get(position).getPro_scheme().trim().isEmpty()){
            img_delete.setVisibility(View.GONE);
            tv_product_qty.setClickable(false);
        }

        double mrpPrice = Double.parseDouble(quotationList.get(position).getMrp());
        double sellingPrice = Double.parseDouble(quotationList.get(position).getSelling_price());

        if (mrpPrice > sellingPrice) {
            /*double offPrice = mrpPrice - sellingPrice;

            double offPercent = (offPrice * 100);
            double percent =offPercent / mrpPrice;

            Log.e("Off Percent",percent+" %");

            int discount = (int) percent;

            Log.e("Discount",discount+" %");

            if (discount >= 1) txt_discount.setText("Discount : " + discount + " % OFF");*/

            double offPrice = mrpPrice - sellingPrice;
            double offPercent = (offPrice * 100);
            double discount = offPercent / mrpPrice;
            if (discount >= 1)
                txt_discount.setText("Discount : " + String.format("%.1f", discount) + " % OFF");
        }

        if (totalPrice == 0) {
            txt_discount.setText("FREE");
        }


        tv_product_name.setText(quotationList.get(position).getName());
        tv_product_code.setText(quotationList.get(position).getPro_code());
        tv_pack_of.setText(quotationList.get(position).getPack_of());

        if (quotationList.get(position).getScheme_title().trim().isEmpty()) {
            txt_scheme.setVisibility(View.GONE);
        } else {
            txt_scheme.setVisibility(View.VISIBLE);
            txt_scheme.setText(quotationList.get(position).getScheme_title());
        }

        tv_product_selling_price.setText(quotationList.get(position).getSelling_price());
        tv_product_qty.setText(quotationList.get(position).getQuantity());
        txt_qty.setText(quotationList.get(position).getQuantity());
        double t = Double.parseDouble(quotationList.get(position).getItem_total());
        String str = String.format("%.2f", t);
        tv_product_total_cost.setText(activity.getResources().getString(R.string.Rs) + "" + str);


        String[] option = quotationList.get(position).getOption_name().toString().split(", ");
        String[] value = quotationList.get(position).getOption_value_name().split(", ");
        String option_value = "";
        if (quotationList.get(position).getOption_name().equalsIgnoreCase("") || quotationList.get(position).getOption_name().equalsIgnoreCase("null")) {
            tv_product_attribute.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < option.length; i++) {
                if (i == 0) {
                    option_value = option[i] + ": " + value[i];
                } else {
                    option_value = option_value + ", " + option[i] + ": " + value[i];
                }
            }

            tv_product_attribute.setText(option_value);
        }

        img_delete.setVisibility(View.VISIBLE);

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quotationListener.onQuantityDelete(position);
            }
        });

        tv_product_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quotationListener.onQuantityEdit(position);
            }
        });

        return view;
    }

    public interface QuotationListener{
        void onQuantityEdit(int position);
        void onQuantityDelete(int position);
    }
}
