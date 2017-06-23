package com.agraeta.user.btl;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Cart extends AppCompatActivity {

    LinearLayout l_list;

    ArrayList<Bean_cartlist> bean_cart = new ArrayList<Bean_cartlist>();
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        l_list = (LinearLayout) findViewById(R.id.list_cart);

        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,1.0f);

        bean_cart = new ArrayList<Bean_cartlist>();

        bean_cart.add(new Bean_cartlist(R.drawable.doorcontrol,
                "DOOR CONTROL(Pack Of 8 Pieces)",
                "\u20B910000.00", "\u20B910000.00"));
        bean_cart.add(new Bean_cartlist(R.drawable.doorfitting,
                "DOOR FITTINGS(Pack Of 8 Pieces)",
                "\u20B92050.00", "\u20B92050.00"));

        setLayout(bean_cart);

        //setActionBar();
    }

     private void setActionBar() {

          // TODO Auto-generated method stub
          ActionBar mActionBar = getActionBar();
          mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
          mActionBar.setCustomView(R.layout.actionbar_design);


          View mCustomView = mActionBar.getCustomView();
          ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
          ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
          ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
          ImageView img_category = (ImageView) mCustomView.findViewById(R.id.img_category);
          ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
          ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
          image_drawer.setImageResource(R.drawable.back);

          image_drawer.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(Cart.this,MainPage_drawer.class);
                  startActivity(i);
              }
          });
          img_category.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(Cart.this,Main_CategoryPage.class);
                  startActivity(i);
              }
          });
          img_home.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(Cart.this, MainPage_drawer.class);
                  startActivity(i);
              }
          });
          img_notification.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(Cart.this, Notification.class);
                  startActivity(i);
              }
          });

          mActionBar.setCustomView(mCustomView);
          mActionBar.setDisplayShowCustomEnabled(true);
      }
    private void setLayout(ArrayList<Bean_cartlist> str) {
        // TODO Auto-generated method stub

        l_list.removeAllViews();
        //Log.e("1", str.size() + "");

        for (int ij = 0; ij < str.size(); ij++) {

            //Log.e("2", ij + "");

            LinearLayout lmain = new LinearLayout(Cart.this);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            lmain.setPadding(1, 2, 1, 2);

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,1.0f);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    200, 130,1.0f);
            LinearLayout.LayoutParams par22 = new LinearLayout.LayoutParams(
                    30, 30);
            LinearLayout.LayoutParams par23 = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, 90,1.0f);

            LinearLayout l1 = new LinearLayout(Cart.this);
            l1.setLayoutParams(par1);
            l1.setPadding(5, 0, 5, 0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(Cart.this);

            l3.setLayoutParams(par3);
            //l3.setGravity(Gravity.LEFT | Gravity.CENTER);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setPadding(5, 0, 5, 0);

            LinearLayout l4 = new LinearLayout(Cart.this);

            l4.setLayoutParams(par3);
            //l3.setGravity(Gravity.LEFT | Gravity.CENTER);
            l4.setOrientation(LinearLayout.VERTICAL);
            l4.setPadding(5, 0, 5, 0);

            LinearLayout l5 = new LinearLayout(Cart.this);

            l5.setLayoutParams(par3);
            l5.setGravity(Gravity.CENTER_VERTICAL);
            l5.setOrientation(LinearLayout.VERTICAL);
            l5.setPadding(5, 0, 5, 0);

            final LinearLayout l2 = new LinearLayout(Cart.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 0, 5, 0);


            final ImageView img_a = new ImageView(Cart.this);
            par21.setMargins(5, 5, 5, 5);
            img_a.setImageResource(str.get(ij).getImg_pro());
            img_a.setScaleType(ImageView.ScaleType.FIT_XY);
           // img_a.setBackgroundResource(R.drawable.background_gradiant);
            img_a.setPadding(5, 0, 5, 0);
            img_a.setLayoutParams(par21);
         /*   l4.addView(img_a);*/

            final TextView txt_name = new TextView(Cart.this);
            txt_name.setText(str.get(ij).getPro_name());
            txt_name.setTextColor(Color.parseColor("#000000"));
            txt_name.setTextSize(12);
            txt_name.setTypeface(null, Typeface.BOLD);
            l3.addView(txt_name);

            final TextView txt_name_pack = new TextView(Cart.this);
            txt_name_pack.setText("Pack of : 1");
            txt_name_pack.setTextColor(Color.parseColor("#000000"));
            txt_name_pack.setTextSize(12);
            txt_name_pack.setTypeface(null, Typeface.BOLD);
            l3.addView(txt_name_pack);

            final TextView txt_rs = new TextView(Cart.this);
            txt_rs.setText(str.get(ij).getPro_oprice());
            txt_rs.setTextColor(Color.parseColor("#000000"));
            txt_rs.setTextSize(12);
            txt_rs.setTypeface(null, Typeface.BOLD);
            l2.addView(txt_rs);

            final ImageView img_cross = new ImageView(Cart.this);
            img_cross.setImageResource(R.drawable.ic_content_clear);
            img_cross.setPadding(5, 0, 5, 0);
            img_cross.setLayoutParams(par22);
            l2.addView(img_cross);


            final EditText edt_qun = new EditText(Cart.this);
            edt_qun.setText("1");
            edt_qun.setTextColor(Color.parseColor("#000000"));
            edt_qun.setBackgroundResource(R.drawable.background_gradiant);
            edt_qun.setInputType(InputType.TYPE_CLASS_NUMBER);
            edt_qun.setPadding(20,20,20,20);
            edt_qun.setTextSize(12);
            l2.addView(edt_qun);

            final TextView txt_ors = new TextView(Cart.this);
            txt_ors.setText("="+str.get(ij).getPro_cprice());
            txt_ors.setTextColor(Color.parseColor("#00AA49"));
            txt_ors.setTextSize(12);
            txt_ors.setTypeface(null,Typeface.BOLD);
            l2.addView(txt_ors);

            l3.addView(l2);


            final ImageView img_delete = new ImageView(Cart.this);
            par23.gravity=Gravity.CENTER_VERTICAL;
            img_delete.setImageResource(R.drawable.ic_action_delete);
            img_delete.setPadding(5, 0, 5, 0);
            img_delete.setLayoutParams(par23);
            l5.addView(img_delete);

            lmain.addView(img_a);
            lmain.addView(l3);
            lmain.addView(l5);

            l_list.addView(lmain);

        }

    }

}
