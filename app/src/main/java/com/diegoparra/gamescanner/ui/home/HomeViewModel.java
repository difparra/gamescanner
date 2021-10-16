package com.diegoparra.gamescanner.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.utils.Event;
import com.diegoparra.gamescanner.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final GamesRepository repository;
    private final LiveData<Resource<List<DealWithGameInfo>>> dealWithGameInfoList;
    private final MutableLiveData<Event<String>> navigateDetails = new MutableLiveData<>();

    @Inject
    public HomeViewModel(GamesRepository gamesRepository) {
        this.repository = gamesRepository;

        Flowable<Resource<List<DealWithGameInfo>>> dealWithGameInfoListFlowable = getDealWithGameInfoListFlowable();
        this.dealWithGameInfoList = LiveDataReactiveStreams.fromPublisher(dealWithGameInfoListFlowable);
    }

    private Flowable<Resource<List<DealWithGameInfo>>> getDealWithGameInfoListFlowable() {
        return repository.getDeals()
                .map(Resource::Success)
                .toFlowable()
                .startWithItem(Resource.Loading())
                .onErrorReturn(Resource::Error)
                .subscribeOn(Schedulers.io());
    }



    //      ----------      Public methods viewModel       --------------------------------------------

    public void navigateDetails(String dealId) {
        navigateDetails.setValue(new Event<>(dealId));
    }


    //      ----------      Public data viewModel       --------------------------------------------

    public LiveData<Resource<List<DealWithGameInfo>>> getDealWithGameInfoList() {
        return dealWithGameInfoList;
    }

    public LiveData<Event<String>> getNavigateDetails() {
        return navigateDetails;
    }


}
