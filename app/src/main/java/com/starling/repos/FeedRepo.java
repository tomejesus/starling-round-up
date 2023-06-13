package com.starling.repos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;

import com.starling.Constants;
import com.starling.client.IHttpClientWrapper;
import com.starling.models.Week;

public class FeedRepo implements IFeedRepo {
    private IHttpClientWrapper httpClientWrapper;
    private Logger logger;

    public FeedRepo(IHttpClientWrapper httpClientWrapper, Logger logger) {
        this.httpClientWrapper = httpClientWrapper;
        this.logger = logger;
    }

    public String getFeedItems(String accountId, String weekStartInputString) {
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

        try {
            String response = httpClientWrapper.get(requestString);
            return response;
        } catch (Exception exception) {
            logger.error("An error occurred getting raw feed items: ", exception);
            throw new RuntimeException("An error occurred getting raw feed items: ", exception);
        }
    }

    private Week getWeekFromStartDate(String startDate) {
        LocalDate weekStart = LocalDate.parse(startDate);
        LocalDate weekEnd = weekStart.plusDays(7);

        String weekStartRequestString = String.format(Constants.DATE_FORMAT, weekStart.toString());
        String weekEndRequestString = String.format(Constants.DATE_FORMAT, weekEnd.toString());

        Week week = new Week(weekStartRequestString, weekEndRequestString);
        return week;
    }
}
