package com.diegoparra.gamescanner.data;

import com.diegoparra.gamescanner.data.network.dtos.DealLookupResponse;
import com.diegoparra.gamescanner.data.network.dtos.GameInfoDto;

public class FakeDealLookupResponses {

    public static DealLookupResponse dealLookupResponse1 = new DealLookupResponse(
            new GameInfoDto(
                    "1",
                    "93503",
                    "BioShock Infinite",
                    "8870",
                    29.99f,
                    29.99f,
                    "Overwhelmingly Positive",
                    95,
                    86740L,
                    94,
                    "/game/pc/bioshock-infinite",
                    1364169600L,
                    "N/A",
                    "1",
                    "https://cdn.cloudflare.steamstatic.com/steam/apps/8870/capsule_sm_120.jpg?t=1602794480"
            )
    );

    public static DealLookupResponse dealLookupResponse2 = new DealLookupResponse(
            new GameInfoDto(
                    "21",
                    "235298",
                    "Nickelodeon All-Star Brawl",
                    "1414850",
                    33.98f,
                    49.99f,
                    "Very Positive",
                    84,
                    2738L,
                    0,
                    "/game/pc/nickelodeon-all-star-brawl",
                    1633392000L,
                    "N/A",
                    "1",
                    "https://cdn.cloudflare.steamstatic.com/steam/apps/1414850/capsule_sm_120.jpg?t=1634078339"
            )
    );

}
