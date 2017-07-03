package com.agraeta.user.btl;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaitalee on 7/16/2016.
 */
public class Drawer_CategoryPage extends AppCompatActivity {
    ArrayList<Integer> list_data = new ArrayList<Integer>();
    ArrayList<String> list_data_name = new ArrayList<String>();
    int count = 0;
    LinearLayout lmain1,lmain2;
    LinearLayout.LayoutParams params;
    AppPrefs app;
    String Cat_ID =new String();
    String json =new String();
    ImageLoader imageloader;
    Custom_ProgressDialog loadingView;
    AppPrefs apps;
    ArrayList<Bean_category> category_arra=new ArrayList<Bean_category>();
  //  TextView title_b,title_c;
   // ImageView img_b,img_c;
    DatabaseHandler db;
    ArrayList<Bean_ProductCart> bean_cart = new ArrayList<Bean_ProductCart>();
    String back = new String();
    String owner_id = new String();
    String u_id = new String();
    String role_id = new String();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();
    String cartJSON="";
    boolean hasCartCallFinish=true;

    TextView txt;


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
        setContentView(R.layout.activity_main__category_page);

        Log.e("DrawerCategory", "true");

        // lmain1 = (LinearLayout)findViewById(R.id.grid_1);
        lmain2 = (LinearLayout)findViewById(R.id.grid_3);
        params = new LinearLayout.LayoutParams(android.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.app.ActionBar.LayoutParams.WRAP_CONTENT);

        imageloader = new ImageLoader(Drawer_CategoryPage.this);

        setActionBar();

        app = new AppPrefs(Drawer_CategoryPage.this);
        Cat_ID = app.getUser_CatId();
        //Log.e("CAT_Id", "" + Cat_ID);
        new Set_Category_Page().execute();

        //setLayout1(list_data);
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
        img_notification.setVisibility(View.GONE);
        ImageView img_cart = (ImageView) mCustomView.findViewById(R.id.img_cart);
        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
         txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
      /*  db = new DatabaseHandler(Drawer_CategoryPage.this);
        bean_cart =  db.Get_Product_Cart();
        db.close();
        if(bean_cart.size() == 0){
            txt.setVisibility(View.GONE);
            txt.setText("");
        }else{
            txt.setVisibility(View.VISIBLE);
            txt.setText(bean_cart.size()+"");
        }*/
        apps = new AppPrefs(Drawer_CategoryPage.this);
        String qun =apps.getCart_QTy();

        setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                 role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equals(C.ADMIN) || role_id.equals(C.COMP_SALES_PERSON) || role_id.equals(C.DISTRIBUTOR_SALES_PERSON)) {

                    app = new AppPrefs(Drawer_CategoryPage.this);
                     role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else{
                    u_id=owner_id;
                }


            }

            List<NameValuePair> para=new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id",u_id));

            new GetCartByQty(para).execute();



        }else{
            apps = new AppPrefs(Drawer_CategoryPage.this);
            apps.setCart_QTy("");
        }

        apps = new AppPrefs(Drawer_CategoryPage.this);
        String qu1 = apps.getCart_QTy();
        if(qu1.equalsIgnoreCase("0") || qu1.equalsIgnoreCase("")){
            txt.setVisibility(View.GONE);
            txt.setText("");
        }else{
            if(Integer.parseInt(qu1) > 999){
                txt.setText("999+");
                txt.setVisibility(View.VISIBLE);
            }else {
                txt.setText(qu1 + "");
                txt.setVisibility(View.VISIBLE);
            }
        }

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  if(Cat_ID.equalsIgnoreCase("0")) {
                    //app.setHas_Subcategroy(false);
                    //finish();
                    Intent i = new Intent(Drawer_CategoryPage.this, MainPage_drawer.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else{
                 *//*   app = new AppPrefs(Drawer_CategoryPage.this);
                    app.setUser_CatId("0");*//*
                    Intent i = new Intent(Drawer_CategoryPage.this, Drawer_CategoryPage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }*/
                if(Cat_ID.equalsIgnoreCase("0")){
                    app = new AppPrefs(Drawer_CategoryPage.this);
                    app.setUser_CatBack("");
                    Intent i = new Intent(Drawer_CategoryPage.this, MainPage_drawer.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else {
                    app = new AppPrefs(Drawer_CategoryPage.this);
                    if (app.getUser_CatBack().equalsIgnoreCase("")) {
                        new set_check_parent().execute();
                    } else {
                        app = new AppPrefs(Drawer_CategoryPage.this);
                        app.setUser_CatBack("");
                        Intent i = new Intent(Drawer_CategoryPage.this, MainPage_drawer.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
              /*  Intent i = new Intent(Drawer_CategoryPage.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(Drawer_CategoryPage.this);
                app.setUser_notification("category");
                Intent i = new Intent(Drawer_CategoryPage.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Drawer_CategoryPage.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(Drawer_CategoryPage.this);
                app.setUser_notification("category");
                Intent i = new Intent(Drawer_CategoryPage.this, Notification.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void setLayout1(ArrayList<Bean_category> str) {
        // TODO Auto-generated method stub
        lmain2.removeAllViews();
        for (int ij = 0; ij < str.size(); ij++) {
            int position =0;
            LinearLayout lmain = new LinearLayout(Drawer_CategoryPage.this);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            //lmain.setPadding(1, 1, 1, 1);
            lmain.setBackgroundColor(Color.parseColor("#f58634"));
            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, 200, 1.0f);

            LinearLayout.LayoutParams par22 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, 200, 1.0f);

            LinearLayout.LayoutParams par23 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par24 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 200);

            LinearLayout.LayoutParams pargrid = new LinearLayout.LayoutParams(
                    300, 300, 1.0f);
            LinearLayout l1 = new LinearLayout(Drawer_CategoryPage.this);
            l1.setLayoutParams(par1);

//            l1.setPadding(5, 0, 5, 0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            // l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(Drawer_CategoryPage.this);
            l3.setLayoutParams(pargrid);
            l3.setOrientation(LinearLayout.VERTICAL);
            pargrid.setMargins(0, 0, 0, 1);
            // par21.set
            l3.setLayoutParams(pargrid);
            l3.setGravity(Gravity.CENTER);
//            l3.setBackgroundResource(R.drawable.sticker_gradient);
            l3.setBackgroundColor(Color.parseColor("#FFFFFF"));

            LinearLayout l4 = new LinearLayout(Drawer_CategoryPage.this);
            l4.setLayoutParams(pargrid);
            l4.setOrientation(LinearLayout.VERTICAL);
            // pargrid.setMargins(1, 1, 1, 1);
            pargrid.setMargins(1, 0, 0, 1);
            l4.setLayoutParams(pargrid);
            l4.setGravity(Gravity.CENTER);
            //l4.setBackgroundResource(R.drawable.sticker_gradient);
            l4.setBackgroundColor(Color.parseColor("#FFFFFF"));

            LinearLayout l5 = new LinearLayout(Drawer_CategoryPage.this);
            l5.setLayoutParams(pargrid);
            l5.setOrientation(LinearLayout.VERTICAL);
            //pargrid.setMargins(1, 1, 1, 1);
            pargrid.setMargins(1, 0, 0, 1);
            l5.setLayoutParams(pargrid);
            l5.setGravity(Gravity.CENTER);
//            l5.setBackgroundResource(R.drawable.sticker_gradient);
            l5.setBackgroundColor(Color.parseColor("#FFFFFF"));

            final LinearLayout l2 = new LinearLayout(Drawer_CategoryPage.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 0, 5, 0);
            l2.setVisibility(View.GONE);

            count = ij;
            position=ij;
            final ImageView img_a1 = new ImageView(Drawer_CategoryPage.this);
            final ImageView img_b1 = new ImageView(Drawer_CategoryPage.this);
            final ImageView img_c1 = new ImageView(Drawer_CategoryPage.this);
            final TextView title = new TextView(Drawer_CategoryPage.this);
            final TextView title_b = new TextView(Drawer_CategoryPage.this);
            final TextView title_c = new TextView(Drawer_CategoryPage.this);



            if(count== str.size())
            {
                //img_a1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else {
                //img_a1 = new ImageView(MainPage_drawer.this);

               // imageloader.DisplayImage(str.get(count).getImg(), img_a1);
                Picasso.with(getApplicationContext())
                        .load(str.get(count).getImg())
                        .placeholder(R.drawable.btl_watermark)
                        .into(img_a1);

                img_a1.setLayoutParams(par24);
                img_a1.setPadding(10, 10, 10, 10);

                img_a1.setTag(str.get(count).getId());
                //Log.e("AAAAAAAA", "" + str.get(count).getId());
                l3.addView(img_a1);

                //title = new TextView(MainPage_drawer.this);
                title.setText(str.get(count).getName().toString());
                title.setLayoutParams(par23);
                title.setGravity(Gravity.CENTER);
                title.setTag(str.get(count).getIs_child());

                title.setTextSize(10);
                title.setTextColor(Color.parseColor("#f58634"));
                title.setPadding(10, 10, 10, 10);
                l3.addView(title);


                l1.addView(l3);

                count = count + 1;
                if (count == str.size()) {
                    // img_b1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {

                    //img_b1 = new ImageView(MainPage_drawer.this);
                   // imageloader.DisplayImage(str.get(count).getImg(),  img_b1);
                    Picasso.with(getApplicationContext())
                            .load(str.get(count).getImg())
                            .placeholder(R.drawable.btl_watermark)
                            .into(img_b1);

                    // img_b.setImageResource(str.get(count));
                    img_b1.setLayoutParams(par24);
                    img_b1.setPadding(10, 10, 10, 10);
                    img_b1.setTag(str.get(count).getId());
                    //Log.e("bbbbbbbbbbbbb", "" + str.get(count).getId());
                    l4.addView(img_b1);

                    //title_b = new TextView(MainPage_drawer.this);
                    title_b.setText(str.get(count).getName().toString());
                    title_b.setLayoutParams(par23);
                    title_b.setTextSize(10);
                    title_b.setTag(str.get(count).getIs_child());
                    title_b.setTextColor(Color.parseColor("#f58634"));
                    title_b.setGravity(Gravity.CENTER);
                    title_b.setPadding(10, 10, 10, 10);
                    l4.addView(title_b);


                    l1.addView(l4);

                    count = count + 1;
                    if (count == str.size()) {

                        // img_c1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    } else {
                        //img_c1 = new ImageView(MainPage_drawer.this);
                       // imageloader.DisplayImage( str.get(count).getImg(), img_c1);
                        Picasso.with(getApplicationContext())
                                .load(str.get(count).getImg())
                                .placeholder(R.drawable.btl_watermark)
                                .into(img_c1);

                        img_c1.setLayoutParams(par24);
                        img_c1.setPadding(10, 10, 10, 10);
                        img_c1.setTag(str.get(count).getId());
                        //Log.e("cccccccc", "" + str.get(count).getId());

                        l5.addView(img_c1);

                        //title_c = new TextView(MainPage_drawer.this);
                        title_c.setText(str.get(count).getName().toString());
                        title_c.setLayoutParams(par23);
                        title_c.setTextSize(10);
                        title_c.setTag(str.get(count).getIs_child());
                        title_c.setTextColor(Color.parseColor("#f58634"));
                        title_c.setGravity(Gravity.CENTER);
                        title_c.setPadding(10, 10, 10, 10);
                        l5.addView(title_c);
                        l1.addView(l5);
                    }
                }
            }

            ij = count;

            lmain.addView(l1);

            lmain2.addView(lmain);

            l3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String CatId = img_a1.getTag().toString();
                    String Cat_is_child = title.getTag().toString();
                    if (Cat_is_child.equalsIgnoreCase("0")) {
                        apps = new AppPrefs(Drawer_CategoryPage.this);
                        apps.setUser_notification("product");
                        apps.setUser_CatId(CatId);
                        apps.setPage_Data("1");
                        Intent i = new Intent(Drawer_CategoryPage.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        //Log.e("CATID", "" + CatId);
                        apps = new AppPrefs(Drawer_CategoryPage.this);
                        // apps.setUser_notification("product");
                        apps.setUser_CatId(CatId);
                        Intent i = new Intent(Drawer_CategoryPage.this, Drawer_CategoryPage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            });

            l4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String CatId = img_b1.getTag().toString();
                    String Cat_is_child = title_b.getTag().toString();
                    if (Cat_is_child.equalsIgnoreCase("0")) {
                        apps = new AppPrefs(Drawer_CategoryPage.this);
                        apps.setUser_notification("product");
                        apps.setUser_CatId(CatId);
                        apps.setPage_Data("1");
                        Intent i = new Intent(Drawer_CategoryPage.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        apps = new AppPrefs(Drawer_CategoryPage.this);
                        // apps.setUser_notification("product");
                        apps.setUser_CatId(CatId);
                        Intent i = new Intent(Drawer_CategoryPage.this, Drawer_CategoryPage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            });
            l5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String CatId = img_c1.getTag().toString();
                    String Cat_is_child = title_c.getTag().toString();
                    if (Cat_is_child.equalsIgnoreCase("0")) {
                        apps = new AppPrefs(Drawer_CategoryPage.this);
                        apps.setUser_notification("product");
                        apps.setUser_CatId(CatId);
                        apps.setPage_Data("1");
                        Intent i = new Intent(Drawer_CategoryPage.this, Product_List.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        apps = new AppPrefs(Drawer_CategoryPage.this);
                        // apps.setUser_notification("product");
                        apps.setUser_CatId(CatId);
                        Intent i = new Intent(Drawer_CategoryPage.this, Drawer_CategoryPage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            });


        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub

      /*  Intent i = new Intent(Drawer_CategoryPage.this, MainPage_drawer.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);*/
       /* if(Cat_ID.equalsIgnoreCase("0")) {
            //app.setHas_Subcategroy(false);
            //finish();
            Intent i = new Intent(Drawer_CategoryPage.this, MainPage_drawer.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }else{
                 *//*   app = new AppPrefs(Drawer_CategoryPage.this);
                    app.setUser_CatId("0");*//*
            Intent i = new Intent(Drawer_CategoryPage.this, Drawer_CategoryPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
*/
        if(Cat_ID.equalsIgnoreCase("0")){
            app = new AppPrefs(Drawer_CategoryPage.this);
            app.setUser_CatBack("");
            Intent i = new Intent(Drawer_CategoryPage.this, MainPage_drawer.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }else {
            app = new AppPrefs(Drawer_CategoryPage.this);
            if (app.getUser_CatBack().equalsIgnoreCase("")) {
                new set_check_parent().execute();
            } else {
                app = new AppPrefs(Drawer_CategoryPage.this);
                app.setUser_CatBack("");
                Intent i = new Intent(Drawer_CategoryPage.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }
    }

  /*  private void setLayout1(ArrayList<Integer> str) {
        // TODO Auto-generated method stub
        lmain2.removeAllViews();
        for (int ij = 0; ij < str.size(); ij++) {

            LinearLayout lmain = new LinearLayout(Drawer_CategoryPage.this);
            lmain.setLayoutParams(params);
            lmain.setOrientation(LinearLayout.HORIZONTAL);
            lmain.setPadding(2, 2, 2, 2);

            LinearLayout.LayoutParams par1 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par4 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par5 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams par21 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, 200, 1.0f);

            LinearLayout.LayoutParams par22 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT, 200, 1.0f);

            LinearLayout.LayoutParams par23 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT,
                    android.app.ActionBar.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams par24 = new LinearLayout.LayoutParams(
                    android.app.ActionBar.LayoutParams.MATCH_PARENT, 200);

            LinearLayout.LayoutParams pargrid = new LinearLayout.LayoutParams(
                    300, 300,1.0f);

            LinearLayout l1 = new LinearLayout(Drawer_CategoryPage.this);
            l1.setLayoutParams(par1);

            l1.setPadding(5, 0, 5, 0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            // l1.setGravity(Gravity.LEFT | Gravity.CENTER);

            LinearLayout l3 = new LinearLayout(Drawer_CategoryPage.this);
            l3.setLayoutParams(pargrid);
            l3.setOrientation(LinearLayout.VERTICAL);
            pargrid.setMargins(5, 5, 5, 5);
            l3.setLayoutParams(pargrid);
            l3.setGravity(Gravity.CENTER);
            l3.setBackgroundResource(R.drawable.sticker_bg);


            LinearLayout l4 = new LinearLayout(Drawer_CategoryPage.this);
            l4.setLayoutParams(pargrid);
            l4.setOrientation(LinearLayout.VERTICAL);
            pargrid.setMargins(5, 5, 5, 5);
            l4.setLayoutParams(pargrid);
            l4.setGravity(Gravity.CENTER);
            l4.setBackgroundResource(R.drawable.sticker_bg);





            LinearLayout l5 = new LinearLayout(Drawer_CategoryPage.this);
            l5.setLayoutParams(pargrid);
            l5.setOrientation(LinearLayout.VERTICAL);
            pargrid.setMargins(5, 5, 5, 5);
            l5.setLayoutParams(pargrid);
            l5.setGravity(Gravity.CENTER);
            l5.setBackgroundResource(R.drawable.sticker_bg);


            final LinearLayout l2 = new LinearLayout(Drawer_CategoryPage.this);
            l2.setLayoutParams(par2);
            l2.setGravity(Gravity.LEFT | Gravity.CENTER);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setPadding(5, 0, 5, 0);
            l2.setVisibility(View.GONE);

            count = ij;
            final ImageView img_a = new ImageView(Drawer_CategoryPage.this);
            img_a.setImageResource(str.get(count));
            img_a.setLayoutParams(par24);
            img_a.setPadding(10, 10, 10, 10);

            l3.addView(img_a);

            final TextView title = new TextView(Drawer_CategoryPage.this);
            title.setText(list_data_name.get(count));
            title.setLayoutParams(par23);
            title.setGravity(Gravity.CENTER);
            title.setTextSize(10);
            title.setPadding(10, 10, 10, 10);
            l3.addView(title);



            l1.addView(l3);

            count = count + 1;
            final ImageView img_b = new ImageView(Drawer_CategoryPage.this);
            img_b.setImageResource(str.get(count));
            img_b.setLayoutParams(par24);
            img_b.setPadding(10, 10, 10, 10);

            l4.addView(img_b);

            final TextView title_b = new TextView(Drawer_CategoryPage.this);
            title_b.setText(list_data_name.get(count));
            title_b.setLayoutParams(par23);
            title_b.setTextSize(10);
            title_b.setGravity(Gravity.CENTER);
            title_b.setPadding(10, 10, 10, 10);
            l4.addView(title_b);


            l1.addView(l4);

            count = count + 1;
            final ImageView img_c = new ImageView(Drawer_CategoryPage.this);
            img_c.setImageResource(str.get(count));
            img_c.setLayoutParams(par24);
            img_c.setPadding(10, 10, 10, 10);

            l5.addView(img_c);

            final TextView title_c = new TextView(Drawer_CategoryPage.this);
            title_c.setText(list_data_name.get(count));
            title_c.setLayoutParams(par23);
            title_c.setTextSize(10);
            title_c.setGravity(Gravity.CENTER);
            title_c.setPadding(10, 10, 10, 10);
            l5.addView(title_c);


            l1.addView(l5);

            ij = count;
			*//*
             * final ImageView img_b = new ImageView(MainActivity.this);
			 * img_b.setImageResource(R.drawable.slidingfitting);
			 * img_b.setBackgroundResource(R.drawable.background_gradiant);
			 * img_b.setPadding(10, 10, 10, 10); img_b.setLayoutParams(par21);
			 * par21.setMargins(5, 5, 5, 5); img_b.setLayoutParams(par21);
			 * l1.addView(img_b);
			 *
			 * final ImageView img_c = new ImageView(MainActivity.this);
			 * img_c.setBackgroundResource(R.drawable.background_gradiant);
			 * img_c.setImageResource(R.drawable.doorfitting);
			 * img_c.setPadding(10, 10, 10, 10); img_c.setLayoutParams(par21);
			 * par21.setMargins(5, 5, 5, 5); img_c.setLayoutParams(par21);
			 * l1.addView(img_c);
			 *//*

            lmain.addView(l1);

            lmain2.addView(lmain);


            l3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Drawer_CategoryPage.this, Product_List.class);
                    startActivity(i);
                }
            });

            l4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Drawer_CategoryPage.this,Product_List.class);
                    startActivity(i);
                }
            });
            l5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Drawer_CategoryPage.this,Product_List.class);
                    startActivity(i);
                }
            });


        }

    }*/

    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(Drawer_CategoryPage.this);

        ArrayList<Bean_User_data> user_array_from_db = db.Get_Contact();

        //Toast.makeText(getApplicationContext(), ""+category_array_from_db.size(), Toast.LENGTH_LONG).show();

        for (int i = 0; i < user_array_from_db.size(); i++) {

            int uid =user_array_from_db.get(i).getId();
            String user_id =user_array_from_db.get(i).getUser_id();
            String email_id = user_array_from_db.get(i).getEmail_id();
            String phone_no =user_array_from_db.get(i).getPhone_no();
            String f_name = user_array_from_db.get(i).getF_name();
            String l_name = user_array_from_db.get(i).getL_name();
            String password = user_array_from_db.get(i).getPassword();
            String gender = user_array_from_db.get(i).getGender();
            String usertype = user_array_from_db.get(i).getUser_type();
            String login_with = user_array_from_db.get(i).getLogin_with();
            String str_rid = user_array_from_db.get(i).getStr_rid();
            String add1 = user_array_from_db.get(i).getAdd1();
            String add2 = user_array_from_db.get(i).getAdd2();
            String add3 = user_array_from_db.get(i).getAdd3();
            String landmark = user_array_from_db.get(i).getLandmark();
            String pincode = user_array_from_db.get(i).getPincode();
            String state_id = user_array_from_db.get(i).getState_id();
            String state_name = user_array_from_db.get(i).getState_name();
            String city_id = user_array_from_db.get(i).getCity_id();
            String city_name = user_array_from_db.get(i).getCity_name();
            String str_response = user_array_from_db.get(i).getStr_response();


            Bean_User_data contact = new Bean_User_data();
            contact.setId(uid);
            contact.setUser_id(user_id);
            contact.setEmail_id(email_id);
            contact.setPhone_no(phone_no);
            contact.setF_name(f_name);
            contact.setL_name(l_name);
            contact.setPassword(password);
            contact.setGender(gender);
            contact.setUser_type(usertype);
            contact.setLogin_with(login_with);
            contact.setStr_rid(str_rid);
            contact.setAdd1(add1);
            contact.setAdd2(add2);
            contact.setAdd3(add3);
            contact.setLandmark(landmark);
            contact.setPincode(pincode);
            contact.setState_id(state_id);
            contact.setState_name(state_name);
            contact.setCity_id(city_id);
            contact.setCity_name(city_name);
            contact.setStr_response(str_response);
            user_data.add(contact);


        }
        db.close();
    }

    public String GetCartByQty(final List<NameValuePair> params){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty",ServiceHandler.POST,params);

                    //System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();
        while (notDone[0]){

        }
        //Log.e("my json",json[0]);
        return json[0];
    }

    public class Set_Category_Page extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Drawer_CategoryPage.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("parent_id", Cat_ID));


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Category/App_GetCategory", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("null")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Drawer_CategoryPage.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Drawer_CategoryPage.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        category_arra.clear();

                        try {

                            JSONArray joj = jObj.getJSONArray("data");


                            for (int z = 0; z < joj.length(); z++) {

                                JSONObject j = joj.getJSONObject(z);


                                Bean_category bean = new Bean_category();
                                bean.setId(j.getString("id"));
                                bean.setName(j.getString("name"));
                                bean.setIs_child(j.getString("is_child"));
                                bean.setImg(j.getString("app_image"));
                                category_arra.add(bean);


                            }
                            //Log.e("1122","1122"+category_arra.size());

                            if (category_arra.size() == 0) {
                                Globals.CustomToast(Drawer_CategoryPage.this, "No Data", getLayoutInflater());
                            } else {

                                setLayout1(category_arra);
                            }


                        } catch (Exception e) {
                        }

                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class set_check_parent extends AsyncTask<Void, Void, String> {
        public StringBuilder sb;
        boolean status;
        private String result;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Drawer_CategoryPage.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("cat_id", Cat_ID));


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "Category/App_Get_Parent_Category", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                //System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("error1: " + e.toString());

                return json;

            }
//            //Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                //System.out.println(result_1);

                if (result_1.equalsIgnoreCase("null")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(Drawer_CategoryPage.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Toast.makeText(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(Drawer_CategoryPage.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        category_arra.clear();

                        try {

                            JSONArray joj = jObj.getJSONArray("data");


                            for (int z = 0; z < joj.length(); z++) {

                                JSONObject j = joj.getJSONObject(z);


                                back = j.getString("parent_id");


                            }
                            //Log.e("back","back"+back);

                            if (back.equalsIgnoreCase("0")) {
                                //Globals.CustomToast(Main_CategoryPage.this,"No Data",getLayoutInflater());
                                app = new AppPrefs(Drawer_CategoryPage.this);
                                app.setUser_CatId("0");
                                app.setUser_CatBack("1");
                                Intent i = new Intent(Drawer_CategoryPage.this, Drawer_CategoryPage.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            } else {

                                //setLayout1(category_arra);
                                app = new AppPrefs(Drawer_CategoryPage.this);
                                app.setUser_CatId(back);
                                Intent i = new Intent(Drawer_CategoryPage.this, Drawer_CategoryPage.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }

                        } catch (Exception e) {
                        }

                        loadingView.dismiss();


                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public class GetCartByQty extends AsyncTask<Void, Void, String>{

        List<NameValuePair> params=new ArrayList<>();

        public GetCartByQty(List<NameValuePair> params){
            hasCartCallFinish=true;
            this.params=params;
        }

        @Override
        protected String doInBackground(Void... param) {



            Globals.generateNoteOnSD(getApplicationContext(),"CartData/App_GetCartQty"+"\n"+params.toString());

            String json=new ServiceHandler().makeServiceCall(Globals.server_link + "CartData/App_GetCartQty",ServiceHandler.POST,params);

            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            cartJSON=json;
            Globals.generateNoteOnSD(getApplicationContext(),cartJSON);
            try {


                //System.out.println(json);

                if (json==null
                        || (json.equalsIgnoreCase(""))) {

                    Globals.CustomToast(Drawer_CategoryPage.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();
                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(Drawer_CategoryPage.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(Drawer_CategoryPage.this);
                        app.setCart_QTy(""+qu);


                    }

                }


            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

            String qu1 = app.getCart_QTy();
            if(qu1.equalsIgnoreCase("0") || qu1.equalsIgnoreCase("")){
                txt.setVisibility(View.GONE);
                txt.setText("");
            }else{
                if(Integer.parseInt(qu1) > 999){
                    txt.setText("999+");
                    txt.setVisibility(View.VISIBLE);
                }else {
                    txt.setText(qu1 + "");
                    txt.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}

