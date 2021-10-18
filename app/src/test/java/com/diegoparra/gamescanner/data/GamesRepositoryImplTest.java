package com.diegoparra.gamescanner.data;

import com.diegoparra.gamescanner.data.network.DtoMappers;
import com.diegoparra.gamescanner.data.network.DtoMappersImpl;
import com.diegoparra.gamescanner.data.network.GamesApi;
import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.StoreDto;
import com.diegoparra.gamescanner.utils.ListUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class GamesRepositoryImplTest {

    @Mock
    private GamesApi gamesApi;
    private DtoMappers dtoMappers;
    private GamesRepositoryImpl repository;


    @Before
    public void setUp() {
        dtoMappers = new DtoMappersImpl();
        repository = new GamesRepositoryImpl(gamesApi, dtoMappers);
    }

    @Test
    public void getStores_success_returnSingleStores() {
        List<StoreDto> storeDtoList = Arrays.asList(FakeDataStores.storeDto1, FakeDataStores.storeDto2, FakeDataStores.storeDto3);
        Mockito.when(gamesApi.getStores()).thenReturn(Single.just(storeDtoList));
        repository.getStores()
                .test()
                .assertResult(ListUtils.map(storeDtoList, dtoMappers::toStore))
                .dispose();
    }

    @Test
    public void getStores_emptyList_returnSingleEmptyList() {
        Mockito.when(gamesApi.getStores()).thenReturn(Single.just(Collections.emptyList()));
        repository.getStores()
                .test()
                .assertResult(Collections.emptyList())
                .dispose();
    }

    @Test
    public void getStores_failureFromApi_returnError() {
        Throwable error = new UnknownHostException();
        Mockito.when(gamesApi.getStores()).thenReturn(Single.error(error));
        repository.getStores()
                .test()
                .assertError(error)
                .dispose();
    }


    /*
        TODO:   Test getDeals and paging library
     */

    @Test
    public void getDealById_success_returnSuccess() {
        String dealId = "dealId";
        DealLookupResponse dealLookupResponse = FakeDealLookupResponses.dealLookupResponse1;
        Mockito.when(gamesApi.getDealById(dealId)).thenReturn(Single.just(dealLookupResponse));
        repository.getDealById(dealId)
                .test()
                .assertResult(dtoMappers.toDealWithGameInfo(dealLookupResponse, dealId))
                .dispose();
    }

    @Test
    public void getDealById_failure_returnError() {
        String dealId = "dealId";
        Throwable error = new UnknownHostException();
        Mockito.when(gamesApi.getDealById(dealId)).thenReturn(Single.error(error));
        repository.getDealById(dealId)
                .test()
                .assertError(error)
                .dispose();
    }


    @Test
    public void getDealsByGameTitle_success_returnSuccess() {
        String title = "title";
        List<DealsListItemDto> dealsListItemDtoList = Arrays.asList(FakeDealsListItemDto.dealListItemDto1, FakeDealsListItemDto.dealListItemDto2);
        Mockito.when(gamesApi.getDealsByTitle(title)).thenReturn(Single.just(dealsListItemDtoList));
        repository.getDealsByGameTitle(title)
                .test()
                .assertResult(ListUtils.map(dealsListItemDtoList, dtoMappers::toDealWithGameInfo))
                .dispose();
    }

    @Test
    public void getDealsByGameTitle_failure_returnError() {
        String title = "title";
        Throwable error = new UnknownHostException();
        Mockito.when(gamesApi.getDealsByTitle(title)).thenReturn(Single.error(error));
        repository.getDealsByGameTitle(title)
                .test()
                .assertError(error)
                .dispose();
    }

    @Test
    public void getGamesByTitle_success_returnSuccess() {
        String title = "title";
        List<GameListItemDto> gameListItemDtoList = Arrays.asList(FakeGameListItemDto.gameListItemDto1, FakeGameListItemDto.gameListItemDto2, FakeGameListItemDto.gameListItemDto3);
        Mockito.when(gamesApi.getGamesByTitle(title)).thenReturn(Single.just(gameListItemDtoList));
        repository.getGamesByTitle(title)
                .test()
                .assertResult(ListUtils.map(gameListItemDtoList, dtoMappers::toGame))
                .dispose();
    }

    @Test
    public void getGamesByGameTitle_failure_returnError() {
        String title = "title";
        Throwable error = new UnknownHostException();
        Mockito.when(gamesApi.getGamesByTitle(title)).thenReturn(Single.error(error));
        repository.getGamesByTitle(title)
                .test()
                .assertError(error)
                .dispose();
    }

    @Test
    public void getDealsForGame_success_returnSuccess() {
        String gameId = "gameId";
        GameLookupResponse gameLookupResponse = FakeGameLookupResponses.gameLookupResponse1;
        Mockito.when(gamesApi.getDealsForGame(gameId)).thenReturn(Single.just(gameLookupResponse));
        repository.getDealsForGame(gameId)
                .test()
                .assertResult(dtoMappers.toDealsList(gameLookupResponse, gameId))
                .dispose();
    }

    @Test
    public void getDealsForGame_failure_returnError() {
        String gameId = "gameId";
        Throwable error = new UnknownHostException();
        Mockito.when(gamesApi.getDealsForGame(gameId)).thenReturn(Single.error(error));
        repository.getDealsForGame(gameId)
                .test()
                .assertError(error)
                .dispose();
    }


}