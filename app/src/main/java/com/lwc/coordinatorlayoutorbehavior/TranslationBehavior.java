package com.lwc.coordinatorlayoutorbehavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * FloatingActionButton 位移的自定义 Behavior
 * Created by lingwancai on
 * 2018/9/13 16:07
 */
public class TranslationBehavior extends FloatingActionButton.Behavior {

    private static final String TAG = "TranslationBehavior";
    //判断是否显示
    private boolean misShow = false;

    public TranslationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 当coordinatorLayout 的子View试图开始嵌套滑动的时候被调用。当返回值为true的时候表明
     * coordinatorLayout 充当nested scroll parent 处理这次滑动，需要注意的是只有当返回值为true
     * 的时候，Behavior 才能收到后面的一些nested scroll 事件回调（如：onNestedPreScroll、onNestedScroll等）
     * 这个方法有个重要的参数nestedScrollAxes，表明处理的滑动的方向。
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //Axes 滑动关联的轴，我们只关心垂直的滑动
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * 进行嵌套滚动时被调用
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param type
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        Log.e(TAG,"dyConsumed ->"+dyConsumed+"");
        //根据滑动情况执行动画 一个显示 一个隐藏
        if (dyConsumed > 0) {//往上滑动 隐藏
            if (!misShow) {
                //((CoordinatorLayout.LayoutParams) child.getLayoutParams()).bottomMargin 意思是FloatingActionButton底部距离
                Log.e(TAG,"((CoordinatorLayout.LayoutParams) child.getLayoutParams()).bottomMargin ->"+((CoordinatorLayout.LayoutParams) child.getLayoutParams()).bottomMargin+"");
                int transY = ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).bottomMargin + child.getMeasuredHeight();
                child.animate().translationY(transY).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        misShow = true;
                    }
                }).setDuration(500).start();
            }
        }
        if (dyConsumed < 0){//往下滑动 显示
            if (misShow){
                child.animate().translationY(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        misShow = false;
                    }
                }).setDuration(500).start();
            }
        }
    }
}
