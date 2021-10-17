package com.diegoparra.gamescanner.data.network.dtos;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

public class GameLookupResponse {

    //  Can get this, but it is not so relevant
    @Nullable private final Map<String, String> info;
    @Nullable private final CheapestPriceDto cheapestPriceEver;
    @Nullable private final List<DealDto> deals;

    public GameLookupResponse(@Nullable Map<String, String> info, @Nullable CheapestPriceDto cheapestPriceEver, @Nullable List<DealDto> deals) {
        this.info = info;
        this.cheapestPriceEver = cheapestPriceEver;
        this.deals = deals;
    }

    @Nullable
    public String getTitle() {
        String key = "title";
        if(info != null && info.containsKey(key)) {
            return info.get(key);
        } else {
            return null;
        }
    }

    @Nullable
    public String getSteamAppId() {
        String key = "steamAppID";
        if(info != null && info.containsKey(key)) {
            return info.get(key);
        } else {
            return null;
        }
    }

    @Nullable
    public String getThumb() {
        String key = "thumb";
        if(info != null && info.containsKey(key)) {
            return info.get(key);
        } else {
            return null;
        }
    }

    @Nullable
    public CheapestPriceDto getCheapestPriceEver() {
        return cheapestPriceEver;
    }

    @Nullable
    public List<DealDto> getDeals() {
        return deals;
    }
}
