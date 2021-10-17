package com.diegoparra.gamescanner.data.network.dtos;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class GameListItemDto {

    @Nullable
    @SerializedName("gameID")
    private final String gameId;
    @Nullable @SerializedName("steamAppID")
    private final String steamAppId;
    @Nullable @SerializedName("cheapest")
    private final Float cheapestPrice;
    @Nullable @SerializedName("cheapestDealID")
    private final String cheapestDealId;
    @Nullable @SerializedName("external")
    private final String title;
    @Nullable private final String internalName;
    @Nullable private final String thumb;

    public GameListItemDto(@Nullable String gameId, @Nullable String steamAppId, @Nullable Float cheapestPrice, @Nullable String cheapestDealId, @Nullable String title, @Nullable String internalName, @Nullable String thumb) {
        this.gameId = gameId;
        this.steamAppId = steamAppId;
        this.cheapestPrice = cheapestPrice;
        this.cheapestDealId = cheapestDealId;
        this.title = title;
        this.internalName = internalName;
        this.thumb = thumb;
    }

    @Nullable
    public String getGameId() {
        return gameId;
    }

    @Nullable
    public String getSteamAppId() {
        return steamAppId;
    }

    @Nullable
    public Float getCheapestPrice() {
        return cheapestPrice;
    }

    @Nullable
    public String getCheapestDealId() {
        return cheapestDealId;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getInternalName() {
        return internalName;
    }

    @Nullable
    public String getThumb() {
        return thumb;
    }
}
