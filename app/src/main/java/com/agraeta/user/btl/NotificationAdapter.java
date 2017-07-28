package com.agraeta.user.btl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.AppModel;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.notifications.InboxResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Nivida new on 27-Sep-16.
 */
public class NotificationAdapter extends BaseAdapter {

    List<InboxResponse.InboxData> notificationList=new ArrayList<>();
    Activity context;
    AdminAPI adminAPI;

    public NotificationAdapter(List<InboxResponse.InboxData> notificationList, Activity context) {
        this.notificationList = notificationList;
        this.context = context;
        this.adminAPI = ServiceGenerator.getAPIServiceClass();
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=null;

        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_notification_item, null);

        LinearLayout layout_main = (LinearLayout) view.findViewById(R.id.layout_main);

        InboxResponse.InboxData notification=notificationList.get(position);

        TextView time, message;

        if (notification.getInbox().getIs_read().equals("0")) {
            layout_main.setBackgroundColor(Color.parseColor("#E6E6E6"));
        } else {
            layout_main.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        time=(TextView) view.findViewById(R.id.time);
        message=(TextView) view.findViewById(R.id.msg);

        //Log.e("time in adapter",notification.getTime());
        String date = notification.getInbox().getCreated();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            time.setText(new SimpleDateFormat("dd-MM-yyyy").format(sdf.parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        message.setText(notification.getInbox().getMsg());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullNotificationDialog(position);
            }
        });

        return view;
    }

    private void showFullNotificationDialog(final int position) {
        if (notificationList.get(position).getInbox().getIs_read().equals("0")) {
            Call<AppModel> modelCall = adminAPI.getNotificationRead(notificationList.get(position).getInbox().getId());
            modelCall.enqueue(new Callback<AppModel>() {
                @Override
                public void onResponse(Call<AppModel> call, Response<AppModel> response) {
                    if (response != null) {
                        notificationList.get(position).getInbox().setIs_read("1");
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<AppModel> call, Throwable t) {

                }
            });
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Message");
        builder.setMessage(notificationList.get(position).getInbox().getMsg());
        builder.setPositiveButton("CLOSE", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
