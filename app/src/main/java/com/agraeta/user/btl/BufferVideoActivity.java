package com.agraeta.user.btl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.agraeta.user.btl.model.VideoResponse;

public class BufferVideoActivity extends AppCompatActivity {

    VideoResponse.Video videoDetail;
    AppPrefs prefs;
    TextView txt_productName, txt_productCode, txt_shortDescription;
    private WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    private myWebViewClient mWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_video);


        prefs = new AppPrefs(this);
        Intent intent = getIntent();
        videoDetail = (VideoResponse.Video) intent.getSerializableExtra("video");

        setActionBar();
        fetchIDs();


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

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BufferVideoActivity.this, MainPage_drawer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void fetchIDs() {

        customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
        webView = (WebView) findViewById(R.id.webView);
        txt_productName = (TextView) findViewById(R.id.txt_productName);
        txt_productCode = (TextView) findViewById(R.id.txt_productCode);
        txt_shortDescription = (TextView) findViewById(R.id.txt_shortDescription);


        mWebViewClient = new myWebViewClient();
        webView.setWebViewClient(mWebViewClient);

        String videoUrl = C.getYoutubeEmbedLink(videoDetail.getUrl());

        mWebChromeClient = new myWebChromeClient();
        webView.setWebChromeClient(mWebChromeClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSaveFormData(true);
        webView.loadUrl(videoUrl);


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

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        if (inCustomView()) {
            hideCustomView();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {

                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goToDetailPage() {
        prefs.setproduct_id(videoDetail.getProduct_id());
        prefs.setRef_Detail("");
        Intent i = new Intent(getApplicationContext(), BTLProduct_Detail.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            webView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(BufferVideoActivity.this);
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            webView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }


}
