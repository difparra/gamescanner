package com.diegoparra.gamescanner.data;

import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface GamesRepository {

    Single<List<Store>> getStores();
    Single<List<DealWithGameInfo>> getDealsWithGameInfo();
    Single<Game> getGameInfoByDealId(String dealId);
    Single<List<Deal>> getDealsForGame(String gameId);

}
