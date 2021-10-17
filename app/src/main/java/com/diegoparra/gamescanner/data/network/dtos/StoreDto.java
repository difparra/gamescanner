package com.diegoparra.gamescanner.data.network.dtos;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class StoreDto {

    @Nullable
    @SerializedName("storeID")
    private final String storeId;
    @Nullable
    private final String storeName;
    @Nullable
    private final Integer isActive;
    @Nullable
    private final Map<String, String> images;

    public StoreDto(@Nullable String storeId, @Nullable String storeName, int isActive, @Nullable Map<String, String> images) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.isActive = isActive;
        this.images = images;
    }

    @Nullable
    public String getStoreId() {
        return storeId;
    }

    @Nullable
    public String getStoreName() {
        return storeName;
    }

    @Nullable
    public Boolean isActive() {
        if (isActive == null) {
            return null;
        } else {
            return isActive != 0;
        }
    }

    @Nullable
    public String getImgBanner() {
        String key = "banner";
        if (images != null && images.containsKey(key)) {
            return images.get(key);
        } else {
            return null;
        }
    }

    @Nullable
    public String getImgLogo() {
        String key = "logo";
        if (images != null && images.containsKey(key)) {
            return images.get(key);
        } else {
            return null;
        }
    }

    @Nullable
    public String getImgIcon() {
        String key = "icon";
        if (images != null && images.containsKey(key)) {
            return images.get(key);
        } else {
            return null;
        }
    }

}
