package com.diegoparra.gamescanner.ui.search;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.utils.Event;
import com.diegoparra.gamescanner.utils.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import timber.log.Timber;

@HiltViewModel
public class SearchViewModel extends ViewModel {

    static final String QUERY_SAVED_STATE_KEY = "query";
    static final String INITIAL_QUERY = "";
    private final SavedStateHandle savedStateHandle;

    private final BehaviorSubject<String> query = BehaviorSubject.create();
    private final GamesRepository repository;
    private final LiveData<Resource<List<Game>>> results;
    private final MutableLiveData<Event<String>> navigateDetails = new MutableLiveData<>();

    @Inject
    public SearchViewModel(GamesRepository gamesRepository, SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
        this.repository = gamesRepository;

        String savedQuery = savedStateHandle.get(QUERY_SAVED_STATE_KEY);
        setQuery(savedQuery != null ? savedQuery : INITIAL_QUERY);

        Flowable<Resource<List<Game>>> dealWithGameInfoListFlowable = getDealWithGameInfoFlowable();
        results = LiveDataReactiveStreams.fromPublisher(dealWithGameInfoListFlowable);
    }

    private Flowable<Resource<List<Game>>> getDealWithGameInfoFlowable() {
        return query
                .distinctUntilChanged()
                .debounce(300, TimeUnit.MILLISECONDS)
                .flatMap(this::getSearchResultsObservable)
                .toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io());
    }

    private ObservableSource<Resource<List<Game>>> getSearchResultsObservable(String query) {
        Timber.d("calling api with query: %s", query);
        return repository
                .getGamesByTitle(query)
                .map(Resource::Success)
                .toObservable()
                .startWithItem(Resource.Loading())
                .onErrorReturn(Resource::Error)
                .subscribeOn(Schedulers.io());
    }

    @VisibleForTesting
    public BehaviorSubject<String> getQuery() {
        return query;
    }

    //      ----------      Public methods viewModel       --------------------------------------------

    public void setQuery(@NonNull String query) {
        savedStateHandle.set(QUERY_SAVED_STATE_KEY, query);
        this.query.onNext(query);
    }

    public void navigateDetails(@NonNull String dealId) {
        navigateDetails.setValue(new Event<>(dealId));
    }


    //      ----------      Public data viewModel       --------------------------------------------

    public LiveData<Resource<List<Game>>> getResults() {
        return results;
    }

    public LiveData<Event<String>> getNavigateDetails() {
        return navigateDetails;
    }

}
