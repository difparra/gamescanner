package com.diegoparra.gamescanner.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.diegoparra.gamescanner.R;

import java.net.UnknownHostException;

public class ErrorUtils {

    public static String getMessage(@NonNull Throwable throwable, @NonNull Context context) {
        if (throwable instanceof UnknownHostException) {
            return context.getString(R.string.network_connection_error);
        } else if (throwable.getMessage() != null) {
            return throwable.getMessage();
        } else {
            return context.getString(R.string.unknown_error);
        }
    }

}
