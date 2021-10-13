package com.diegoparra.gamescanner.data.network.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class StoreDto {

    @SerializedName("storeID")
    private String storeId;
    private String storeName;
    private int isActive;
    private Map<String, String> images;

    public StoreDto(String storeId, String storeName, int isActive, Map<String, String> images) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.isActive = isActive;
        this.images = images;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public boolean isActive() {
        return isActive != 0;
    }

    public String getImgBanner() {
        String key = "banner";
        if(images != null && images.containsKey(key)) {
            return images.get(key);
        } else {
            return null;
        }
    }

    public String getImgLogo() {
        String key = "logo";
        if(images != null && images.containsKey(key)) {
            return images.get(key);
        } else {
            return null;
        }
    }

    public String getImgIcon() {
        String key = "icon";
        if(images != null && images.containsKey(key)) {
            return images.get(key);
        } else {
            return null;
        }
    }

}
