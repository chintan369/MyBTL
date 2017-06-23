package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.VideoGalleryDetailActivity;
import com.agraeta.user.btl.model.VideoResponse;
import com.agraeta.user.btl.utils.BlurTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 10-Jun-17.
 */

public class VideoGalleryAdapter extends BaseAdapter {

    List<VideoResponse.Video> videoList=new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;

    public VideoGalleryAdapter(List<VideoResponse.Video> videoList, Activity activity) {
        this.videoList = videoList;
        this.activity = activity;
        this.inflater=activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.layout_videogallery_item,parent,false);

        ImageView img_youtubeThumb=(ImageView) view.findViewById(R.id.img_youtubeThumb);
        TextView txt_productName=(TextView) view.findViewById(R.id.txt_productName);
        TextView txt_productCode=(TextView) view.findViewById(R.id.txt_productCode);

        Picasso.with(activity)
                .load(C.getYoutubeThumb(videoList.get(position).getUrl()))
                .transform(new BlurTransformation(activity))
                .into(img_youtubeThumb);

        txt_productName.setText(videoList.get(position).getProduct_name());
        txt_productCode.setText(videoList.get(position).getProduct_code());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, VideoGalleryDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("video",videoList.get(position));
                activity.startActivity(intent);
            }
        });

        return view;
    }
}
