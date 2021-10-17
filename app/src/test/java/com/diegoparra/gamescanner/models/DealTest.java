package com.diegoparra.gamescanner.models;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import java.time.Instant;

public class DealTest {

    @Test
    public void getDiscountPercent_givenValues() {
        float normalPrice = 100;
        float salePrice = 75;
        float discountPercent = 25;
        Deal deal = new Deal("dealId", "gameId", "storeId", normalPrice, salePrice, null, null);

        float result = deal.getDiscountPercent();

        assertThat(result).isEqualTo(discountPercent);
    }

    @Test
    public void isOnSale_samePrices_returnFalse() {
        float normalPrice = 100;
        float salePrice = 100;
        Deal deal = new Deal("dealId", "gameId", "storeId", normalPrice, salePrice, null, null);
        boolean result = deal.isOnSale();
        assertThat(result).isFalse();
    }

    @Test
    public void isOnSale_greaterSalePrice_returnFalse() {
        float normalPrice = 100;
        float salePrice = 125;
        Deal deal = new Deal("dealId", "gameId", "storeId", normalPrice, salePrice, null, null);
        boolean result = deal.isOnSale();
        assertThat(salePrice).isGreaterThan(normalPrice);
        assertThat(result).isFalse();
    }

    @Test
    public void isOnSale_lessSalePrice_returnTrue() {
        float normalPrice = 100;
        float salePrice = 75;
        Deal deal = new Deal("dealId", "gameId", "storeId", normalPrice, salePrice, null, null);
        boolean result = deal.isOnSale();
        assertThat(salePrice).isLessThan(normalPrice);
        assertThat(result).isTrue();
    }

    @Test
    public void lastChangeIsNull_getDateLasChangeAndGetTimeSinceLastChangeAreNull() {
        Instant lastChange = null;
        Deal deal = new Deal("dealId", "gameId", "storeId", 0, 0, lastChange, null);

        assertThat(deal.getDateLastChange()).isNull();
        assertThat(deal.getTimeSinceLastChange()).isNull();
    }

}