package com.diegoparra.gamescanner.data.network.dtos;

import com.google.gson.annotations.SerializedName;

public class GameInfoDto {

    @SerializedName("storeID")
    private String storeId;
    @SerializedName("gameID")
    private String gameId;
    private String name;
    @SerializedName("steamAppID")
    private String steamAppId;
    private float salePrice;
    @SerializedName("retailPrice")
    private float normalPrice;
    private String steamRatingText;
    private int steamRatingPercent;
    private long steamRatingCount;
    private int metacriticScore;
    private String metacriticLink;
    private long releaseDate;
    private String publisher;
    private String steamworks;
    private String thumb;

    public GameInfoDto(String storeId, String gameId, String name, String steamAppId, float salePrice, float normalPrice, String steamRatingText, int steamRatingPercent, long steamRatingCount, int metacriticScore, String metacriticLink, long releaseDate, String publisher, String steamworks, String thumb) {
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

    public String getStoreId() {
        return storeId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getSteamAppId() {
        return steamAppId;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public float getNormalPrice() {
        return normalPrice;
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

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public String getMetacriticLink() {
        return metacriticLink;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSteamworks() {
        return steamworks;
    }

    public String getThumb() {
        return thumb;
    }
}
