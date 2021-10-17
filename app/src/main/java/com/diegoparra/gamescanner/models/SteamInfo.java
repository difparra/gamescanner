package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class SteamInfo {

    @Nullable private final String ratingText;
    @Nullable private final Integer ratingPercent;
    @Nullable private final Long ratingCount;

    public SteamInfo(@Nullable String ratingText, @Nullable Integer ratingPercent, @Nullable Long ratingCount) {
        this.ratingText = ratingText;
        this.ratingPercent = ratingPercent;
        this.ratingCount = ratingCount;
    }

    @Nullable
    public String getRatingText() {
        return ratingText;
    }

    @Nullable
    public Integer getRatingPercent() {
        return ratingPercent;
    }

    @Nullable
    public Long getRatingCount() {
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
        return Objects.equals(ratingText, steamInfo.ratingText) && Objects.equals(ratingPercent, steamInfo.ratingPercent) && Objects.equals(ratingCount, steamInfo.ratingCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingText, ratingPercent, ratingCount);
    }
}
