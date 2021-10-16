package com.diegoparra.gamescanner.data.network;

import com.diegoparra.gamescanner.data.network.dtos.DealDto;
import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameInfoDto;
import com.diegoparra.gamescanner.data.network.dtos.GameLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.StoreDto;
import com.diegoparra.gamescanner.models.Deal;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.models.Game;
import com.diegoparra.gamescanner.models.MetacriticInfo;
import com.diegoparra.gamescanner.models.SteamInfo;
import com.diegoparra.gamescanner.models.Store;
import com.diegoparra.gamescanner.utils.ListUtils;

import java.time.Instant;
import java.util.List;

public class DtoMappersImpl implements DtoMappers {

    private static final String CHEAPSHARK_URL_PREFIX = "https://www.cheapshark.com";
    private static final String METACRITIC_URL_PREFIX = "https://www.metacritic.com";

    @Override
    public Store toStore(StoreDto storeDto) {
        return new Store(
                storeDto.getStoreId(),
                storeDto.getStoreName(),
                storeDto.isActive(),
                CHEAPSHARK_URL_PREFIX + storeDto.getImgBanner(),
                CHEAPSHARK_URL_PREFIX + storeDto.getImgLogo(),
                CHEAPSHARK_URL_PREFIX + storeDto.getImgIcon()
        );
    }

    @Override
    public DealWithGameInfo toDealWithGameInfo(DealsListItemDto dealsListItemDto) {
        return new DealWithGameInfo(
                new Deal(
                        dealsListItemDto.getDealId(),
                        dealsListItemDto.getGameId(),
                        dealsListItemDto.getStoreId(),
                        dealsListItemDto.getNormalPrice(),
                        dealsListItemDto.getSalePrice(),
                        toInstantOrNull(dealsListItemDto.getLastChange())
                ),
                new Game(
                        dealsListItemDto.getGameId(),
                        dealsListItemDto.getTitle(),
                        dealsListItemDto.getThumb(),
                        new SteamInfo(dealsListItemDto.getSteamRatingText(), dealsListItemDto.getSteamRatingPercent(), dealsListItemDto.getSteamRatingCount()),
                        new MetacriticInfo(METACRITIC_URL_PREFIX + dealsListItemDto.getMetacriticLink(), dealsListItemDto.getMetacriticScore()),
                        toInstantOrNull(dealsListItemDto.getReleaseDate())
                )
        );
    }

    @Override
    public DealWithGameInfo toDealWithGameInfo(DealLookupResponse dealLookupResponse, String dealId) {
        GameInfoDto gameInfoDto = dealLookupResponse.getGameInfo();
        if(gameInfoDto != null) {
            return new DealWithGameInfo(
                    new Deal(
                            dealId,
                            gameInfoDto.getGameId(),
                            gameInfoDto.getStoreId(),
                            gameInfoDto.getNormalPrice(),
                            gameInfoDto.getSalePrice(),
                            null
                    ),
                    new Game(
                            gameInfoDto.getGameId(),
                            gameInfoDto.getName(),
                            gameInfoDto.getThumb(),
                            new SteamInfo(gameInfoDto.getSteamRatingText(), gameInfoDto.getSteamRatingPercent(), gameInfoDto.getSteamRatingCount()),
                            new MetacriticInfo(METACRITIC_URL_PREFIX + gameInfoDto.getMetacriticLink(), gameInfoDto.getMetacriticScore()),
                            toInstantOrNull(gameInfoDto.getReleaseDate())
                    )
            );
        }else{
            return null;
        }
    }

    @Override
    public List<Deal> toDealsList(GameLookupResponse gameLookupResponse, String gameId) {
        List<DealDto> dealDtos = gameLookupResponse.getDeals();
        return ListUtils.map(dealDtos, dealDto -> toDeal(dealDto, gameId));
    }

    private Deal toDeal(DealDto dealDto, String gameId) {
        return new Deal(
                dealDto.getDealId(),
                gameId,
                dealDto.getStoreId(),
                dealDto.getNormalPrice(),
                dealDto.getSalePrice(),
                null
        );
    }


    private Instant toInstantOrNull(long epochSecond) {
        if(epochSecond <= 0) {
            return null;
        }else {
            return Instant.ofEpochSecond(epochSecond);
        }
    }

}
