package com.diegoparra.gamescanner.data;

import com.diegoparra.gamescanner.data.network.dtos.StoreDto;

import java.util.HashMap;
import java.util.Map;

public class FakeDataStores {

    private static Map<String, String> createStoreImagesMap(String banner, String logo, String icon) {
        Map<String, String> imagesMap = new HashMap<>();
        imagesMap.put("banner", banner);
        imagesMap.put("logo", logo);
        imagesMap.put("icon", icon);
        return imagesMap;
    }

    public static StoreDto copy(StoreDto storeDto, String storeId) {
        return new StoreDto(
                storeId,
                storeDto.getStoreName(),
                (storeDto.isActive() == null) ? null : (storeDto.isActive() ? 1 : 0),
                createStoreImagesMap(
                        storeDto.getImgBanner(),
                        storeDto.getImgLogo(),
                        storeDto.getImgIcon()
                )
        );
    }


    public static StoreDto storeDto1 = new StoreDto(
            "1",
            "Steam",
            1,
            createStoreImagesMap(
                    "/img/stores/banners/0.png",
                    "/img/stores/logos/0.png",
                    "/img/stores/icons/0.png"
            )
    );

    public static StoreDto storeDto2 = new StoreDto(
            "2",
            "GamersGate",
            1,
            createStoreImagesMap(
                    "/img/stores/banners/1.png",
                    "/img/stores/logos/1.png",
                    "/img/stores/icons/1.png"
            )
    );

    public static StoreDto storeDto3 = new StoreDto(
            "3",
            "GreenManGaming",
            1,
            createStoreImagesMap(
                    "/img/stores/banners/2.png",
                    "/img/stores/logos/2.png",
                    "/img/stores/icons/2.png"
            )
    );

    public static StoreDto storeDto4 = new StoreDto(
            "4",
            "Amazon",
            0,
            createStoreImagesMap(
                    "/img/stores/banners/3.png",
                    "/img/stores/logos/3.png",
                    "/img/stores/icons/3.png"
            )
    );


}
