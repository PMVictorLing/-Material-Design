package com.lwc.coordinatorlayoutorbehavior.adapter;

import android.content.Context;

import com.lwc.coordinatorlayoutorbehavior.R;
import com.lwc.coordinatorlayoutorbehavior.bean.ChatItembean;

import java.util.List;

/**
 * 多布局item
 * <p>
 * Created by lingwancai on
 * 2018/9/19 16:06
 */
public class MultiItemAdatpter extends CommonRecyclerViewAdaper<ChatItembean> {


    public MultiItemAdatpter(Context context, List<ChatItembean> mDataList, MulitiTypeSupport typeSupport) {
        super(context, mDataList, typeSupport);
    }

    @Override
    public void convert(ViewHolder holder, ChatItembean item) {
        if (item.getChatId() == 1){
            holder.setTextView(R.id.tv_content,item.getContent());
            holder.setImagePath(R.id.iv_pic,new ImageViewLoader("http://img03.tooopen.com/uploadfile/downs/images/20110714/sy_20110714135215645030.jpg"));
        } else {
            holder.setTextView(R.id.tv_content,item.getContent());
            holder.setImagePath(R.id.iv_pic,new ImageViewLoader("http://weitu-650-water.bj.bcebos.com/233109222803.jpg"));
        }

    }

}
