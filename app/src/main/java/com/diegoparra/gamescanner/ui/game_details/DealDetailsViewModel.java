package com.diegoparra.gamescanner.ui.game_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Store;
import com.diegoparra.gamescanner.utils.ListUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DealDetailsViewModel extends ViewModel {

    //  Must be the same keys as in the navigation resource file, because those are the keys which navArgs are passed with.
    private static final String DEAL_ID_SAVED_STATE_KEY = "deal_id";

    private final GamesRepository repository;

    private final Flowable<List<Store>> storeListFlowable;
    private final LiveData<DealWithGameAndStoreInfo> dealWithGameInfo;
    private final LiveData<List<DealWithStore>> additionalDealsWithStoreInfo;

    @Inject
    public DealDetailsViewModel(GamesRepository gamesRepository, SavedStateHandle savedStateHandle) {
        this.repository = gamesRepository;
        String dealId = savedStateHandle.get(DEAL_ID_SAVED_STATE_KEY);

        storeListFlowable = repository.getStores().toFlowable().subscribeOn(Schedulers.io());

        Flowable<DealWithGameInfo> dealWithGameInfoFlowable = repository.getDealById(dealId).toFlowable().subscribeOn(Schedulers.io());
        Flowable<DealWithGameAndStoreInfo> dealWithGameAndStoreInfoFlowable = getDealWithGameAndStoreInfo(storeListFlowable, dealWithGameInfoFlowable);
        this.dealWithGameInfo = LiveDataReactiveStreams.fromPublisher(dealWithGameAndStoreInfoFlowable);

        Flowable<String> gameId = dealWithGameInfoFlowable.map(dealWithGameInfo -> dealWithGameInfo.getGame().getGameId());
        Flowable<List<DealWithStore>> additionalDealsWithStoreInfoFlowable = getDealsWithStoreFlowable(gameId);
        this.additionalDealsWithStoreInfo = LiveDataReactiveStreams.fromPublisher(additionalDealsWithStoreInfoFlowable);
    }

    private Flowable<DealWithGameAndStoreInfo> getDealWithGameAndStoreInfo(Flowable<List<Store>> storeListFlowable, Flowable<DealWithGameInfo> dealWithGameInfoFlowable) {
        return Flowable.combineLatest(storeListFlowable, dealWithGameInfoFlowable, new BiFunction<List<Store>, DealWithGameInfo, DealWithGameAndStoreInfo>() {
            @Override
            public DealWithGameAndStoreInfo apply(List<Store> stores, DealWithGameInfo dealWithGameInfo) throws Throwable {
                Store store = ListUtils.find(stores, store1 -> store1.getId().equals(dealWithGameInfo.getDeal().getStoreId()));
                return new DealWithGameAndStoreInfo(dealWithGameInfo, store);
            }
        });
    }

    private Flowable<List<DealWithStore>> getDealsWithStoreFlowable(Flowable<String> gameId) {
        return gameId.flatMap(this::getDealsWithStoreFlowable).subscribeOn(Schedulers.io());
    }

    private Flowable<List<DealWithStore>> getDealsWithStoreFlowable(String gameId) {
        return Flowable.combineLatest(
                repository.getDealsForGame(gameId).toFlowable().subscribeOn(Schedulers.io()),
                storeListFlowable,
                new BiFunction<List<Deal>, List<Store>, List<DealWithStore>>() {
                    @Override
                    public List<DealWithStore> apply(List<Deal> deals, List<Store> stores) throws Throwable {
                        return ListUtils.map(deals, deal -> {
                            String storeId = deal.getStoreId();
                            Store store = ListUtils.find(stores, store1 -> store1.getId().equals(storeId));
                            return new DealWithStore(deal, store);
                        });
                    }
                });
    }


    //      ----------      Public data viewModel       --------------------------------------------

    public LiveData<DealWithGameAndStoreInfo> getDealWithGameInfo() {
        return dealWithGameInfo;
    }

    public LiveData<List<DealWithStore>> getAdditionalDealsWithStoreInfo() {
        return additionalDealsWithStoreInfo;
    }


}
