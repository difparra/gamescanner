package com.diegoparra.gamescanner.data;

import androidx.paging.PagingData;

import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface GamesRepository {

    Single<List<Store>> getStores();

    Flowable<PagingData<DealWithGameInfo>> getDeals();
    Single<DealWithGameInfo> getDealById(String dealId);

    Single<List<DealWithGameInfo>> getDealsByGameTitle(String title);
    Single<List<Game>> getGamesByTitle(String title);

    Single<List<Deal>> getDealsForGame(String gameId);

}
