package com.agraeta.user.btl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.PageResponse;
import com.agraeta.user.btl.model.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadsActivity extends AppCompatActivity {

    WebView web_downloads;
    AdminAPI adminAPI;

    Custom_ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        dialog=new Custom_ProgressDialog(this,"");
        dialog.setCancelable(false);


        adminAPI= ServiceGenerator.getAPIServiceClass();

        fetchIDs();
    }

    private void fetchIDs() {
        web_downloads=(WebView) findViewById(R.id.web_downloads);

        WebSettings settings = web_downloads.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);

        dialog.show();

        Call<PageResponse> pageResponseCall=adminAPI.getInformtionPage("14");
        pageResponseCall.enqueue(new Callback<PageResponse>() {
            @Override
            public void onResponse(Call<PageResponse> call, Response<PageResponse> response) {
                dialog.dismiss();
                PageResponse pageResponse=response.body();
                if(pageResponse!=null){
                    if(pageResponse.isStatus()){
                        web_downloads.loadData(pageResponse.getData().getDescription(),"text/html","UTF-8");
                    }
                    else {
                        Globals.Toast2(getApplicationContext(),pageResponse.getMessage());
                    }
                }
                else {
                    Globals.defaultError(getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<PageResponse> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });

    }
}
