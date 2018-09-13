package com.lwc.coordinatorlayoutorbehavior;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by lingwancai on
 * 2018/9/13 10:44
 */
public class StatusBarUtils {
    private static final String TAG = "StatusBarUtils";

    /**
     * 设置activity全屏
     *
     * @param activity
     */
    public static void setStatusBarTranslucent(Activity activity) {
        //5.0以上 21api
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //4.4 - 5.0之间采用一个技巧，首先弄成全屏，在状态栏的部分添加一个布局
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     *
     * 设置statusBar color
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity,int color){

        //5.0 以上 直接调用系统提供的方法 setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().setStatusBarColor(color);
        }

        //4.4 - 5.0之间采用一个技巧，首先弄成全屏，在状态栏的部分添加一个布局
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //在状态栏的部分加一个布局 参考setContentView源码
            //高度是状态的高度
            View view = new View(activity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHight(activity));
            view.setLayoutParams(params);
            view.setBackgroundColor(color);

            //android:fitsSystemWindows="true" 每个布局都要写
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view);

            //获取activity中setContentView布局的根布局
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            contentView.setPadding(0,getStatusBarHight(activity),0,0);
        }
    }

    /**
     * statusBar 高度
     *
     * @return
     */
    private static int getStatusBarHight(Activity activity) {
        //根据资源获取id
        Resources resources = activity.getResources();
        int statusBarHeightid = resources.getIdentifier("status_bar_height","dimen","android");
        Log.e(TAG,statusBarHeightid +"->"+resources.getDimensionPixelOffset(statusBarHeightid));
        return resources.getDimensionPixelOffset(statusBarHeightid);
    }
}
