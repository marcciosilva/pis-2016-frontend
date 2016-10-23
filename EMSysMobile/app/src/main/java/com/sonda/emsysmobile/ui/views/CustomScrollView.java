package com.sonda.emsysmobile.ui.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marccio on 10-Oct-16.
 */

public class CustomScrollView extends ScrollView {

    private List<View> mInterceptScrollViews = new ArrayList<>();

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public List<View> getmInterceptScrollViews() {
        return mInterceptScrollViews;
    }

    public void setmInterceptScrollViews(List<View> mInterceptScrollViews) {
        this.mInterceptScrollViews = mInterceptScrollViews;
    }

    public final void addInterceptScrollView(View view) {
        mInterceptScrollViews.add(view);
    }

    public final void removeInterceptScrollView(View view) {
        mInterceptScrollViews.remove(view);
    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent event) {

        // check if we have any views that should use their own scrolling
        if (mInterceptScrollViews.size() > 0) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Rect bounds = new Rect();

            for (View view : mInterceptScrollViews) {
                view.getHitRect(bounds);
                if (bounds.contains(x, y)) {
                    //were touching a view that should intercept scrolling
                    return false;
                }
            }
        }

        return super.onInterceptTouchEvent(event);
    }
}