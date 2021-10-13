package com.diegoparra.gamescanner.data;

import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GamesRepository {

    Observable<List<Store>> getStores();
    Observable<List<DealWithGameInfo>> getDealsWithGameInfo();
    Observable<Game> getGameInfoByDealId(String dealId);
    Observable<List<Deal>> getDealsForGame(String gameId);

}
