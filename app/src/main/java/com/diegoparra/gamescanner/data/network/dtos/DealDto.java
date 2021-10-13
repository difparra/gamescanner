package com.diegoparra.gamescanner.data.network.dtos;

import com.google.gson.annotations.SerializedName;

public class DealDto {

    @SerializedName("storeID")
    private String storeId;
    @SerializedName("dealID")
    private String dealId;
    @SerializedName("retailPrice")
    private float normalPrice;
    @SerializedName("price")
    private float salePrice;
    @SerializedName("savings")
    private float discountPercent;

    public DealDto(String storeId, String dealId, float normalPrice, float salePrice, float discountPercent) {
        this.storeId = storeId;
        this.dealId = dealId;
        this.normalPrice = normalPrice;
        this.salePrice = salePrice;
        this.discountPercent = discountPercent;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getDealId() {
        return dealId;
    }

    public float getNormalPrice() {
        return normalPrice;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }
}
