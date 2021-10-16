package com.diegoparra.gamescanner.ui.shared;

public class NavigateDetailsData {

    private final String dealId;
    private final String gameId;

    public NavigateDetailsData(String dealId, String gameId) {
        this.dealId = dealId;
        this.gameId = gameId;
    }

    public String getDealId() {
        return dealId;
    }

    public String getGameId() {
        return gameId;
    }

}
