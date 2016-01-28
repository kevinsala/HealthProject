package com.example.ksala.healthproject.Views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    private boolean swipeable;

    public CustomViewPager(Context context) {
        super(context);
        swipeable = true;
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        swipeable = true;
    }

    public boolean isSwipeable() {
        return swipeable;
    }

    public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (swipeable) return super.onInterceptTouchEvent(event);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        if (swipeable) return super.onTouchEvent(event);
        return false;
    }
}