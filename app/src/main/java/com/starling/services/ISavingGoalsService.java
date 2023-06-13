package com.starling.services;

public interface ISavingGoalsService {
    void addMoneyToSavingsGoal(String accountId, String bearerToken, int amount);
}
