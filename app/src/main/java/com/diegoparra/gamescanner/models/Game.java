package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class Game {

    @NonNull
    private final String gameId;
    @NonNull
    private final String title;
    @Nullable
    private final String imageUrl;
    @Nullable
    private final SteamInfo steamInfo;
    @Nullable
    private final MetacriticInfo metacriticInfo;
    @Nullable
    private final Instant releaseDate;
    @Nullable
    private final String cheapestDealId;
    @Nullable
    private final Float cheapestCurrentPrice;

    public Game(@NonNull String gameId,
                @NonNull String title,
                @Nullable String imageUrl,
                @Nullable SteamInfo steamInfo,
                @Nullable MetacriticInfo metacriticInfo,
                @Nullable Instant releaseDate,
                @Nullable String cheapestDealId,
                @Nullable Float cheapestCurrentPrice) {
        this.gameId = gameId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.steamInfo = steamInfo;
        this.metacriticInfo = metacriticInfo;
        this.releaseDate = releaseDate;
        this.cheapestDealId = cheapestDealId;
        this.cheapestCurrentPrice = cheapestCurrentPrice;
    }

    @NonNull
    public String getGameId() {
        return gameId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public SteamInfo getSteamInfo() {
        return steamInfo;
    }

    @Nullable
    public MetacriticInfo getMetacriticInfo() {
        return metacriticInfo;
    }

    @Nullable
    public LocalDate getReleaseDate() {
        if (releaseDate != null && releaseDate.getEpochSecond() > 0) {
            return LocalDateTime.ofInstant(releaseDate, ZoneId.systemDefault()).toLocalDate();
        } else {
            return null;
        }
    }

    @Nullable
    public String getCheapestDealId() {
        return cheapestDealId;
    }

    @Nullable
    public Float getCheapestCurrentPrice() {
        return cheapestCurrentPrice;
    }

    @NonNull
    @Override
    public String toString() {
        return "Game{" +
                "gameId='" + gameId + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", steamInfo=" + steamInfo +
                ", metacriticInfo=" + metacriticInfo +
                ", releaseDate=" + releaseDate +
                ", cheapestDealId='" + cheapestDealId + '\'' +
                ", cheapestCurrentPrice=" + cheapestCurrentPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId.equals(game.gameId) && title.equals(game.title) && Objects.equals(imageUrl, game.imageUrl) && Objects.equals(steamInfo, game.steamInfo) && Objects.equals(metacriticInfo, game.metacriticInfo) && Objects.equals(releaseDate, game.releaseDate) && Objects.equals(cheapestDealId, game.cheapestDealId) && Objects.equals(cheapestCurrentPrice, game.cheapestCurrentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, title, imageUrl, steamInfo, metacriticInfo, releaseDate, cheapestDealId, cheapestCurrentPrice);
    }
}
