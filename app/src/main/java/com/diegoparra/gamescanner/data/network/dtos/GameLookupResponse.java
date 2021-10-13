package com.diegoparra.gamescanner.data.network.dtos;

import java.util.List;
import java.util.Map;

public class GameLookupResponse {

    //  Can get this, but it is not so relevant
    private Map<String, String> info;
    private CheapestPriceDto cheapestPriceEver;
    private List<DealDto> deals;

    public GameLookupResponse(Map<String, String> info, CheapestPriceDto cheapestPriceEver, List<DealDto> deals) {
        this.info = info;
        this.cheapestPriceEver = cheapestPriceEver;
        this.deals = deals;
    }

    public String getTitle() {
        String key = "title";
        if(info != null && info.containsKey(key)) {
            return info.get(key);
        } else {
            return null;
        }
    }

    public String getSteamAppId() {
        String key = "steamAppID";
        if(info != null && info.containsKey(key)) {
            return info.get(key);
        } else {
            return null;
        }
    }

    public String getThumb() {
        String key = "thumb";
        if(info != null && info.containsKey(key)) {
            return info.get(key);
        } else {
            return null;
        }
    }

    public CheapestPriceDto getCheapestPriceEver() {
        return cheapestPriceEver;
    }

    public List<DealDto> getDeals() {
        return deals;
    }
}
