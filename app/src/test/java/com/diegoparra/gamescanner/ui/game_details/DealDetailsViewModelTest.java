package com.diegoparra.gamescanner.ui.game_details;

import androidx.lifecycle.SavedStateHandle;

import com.diegoparra.gamescanner.data.FakeDataStores;
import com.diegoparra.gamescanner.data.FakeDealsListItemDto;
import com.diegoparra.gamescanner.data.FakeGameLookupResponses;
import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.data.network.DtoMappers;
import com.diegoparra.gamescanner.data.network.DtoMappersImpl;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Store;
import com.diegoparra.gamescanner.utils.ListUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class DealDetailsViewModelTest {

    @Mock
    private GamesRepository repository;
    private DealDetailsViewModel viewModel;

    private final DtoMappers dtoMappers = new DtoMappersImpl();
    private final DealWithGameInfo dealWithGameInfo = dtoMappers.toDealWithGameInfo(FakeDealsListItemDto.dealListItemDto1);
    private final String storeId = dealWithGameInfo.getDeal().getStoreId();
    private final Store store = dtoMappers.toStore(FakeDataStores.copy(FakeDataStores.storeDto1, storeId));
    private final String dealId = dealWithGameInfo.getDeal().getDealId();
    private final String gameId = dealWithGameInfo.getGame().getGameId();
    private final List<Deal> dealList = dtoMappers.toDealsList(FakeGameLookupResponses.gameLookupResponse1, gameId);

    //  Additional data
    private final List<Store> additionalStores = ListUtils.map(dealList, deal -> dtoMappers.toStore(FakeDataStores.copy(FakeDataStores.storeDto1, deal.getStoreId())));
    private List<Store> allStores() {
        List<Store> allStores = new ArrayList<>(additionalStores.size() + 1);
        allStores.add(store);
        allStores.addAll(additionalStores);
        return allStores;
    }


    @Before
    public void initHappyPath() {
        SavedStateHandle savedStateHandle = new SavedStateHandle();
        savedStateHandle.set(DealDetailsViewModel.DEAL_ID_SAVED_STATE_KEY, dealId);

        Mockito.when(repository.getStores()).thenReturn(Single.just(allStores()));
        Mockito.when(repository.getDealById(dealId)).thenReturn(Single.just(dealWithGameInfo));
        Mockito.when(repository.getDealsForGame(gameId)).thenReturn(Single.just(dealList));

        viewModel = new DealDetailsViewModel(repository, savedStateHandle);
    }


    @Test
    public void testHappyPath_getStoreListFlowable() throws InterruptedException {
        viewModel.getStoreListFlowable().test().await()
                .assertResult(allStores());
    }

    @Test
    public void testHappyPath_getDealWithGameInfoFlowable() throws InterruptedException {
        viewModel.getDealWithGameInfoFlowable().test().await()
                .assertResult(dealWithGameInfo);
    }

    @Test
    public void testHappyPath_getDealWithGameAndStoreInfoFlowable() throws InterruptedException {
        viewModel.getDealWithGameAndStoreInfoFlowable().test().await()
                .assertResult(new DealWithGameAndStoreInfo(dealWithGameInfo, store));
    }

    @Test
    public void testHappyPath_getGameId() throws InterruptedException {
        viewModel.getGameId().test().await()
                .assertResult(gameId);
    }

    @Test
    public void testHappyPath_getAdditionalDealsWithStoreInfoFlowable() throws InterruptedException {
        viewModel.getAdditionalDealsWithStoreInfoFlowable().test().await()
                .assertNoErrors();
    }


    @Test
    public void getDealWithGameAndStoreInfo() {
        Flowable<List<Store>> storeListFlowable = Flowable.just(allStores());
        Flowable<DealWithGameInfo> dealWithGameInfoFlowable = Flowable.just(dealWithGameInfo);

        viewModel.getDealWithGameAndStoreInfo(storeListFlowable, dealWithGameInfoFlowable)
                .test()
                .assertResult(new DealWithGameAndStoreInfo(dealWithGameInfo, store));
    }

    @Test
    public void getDealsWithStoreFlowable() {
        Flowable<String> gameIdFlowable = Flowable.just(gameId);
        viewModel.getDealsWithStoreFlowable(gameIdFlowable)
                .test()
                .assertNoErrors();
    }

}