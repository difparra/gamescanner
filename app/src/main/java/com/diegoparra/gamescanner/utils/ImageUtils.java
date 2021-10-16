package com.diegoparra.gamescanner.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.diegoparra.gamescanner.R;

public class ImageUtils {

    public static void loadImageWithPlaceholderAndError(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(imageView);
    }

}
