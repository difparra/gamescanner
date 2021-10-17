package com.diegoparra.gamescanner.data.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.diegoparra.gamescanner.data.network.dtos.DealDto;
import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.DealsListItemDto;
import com.diegoparra.gamescanner.data.network.dtos.GameInfoDto;
import com.diegoparra.gamescanner.data.network.dtos.GameListItemDto;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DtoMappersImpl implements DtoMappers {

    private static final String CHEAPSHARK_URL_PREFIX = "https://www.cheapshark.com";
    private static final String METACRITIC_URL_PREFIX = "https://www.metacritic.com";
    private static final String BASE_GO_TO_DEAL_LINK = "https://www.cheapshark.com/redirect?dealID=";

    private static final String DEAFULT_STRING = "";
    private static final float DEFAULT_FLOAT = 0;

    @NonNull
    @Override
    public Store toStore(@NonNull StoreDto storeDto) {
        return new Store(
                Objects.requireNonNull(storeDto.getStoreId()),
                toStringOrDefault(storeDto.getStoreName()),
                storeDto.isActive(),
                CHEAPSHARK_URL_PREFIX + storeDto.getImgBanner(),
                CHEAPSHARK_URL_PREFIX + storeDto.getImgLogo(),
                CHEAPSHARK_URL_PREFIX + storeDto.getImgIcon()
        );
    }

    @NonNull
    @Override
    public DealWithGameInfo toDealWithGameInfo(@NonNull DealsListItemDto dealsListItemDto) {
        return new DealWithGameInfo(
                new Deal(
                        Objects.requireNonNull(dealsListItemDto.getDealId()),
                        Objects.requireNonNull(dealsListItemDto.getGameId()),
                        Objects.requireNonNull(dealsListItemDto.getStoreId()),
                        toFloatOrDefault(dealsListItemDto.getNormalPrice()),
                        toFloatOrDefault(dealsListItemDto.getSalePrice()),
                        toInstantOrNull(dealsListItemDto.getLastChange()),
                        BASE_GO_TO_DEAL_LINK + dealsListItemDto.getDealId()
                ),
                new Game(
                        dealsListItemDto.getGameId(),
                        toStringOrDefault(dealsListItemDto.getTitle()),
                        dealsListItemDto.getThumb(),
                        new SteamInfo(dealsListItemDto.getSteamRatingText(), dealsListItemDto.getSteamRatingPercent(), dealsListItemDto.getSteamRatingCount()),
                        new MetacriticInfo(METACRITIC_URL_PREFIX + dealsListItemDto.getMetacriticLink(), dealsListItemDto.getMetacriticScore()),
                        toInstantOrNull(dealsListItemDto.getReleaseDate()),
                        null,
                        null
                )
        );
    }

    @NonNull
    @Override
    public DealWithGameInfo toDealWithGameInfo(@NonNull DealLookupResponse dealLookupResponse, @NonNull String dealId) {
        GameInfoDto gameInfoDto = dealLookupResponse.getGameInfo();
        Objects.requireNonNull(gameInfoDto);
        return new DealWithGameInfo(
                new Deal(
                        dealId,
                        Objects.requireNonNull(gameInfoDto.getGameId()),
                        Objects.requireNonNull(gameInfoDto.getStoreId()),
                        toFloatOrDefault(gameInfoDto.getNormalPrice()),
                        toFloatOrDefault(gameInfoDto.getSalePrice()),
                        null,
                        BASE_GO_TO_DEAL_LINK + dealId
                ),
                new Game(
                        gameInfoDto.getGameId(),
                        toStringOrDefault(gameInfoDto.getName()),
                        gameInfoDto.getThumb(),
                        new SteamInfo(gameInfoDto.getSteamRatingText(), gameInfoDto.getSteamRatingPercent(), gameInfoDto.getSteamRatingCount()),
                        new MetacriticInfo(METACRITIC_URL_PREFIX + gameInfoDto.getMetacriticLink(), gameInfoDto.getMetacriticScore()),
                        toInstantOrNull(gameInfoDto.getReleaseDate()),
                        null,
                        null
                )
        );
    }

    @NonNull
    @Override
    public List<Deal> toDealsList(@NonNull GameLookupResponse gameLookupResponse, @NonNull String gameId) {
        List<DealDto> dealDtos = gameLookupResponse.getDeals() != null ? gameLookupResponse.getDeals() : Collections.emptyList();
        return ListUtils.map(dealDtos, dealDto -> toDeal(dealDto, gameId));
    }

    @NonNull
    private Deal toDeal(@NonNull DealDto dealDto, @NonNull String gameId) {
        return new Deal(
                Objects.requireNonNull(dealDto.getDealId()),
                gameId,
                Objects.requireNonNull(dealDto.getStoreId()),
                toFloatOrDefault(dealDto.getNormalPrice()),
                toFloatOrDefault(dealDto.getSalePrice()),
                null,
                BASE_GO_TO_DEAL_LINK + dealDto.getDealId()
        );
    }


    @NonNull
    @Override
    public Game toGame(@NonNull GameListItemDto gameListItemDto) {
        return new Game(
                Objects.requireNonNull(gameListItemDto.getGameId()),
                toStringOrDefault(gameListItemDto.getTitle()),
                gameListItemDto.getThumb(),
                null,
                null,
                null,
                gameListItemDto.getCheapestDealId(),
                gameListItemDto.getCheapestPrice()
        );
    }


    //      UTIL FUNCTIONS FOR MAPPING      --------------------------------------------------------

    private float toFloatOrDefault(@Nullable Float number) {
        return number != null ? number : DEFAULT_FLOAT;
    }

    @NonNull
    private String toStringOrDefault(@Nullable String string) {
        return string != null ? string : DEAFULT_STRING;
    }

    @Nullable
    private Instant toInstantOrNull(Long epochSecond) {
        if (epochSecond == null || epochSecond <= 0) {
            return null;
        } else {
            return Instant.ofEpochSecond(epochSecond);
        }
    }

}
