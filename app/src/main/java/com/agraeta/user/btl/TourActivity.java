package com.agraeta.user.btl;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.agraeta.user.btl.adapters.TabsAdapter;
import com.agraeta.user.btl.fragment.RegisteredUserListFragment;
import com.agraeta.user.btl.fragment.UnRegisteredUserListFragment;

public class TourActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager viewPager;
    TabsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        fetchIDs();
    }

    private void fetchIDs() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        adapter = new TabsAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegisteredUserListFragment(), "Registered User");
        adapter.addFragment(new UnRegisteredUserListFragment(), "Un-Registered User");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }
}
