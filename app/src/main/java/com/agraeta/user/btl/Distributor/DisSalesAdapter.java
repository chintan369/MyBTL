package com.agraeta.user.btl.Distributor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.R;

import java.util.List;

/**
 * Created by SEO on 8/23/2016.
 */
public class DisSalesAdapter extends BaseAdapter {
    List<Bean_Dis_Sales> disSalesList;
    Context context;
    Activity activity;
    DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;

    public DisSalesAdapter(Context context, List<Bean_Dis_Sales> disSalesList, Activity activity){
        this.context=context;
        this.disSalesList=disSalesList;
        this.activity=activity;

    }


    @Override
    public int getCount() {
        return disSalesList.size();
    }

    @Override
    public Object getItem(int position) {
        return disSalesList.get(position);
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

        final Bean_Dis_Sales salesPerson=disSalesList.get(position);

        TextView txtName=(TextView) view.findViewById(R.id.txt_sales_name);

        ImageView edit_icon=(ImageView) view.findViewById(R.id.edit_icon);
        ImageView delete_icon=(ImageView) view.findViewById(R.id.delete_icons);

        txtName.setText(salesPerson.getFirstName()+" "+salesPerson.getLastName());
        //txtName.setText(salesPerson.getFirstName());
        //txtName.setText(disSalesList.get(position).getFirstName());
//        String txtname=dis_sales_b.getFirstName();
//        txtName.setText(""+txtname);
        prefs=new AppPrefs(context);
        userID=prefs.getUserId();

        edit_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,EditDisSalesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("salesPerson",salesPerson);
                context.startActivity(intent);
                activity.finish();
            }
        });

        delete_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("Delete","clicked "+String.valueOf(salesPerson.getSales_id()));
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        context);
                // Setting Dialog Title
                alertDialog.setTitle("Delete");
                // Setting Dialog Message
                alertDialog
                        .setMessage("Are you sure you want to delete?");

                // Setting Icon to Dialog
                // .setIcon(R.drawable.ic_launcher);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                disSalesCallback.deleteUser(String.valueOf(salesPerson.getSales_id()),prefs.getUserId(),position);
                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Write your code here to invoke NO event
                                dialog.cancel();
                            }
                        });
                // Showing Alert Message
                alertDialog.show();

            }
        });

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",disSalesList.size()+"");
    }
    public void setCallBack(DisSalesCallback callBack){
        this.disSalesCallback=callBack;
    }

    public interface DisSalesCallback{
         void deleteUser(String saless_id, String dis_id, int position);
    }
    public void updateData(List<Bean_Dis_Sales> disSalesList) {
        this.disSalesList=disSalesList;
        notifyDataSetChanged();
    }
}
