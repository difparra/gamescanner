package com.diegoparra.gamescanner.data.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.utils.ListUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DealsPagingSource extends RxPagingSource<Integer, DealWithGameInfo> {

    private static final int CHEAPSHARK_STARTING_PAGE_INDEX = 0;
    private static final int NETWORK_PAGE_SIZE = 20;

    @NonNull
    private final GamesApi api;
    @NonNull
    private final DtoMappers dtoMappers;

    public DealsPagingSource(@NonNull GamesApi gamesApi, @NonNull DtoMappers dtoMappers) {
        this.api = gamesApi;
        this.dtoMappers = dtoMappers;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, DealWithGameInfo>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        //  Start refresh at page 1 if undefined.
        Integer position = loadParams.getKey();
        if (position == null) {
            position = CHEAPSHARK_STARTING_PAGE_INDEX;
        }

        return api.getDeals(position, loadParams.getLoadSize())
                .subscribeOn(Schedulers.io())
                .map(response -> toLoadResult(response, loadParams))
                .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, DealWithGameInfo> toLoadResult(@NonNull List<DealsListItemDto> response, LoadParams<Integer> loadParams) {
        return new LoadResult.Page<>(
                ListUtils.map(response, dtoMappers::toDealWithGameInfo),
                null,
                getNextKey(loadParams, response.isEmpty()),
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED
        );
    }

    private Integer getNextKey(LoadParams<Integer> loadParams, boolean resultListIsEmpty) {
        if (resultListIsEmpty) {
            return null;
        } else {
            Integer position = loadParams.getKey();
            if (position == null) {
                position = CHEAPSHARK_STARTING_PAGE_INDEX;
            }
            return position + (loadParams.getLoadSize() / NETWORK_PAGE_SIZE);
        }
    }


    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, DealWithGameInfo> pagingState) {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, DealWithGameInfo> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }
}
