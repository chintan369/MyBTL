package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.combooffer.ComboOfferData;
import com.agraeta.user.btl.model.combooffer.ComboOfferItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 17-Apr-17.
 */

public class ComboOfferListAdapter extends BaseAdapter {

    List<ComboOfferItem> offerItemList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    public ComboOfferListAdapter(List<ComboOfferItem> offerItemList, Activity activity) {
        this.offerItemList = offerItemList;
        this.activity = activity;
        inflater=activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return offerItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return offerItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView img_offerThumb;
        TextView txt_offerTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){

            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.layout_combo_offer_list,null);

            holder.img_offerThumb=(ImageView) convertView.findViewById(R.id.img_offerThumb);
            holder.txt_offerTitle=(TextView) convertView.findViewById(R.id.txt_offerTitle);

            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }

        Picasso.with(activity).load(Globals.IMAGE_LINK+offerItemList.get(position).getImagePath())
                .into(holder.img_offerThumb);

        holder.txt_offerTitle.setText(offerItemList.get(position).getOfferTitle());

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        Log.e("Called","-->"+offerItemList.size());
    }
}
