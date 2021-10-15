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
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class GameDetailsViewModel extends ViewModel {

    //  Must be the same keys as in the navigation resource file, because those are the keys which navArgs are passed with.
    private static final String DEAL_ID_SAVED_STATE_KEY = "deal_id";
    private static final String GAME_ID_SAVED_STATE_KEY = "game_id";

    private final GamesRepository repository;
    private final String dealId;
    private final String gameId;

    private LiveData<DealWithGameInfo> dealWithGameInfo;
    private LiveData<List<DealWithStore>> additionalDealsWithStoreInfo;

    @Inject
    public GameDetailsViewModel(GamesRepository gamesRepository, SavedStateHandle savedStateHandle) {
        this.repository = gamesRepository;
        this.dealId = savedStateHandle.get(DEAL_ID_SAVED_STATE_KEY);
        this.gameId = savedStateHandle.get(GAME_ID_SAVED_STATE_KEY);
    }

    public LiveData<DealWithGameInfo> getDealWithGameInfo() {
        if (dealWithGameInfo == null) {
            dealWithGameInfo = LiveDataReactiveStreams.fromPublisher(
                    repository.getDealById(dealId)
                            .toFlowable()
                            .subscribeOn(Schedulers.io())
            );
        }
        return dealWithGameInfo;
    }

    public LiveData<List<DealWithStore>> getAdditionalDealsWithStoreInfo() {
        if (additionalDealsWithStoreInfo == null) {
            additionalDealsWithStoreInfo = LiveDataReactiveStreams.fromPublisher(
                    getDealsWithStoreObservable()
                            .toFlowable(BackpressureStrategy.LATEST)
                            .subscribeOn(Schedulers.io())
            );
        }
        return additionalDealsWithStoreInfo;
    }

    private Observable<List<DealWithStore>> getDealsWithStoreObservable() {
        return Observable.combineLatest(
                repository.getDealsForGame(gameId).subscribeOn(Schedulers.io()).toObservable(),
                repository.getStores().subscribeOn(Schedulers.io()).toObservable(),
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


}
