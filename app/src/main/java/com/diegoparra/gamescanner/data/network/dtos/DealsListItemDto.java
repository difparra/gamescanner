package com.diegoparra.gamescanner.data.network.dtos;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class DealsListItemDto {

    @Nullable
    private final String internalName;
    @Nullable
    private final String title;
    @Nullable
    private final String metacriticLink;
    @Nullable
    @SerializedName("dealID")
    private final String dealId;
    @Nullable
    @SerializedName("storeID")
    private final String storeId;
    @Nullable
    @SerializedName("gameID")
    private final String gameId;
    @Nullable
    private final Float salePrice;
    @Nullable
    private final Float normalPrice;
    @Nullable
    private final Boolean isOnSale;
    @Nullable
    @SerializedName("savings")
    private final Float discountPercent;
    @Nullable
    private final Integer metacriticScore;
    @Nullable
    private final String steamRatingText;
    @Nullable
    private final Integer steamRatingPercent;
    @Nullable
    private final Long steamRatingCount;
    @Nullable
    @SerializedName("steamAppID")
    private final String steamAppId;
    @Nullable
    private final Long releaseDate;
    @Nullable
    private final Long lastChange;
    @Nullable
    private final Float dealRating;
    @Nullable
    private final String thumb;

    public DealsListItemDto(@Nullable String internalName, @Nullable String title, @Nullable String metacriticLink, @Nullable String dealId, @Nullable String storeId, @Nullable String gameId, @Nullable Float salePrice, @Nullable Float normalPrice, @Nullable Boolean isOnSale, @Nullable Float discountPercent, @Nullable Integer metacriticScore, @Nullable String steamRatingText, @Nullable Integer steamRatingPercent, @Nullable Long steamRatingCount, @Nullable String steamAppId, @Nullable Long releaseDate, @Nullable Long lastChange, @Nullable Float dealRating, @Nullable String thumb) {
        this.internalName = internalName;
        this.title = title;
        this.metacriticLink = metacriticLink;
        this.dealId = dealId;
        this.storeId = storeId;
        this.gameId = gameId;
        this.salePrice = salePrice;
        this.normalPrice = normalPrice;
        this.isOnSale = isOnSale;
        this.discountPercent = discountPercent;
        this.metacriticScore = metacriticScore;
        this.steamRatingText = steamRatingText;
        this.steamRatingPercent = steamRatingPercent;
        this.steamRatingCount = steamRatingCount;
        this.steamAppId = steamAppId;
        this.releaseDate = releaseDate;
        this.lastChange = lastChange;
        this.dealRating = dealRating;
        this.thumb = thumb;
    }

    @Nullable
    public String getInternalName() {
        return internalName;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getMetacriticLink() {
        return metacriticLink;
    }

    @Nullable
    public String getDealId() {
        return dealId;
    }

    @Nullable
    public String getStoreId() {
        return storeId;
    }

    @Nullable
    public String getGameId() {
        return gameId;
    }

    @Nullable
    public Float getSalePrice() {
        return salePrice;
    }

    @Nullable
    public Float getNormalPrice() {
        return normalPrice;
    }

    @Nullable
    public Boolean getOnSale() {
        return isOnSale;
    }

    @Nullable
    public Float getDiscountPercent() {
        return discountPercent;
    }

    @Nullable
    public Integer getMetacriticScore() {
        return metacriticScore;
    }

    @Nullable
    public String getSteamRatingText() {
        return steamRatingText;
    }

    @Nullable
    public Integer getSteamRatingPercent() {
        return steamRatingPercent;
    }

    @Nullable
    public Long getSteamRatingCount() {
        return steamRatingCount;
    }

    @Nullable
    public String getSteamAppId() {
        return steamAppId;
    }

    @Nullable
    public Long getReleaseDate() {
        return releaseDate;
    }

    @Nullable
    public Long getLastChange() {
        return lastChange;
    }

    @Nullable
    public Float getDealRating() {
        return dealRating;
    }

    @Nullable
    public String getThumb() {
        return thumb;
    }
}
