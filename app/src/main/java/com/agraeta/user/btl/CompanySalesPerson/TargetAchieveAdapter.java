package com.agraeta.user.btl.CompanySalesPerson;

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
 * Created by SEO on 9/29/2016.
 */
public class TargetAchieveAdapter extends BaseAdapter {
    List<BeanTargetAchieve> targetAchieveList;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;

    public TargetAchieveAdapter(Context context, List<BeanTargetAchieve> targetAchieveList, Activity activity) {
        this.context = context;
        this.targetAchieveList = targetAchieveList;
        this.activity = activity;

    }


    @Override
    public int getCount() {
        return targetAchieveList.size();
    }

    @Override
    public Object getItem(int position) {
        return targetAchieveList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_targetachieve_layout, parent, false);

        final BeanTargetAchieve targetAchieve = targetAchieveList.get(position);

        TextView lstCategory = (TextView) view.findViewById(R.id.lst_category);
        TextView lsttarget = (TextView) view.findViewById(R.id.lst_target);
        TextView lstachieve = (TextView) view.findViewById(R.id.lst_achieve);

        lstCategory.setText(targetAchieve.getCategoryName());
        lsttarget.setText(targetAchieve.getTargetQuality());
        lstachieve.setText(targetAchieve.getAchieveQuality());


        prefs = new AppPrefs(context);
        userID = prefs.getUserId();

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter", targetAchieveList.size() + "");
    }

    //    public void setCallBack(DisSalesCallback callBack){
//        this.disSalesCallback=callBack;
//    }
//
//    public interface DisSalesCallback{
//        void deleteUser(String saless_id, String dis_id, int position);
//    }
    public void updateData(List<BeanTargetAchieve> targetAchieveList) {
        this.targetAchieveList = targetAchieveList;
        notifyDataSetChanged();
    }
}
