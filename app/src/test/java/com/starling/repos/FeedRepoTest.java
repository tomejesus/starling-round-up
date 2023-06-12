package com.starling.repos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FeedRepoTest {

    private FeedRepo feedRepo;

    @Mock
    private HttpClient httpClient;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        feedRepo = new FeedRepo(httpClient, logger);
    }

    @Test
    void testGetFeedItemsSuccess() throws Exception {
        // Arrange
        String accountId = "MockAccountId";
        String startDate = LocalDate.now().toString();
        String token = "MockToken";

        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body())
                .thenReturn("{\"feedItems\":[{\"amount\":{\"currency\":\"GBP\",\"minorUnits\":1234}}]}");

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        // Act
        String response = feedRepo.getFeedItems(accountId, startDate, token);

        // Assert
        assertEquals("{\"feedItems\":[{\"amount\":{\"currency\":\"GBP\",\"minorUnits\":1234}}]}", response);
    }

    @Test
    void testGetFeedItemsError() throws Exception {
        // Arrange
        String accountId = "MockAccountId";
        String startDate = LocalDate.now().toString();
        String token = "MockToken";

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new RuntimeException());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> feedRepo.getFeedItems(accountId, startDate, token));
    }

    @Test
    void testGetFeedItemsInvalidDate() {
        // Arrange
        String accountId = "MockAccountId";
        String startDate = "InvalidDate";
        String token = "MockToken";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> feedRepo.getFeedItems(accountId, startDate, token));
    }
}
