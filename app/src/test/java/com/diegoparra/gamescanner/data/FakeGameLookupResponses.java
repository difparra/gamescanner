package com.diegoparra.gamescanner.data;

import com.diegoparra.gamescanner.data.network.dtos.CheapestPriceDto;
import com.diegoparra.gamescanner.data.network.dtos.DealDto;
import com.diegoparra.gamescanner.data.network.dtos.GameLookupResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FakeGameLookupResponses {

    private static Map<String, String> createInfoMap(String title, String steamAppId, String thumb) {
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("steamAppId", steamAppId);
        map.put("thumb", thumb);
        return map;
    }

    public static final DealDto dealDto1 = new DealDto(
            "21",
            "on3LSvb5YoZJvxvvcndY3GYjXMWBgqoj30gdJVJ%2Bkdc%3D",
            33.98f,
            49.99f,
            32.026405f
    );


    public static GameLookupResponse gameLookupResponse1 = new GameLookupResponse(
            createInfoMap("Nickelodeon All-Star Brawl", "1414850", "https://cdn.cloudflare.steamstatic.com/steam/apps/1414850/capsule_sm_120.jpg?t=1634078339"),
            new CheapestPriceDto(33.98f, 1633553074L),
            Arrays.asList(
                    new DealDto(
                            "21",
                            "on3LSvb5YoZJvxvvcndY3GYjXMWBgqoj30gdJVJ%2Bkdc%3D",
                            33.98f,
                            49.99f,
                            32.026405f
                    ),
                    new DealDto(
                            "15",
                            "%2FrALQx4L1b4vEXQJ29g7DUWwNDtdCJeOmXpK8keq6kg%3D",
                            37.49f,
                            49.99f,
                            25.005001f
                    ),
                    new DealDto(
                            "27",
                            "1GMqVXVWJYmjhfwC1HMXprZEQYHBI8ooq75bo5YuHJ0%3D",
                            37.49f,
                            49.99f,
                            25.005001f
                    )
            )
    );


    public static GameLookupResponse gameLookupResponse2 = new GameLookupResponse(
            createInfoMap("BioShock Infinite", "8870", "https://cdn.cloudflare.steamstatic.com/steam/apps/8870/capsule_sm_120.jpg?t=1602794480"),
            new CheapestPriceDto(5.27f, 1543553226L),
            Arrays.asList(
                    new DealDto(
                            "3",
                            "IIokw%2BluKJ5aKPP6t7UTl7mkCLmEWDVhipCOS9FY4D4%3D",
                            6.75f,
                            29.99f,
                            77.492497f
                    ),
                    new DealDto(
                            "11",
                            "e%2FE%2FHQfd0RiMBE%2FRh%2Fm9606KlgVCh7qxzZ4zCry9sW4%3D",
                            7.49f,
                            29.99f,
                            75.025008f
                    )
            )
    );

}
