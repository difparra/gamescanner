package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return isActive == store.isActive && id.equals(store.id) && Objects.equals(name, store.name) && Objects.equals(bannerUrl, store.bannerUrl) && Objects.equals(logoUrl, store.logoUrl) && Objects.equals(iconUrl, store.iconUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive, bannerUrl, logoUrl, iconUrl);
    }
}
