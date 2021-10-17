package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

import java.util.Objects;

public class DealWithGameInfo {

    @NonNull
    private final Deal deal;
    @NonNull
    private final Game game;

    public DealWithGameInfo(@NonNull Deal deal, @NonNull Game game) {
        this.deal = deal;
        this.game = game;
    }

    @NonNull
    public Deal getDeal() {
        return deal;
    }

    @NonNull
    public Game getGame() {
        return game;
    }

    @NonNull
    @Override
    public String toString() {
        return "DealWithGameInfo{" +
                "deal=" + deal +
                ", game=" + game +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealWithGameInfo that = (DealWithGameInfo) o;
        return deal.equals(that.deal) && game.equals(that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deal, game);
    }
}
