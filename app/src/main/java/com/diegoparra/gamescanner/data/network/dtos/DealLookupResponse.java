package com.diegoparra.gamescanner.data.network.dtos;

public class DealLookupResponse {

    private GameInfoDto gameInfo;

    /*
      I could add cheaperStores and cheapestPrice, as part of attributes in this response,
      but I have found they are not really reliable.

      For example, for the game:
        gameId = 235398
        dealId = on3LSvb5YoZJvxvvcndY3GYjXMWBgqoj30gdJVJ%2Bkdc%3D
      When I search cheapestPrice by:
        DealLookup query, cheaperStores is empty, and cheapestPrice has no info.
        GameLookup query, I actually get info on cheapestPriceEver and deals.
     */

    public DealLookupResponse(GameInfoDto gameInfo) {
        this.gameInfo = gameInfo;
    }

    public GameInfoDto getGameInfo() {
        return gameInfo;
    }

}
