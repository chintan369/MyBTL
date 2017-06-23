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
 * Created by chaitalee on 9/17/2016.
 */
public class LedgerAdapter extends BaseAdapter {
    List<Bean_Ledger> ledgerList;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;

    public LedgerAdapter(Context context, List<Bean_Ledger> ledgerList, Activity activity){
        this.context=context;
        this.ledgerList=ledgerList;
        this.activity=activity;

    }


    @Override
    public int getCount() {
        return ledgerList.size();
    }

    @Override
    public Object getItem(int position) {
        return ledgerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_ledger_list,parent,false);

        final Bean_Ledger ledgerPerson=ledgerList.get(position);
        //TextView txtName=(TextView) view.findViewById(R.id.txt_ledgername);
        //TextView txtopening=(TextView) view.findViewById(R.id.txt_opening);
        //TextView txtClosing=(TextView) view.findViewById(R.id.txt_closing);
       // TextView txtCurrentCredit=(TextView) view.findViewById(R.id.txt_currentCredit);
        //TextView txtCurrentDebit=(TextView) view.findViewById(R.id.txt_currentDebit);
        TextView lstDate=(TextView) view.findViewById(R.id.lst_date);
        TextView lstParticulars=(TextView) view.findViewById(R.id.lst_particulars);
        TextView lstVchType=(TextView) view.findViewById(R.id.lst_vchtype);
        TextView lstVchNo=(TextView) view.findViewById(R.id.lst_vchno);
        TextView lstDebit=(TextView) view.findViewById(R.id.lst_debit);
        TextView lstCredit=(TextView) view.findViewById(R.id.lst_credit);


//        txtName.setText(ledgerPerson.getLedgerName());
//        txtopening.setText(ledgerPerson.getOpeningBalance());
//        txtClosing.setText(ledgerPerson.getClosingBalance());
//        txtCurrentCredit.setText(ledgerPerson.getCurrentTotalCredit());
//        txtCurrentDebit.setText(ledgerPerson.getCurrentTotalDebit());
        lstDate.setText(ledgerPerson.getDate());
        lstParticulars.setText(ledgerPerson.getParticulars());
        lstVchType.setText(ledgerPerson.getVchType());
        lstVchNo.setText(ledgerPerson.getVchNo());
        //lstDebit.setText(context.getResources().getString(R.string.Rs) +ledgerPerson.getDebit());

        if(ledgerPerson.getDebit().equalsIgnoreCase(""))
        {lstDebit.setText(ledgerPerson.getDebit());}
        else
        {  lstDebit.setText(context.getResources().getString(R.string.Rs) +ledgerPerson.getDebit());}

        if(ledgerPerson.getCredit().equalsIgnoreCase(""))
        {lstCredit.setText(ledgerPerson.getCredit());}
        else
        {  lstCredit.setText(context.getResources().getString(R.string.Rs) +ledgerPerson.getCredit());}


        prefs=new AppPrefs(context);
        userID=prefs.getUserId();

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",ledgerList.size()+"");
    }
//    public void setCallBack(DisSalesCallback callBack){
//        this.disSalesCallback=callBack;
//    }
//
//    public interface DisSalesCallback{
//        void deleteUser(String saless_id, String dis_id, int position);
//    }
    public void updateData(List<Bean_Ledger> ledgerList) {
        this.ledgerList=ledgerList;
        notifyDataSetChanged();
    }
}

