package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

public class SteamInfo {

    private String ratingText;
    private int ratingPercent;
    private long ratingCount;

    public SteamInfo(String ratingText, int ratingPercent, long ratingCount) {
        this.ratingText = ratingText;
        this.ratingPercent = ratingPercent;
        this.ratingCount = ratingCount;
    }

    public String getRatingText() {
        return ratingText;
    }

    public int getRatingPercent() {
        return ratingPercent;
    }

    public long getRatingCount() {
        return ratingCount;
    }

    @NonNull
    @Override
    public String toString() {
        return "SteamInfo{" +
                "ratingText='" + ratingText + '\'' +
                ", ratingPercent=" + ratingPercent +
                ", ratingCount=" + ratingCount +
                '}';
    }
}
