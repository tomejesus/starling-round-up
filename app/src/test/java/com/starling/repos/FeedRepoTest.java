package com.starling.repos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.starling.client.IHttpClientWrapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FeedRepoTest {

    private FeedRepo feedRepo;

    @Mock
    private IHttpClientWrapper httpClientWrapper;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        feedRepo = new FeedRepo(httpClientWrapper, logger);
    }

    @Test
    void testGetFeedItemsSuccess() throws Exception {
        // Arrange
        String accountId = "MockAccountId";
        String startDate = LocalDate.now().toString();

        when(httpClientWrapper.get(anyString()))
                .thenReturn("{\"feedItems\":[{\"amount\":{\"currency\":\"GBP\",\"minorUnits\":1234}}]}");

        // Act
        String response = feedRepo.getFeedItems(accountId, startDate);

        // Assert
        assertEquals("{\"feedItems\":[{\"amount\":{\"currency\":\"GBP\",\"minorUnits\":1234}}]}", response);
    }

    @Test
    void testGetFeedItemsError() {
        // Arrange
        String accountId = "MockAccountId";
        String startDate = LocalDate.now().toString();

        when(httpClientWrapper.get(anyString()))
                .thenThrow(new RuntimeException());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> feedRepo.getFeedItems(accountId, startDate));
    }

    @Test
    void testGetFeedItemsInvalidDate() {
        // Arrange
        String accountId = "MockAccountId";
        String startDate = "InvalidDate";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> feedRepo.getFeedItems(accountId, startDate));
    }
}
