package com.starling.services;

import static org.mockito.Mockito.mock;

import java.net.http.HttpClient;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SavingsGoalServiceTest {
    private final HttpClient client = mock(HttpClient.class);
    private final Logger logger = LoggerFactory.getLogger(FeedServiceTest.class);
    private final SavingGoalsService savingsGoalService = new SavingGoalsService(client, logger);

    @Test
    void testAddsMoneyToSavingsGoal() {
        // Arrange
        // mock get http response for get savings goal
        // mock put http response for add money to savings goal

        // Act

        // Assert

    }
}
