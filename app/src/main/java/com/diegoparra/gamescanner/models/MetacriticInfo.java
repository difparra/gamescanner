package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

public class MetacriticInfo {

    private String url;
    private int ratingPercent;

    public MetacriticInfo(String url, int ratingPercent) {
        this.url = url;
        this.ratingPercent = ratingPercent;
    }

    public String getUrl() {
        return url;
    }

    public int getRatingPercent() {
        return ratingPercent;
    }

    @NonNull
    @Override
    public String toString() {
        return "MetacriticInfo{" +
                "url='" + url + '\'' +
                ", ratingPercent=" + ratingPercent +
                '}';
    }
}
