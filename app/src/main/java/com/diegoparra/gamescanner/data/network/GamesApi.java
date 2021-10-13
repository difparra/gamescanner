package com.diegoparra.gamescanner.data.network;

import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.StoreDto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GamesApi {

    String BASE_URL = "https://www.cheapshark.com/api/1.0/";

    @GET("stores")
    Observable<List<StoreDto>> getStores();

    @GET("deals")
    Observable<List<DealsListItemDto>> getDealsWithGameInfo();

    @GET("deals")
    Observable<DealLookupResponse> getDealWithGameInfo(@Query(value = "id", encoded = true) String dealId);
    //  Must use encoded true, as dealId may contain special characters as % and I don't want to change them in the URL by encoding

    @GET("games")
    Observable<GameLookupResponse> getDealsForGame(@Query("id") String gameId);

}
