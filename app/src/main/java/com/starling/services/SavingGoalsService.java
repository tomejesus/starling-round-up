package com.starling.services;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.Constants;
import com.starling.models.AddToSavingsGoalResponse;
import com.starling.models.CreateSavingsGoalResponse;
import com.starling.models.SavingsGoal;
import com.starling.models.SavingsGoalList;
import com.starling.repos.SavingGoalsRepo;

public class SavingGoalsService {
    private SavingGoalsRepo repo;
    private Logger logger;
    private ObjectMapper objectMapper;

    public SavingGoalsService(SavingGoalsRepo repo, ObjectMapper objectMapper, Logger logger) {
        this.repo = repo;
        this.objectMapper = objectMapper;
        this.logger = logger;
    }

    public void addMoneyToSavingsGoal(String accountId, String bearerToken, int amount) {
        String savingsGoalId;
        try {
            savingsGoalId = this.getSavingsGoalId(accountId, bearerToken);
        } catch (Exception e) {
            this.logger.error("Failed to get savings goal ID", e);
            throw new RuntimeException("Failed to get savings goal ID", e);
        }

        String response;
        try {
            response = this.repo.addMoneyToSavingsGoal(accountId, savingsGoalId, amount, bearerToken);
        } catch (Exception e) {
            this.logger.error("Failed to add money to savings goal", e);
            throw new RuntimeException("Failed to add money to savings goal", e);
        }

        AddToSavingsGoalResponse addMoneyResponse;

        try {
            addMoneyResponse = objectMapper.readValue(response, AddToSavingsGoalResponse.class);
        } catch (JsonProcessingException e) {
            this.logger.error("Failed to parse add money response", e);
            throw new RuntimeException("Failed to parse add  money response", e);
        }

        if ("true".equals(addMoneyResponse.success)) {
            String amountInDecimal = String.format("%.2f", (double) amount / 100);
            this.logger.info("GBP " + amountInDecimal + " added to savings goal.");
            return;
        } else {
            this.logger.error("Unable to add money to savings goal.", response);
            throw new RuntimeException("Unable to add money to savings goal.");
        }
    }

    private String getSavingsGoalId(String accountId, String bearerToken) {
        SavingsGoalList savingsGoals = this.getSavingsGoalsList(accountId, bearerToken);

        for (SavingsGoal savingsGoal : savingsGoals.savingsGoalList) {
            if (Constants.SAVING_GOAL_NAME.equals(savingsGoal.name)) {
                this.logger.info("Round up goal found.");
                return savingsGoal.savingsGoalUid;
            }
        }

        this.logger.info("Round up goal not found creating new goal.");
        String savingsGoalResponse = this.repo.createSavingsGoal(accountId, bearerToken);
        try {
            return objectMapper.readValue(savingsGoalResponse, CreateSavingsGoalResponse.class).savingsGoalUid;
        } catch (JsonProcessingException e) {
            this.logger.error("Failed to parse savings goal response", e);
            throw new RuntimeException("Failed to parse savings goal response", e);
        }

    }

    private SavingsGoalList getSavingsGoalsList(String accountId, String bearerToken) {
        String rawSavingsGoals;
        try {
            rawSavingsGoals = this.repo.getSavingGoalsList(accountId, bearerToken);
        } catch (Exception e) {
            this.logger.error("Failed to get savings goals list", e);
            throw new RuntimeException("Failed to get savings goals list", e);
        }

        try {
            SavingsGoalList savingsGoals = objectMapper.readValue(rawSavingsGoals, SavingsGoalList.class);
            return savingsGoals;
        } catch (Exception exception) {
            this.logger.error("Failed to parse savings goals", exception);
            throw new RuntimeException("Failed to parse savings goals", exception);
        }
    }
}
