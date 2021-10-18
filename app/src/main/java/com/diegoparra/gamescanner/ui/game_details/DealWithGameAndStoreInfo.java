package com.diegoparra.gamescanner.ui.game_details;

import androidx.annotation.NonNull;

import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;

import java.util.Objects;

/**
 * This class is intended just as a wrapper to pass data to the DealsAdapter.
 * It is not part of the domain or business layer, is just to wrap some models around and pass as
 * one object to ui.
 * It is just used in viewModel and ui layer, and may be considered as a dto in clean architecture,
 * just holding data and passing, without additional methods.
 */
public class DealWithGameAndStoreInfo {

    @NonNull
    private final DealWithGameInfo dealWithGameInfo;
    @NonNull
    private final Store store;

    public DealWithGameAndStoreInfo(@NonNull DealWithGameInfo dealWithGameInfo, @NonNull Store store) {
        this.dealWithGameInfo = dealWithGameInfo;
        this.store = store;
    }

    @NonNull
    public Deal getDeal() {
        return dealWithGameInfo.getDeal();
    }

    @NonNull
    public Game getGame() {
        return dealWithGameInfo.getGame();
    }

    @NonNull
    public Store getStore() {
        return store;
    }

    @NonNull
    @Override
    public String toString() {
        return "DealWithGameAndStoreInfo{" +
                "dealWithGameInfo=" + dealWithGameInfo +
                ", store=" + store +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealWithGameAndStoreInfo that = (DealWithGameAndStoreInfo) o;
        return dealWithGameInfo.equals(that.dealWithGameInfo) && store.equals(that.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dealWithGameInfo, store);
    }
}
