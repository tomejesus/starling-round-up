package com.starling.client;

import com.starling.models.FeedAmount;
import com.starling.models.FeedItem;
import com.starling.models.FeedItems;
import com.starling.services.AccountsService;
import com.starling.services.FeedService;
import com.starling.services.SavingGoalsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class StarlingClientTest {

    private StarlingClient client;

    @Mock
    private HttpClient httpClient;

    @Mock
    private AccountsService accountsService;

    @Mock
    private FeedService feedService;

    @Mock
    private SavingGoalsService savingGoalsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new StarlingClient(accountsService, feedService, savingGoalsService);
    }

    @Test
    void processSavings() {
        // Arrange
        String accountId = "acc123";
        String weekStart = "2023-01-01";
        String bearerToken = "bearer";
        int savings = 77;

        // Act
        when(accountsService.getPrimaryAccountId(bearerToken)).thenReturn(accountId);
        when(feedService.getFeedItems(accountId, weekStart, bearerToken)).thenReturn(createMockedFeedItems());

        client.processSavings(weekStart, bearerToken);

        // Assert
        verify(accountsService).getPrimaryAccountId(bearerToken);
        verify(feedService).getFeedItems(accountId, weekStart, bearerToken);
        verify(savingGoalsService).addMoneyToSavingsGoal(accountId, bearerToken, savings);
    }

    private FeedItems createMockedFeedItems() {
        FeedAmount feedAmount = new FeedAmount("USD", 123);
        FeedItem feedItem = new FeedItem("MockFeedItemId", "OUT", feedAmount);
        FeedItems feedItems = new FeedItems(new FeedItem[] { feedItem });
        return feedItems;
    }
}
