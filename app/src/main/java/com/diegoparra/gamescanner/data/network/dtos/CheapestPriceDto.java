package com.diegoparra.gamescanner.data.network.dtos;

public class CheapestPriceDto {

    private float price;
    private long date;

    public CheapestPriceDto(float price, long date) {
        this.price = price;
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public long getDate() {
        return date;
    }
}
