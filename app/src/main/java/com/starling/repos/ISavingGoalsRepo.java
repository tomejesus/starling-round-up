package com.starling.repos;

public interface ISavingGoalsRepo {
    String addMoneyToSavingsGoal(String accountId, String savingsGoalId, int amount);

    String getSavingGoalsList(String accountId);

    String createSavingsGoal(String accountId);
}
