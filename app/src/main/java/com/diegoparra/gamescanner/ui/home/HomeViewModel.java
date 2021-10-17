package com.diegoparra.gamescanner.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.utils.Event;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlinx.coroutines.CoroutineScope;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final GamesRepository repository;
    private final LiveData<PagingData<DealWithGameInfo>> dealWithGameInfoList;
    private final MutableLiveData<Event<String>> navigateDetails = new MutableLiveData<>();

    @Inject
    public HomeViewModel(GamesRepository gamesRepository) {
        this.repository = gamesRepository;

        Flowable<PagingData<DealWithGameInfo>> dealsRawFlowable = getDealWithGameInfoListFlowable();
        //  Small implementation to cacheIn flowable according to developers.android Load and display paged data
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Flowable<PagingData<DealWithGameInfo>> dealsCachedInFlowable = PagingRx.cachedIn(dealsRawFlowable, viewModelScope);
        this.dealWithGameInfoList = LiveDataReactiveStreams.fromPublisher(dealsCachedInFlowable);
    }

    private Flowable<PagingData<DealWithGameInfo>> getDealWithGameInfoListFlowable() {
        return repository.getDeals().subscribeOn(Schedulers.io());
    }



    //      ----------      Public methods viewModel       --------------------------------------------

    public void navigateDetails(String dealId) {
        navigateDetails.setValue(new Event<>(dealId));
    }


    //      ----------      Public data viewModel       --------------------------------------------

    public LiveData<PagingData<DealWithGameInfo>> getDealWithGameInfoList() {
        return dealWithGameInfoList;
    }

    public LiveData<Event<String>> getNavigateDetails() {
        return navigateDetails;
    }


}
