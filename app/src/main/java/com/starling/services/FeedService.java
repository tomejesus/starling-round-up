package com.starling.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.Constants;
import com.starling.models.FeedItems;
import com.starling.models.Week;

public class FeedService {
    private HttpClient client;
    private Logger logger;

    public FeedService(HttpClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    public FeedItems getFeedItems(String accountId, String weekEndInputString, String bearerToken) {
        String rawFeedItems = this.getRawFeedItems(accountId, weekEndInputString, bearerToken);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            FeedItems feedItems = objectMapper.readValue(rawFeedItems, FeedItems.class);
            return feedItems;
        } catch (Exception exception) {
            this.logger.error("An error occurred parsing feed items: ", exception);
            throw new RuntimeException("An error occurred parsing feed items: ", exception);
        }
    }

    private String getRawFeedItems(String accountId, String weekStartInputString, String bearerToken) {
        Week week = this.getWeekFromStartDate(weekStartInputString);

        String requestString = String.format(
                Constants.FEED_API_STRING_FORMAT,
                accountId,
                week.start,
                week.end);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestString))
                .header("Authorization", "Bearer " + bearerToken)
                .build();

        try {
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception exception) {
            this.logger.error("An error occurred getting raw feed items: ", exception);
            throw new RuntimeException("An error occurred getting raw feed items: ", exception);
        }
    }

    private Week getWeekFromStartDate(String startDate) {
        LocalDate weekStart = LocalDate.parse(startDate);
        LocalDate weekEnd = weekStart.plusDays(7);

        String weekStartRequestString = weekStart.toString() + "T00:00:00.000Z";
        String weekEndRequestString = weekEnd.toString() + "T00:00:00.000Z";

        Week week = new Week(weekStartRequestString, weekEndRequestString);
        return week;
    }
}
