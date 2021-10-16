package com.diegoparra.gamescanner.ui.game_details;

import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;

/**
 * This class is intended just as a wrapper to pass data to the DealsAdapter.
 * It is not part of the domain or business layer, is just to wrap some models around and pass as
 * one object to ui.
 * It is just used in viewModel and ui layer, and may be considered as a dto in clean architecture,
 * just holding data and passing, without additional methods.
 */
public class DealWithGameAndStoreInfo {

    private final DealWithGameInfo dealWithGameInfo;
    private final Store store;

    public DealWithGameAndStoreInfo(DealWithGameInfo dealWithGameInfo, Store store) {
        this.dealWithGameInfo = dealWithGameInfo;
        this.store = store;
    }

    public Deal getDeal() {
        return dealWithGameInfo.getDeal();
    }

    public Game getGame() {
        return dealWithGameInfo.getGame();
    }

    public Store getStore() {
        return store;
    }
}
