package com.agraeta.user.btl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.agraeta.user.btl.adapters.TabsAdapter;
import com.agraeta.user.btl.fragment.RegisteredUserListFragment;
import com.agraeta.user.btl.fragment.UnRegisteredUserListFragment;

public class TourActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_EDIT = 36;
    public String stateID = "", tourID = "";
    TabLayout tabs;
    ViewPager viewPager;
    TabsAdapter adapter;
    ImageView img_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        Intent intent = getIntent();
        stateID = intent.getStringExtra("stateID");
        tourID = intent.getStringExtra("tourID");

        fetchIDs();
        setActionBar();
    }

    private void fetchIDs() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        adapter = new TabsAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegisteredUserListFragment(), "Registered User");
        adapter.addFragment(new UnRegisteredUserListFragment(), "Un-Registered User");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    img_add.setVisibility(View.GONE);
                } else {
                    img_add.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        img_add = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_notification = (ImageView) mCustomView.findViewById(R.id.img_notification);
        FrameLayout unread = (FrameLayout) mCustomView.findViewById(R.id.unread);

        image_drawer.setImageResource(R.drawable.ic_action_btl_back);
        img_add.setImageResource(R.drawable.add_icon);
        img_add.setVisibility(View.GONE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SkipOrderUnregisterUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, REQUEST_ADD_EDIT);
            }
        });

        img_notification.setVisibility(View.GONE);
        unread.setVisibility(View.GONE);

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
}
