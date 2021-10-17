package com.diegoparra.gamescanner.data;

import androidx.annotation.NonNull;
import androidx.paging.PagingData;

import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface GamesRepository {

    @NonNull
    Single<List<Store>> getStores();

    @NonNull
    Flowable<PagingData<DealWithGameInfo>> getDeals();

    @NonNull
    Single<DealWithGameInfo> getDealById(@NonNull String dealId);

    @NonNull
    Single<List<DealWithGameInfo>> getDealsByGameTitle(@NonNull String title);

    @NonNull
    Single<List<Game>> getGamesByTitle(@NonNull String title);

    @NonNull
    Single<List<Deal>> getDealsForGame(@NonNull String gameId);

}
