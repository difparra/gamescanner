package com.diegoparra.gamescanner.data.network;

import androidx.annotation.NonNull;

import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.StoreDto;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;

import java.util.List;

public interface DtoMappers {

    @NonNull
    Store toStore(@NonNull StoreDto storeDto);

    @NonNull
    DealWithGameInfo toDealWithGameInfo(@NonNull DealsListItemDto dealsListItemDto);

    @NonNull
    DealWithGameInfo toDealWithGameInfo(@NonNull DealLookupResponse dealLookupResponse, @NonNull String dealId);

    @NonNull
    List<Deal> toDealsList(@NonNull GameLookupResponse gameLookupResponse, @NonNull String gameId);

    @NonNull
    Game toGame(@NonNull GameListItemDto gameListItemDto);

}
