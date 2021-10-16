package com.diegoparra.gamescanner.ui.game_details;

import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.Store;

import java.util.Objects;

/**
 * This class is intended just as a wrapper to pass data to the DealsAdapter.
 * It will be used just in viewModel and ui. Neither models nor data layers will know about it.
 *
 * It is just used in viewModel and ui layer, and may be considered as a dto in clean architecture,
 * just holding data and passing, without additional methods.
 */
public class DealWithStore {

    private final Deal deal;
    private final Store store;

    public DealWithStore(Deal deal, Store store) {
        this.deal = deal;
        this.store = store;
    }

    public Deal getDeal() {
        return deal;
    }

    public Store getStore() {
        return store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealWithStore that = (DealWithStore) o;
        return deal.equals(that.deal) && store.equals(that.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deal, store);
    }
}
