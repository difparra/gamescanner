package com.diegoparra.gamescanner.ui.game_details;

import androidx.annotation.NonNull;

import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.Store;

import java.util.Objects;

/**
 * This class is intended just as a wrapper to pass data to the DealsAdapter.
 * It will be used just in viewModel and ui. Neither models nor data layers will know about it.
 * <p>
 * It is just used in viewModel and ui layer, and may be considered as a dto in clean architecture,
 * just holding data and passing, without additional methods.
 */
public class DealWithStore {

    @NonNull
    private final Deal deal;
    @NonNull
    private final Store store;

    public DealWithStore(@NonNull Deal deal, @NonNull Store store) {
        this.deal = deal;
        this.store = store;
    }

    @NonNull
    public Deal getDeal() {
        return deal;
    }

    @NonNull
    public Store getStore() {
        return store;
    }

    @Override
    public String toString() {
        return "DealWithStore{" +
                "deal=" + deal +
                ", store=" + store +
                '}';
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
