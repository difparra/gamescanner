package com.diegoparra.gamescanner.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final GamesRepository repository;
    private LiveData<Resource<List<DealWithGameInfo>>> dealWithGameInfoList;

    @Inject
    public HomeViewModel(GamesRepository gamesRepository) {
        this.repository = gamesRepository;
    }

    public LiveData<Resource<List<DealWithGameInfo>>> getDealWithGameInfoList() {
        if (dealWithGameInfoList == null) {
            dealWithGameInfoList = LiveDataReactiveStreams.fromPublisher(
                    repository.getDealsWithGameInfo()
                            .map(Resource::Success)
                            .toFlowable()
                            .startWithItem(Resource.Loading())
                            .onErrorReturn(Resource::Error)
                            .subscribeOn(Schedulers.io())
            );
        }
        return dealWithGameInfoList;
    }
}
