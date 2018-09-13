package com.lwc.coordinatorlayoutorbehavior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ScrollViewMainActivity extends AppCompatActivity {
    private LinearLayout mTitlerBar;
    private MyScrollView mScrollview;
    private ImageView mIvPic;
    private int mImageViewHeight;
    private int mTitleBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_main);
        StatusBarUtils.setStatusBarTranslucent(this);
        //QQ效果
        // 1.不断监听ScrollView的滚动，判断当前滚动的位置跟头部Imageview比较计算出背景透明度
        // 2.自定义behavior 参考简书

        //1.刚刚进来背景设置成完全透明
        mTitlerBar = (LinearLayout) this.findViewById(R.id.ll_title_bar);
        mTitlerBar.getBackground().setAlpha(0);

        mIvPic = (ImageView) this.findViewById(R.id.iv_pic);

        //获取图片高度
        mIvPic.post(new Runnable() {
            @Override
            public void run() {
                mImageViewHeight = mIvPic.getMeasuredHeight();
            }
        });

        //获取titleBar高度
        mTitlerBar.post(new Runnable() {
            @Override
            public void run() {
                mTitleBarHeight = mTitlerBar.getMeasuredHeight();
            }
        });

        //不断监听ScrollView的滚动，判断当前滚动的位置跟头部Imageview比较计算出背景透明度
        mScrollview = (MyScrollView) this.findViewById(R.id.scroll_view);
        mScrollview.setOnScrollChangeListener(new MyScrollView.ScrollChangeListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                if (mImageViewHeight == 0)
                    return;
                //获取图片的高度，根据当前滚动的位置，计算alpha值 具体还应该减去titleBar的高度
                float alpha = (float) t / (mImageViewHeight - mTitleBarHeight);

                //判断小于0时
                if (alpha <= 0) {
                    alpha = 0;
                }

                if (alpha > 1) {
                    alpha = 1;
                }
                mTitlerBar.getBackground().setAlpha((int) (alpha * 255));


            }
        });
    }
}
