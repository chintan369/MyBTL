package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.CatalogResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 27-Jun-17.
 */

public class CatalogAdapter extends BaseAdapter {

    List<CatalogResponse.Catalog> catalogList = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    public CatalogAdapter(List<CatalogResponse.Catalog> catalogList, Activity activity) {
        this.catalogList = catalogList;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return catalogList.size();
    }

    @Override
    public Object getItem(int position) {
        return catalogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_catalog_list, parent, false);
        }

        ImageView img_thumb = (ImageView) convertView.findViewById(R.id.img_thumb);
        TextView txt_fileName = (TextView) convertView.findViewById(R.id.txt_fileName);

        Picasso.with(activity)
                .load(Globals.IMAGE_LINK + catalogList.get(position).getImg_url())
                .placeholder(R.drawable.btl_watermark)
                .into(img_thumb);

        txt_fileName.setText(catalogList.get(position).getName());

        return convertView;
    }
}
