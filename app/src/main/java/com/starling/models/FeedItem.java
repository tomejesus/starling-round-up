package com.starling.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedItem {
    public String feedItemUid;
    public String direction;
    public FeedAmount amount;

    @JsonCreator
    public FeedItem(
            @JsonProperty("feedItemUid") String feedItemId,
            @JsonProperty("direction") String direction,
            @JsonProperty("amount") FeedAmount amount) {
        this.feedItemUid = feedItemId;
        this.direction = direction;
        this.amount = amount;
    }
}
