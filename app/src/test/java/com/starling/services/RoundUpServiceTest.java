package com.starling.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.starling.models.FeedAmount;
import com.starling.models.FeedItem;
import com.starling.models.FeedItems;

public class RoundUpServiceTest {
    @Test
    public void testCalculateTotalSavingsWhenThereAreRemainders() {
        FeedItem[] feedItemArray = new FeedItem[] {
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 150)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 250)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 350)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 450)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 550)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 650)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 750)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 850)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 950)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1050)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1180)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1280)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1380)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1480)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1580))
        };
        FeedItems feedItems = new FeedItems(feedItemArray);
        assertEquals(600, RoundUpService.calculateTotalSavings(feedItems));
    }

    @Test
    public void testCalculateTotalSavingsWhenThereAreNoRemainders() {
        FeedItem[] feedItemArray = new FeedItem[] {
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 100)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 200)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 300)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 400)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 500)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 600)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 700)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 800)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 900)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1000)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1100)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1200)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1300)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1400)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1500))
        };
        FeedItems feedItems = new FeedItems(feedItemArray);
        assertEquals(0, RoundUpService.calculateTotalSavings(feedItems));
    }

    @Test
    public void testCalculateTotalSavingsDoesNotCountInTransactions() {
        FeedItem[] feedItemArray = new FeedItem[] {
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 150)),
                new FeedItem("2017-10-14T14:00:00Z", "IN", new FeedAmount("GBP", 250)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 350)),
                new FeedItem("2017-10-14T14:00:00Z", "IN", new FeedAmount("GBP", 450)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 550)),
                new FeedItem("2017-10-14T14:00:00Z", "IN", new FeedAmount("GBP", 650)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 750)),
                new FeedItem("2017-10-14T14:00:00Z", "IN", new FeedAmount("GBP", 850)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 950)),
                new FeedItem("2017-10-14T14:00:00Z", "IN", new FeedAmount("GBP", 1050)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1150)),
                new FeedItem("2017-10-14T14:00:00Z", "IN", new FeedAmount("GBP", 1250)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1350)),
                new FeedItem("2017-10-14T14:00:00Z", "IN", new FeedAmount("GBP", 1450)),
                new FeedItem("2017-10-14T14:00:00Z", "OUT", new FeedAmount("GBP", 1550))
        };
        FeedItems feedItems = new FeedItems(feedItemArray);
        assertEquals(400, RoundUpService.calculateTotalSavings(feedItems));
    }
}
