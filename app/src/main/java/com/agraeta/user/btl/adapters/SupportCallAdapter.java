package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.SupportCallInfo;

import java.util.ArrayList;

/**
 * Created by Nivida new on 09-Jun-17.
 */

public class SupportCallAdapter extends BaseAdapter {
    ArrayList<SupportCallInfo> callInfos=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    public SupportCallAdapter(ArrayList<SupportCallInfo> callInfos, Activity activity) {
        this.callInfos = callInfos;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return callInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return callInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.layout_support_item,parent,false);

        TextView txt_title=(TextView) view.findViewById(R.id.txt_title);
        TextView txt_contact=(TextView) view.findViewById(R.id.txt_contact);

        txt_title.setText(callInfos.get(position).getTitle());
        txt_contact.setText(callInfos.get(position).getContact());

        return view;
    }
}
