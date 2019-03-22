package com.example.udaysaikumar.clgattendance.CustomViewPage;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

public class PagerTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position < 0) {
            view.setScrollX((int)((float)(view.getWidth()) * position));
        } else if (position > 0) {
            view.setScrollX(-(int) ((float) (view.getWidth()) * -position));
        } else {
            view.setScrollX(0);
        }
    }
}
