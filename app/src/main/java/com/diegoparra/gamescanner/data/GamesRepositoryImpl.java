package com.diegoparra.gamescanner.data;

import com.diegoparra.gamescanner.data.network.DtoMappers;
import com.diegoparra.gamescanner.data.network.GamesApi;
import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.StoreDto;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;
import com.diegoparra.gamescanner.utils.ListUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import timber.log.Timber;

public class GamesRepositoryImpl implements GamesRepository {

    private GamesApi api;
    private DtoMappers dtoMappers;

    @Inject
    public GamesRepositoryImpl(GamesApi api, DtoMappers dtoMappers) {
        this.api = api;
        this.dtoMappers = dtoMappers;
    }

    @Override
    public Observable<List<Store>> getStores() {
        Timber.i("getStores called!");
        return api.getStores().map(new Function<List<StoreDto>, List<Store>>() {
            @Override
            public List<Store> apply(List<StoreDto> storeDtos) throws Throwable {
                List<Store> stores = ListUtils.map(storeDtos, dtoMappers::toStore);
                Timber.d("getStores result: \n stores={" + ListUtils.joinToString(stores, "\n") + "}");
                return stores;
            }
        });
    }

    @Override
    public Observable<List<DealWithGameInfo>> getDealsWithGameInfo() {
        Timber.i("getDealsWithGameInfo called!");
        return api.getDealsWithGameInfo().map(new Function<List<DealsListItemDto>, List<DealWithGameInfo>>() {
            @Override
            public List<DealWithGameInfo> apply(List<DealsListItemDto> dealsListItemDtos) throws Throwable {
                List<DealWithGameInfo> dealWithGameInfos = ListUtils.map(dealsListItemDtos, dtoMappers::toDealWithGameInfo);
                Timber.d("getDealsWithGameInfo result: \n dealWithGameInfo={" + ListUtils.joinToString(dealWithGameInfos, "\n") + "}");
                return dealWithGameInfos;
            }
        });
    }

    @Override
    public Observable<Game> getGameInfoByDealId(String dealId) {
        Timber.i("getGameInfoByDealId called!");
        return api.getDealWithGameInfo(dealId).map(new Function<DealLookupResponse, Game>() {
            @Override
            public Game apply(DealLookupResponse dealLookupResponse) throws Throwable {
                Game game = dtoMappers.toGame(dealLookupResponse);
                Timber.d("getGameInfoByDealId result: \n game=" + game);
                return game;
            }
        });
    }

    @Override
    public Observable<List<Deal>> getDealsForGame(String gameId) {
        Timber.i("getDealsForGame called!");
        return api.getDealsForGame(gameId).map(new Function<GameLookupResponse, List<Deal>>() {
            @Override
            public List<Deal> apply(GameLookupResponse gameLookupResponse) throws Throwable {
                List<Deal> deals = dtoMappers.toDealsList(gameLookupResponse, gameId);
                Timber.d("getDealsForGame result: \n deals={" + ListUtils.joinToString(deals, "\n") + "}");
                return deals;
            }
        });
    }
}
