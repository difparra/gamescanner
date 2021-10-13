package com.diegoparra.gamescanner.ui;

import androidx.lifecycle.ViewModel;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;
import com.diegoparra.gamescanner.utils.ListUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {

    private GamesRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    //private MutableLiveData<List<Store>> _stores = new MutableLiveData<>(Collections.emptyList());


    @Inject
    public MainActivityViewModel(GamesRepository gamesRepository) {
        this.repository = gamesRepository;
        loadStores();
        loadDealsWithGameInfo();
        loadGameInfoByDealId("X8sebHhbc1Ga0dTkgg59WgyM506af9oNZZJLU9uSrX8%3D");
        loadDealsForGame("93503");
    }

    private void loadStores() {
        repository.getStores()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Store>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.i("onSubscribe getStores");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Store> stores) {
                        Timber.i("onNext getStores");
                        Timber.d("stores={" + ListUtils.joinToString(stores, "\n") + "}");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.i("onError getStores");
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("onComplete getStores");
                    }
                });
    }

    private void loadDealsWithGameInfo() {
        repository.getDealsWithGameInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DealWithGameInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.i("onSubscribe getDealsWithGameInfo");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<DealWithGameInfo> dealWithGameInfos) {
                        Timber.i("onNext getDealsWithGameInfo");
                        Timber.d("dealWithGameInfo={" + ListUtils.joinToString(dealWithGameInfos, "\n") + "}");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.i("onError getDealsWithGameInfo");
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("onComplete getDealsWithGameInfo");
                    }
                });
    }

    private void loadGameInfoByDealId(String dealId) {
        repository.getGameInfoByDealId(dealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Game>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.i("onSubscribe getGameInfoByDealId");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Game game) {
                        Timber.i("onNext getGameInfoByDealId");
                        Timber.d("game=" + game);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.i("onError getGameInfoByDealId");
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("onComplete getGameInfoByDealId");
                    }
                });
    }

    private void loadDealsForGame(String gameId) {
        repository.getDealsForGame(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Deal>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.i("onSubscribe getDealsForGame");
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Deal> deals) {
                        Timber.i("onNext getDealsForGame");
                        Timber.d("deals={" + ListUtils.joinToString(deals, "\n") + "}");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.i("onError getDealsForGame");
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("onComplete getDealsForGame");
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
