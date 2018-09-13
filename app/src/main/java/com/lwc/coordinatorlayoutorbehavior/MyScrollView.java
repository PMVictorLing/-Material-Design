package com.lwc.coordinatorlayoutorbehavior;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by lingwancai on
 * 2018/9/13 15:05
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 不断监听回调
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mChangeListener != null) {
            mChangeListener.onScroll(l,t,oldl,oldt);
        }
    }

    interface ScrollChangeListener {
        void onScroll(int l, int t, int oldl, int oldt);
    }

    private ScrollChangeListener mChangeListener;

    public void setOnScrollChangeListener(ScrollChangeListener changeListener) {
        mChangeListener = changeListener;
    }
}
