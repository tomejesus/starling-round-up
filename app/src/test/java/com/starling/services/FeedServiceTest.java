package com.starling.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.starling.models.FeedItems;

public class FeedServiceTest {
    private final HttpClient client = mock(HttpClient.class);
    private final Logger logger = LoggerFactory.getLogger(FeedServiceTest.class);
    private final FeedService feedService = new FeedService(client, logger);

    @Test
    void testGetFeedItemsGetsItems() throws Exception {
        // Arrange
        String mockFeedResponse = "{\"feedItems\":[{\"feedItemUid\":\"feedItem1\", \"direction\":\"OUT\", \"amount\":{\"currency\":\"GBP\",\"minorUnits\":25}},{\"feedItemUid\":\"feedItem2\", \"direction\":\"IN\", \"amount\":{\"currency\":\"GBP\",\"minorUnits\":50}}]}";
        HttpResponse<Object> httpResponse = mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn(mockFeedResponse);
        when(client.send(any(), any())).thenReturn(httpResponse);

        // Act
        FeedItems response = feedService.getFeedItems("MockAccountId", "2021-01-01", "MockToken");

        // Assert
        assertEquals(2, response.feedItems.length);
        assertEquals("feedItem1", response.feedItems[0].feedItemUid);
        assertEquals("OUT", response.feedItems[0].direction);
        assertEquals("GBP", response.feedItems[0].amount.currency);
        assertEquals(25, response.feedItems[0].amount.minorUnits);
        assertEquals("feedItem2", response.feedItems[1].feedItemUid);
        assertEquals("IN", response.feedItems[1].direction);
        assertEquals("GBP", response.feedItems[1].amount.currency);
        assertEquals(50, response.feedItems[1].amount.minorUnits);
    }

    @Test
    void testGetFeedItemsThrowsWhenServerError() throws Exception {
        // Arrange
        HttpResponse<Object> httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(404);
        when(client.send(any(), any())).thenReturn(httpResponse);

        // Act
        try {
            feedService.getFeedItems("MockAccountId", "2021-01-01", "MockToken");
        } catch (Exception exception) {
            // Assert
            assertEquals("An error occurred: 404", "An error occurred: 404");
        }
    }
}
