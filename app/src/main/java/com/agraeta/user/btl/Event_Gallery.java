package com.agraeta.user.btl;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Event_Gallery extends AppCompatActivity {
    ViewPager mViewPager;
    CustomPagerAdapter mCustomPagerAdapter;
    ArrayList<String> array_image = new ArrayList<String>();
    DatabaseHandler db;
    AppPrefs appPrefs;
    ArrayList<Bean_texhnicalImages> bean_technical = new ArrayList<Bean_texhnicalImages>();
    String productCode="";
    String productName="";
    String Id;
    protected void onResume() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        Log.e("System GC", "Called");
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_gallery);

        appPrefs  = new AppPrefs(Event_Gallery.this);



//        getActionBar().hide();
        Intent intent=getIntent();
        int position=intent.getIntExtra("position",0);
        bean_technical=(ArrayList<Bean_texhnicalImages>)intent.getSerializableExtra("FILES_TO_SEND");
        productCode=intent.getStringExtra("productCode");
        productName=intent.getStringExtra("productName");
        Id = intent.getStringExtra("ID").toString();
        array_image = new ArrayList<String>();
        for (int i = 0; i < bean_technical.size(); i++) {
            array_image.add(bean_technical.get(i).getImg());
        }

        mCustomPagerAdapter = new CustomPagerAdapter(Event_Gallery.this,array_image,Event_Gallery.this);
        mCustomPagerAdapter.setCodeAndName(productCode,productName);
        //mViewPager = (ViewPager) findViewById(R.id.pager_gallery);

        //mViewPager.setAdapter(mCustomPagerAdapter);


        ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.pager_gallery);
        // setContentView(mViewPager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(position);
    }
}
