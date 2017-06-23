package com.agraeta.user.btl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class pro_list extends BaseAdapter {
    ArrayList<Integer> image_product = new ArrayList<Integer>();
   // ArrayList<String> title = new ArrayList<String>();

   /* String[] title_pname,p_mrp,p_prize,p_del_prize,p_no,p_margin;*/
    Context context;
    String[] pro_name,  pro_delar_prize,  pro_margin,  pro_mrp,  pro_prize,  prod_no;
String count = new String();
    Dialog dialog;
    int s =0;
    //ImageLoader imageloader;
    LayoutInflater layoutinflater;
    int position1;
    ImageView im;
    TextView tv_pname,tv_mrgn,tc_prize,tv_del_prize,tv_no,tv_mrp;
    Button btn_buynow;
    Activity activity;
    ImageView minuss,plus;
    Button buy_cart,cancel;
    EditText edt_count;
    TextView p_prize1;

/*    public pro_list(Context context,ArrayList<Integer> image_url, String[] title_pname, String[] p_mrp, String[] p_prize, String[] p_del_prize, String[] p_no, String[] p_margin, LayoutInflater layoutinflater, Activity activity) {
        this.image_url = image_url;
        this.title_pname = title_pname;
        this.p_mrp = p_mrp;
        this.p_prize = p_prize;
        this.p_del_prize = p_del_prize;
        this.p_no = p_no;
        this.p_margin = p_margin;
        this.context = context;
        this.layoutinflater = layoutinflater;

    }*/
    public pro_list(Context context, ArrayList<Integer> image_product, String[] pro_name, String[] pro_delar_prize, String[] pro_margin, String[] pro_mrp, String[] pro_prize, String[] prod_no, LayoutInflater layoutInflater, Activity activity) {


        this.activity=activity;
        this.context = context;
        this.image_product = image_product;
        this.pro_name = pro_name;
        this.pro_delar_prize = pro_delar_prize;
        this.pro_margin = pro_margin;
        this.pro_mrp = pro_mrp;
        this.prod_no = prod_no;
        this.pro_prize = pro_prize;
        this.layoutinflater=layoutInflater;

    }

    @Override
    public int getCount() {
        return image_product.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        position1 = position;
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
       // display_width = (metrics.widthPixels / 3);
if(convertView==null) {

    convertView = layoutinflater.inflate(R.layout.product_list_single, null);
}

      /*  im=(ImageView)convertView.findViewById(R.id.imageView2);
        tv_pname=(TextView)convertView.findViewById(R.id.Title);
        tv_mrgn =(TextView)convertView.findViewById(R.id.p_mrgn);
        tc_prize=(TextView)convertView.findViewById(R.id.p_prize);
        tv_mrp=(TextView)convertView.findViewById(R.id.p_mrp);
        tv_del_prize=(TextView)convertView.findViewById(R.id.p_del_prize);
        tv_no=(TextView)convertView.findViewById(R.id.p_no);
        btn_buynow=(Button)convertView.findViewById(R.id.btn_buynow);*/



      /*  im.setImageResource(image_product.get(position));
        tv_pname.setText(pro_name[position]);
        tv_mrgn.setText(pro_margin[position]);
        tc_prize.setText(pro_prize[position]);
      //  tc_prize.setTag(pro_prize[position].toString());
        tv_mrp.setText(pro_mrp[position]);

        tv_no.setText(prod_no[position]);
        tv_del_prize.setText(pro_delar_prize[position]);

        btn_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(activity);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                dialog.setCanceledOnTouchOutside(false);
                wlp.gravity = Gravity.BOTTOM;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_cart);
                minuss = (ImageView)dialog.findViewById(R.id.minus);
                plus =(ImageView)dialog.findViewById(R.id.plus);
                buy_cart=(Button)dialog.findViewById(R.id.Btn_Buy_online11);
                cancel=(Button)dialog.findViewById(R.id.cancel);
                edt_count=(EditText)dialog.findViewById(R.id.edt_count);
                edt_count.setSelection(edt_count.getText().length());

                p_prize1=(TextView)dialog.findViewById(R.id.p_prize11);
                String ss = tc_prize.getText().toString();
                p_prize1.setText(ss);

                dialog.show();


                minuss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(s==0)
                        {

                            Toast.makeText(activity,"Its impossible",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {

                            s = s - 1;
                            edt_count.setText(String.valueOf(s));


                            // Toast.makeText(activity,"Total "+to,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                String c=edt_count.getText().toString();
                s=Integer.parseInt(c);
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s = s + 1;
                        edt_count.setText(String.valueOf(s));

                        // Toast.makeText(getApplicationContext(),"Total "+to,Toast.LENGTH_SHORT).show();
                    }
                });

                buy_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(activity, "Item insert in cart", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });


        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, BTLProdcut_Detailpage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(i);
            }
        });*/



        return convertView;
    }
}
