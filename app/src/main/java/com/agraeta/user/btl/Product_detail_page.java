package com.agraeta.user.btl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.ActionBar.LayoutParams;
import android.text.Html;

import java.util.ArrayList;

public class Product_detail_page extends AppCompatActivity {

    TextView txt_high;
    LinearLayout l_grid;
    ArrayList<Integer> list_data = new ArrayList<Integer>();
    LinearLayout.LayoutParams params;

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
        setContentView(R.layout.activity_product_detail_page);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        setActionBar();
        txt_high = (TextView) findViewById(R.id.high_text);
        l_grid =(LinearLayout)findViewById(R.id.l_grid);
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);


        txt_high.setText(Html.fromHtml("<medium><b></b></medium><medium> Have the </medium> <medium><b> courage </b></medium> <medium> to explore and seek out new opportunities </medium>"));

        list_data = new ArrayList<Integer>();
        list_data.add(R.drawable.b);
        list_data.add(R.drawable.c);
        list_data.add(R.drawable.a);

        setLayout(list_data);

    }

    private void setLayout(ArrayList<Integer> str) {
        // TODO Auto-generated method stub

        l_grid.removeAllViews();
        //Log.e("1", str.size() + "");

        for (int ij = 0; ij < str.size(); ij++) {

            //Log.e("2",ij+"");

            LinearLayout lmain = new LinearLayout(Product_detail_page.this);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            lmain.setPadding(1, 2, 1, 2);

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    150, 150);

            LinearLayout l1 = new LinearLayout(Product_detail_page.this);
            l1.setLayoutParams(par1);
            l1.setPadding(5, 0, 5, 0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(Product_detail_page.this);
            l3.setLayoutParams(par3);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 0, 5, 0);

            final LinearLayout l2 = new LinearLayout(Product_detail_page.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 0, 5, 0);
            l2.setVisibility(View.GONE);
            final ImageView img_a = new ImageView(Product_detail_page.this);
            par21.setMargins(5, 5, 5, 5);
            if(ij == 0){
                img_a.setImageResource(R.drawable.c);
                img_a.setBackgroundResource(R.drawable.background_gradiant);
                img_a.setPadding(5, 0, 5, 0);
                img_a.setLayoutParams(par21);
            }
            else if(ij == 1){
                img_a.setImageResource(R.drawable.b);
                img_a.setBackgroundResource(R.drawable.background_gradiant);
                img_a.setPadding(5, 5,5,5);
                img_a.setLayoutParams(par21);
            }else if(ij == 2){
                img_a.setImageResource(R.drawable.a);
                img_a.setBackgroundResource(R.drawable.background_gradiant);
                img_a.setPadding(5, 0, 5, 0);
                img_a.setLayoutParams(par21);
            }

            //l1.addView(img_a);




            lmain.addView(img_a);

            l_grid.addView(lmain);

        }


    }
    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();

        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_detail_page.this,MainPage_drawer.class);
                startActivity(i);
            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_detail_page.this,Main_CategoryPage.class);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_detail_page.this,MainPage_drawer.class);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_detail_page.this,Notification.class);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_detail_page.this,MainPage.class);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

}

