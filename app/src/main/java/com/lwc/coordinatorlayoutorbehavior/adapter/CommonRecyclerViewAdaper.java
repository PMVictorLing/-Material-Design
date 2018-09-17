package com.lwc.coordinatorlayoutorbehavior.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * recyclerView通用Adapter
 * <p>
 * Created by lingwancai on
 * 2018/9/14 15:18
 */
public abstract class CommonRecyclerViewAdaper<T> extends RecyclerView.Adapter<CommonRecyclerViewAdaper.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;

    //数据使用泛型
    private List<T> mDataList;

    //布局使用构造函数传递
    private int mLayoutId;

    //绑定数据，使用抽象方法回传
    public abstract void convert(ViewHolder holder, T item);

    public CommonRecyclerViewAdaper(List<T> mDataList, int mLayoutId, Context context) {
        this.mDataList = mDataList;
        this.mLayoutId = mLayoutId;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载布局，返回ViewHolder
        View view = mInflater.inflate(mLayoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

//    @Override 不然编译会报错
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        convert(holder, mDataList.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 增加ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        //存放view的集合
        private SparseArray<View> mViewArray;

        public ViewHolder(View itemView) {
            super(itemView);
            mViewArray = new SparseArray<>();
        }

        //设置textView文本 采用链式构造
        public ViewHolder setTextView(int textViewId, String string) {
            //根据id获取textView
            TextView textView = (TextView) getView(textViewId);
            textView.setText(string);
            return this;
        }

        //设置imageview 本地资源
        public ViewHolder setImageView(int imageViewId,int resourceId){
            ImageView imageView = getView(imageViewId);
            imageView.setImageResource(resourceId);
            return this;
        }

        //设置view组件是否显示
        public ViewHolder setViewVisibility(int viewId,int visibiliby){
            getView(viewId).setVisibility(visibiliby);
            return this;
        }

        //设置条目点击事件
        public void setItemOnClicklistener(View.OnClickListener listener){
            itemView.setOnClickListener(listener);
        }

        //根据id获取View
        private <T extends View> T getView(int viewId) {
            //从缓存中获取view
            View view = mViewArray.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                //存入view
                mViewArray.put(viewId,view);
            }
            return (T) view;
        }

    }


}
