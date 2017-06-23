package com.agraeta.user.btl.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.agraeta.user.btl.Bean_schemeData;
import com.agraeta.user.btl.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Nivida new on 14-Apr-17.
 */

public class ExpandableSchemeAdapter extends BaseExpandableListAdapter {

    List<String> dataHeaderList=new ArrayList<>();
    HashMap<String,List<Bean_schemeData>> dataChildList;
    Context context;

    public ExpandableSchemeAdapter(Context context, List<String> dataHeaderList, HashMap<String,List<Bean_schemeData>> dataChildList){
        this.dataHeaderList=dataHeaderList;
        this.dataChildList=dataChildList;
        this.context=context;
    }

    @Override
    public int getGroupCount() {
        return dataHeaderList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataChildList.get(dataHeaderList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataHeaderList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataChildList.get(dataHeaderList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.layout_group_header,parent,false);
        }

        TextView lblListHeader=(TextView) convertView.findViewById(R.id.lblListHeader);

        String groupHeader=((String) getGroup(groupPosition))+" ("+getChildrenCount(groupPosition)+")";

        lblListHeader.setText(groupHeader);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Bean_schemeData schemeData= (Bean_schemeData) getChild(groupPosition,childPosition);

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.layout_child_item,parent,false);
        }

        TextView lblListHeader=(TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setText(schemeData.getSchme_name());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
