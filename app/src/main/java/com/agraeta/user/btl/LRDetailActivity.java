package com.agraeta.user.btl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LRDetailActivity extends AppCompatActivity {

    TextView txt_transportarName,txt_designation, txt_LRNo, txt_LRDate;
    ImageView img_download, img_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrdetail);
    }
}
