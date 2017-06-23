package com.agraeta.user.btl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.Distributor.EditDisSalesActivity;

import java.util.List;

/**
 * Created by SEO on 9/1/2016.
 */
public class InvoiceAdapter extends BaseAdapter {
    List<Bean_Order_history> invoiceList;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
//    AppPrefs prefs;
//    String userID;

    public InvoiceAdapter(Context context, List<Bean_Order_history> invoiceList, Activity activity){
        this.context=context;
        this.invoiceList=invoiceList;
        this.activity=activity;

    }


    @Override
    public int getCount() {
        return invoiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return invoiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_listview_sales,parent,false);

        final Bean_Order_history invoicee=invoiceList.get(position);
//
        TextView txtInvoice=(TextView) view.findViewById(R.id.txt_invoice_no);
        LinearLayout lyt_invoice=(LinearLayout)view.findViewById(R.id.layout_invoice);
//
//        ImageView edit_icon=(ImageView) view.findViewById(R.id.edit_icon);
//        ImageView delete_icon=(ImageView) view.findViewById(R.id.delete_icons);

        txtInvoice.setText(invoicee.getInvoice_no());
//        prefs=new AppPrefs(context);
//        userID=prefs.getUserId();

        lyt_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,EditDisSalesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("invoicee",invoicee);
                context.startActivity(intent);
                activity.finish();
            }
        });

//        delete_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("Delete","clicked "+String.valueOf(salesPerson.getSales_id()));
//                disSalesCallback.deleteUser(String.valueOf(salesPerson.getSales_id()),prefs.getUserId(),position);
//            }
//        });


        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",invoiceList.size()+"");
    }
//    public void setCallBack(DisSalesCallback callBack){
//        this.disSalesCallback=callBack;
//    }
//
//    public interface DisSalesCallback{
//        void deleteUser(String saless_id, String dis_id,int position);
//    }
}
