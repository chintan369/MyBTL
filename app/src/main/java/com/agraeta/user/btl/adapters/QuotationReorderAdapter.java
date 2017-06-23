package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.QuotationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 23-Jun-17.
 */

public class QuotationReorderAdapter extends BaseAdapter {

    List<QuotationItem> quotationItemList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    public QuotationReorderAdapter(List<QuotationItem> quotationItemList, Activity activity) {
        this.quotationItemList = quotationItemList;
        this.activity = activity;
        this.inflater=activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return quotationItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return quotationItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(R.layout.checkout_product_list,parent,false);



        return view;
    }
}
