package com.diegoparra.gamescanner.ui.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.ui.shared.NavigateDetailsData;
import com.diegoparra.gamescanner.utils.Event;
import com.diegoparra.gamescanner.utils.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import timber.log.Timber;

@HiltViewModel
public class SearchViewModel extends ViewModel {

    private static final String QUERY_SAVED_STATE_KEY = "query";
    private static final String INITIAL_QUERY = "";
    private final SavedStateHandle savedStateHandle;

    private final BehaviorSubject<String> query = BehaviorSubject.create();
    private final GamesRepository repository;
    private LiveData<Resource<List<DealWithGameInfo>>> results;
    private final MutableLiveData<Event<NavigateDetailsData>> navigateDetails = new MutableLiveData<>();

    @Inject
    public SearchViewModel(GamesRepository gamesRepository, SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
        this.repository = gamesRepository;
        String savedQuery = savedStateHandle.get(QUERY_SAVED_STATE_KEY);
        setQuery(savedQuery != null ? savedQuery : INITIAL_QUERY);
    }

    public void setQuery(@NonNull String query) {
        savedStateHandle.set(QUERY_SAVED_STATE_KEY, query);
        this.query.onNext(query);
    }

    public LiveData<Resource<List<DealWithGameInfo>>> getResults() {
        if (results == null) {
            results = LiveDataReactiveStreams.fromPublisher(
                    getDealWithGameInfoObservable()
                            .toFlowable(BackpressureStrategy.LATEST)
                            .subscribeOn(Schedulers.io())
            );
        }
        return results;
    }

    private Observable<Resource<List<DealWithGameInfo>>> getDealWithGameInfoObservable() {
        return query
                .distinctUntilChanged()
                .debounce(300, TimeUnit.MILLISECONDS)
                .flatMap(new Function<String, ObservableSource<Resource<List<DealWithGameInfo>>>>() {
                    @Override
                    public ObservableSource<Resource<List<DealWithGameInfo>>> apply(String s) throws Throwable {
                        Timber.d("calling api with query: %s", s);
                        return repository
                                .getDealsByGameTitle(s)
                                .map(Resource::Success)
                                .toObservable()
                                .startWithItem(Resource.Loading())
                                .subscribeOn(Schedulers.io());
                    }
                })
                .onErrorReturn(Resource::Error);
    }

    public void navigateDetails(String dealId, String gameId) {
        navigateDetails.setValue(new Event<>(new NavigateDetailsData(dealId, gameId)));
    }

    public LiveData<Event<NavigateDetailsData>> getNavigateDetails() {
        return navigateDetails;
    }

}
