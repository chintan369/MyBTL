package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.Bean_Product;
import com.agraeta.user.btl.Bean_schemeData;
import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nivida new on 14-Mar-17.
 */

public class ReOrderGridAdapter extends BaseAdapter {

    List<Bean_Product> productList=new ArrayList<>();
    Activity context;
    LayoutInflater inflater;
    int breakPosition=-1;

    public ReOrderGridAdapter(Activity context, List<Bean_Product> productList) {
        this.context = context;
        this.productList = productList;

        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(R.layout.layout_reorder_item,parent,false);

        final TextView txt_productCode, txt_productName, txt_mrpPrice, txt_sellingPrice, txt_packOf, txt_totalPrice, txt_optionName, txt_optionSeperator, txt_optionValue, txt_schemeLabel, txt_discountName, txt_mrpLabel;
        final EditText edt_qty;
        ImageView img_plus,img_minus,img_offer;

        txt_productCode=(TextView) view.findViewById(R.id.txt_productCode);
        txt_productName=(TextView) view.findViewById(R.id.txt_productName);
        txt_mrpPrice=(TextView) view.findViewById(R.id.txt_mrpPrice);
        txt_mrpLabel = (TextView) view.findViewById(R.id.txt_mrpLabel);
        txt_sellingPrice=(TextView) view.findViewById(R.id.txt_sellingPrice);
        txt_packOf=(TextView) view.findViewById(R.id.txt_packOf);
        txt_totalPrice=(TextView) view.findViewById(R.id.txt_totalPrice);
        txt_optionName=(TextView) view.findViewById(R.id.txt_optionName);
        txt_optionSeperator=(TextView) view.findViewById(R.id.txt_optionSeperator);
        txt_optionValue=(TextView) view.findViewById(R.id.txt_optionValue);
        txt_schemeLabel = (TextView) view.findViewById(R.id.txt_schemeLabel);
        txt_discountName=(TextView) view.findViewById(R.id.txt_discountName);

        txt_mrpPrice.setPaintFlags(txt_mrpPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        edt_qty=(EditText) view.findViewById(R.id.edt_qty);

        img_plus=(ImageView) view.findViewById(R.id.img_plus);
        img_minus=(ImageView) view.findViewById(R.id.img_minus);
        img_offer=(ImageView) view.findViewById(R.id.img_offer);

        if(!(productList.get(position).getSchemeList().size()>0)){
            img_offer.setVisibility(View.GONE);
        }

        txt_productCode.setText(productList.get(position).getPro_code());
        txt_productName.setText(productList.get(position).getPro_name());

        txt_mrpPrice.setText(context.getString(R.string.ruppe_name)+" "+productList.get(position).getPro_mrp());
        txt_sellingPrice.setText(context.getString(R.string.ruppe_name)+" "+productList.get(position).getPro_sellingprice());
        txt_packOf.setText(productList.get(position).getPro_label());
        txt_optionName.setText(productList.get(position).getOptionName());
        txt_optionValue.setText(productList.get(position).getOptionValueName());

        if(productList.get(position).getOptionName().isEmpty() && productList.get(position).getOptionValueName().isEmpty()){
            txt_optionSeperator.setVisibility(View.GONE);
        }

        double mrpPrice= Double.parseDouble(productList.get(position).getPro_mrp());
        double spPrice= Double.parseDouble(productList.get(position).getPro_sellingprice());

        if (mrpPrice == spPrice) {
            txt_mrpLabel.setVisibility(View.GONE);
            txt_mrpPrice.setVisibility(View.GONE);
            txt_discountName.setVisibility(View.GONE);
        }

        double discountPrice= mrpPrice-spPrice;
        double discountPercent = discountPrice * 100;
        discountPercent = discountPercent / mrpPrice;
        int percent = (int) discountPercent;

        txt_discountName.setText(percent+" % OFF");

        int currentQty = Integer.parseInt(productList.get(position).getPro_qty());

        if (currentQty < productList.get(position).getPackQty()) {
            edt_qty.setText(String.valueOf(productList.get(position).getPackQty()));
            productList.get(position).setPro_qty(String.valueOf(productList.get(position).getPackQty()));
        } else {
            edt_qty.setText(productList.get(position).getPro_qty());
        }

        if(breakPosition==position)
        {
            edt_qty.requestFocus();
        }

        float sellingPrice=Float.parseFloat(productList.get(position).getPro_sellingprice());

        float totalPrice= sellingPrice * currentQty ;

        String totalPriceString=String.format("%.2f",totalPrice);

        txt_totalPrice.setText(context.getString(R.string.ruppe_name)+" "+totalPriceString);

        int schemeSelectedPosition = -1;

        for (int i = 0; i < productList.get(position).getSchemeList().size(); i++) {
            int schemeQty = Integer.parseInt(productList.get(position).getSchemeList().get(i).getSchme_qty());
            int qty = Integer.parseInt(productList.get(position).getPro_qty());

            if (qty < schemeQty) {
                schemeSelectedPosition = i;
                break;
            } else {
                schemeSelectedPosition = productList.get(position).getSchemeList().size() - 1;
            }
        }

        if (productList.get(position).getSchemeList().size() > 0 && schemeSelectedPosition >= 0) {
            txt_schemeLabel.setText(productList.get(position).getSchemeList().get(schemeSelectedPosition).getSchme_name());
        } else {
            txt_schemeLabel.setText("");
        }

        img_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSchemeDialog(position);
            }
        });

        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredQty = edt_qty.getText().toString();

                enteredQty = enteredQty.isEmpty() ? "0" : enteredQty;

                int qty = Integer.parseInt(enteredQty);
                int minPackOfQty = productList.get(position).getPackQty();
                if (qty == 0) {
                    qty = qty + minPackOfQty;
                } else if (qty > 0) {

                    int remainder= C.modOf(minPackOfQty,qty);

                    if (qty < minPackOfQty) {

                        if (remainder > 0) {
                            qty = qty + (minPackOfQty-qty);
                        } else {
                            qty = qty + minPackOfQty;
                        }
                    } else if (qty >= minPackOfQty) {
                        if (remainder > 0) {
                            qty = qty - remainder + minPackOfQty;
                        } else {
                            qty = qty + minPackOfQty;
                        }
                    }
                }

                edt_qty.setText(String.valueOf(qty));
            }
        });

        img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredQty = edt_qty.getText().toString();

                if (!enteredQty.isEmpty()) {
                    int qty = Integer.parseInt(enteredQty);
                    if (qty > 0) {
                        int minPackOfQty = productList.get(position).getPackQty();
                        if (qty < minPackOfQty) {
                            qty = 0;
                        } else {
                            int toCutOutQty = qty % minPackOfQty;

                            if (toCutOutQty > 0)
                                qty = qty - toCutOutQty;
                            else
                                qty = qty - minPackOfQty;
                        }

                        edt_qty.setText(String.valueOf(qty));
                    }
                }
            }
        });

        edt_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredQty=edt_qty.getText().toString();

                enteredQty = enteredQty.isEmpty() ? "0" : enteredQty;

                int qty=Integer.parseInt(enteredQty);

                float sellingPrice=Float.parseFloat(productList.get(position).getPro_sellingprice());

                float totalPrice= sellingPrice * qty ;

                String totalPriceString=String.format("%.2f",totalPrice);

                txt_totalPrice.setText(context.getString(R.string.ruppe_name)+" "+totalPriceString);

                productList.get(position).setPro_qty(enteredQty);

                int schemeSelectedPosition = -1;

                for (int i = 0; i < productList.get(position).getSchemeList().size(); i++) {
                    int schemeQty = Integer.parseInt(productList.get(position).getSchemeList().get(i).getSchme_qty());


                    if (qty < schemeQty) {
                        schemeSelectedPosition = i;
                        break;
                    } else {
                        schemeSelectedPosition = productList.get(position).getSchemeList().size() - 1;
                    }
                }

                if (productList.get(position).getSchemeList().size() > 0 && schemeSelectedPosition >= 0) {
                    txt_schemeLabel.setText(productList.get(position).getSchemeList().get(schemeSelectedPosition).getSchme_name());
                } else {
                    txt_schemeLabel.setText("");
                }

            }
        });

        return view;
    }

    private void showSchemeDialog(final int selectedPosition) {
        final ArrayList<Bean_schemeData> schemeDataList=productList.get(selectedPosition).getSchemeList();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View schemeView=inflater.inflate(R.layout.scheme_layout,null);

        ImageView btn_cancel=(ImageView) schemeView.findViewById(R.id.btn_cancel);

        ExpandableListView listSchemes = (ExpandableListView) schemeView.findViewById(R.id.listSchemes);

        final List<String> schemeHeaders=new ArrayList<String>();
        final HashMap<String,List<Bean_schemeData>> schemeChildList=new HashMap<String, List<Bean_schemeData>>();

        List<Bean_schemeData> extraSpecialScheme=new ArrayList<Bean_schemeData>();
        List<Bean_schemeData> specialScheme=new ArrayList<Bean_schemeData>();
        List<Bean_schemeData> generalScheme=new ArrayList<Bean_schemeData>();

        for(int i=0; i<schemeDataList.size(); i++){
            if(schemeDataList.get(i).getCategory_id().equals(C.EXTRA_SPECIAL_SCHEME)){
                extraSpecialScheme.add(schemeDataList.get(i));
            }
            else if(schemeDataList.get(i).getCategory_id().equals(C.SPECIAL_SCHEME)){
                specialScheme.add(schemeDataList.get(i));
            }
            else {
                generalScheme.add(schemeDataList.get(i));
            }
        }

        if(extraSpecialScheme.size()>0){
            schemeHeaders.add("Extra Special Schemes");
            schemeChildList.put(schemeHeaders.get(schemeHeaders.size()-1),extraSpecialScheme);
        }

        if(specialScheme.size()>0){
            schemeHeaders.add("Special Schemes");
            schemeChildList.put(schemeHeaders.get(schemeHeaders.size()-1),specialScheme);
        }

        if(generalScheme.size()>0){
            schemeHeaders.add("General Schemes");
            schemeChildList.put(schemeHeaders.get(schemeHeaders.size()-1),generalScheme);
        }

        ExpandableSchemeAdapter schemeAdapter=new ExpandableSchemeAdapter(context,schemeHeaders,schemeChildList);
        listSchemes.setAdapter(schemeAdapter);

        builder.setView(schemeView);

        final AlertDialog dialog=builder.create();

        listSchemes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                productList.get(selectedPosition).setPro_qty(schemeChildList.get(schemeHeaders.get(groupPosition)).get(childPosition).getSchme_qty());
                dialog.dismiss();
                notifyDataSetChanged();

                return true;
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public int brokenPoint(){
        int breakPoint=-1;

        for (int i = 0; i < productList.size(); i++) {
            int minPackOfQty=productList.get(i).getPackQty();
            int currentQty=Integer.parseInt(productList.get(i).getPro_qty().isEmpty() ? "0" : productList.get(i).getPro_qty());

            if (currentQty < minPackOfQty || (currentQty > 0 && C.modOf(currentQty, minPackOfQty) > 0)) {
                breakPoint=i;
                C.showMinPackAlert(context,minPackOfQty);
                //Globals.Toast2(context,"Please enter quantity in multiplication of "+minPackOfQty);
                break;
            }
            else if(currentQty<=0){
                breakPoint=i;
                Globals.Toast2(context,"Please enter at least 1 quantity");
                break;
            }
        }

        return breakPoint;
    }

    public void setBreakPosition(int breakPosition){
        this.breakPosition=breakPosition;
        notifyDataSetChanged();
        //context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public String getProductArray(){
        String productArray="";

        JSONArray array=new JSONArray();

        for(int i=0; i<productList.size(); i++){
            JSONObject object=new JSONObject();
            try {
                object.put("category_id",productList.get(i).getPro_cat_id());
                object.put("option_id",productList.get(i).getOptionID());
                object.put("pack_of",productList.get(i).getPro_label());
                object.put("option_name",productList.get(i).getOptionName());
                object.put("pro_code",productList.get(i).getPro_code());
                object.put("option_value_id",productList.get(i).getOptionValueID());
                object.put("option_value_name",productList.get(i).getOptionValueName());
                object.put("prod_img",productList.get(i).getPro_image());
                object.put("name",productList.get(i).getPro_name());
                object.put("id",productList.get(i).getPro_id());
                object.put("qty",productList.get(i).getPro_qty());
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        productArray=array.toString();

        return productArray;
    }
}
