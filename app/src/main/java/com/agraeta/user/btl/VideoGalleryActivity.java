package com.agraeta.user.btl;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.agraeta.user.btl.adapters.VideoGalleryAdapter;
import com.agraeta.user.btl.model.AdminAPI;
import com.agraeta.user.btl.model.ServiceGenerator;
import com.agraeta.user.btl.model.VideoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoGalleryActivity extends AppCompatActivity {

    ListView list_Videos;
    VideoGalleryAdapter videoAdapter;
    List<VideoResponse.Video> videoList=new ArrayList<>();

    AdminAPI adminAPI;
    Custom_ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);

        adminAPI= ServiceGenerator.getAPIServiceClass();
        dialog=new Custom_ProgressDialog(this,"");
        dialog.setCancelable(false);
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

                onBackPressed();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }


    private void fetchIDs() {
        list_Videos=(ListView) findViewById(R.id.list_Videos);
        videoAdapter=new VideoGalleryAdapter(videoList,this);
        list_Videos.setAdapter(videoAdapter);

        dialog.show();
        Call<VideoResponse> videoResponseCall=adminAPI.videoResponseCall();
        videoResponseCall.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                dialog.dismiss();
                VideoResponse videoResponse=response.body();
                if(videoResponse!=null){
                    if(videoResponse.isStatus()){
                        videoList.addAll(videoResponse.getData());
                    }
                    else {
                        Globals.Toast2(getApplicationContext(),videoResponse.getMessage());
                    }
                }
                else {
                    Globals.defaultError(getApplicationContext());
                }

                videoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                dialog.dismiss();
                Globals.showError(t,getApplicationContext());
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
