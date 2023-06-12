package com.starling.services;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.models.FeedItems;
import com.starling.repos.FeedRepo;

public class FeedService {
    private FeedRepo repo;
    private ObjectMapper objectMapper;
    private Logger logger;

    public FeedService(FeedRepo repo, ObjectMapper objectMapper, Logger logger) {
        this.repo = repo;
        this.objectMapper = objectMapper;
        this.logger = logger;
    }

    public FeedItems getFeedItems(String accountId, String weekEndInputString, String bearerToken) {
        String rawFeedItems;
        try {
            rawFeedItems = this.repo.getFeedItems(accountId, weekEndInputString, bearerToken);
            if (rawFeedItems == null || rawFeedItems.isEmpty()) {
                this.logger.error("No feed items returned for account: {}", accountId);
                throw new RuntimeException("No feed items returned for account: " + accountId);
            }
            this.logger.info("Successfully retrieved feed items for account: {}", accountId);
        } catch (Exception exception) {
            String errorMsg = String.format("An error occurred when retrieving feed items for account: %s", accountId);
            this.logger.error(errorMsg, exception);
            throw new RuntimeException(errorMsg, exception);
        }

        try {
            FeedItems feedItems = this.objectMapper.readValue(rawFeedItems, FeedItems.class);
            return feedItems;
        } catch (Exception exception) {
            String errorMsg = String.format("An error occurred when parsing the feed items for account: %s", accountId);
            this.logger.error(errorMsg, exception);
            throw new RuntimeException(errorMsg, exception);
        }
    }
}
