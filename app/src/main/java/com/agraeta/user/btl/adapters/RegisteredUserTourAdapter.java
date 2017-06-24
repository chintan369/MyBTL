package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.RegisteredUserTourResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 24-Jun-17.
 */

public class RegisteredUserTourAdapter extends BaseAdapter {

    List<RegisteredUserTourResponse.RegisteredUserTour> userTourList = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    public RegisteredUserTourAdapter(List<RegisteredUserTourResponse.RegisteredUserTour> userTourList, Activity activity) {
        this.userTourList = userTourList;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return userTourList.size();
    }

    @Override
    public Object getItem(int position) {
        return userTourList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_user_item, parent, false);

        TextView txt_firmName = (TextView) view.findViewById(R.id.txt_firmName);
        TextView txt_fullname = (TextView) view.findViewById(R.id.txt_fullname);
        TextView txt_date = (TextView) view.findViewById(R.id.txt_date);

        txt_firmName.setText(userTourList.get(position).getDistributor().getFirm_name());
        txt_fullname.setText(userTourList.get(position).getUser().getFirst_name() + " " + userTourList.get(position).getUser().getLast_name());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
