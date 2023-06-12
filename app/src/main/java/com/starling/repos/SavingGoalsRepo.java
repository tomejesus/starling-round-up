package com.starling.repos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.slf4j.Logger;

import com.starling.Constants;

public class SavingGoalsRepo {
    private HttpClient client;
    private Logger logger;

    public SavingGoalsRepo(HttpClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    public String addMoneyToSavingsGoal(String accountId, String savingsGoalId, int amount, String bearerToken) {
        String transferUid = UUID.randomUUID().toString();
        String requestString = String.format(Constants.ADD_MONEY_TO_SAVINGS_GOAL_API_STRING_FORMAT, accountId,
                savingsGoalId, transferUid);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestString))
                .header("Authorization", "Bearer " + bearerToken)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers
                        .ofString(String.format(Constants.ADD_MONEY_TO_SAVINGS_GOAL_REQUEST_BODY_FORMAT, amount)))
                .build();

        HttpResponse<String> response;
        try {
            response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            logger.error("Failed to send request to add money to savings goal: ", exception);
            throw new RuntimeException("Failed to send request to add money to savings goal: ", exception);
        }

        if (response.statusCode() != 200) {
            logger.error("Failed to add money to savings goal. HTTP status code: " + response.statusCode());
            throw new RuntimeException(
                    "Failed to add money to savings goal. HTTP status code: " + response.statusCode());
        }

        return response.body();
    }

    public String getSavingGoalsList(String accountId, String bearerToken) {
        String requestString = String.format(Constants.SAVINGS_GOALS_API_STRING_FORMAT, accountId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestString))
                .header("Authorization", "Bearer " + bearerToken)
                .build();

        HttpResponse<String> response;
        try {
            response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            logger.error("Failed to send request to get savings goals list: ", exception);
            throw new RuntimeException("Failed to send request to get savings goals list: ", exception);
        }

        if (response.statusCode() != 200) {
            logger.error("Failed to get savings goals list. HTTP status code: " + response.statusCode());
            throw new RuntimeException("Failed to get savings goals list. HTTP status code: " + response.statusCode());
        }

        return response.body();
    }

    public String createSavingsGoal(String accountId, String bearerToken) {
        String requestString = String.format(Constants.SAVINGS_GOALS_API_STRING_FORMAT, accountId);
        BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(Constants.SAVINGS_GOAL_REQUEST_BODY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestString))
                .header("Authorization", "Bearer " + bearerToken)
                .header("Content-Type", "application/json")
                .PUT(bodyPublisher)
                .build();

        HttpResponse<String> response;
        try {
            response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            logger.error("Failed to send request to create savings goal: ", exception);
            throw new RuntimeException("Failed to send request to create savings goal: ", exception);
        }

        if (response.statusCode() != 200) {
            logger.error("Failed to create savings goal. HTTP status code: " + response.statusCode());
            throw new RuntimeException("Failed to create savings goal. HTTP status code: " + response.statusCode());
        }

        return response.body();
    }
}
