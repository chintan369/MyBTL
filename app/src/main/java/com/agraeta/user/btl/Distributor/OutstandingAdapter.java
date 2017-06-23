package com.agraeta.user.btl.Distributor;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.R;

import java.util.List;

/**
 * Created by chaitalee on 9/19/2016.
 */
public class OutstandingAdapter extends BaseAdapter{
    List<Bean_Outstanding> outstandingList;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;
    String ot,pt;
    public OutstandingAdapter(Context context, List<Bean_Outstanding> outstandingList, Activity activity){
        this.context=context;
        this.outstandingList=outstandingList;
        this.activity=activity;

    }


    @Override
    public int getCount() {
        return outstandingList.size();
    }

    @Override
    public Object getItem(int position) {
        return outstandingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_outstanding_layout,parent,false);

        final Bean_Outstanding ledgerPerson=outstandingList.get(position);

        TextView lstDate=(TextView) view.findViewById(R.id.lst_date);
        TextView lstrefno=(TextView) view.findViewById(R.id.lst_refno);
        TextView lstopeningamt=(TextView) view.findViewById(R.id.lst_openingamt);
        TextView lstpendingamt=(TextView) view.findViewById(R.id.lst_pendingamt);
        TextView lstdueon=(TextView) view.findViewById(R.id.lst_dueon);
        TextView lstoverdues=(TextView) view.findViewById(R.id.lst_overdues);


        prefs=new AppPrefs(context);


        lstDate.setText(ledgerPerson.getDate());
        lstrefno.setText(ledgerPerson.getRefNo());
        lstopeningamt.setText(ledgerPerson.getOpeningAmount()+" "+ledgerPerson.getOpeningType());
        lstpendingamt.setText(ledgerPerson.getPendingAmount()+" "+ledgerPerson.getPendingType());
        lstdueon.setText(ledgerPerson.getDueOn());
        lstoverdues.setText(ledgerPerson.getOverDues());


        prefs=new AppPrefs(context);
        userID=prefs.getUserId();

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",outstandingList.size()+"");
    }
    //    public void setCallBack(DisSalesCallback callBack){
//        this.disSalesCallback=callBack;
//    }
//
//    public interface DisSalesCallback{
//        void deleteUser(String saless_id, String dis_id, int position);
//    }
    public void updateData(List<Bean_Outstanding> outstandingList) {
        this.outstandingList=outstandingList;
        notifyDataSetChanged();
    }
}
