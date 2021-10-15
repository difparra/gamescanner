package com.diegoparra.gamescanner.ui.game_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;
import com.diegoparra.gamescanner.utils.ListUtils;

import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class GameDetailsViewModel extends ViewModel {

    //  Must be the same keys as in the navigation resource file, because those are the keys which navArgs are passed with.
    private static final String DEAL_ID_SAVED_STATE_KEY = "deal_id";
    private static final String GAME_ID_SAVED_STATE_KEY = "game_id";

    private final GamesRepository repository;
    private final String dealId;
    private final String gameId;

    private LiveData<Game> gameInfo;
    private LiveData<List<DealWithStore>> dealWithStore;

    @Inject
    public GameDetailsViewModel(GamesRepository gamesRepository, SavedStateHandle savedStateHandle) {
        this.repository = gamesRepository;
        this.dealId = savedStateHandle.get(DEAL_ID_SAVED_STATE_KEY);
        this.gameId = savedStateHandle.get(GAME_ID_SAVED_STATE_KEY);
    }

    public LiveData<Game> getGame() {
        if (gameInfo == null) {
            gameInfo = LiveDataReactiveStreams.fromPublisher(
                    repository.getGameInfoByDealId(dealId)
                            .toFlowable()
                            .subscribeOn(Schedulers.io())
            );
        }
        return gameInfo;
    }

    public LiveData<List<DealWithStore>> getDealWithStoreList() {
        if (dealWithStore == null) {
            dealWithStore = LiveDataReactiveStreams.fromPublisher(
                    getDealsWithStoreObservable()
                            .toFlowable(BackpressureStrategy.LATEST)
                            .subscribeOn(Schedulers.io())
            );
        }
        return dealWithStore;
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
