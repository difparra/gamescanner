package com.diegoparra.gamescanner.data.network.dtos;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class DealDto {

    @Nullable
    @SerializedName("storeID")
    private final String storeId;
    @Nullable
    @SerializedName("dealID")
    private final String dealId;
    @Nullable
    @SerializedName("retailPrice")
    private final Float normalPrice;
    @Nullable
    @SerializedName("price")
    private final Float salePrice;
    @Nullable
    @SerializedName("savings")
    private final Float discountPercent;

    public DealDto(@Nullable String storeId, @Nullable String dealId, @Nullable Float normalPrice, @Nullable Float salePrice, @Nullable Float discountPercent) {
        this.storeId = storeId;
        this.dealId = dealId;
        this.normalPrice = normalPrice;
        this.salePrice = salePrice;
        this.discountPercent = discountPercent;
    }

    @Nullable
    public String getStoreId() {
        return storeId;
    }

    @Nullable
    public String getDealId() {
        return dealId;
    }

    @Nullable
    public Float getNormalPrice() {
        return normalPrice;
    }

    @Nullable
    public Float getSalePrice() {
        return salePrice;
    }

    @Nullable
    public Float getDiscountPercent() {
        return discountPercent;
    }
}
