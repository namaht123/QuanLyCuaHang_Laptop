package com.example.duan1_nhom3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.duan1_nhom3.R;

import java.util.ArrayList;

public class SlideShowPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Integer> mImageIds;

    public SlideShowPagerAdapter(Context context, ArrayList<Integer> imageIds) {
        mContext = context;
        mImageIds = imageIds;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.slide_show_item, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(mImageIds.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImageIds.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

