package com.starling.client;

import com.starling.models.FeedAmount;
import com.starling.models.FeedItem;
import com.starling.models.FeedItems;
import com.starling.services.IAccountsService;
import com.starling.services.IFeedService;
import com.starling.services.ISavingGoalsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class StarlingClientTest {

    private StarlingClient client;

    @Mock
    private HttpClient httpClient;

    @Mock
    private IAccountsService accountsService;

    @Mock
    private IFeedService feedService;

    @Mock
    private ISavingGoalsService savingGoalsService;

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
        int savings = 77;

        // Act
        when(accountsService.getPrimaryAccountId()).thenReturn(accountId);
        when(feedService.getFeedItems(accountId, weekStart)).thenReturn(createMockedFeedItems());

        client.processSavings(weekStart);

        // Assert
        verify(accountsService).getPrimaryAccountId();
        verify(feedService).getFeedItems(accountId, weekStart);
        verify(savingGoalsService).addMoneyToSavingsGoal(accountId, savings);
    }

    private FeedItems createMockedFeedItems() {
        FeedAmount feedAmount = new FeedAmount("USD", 123);
        FeedItem feedItem = new FeedItem("MockFeedItemId", "OUT", feedAmount);
        FeedItems feedItems = new FeedItems(new FeedItem[] { feedItem });
        return feedItems;
    }
}
