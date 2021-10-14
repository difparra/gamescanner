package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SteamInfo steamInfo = (SteamInfo) o;
        return ratingPercent == steamInfo.ratingPercent && ratingCount == steamInfo.ratingCount && Objects.equals(ratingText, steamInfo.ratingText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingText, ratingPercent, ratingCount);
    }
}
