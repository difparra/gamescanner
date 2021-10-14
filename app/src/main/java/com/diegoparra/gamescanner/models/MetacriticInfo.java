package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetacriticInfo that = (MetacriticInfo) o;
        return ratingPercent == that.ratingPercent && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, ratingPercent);
    }
}
