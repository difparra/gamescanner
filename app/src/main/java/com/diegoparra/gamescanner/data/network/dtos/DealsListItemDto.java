package com.diegoparra.gamescanner.data.network.dtos;

import com.google.gson.annotations.SerializedName;

public class DealsListItemDto {

    private String internalName;
    private String title;
    private String metacriticLink;
    @SerializedName("dealID")
    private String dealId;
    @SerializedName("storeID")
    private String storeId;
    @SerializedName("gameID")
    private String gameId;
    private float salePrice;
    private float normalPrice;
    private boolean isOnSale;
    @SerializedName("savings")
    private float discountPercent;
    private int metacriticScore;
    private String steamRatingText;
    private int steamRatingPercent;
    private long steamRatingCount;
    @SerializedName("steamAppID")
    private String steamAppId;
    private long releaseDate;
    private long lastChange;
    private float dealRating;
    private String thumb;

    public DealsListItemDto(String internalName, String title, String metacriticLink, String dealId, String storeId, String gameId, float salePrice, float normalPrice, boolean isOnSale, float discountPercent, int metacriticScore, String steamRatingText, int steamRatingPercent, long steamRatingCount, String steamAppId, long releaseDate, long lastChange, float dealRating, String thumb) {
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

    public String getInternalName() {
        return internalName;
    }

    public String getTitle() {
        return title;
    }

    public String getMetacriticLink() {
        return metacriticLink;
    }

    public String getDealId() {
        return dealId;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getGameId() {
        return gameId;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public float getNormalPrice() {
        return normalPrice;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public String getSteamRatingText() {
        return steamRatingText;
    }

    public int getSteamRatingPercent() {
        return steamRatingPercent;
    }

    public long getSteamRatingCount() {
        return steamRatingCount;
    }

    public String getSteamAppId() {
        return steamAppId;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public long getLastChange() {
        return lastChange;
    }

    public float getDealRating() {
        return dealRating;
    }

    public String getThumb() {
        return thumb;
    }
}
