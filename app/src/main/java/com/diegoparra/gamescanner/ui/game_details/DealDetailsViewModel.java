package com.diegoparra.gamescanner.ui.game_details;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Store;
import com.diegoparra.gamescanner.utils.ListUtils;
import com.diegoparra.gamescanner.utils.Resource;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DealDetailsViewModel extends ViewModel {

    //  Must be the same keys as in the navigation resource file, because those are the keys which navArgs are passed with.
    static final String DEAL_ID_SAVED_STATE_KEY = "deal_id";

    @NonNull
    private final GamesRepository repository;

    private final Flowable<List<Store>> storeListFlowable;
    private final Flowable<DealWithGameInfo> dealWithGameInfoFlowable;
    private final Flowable<DealWithGameAndStoreInfo> dealWithGameAndStoreInfoFlowable;
    private final Flowable<String> gameId;
    private final Flowable<List<DealWithStore>> additionalDealsWithStoreInfoFlowable;

    private final LiveData<Resource<DealWithGameAndStoreInfo>> dealWithGameInfo;
    private final LiveData<Resource<List<DealWithStore>>> additionalDealsWithStoreInfo;

    @Inject
    public DealDetailsViewModel(@NonNull GamesRepository gamesRepository, @NonNull SavedStateHandle savedStateHandle) {
        this.repository = gamesRepository;
        String dealId = Objects.requireNonNull(savedStateHandle.get(DEAL_ID_SAVED_STATE_KEY));

        storeListFlowable = repository.getStores().toFlowable().subscribeOn(Schedulers.io());

        dealWithGameInfoFlowable = repository.getDealById(dealId).toFlowable().subscribeOn(Schedulers.io());
        dealWithGameAndStoreInfoFlowable = getDealWithGameAndStoreInfo(storeListFlowable, dealWithGameInfoFlowable);
        this.dealWithGameInfo = LiveDataReactiveStreams.fromPublisher(
                dealWithGameAndStoreInfoFlowable.map(Resource::Success).onErrorReturn(Resource::Error)
        );

        gameId = dealWithGameInfoFlowable.map(dealWithGameInfo -> dealWithGameInfo.getGame().getGameId());
        additionalDealsWithStoreInfoFlowable = getDealsWithStoreFlowable(gameId);
        this.additionalDealsWithStoreInfo = LiveDataReactiveStreams.fromPublisher(
                additionalDealsWithStoreInfoFlowable.map(Resource::Success).onErrorReturn(Resource::Error)
        );
    }

    @VisibleForTesting
    Flowable<DealWithGameAndStoreInfo> getDealWithGameAndStoreInfo(@NonNull Flowable<List<Store>> storeListFlowable, @NonNull Flowable<DealWithGameInfo> dealWithGameInfoFlowable) {
        return Flowable.combineLatest(storeListFlowable, dealWithGameInfoFlowable, new BiFunction<List<Store>, DealWithGameInfo, DealWithGameAndStoreInfo>() {
            @Override
            public DealWithGameAndStoreInfo apply(List<Store> stores, DealWithGameInfo dealWithGameInfo) throws Throwable {
                Store store = ListUtils.find(stores, store1 -> store1.getId().equals(dealWithGameInfo.getDeal().getStoreId()));
                Objects.requireNonNull(store, "Store info was not found, storeId = " + dealWithGameInfo.getDeal().getStoreId());
                return new DealWithGameAndStoreInfo(dealWithGameInfo, store);
            }
        });
    }

    @VisibleForTesting
    Flowable<List<DealWithStore>> getDealsWithStoreFlowable(@NonNull Flowable<String> gameId) {
        return gameId.flatMap(this::getDealsWithStoreFlowable).subscribeOn(Schedulers.io());
    }

    @VisibleForTesting
    Flowable<List<DealWithStore>> getDealsWithStoreFlowable(@NonNull String gameId) {
        return Flowable.combineLatest(
                repository.getDealsForGame(gameId).toFlowable().subscribeOn(Schedulers.io()),
                storeListFlowable,
                new BiFunction<List<Deal>, List<Store>, List<DealWithStore>>() {
                    @Override
                    public List<DealWithStore> apply(List<Deal> deals, List<Store> stores) throws Throwable {
                        return ListUtils.mapNotNull(deals, deal -> {
                            String storeId = deal.getStoreId();
                            Store store = ListUtils.find(stores, store1 -> store1.getId().equals(storeId));
                            return (store == null) ? null : new DealWithStore(deal, store);
                        });
                    }
                });
    }

    @VisibleForTesting
    Flowable<List<Store>> getStoreListFlowable() {
        return storeListFlowable;
    }

    @VisibleForTesting
    Flowable<DealWithGameInfo> getDealWithGameInfoFlowable() {
        return dealWithGameInfoFlowable;
    }

    @VisibleForTesting
    Flowable<DealWithGameAndStoreInfo> getDealWithGameAndStoreInfoFlowable() {
        return dealWithGameAndStoreInfoFlowable;
    }

    @VisibleForTesting
    Flowable<String> getGameId() {
        return gameId;
    }

    @VisibleForTesting
    Flowable<List<DealWithStore>> getAdditionalDealsWithStoreInfoFlowable() {
        return additionalDealsWithStoreInfoFlowable;
    }

    //      ----------      Public data viewModel       --------------------------------------------

    public LiveData<Resource<DealWithGameAndStoreInfo>> getDealWithGameInfo() {
        return dealWithGameInfo;
    }

    public LiveData<Resource<List<DealWithStore>>> getAdditionalDealsWithStoreInfo() {
        return additionalDealsWithStoreInfo;
    }


}
