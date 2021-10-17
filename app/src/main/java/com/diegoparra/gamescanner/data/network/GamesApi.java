package com.diegoparra.gamescanner.data.network;

import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.StoreDto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GamesApi {

    String BASE_URL = "https://www.cheapshark.com/api/1.0/";

    @GET("stores")
    Single<List<StoreDto>> getStores();

    @GET("deals")
    Single<List<DealsListItemDto>> getDeals(@Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);

    @GET("deals")
    Single<DealLookupResponse> getDealById(@Query(value = "id", encoded = true) String dealId);
    //  Must use encoded true, as dealId may contain special characters as % and I don't want to change them in the URL by encoding

    @GET("deals")
    Single<List<DealsListItemDto>> getDealsByTitle(@Query("title") String title);

    @GET("games")
    Single<List<GameListItemDto>> getGamesByTitle(@Query("title") String title);

    @GET("games")
    Single<GameLookupResponse> getDealsForGame(@Query("id") String gameId);

}
