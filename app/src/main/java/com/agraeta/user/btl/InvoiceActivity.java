package com.agraeta.user.btl;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.Distributor.DisSalesFormActivity;
import com.agraeta.user.btl.Distributor.DistributorActivity;

import java.util.ArrayList;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity {
    InvoiceAdapter invoiceAdapter;
    ListView list_invoice;
    //SwipeRefreshLayout refreshLayout;
    List<Bean_Order_history> invoiceList=new ArrayList<>();
    String jsonInvoice="";

    AppPrefs prefs;

    Toolbar toolbar;
    ImageView img_drawer,img_home,img_notification,img_cart,img_add;
    FrameLayout unread;
    TextView message_tv;
    String userID;
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
        setContentView(R.layout.activity_invoice);
        fetchIDs();
        setActionBar();
    }

    private void fetchIDs() {
        list_invoice=(ListView) findViewById(R.id.lsView_invoice);
//        refreshLayout=(SwipeRefreshLayout) findViewById(R.id.refreshView);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //new GetDisSalesData(InvoiceActivity.this,prefs.getUserId()).execute();
//            }
//        });

//        refreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//            }
//        });

        invoiceAdapter=new InvoiceAdapter(this, invoiceList , this);
        list_invoice.setAdapter(invoiceAdapter);
        //invoiceAdapter.setCallBack(this);
        list_invoice.setDivider(null);
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
        img_cart.setVisibility(View.GONE);
        img_home.setVisibility(View.VISIBLE);
        img_category.setVisibility(View.GONE);
        img_notification.setVisibility(View.GONE);

        ImageView img_add=(ImageView)mCustomView.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DisSalesFormActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FrameLayout frame = (FrameLayout)mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        TextView txt = (TextView)mCustomView.findViewById(R.id.menu_message_tv);
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
//                db= new DatabaseHandler(Product_List.this);
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
                Intent i = new Intent(InvoiceActivity.this,DistributorActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                finish();
            }
        });
//        img_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                Intent i = new Intent(Product_List.this, Main_CategoryPage.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app = new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
                Intent i = new Intent(InvoiceActivity.this, MainPage_drawer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
//        img_notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app = new AppPrefs(Product_List.this);
//                app.setUser_notification("product");
//                Intent i = new Intent(Product_List.this, Notification.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
//        img_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db= new DatabaseHandler(getApplicationContext());
//                db.Delete_ALL_table();
//                db.close();
//                app =new AppPrefs(Product_List.this);
//                app.setProduct_Sort("");
//                app.setFilter_Option("");
//                app.setFilter_SubCat("");
//                app.setFilter_Cat("");
//                app.setFilter_Sort("");
//                app = new AppPrefs(Product_List.this);
//                app.setUser_notification("product");
//                Intent i = new Intent(Product_List.this, BTL_Cart.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
}
