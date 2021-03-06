package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.CompanySalesPerson.RegisteredUserDSRDetailActivity;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.RegisteredUserData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nivida new on 02-Jun-17.
 */

public class RegisteredUserDSRAdapter extends BaseAdapter {

    List<RegisteredUserData.RegisteredUser> userList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;
    Date date3;

    public RegisteredUserDSRAdapter(List<RegisteredUserData.RegisteredUser> userList, Activity activity) {
        this.userList = userList;
        this.activity = activity;
        this.inflater=activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.custom_skip_layout,parent,false);

        TextView txt_skipfor=(TextView) view.findViewById(R.id.txt_skipfor);
        TextView txt_skipby=(TextView) view.findViewById(R.id.txt_skipby);
        ImageView img_info=(ImageView) view.findViewById(R.id.img_info);
        TextView txt_date=(TextView) view.findViewById(R.id.txt_date);

        String date=userList.get(position).getOrderReason().getCreated();

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  //2017-07-01 05:06:17

            date3 = sdf.parse(date);
        }
        catch (final ParseException e) {
            e.printStackTrace();
        }
        txt_date.setText("Date : "+new SimpleDateFormat("dd-MM-yyyy").format(date3));

        txt_skipfor.setText(userList.get(position).getOrderUser().getFirst_name() + " " + userList.get(position).getOrderUser().getLast_name());
        txt_skipby.setText("Skipped By : "+userList.get(position).getUser().getFirst_name()+" "+userList.get(position).getUser().getLast_name());

        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, RegisteredUserDSRDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userData",userList.get(position));
                activity.startActivity(intent);
            }
        });

        return view;
    }

    public void updateData(List<RegisteredUserData.RegisteredUser> searchedUserList) {
        this.userList = searchedUserList;
        notifyDataSetChanged();
    }

}
