package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

public class Store {

    private String id;
    private String name;
    private boolean isActive;
    private String bannerUrl;
    private String logoUrl;
    private String iconUrl;

    public Store(String id, String name, boolean isActive, String bannerUrl, String logoUrl, String iconUrl) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.bannerUrl = bannerUrl;
        this.logoUrl = logoUrl;
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
