package com.agraeta.user.btl;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agraeta.user.btl.model.notifications.InboxResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



/**
 * Created by Nivida new on 27-Sep-16.
 */
public class NotificationAdapter extends BaseAdapter {

    List<InboxResponse.InboxData> notificationList=new ArrayList<>();
    Context context;

    public NotificationAdapter(List<InboxResponse.InboxData> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;

        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_notification_item, null);

        InboxResponse.InboxData notification=notificationList.get(position);

        TextView time, message;

        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm dd/MMM", Locale.getDefault());
        sdf.setLenient(true);

        time=(TextView) view.findViewById(R.id.time);
        message=(TextView) view.findViewById(R.id.msg);

        //Log.e("time in adapter",notification.getTime());
        time.setText(notification.getInbox().getCreated());
        message.setText(Html.fromHtml("<p align=\"justify\" style=\"font-size:20px\"><i>"+notification.getInbox().getMsg()+"</i></p>"));
        return view;
    }
}
