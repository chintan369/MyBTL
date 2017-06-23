package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.OrderListItem;
import com.agraeta.user.btl.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Mar-17.
 */

public class UserOrderListAdapter extends BaseAdapter {

    List<OrderListItem> orderListItems=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    public UserOrderListAdapter(List<OrderListItem> orderListItems, Activity activity) {
        this.orderListItems = orderListItems;
        this.activity = activity;

        inflater=activity.getLayoutInflater();

        builder=new AlertDialog.Builder(activity);
    }

    @Override
    public int getCount() {
        return orderListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return orderListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        OrderHolder orderHolder;

        if(convertView==null){
            convertView=inflater.inflate(R.layout.layout_orderlist_admin,null);
            orderHolder=new OrderHolder();

            orderHolder.txt_orderID=(TextView) convertView.findViewById(R.id.txt_orderID);
            orderHolder.txt_orderedFor=(TextView) convertView.findViewById(R.id.txt_orderedFor);
            orderHolder.txt_orderedBy=(TextView) convertView.findViewById(R.id.txt_orderedBy);
            orderHolder.txt_orderAmount=(TextView) convertView.findViewById(R.id.txt_orderAmount);
            orderHolder.txt_orderDate=(TextView) convertView.findViewById(R.id.txt_orderDate);
            orderHolder.txt_orderStatus=(TextView) convertView.findViewById(R.id.txt_orderStatus);

            convertView.setTag(orderHolder);
        }
        else {
            orderHolder= (OrderHolder) convertView.getTag();
        }

        String orderID= "Order ID #"+"<b>"+orderListItems.get(position).order.getId()+"</b>";
        String orderAmount= "<b>"+"Order Amount : "+activity.getString(R.string.ruppe_name)+" "+orderListItems.get(position).order.getTotal()+"</b>";

        orderHolder.txt_orderID.setText(Html.fromHtml(orderID));
        orderHolder.txt_orderedFor.setText(orderListItems.get(position).order.getName());
        orderHolder.txt_orderedBy.setText("Taken by "+orderListItems.get(position).owner.getOwnedBy());
        orderHolder.txt_orderAmount.setText(Html.fromHtml(orderAmount));
        orderHolder.txt_orderDate.setText(orderListItems.get(position).order.getOrderDate("dd MMM yyyy"));
        orderHolder.txt_orderStatus.setText(orderListItems.get(position).orderStatus.getStatus());

        String status=orderListItems.get(position).orderStatus.getStatus().toLowerCase();

        if(status.contains("pending")) orderHolder.txt_orderStatus.setBackgroundResource(R.color.st_pending);
        else if(status.contains("process")) orderHolder.txt_orderStatus.setBackgroundResource(R.color.st_processed);
        else if(status.contains("complete")) orderHolder.txt_orderStatus.setBackgroundResource(R.color.st_compelted);
        else if(status.contains("cancel")) orderHolder.txt_orderStatus.setBackgroundResource(R.color.st_canceled);


        return convertView;
    }

    private class OrderHolder{
        TextView txt_orderID,txt_orderedFor,txt_orderedBy,txt_orderAmount,txt_orderDate,txt_orderStatus;
    }

    public String getOrderID(int position){
        return orderListItems.get(position).order.getId();
    }
}
