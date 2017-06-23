package com.agraeta.user.btl.CompanySalesPerson;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.Distributor.DisSalesFormActivity;
import com.agraeta.user.btl.FullZoomImageViewActivity;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SkipOrderInformation extends AppCompatActivity {

TextView txt_skipname, txt_username, txt_reasontitle, txt_comment, txt_date;
    ImageView img1, img2;

    Bean_SkipIcon skipIconList;

    LinearLayout lnr_att1,lnr_att2;

    Toolbar toolbar;
    ImageView img_drawer,img_logout;
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
        setContentView(R.layout.activity_skip_order_information);

        Intent intent=getIntent();
        skipIconList=(Bean_SkipIcon) intent.getSerializableExtra("skipOrder");

        fetchID();
        setActionBar();
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
        ImageView img_pin = (ImageView) mCustomView.findViewById(R.id.img_pin);
        img_pin.setVisibility(View.GONE);

        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        img_cart.setVisibility(View.GONE);
        img_home.setVisibility(View.GONE);
        img_category.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);

        ImageView img_add = (ImageView) mCustomView.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisSalesFormActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView img_skip = (ImageView) mCustomView.findViewById(R.id.image_skip);
        img_skip.setVisibility(View.GONE);
        img_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), S_SkipListIconActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView img_logout = (ImageView) mCustomView.findViewById(R.id.image_logout);
        img_logout.setVisibility(View.GONE);


        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        TextView txt = (TextView) mCustomView.findViewById(R.id.menu_message_tv);
//        db = new DatabaseHandler(Product_List.this);
//        bean_cart =  db.Get_Product_Cart();
//        db.close();
//        if(bean_cart.size() == 0){
//            txt.setVisibility(View.GONE);
//            txt.setText("");
//        }else{
//            txt.setVisibility(View.VISIBLE);
//            txt.setText(bean_cart.size()+"");
//        }

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fetchID() {
        txt_skipname=(TextView) findViewById(R.id.txt_skipperson);
        txt_username=(TextView) findViewById(R.id.txt_username);
        txt_reasontitle=(TextView) findViewById(R.id.txt_reasontitle);
        txt_comment=(TextView) findViewById(R.id.txt_comment);
        txt_date=(TextView) findViewById(R.id.txt_date);

        img1=(ImageView) findViewById(R.id.img1);
        img2=(ImageView) findViewById(R.id.img2);

        lnr_att1=(LinearLayout) findViewById(R.id.lnr_att1);
        lnr_att2=(LinearLayout) findViewById(R.id.lnr_att2);

        txt_skipname.setText(skipIconList.getSkipForFname()+" "+skipIconList.getSkipForLname());
        txt_username.setText(skipIconList.getSkipByFname()+" "+skipIconList.getSkipByLname());

        txt_date.setText(skipIconList.getCreated());

        if(!skipIconList.getReason_title().toLowerCase().contains("Other".toLowerCase()))
            txt_reasontitle.setText(skipIconList.getReason_title());
        else
            txt_reasontitle.setText(skipIconList.getOther_reason_title().toString());

//        int height_in_pixels = txt_reasontitle.getLineCount() * txt_reasontitle.getLineHeight(); //approx height text
//        txt_reasontitle.setHeight(height_in_pixels);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imagePaths=new ArrayList<String>();

                if(!skipIconList.getAttachment_1().isEmpty() && !skipIconList.getAttachment_1().equals("null"))
                    imagePaths.add(skipIconList.getAttachment_1());
                if(!skipIconList.getAttachment_2().isEmpty() && !skipIconList.getAttachment_2().equals("null"))
                    imagePaths.add(skipIconList.getAttachment_2());

                Intent intent=new Intent(getApplicationContext(), FullZoomImageViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putStringArrayListExtra("imagePaths",imagePaths);
                intent.putExtra("index",0);
                startActivity(intent);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imagePaths=new ArrayList<String>();

                if(!skipIconList.getAttachment_1().isEmpty() && !skipIconList.getAttachment_1().equals("null"))
                    imagePaths.add(skipIconList.getAttachment_1());
                if(!skipIconList.getAttachment_2().isEmpty() && !skipIconList.getAttachment_2().equals("null"))
                    imagePaths.add(skipIconList.getAttachment_2());

                Intent intent=new Intent(getApplicationContext(), FullZoomImageViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putStringArrayListExtra("imagePaths",imagePaths);
                intent.putExtra("index",1);
                startActivity(intent);
            }
        });


        if(!skipIconList.getComment().equalsIgnoreCase("null") && !skipIconList.getComment().equalsIgnoreCase(""))
            txt_comment.setText(skipIconList.getComment().toString());

//        int height_in_pixels1 = txt_comment.getLineCount() * txt_comment.getLineHeight(); //approx height text
//        txt_comment.setHeight(height_in_pixels1);



        if(!skipIconList.getAttachment_1().equalsIgnoreCase("") && !skipIconList.getAttachment_1().equalsIgnoreCase("null")){
            //Log.e("attach 1",skipIconList.getAttachment_1());
            Picasso.with(getApplicationContext())
                    .load(Globals.server_link+skipIconList.getAttachment_1())
                    .placeholder(R.drawable.btl_watermark)
                    .into(img1);
        }
        else {
            lnr_att1.setVisibility(View.GONE);
        }

        if(!skipIconList.getAttachment_2().equalsIgnoreCase("") && !skipIconList.getAttachment_2().equalsIgnoreCase("null")){
            //Log.e("attach 2",skipIconList.getAttachment_2());
            Picasso.with(getApplicationContext())
                    .load(Globals.server_link+skipIconList.getAttachment_2())
                    .placeholder(R.drawable.btl_watermark)
                    .into(img2);
        }
        else {
            lnr_att2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
