package com.agraeta.user.btl.adapters;

import android.app.Activity;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agraeta.user.btl.AppPrefs;
import com.agraeta.user.btl.C;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.model.combooffer.ComboOfferDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nivida new on 24-Apr-17.
 */

public class ComboProductListAdapter extends BaseAdapter {

    List<ComboOfferDetail.ComboProduct> comboProductList = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;
    int breakPoint = -1;
    ProductChangeListener productChangeListener;
    String roleID = "";
    AppPrefs prefs;

    public ComboProductListAdapter(List<ComboOfferDetail.ComboProduct> comboProductList, Activity activity, ProductChangeListener productChangeListener) {
        this.comboProductList = comboProductList;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
        this.productChangeListener = productChangeListener;
        prefs = new AppPrefs(activity);
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    @Override
    public int getCount() {
        return comboProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return comboProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateProductQty(String productId, String qty) {
        for (int i = 0; i < comboProductList.size(); i++) {
            if (comboProductList.get(i).getProduct().getProductID().equals(productId)) {
                comboProductList.get(i).getProduct().setQuantity(Integer.parseInt(qty));
                comboProductList.get(i).setChecked(true);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        /*if (convertView == null) {*/
        holder = new ViewHolder();

        convertView = inflater.inflate(R.layout.layout_combo_product_item, null);

        holder.chk_product = (CheckBox) convertView.findViewById(R.id.chk_product);
        holder.txt_product = (TextView) convertView.findViewById(R.id.txt_product);
        holder.txt_productCode = (TextView) convertView.findViewById(R.id.txt_productCode);
        holder.txt_mrpPrice = (TextView) convertView.findViewById(R.id.txt_mrpPrice);
        holder.txt_sellingPrice = (TextView) convertView.findViewById(R.id.txt_sellingPrice);
        holder.txt_packOf = (TextView) convertView.findViewById(R.id.txt_packOf);
        holder.txt_discount = (TextView) convertView.findViewById(R.id.txt_discount);
        holder.img_minus = (ImageView) convertView.findViewById(R.id.img_minus);
        holder.img_plus = (ImageView) convertView.findViewById(R.id.img_plus);
        holder.edt_qty = (EditText) convertView.findViewById(R.id.edt_qty);
        holder.layout_mrpPrice = (LinearLayout) convertView.findViewById(R.id.layout_mrpPrice);
        holder.layout_spPrice = (LinearLayout) convertView.findViewById(R.id.layout_spPrice);

        convertView.setTag(holder);
        /*} else {
            holder = (ViewHolder) convertView.getTag();
        }*/

        if (prefs.getUserRoleId().equals(C.COMP_SALES_PERSON) && roleID.equals(C.DISTRIBUTOR)) {
            holder.layout_mrpPrice.setVisibility(View.GONE);
            holder.layout_spPrice.setVisibility(View.GONE);
        }

        holder.txt_mrpPrice.setPaintFlags(holder.txt_mrpPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.txt_product.setText(comboProductList.get(position).getProduct().getProductName());
        holder.txt_productCode.setText(comboProductList.get(position).getProduct().getProductCode());
        holder.txt_mrpPrice.setText(activity.getResources().getString(R.string.ruppe_name) + " " + comboProductList.get(position).getProduct().getMrpPrice());
        holder.txt_sellingPrice.setText(activity.getResources().getString(R.string.ruppe_name) + " " + comboProductList.get(position).getProduct().getSellingPrice());
        holder.txt_packOf.setText(comboProductList.get(position).getProduct().getLabel().getName());
        holder.edt_qty.setText(String.valueOf(comboProductList.get(position).getProduct().getQuantity()));

        holder.chk_product.setChecked(comboProductList.get(position).isChecked());

        holder.img_plus.setEnabled(comboProductList.get(position).isChecked());
        holder.img_minus.setEnabled(comboProductList.get(position).isChecked());
        holder.edt_qty.setEnabled(comboProductList.get(position).isChecked());

        if (position == breakPoint) {
            holder.edt_qty.requestFocus();
            breakPoint = -1;
        }

        float mrpPrice = Float.parseFloat(comboProductList.get(position).getProduct().getMrpPrice());
        float sellingPrice = Float.parseFloat(comboProductList.get(position).getProduct().getSellingPrice());

        if (mrpPrice > sellingPrice) {
            double discount = (sellingPrice * 100) / mrpPrice;
            discount = 100 - discount;

            if (discount >= 1) {
                holder.txt_discount.setVisibility(View.VISIBLE);
                holder.txt_discount.setText("Discount : " + String.format(Locale.getDefault(), "%.2f", discount) + " % OFF");
            }
        }

        holder.chk_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                comboProductList.get(position).setChecked(isChecked);

                holder.img_plus.setEnabled(isChecked);
                holder.img_minus.setEnabled(isChecked);
                holder.edt_qty.setEnabled(isChecked);

                if (!isChecked) {
                    comboProductList.get(position).getProduct().setQuantity(0);
                    holder.edt_qty.setText("0");
                }

                updateTotalQuantity();
            }
        });

        holder.img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredQty = holder.edt_qty.getText().toString();

                enteredQty = enteredQty.isEmpty() ? "0" : enteredQty;

                int qty = Integer.parseInt(enteredQty);
                int minPackOfQty = Integer.parseInt(comboProductList.get(position).getProduct().getMinPackOfQty());
                if (qty == 0) {
                    qty = qty + minPackOfQty;
                } else if (qty > 0) {

                    int remainder = C.modOf(minPackOfQty, qty);

                    if (qty < minPackOfQty) {

                        if (remainder > 0) {
                            qty = qty + (minPackOfQty - qty);
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

                holder.edt_qty.setText(String.valueOf(qty));
            }
        });

        holder.img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredQty = holder.edt_qty.getText().toString();

                if (!enteredQty.isEmpty()) {
                    int qty = Integer.parseInt(enteredQty);
                    if (qty > 0) {
                        int minPackOfQty = Integer.parseInt(comboProductList.get(position).getProduct().getMinPackOfQty());
                        if (qty < minPackOfQty) {
                            qty = 0;
                        } else {
                            int toCutOutQty = qty % minPackOfQty;

                            if (toCutOutQty > 0)
                                qty = qty - toCutOutQty;
                            else
                                qty = qty - minPackOfQty;
                        }

                        holder.edt_qty.setText(String.valueOf(qty));
                    }
                }
            }
        });

