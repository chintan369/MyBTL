package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.UnregisteredUserDetailActivity;
import com.agraeta.user.btl.model.UnregisteredUserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 24-Jun-17.
 */

public class UnRegisteredUserTourAdapter extends BaseAdapter {

    List<UnregisteredUserData.UserData> userTourList = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    public UnRegisteredUserTourAdapter(List<UnregisteredUserData.UserData> userTourList, Activity activity) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_list_unregister_user, parent, false);

        TextView txt_firmName = (TextView) view.findViewById(R.id.txt_firmName);
        ImageView img_info = (ImageView) view.findViewById(R.id.img_info);

        txt_firmName.setText(userTourList.get(position).getUnRegisteredUser().getFirm_name());
        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UnregisteredUserDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userData", userTourList.get(position));
                activity.startActivity(intent);
            }
        });

        return view;
    }
}
