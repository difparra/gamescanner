package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class MetacriticInfo {

    @Nullable
    private final String url;
    @Nullable
    private final Integer ratingPercent;

    public MetacriticInfo(@Nullable String url, @Nullable Integer ratingPercent) {
        this.url = url;
        this.ratingPercent = ratingPercent;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    @Nullable
    public Integer getRatingPercent() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetacriticInfo that = (MetacriticInfo) o;
        return Objects.equals(url, that.url) && Objects.equals(ratingPercent, that.ratingPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, ratingPercent);
    }
}
