package com.diegoparra.gamescanner.data;

import com.diegoparra.gamescanner.data.network.DtoMappers;
import com.diegoparra.gamescanner.data.network.GamesApi;
import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.StoreDto;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Store;
import com.diegoparra.gamescanner.utils.ListUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import timber.log.Timber;

public class GamesRepositoryImpl implements GamesRepository {

    private final GamesApi api;
    private final DtoMappers dtoMappers;

    @Inject
    public GamesRepositoryImpl(GamesApi api, DtoMappers dtoMappers) {
        this.api = api;
        this.dtoMappers = dtoMappers;
    }

    @Override
    public Single<List<Store>> getStores() {
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
    public Single<List<DealWithGameInfo>> getDeals() {
        Timber.i("getDeals called!");
        return api.getDeals().map(new Function<List<DealsListItemDto>, List<DealWithGameInfo>>() {
            @Override
            public List<DealWithGameInfo> apply(List<DealsListItemDto> dealsListItemDtos) throws Throwable {
                List<DealWithGameInfo> dealWithGameInfoList = ListUtils.map(dealsListItemDtos, dtoMappers::toDealWithGameInfo);
                Timber.d("getDeals result: \n dealWithGameInfo={" + ListUtils.joinToString(dealWithGameInfoList, "\n") + "}");
                return dealWithGameInfoList;
            }
        });
    }

    @Override
    public Single<List<DealWithGameInfo>> getDealsByGameTitle(String title) {
        Timber.i("getDealsByGameTitle called! with title=%s", title);
        return api.getDealsByTitle(title).map(new Function<List<DealsListItemDto>, List<DealWithGameInfo>>() {
            @Override
            public List<DealWithGameInfo> apply(List<DealsListItemDto> dealsListItemDtos) throws Throwable {
                List<DealWithGameInfo> dealWithGameInfoList = ListUtils.map(dealsListItemDtos, dtoMappers::toDealWithGameInfo);
                Timber.d("getDealsByGameTitle result: \n dealWithGameInfo={" + ListUtils.joinToString(dealWithGameInfoList, "\n") + "}");
                return dealWithGameInfoList;
            }
        });
    }

    @Override
    public Single<DealWithGameInfo> getDealById(String dealId) {
        Timber.i("getDealById called! with dealId=%s", dealId);
        return api.getDealById(dealId).map(new Function<DealLookupResponse, DealWithGameInfo>() {
            @Override
            public DealWithGameInfo apply(DealLookupResponse dealLookupResponse) throws Throwable {
                DealWithGameInfo dealWithGameInfo = dtoMappers.toDealWithGameInfo(dealLookupResponse, dealId);
                Timber.d("getDealById result: dealWithGameInfo=%s", dealWithGameInfo);
                return dealWithGameInfo;
            }
        });
    }

    @Override
    public Single<List<Deal>> getDealsForGame(String gameId) {
        Timber.i("getDealsForGame called! with gameId=%s", gameId);
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
