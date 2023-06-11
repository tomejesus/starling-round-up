package com.starling.models;

public class FeedItem {
    public String feedItemUid;
    public String direction;
    public FeedAmount amount;

    public FeedItem(String feedItemId, String direction, FeedAmount amount) {
        this.feedItemUid = feedItemId;
        this.direction = direction;
        this.amount = amount;
    }
}
