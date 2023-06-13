package com.starling.services;

import com.starling.models.FeedItems;

public interface IFeedService {
    FeedItems getFeedItems(String accountId, String weekEndInputString, String bearerToken);
}
