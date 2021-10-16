package com.diegoparra.gamescanner.data.network.dtos;

import com.google.gson.annotations.SerializedName;

public class GameListItemDto {

    @SerializedName("gameID")
    private String gameId;
    @SerializedName("steamAppID")
    private String steamAppId;
    @SerializedName("cheapest")
    private float cheapestPrice;
    @SerializedName("cheapestDealID")
    private String cheapestDealid;
    @SerializedName("external")
    private String title;
    private String internalName;
    private String thumb;

    public GameListItemDto(String gameId, String steamAppId, float cheapestPrice, String cheapestDealid, String title, String internalName, String thumb) {
        this.gameId = gameId;
        this.steamAppId = steamAppId;
        this.cheapestPrice = cheapestPrice;
        this.cheapestDealid = cheapestDealid;
        this.title = title;
        this.internalName = internalName;
        this.thumb = thumb;
    }

    public String getGameId() {
        return gameId;
    }

    public String getSteamAppId() {
        return steamAppId;
    }

    public float getCheapestPrice() {
        return cheapestPrice;
    }

    public String getCheapestDealId() {
        return cheapestDealid;
    }

    public String getTitle() {
        return title;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getThumb() {
        return thumb;
    }
}
