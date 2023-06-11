package com.starling.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedItems {
    public FeedItem[] feedItems;

    @JsonCreator
    public FeedItems(@JsonProperty("feedItems") FeedItem[] feedItems) {
        this.feedItems = feedItems;
    }
}
