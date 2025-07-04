package com.starling.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.models.FeedItems;
import com.starling.repos.IFeedRepo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FeedServiceTest {

    private IFeedService feedService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IFeedRepo feedRepo;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        feedService = new FeedService(feedRepo, objectMapper, logger);
    }

    @Test
    void testGetFeedItemsSuccess() throws Exception {
        // Arrange
        String mockFeedJson = "{\"feedItems\":[{\"feedItemUid\":\"MockFeedItem\"}]}";
        when(feedRepo.getFeedItems("MockAccountId", "MockDate")).thenReturn(mockFeedJson);

        // Act
        FeedItems feedItems = feedService.getFeedItems("MockAccountId", "MockDate");

        // Assert
        assertEquals("MockFeedItem", feedItems.feedItems[0].feedItemUid);
    }

    @Test
    void testGetFeedItemsNoFeedItems() {
        // Arrange
        when(feedRepo.getFeedItems("MockAccountId", "MockDate")).thenReturn(null);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> feedService.getFeedItems("MockAccountId", "MockDate"));
    }

    @Test
    void testGetFeedItemsRepoError() {
        // Arrange
        when(feedRepo.getFeedItems("MockAccountId", "MockDate")).thenThrow(new RuntimeException("Mocked repo error"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> feedService.getFeedItems("MockAccountId", "MockDate"));
    }

    @Test
    void testGetFeedItemsParseError() {
        // Arrange
        when(feedRepo.getFeedItems("MockAccountId", "MockDate")).thenReturn("Not valid JSON");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> feedService.getFeedItems("MockAccountId", "MockDate"));
    }
}
