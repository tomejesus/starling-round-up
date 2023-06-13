package com.starling.repos;

public interface ISavingGoalsRepo {
    String addMoneyToSavingsGoal(String accountId, String savingsGoalId, int amount, String bearerToken);

    String getSavingGoalsList(String accountId, String bearerToken);

    String createSavingsGoal(String accountId, String bearerToken);
}
