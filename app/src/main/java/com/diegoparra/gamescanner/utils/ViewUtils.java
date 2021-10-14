package com.diegoparra.gamescanner.utils;

import android.view.View;

public class ViewUtils {

    public static void isVisible(View view, boolean isVisible) {
        if (isVisible && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        } else if (!isVisible && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

}
