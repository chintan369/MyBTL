package com.agraeta.user.btl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.agraeta.user.btl.model.VideoResponse;

public class VideoGalleryDetailActivity extends AppCompatActivity {

    WebView web_videoGallery;
    TextView txt_productName,txt_productCode,txt_shortDescription;

    VideoResponse.Video videoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery_detail);

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
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
