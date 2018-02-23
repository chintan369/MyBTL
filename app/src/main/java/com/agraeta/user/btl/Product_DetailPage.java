package com.agraeta.user.btl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Product_DetailPage extends AppCompatActivity {


    public static int display_width = 0;
    Button btn_buynow_productdetail,btn_addtocart_productdetail;
    ListView lst_prodct_grid_img;
    ImageView img_Prodct;
    ArrayList<Integer> Categoryimg = new ArrayList<Integer>();

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
        setContentView(R.layout.activity_product__detail_page);


        Log.e("Yes i am at right place", "--->");


        fetchId();
        Categoryimg.add(R.drawable.door);
        Categoryimg.add(R.drawable.door_new);
        Categoryimg.add(R.drawable.door);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);


        display_width = (metrics.widthPixels / 2);


        lst_prodct_grid_img.setAdapter(new ProductAdapter(Product_DetailPage.this, Categoryimg,
                getLayoutInflater(),
                Product_DetailPage.this));


        btn_addtocart_productdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Product_DetailPage.this,Cart.class);
                startActivity(i);
            }
        });

    }
    private void fetchId()
    {
        lst_prodct_grid_img=(ListView)findViewById(R.id.lst_prodct_grid_img);
        img_Prodct=(ImageView)findViewById(R.id.img_Prodct);

        btn_buynow_productdetail=(Button)findViewById(R.id.btn_buynow_productdetail);
        btn_addtocart_productdetail=(Button)findViewById(R.id.btn_addtocart_productdetail);

    }

    public class ProductAdapter extends BaseAdapter {

        ArrayList<Integer> image_url = new ArrayList<Integer>();

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

        public ProductAdapter(Context context, ArrayList<Integer> categoryimg, LayoutInflater layoutinflater,
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
            display_width = (metrics.widthPixels / 2);
            if (convertView == null) {

                convertView = layoutinflater.inflate(R.layout.listview_image_row, null);

            }

          /*  framelayout_image = (LinearLayout) convertView
                    .findViewById(R.id.framelayout_image);
            framelayout_image.getLayoutParams().height = display_width;*/
            /// framelayout_image.getLayoutParams().width = display_width;

            im = (ImageView) convertView.findViewById(R.id.imageView1);



            // im.getLayoutParams().height = display_width;
            // im.getLayoutParams().width = display_width;
		/*tv = (TextView) convertView.findViewById(R.id.tv_name);
		tv.setText(title.get(position));
*/
            im.setImageResource(image_url.get(position));

            return convertView;
        }


    }

}
