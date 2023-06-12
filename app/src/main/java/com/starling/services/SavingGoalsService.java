package com.starling.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.util.UUID;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.Constants;
import com.starling.models.CreateSavingsGoalResponse;
import com.starling.models.SavingsGoal;
import com.starling.models.SavingsGoalList;

public class SavingGoalsService {
    private HttpClient client;
    private Logger logger;
    private ObjectMapper objectMapper = new ObjectMapper();

    public SavingGoalsService(HttpClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    public boolean addMoneyToSavingsGoal(String accountId, String bearerToken, int amount) {
        String savingsGoalId = this.getRoundUpGoalId(accountId, bearerToken);
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

        try {
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception exception) {
            this.logger.error("An error occurred adding money to savings goal: ", exception);
            throw new RuntimeException("An error occurred adding money to savings goal: ", exception);
        }
    }

    private String getRoundUpGoalId(String accountId, String bearerToken) {
        String rawSavingsGoals = this.getRawSavingGoalsList(accountId, bearerToken);
        SavingsGoalList savingsGoals = this.parseSavingsGoalsList(rawSavingsGoals);

        for (SavingsGoal savingsGoal : savingsGoals.savingsGoalList) {
            if ("RoundUp1".equals(savingsGoal.name)) {
                return savingsGoal.savingsGoalUid;
            }
        }

        this.logger.info("Round up goal not found creating new goal.");
        return createSavingsGoal(accountId, bearerToken);
    }

    private String createSavingsGoal(String accountId, String bearerToken) {
        String requestString = String.format(Constants.SAVINGS_GOALS_API_STRING_FORMAT, accountId);
        BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(Constants.SAVINGS_GOAL_REQUEST_BODY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestString))
                .header("Authorization", "Bearer " + bearerToken)
                .header("Content-Type", "application/json")
                .PUT(bodyPublisher)
                .build();

        try {
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), CreateSavingsGoalResponse.class).savingsGoalUid;
        } catch (Exception exception) {
            this.logger.error("An error occurred creating savings goal: ", exception);
            throw new RuntimeException("An error occurred creating savings goal: ", exception);
        }
    }

    private SavingsGoalList parseSavingsGoalsList(String rawSavingsGoals) {
        try {
            SavingsGoalList savingsGoals = objectMapper.readValue(rawSavingsGoals, SavingsGoalList.class);
            return savingsGoals;
        } catch (Exception exception) {
            this.logger.error("An error occurred parsing savings goals: ", exception);
            throw new RuntimeException("An error occurred parsing savings goals: ", exception);
        }
    }

    private String getRawSavingGoalsList(String accountId, String bearerToken) {
        String requestString = String.format(Constants.SAVINGS_GOALS_API_STRING_FORMAT, accountId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestString))
                .header("Authorization", "Bearer " + bearerToken)
                .build();

        try {
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception exception) {
            this.logger.error("An error occurred getting raw savings goal list: ", exception);
            throw new RuntimeException("An error occurred getting raw savings goal list: ", exception);
        }
    }
}
