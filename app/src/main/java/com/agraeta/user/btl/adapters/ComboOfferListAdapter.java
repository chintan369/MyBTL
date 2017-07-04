package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.ComboOfferActivity;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){

            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.layout_combo_offer_list,null);

            holder.img_offerThumb=(ImageView) convertView.findViewById(R.id.img_offerThumb);
            holder.txt_offerTitle=(TextView) convertView.findViewById(R.id.txt_offerTitle);
            holder.card_comboOffer = (CardView) convertView.findViewById(R.id.card_comboOffer);
            holder.layout_combo = (LinearLayout) convertView.findViewById(R.id.layout_combo);

            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }

        Picasso.with(activity).load(Globals.IMAGE_LINK+offerItemList.get(position).getImagePath())
                .into(holder.img_offerThumb);

        holder.txt_offerTitle.setText(offerItemList.get(position).getOfferTitle());

        holder.img_offerThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ComboOfferActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("combo_id", offerItemList.get(position).getOfferID());
                activity.startActivity(intent);
            }
        });

        holder.txt_offerTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ComboOfferActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("combo_id", offerItemList.get(position).getOfferID());
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        Log.e("Called","-->"+offerItemList.size());
    }

    private class ViewHolder {
        ImageView img_offerThumb;
        TextView txt_offerTitle;
        CardView card_comboOffer;
        LinearLayout layout_combo;
    }
}
