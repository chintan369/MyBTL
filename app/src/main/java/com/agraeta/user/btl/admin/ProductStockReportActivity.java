package com.agraeta.user.btl.admin;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.adapters.ProductStockReportAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.ProductStockResponse;
import com.agraeta.user.btl.model.ProductSuggestionResponse;
import com.agraeta.user.btl.model.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductStockReportActivity extends AppCompatActivity {

    ListView listProducts;
    AdminAPI adminAPI;
    AutoCompleteTextView edt_search;
    ImageView img_search;
    ArrayList<String> searchSuggestions = new ArrayList<>();
    ArrayAdapter<String> suggestionAdapter;
    Custom_ProgressDialog dialog;
    ProductStockReportAdapter adapter;
    List<ProductStockResponse.ProductStock> stockList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_stock_report);

        dialog = new Custom_ProgressDialog(this, "");
        dialog.setCancelable(false);

        adminAPI = ServiceGenerator.getAPIServiceClass();

        fetchIDs();
    }

    private void fetchIDs() {
        edt_search = (AutoCompleteTextView) findViewById(R.id.edt_search);
        img_search = (ImageView) findViewById(R.id.img_search);
        listProducts = (ListView) findViewById(R.id.listProducts);
        adapter = new ProductStockReportAdapter(stockList, this);
        listProducts.setAdapter(adapter);

        suggestionAdapter = new ArrayAdapter<String>(this, R.layout.auto_textview, searchSuggestions);
        edt_search.setAdapter(suggestionAdapter);

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchKey = edt_search.getText().toString();
                searchForProduct(searchKey);
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchKey = edt_search.getText().toString().trim();

                if (!searchKey.isEmpty()) {
                    Call<ProductSuggestionResponse> productSuggestionResponseCall = adminAPI.suggestionResponseCall(searchKey);
                    productSuggestionResponseCall.enqueue(new Callback<ProductSuggestionResponse>() {
                        @Override
                        public void onResponse(Call<ProductSuggestionResponse> call, Response<ProductSuggestionResponse> response) {
                            searchSuggestions.clear();
                            ProductSuggestionResponse suggestionResponse = response.body();
                            if (suggestionResponse != null) {
                                if (suggestionResponse.isStatus()) {
                                    for (int i = 0; i < suggestionResponse.getData().size(); i++) {
                                        searchSuggestions.add(suggestionResponse.getData().get(i).getProduct_name());
                                    }
                                    suggestionAdapter.notifyDataSetChanged();
                                    edt_search.showDropDown();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductSuggestionResponse> call, Throwable t) {

                        }
                    });
                } else {

                }

            }
        });

        edt_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchKey = edt_search.getText().toString();
                searchForProduct(searchKey);
            }
        });


    }

    private void searchForProduct(String search) {
        if (search.isEmpty()) {
            Globals.Toast2(getApplicationContext(), "Please Enter Product Name");
            return;
        }

        dialog.show();
        stockList.clear();
        adapter.notifyDataSetChanged();
        final Call<ProductStockResponse> stockResponseCall = adminAPI.productStockResponseCall(search);
        stockResponseCall.enqueue(new Callback<ProductStockResponse>() {
            @Override
            public void onResponse(Call<ProductStockResponse> call, Response<ProductStockResponse> response) {
                dialog.dismiss();
                ProductStockResponse stockResponse = response.body();
                if (stockResponse != null) {
                    if (stockResponse.isStatus()) {
                        stockList.addAll(stockResponse.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        Globals.Toast2(getApplicationContext(), stockResponse.getMessage());
                    }
                } else {
                    Globals.defaultError(getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<ProductStockResponse> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t, getApplicationContext());
            }
        });
    }

    public void showInfoDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_product_stock, null);

        ImageView img_close = (ImageView) dialogView.findViewById(R.id.img_close);
        TextView txt_productName = (TextView) dialogView.findViewById(R.id.txt_productName);
        TextView txt_productCategory = (TextView) dialogView.findViewById(R.id.txt_productCategory);
        TextView txt_packOf = (TextView) dialogView.findViewById(R.id.txt_packOf);
        LinearLayout layout_stockReport = (LinearLayout) dialogView.findViewById(R.id.layout_stockReport);

        if (stockList.get(position).getCompany_name().size() > 0) {
            for (int i = 0; i < stockList.get(position).getCompany_name().size(); i++) {
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.textview, null);

                textView.setText(stockList.get(position).getCompany_name().get(i).getCompany_name() + "  Qty: " + stockList.get(position).getCompany_name().get(i).getQty());

                layout_stockReport.addView(textView);
            }
        } else {
            layout_stockReport.setVisibility(View.GONE);
        }

        txt_productName.setText(stockList.get(position).getProduct_name());
        txt_productCategory.setText(stockList.get(position).getProduct_category());
        txt_packOf.setText(stockList.get(position).getPack_of());

        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
