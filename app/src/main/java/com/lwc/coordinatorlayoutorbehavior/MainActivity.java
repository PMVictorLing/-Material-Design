package com.lwc.coordinatorlayoutorbehavior;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lwc.coordinatorlayoutorbehavior.adapter.GridlayoutItemDecoration;
import com.lwc.coordinatorlayoutorbehavior.adapter.LinealayoutItemDecoration;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ArrayList<String> mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        StatusBarUtils.setStatusBarTranslucent(this);
        StatusBarUtils.setStatusBarColor(this, getResources().getColor(R.color.status_bar_color));

        //初始化数据
        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerview);
        //设置布局管理器 new LinearLayoutManager(this)
        // new GridLayoutManager(this,2) -根据每列来确定从哪里开始绘制
        // new StaggeredGridLayoutManager()
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //设置分割线
//        mRecyclerView.addItemDecoration(new ItemDecorationDider());  LinealayoutItemDecoration
        mRecyclerView.addItemDecoration(new GridlayoutItemDecoration(this,R.drawable.item_listview_drawabler));
        mRecyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_recycler_view_layout, parent, false);
                ViewHolder viewHolder = new ViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                String content = mListData.get(position);
                holder.mTvContent.setText(content);

            }

            @Override
            public int getItemCount() {
                return mListData.size();
            }
        });

    }

    private void initData() {
        mListData = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            mListData.add("" + (char) i);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    /**
     * 自定义分割线 --- 简单原来
     */
    private class ItemDecorationDider extends RecyclerView.ItemDecoration {

        private Paint mPaint;

        @SuppressLint("ResourceAsColor")
        public ItemDecorationDider() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(R.color.colorPrimary);
        }

        /**
         * 绘制分割线的区域
         *
         * @param c
         * @param parent
         * @param state
         */
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            //在每个item头部绘制
            int childCount = parent.getChildCount();

            //计算区域
            Rect rect = new Rect();
            rect.left = parent.getPaddingLeft();
            rect.right = parent.getWidth() - parent.getPaddingRight();

            //第一个头部不绘制 --- 如new GridLayoutManager(this,2) -根据每列来确定从哪里开始绘制 i=2
            for (int i = 2; i < childCount; i++) {
                //分割线的底部bottom就是itemView的头部
                rect.bottom = parent.getChildAt(i).getTop();
                rect.top = rect.bottom - 10;
                c.drawRect(rect, mPaint);
            }

        }


        /**
         * 基本操作，留出分割线的位置
         *
         * @param outRect
         * @param view
         * @param parent
         * @param state
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //有两种思路：一个是recyclerview的item底部bottom绘制分割线，另一个是top绘制

            //1.绘制底部分割线--会有问题可以试试


            //2.另一个是top绘制
            //获取item的position
            int position = parent.getChildAdapterPosition(view);
            Log.d(TAG, "position ->" + position + " itemCount ->" + parent.getChildCount());
            //保证第一条不需要绘制
            if (position != 0) {
                outRect.top = 10;
            }
        }
    }
}
