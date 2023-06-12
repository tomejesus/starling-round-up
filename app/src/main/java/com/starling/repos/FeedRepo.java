package com.starling.repos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;

import com.starling.Constants;
import com.starling.models.Week;

public class FeedRepo {
    private HttpClient client;
    private Logger logger;

    public FeedRepo(HttpClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    public String getFeedItems(String accountId, String weekStartInputString, String bearerToken) {
        Week week;
        try {
            week = this.getWeekFromStartDate(weekStartInputString);
        } catch (DateTimeParseException e) {
            logger.error("Invalid start date format: {}", weekStartInputString, e);
            throw new IllegalArgumentException("Invalid start date format: " + weekStartInputString, e);
        }

        String requestString = String.format(
                Constants.GET_FEED_API_STRING_FORMAT,
                accountId,
                week.start,
                week.end);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestString))
                .header("Authorization", "Bearer " + bearerToken)
                .build();

        try {
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                logger.error("Failed to retrieve feed items. HTTP status code: {}", response.statusCode());
                throw new RuntimeException("Failed to retrieve feed items. HTTP status code: " + response.statusCode());
            }
            return response.body();
        } catch (Exception exception) {
            logger.error("An error occurred getting raw feed items: ", exception);
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
