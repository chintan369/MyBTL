package com.agraeta.user.btl;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class FullZoomImageViewActivity extends AppCompatActivity {

    ViewPager imageViewer;
    ArrayList<String> imagePaths=new ArrayList<>();
    int index=0;

    CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_zoom_image_view);

        Intent intent=getIntent();
        imagePaths=intent.getStringArrayListExtra("imagePaths");
        index=intent.getIntExtra("index",0);

        setActionBar();
        fetchIDs();
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();

        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        frame.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);
        img_home.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void fetchIDs() {
        imageViewer=(ViewPager) findViewById(R.id.imageViewer);
        customPagerAdapter=new CustomPagerAdapter(getApplicationContext(),imagePaths,this,true);
        imageViewer.setAdapter(customPagerAdapter);
        imageViewer.setCurrentItem(index,true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
