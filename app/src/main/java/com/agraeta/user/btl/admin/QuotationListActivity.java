package com.agraeta.user.btl.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.agraeta.user.btl.Custom_ProgressDialog;
import com.agraeta.user.btl.Globals;
import com.agraeta.user.btl.R;
import com.agraeta.user.btl.adapters.QuotationHistoryAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.quotation.QuotationResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotationListActivity extends AppCompatActivity implements Callback<QuotationResponse> {

    ListView listQuotation;
    AdminAPI adminAPI;
    List<QuotationResponse.QuotationData> quotationDataList = new ArrayList<>();
    QuotationHistoryAdapter historyAdapter;
    int currentPage = 1, totalPages = 1;
    String fromDate = null, toDate = null;

    Custom_ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_list);

        adminAPI = ServiceGenerator.getAPIServiceClass();
        dialog = new Custom_ProgressDialog(this, "");
        dialog.setCancelable(false);

        fetchIDs();

    }

    private void fetchIDs() {
        listQuotation = (ListView) findViewById(R.id.listQuotation);
        historyAdapter = new QuotationHistoryAdapter(this, quotationDataList);
        listQuotation.setAdapter(historyAdapter);

        Call<QuotationResponse> quotationResponseCall = adminAPI.quotationResponseCall(fromDate, toDate, String.valueOf(currentPage));
        quotationResponseCall.enqueue(this);

    }

    @Override
    public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
        dialog.dismiss();
        QuotationResponse quotationResponse = response.body();
        if (quotationResponse != null) {
            if (quotationResponse.isStatus()) {
                totalPages = quotationResponse.getTotalPage();
                quotationDataList.addAll(quotationResponse.getData());

                if (currentPage < totalPages) {
                    currentPage++;
                    Call<QuotationResponse> quotationResponseCall = adminAPI.quotationResponseCall(fromDate, toDate, String.valueOf(currentPage));
                    quotationResponseCall.enqueue(this);
                }
            }
        } else {
            Globals.defaultError(this);
        }
    }

    @Override
    public void onFailure(Call<QuotationResponse> call, Throwable t) {
        dialog.dismiss();
        Globals.showError(t, getApplicationContext());
    }
}
