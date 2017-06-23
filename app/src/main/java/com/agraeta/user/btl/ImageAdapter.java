package com.agraeta.user.btl;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {

    ArrayList<Integer> image_url = new ArrayList<Integer>();
    ArrayList<String> title = new ArrayList<String>();
    Context context;


    LayoutInflater layoutinflater;
    int position1;
    ImageView im;
    TextView tv;
    Activity activity;
    int display_width;
    FrameLayout framelayout_image;

	public ImageAdapter(Context context, ArrayList<Integer> categoryimg,
            ArrayList<String> title, LayoutInflater layoutinflater,
			Activity activity) {

		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.context = context;
		this.image_url = categoryimg;
		this.title = title;
		this.layoutinflater = layoutinflater;

	}

    public ImageAdapter(Context context, ArrayList<Integer> categoryimg, LayoutInflater layoutinflater,
                        Activity activity) {

        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.context = context;
        this.image_url = categoryimg;

        this.layoutinflater = layoutinflater;

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return image_url.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        position1 = position;
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        display_width = (metrics.widthPixels / 3);
        if (convertView == null) {

            convertView = layoutinflater.inflate(R.layout.grid_img, null);

        }

        framelayout_image = (FrameLayout) convertView
                .findViewById(R.id.framelayout_image);
        im = (ImageView) convertView.findViewById(R.id.imageView1);


        framelayout_image.getLayoutParams().height = display_width;
       // framelayout_image.getLayoutParams().height = display_width;
        // im.getLayoutParams().height = display_width;
        // im.getLayoutParams().width = display_width;
		tv = (TextView) convertView.findViewById(R.id.tv_name);
		tv.setText(title.get(position));

        im.setImageResource(image_url.get(position));

        return convertView;
    }

}