        holder.edt_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredQty = holder.edt_qty.getText().toString();

                enteredQty = enteredQty.isEmpty() ? "0" : enteredQty;

                int qty = Integer.parseInt(enteredQty);

                comboProductList.get(position).getProduct().setQuantity(qty);

                updateTotalQuantity();
            }
        });


        return convertView;
    }

    private void updateTotalQuantity() {
        int totalQuantity = 0;

        for (int i = 0; i < comboProductList.size(); i++) {
            if (comboProductList.get(i).isChecked()) {
                totalQuantity += comboProductList.get(i).getProduct().getQuantity();
            }
        }

        productChangeListener.onQunatityChanged(totalQuantity);
    }

    public int getBreakPoint() {
        int breakPoint = -1;

        for (int i = 0; i < comboProductList.size(); i++) {
            int qty = comboProductList.get(i).getProduct().getQuantity();
            int minPackQty = Integer.parseInt(comboProductList.get(i).getProduct().getMinPackOfQty());

            if (comboProductList.get(i).isChecked() && (qty < minPackQty || (C.modOf(qty, minPackQty)) > 0)) {
                breakPoint = i;
                break;
            }
        }

        if (breakPoint != -1) {
            int qty = comboProductList.get(breakPoint).getProduct().getQuantity();
            int minPackQty = Integer.parseInt(comboProductList.get(breakPoint).getProduct().getMinPackOfQty());

            if (qty <= 0) {
                Globals.Toast2(activity, "Please Enter At least " + minPackQty + " quantity");
            } else {
                C.showMinPackAlert(activity, minPackQty);
            }
        }


        return breakPoint;
    }

    public void setBreakPoint(int breakPoint) {
        this.breakPoint = breakPoint;
        notifyDataSetChanged();
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (int i = 0; i < comboProductList.size(); i++) {

            if (comboProductList.get(i).isChecked()) {
                int qty = comboProductList.get(i).getProduct().getQuantity();
                totalQuantity += qty;
            }
        }

        return totalQuantity;
    }

    public int getCheckedProductCount() {
        int selectedProduct = 0;

        for (int i = 0; i < comboProductList.size(); i++) {

            if (comboProductList.get(i).isChecked()) {
                selectedProduct += 1;
            }
        }

        return selectedProduct;
    }

    public List<ComboOfferDetail.ComboProduct> getCheckedProducts() {
        List<ComboOfferDetail.ComboProduct> productList = new ArrayList<>();

        for (int i = 0; i < comboProductList.size(); i++) {
            if (comboProductList.get(i).isChecked()) {
                productList.add(comboProductList.get(i));
            }
        }

        return productList;
    }

    public void setExtraDiscountPrice(String extraDiscountPrice) {

        Log.e("Extra Discount", "Combo " + extraDiscountPrice);

        for (int i = 0; i < comboProductList.size(); i++) {

            float extraDiscount = Float.parseFloat(extraDiscountPrice);
            float sellingPrice = Float.parseFloat(comboProductList.get(i).getProduct().getSellingPrice());

            double discount = (sellingPrice * extraDiscount) / 100;

            double newSellingPrice = sellingPrice - discount;

            Log.e("New Prices", extraDiscount + ", " + sellingPrice + ", " + discount + ", " + newSellingPrice);

            comboProductList.get(i).getProduct().setSellingPrice(String.format(Locale.getDefault(), "%.2f", newSellingPrice));
        }

        notifyDataSetChanged();
    }

    private class ViewHolder {
        CheckBox chk_product;
        TextView txt_product, txt_productCode, txt_packOf, txt_sellingPrice, txt_mrpPrice, txt_discount;
        ImageView img_minus, img_plus;
        EditText edt_qty;
        LinearLayout layout_spPrice, layout_mrpPrice;
    }
}
