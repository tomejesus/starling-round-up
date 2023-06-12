package com.starling.services;

import com.starling.Constants;
import com.starling.models.FeedItem;
import com.starling.models.FeedItems;

public class RoundUpService {

    public static int calculateRoundUp(FeedItems feedItems) {
        int totalSavings = 0;
        for (FeedItem feedItem : feedItems.feedItems) {
            int remainder = feedItem.amount.minorUnits % 100;

            if (feedItem.direction.equals(Constants.OUTGOING_TRANSACTION)
                    && remainder != 0) {
                totalSavings += (100 - remainder);
            }
        }
        return totalSavings;
    }

}
