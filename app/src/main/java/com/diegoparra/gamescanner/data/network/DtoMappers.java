package com.diegoparra.gamescanner.data.network;

import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.StoreDto;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.Store;

import java.util.List;

public interface DtoMappers {

    Store toStore(StoreDto storeDto);
    DealWithGameInfo toDealWithGameInfo(DealsListItemDto dealsListItemDto);
    Game toGame(DealLookupResponse dealLookupResponse);
    List<Deal> toDealsList(GameLookupResponse gameLookupResponse, String gameId);

}
