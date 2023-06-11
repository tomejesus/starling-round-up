package com.starling.models;

public class FeedAmount {
    public String currency;
    public int minorUnits;

    public FeedAmount(String currency, int minorUnits) {
        this.currency = currency;
        this.minorUnits = minorUnits;
    }
}
