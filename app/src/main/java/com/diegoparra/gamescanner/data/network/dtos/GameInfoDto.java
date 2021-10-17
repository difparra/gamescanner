package com.diegoparra.gamescanner.data.network.dtos;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class GameInfoDto {

    @Nullable
    @SerializedName("storeID")
    private final String storeId;
    @Nullable
    @SerializedName("gameID")
    private String gameId;
    @Nullable
    private String name;
    @Nullable
    @SerializedName("steamAppID")
    private String steamAppId;
    @Nullable
    private Float salePrice;
    @Nullable
    @SerializedName("retailPrice")
    private Float normalPrice;
    @Nullable
    private String steamRatingText;
    @Nullable
    private Integer steamRatingPercent;
    @Nullable
    private Long steamRatingCount;
    @Nullable
    private Integer metacriticScore;
    @Nullable
    private String metacriticLink;
    @Nullable
    private Long releaseDate;
    @Nullable
    private String publisher;
    @Nullable
    private String steamworks;
    @Nullable
    private String thumb;

    public GameInfoDto(@Nullable String storeId, @Nullable String gameId, @Nullable String name, @Nullable String steamAppId, @Nullable Float salePrice, @Nullable Float normalPrice, @Nullable String steamRatingText, @Nullable Integer steamRatingPercent, @Nullable Long steamRatingCount, @Nullable Integer metacriticScore, @Nullable String metacriticLink, @Nullable Long releaseDate, @Nullable String publisher, @Nullable String steamworks, @Nullable String thumb) {
        this.storeId = storeId;
        this.gameId = gameId;
        this.name = name;
        this.steamAppId = steamAppId;
        this.salePrice = salePrice;
        this.normalPrice = normalPrice;
        this.steamRatingText = steamRatingText;
        this.steamRatingPercent = steamRatingPercent;
        this.steamRatingCount = steamRatingCount;
        this.metacriticScore = metacriticScore;
        this.metacriticLink = metacriticLink;
        this.releaseDate = releaseDate;
        this.publisher = publisher;
        this.steamworks = steamworks;
        this.thumb = thumb;
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
    public String getName() {
        return name;
    }

    @Nullable
    public String getSteamAppId() {
        return steamAppId;
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
    public Integer getMetacriticScore() {
        return metacriticScore;
    }

    @Nullable
    public String getMetacriticLink() {
        return metacriticLink;
    }

    @Nullable
    public Long getReleaseDate() {
        return releaseDate;
    }

    @Nullable
    public String getPublisher() {
        return publisher;
    }

    @Nullable
    public String getSteamworks() {
        return steamworks;
    }

    @Nullable
    public String getThumb() {
        return thumb;
    }
}
