package com.lwc.coordinatorlayoutorbehavior.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lwc.coordinatorlayoutorbehavior.R;

/**
 * Created by lingwancai on
 * 2018/9/19 10:50
 */
public class ImageViewLoader extends CommonRecyclerViewAdaper.HolderImagerLoader {
    public ImageViewLoader(String path) {
        super(path);
    }

    @Override
    public void loadImage(ImageView imageView, String path) {
        Glide.with(imageView.getContext()).load(path).placeholder(R.mipmap.ic_launcher).into(imageView);
    }
}
