package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Deal {

    private String dealId;
    private String gameId;
    private String storeId;
    private float normalPrice;
    private float salePrice;
    private float discountPercent;
    private Instant lastChange;

    public Deal(String dealId, String gameId, String storeId, float normalPrice, float salePrice, float discountPercent, Instant lastChange) {
        this.dealId = dealId;
        this.gameId = gameId;
        this.storeId = storeId;
        this.normalPrice = normalPrice;
        this.salePrice = salePrice;
        this.discountPercent = discountPercent;
        this.lastChange = lastChange;
    }

    public String getDealId() {
        return dealId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getStoreId() {
        return storeId;
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

    public LocalDate getLastChange() {
        return LocalDateTime.ofInstant(lastChange, ZoneId.systemDefault()).toLocalDate();
    }


    @NonNull
    @Override
    public String toString() {
        return "Deal{" +
                "dealId='" + dealId + '\'' +
                ", gameId='" + gameId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", normalPrice=" + normalPrice +
                ", salePrice=" + salePrice +
                ", discountPercent=" + discountPercent +
                ", lastChange=" + lastChange +
                '}';
    }
}
