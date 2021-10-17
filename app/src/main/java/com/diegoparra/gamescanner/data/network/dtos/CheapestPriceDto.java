package com.diegoparra.gamescanner.data.network.dtos;

import androidx.annotation.Nullable;

public class CheapestPriceDto {

    @Nullable private final Float price;
    @Nullable private final Long date;

    public CheapestPriceDto(@Nullable Float price, @Nullable Long date) {
        this.price = price;
        this.date = date;
    }

    @Nullable
    public Float getPrice() {
        return price;
    }

    @Nullable
    public Long getDate() {
        return date;
    }
}
