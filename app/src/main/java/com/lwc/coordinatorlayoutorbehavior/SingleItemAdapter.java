package com.lwc.coordinatorlayoutorbehavior;

import android.content.Context;

import com.lwc.coordinatorlayoutorbehavior.adapter.CommonRecyclerViewAdaper;
import com.lwc.coordinatorlayoutorbehavior.adapter.ImageViewLoader;

import java.util.List;

/**
 * 单个item布局测试
 *
 * Created by lingwancai on
 * 2018/9/19 14:02
 */
public class SingleItemAdapter extends CommonRecyclerViewAdaper<String> {

    public SingleItemAdapter(Context context, List<String> mDataList, int mLayoutId) {
        super(context, mDataList, mLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, String item) {
        holder.setTextView(R.id.tv_content,"排序："+item);

        holder.setImagePath(R.id.iv_pic,new ImageViewLoader("http://weitu-650-water.bj.bcebos.com/233109222803.jpg"));

    }

}
