package com.example.namnguyen.demogooglemap.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.namnguyen.demogooglemap.R;

import java.util.List;

/**
 * Created by Nam Nguyen on 17/07/2016.
 */
public class ImageSliderAdapter extends PagerAdapter {

    Context context;
    List<String> imageList;
//    String photo;

    public ImageSliderAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
//        this.photo = photo;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_image, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Glide.with(context).load(imageList.get(position)).into(imageView);
//        Glide.with(context).load(photo).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((View) object);
    }
}
