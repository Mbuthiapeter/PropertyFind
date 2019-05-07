package com.friki.mbuthia.propertyfindapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SwipeAdapter extends PagerAdapter {

    private Context ctx;
    private LayoutInflater layoutInflater;
    ArrayList<String> imageResources;


    public SwipeAdapter(Context ctx, ArrayList<String> imageResources) {

        this.ctx = ctx;
        this.imageResources = imageResources;
    }

    @Override
    public int getCount() {
        return imageResources.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(ConstraintLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);

        ImageView imageView = (ImageView)item_view.findViewById(R.id.imageView);
        Picasso.get().load(imageResources.get(position)).fit().centerInside().into(imageView);
        //Glide.with(ctx).load(imageResources.get(position)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        //imageView.setImageResource(imageResources.get(position));
        container.addView(item_view);
        return item_view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
