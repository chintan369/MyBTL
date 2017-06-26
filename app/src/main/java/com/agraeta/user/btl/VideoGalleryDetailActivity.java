package com.agraeta.user.btl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.agraeta.user.btl.model.VideoResponse;

public class VideoGalleryDetailActivity extends AppCompatActivity {

    WebView web_videoGallery;
    TextView txt_productName,txt_productCode,txt_shortDescription;

    VideoResponse.Video videoDetail;
    AppPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery_detail);

        prefs = new AppPrefs(this);

        Intent intent=getIntent();
        videoDetail=(VideoResponse.Video) intent.getSerializableExtra("video");

        fetchIDs();
    }

    private void fetchIDs() {
        web_videoGallery=(WebView) findViewById(R.id.web_videoGallery);
        txt_productName=(TextView) findViewById(R.id.txt_productName);
        txt_productCode=(TextView) findViewById(R.id.txt_productCode);
        txt_shortDescription=(TextView) findViewById(R.id.txt_shortDescription);

        String videoUrl=C.getYoutubeEmbedLink(videoDetail.getUrl());

        Log.e("VideoURL",videoUrl);

        web_videoGallery.getSettings().setAllowContentAccess(true);
        web_videoGallery.getSettings().setJavaScriptEnabled(true);
        web_videoGallery.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        web_videoGallery.loadUrl(videoUrl);

        txt_productName.setText(videoDetail.getProduct_name());
        txt_productCode.setText(videoDetail.getProduct_code());
        txt_shortDescription.setText(videoDetail.getShort_description());

        txt_productCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetailPage();
            }
        });

        txt_productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetailPage();
            }
        });
    }


    private void goToDetailPage() {
        prefs.setproduct_id(videoDetail.getProduct_id());
        prefs.setRef_Detail("");
        Intent i = new Intent(getApplicationContext(), BTLProduct_Detail.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
