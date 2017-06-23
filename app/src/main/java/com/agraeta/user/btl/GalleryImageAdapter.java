package com.agraeta.user.btl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nivida new on 12-Aug-16.
 */
public class GalleryImageAdapter extends BaseAdapter
{
    private Context context;
    ArrayList<String> array_image_id;
    ImageLoader imageloader;
    AppPrefs prefs;

    public GalleryImageAdapter(Context context, ArrayList<String> array_image_id)
    {
        this.context = context;
        this.array_image_id=array_image_id;
        imageloader = new ImageLoader(context);
    }

    public int getCount() {
        return array_image_id.size();
    }

    public Object getItem(int position) {
        return array_image_id.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup)
    {
        // TODO Auto-generated method stub
        ImageView img = new ImageView(context);

        img.setDrawingCacheEnabled(true);

       // imageloader.DisplayImage(Globals.server_link+"files/"+array_image_id.get(index), R.drawable.btl_noimage, img);
        Picasso.with(context)
                .load(Globals.server_link+"files/"+array_image_id.get(index))
                .placeholder(R.drawable.btl_watermark)
                .into(img);

        img.setLayoutParams(new Gallery.LayoutParams(200, 200));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setScaleType(ImageView.ScaleType.FIT_XY);

        img.setBackgroundResource(R.drawable.gallery_img_border);

        return img;
    }

}