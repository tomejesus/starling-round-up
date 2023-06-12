package com.starling.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SavingsGoalList {
    public SavingsGoal[] savingsGoalList;

    @JsonCreator
    public SavingsGoalList(@JsonProperty("savingsGoalList") SavingsGoal[] savingsGoalList) {
        this.savingsGoalList = savingsGoalList;
    }
}
