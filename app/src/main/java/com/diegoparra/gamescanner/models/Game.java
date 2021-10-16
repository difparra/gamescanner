package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class Game {

    private String gameId;
    private String title;
    private String imageUrl;
    private SteamInfo steamInfo;
    private MetacriticInfo metacriticInfo;
    private Instant releaseDate;
    private String cheapestDealId;
    private Float cheapestCurrentPrice;

    public Game(String gameId, String title, String imageUrl, SteamInfo steamInfo, MetacriticInfo metacriticInfo, Instant releaseDate, String cheapestDealId, Float cheapestCurrentPrice) {
        this.gameId = gameId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.steamInfo = steamInfo;
        this.metacriticInfo = metacriticInfo;
        this.releaseDate = releaseDate;
        this.cheapestDealId = cheapestDealId;
        this.cheapestCurrentPrice = cheapestCurrentPrice;
    }

    public String getGameId() {
        return gameId;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public SteamInfo getSteamInfo() {
        return steamInfo;
    }

    public MetacriticInfo getMetacriticInfo() {
        return metacriticInfo;
    }

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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId.equals(game.gameId) && Objects.equals(title, game.title) && Objects.equals(imageUrl, game.imageUrl) && Objects.equals(steamInfo, game.steamInfo) && Objects.equals(metacriticInfo, game.metacriticInfo) && Objects.equals(releaseDate, game.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, title, imageUrl, steamInfo, metacriticInfo, releaseDate);
    }
}
