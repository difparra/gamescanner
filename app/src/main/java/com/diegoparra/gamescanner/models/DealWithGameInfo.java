package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

import java.util.Objects;

public class DealWithGameInfo {

    private Deal deal;
    private Game game;

    public DealWithGameInfo(Deal deal, Game game) {
        this.deal = deal;
        this.game = game;
    }

    public Deal getDeal() {
        return deal;
    }

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
