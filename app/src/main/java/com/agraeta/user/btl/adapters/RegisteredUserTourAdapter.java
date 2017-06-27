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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_user_item, parent, false);

        TextView txt_firmName = (TextView) view.findViewById(R.id.txt_firmName);
        TextView txt_visitedBy = (TextView) view.findViewById(R.id.txt_visitedBy);
        TextView txt_date = (TextView) view.findViewById(R.id.txt_date);
        ImageView img_info = (ImageView) view.findViewById(R.id.img_info);

        txt_firmName.setText(userTourList.get(position).getDistributor().getFirm_name());
        if (userTourList.get(position).getSkipOrderPerson().size() > 0) {
            txt_visitedBy.setText("Visited By : " + userTourList.get(position).getSkipOrderPerson().get(0).getUser().getFirst_name() + " " + userTourList.get(position).getSkipOrderPerson().get(0).getUser().getLast_name());
            txt_date.setText("Visit Date : " + userTourList.get(position).getSkipOrderPerson().get(0).getCreated().split(" ")[0]);
            txt_date.setVisibility(View.VISIBLE);
            img_info.setVisibility(View.VISIBLE);
        }

        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisteredUserData.RegisteredUser registeredUser = new RegisteredUserData.RegisteredUser();
                registeredUser.setOrderReason(userTourList.get(position).getSkipOrderPerson().get(0));
                registeredUser.setCountry(userTourList.get(position).getAddress().get(0).getCountry());
                registeredUser.setState(userTourList.get(position).getAddress().get(0).getState());
                registeredUser.setCity(userTourList.get(position).getAddress().get(0).getCity());
                registeredUser.setArea(userTourList.get(position).getAddress().get(0).getArea());
                registeredUser.setRole(userTourList.get(position).getRole());
                registeredUser.setUser(userTourList.get(position).getUser());

                Intent intent = new Intent(activity, RegisteredUserDSRDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userData", registeredUser);
                activity.startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
