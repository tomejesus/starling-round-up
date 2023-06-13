package com.starling.repos;

import java.util.UUID;

import org.slf4j.Logger;

import com.starling.Constants;
import com.starling.client.IHttpClientWrapper;

public class SavingGoalsRepo implements ISavingGoalsRepo {
    private IHttpClientWrapper httpClientWrapper;
    private Logger logger;

    public SavingGoalsRepo(IHttpClientWrapper httpClientWrapper, Logger logger) {
        this.httpClientWrapper = httpClientWrapper;
        this.logger = logger;
    }

    public String addMoneyToSavingsGoal(String accountId, String savingsGoalId, int amount) {
        String transferUid = UUID.randomUUID().toString();
        String requestString = String.format(Constants.ADD_MONEY_TO_SAVINGS_GOAL_API_STRING_FORMAT, accountId,
                savingsGoalId, transferUid);

        try {
            String response = httpClientWrapper.put(requestString,
                    String.format(Constants.ADD_MONEY_TO_SAVINGS_GOAL_REQUEST_BODY_FORMAT, amount));
            return response;
        } catch (Exception exception) {
            logger.error("Failed to send request to add money to savings goal: ", exception);
            throw new RuntimeException("Failed to send request to add money to savings goal: ", exception);
        }
    }

    public String getSavingGoalsList(String accountId) {
        String requestString = String.format(Constants.SAVINGS_GOALS_API_STRING_FORMAT, accountId);

        try {
            String response = httpClientWrapper.get(requestString);
            return response;
        } catch (Exception exception) {
            logger.error("Failed to send request to get savings goals list: ", exception);
            throw new RuntimeException("Failed to send request to get savings goals list: ", exception);
        }
    }

    public String createSavingsGoal(String accountId) {
        String requestString = String.format(Constants.SAVINGS_GOALS_API_STRING_FORMAT, accountId);

        try {
            String response = httpClientWrapper.put(requestString, Constants.SAVINGS_GOAL_REQUEST_BODY);
            return response;
        } catch (Exception exception) {
            logger.error("Failed to send request to create savings goal: ", exception);
            throw new RuntimeException("Failed to send request to create savings goal: ", exception);
        }
    }
}
