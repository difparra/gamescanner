package com.diegoparra.gamescanner.ui.search;

import static com.google.common.truth.Truth.assertThat;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.core.util.Pair;
import androidx.lifecycle.SavedStateHandle;

import com.diegoparra.gamescanner.data.FakeGameListItemDto;
import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.data.network.DtoMappers;
import com.diegoparra.gamescanner.data.network.DtoMappersImpl;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.test_utils.LiveDataTestUtil;
import com.diegoparra.gamescanner.utils.Event;
import com.diegoparra.gamescanner.utils.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class SearchViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private GamesRepository repository;
    private SearchViewModel viewModel;

    private final DtoMappers dtoMappers = new DtoMappersImpl();
    private final Game game1 = dtoMappers.toGame(FakeGameListItemDto.gameListItemDto1);
    private final Game game2 = dtoMappers.toGame(FakeGameListItemDto.gameListItemDto2);
    private final Game game3 = dtoMappers.toGame(FakeGameListItemDto.gameListItemDto3);

    private final Pair<String, List<Game>> emptyQuery = new Pair<>("", Collections.emptyList());
    private final Pair<String, List<Game>> query1 = new Pair<>("abc", Arrays.asList(game1, game2, game3));
    private final Pair<String, List<Game>> queryNoResults = new Pair<>("abc", Collections.emptyList());


    @Before
    public void initHappyPath() {
        SavedStateHandle savedStateHandle = new SavedStateHandle();
        savedStateHandle.set(SearchViewModel.QUERY_SAVED_STATE_KEY, SearchViewModel.INITIAL_QUERY);

        Mockito.when(repository.getGamesByTitle(emptyQuery.first)).thenReturn(Single.just(emptyQuery.second));

        viewModel = new SearchViewModel(repository, savedStateHandle);
    }

    @Test
    public void test_setQuery() {
        viewModel.getQuery().test().assertValue(SearchViewModel.INITIAL_QUERY).dispose();
        viewModel.setQuery(query1.first);
        viewModel.getQuery().test().assertValue(query1.first).dispose();
    }

    @Test
    public void test_navigateDetails() throws InterruptedException {
        viewModel.navigateDetails("dealId");
        Event<String> newValue = LiveDataTestUtil.getOrAwaitValue(viewModel.getNavigateDetails());
        assertThat(newValue).isEqualTo(new Event<>("dealId"));
    }

    @Test
    public void getResults_emptyQuery() throws InterruptedException {
        viewModel.setQuery(emptyQuery.first);
        List<Resource<List<Game>>> result = LiveDataTestUtil.getOrAwaitValues(viewModel.getResults(), 2);
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(Resource.Loading());
        assertThat(result.get(1)).isEqualTo(Resource.Success(emptyQuery.second));
    }

}