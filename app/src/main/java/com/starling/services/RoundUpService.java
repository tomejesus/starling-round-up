package com.starling.services;

import com.starling.models.FeedItem;
import com.starling.models.FeedItems;

public class RoundUpService {
    public static int calculateTotalSavings(FeedItems feedItems) {
        int totalSavings = 0;
        for (FeedItem feedItem : feedItems.feedItems) {
            int remainder = feedItem.amount.minorUnits % 100;

            if (feedItem.feedItemUid.substring(0, 7).equals("ROUNDUP") && feedItem.direction.equals("OUT")
                    && remainder != 0) {
                totalSavings += (100 - remainder);
            }
        }
        return totalSavings;
    }
}
