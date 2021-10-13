package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Game {

    private String gameId;
    private String title;
    private String imageUrl;
    private SteamInfo steamInfo;
    private MetacriticInfo metacriticInfo;
    private Instant releaseDate;

    public Game(String gameId, String title, String imageUrl, SteamInfo steamInfo, MetacriticInfo metacriticInfo, Instant releaseDate) {
        this.gameId = gameId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.steamInfo = steamInfo;
        this.metacriticInfo = metacriticInfo;
        this.releaseDate = releaseDate;
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
        return LocalDateTime.ofInstant(releaseDate, ZoneId.systemDefault()).toLocalDate();
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
}
