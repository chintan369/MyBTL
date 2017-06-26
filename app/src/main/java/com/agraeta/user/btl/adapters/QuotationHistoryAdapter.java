package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.quotation.QuotationResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 26-Jun-17.
 */

public class QuotationHistoryAdapter extends BaseAdapter {

    Activity activity;
    List<QuotationResponse.QuotationData> quotationDataList = new ArrayList<>();
    LayoutInflater inflater;

    public QuotationHistoryAdapter(Activity activity, List<QuotationResponse.QuotationData> quotationDataList) {
        this.activity = activity;
        this.quotationDataList = quotationDataList;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return quotationDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return quotationDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.uo_info_row, parent, false);

        ImageView img_reOrderAll = (ImageView) view.findViewById(R.id.img_reOrderAll);
        ImageView im_pdf = (ImageView) view.findViewById(R.id.im_pdf);
        TextView t_title = (TextView) view.findViewById(R.id.t_title);
        TextView t_name = (TextView) view.findViewById(R.id.t_name);
        TextView txt_quotationFor = (TextView) view.findViewById(R.id.txt_quotationFor);
        TextView txt_takenBy = (TextView) view.findViewById(R.id.txt_takenBy);
        TextView t_emailto = (TextView) view.findViewById(R.id.t_emailto);
        TextView t_emailcc = (TextView) view.findViewById(R.id.t_emailcc);

        img_reOrderAll.setVisibility(View.GONE);
        im_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(Globals.server_link + quotationDataList.get(position).getQuotation().getFile_path()), "application/pdf");
                activity.startActivity(intent);
            }
        });

        t_title.setText(quotationDataList.get(position).getQuotation().getTitle());
        t_name.setText(quotationDataList.get(position).getQuotation().getTo_name());
        txt_takenBy.setText(quotationDataList.get(position).getOwner().getFirst_name() + " " + quotationDataList.get(position).getOwner().getLast_name());
        if (quotationDataList.get(position).getQuotation().getRole_id().equals(C.DISTRIBUTOR)) {
            txt_quotationFor.setText(quotationDataList.get(position).getUser().getDistributor().getFirm_name());
        } else {
            txt_quotationFor.setText(quotationDataList.get(position).getUser().getFirst_name() + " " + quotationDataList.get(position).getUser().getLast_name());
        }

        t_emailto.setText(quotationDataList.get(position).getQuotation().getEmail_id());
        t_emailcc.setText(quotationDataList.get(position).getQuotation().getEmail_cc());


        return view;
    }
}
