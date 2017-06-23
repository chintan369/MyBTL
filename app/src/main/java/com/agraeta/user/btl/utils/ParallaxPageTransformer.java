package com.agraeta.user.btl.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.agraeta.user.btl.R;

/**
 * Created by Nivida new on 01-Mar-17.
 */

public class ParallaxPageTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {

        int pageWidth = view.getWidth();

        if (position < -1) {
            view.setAlpha(1);
        } else if (position <= 1) { // [-1,1]
            view.findViewById(R.id.image).setTranslationX(-position * (pageWidth / 2));
        } else {
            view.setAlpha(1);
        }
    }
}
