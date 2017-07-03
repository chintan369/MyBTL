package com.agraeta.user.btl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ImageLoader imageloader;
    ArrayList<String> array_image;
    Activity activity;
    AppPrefs appPrefs;

    String productCode = "";
    String productName = "";

    boolean withoutFiles = false;
    LayoutInflater inflater;

    public CustomPagerAdapter() {

    }

    public CustomPagerAdapter(Context context, ArrayList<String> array_image, Activity activity) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageloader = new ImageLoader(context);
        this.activity = activity;
        this.array_image = array_image;
        inflater = activity.getLayoutInflater();
    }

    public CustomPagerAdapter(Context context, ArrayList<String> array_image, Activity activity, boolean withoutFiles) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageloader = new ImageLoader(context);
        this.activity = activity;
        this.array_image = array_image;
        this.withoutFiles = withoutFiles;
        inflater = activity.getLayoutInflater();
    }

    public void setCodeAndName(String productCode, String productName) {
        this.productCode = productCode;
        this.productName = productName;
    }

    @Override
    public int getCount() {
        return array_image.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout layout_full = (LinearLayout) inflater.inflate(R.layout.layout_full_image_with_share_option, null);
        ImageView img_share = (ImageView) layout_full.findViewById(R.id.img_share);
        final TouchImageView img_full = (TouchImageView) layout_full.findViewById(R.id.img_full);

        Picasso.with(mContext)
                .load(withoutFiles ? Globals.server_link + array_image.get(position) : Globals.server_link + "files/" + array_image.get(position))
                .placeholder(R.drawable.btl_watermark)
                .into(img_full);

        container.addView(layout_full, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(mContext)
                        .load(withoutFiles ? Globals.server_link + array_image.get(position) : Globals.server_link + "files/" + array_image.get(position))
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                String filePath = "";
                                try {
                                    File rootFile = new File(Environment.getExternalStorageDirectory() + "/BTL/Gallery");
                                    if (rootFile.exists()) rootFile.delete();
                                    rootFile.mkdir();
                                    File imagePath = new File(rootFile + File.separator + productCode + "_" + productName + "_" + position + ".jpg");
                                    if (imagePath.exists()) imagePath.delete();
                                    imagePath.createNewFile();
                                    //fileName = filePath+File.separator+fileName;
                                    OutputStream ostream = new FileOutputStream(imagePath);
                                    filePath = imagePath.getAbsolutePath();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                                    ostream.flush();
                                    ostream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                //Log.e("File Path","--> "+filePath);

                                Intent share = new Intent(Intent.ACTION_SEND);
                                share.setType("image/*");
                                //share.setAction(Intent.ACTION_SEND_MULTIPLE);
                                share.putExtra(Intent.EXTRA_SUBJECT, "" + productCode + "(" + productName + ")");
                                share.putExtra(Intent.EXTRA_TEXT, "Item Code : " + "(" + productCode + ")" + "\nItem Name : " + productName);
                                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                                activity.startActivity(Intent.createChooser(share, "Share via"));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Globals.Toast2(mContext, "Failed to Save Image");
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });

                /*img_full.setDrawingCacheEnabled(true);
                img_full.destroyDrawingCache();
                img_full.buildDrawingCache();
                Bitmap bitmap = img_full.getDrawingCache();*/

            }
        });
        
        
        
      /* img.setOnTouchListener(new OnSwipeTouchListener(activity) {
            public void onSwipeTop() {
                Toast.makeText(activity, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(activity, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(activity, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(activity, "bottom", Toast.LENGTH_SHORT).show();
            }

        });*/

        return layout_full;
    }
 
  /*  @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    	((ViewPager) container).removeView((View) object); 
    }*/

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }


}