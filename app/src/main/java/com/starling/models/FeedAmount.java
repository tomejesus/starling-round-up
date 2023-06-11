package com.starling.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedAmount {
    public String currency;
    public int minorUnits;

    @JsonCreator
    public FeedAmount(
            @JsonProperty("currency") String currency,
            @JsonProperty("minorUnits") int minorUnits) {
        this.currency = currency;
        this.minorUnits = minorUnits;
    }
}
