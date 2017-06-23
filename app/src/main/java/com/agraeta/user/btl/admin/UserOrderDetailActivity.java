package com.agraeta.user.btl.admin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.Bean_Product;
import com.agraeta.user.btl.Bean_ProductCart;
import com.agraeta.user.btl.Bean_ProductOprtion;
import com.agraeta.user.btl.Bean_Schme_value;
import com.agraeta.user.btl.C;
import com.agraeta.user.btl.CheckoutPage_Product;
import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.LogInPage;
import com.agraeta.user.btl.Order_History_Details;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.OrderDetailData;
import com.agraeta.user.btl.model.OrderListItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserOrderDetailActivity extends AppCompatActivity implements Callback<OrderDetailData> {

    private String orderID = "";

    TextView txt_orderID, txt_orderDate, txt_orderStatus;
    TextView txt_name_billing, txt_phone_billing, txt_addr_billing;
    TextView txt_name_delivery, txt_phone_delivery, txt_addr_delivery;
    TextView txt_totalProducts, txt_subTotal, txt_totalAmount, txt_discountAmount;
    LinearLayout layout_products, layout_discount;

    ImageView option_icon;

    Call<OrderDetailData> orderDetailDataCall;

    OrderDetailData detailData;

    private Custom_ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_detail);

        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");

        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {
        progressDialog = new Custom_ProgressDialog(this, "Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        txt_orderStatus = (TextView) findViewById(R.id.txt_orderStatus);
        txt_orderDate = (TextView) findViewById(R.id.txt_orderDate);
        txt_orderID = (TextView) findViewById(R.id.txt_orderID);
        txt_name_billing = (TextView) findViewById(R.id.txt_name_billing);
        txt_phone_billing = (TextView) findViewById(R.id.txt_phone_billing);
        txt_addr_billing = (TextView) findViewById(R.id.txt_addr_billing);
        txt_name_delivery = (TextView) findViewById(R.id.txt_name_delivery);
        txt_phone_delivery = (TextView) findViewById(R.id.txt_phone_delivery);
        txt_addr_delivery = (TextView) findViewById(R.id.txt_addr_delivery);
        txt_totalProducts = (TextView) findViewById(R.id.txt_totalProducts);
        txt_subTotal = (TextView) findViewById(R.id.txt_subTotal);
        txt_discountAmount = (TextView) findViewById(R.id.txt_discountAmount);
        txt_totalAmount = (TextView) findViewById(R.id.txt_totalAmount);

        option_icon=(ImageView) findViewById(R.id.option_icon);

        layout_products = (LinearLayout) findViewById(R.id.layout_products);
        layout_discount = (LinearLayout) findViewById(R.id.layout_discount);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.server_link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AdminAPI adminAPI = retrofit.create(AdminAPI.class);

        orderDetailDataCall = adminAPI.orderDetailData(orderID);
        orderDetailDataCall.enqueue(this);

        option_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(UserOrderDetailActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_order, popupMenu.getMenu());
                popupMenu.setGravity(Gravity.CENTER);

                for(int i=0; i<popupMenu.getMenu().size(); i++){
                    if(popupMenu.getMenu().getItem(i).getItemId()==R.id.order_enquiry){
                        popupMenu.getMenu().getItem(i).setTitle("Order PDF");
                        break;
                    }
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.more_info:
                                showOrderTracking();
                                break;
                            case R.id.order_enquiry:
                                showOrderPDF();
                                break;
                            case R.id.order_enquiry_history:
                                showOrderEnquiryHistory();
                                break;
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }

    private void showOrderPDF() {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");
        intent.setData(Uri.parse(Globals.server_link+detailData.getOrderDetail().getOrder().getOrderPDF()));
        startActivity(intent);
    }

    private void showOrderEnquiryHistory() {
        try{
            Intent intent=new Intent(getApplicationContext(),UserOrderEnquiryHistoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("orderID",detailData.getOrderDetail().getOrder().getId());
            startActivity(intent);
        }catch (Exception e){
            Globals.showError(e.getCause(),getApplicationContext());
        }
    }

    private void showOrderTracking() {
        try{
            Intent intent=new Intent(getApplicationContext(),OrderTrackingHistoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("orderID",detailData.getOrderDetail().getOrder().getId());
            intent.putExtra("comment",detailData.getOrderDetail().getOrder().getComment());
            intent.putExtra("attachmentPath",detailData.getOrderDetail().getOrder().getAttchmentPath());
            startActivity(intent);
        }catch (Exception e){
            Globals.showError(e.getCause(),getApplicationContext());
        }
    }

    @Override
    public void onResponse(Call<OrderDetailData> call, Response<OrderDetailData> response) {
        progressDialog.dismiss();
        detailData = response.body();

        if (detailData.isStatus()) {
            setData(detailData.getOrderDetail());
        } else {
            Globals.Toast2(getApplicationContext(), detailData.getMessage());
        }
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        img_notification.setVisibility(View.GONE);
        FrameLayout frame = (FrameLayout) mCustomView.findViewById(R.id.unread);
        frame.setVisibility(View.GONE);
        image_drawer.setImageResource(R.drawable.ic_action_btl_back);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_home.setVisibility(View.GONE);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void setData(OrderDetailData.OrderDetail orderDetail) {
        txt_orderID.setText("Order ID #" + orderDetail.getOrder().getId());
        txt_orderDate.setText(orderDetail.getOrder().getOrderDate("dd-MM-yyyy", "dd MMM yyyy"));
        txt_orderStatus.setText(orderDetail.getOrder().getOrderStatus());

        txt_name_billing.setText(orderDetail.getOrder().getPaymentName());
        txt_phone_billing.setText(orderDetail.getOrder().getMobile());
        txt_addr_billing.setText(orderDetail.getOrder().getPaymentAddress());

        txt_name_delivery.setText(orderDetail.getOrder().getShippingName());
        txt_phone_delivery.setText(orderDetail.getOrder().getMobile());
        txt_addr_delivery.setText(orderDetail.getOrder().getShippingAddress());

        txt_totalProducts.setText(String.valueOf(orderDetail.getOrderProductList().size()));

        float subTotal = orderDetail.getSubTotalAmount();
        float totalPaid = Float.parseFloat(orderDetail.getOrder().getTotal());

        if ((subTotal-totalPaid)>0.999) {
            //Do Discount Operation
            layout_discount.setVisibility(View.VISIBLE);
            txt_discountAmount.setText(String.format("%.2f",subTotal-totalPaid));

        }

        txt_subTotal.setText(String.format("%.2f",subTotal));
        txt_totalAmount.setText(String.format("%.2f",totalPaid));

        showProducts(orderDetail.getOrderProductList());


    }

    private void showProducts(List<OrderListItem.OrderProduct> orderProductList) {
        layout_products.removeAllViews();
        for(int position=0; position<orderProductList.size(); position++){
            View convertView=getLayoutInflater().inflate(R.layout.checkout_product_list,null);

            final TextView tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
            final TextView tv_product_code = (TextView) convertView.findViewById(R.id.tv_product_code);
            final TextView tv_product_attribute = (TextView) convertView.findViewById(R.id.tv_product_attribute);
            final  TextView tv_pack_of = (TextView) convertView.findViewById(R.id.tv_pack_of);
            final TextView tv_product_selling_price = (TextView) convertView.findViewById(R.id.tv_product_selling_price);
            final TextView tv_product_qty = (TextView) convertView.findViewById(R.id.tv_product_qty);
            final TextView txt_scheme=(TextView)convertView.findViewById(R.id.txt_scheme);
            final TextView tv_product_total_cost = (TextView) convertView.findViewById(R.id.tv_product_total_cost);
            TextView txt_qty=(TextView)convertView.findViewById(R.id.tv_qty);


            tv_product_name.setText(orderProductList.get(position).getName());
            tv_product_code.setText(orderProductList.get(position).getCode());
            tv_pack_of.setText(orderProductList.get(position).getPack_of());

            if(orderProductList.get(position).getSchemeTitle()==null || orderProductList.get(position).getSchemeTitle().isEmpty()){
                txt_scheme.setVisibility(View.GONE);
            }else{
                txt_scheme.setVisibility(View.VISIBLE);
                txt_scheme.setText(orderProductList.get(position).getSchemeTitle());
            }

            tv_product_selling_price.setText(orderProductList.get(position).getSelling_price());
            tv_product_qty.setText(orderProductList.get(position).getQuantity());
            txt_qty.setText(orderProductList.get(position).getQuantity());
            float t = Float.parseFloat(orderProductList.get(position).getItem_total());
            String str = String.format("%.2f", t);
            tv_product_total_cost.setText(getResources().getString(R.string.ruppe_name)+" "+str);

            if(orderProductList.get(position).getOptionValue()==null || orderProductList.get(position).getOptionValue().isEmpty())
            {
                tv_product_attribute.setVisibility(View.GONE);
            }

            else {

                tv_product_attribute.setText(orderProductList.get(position).getOptionValue());
            }

            layout_products.addView(convertView);
        }
    }

    @Override
    public void onFailure(Call<OrderDetailData> call, Throwable t) {
        progressDialog.dismiss();

        if(t instanceof UnknownHostException || t instanceof ConnectException)
            Globals.noInternet(getApplicationContext());
        else Globals.defaultError(getApplicationContext());
        Log.e("Exception", call.toString() + "\n" + t.getMessage());
    }
}
