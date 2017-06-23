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
import com.agraeta.user.btl.SkipOrderUnregisterUserActivity;
import com.agraeta.user.btl.UnregisteredUserDetailActivity;
import com.agraeta.user.btl.model.UnregisteredUserData;

import java.util.ArrayList;
import java.util.List;

import static com.agraeta.user.btl.UnRegisteredUserListActivity.REQUEST_ADD_EDIT;

/**
 * Created by Nivida new on 22-May-17.
 */

public class UnregisteredUserAdapter extends BaseAdapter {

    List<UnregisteredUserData.UserData> userList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    boolean fromAdmin=false;

    public UnregisteredUserAdapter(List<UnregisteredUserData.UserData> userList, Activity activity) {
        this.userList = userList;
        this.activity = activity;
        this.inflater=activity.getLayoutInflater();
    }

    public void setFromAdmin(boolean fromAdmin) {
        this.fromAdmin=fromAdmin;
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
        View view=inflater.inflate(R.layout.layout_list_unregister_user,parent,false);
        TextView txt_firmName=(TextView) view.findViewById(R.id.txt_firmName);
        ImageView img_info=(ImageView) view.findViewById(R.id.img_info);

        txt_firmName.setText(userList.get(position).getUnRegisteredUser().getFirm_name());
        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, UnregisteredUserDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userData",userList.get(position));
                activity.startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fromAdmin){
                    Intent intent=new Intent(activity, SkipOrderUnregisterUserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("isInEditMode",true);
                    intent.putExtra("userData",userList.get(position).getUnRegisteredUser());
                    activity.startActivityForResult(intent,REQUEST_ADD_EDIT);
                }

            }
        });

        return view;
    }

    public void updateData(List<UnregisteredUserData.UserData> searchedUserList) {
        this.userList=searchedUserList;
        notifyDataSetChanged();
    }
}
