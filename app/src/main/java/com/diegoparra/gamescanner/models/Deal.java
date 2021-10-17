package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class Deal {

    @NonNull
    private final String dealId;
    @NonNull
    private final String gameId;
    @NonNull
    private final String storeId;
    private final float normalPrice;
    private final float salePrice;
    @Nullable
    private final Instant lastChange;
    @Nullable
    private final String goToDealLink;

    public Deal(@NonNull String dealId,
                @NonNull String gameId,
                @NonNull String storeId,
                float normalPrice,
                float salePrice,
                @Nullable Instant lastChange,
                @Nullable String goToDealLink) {
        this.dealId = dealId;
        this.gameId = gameId;
        this.storeId = storeId;
        this.normalPrice = normalPrice;
        this.salePrice = salePrice;
        this.lastChange = lastChange;
        this.goToDealLink = goToDealLink;
    }

    @NonNull
    public String getDealId() {
        return dealId;
    }

    @NonNull
    public String getGameId() {
        return gameId;
    }

    @NonNull
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
        return (1 - (salePrice / normalPrice)) * 100;
    }

    public boolean isOnSale() {
        return normalPrice > salePrice + 0.01;
    }

    @Nullable
    public LocalDate getDateLastChange() {
        if (lastChange != null) {
            return LocalDateTime.ofInstant(lastChange, ZoneId.systemDefault()).toLocalDate();
        } else {
            return null;
        }
    }

    @Nullable
    public Duration getTimeSinceLastChange() {
        if (lastChange != null) {
            return Duration.between(lastChange, Instant.now());
        } else {
            return null;
        }
    }

    @Nullable
    public String getGoToDealLink() {
        return goToDealLink;
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
                ", lastChange=" + lastChange +
                ", goToDealLink='" + goToDealLink + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deal deal = (Deal) o;
        return Float.compare(deal.normalPrice, normalPrice) == 0 && Float.compare(deal.salePrice, salePrice) == 0 && dealId.equals(deal.dealId) && gameId.equals(deal.gameId) && storeId.equals(deal.storeId) && Objects.equals(lastChange, deal.lastChange) && Objects.equals(goToDealLink, deal.goToDealLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dealId, gameId, storeId, normalPrice, salePrice, lastChange, goToDealLink);
    }
}
