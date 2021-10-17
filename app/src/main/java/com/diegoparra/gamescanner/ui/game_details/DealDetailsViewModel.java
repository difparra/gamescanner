package com.diegoparra.gamescanner.ui.game_details;

import androidx.annotation.NonNull;
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
    private static final String DEAL_ID_SAVED_STATE_KEY = "deal_id";

    @NonNull
    private final GamesRepository repository;

    private final Flowable<List<Store>> storeListFlowable;
    private final LiveData<Resource<DealWithGameAndStoreInfo>> dealWithGameInfo;
    private final LiveData<Resource<List<DealWithStore>>> additionalDealsWithStoreInfo;

    @Inject
    public DealDetailsViewModel(@NonNull GamesRepository gamesRepository, @NonNull SavedStateHandle savedStateHandle) {
        this.repository = gamesRepository;
        String dealId = Objects.requireNonNull(savedStateHandle.get(DEAL_ID_SAVED_STATE_KEY));

        storeListFlowable = repository.getStores().toFlowable().subscribeOn(Schedulers.io());

        Flowable<DealWithGameInfo> dealWithGameInfoFlowable = repository.getDealById(dealId).toFlowable().subscribeOn(Schedulers.io());
        Flowable<DealWithGameAndStoreInfo> dealWithGameAndStoreInfoFlowable = getDealWithGameAndStoreInfo(storeListFlowable, dealWithGameInfoFlowable);
        this.dealWithGameInfo = LiveDataReactiveStreams.fromPublisher(
                dealWithGameAndStoreInfoFlowable.map(Resource::Success).onErrorReturn(Resource::Error)

        );

        Flowable<String> gameId = dealWithGameInfoFlowable.map(dealWithGameInfo -> dealWithGameInfo.getGame().getGameId());
        Flowable<List<DealWithStore>> additionalDealsWithStoreInfoFlowable = getDealsWithStoreFlowable(gameId);
        this.additionalDealsWithStoreInfo = LiveDataReactiveStreams.fromPublisher(
                additionalDealsWithStoreInfoFlowable.map(Resource::Success).onErrorReturn(Resource::Error)
        );
    }

    private Flowable<DealWithGameAndStoreInfo> getDealWithGameAndStoreInfo(@NonNull Flowable<List<Store>> storeListFlowable, @NonNull Flowable<DealWithGameInfo> dealWithGameInfoFlowable) {
        return Flowable.combineLatest(storeListFlowable, dealWithGameInfoFlowable, new BiFunction<List<Store>, DealWithGameInfo, DealWithGameAndStoreInfo>() {
            @Override
            public DealWithGameAndStoreInfo apply(List<Store> stores, DealWithGameInfo dealWithGameInfo) throws Throwable {
                Store store = ListUtils.find(stores, store1 -> store1.getId().equals(dealWithGameInfo.getDeal().getStoreId()));
                Objects.requireNonNull(store, "Store info was not found, storeId = " + dealWithGameInfo.getDeal().getStoreId());
                return new DealWithGameAndStoreInfo(dealWithGameInfo, store);
            }
        });
    }

    private Flowable<List<DealWithStore>> getDealsWithStoreFlowable(@NonNull Flowable<String> gameId) {
        return gameId.flatMap(this::getDealsWithStoreFlowable).subscribeOn(Schedulers.io());
    }

    private Flowable<List<DealWithStore>> getDealsWithStoreFlowable(@NonNull String gameId) {
        return Flowable.combineLatest(
                repository.getDealsForGame(gameId).toFlowable().subscribeOn(Schedulers.io()),
                storeListFlowable,
                new BiFunction<List<Deal>, List<Store>, List<DealWithStore>>() {
                    @Override
                    public List<DealWithStore> apply(List<Deal> deals, List<Store> stores) throws Throwable {
                        return ListUtils.map(deals, deal -> {
                            String storeId = deal.getStoreId();
                            Store store = ListUtils.find(stores, store1 -> store1.getId().equals(storeId));
                            Objects.requireNonNull(store, "Store info was not found, storeId = " + storeId);
                            return new DealWithStore(deal, store);
                        });
                    }
                });
    }


    //      ----------      Public data viewModel       --------------------------------------------

    public LiveData<Resource<DealWithGameAndStoreInfo>> getDealWithGameInfo() {
        return dealWithGameInfo;
    }

    public LiveData<Resource<List<DealWithStore>>> getAdditionalDealsWithStoreInfo() {
        return additionalDealsWithStoreInfo;
    }


}
