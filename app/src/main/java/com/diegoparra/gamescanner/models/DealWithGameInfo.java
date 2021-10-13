package com.diegoparra.gamescanner.models;

import androidx.annotation.NonNull;

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
}
