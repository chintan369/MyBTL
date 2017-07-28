package com.agraeta.user.btl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.agraeta.user.btl.adapters.CatalogAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.CatalogResponse;
import com.agraeta.user.btl.model.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadsActivity extends AppCompatActivity {

    GridView list_downloads;
    AdminAPI adminAPI;
    List<CatalogResponse.Catalog> catalogList = new ArrayList<>();
    CatalogAdapter catalogAdapter;

    Custom_ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        dialog=new Custom_ProgressDialog(this,"");
        dialog.setCancelable(false);


        adminAPI= ServiceGenerator.getAPIServiceClass();
        setActionBar();
        fetchIDs();
    }

    private void fetchIDs() {
        list_downloads = (GridView) findViewById(R.id.list_downloads);
        catalogAdapter = new CatalogAdapter(catalogList, this);
        list_downloads.setAdapter(catalogAdapter);

        list_downloads.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(Globals.IMAGE_LINK + catalogList.get(position).getFile_url()), "application/pdf");
                startActivity(intent);
            }
        });

        dialog.show();
        Call<CatalogResponse> catalogResponseCall = adminAPI.catalogResponseCall();
        catalogResponseCall.enqueue(new Callback<CatalogResponse>() {
            @Override
            public void onResponse(Call<CatalogResponse> call, Response<CatalogResponse> response) {
                dialog.dismiss();
                CatalogResponse catalogResponse = response.body();
                if (catalogResponse != null) {
                    if (catalogResponse.isStatus()) {
                        catalogList.addAll(catalogResponse.getData());
                    }
                    else {
                        Globals.Toast2(getApplicationContext(), catalogResponse.getMessage());
                    }
                } else {
                    Globals.defaultError(getApplicationContext());
                }

                catalogAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CatalogResponse> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);

        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        FrameLayout unread = (FrameLayout) mCustomView.findViewById(R.id.unread);

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_notification.setVisibility(View.GONE);
        unread.setVisibility(View.GONE);

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
