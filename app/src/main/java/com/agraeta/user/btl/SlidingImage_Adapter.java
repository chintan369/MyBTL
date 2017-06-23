package com.agraeta.user.btl;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<Bean_Slider> IMAGES;
    private LayoutInflater inflater;
    ImageLoader imageloader;
    private Context context;
    AppPrefs prefs;


    public SlidingImage_Adapter(Context context, ArrayList<Bean_Slider> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        prefs=new AppPrefs(context);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();

    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        imageloader = new ImageLoader(context);


        //Log.e("Img",""+IMAGES.get(position).getImg());
       // imageloader.DisplayImage(IMAGES.get(position).getImg(), imageView);
        Picasso.with(context)
                .load(IMAGES.get(position).getImg())
                .placeholder(R.drawable.btl_watermark)
                .into(imageView);

      //  imageView.setImageResource(IMAGES.get(position));

        imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Log.e("LTIC",IMAGES.get(position).getLink_type()+"-->"+IMAGES.get(position).getLink_id()+"-->"+IMAGES.get(position).getHas_child());

                if(IMAGES.get(position).getLink_type().equalsIgnoreCase(C.CATEGORY)){
                    prefs.setUser_notification("");
                    prefs.setUser_CatId(IMAGES.get(position).getLink_id());
                    if(IMAGES.get(position).getHas_child().equals("0")){
                        Log.e("LTIC1",IMAGES.get(position).getLink_type()+"-->"+IMAGES.get(position).getLink_id()+"-->"+IMAGES.get(position).getHas_child());
                        prefs.setPage_Data("1");
                        Intent i = new Intent(context, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                    else {
                        Log.e("LTIC0",IMAGES.get(position).getLink_type()+"-->"+IMAGES.get(position).getLink_id()+"-->"+IMAGES.get(position).getHas_child());
                        Intent i = new Intent(context, Main_CategoryPage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
                else if(IMAGES.get(position).getLink_type().equalsIgnoreCase(C.PRODUCT)){
                    Log.e("LTIC2",IMAGES.get(position).getLink_type()+"-->"+IMAGES.get(position).getLink_id()+"-->"+IMAGES.get(position).getHas_child());
                    prefs.setproduct_id(IMAGES.get(position).getLink_id());
                    prefs.setRef_Detail("");
                    Intent i = new Intent(context, BTLProduct_Detail.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }

            }
        });




        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}