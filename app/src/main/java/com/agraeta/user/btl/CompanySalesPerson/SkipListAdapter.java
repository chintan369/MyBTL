package com.agraeta.user.btl.CompanySalesPerson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.DatabaseHandler;
import com.agraeta.user.btl.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SEO on 9/21/2016.
 */
public class SkipListAdapter extends BaseAdapter implements Serializable {
    List<Bean_SkipIcon> skipIconList;
    Context context;
    Activity activity;
    AppPrefs prefs;
    String subsalesID;
    DatabaseHandler db;
    public SkipListAdapter(Context context, List<Bean_SkipIcon> skipIconList, Activity activity){
        this.context=context;
        this.skipIconList=skipIconList;
        this.activity=activity;

    }

    @Override
    public int getCount() {
        return skipIconList.size();
    }

    @Override
    public Object getItem(int position) {
        return skipIconList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_skip_layout,parent,false);

        final Bean_SkipIcon skipPerson=skipIconList.get(position);

//        final TextView txtName2=(TextView) view.findViewById(R.id.txt_userListName2);
//        final TextView txtName4=(TextView) view.findViewById(R.id.txt_userListName4);
//        final TextView txtName1=(TextView) view.findViewById(R.id.txt_userListName);
//        final TextView txtName3=(TextView) view.findViewById(R.id.txt_userListName);
        TextView txt_skipfor=(TextView) view.findViewById(R.id.txt_skipfor);
        TextView txt_skipby=(TextView) view.findViewById(R.id.txt_skipby);
        ImageView img_info=(ImageView) view.findViewById(R.id.img_info);

        txt_skipfor.setText("Skipped For : "+skipIconList.get(position).getSkipForFname()+" "+skipIconList.get(position).getSkipForLname());
        txt_skipby.setText("Skipped By : "+skipIconList.get(position).getSkipByFname()+" "+skipIconList.get(position).getSkipByLname());

//        LinearLayout l_salesdata=(LinearLayout)view.findViewById(R.id.l_salesdata);
//        ImageView info_icon=(ImageView) view.findViewById(R.id.info_icon);

        db=new DatabaseHandler(context);

        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("CLICKEDdddddddddddddddd",""+statusD);
//                new GetSkipReasonInformation(activity,disUserID).execute();
//                //showDetailInfoDialog();
                Intent intent=new Intent(context,SkipOrderInformation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("skipOrder",skipIconList.get(position));
                context.startActivity(intent);

            }
        });


        return view;
    }


    public void updateData(List<Bean_SkipIcon> skipIconList) {
        this.skipIconList=skipIconList;
        notifyDataSetChanged();
    }

}
