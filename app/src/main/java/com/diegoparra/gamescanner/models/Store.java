package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Store {

    @NonNull
    private final String id;
    @NonNull
    private final String name;
    @Nullable
    private final Boolean isActive;
    @Nullable
    private final String bannerUrl;
    @Nullable
    private final String logoUrl;
    @Nullable
    private final String iconUrl;

    public Store(@NonNull String id,
                 @NonNull String name,
                 @Nullable Boolean isActive,
                 @Nullable String bannerUrl,
                 @Nullable String logoUrl,
                 @Nullable String iconUrl) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.bannerUrl = bannerUrl;
        this.logoUrl = logoUrl;
        this.iconUrl = iconUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public Boolean isActive() {
        return isActive;
    }

    @Nullable
    public String getBannerUrl() {
        return bannerUrl;
    }

    @Nullable
    public String getLogoUrl() {
        return logoUrl;
    }

    @Nullable
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
        return id.equals(store.id) && name.equals(store.name) && Objects.equals(isActive, store.isActive) && Objects.equals(bannerUrl, store.bannerUrl) && Objects.equals(logoUrl, store.logoUrl) && Objects.equals(iconUrl, store.iconUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive, bannerUrl, logoUrl, iconUrl);
    }
}
