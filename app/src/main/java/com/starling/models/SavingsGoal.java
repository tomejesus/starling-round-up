package com.starling.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsGoal {
    public String savingsGoalUid;
    public String name;

    @JsonCreator
    public SavingsGoal(
            @JsonProperty("savingsGoalUid") String savingsGoalUid,
            @JsonProperty("name") String name) {
        this.savingsGoalUid = savingsGoalUid;
        this.name = name;
    }

}
