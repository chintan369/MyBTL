package com.agraeta.user.btl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductPage extends AppCompatActivity {
    public static int display_width = 0;
    GridView gridView_product;
    ArrayList<Integer> Categoryimg = new ArrayList<Integer>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> original_price = new ArrayList<String>();
    AppPrefs app;
    DatabaseHandler db;
    String owner_id = new String();
    String u_id = new String();
    String role_id = new String();
    ArrayList<Bean_User_data> user_data = new ArrayList<Bean_User_data>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        setActionBar();
        Categoryimg.add(R.drawable.door);
        Categoryimg.add(R.drawable.door_new);
        Categoryimg.add(R.drawable.door);
        Categoryimg.add(R.drawable.door_new);


        name.add("Door");
        name.add("Door1");
        name.add("Door2");
        name.add("Door3");


        price.add("Rs.50");
        price.add("Rs.34");
        price.add("Rs.78");
        price.add("Rs.80");

        original_price.add("Rs.50");
        original_price.add("Rs.34");
        original_price.add("Rs.78");
        original_price.add("Rs.80");



        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);


        display_width = (metrics.widthPixels / 2);

        gridView_product = (GridView) findViewById(R.id.gridView_product);
        gridView_product.setAdapter(new ProductAdapter(ProductPage.this, Categoryimg,name,price,original_price,
                getLayoutInflater(),
                ProductPage.this));


        gridView_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent i = new Intent(ProductPage.this, Product_DetailPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
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

        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.VISIBLE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
        img_notification.setVisibility(View.GONE);
        app = new AppPrefs(ProductPage.this);
        String qun =app.getCart_QTy();

       /* setRefershData();

        if(user_data.size() != 0){
            for (int i = 0; i < user_data.size(); i++) {

                owner_id =user_data.get(i).getUser_id().toString();

                role_id=user_data.get(i).getUser_type().toString();

                if (role_id.equalsIgnoreCase("6") ) {

                    app = new AppPrefs(ProductPage.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                }else if(role_id.equalsIgnoreCase("7")){
                    app = new AppPrefs(ProductPage.this);
                    role_id = app.getSubSalesId().toString();
                    u_id = app.getSalesPersonId().toString();
                    //Log.e("IDIDD",""+app.getSalesPersonId().toString());
                }else{
                    u_id=owner_id;
                }


            }

            List<NameValuePair> para=new ArrayList<NameValuePair>();

            para.add(new BasicNameValuePair("owner_id", owner_id));
            para.add(new BasicNameValuePair("user_id",u_id));


            String json=GetCartByQty(para);

            try {


                //System.out.println(json);

                if (json==null
                        || (json.equalsIgnoreCase(""))) {
                    *//*Toast.makeText(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*//*
                    Globals.CustomToast(ProductPage.this, "SERVER ERRER", getLayoutInflater());
                    // loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(json);

                    String date = jObj.getString("status");

                    if (date.equalsIgnoreCase("false")) {

                        app = new AppPrefs(ProductPage.this);
                        app.setCart_QTy("0");

                        // loadingView.dismiss();
                    } else {


                        int qu = jObj.getInt("data");
                        app = new AppPrefs(ProductPage.this);
                        app.setCart_QTy(""+qu);


                    }

                }
            }catch(Exception j){
                j.printStackTrace();
                //Log.e("json exce",j.getMessage());
            }

        }else{
            app = new AppPrefs(ProductPage.this);
            app.setCart_QTy("");
        }*/

        app = new AppPrefs(ProductPage.this);
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


        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductPage.this, Main_CategoryPage.class);
                startActivity(i);
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductPage.this,Notification.class);
                startActivity(i);
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(ProductPage.this);
                app.setUser_notification("ProductPage");
                Intent i = new Intent(ProductPage.this, BTL_Cart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public class ProductAdapter extends BaseAdapter {

        ArrayList<Integer> image_url = new ArrayList<Integer>();
        ArrayList<String> name1 = new ArrayList<String>();
        ArrayList<String> price1 = new ArrayList<String>();
        ArrayList<String> original_price1 = new ArrayList<String>();
        ArrayList<String> title = new ArrayList<String>();
        Context context;


        LayoutInflater layoutinflater;
        int position1;
        ImageView im;
        TextView tv,tv_name,tv_prodct_price,tv_prodct_original_price;
        Activity activity;
        int display_width;
        LinearLayout framelayout_image;

	/*public ImageAdapter(Context context, ArrayList<String> categoryimg,
            ArrayList<String> title, LayoutInflater layoutinflater,
			Activity activity) {

		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.context = context;
		this.image_url = categoryimg;
		this.title = title;
		this.layoutinflater = layoutinflater;

	}*/

        public ProductAdapter(Context context, ArrayList<Integer> categoryimg,ArrayList<String> name_n,ArrayList<String> price_n,ArrayList<String> original_price_n, LayoutInflater layoutinflater,
                              Activity activity) {

            // TODO Auto-generated constructor stub
            this.activity = activity;
            this.context = context;
            this.image_url = categoryimg;
            this.name1 = name_n;
            this.price1 = price_n;
            this.original_price1 = original_price_n;
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
            display_width = (metrics.widthPixels / 2);
            if (convertView == null) {

                convertView = layoutinflater.inflate(R.layout.grid_product_row, null);

            }

          /*  framelayout_image = (LinearLayout) convertView
                    .findViewById(R.id.framelayout_image);
            framelayout_image.getLayoutParams().height = display_width;*/
           /// framelayout_image.getLayoutParams().width = display_width;

            im = (ImageView) convertView.findViewById(R.id.imageView1);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);

            tv_prodct_price = (TextView) convertView.findViewById(R.id.tv_prodct_price);

            tv_prodct_original_price = (TextView) convertView.findViewById(R.id.tv_prodct_original_price);


            // im.getLayoutParams().height = display_width;
            // im.getLayoutParams().width = display_width;
		/*tv = (TextView) convertView.findViewById(R.id.tv_name);
		tv.setText(title.get(position));
*/          tv_name.setText(name.get(position));
            //Log.e("name", name.get(position));
            tv_prodct_price.setText(price.get(position));
            //Log.e("price", price.get(position));

            tv_prodct_original_price.setText(original_price.get(position));
            //Log.e("original_price", original_price.get(position));
            im.setImageResource(image_url.get(position));

            return convertView;
        }


    }
    private void setRefershData() {
        // TODO Auto-generated method stub
        user_data.clear();
        db = new DatabaseHandler(ProductPage.this);

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
                    //  System.out.println("error1: " + e.toString());
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
}
