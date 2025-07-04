package com.starling.client;

import com.starling.models.FeedItems;
import com.starling.repos.*;
import com.starling.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StarlingClient implements IStarlingClient {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(StarlingClient.class);

    private IAccountsService accountsService;
    private IFeedService feedService;
    private ISavingGoalsService savingGoalsService;

    public StarlingClient(IHttpClientWrapper httpClientWrapper) {
        AccountsRepo accountsRepo = new AccountsRepo(httpClientWrapper, LOGGER);
        this.accountsService = new AccountsService(accountsRepo, OBJECT_MAPPER, LOGGER);

        FeedRepo feedRepo = new FeedRepo(httpClientWrapper, LOGGER);
        this.feedService = new FeedService(feedRepo, OBJECT_MAPPER, LOGGER);

        SavingGoalsRepo savingsGoalsRepo = new SavingGoalsRepo(httpClientWrapper, LOGGER);
        this.savingGoalsService = new SavingGoalsService(savingsGoalsRepo, OBJECT_MAPPER, LOGGER);
    }

    public StarlingClient(IAccountsService accountsService, IFeedService feedService,
            ISavingGoalsService savingGoalsService) {
        this.accountsService = accountsService;
        this.feedService = feedService;
        this.savingGoalsService = savingGoalsService;
    }

    public void processSavings(String weekStart) {
        try {
            String accountId = this.accountsService.getPrimaryAccountId();
            FeedItems feedItems = this.feedService.getFeedItems(accountId, weekStart);
            int savings = RoundUpService.calculateRoundUp(feedItems);
            this.savingGoalsService.addMoneyToSavingsGoal(accountId, savings);
        } catch (Exception e) {
            LOGGER.error("An error occurred while processing savings: ", e);
            throw e;
        }
    }
}
